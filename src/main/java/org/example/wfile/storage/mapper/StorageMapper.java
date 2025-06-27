package org.example.wfile.storage.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.wfile.storage.entity.Storage;

@Mapper
public interface StorageMapper extends BaseMapper<Storage> {
//    Storage selectByName(String name);
}
