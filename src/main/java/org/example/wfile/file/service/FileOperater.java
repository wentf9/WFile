package org.example.wfile.file.service;

import org.example.wfile.file.entity.FileInfo;

import java.util.List;

public interface FileOperater {
    int uploadFile(String path, String fileName, byte[] bytes);
    byte[] downloadFile(String path, String fileName);
    boolean deleteFile(String path);
    boolean renameFile(String path, String newName);
    int moveFile(String path, String fileName, String destPath);
    List<FileInfo> getFileList(String path);
    FileInfo getFileItem(String path);
}
