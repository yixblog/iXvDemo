package com.sky.app.dao.pojo

import com.sky.app.utils.YixDateUtils
import com.sky.vdk.vlx.generator.annotations.VlxEndType
import com.sky.vdk.vlx.generator.annotations.VlxProperty


/**
 * Created by Yixian on 13-12-22.
 */
@VlxEndType(localName = 'punishRecord',displayName = '处罚记录',imageURI = 'VLImages/gifs/new/Prisoner.png')
class PunishRecord implements IDatabaseBean{
    @VlxProperty(localName = 'identityProperty',displayName = '标识')
    int id;
    Person person;
    @VlxProperty(displayName = '详情')
    String detail;
    @VlxProperty(displayName = '罪名')
    String caseName;
    @VlxProperty(displayName = '处罚')
    String punish;
    Date punishDate;

    int getPersonId(){
        person?.getId()
    }

    @VlxProperty(displayName = '处罚时间',isLabel = true)
    String getPunishDateString(){
        YixDateUtils.formatDate(punishDate,YixDateUtils.DATE_FORMAT);
    }

    Long getPunishDateMillis(){
        punishDate?.getTime();
    }

    void setPunishDate(Long time){
        if(time!=null && time>0){
            punishDate = new Date(time);
        }
    }

    @Override
    boolean pkAutoIncrement() {
        return true
    }
}
