package com.sky.app.mysql

import com.sky.app.dao.IJusticeEducationAttendDAO
import com.sky.app.dao.beans.JusticeEducation
import com.sky.app.dao.beans.JusticeEducationAttended
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
@Repository('justiceEducationAttendDAO')
class JusticeEducationAttendDAO implements IJusticeEducationAttendDAO {
    @Resource(name = 'jdbcTemplate')
    private JdbcTemplate template;

    @Override
    List<JusticeEducationAttended> findEducationAttendByPerson(int personId) {
        String sql = """select edu.pkid eduid,edu.date,edu.location,
                            p.* from person p
                        left join justice_education_attend a on a.personid=p.pkid
                        left join justice_education edu on a.educationid=edu.pkid
                         where p.pkid=?"""
        return template.query(sql, [personId].toArray(), new RowMapper<JusticeEducationAttended>() {
            @Override
            JusticeEducationAttended mapRow(ResultSet rs, int rowNum) throws SQLException {
                Person person = new Person(id: rs.getInt('pkid'), name: rs.getString('name'), idCard: rs.getString('idcard'), birth: rs.getString('birth'))
                person.setSexId(rs.getInt('sex'));
                JusticeEducation education = new JusticeEducation(id: rs.getInt('eduid'), date: rs.getString('date'), location: rs.getString('location'));
                new JusticeEducationAttended(education: education, person: person);
            }
        })
    }
}
