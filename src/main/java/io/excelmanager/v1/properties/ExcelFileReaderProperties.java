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
    @NotEmpty
    private List<String> attribute;
    @NotEmpty
    private String path;
    @NotEmpty
    private String fileName;

    public ExcelFileReaderProperties(List<String> attribute, String path, String fileName) {
        this.attribute = attribute;
        this.path = path;
        this.fileName = fileName;
    }

}
