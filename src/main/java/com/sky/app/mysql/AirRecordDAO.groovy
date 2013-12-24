package com.sky.app.mysql

import com.sky.app.dao.IAirRecordDAO
import com.sky.app.dao.pojo.AirRecord
import com.sky.app.dao.pojo.Flight
import com.sky.app.dao.pojo.Person
import org.apache.log4j.Logger
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository

import java.sql.ResultSet
import java.sql.SQLException

/**
 * Created by Yixian on 13-12-22.
 */
@Repository('airRecordDAO')
class AirRecordDAO extends BasicCRUDDAO<AirRecord> implements IAirRecordDAO{
    private Logger logger = Logger.getLogger(getClass())

    @Override
    String getTableName() {
        return "airrecord"
    }


    @Override
    protected String getBasicQuerySql() {
        "select f.*,p.*,r.* from airrecord r left join person p on p.id=r.person left join flight f on f.id=r.flight"
    }

    @Override
    protected RowMapper<AirRecord> getFindBasicRowMapper() {
        return new AirRecordMapper()
    }

    @Override
    Map<String, String> getReferenceMapping() {
        return [id: 'id', flight: 'flightId', person: 'personId', bdno: 'bdno', seat: 'seat', 'class': 'seatClass', bagunit: 'bagUnit']
    }

    private class AirRecordMapper implements RowMapper<AirRecord> {

        @Override
        AirRecord mapRow(ResultSet rs, int rowNum) throws SQLException {
            Person person = new Person(id: rs.getInt('p.id'), name: rs.getString('p.name'),
                    cardNumber: rs.getString('cardnumber'), focusDepart: rs.getString('focusdepart'),
                    echnic: rs.getString('echnic'), zipcode: rs.getString('zipcode'), address: rs.getString('p.address'),
                    phone: rs.getString('p.phone'));
            person.setBirth(rs.getLong('birth'));
            person.setSexValue(rs.getInt('sex'));

            Flight flight = new Flight(id: rs.getInt('f.id'), flightId: rs.getString('flightid'),
                    offday: rs.getLong('offday'), stat: rs.getString('stat'), dest: rs.getString('dest'),
                    depTime: rs.getString('deptime'), arriveTime: rs.getString('arrivetime'));

            new AirRecord(id: rs.getInt('r.id'), flight: flight, person: person, bdno: rs.getString('bdno'),
                    seat: rs.getString('seat'), seatClass: rs.getString('class'), bagUnit: rs.getInt('bagunit'))
        }
    }
}
