package com.github.developframework.mock;

import java.lang.annotation.*;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Mock {

    String value() default "";

    Class<?> with() default void.class;

    int size() default 0;
}
