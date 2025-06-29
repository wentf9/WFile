package org.example.wfile.storage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.wfile.storage.entity.Storage;

public interface IStorageService extends IService<Storage>{
    Storage getStorageByStorageKey(String storageKey);
    boolean addStorage(Storage storage);

}
