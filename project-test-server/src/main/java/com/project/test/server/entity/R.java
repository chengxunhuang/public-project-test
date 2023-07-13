package com.project.test.server.entity;

import com.project.test.server.constant.CoreConstants;

import java.io.Serializable;

/**
 * 响应信息主体
 *
 * @author rean
 * @date 2021/12/13 13:52
 */

public class R<T> implements Serializable {

    private static final long serialVersionUID = 5095439538716185017L;


    /**
     * 失败500 Or 成功200
     */
    private int code;
    /**
     * 失败信息
     */
    private String msg;

    /**
     * 数据
     */
    private T data;

    private Boolean decorate;

    public R() {
        this.decorate = Boolean.TRUE;
    }

    public static <T> R<T> ok() {
        return restResult(null, CoreConstants.SUCCESS, null);
    }

    public static <T> R<T> ok(T data) {
        return restResult(data, CoreConstants.SUCCESS, null);
    }

    public static <T> R<T> ok(T data, String msg) {
        return restResult(data, CoreConstants.SUCCESS, msg);
    }

    public static <T> R<T> fail() {
        return restResult(null, CoreConstants.FAIL, null);
    }

    public static <T> R<T> fail(String msg) {
        return restResult(null, CoreConstants.FAIL, msg);
    }

    public static <T> R<T> fail(T data) {
        return restResult(data, CoreConstants.FAIL, null);
    }

    public static <T> R<T> fail(T data, String msg) {
        return restResult(data, CoreConstants.FAIL, msg);
    }

    public static <T> R<T> fail(int code, String msg) {
        return restResult(null, code, msg);
    }

    private static <T> R<T> restResult(T data, int code, String msg) {
        R<T> apiResult = new R<>();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        return apiResult;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Boolean getDecorate() {
        return decorate;
    }

    public void setDecorate(Boolean decorate) {
        this.decorate = decorate;
    }

    public boolean isDecorate() {
        return this.decorate;
    }

}
