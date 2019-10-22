/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.flow.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sijibao.oa.common.persistence.DataEntity;

/**
 * 报销费用明细Entity
 * @author Petter
 * @version 2017-12-25
 */
public class ExpenseDetail extends DataEntity<ExpenseDetail> {
	
	private static final long serialVersionUID = 1L;
	private String procCode;		// 流程编号
	private Date startDate;		// 开始日期
	private String startPoint;		// 起点
	private String startPointName;
	private Date endDate;		// 结束日期
	private String endPoint;		// 终点
	private String endPointName;
	private String firstSub;		// 一级科目
	private String secondSub;		// 二级科目
	private String personNum;		// 人数
	private String dayNum;		// 天数
	private String billNum;		// 单据数量
	private BigDecimal expenseAmt;		// 报销金额
	private String firstSubName;	// 一级科目name
	private String secondSubName;	// 二级科目name
	private int rowNum; //行号
	private ExpenseFlow expenseFlow; //报销主表
	private List<ExpenseAttachment> expenseAttachment = new ArrayList<ExpenseAttachment>(); //
	private String detailOrderBy; //明细排序
	private String firstEnable;//科目状态：1停用，0启用
	private String secondEnable;//科目状态：1停用，0启用
	private String enable;//科目状态：1停用，0启用

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

	public String getEnable() {
		return enable;
	}

	public void setEnable(String enable) {
		this.enable = enable;
	}

	public ExpenseDetail() {
		super();
	}

	public ExpenseDetail(String id){
		super(id);
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

	@Length(min=0, max=16, message="流程编号长度必须介于 0 和 16 之间")
	public String getProcCode() {
		return procCode;
	}

	public void setProcCode(String procCode) {
		this.procCode = procCode;
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
	
	@Length(min=0, max=5, message="单据数量长度必须介于 0 和 5 之间")
	public String getBillNum() {
		return billNum;
	}

	public void setBillNum(String billNum) {
		this.billNum = billNum;
	}
	
	public BigDecimal getExpenseAmt() {
		return expenseAmt;
	}

	public void setExpenseAmt(BigDecimal expenseAmt) {
		this.expenseAmt = expenseAmt;
	}

	public String getFirstSubName() {
		return firstSubName;
	}

	public String getSecondSubName() {
		return secondSubName;
	}

	public void setFirstSubName(String firstSubName) {
		this.firstSubName = firstSubName;
	}

	public void setSecondSubName(String secondSubName) {
		this.secondSubName = secondSubName;
	}

	public ExpenseFlow getExpenseFlow() {
		return expenseFlow;
	}

	public void setExpenseFlow(ExpenseFlow expenseFlow) {
		this.expenseFlow = expenseFlow;
	}

	public List<ExpenseAttachment> getExpenseAttachment() {
		return expenseAttachment;
	}

	public void setExpenseAttachment(List<ExpenseAttachment> expenseAttachment) {
		this.expenseAttachment = expenseAttachment;
	}

	public int getRowNum() {
		return rowNum;
	}

	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}

	public String getDetailOrderBy() {
		return detailOrderBy;
	}

	public void setDetailOrderBy(String detailOrderBy) {
		this.detailOrderBy = detailOrderBy;
	}
	
}