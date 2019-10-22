/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.oa.entity.test;

import com.sijibao.oa.common.persistence.DataEntity;

/**
 * 协作管理批量移动Entity
 * @author wanxb
 * @version 2019-01-24
 */

public class NeedMoveHis extends DataEntity<NeedMoveHis> {
	

	private String nId;
	private String moveCode;		//编号
	private String principal;		// 当前负责人id
	private String needFlowId;		// 协作管理流程ids
	private String needFlowIds;		// 协作管理流程ids

	public String getnId() {
		return nId;
	}

	public void setnId(String nId) {
		this.nId = nId;
	}

	public String getMoveCode() {
		return moveCode;
	}

	public void setMoveCode(String moveCode) {
		this.moveCode = moveCode;
	}

	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	public String getNeedFlowId() {
		return needFlowId;
	}

	public void setNeedFlowId(String needFlowId) {
		this.needFlowId = needFlowId;
	}

	public String getNeedFlowIds() {
		return needFlowIds;
	}

	public void setNeedFlowIds(String needFlowIds) {
		this.needFlowIds = needFlowIds;
	}

	@Override
	public String toString() {
		return "NeedMoveHis{" +
				"nId='" + nId + '\'' +
				", moveCode='" + moveCode + '\'' +
				", principal='" + principal + '\'' +
				", needFlowId='" + needFlowId + '\'' +
				", needFlowIds='" + needFlowIds + '\'' +
				'}';
	}
}