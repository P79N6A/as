package com.yaoguang.lib.annotation.aspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by wly on 2017/12/5 0005.
 */

@Retention(RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
public @interface BackKey {
}
