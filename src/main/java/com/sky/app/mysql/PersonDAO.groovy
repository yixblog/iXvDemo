package com.sky.app.mysql

import com.sky.app.dao.IPersonDAO
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
@Repository('personDAO')
class PersonDAO implements IPersonDAO {
    @Resource(name = 'jdbcTemplate')
    private JdbcTemplate template;

    @Override
    Person findPersonById(int id) {
        String sql = "select * from person where pkid=?";
        return template.queryForObject(sql, new RowMapper<Person>() {
            @Override
            Person mapRow(ResultSet rs, int rowNum) throws SQLException {
                Person person = new Person(id: rs.getInt('pkid'), name: rs.getString('name'), idCard: rs.getString('idcard'), birth: rs.getString('birth'))
                person.setSexId(rs.getInt('sex'));
                return person;
            }
        }, id)
    }
}
