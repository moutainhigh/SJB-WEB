/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.sys.entity;

import com.sijibao.oa.common.persistence.DataEntity;

/**
 * 审批时长Entity
 * @author huangkai
 * @version 2019-01-03
 */
public class ApproveTime extends DataEntity<ApproveTime> {

	private static final long serialVersionUID = 1L;
	private String flowType;		// id
	private String finishCount;		// 职级编号
	private String finishTime;		// 职级说明
	private String startTime;
	private String endTime;



	public ApproveTime() {
		super();
	}

	public ApproveTime(String id){
		super(id);
	}

	@Override
	public String toString() {
		return "ApproveTime{" +
				"flowType='" + flowType + '\'' +
				", finishCount='" + finishCount + '\'' +
				", finishTime='" + finishTime + '\'' +
				", startTime='" + startTime + '\'' +
				", endTime='" + endTime + '\'' +
				'}';
	}

	public String getFlowType() {
		return flowType;
	}

	public void setFlowType(String flowType) {
		this.flowType = flowType;
	}

	public String getFinishCount() {
		return finishCount;
	}

	public void setFinishCount(String finishCount) {
		this.finishCount = finishCount;
	}

	public String getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(String finishTime) {
		this.finishTime = finishTime;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
}