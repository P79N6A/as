package com.yaoguang.greendao.entity;

import java.io.Serializable;

/**
 * 司机订单消息包装类
 * @author liyangbin
 * 
 * @date 2017年7月10日下午2:36:50
 */
public class DriverOrderMsgWrapper extends DriverOrderMsg implements Serializable{
	
	private static final long serialVersionUID = 665823055316711845L;
    
	public String getMsgTypeDisplay(){
		switch (getMsgType()) {
		case 0:
			return "派";
		case 1:
			return "改";
		default:
			return "关";
		}
	}
}