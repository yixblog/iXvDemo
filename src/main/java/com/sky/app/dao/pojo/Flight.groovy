package com.sky.app.dao.pojo

import com.sky.app.utils.YixDateUtils
import com.sky.vdk.vlx.generator.annotations.VlxEndType
import com.sky.vdk.vlx.generator.annotations.VlxProperty

/**
 * Created by Yixian on 13-12-22.
 */
@VlxEndType(localName = 'flight',displayName = '航班信息',imageURI = 'VLImages/gifs/new/airplane.png')
class Flight implements IDatabaseBean {
    @VlxProperty(localName = 'identityProperty',displayName = '标识')
    int id;
    @VlxProperty(localName = 'flightid',displayName = '航班号',isLabel = true)
    String flightId;
    Date offday;
    @VlxProperty(displayName = '起飞时间')
    String depTime;
    @VlxProperty(displayName = '到达时间')
    String arriveTime;
    @VlxProperty(displayName = '起飞站点')
    String stat;
    @VlxProperty(displayName = '到达站点')
    String dest;

    @VlxProperty(displayName = '起飞日期',isLabel = true)
    String getOffdayString() {
        return YixDateUtils.formatDate(offday, YixDateUtils.DATE_FORMAT)
    }

    Long getOffdayMillis(){
        return offday?.getTime()
    }

    void setOffday(Long time){
        if(time!=null && time>0){
            offday = new Date(time);
        }
    }

    @Override
    boolean pkAutoIncrement() {
        return true
    }
}
