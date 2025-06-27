package org.example.wfile.file.factory;

import lombok.extern.slf4j.Slf4j;
import org.example.wfile.storage.constant.StorageType;
import org.example.wfile.file.service.FileOperater;

@Slf4j
public class FileOperatorFactory {
    public static FileOperater getInstance(StorageType storageType) {
        if (storageType == null) {
            return null;
        }
        FileOperater fileOperater = null;
        try {
            fileOperater = (FileOperater) Class.forName(storageType.getStorageClassName()).newInstance();
        } catch (Exception e) {
            log.error("FileOperatorFactory getInstance error", e);
        }
        return fileOperater;
    }
}
