package com.digipower.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@SuppressWarnings("serial")
@TableName(value = "ucas_auth_user")
@Data
public class UcasAuthUserInfo implements java.io.Serializable {
	private String sid;

	@NotBlank(message="登入账户不能为空")
	@Length(min=1, max=21, message="登入账户名称过长")
	private String userPin;

	@NotBlank(message="名称不能为空")
	@Length(min=1, max=21, message="名称过长")
	private String userName;

	@NotBlank(message="密码不能为空")
	private String password;

	private String gender;

	private String tel;

	private String phone;

	private String email;

	@NotBlank(message="状态不能为空")
	private String state;

	private String createdBy;

	private Date createdDt;

	private Integer version;

	private String updatedBy;

	private Date updatedDt;

	private String zoneOrgCode;
	
	private String organiztionSid;

	private String value1;

	private String value2;

	private String value3;

	private Integer deleteFlag;

	private String sessionId;

	// 补全字段(用户类别:系统管理员\安全保密管理员\安全审计员\普通用户)
	private String userCategory;

	private String uniqueSid;

}
