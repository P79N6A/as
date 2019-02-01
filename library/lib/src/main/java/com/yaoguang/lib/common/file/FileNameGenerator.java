package com.yaoguang.lib.common.file;

import com.yaoguang.lib.common.DateUtils;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 文件号生成器
 * 
 * @author heyonggang
 * @date 2017年7月11日下午1:56:51
 */
public class FileNameGenerator {

	private static String initCode;

	private static AtomicInteger seq = new AtomicInteger(1);
	
	private static ReentrantLock lock = new ReentrantLock();

	/**
	 * 生成器
	 * @param fileType 文件类型
	 * @return
	 */
	public static String generateCode(String fileType) {

		String time = DateUtils.dateToString(new Date(), DateUtils.YEAR_MONTH_DAY_HOUR_MINUTE_SECOND);

		String code =  fileType + "" + time;
		
		lock.lock();

		if (initCode != null && !initCode.equals(code)) {
			seq.set(1);
		}
		initCode = code;

		String rechargeCode = fileType + "_" + time + "" + seq.getAndIncrement();

		lock.unlock();

		return rechargeCode;
	}
	
	public static void main(String[] args) {
		System.out.println(generateCode("pic")); 
		System.out.println(generateCode("pic")); 
		System.out.println(generateCode("video")); 
		System.out.println(generateCode("video")); 
		System.out.println(generateCode("video")); 
	}


}
