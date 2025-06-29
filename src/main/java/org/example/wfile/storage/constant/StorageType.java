package org.example.wfile.storage.constant;

import lombok.Getter;

@Getter
public enum StorageType {
    LOCAL(1,"local","org.example.wfile.file.service.impl.LocalStorageFileOperator"),
    ALIYUN(2,"aliyun",""),
    TENCENT(3,"tencent",""),
    QINIU(4,"qiniu","");
    private final String storageClassName;
//    private final String ;
    private final String name;
    private final long id;
    StorageType(long id, String name, String storageClassName) {
        this.name = name;
        this.storageClassName = storageClassName;
        this.id = id;
    }

    public static StorageType getStorageType(String storageName) {
        for (StorageType storageType : StorageType.values()) {
            if (storageType.getName().equals(storageName)) {
                return storageType;
            }
        }
        return null;
    }

    public static StorageType getStorageType(int storageId) {
        for (StorageType storageType : StorageType.values()) {
            if (storageType.getId() == storageId) {
                return storageType;
            }
        }
        return null;
    }

}
