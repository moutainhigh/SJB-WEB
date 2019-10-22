package sijibao.oa.jeesite.modules.intfz.response.common;


import io.swagger.annotations.ApiModelProperty;

/**
 * 客户信息返回对象信息
 * @author wanxb
 *
 */
public class SysVersionResponse {
	@ApiModelProperty(value="版本编号")
	private String versionNo;		// 版本编号
	@ApiModelProperty(value="内容")
	private String context;		// 内容
	@ApiModelProperty(value="创建时间")
	private String createTime;	//创建时间
	public String getVersionNo() {
		return versionNo;
	}
	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	@Override
	public String toString() {
		return "SysVersionResponse [versionNo=" + versionNo + ", context=" + context + ", createTime=" + createTime
				+ "]";
	}
	
}
