package com.sky.app.mysql

import com.sky.app.dao.ICafeRecordDAO
import com.sky.app.dao.pojo.CafeRecord
import com.sky.app.dao.pojo.NetCafe
import com.sky.app.dao.pojo.Person
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository

import java.sql.ResultSet
import java.sql.SQLException

/**
 * Created by dyb on 13-12-23.
 */
@Repository('cafeRecordDAO')
class CafeRecordDAO extends BasicCRUDDAO<CafeRecord> implements ICafeRecordDAO{
    @Override
    protected String getBasicQuerySql() {
        return "select p.*,c.*,r.* from caferecord r left join person p on p.id=r.person left join netcafe c on c.id=r.cafe"
    }

    @Override
    protected RowMapper<CafeRecord> getFindBasicRowMapper() {
        return new RowMapper<CafeRecord>() {
            @Override
            CafeRecord mapRow(ResultSet rs, int rowNum) throws SQLException {
                Person person = new Person(id: rs.getInt('p.id'), name: rs.getString('p.name'), cardNumber: rs.getString('cardnumber'),
                        focusDepart: rs.getString('focusdepart'), echnic: rs.getString('echnic'), zipcode: rs.getString('zipcode'),
                        address: rs.getString('p.address'), phone: rs.getString('p.phone'));
                person.setSexValue(rs.getInt('sex'))
                person.setBirth(rs.getLong('birth'))

                NetCafe cafe = new NetCafe(id: rs.getString('c.id'), name: rs.getString('c.name'), address: rs.getString('c.address'),
                        phone: rs.getString('c.phone'), ip: rs.getString('c.ip'), legalPerson: rs.getString('legal'));

                CafeRecord record = new CafeRecord(id: rs.getInt('r.id'), cafe: cafe, person: person, ip: rs.getString('r.ip'), mac: rs.getString('mac'));
                record.setOnlineTime(rs.getLong('onlinetime'));
                record.setOfflineTime(rs.getLong('offlinetime'));
                return record;
            }
        }
    }

    @Override
    String getTableName() {
        return 'caferecord'
    }

    @Override
    Map<String, String> getReferenceMapping() {
        return [id: 'id', cafe: 'cafeId', person: 'personId', onlinetime: 'onlineTimeMillis', offlinetime: 'offlineTimeMillis', ip: 'ip', mac: 'mac']
    }
}
