package com.sky.app.dao.beans

import com.sky.vdk.vlx.generator.annotations.VlxEndType
import com.sky.vdk.vlx.generator.annotations.VlxProperty

/**
 * Created by Yixian on 13-12-9.
 */
@VlxEndType(localName = "justiceEduAttend", displayName = "集中教育")
class JusticeEducationAttended {
    Person person;
    JusticeEducation education;

    @VlxProperty(displayName = "标识")
    String getIdentityProperty() {
        return "${person.getId()}_${education.getId()}";
    }

    @VlxProperty(displayName = "姓名", isLabel = true)
    String getPersonName() {
        return person.getName();
    }

    @VlxProperty(displayName = "时间", isLabel = true)
    String getAttendDate() {
        return education.getDate();
    }

    boolean equals(Object obj) {
        if (obj instanceof JusticeEducationAttended) {
            return (JusticeEducationAttended) obj.id == this.id;
        }
        return false;
    }
}
