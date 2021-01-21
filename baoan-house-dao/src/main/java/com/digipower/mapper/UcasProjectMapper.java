package com.digipower.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.digipower.entity.UcasProject;
import com.digipower.vo.UcasProjectVo;

public interface UcasProjectMapper extends BaseMapper<UcasProject> {
	
	List<UcasProjectVo> selectList(Map<String, Object> parameter);
	
	IPage<UcasProjectVo> selectPage(Page page, @Param("vo")Map<String, Object> parameter);
}
