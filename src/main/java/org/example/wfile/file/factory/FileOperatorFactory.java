package org.example.wfile.file.factory;

import lombok.extern.slf4j.Slf4j;
import org.example.wfile.storage.constant.StorageType;
import org.example.wfile.file.service.FileOperator;

@Slf4j
public class FileOperatorFactory {
    public static FileOperator getInstance(StorageType storageType) {
        if (storageType == null) {
            return null;
        }
        FileOperator fileOperator = null;
        try {
            fileOperator = (FileOperator) Class.forName(storageType.getStorageClassName()).getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            log.error("FileOperatorFactory getInstance error", e);
        }
        return fileOperator;
    }
}
