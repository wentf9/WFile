package org.example.core.entity;

import lombok.Data;
import org.example.core.constant.StorageType;
import org.example.core.service.FileOperater;

@Data
public abstract class AbstractStorage {
    private String name;
    private StorageType type;
    private FileOperater fileOperater;

    public AbstractStorage(String name, StorageType type, FileOperater fileOperater)
    {
        this.name = name;
        this.type = type;
        this.fileOperater = fileOperater;
    }
}
