package com.yaoguang.greendao.entity.driver;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import com.yaoguang.greendao.entity.DriverBillsVo;

/**
 * 司机订单详情页
 * @author liyangbin
 * 
 * @date 2017年11月1日下午6:29:56
 */
public class DriverOrderDetailVo implements Parcelable {

	// 头部
	private DriverOrderWrapper orderWrapper;

	// 司机工作单
	private List<ListDriverBillsVo> billList = new ArrayList<>();

	private List<DriverBillsVo> driverBillsOne;
	private List<DriverBillsVo> driverBillsTwo;

	public List<DriverBillsVo> getDriverBillsOne() {
		return driverBillsOne;
	}

	public void setDriverBillsOne(List<DriverBillsVo> driverBillsOne) {
		this.driverBillsOne = driverBillsOne;
	}

	public List<DriverBillsVo> getDriverBillsTwo() {
		return driverBillsTwo;
	}

	public void setDriverBillsTwo(List<DriverBillsVo> driverBillsTwo) {
		this.driverBillsTwo = driverBillsTwo;
	}

	public DriverOrderWrapper getOrderWrapper() {
		return orderWrapper;
	}

	public void setOrderWrapper(DriverOrderWrapper orderWrapper) {
		this.orderWrapper = orderWrapper;
	}

	public List<ListDriverBillsVo> getBillList() {
		return billList;
	}

	public void setBillList(List<ListDriverBillsVo> billList) {
		this.billList = billList;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeParcelable(this.orderWrapper, flags);
		dest.writeTypedList(this.billList);
		dest.writeTypedList(this.driverBillsOne);
		dest.writeTypedList(this.driverBillsTwo);
	}

	public DriverOrderDetailVo() {
	}

	protected DriverOrderDetailVo(Parcel in) {
		this.orderWrapper = in.readParcelable(DriverOrderWrapper.class.getClassLoader());
		this.billList = in.createTypedArrayList(ListDriverBillsVo.CREATOR);
		this.driverBillsOne = in.createTypedArrayList(DriverBillsVo.CREATOR);
		this.driverBillsTwo = in.createTypedArrayList(DriverBillsVo.CREATOR);
	}

	public static final Creator<DriverOrderDetailVo> CREATOR = new Creator<DriverOrderDetailVo>() {
		@Override
		public DriverOrderDetailVo createFromParcel(Parcel source) {
			return new DriverOrderDetailVo(source);
		}

		@Override
		public DriverOrderDetailVo[] newArray(int size) {
			return new DriverOrderDetailVo[size];
		}
	};

}
