package com.sky.app.dao;

import com.sky.app.dao.beans.HotelLiveRecord;

import java.util.List;

/**
 * as name
 * Created by yixian on 13-12-10.
 */
public interface IHotelDAO {

    public List<HotelLiveRecord> listLiveRecordByPerson(int personId);

}
