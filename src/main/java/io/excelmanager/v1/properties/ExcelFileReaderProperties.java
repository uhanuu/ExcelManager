package io.excelmanager.v1.properties;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Getter
@ConfigurationProperties("excel")
@Validated
public class ExcelFileReaderProperties {

    @NotEmpty(message = "excel.attribute-key를 입력해주세요")
    private List<String> attributeKey;
    @NotEmpty(message = "excel.attribute-value를 입력해주세요")
    private List<Character> attributeValue;
    @NotEmpty(message = "excel.attribute-type를 입력해주세요")
    private List<String> attributeType;
    @NotEmpty(message = "excel.path를 입력해주세요")
    private String path;
    @NotEmpty(message = "excel.file-name를 입력해주세요")
    private String fileName;
    @NotEmpty(message = "excel.database-table-name를 입력해주세요")
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
