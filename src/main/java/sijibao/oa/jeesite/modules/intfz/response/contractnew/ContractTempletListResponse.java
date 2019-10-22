package sijibao.oa.jeesite.modules.intfz.response.contractnew;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 合同模版列表返回对象
 * @author xby
 * @version 2018-07-12
 */
@ApiModel(value="合同模版列表返回对象")
public class ContractTempletListResponse {
	
	@ApiModelProperty(value="合同模版ID")
	private String id; //合同模版ID
	
	@ApiModelProperty(value="合同模版code")
	private String code; //合同模版code
	
	@ApiModelProperty(value="合同模版名称")
	private String contractName; //合同模版名称

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "ContractTempletListResponse [id=" + id + ", code=" + code + ", contractName=" + contractName + "]";
	}

}
