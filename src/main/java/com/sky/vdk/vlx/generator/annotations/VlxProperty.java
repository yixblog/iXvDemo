package com.sky.vdk.vlx.generator.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface VlxProperty {
    public String localName() default "";

    public String displayName() default "";

    public boolean isLabel() default false;
}
