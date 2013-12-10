package com.sky.app.dao;

import com.sky.app.dao.beans.JusticeVolunteerWork;

import java.util.List;

/**
 * Created by yixian on 13-12-10.
 */
public interface IVolunteerWorkDAO {
    public List<JusticeVolunteerWork> listPersonVolunteerWorks(int personId);
}
