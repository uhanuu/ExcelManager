package io.excelmanager.v1.file;

import io.excelmanager.v1.repository.UploadFileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Slf4j
@Component
@RequiredArgsConstructor
public class FileStore {

    @Value("${excel.path}")
    private String fileDir;

    private final UploadFileRepository fileRepository;

    public String getFullPath(String filename) {
        return fileDir + filename;
    }

    public UploadFile storeFile(MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()) {
            return null;
        }
        String originalFilename = multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(originalFilename);

        fileCheckAndDelete(originalFilename);

        multipartFile.transferTo(new File(getFullPath(storeFileName)));
        return new UploadFile(originalFilename, storeFileName);
    }


    private String createStoreFileName(String originalFilename) {
        String ext = extractExt(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }

    private void fileCheckAndDelete(String originalFilename) {
        String findUploadFileName =
                fileRepository.findByUploadFileName(originalFilename);
        if (findUploadFileName != null){
            deleteFile(findUploadFileName);
        }
    }
    private void deleteFile(String fileName){
        File file = new File(fileDir + fileName);
        if (file.delete()){
            log.info("file 삭제 완료");
        }
    }


}
