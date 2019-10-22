package sijibao.oa.jeesite.modules.intfz.response.contract;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;


/**
 * 合同方配置信息放回对象
 * @author xby
 * @version 2018-10-23
 */
public class MainContractSuppResp implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value="补充协议ID")
	private String suppId;
	@ApiModelProperty(value="补充协议name")
	private String suppName;
	public String getSuppId() {
		return suppId;
	}
	public void setSuppId(String suppId) {
		this.suppId = suppId;
	}
	public String getSuppName() {
		return suppName;
	}
	public void setSuppName(String suppName) {
		this.suppName = suppName;
	}
	@Override
	public String toString() {
		return "ContractSuppResp [suppId=" + suppId + ", suppName=" + suppName + "]";
	}
	
	
}
