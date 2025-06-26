package org.example.core.factory;

import org.example.core.constant.StorageType;
import org.example.core.service.FileOperater;

public class FileOperaterFactory {
    public static FileOperater getInstance(StorageType storageType) {
        if (storageType == null) {
            return null;
        }
        FileOperater fileOperater = null;
        try {
            fileOperater = (FileOperater) Class.forName(storageType.getStorageClassName()).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileOperater;
    }
}
