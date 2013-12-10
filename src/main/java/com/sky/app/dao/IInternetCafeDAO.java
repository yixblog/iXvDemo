package com.sky.app.dao;

import com.sky.app.dao.beans.InternetCafeRecord;

import java.util.List;

/**
 *
 * Created by yixian on 13-12-10.
 */
public interface IInternetCafeDAO {
    public List<InternetCafeRecord> listCafeRecordByPerson(int personId);
}
