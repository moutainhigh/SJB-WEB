/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.intfz.response.expense;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 费用附件Entity
 * @author xuby
 * @version 2018-04-26
 */
@ApiModel(value="科目附件信息")
public class ExpenseAttachmentResponse{
	
	@ApiModelProperty(value="图片URL")
	private String url; //科目图片路径
	@ApiModelProperty(value="id")
	private String id; //科目图片控制ID
	@ApiModelProperty(value="科目附件描述")
	private String confDesc; //科目附件描述
	@ApiModelProperty(value="图片前缀")
	private String urlPrefix; //图片前缀
	public ExpenseAttachmentResponse() {
		super();
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getConfDesc() {
		return confDesc;
	}

	public void setConfDesc(String confDesc) {
		this.confDesc = confDesc;
	}
	
	public String getUrlPrefix() {
		return urlPrefix;
	}

	public void setUrlPrefix(String urlPrefix) {
		this.urlPrefix = urlPrefix;
	}

	@Override
	public String toString() {
		return "ExpenseAttachmentRequest [url=" + url + ", id=" + id + ", confDesc=" + confDesc + "]";
	}
}