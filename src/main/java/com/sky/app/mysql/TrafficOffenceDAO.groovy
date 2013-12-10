package com.sky.app.mysql

import com.sky.app.dao.ICarDAO
import com.sky.app.dao.ITrafficOffenceDAO
import com.sky.app.dao.beans.Car
import com.sky.app.dao.beans.TrafficOffence
import org.apache.commons.lang.StringUtils
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository

import javax.annotation.Resource
import java.sql.ResultSet
import java.sql.SQLException

/**
 * Created by yixian on 13-12-10.
 */
@Repository('trafficOffenceDAO')
class TrafficOffenceDAO implements ITrafficOffenceDAO {
    @Resource(name = 'jdbcTemplate')
    private JdbcTemplate template;

    @Resource(name = 'carDAO')
    private ICarDAO carDAO;

    @Override
    List<TrafficOffence> listDriverTrafficOffences(int driverId) {
        List<Car> cars = carDAO.listDriverCars(driverId);
        String inParam = StringUtils.repeat("?,", cars.size());
        inParam = inParam.substring(0, inParam.lastIndexOf(","));
        String sql = "select t.* from traffic_offence t where t.carid in ($inParam)";
        return template.query(sql, [driverId].toArray(), new RowMapper<TrafficOffence>() {
            @Override
            TrafficOffence mapRow(ResultSet rs, int rowNum) throws SQLException {
                TrafficOffence offence = new TrafficOffence(id: rs.getInt('pkid'), location: rs.getString('location'), information: rs.getString('infomation'), time: rs.getString('time'));
                int carid = rs.getInt('carid');
                Car car = scanCarById(cars, carid);
                offence.setCar(car);
                return offence;
            }
        })
    }


    private Car scanCarById(List<Car> cars, int carId) {
        for (Car car : cars) {
            if (car.getId() == carId) {
                return car;
            }
        }
        return null;
    }
}
