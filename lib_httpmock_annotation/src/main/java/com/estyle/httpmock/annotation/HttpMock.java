package com.estyle.httpmock.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = {ElementType.METHOD})
@Retention(value = RetentionPolicy.SOURCE)
public @interface HttpMock {

    String fileName();

    boolean enable() default true;
}
