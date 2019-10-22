package sijibao.oa.jeesite.modules.intfz.request.contract;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * WEB合同后台添加-合同归档回执状态变更对象
 * @author wanxb
 * 2018-10-22 14:32:24
 */
@ApiModel(value="合同归档回执状态变更对象")
public class MainChangeReceiptReq implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value="合同id")
	private String contractId;//合同id
	@ApiModelProperty(value="回执状态：0无，1已处理，2不再续签")
    private String receiptStatus;//回执状态：0无，1已处理，2不再续签

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	public String getReceiptStatus() {
		return receiptStatus;
	}

	public void setReceiptStatus(String receiptStatus) {
		this.receiptStatus = receiptStatus;
	}

	@Override
	public String toString() {
		return "MainChangeReceiptReq{" +
				"contractId='" + contractId + '\'' +
				", receiptStatus='" + receiptStatus + '\'' +
				'}';
	}
}
