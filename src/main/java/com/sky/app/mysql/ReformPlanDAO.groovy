package com.sky.app.mysql

import com.sky.app.dao.IReformPlanDAO
import com.sky.app.dao.beans.JusticeReformPlan
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
@Repository('reformPlanDAO')
class ReformPlanDAO implements IReformPlanDAO {
    @Resource(name = 'jdbcTemplate')
    private JdbcTemplate template;

    @Override
    List<JusticeReformPlan> listPlansOfPerson(int personId) {
        String sql = """select r.pkid planid,r.adddate,r.analyse,r.method,p.*
                            from person p left join justice_reform_plan r on r.personid=p.pkid
                         where p.pkid=?"""
        return template.query(sql, [personId].toArray(), new RowMapper<JusticeReformPlan>() {
            @Override
            JusticeReformPlan mapRow(ResultSet rs, int rowNum) throws SQLException {
                Person person = new Person(id: rs.getInt('pkid'), name: rs.getString('name'), idCard: rs.getString('idcard'), birth: rs.getString('birth'))
                person.setSexId(rs.getInt('sex'));
                new JusticeReformPlan(id: rs.getInt('planid'), addDate: rs.getString('adddate'), analysis: rs.getString('analyse'), method: rs.getString('method'), person: person)
            }
        })
    }
}
