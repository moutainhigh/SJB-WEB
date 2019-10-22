/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.flow.entity;

import org.hibernate.validator.constraints.Length;

import com.sijibao.oa.common.persistence.DataEntity;

/**
 * 附件信息Entity
 * @author wanxb
 * @version 2018-07-03
 */
public class OpenAccountAttachment extends DataEntity<OpenAccountAttachment> {
	
	private static final long serialVersionUID = 1L;
	private String openAccountCode;		// 合同编号
	private String openAccountAttachmentUrl;		// 附件路径
	private String fileName;		// 附件名称
	private String fileType;		// 文件类别
	
	public OpenAccountAttachment() {
		super();
	}

	public OpenAccountAttachment(String id){
		super(id);
	}

	@Length(min=1, max=64, message="合同编号长度必须介于 1 和 64 之间")
	public String getOpenAccountCode() {
		return openAccountCode;
	}

	public void setOpenAccountCode(String openAccountCode) {
		this.openAccountCode = openAccountCode;
	}
	
	@Length(min=0, max=200, message="附件路径长度必须介于 0 和 200 之间")
	public String getOpenAccountAttachmentUrl() {
		return openAccountAttachmentUrl;
	}

	public void setOpenAccountAttachmentUrl(String openAccountAttachmentUrl) {
		this.openAccountAttachmentUrl = openAccountAttachmentUrl;
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