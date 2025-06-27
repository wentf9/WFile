package org.example.wfile.core.config;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

import org.example.wfile.common.ResultCodeEnum;
import org.example.wfile.util.SqliteBuilder;
import org.example.wfile.util.SqliteUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


import javax.sql.DataSource;

/**
 * 用于第一次启动时，初始化数据库的配置类
 */
@Slf4j
@Configuration
public class DatabaseInitialize {
    /**
     * 读取连接地址
     */
    @Value("${spring.datasource.url}")
    private String dataSourceUrl;

    /**
     * 配置sqlite数据源
     */
    @PostConstruct
    public void initSqliteDataSource(){
        //尝试创建sqlite文件-不存在时创建
        SqliteUtils.initSqliteFile(SqliteUtils.getFilePath(dataSourceUrl));
        //创建数据源
        DataSource dataSource  = SqliteBuilder.create().url(dataSourceUrl).build();
        try {
            //尝试初始化数据库-表不存在时创建
            SqliteUtils.initProDb(dataSource.getConnection());
        } catch (Exception e) {
            log.error(ResultCodeEnum.DATABASE_INITIALIZE_ERROR.getMessage(),e);
            System.exit(1);
        }
    }

}
