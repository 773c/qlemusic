package com.cjj.qlemusic.common.exception;

import com.cjj.qlemusic.common.util.ResponseResultUtil;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolationException;

/**
 * 全局异常处理类
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
    /**
     * 自定义捕捉异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = ApiException.class)
    public ResponseResultUtil handle(ApiException e){
        return ResponseResultUtil.failed(e.getMessage());
    }

    /**
     * 未授权异常
     * @return
     */
    @ExceptionHandler(value = AuthorizationException.class)
    public ResponseResultUtil noAuthorization(){
        return ResponseResultUtil.noAuthorization();
    }

    /**
     * 变量参数校验
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseResultUtil paramValidated(ConstraintViolationException exception){
        System.out.println(exception.getMessage());
        return ResponseResultUtil.validateFailed(exception.getMessage().substring(exception.getMessage().indexOf(':')+1));
    }

}
