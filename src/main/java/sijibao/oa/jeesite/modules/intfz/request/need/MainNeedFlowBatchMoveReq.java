/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.intfz.request.need;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;

/**
 * 协作类型管理Entity
 * @author wanxb
 * @version 2018-11-23
 */
public class MainNeedFlowBatchMoveReq{
	
	@ApiModelProperty(value="合同负责人id")
	private String principal;//当前负责人id
	@ApiModelProperty(value="所选合同ids")
	private List<String> needFlowIds;//协作管理流程ids
	public String getPrincipal() {
		return principal;
	}
	public void setPrincipal(String principal) {
		this.principal = principal;
	}
	public List<String> getNeedFlowIds() {
		return needFlowIds;
	}
	public void setNeedFlowIds(List<String> needFlowIds) {
		this.needFlowIds = needFlowIds;
	}
	@Override
	public String toString() {
		return "MainNeedFlowBatchMoveReq [principal=" + principal + ", needFlowIds=" + needFlowIds + "]";
	}
	
}