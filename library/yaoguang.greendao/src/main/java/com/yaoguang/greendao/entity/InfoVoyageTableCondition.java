package com.yaoguang.greendao.entity;

import java.util.List;

/**
 * 基础资料-船期表查询条件
 * 
 * @author ZhangDeQuan
 * @time 2017年5月10日 上午10:54:08
 */
public class InfoVoyageTableCondition  {

	// 起始港
	private String placeLoading;

	// 目的港
	private String placeDelivery;

	// 船公司
	private String shipCompany;

	// 柜型
	private String contId;

	// 发布起始日期
	private String startReleaseDate;

	// 发布截止日期
	private String endReleaseDate;

	// 预开起始日期
	private String startOnboardDate;

	// 预开截止日期
	private String endOnboardDate;

	// 预抵起始日期
	private String startArrivalDate;

	// 预抵截止日期
	private String endArrivalDate;

	// 总公司编号
	private String clientId;

	// 总公司名称
	private String strClient;
	
	// 总公司编号集合
	private List<String> clientIdList;

	public String getPlaceLoading() {
		return placeLoading;
	}

	public void setPlaceLoading(String placeLoading) {
		this.placeLoading = placeLoading;
	}

	public String getPlaceDelivery() {
		return placeDelivery;
	}

	public void setPlaceDelivery(String placeDelivery) {
		this.placeDelivery = placeDelivery;
	}

	public String getShipCompany() {
		return shipCompany;
	}

	public void setShipCompany(String shipCompany) {
		this.shipCompany = shipCompany;
	}

	public String getContId() {
		return contId;
	}

	public void setContId(String contId) {
		this.contId = contId;
	}

	public String getStartReleaseDate() {
		return startReleaseDate;
	}

	public void setStartReleaseDate(String startReleaseDate) {
		this.startReleaseDate = startReleaseDate;
	}

	public String getEndReleaseDate() {
		return endReleaseDate;
	}

	public void setEndReleaseDate(String endReleaseDate) {
		this.endReleaseDate = endReleaseDate;
	}

	public String getStartOnboardDate() {
		return startOnboardDate;
	}

	public void setStartOnboardDate(String startOnboardDate) {
		this.startOnboardDate = startOnboardDate;
	}

	public String getEndOnboardDate() {
		return endOnboardDate;
	}

	public void setEndOnboardDate(String endOnboardDate) {
		this.endOnboardDate = endOnboardDate;
	}

	public String getStartArrivalDate() {
		return startArrivalDate;
	}

	public void setStartArrivalDate(String startArrivalDate) {
		this.startArrivalDate = startArrivalDate;
	}

	public String getEndArrivalDate() {
		return endArrivalDate;
	}

	public void setEndArrivalDate(String endArrivalDate) {
		this.endArrivalDate = endArrivalDate;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public List<String> getClientIdList() {
		return clientIdList;
	}

	public void setClientIdList(List<String> clientIdList) {
		this.clientIdList = clientIdList;
	}

	public String getStrClient() {
		return strClient;
	}

	public void setStrClient(String strClient) {
		this.strClient = strClient;
	}

}
