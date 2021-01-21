package com.digipower.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
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
import com.digipower.common.util.DesensitizationUtil;
import com.digipower.common.uuid.SnowflakeIdGenerator;
import com.digipower.entity.UcasAuthUserInfo;
import com.digipower.entity.UcasProject;
import com.digipower.sensitive.UcasProjectVoSensitive;
import com.digipower.service.UcasProjectService;
import com.digipower.vo.UcasProjectVo;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/project")
public class UcasProjectController extends AbstractController {
	@Autowired
	private UcasProjectService ucasProjectService;
	@Autowired
	private SnowflakeIdGenerator idGenerator;

	// 增
	@ApiOperation(httpMethod = "POST", value = "工程保存")
	@RequestMapping(value = "/insert", method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public Result insert(@RequestBody UcasProject entity) {
		String sid = entity.getSid();
		if (StringUtils.isEmpty(sid)) {
			sid = String.valueOf(idGenerator.nextId());
			entity.setSid(sid);
		}
		boolean target = ucasProjectService.save(entity);
		if (target) {
			return Result.ok().setDatas("sid", sid);
		} else {
			return Result.error("工程新增失败");
		}
	}

	// 改
	@ApiOperation(httpMethod = "POST", value = "工程修改")
	@RequestMapping(value = "/update", method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public Result update(@RequestBody UcasProject entity) {
		boolean target = ucasProjectService.updateById(entity);
		if (target) {
			return Result.ok();
		} else {
			return Result.error("工程更新失败");
		}
	}

	// 删
	@ApiOperation(httpMethod = "POST", value = "工程删除")
	@RequestMapping(value = "/delete", method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public Result delete(@RequestBody UcasProject entity) {
		String sid = entity.getSid();
		if (StringUtils.isNotEmpty(sid)) {
			ucasProjectService.removeById(sid);
			return Result.ok();
		}
		return Result.error("请求参数缺失Sid");
	}

	// 查
	@ApiOperation(httpMethod = "POST", value = "工程查询指定Sid")
	@RequestMapping(value = "/getSid", method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public Result getUserByName(@RequestBody UcasProject entity) {
		String sid = entity.getSid();
		if (StringUtils.isNotEmpty(sid)) {
			QueryWrapper<UcasProject> query = new QueryWrapper<UcasProject>();
			query.eq("sid", sid);
			return Result.ok().setDatas(ucasProjectService.getOne(query));
		}
		return Result.error("请求参数缺失Sid");

	}

	// 查
	@ApiOperation(httpMethod = "POST", value = "查询符合条件工程")
	@RequestMapping(value = "/getList", method = { RequestMethod.POST })
	@ApiImplicitParams({
			@ApiImplicitParam(name = "itemName", value = "项目名称", required = false, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "itemNo", value = "项目编号", required = false, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "projectName", value = "工程名称", required = false, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "projectNo", value = "工程编号", required = false, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "consPermitNo", value = "施工许可证号", required = false, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "startSendDt", value = "发证开始时间", required = false, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "endSendDt", value = "发证结束时间", required = false, dataType = "String", paramType = "query") })
	public Result getList(@RequestBody Map<String, Object> parame) {
		// 动态构建添加参数
		QueryWrapper<UcasProject> query = new QueryWrapper<UcasProject>();
		this.buildQuery(parame, query);

		List<UcasProject> list = ucasProjectService.list(query);
		return Result.ok().setDatas(list);
	}

	// 查
	@ApiOperation(httpMethod = "POST", value = "查询符合条件工程")
	@RequestMapping(value = "/getListVo", method = { RequestMethod.POST })
	@ApiImplicitParams({
			@ApiImplicitParam(name = "itemName", value = "项目名称", required = false, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "itemNo", value = "项目编号", required = false, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "projectName", value = "工程名称", required = false, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "projectNo", value = "工程编号", required = false, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "consPermitNo", value = "施工许可证号", required = false, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "startSendDt", value = "发证开始时间", required = false, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "endSendDt", value = "发证结束时间", required = false, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "buildName", value = "建设单位名称", required = false, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "consName", value = "施工单位名称", required = false, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "qualitySupervisionName", value = "监理单位名称", required = false, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "reconName", value = "勘察单位名称", required = false, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "designName", value = "设计单位名称", required = false, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "yearSerialNumber", value = "年度流水号", required = false, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "itemType", value = "项目类型", required = false, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "sendPackageType", value = "发包类型", required = false, dataType = "String", paramType = "query")
	})
	public Result getListVo(@RequestBody Map<String, Object> parame) {
		List<UcasProjectVo> list = ucasProjectService.selectList(parame);
		if(CollectionUtils.isNotEmpty(list)){
			UcasProjectVoSensitive.ucasProjectVoSensitive(list);			
		}
		return Result.ok().setDatas(list);
	}

	// 查
	@ApiOperation(httpMethod = "POST", value = "基于分页查询符合条件工程")
	@RequestMapping(value = "/getPage", method = { RequestMethod.POST })
	@ApiImplicitParams({
			@ApiImplicitParam(name = "itemName", value = "项目名称", required = false, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "itemNo", value = "项目编号", required = false, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "projectName", value = "工程名称", required = false, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "projectNo", value = "工程编号", required = false, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "consPermitNo", value = "施工许可证号", required = false, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "startSendDt", value = "发证开始时间", required = false, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "endSendDt", value = "发证结束时间", required = false, dataType = "String", paramType = "query")
			
	})
	public Result getPage(@RequestBody Map<String, Object> parame) {
		// 动态构建添加参数
		QueryWrapper<UcasProject> query = new QueryWrapper<UcasProject>();
		this.buildQuery(parame, query);

		PageParame pageParame = this.initPageBounds(parame);
		Page<UcasProject> page = new Page<UcasProject>(pageParame.getPage(), pageParame.getLimit());
		IPage<UcasProject> list = ucasProjectService.page(page, query);
		return Result.ok().setDatas(list);
	}

	// 查
	@ApiOperation(httpMethod = "POST", value = "查询符合条件工程")
	@RequestMapping(value = "/getPageVo", method = { RequestMethod.POST })
	@ApiImplicitParams({
			@ApiImplicitParam(name = "itemName", value = "项目名称", required = false, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "itemNo", value = "项目编号", required = false, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "projectName", value = "工程名称", required = false, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "projectNo", value = "工程编号", required = false, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "consPermitNo", value = "施工许可证号", required = false, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "startSendDt", value = "发证开始时间", required = false, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "endSendDt", value = "发证结束时间", required = false, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "buildName", value = "建设单位名称", required = false, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "consName", value = "施工单位名称", required = false, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "qualitySupervisionName", value = "监理单位名称", required = false, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "reconName", value = "勘察单位名称", required = false, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "designName", value = "设计单位名称", required = false, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "yearSerialNumber", value = "年度流水号", required = false, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "itemType", value = "项目类型", required = false, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "sendPackageType", value = "发包类型", required = false, dataType = "String", paramType = "query")
			
	})
	public Result getPageVo(@RequestBody Map<String, Object> parame) {
		PageParame pageParame = this.initPageBounds(parame);
		Page<UcasProjectVo> page = new Page<UcasProjectVo>(pageParame.getPage(), pageParame.getLimit());

		IPage<UcasProjectVo> list = ucasProjectService.selectPage(page, parame);
		if(CollectionUtils.isNotEmpty(list.getRecords())){
			UcasProjectVoSensitive.ucasProjectVoSensitive(list.getRecords());
		}
		return Result.ok().setDatas(list);
	}

	@Override
	public void buildQuery(Map<String, Object> param, Object query) {
		// TODO Auto-generated method stub
		if (query != null) {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			QueryWrapper<UcasProject> queryWrapper = (QueryWrapper<UcasProject>) query;
			if (param.get("itemName") != null) {
				if (StringUtils.isNotBlank(String.valueOf(param.get("itemName")))) {
					queryWrapper.like("item_name", param.get("itemName"));
				}
			}
			if (param.get("itemNo") != null) {
				if (StringUtils.isNotBlank(String.valueOf(param.get("itemNo")))) {
					queryWrapper.like("item_no", param.get("itemNo"));
				}
			}
			if (param.get("projectName") != null) {
				if (StringUtils.isNotBlank(String.valueOf(param.get("projectName")))) {
					queryWrapper.like("project_name", param.get("projectName"));
				}
			}
			if (param.get("projectNo") != null) {
				if (StringUtils.isNotBlank(String.valueOf(param.get("projectNo")))) {
					queryWrapper.like("project_no", param.get("projectNo"));
				}
			}
			if (param.get("consPermitNo") != null) {
				if (StringUtils.isNotBlank(String.valueOf(param.get("consPermitNo")))) {
					queryWrapper.like("cons_permit_no", param.get("consPermitNo"));
				}
			}
			if (param.get("startSendDt") != null) {
				if (StringUtils.isNotBlank(String.valueOf(param.get("startSendDt")))) {
					try {
						// 大于
						queryWrapper.ge("send_dt", format.parse(String.valueOf(param.get("startSendDt"))));
					} catch (ParseException e) {

					}
				}
			}
			if (param.get("endSendDt") != null) {
				if (StringUtils.isNotBlank(String.valueOf(param.get("endSendDt")))) {
					try {
						// 小于
						queryWrapper.le("send_dt", format.parse(String.valueOf(param.get("endSendDt"))));
					} catch (ParseException e) {

					}
				}
			}
		}
	}

}
