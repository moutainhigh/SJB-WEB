/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.oa.entity;

import java.math.BigDecimal;

import com.sijibao.oa.common.persistence.DataEntity;

/**
 * 内务管理Entity
 * @author Petter
 * @version 2018-03-29
 */
public class HouseKeeping extends DataEntity<HouseKeeping> {
	
	private static final long serialVersionUID = 1L;
	private String userId;		// 用户ID
	private String userName;    // 用户名称
	private String workDate;    // 工作日
	private String signCode;    // 签到code
	private String workCode;    // 工作code
	private String innerCode;   // 内务code
	private String isSign;      // 是否签到0否1是2草稿
	private BigDecimal score; 	// 评分
	private String scoreId;     // 评分人ID
	private String scoreName;     // 评分人姓名
	
	//查询
	private String startWorkMonth;//开始月份
	private String endWorkMonth;//结束月份
	private String endWorkDate;//结束工作日
	
	//月份结果
	private String signNum;//签到上传次数
	private String workNum;//工作上传次数
	private String innerNum;//内务上传次数
	//日份结果
	private String signUrl;//签到上传url
	private String workUrl;//工作上传url
	private String innerUrl;//内务上传url
	
	public HouseKeeping() {
		super();
	}
	public HouseKeeping(String id){
		super(id);
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
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
	public String getSignCode() {
		return signCode;
	}
	public void setSignCode(String signCode) {
		this.signCode = signCode;
	}
	public String getWorkCode() {
		return workCode;
	}
	public void setWorkCode(String workCode) {
		this.workCode = workCode;
	}
	public String getInnerCode() {
		return innerCode;
	}
	public void setInnerCode(String innerCode) {
		this.innerCode = innerCode;
	}
	public String getIsSign() {
		return isSign;
	}
	public void setIsSign(String isSign) {
		this.isSign = isSign;
	}
	public BigDecimal getScore() {
		return score;
	}
	public void setScore(BigDecimal score) {
		this.score = score;
	}
	public String getScoreId() {
		return scoreId;
	}
	public void setScoreId(String scoreId) {
		this.scoreId = scoreId;
	}
	public String getScoreName() {
		return scoreName;
	}
	public void setScoreName(String scoreName) {
		this.scoreName = scoreName;
	}
	
	public String getStartWorkMonth() {
		return startWorkMonth;
	}
	public void setStartWorkMonth(String startWorkMonth) {
		this.startWorkMonth = startWorkMonth;
	}
	public String getEndWorkMonth() {
		return endWorkMonth;
	}
	public void setEndWorkMonth(String endWorkMonth) {
		this.endWorkMonth = endWorkMonth;
	}
	public String getEndWorkDate() {
		return endWorkDate;
	}
	public void setEndWorkDate(String endWorkDate) {
		this.endWorkDate = endWorkDate;
	}
	public String getSignNum() {
		return signNum;
	}
	public void setSignNum(String signNum) {
		this.signNum = signNum;
	}
	public String getWorkNum() {
		return workNum;
	}
	public void setWorkNum(String workNum) {
		this.workNum = workNum;
	}
	public String getInnerNum() {
		return innerNum;
	}
	public void setInnerNum(String innerNum) {
		this.innerNum = innerNum;
	}
	public String getSignUrl() {
		return signUrl;
	}
	public void setSignUrl(String signUrl) {
		this.signUrl = signUrl;
	}
	public String getWorkUrl() {
		return workUrl;
	}
	public void setWorkUrl(String workUrl) {
		this.workUrl = workUrl;
	}
	public String getInnerUrl() {
		return innerUrl;
	}
	public void setInnerUrl(String innerUrl) {
		this.innerUrl = innerUrl;
	}
	@Override
	public String toString() {
		return "HouseKeeping [userId=" + userId + ", userName=" + userName + ", workDate=" + workDate + ", signCode="
				+ signCode + ", workCode=" + workCode + ", innerCode=" + innerCode + ", isSign=" + isSign + ", score="
				+ score + ", scoreId=" + scoreId + ", scoreName=" + scoreName + ", startWorkMonth=" + startWorkMonth
				+ ", endWorkMonth=" + endWorkMonth + ", signNum=" + signNum + ", workNum=" + workNum + ", innerNum="
				+ innerNum + ", signUrl=" + signUrl + ", workUrl=" + workUrl + ", innerUrl=" + innerUrl + "]";
	}
	
}