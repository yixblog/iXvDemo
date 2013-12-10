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

    public String expandCarInfo(int personId);

    public String expandHotelRecord(int personId);

    public String expandWebCafeRecord(int personId);

    public String expandTrafficOffences(int personId);

    public String expandJustice(int personId);

    public String expandReform(int personId);

    public String expandEducation(int personId);

    public String expandVolunteer(int personId);

}
