package com.alioth4j.chatgpt3p5o.common;

import lombok.Getter;
import lombok.Setter;

/**
 * 通用返回类
 * @author Alioth4J
 */
@Getter
@Setter
public class CommonResult<T> {

    private long code;
    private String message;
    private T data;

    protected CommonResult() {
    }

    protected CommonResult(long code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> CommonResult<T> success() {
        return new CommonResult<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), null);
    }

    public static <T> CommonResult<T> success(T data) {
        return new CommonResult<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
    }

    public static <T> CommonResult<T> success(String message, T data) {
        return new CommonResult<>(ResultCode.SUCCESS.getCode(), message, data);
    }

    public static <T> CommonResult<T> failed() {
        return new CommonResult<>(ResultCode.FAILED.getCode(), ResultCode.FAILED.getMessage(), null);
    }

    public static <T> CommonResult<T> failed(T data) {
        return new CommonResult<>(ResultCode.FAILED.getCode(), ResultCode.FAILED.getMessage(), data);
    }

    public static <T> CommonResult<T> failed(String message, T data) {
        return new CommonResult<>(ResultCode.FAILED.getCode(), message, data);
    }

    public static <T> CommonResult<T> validateFailed() {
        return new CommonResult<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null);
    }

    public static <T> CommonResult<T> validateFailed(T data) {
        return new CommonResult<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), data);
    }

    public static <T> CommonResult<T> validateFailed(String message, T data) {
        return new CommonResult<>(ResultCode.VALIDATE_FAILED.getCode(), message, data);
    }

    public static <T> CommonResult<T> unauthorized() {
        return new CommonResult<>(ResultCode.UNAUTHORIZED.getCode(), ResultCode.UNAUTHORIZED.getMessage(), null);
    }

    public static <T> CommonResult<T> unauthorized(T data) {
        return new CommonResult<>(ResultCode.UNAUTHORIZED.getCode(), ResultCode.UNAUTHORIZED.getMessage(), data);
    }

    public static <T> CommonResult<T> unauthorized(String message, T data) {
        return new CommonResult<>(ResultCode.UNAUTHORIZED.getCode(), message, data);
    }

    public static <T> CommonResult<T> forbidden() {
        return new CommonResult<>(ResultCode.FORBIDDEN.getCode(), ResultCode.FORBIDDEN.getMessage(), null);
    }

    public static <T> CommonResult<T> forbidden(T data) {
        return new CommonResult<>(ResultCode.FORBIDDEN.getCode(), ResultCode.FORBIDDEN.getMessage(), data);
    }

    public static <T> CommonResult<T> forbidden(String message, T data) {
        return new CommonResult<>(ResultCode.FORBIDDEN.getCode(), message, data);
    }
}
