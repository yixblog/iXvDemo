package com.sky.app.dao.beans

import com.sky.vdk.vlx.generator.annotations.VlxEndType
import com.sky.vdk.vlx.generator.annotations.VlxProperty

/**
 * 酒店入住记录
 * Created by Yixian on 13-12-9.
 */
@VlxEndType(localName = "liveRecord", displayName = "住宿记录", imageURI = "VLImages/gifs/new/Hotel.png")
class HotelLiveRecord {
    @VlxProperty(localName = "identityProperty", displayName = "标识")
    int id;
    Person person;
    Hotel hotel;
    @VlxProperty(displayName = "入住日期")
    String startDate;
    String endDate;

    @VlxProperty(displayName = "姓名")
    String getPersonName() {
        return person?.getName();
    }

    @VlxProperty(displayName = "酒店名称", isLabel = true)
    String getHotelName() {
        return hotel?.getName();
    }

    boolean equals(Object obj) {
        if (obj instanceof HotelLiveRecord) {
            return (HotelLiveRecord) obj.id == this.id;
        }
        return false;
    }
}
