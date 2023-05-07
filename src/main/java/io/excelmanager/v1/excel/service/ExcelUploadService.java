package io.excelmanager.v1.excel.service;

import io.excelmanager.v1.file.FileStore;
import io.excelmanager.v1.file.UploadFile;
import io.excelmanager.v1.repository.UploadFileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;

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

    public ResponseEntity<Resource> downloadExcelFile(String filename) throws MalformedURLException {
        String findUploadFileName = uploadFileRepository.findByUploadFileName(filename);

        UrlResource urlResource = new UrlResource("file:" + fileStore.getFullPath(findUploadFileName));
        String encodedUploadFileName = UriUtils.encode(filename, StandardCharsets.UTF_8);
        String contentDisposition = "attachment; filename=\"" + encodedUploadFileName + "\"";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(urlResource);
    }

}
