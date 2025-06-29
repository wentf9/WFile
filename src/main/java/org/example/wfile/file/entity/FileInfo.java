package org.example.wfile.file.entity;


import lombok.Data;

@Data
public class FileInfo {
    private String name;
    private String path;
    private String formatSize;
    private long size;
    // 0 文件 1 目录
    private String type;
    private String lastModified;
    private boolean isDir;
}
