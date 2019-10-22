/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.intfz.response.contractnew;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 合同附件返回对象
 * @author xby
 * @version 2018-07-12
 */
@ApiModel(value="合同附件返回对象")
public class ContractAttachmentNewResponse{
	
	@ApiModelProperty(value="合同编号")
	private String contractProcCode;		// 合同编号
	
	@ApiModelProperty(value="附件路径")
	private String contractAttachmentUrl;		// 附件路径
	
	@ApiModelProperty(value="附件名称")
	private String name;		// 附件名称
	
	@ApiModelProperty(value="文件类别")
	private String fileType;		// 文件类别
	
	@ApiModelProperty(value="附件前缀")
	private String urlPrefix; //附件前缀
	
	public ContractAttachmentNewResponse(){}
	
	public String getContractProcCode() {
		return contractProcCode;
	}

	public void setContractProcCode(String contractProcCode) {
		this.contractProcCode = contractProcCode;
	}

	public String getContractAttachmentUrl() {
		return contractAttachmentUrl;
	}

	public void setContractAttachmentUrl(String contractAttachmentUrl) {
		this.contractAttachmentUrl = contractAttachmentUrl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getUrlPrefix() {
		return urlPrefix;
	}

	public void setUrlPrefix(String urlPrefix) {
		this.urlPrefix = urlPrefix;
	}

	@Override
	public String toString() {
		return "ContractAttachmentResponse [contractProcCode=" + contractProcCode + ", contractAttachmentUrl="
				+ contractAttachmentUrl + ", name=" + name + ", fileType=" + fileType + ", urlPrefix=" + urlPrefix
				+ "]";
	}
	
}