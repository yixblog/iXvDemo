package com.sky.app.mysql

import com.sky.app.dao.IHotelDAO
import com.sky.app.dao.pojo.Hotel
import org.apache.log4j.Logger
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository

import java.sql.ResultSet
import java.sql.SQLException

/**
 * Created by Yixian on 13-12-21.
 */
@Repository('hotelDAO')
class HotelDAO extends BasicCRUDDAO<Hotel> implements IHotelDAO{
    private Logger logger = Logger.getLogger(getClass())

    @Override
    String getTableName() {
        return 'hotel'
    }

    @Override
    Map<String, String> getReferenceMapping() {
        return [id: 'id', code: 'code', name: 'name', legal: 'legalPerson']
    }

    @Override
    protected String getBasicQuerySql() {
        String sql = "select id,code,name,legal from $tableName"
        logger.debug("find one sql:$sql")
        return sql;
    }

    @Override
    protected RowMapper<Hotel> getFindBasicRowMapper() {
        return new HotelRowMapper()
    }

    private class HotelRowMapper implements RowMapper<Hotel> {

        @Override
        Hotel mapRow(ResultSet rs, int rowNum) throws SQLException {
            new Hotel(id: rs.getInt('id'), code: rs.getString('code'), name: rs.getString('name'), legalPerson: rs.getString('legal'));
        }
    }

}
