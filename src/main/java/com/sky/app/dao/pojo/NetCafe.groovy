package com.sky.app.dao.pojo

import com.sky.vdk.vlx.generator.annotations.VlxEndType
import com.sky.vdk.vlx.generator.annotations.VlxProperty

/**
 * Created by dyb on 13-12-21.
 */
@VlxEndType(localName = 'netcafe',displayName = '网吧',imageURI = 'VLImages/gifs/new/pc.png')
class NetCafe implements IDatabaseBean{
    @VlxProperty(localName = 'identityProperty',displayName = '标识')
    String id;
    @VlxProperty(displayName = '网吧名称',isLabel = true)
    String name;
    @VlxProperty(displayName = '地址')
    String address;
    @VlxProperty(displayName = '联系方式')
    String phone;
    @VlxProperty(displayName = 'IP地址')
    String ip
    @VlxProperty(displayName = '法人')
    String legalPerson;

    @Override
    boolean pkAutoIncrement() {
        return false
    }
}
