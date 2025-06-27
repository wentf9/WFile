package org.example.wfile.file.service;

import org.example.wfile.file.entity.FileInfo;

import java.util.List;

public interface FileOperater {
    int uploadFile(String path, String fileName, byte[] bytes);
    byte[] downloadFile(String path, String fileName);
    int deleteFile(String path, String fileName);
    int renameFile(String path, String oldName, String newName);
    int moveFile(String path, String fileName, String destPath);
    List<FileInfo> getFileList(String path);
    FileInfo getFileItem(String path, String fileName);
}
