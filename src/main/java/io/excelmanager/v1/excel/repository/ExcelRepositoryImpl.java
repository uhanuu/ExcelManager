package io.excelmanager.v1.excel.repository;

import io.excelmanager.v1.excel.service.ExcelFileReader;
import io.excelmanager.v1.properties.ExcelFileReaderProperties;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ExcelRepositoryImpl implements ExcelRepository{

    private final JdbcTemplate jdbcTemplate;
    private final ExcelFileReaderProperties excelFileReaderProperties;


    public List<?> findField(String fieldName){
        String tableName = excelFileReaderProperties.getDatabaseTableName();
        String sql = "select "+tableName+"."+fieldName+" from "+tableName;
        return jdbcTemplate.queryForList(sql);
    }

    public List<?> findFieldByList(List<String> selectFields){

        String tableName = excelFileReaderProperties.getDatabaseTableName();
        StringBuffer sb = new StringBuffer();
        sb.append("select ");
        for (String selectField : selectFields) {
            sb.append(tableName+"."+selectField+", ");
        }
        sb.deleteCharAt(sb.lastIndexOf(", "));
        sb.append(" from "+tableName);
        return jdbcTemplate.queryForList(String.valueOf(sb));
    }

}
