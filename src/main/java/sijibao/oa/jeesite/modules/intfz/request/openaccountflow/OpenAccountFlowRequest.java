/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.intfz.request.openaccountflow;

import java.util.List;

import com.sijibao.oa.modules.intfz.validator.IntfzValid;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 开户申请request
 * @author wanxb
 * @version 2018-05-30
 */
@ApiModel(value="开户申请--申请对象")
public class OpenAccountFlowRequest{
	@IntfzValid(min=0, max = 32, nullable=true)
	@ApiModelProperty(value="主键ID")
	private String id;  //主键ID
	@ApiModelProperty(value="流程实例ID")
	private String procInsId;		// 流程实例ID
	@ApiModelProperty(value="流程编号")
	private String procCode;		// 流程编号
	@ApiModelProperty(value="流程名称")
	private String procName;		// 流程名称
	@ApiModelProperty(value="开户编号")
	private String openAccountCode;		// 开户编号
	@ApiModelProperty(value="合同编号")
	private String contractCode;		// 合同编号
	@ApiModelProperty(value="合同名称")
	private String contractName;		// 合同名称
	@ApiModelProperty(value="甲方名称")
	private String firstPartyName;		// 甲方名称
	@ApiModelProperty(value="市场负责人id")
	private String marketLeaderId;		// 市场负责人id
	@ApiModelProperty(value="附件信息")
	private List<OpenAccountAttachmentRequest> openAccountAttachmentRequest;//附件信息
	
	public String getProcInsId() {
		return procInsId;
	}
	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}
	public String getProcCode() {
		return procCode;
	}
	public void setProcCode(String procCode) {
		this.procCode = procCode;
	}
	public String getProcName() {
		return procName;
	}
	public void setProcName(String procName) {
		this.procName = procName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOpenAccountCode() {
		return openAccountCode;
	}
	public void setOpenAccountCode(String openAccountCode) {
		this.openAccountCode = openAccountCode;
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
	public String getFirstPartyName() {
		return firstPartyName;
	}
	public void setFirstPartyName(String firstPartyName) {
		this.firstPartyName = firstPartyName;
	}
	public String getMarketLeaderId() {
		return marketLeaderId;
	}
	public void setMarketLeaderId(String marketLeaderId) {
		this.marketLeaderId = marketLeaderId;
	}
	public List<OpenAccountAttachmentRequest> getOpenAccountAttachmentRequest() {
		return openAccountAttachmentRequest;
	}
	public void setOpenAccountAttachmentRequest(List<OpenAccountAttachmentRequest> openAccountAttachmentRequest) {
		this.openAccountAttachmentRequest = openAccountAttachmentRequest;
	}
	@Override
	public String toString() {
		return "OpenAccountFlowRequest [id=" + id + ", procInsId=" + procInsId + ", procCode=" + procCode
				+ ", procName=" + procName + ", openAccountCode=" + openAccountCode + ", contractCode=" + contractCode
				+ ", contractName=" + contractName + ", firstPartyName=" + firstPartyName + ", marketLeaderId="
				+ marketLeaderId + ", openAccountAttachmentRequest=" + openAccountAttachmentRequest + "]";
	}
	
	
}