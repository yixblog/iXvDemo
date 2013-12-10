package com.sky.vdk.vlx.generator.exceptions;

/**
 * 使用的类没有使用VlxEndType标注
 * Created by yixian on 13-12-10.
 */
public class NotAnnotatedClassException extends VlxResolveException {
    public NotAnnotatedClassException(Class clazz) {
        super("类没有使用VlxEndType注解：" + clazz.getName());
    }
}
