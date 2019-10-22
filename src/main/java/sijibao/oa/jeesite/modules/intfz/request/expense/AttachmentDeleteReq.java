package sijibao.oa.jeesite.modules.intfz.request.expense;

import io.swagger.annotations.ApiModelProperty;

/**
 * 附件上传接收参数实体
 * @author xuby
 *
 */
public class AttachmentDeleteReq {
	@ApiModelProperty(value="附件URL")
	private String url; //附件URL

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "AttachmentDeleteReq [url=" + url + "]";
	}
	
}
