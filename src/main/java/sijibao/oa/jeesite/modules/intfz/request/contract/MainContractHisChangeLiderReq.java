package sijibao.oa.jeesite.modules.intfz.request.contract;

import com.sijibao.oa.modules.intfz.request.common.PageRequest;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * WEB合同归档服务-负责人修改记录
 * @author wanxb
 * 2018-10-22 14:32:24
 */
@ApiModel(value="WEB合同归档服务-负责人修改记录")
public class MainContractHisChangeLiderReq  extends PageRequest{

	@ApiModelProperty(value="合同负责人id")
	private String contractId;		// 合同负责人id

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	@Override
	public String toString() {
		return "MainContractHisChangeLiderReq [contractId=" + contractId + "]";
	}

	
}
