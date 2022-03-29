package cn.cdipcc.aerolite.server.exception;

import cn.cdipcc.aerolite.server.common.ResultCode;
import cn.cdipcc.aerolite.server.dto.ApiResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SecurityEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setContentType("application/json; charset=utf-8");
        ResultCode resultCode = ResultCode.INVALID_TOKEN;
        if (authException instanceof BadCredentialsException) {
            resultCode = ResultCode.LOGIN_ERROR;
        } else if (authException instanceof DisabledException) {
            resultCode = ResultCode.DISABLED_USER;
        }
        response.setStatus(resultCode.status);
        mapper.writeValue(response.getWriter(), new ApiResult<Void>(resultCode));
    }
}
