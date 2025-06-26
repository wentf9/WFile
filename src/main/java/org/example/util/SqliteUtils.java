package org.example.util;

import lombok.extern.slf4j.Slf4j;
import org.example.common.ResultCodeEnum;
import org.example.exception.CoustomException;
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
                log.error("classpath:sql/init.sql not found",e);
            }

            //获取sql
            String sql = "";
            FileInputStream fis = null;
            InputStreamReader isr = null;
            try {
                if (file != null) {
                    fis = new FileInputStream(file);
                }
                if (fis != null) {
                    isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
                }
                BufferedReader bf = null;
                if (isr != null) {
                    bf = new BufferedReader(isr);
                }
                String content = "";
                StringBuilder sb = new StringBuilder();
                while (true) {
                    if (bf != null) {
                        content = bf.readLine();
                    }
                    if (content == null) {
                        break;
                    }
                    sb.append(content.trim());
                }
                sql = sb.toString();
            } catch (IOException e) {
                log.error("read init.sql error",e);
                System.exit(1);
            } finally {
                try {
                    if (isr != null) {
                        isr.close();
                    }
                    if (fis != null) {
                        fis.close();
                    }
                } catch (IOException e) {
                    log.error("close resource error",e);
                }
            }
            //分割sql
            String[] sqlList = sql.split(";");

            try (connection) {
                for (String str : sqlList) {
                    //开始初始化数据库
                    connection.setAutoCommit(false);
                    connection.prepareStatement(str).execute();
                }
                //提交sql
                connection.commit();
            } catch (SQLException e) {
                log.error("init db error",e);
                System.exit(1);
            }
            log.debug("finish init project db>>>");
        }else {
            log.debug("project db is exist");
        }
    }

    public static void initDb(Connection connection,String... sqls){
        log.debug(">>>start initDb:{}",sqls);
        try {
            for(String str:sqls) {
                connection.setAutoCommit(false);
                connection.prepareStatement(str).execute();
            }
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        log.debug("finish initDb>>>");
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
