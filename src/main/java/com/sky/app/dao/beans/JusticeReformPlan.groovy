package com.sky.app.dao.beans

import com.sky.vdk.vlx.generator.annotations.VlxEndType
import com.sky.vdk.vlx.generator.annotations.VlxProperty

/**
 * Created by Yixian on 13-12-9.
 */
@VlxEndType(localName = "justiceReformPlan", displayName = "矫正方案")
class JusticeReformPlan {
    @VlxProperty(localName = "identityProperty", displayName = "标识")
    int id;
    Person person;
    @VlxProperty(displayName = "添加时间", isLabel = true)
    String addDate;
    @VlxProperty(displayName = "矫正分析")
    String analysis;
    @VlxProperty(displayName = "矫正方案")
    String method;

    @VlxProperty(displayName = "姓名", isLabel = true)
    String getPersonName() {
        return person?.getName();
    }

    boolean equals(Object obj) {
        if (obj instanceof JusticeReformPlan) {
            return (JusticeReformPlan) obj.id == this.id;
        }
        return false;
    }
}
