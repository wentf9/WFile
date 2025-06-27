package org.example.wfile.storage.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.wfile.storage.entity.Storage;
import org.example.wfile.storage.mapper.StorageMapper;
import org.example.wfile.storage.service.IStorageService;
import org.springframework.stereotype.Service;


@Service
public class StorageServiceImpl extends ServiceImpl<StorageMapper, Storage> implements IStorageService {


}
