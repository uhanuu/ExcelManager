package io.excelmanager.v1.excel.controller;

import io.excelmanager.v2.dto.InsertDatabaseDto;
import io.excelmanager.v1.excel.service.ExcelConnection;
import io.excelmanager.v1.excel.service.ExcelFileReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/admin")
public class ExcelController {

    private final ExcelFileReader excelFileReader;
    private final ExcelConnection excelConnection;
//    private final ExcelDataService excelDataService;

    @GetMapping("/insert-db")
    public List<Map<String, Object>> ExcelDataReader() {
        List<Map<String, Object>> excelData = excelFileReader.readExcel();

        try {
            excelConnection.excute(excelData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return excelData;
    }

    @PostMapping("/insert-db")
    public ResponseEntity insertDatabase(@Validated @RequestBody InsertDatabaseDto insertDatabaseDto, BindingResult bindingResult){

        log.info("API 호출");
        if (bindingResult.hasErrors()){
            log.info("검증 오류 발생 errors={}",bindingResult);
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            return ResponseEntity.badRequest().body(allErrors);
        }

//        excelDataService.insertExcelData(insertDatabaseDto); 아직 구현안함


        return ResponseEntity.ok().body(insertDatabaseDto);
    }
}
