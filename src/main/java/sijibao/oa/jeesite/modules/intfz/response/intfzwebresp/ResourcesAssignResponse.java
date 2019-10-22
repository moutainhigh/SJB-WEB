package sijibao.oa.jeesite.modules.intfz.response.intfzwebresp;

import io.swagger.annotations.ApiModelProperty;

/**
 * 指派返回对象信息
 * @author wanxb
 *
 */
public class ResourcesAssignResponse {
	@ApiModelProperty(value="指派者id")
	private String sourceAssign;// 指派者id
	@ApiModelProperty(value="指派者name")
	private String sourceAssignName;		// 指派者name
	@ApiModelProperty(value="被指派者id")
	private String targetAssign;// 被指派者id
	@ApiModelProperty(value="被指派者name")
	private String targetAssignName;		// 被指派者name
	@ApiModelProperty(value=" 满足人数")
	private int personelNum;		// 满足人数
	@ApiModelProperty(value="审批时间")
	private String updateTime;		// 审批时间
	@ApiModelProperty(value="备注")
	private String remarks;	// 备注
	
	public String getTargetAssignName() {
		return targetAssignName;
	}
	public void setTargetAssignName(String targetAssignName) {
		this.targetAssignName = targetAssignName;
	}
	public int getPersonelNum() {
		return personelNum;
	}
	public void setPersonelNum(int personelNum) {
		this.personelNum = personelNum;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	
	public String getSourceAssignName() {
		return sourceAssignName;
	}
	public void setSourceAssignName(String sourceAssignName) {
		this.sourceAssignName = sourceAssignName;
	}
	public String getSourceAssign() {
		return sourceAssign;
	}
	public void setSourceAssign(String sourceAssign) {
		this.sourceAssign = sourceAssign;
	}
	public String getTargetAssign() {
		return targetAssign;
	}
	public void setTargetAssign(String targetAssign) {
		this.targetAssign = targetAssign;
	}
	@Override
	public String toString() {
		return "ResourcesAssignResponse [sourceAssign=" + sourceAssign + ", sourceAssignName=" + sourceAssignName
				+ ", targetAssign=" + targetAssign + ", targetAssignName=" + targetAssignName + ", personelNum="
				+ personelNum + ", updateTime=" + updateTime + ", remarks=" + remarks + "]";
	}
	
}
