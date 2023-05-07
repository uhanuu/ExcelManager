package io.excelmanager.v1.excel.controller;


import io.excelmanager.v1.excel.service.ExcelUploadService;
import io.excelmanager.v1.file.UploadFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/excel")
public class ExcelUploadController {

    private final ExcelUploadService excelUploadService;

    @PostMapping("/upload")
    public ResponseEntity<UploadFile> saveItem(@RequestParam("excelFile") MultipartFile file) throws IOException {

        UploadFile attachFile = excelUploadService.upload(file);
        return ResponseEntity.ok().body(attachFile);
    }

    @ResponseBody
    @GetMapping("/download/{filename}")
    public ResponseEntity<Resource> downloadExcelFile(@PathVariable String filename) throws MalformedURLException {
        return excelUploadService.downloadExcelFile(filename);
    }
}
