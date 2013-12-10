package com.sky.app.dao.beans

import com.sky.vdk.vlx.generator.annotations.VlxEndType
import com.sky.vdk.vlx.generator.annotations.VlxProperty

/**
 * Created by Yixian on 13-12-9.
 */
@VlxEndType(localName = "volunteerWork", displayName = "公益劳动")
class JusticeVolunteerWork {
    @VlxProperty(localName = "identityProperty", displayName = "标识")
    int id;
    Person person;
    @VlxProperty(displayName = "日期", isLabel = true)
    String date;

    @VlxProperty(displayName = "地点")
    String location;

    @VlxProperty(displayName = "内容")
    String detail;

    @VlxProperty(displayName = "姓名", isLabel = true)
    String getPersonName() {
        return person?.getName();
    }

    boolean equals(Object obj) {
        if (obj instanceof JusticeVolunteerWork) {
            return (JusticeVolunteerWork) obj.id == this.id;
        }
        return false;
    }
}
