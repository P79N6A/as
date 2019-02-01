package com.yaoguang.lib.annotation.aspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * =====================================
 * 作    者: 韦理英
 * 版    本：1.2.9
 * 创建日期：2018/01/31
 * 描    述：
 *  这是异常登录处理，主要判断服务关返回的107，就是有人登录这个帐号
 *  并退出提示
 * =====================================
 */

@Retention(RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
public @interface AbnormalLogin {
}
