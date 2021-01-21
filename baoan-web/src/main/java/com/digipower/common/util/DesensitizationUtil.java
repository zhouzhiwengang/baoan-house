package com.digipower.common.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 数据脱敏工具类
 * @author zzg
 *
 */
public class DesensitizationUtil {
	private static final int RIGHT=10;
    private static final int LEFT=6;
	/**
	 * 收货地址数据脱敏
	 * 地址只显示到地区，不显示详细地址；我们要对个人信息增强保护
	 * 例子：北京市海淀区****
	 * @param src
	 * @return
	 */
	public static String addressSensitive(Object src){
		if(src==null){
            return null;
        }
        String address = src.toString();
        int length = StringUtils.length(address);
        if(length>RIGHT+LEFT){
            return StringUtils.rightPad(StringUtils.left(address, length-RIGHT), length, "*");
        }
        if(length<=LEFT){
            return address;
        }else{
            return address.substring(0,LEFT+1).concat("*****");
        }
	}
	
	/**
	 * 银行卡号数据脱敏
	 * 只留前四位和后四位
	 * 6227 0383 3938 3938 393 脱敏结果: 6227 **** **** ***8 393
	 * @param src
	 * @return
	 */
	public static String bandCardSensitive(Object src){
		if(src==null){
            return null;
        }
        String bankCard = src.toString();
        return StringUtils.left(bankCard, 4).concat(StringUtils.removeStart(StringUtils.leftPad(StringUtils.right(bankCard, 4), StringUtils.length(bankCard), "*"), "***"));
	}
	
	/**
	 * 邮箱数据脱敏
	 * 邮箱前缀仅显示第一个字母，前缀其他隐藏，用星号代替，@及后面的地址显示
	 * 例子:g**@163.com
	 * @param src
	 * @return
	 */
	public static String emailSensitive(Object src){
		if(src==null){
            return null;
        }
        String email = src.toString();
        int index = StringUtils.indexOf(email, "@");
        if (index <= 1) {
            return email;
        } else {
            return StringUtils.rightPad(StringUtils.left(email, 1), index, "*").concat(
                    StringUtils.mid(email, index, StringUtils.length(email)));
        }
	}
	
	/**
	 * 座机数据脱敏
	 * 座机的前2位和后4位保留，其余的隐藏。
	 * @param src
	 * @return
	 */
	public static String fixedPhoneSensitive(Object src){
		 if(src==null){
	            return null;
	        }
	        String fixedPhone=src.toString();
	        return StringUtils.left(fixedPhone, 2).concat(StringUtils.removeStart(StringUtils.leftPad(StringUtils.right(fixedPhone, 4), StringUtils.length(fixedPhone), "*"), "***"));
	}
	
	/**
	 * 身份证号脱敏类型
	 * 前3位，后4位
	 * 130722199102323232 脱敏后: 130*************3232
	 * @param src
	 * @return
	 */
	public static String iDCardSensitive(Object src){
		if(src==null){
            return null;
        }
        String idCard = src.toString();
        return StringUtils.left(idCard, 3).concat(StringUtils.removeStart(StringUtils.leftPad(StringUtils.right(idCard, 4), StringUtils.length(idCard), "*"), "***"));
	}
	
	/**
	 * 手机号码数据脱敏
	 * 18233583070 脱敏后: 182****3030
	 * @param src
	 * @return
	 */
	public static String  mobilePhoneSensitive(Object src){
		 if(src==null){
	          return null;
	     }
	     String value = src.toString();
	     return StringUtils.left(value, 3).concat(StringUtils.removeStart(StringUtils.leftPad(StringUtils.right(value, 4), StringUtils.length(value), "*"), "***"));
	}
	
	/**
	 * 真实姓名脱敏
	 * 中文姓名只显示第一个汉字，其他隐藏为2个星号
	 * 例子：李**
	 * 张三丰 ：张**
	 * @param src
	 * @return
	 */
	public static String nameSensitive(Object src){
		  if (src==null) {
	            return "";
	        }
	        String fullName = src.toString();
	        String name = StringUtils.left(fullName, 1);
	        return StringUtils.rightPad(name, StringUtils.length(fullName), "*");
	}
	
}
