package com.sky.app.enums;

/**
 * person sex
 * Created by dyb on 13-12-21.
 */
public enum Sex {
    SECRET(0),MALE(1),FEMALE(2);
    private int value;
    Sex(int value){
        this.value = value;
    }

    public int getValue(){
        return value;
    }

    public String getSexString(){
        switch (value){
            case 0:return "保密";
            case 1:return "男";
            case 2:return "女";
            default:return "";
        }
    }
}
