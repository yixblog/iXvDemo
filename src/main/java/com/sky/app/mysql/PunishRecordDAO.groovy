package com.sky.app.mysql

import com.sky.app.dao.IPunishRecordDAO
import com.sky.app.dao.pojo.Person
import com.sky.app.dao.pojo.PunishRecord
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository

import java.sql.ResultSet
import java.sql.SQLException

/**
 * Created by dyb on 13-12-23.
 */
@Repository('punishRecord')
class PunishRecordDAO extends BasicCRUDDAO<PunishRecord> implements IPunishRecordDAO{
    @Override
    protected String getBasicQuerySql() {
        return "select p.*,r.* from $tableName r left join person p on p.id=r.person"
    }

    @Override
    protected RowMapper<PunishRecord> getFindBasicRowMapper() {
        return new RowMapper<PunishRecord>() {
            @Override
            PunishRecord mapRow(ResultSet rs, int rowNum) throws SQLException {
                Person person = new Person(id: rs.getInt('p.id'), name: rs.getString('name'), cardNumber: rs.getString('cardnumber'),
                        focusDepart: rs.getString('focusdepart'), echnic: rs.getString('echnic'), zipcode: rs.getString('zipcode'),
                        address: rs.getString('address'), phone: rs.getString('phone'));
                person.setSexValue(rs.getInt('sex'))
                person.setBirth(rs.getLong('birth'))

                PunishRecord record = new PunishRecord(id: rs.getInt('r.id'), person: person, detail: rs.getString('detail'),
                        caseName: rs.getString('casename'), punish: rs.getString('punish'));
                record.setPunishDate(rs.getLong('punishdate'));
                return record;
            }
        }
    }

    @Override
    String getTableName() {
        return 'punishrecord'
    }

    @Override
    Map<String, String> getReferenceMapping() {
        return [id: 'id', person: 'personId', detail: 'detail', casename: 'caseName', punish: 'punish', punishdate: 'punishDateMillis']
    }
}
