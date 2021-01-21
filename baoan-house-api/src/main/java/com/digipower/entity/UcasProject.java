package com.digipower.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@SuppressWarnings("serial")
@TableName(value = "ucas_project")
@Data
public class UcasProject implements java.io.Serializable {
	private String sid;

    private String itemNo;

    private String itemName;

    private String projectNo;

    private String projectName;

    private String projectCategory;

    private String projectAddress;

    private String projectLocation;

    private String planPrice;

    private String longitude;

    private String latitude;

    private String winnerPrice;

    private String contractPrice;

    private String consPermitNo;

    private String planPermitNo;

    private String planProjectNo;

    private Date contractStartDt;

    private Date contractEndDt;

    private String architectureArea;

    private String consRange;

    private Date sendDt;

    private Date consReportDt;

    private String tenderer;

    private String yearSerialNumber;

    private String consReportType;

    private String sendPackageType;
    
    private String consUnitSid;
    
    private String buildUnitSid;
    
    private String qualitySupervisionUnitSid;
    
    private String reconUnitSid;
    
    private String designUnitSid;
    
    private String completedProjectName;
    
    private String completedProjectNumber;
    
    private String completedProjectPerson;
    
    private String completedProjectPersonTel;
    
    private String buildUnitManager;
    
    private Date recordDt;
    
    private String reviewer;
}
