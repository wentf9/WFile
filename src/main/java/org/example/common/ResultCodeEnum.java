package org.example.common;

import lombok.Getter;

@Getter
public enum ResultCodeEnum {
    SUCCESS(200, "操作成功"),
    FAIL(500, "操作失败"),
    DATABASE_CREATE_ERROR(501, "数据库创建失败"),
    DATABASE_CONFIG_ERROR(502, "数据库配置错误"),
    NOT_FOUND(404, "未找到"),
    UNAUTHORIZED(401, "未授权"),
    STORAGE_NOT_FOUND(404, "存储库不存在"),
    FORBIDDEN(403, "禁止访问"),
    NOT_ACCEPTABLE(406, "请求格式不支持"),
    REQUEST_TIMEOUT(408, "请求超时"),
    CONFLICT(409, "请求冲突"),
    GONE(410, "请求资源已不存在"),
    UNSUPPORTED_MEDIA_TYPE(415, "不支持的媒体类型");
    private final int code;
    private final String message;
    ResultCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
