package sijibao.oa.jeesite.modules.intfz.request.contract;

import java.util.List;

import com.sijibao.oa.modules.intfz.request.common.PageRequest;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * WEB合同归档服务-负责人批量移动请求对象
 * @author wanxb
 * 2018-10-22 14:33:02
 */
@ApiModel(value="WEB合同归档服务-负责人批量移动请求对象")
public class MainContractHisBatchMoveReq extends PageRequest{

	@ApiModelProperty(value="合同负责人id")
	private String contractLeaderId;//合同负责人id
	@ApiModelProperty(value="所选合同ids")
	private List<String> contractHisIds;//所选合同ids
	public String getContractLeaderId() {
		return contractLeaderId;
	}
	public void setContractLeaderId(String contractLeaderId) {
		this.contractLeaderId = contractLeaderId;
	}
	public List<String> getContractHisIds() {
		return contractHisIds;
	}
	public void setContractHisIds(List<String> contractHisIds) {
		this.contractHisIds = contractHisIds;
	}
	@Override
	public String toString() {
		return "MainContractHisBatchMoveReq [contractLeaderId=" + contractLeaderId + ", contractHisIds="
				+ contractHisIds + "]";
	}
	
	
	
}
