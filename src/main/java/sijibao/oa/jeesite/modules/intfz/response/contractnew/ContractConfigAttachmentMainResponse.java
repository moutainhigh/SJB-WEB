package sijibao.oa.jeesite.modules.intfz.response.contractnew;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 合同附件配置Entity
 * @author xby
 * @version 2018-10-24
 */
@ApiModel(value="合同附件配置对象")
public class ContractConfigAttachmentMainResponse implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value="合同模版ID")
	private String contractId;		// 合同模版表id
	
	@ApiModelProperty(value="合同方：1预签合同，2合同扫描件，3附件资料")
	private String attachmentType;		// 合同方：1预签合同，2合同扫描件，3附件资料
	
	@ApiModelProperty(value="最大上传个数")
	private String maxCount;		// 最大上传个数
	
	@ApiModelProperty(value="必传个数")
	private String mustCount;		// 必传个数
	
	@ApiModelProperty(value="文件类型img/pdf，逗号分隔")
	private String fileType;		// 文件类型img/pdf，逗号分隔
	
	@ApiModelProperty(value="单个文件大小限制，单位M")
	private String maxSize;		// 单个文件大小限制，单位M
	
	@ApiModelProperty(value="提示文案")
	private String message;		// 提示文案
	
	@ApiModelProperty(value="配置状态0:未启用，1：启用")
	private String confStatus;		// 配置状态0:未启用，1：启用
	
	public ContractConfigAttachmentMainResponse(){}

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	public String getAttachmentType() {
		return attachmentType;
	}

	public void setAttachmentType(String attachmentType) {
		this.attachmentType = attachmentType;
	}

	public String getMaxCount() {
		return maxCount;
	}

	public void setMaxCount(String maxCount) {
		this.maxCount = maxCount;
	}

	public String getMustCount() {
		return mustCount;
	}

	public void setMustCount(String mustCount) {
		this.mustCount = mustCount;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(String maxSize) {
		this.maxSize = maxSize;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getConfStatus() {
		return confStatus;
	}

	public void setConfStatus(String confStatus) {
		this.confStatus = confStatus;
	}

	@Override
	public String toString() {
		return "ContractConfigAttachmentResponse [contractId=" + contractId + ", attachmentType=" + attachmentType
				+ ", maxCount=" + maxCount + ", mustCount=" + mustCount + ", fileType=" + fileType + ", maxSize="
				+ maxSize + ", message=" + message + ", confStatus=" + confStatus + "]";
	}
	
}