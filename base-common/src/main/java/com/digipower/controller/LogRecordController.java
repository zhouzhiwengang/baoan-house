package com.digipower.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.digipower.entity.SysLogRecord;
import com.digipower.service.SysLogRecordService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/log/record")
public class LogRecordController extends AbstractController {
	@Autowired
	private SysLogRecordService sysLogRecordService;
	@Autowired
	private SnowflakeIdGenerator idGenerator;

	// 增
	@ApiOperation(httpMethod = "POST", value = "催缴日志保存")
	@RequestMapping(value = "/insert", method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public Result insert(@RequestBody SysLogRecord entity) {
		String sid = entity.getSid();
		if (StringUtils.isEmpty(sid)) {
			sid = String.valueOf(idGenerator.nextId());
			entity.setSid(sid);
		}
		boolean target = sysLogRecordService.save(entity);
		if (target) {
			return Result.ok().setDatas("sid", sid);
		} else {
			return Result.error("催缴日志失败");
		}
	}

	// 改
	@ApiOperation(httpMethod = "POST", value = "催缴日志修改")
	@RequestMapping(value = "/update", method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public Result update(@RequestBody SysLogRecord entity) {
		boolean target = sysLogRecordService.updateById(entity);
		if (target) {
			return Result.ok();
		} else {
			return Result.error("催缴日志更新失败");
		}
	}

	// 删
	@ApiOperation(httpMethod = "POST", value = "催缴日志删除")
	@RequestMapping(value = "/delete", method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public Result delete(@RequestBody SysLogRecord entity) {
		String sid = entity.getSid();
		if (StringUtils.isNotEmpty(sid)) {
			sysLogRecordService.removeById(sid);
			return Result.ok();
		}
		return Result.error("请求参数缺失Sid");
	}

	// 查
	@ApiOperation(httpMethod = "POST", value = "用户查询指定催缴日志Sid")
	@RequestMapping(value = "/getSid", method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public Result getUserByName(@RequestBody SysLogRecord entity) {
		String sid = entity.getSid();
		if (StringUtils.isNotEmpty(sid)) {
			QueryWrapper<SysLogRecord> query = new QueryWrapper<SysLogRecord>();
			query.eq("sid", sid);
			return Result.ok().setDatas(sysLogRecordService.getOne(query));
		}
		return Result.error("请求参数缺失Sid");

	}

	// 查
	@ApiOperation(httpMethod = "POST", value = "查询符合条件催缴日志记录")
	@RequestMapping(value = "/getList", method = { RequestMethod.POST })
	@ApiImplicitParams({
			@ApiImplicitParam(name = "state", value = "同步状态: 1=未同步(默认值)、2=已同步", required = false, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "consPermitNo", value = "施工许可证号", required = false, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "completedProjectNumber", value = "竣工验收备案号", required = false, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "itemNo", value = "项目编号", required = false, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "itemName", value = "项目名称", required = false, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "projectNo", value = "工程编号", required = false, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "projectName", value = "施工工程名称", required = false, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "completedProjectName", value = "竣工工程名称", required = false, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "startDt", value = "备案日期起", required = false, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "endDt", value = "备案日期止", required = false, dataType = "String", paramType = "query") })
	public Result getList(@RequestBody Map<String, Object> parame) {
		// 动态构建添加参数
		QueryWrapper<SysLogRecord> query = new QueryWrapper<SysLogRecord>();
		this.buildQuery(parame, query);

		List<SysLogRecord> list = sysLogRecordService.list(query);
		return Result.ok().setDatas(list);
	}

	// 查
	@ApiOperation(httpMethod = "POST", value = "基于分页查询符合条件催缴日志记录")
	@RequestMapping(value = "/getPage", method = { RequestMethod.POST })
	@ApiImplicitParams({
			@ApiImplicitParam(name = "state", value = "同步状态: 1=未同步(默认值)、2=已同步", required = false, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "consPermitNo", value = "施工许可证号", required = false, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "completedProjectNumber", value = "竣工验收备案号", required = false, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "itemNo", value = "项目编号", required = false, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "itemName", value = "项目名称", required = false, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "projectNo", value = "工程编号", required = false, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "projectName", value = "施工工程名称", required = false, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "completedProjectName", value = "竣工工程名称", required = false, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "startDt", value = "备案日期起", required = false, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "endDt", value = "备案日期止", required = false, dataType = "String", paramType = "query") })
	public Result getPage(@RequestBody Map<String, Object> parame) {
		// 动态构建添加参数
		QueryWrapper<SysLogRecord> query = new QueryWrapper<SysLogRecord>();
		this.buildQuery(parame, query);

		PageParame pageParame = this.initPageBounds(parame);
		Page<SysLogRecord> page = new Page<SysLogRecord>(pageParame.getPage(), pageParame.getLimit());
		IPage<SysLogRecord> list = sysLogRecordService.page(page, query);
		return Result.ok().setDatas(list);
	}

	@Override
	public void buildQuery(Map<String, Object> param, Object query) {
		if (query != null) {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			QueryWrapper<SysLogRecord> queryWrapper = (QueryWrapper<SysLogRecord>) query;
			if (param.get("state") != null) {
				if (StringUtils.isNotBlank(String.valueOf(param.get("state")))) {
					queryWrapper.eq("state", param.get("state"));
				}
			}
			if (param.get("consPermitNo") != null) {
				if (StringUtils.isNotBlank(String.valueOf(param.get("consPermitNo")))) {
					queryWrapper.like("cons_permit_no", param.get("consPermitNo"));
				}
			}
			if (param.get("completedProjectNumber") != null) {
				if (StringUtils.isNotBlank(String.valueOf(param.get("completedProjectNumber")))) {
					queryWrapper.like("completed_project_number", param.get("completedProjectNumber"));
				}
			}
			if (param.get("itemNo") != null) {
				if (StringUtils.isNotBlank(String.valueOf(param.get("itemNo")))) {
					queryWrapper.like("item_no", param.get("itemNo"));
				}
			}
			if (param.get("itemName") != null) {
				if (StringUtils.isNotBlank(String.valueOf(param.get("itemName")))) {
					queryWrapper.like("item_name", param.get("itemName"));
				}
			}
			if (param.get("projectNo") != null) {
				if (StringUtils.isNotBlank(String.valueOf(param.get("projectNo")))) {
					queryWrapper.like("project_no", param.get("projectNo"));
				}
			}
			if (param.get("projectName") != null) {
				if (StringUtils.isNotBlank(String.valueOf(param.get("projectName")))) {
					queryWrapper.like("project_name", param.get("projectName"));
				}
			}
			if (param.get("completedProjectName") != null) {
				if (StringUtils.isNotBlank(String.valueOf(param.get("completedProjectName")))) {
					queryWrapper.like("completed_project_name", param.get("completedProjectName"));
				}
			}
			if (param.get("startDt") != null) {
				if (StringUtils.isNotBlank(String.valueOf(param.get("startDt")))) {
					try {
						// 大于
						queryWrapper.ge("record_dt", format.parse(String.valueOf(param.get("startDt"))));
					} catch (ParseException e) {

					}
				}
			}
			if (param.get("endDt") != null) {
				if (StringUtils.isNotBlank(String.valueOf(param.get("endDt")))) {
					try {
						// 小于
						queryWrapper.le("record_dt", format.parse(String.valueOf(param.get("endDt"))));
					} catch (ParseException e) {

					}
				}
			}

		}

	}

}
