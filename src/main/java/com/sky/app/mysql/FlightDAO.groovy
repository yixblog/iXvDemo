package com.sky.app.mysql

import com.sky.app.dao.IFlightDAO
import com.sky.app.dao.pojo.Flight
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository

import java.sql.ResultSet
import java.sql.SQLException

/**
 * Created by dyb on 13-12-23.
 */
@Repository('flightDAO')
class FlightDAO extends BasicCRUDDAO<Flight> implements IFlightDAO {
    @Override
    protected String getBasicQuerySql() {
        return "select * from $tableName"
    }

    @Override
    protected RowMapper<Flight> getFindBasicRowMapper() {
        return new RowMapper<Flight>() {
            @Override
            Flight mapRow(ResultSet rs, int rowNum) throws SQLException {
                Flight flight = new Flight(id: rs.getInt('id'), flightId: rs.getString('flightid'), stat: rs.getString('stat'),
                        dest: rs.getString('dest'), depTime: rs.getString('deptime'),
                        arriveTime: rs.getString('arrivetime'))
                flight.setOffday(rs.getLong('offday'))
                flight
            }
        }
    }

    @Override
    String getTableName() {
        return 'flight'
    }

    @Override
    Map<String, String> getReferenceMapping() {
        return [id: 'id', flightid: 'flightId', offday: 'offdayMillis', stat: 'stat', dest: 'dest', deptime: 'depTime', arrivetime: 'arriveTime']
    }
}
