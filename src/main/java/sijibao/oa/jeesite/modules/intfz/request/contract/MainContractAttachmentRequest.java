/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.intfz.request.contract;

import java.io.Serializable;

import com.sijibao.oa.modules.intfz.validator.IntfzValid;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 合同附件Entity
 * @author xby
 * @version 2018-07-12
 */
@ApiModel(value="合同归档附件接收对象")
public class MainContractAttachmentRequest implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@IntfzValid(min=0, max = 1, nullable=false)
	@ApiModelProperty(value="文件类别(1:预签合同图片，2：附件资料图片,3:合同扫描件,4:续签扫描件，5：废弃)")
	private String fileType;		// 文件类别
	@IntfzValid(min=0, max = 100, nullable=false)
	@ApiModelProperty(value="附件路径")
	private String contractAttachmentUrl;		// 附件路径
	
	@IntfzValid(min=0, max = 100, nullable=false)	
	@ApiModelProperty(value="附件名称")
	private String name;		// 附件名称

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
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

	@Override
	public String toString() {
		return "MainContractAttachmentRequest [fileType=" + fileType + ", contractAttachmentUrl="
				+ contractAttachmentUrl + ", name=" + name + "]";
	}


	
}