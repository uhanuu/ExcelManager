package io.excelmanager.v1.properties;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Getter
@ConfigurationProperties("excel")
@Validated
public class ExcelFileReaderProperties {

    private List<String> attributeKey;
    private List<Character> attributeValue;
    private List<String> attributeType;
    private String path;
    private String fileName;
    private String databaseTableName;

    public ExcelFileReaderProperties(List<String> attributeKey, List<Character> attributeValue,
                                     List<String> attributeType, String path, String fileName,
                                     String databaseTableName) {
        this.attributeKey = attributeKey;
        this.attributeValue = attributeValue;
        this.attributeType = attributeType;
        this.path = path;
        this.fileName = fileName;
        this.databaseTableName = databaseTableName;
    }
}
