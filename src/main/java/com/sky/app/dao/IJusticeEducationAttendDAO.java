package com.sky.app.dao;

import com.sky.app.dao.beans.JusticeEducationAttended;

import java.util.List;

/**
 * dao
 * Created by yixian on 13-12-10.
 */
public interface IJusticeEducationAttendDAO {
    public List<JusticeEducationAttended> findEducationAttendByPerson(int personId);
}
