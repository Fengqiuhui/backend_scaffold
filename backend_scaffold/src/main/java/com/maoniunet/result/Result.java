package com.maoniunet.result;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * 数据返回类
 */
@Data
public class Result {

    /**
     * 状态码
     */
    private int code;
    /**
     * 是否成功
     */
    private Boolean success;
    /**
     * 数据
     */
    private Object data;
    /**
     * 补充数据
     */
    private Object extraData;
    /**
     * 补充信息,通常用于请求错误说明
     */
    private String message;

    private Result() {}

    public static ResponseEntity<Result> success(Object data) {
        return success(data, null, HttpStatus.OK);
    }

    public static ResponseEntity<Result> success(HttpStatus httpStatus) {
        return success(null, httpStatus);
    }

    public static ResponseEntity<Result> success(Object data, HttpStatus httpStatus) {
        return success(data, null, httpStatus);
    }

    public static ResponseEntity<Result> success(Object data, Object extraData) {
        return success(data, extraData, HttpStatus.OK);
    }

    public static ResponseEntity<Result> success(Object data, Object extraData, HttpStatus httpStatus) {
        Result result = new Result();
        result.setCode(httpStatus.value());
        result.setMessage(httpStatus.name());
        result.setSuccess(true);
        result.setData(data);
        result.setExtraData(extraData);
        return ResponseEntity.status(httpStatus).body(result);
    }

    public static ResponseEntity<Result> error(String message) {
        return error(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static ResponseEntity<Result> error(String message, HttpStatus httpStatus) {
        Result result = new Result();
        result.setCode(httpStatus.value());
        result.setMessage(message);
        result.setSuccess(false);
        return ResponseEntity.status(httpStatus).body(result);
    }

}
