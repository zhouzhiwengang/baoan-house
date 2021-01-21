package com.digipower.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@SuppressWarnings("serial")
@TableName(value = "sys_log_record")
@Data
public class SysLogRecord implements Serializable {
    private String sid;

    private Date createDt;

    private String state;

    private Date recordDt;

    private String consPermitNo;

    private String completedProjectNumber;

    private String itemNo;

    private String itemName;

    private String projectNo;

    private String projectName;

    private String completedProjectName;
}