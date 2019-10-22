package sijibao.oa.jeesite.modules.intfz.request.contractnew;

import com.sijibao.oa.modules.intfz.request.common.PageRequest;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 合同配置信息获取请求对象
 * @author xuby
 *
 */
@ApiModel(value="合同配置信息获取请求对象")
public class ContractConfigMainRequest extends PageRequest{
		
	
	/**
	 * 主键ID
	 */
	@ApiModelProperty(value="合同名称ID")
	private String id;
	
	/**
	 * 合同模版编码
	 */
	@ApiModelProperty(value="合同名称编码")
	private String contractCode;
	
	/**
	 * 合同模版名称
	 */
	@ApiModelProperty(value="合同名称名称")
	private String contractName;
	
	public ContractConfigMainRequest(){}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	@Override
	public String toString() {
		return "ContractConfigMainRequest [id=" + id + ", contractCode=" + contractCode + ", contractName="
				+ contractName + "]";
	}
	
}
