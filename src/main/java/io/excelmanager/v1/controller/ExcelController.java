package io.excelmanager.v1.controller;

import io.excelmanager.v1.dto.ExcelDto;
import io.excelmanager.v1.service.ExcelConnection;
import io.excelmanager.v1.service.ExcelFileReader;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ExcelController {

    private final ExcelFileReader excelFileReader;
    private final ExcelConnection excelConnection;

    @GetMapping("/admin/insert-db")
    public List<Map<String, Object>> ExcelDataReader() {
        List<Map<String, Object>> excelData = excelFileReader.readExcel();

        try {
            excelConnection.excute(excelData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return excelData;
    }

    @PostMapping("/admin/insert-db")
    public ResponseEntity insertDatabase(@Validated @RequestBody ExcelDto excelDto, Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.badRequest().body(errors.getFieldError());
        }

        return ResponseEntity.ok().body(excelDto);
    }
}
