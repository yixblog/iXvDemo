package com.sky.vlxTest;

import com.sky.app.dao.beans.Car;
import com.sky.app.dao.beans.Person;
import com.sky.vdk.vlx.generator.VLXGenerator;
import org.junit.Test;

/**
 * test VLXGenerator
 * Created by yixian on 13-12-10.
 */
public class VLXGeneratroTester {
    @Test
    public void testAnnotationResolver() {
        VLXGenerator generator = new VLXGenerator();
        Object car = initCar();
        generator.addEndByPojo(car);
        System.out.println(generator.generateVLX());
    }

    private Object initCar() {
        Car car = new Car();
        car.setId(1123);
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
}
