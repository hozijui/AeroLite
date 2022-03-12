package cn.cdipcc.aerolite.server.dto;

import cn.cdipcc.aerolite.server.common.ResultCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "统一返回结果")
public class ApiResult<T> implements Serializable {
    private static final long serialVersionUID = -5881791791498134223L;

    @ApiModelProperty(value = "状态码", required = true)
    private Integer code;

    @ApiModelProperty(value = "是否成功", required = true)
    private Boolean success;

    @ApiModelProperty(value = "状态消息", required = true)
    private String msg;

    @ApiModelProperty(value = "返回数据")
    private T data;

    public ApiResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
        this.success = code == 2000;
    }

    public ApiResult(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.success = code == 2000;
        this.data = data;
    }

    public ApiResult(ResultCode resultCode) {
        this.code = resultCode.code;
        this.msg = resultCode.msg;
        this.success = resultCode.success;
    }

    public ApiResult(ResultCode resultCode, T data) {
        this.code = resultCode.code;
        this.msg = resultCode.msg;
        this.success = resultCode.success;
        this.data = data;
    }

    public static <T> ApiResult<T> success() {
        return new ApiResult(ResultCode.SUCCESS);
    }

    public static <T> ApiResult<T> success(T data) {
        return new ApiResult(ResultCode.SUCCESS, data);
    }

    public static <T> ApiResult<T> fail() {
        return new ApiResult(ResultCode.FAIL);
    }

    public static <T> ApiResult<T> fail(T data) {
        return new ApiResult(ResultCode.FAIL, data);
    }
}
