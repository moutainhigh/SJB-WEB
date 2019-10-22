/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.oa.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sijibao.oa.common.persistence.DataEntity;

/**
 * 需求预算明细管理Entity
 * @author xuby
 * @version 2018-03-28
 */
public class DemandManagementBudget extends DataEntity<DemandManagementBudget> {
	
	private static final long serialVersionUID = 1L;
	private String procCode;		// 流程编号
	private String userCode;		// 实施申请人编码
	private Date startDate;		// 开始日期
	private String startPoint;		// 起点
	private String startPointName;		// 起点名称
	private Date endDate;		// 结束日期
	private String endPoint;		// 终点
	private String endPointName;		// 终点名称
	private String firstSub;		// 一级科目
	private String firstSubName;	// 一级科目名称
	private String secondSub;		// 二级科目
	private String secondSubName;	// 二级科目名称
	private String personNum;		// 人数
	private String dayNum;		// 天数
//	private String billNum;		// 单据数量
	private String expenseAmt;		// 预算金额
	private int rowNum; //明细行号
	private String firstEnable;//科目状态：1停用，0启用
	private String secondEnable;//科目状态：1停用，0启用

	public String getFirstEnable() {
		return firstEnable;
	}

	public void setFirstEnable(String firstEnable) {
		this.firstEnable = firstEnable;
	}

	public String getSecondEnable() {
		return secondEnable;
	}

	public void setSecondEnable(String secondEnable) {
		this.secondEnable = secondEnable;
	}

	public DemandManagementBudget() {
		super();
	}

	public DemandManagementBudget(String id){
		super(id);
	}

	@Length(min=0, max=16, message="流程编号长度必须介于 0 和 16 之间")
	public String getProcCode() {
		return procCode;
	}

	public void setProcCode(String procCode) {
		this.procCode = procCode;
	}
	
	@Length(min=1, max=32, message="实施申请人编码长度必须介于 1 和 32 之间")
	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	@Length(min=0, max=100, message="起点长度必须介于 0 和 100 之间")
	public String getStartPoint() {
		return startPoint;
	}

	public void setStartPoint(String startPoint) {
		this.startPoint = startPoint;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	@Length(min=0, max=100, message="终点长度必须介于 0 和 100 之间")
	public String getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(String endPoint) {
		this.endPoint = endPoint;
	}
	
	@Length(min=0, max=50, message="一级科目长度必须介于 0 和 50 之间")
	public String getFirstSub() {
		return firstSub;
	}

	public void setFirstSub(String firstSub) {
		this.firstSub = firstSub;
	}
	
	@Length(min=0, max=50, message="二级科目长度必须介于 0 和 50 之间")
	public String getSecondSub() {
		return secondSub;
	}

	public void setSecondSub(String secondSub) {
		this.secondSub = secondSub;
	}
	
	@Length(min=0, max=5, message="人数长度必须介于 0 和 5 之间")
	public String getPersonNum() {
		return personNum;
	}

	public void setPersonNum(String personNum) {
		this.personNum = personNum;
	}
	
	@Length(min=0, max=5, message="天数长度必须介于 0 和 5 之间")
	public String getDayNum() {
		return dayNum;
	}

	public void setDayNum(String dayNum) {
		this.dayNum = dayNum;
	}
	
	public String getExpenseAmt() {
		return expenseAmt;
	}

	public void setExpenseAmt(String expenseAmt) {
		this.expenseAmt = expenseAmt;
	}

	public String getFirstSubName() {
		return firstSubName;
	}

	public void setFirstSubName(String firstSubName) {
		this.firstSubName = firstSubName;
	}

	public String getSecondSubName() {
		return secondSubName;
	}

	public void setSecondSubName(String secondSubName) {
		this.secondSubName = secondSubName;
	}

	public String getStartPointName() {
		return startPointName;
	}

	public void setStartPointName(String startPointName) {
		this.startPointName = startPointName;
	}

	public String getEndPointName() {
		return endPointName;
	}

	public void setEndPointName(String endPointName) {
		this.endPointName = endPointName;
	}

	public int getRowNum() {
		return rowNum;
	}

	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}
}