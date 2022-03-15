package cn.cdipcc.aerolite.server.exception;

import cn.cdipcc.aerolite.server.common.ResultCode;
import cn.cdipcc.aerolite.server.dto.ApiResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.BadCredentialsException;
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
        ResultCode resultCode = authException instanceof BadCredentialsException ? ResultCode.LOGIN_ERROR : ResultCode.INVALID_TOKEN;
        response.setStatus(resultCode.status);
        mapper.writeValue(response.getWriter(), new ApiResult<Void>(resultCode));
    }
}
