package io.excelmanager.v1.excel.controller;

import io.excelmanager.v1.excel.service.ExcelConnection;
import io.excelmanager.v1.excel.service.ExcelFileReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/admin")
public class ExcelController {

    private final ExcelFileReader excelFileReader;
    private final ExcelConnection excelConnection;

    @GetMapping("/insert-db")
    public List<Map<String, Object>> ExcelDataReader(){
        List<Map<String, Object>> excelData = excelFileReader.readExcel();

        excelConnection.execute(excelData);
        return excelData;
    }
}
