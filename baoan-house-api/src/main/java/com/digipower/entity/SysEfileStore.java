package com.digipower.entity;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@SuppressWarnings("serial")
@TableName(value = "sys_efile_store")
@Data
public class SysEfileStore implements Serializable {
	private String sid;

    private String storeName;

    private String storeCode;
    
    @NotBlank(message="存储路径不能为空")
   	@Length(min = 1, max = 32, message="存储路径超长")
    private String storePath;

    private String storeDesc;

    private String isVirtual;

    private String createdBy;

    private Date createdDt;

    private String updatedBy;

    private Date updatedDt;
    
    // 补全字段
    private String psid;
    
    private Integer stateType;
    
    private String storeIdentifier;
}
