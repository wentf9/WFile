package org.example.wfile.util;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.sqlite.JDBC;

import javax.sql.DataSource;

public class SqliteBuilder {

    private String filePath;

    private String url;

    public static SqliteBuilder create(){
        return  new SqliteBuilder();
    }

    public SqliteBuilder filePath(String filePath){
        this.filePath =filePath;
        return this;
    }

    public SqliteBuilder url(String url) {
        this.url = url;
        return this;
    }

    public DataSource build(){
        if (url!=null&& !url.isEmpty()){
            return DataSourceBuilder.create().
                    url(url).driverClassName(JDBC.class.getName()).build();
        }
        if(filePath!=null&& !filePath.isEmpty()){
            return DataSourceBuilder.create().
                    url("jdbc:sqlite:" + filePath).driverClassName(JDBC.class.getName()).build();
        }
        return null;
    }
}
