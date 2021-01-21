package com.digipower.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@SuppressWarnings("serial")
@TableName(value = "ucas_unit")
@Data
public class UcasUnit implements java.io.Serializable {
    private String sid;

    private String unitName;

    private String unitType;

    private String unitLegalPerson;

    private String unitLegalPersonIdcard;

    private String unitAdd;

    private String unitContactPerson;

    private String unitContactTel;

    private String unitQualificationLevel;
    
    private String principalName;
    
    private String principalIdcard;
    
    private String principalTel;
    
    private String qualiteManagerName;
    
    private String qualiteManagerTel;
    
    private String safeManagerName;
    
    private String safeManagerTel;
    
    private String projectManagerName;
    
    private String projectManagerCertificate;
    
    private String projectManagerLevel;
}