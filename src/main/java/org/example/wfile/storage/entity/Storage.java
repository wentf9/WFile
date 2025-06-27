package org.example.wfile.storage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.wfile.file.service.FileOperater;

import java.io.Serial;
import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("storage")
public class Storage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.AUTO)
    private long id;
    @TableField(value = "name")
    private String name;
    @TableField(value = "key")
    private String key;
    @TableField(value = "type")
    private int type;
    @TableField(exist = false)
    private FileOperater fileOperater;
    @TableField(value = "base_path")
    private String basePath;
    @TableField(value = "access_url")
    private String accessUrl;
    @TableField(value = "config")
    private String config;
    @TableField(value = "status")
    private String status;
    @TableField(value = "create_time")
    private String createTime;
    @TableField(value = "update_time")
    private String updateTime;
}
