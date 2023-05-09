package io.excelmanager.v1.properties.table;

import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
public class ExcelToTableCreator implements ExcelToTableOperation {
    @Override
    public void execute(Statement statement, String tableName,
                        List<String> attributeKey,
                        List<String> attributeType) throws SQLException
    {
        statement.executeUpdate("DROP TABLE IF EXISTS " + tableName);
        log.info("{} table 삭제",tableName);

        // Create table
        String createTableSql = setInitCreateQuery(attributeKey,attributeType,tableName);

        log.info("table 생성={}",createTableSql);
        statement.executeUpdate(createTableSql);
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
                return "varchar(50)";
            case "int":
                return "int";
            case "long":
                return "bigint";
        }
        throw new NoSuchElementException();
    }
}
