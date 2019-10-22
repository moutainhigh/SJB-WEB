/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.oa.entity;

import org.hibernate.validator.constraints.Length;

import com.sijibao.oa.common.persistence.DataEntity;

/**
 * 贸易结构Entity
 * @author wanxb
 * @version 2018-05-25
 */
public class CustTradeStructure extends DataEntity<CustTradeStructure> {
	
	private static final long serialVersionUID = 1L;
	private String custId;		// 客户id
	private String upstream;		// 上游
	private String downstream;		// 下游
	
	public CustTradeStructure() {
		super();
	}

	public CustTradeStructure(String id){
		super(id);
	}

	@Length(min=1, max=32, message="客户id长度必须介于 1 和 32 之间")
	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}
	
	@Length(min=0, max=1000, message="上游长度必须介于 0 和 1000 之间")
	public String getUpstream() {
		return upstream;
	}

	public void setUpstream(String upstream) {
		this.upstream = upstream;
	}
	
	@Length(min=0, max=1000, message="下游长度必须介于 0 和 1000 之间")
	public String getDownstream() {
		return downstream;
	}

	public void setDownstream(String downstream) {
		this.downstream = downstream;
	}
	
}