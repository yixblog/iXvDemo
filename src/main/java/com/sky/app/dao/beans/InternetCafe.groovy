package com.sky.app.dao.beans

import com.sky.vdk.vlx.generator.annotations.VlxEndType
import com.sky.vdk.vlx.generator.annotations.VlxProperty

/**
 * 网吧
 * Created by Yixian on 13-12-9.
 */
@VlxEndType(localName = "internetCafe", displayName = "网吧", imageURI = "VLImages/gifs/new/Computer Monitor.png")
class InternetCafe {
    @VlxProperty(localName = "identityProperty", displayName = "标识")
    int id;
    @VlxProperty(displayName = "网吧名称", isLabel = true)
    String name;
    @VlxProperty(displayName = "地址")
    String address;

    boolean equals(Object obj) {
        if (obj instanceof InternetCafe) {
            return (InternetCafe) obj.id == this.id;
        }
        return false;
    }
}
