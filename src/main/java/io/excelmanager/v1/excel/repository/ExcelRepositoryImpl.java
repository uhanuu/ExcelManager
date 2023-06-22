package io.excelmanager.v1.excel.repository;

import io.excelmanager.v1.excel.service.ExcelFileReader;
import io.excelmanager.v1.properties.ExcelFileReaderProperties;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
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
        //sql injection 조심해야 됨
        String sql = "select "+tableName+"."+fieldName+" from "+tableName;
        return jdbcTemplate.queryForList(sql);
    }



}
