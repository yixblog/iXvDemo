package com.sky.app.dao.beans
/**
 * 酒店
 * Created by Yixian on 13-12-9.
 */
class Hotel {
    int id;
    String name;
    String address;

    boolean equals(Object obj) {
        if (obj instanceof Hotel) {
            return (Hotel) obj.id == this.id;
        }
        return false;
    }
}
