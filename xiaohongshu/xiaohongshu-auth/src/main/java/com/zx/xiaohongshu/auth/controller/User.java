package com.zx.xiaohongshu.auth.controller;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
//用这个注解修饰的类会把里面的变量都生成全参数的构造器
@AllArgsConstructor
//没有没有参数的构造器的参数注解
@NoArgsConstructor
//@Builder 会生成一个全参构造方法，因此就没有了无参构造方法
@Builder
public class User {
    /**
     * 昵称
     */
    @NotBlank(message = "昵称不能为空")
    private String nickName;

    /*
     *创建时间
     */
    private LocalDateTime createTime;
}
