package sijibao.oa.jeesite.modules.intfz.response;

import java.math.BigDecimal;

import com.sijibao.oa.common.utils.DateUtils;
import com.sijibao.oa.common.utils.StringUtils;
import com.sijibao.oa.modules.intfz.utils.Constant;
import com.sijibao.oa.modules.oa.entity.DemandManagementBudget;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 实施需求预算明细Entity
 */
@ApiModel(value="实施需求明细信息")
public class DemandManagementBudgetResponse {
	
	@ApiModelProperty(value="流程编号")
	private String procCode;		// 流程编号
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
	@ApiModelProperty(value="一级科目编号")
	private String firstSub;		// 一级科目
	@ApiModelProperty(value="一级科目名称")
	private String firstSubName;	// 一级科目name
	@ApiModelProperty(value="二级科目编号")
	private String secondSub;		// 二级科目
	@ApiModelProperty(value="二级科目名称")
	private String secondSubName;	// 二级科目name
	@ApiModelProperty(value="科目数组")
	private String[] subject; //科目数组
	@ApiModelProperty(value="科目名称数组")
	private String[] subjectName; //科目名称数组
	@ApiModelProperty(value="人数")
	private Integer personNum;		// 人数
	@ApiModelProperty(value="天数")
	private Integer dayNum;		// 天数
//	@ApiModelProperty(value="票据数量")
//	private Integer billNum;		// 单据数量
	@ApiModelProperty(value="报销金额")
	private BigDecimal expenseAmt;		// 报销金额
	@ApiModelProperty(value="创建时间")
	private String createTime;		//创建时间
	@ApiModelProperty(value="备注")
	private String remarks;			//备注
	
	public DemandManagementBudgetResponse(DemandManagementBudget demandManagementBudget) {
		super();
		this.procCode = demandManagementBudget.getProcCode();
		if(demandManagementBudget.getStartDate() != null){
			this.startDate =demandManagementBudget.getStartDate().getTime();
		}
		if(demandManagementBudget.getEndDate() != null){
			this.endDate = demandManagementBudget.getEndDate().getTime();
		}
		this.firstSub = demandManagementBudget.getFirstSub();
		this.secondSub = demandManagementBudget.getSecondSub();
		if(StringUtils.isNotBlank(demandManagementBudget.getPersonNum())){
			this.personNum = Integer.valueOf(demandManagementBudget.getPersonNum());
		}else{
			this.personNum = 0;
		}
		if(StringUtils.isNotBlank(demandManagementBudget.getDayNum())){
			this.dayNum = Integer.valueOf(demandManagementBudget.getDayNum());
		}else{
			this.dayNum = 0;
		}
		this.expenseAmt = new BigDecimal(demandManagementBudget.getExpenseAmt()==null?"0":demandManagementBudget.getExpenseAmt());
		if(demandManagementBudget.getCreateTime() != null){
			this.createTime = DateUtils.formatDate(demandManagementBudget.getCreateTime(), DateUtils.YYYY_MM_DD_HH_MM_SS);
		}
		this.remarks = demandManagementBudget.getRemarks() == null ? "": demandManagementBudget.getRemarks();
		this.firstSubName = demandManagementBudget.getFirstSubName();
		this.secondSubName = demandManagementBudget.getSecondSubName() == null ? "": demandManagementBudget.getSecondSubName();
	}
	
	public DemandManagementBudgetResponse(DemandManagementBudget demandManagementBudget,String[] startPoint,String[] startPointName,String[] endPoint,String[] endPointName,String clientType) {
		super();
		this.procCode = demandManagementBudget.getProcCode();
		if(demandManagementBudget.getStartDate() != null){
			this.startDate = demandManagementBudget.getStartDate().getTime();
		}
		this.startPoint = startPoint;
		this.startPointName = startPointName;
		if(demandManagementBudget.getEndDate() != null){
			this.endDate = demandManagementBudget.getEndDate().getTime();
		}
		this.endPoint = endPoint;
		this.endPointName = endPointName;
		this.firstSub = demandManagementBudget.getFirstSub();
		this.secondSub = demandManagementBudget.getSecondSub();
		if(Constant.CLIENT_TYPE_APP.equals(clientType)){
			String[] subject = new String[2];
			String[] subjectName = new String[2];
			if(StringUtils.isBlank(demandManagementBudget.getSecondSub())){
				subject[0] =  demandManagementBudget.getFirstSub();
				subject[1] =  "no_"+demandManagementBudget.getFirstSub();
				subjectName = new String[1];
				subjectName[0] = demandManagementBudget.getFirstSubName();
			}else{
				subject[0] =  demandManagementBudget.getFirstSub();
				subject[1] =  demandManagementBudget.getSecondSub();
				subjectName[0] = demandManagementBudget.getFirstSubName();
				subjectName[1] = demandManagementBudget.getSecondSubName();
			}
			this.subject = subject;
			this.subjectName = subjectName;
		}
		if(StringUtils.isNotBlank(demandManagementBudget.getPersonNum())){
			this.personNum = Integer.valueOf(demandManagementBudget.getPersonNum());
		}else{
			this.personNum = 0;
		}
		if(StringUtils.isNotBlank(demandManagementBudget.getDayNum())){
			this.dayNum = Integer.valueOf(demandManagementBudget.getDayNum());
		}else{
			this.dayNum = 0;
		}
//		this.expenseAmt = new BigDecimal(demandManagementBudget.getExpenseAmt()==null?"0":demandManagementBudget.getExpenseAmt()); 注释掉的原来的逻辑是，保存草稿时未填金额就给前端返0
        if(demandManagementBudget.getExpenseAmt() != null){
            this.expenseAmt = new BigDecimal(demandManagementBudget.getExpenseAmt());
        }else {
            this.expenseAmt = null;
        }
		if(demandManagementBudget.getCreateTime() != null){
			this.createTime = DateUtils.formatDate(demandManagementBudget.getCreateTime(), DateUtils.YYYY_MM_DD_HH_MM_SS);
		}
		this.remarks = demandManagementBudget.getRemarks() == null ? "": demandManagementBudget.getRemarks();
		this.firstSubName = demandManagementBudget.getFirstSubName();
		this.secondSubName = demandManagementBudget.getSecondSubName() == null ? "": demandManagementBudget.getSecondSubName();
	}
	
	public String getProcCode() {
		return procCode;
	}

	public long getStartDate() {
		return startDate;
	}


	public long getEndDate() {
		return endDate;
	}


	public String getFirstSub() {
		return firstSub;
	}

	public String getSecondSub() {
		return secondSub;
	}

	public Integer getPersonNum() {
		return personNum;
	}

	public Integer getDayNum() {
		return dayNum;
	}

	public BigDecimal getExpenseAmt() {
		return expenseAmt;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setProcCode(String procCode) {
		this.procCode = procCode;
	}

	public void setStartDate(long startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(long endDate) {
		this.endDate = endDate;
	}

	public void setFirstSub(String firstSub) {
		this.firstSub = firstSub;
	}

	public void setSecondSub(String secondSub) {
		this.secondSub = secondSub;
	}

	public void setPersonNum(Integer personNum) {
		this.personNum = personNum;
	}

	public void setDayNum(Integer dayNum) {
		this.dayNum = dayNum;
	}

	public void setExpenseAmt(BigDecimal expenseAmt) {
		this.expenseAmt = expenseAmt;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
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

	public String[] getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String[] subjectName) {
		this.subjectName = subjectName;
	}
	
}