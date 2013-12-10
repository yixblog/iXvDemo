package com.sky.app.dao.beans

import com.sky.vdk.vlx.generator.annotations.VlxEndType
import com.sky.vdk.vlx.generator.annotations.VlxProperty

/**
 * Created by yixian on 13-12-10.
 */
@VlxEndType(localName = "trafficOffence", displayName = "交通违章", imageURI = "VLImages/gifs/new/Car (Damaged).png")
class TrafficOffence {
    @VlxProperty(localName = "identityProperty", displayName = "标识")
    int id;
    @VlxProperty(displayName = "地点", isLabel = true)
    String location;
    @VlxProperty(displayName = "违章信息")
    String information;
    @VlxProperty(displayName = "时间")
    String time;
    Car car;

    @VlxProperty(displayName = "违章人员", isLabel = true)
    String getDriverName() {
        return car?.getDriver()?.getName();
    }

    boolean equals(Object obj) {
        if (obj instanceof TrafficOffence) {
            return (TrafficOffence) obj.id == this.id;
        }
        return false;
    }
}
