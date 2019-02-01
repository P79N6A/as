package com.yaoguang.greendao.entity;

import java.io.Serializable;

/**
 * 司机货柜
 * @author liyangbin
 * 
 * @date 2017年11月1日下午7:26:41
 */
public class DriverSonoVo implements Serializable {

	private static final long serialVersionUID = 4711847854801936285L;

	private String id;
	
	private String contNo;

	private String sealNo;

	private String carryPort;

	private String getPort;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContNo() {
		return contNo;
	}

	public void setContNo(String contNo) {
		this.contNo = contNo;
	}

	public String getSealNo() {
		return sealNo;
	}

	public void setSealNo(String sealNo) {
		this.sealNo = sealNo;
	}

	public String getCarryPort() {
		return carryPort;
	}

	public void setCarryPort(String carryPort) {
		this.carryPort = carryPort;
	}

	public String getGetPort() {
		return getPort;
	}

	public void setGetPort(String getPort) {
		this.getPort = getPort;
	}

}