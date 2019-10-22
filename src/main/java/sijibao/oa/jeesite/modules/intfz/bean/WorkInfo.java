/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.intfz.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 工作及内务生成接收对象
 * @author Petter
 * @version 2018-04-15
 */
@ApiModel(value="工作及内务对象")
public class WorkInfo {
	
	@ApiModelProperty(value="签到ID")
	private String signId;
	@ApiModelProperty(value="提交人信息")
	private UserInfo userInfo;  	//创建人姓名
	@ApiModelProperty(value="年份")
	private String workYear;	//年份
	@ApiModelProperty(value="月份")
	private String workMonth;	//月份
	@ApiModelProperty(value="日数，天列表时有")
	private String workDay;	//日数
	@ApiModelProperty(value="工作签到次数，年月列表时有")
	private String workSignCount;		// 工作签到次数
	@ApiModelProperty(value="签到URL，天列表时有")
	private String signUrl;
	@ApiModelProperty(value="工作照片上传次数，年月列表时有")
	private String workPhotoCount;		// 工作照片上传次数
	@ApiModelProperty(value="工作URL，天列表时有")
	private String workUrl;
	@ApiModelProperty(value="内务照片上传次数，年月列表时有")
	private String interiorPhotoCount;		// 内务照片上传次数
	@ApiModelProperty(value="内务URL，天列表时有")
	private String innerUrl;
	@ApiModelProperty(value="得分")
	private String score;		// 得分
	@ApiModelProperty(value="是否签到0否1签到2草稿")
	private String isSign;//是否签到
	@ApiModelProperty(value="是否评分0否1是")
	private String isScore;//是否评分
	public String getSignId() {
		return signId;
	}
	public void setSignId(String signId) {
		this.signId = signId;
	}
	public UserInfo getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
	public String getWorkYear() {
		return workYear;
	}
	public void setWorkYear(String workYear) {
		this.workYear = workYear;
	}
	public String getWorkMonth() {
		return workMonth;
	}
	public void setWorkMonth(String workMonth) {
		this.workMonth = workMonth;
	}
	public String getWorkDay() {
		return workDay;
	}
	public void setWorkDay(String workDay) {
		this.workDay = workDay;
	}
	public String getWorkSignCount() {
		return workSignCount;
	}
	public void setWorkSignCount(String workSignCount) {
		this.workSignCount = workSignCount;
	}
	public String getSignUrl() {
		return signUrl;
	}
	public void setSignUrl(String signUrl) {
		this.signUrl = signUrl;
	}
	public String getWorkPhotoCount() {
		return workPhotoCount;
	}
	public void setWorkPhotoCount(String workPhotoCount) {
		this.workPhotoCount = workPhotoCount;
	}
	public String getWorkUrl() {
		return workUrl;
	}
	public void setWorkUrl(String workUrl) {
		this.workUrl = workUrl;
	}
	public String getInteriorPhotoCount() {
		return interiorPhotoCount;
	}
	public void setInteriorPhotoCount(String interiorPhotoCount) {
		this.interiorPhotoCount = interiorPhotoCount;
	}
	public String getInnerUrl() {
		return innerUrl;
	}
	public void setInnerUrl(String innerUrl) {
		this.innerUrl = innerUrl;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getIsSign() {
		return isSign;
	}
	public void setIsSign(String isSign) {
		this.isSign = isSign;
	}
	public String getIsScore() {
		return isScore;
	}
	public void setIsScore(String isScore) {
		this.isScore = isScore;
	}
	@Override
	public String toString() {
		return "WorkInfo [signId=" + signId + ", userInfo=" + userInfo + ", workYear=" + workYear + ", workMonth="
				+ workMonth + ", workDay=" + workDay + ", workSignCount=" + workSignCount + ", signUrl=" + signUrl
				+ ", workPhotoCount=" + workPhotoCount + ", workUrl=" + workUrl + ", interiorPhotoCount="
				+ interiorPhotoCount + ", innerUrl=" + innerUrl + ", score=" + score + ", isSign=" + isSign
				+ ", isScore=" + isScore + "]";
	}

}