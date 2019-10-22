package sijibao.oa.jeesite.modules.intfz.request.intfzwebreq;

import io.swagger.annotations.ApiModelProperty;

public class WebAttachmentReq {
	@ApiModelProperty(value="图片URL")
	private String url;
	@ApiModelProperty(value="图片名称")
	private String name;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "WebAttachmentReq [url=" + url + ", name=" + name + "]";
	}
	
}
