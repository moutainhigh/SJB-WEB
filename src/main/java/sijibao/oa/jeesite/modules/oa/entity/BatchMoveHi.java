/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.oa.entity;

import org.hibernate.validator.constraints.Length;

import com.sijibao.oa.common.persistence.DataEntity;

/**
 * 客户批量移动实体Entity
 * @author wanxb
 * @version 2018-08-20
 */
public class BatchMoveHi extends DataEntity<BatchMoveHi> {
	
	private static final long serialVersionUID = 1L;
	private String marketLeaderId;		// 市场负责人id
	private String custOfficeId;		// 客户移入归属区域id
	private String custStage;		// 客户阶段
	private String custTrades;		// 行业
	private String custIds;		// 客户移入ids
	
	public BatchMoveHi() {
		super();
	}

	public BatchMoveHi(String id){
		super(id);
	}

	@Length(min=1, max=32, message="市场负责人id长度必须介于 1 和 32 之间")
	public String getMarketLeaderId() {
		return marketLeaderId;
	}

	public void setMarketLeaderId(String marketLeaderId) {
		this.marketLeaderId = marketLeaderId;
	}
	
	@Length(min=1, max=32, message="客户移入归属区域id长度必须介于 1 和 32 之间")
	public String getCustOfficeId() {
		return custOfficeId;
	}

	public void setCustOfficeId(String custOfficeId) {
		this.custOfficeId = custOfficeId;
	}
	
	@Length(min=1, max=32, message="客户阶段长度必须介于 1 和 32 之间")
	public String getCustStage() {
		return custStage;
	}

	public void setCustStage(String custStage) {
		this.custStage = custStage;
	}
	
	@Length(min=1, max=32, message="行业长度必须介于 1 和 32 之间")
	public String getCustTrades() {
		return custTrades;
	}

	public void setCustTrades(String custTrades) {
		this.custTrades = custTrades;
	}
	
	@Length(min=1, max=1000, message="客户移入ids长度必须介于 1 和 1000 之间")
	public String getCustIds() {
		return custIds;
	}

	public void setCustIds(String custIds) {
		this.custIds = custIds;
	}

	@Override
	public String toString() {
		return "BatchMoveHi [marketLeaderId=" + marketLeaderId + ", custOfficeId=" + custOfficeId + ", custStage="
				+ custStage + ", custTrades=" + custTrades + ", custIds=" + custIds + "]";
	}
	
	
}