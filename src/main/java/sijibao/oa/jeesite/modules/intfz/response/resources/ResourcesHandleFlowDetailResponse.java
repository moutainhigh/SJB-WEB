package sijibao.oa.jeesite.modules.intfz.response.resources;

import java.util.List;

import com.sijibao.oa.modules.intfz.response.common.FlowLogResponse;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 资源办理详情对象
 * @author xby
 * @time 2017年12月27日 下午3:04:46
 */
@ApiModel(value="资源办理详情对象")
public class ResourcesHandleFlowDetailResponse {
	
	@ApiModelProperty(value="资源办理流程日志信息")
	private List<FlowLogResponse> flowLoglist;
	@ApiModelProperty(value="资源办理信息")
	private ResourcesHandleFlowResponse resourcesHandleFlowResponse;
	
	public ResourcesHandleFlowDetailResponse(){}
	public ResourcesHandleFlowDetailResponse(ResourcesHandleFlowResponse resourcesHandleFlowResponse, 
			List<FlowLogResponse> flowLoglist) {
		super();
		this.resourcesHandleFlowResponse = resourcesHandleFlowResponse;
		this.flowLoglist = flowLoglist;
	}
	public List<FlowLogResponse> getFlowLoglist() {
		return flowLoglist;
	}
	public void setFlowLoglist(List<FlowLogResponse> flowLoglist) {
		this.flowLoglist = flowLoglist;
	}
	public ResourcesHandleFlowResponse getResourcesHandleFlowResponse() {
		return resourcesHandleFlowResponse;
	}
	public void setResourcesHandleFlowResponse(ResourcesHandleFlowResponse resourcesHandleFlowResponse) {
		this.resourcesHandleFlowResponse = resourcesHandleFlowResponse;
	}
	
}
