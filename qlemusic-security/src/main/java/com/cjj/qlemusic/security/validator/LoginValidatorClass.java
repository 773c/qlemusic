package com.cjj.qlemusic.security.validator;

import cn.hutool.core.util.StrUtil;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 解决同一个API传入的不同参数
 */
public class LoginValidatorClass implements ConstraintValidator<LoginValidator,String> {

    @Override
    public void initialize(LoginValidator loginValidator) {
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        boolean flag = false;
        if (StrUtil.isEmpty(s))
            flag = false;
        else
            flag = true;
        if(s == null)
            flag = true;
        return flag;
    }
}
