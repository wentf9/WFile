package org.example.wfile.common;

import org.apache.commons.lang3.ObjectUtils;
import org.example.wfile.core.constant.HttpStatus;

import java.io.Serial;
import java.util.HashMap;

public class ApiResponse extends HashMap<String, Object> {
    @Serial
    private static final long serialVersionUID = 1L;

    /** 状态码 */
    public static final String CODE_TAG = "code";

    /** 返回内容 */
    public static final String MSG_TAG = "msg";

    /** 数据对象 */
    public static final String DATA_TAG = "data";

    /**
     * 初始化一个新创建的 ApiResponse 对象，使其表示一个空消息。
     */
    public ApiResponse() {
    }

    /**
     * 初始化一个新创建的 ApiResponse 对象
     *
     * @param code 状态码
     * @param msg 返回内容
     */
    public ApiResponse(int code, String msg) {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
    }

    /**
     * 初始化一个新创建的 ApiResponse 对象
     *
     * @param code 状态码
     * @param msg 返回内容
     * @param data 数据对象
     */
    public ApiResponse(int code, String msg, Object data) {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
        if (ObjectUtils.isNotEmpty(data)) {
            super.put(DATA_TAG, data);
        }
    }

    /**
     * 返回成功消息
     *
     * @return 成功消息
     */
    public static ApiResponse success() {
        return ApiResponse.success("操作成功");
    }

    /**
     * 返回成功数据
     *
     * @return 成功消息
     */
    public static ApiResponse success(Object data) {
        return ApiResponse.success("操作成功", data);
    }

    /**
     * 返回成功消息
     *
     * @param msg 返回内容
     * @return 成功消息
     */
    public static ApiResponse success(String msg) {
        return ApiResponse.success(msg, null);
    }

    /**
     * 返回成功消息
     *
     * @param msg 返回内容
     * @param data 数据对象
     * @return 成功消息
     */
    public static ApiResponse success(String msg, Object data) {
        return new ApiResponse(HttpStatus.SUCCESS, msg, data);
    }

    /**
     * 返回错误消息
     *
     * @return
     */
    public static ApiResponse error() {
        return ApiResponse.error("操作失败");
    }

    /**
     * 返回错误消息
     *
     * @param msg 返回内容
     * @return 警告消息
     */
    public static ApiResponse error(String msg) {
        return ApiResponse.error(msg, null);
    }

    /**
     * 返回错误消息
     *
     * @param msg 返回内容
     * @param data 数据对象
     * @return 警告消息
     */
    public static ApiResponse error(String msg, Object data) {
        return new ApiResponse(HttpStatus.ERROR, msg, data);
    }

    /**
     * 返回错误消息
     *
     * @param code 状态码
     * @param msg 返回内容
     * @return 警告消息
     */
    public static ApiResponse error(int code, String msg) {
        return new ApiResponse(code, msg, null);
    }

    public static ApiResponse error(ResultCodeEnum resultCodeEnum) {
        return new ApiResponse(resultCodeEnum.getCode(), resultCodeEnum.getMessage(), null);
    }

    public static ApiResponse error(ResultCodeEnum resultCodeEnum, Object data) {
        return new ApiResponse(resultCodeEnum.getCode(), resultCodeEnum.getMessage(), data);
    }

    /**
     * 方便链式调用
     *
     * @param key 键
     * @param value 值
     * @return 数据对象
     */
    @Override
    public ApiResponse put(String key, Object value) {
        super.put(key, value);
        return this;
    }

}
