package com.sky.app.dao.beans
/**
 * Created by Yixian on 13-12-9.
 */
class JusticeEducation {
    int id;
    String date;
    String location;

    boolean equals(Object obj) {
        if (obj instanceof JusticeEducation) {
            return (JusticeEducation) obj.id == this.id;
        }
        return false;
    }
}
