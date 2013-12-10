package com.sky.vdk.vlx.generator.exceptions;

/**
 * VlxTypeName冲突
 * Created by yixian on 13-12-10.
 */
public class VlxTypeNameConflictException extends VlxResolveException {
    public VlxTypeNameConflictException(String vlxTypeName, Class clazz) {
        super("EndType名称冲突：" + vlxTypeName + ",相关类：" + clazz.getName());
    }
}
