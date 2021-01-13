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
import com.digipower.entity.UcasAuthUserInfo;
import com.digipower.service.UcasAuthUserInfoService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/user")
public class UserController extends AbstractController {
	@Autowired
	private UcasAuthUserInfoService ucasAuthUserInfoService;
	@Autowired
	private SnowflakeIdGenerator idGenerator;

	// 增
	@ApiOperation(httpMethod = "POST", value = "用户保存")
	@RequestMapping(value = "/insert", method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public Result insert(@RequestBody UcasAuthUserInfo entity) {
		String sid = entity.getSid();
		if (StringUtils.isEmpty(sid)) {
			sid = String.valueOf(idGenerator.nextId());
			entity.setSid(sid);
		}
		boolean target = ucasAuthUserInfoService.save(entity);
		if (target) {
			return Result.ok().setDatas("sid", sid);
		} else {
			return Result.error("用户新增失败");
		}
	}

	// 改
	@ApiOperation(httpMethod = "POST", value = "用户修改")
	@RequestMapping(value = "/update", method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public Result update(@RequestBody UcasAuthUserInfo entity) {
		boolean target = ucasAuthUserInfoService.updateById(entity);
		if (target) {
			return Result.ok();
		} else {
			return Result.error("用户更新失败");
		}
	}

	// 删
	@ApiOperation(httpMethod = "POST", value = "用户删除")
	@RequestMapping(value = "/delete", method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public Result delete(@RequestBody UcasAuthUserInfo entity) {
		String sid = entity.getSid();
		if (StringUtils.isNotEmpty(sid)) {
			ucasAuthUserInfoService.removeById(sid);
			return Result.ok();
		}
		return Result.error("请求参数缺失Sid");
	}

	// 查
	@ApiOperation(httpMethod = "POST", value = "用户查询指定Sid")
	@RequestMapping(value = "/getSid", method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public Result getUserByName(@RequestBody UcasAuthUserInfo entity) {
		String sid = entity.getSid();
		if (StringUtils.isNotEmpty(sid)) {
			QueryWrapper<UcasAuthUserInfo> query = new QueryWrapper<UcasAuthUserInfo>();
			query.eq("sid", sid);
			return Result.ok().setDatas(ucasAuthUserInfoService.getOne(query));
		}
		return Result.error("请求参数缺失Sid");

	}

	// 查
	@ApiOperation(httpMethod = "POST", value = "查询符合条件用户")
	@RequestMapping(value = "/getList", method = { RequestMethod.POST })
	@ApiImplicitParams({
		@ApiImplicitParam(name = "sid", value = "用户Sid", required = false, dataType = "String", paramType = "query"),
		@ApiImplicitParam(name = "userPin", value = "账户名称", required = false, dataType = "String", paramType = "query"),
		@ApiImplicitParam(name = "userName", value = "真实姓名", required = false, dataType = "String", paramType = "query"),
		@ApiImplicitParam(name = "tel", value = "电话号码", required = false, dataType = "String", paramType = "query"),
		@ApiImplicitParam(name = "phone", value = "手机号码", required = false, dataType = "String", paramType = "query"),
		@ApiImplicitParam(name = "email", value = "邮箱", required = false, dataType = "String", paramType = "query"),
		@ApiImplicitParam(name = "state", value = "用户状态", required = false, dataType = "String", paramType = "query")
	})
	public Result getList(@RequestBody Map<String, Object> parame) {
		// 动态构建添加参数
		QueryWrapper<UcasAuthUserInfo> query = new QueryWrapper<UcasAuthUserInfo>();
		this.buildQuery(parame, query);

		List<UcasAuthUserInfo> list = ucasAuthUserInfoService.list(query);
		return Result.ok().setDatas(list);
	}

	// 查
	@ApiOperation(httpMethod = "POST", value = "基于分页查询符合条件菜单项")
	@RequestMapping(value = "/getPage", method = { RequestMethod.POST })
	@ApiImplicitParams({
			@ApiImplicitParam(name = "sid", value = "用户Sid", required = false, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "userPin", value = "账户名称", required = false, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "userName", value = "真实姓名", required = false, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "tel", value = "电话号码", required = false, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "phone", value = "手机号码", required = false, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "email", value = "邮箱", required = false, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "state", value = "用户状态", required = false, dataType = "String", paramType = "query")
	})
	public Result getPage(@RequestBody Map<String, Object> parame) {
		// 动态构建添加参数
		QueryWrapper<UcasAuthUserInfo> query = new QueryWrapper<UcasAuthUserInfo>();
		this.buildQuery(parame, query);

		PageParame pageParame = this.initPageBounds(parame);
		Page<UcasAuthUserInfo> page = new Page<UcasAuthUserInfo>(pageParame.getPage(), pageParame.getLimit());
		IPage<UcasAuthUserInfo> list = ucasAuthUserInfoService.page(page, query);
		return Result.ok().setDatas(list);
	}

	@Override
	public void buildQuery(Map<String, Object> parame, Object query) {
		// TODO Auto-generated method stub
		if(query != null){
			QueryWrapper<UcasAuthUserInfo> queryWrapper = (QueryWrapper<UcasAuthUserInfo>) query;
			if (parame.get("sid") != null) {
				if (StringUtils.isNotBlank(String.valueOf(parame.get("sid")))) {
					queryWrapper.eq("sid", parame.get("sid"));
				}
			}
			if (parame.get("userPin") != null) {
				if (StringUtils.isNotBlank(String.valueOf(parame.get("userPin")))) {
					queryWrapper.like("user_pin", parame.get("userPin"));
				}
			}
			if (parame.get("userName") != null) {
				if (StringUtils.isNotBlank(String.valueOf(parame.get("userName")))) {
					queryWrapper.like("user_name", parame.get("userName"));
				}
			}
			if (parame.get("tel") != null) {
				if (StringUtils.isNotBlank(String.valueOf(parame.get("tel")))) {
					queryWrapper.like("tel", parame.get("tel"));
				}
			}
			if (parame.get("phone") != null) {
				if (StringUtils.isNotBlank(String.valueOf(parame.get("phone")))) {
					queryWrapper.like("phone", parame.get("phone"));
				}
			}
			if (parame.get("email") != null) {
				if (StringUtils.isNotBlank(String.valueOf(parame.get("email")))) {
					queryWrapper.like("email", parame.get("email"));
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
