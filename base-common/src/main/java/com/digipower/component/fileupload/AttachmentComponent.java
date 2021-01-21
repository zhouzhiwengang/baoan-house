package com.digipower.component.fileupload;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.digipower.common.callback.UploadFileCallback;
import com.digipower.common.constants.FileUploadConstant;
import com.digipower.common.entity.ChunkInfoModel;
import com.digipower.common.entity.Result;
import com.digipower.common.uuid.SnowflakeIdGenerator;
import com.digipower.entity.SysEfileInfo;
import com.digipower.entity.SysEfileStore;
import com.digipower.service.SysEfileInfoService;
import com.digipower.service.SysEfileStoreService;


/**
 * 附件上传组件(支持大文件上传)
 * @author zzg
 *
 */
@Component
public class AttachmentComponent {
	// 文件上传线程池
	@Autowired
	private ExecutorService uploadExecutor;
	// 文件上传记录服务
	@Autowired
	private SysEfileInfoService sysEfileInfoService;
	// 文件存储服务
	@Autowired
	private SysEfileStoreService sysEfileStoreService;
	// id 生成器
	@Autowired
	@Lazy
	private SnowflakeIdGenerator idGenerator;
	
	public SysEfileInfo smallAttachUpload(ChunkInfoModel entity, String folder) {
		QueryWrapper<SysEfileStore> queryWrapper = new QueryWrapper<SysEfileStore>();
		queryWrapper.eq("store_code", FileUploadConstant.EFILE_UPLOAD_DIR_PATH);
		Map<String, Object> map = sysEfileStoreService.getMap(queryWrapper);
		

		// 真实路径
		StringBuilder builder = new StringBuilder();
		builder.append(String.valueOf(map.get(FileUploadConstant.ACTUALPATH))).append("/").append(folder).append("/");
		String actualPath = builder.toString();
		// 文件存储唯一标识
		String pathPrefix = String.valueOf(map.get(FileUploadConstant.IDENTIFIER));

		// 系统业务模块业务添加。
		entity.setFileSid(String.valueOf(idGenerator.nextId()));
		UploadFileCallback callback = new UploadFileCallback(entity, "single", actualPath);
		Future<Result> future = uploadExecutor.submit(callback);
		Result result = null;
		try {
			result = future.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Map<String, Object> paramter = (Map<String, Object>) result.getDatas();
		ChunkInfoModel model = (ChunkInfoModel) paramter.get("file");

		SysEfileInfo efile = new SysEfileInfo();
		efile.setSid(String.valueOf(idGenerator.nextId()));
		efile.setFileName(model.getFilename());
		efile.setFilePath(model.getFilePath().replace(String.valueOf(map.get(FileUploadConstant.ACTUALPATH)).replace("/", "\\"), pathPrefix.concat("\\")));
		efile.setFileSid(model.getFileSid());
		efile.setFileType(model.getFilename().substring(model.getFilename().lastIndexOf(".") + 1));
		efile.setIsBinding(FileUploadConstant.UNBOUND);
		efile.setBusinessKey(model.getBusinessKey());
		efile.setCreatedDt(new Date());
		sysEfileInfoService.save(efile);

		return efile;
	}
	
	public Result attachBlockUpload(ChunkInfoModel entity, String folder) {
		QueryWrapper<SysEfileStore> queryWrapper = new QueryWrapper<SysEfileStore>();
		queryWrapper.eq("store_Path", FileUploadConstant.EFILE_UPLOAD_DIR_PATH);
		Map<String, Object> map = sysEfileStoreService.getMap(queryWrapper);
		// 真实路径
		StringBuilder builder = new StringBuilder();
		builder.append(String.valueOf(map.get(FileUploadConstant.ACTUALPATH))).append(folder).append("/");
		String actualPath = builder.toString();

		UploadFileCallback callback = new UploadFileCallback(entity, "upload", actualPath);
		Future<Result> future = uploadExecutor.submit(callback);
		try {
			return future.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Result.error("文件块上传失败");
	}
	
	public SysEfileInfo bigAttachMerge(ChunkInfoModel entity, String folder) {
		QueryWrapper<SysEfileStore> queryWrapper = new QueryWrapper<SysEfileStore>();
		queryWrapper.eq("store_Path", FileUploadConstant.EFILE_UPLOAD_DIR_PATH);
		Map<String, Object> map = sysEfileStoreService.getMap(queryWrapper);
		
		// 真实路径
		StringBuilder builder = new StringBuilder();
		builder.append(map.get(FileUploadConstant.ACTUALPATH)).append(folder).append("/");
		String actualPath = builder.toString();
		// 虚拟路径
		String virtualPath = String.valueOf(map.get(FileUploadConstant.VIRTUALPATH));
		// 文件存储唯一标识
		String pathPrefix = String.valueOf(map.get(FileUploadConstant.IDENTIFIER));

		ChunkInfoModel model = null;
		entity.setFileSid(String.valueOf(idGenerator.nextId()));
		UploadFileCallback callback = new UploadFileCallback(entity, "merge", actualPath);
		Future<Result> future = uploadExecutor.submit(callback);
		try {
			Result result = future.get();
			Map<String, Object> paramtr = (Map<String, Object>) result.getDatas();
			model = (ChunkInfoModel) paramtr.get("file");
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

		SysEfileInfo efile = new SysEfileInfo();
		efile.setFileName(model.getFilename());
		efile.setFilePath(model.getFilePath().replace(String.valueOf(map.get(FileUploadConstant.ACTUALPATH)).replace("/", "\\"), pathPrefix.concat("\\")));
		efile.setFileSid(model.getFileSid());
		efile.setFileType(model.getFilename().substring(model.getFilename().lastIndexOf(".") + 1));
		efile.setIsBinding(FileUploadConstant.UNBOUND);
		efile.setBusinessKey(model.getBusinessKey());
		sysEfileInfoService.save(efile);

		return efile;
	}
	
	public Result breakRenewal(ChunkInfoModel entity, String folder) {
		QueryWrapper<SysEfileStore> queryWrapper = new QueryWrapper<SysEfileStore>();
		queryWrapper.eq("store_Path", FileUploadConstant.EFILE_UPLOAD_DIR_PATH);
		Map<String, Object> map = sysEfileStoreService.getMap(queryWrapper);
		
		// 真实路径
		StringBuilder builder = new StringBuilder();
		builder.append(String.valueOf(map.get(FileUploadConstant.ACTUALPATH))).append(folder).append("/");
		String actualPath = builder.toString();

		File parentFileDir = new File(actualPath + entity.getIdentifier());
		// 已经上传文件块信息
		List<String> numbers = new ArrayList<String>();
		// 已经上传文件大小
		long uploadFileSize = 0;

		// 判断文件目录是否存储
		if (parentFileDir.exists() && parentFileDir.isDirectory()) {
			// 指定文件已经存在,用户已经上传指定文件，但是文件未上传完毕
			File[] files = parentFileDir.listFiles();
			if (files != null && files.length > 0) {
				for (int i = 0; i < files.length; i++) {
					File file = files[i];
					String number = file.getName().split(".part")[0];
					numbers.add(number);
					uploadFileSize = uploadFileSize + file.length();
				}
			}
		}

		return Result.ok().setDatas("numbers", numbers).setDatas("uploadFileSize", uploadFileSize);
	}

}
