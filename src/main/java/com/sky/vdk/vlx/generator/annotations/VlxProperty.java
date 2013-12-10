package com.sky.vdk.vlx.generator.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.METHOD,ElementType.TYPE})
public @interface VlxProperty {
    public String localName() default "";

    public String displayName() default "";

    public boolean isLabel() default false;
}
