package sijibao.oa.jeesite.modules.intfz.request.contractnew;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 合同管理列表查询
 * @author xby
 */
@ApiModel
public class ContractInfoPartyNameReq implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value="合同方类别(1甲方，2乙方，3丙方)")
	private String memberType;
	@ApiModelProperty(value="名称(模糊搜索)")
	private String memberName;
	public String getMemberType() {
		return memberType;
	}
	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	@Override
	public String toString() {
		return "ContractInfoPartyNameReq [memberType=" + memberType + ", memberName=" + memberName + "]";
	}
}
