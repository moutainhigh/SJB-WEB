/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.flow.entity;

import org.hibernate.validator.constraints.Length;

import com.sijibao.oa.common.persistence.DataEntity;

/**
 * 流程角色主表Entity
 * @author xby
 * @version 2018-06-20
 */
public class FlowProcRoleMain extends DataEntity<FlowProcRoleMain> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 流程角色名称
	private String code;		// 流程角色编码
	
	public FlowProcRoleMain() {
		super();
	}

	public FlowProcRoleMain(String id){
		super(id);
	}

	@Length(min=1, max=100, message="流程角色名称长度必须介于 1 和 100 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=1, max=32, message="流程角色编码长度必须介于 1 和 32 之间")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
}