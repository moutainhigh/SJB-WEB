package sijibao.oa.jeesite.modules.intfz.request.travel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 出差申请流程申请处理对象
 * @author xuby
 */
@ApiModel(value="出差申请流程申请处理对象")
public class TravelFlowHandleReq {

	@ApiModelProperty(value="出差申请ID")
	private String travelFlowId;//流程申请ID

	public String getTravelFlowId() {
		return travelFlowId;
	}

	public void setTravelFlowId(String travelFlowId) {
		this.travelFlowId = travelFlowId;
	}

	@Override
	public String toString() {
		return "TravelFlowHandleReq [travelFlowId=" + travelFlowId + "]";
	}
}
