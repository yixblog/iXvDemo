package com.sky.app.mysql

import com.sky.app.dao.IInternetCafeDAO
import com.sky.app.dao.beans.InternetCafe
import com.sky.app.dao.beans.InternetCafeRecord
import com.sky.app.dao.beans.Person
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository

import javax.annotation.Resource
import java.sql.ResultSet
import java.sql.SQLException

/**
 * Created by yixian on 13-12-10.
 */
@Repository('internetCafeDAO')
class InternetCafeDAO implements IInternetCafeDAO {
    @Resource(name = 'jdbcTemplate')
    private JdbcTemplate template;

    @Override
    List<InternetCafeRecord> listCafeRecordByPerson(int personId) {
        String sql = """select c.pkid cafeid,c.name cafename,c.address,
                               r.pkid recordid,r.starttime,
                               p.*
                        from person p
                          left join internetcafe_record r on r.personid=p.pkid
                          left join internetcafe c on c.pkid=r.cafeid
                        where p.pkid=?"""
        template.query(sql, [personId].toArray(), new RowMapper<InternetCafeRecord>() {
            @Override
            InternetCafeRecord mapRow(ResultSet rs, int rowNum) throws SQLException {
                InternetCafe cafe = new InternetCafe(id: rs.getInt('cafeid'), name: rs.getString('cafename'), address: rs.getString('address'));
                Person person = new Person(id: rs.getInt('pkid'), name: rs.getString('name'), idCard: rs.getString('idcard'), birth: rs.getString('birth'));
                person.setSexId(rs.getInt('sex'));
                InternetCafeRecord record = new InternetCafeRecord(id: rs.getInt('recordid'), startTime: rs.getString('starttime'), person: person, cafe: cafe)
                record
            }
        })
    }

}
