package com.sky.vdk.vlx.generator.nodecfg

import com.sky.vdk.vlx.generator.annotations.VlxProperty
import com.sky.vdk.vlx.generator.exceptions.VlxResolveException
import groovy.transform.CompileStatic
import org.apache.commons.lang.reflect.FieldUtils
import org.apache.commons.lang.reflect.MethodUtils
import org.apache.log4j.Logger

import java.lang.reflect.Field
import java.lang.reflect.Method

/**
 * Vlx Property Node Config
 * Created by yixian on 13-12-10.
 */
@CompileStatic
class PropertyInfo {
    private Logger logger = Logger.getLogger(getClass());
    String localName;
    String fieldName;
    String displayName;
    boolean isLabel;
    String getterMethodName;

    static PropertyInfo newInstance(Method method) {
        if (!method.isAnnotationPresent(VlxProperty.class)) {
            throw new VlxResolveException("解析方法没有使用VlxProperty注解:" + method.getDeclaringClass() + "." + method.getName())
        }
        VlxProperty property = method.getAnnotation(VlxProperty.class);
        String localName = property.localName();
        String getterMethodName = method.getName();
        if (localName == "") {
            if (getterMethodName.startsWith("is")) {
                localName = getterMethodName.substring(2, 3).toLowerCase() + getterMethodName.substring(3);
            } else if (getterMethodName.startsWith("get")) {
                localName = getterMethodName.substring(3, 4).toLowerCase() + getterMethodName.substring(4);
            }
        }
        return new PropertyInfo(localName: localName, displayName: property.displayName(), isLabel: property.isLabel(), getterMethodName: getterMethodName);
    }

    static PropertyInfo newInstance(Field field) {
        if (!field.isAnnotationPresent(VlxProperty.class)) {
            throw new VlxResolveException("解析属性没有使用VlxProperty注解:" + field.getDeclaringClass() + "." + field.getName())
        }
        VlxProperty property = field.getAnnotation(VlxProperty.class);
        String localName = property.localName();
        if (localName == "") {
            localName = field.getName();
        }
        return new PropertyInfo(localName: localName, displayName: property.displayName(), isLabel: property.isLabel(), fieldName: field.getName())
    }

    String solveProperty(Object obj) {
        logger.debug("getter method name is not null?"+(getterMethodName!=null))
        if (getterMethodName != null) {
            return MethodUtils.invokeMethod(obj, getterMethodName)?.toString();
        } else {
            return FieldUtils.readField(obj, fieldName, true)?.toString();
        }
    }
}
