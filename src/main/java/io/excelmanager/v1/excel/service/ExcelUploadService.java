package io.excelmanager.v1.excel.service;

import io.excelmanager.v1.file.FileStore;
import io.excelmanager.v1.file.UploadFile;
import io.excelmanager.v1.repository.UploadFileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExcelUploadService {

    private final UploadFileRepository uploadFileRepository;
    private final FileStore fileStore;

    public UploadFile upload(MultipartFile multipartFile) throws IOException {

        UploadFile attachFile = fileStore.storeFile(multipartFile);
        uploadFileRepository.save(attachFile);
        return attachFile;
    }


}
