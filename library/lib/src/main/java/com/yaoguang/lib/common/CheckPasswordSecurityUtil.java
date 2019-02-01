package com.yaoguang.lib.common;

/**
 * 检测密码强度
 * 
 * @author heyonggang
 * @date 2016年10月9日下午5:30:55
 */
public class CheckPasswordSecurityUtil {
	
	/**
	 * 密码强度检测(0：弱 1：中 2：强)
	 * @param pwd 密码
	 * @return
	 */
	public static int checkSecurity(String pwd) {
		if (pwd.matches("^[1-9]\\d*$") || pwd.matches("^[A-Z]+$") || pwd.matches("^[a-z]+$")) {
			return 0;// 同一种的都为0
		} else if (pwd.matches("^[A-Za-z0-9]+$") && pwd.length() < 10) {
			return 1;
		} else {
			return 2;
		}
	}

}
