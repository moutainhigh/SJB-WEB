/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.intfz.request.contract;

import com.sijibao.oa.modules.intfz.request.common.PageRequest;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 合同信息Entity
 * @author xby
 * @version 2018-07-12
 */
@ApiModel(value="合同信息接收对象")
public class FirstCompanyInfoHandleRequest extends PageRequest{
	
	@ApiModelProperty(value="客户名称")
	private String custName;  //客户名称
	@ApiModelProperty(value="市场负责人姓名")
	private String marketLeaderName;//市场负责人姓名
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getMarketLeaderName() {
		return marketLeaderName;
	}
	public void setMarketLeaderName(String marketLeaderName) {
		this.marketLeaderName = marketLeaderName;
	}
	@Override
	public String toString() {
		return "FirstCompanyInfoHandleRequest [custName=" + custName + ", marketLeaderName=" + marketLeaderName + "]";
	}
	
}