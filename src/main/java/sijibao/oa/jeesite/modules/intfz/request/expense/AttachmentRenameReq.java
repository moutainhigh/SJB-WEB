package sijibao.oa.jeesite.modules.intfz.request.expense;

import io.swagger.annotations.ApiModelProperty;

/**
 * 附件上传接收参数实体
 * @author xuby
 *
 */
public class AttachmentRenameReq {
	@ApiModelProperty(value="文件URL")
	private String url; //附件URL
	@ApiModelProperty(value="文件名称")
	private String fileName; //文件名称
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	@Override
	public String toString() {
		return "AttachmentRenameReq [url=" + url + ", fileName=" + fileName + "]";
	}
	
}
