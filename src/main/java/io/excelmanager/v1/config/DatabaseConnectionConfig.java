package io.excelmanager.v1.config;

import io.excelmanager.v1.properties.ExcelDataSourceProperties;
import io.excelmanager.v1.properties.ExcelFileReaderProperties;
import io.excelmanager.v1.properties.DataSourceConverter;
import io.excelmanager.v1.properties.table.ExcelDBTableDataManager;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties({ExcelFileReaderProperties.class, ExcelDataSourceProperties.class})
public class DatabaseConnectionConfig {

    private final ExcelFileReaderProperties excelFileReaderProperties;
    private final ExcelDataSourceProperties excelDataSourceProperties;
    private final DataSource dataSource;

    @Bean
    public DataSourceConverter DataSourceConverter(){
        return new DataSourceConverter(
                excelFileReaderProperties.getAttributeKey(),
                excelFileReaderProperties.getAttributeValue()
        );
    }

    @Bean
    public ExcelDBTableDataManager ExcelDBTableDataManager(){
        return new ExcelDBTableDataManager(
                dataSource,
                excelDataSourceProperties.getUrl(),
                excelDataSourceProperties.getUsername(),
                excelDataSourceProperties.getPassword(),
                excelFileReaderProperties.getDatabaseTableName(),
                excelFileReaderProperties.getAttributeKey(),
                excelFileReaderProperties.getAttributeType()
        );
    }


}
