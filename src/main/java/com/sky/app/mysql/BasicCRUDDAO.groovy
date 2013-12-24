package com.sky.app.mysql

import com.sky.app.dao.IBasicCRUDDAO
import com.sky.app.dao.pojo.IDatabaseBean
import org.apache.commons.lang.StringUtils
import org.apache.log4j.Logger
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.*
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.jdbc.support.KeyHolder

import javax.annotation.Resource
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.SQLException

/**
 * Created by Yixian on 13-12-22.
 */
abstract class BasicCRUDDAO<T extends IDatabaseBean> implements IBasicCRUDDAO<T> {
    private Logger logger = Logger.getLogger(getClass());
    @Resource(name = 'jdbcTemplate')
    protected JdbcTemplate template;

    @Override
    void saveObject(T object) {
        List<String> columns = getDatabaseColumns();
        if (object.pkAutoIncrement()) {
            columns.remove('id');
        }
        String sql = "insert into $tableName (${listColumnString(columns)}) values (${StringUtils.repeat(',?', columns.size()).substring(1)})"
        logger.debug("save object sql:$sql");
        Map<String, String> referenceMapping = getReferenceMapping();
        if (object.pkAutoIncrement()) {
            KeyHolder holder = new GeneratedKeyHolder();
            int rowCount = template.update(new PreparedStatementCreator() {
                @Override
                PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement ps = connection.prepareStatement(sql);
                    columns.eachWithIndex { String col, int index ->
                        String fieldName = referenceMapping[col];
                        Object value = object[fieldName];
                        StatementCreatorUtils.setParameterValue(ps, index + 1, SqlTypeValue.TYPE_UNKNOWN, value);
                    }
                    return ps;
                }
            }, holder);
            if (rowCount > 0) {
                object['id'] = holder.getKey().intValue();
            }
        } else {
            List<Object> valueArray = buildInsertValues(object, columns);
            template.update(sql, valueArray.toArray())
        }
    }

    @Override
    List<T> findMany(T example, String... columns) {
        String sql = getBasicQuerySql();
        sql += " where ${buildQueryWhereClause(example, columns)}";
        template.query(sql, getFindOneParamValues(example, columns), getFindBasicRowMapper());
    }

    @Override
    List<T> listAll() {
        return template.query(getBasicQuerySql(), getFindBasicRowMapper())
    }

    protected List<Object> buildInsertValues(T bean, List<String> columns) {
        String[] columnArray = new String[columns.size()]
        columns.toArray(columnArray)
        return buildValueArray(bean, false, columnArray)
    }

    protected String listColumnString(List<String> keyList) {
        String columnString = "";
        keyList.each { key ->
            columnString += ",$key"
        }
        return columnString.length() == 0 ? "" : columnString.substring(1);
    }

    @Override
    T findOneObject(T example, String... queryKeys) {
        try {
            String sql = getBasicQuerySql();
            sql += " where ${buildQueryWhereClause(example, queryKeys)}"
            logger.debug("find one query:$sql")
            template.queryForObject(sql, getFindBasicRowMapper(), getFindOneParamValues(example, queryKeys))
        } catch (EmptyResultDataAccessException ignored) {
            return null;
        }
    }

    protected abstract String getBasicQuerySql();

    protected abstract RowMapper<T> getFindBasicRowMapper();

    protected Object[] getFindOneParamValues(T example, String... queryKeys) {
        return buildQueryValues(example, queryKeys).toArray();
    }

    @Override
    void updateObject(T object) {
        List<String> columns = getDatabaseColumns();
        columns.remove('id');
        String sql = "update $tableName set ${updateSetterSqlString(columns)} where id=?"
        List valueArray = buildInsertValues(object, columns);
        valueArray.add(object['id']);
        template.update(sql, valueArray.toArray());
    }

    protected String updateSetterSqlString(List<String> columns) {
        String sqlString = "";
        columns.each { column ->
            sqlString += ",$column=?";
        }
        return sqlString.length() == 0 ? "" : sqlString.substring(1);
    }

    @Override
    void deleteObject(T object) {
        String sql = "delete from $tableName where id=?";
        template.update(sql, object['id']);
    }

    @Override
    List<String> getDatabaseColumns() {
        return referenceMapping.keySet().toList();
    }

    protected String buildQueryWhereClause(T example, String... queryColumns) {
        String whereClause = "";
        Map<String, String> referenceMap = getReferenceMapping();
        queryColumns?.each { col ->
            String fieldName = referenceMap[col];
            if (example[fieldName] != null) {
                whereClause += " and $col=?";
            }
        }
        return whereClause.length() == 0 ? "" : whereClause.substring(5);
    }

    protected List<Object> buildQueryValues(T example, String... columns) {
        return buildValueArray(example, true, columns);
    }

    protected List<Object> buildValueArray(T example, boolean skipNullField, String... columns) {
        List<Object> values = [];
        Map<String, String> referenceMap = getReferenceMapping();
        columns?.each { col ->
            String fieldName = referenceMap[col];
            if (!skipNullField || example[fieldName] != null) {
                values.add(example[fieldName]);
            }
        }
        return values;
    }
}
