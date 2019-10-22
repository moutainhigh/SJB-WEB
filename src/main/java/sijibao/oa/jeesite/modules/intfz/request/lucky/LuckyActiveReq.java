package sijibao.oa.jeesite.modules.intfz.request.lucky;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

/**
 * 年会活动Entity
 * @author xby
 * @version 2019-01-14
 */
public class LuckyActiveReq implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value="入参：活动开始时间")
	private String startDate;
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
}