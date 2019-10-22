package sijibao.oa.jeesite.modules.intfz.request.daily;

import com.sijibao.base.common.provider.entity.PagerBase;

public class MainMarketDailyReq extends PagerBase<MainMarketDailyReq>  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String custName;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	@Override
	public String toString() {
		return "MainMarketDailyReq{" +
				"id='" + id + '\'' +
				", custName='" + custName + '\'' +
				'}';
	}
}
