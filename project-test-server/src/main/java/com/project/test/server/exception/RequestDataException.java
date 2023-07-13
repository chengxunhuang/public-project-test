package com.project.test.server.exception;

import com.project.test.server.constant.CoreConstants;

/**
 * 数据异常
 *
 * @author rean
 * @date 2021/12/13 13:52
 */

public class RequestDataException extends BaseException {

    private static final long serialVersionUID = 5095433598316185017L;


    /**
     * 异常消息
     */
    public static final String MESSAGE = "请求数据异常";

    public RequestDataException() {
        super(MESSAGE);
    }

    public RequestDataException(String message) {
        super(message);
    }

    /**
     * 错误码
     *
     * @return
     */
    @Override
    public String getCode() {
        return CoreConstants.FAIL.toString();
    }
}
