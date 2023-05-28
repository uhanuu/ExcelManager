package io.excelmanager.v1.excel.service;

import io.excelmanager.v1.properties.DataSourceConverter;
import io.excelmanager.v1.properties.ExcelFileReaderProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class ExcelConnection {
    private final ExcelFileReaderProperties excelFileReaderProperties;
    private final JdbcTemplate jdbcTemplate;

    public void execute(List<Map<String, Object>> list){
        String query = setInitialQuery();
        Map<String, Object> mapKeyToValue = DataSourceConverter.getMapKeyToValue();
        List<String> attributeType = excelFileReaderProperties.getAttributeType();
        List<String> key = excelFileReaderProperties.getAttributeKey();
        log.info("총 라인 수 = {}", list.size());

        for (int i = 1; i < list.size(); i++) {
            if (list.get(i).isEmpty()) continue;

            checkExtract(list, query, mapKeyToValue, attributeType, key, i);
        }

        log.info("insert를 완료했습니다.");
    }

    private void checkExtract(List<Map<String, Object>> list, String query, Map<String, Object> mapKeyToValue,
                              List<String> attributeType, List<String> key, int i) {
        Object[] params = new Object[mapKeyToValue.size()];

        int index = 0;

        for (int j = 0; j < mapKeyToValue.size(); j++) {
            String excelValue = (String) list.get(i).get(key.get(j));

            switch (attributeType.get(j).toLowerCase()) {
                case "string":
                    params[index++] = excelValue;
                    break;
                case "int":
                    params[index++] = Integer.parseInt(excelValue);
                    break;
                case "long":
                    params[index++] = Long.parseLong(excelValue);
                    break;
            }
        }
        log.info("params={}", Arrays.toString(params));
        // update query 실행
        jdbcTemplate.update(query, params);
    }

    private String setInitialQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ");

        List<String> attributes = excelFileReaderProperties.getAttributeKey();
        String databaseTableName = excelFileReaderProperties.getDatabaseTableName();

        sb.append(databaseTableName).append(" (");
        for (String attribute : attributes) {
            sb.append(attribute).append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(") VALUES (");

        for (int i = 0; i < attributes.size(); i++) {
            sb.append("?,");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(")");

        return sb.toString();
    }
}
