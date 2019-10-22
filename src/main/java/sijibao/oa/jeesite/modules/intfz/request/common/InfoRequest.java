package sijibao.oa.jeesite.modules.intfz.request.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 工作及内务接收实体对象
 * @author wanxb
 */
@ApiModel(value="工作内务对象")
public class InfoRequest {
	
	@ApiModelProperty(value="需要提交明细单的年份用户id")
	private String userId;
	@ApiModelProperty(value="需要提交明细单的年份")
	private String year;
	@ApiModelProperty(value="需要提交明细单的月份")
	private String month;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	@Override
	public String toString() {
		return "WorkRequest [userId=" + userId + ", year=" + year + ", month=" + month + "]";
	}
}
