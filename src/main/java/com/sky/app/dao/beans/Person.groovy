package com.sky.app.dao.beans

import com.sky.app.dao.enums.PersonSex
import com.sky.vdk.vlx.generator.annotations.VlxEndType
import com.sky.vdk.vlx.generator.annotations.VlxProperty

/**
 * Created by Yixian on 13-12-9.
 */
@VlxEndType(localName = "person", displayName = "重点人员", imageURI = "VLImages/gifs/new/Prisoner.png")
class Person {
    @VlxProperty(localName = "identityProperty", displayName = "标识")
    int id;
    @VlxProperty(displayName = "姓名", isLabel = true)
    String name;
    @VlxProperty(displayName = "身份证号")
    String idCard;
    @VlxProperty(displayName = "出生日期")
    String birth;
    PersonSex sex;

    void setSexId(int sexId) {
        sex = PersonSex.values()[sexId];
    }

    int getSexId() {
        return sex.intValue();
    }

    @VlxProperty(localName = "sex", displayName = "性别")
    String getSexString() {
        return sex.stringValue();
    }

    boolean equals(Object obj){
        if(obj instanceof Person){
            return (Person)obj.id==this.id;
        }
        return false;
    }
}
