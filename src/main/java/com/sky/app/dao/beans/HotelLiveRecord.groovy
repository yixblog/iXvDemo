package com.sky.app.dao.beans

import com.sky.vdk.vlx.generator.annotations.VlxEndType
import com.sky.vdk.vlx.generator.annotations.VlxProperty

/**
 * 酒店入住记录
 * Created by Yixian on 13-12-9.
 */
@VlxEndType(localName = "liveRecord",displayName = "住宿记录",imageURI = "VLImages/gifs/new/Flat.png")
class HotelLiveRecord {
    @VlxProperty(localName = "identityProperty",displayName = "标识")
    int id;
    Person person;
    Hotel hotel;
    String startDate;
    String endDate;
}
