package cn.cdipcc.aerolite.server.exception;

import cn.cdipcc.aerolite.server.common.ResultCode;
import lombok.Getter;

public class CustomException extends RuntimeException {
    @Getter
    private ResultCode resultCode;

    public CustomException(ResultCode resultCode){
        super(resultCode.msg);
        this.resultCode = resultCode;
    }
}
