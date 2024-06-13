package com.zx.xiaohongshu.auth.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@Getter
@AllArgsConstructor
public enum LoginTypeEnum {
    //验证码
    VERIFICATION_CODE(1),
    //密码
    PASSWORD(2);

    private final Integer value;

    public static LoginTypeEnum valueOf(Integer code){
        //LoginTypeEnum.value():VERIFICATION OR PASSWORD
        for(LoginTypeEnum loginTypeEnum:LoginTypeEnum.values()){
            if (Objects.equals(code,loginTypeEnum.getValue())){
                //loginTypeEnum:VERIFICATION OR PASSWORD
                return loginTypeEnum;
            }
        }
        return  null;
    }

}
