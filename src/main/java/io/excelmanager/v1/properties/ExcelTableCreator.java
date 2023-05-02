package io.excelmanager.v1.properties;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
public class ExcelTableCreator {

    private String url;
    private String username;
    private String password;
    private String tableName;
    private List<String> attributeKey;
    private List<String> attributeType;

    public ExcelTableCreator(String url, String username, String password,
                             String tableName, List<String> attributeKey,
                             List<String> attributeType) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.tableName = tableName;
        this.attributeKey = attributeKey;
        this.attributeType = attributeType;
    }

    @PostConstruct
    public void createTable(){
        log.info("createTable={}",tableName);
        try (Connection connection = DriverManager.getConnection(url, username, password)) {

            Statement statement = connection.createStatement();

            // Drop table if it exists
            statement.executeUpdate("DROP TABLE IF EXISTS " + tableName);
            log.info("{} table 삭제",tableName);

            // Create table
            String createTableSql = setInitCreateQuery(attributeKey,attributeType,tableName);

            log.info("table 생성={}",createTableSql);
            statement.executeUpdate(createTableSql);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NoSuchElementException e){
            log.error("application에서 DataType을 확인해주세요");
        }
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
    throws NoSuchElementException{
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
