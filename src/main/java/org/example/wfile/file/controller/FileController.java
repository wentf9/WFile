package org.example.wfile.file.controller;

import org.example.wfile.common.ApiResponse;
import org.example.wfile.common.ResultCodeEnum;
import org.example.wfile.storage.constant.StorageType;
import org.example.wfile.file.entity.FileInfo;
import org.example.wfile.file.factory.FileOperatorFactory;
import org.example.wfile.file.service.FileOperator;
import org.example.wfile.storage.entity.Storage;
import org.example.wfile.storage.manager.StorageManager;
import org.springframework.beans.factory.annotation.Autowired;
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
            return ApiResponse.error(ResultCodeEnum.STORAGE_NOT_FOUND);
        }
        FileOperator fileOperator = storage.getFileOperator();
        path = Paths.get(storage.getBasePath(), path).toString();
        List<FileInfo> fileList = fileOperator.getFileList(path);
        return ApiResponse.success(fileList);
    }
}
