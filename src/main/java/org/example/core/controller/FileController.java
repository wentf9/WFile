package org.example.core.controller;

import org.example.common.ApiResponse;
import org.example.core.constant.StorageType;
import org.example.core.entity.FileInfo;
import org.example.core.factory.FileOperaterFactory;
import org.example.core.service.FileOperater;
import org.example.storage.entity.Storage;
import org.example.storage.manager.StorageManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;


import java.nio.file.Paths;
import java.util.List;

@Controller
public class FileController {
    @Autowired
    private StorageManager storageManager;
    @GetMapping("/{storageKey}/{path}")
    @ResponseBody
    public ApiResponse index(@PathVariable String storageKey, @PathVariable String path) {
        Storage storage = null;
        if (storageManager.containsStorage(storageKey)){
            storage = storageManager.getStorage(storageKey);
        }else {
            return ApiResponse.error("storage not found");
        }
        FileOperater fileOperater = FileOperaterFactory.getInstance(StorageType.LOCAL);
        if (storage != null) {
            path=Paths.get(storage.getBasePath(), path).toString();
        }
        List<FileInfo> fileList = fileOperater.getFileList(path);
        return ApiResponse.success(fileList);
    }
}
