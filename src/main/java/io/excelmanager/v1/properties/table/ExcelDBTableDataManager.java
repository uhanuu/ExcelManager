package io.excelmanager.v1.properties.table;

import io.excelmanager.v1.exception.ModeNotFountException;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
public class TableManager {
    private static String[] MODE_CONSTANT = {
            "CREATE", "UPDATE", "DEFAULT"
    };
    private HashMap<String, TableOperation> selectOperationTable = new HashMap<>();
    {
        selectOperationTable.put(MODE_CONSTANT[0], new TableCreator());
        selectOperationTable.put(MODE_CONSTANT[1], new TableUpdater());
    }
    private TableOperation tableOperation;
    @Value("${excel.mode:default}")
    private String mode;
    private String url;
    private String username;
    private String password;
    private String tableName;
    private List<String> attributeKey;
    private List<String> attributeType;

    public TableManager(TableOperation tableOperation, String url, String username, String password, String tableName, List<String> attributeKey, List<String> attributeType) {
        this.tableOperation = selectOperationTable.get(this.mode);
        this.url = url;
        this.username = username;
        this.password = password;
        this.tableName = tableName;
        this.attributeKey = attributeKey;
        this.attributeType = attributeType;
        log.info("TableOperation init ={}",tableOperation);
    }

    @PostConstruct
    public void executeTableOperation(){
        if (!modeCheck(this.mode)){
            throw new ModeNotFountException();
        }

        try{
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            tableOperation.execute(statement,tableName,attributeKey,attributeType);

        } catch (SQLException e){
            e.printStackTrace();
        } catch (NoSuchElementException e){
            log.error("application에서 DataType을 확인해주세요");
        }

    }

    private static boolean modeCheck(String mode){
        String checkValue = mode.toUpperCase();
        for (String constant  : MODE_CONSTANT) {
            if (constant.equals(checkValue)) return true;
        }
        return false;
    }
}
