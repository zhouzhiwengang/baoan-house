package com.digipower.controller;

import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.digipower.common.entity.ChunkInfoModel;
import com.digipower.common.entity.Result;
import com.digipower.component.fileupload.AttachmentComponent;
import com.digipower.entity.SysEfileInfo;
import com.digipower.service.SysEfileInfoService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;


@RestController
@RequestMapping("/api/file")
public class FileController {
	// 日志记录
	public static final Logger logger = LoggerFactory.getLogger(FileController.class);
	
	@Autowired
	private AttachmentComponent upload;
	@Autowired
	private SysEfileInfoService sysEfileInfoService;
	
	/**
	 * 通用文件上传功能; 备注：文件大小<=30M,如果超出规定文件大小,建议采用大文件上传
	 * @param entity
	 * @return
	 */
	@RequestMapping(value = "/fileUpload", method = { RequestMethod.POST })
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "文件上传(小于等于30M)")
	public Result upload(ChunkInfoModel entity) {
		if (logger.isDebugEnabled()) {
			logger.debug(entity.toString());
		}
		SysEfileInfo model = upload.smallAttachUpload(entity, "default");
		return Result.ok("文件上传成功").setDatas("model", model);
	}
	
	/**
	 * 通用文件删除功能
	 * @param entity
	 * @return
	 */
	@RequestMapping(value = "/fileDelete", method = { RequestMethod.POST })
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "附件删除")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "efileSid", value = "efileSid", required = false, dataType = "String", paramType = "query")
	})
	public Result fileDelete(@RequestBody @ApiParam(name = "JSONObject对象", value = "json格式对象", required = true) Map<String, Object> parameter) {
		if (logger.isDebugEnabled()) {
			logger.debug(String.valueOf(parameter.get("sid")));
		}
		if (parameter.get("sid") != null) {
			String sid = String.valueOf(parameter.get("sid"));
			if (StringUtils.isNotBlank(sid)) {
				sysEfileInfoService.removeById(sid);
				return Result.ok("文件删除成功").setDatas("efileSid", sid);
			}
		}
		return Result.error("请求参数sid缺失");
		
	}
	
	
	/**
	 * 通用大文件分块上传; 设计思路：大文件按照指定文件大小分割成大小统一的文件块,文件块生成的规则是按数字递增生成创建。
	 * @param entity
	 * @return
	 */
	@ApiOperation(httpMethod = "POST", value = "文件块上传")
	@RequestMapping(value = "/fileBlockUpload", method = { RequestMethod.POST })
	@ResponseBody
	public Result blockUpload(ChunkInfoModel entity) {
		if (logger.isDebugEnabled()) {
			logger.debug(entity.toString());
		}
		return upload.attachBlockUpload(entity, "bigFile");
		
	}

	/**
	 * 通用大文件分块合并; 设计思路:文件块按照数字递增的顺序合并为大文件
	 * @param entity
	 * @return
	 */
	@ApiOperation(httpMethod = "POST", value = "文件块合并")
	@RequestMapping(value = "/fileMerge", method = { RequestMethod.POST })
	@ResponseBody
	public Result merge(ChunkInfoModel entity) {
		if (logger.isDebugEnabled()) {
			logger.debug(entity.toString());
		}
		SysEfileInfo model = upload.bigAttachMerge(entity, "bigFile");;
		
		return Result.ok("文件合并成功").setDatas("model", model);
	}
	
	/**
	 * 通用大文件 暂停/重新上传功能; 设计思路：返回大文件已经正常上传完成的文件块信息。
	 * @param entity
	 * @return
	 */
	@ApiOperation(httpMethod = "POST", value = "文件上传记录")
	@RequestMapping(value = "/find", method = { RequestMethod.POST })
	@ResponseBody
	public Result find(ChunkInfoModel entity) {
		if (logger.isDebugEnabled()) {
			logger.debug(entity.toString());
		}
		return upload.breakRenewal(entity, "bigFile");
	}
}
