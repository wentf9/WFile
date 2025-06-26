package org.example.storage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.storage.entity.Storage;
import org.example.storage.mapper.StorageMapper;
import org.example.storage.service.IStorageService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class StorageServiceImpl extends ServiceImpl<StorageMapper, Storage> implements IStorageService {


}
