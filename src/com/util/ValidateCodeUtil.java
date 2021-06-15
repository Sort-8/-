package com.util;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ShearCaptcha;

public class ValidateCodeUtil {
	
	/**
	 * 返回验证码类
	 * @return
	 */
	public static ShearCaptcha getValidateCode() {
		ShearCaptcha captcha = CaptchaUtil.createShearCaptcha(Constant.ImgHeight,  //图片高度
				  Constant.Imgwidth,   //图片宽带
				  Constant.CodeCount,  //验证码字符数
				  Constant.LineWidth); //干扰线宽度
		return captcha;
	}
}
