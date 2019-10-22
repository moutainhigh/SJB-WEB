package sijibao.oa.jeesite.modules.intfz.request.report;

import com.sijibao.oa.modules.intfz.request.common.PageRequest;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 员工状态日报表接收对象
 * @author xuby
 * @date 2018-09-16
 */
@ApiModel(value="员工状态日报表接收对象")
public class EmpStatusDayReportRequest  extends PageRequest{

	/**
	 * 时间
	 */
	@ApiModelProperty(value="时间")
	private long dateTime; //时间

	/**
	 * 人员状态
	 */
	@ApiModelProperty(value="人员状态")
	private String empStatus; //人员状态

	/**
	 * 项目或节点模糊匹配
	 */
	@ApiModelProperty(value="项目或节点模糊匹配")
	private String textQuery;  //项目或节点模糊匹配

	/**
	 * 用户ID
	 */
	@ApiModelProperty(value="用户ID")
	private String userId;  //用户ID

	/**
	 * 基地id
	 */
	@ApiModelProperty(value = "基地id")
	private String baseId;

	/**
	 * 人员id或部门id
	 */
	@ApiModelProperty(value = "人员id或部门id")
	private String userIdOrDeptId;

	public long getDateTime() {
		return dateTime;
	}

	public void setDateTime(long dateTime) {
		this.dateTime = dateTime;
	}

	public String getEmpStatus() {
		return empStatus;
	}

	public void setEmpStatus(String empStatus) {
		this.empStatus = empStatus;
	}

	public String getTextQuery() {
		return textQuery;
	}

	public void setTextQuery(String textQuery) {
		this.textQuery = textQuery;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getBaseId() {
		return baseId;
	}

	public void setBaseId(String baseId) {
		this.baseId = baseId;
	}

	public String getUserIdOrDeptId() {
		return userIdOrDeptId;
	}

	public void setUserIdOrDeptId(String userIdOrDeptId) {
		this.userIdOrDeptId = userIdOrDeptId;
	}

	@Override
	public String toString() {
		return "EmpStatusDayReportRequest{" +
				"dateTime=" + dateTime +
				", empStatus='" + empStatus + '\'' +
				", textQuery='" + textQuery + '\'' +
				", userId='" + userId + '\'' +
				", baseId='" + baseId + '\'' +
				", userIdOrDeptId='" + userIdOrDeptId + '\'' +
				'}';
	}
}
