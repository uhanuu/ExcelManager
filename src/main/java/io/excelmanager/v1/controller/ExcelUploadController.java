package io.excelmanager.v1.controller;


import io.excelmanager.v1.file.FileStore;
import io.excelmanager.v1.file.UploadFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ExcelUploadController {
    private final FileStore fileStore;

    @PostMapping("/excel/upload")
    public ResponseEntity saveItem(@RequestParam("excelFile") MultipartFile file) throws IOException {
        UploadFile attachFile = fileStore.storeFile(file);
        log.info("attachFile={}",file);
        log.info("getStoreFileName={}",attachFile.getStoreFileName());
        log.info("getUploadFileName={}",attachFile.getUploadFileName());


        return ResponseEntity.ok().build();
    }
}
