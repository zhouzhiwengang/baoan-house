package com.digipower.sensitive;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.digipower.common.util.DesensitizationUtil;
import com.digipower.entity.UcasUnit;
import com.digipower.vo.UcasProjectVo;

/**
 * 工程类数据脱敏类
 * @author zzg
 *
 */
public class UcasProjectVoSensitive {
	
	public static void ucasProjectVoSensitive(List<UcasProjectVo> list){
		if(CollectionUtils.isNotEmpty(list)){
			list.stream().forEach(item->{
				if(StringUtils.isNotEmpty(item.getTenderer())){
					item.setTenderer(DesensitizationUtil.nameSensitive(item.getTenderer()));
				}
				if(StringUtils.isNotEmpty(item.getCompletedProjectPerson())){
					item.setCompletedProjectPerson(DesensitizationUtil.nameSensitive(item.getCompletedProjectPerson()));
				}
				if(StringUtils.isNotEmpty(item.getCompletedProjectPersonTel())){
					item.setCompletedProjectPersonTel(DesensitizationUtil.mobilePhoneSensitive(item.getCompletedProjectPersonTel()));
				}
				if(StringUtils.isNotEmpty(item.getBuildUnitManager())){
					item.setBuildUnitManager(DesensitizationUtil.nameSensitive(item.getBuildUnitManager()));
				}
				if(StringUtils.isNotEmpty(item.getReviewer())){
					item.setReviewer(DesensitizationUtil.nameSensitive(item.getReviewer()));
				}
				if(item.getConsUnit() != null){
					ucasUnitSensitive(item.getConsUnit());
				}
				if(item.getBuildUnit() != null){
					ucasUnitSensitive(item.getBuildUnit());
				}
				if(item.getQualitySupervisionUnit() != null){
					ucasUnitSensitive(item.getQualitySupervisionUnit());
				}
				if(item.getReconUnit() != null){
					ucasUnitSensitive(item.getReconUnit());
				}
				if(item.getDesignUnit() != null){
					ucasUnitSensitive(item.getDesignUnit());
				}
			});
		}
	}
	
	public static void ucasUnitSensitive(UcasUnit ucasUnit){
		if(StringUtils.isNotEmpty(ucasUnit.getUnitLegalPerson())){
			ucasUnit.setUnitLegalPerson(DesensitizationUtil.nameSensitive(ucasUnit.getUnitLegalPerson()));
		}
		if(StringUtils.isNotEmpty(ucasUnit.getUnitLegalPersonIdcard())){
			ucasUnit.setUnitLegalPersonIdcard(DesensitizationUtil.iDCardSensitive(ucasUnit.getUnitLegalPersonIdcard()));
		}
		if(StringUtils.isNotEmpty(ucasUnit.getUnitContactPerson())){
			ucasUnit.setUnitContactPerson(DesensitizationUtil.nameSensitive(ucasUnit.getUnitContactPerson()));
		}
		if(StringUtils.isNotEmpty(ucasUnit.getUnitContactTel())){
			ucasUnit.setUnitContactTel(DesensitizationUtil.mobilePhoneSensitive(ucasUnit.getUnitContactTel()));
		}
		if(StringUtils.isNotEmpty(ucasUnit.getPrincipalName())){
			ucasUnit.setPrincipalName(DesensitizationUtil.nameSensitive(ucasUnit.getPrincipalName()));
		}
		if(StringUtils.isNotEmpty(ucasUnit.getPrincipalTel())){
			ucasUnit.setPrincipalTel(DesensitizationUtil.mobilePhoneSensitive(ucasUnit.getPrincipalTel()));
		}
		if(StringUtils.isNotEmpty(ucasUnit.getPrincipalIdcard())){
			ucasUnit.setPrincipalIdcard(DesensitizationUtil.iDCardSensitive(ucasUnit.getPrincipalIdcard()));
		}
		if(StringUtils.isNotEmpty(ucasUnit.getQualiteManagerName())){
			ucasUnit.setQualiteManagerName(DesensitizationUtil.nameSensitive(ucasUnit.getQualiteManagerName()));
		}
		if(StringUtils.isNotEmpty(ucasUnit.getQualiteManagerTel())){
			ucasUnit.setQualiteManagerTel(DesensitizationUtil.mobilePhoneSensitive(ucasUnit.getQualiteManagerTel()));
		}
		if(StringUtils.isNotEmpty(ucasUnit.getSafeManagerName())){
			ucasUnit.setSafeManagerName(DesensitizationUtil.nameSensitive(ucasUnit.getSafeManagerName()));
		}
		if(StringUtils.isNotEmpty(ucasUnit.getSafeManagerTel())){
			ucasUnit.setSafeManagerTel(DesensitizationUtil.mobilePhoneSensitive(ucasUnit.getSafeManagerTel()));
		}
		if(StringUtils.isNotEmpty(ucasUnit.getProjectManagerName())){
			ucasUnit.setProjectManagerName(DesensitizationUtil.nameSensitive(ucasUnit.getProjectManagerName()));
		}
	}

}
