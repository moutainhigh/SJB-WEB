package sijibao.oa.jeesite.modules.intfz.response.intfzwebresp.company;

public class MainAbnormalOrderListForOaRsp {


	/**
	 * 运单号
	 */
	private String orderNumber;
	
	/**
	 * 车牌号
	 */
	private String vehicleNumber;
	
	/**
	 * 装货地名称
	 */
	private String loadAddressName;
	
	/**
	 * 装货地时间
	 */
	private long loadTime;
	
	/**
	 * 卸货地名称
	 */
	private String unloadAddressName;
	
	/**
	 * 卸货地时间
	 */
	private long unloadTime;
	
	/**
	 * 异常时间
	 */
	private long abnormalTime;
	
	/**
	 * 异常备注
	 */
	private String abnormalMemo;

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getVehicleNumber() {
		return vehicleNumber;
	}

	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}

	public String getLoadAddressName() {
		return loadAddressName;
	}

	public void setLoadAddressName(String loadAddressName) {
		this.loadAddressName = loadAddressName;
	}

	public long getLoadTime() {
		return loadTime;
	}

	public void setLoadTime(long loadTime) {
		this.loadTime = loadTime;
	}

	public String getUnloadAddressName() {
		return unloadAddressName;
	}

	public void setUnloadAddressName(String unloadAddressName) {
		this.unloadAddressName = unloadAddressName;
	}

	public long getUnloadTime() {
		return unloadTime;
	}

	public void setUnloadTime(long unloadTime) {
		this.unloadTime = unloadTime;
	}

	public long getAbnormalTime() {
		return abnormalTime;
	}

	public void setAbnormalTime(long abnormalTime) {
		this.abnormalTime = abnormalTime;
	}

	public String getAbnormalMemo() {
		return abnormalMemo;
	}

	public void setAbnormalMemo(String abnormalMemo) {
		this.abnormalMemo = abnormalMemo;
	}

	@Override
	public String toString() {
		return "MainAbnormalOrderListForOaRsp [orderNumber=" + orderNumber + ", vehicleNumber=" + vehicleNumber
				+ ", loadAddressName=" + loadAddressName + ", loadTime=" + loadTime + ", unloadAddressName="
				+ unloadAddressName + ", unloadTime=" + unloadTime + ", abnormalTime=" + abnormalTime
				+ ", abnormalMemo=" + abnormalMemo + "]";
	}
	
	
	
}
