package com.sky.app.dao.pojo

import com.sky.vdk.vlx.generator.annotations.VlxEndType
import com.sky.vdk.vlx.generator.annotations.VlxProperty

/**
 * Created by yxdave on 13-12-21.
 */
@VlxEndType(localName = 'hotel',displayName = '旅馆',imageURI = 'VLImages/gifs/new/Hotel.png')
class Hotel implements IDatabaseBean{
    @VlxProperty(localName = 'identityProperty',displayName = '标识')
    int id;
    @VlxProperty(displayName = '旅馆名称',isLabel = true)
    String name;
    @VlxProperty(displayName = '旅馆编号',isLabel = true)
    String code;
    @VlxProperty(displayName = '法人代表')
    String legalPerson;

    @Override
    boolean pkAutoIncrement() {
        return true
    }
}
