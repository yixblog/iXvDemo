package com.sky.app.dao.pojo

import com.sky.app.utils.YixDateUtils


/**
 * Created by dyb on 13-12-21.
 */
class LiveRecord implements IDatabaseBean {
    int id;
    Hotel hotel;
    Person person;
    Date enterTime;
    Date leaveTime;
    String room;
    Date recordTime;

    int getHotelId() {
        return hotel?.getId();
    }

    int getPersonId() {
        person?.getId()
    }

    String getEnterTimeString() {
        YixDateUtils.formatDate(enterTime, YixDateUtils.DATE_TIME_FORMAT)
    }

    String getLeaveTimeString() {
        YixDateUtils.formatDate(leaveTime, YixDateUtils.DATE_TIME_FORMAT);
    }

    String getRecordTimeString() {
        YixDateUtils.formatDate(recordTime, YixDateUtils.DATE_TIME_FORMAT);
    }

    void setEnterTime(Long time) {
        if (time != null && time > 0) {
            enterTime = new Date(time);
        }
    }

    Long getEnterTimeMillis() {
        return enterTime?.getTime();
    }

    void setLeaveTime(Long time) {
        if (time != null && time > 0) {
            leaveTime = new Date(time);
        }
    }

    Long getLeaveTimeMillis() {
        return leaveTime?.getTime();
    }

    void setRecordTime(Long time) {
        if (time!=null && time > 0) {
            recordTime = new Date(time);
        }
    }

    Long getRecordTimeMillis() {
        return recordTime?.getTime();
    }

    @Override
    boolean pkAutoIncrement() {
        return true
    }
}
