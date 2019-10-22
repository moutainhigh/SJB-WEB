/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.flow.entity;

import org.hibernate.validator.constraints.Length;

import com.sijibao.oa.common.persistence.DataEntity;

/**
 * 流程机构Entity
 * @author xby
 * @version 2018-06-25
 */
public class FlowProcOrg extends DataEntity<FlowProcOrg> {
	
	private static final long serialVersionUID = 1L;
	private String procCode;		// 流程编码
	private String procKey; //流程Key
	private String orgId;		// 机构ID
	private String orgName;     //机构名称
	private String billType;		// 单据类型
	private String billTypeName; //单据类型名称
	public FlowProcOrg() {
		super();
	}

	public FlowProcOrg(String id){
		super(id);
	}

	@Length(min=1, max=32, message="流程编码长度必须介于 1 和 32 之间")
	public String getProcCode() {
		return procCode;
	}

	public void setProcCode(String procCode) {
		this.procCode = procCode;
	}
	
	@Length(min=1, max=32, message="机构ID长度必须介于 1 和 32 之间")
	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	
	@Length(min=1, max=8, message="单据类型长度必须介于 1 和 8 之间")
	public String getBillType() {
		return billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getBillTypeName() {
		return billTypeName;
	}

	public void setBillTypeName(String billTypeName) {
		this.billTypeName = billTypeName;
	}

	public String getProcKey() {
		return procKey;
	}

	public void setProcKey(String procKey) {
		this.procKey = procKey;
	}
}