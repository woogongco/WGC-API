package com.wgc.wgcapi.Common.Annotations;
/*
Created on 2023/03/08 12:13 AM In Intelli J IDEA 
by jeon-wangi
*/

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequireToken {
    String value() default "";
}
