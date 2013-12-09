package com.sky.app.dao.beans

import com.sky.app.dao.enums.PersonSex

/**
 * Created by Yixian on 13-12-9.
 */
class Person {
    int id;
    String name;
    String idCard;
    String birth;
    PersonSex sex;

    void setSexId(int sexId) {
        sex = PersonSex.values()[sexId];
    }

    int getSexId(){
        return sex.intValue();
    }

    String getSexString(){
        return sex.stringValue();
    }
}
