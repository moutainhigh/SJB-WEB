package sijibao.oa.jeesite.modules.intfz.request.resources;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 资源办理流程申请处理对象
 * @author xuby
 */
@ApiModel(value="资源办理流程申请处理对象")
public class ResourcesHandleFlowHandleReq {

	@ApiModelProperty(value="资源办理ID")
	private String resHandleFlowId;//流程申请ID
	
	public String getResHandleFlowId() {
		return resHandleFlowId;
	}

	public void setResHandleFlowId(String resHandleFlowId) {
		this.resHandleFlowId = resHandleFlowId;
	}

	@Override
	public String toString() {
		return "ResourcesHandleFlowHandleReq [resHandleFlowId=" + resHandleFlowId + "]";
	}
	
}
