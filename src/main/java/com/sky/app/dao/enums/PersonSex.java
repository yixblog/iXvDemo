package com.sky.app.dao.enums;

/**
 * 性别
 * Created by Yixian on 13-12-9.
 */
public enum PersonSex {
    none("未知", 0), male("男", 1), female("女", 2);

    private String textValue;
    private int sexId;

    private PersonSex(String textValue, int intValue) {
        this.textValue = textValue;
        this.sexId = intValue;
    }

    public String stringValue() {
        return textValue;
    }

    public int intValue() {
        return sexId;
    }
}
