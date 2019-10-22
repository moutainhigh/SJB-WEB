/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.intfz.request.contract;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 合同归档修改保存请求对象
 * @author wanxb
 * @version 2018-10-22 14:32:45
 */
@ApiModel(value="合同归档修改保存请求对象")
public class MainChangeContractHisRequest {
	
	@ApiModelProperty(value="归档合同id")
	private String contractHisId;  //归档合同id
	@ApiModelProperty(value="合同负责人id")
	private String contractLeaderId;		// 合同负责人id
	@ApiModelProperty(value="关联项目id")
	private List<String> projectIds;
	public String getContractHisId() {
		return contractHisId;
	}
	public void setContractHisId(String contractHisId) {
		this.contractHisId = contractHisId;
	}
	public String getContractLeaderId() {
		return contractLeaderId;
	}
	public void setContractLeaderId(String contractLeaderId) {
		this.contractLeaderId = contractLeaderId;
	}
	public List<String> getProjectIds() {
		return projectIds;
	}
	public void setProjectIds(List<String> projectIds) {
		this.projectIds = projectIds;
	}
	@Override
	public String toString() {
		return "MainChangeContractHisRequest [contractHisId=" + contractHisId + ", contractLeaderId=" + contractLeaderId
				+ ", projectIds=" + projectIds + "]";
	}
	
	

	
}