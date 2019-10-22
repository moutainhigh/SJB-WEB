package sijibao.oa.jeesite.modules.intfz.request.custinfo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 客户合同pdf下载请求对象
 * @author xby
 */
@ApiModel(value="客户合同pdf下载请求对象")
public class CustDownloadContractPdfReq implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value="客户ID")
	private String custId; //客户ID
	@ApiModelProperty(value="客户名称编码")
	private String contractCompanyCode; //客户名称编码
	
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public String getContractCompanyCode() {
		return contractCompanyCode;
	}
	public void setContractCompanyCode(String contractCompanyCode) {
		this.contractCompanyCode = contractCompanyCode;
	}
}
