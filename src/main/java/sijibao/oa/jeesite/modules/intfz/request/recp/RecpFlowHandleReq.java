package sijibao.oa.jeesite.modules.intfz.request.recp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 接待申请流程申请处理对象
 * @author xuby
 */
@ApiModel(value="接待申请流程申请处理对象")
public class RecpFlowHandleReq {

	@ApiModelProperty(value="接待申请ID")
	private String recpFlowId;//流程申请ID

	public String getRecpFlowId() {
		return recpFlowId;
	}

	public void setRecpFlowId(String recpFlowId) {
		this.recpFlowId = recpFlowId;
	}

	@Override
	public String toString() {
		return "RecpFlowHandleReq [recpFlowId=" + recpFlowId + "]";
	}
	
}
