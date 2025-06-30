package org.example.wfile.file.service.impl;


import lombok.extern.slf4j.Slf4j;
import org.example.wfile.common.ResultCodeEnum;
import org.example.wfile.exception.CoustomException;
import org.example.wfile.file.entity.FileInfo;
import org.example.wfile.file.service.FileOperator;
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
public class LocalStorageFileOperator implements FileOperator {
    @Override
    public int uploadFile(String path, String fileName, byte[] bytes) {
        return 0;
    }

    @Override
    public byte[] downloadFile(String path, String fileName) {
        return new byte[0];
    }

    @Override
    public boolean deleteFile(String path) {
        Path pathFile = Paths.get(path);
        try {
            return Files.deleteIfExists(pathFile);
        } catch (IOException e) {
            log.error("LocalStorageFileOperator.deleteFile error: ", e);
            return false;
        }
    }

    @Override
    public boolean renameFile(String path, String newName) {
        if(newName == null || newName.isEmpty()){
            throw new CoustomException(ResultCodeEnum.NOT_ACCEPTABLE);
        }

        Path sourcePath = Paths.get(path);
        Path targetPath = sourcePath.getParent().resolve(newName);
        try {
            Files.move(sourcePath, targetPath);
            return true;
        } catch (IOException e) {
            log.error("LocalStorageFileOperator.renameFile error: ", e);
            return false;
        }

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
            throw new CoustomException(ResultCodeEnum.FILE_NOT_DIR);
        }

        try (Stream<Path> paths = Files.list(pathFile)){
            paths.forEach(p -> {
                FileInfo fileInfo = new FileInfo();
                fileInfo.setName(p.getFileName().toString());
                try {
                    fileInfo.setSize(Files.size(p));
                    fileInfo.setLastModified(Files.getLastModifiedTime(pathFile).toString());
                    fileInfo.setType(Files.probeContentType(p));
                } catch (IOException e) {
                    log.error("LocalStorageFileOperator.getFileList error: 获取文件信息错误.path:{}", p, e);
                }
                fileInfo.setFormatSize(FileSizeConverter.convertFileSize(fileInfo.getSize(),0));
                fileInfo.setDir(Files.isDirectory(p));
                fileInfo.setPath(p.toString());
                fileInfoList.add(fileInfo);});
        } catch (IOException e) {
            log.error("LocalStorageFileOperator.getFileList error: ", e);
        }
        return fileInfoList;
    }

    @Override
    public FileInfo getFileItem(String path) {
        Path pathFile = Paths.get(path);
        if (! Files.exists(pathFile)) {
            throw new CoustomException(ResultCodeEnum.FILE_NOT_FOUND);
        }
        if (Files.isDirectory(pathFile)){
            throw new CoustomException(ResultCodeEnum.FILE_IS_DIR);
        }
        FileInfo fileInfo = new FileInfo();
        fileInfo.setName(pathFile.getFileName().toString());
        try {
            fileInfo.setSize(Files.size(pathFile));
            fileInfo.setLastModified(Files.getLastModifiedTime(pathFile).toString());
            fileInfo.setType(Files.probeContentType(pathFile));
        }catch (IOException e){
            log.error("LocalStorageFileOperator.getFileItem error: 获取文件信息错误.path:{}", pathFile, e);
        }
        fileInfo.setFormatSize(FileSizeConverter.convertFileSize(fileInfo.getSize(),0));
        fileInfo.setDir(Files.isDirectory(pathFile));
        fileInfo.setPath(pathFile.toString());
        return fileInfo;
    }
}
