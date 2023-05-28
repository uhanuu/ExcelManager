package io.excelmanager.v1.properties.table;

import io.excelmanager.v2.exception.ModeNotFountException;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
public class ExcelDBTableDataManager {
    private final JdbcTemplate template;

    private static String[] MODE_CONSTANT = {
            "CREATE", "UPDATE", "DEFAULT"
    };

    @Value("${excel.mode:default}")
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
        if (!modeCheck(checkValue)){
            throw new ModeNotFountException();
        }

        if (checkValue.equals(MODE_CONSTANT[2])) return;

        droptable();
        String createTableSql = setInitCreateQuery(attributeKey,attributeType,tableName);
        log.info("table 생성={}",createTableSql);
        template.update(createTableSql);

    }

    private void droptable() {
        String sql = "DROP TABLE IF EXISTS " + tableName;
        log.info("{} table 삭제",tableName);
        template.update(sql);
    }

    private static String setInitCreateQuery(List<String> attributeKey, List<String> attributeType, String tableName) {
        StringBuffer sb = new StringBuffer();

        sb.append("CREATE TABLE "+tableName+" (");

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

    private static String setInitDatatypeTable(List<String> attributeType, int index)
            throws NoSuchElementException {
        switch (attributeType.get(index).toLowerCase()){
            case "string":
                return "varchar(80)";
            case "int":
                return "int";
            case "long":
                return "bigint";
        }
        throw new NoSuchElementException();
    }


    private static boolean modeCheck(String checkValue){

        for (String constant  : MODE_CONSTANT) {
            if (constant.equals(checkValue)) return true;
        }
        return false;
    }
}
