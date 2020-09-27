package com.cjj.qlemusic.admin.aspect;

import cn.hutool.json.JSONUtil;
import com.cjj.qlemusic.common.util.ResponseResultUtil;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

/**
 * HibernateValidator错误结果处理切面
 */

@Aspect
@Component
@Order(2)
public class BindingResultAspect {
    @Pointcut("execution(public * com.cjj.qlemusic.admin.controller.*.*(..))")
    public void BindingResult() {
    }

    @Around("BindingResult()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof BindingResult) {
                BindingResult result = (BindingResult) arg;
                if (result.hasErrors()) {
                    FieldError fieldError = result.getFieldError();
                    if(fieldError!=null){
                        System.out.println(ResponseResultUtil.validateFailed(fieldError.getDefaultMessage()));
                        return JSONUtil.toJsonPrettyStr(ResponseResultUtil.validateFailed(fieldError.getDefaultMessage()));
                    }else{
                        System.out.println(ResponseResultUtil.validateFailed());
                        return JSONUtil.toJsonPrettyStr(ResponseResultUtil.validateFailed());
                    }
                }
            }
        }
        return joinPoint.proceed();
    }
}
