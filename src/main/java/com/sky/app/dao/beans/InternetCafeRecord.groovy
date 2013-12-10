package com.sky.app.dao.beans

/**
 * 网吧上午记录
 * Created by Yixian on 13-12-9.
 */
class InternetCafeRecord {
    int id;
    Person person;
    InternetCafe cafe;
    String startTime;

    boolean equals(Object obj) {
        if (obj instanceof InternetCafeRecord) {
            return (InternetCafeRecord) obj.id == this.id;
        }
        return false;
    }
}
