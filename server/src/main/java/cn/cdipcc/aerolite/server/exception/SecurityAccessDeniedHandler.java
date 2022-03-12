package cn.cdipcc.aerolite.server.exception;

import cn.cdipcc.aerolite.server.common.ResultCode;
import cn.cdipcc.aerolite.server.dto.ApiResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SecurityAccessDeniedHandler implements AccessDeniedHandler {
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        response.setStatus(403);
        response.setContentType("application/json; charset=utf-8");
        mapper.writeValue(response.getWriter(), new ApiResult<Void>(ResultCode.ACCESS_DENIED));
    }
}
