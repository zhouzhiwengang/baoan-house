package com.digipower.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.digipower.entity.SysLogRecord;
import com.digipower.mapper.SysLogRecordMapper;
import com.digipower.service.SysLogRecordService;

@Service
public class SysLogRecordServiceImpl extends ServiceImpl<SysLogRecordMapper, SysLogRecord> implements SysLogRecordService {

}
