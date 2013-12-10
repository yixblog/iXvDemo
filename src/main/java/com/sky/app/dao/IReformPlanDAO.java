package com.sky.app.dao;

import com.sky.app.dao.beans.JusticeReformPlan;

import java.util.List;

/**
 * Created by yixian on 13-12-10.
 */
public interface IReformPlanDAO {
    public List<JusticeReformPlan> listPlansOfPerson(int personId);
}
