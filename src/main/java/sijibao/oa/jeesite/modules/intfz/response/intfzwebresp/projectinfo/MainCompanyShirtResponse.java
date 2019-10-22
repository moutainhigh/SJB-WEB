package sijibao.oa.jeesite.modules.intfz.response.intfzwebresp.projectinfo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="项目管理--企业简短信息返回对象")
public class MainCompanyShirtResponse implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value="企业编码")
	private String companyCode;//企业编码
	@ApiModelProperty(value="企业名称")
	private String companyName;//企业名称
	@ApiModelProperty(value="企业code")
	private String holderCode;
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getHolderCode() {
		return holderCode;
	}
	public void setHolderCode(String holderCode) {
		this.holderCode = holderCode;
	}
	@Override
	public String toString() {
		return "MainCompanyShirtResponse [companyCode=" + companyCode + ", companyName=" + companyName + ", holderCode="
				+ holderCode + "]";
	}
	
	
	
	
}
