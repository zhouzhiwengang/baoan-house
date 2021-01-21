package com.digipower.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@SuppressWarnings("serial")
@TableName(value = "sys_efile_info")
@Data
public class SysEfileInfo implements Serializable {
	private String sid;

	private String fileSid;

	private String fileName;

	private String filePath;

	private String fileType;

	private Long fileSize;

	private Integer fileSort;

	private String fileDesc;

	private String isBinding;

	private String businessKey;

	private Date createdDt;

}
