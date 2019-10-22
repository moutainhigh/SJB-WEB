/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.flow.entity;

import org.hibernate.validator.constraints.Length;

import com.sijibao.oa.common.persistence.DataEntity;

/**
 * 合同附件Entity
 * @author wanxb
 * @version 2018-07-05
 */
public class ContractAttachment extends DataEntity<ContractAttachment> {
	
	private static final long serialVersionUID = 1L;
	private String contractProcCode;		// 合同编号
	private String contractAttachmentUrl;		// 附件路径
	private String fileName;		// 附件名称
	private String fileType;		// 文件类别
	
	public ContractAttachment() {
		super();
	}

	public ContractAttachment(String id){
		super(id);
	}
	
	public String getContractProcCode() {
		return contractProcCode;
	}

	public void setContractProcCode(String contractProcCode) {
		this.contractProcCode = contractProcCode;
	}

	@Length(min=0, max=100, message="附件名称长度必须介于 0 和 100 之间")
	public String getFileName() {
		return fileName;
	}

	public String getContractAttachmentUrl() {
		return contractAttachmentUrl;
	}

	public void setContractAttachmentUrl(String contractAttachmentUrl) {
		this.contractAttachmentUrl = contractAttachmentUrl;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	@Length(min=0, max=100, message="文件类别长度必须介于 0 和 100 之间")
	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	
}