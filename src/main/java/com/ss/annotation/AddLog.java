package com.ss.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})  //在方法上使用
@Retention(RetentionPolicy.RUNTIME)  //运行时生效
public @interface AddLog {

    String value();

    String name () default "";
}
