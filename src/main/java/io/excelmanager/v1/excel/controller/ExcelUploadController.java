package io.excelmanager.v1.excel.controller;


import io.excelmanager.v1.excel.service.ExcelUploadService;
import io.excelmanager.v1.file.FileStore;
import io.excelmanager.v1.file.UploadFile;
import io.excelmanager.v1.repository.UploadFileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ExcelUploadController {

    private final ExcelUploadService excelUploadService;

    @PostMapping("/excel/upload")
    public ResponseEntity<UploadFile> saveItem(@RequestParam("excelFile") MultipartFile file) throws IOException {

        UploadFile attachFile = excelUploadService.upload(file);
        return ResponseEntity.ok().body(attachFile);
    }
    
}
