package com.digipower.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.digipower.common.entity.Result;
import com.digipower.common.uuid.SnowflakeIdGenerator;
import com.digipower.entity.SysVerificationCode;
import com.digipower.service.SysVerificationCodeService;
import com.google.code.kaptcha.impl.DefaultKaptcha;

@RestController
public class KaptchaController {
	@Autowired
	private DefaultKaptcha defaultKaptcha;
	@Autowired
	private SysVerificationCodeService sysVerificationCodeService;
	@Autowired
	private SnowflakeIdGenerator idGenerator;
	
	/**
	 * 验证码使用数据库存储
	 * @param code
	 */
	public void saveVerificationCode(String code){
		if(StringUtils.isNotEmpty(code)){
			SysVerificationCode sysVerificationCode = new SysVerificationCode();
			sysVerificationCode.setCode(code);
			sysVerificationCode.setCreatedDt(new Date());
			sysVerificationCode.setSid(String.valueOf(idGenerator.nextId()));
			sysVerificationCodeService.save(sysVerificationCode);
		}
	}
	
	/**
	 * 1、生成验证码
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @throws Exception
	 */
	@RequestMapping("/defaultKaptcha")
	public void defaultKaptcha(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
			throws Exception {
		byte[] captchaChallengeAsJpeg = null;
		ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
		try {
			// 生产验证码字符串并保存到数据库中
			String createText = defaultKaptcha.createText();
			saveVerificationCode(createText);
			httpServletRequest.getSession().setAttribute("rightCode", createText);
			// 使用生产的验证码字符串返回一个BufferedImage对象并转为byte写入到byte数组中
			BufferedImage challenge = defaultKaptcha.createImage(createText);
			ImageIO.write(challenge, "jpg", jpegOutputStream);
		} catch (IllegalArgumentException e) {
			httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		// 定义response输出类型为image/jpeg类型，使用response输出流输出图片的byte数组
		captchaChallengeAsJpeg = jpegOutputStream.toByteArray();
		httpServletResponse.setHeader("Cache-Control", "no-store");
		httpServletResponse.setHeader("Pragma", "no-cache");
		httpServletResponse.setDateHeader("Expires", 0);
		httpServletResponse.setContentType("image/jpeg");
		ServletOutputStream responseOutputStream = httpServletResponse.getOutputStream();
		responseOutputStream.write(captchaChallengeAsJpeg);
		responseOutputStream.flush();
		responseOutputStream.close();
	}
}
