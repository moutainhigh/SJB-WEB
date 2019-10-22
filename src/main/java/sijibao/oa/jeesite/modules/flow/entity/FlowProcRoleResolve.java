/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.flow.entity;

import org.hibernate.validator.constraints.Length;

import com.sijibao.oa.common.persistence.DataEntity;

/**
 * 流程角色明细解析Entity
 * @author xby
 * @version 2018-06-20
 */
public class FlowProcRoleResolve extends DataEntity<FlowProcRoleResolve> {
	
	private static final long serialVersionUID = 1L;
	private String detailId;		// 流程角色明细信息表ID
	private String userLoginName;		// 审批人用户登录名
	private String userName;		// 审批人名称
	private String orgId;		// 机构ID
	private String orgName;		// 机构名称
	
	private String flowPorcRoleCode;  //流程角色编码

	public FlowProcRoleResolve() {
		super();
	}

	public FlowProcRoleResolve(String id){
		super(id);
	}

	@Length(min=1, max=32, message="流程角色明细信息表ID长度必须介于 1 和 32 之间")
	public String getDetailId() {
		return detailId;
	}

	public void setDetailId(String detailId) {
		this.detailId = detailId;
	}
	
	@Length(min=1, max=32, message="审批人用户登录名长度必须介于 1 和 32 之间")
	public String getUserLoginName() {
		return userLoginName;
	}

	public void setUserLoginName(String userLoginName) {
		this.userLoginName = userLoginName;
	}
	
	@Length(min=1, max=32, message="审批人名称长度必须介于 1 和 32 之间")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Length(min=1, max=32, message="机构ID长度必须介于 1 和 32 之间")
	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	
	@Length(min=1, max=100, message="机构名称长度必须介于 1 和 100 之间")
	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getFlowPorcRoleCode() {
		return flowPorcRoleCode;
	}

	public void setFlowPorcRoleCode(String flowPorcRoleCode) {
		this.flowPorcRoleCode = flowPorcRoleCode;
	}

}