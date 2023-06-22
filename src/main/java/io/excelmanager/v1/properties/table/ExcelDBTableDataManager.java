package io.excelmanager.v1.properties.table;


import io.excelmanager.v1.constant.ModeConstant;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
public class ExcelDBTableDataManager {
    private final JdbcTemplate template;

    @Value("${excel.mode:none}")
    private String mode;
    private String tableName;
    private List<String> attributeKey;
    private List<String> attributeType;

    public ExcelDBTableDataManager(DataSource dataSource , String tableName, List<String> attributeKey, List<String> attributeType) {
        this.template = new JdbcTemplate(dataSource);
        this.tableName = tableName;
        this.attributeKey = attributeKey;
        this.attributeType = attributeType;
    }

    @PostConstruct
    public void executeTableOperation() {
        String checkValue = mode.toUpperCase();

        if (checkValue.equals(ModeConstant.CREATE)){
            createTable();
        } else if (checkValue.equals(ModeConstant.DROP)) {
            dropTable();
        } else if (checkValue.equals(ModeConstant.NONE)) {

        } else throw new NoSuchElementException("application.properties에서 사용 가능한 모드는 NONE,CREATE,DROP만 가능합니다.");
    }

    private void createTable() {
        dropTable();
        String createTableSql = setInitCreateQuery(attributeKey,attributeType,tableName);
        log.info("table 생성={}",createTableSql);
        template.update(createTableSql);
    }

    private void dropTable() {
        String sql = "DROP TABLE IF EXISTS " + tableName;
        log.info("{} table 삭제",tableName);
        template.update(sql);
    }

    private static String setInitCreateQuery(List<String> attributeKey, List<String> attributeType, String tableName) {
        StringBuffer sb = new StringBuffer();

        sb.append("CREATE TABLE "+tableName+" (");
        sb.append("id bigint PRIMARY KEY AUTO_INCREMENT,");

        return String.valueOf(setInitColumnTable(attributeKey,attributeType,sb));
    }

    private static StringBuffer setInitColumnTable(List<String> attributeKey, List<String> attributeType, StringBuffer sb){

        for (int i = 0; i < attributeKey.size(); i++){
            sb.append(attributeKey.get(i)+" "+setInitDatatypeTable(attributeType,i)+",");
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append(");");

        return sb;
    }

    //최대한 모든 DBMS 사용할 수 있게 처리해주기
    private static String setInitDatatypeTable(List<String> attributeType, int index)
            throws NoSuchElementException {
        switch (attributeType.get(index).toLowerCase()){
            case "string":
                return "varchar(255)";
            case "int":
                return "int";
            case "long":
                return "bigint";
        }
        throw new NoSuchElementException();
    }
}
