package com.zx.xiaohongshu.auth.model.vo.verificationcode;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SendVerificationCodeReqVO {
//验证字段（Field）或方法参数（Method Parameter）是否为空
    @NotBlank(message = "手机号不能空")
    private String phone;
}
