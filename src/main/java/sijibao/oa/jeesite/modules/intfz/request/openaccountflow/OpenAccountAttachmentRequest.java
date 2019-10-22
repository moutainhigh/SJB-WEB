/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.intfz.request.openaccountflow;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 费用附件Entity
 * @author wanxb
 */
@ApiModel(value="附件接收对象")
public class OpenAccountAttachmentRequest{
	@ApiModelProperty(value="附件路径")
	private String openAccountAttachmentUrl;// 附件路径
	@ApiModelProperty(value="附件名称")
	private String fileName;// 附件名称
	@ApiModelProperty(value="文件类别")
	private String fileType;// 文件类别
	public String getOpenAccountAttachmentUrl() {
		return openAccountAttachmentUrl;
	}
	public void setOpenAccountAttachmentUrl(String openAccountAttachmentUrl) {
		this.openAccountAttachmentUrl = openAccountAttachmentUrl;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	
}