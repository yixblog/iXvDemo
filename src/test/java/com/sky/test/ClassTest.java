package com.sky.test;

import com.sky.app.dao.beans.Car;
import com.sky.app.dao.beans.Person;
import com.sky.vdk.vlx.generator.annotations.VlxEndType;
import com.sky.vdk.vlx.generator.annotations.VlxProperty;
import org.apache.commons.lang.reflect.MethodUtils;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * test of class annotations
 * Created by Yixian on 13-12-9.
 */
public class ClassTest {
    @Test
    public void testClassName() {
        Object obj = initCar();
        System.out.println(obj.getClass().getName());
    }

    private Object initCar() {
        Car car = new Car();
        car.setBrand("brand");
        car.setColor("red");
        Person person = new Person();
        person.setName("xxx");
        person.setBirth("xxx");
        person.setSexId(1);
        person.setIdCard("sss");
        car.setDriver(person);
        return car;
    }

    @Test
    public void testAnnotations() throws NoSuchFieldException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Object car = initCar();
        Class clazz = car.getClass();
        System.out.println(clazz);
        VlxEndType endTypeConfig = (VlxEndType) clazz.getAnnotation(VlxEndType.class);
        System.out.println(endTypeConfig.displayName());
        Field[] fields = clazz.getDeclaredFields();
        System.out.println("fields:");
        for (Field f : fields) {
            if (f.isAnnotationPresent(VlxProperty.class)) {
                System.out.println("----------" + f.getName());
                VlxProperty property = f.getAnnotation(VlxProperty.class);
                System.out.println(property.displayName());
            }
        }
        Method[] methods = clazz.getDeclaredMethods();
        System.out.println("methods:");
        for (Method m : methods) {
            if (m.isAnnotationPresent(VlxProperty.class)) {
                System.out.println("---------" + m.getName());
                VlxProperty property = m.getAnnotation(VlxProperty.class);
                System.out.println(property.displayName());
            }
        }
        System.out.println("driver name:"+ MethodUtils.invokeMethod(car,"getDriverName",null));
    }

    @Test
    public void booleanTypeTest(){
        System.out.println(Boolean.class.equals(Boolean.class));
    }
}
