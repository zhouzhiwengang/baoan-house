package com.digipower.controller;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.digipower.common.controller.AbstractController;
import com.digipower.common.entity.PageParame;
import com.digipower.common.entity.Result;
import com.digipower.common.uuid.SnowflakeIdGenerator;
import com.digipower.entity.UcasAuthPrivilegeInfo;
import com.digipower.service.UcasAuthPrivilegeInfoService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/privilege")
public class PrivilegeController extends AbstractController {

	@Autowired
	private UcasAuthPrivilegeInfoService ucasAuthPrivilegeInfoService;
	@Autowired
	private SnowflakeIdGenerator idGenerator;

	

	// 增
	@ApiOperation(httpMethod = "POST", value = "菜单保存")
	@RequestMapping(value = "/insert", method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public Result insert(@RequestBody UcasAuthPrivilegeInfo entity) {
		String sid = entity.getSid();
		if (StringUtils.isEmpty(sid)) {
			sid = String.valueOf(idGenerator.nextId());
			entity.setSid(sid);
		}
		boolean target = ucasAuthPrivilegeInfoService.save(entity);
		if (target) {
			return Result.ok().setDatas("sid", sid);
		} else {
			return Result.error("菜单新增失败");
		}
	}

	// 改
	@ApiOperation(httpMethod = "POST", value = "菜单修改")
	@RequestMapping(value = "/update", method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public Result update(@RequestBody UcasAuthPrivilegeInfo entity) {
		boolean target = ucasAuthPrivilegeInfoService.updateById(entity);
		if (target) {
			return Result.ok();
		} else {
			return Result.error("菜单更新shib ");
		}
	}

	// 删
	@ApiOperation(httpMethod = "POST", value = "菜单删除")
	@RequestMapping(value = "/delete", method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public Result delete(@RequestBody UcasAuthPrivilegeInfo entity) {
		String sid = entity.getSid();
		if (StringUtils.isNotEmpty(sid)) {
			ucasAuthPrivilegeInfoService.removeById(sid);
			return Result.ok();
		}
		return Result.error("请求参数缺失Sid");
	}

	// 查
	@ApiOperation(httpMethod = "POST", value = "菜单查询指定Sid")
	@RequestMapping(value = "/getSid", method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public Result getUserByName(@RequestBody UcasAuthPrivilegeInfo entity) {
		String sid = entity.getSid();
		if (StringUtils.isNotEmpty(sid)) {
			QueryWrapper<UcasAuthPrivilegeInfo> query = new QueryWrapper<UcasAuthPrivilegeInfo>();
			query.eq("sid", sid);
			return Result.ok().setDatas(ucasAuthPrivilegeInfoService.getOne(query));
		}
		return Result.error("请求参数缺失Sid");

	}

	// 查
	@ApiOperation(httpMethod = "POST", value = "查询符合条件菜单项")
	@RequestMapping(value = "/getList", method = { RequestMethod.POST })
	@ApiImplicitParams({
			@ApiImplicitParam(name = "sid", value = "菜单项Sid", required = false, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "privilegeName", value = "菜单项名称", required = false, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "privilegeCode", value = "菜单项编码", required = false, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "psid", value = "菜单项父类Sid", required = false, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "privilegeType", value = "菜单项类型", required = false, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "state", value = "菜单项状态", required = false, dataType = "String", paramType = "query"),
	})
	public Result getList(@RequestBody Map<String, Object> parame) {
		// 动态构建添加参数
		QueryWrapper<UcasAuthPrivilegeInfo> query = new QueryWrapper<UcasAuthPrivilegeInfo>();
		this.buildQuery(parame, query);

		List<UcasAuthPrivilegeInfo> list = ucasAuthPrivilegeInfoService.list(query);
		return Result.ok().setDatas(list);
	}

	// 查
	@ApiOperation(httpMethod = "POST", value = "基于分页查询符合条件菜单项")
	@RequestMapping(value = "/getPage", method = { RequestMethod.POST })
	@ApiImplicitParams({
		@ApiImplicitParam(name = "sid", value = "菜单项Sid", required = false, dataType = "String", paramType = "query"),
		@ApiImplicitParam(name = "privilegeName", value = "菜单项名称", required = false, dataType = "String", paramType = "query"),
		@ApiImplicitParam(name = "privilegeCode", value = "菜单项编码", required = false, dataType = "String", paramType = "query"),
		@ApiImplicitParam(name = "psid", value = "菜单项父类Sid", required = false, dataType = "String", paramType = "query"),
		@ApiImplicitParam(name = "privilegeType", value = "菜单项类型", required = false, dataType = "String", paramType = "query"),
		@ApiImplicitParam(name = "state", value = "菜单项状态", required = false, dataType = "String", paramType = "query"),
})
	public Result getPage(@RequestBody Map<String, Object> parame) {
		// 动态构建添加参数
		QueryWrapper<UcasAuthPrivilegeInfo> query = new QueryWrapper<UcasAuthPrivilegeInfo>();
		this.buildQuery(parame, query);
		
		PageParame pageParame = this.initPageBounds(parame);
		Page<UcasAuthPrivilegeInfo> page = new Page<UcasAuthPrivilegeInfo>(pageParame.getPage(), pageParame.getLimit());
		IPage<UcasAuthPrivilegeInfo> list = ucasAuthPrivilegeInfoService.page(page, query);
		return Result.ok().setDatas(list);
	}

	@Override
	public void buildQuery(Map<String, Object> parame, Object query) {
		if(query != null){
			QueryWrapper<UcasAuthPrivilegeInfo> queryWrapper = (QueryWrapper<UcasAuthPrivilegeInfo>) query;
			if (parame.get("sid") != null) {
				if (StringUtils.isNotBlank(String.valueOf(parame.get("sid")))) {
					queryWrapper.eq("sid", parame.get("sid"));
				}
			}
			if (parame.get("privilegeName") != null) {
				if (StringUtils.isNotBlank(String.valueOf(parame.get("privilegeName")))) {
					queryWrapper.like("privilege_name", parame.get("privilegeName"));
				}
			}
			if (parame.get("privilegeCode") != null) {
				if (StringUtils.isNotBlank(String.valueOf(parame.get("privilegeCode")))) {
					queryWrapper.like("privilege_code", parame.get("privilegeCode"));
				}
			}
			if (parame.get("psid") != null) {
				if (StringUtils.isNotBlank(String.valueOf(parame.get("psid")))) {
					queryWrapper.eq("psid", parame.get("psid"));
				}
			}
			if (parame.get("privilegeType") != null) {
				if (StringUtils.isNotBlank(String.valueOf(parame.get("privilegeType")))) {
					queryWrapper.eq("privilege_type", parame.get("privilegeType"));
				}
			}
			if (parame.get("state") != null) {
				if (StringUtils.isNotBlank(String.valueOf(parame.get("state")))) {
					queryWrapper.eq("state", parame.get("state"));
				}
			}
		}
		
	}

}
