package org.example.wfile.storage.manager;

import jakarta.annotation.PostConstruct;
import org.example.wfile.file.factory.FileOperatorFactory;
import org.example.wfile.storage.constant.StorageType;
import org.example.wfile.storage.entity.Storage;

import org.example.wfile.storage.service.impl.StorageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class StorageManager {

    private final Map<String, Storage> storageMap = new ConcurrentHashMap<>();
    @Autowired
    private StorageServiceImpl storageService;

    @PostConstruct
    public void init() {
        List<Storage> storageList = storageService.list();
        if (storageList == null) {
            return;
        }
        for (Storage storage : storageList) {
            storage.setFileOperator(FileOperatorFactory.getInstance(StorageType.getStorageType(storage.getType())));
            storageMap.put(storage.getKey(), storage);
        }
    }
    public boolean containsStorage(String storageKey) {
        return storageMap.containsKey(storageKey);
    }

    public Storage getStorage(String storageKey) {
        return storageMap.get(storageKey);
    }

    public void addStorage(String storageKey, Storage storage) {
        storageMap.put(storageKey, storage);
    }

    public void removeStorage(String storageKey) {
        storageMap.remove(storageKey);
    }

    public void clear() {
        storageMap.clear();
    }

    public Collection<Storage> getAllStorages() {
        return Collections.unmodifiableCollection(storageMap.values());
    }
}
