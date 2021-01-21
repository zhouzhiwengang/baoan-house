package com.digipower.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.digipower.entity.UcasProject;
import com.digipower.mapper.UcasProjectMapper;
import com.digipower.service.UcasProjectService;
import com.digipower.vo.UcasProjectVo;

@Service
public class UcasProjectServiceImpl extends ServiceImpl<UcasProjectMapper, UcasProject> implements UcasProjectService {
	
	@Autowired
	private UcasProjectMapper mapper;
	
	@Override
	public List<UcasProjectVo> selectList(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return mapper.selectList(parameter);
	}

	@Override
	public IPage<UcasProjectVo> selectPage(Page<UcasProjectVo> page, Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return mapper.selectPage(page, parameter);
	}
}
