package com.sky.app.core;

import com.alibaba.fastjson.JSONObject;

/**
 * Created with IntelliJ IDEA.
 * User: yixian
 * Date: 13-11-8
 * Time: 下午2:29
 */
public interface IVLXBuilder {
    public String buildPersonVLX();

    public String appendPersonPropertyVLX(String personId);

    public String appendHotelInfo(String personId);

    public String appendCars(String personId);

    public String appendTrafficAccidents(String carId);

    public JSONObject getEndNodes();
}
