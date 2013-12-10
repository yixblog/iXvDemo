package com.sky.app.mysql

import com.sky.app.dao.IVolunteerWorkDAO
import com.sky.app.dao.beans.JusticeVolunteerWork
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
@Repository('volunteerWorkDAO')
class VolunteerWorkDAO implements IVolunteerWorkDAO{
    @Resource(name = 'jdbcTemplate')
    private JdbcTemplate template;

    @Override
    List<JusticeVolunteerWork> listPersonVolunteerWorks(int personId) {
        String sql = '''select p.*,v.pkid vid,v.date,v.location,v.detail from person p
                       left join justice_volunteer_work v on v.personid=p.pkid
                       where p.pkid=?''';
        return template.query(sql,[personId].toArray(),new RowMapper<JusticeVolunteerWork>() {
            @Override
            JusticeVolunteerWork mapRow(ResultSet rs, int rowNum) throws SQLException {
                Person person = new Person(id: rs.getInt('pkid'),name: rs.getString('name'),idCard: rs.getString('idcard'),birth: rs.getString('birth'));
                person.setSexId(rs.getInt('sex'));
                new JusticeVolunteerWork(person: person,id: rs.getInt('vid'),date: rs.getString('date'),location: rs.getString('location'),detail: rs.getString('detail'));
            }
        })
    }
}
