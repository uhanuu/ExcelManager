package io.excelmanager.v1.config;

import io.excelmanager.v1.properties.ExcelDataSourceProperties;
import io.excelmanager.v1.properties.ExcelFileReaderProperties;
import io.excelmanager.v1.properties.TestDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties({ExcelFileReaderProperties.class, ExcelDataSourceProperties.class})
public class PropertiesConfig {

    private final ExcelFileReaderProperties excelFileReaderProperties;
    private final ExcelDataSourceProperties excelDataSourceProperties;

    @Bean
    public TestDataSource TestDataSource(){
        return new TestDataSource(
                excelFileReaderProperties.getAttribute(),
                excelDataSourceProperties.getUrl(),
                excelDataSourceProperties.getUsername(),
                excelDataSourceProperties.getPassword(),
                excelDataSourceProperties.getDriverClassName(),
                excelFileReaderProperties.getPath(),
                excelFileReaderProperties.getFileName()
        );
    }


}
