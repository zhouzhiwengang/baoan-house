package com.digipower.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@SuppressWarnings("serial")
@TableName(value = "sys_verification_code")
@Data
public class SysVerificationCode implements Serializable {
	private String sid;

    private String code;

    private Date createdDt;
}
