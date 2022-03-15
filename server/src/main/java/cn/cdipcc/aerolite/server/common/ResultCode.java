package cn.cdipcc.aerolite.server.common;

public enum ResultCode {
    SUCCESS(200, 2000, "成功"),

    FAIL(200, 2001, "失败"),

    BAD_REQUEST(400, 4000, "错误请求"),

    REQUEST_NOT_FOUND(404, 4004, "不存在"),

    METHOD_NOT_ALLOW(405, 4005, "不支持的请求方式"),

    INVALID_TOKEN(401, 4011, "Token非法/失效"),

    INVALID_REFRESH_TOKEN(401, 4012, "Refresh Token非法/失效"),

    LOGIN_ERROR(401, 4013, "账户或密码错误"),

    ACCESS_DENIED(403, 4003, "无权限"),

    UNKNOWN_ERROR(500, 5000, "未知错误");

    public final Integer status;

    public final Integer code;

    public final Boolean success;

    public final String msg;

    ResultCode(Integer status, Integer code, String msg) {
        this.status = status;
        this.success = code == 2000;
        this.code = code;
        this.msg = msg;
    }
}
