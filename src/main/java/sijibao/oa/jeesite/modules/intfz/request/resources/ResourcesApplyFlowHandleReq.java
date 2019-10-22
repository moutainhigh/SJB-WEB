package sijibao.oa.jeesite.modules.intfz.request.resources;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 资源申请流程申请处理对象
 * @author xuby
 */
@ApiModel(value="资源申请流程申请处理对象")
public class ResourcesApplyFlowHandleReq {

	@ApiModelProperty(value="资源申请ID")
	private String resApplyFlowId;//流程申请ID

	public String getResApplyFlowId() {
		return resApplyFlowId;
	}

	public void setResApplyFlowId(String resApplyFlowId) {
		this.resApplyFlowId = resApplyFlowId;
	}

	@Override
	public String toString() {
		return "ResourcesApplyFlowHandleReq [resApplyFlowId=" + resApplyFlowId + "]";
	}
	
}
