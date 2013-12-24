package com.sky.app.dao;


import com.sky.app.dao.pojo.IDatabaseBean;

import java.util.List;
import java.util.Map;

/**
 * basic crud interface
 * Created by Yixian on 13-12-22.
 */
public interface IBasicCRUDDAO <T extends IDatabaseBean> {
    public String getTableName();
    public T findOneObject(T example, String... queryKeys);

    public void saveObject(T object);

    public List<T> findMany(T example, String... queryKey);

    public List<T> listAll();

    public void updateObject(T object);

    public void deleteObject(T object);

    public Map<String,String> getReferenceMapping();

    public List<String> getDatabaseColumns();
}
