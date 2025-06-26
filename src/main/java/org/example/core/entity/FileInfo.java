package org.example.core.entity;


import lombok.Data;

@Data
public class FileInfo {
    private String name;
    private String path;
    private String formatSize;
    private long size;
    // 0 文件 1 目录
    private int type;
}
