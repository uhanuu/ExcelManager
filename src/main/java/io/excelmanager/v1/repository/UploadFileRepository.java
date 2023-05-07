package io.excelmanager.v1.repository;

import io.excelmanager.v1.file.UploadFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
@Slf4j
public class UploadFileRepository {

    private final Map<String, String> store = new HashMap<>();

    public String save(UploadFile uploadFile) {
        String uploadFileName = uploadFile.getUploadFileName();

        store.put(uploadFileName,uploadFile.getStoreFileName());
        return uploadFileName;
    }

    public String findByUploadFileName(String uploadFileName) {
        return store.get(uploadFileName);
    }

    public boolean delete(String uploadFileName){

        String removeValue = store.remove(uploadFileName);
        if (removeValue != null){
            return true;
        }
        return false;
    }
}

