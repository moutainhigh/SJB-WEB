package sijibao.oa.jeesite.modules.intfz.request.expense;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import com.sijibao.oa.modules.intfz.validator.IntfzValid;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 报销明细接收实体
 * @author xuby
 */
@ApiModel(value="报销流程明细对象")
public class ExpenseFlowDetailRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @IntfzValid(min=0, max = 32, nullable=false)
	@ApiModelProperty(value="开始日期")
	private long startDate;		// 开始日期
	
	@IntfzValid(min=0, max = 50, nullable=false)
	@ApiModelProperty(value="起点")
	private String[] startPoint;		// 起点
	
	@ApiModelProperty(value="起点名称")
	private String[] startPointName;		// 起点
	
	@IntfzValid(min=0, max = 32, nullable=false)
	@ApiModelProperty(value="结束日期")
	private long endDate;		// 结束日期
	@IntfzValid(min=0, max = 100, nullable=false)
	private String[] endPoint;		// 终点
	@ApiModelProperty(value="终点名称")
	private String[] endPointName;		// 终点
	
//	@IntfzValid(min=0, max = 32, nullable=false)
	@ApiModelProperty(value="一级科目")
	private String firstSub;		// 一级科目
	@ApiModelProperty(value="二级科目")
	private String secondSub;		// 二级科目
	@ApiModelProperty(value="科目数组")
	private String[] subject;  //科目数组 
	
	@IntfzValid(min=0, max = 32, nullable=false)
	@ApiModelProperty(value="人数")
	private int personNum;		// 人数
	
	@IntfzValid(min=0, max = 32, nullable=false)
	@ApiModelProperty(value="天数")
	private String dayNum;		// 天数
	
	@IntfzValid(min=0, max = 32, nullable=false)
	@ApiModelProperty(value="单据数量")
	private int billNum;		// 单据数量
	
	@IntfzValid(min=0, max = 32, nullable=false)
	@ApiModelProperty(value="报销金额")
	private BigDecimal expenseAmt;		// 报销金额
	@ApiModelProperty(value="备注")
	private String remarks; //备注
	@ApiModelProperty(value="科目明细附件")
	private List<ExpenseFlowAttachmentRequest> subConfList; //明细科目附件
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
	public int getBillNum() {
		return billNum;
	}
	public void setBillNum(int billNum) {
		this.billNum = billNum;
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
	public List<ExpenseFlowAttachmentRequest> getSubConfList() {
		return subConfList;
	}
	public void setSubConfList(List<ExpenseFlowAttachmentRequest> subConfList) {
		this.subConfList = subConfList;
	}
	
	public String[] getSubject() {
		return subject;
	}
	public void setSubject(String[] subject) {
		this.subject = subject;
	}
	@Override
	public String toString() {
		return "ExpenseDetailRequest [startDate=" + startDate + ", startPoint=" + Arrays.toString(startPoint)
				+ ", startPointName=" + Arrays.toString(startPointName) + ", endDate=" + endDate + ", endPoint="
				+ Arrays.toString(endPoint) + ", endPointName=" + Arrays.toString(endPointName) + ", firstSub="
				+ firstSub + ", secondSub=" + secondSub + ", subject=" + Arrays.toString(subject) + ", personNum="
				+ personNum + ", dayNum=" + dayNum + ", billNum=" + billNum + ", expenseAmt=" + expenseAmt
				+ ", remarks=" + remarks + ", subConfList=" + subConfList + "]";
	}
	
}
