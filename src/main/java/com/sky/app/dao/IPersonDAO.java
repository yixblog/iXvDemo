package com.sky.app.dao;

import com.sky.app.dao.beans.Person;

/**
 * 人员信息DAO
 * Created by yixian on 13-12-10.
 */
public interface IPersonDAO {

    public Person findPersonById(int id);
}
