/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.oa.entity;

import java.util.Date;

import com.sijibao.oa.common.persistence.DataEntity;
import com.sijibao.oa.modules.sys.entity.Area;
import com.sijibao.oa.modules.sys.entity.Office;
import com.sijibao.oa.modules.sys.entity.User;

/**
 * 项目信息
 * @author Petter
 * @version 2018-04-17
 */
@SuppressWarnings("serial")
public class ProjectInfo extends DataEntity<ProjectInfo> {
	
	private String projectCode;//项目Code
	private String projectName;//项目名称
	private String projectType;//项目类型0公司项目1市场项目
	private Office office;//归属部门
	private CustInfo custInfo;//归属客户
	private Area area;//归属区域
	private String projectState;//项目状态0待上线1已上线2已关闭
	private Date onLineDate;//上线时间格式yyyy-MM-dd
	private User projectLeader;//项目负责人
	private User marketLeader;//市场负责人
	private User impleLeader;//实施负责人
	private String pnameInitials; //项目名称首字母
	private String queryProjectState; //项目查询状态标识
	private String timeType;//时间类型
	private Date start;//开始搜索时间
	private Date end;//结束搜索时间
	public String getProjectCode() {
		return projectCode;
	}
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getProjectType() {
		return projectType;
	}
	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}
	public Office getOffice() {
		return office;
	}
	public void setOffice(Office office) {
		this.office = office;
	}
	public CustInfo getCustInfo() {
		return custInfo;
	}
	public void setCustInfo(CustInfo custInfo) {
		this.custInfo = custInfo;
	}
	public Area getArea() {
		return area;
	}
	public void setArea(Area area) {
		this.area = area;
	}
	public String getProjectState() {
		return projectState;
	}
	public void setProjectState(String projectState) {
		this.projectState = projectState;
	}
	public Date getOnLineDate() {
		return onLineDate;
	}
	public void setOnLineDate(Date onLineDate) {
		this.onLineDate = onLineDate;
	}
	public User getProjectLeader() {
		return projectLeader;
	}
	public void setProjectLeader(User projectLeader) {
		this.projectLeader = projectLeader;
	}
	public User getMarketLeader() {
		return marketLeader;
	}
	public void setMarketLeader(User marketLeader) {
		this.marketLeader = marketLeader;
	}
	public User getImpleLeader() {
		return impleLeader;
	}
	public void setImpleLeader(User impleLeader) {
		this.impleLeader = impleLeader;
	}
	public String getPnameInitials() {
		return pnameInitials;
	}
	public void setPnameInitials(String pnameInitials) {
		this.pnameInitials = pnameInitials;
	}
	public String getQueryProjectState() {
		return queryProjectState;
	}
	public void setQueryProjectState(String queryProjectState) {
		this.queryProjectState = queryProjectState;
	}
	
	
	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
	}
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		this.end = end;
	}
	public String getTimeType() {
		return timeType;
	}
	public void setTimeType(String timeType) {
		this.timeType = timeType;
	}
	@Override
	public String toString() {
		return "ProjectInfo [projectCode=" + projectCode + ", projectName=" + projectName + ", projectType="
				+ projectType + ", office=" + office + ", custInfo=" + custInfo + ", area=" + area + ", projectState="
				+ projectState + ", onLineDate=" + onLineDate + ", projectLeader=" + projectLeader + ", marketLeader="
				+ marketLeader + ", impleLeader=" + impleLeader + ", pnameInitials=" + pnameInitials
				+ ", queryProjectState=" + queryProjectState + ", timeType=" + timeType + ", start=" + start + ", end="
				+ end + "]";
	}
	
	
}