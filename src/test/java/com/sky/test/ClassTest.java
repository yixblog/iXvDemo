package com.sky.test;

import com.sky.app.dao.beans.Car;
import com.sky.app.dao.beans.Person;
import com.sky.vdk.vlx.generator.annotations.VlxEndType;
import com.sky.vdk.vlx.generator.annotations.VlxProperty;
import org.junit.Test;

import java.lang.reflect.Field;

/**
 * test of class annotations
 * Created by Yixian on 13-12-9.
 */
public class ClassTest {
    @Test
    public void testClassName(){
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
    public void testAnnotations() throws NoSuchFieldException {
        Object car = initCar();
        Class clazz = car.getClass();
        System.out.println(clazz);
        VlxEndType endTypeConfig = (VlxEndType) clazz.getAnnotation(VlxEndType.class);
        System.out.println(endTypeConfig.displayName());
        Field[] fields = clazz.getDeclaredFields();
        for (Field f:fields){
            System.out.println("----------"+f.getName());
            System.out.println(f.isAnnotationPresent(VlxProperty.class));
        }
    }
}
