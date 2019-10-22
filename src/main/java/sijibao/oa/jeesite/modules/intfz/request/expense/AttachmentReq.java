package sijibao.oa.jeesite.modules.intfz.request.expense;

import io.swagger.annotations.ApiModelProperty;

/**
 * 附件上传接收参数实体
 * @author xuby
 *
 */
public class AttachmentReq {
	@ApiModelProperty(value="科目附件配置ID")
	private String id; //科目附件配置ID
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "AttachmentReq [id=" + id + "]";
	}
	
}
