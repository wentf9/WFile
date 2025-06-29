package org.example.wfile.storage.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.wfile.storage.entity.Storage;
import org.example.wfile.storage.mapper.StorageMapper;
import org.example.wfile.storage.service.IStorageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class StorageServiceImpl extends ServiceImpl<StorageMapper, Storage> implements IStorageService {


    @Override
    public Storage getStorageByStorageKey(String storageKey) {
        return null;
    }

    @Override
    @Transactional
    public boolean addStorage(Storage storage) {
        return this.save(storage);
    }
}
