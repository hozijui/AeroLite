package cn.cdipcc.aerolite.server.service;

import cn.cdipcc.aerolite.server.dto.AuthInfo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.servlet.http.HttpServletRequest;

public interface AuthService extends UserDetailsService {
    boolean hasPermission(HttpServletRequest request, Authentication authentication);

    AuthInfo login(String username, String password);

    void logout();

    AuthInfo refresh(String refreshToken);
}
