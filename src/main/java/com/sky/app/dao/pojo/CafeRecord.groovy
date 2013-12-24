package com.sky.app.dao.pojo

import com.sky.app.utils.YixDateUtils


/**
 * Created by Yixian on 13-12-22.
 */
class CafeRecord implements IDatabaseBean {
    int id;
    NetCafe cafe;
    Person person;
    Date onlineTime;
    Date offlineTime;
    String ip;
    String mac;

    int getPersonId() {
        return person?.getId()
    }

    String getCafeId() {
        cafe?.getId();
    }

    String getOnlineTimeString() {
        return YixDateUtils.formatDate(onlineTime, YixDateUtils.DATE_TIME_FORMAT);
    }

    String getOfflineTimeString() {
        return YixDateUtils.formatDate(offlineTime, YixDateUtils.DATE_TIME_FORMAT);
    }

    Long getOnlineTimeMillis() {
        onlineTime?.getTime();
    }

    Long getOfflineTimeMillis() {
        offlineTime?.getTime();
    }

    void setOnlineTime(Long time) {
        if (time != null && time > 0) {
            onlineTime = new Date(time);
        }
    }

    void setOfflineTime(Long time) {
        if (time != null && time > 0) {
            offlineTime = new Date(time);
        }
    }

    @Override
    boolean pkAutoIncrement() {
        return true
    }
}
