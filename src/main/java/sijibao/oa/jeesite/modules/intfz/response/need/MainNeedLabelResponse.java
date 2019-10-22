package sijibao.oa.jeesite.modules.intfz.response.need;

/**
 * 合同方配置信息放回对象
 * @author wanxb
 * @version 2018-11-23 09:41:38
 */
public class MainNeedLabelResponse{
	
	private String id;
	private String labelCode;		// 标签编号
	private String labelName;		// 标签名称
	private String delFlag;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLabelCode() {
		return labelCode;
	}
	public void setLabelCode(String labelCode) {
		this.labelCode = labelCode;
	}
	public String getLabelName() {
		return labelName;
	}
	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}
	public String getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	@Override
	public String toString() {
		return "MainNeedLabelResponse [id=" + id + ", labelCode=" + labelCode + ", labelName=" + labelName
				+ ", delFlag=" + delFlag + "]";
	}
	

	
}
