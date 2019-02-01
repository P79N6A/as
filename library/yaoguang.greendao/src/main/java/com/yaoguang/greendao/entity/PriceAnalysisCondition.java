package com.yaoguang.greendao.entity;

import java.io.Serializable;

/**
 * 价格管理-报价分析查询条件
 * 
 * @author wangxiaohui
 * @date 2017年4月27日 下午6:42:31
 */
public class PriceAnalysisCondition implements Serializable {
	private static final long serialVersionUID = 2711015254697152556L;

		// 起运地
		private String dockLoading;

		// 起运港
		private String portLoading;

		// 目的港
		private String portDelivery;

		// 目的地
		private String finalDestination;
		
		// 柜型
		private String contId;

		// 总公司编号
		private String clientId;

		// 分公司编号
		private String companyId;
		
		public String getDockLoading() {
			return dockLoading;
		}

		public void setDockLoading(String dockLoading) {
			this.dockLoading = dockLoading;
		}

		public String getPortLoading() {
			return portLoading;
		}

		public void setPortLoading(String portLoading) {
			this.portLoading = portLoading;
		}

		public String getPortDelivery() {
			return portDelivery;
		}

		public void setPortDelivery(String portDelivery) {
			this.portDelivery = portDelivery;
		}

		public String getFinalDestination() {
			return finalDestination;
		}

		public void setFinalDestination(String finalDestination) {
			this.finalDestination = finalDestination;
		}

		public static long getSerialversionuid() {
			return serialVersionUID;
		}

		public String getClientId() {
			return clientId;
		}

		public void setClientId(String clientId) {
			this.clientId = clientId;
		}

		public String getCompanyId() {
			return companyId;
		}

		public void setCompanyId(String companyId) {
			this.companyId = companyId;
		}

		public String getContId() {
			return contId;
		}

		public void setContId(String contId) {
			this.contId = contId;
		}
		
			
}
