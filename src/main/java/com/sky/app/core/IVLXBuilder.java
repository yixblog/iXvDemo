package com.sky.app.core;

/**
 * Created with IntelliJ IDEA.
 * User: yixian
 * Date: 13-11-8
 * Time: 下午2:29
 */
public interface IVLXBuilder {
    public String findPerson(int id);

    public String expandPersonParams(int id);

    public String expandHotelRecord(int personId);

    public String expandWebCafeRecord(int personId);

    public String expandJustice(int personId);

    public String expandFlightRecord(int personId);

    public String expandPunishRecord(int personId);
}
