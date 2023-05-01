package io.excelmanager.v1.properties;

import jakarta.annotation.PostConstruct;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Data
@Slf4j
public class TestDataSource {

    private List<String> attribute;
    private String url;
    private String username;
    private String password;
    private String driverClassName;
    private String path;
    private String fileName;

    public TestDataSource(
            List<String> attribute,
            String url, String username,
            String password,
            String driverClassName,
            String path,
            String fileName
    ) {

        this.attribute = attribute;
        this.url = url;
        this.username = username;
        this.password = password;
        this.driverClassName = driverClassName;
        this.path = path;
        this.fileName = fileName;
    }

    @PostConstruct
    public void init(){
        log.info("url={}", url);
        log.info("username={}", username);
        log.info("password={}", password);
        log.info("attribute={}", attribute);
        log.info("driverClassName={}", driverClassName);
        log.info("path={}", path);
        log.info("fileName={}", fileName);
    }
}
