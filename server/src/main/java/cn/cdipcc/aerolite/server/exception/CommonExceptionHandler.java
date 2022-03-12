package cn.cdipcc.aerolite.server.exception;

import cn.cdipcc.aerolite.server.dto.ApiResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class CommonExceptionHandler {
    @ResponseBody
    @ExceptionHandler(CustomException.class)
    public ApiResult<Void> CustomExceptionHandler(CustomException e) {
        return new ApiResult<>(e.getResultCode());
    }
}
