package org.example.wfile;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("org.example.wfile.storage.mapper") // 修改了包路径以确保StorageMapper被扫描到
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}