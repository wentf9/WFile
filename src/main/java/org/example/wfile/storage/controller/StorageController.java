package org.example.wfile.storage.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.wfile.common.ApiResponse;
import org.example.wfile.common.ResultCodeEnum;
import org.example.wfile.file.factory.FileOperatorFactory;
import org.example.wfile.file.service.FileOperator;
import org.example.wfile.storage.constant.StorageType;
import org.example.wfile.storage.entity.Storage;
import org.example.wfile.storage.manager.StorageManager;
import org.example.wfile.storage.service.impl.StorageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
public class StorageController {

    @Autowired
    private StorageManager storageManager;

    @Autowired
    private StorageServiceImpl storageService;

    @GetMapping("/storage/{storageKey}")
    @ResponseBody
    public ApiResponse getStorage(@PathVariable String storageKey) {
        if (storageManager.containsStorage(storageKey)) {
            return ApiResponse.success(storageManager.getStorage(storageKey));
        }
        return ApiResponse.error(ResultCodeEnum.STORAGE_NOT_FOUND);
    }

    @PostMapping("/storage")
    @ResponseBody
    public ApiResponse addStorage(@RequestBody Storage storage) {
        if(storageManager.containsStorage(storage.getKey())){
            return ApiResponse.error(ResultCodeEnum.STORAGE_EXIST);
        }
        FileOperator fileOperator = FileOperatorFactory.getInstance(StorageType.getStorageType(storage.getType()));
        if (fileOperator == null){
            return ApiResponse.error(ResultCodeEnum.STORAGE_TYPE_ERROR);
        }
        if(storageService.addStorage(storage)){
            log.info("add storage success: {}", storage.getName());
            storage.setFileOperator(fileOperator);
            storageManager.addStorage(storage.getKey(), storage);
        }
        return ApiResponse.success();
    }

    @PutMapping("/storage")
    @ResponseBody
    public ApiResponse updateStorage(@RequestBody Storage storage) {
        if (! storageManager.containsStorage(storage.getKey())) {
            return ApiResponse.error(ResultCodeEnum.STORAGE_NOT_FOUND);
        }
        FileOperator fileOperator = FileOperatorFactory.getInstance(StorageType.getStorageType(storage.getType()));
        if (fileOperator == null){
            return ApiResponse.error(ResultCodeEnum.STORAGE_TYPE_ERROR);
        }
        if(storageService.updateStorage(storage)){
            log.info("update storage success: {}", storage.getName());
            storage.setFileOperator(fileOperator);
            storageManager.removeStorage(storage.getKey());
            storageManager.addStorage(storage.getKey(), storage);
        }
        return ApiResponse.success();
    }

    @DeleteMapping("/storage/{storageKey}")
    @ResponseBody
    public ApiResponse deleteStorage(@PathVariable String storageKey) {
        if (! storageManager.containsStorage(storageKey)) {
            return ApiResponse.error(ResultCodeEnum.STORAGE_NOT_FOUND);
        }
        if(storageService.removeStorage(storageKey)) {
            storageManager.removeStorage(storageKey);
        }else {
            return ApiResponse.error(ResultCodeEnum.STORAGE_DELETE_ERROR);
        }
        return ApiResponse.success();
    }

    @GetMapping("/storages")
    @ResponseBody
    public ApiResponse getAllStorages() {
        return ApiResponse.success(storageManager.getAllStorages());
    }
}
