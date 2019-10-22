/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.intfz.request;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 工作及内务表生成接收对象
 * @author Petter
 * @version 2018-04-15
 */
@ApiModel(value="签到评分请求对象")
public class WorkReq implements Serializable {
	
	@ApiModelProperty(value="用户ID，评分时必传，保存草稿或提交时可为空")
	private String userId;
	@ApiModelProperty(value="工作年月日yyyy-MM-dd")
	private String workDate;		// 工作年月日
	@ApiModelProperty(value="得分")
	private String score;		// 得分
	@ApiModelProperty(value="类型0草稿1提交")
	private String type;
	
	@ApiModelProperty(value="签到图片地址")
	private String workSignUrl;
	@ApiModelProperty(value="工作图片地址")
	private String workPhotoUrl;
	@ApiModelProperty(value="内务图片地址")
	private String interPhotoUrl;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getWorkDate() {
		return workDate;
	}
	public void setWorkDate(String workDate) {
		this.workDate = workDate;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getWorkSignUrl() {
		return workSignUrl;
	}
	public void setWorkSignUrl(String workSignUrl) {
		this.workSignUrl = workSignUrl;
	}
	public String getWorkPhotoUrl() {
		return workPhotoUrl;
	}
	public void setWorkPhotoUrl(String workPhotoUrl) {
		this.workPhotoUrl = workPhotoUrl;
	}
	public String getInterPhotoUrl() {
		return interPhotoUrl;
	}
	public void setInterPhotoUrl(String interPhotoUrl) {
		this.interPhotoUrl = interPhotoUrl;
	}
	@Override
	public String toString() {
		return "WorkReq [userId=" + userId + ", workDate=" + workDate + ", score=" + score + ", type=" + type
				+ ", workSignUrl=" + workSignUrl + ", workPhotoUrl=" + workPhotoUrl + ", interPhotoUrl=" + interPhotoUrl
				+ "]";
	}
	
}