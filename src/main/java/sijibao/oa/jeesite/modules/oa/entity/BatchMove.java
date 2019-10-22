/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.oa.entity;

import java.util.List;

import com.sijibao.oa.common.persistence.DataEntity;

/**
 * 客户信息表
 * @author wanxb
 * @version 2018年8月14日 15:40:48
 */
public class BatchMove extends DataEntity<BatchMove> {
	
	private static final long serialVersionUID = 1L;
	private String marketLeaderId;		// 市场负责人id
	private String marketLeaderName;		// 市场负责人id
	private String marketLeaderPhone;
	private String custOfficeId;//客户归属区域id
	private String custStage;		// 客户阶段
	private String custTrades;		// 所属行业：煤炭，渣土，水泥，钢铁
	private List<String> custIds;//所选客户id
	private String id; //客户ID
	private String custListPlace;//客户所在位置
	
	public String getMarketLeaderId() {
		return marketLeaderId;
	}
	public void setMarketLeaderId(String marketLeaderId) {
		this.marketLeaderId = marketLeaderId;
	}
	public String getCustOfficeId() {
		return custOfficeId;
	}
	public void setCustOfficeId(String custOfficeId) {
		this.custOfficeId = custOfficeId;
	}
	public String getCustStage() {
		return custStage;
	}
	public void setCustStage(String custStage) {
		this.custStage = custStage;
	}
	public String getCustTrades() {
		return custTrades;
	}
	public void setCustTrades(String custTrades) {
		this.custTrades = custTrades;
	}
	public List<String> getCustIds() {
		return custIds;
	}
	public void setCustIds(List<String> custIds) {
		this.custIds = custIds;
	}
	
	public String getMarketLeaderName() {
		return marketLeaderName;
	}
	public void setMarketLeaderName(String marketLeaderName) {
		this.marketLeaderName = marketLeaderName;
	}
	public String getCustListPlace() {
		return custListPlace;
	}
	public void setCustListPlace(String custListPlace) {
		this.custListPlace = custListPlace;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMarketLeaderPhone() {
		return marketLeaderPhone;
	}
	public void setMarketLeaderPhone(String marketLeaderPhone) {
		this.marketLeaderPhone = marketLeaderPhone;
	}
	@Override
	public String toString() {
		return "BatchMove [marketLeaderId=" + marketLeaderId + ", marketLeaderName=" + marketLeaderName
				+ ", marketLeaderPhone=" + marketLeaderPhone + ", custOfficeId=" + custOfficeId + ", custStage="
				+ custStage + ", custTrades=" + custTrades + ", custIds=" + custIds + ", id=" + id + ", custListPlace="
				+ custListPlace + "]";
	}
	
}