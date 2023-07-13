package com.project.test.server.exception;

import com.project.test.server.constant.CoreConstants;
import com.project.test.server.util.StringUtil;

/**
 * 业务异常
 *
 * @author rean
 * @date 2021/12/13 13:52
 */

public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = 1095433538316185019L;

    /**
     * 所属模块
     */
    private String module;

    /**
     * 错误码
     */
    private String code = CoreConstants.FAIL.toString();

    /**
     * 错误码对应的参数
     */
    private Object[] args;

    /**
     * 错误消息
     */
    private String defaultMessage;

    /**
     *
     * @param: throwable 异常类型
     * @param: messageTemplate 异常信息
     * @param: params 参数
     */
    public BusinessException(Throwable throwable, String messageTemplate, Object... params) {
        super(StringUtil.format(messageTemplate, params), throwable);
    }


    public BusinessException(String module, String code, Object[] args, String defaultMessage) {
        this.module = module;
        this.code = code;
        this.args = args;
        this.defaultMessage = defaultMessage;
    }

    public BusinessException(String module, String code, Object[] args) {
        this(module, code, args, null);
    }

    public BusinessException(String module, String defaultMessage) {
        this(module, null, null, defaultMessage);
    }

    public BusinessException(String code, Object[] args) {
        this(null, code, args, null);
    }

    public BusinessException(String msg, Throwable throwable) {
        super(msg, throwable);
    }
    public BusinessException(Throwable throwable) {
        super(throwable.getMessage(), throwable);
    }

    public BusinessException(String defaultMessage) {
        this(null, null, null, defaultMessage);
    }

    public String getModule() {
        return module;
    }

    public String getCode() {
        return code;
    }

    public Object[] getArgs() {
        return args;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }
}
