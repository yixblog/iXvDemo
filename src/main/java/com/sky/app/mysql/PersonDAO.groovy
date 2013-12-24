package com.sky.app.mysql

import com.sky.app.dao.IPersonDAO
import com.sky.app.dao.pojo.Person
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository

import java.sql.ResultSet
import java.sql.SQLException

/**
 * Created by dyb on 13-12-23.
 */
@Repository('personDAO')
class PersonDAO extends BasicCRUDDAO<Person> implements IPersonDAO{
    @Override
    protected String getBasicQuerySql() {
        return "select * from $tableName"
    }

    @Override
    protected RowMapper<Person> getFindBasicRowMapper() {
        return new RowMapper<Person>() {
            @Override
            Person mapRow(ResultSet rs, int rowNum) throws SQLException {
                Person person = new Person(id: rs.getInt('id'), name: rs.getString('name'),
                        cardNumber: rs.getString('cardnumber'), focusDepart: rs.getString('focusdepart'),
                        echnic: rs.getString('echnic'), zipcode: rs.getString('zipcode'), address: rs.getString('address'),
                        phone: rs.getString('phone'));
                person.setSexValue(rs.getInt('sex'));
                person.setBirth(rs.getLong('birth'));

                return person
            }
        }
    }

    @Override
    String getTableName() {
        return 'person'
    }

    @Override
    Map<String, String> getReferenceMapping() {
        return [id: 'id', name: 'name', cardnumber: 'cardNumber', focusdepart: 'focusDepart', sex: 'sexValue', echnic: 'echnic',
                birth: 'birthMillis', zipcode: 'zipcode', address: 'address', phone: 'phone']
    }
}
