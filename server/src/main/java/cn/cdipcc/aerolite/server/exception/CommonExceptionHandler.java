package cn.cdipcc.aerolite.server.exception;

import cn.cdipcc.aerolite.server.dto.ApiResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class CommonExceptionHandler {
    @ResponseBody
    @ExceptionHandler(CustomException.class)
    public ApiResult<Void> CustomExceptionHandler(CustomException e, HttpServletResponse response) {
        response.setStatus(e.getResultCode().status);
        return new ApiResult<>(e.getResultCode());
    }
}
