package sijibao.oa.jeesite.modules.intfz.response.resources;

import java.util.List;

import com.sijibao.oa.modules.intfz.response.common.FlowLogResponse;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 资源申请详情对象
 * @author xby
 * @time 2017年12月27日 下午3:04:46
 */
@ApiModel(value="资源申请详情对象")
public class ResourcesApplyFlowDetailResponse {
	
	@ApiModelProperty(value="资源申请流程日志信息")
	private List<FlowLogResponse> flowLoglist;
	@ApiModelProperty(value="资源申请信息")
	private ResourcesApplyFlowResponse resourcesApplyFlowResponse;
	
	public ResourcesApplyFlowDetailResponse(){}
	public ResourcesApplyFlowDetailResponse(ResourcesApplyFlowResponse resourcesApplyFlowResponse, 
			List<FlowLogResponse> flowLoglist) {
		super();
		this.resourcesApplyFlowResponse = resourcesApplyFlowResponse;
		this.flowLoglist = flowLoglist;
	}
	public List<FlowLogResponse> getFlowLoglist() {
		return flowLoglist;
	}
	public void setFlowLoglist(List<FlowLogResponse> flowLoglist) {
		this.flowLoglist = flowLoglist;
	}
	public ResourcesApplyFlowResponse getResourcesApplyFlowResponse() {
		return resourcesApplyFlowResponse;
	}
	public void setResourcesApplyFlowResponse(ResourcesApplyFlowResponse resourcesApplyFlowResponse) {
		this.resourcesApplyFlowResponse = resourcesApplyFlowResponse;
	}
	
}
