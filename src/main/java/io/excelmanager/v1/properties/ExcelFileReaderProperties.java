package io.excelmanager.v1.properties;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.util.HashMap;
import java.util.List;

@Getter
@ConfigurationProperties("excel")
@Validated
public class ExcelFileReaderProperties {

    @NotEmpty
    private List<String> attributeKey;
    @NotEmpty
    private List<Character> attributeValue;
    @NotEmpty
    private List<String> attributeType;
    @NotEmpty
    private String path;
    @NotEmpty
    private String fileName;
    @NotEmpty
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
