package cn.cdipcc.aerolite.server.exception;

import cn.cdipcc.aerolite.server.common.ResultCode;
import cn.cdipcc.aerolite.server.dto.ApiResult;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("${server.error.path:${error.path:/error}}")
public class CustomErrorController extends AbstractErrorController {
    public CustomErrorController(ErrorAttributes errorAttributes) {
        super(errorAttributes);
    }

    @RequestMapping
    public ApiResult<Void> error(HttpServletRequest request) {
        HttpStatus status = getStatus(request);
        ResultCode resultCode = ResultCode.UNKNOWN_ERROR;
        if (status == HttpStatus.NOT_FOUND) {
            resultCode = ResultCode.REQUEST_NOT_FOUND;
        } else if (status == HttpStatus.BAD_REQUEST) {
            resultCode = ResultCode.BAD_REQUEST;
        } else if (status == HttpStatus.METHOD_NOT_ALLOWED) {
            resultCode = ResultCode.METHOD_NOT_ALLOW;
        }
        return new ApiResult<>(resultCode);
    }
}
