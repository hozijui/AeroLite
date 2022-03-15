package cn.cdipcc.aerolite.server.config.security;

import cn.cdipcc.aerolite.server.entity.User;
import cn.cdipcc.aerolite.server.service.AuthService;
import cn.cdipcc.aerolite.server.service.UserService;
import cn.cdipcc.aerolite.server.utils.JwtUtil;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Resource
    private UserService userService;

    @Resource
    private AuthService authService;

    @Resource
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            String jwt = bearerToken.substring(7);
            DecodedJWT decodedJWT = jwtUtil.decode(jwt);

            if (decodedJWT != null) {
                String username = decodedJWT.getSubject();
                User user = userService.queryByUsername(username);
                if (user.getLastLogin() != null) {
                    String secretKey = jwtUtil.genSecretKey(user.getLastLogin());
                    if (jwtUtil.verify(secretKey, jwt, false)) {
                        UserDetails userDetails = authService.loadUserByUsername(username);
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
