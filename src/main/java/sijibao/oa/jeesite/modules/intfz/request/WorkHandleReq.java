package sijibao.oa.jeesite.modules.intfz.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 内务管理处理请求对象
 * @author Petter
 */
@ApiModel
public class WorkHandleReq {

	@ApiModelProperty(value="页数")
	private int pageNo;//页数
	@ApiModelProperty(value="每页数量")
	private int pageSize;//每页数量
	@ApiModelProperty(value="签到ID")
	private String signId;
	@ApiModelProperty(value="用户ID")
	private String userId;//用户ID
	@ApiModelProperty(value="用户姓名")
	private String userName;//用户姓名
	@ApiModelProperty(value="天列表时必填，yyyy-MM")
	private String workDate;
	@ApiModelProperty(value="开始年月yyyy-MM")
	private String startMonth;		//开始时间
	@ApiModelProperty(value="截止年月yyyy-MM")
	private String endMonth;		//截止时间
	
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public String getSignId() {
		return signId;
	}
	public void setSignId(String signId) {
		this.signId = signId;
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
	public String getStartMonth() {
		return startMonth;
	}
	public void setStartMonth(String startMonth) {
		this.startMonth = startMonth;
	}
	public String getEndMonth() {
		return endMonth;
	}
	public void setEndMonth(String endMonth) {
		this.endMonth = endMonth;
	}
	@Override
	public String toString() {
		return "WorkHandleReq [pageNo=" + pageNo + ", pageSize=" + pageSize + ", signId=" + signId + ", userId="
				+ userId + ", userName=" + userName + ", workDate=" + workDate + ", startMonth=" + startMonth
				+ ", endMonth=" + endMonth + "]";
	}
	
}
