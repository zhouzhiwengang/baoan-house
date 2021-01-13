package com.digipower.common.entity;

import java.io.Serializable;
import org.springframework.web.multipart.MultipartFile;

@SuppressWarnings("serial")
public class ChunkInfoModel implements Serializable {
	// 文件块sid
	private String sid;

	// 文件块编号
	private Integer number;

	// 默认文件块大小
	private Long size;

	// 实际文件块大小
	private Long currentSize;

	// 文件总大小
	private Long totalSize;

	// 文件标识符
	private String identifier;

	// 文件名称
	private String filename;

	// 文件流水号
	private String fileSid;

	// 文件路径
	private String filePath;

	// 文件类型
	private String type;

	// 文件块总数
	private Integer totalChunks;

	// 上传文件
	private MultipartFile file;

	// 上传业务key 值
	private String businessKey;

	// base64 文件上传
	private String base64;

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public Long getCurrentSize() {
		return currentSize;
	}

	public void setCurrentSize(Long currentSize) {
		this.currentSize = currentSize;
	}

	public Long getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(Long totalSize) {
		this.totalSize = totalSize;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFileSid() {
		return fileSid;
	}

	public void setFileSid(String fileSid) {
		this.fileSid = fileSid;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getTotalChunks() {
		return totalChunks;
	}

	public void setTotalChunks(Integer totalChunks) {
		this.totalChunks = totalChunks;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public String getBusinessKey() {
		return businessKey;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	public String getBase64() {
		return base64;
	}

	public void setBase64(String base64) {
		this.base64 = base64;
	}
	
	

}
