package com.sky.app.mysql

import com.sky.app.dao.ICarDAO
import com.sky.app.dao.beans.Car
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
@Repository("carDAO")
class CarDAO implements ICarDAO {
    @Resource(name = "jdbcTemplate")
    private JdbcTemplate template;

    @Override
    List<Car> listDriverCars(int driverId) {
        String sql = "select c.*,p.pkid personid,p.name,p.idcard,p.birth,p.sex from person p " +
                "left join car c on p.pkid=c.driver where p.pkid=?";
        return template.query(sql, [driverId].toArray(), new RowMapper<Car>() {
            @Override
            Car mapRow(ResultSet rs, int rowNum) throws SQLException {
                Person person = new Person(id: rs.getInt('personId'), name: rs.getString('name'), idCard: rs.getString('idcard'), birth: rs.getString('birth'));
                person.setSexId(rs.getInt('sex'));
                Car car = new Car(driver: person, id: rs.getInt('pkid'), plate: rs.getString('plate'), brand: rs.getString('brand'), color: rs.getString('color'), engineCode: rs.getString('enginecode'));
                return car
            }
        });
    }

}
