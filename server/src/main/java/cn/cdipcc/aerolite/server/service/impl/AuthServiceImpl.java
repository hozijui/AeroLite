package cn.cdipcc.aerolite.server.service.impl;

import cn.cdipcc.aerolite.server.dao.RoleDao;
import cn.cdipcc.aerolite.server.dao.UserDao;
import cn.cdipcc.aerolite.server.dto.AuthInfo;
import cn.cdipcc.aerolite.server.dto.RolePermission;
import cn.cdipcc.aerolite.server.dto.UserRole;
import cn.cdipcc.aerolite.server.config.security.AuthUser;
import cn.cdipcc.aerolite.server.entity.Permission;
import cn.cdipcc.aerolite.server.entity.User;
import cn.cdipcc.aerolite.server.service.AuthService;
import cn.cdipcc.aerolite.server.utils.JwtUtil;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Service("authService")
public class AuthServiceImpl implements AuthService {
    @Resource
    private UserDao userDao;

    @Resource
    private RoleDao roleDao;

    @Resource
    private JwtUtil jwtUtil;

    @Resource
    private RequestMappingHandlerMapping mapping;

    @Lazy
    @Resource
    private AuthenticationManager authenticationManager;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserRole user = userDao.getRolesByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username + "用户不存在");
        } {
            return new AuthUser(user);
        }
    }

    @Override
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        if (!registered(request)) return true;
        if (authentication.getPrincipal() instanceof UserDetails) {
            String method = request.getMethod();
            String uri = request.getRequestURI();
            for (GrantedAuthority authority : authentication.getAuthorities()) {
                String roleName = authority.getAuthority().substring(5);
                RolePermission rolePermission = roleDao.getPermissionByRole(roleName);
                for (Permission permission : rolePermission.getPermissions()) {
                    if (method.equalsIgnoreCase(permission.getMethod()) && uri.equals(permission.getUri())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public AuthInfo login(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = userDao.queryByUsername(((UserDetails) authentication.getPrincipal()).getUsername());
        Date loginTime = new Date();
        user.setLastLogin(loginTime);
        userDao.update(user);
        user = userDao.queryById(user.getId());
        String secretKey = jwtUtil.genSecretKey(user.getLastLogin());

        Instant issueAt = Instant.now();
        String token = jwtUtil.getToken(false, secretKey, user.getUsername(), issueAt);
        String refreshToken = jwtUtil.getToken(true, secretKey, user.getUsername(), issueAt);
        return new AuthInfo(token, refreshToken);
    }

    @Override
    public void logout() {
        SecurityContextHolder.clearContext();
    }

    @Override
    public AuthInfo refresh(String refreshToken) {
        DecodedJWT decodedToken = jwtUtil.decode(refreshToken);
        if (decodedToken != null) {
            String username = decodedToken.getSubject();
            User user = userDao.queryByUsername(username);
            String secretKey = jwtUtil.genSecretKey(user.getLastLogin());
            if (jwtUtil.verify(secretKey, refreshToken, true)) {
                return new AuthInfo(jwtUtil.refreshToken(secretKey, decodedToken), refreshToken);
            }
        }
        return null;
    }

    private boolean registered(HttpServletRequest request) {
        String currentMethod = request.getMethod();
        for (RequestMappingInfo info : mapping.getHandlerMethods().keySet()) {
            Set<String> methods = info.getMethodsCondition().getMethods().stream().map(Enum::toString).collect(Collectors.toSet());
            if (methods.contains(currentMethod)) {
                Set<String> urls = info.getPatternValues();
                for (String uri : urls) {
                    if (!uri.startsWith("/api")) continue;
                    AntPathRequestMatcher matcher = new AntPathRequestMatcher(uri);
                    if (matcher.matches(request)) return true;
                }
            }
        }
        return false;
    }
}
