package com.yaoguang.greendao.entity.company;

import com.yaoguang.greendao.entity.common.TruckBills;
import com.yaoguang.greendao.entity.common.ViewForwardOrder;


/**
 * web端工作单模板包装
 * @author wangxiaohui
 * 2018年1月29日
 */
public class WebOrderTemplateWrapper {
	private ViewForwardOrder viewForwardOrder;
	
	private TruckBills truckBills;
	
	private WebOrderTemplate template;

	public WebOrderTemplate getTemplate() {
		return template;
	}

	public void setTemplate(WebOrderTemplate template) {
		this.template = template;
	}

	public ViewForwardOrder getViewForwardOrder() {
		return viewForwardOrder;
	}

	public void setViewForwardOrder(ViewForwardOrder viewForwardOrder) {
		this.viewForwardOrder = viewForwardOrder;
	}

	public TruckBills getTruckBills() {
		return truckBills;
	}

	public void setTruckBills(TruckBills truckBills) {
		this.truckBills = truckBills;
	}

}
