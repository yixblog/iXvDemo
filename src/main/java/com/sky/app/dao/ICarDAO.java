package com.sky.app.dao;

import com.sky.app.dao.beans.Car;

import java.util.List;

/**
 * car dao
 * Created by yixian on 13-12-10.
 */
public interface ICarDAO {
    public List<Car> listDriverCars(int driverId);
}
