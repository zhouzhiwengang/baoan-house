package com.digipower.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@SuppressWarnings("serial")
@TableName(value = "ucas_auth_privilege")
@Data
public class UcasAuthPrivilegeInfo implements java.io.Serializable {
	private String sid;
	
	@NotBlank(message="目录名称不能为空")
	@Length(min = 1, max = 32, message="目录名称超长")
    private String privilegeName;

	@NotBlank(message="目录编码不能为空")
	@Length(min = 1, max = 32, message="目录编码超长")
    private String privilegeCode;

    private String psid;

    private String url;

    private Integer orderRank;

    private String privilegeType;

    private String privilegeDescription;

    private String state;

    private String createdBy;

    private Date createdDt;

    private Integer version;

    private String updatedBy;

    private Date updatedDt;

    private String zoneOrgCode;

    private String iconName;

    private String value1;

    private String value2;

    private String value3;

    private Integer depth;

    private Integer deleteFlag;

}
