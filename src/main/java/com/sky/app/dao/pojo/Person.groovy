package com.sky.app.dao.pojo

import com.sky.app.enums.Sex
import com.sky.app.utils.YixDateUtils
import com.sky.vdk.vlx.generator.annotations.VlxEndType
import com.sky.vdk.vlx.generator.annotations.VlxProperty


/**
 * Created by dyb on 13-12-21.
 */
@VlxEndType(localName = 'person',displayName = '重点人员',imageURI = 'VLImages/gifs/new/person.png')
class Person implements IDatabaseBean {
    @VlxProperty(localName = "identityProperty",displayName = '标识')
    int id;
    @VlxProperty(displayName = '姓名',isLabel = true)
    String name;
    @VlxProperty(displayName = '身份证号',isLabel = true)
    String cardNumber;
    @VlxProperty(displayName = '矫正单位')
    String focusDepart;
    Sex sex;
    String echnic;//民族
    Date birth;
    String zipcode;
    String address;
    @VlxProperty(displayName = '联系方式')
    String phone;

    public void setSexValue(int value) {
        this.sex = Sex.values()[value];
    }

    public int getSexValue() {
        return sex?.getValue();
    }

    @VlxProperty(localName = 'sex',displayName = '性别')
    public String getSexString() {
        return sex?.getSexString();
    }

    public void setBirth(Long birthday) {
        if (birthday != null && birthday > 0) {
            birth = new Date(birthday);
        }
    }

    public Long getBirthMillis() {
        return birth?.getTime();
    }

    @VlxProperty(localName = 'birthday',displayName = '出生日期')
    public String getBirthDayString() {
        YixDateUtils.formatDate(birth, YixDateUtils.DATE_FORMAT);
    }

    @Override
    boolean pkAutoIncrement() {
        return true
    }

    public void setSexByString(String sex) {
        if (sex == null || sex == "") {
            setSex(Sex.SECRET);
        } else {
            if (sex.contains("男")) {
                setSex(Sex.MALE)
            } else {
                setSex(Sex.FEMALE)
            }
        }
    }
}
