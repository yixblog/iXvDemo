package com.sky.vdk.vlx.generator.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * EndType配置
 * Created by Yixian on 13-12-9.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface VlxEndType {
    public String localName();
    public String displayName();
    public String imageURI() default "VLImages/gifs/new/Annotation.png";
    public int imageSize() default 32;
}
