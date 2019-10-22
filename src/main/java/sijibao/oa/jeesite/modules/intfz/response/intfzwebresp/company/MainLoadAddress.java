package sijibao.oa.jeesite.modules.intfz.response.intfzwebresp.company;

import java.io.Serializable;

/**
 * 企业管理-常用装卸货地址
 * @author wanxb
 *
 */
public class MainLoadAddress implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String addressCode;

	private String addressName;

	private Integer addressType;

	private String companyCode;

	private String companyName;

	private Long createDate;

	private Long updateDate;

	private String contactsName;

	private String contactsTel;

	private String addressMemo;

	private String addressMapImage;

	private String locationCode;

	private Double latitude;

	private Double longitude;

	private String province;

	private String provinceCode;

	private String city;

	private String cityCode;

	private String regionName;

	private String regionCode;

	private String street;

	private String streetno;

	private String addressDetail;

	private Integer geotype;

	private Integer state;

	public String getAddressCode() {
		return addressCode;
	}

	public void setAddressCode(String addressCode) {
		this.addressCode = addressCode;
	}

	public String getAddressName() {
		return addressName;
	}

	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}

	public Integer getAddressType() {
		return addressType;
	}

	public void setAddressType(Integer addressType) {
		this.addressType = addressType;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Long getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Long createDate) {
		this.createDate = createDate;
	}

	public Long getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Long updateDate) {
		this.updateDate = updateDate;
	}

	public String getContactsName() {
		return contactsName;
	}

	public void setContactsName(String contactsName) {
		this.contactsName = contactsName;
	}

	public String getContactsTel() {
		return contactsTel;
	}

	public void setContactsTel(String contactsTel) {
		this.contactsTel = contactsTel;
	}

	public String getAddressMemo() {
		return addressMemo;
	}

	public void setAddressMemo(String addressMemo) {
		this.addressMemo = addressMemo;
	}

	public String getAddressMapImage() {
		return addressMapImage;
	}

	public void setAddressMapImage(String addressMapImage) {
		this.addressMapImage = addressMapImage;
	}

	public String getLocationCode() {
		return locationCode;
	}

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getStreetno() {
		return streetno;
	}

	public void setStreetno(String streetno) {
		this.streetno = streetno;
	}

	public String getAddressDetail() {
		return addressDetail;
	}

	public void setAddressDetail(String addressDetail) {
		this.addressDetail = addressDetail;
	}

	public Integer getGeotype() {
		return geotype;
	}

	public void setGeotype(Integer geotype) {
		this.geotype = geotype;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "MainLoadAddress [addressCode=" + addressCode + ", addressName=" + addressName + ", addressType="
				+ addressType + ", companyCode=" + companyCode + ", companyName=" + companyName + ", createDate="
				+ createDate + ", updateDate=" + updateDate + ", contactsName=" + contactsName + ", contactsTel="
				+ contactsTel + ", addressMemo=" + addressMemo + ", addressMapImage=" + addressMapImage
				+ ", locationCode=" + locationCode + ", latitude=" + latitude + ", longitude=" + longitude
				+ ", province=" + province + ", provinceCode=" + provinceCode + ", city=" + city + ", cityCode="
				+ cityCode + ", regionName=" + regionName + ", regionCode=" + regionCode + ", street=" + street
				+ ", streetno=" + streetno + ", addressDetail=" + addressDetail + ", geotype=" + geotype + ", state="
				+ state + "]";
	}
	
	
	
}
