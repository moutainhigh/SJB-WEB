package sijibao.oa.jeesite.modules.intfz.response.contractnew;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 合同公司信息列表返回对象
 * @author xby
 * @version 2018-10-22
 */
@ApiModel(value="合同公司信息列表返回对象")
public class ContractCompanyListResponse {
	
	@ApiModelProperty(value="合同公司ID")
	private String id; //合同模版ID
	
	@ApiModelProperty(value="合同公司名称")
	private String companyName; //合同模版名称
	
	@ApiModelProperty(value="统一信用代码")
	private String creditCode; //统一信用代码
	
	@ApiModelProperty(value="住所")
	private String address; //住所
	
	@ApiModelProperty(value="法定代表人")
	private String legalRepresentative; //法定代表人

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCreditCode() {
		return creditCode;
	}

	public void setCreditCode(String creditCode) {
		this.creditCode = creditCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLegalRepresentative() {
		return legalRepresentative;
	}

	public void setLegalRepresentative(String legalRepresentative) {
		this.legalRepresentative = legalRepresentative;
	}

	@Override
	public String toString() {
		return "ContractCompanyListResponse [id=" + id + ", companyName=" + companyName + ", creditCode=" + creditCode
				+ ", address=" + address + ", legalRepresentative=" + legalRepresentative + "]";
	}
}
