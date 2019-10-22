package sijibao.oa.jeesite.modules.oa.entity;

import com.sijibao.oa.common.persistence.DataEntity;

public class AllData extends DataEntity<AllData>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String officeName;
	private String userName;
	private String projectName;
	private String firstSubCode;
	private String secondSubCode;
	private String amt;
	public String getOfficeName() {
		return officeName;
	}
	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getFirstSubCode() {
		return firstSubCode;
	}
	public void setFirstSubCode(String firstSubCode) {
		this.firstSubCode = firstSubCode;
	}
	public String getSecondSubCode() {
		return secondSubCode;
	}
	public void setSecondSubCode(String secondSubCode) {
		this.secondSubCode = secondSubCode;
	}
	public String getAmt() {
		return amt;
	}
	public void setAmt(String amt) {
		this.amt = amt;
	}
	@Override
	public String toString() {
		return "AllData [officeName=" + officeName + ", userName=" + userName + ", projectName=" + projectName
				+ ", firstSubCode=" + firstSubCode + ", secondSubCode=" + secondSubCode + ", amt=" + amt + "]";
	}
	
	
	
}
