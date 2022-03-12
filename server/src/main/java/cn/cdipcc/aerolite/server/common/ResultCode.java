package cn.cdipcc.aerolite.server.common;

public enum ResultCode {
    SUCCESS(2000, "成功"),

    FAIL(2001, "失败"),

    BAD_REQUEST(4000, "错误请求"),

    REQUEST_NOT_FOUND(4004, "不存在"),

    METHOD_NOT_ALLOW(4005, "不支持的请求方式"),

    INVALID_TOKEN(4011, "Token非法/失效"),

    LOGIN_ERROR(4012, "账户或密码错误"),

    ACCESS_DENIED(4003, "无权限"),

    UNKNOWN_ERROR(5000, "未知错误");

    public Integer code;

    public Boolean success;

    public String msg;

    ResultCode(Integer code, String msg) {
        this.success = code == 2000;
        this.code = code;
        this.msg = msg;
    }
}
