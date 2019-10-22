/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.flow.entity;

import org.hibernate.validator.constraints.Length;

import com.sijibao.oa.common.persistence.DataEntity;

/**
 * 流程角色明细表Entity
 * @author xby
 * @version 2018-06-20
 */
public class FlowProcRoleDetail extends DataEntity<FlowProcRoleDetail> {
	
	private static final long serialVersionUID = 1L;
	private String mainId;		// 流程角色主表ID
	private String userId;		// 审批人ID
	private String userName; //审批人名称
	private String orgId;		// 管辖机构ID
	private String orgName;     //机构名称
	public FlowProcRoleDetail() {
		super();
	}

	public FlowProcRoleDetail(String id){
		super(id);
	}

	@Length(min=1, max=32, message="流程角色主表ID长度必须介于 1 和 32 之间")
	public String getMainId() {
		return mainId;
	}

	public void setMainId(String mainId) {
		this.mainId = mainId;
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	
}