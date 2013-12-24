package com.sky.app.mysql

import com.sky.app.dao.INetCafeDAO
import com.sky.app.dao.pojo.NetCafe
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository

import java.sql.ResultSet
import java.sql.SQLException

/**
 * Created by dyb on 13-12-23.
 */
@Repository('netCafeDAO')
class NetCafeDAO extends BasicCRUDDAO<NetCafe> implements INetCafeDAO{
    @Override
    protected String getBasicQuerySql() {
        return "select * from $tableName"
    }

    @Override
    protected RowMapper<NetCafe> getFindBasicRowMapper() {
        return new RowMapper<NetCafe>() {
            @Override
            NetCafe mapRow(ResultSet rs, int rowNum) throws SQLException {
                new NetCafe(id: rs.getString('id'), name: rs.getString('name'), address: rs.getString('address'),
                        phone: rs.getString('phone'), ip: rs.getString('ip'), legalPerson: rs.getString('legal'))
            }
        }
    }

    @Override
    String getTableName() {
        return "netcafe"
    }

    @Override
    Map<String, String> getReferenceMapping() {
        return [id: 'id', name: 'name', address: 'address', phone: 'phone', ip: 'ip', legal: 'legalPerson']
    }

}
