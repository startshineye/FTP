package com.order.cc.ftp.domain;

/**
 * 银行设备类
 * 
 * @author yxm
 * @date 2016-12-7
 */
public class DeviceVO {
	private String id;//主鍵
	private String deviceNum;// 设备编号
	private String institution;// 所属机构
	private String deviceAddress;// 设备地址
	private String managerName;// 管理员名称
	private String contact;// 联系方式
	private String createTime;// 创建时间
	private Integer flag;// 标识【0保存失败，1保存成功】
   
	@Override
	public String toString() {
		return "DeviceVO [id=" + id + ", deviceNum=" + deviceNum
				+ ", institution=" + institution + ", deviceAddress="
				+ deviceAddress + ", managerName=" + managerName + ", contact="
				+ contact + ", createTime=" + createTime + ", flag=" + flag
				+ "]";
	}

	public String getDeviceNum() {
		return deviceNum;
	}

	public void setDeviceNum(String deviceNum) {
		this.deviceNum = deviceNum;
	}

	public String getInstitution() {
		return institution;
	}

	public void setInstitution(String institution) {
		this.institution = institution;
	}

	public String getDeviceAddress() {
		return deviceAddress;
	}

	public void setDeviceAddress(String deviceAddress) {
		this.deviceAddress = deviceAddress;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

}
