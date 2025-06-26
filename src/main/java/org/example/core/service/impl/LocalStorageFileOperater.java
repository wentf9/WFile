package org.example.core.service.impl;


import lombok.extern.slf4j.Slf4j;
import org.example.core.entity.FileInfo;
import org.example.core.service.FileOperater;
import org.example.util.FileSizeConverter;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
public class LocalStorageFileOperater implements FileOperater {
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
        log.debug("LocalStorageFileOperater.getFileList");
        log.debug("path:" + path);
        Path pathFile = Paths.get(path);
        List<FileInfo> fileInfoList = new ArrayList<>();
        if (! Files.exists(pathFile)) {
            return Collections.emptyList();
        }
        if (! Files.isDirectory(pathFile)){
            return Collections.emptyList();
        }

        try (Stream<Path> paths = Files.list(pathFile)){
            for (Path p : paths.toList()){
                FileInfo fileInfo = new FileInfo();
                fileInfo.setName(p.getFileName().toString());
                fileInfo.setSize(Files.size(p));
                fileInfo.setFormatSize(FileSizeConverter.convertFileSize(fileInfo.getSize(),0));
                fileInfo.setType(Files.isDirectory(p) ? 1 : 0);
                fileInfo.setPath(p.toString());
                fileInfoList.add(fileInfo);
            }
        } catch (IOException e) {
            log.error("LocalStorageFileOperater.getFileList error: ", e);
        }
        return fileInfoList;
    }

    @Override
    public FileInfo getFileItem(String path, String fileName) {
        return null;

    }
}
