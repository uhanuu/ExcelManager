package io.excelmanager.v1.controller;

import io.excelmanager.v1.service.ExcelConnection;
import io.excelmanager.v1.service.ExcelFileReader;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ExcelController {

    private final ExcelFileReader excelFileReader;
    private final ExcelConnection excelConnection;

    @GetMapping("/test")
    public List<Map<String, Object>> test() {
        List<Map<String, Object>> excelData = excelFileReader.readExcel();

        try {
            excelConnection.excute(excelData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return excelData;
    }
}
