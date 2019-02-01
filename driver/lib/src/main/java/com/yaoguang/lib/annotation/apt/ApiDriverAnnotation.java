package com.yaoguang.lib.annotation.apt;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * api接口
 * Created by wly on 2017/12/7 0007.
 */

@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface ApiDriverAnnotation {
    String value() default "no set value";
}
