package com.project.test.server.advice;

import cn.hutool.log.Log;
import com.project.test.server.entity.R;
import com.project.test.server.exception.BaseException;
import com.project.test.server.exception.BusinessException;
import com.project.test.server.exception.RequestDataException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

/**
 * @author olaf
 * @version V1.0
 * @ClassName ExceptionAdvice
 * @Description
 * @date 2022/3/21 10:29
 */
@RestControllerAdvice
public class ExceptionAdvice {

    Log log =Log.get(ExceptionAdvice.class);

    /**
     * 全局自定义runtime异常
     * 返回200的状态码
     *
     * @param e 异常
     * @return R
     */
    @ExceptionHandler(BaseException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public R<String> exception(BaseException e) {
        log.error("系统服务异常,异常信息:{},堆栈信息:{}", e.getMessage(), e);
        return R.fail("业务异常",e.getDefaultMessage());
    }
    /**
     * 全局自定义runtime异常
     * 返回200的状态码
     *
     * @param e 异常
     * @return R
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public R<String> exception(BusinessException e) {
        log.error("系统服务异常,异常信息:{},堆栈信息:{}", e.getMessage(), e);
        return R.fail("业务异常",e.getDefaultMessage());
    }

    /**
     * 全局自定义runtime异常
     * 返回200的状态码
     *
     * @param e 异常
     * @return R
     */
    @ExceptionHandler(RequestDataException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public R<String> exception(RequestDataException e) {
        log.error("系统服务异常,异常信息:{},堆栈信息:{}", e.getMessage(), e);
        return R.fail("数据异常",e.getDefaultMessage());
    }

    /**
     * 数据绑定异常
     * @param e
     * @return R
     */
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.OK)
    public R<String> exception(BindException e) {
        log.error("系统服务异常,异常信息:{},堆栈信息:{}", e.getMessage(), e);
        return R.fail("数据异常",e.getMessage());
    }

    /**
     * 参数校验异常
     * @param e
     * @return R
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.OK)
    public R<String> exception(MethodArgumentNotValidException e) {
        log.error("参数校验错误(MethodArgumentNotValidException),异常信息:{}", e.getMessage());
        return R.fail("参数校验错误(MethodArgumentNotValidException)", e.getBindingResult().getFieldError().getDefaultMessage());
    }

    /**
     * 参数校验异常 ,localhost:9001/pay/getById?payId= 或者 localhost:9001/pay/getById
     * payId 是 required=true的参数，而导致调用失败
     * @param e
     * @return R
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.OK)
    public R<String> exception(MissingServletRequestParameterException e) {
        log.error("参数校验错误(MissingServletRequestParameterException),异常信息:{}", e.getMessage());
        return R.fail("参数校验错误(MissingServletRequestParameterException)",e.getParameterName()+"不能为null");
    }


    /**
     * 参数校验异常
     * @param e
     * @return R
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.OK)
    public R<String> exception(ConstraintViolationException e) {
        log.error("参数校验错误(ConstraintViolationException),异常信息:{}", e.getMessage());
        return R.fail("参数校验错误(ConstraintViolationException)", e.getMessage());
    }

    /**
     * 全局异常
     *
     * @param e 异常
     * @return R
     *
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public R<String> exception(Exception e) {
        log.error("系统服务异常,异常信息:{},堆栈信息:{}", e.getMessage(), e);
        return R.fail("系统服务异常",e.getMessage());
    }

}
