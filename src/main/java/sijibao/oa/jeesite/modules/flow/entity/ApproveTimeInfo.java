/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.flow.entity;

import com.sijibao.oa.common.persistence.DataEntity;

/**
 * 审批时长Entity
 * @author huangkai
 * @version 2019-01-03
 */
public class ApproveTimeInfo extends DataEntity<ApproveTimeInfo> {

	private static final long serialVersionUID = 1L;
	private String countDate;
	private String expenseAvgTime;
	private String recpAvgTime;
	private String travelAvgTime;
	private String contractAvgTime;
	private String totalAvgTime;



	public ApproveTimeInfo() {
		super();
	}

	public ApproveTimeInfo(String id){
		super(id);
	}

	@Override
	public String toString() {
		return "ApproveTimeInfo{" +
				"countDate='" + countDate + '\'' +
				", expenseAvgTime='" + expenseAvgTime + '\'' +
				", recpAvgTime='" + recpAvgTime + '\'' +
				", travelAvgTime='" + travelAvgTime + '\'' +
				", contractAvgTime='" + contractAvgTime + '\'' +
				", totalAvgTime='" + totalAvgTime + '\'' +
				'}';
	}

	public String getCountDate() {
		return countDate;
	}

	public void setCountDate(String countDate) {
		this.countDate = countDate;
	}

	public String getExpenseAvgTime() {
		return expenseAvgTime;
	}

	public void setExpenseAvgTime(String expenseAvgTime) {
		this.expenseAvgTime = expenseAvgTime;
	}

	public String getRecpAvgTime() {
		return recpAvgTime;
	}

	public void setRecpAvgTime(String recpAvgTime) {
		this.recpAvgTime = recpAvgTime;
	}

	public String getTravelAvgTime() {
		return travelAvgTime;
	}

	public void setTravelAvgTime(String travelAvgTime) {
		this.travelAvgTime = travelAvgTime;
	}

	public String getContractAvgTime() {
		return contractAvgTime;
	}

	public void setContractAvgTime(String contractAvgTime) {
		this.contractAvgTime = contractAvgTime;
	}

	public String getTotalAvgTime() {
		return totalAvgTime;
	}

	public void setTotalAvgTime(String totalAvgTime) {
		this.totalAvgTime = totalAvgTime;
	}
}