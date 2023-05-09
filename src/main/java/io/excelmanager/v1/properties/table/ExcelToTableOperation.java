package io.excelmanager.v1.properties.table;

import java.sql.Statement;
import java.util.List;

public interface TableOperation {
    void execute(Statement statement,String tableName,
                 List<String> attributeKey, List<String> attributeType);
}
