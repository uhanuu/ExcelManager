package io.excelmanager.v1.excel.repository;

import java.util.List;

public interface ExcelRepository {
    public List findField(String fieldName);
    public List findFieldByList(List<String> fieldNames);
}
