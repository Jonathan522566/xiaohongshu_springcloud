package com.zx.framework.common.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

//phoneNumber是自定义注解类型,String是被校验的属性类型
public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber,String> {


    @Override
    public void initialize(PhoneNumber constraintAnnotation) {
       //这里进行一些初始化
    }

    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext constraintValidatorContext) {
        // \\d 表示匹配一个数字字符，{11} 表示匹配前面的模式正好 11 次
        return phoneNumber!=null && phoneNumber.matches("\\d{11}");
    }
}
