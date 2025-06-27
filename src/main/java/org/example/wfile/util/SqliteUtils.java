package org.example.wfile.util;

import lombok.extern.slf4j.Slf4j;
import org.example.wfile.common.ResultCodeEnum;
import org.example.wfile.exception.CoustomException;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;

@Slf4j
public class SqliteUtils {

    /**
     * 初始化项目db
     * @param connection
     */
    public static void initProDb(Connection connection){
        //判断数据表是否存在
        boolean hasPro = false;
        try {
            hasPro = true;
            //测试数据表是否存在
            connection.prepareStatement("select count(*) from storage").execute();
        }catch (SQLException e){
            //不存在
            log.debug("table storage is not exist");
            hasPro = false;
        }
        //不存在时创建db
        if(!hasPro) {
            log.debug(">>>start init project db");
            File file = null;
            try {
                //读取初始化数据sql
                file = ResourceUtils.getFile("classpath:sql/init.sql");
            } catch (FileNotFoundException e) {
                throw new CoustomException("classpath:sql/init.sql not found",e);
            }

            //获取sql
            String[] sqlList = getSqlStrings(file);

            try (connection) {
                for (String str : sqlList) {
                    //开始初始化数据库
                    connection.setAutoCommit(false);
                    connection.prepareStatement(str).execute();
                }
                //提交sql
                connection.commit();
            } catch (SQLException e) {
                throw new CoustomException("init sql execute error",e);
            }
            log.debug("finish init project db>>>");
        }else {
            log.debug("project db is exist");
        }
    }

    private static String[] getSqlStrings(File file) {
        String sql = "";
        try (FileInputStream fis = new FileInputStream(file); InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8)) {
            BufferedReader bf = null;
            bf = new BufferedReader(isr);
            String content = "";
            StringBuilder sb = new StringBuilder();
            while (true) {
                content = bf.readLine();
                if (content == null) {
                    break;
                }
                sb.append(content.trim());
            }
            sql = sb.toString();
        } catch (IOException e) {
            throw new CoustomException("classpath:sql/init.sql read error",e);
        }
        //分割sql
        return sql.split(";");
    }

    public static void initSqliteFile(String filePath){
        Path path = Paths.get(filePath);
        if (Files.exists(path)) {
            return;
        }
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new CoustomException(ResultCodeEnum.DATABASE_CREATE_ERROR);
        }
    }

    public static String getFilePath(String url){
        url = url.replace("jdbc:sqlite:", "");
        return url;
    }
}
