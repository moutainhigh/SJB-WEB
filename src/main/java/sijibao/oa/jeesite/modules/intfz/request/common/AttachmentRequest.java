package sijibao.oa.jeesite.modules.intfz.request.common;

import com.sijibao.oa.modules.intfz.validator.IntfzValid;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 附件请求实体对象
 * @author wanxb
 */
@ApiModel(value="附件请求实体对象")
public class AttachmentRequest {
	@IntfzValid(min=0, max = 100, nullable=false)
	@ApiModelProperty(value="附件路径")
	private String contractAttachmentUrl;		// 附件路径
	
	@IntfzValid(min=0, max = 100, nullable=false)	
	@ApiModelProperty(value="附件名称")
	private String name;		// 附件名称

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




}
