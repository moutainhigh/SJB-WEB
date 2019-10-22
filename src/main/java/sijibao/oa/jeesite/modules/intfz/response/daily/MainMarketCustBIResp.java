/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.intfz.response.daily;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

/**
 * 日报-市场日志Entity
 * @author wanxb
 * @version 2018-12-12
 */
public class MainMarketCustBIResp implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "客户名称")
	private String custName;//客户名称
	@ApiModelProperty(value = "访问类型")
	private String visitType;//访问类型
	@ApiModelProperty(value = "提交时间")
	private long  commitTime;//提交时间

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getVisitType() {
		return visitType;
	}

	public void setVisitType(String visitType) {
		this.visitType = visitType;
	}

	public long getCommitTime() {
		return commitTime;
	}

	public void setCommitTime(long commitTime) {
		this.commitTime = commitTime;
	}

	@Override
	public String toString() {
		return "MainMarketCustBIResp{" +
				"custName='" + custName + '\'' +
				", visitType='" + visitType + '\'' +
				", commitTime=" + commitTime +
				'}';
	}
}