package com.sky.app.dao;

import com.sky.app.dao.beans.TrafficOffence;

import java.util.List;

/**
 * traffic offence dao
 * Created by yixian on 13-12-10.
 */
public interface ITrafficOffenceDAO {
    public List<TrafficOffence> listDriverTrafficOffences(int driverId);

}
