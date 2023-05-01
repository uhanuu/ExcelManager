package io.excelmanager.v1.config;

import io.excelmanager.v1.properties.ExcelDataSourceProperties;
import io.excelmanager.v1.properties.ExcelFileReaderProperties;
import io.excelmanager.v1.properties.DataSourceConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties({ExcelFileReaderProperties.class, ExcelDataSourceProperties.class})
public class PropertiesConfig {

    private final ExcelFileReaderProperties excelFileReaderProperties;

    @Bean
    public DataSourceConverter DataSourceConverter(){
        return new DataSourceConverter(
                excelFileReaderProperties.getAttributeKey(),
                excelFileReaderProperties.getAttributeValue()
        );
    }


}
