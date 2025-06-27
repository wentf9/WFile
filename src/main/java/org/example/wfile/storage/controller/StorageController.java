package org.example.wfile.storage.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.wfile.common.ApiResponse;
import org.example.wfile.common.ResultCodeEnum;
import org.example.wfile.storage.manager.StorageManager;
import org.example.wfile.storage.service.impl.StorageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Slf4j
@Controller
public class StorageController {

    @Autowired
    private StorageManager storageManager;

    @Autowired
    private StorageServiceImpl storageService;

    @GetMapping("/storage/{storageKey}")
    public ApiResponse getStorage(@PathVariable String storageKey) {
        if (storageManager.containsStorage(storageKey)) {
            return ApiResponse.success(storageManager.getStorage(storageKey));
        }
        return ApiResponse.error(ResultCodeEnum.STORAGE_NOT_FOUND);
    }
}
