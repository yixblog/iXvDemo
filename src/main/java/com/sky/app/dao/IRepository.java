package com.sky.app.dao;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: yixian
 * Date: 13-11-29
 * Time: 下午3:17
 */
public interface IRepository {
    public JSONObject getOnePerson();

    public List<JSONObject> findHotelRecords(String personId);

    public List<JSONObject> findPersonCars(String personId);

    public JSONObject findCar(String carId);

    public List<JSONObject> findAccidentByCar(String carId);
}
