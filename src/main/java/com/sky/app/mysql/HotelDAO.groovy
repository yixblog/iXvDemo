package com.sky.app.mysql

import com.sky.app.dao.IHotelDAO
import com.sky.app.dao.beans.Hotel
import com.sky.app.dao.beans.HotelLiveRecord
import com.sky.app.dao.beans.Person
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository

import javax.annotation.Resource
import java.sql.ResultSet
import java.sql.SQLException

/**
 * dao
 * Created by yixian on 13-12-10.
 */
@Repository('hotelDAO')
class HotelDAO implements IHotelDAO {
    @Resource(name = 'jdbcTemplate')
    private JdbcTemplate template;

    @Override
    List<HotelLiveRecord> listLiveRecordByPerson(int personId) {
        String sql = """select h.pkid hotelId,h.hotelname,h.address,
                         r.pkid id,r.startdate,r.enddate,
                         p.* from person p
                         left join liverecord r on r.personid=p.pkid
                         left join hotel h on h.pkid=r.hotelid
                         where p.pkid=?"""
        return template.query(sql, [personId].toArray(), new RowMapper<HotelLiveRecord>() {
            @Override
            HotelLiveRecord mapRow(ResultSet rs, int rowNum) throws SQLException {
                Hotel hotel = new Hotel(id: rs.getInt('hotelId'), name: rs.getString('hotelname'), address: rs.getString('address'));
                Person person = new Person(id: rs.getInt('pkid'), name: rs.getString('name'), idCard: rs.getString('idcard'), birth: rs.getString('birth'));
                person.setSexId(rs.getInt('sex'));
                HotelLiveRecord record = new HotelLiveRecord(id: rs.getInt('id'), hotel: hotel, person: person, startDate: rs.getString('startdate'), endDate: rs.getString('enddate'))
                return record;
            }
        })
    }

}
