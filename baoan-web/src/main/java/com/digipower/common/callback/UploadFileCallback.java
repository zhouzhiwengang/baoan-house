package com.digipower.common.callback;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.concurrent.Callable;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import com.digipower.common.entity.ChunkInfoModel;
import com.digipower.common.entity.Result;
import com.digipower.common.util.UploadFileUtil;


@SuppressWarnings("rawtypes")
public class UploadFileCallback implements Callable{
	
	// 文件上传实体对象
		private ChunkInfoModel model;
		// 文件操作类型
		private String type;
		// 文件根路径
		private String location;

		// set 和 get 方法
		public ChunkInfoModel getModel() {
			return model;
		}

		public void setModel(ChunkInfoModel model) {
			this.model = model;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getLocation() {
			return location;
		}

		public void setLocation(String location) {
			this.location = location;
		}

		// 构造函数
		public UploadFileCallback(ChunkInfoModel model, String type, String localtion) {
			super();
			this.model = model;
			this.type = type;
			this.location = localtion;
		}
		
		public String suffix(String character){
			if(character.indexOf("data:image/jpg;") != -1){
				return "jpg";
			} else if(character.indexOf("data:image/png;") != -1){
				return "png";
			} else if(character.indexOf("data:image/jpeg;") != -1){
				return "jpeg";
			}
			return "jpg";
		}
		
		public String clearnHead(String character){
			if(character.indexOf("data:image/jpg;") != -1){
				return character.replace("data:image/jpg;base64,", "");
			} else if(character.indexOf("data:image/png;") != -1){
				return character.replace("data:image/png;base64,", "");
			} else if(character.indexOf("data:image/jpeg;") != -1){
				return character.replace("data:image/jpeg;base64,", "");
			}
			return character;
		}
		
	
		// 核心功能方法
		@Override
		public Object call() throws Exception {
			// base64 
			if(this.type.equalsIgnoreCase("base64")){
				
				String base64 = this.model.getBase64();
				if(StringUtils.isNotEmpty(base64)){
					String suffix = this.suffix(base64);
					String fileContent = this.clearnHead(base64);
					byte[] fileBytes = Base64.getDecoder().decode(fileContent);
					InputStream inputStream = new ByteArrayInputStream(fileBytes);
					File destTempFile = new File(this.location, this.model.getFileSid() + "." + suffix);
					try {
						FileUtils.copyInputStreamToFile(inputStream, destTempFile);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						// 移除错误文件块
						destTempFile.delete();
						return Result.error("文件上传异常").setDatas("file", this.model);
					}
					this.model.setFile(null);
					// 获取文件相对路径
					this.model.setFilePath(destTempFile.getAbsolutePath());
					// 获取文件名称
					this.model.setFilename(destTempFile.getName());
					return Result.ok("文件上传成功").setDatas("file", this.model);
				}
			}
			// 附件上传
			if (this.type.equalsIgnoreCase("single")) {
				File destTempFile = new File(this.location, this.model.getFileSid() + "." + getSuffix(this.model.getFilename()));
				try {
					FileUtils.copyInputStreamToFile(this.model.getFile().getInputStream(), destTempFile);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					// 移除错误文件块
					destTempFile.delete();
					// 打印堆栈信息
					e.printStackTrace();

					return Result.error("文件上传异常").setDatas("file", this.model);
				}
				// 校验文件是否上传成功
				long size = FileUtils.sizeOf(destTempFile);
				boolean target = this.model.getCurrentSize().equals(size);
				if (target) {
					this.model.setFile(null);
					// 获取文件相对路径
					this.model.setFilePath(destTempFile.getAbsolutePath());
					return Result.ok("文件上传成功").setDatas("file", this.model);
				} else {
					this.model.setFile(null);
					// 移除错误文件块
					destTempFile.delete();
					return Result.error("文件上传异常").setDatas("file", this.model);
				}
			}
			if (this.type.equalsIgnoreCase("upload")) {
				// 临时目录用来存放所有分片文件
				String tempFileDir = this.location + this.model.getIdentifier();
				File parentFileDir = new File(tempFileDir);
				if (!parentFileDir.exists()) {
					parentFileDir.mkdirs();
				}
				// 分片处理时，前台会多次调用上传接口，每次都会上传文件的一部分到后台
				File tempPartFile = new File(parentFileDir, this.model.getNumber() + ".part");
				try {
					FileUtils.copyInputStreamToFile(this.model.getFile().getInputStream(), tempPartFile);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					// 移除错误文件块
					tempPartFile.delete();
					// 打印堆栈信息
					e.printStackTrace();

					return Result.error("文件块上传异常").setDatas("chunk", this.model);
				}

				// 校验文件是否上传成功
				long size = FileUtils.sizeOf(tempPartFile);
				boolean target = this.model.getCurrentSize().equals(size);
				if (target) {
					this.model.setFile(null);
					return Result.ok("文件块上传成功").setDatas("chunk", this.model);
				} else {
					// 移除错误文件块
					tempPartFile.delete();
					this.model.setFile(null);
					return Result.error("文件块上传异常").setDatas("chunk", this.model);
				}

			}
			if (this.type.equalsIgnoreCase("merge")) {
				File parentFileDir = new File(this.location + this.model.getIdentifier());
				if (parentFileDir.isDirectory()) {
					File destTempFile = new File(this.location, this.model.getFileSid() + "." + getSuffix(this.model.getFilename()));
					if (!destTempFile.exists()) {
						if (!destTempFile.getParentFile().exists()) {
							// 先得到文件的上级目录，并创建上级目录，在创建文件,
							destTempFile.getParentFile().mkdir();
						}
						try {
							// 创建文件
							destTempFile.createNewFile(); // 上级目录没有创建，这里会报错
						} catch (IOException e) {
							// 输出堆栈信息
							e.printStackTrace();
							return Result.error("文件合并异常").setDatas("file", this.model);
						}
					}
					for (int i = 0; i < parentFileDir.listFiles().length; i++) {
						File partFile = new File(parentFileDir, i + ".part");
						FileOutputStream destTempfos = new FileOutputStream(destTempFile, true);
						// 遍历"所有分片文件"到"最终文件"中
						FileUtils.copyFile(partFile, destTempfos);
						destTempfos.close();
					}
					// 删除临时目录中的分片文件
					FileUtils.deleteDirectory(parentFileDir);

					// 校验文件是否完整
					String marker = this.model.getIdentifier();
					String md5 = UploadFileUtil.calcMD5(new File(
							this.location + this.model.getFileSid() + "." + getSuffix(this.model.getFilename())));
					if (md5.equalsIgnoreCase(marker)) {
						// 获取文件相对路径
						this.model.setFilePath(destTempFile.getAbsolutePath());
						return Result.ok("文件上传成功").setDatas("file", this.model);
					} else {
						if (destTempFile.exists()) {
							// 移除合并文件
							destTempFile.delete();
						}
						return Result.error("文件上传异常").setDatas("file", this.model);
					}
				}
			}
			return Result.error("文件无法处理");
		}

		/**
		 * 获取文件名的后缀类型
		 * 
		 * @param fileName
		 * @return
		 */
		public String getSuffix(String fileName) {
			return fileName.substring(fileName.lastIndexOf(".") + 1);
		}
		
		/**
		 * 获取文件名的前缀
		 * 
		 */
		public String getPrefix(String fileName) {
			return fileName.substring(0, fileName.lastIndexOf(".") -1);
		}

}
