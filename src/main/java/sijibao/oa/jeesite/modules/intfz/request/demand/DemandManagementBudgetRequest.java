/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.intfz.request.demand;

import java.math.BigDecimal;
import java.util.Arrays;

import com.sijibao.oa.common.persistence.DataEntity;

import io.swagger.annotations.ApiModelProperty;

/**
 * 需求预算明细管理Entity
 * @author xuby
 * @version 2018-04-10
 */
public class DemandManagementBudgetRequest extends DataEntity<DemandManagementBudgetRequest> {
	
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value="开始日期")
	private long startDate;		// 开始日期
	@ApiModelProperty(value="起点")
	private String[] startPoint;		// 起点
	@ApiModelProperty(value="起点名称")
	private String[] startPointName;		// 起点
	@ApiModelProperty(value="结束日期")
	private long endDate;		// 结束日期
	@ApiModelProperty(value="终点")
	private String[] endPoint;		// 终点
	@ApiModelProperty(value="终点名称")
	private String[] endPointName;		// 终点
	@ApiModelProperty(value="一级科目")
	private String firstSub;		// 一级科目
	@ApiModelProperty(value="二级科目")
	private String secondSub;		// 二级科目
	@ApiModelProperty(value="科目数组")
	private String[] subject; //科目数组
	@ApiModelProperty(value="人数")
	private int personNum;		// 人数
	@ApiModelProperty(value="天数")
	private String dayNum;		// 天数
//	@ApiModelProperty(value="单据数量")
//	private int billNum;		// 单据数量
	@ApiModelProperty(value="预算金额")
	private BigDecimal expenseAmt;		// 预算金额
	@ApiModelProperty(value="备注")
	private String remarks; //备注
	
	public long getStartDate() {
		return startDate;
	}
	public void setStartDate(long startDate) {
		this.startDate = startDate;
	}
	public long getEndDate() {
		return endDate;
	}
	public void setEndDate(long endDate) {
		this.endDate = endDate;
	}
	public String getFirstSub() {
		return firstSub;
	}
	public void setFirstSub(String firstSub) {
		this.firstSub = firstSub;
	}
	public String getSecondSub() {
		return secondSub;
	}
	public void setSecondSub(String secondSub) {
		this.secondSub = secondSub;
	}
	public int getPersonNum() {
		return personNum;
	}
	public void setPersonNum(int personNum) {
		this.personNum = personNum;
	}
	public String getDayNum() {
		return dayNum;
	}
	public void setDayNum(String dayNum) {
		this.dayNum = dayNum;
	}
	public BigDecimal getExpenseAmt() {
		return expenseAmt;
	}
	public void setExpenseAmt(BigDecimal expenseAmt) {
		this.expenseAmt = expenseAmt;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String[] getStartPoint() {
		return startPoint;
	}
	public void setStartPoint(String[] startPoint) {
		this.startPoint = startPoint;
	}
	public String[] getStartPointName() {
		return startPointName;
	}
	public void setStartPointName(String[] startPointName) {
		this.startPointName = startPointName;
	}
	public String[] getEndPoint() {
		return endPoint;
	}
	public void setEndPoint(String[] endPoint) {
		this.endPoint = endPoint;
	}
	public String[] getEndPointName() {
		return endPointName;
	}
	public void setEndPointName(String[] endPointName) {
		this.endPointName = endPointName;
	}
	public String[] getSubject() {
		return subject;
	}
	public void setSubject(String[] subject) {
		this.subject = subject;
	}
	@Override
	public String toString() {
		return "DemandManagementBudgetRequest [startDate=" + startDate + ", startPoint=" + Arrays.toString(startPoint)
				+ ", startPointName=" + Arrays.toString(startPointName) + ", endDate=" + endDate + ", endPoint="
				+ Arrays.toString(endPoint) + ", endPointName=" + Arrays.toString(endPointName) + ", firstSub="
				+ firstSub + ", secondSub=" + secondSub + ", subject=" + Arrays.toString(subject) + ", personNum="
				+ personNum + ", dayNum=" + dayNum + ", expenseAmt=" + expenseAmt + ", remarks=" + remarks + "]";
	}
}