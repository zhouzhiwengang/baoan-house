package com.digipower.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;


import lombok.Data;

@SuppressWarnings("serial")
@TableName(value = "ucas_auth_user_r2_privilege")
@Data
public class UcasAuthUserRelPrivilegeInfo implements Serializable {
	private String sid;

    private String userSid;
   
    private String privilegeSid;
  
    private String createdBy;
   
    private Date createdDt;
   
    private Integer version;
   
    private String updatedBy;
   
    private Date updatedDt;
}
