/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.oa.entity;

import org.hibernate.validator.constraints.Length;

import com.sijibao.oa.common.persistence.DataEntity;

/**
 * 合同归档附件Entity
 * @author wanxb
 * @version 2018-10-22
 */
public class ContractAttachmentHis extends DataEntity<ContractAttachmentHis> {
	
	private static final long serialVersionUID = 1L;
	private String contractCode;		// 合同编号
	private String oaContractAttachmentUrl;		// 附件路径
	private String fileName;		// 附件名称
	private String fileType;		// 文件类别
	
	public ContractAttachmentHis() {
		super();
	}

	public ContractAttachmentHis(String id){
		super(id);
	}

	@Length(min=1, max=64, message="合同编号长度必须介于 1 和 64 之间")
	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	
	@Length(min=0, max=200, message="附件路径长度必须介于 0 和 200 之间")
	public String getOaContractAttachmentUrl() {
		return oaContractAttachmentUrl;
	}

	public void setOaContractAttachmentUrl(String oaContractAttachmentUrl) {
		this.oaContractAttachmentUrl = oaContractAttachmentUrl;
	}
	
	@Length(min=0, max=100, message="附件名称长度必须介于 0 和 100 之间")
	public String getFileName() {
		return fileName;
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