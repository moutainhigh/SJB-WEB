/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.oa.entity.test;

import com.sijibao.oa.common.persistence.DataEntity;

/**
 * 合同归档批量移动Entity
 * @author wanxb
 * @version 2019-01-24
 */

public class ContracthisMoveHis  extends DataEntity<ContracthisMoveHis> {
	
	private String cId;//id
	private String moveCode;			//编号
	private String contractLeaderId;		// 合同负责人id
	private String contractHisId;		// 所选合同ids
	private String contractHisIds;		// 所选合同ids

	public String getcId() {
		return cId;
	}

	public void setcId(String cId) {
		this.cId = cId;
	}

	public String getMoveCode() {
		return moveCode;
	}

	public void setMoveCode(String moveCode) {
		this.moveCode = moveCode;
	}

	public String getContractLeaderId() {
		return contractLeaderId;
	}

	public void setContractLeaderId(String contractLeaderId) {
		this.contractLeaderId = contractLeaderId;
	}

	public String getContractHisId() {
		return contractHisId;
	}

	public void setContractHisId(String contractHisId) {
		this.contractHisId = contractHisId;
	}

	public String getContractHisIds() {
		return contractHisIds;
	}

	public void setContractHisIds(String contractHisIds) {
		this.contractHisIds = contractHisIds;
	}
}