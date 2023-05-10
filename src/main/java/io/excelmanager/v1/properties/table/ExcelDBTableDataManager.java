package io.excelmanager.v1.properties.table;

import io.excelmanager.v1.exception.ModeNotFountException;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
public class ExcelDBTableDataManager {
    private static String[] MODE_CONSTANT = {
            "CREATE", "UPDATE", "DEFAULT"
    };
    private static HashMap<String, ExcelToTableOperation> selectOperationTable = new HashMap<>();
    {
        selectOperationTable.put(MODE_CONSTANT[0], new ExcelToTableCreator());
        selectOperationTable.put(MODE_CONSTANT[1], new ExcelToTableUpdater());
        selectOperationTable.put(MODE_CONSTANT[2], new ExcelToTableDefault());
    }
    private ExcelToTableOperation ExcelToTableOperation;
    @Value("${excel.mode:default}")
    private String mode;
    private String url;
    private String username;
    private String password;
    private String tableName;
    private List<String> attributeKey;
    private List<String> attributeType;

    public ExcelDBTableDataManager(String url, String username, String password, String tableName, List<String> attributeKey, List<String> attributeType) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.tableName = tableName;
        this.attributeKey = attributeKey;
        this.attributeType = attributeType;
    }

    @PostConstruct
    public void executeTableOperation() throws SQLException {
        String checkValue = mode.toUpperCase();
        if (!modeCheck(checkValue)){
            throw new ModeNotFountException();
        }

        if (checkValue.equals(MODE_CONSTANT[2])) return;

        ExcelToTableOperation = selectOperationTable.get(checkValue);
        Connection connection = null;
        Statement statement = null;
        try{
            //get
            connection = getConnection();
            statement = connection.createStatement();
            ExcelToTableOperation.execute(statement,tableName,attributeKey,attributeType);

        } catch (SQLException e){
            e.printStackTrace();
            throw e;
        } catch (NoSuchElementException e){
            log.error("application에서 DataType을 확인해주세요");
        } finally {
            //시작과 역순으로 close 해주기
            close(connection,statement,null);
        }
    }

    private void close(Connection con, Statement stmt, ResultSet resultSet){

        if (resultSet != null){
            try {
                resultSet.close();
            } catch (SQLException e) {
                log.info("error",e);
            }
        }

        if (stmt != null){
            try {
                stmt.close();
            } catch (SQLException e) {
                log.info("error",e);
            }
        }

        if (con != null){
            try {
                con.close(); //Exception
            } catch (SQLException e) {
                log.info("error",e);
            }
        }
    }

    public Connection getConnection() {
        try{
            Connection connection = DriverManager.getConnection(url, username, password);
            log.info("get connection={}, class={}",connection,connection.getClass());
            return connection;
        } catch (SQLException e){
            throw new IllegalStateException(e);
        }
    }

    private static boolean modeCheck(String checkValue){

        for (String constant  : MODE_CONSTANT) {
            if (constant.equals(checkValue)) return true;
        }
        return false;
    }
}
