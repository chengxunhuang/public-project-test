package com.project.test.server.exception;

import com.project.test.server.constant.CoreConstants;

/**
 * 业务异常
 *
 * @author rean
 * @date 2021/12/13 13:52
 */

public final class ServiceException extends RuntimeException {
    private static final long serialVersionUID = 5095439538316185017L;

    /**
     * 错误码
     */
    private String code = CoreConstants.FAIL.toString();

    /**
     * 错误提示
     */
    private String message;

    /**
     * 错误明细，内部调试错误
     */
    private String detailMessage;

    /**
     * 空构造方法，避免反序列化问题
     */
    public ServiceException() {
    }

    public ServiceException(String message) {
        this.message = message;
    }

    public ServiceException(String message, String code) {
        this.message = message;
        this.code = code;
    }
    public ServiceException(Throwable throwable) {
        super(throwable.getMessage(), throwable);
//        this.detailMessage=throwable.getStackTrace().
    }
    public ServiceException(String msg,Throwable throwable) {
        super(msg, throwable);
//        this.detailMessage=throwable.getStackTrace().
    }
    public String getDetailMessage() {
        return detailMessage;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }

    public ServiceException setMessage(String message) {
        this.message = message;
        return this;
    }

    public ServiceException setDetailMessage(String detailMessage) {
        this.detailMessage = detailMessage;
        return this;
    }
}