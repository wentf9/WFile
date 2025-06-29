package org.example.wfile.core.config;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

import org.example.wfile.common.ResultCodeEnum;
import org.example.wfile.util.SqliteUtils;
import org.springframework.beans.factory.annotation.Value;


import javax.sql.DataSource;

/**
 * 用于第一次启动时，初始化数据库的配置类
 */
@Slf4j
public class DatabaseInitialize {
    /**
     * 读取连接地址
     */
    @Value("${spring.datasource.url}")
    private String dataSourceUrl;

    @Resource
    private DataSource dataSource;

    /**
     * sqlite数据库初始化
     */
    @PostConstruct
    public void initSqliteDataSource() {
        //尝试创建sqlite文件-不存在时创建
        SqliteUtils.initSqliteFile(SqliteUtils.getFilePath(dataSourceUrl));
        try {
            //尝试初始化数据库-表不存在时创建
            SqliteUtils.initProDb(dataSource.getConnection());
        } catch (Exception e) {
            log.error(ResultCodeEnum.DATABASE_INITIALIZE_ERROR.getMessage(),e);
            System.exit(1);
        }
    }

}
