package sijibao.oa.jeesite.modules.intfz.request.contract;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 合同管理流程完成任务请求对象
 * @author xuby
 */
@ApiModel(value="合同管理流程完成任务请求对象")
public class ContractFlowCompleteTaskReq {
	@ApiModelProperty(value="流程实例ID")
	private String procInsId; //流程实例ID
	@ApiModelProperty(value="审批意见状态：yes/no")
	private String flag; //审批意见状态
	@ApiModelProperty(value="审批意见：同意/驳回")
	private String comment; //审批意见
	@ApiModelProperty(value="流程申请ID")
	private String contractFlowId; //业务流程ID
	
	@ApiModelProperty(value="合同附件扫描件信息")
	private List<MainContractAttachmentRequest> contractAttachmentList;//附件扫描件信息
	public String getProcInsId() {
		return procInsId;
	}
	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getContractFlowId() {
		return contractFlowId;
	}
	public void setContractFlowId(String contractFlowId) {
		this.contractFlowId = contractFlowId;
	}
	public List<MainContractAttachmentRequest> getContractAttachmentList() {
		return contractAttachmentList;
	}
	public void setContractAttachmentList(List<MainContractAttachmentRequest> contractAttachmentList) {
		this.contractAttachmentList = contractAttachmentList;
	}
	@Override
	public String toString() {
		return "ContractFlowCompleteTaskReq [procInsId=" + procInsId + ", flag=" + flag + ", comment=" + comment
				+ ", contractFlowId=" + contractFlowId + ", contractAttachmentList=" + contractAttachmentList + "]";
	}
}
