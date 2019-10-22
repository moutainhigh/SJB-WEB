package sijibao.oa.jeesite.modules.intfz.request.report;

import com.sijibao.oa.modules.intfz.request.common.PageRequest;

import io.swagger.annotations.ApiModel;

/**
 * 员工状态月报表接收对象
 * @author xuby
 * @date 2018-09-26
 */
@ApiModel(value="员工状态月报表接收对象")
public class EmpStatusMonthReportRequest extends PageRequest{
	
	/**
	 * 年月
	 */
	private long dateTime;
	
	/**
	 * 用户ID
	 */
	private String userId;

	public long getDateTime() {
		return dateTime;
	}

	public void setDateTime(long dateTime) {
		this.dateTime = dateTime;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "EmpStatusMonthReportRequest [dateTime=" + dateTime + ", userId=" + userId + "]";
	}
	
}
