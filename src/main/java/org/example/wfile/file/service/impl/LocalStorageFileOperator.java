package org.example.wfile.file.service.impl;


import lombok.extern.slf4j.Slf4j;
import org.example.wfile.file.entity.FileInfo;
import org.example.wfile.file.service.FileOperater;
import org.example.wfile.util.FileSizeConverter;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
public class LocalStorageFileOperator implements FileOperater {
    @Override
    public int uploadFile(String path, String fileName, byte[] bytes) {
        return 0;
    }

    @Override
    public byte[] downloadFile(String path, String fileName) {
        return new byte[0];
    }

    @Override
    public int deleteFile(String path, String fileName) {
        return 0;
    }

    @Override
    public int renameFile(String path, String oldName, String newName) {
        return 0;
    }

    @Override
    public int moveFile(String path, String fileName, String destPath) {
        return 0;
    }

    @Override
    public List<FileInfo> getFileList(String path) {
        log.debug("LocalStorageFileOperator.getFileList");
        log.debug("path:{}", path);
        Path pathFile = Paths.get(path);
        List<FileInfo> fileInfoList = new ArrayList<>();
        if (! Files.exists(pathFile)) {
            return Collections.emptyList();
        }
        if (! Files.isDirectory(pathFile)){
            return Collections.emptyList();
        }

        try (Stream<Path> paths = Files.list(pathFile)){
            paths.forEach(p -> {
                FileInfo fileInfo = new FileInfo();
                fileInfo.setName(p.getFileName().toString());
                try {
                    fileInfo.setSize(Files.size(p));
                } catch (IOException e) {
                    log.error("LocalStorageFileOperator.getFileList error: 获取文件大小错误.path:{}", p, e);
                }
                fileInfo.setFormatSize(FileSizeConverter.convertFileSize(fileInfo.getSize(),0));
                fileInfo.setType(Files.isDirectory(p) ? 1 : 0);
                fileInfo.setPath(p.toString());
                fileInfoList.add(fileInfo);});
        } catch (IOException e) {
            log.error("LocalStorageFileOperator.getFileList error: ", e);
        }
        return fileInfoList;
    }

    @Override
    public FileInfo getFileItem(String path, String fileName) {
        return null;

    }
}
