/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.oa.entity;

import com.sijibao.oa.common.persistence.DataEntity;

/**
 * 内务管理Entity
 * @author Petter
 * @version 2018-04-15
 */
public class HouseKeepingPhoto extends DataEntity<HouseKeepingPhoto> {
	
	private static final long serialVersionUID = 1L;
	private String userName;      //用户名称
	private String workDate;      //签到日期
	private String photoType;		// 关联code
	private String url;    // 地址
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getWorkDate() {
		return workDate;
	}
	public void setWorkDate(String workDate) {
		this.workDate = workDate;
	}
	public String getPhotoType() {
		return photoType;
	}
	public void setPhotoType(String photoType) {
		this.photoType = photoType;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public String toString() {
		return "HouseKeepingPhoto [userName=" + userName + ", workDate=" + workDate + ", photoType=" + photoType
				+ ", url=" + url + "]";
	}
	
}