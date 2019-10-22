package sijibao.oa.jeesite.modules.intfz.request.intfzwebreq.projectinfo;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 客户管理-批量移动客户请求对象
 * @author wanxb
 *
 */
@ApiModel(value="客户管理-批量移动客户请求对象")
public class MainProjectBatchMoveReq implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value="项目负责人id")
	private String projectLeaderId;		// 项目负责人id
	@ApiModelProperty(value="实施负责人id")
	private String impleLeaderId;		// 实施负责人id
	@ApiModelProperty(value="所选项目id")
	private List<String> projectIds;//所选客户id
	@ApiModelProperty(value="商务助理")
	private String businessAssistantId;//商务助理
	@ApiModelProperty(value="项目管理负责人")
	private String projectManagerId;//项目管理负责人
	@ApiModelProperty(value="VIP客服")
	private String vipCustomerId;//VIP客服
	@ApiModelProperty(value="清结算")
	private String accountLeaderId;//清结算

	public String getProjectLeaderId() {
		return projectLeaderId;
	}

	public void setProjectLeaderId(String projectLeaderId) {
		this.projectLeaderId = projectLeaderId;
	}

	public String getImpleLeaderId() {
		return impleLeaderId;
	}

	public void setImpleLeaderId(String impleLeaderId) {
		this.impleLeaderId = impleLeaderId;
	}

	public List<String> getProjectIds() {
		return projectIds;
	}

	public void setProjectIds(List<String> projectIds) {
		this.projectIds = projectIds;
	}

	public String getBusinessAssistantId() {
		return businessAssistantId;
	}

	public void setBusinessAssistantId(String businessAssistantId) {
		this.businessAssistantId = businessAssistantId;
	}

	public String getProjectManagerId() {
		return projectManagerId;
	}

	public void setProjectManagerId(String projectManagerId) {
		this.projectManagerId = projectManagerId;
	}

	public String getVipCustomerId() {
		return vipCustomerId;
	}

	public void setVipCustomerId(String vipCustomerId) {
		this.vipCustomerId = vipCustomerId;
	}

	public String getAccountLeaderId() {
		return accountLeaderId;
	}

	public void setAccountLeaderId(String accountLeaderId) {
		this.accountLeaderId = accountLeaderId;
	}

}
