package com.sky.app.mysql

import com.sky.app.dao.ILiveRecordDAO
import com.sky.app.dao.pojo.Hotel
import com.sky.app.dao.pojo.LiveRecord
import com.sky.app.dao.pojo.Person
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository

import java.sql.ResultSet
import java.sql.SQLException

/**
 * Created by dyb on 13-12-23.
 */
@Repository('liveRecordDAO')
class LiveRecordDAO extends BasicCRUDDAO<LiveRecord> implements ILiveRecordDAO{
    @Override
    protected String getBasicQuerySql() {
        return "select p.*,r.*,h.* from liverecord r left join person p on p.id=r.person left join hotel h on h.id=r.hotel"
    }

    @Override
    protected RowMapper<LiveRecord> getFindBasicRowMapper() {
        return new RowMapper<LiveRecord>() {
            @Override
            LiveRecord mapRow(ResultSet rs, int rowNum) throws SQLException {
                Person person = new Person(id: rs.getInt('p.id'), name: rs.getString('p.name'), cardNumber: rs.getString('cardnumber'),
                        focusDepart: rs.getString('focusdepart'), echnic: rs.getString('echnic'), zipcode: rs.getString('zipcode'),
                        address: rs.getString('p.address'), phone: rs.getString('p.phone'));
                person.setSexValue(rs.getInt('sex'));
                person.setBirth(rs.getLong('birth'));

                Hotel hotel = new Hotel(id: rs.getInt('h.id'), name: rs.getString('h.name'), code: rs.getString('code'), legalPerson: rs.getString('legal'));

                LiveRecord record = new LiveRecord(id: rs.getInt('r.id'), hotel: hotel, person: person, room: rs.getString('room'));
                record.setEnterTime(rs.getLong('entertime'));
                record.setLeaveTime(rs.getLong('leavetime'));
                record.setRecordTime(rs.getLong('recordtime'));
                return record
            }
        }
    }

    @Override
    String getTableName() {
        return 'liverecord'
    }

    @Override
    Map<String, String> getReferenceMapping() {
        return [id: 'id', hotel: 'hotelId', person: 'personId', entertime: 'enterTimeMillis', leavetime: 'leaveTimeMillis', recordtime: 'recordTimeMillis']
    }
}
