package sijibao.oa.jeesite.modules.oa.entity;
/**
 * 查询条件对象
 * @author wuys
 *
 */
public class ExpensesReportInfo {
	private String projectName;  	//项目编号
	private String status;			//报销状态
	private String querDateStart;	//开始时间
	private String querDateEnd;		//截止时间
	private String querOffice;		//归属部门
	private String isHide;          //是否隐藏列
	private String isInit;          //是否首次进入,为1时，查询页面首次登入；2时，为审批首次登入
	private String applyPerCode;			//个人统计查询
	private String rowName;			//列名
	
	private String querOfficeName;
	private String userName;

	public String getQuerOfficeName() {
		return querOfficeName;
	}
	public void setQuerOfficeName(String querOfficeName) {
		this.querOfficeName = querOfficeName;
	}
	public String getQuerDateStart() {
		return querDateStart;
	}
	public void setQuerDateStart(String querDateStart) {
		this.querDateStart = querDateStart;
	}
	public String getQuerDateEnd() {
		return querDateEnd;
	}
	public void setQuerDateEnd(String querDateEnd) {
		this.querDateEnd = querDateEnd;
	}
	public String getQuerOffice() {
		return querOffice;
	}
	public void setQuerOffice(String querOffice) {
		this.querOffice = querOffice;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getIsHide() {
		return isHide;
	}
	public void setIsHide(String isHide) {
		this.isHide = isHide;
	}
	public String getIsInit() {
		return isInit;
	}
	public void setIsInit(String isInit) {
		this.isInit = isInit;
	}
	
	public String getApplyPerCode() {
		return applyPerCode;
	}
	public void setApplyPerCode(String applyPerCode) {
		this.applyPerCode = applyPerCode;
	}
	
	public String getRowName() {
		return rowName;
	}
	public void setRowName(String rowName) {
		this.rowName = rowName;
	}
	@Override
	public String toString() {
		return "ExpensesReportInfo [projectName=" + projectName + ", status=" + status + ", querDateStart="
				+ querDateStart + ", querDateEnd=" + querDateEnd + ", querOffice=" + querOffice + ", isHide=" + isHide
				+ ", isInit=" + isInit + ", applyPerCode=" + applyPerCode + ", rowName=" + rowName + ", querOfficeName="
				+ querOfficeName + "]";
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
