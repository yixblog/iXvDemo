package com.sky.app.dao.beans

import com.sky.vdk.vlx.generator.annotations.VlxEndType
import com.sky.vdk.vlx.generator.annotations.VlxProperty

/**
 * 车辆信息
 * Created by Yixian on 13-12-9.
 */
@VlxEndType(localName = "car", displayName = "车辆信息", imageURI = "VLImages/gifs/new/car.png")
class Car{
    @VlxProperty(localName = "identityProperty", displayName = "标识")
    int id;
    @VlxProperty(displayName = "车牌",isLabel = true)
    String plate;
    @VlxProperty(displayName = "品牌型号")
    String brand;
    String color;
    String engineCode;
    Person driver;

    @VlxProperty(displayName = "车主姓名")
    String getDriverName() {
        return driver?.getName();
    }

    boolean equals(Object obj){
        if(obj instanceof Car){
            return (Car)obj.id==this.id;
        }
        return false;
    }

}
