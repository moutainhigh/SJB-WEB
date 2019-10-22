package sijibao.oa.jeesite.modules.intfz.response.expense;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.sijibao.oa.common.utils.DateUtils;
import com.sijibao.oa.common.utils.StringUtils;
import com.sijibao.oa.modules.flow.entity.ExpenseAttachment;
import com.sijibao.oa.modules.flow.entity.ExpenseDetail;
import com.sijibao.oa.modules.intfz.utils.Constant;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 报销费用明细Entity
 */
@ApiModel(value="报销费用明细信息")
public class ExpenseDetailResponse {

	@ApiModelProperty(value = "明细编号")
	private String detailNo;

	@ApiModelProperty(value="流程编号")
	private String procCode;

	@ApiModelProperty(value="开始日期")
	private long startDate;

	@ApiModelProperty(value="起点")
	private String[] startPoint;

	@ApiModelProperty(value="起点名称")
	private String[] startPointName;

	@ApiModelProperty(value="结束日期")
	private long endDate;

	@ApiModelProperty(value="终点")
	private String[] endPoint;

	@ApiModelProperty(value="终点名称")
	private String[] endPointName;

	@ApiModelProperty(value="一级科目编号")
	private String firstSub;

	@ApiModelProperty(value="一级科目名称")
	private String firstSubName;

	@ApiModelProperty(value="二级科目编号")
	private String secondSub;

	@ApiModelProperty(value="二级科目名称")
	private String secondSubName;

	@ApiModelProperty(value="科目数组")
	private String[] subject;

	@ApiModelProperty(value="科目名称数组")
	private String[] subjectName;

	@ApiModelProperty(value = "科目是否超过了标准，0否，1是")
	private String subjectExceed;

	@ApiModelProperty(value="人数")
	private Integer personNum;

	@ApiModelProperty(value="天数")
	private Integer dayNum;

	@ApiModelProperty(value="票据数量")
	private Integer billNum;

	@ApiModelProperty(value="报销金额")
	private BigDecimal expenseAmt;

    @ApiModelProperty(value="报销金额是否超过了标准，0否，1是")
	private String expenseAmtExceed;

	@ApiModelProperty(value="创建时间")
	private String createTime;

    @ApiModelProperty(value = "异常分析")
	private String anomalyAnalysis;

	@ApiModelProperty(value="备注")
	private String remarks;

	@ApiModelProperty(value="科目附件图片")
	private List<ExpenseAttachmentResponse> subConfList = new ArrayList<>();

	public ExpenseDetailResponse() {
	}

	public ExpenseDetailResponse(ExpenseDetail expenseDetail) {
		super();
		this.procCode = expenseDetail.getProcCode();
		if(expenseDetail.getStartDate() != null){
			this.startDate = expenseDetail.getStartDate().getTime();
		}
		if(expenseDetail.getEndDate() != null){
			this.endDate = expenseDetail.getEndDate().getTime();
		}

		this.firstSub = expenseDetail.getFirstSub();
		this.secondSub = expenseDetail.getSecondSub();
		if(StringUtils.isNotBlank(expenseDetail.getPersonNum())){
			this.personNum = Integer.valueOf(expenseDetail.getPersonNum());
		}
		if(StringUtils.isNotBlank(expenseDetail.getDayNum())){
			this.dayNum = Integer.valueOf(expenseDetail.getDayNum());
		}
		if(StringUtils.isNotBlank(expenseDetail.getBillNum())){
			this.billNum = Integer.valueOf(expenseDetail.getBillNum());
		}
		this.expenseAmt = expenseDetail.getExpenseAmt();
		if(expenseDetail.getCreateTime() != null){
			this.createTime = DateUtils.formatDate(expenseDetail.getCreateTime(), DateUtils.YYYY_MM_DD_HH_MM_SS);
		}
		this.remarks = expenseDetail.getRemarks() == null ? "": expenseDetail.getRemarks();
		this.firstSubName = expenseDetail.getFirstSubName();
		this.secondSubName = expenseDetail.getSecondSubName() == null ? "": expenseDetail.getSecondSubName();
	}

	public ExpenseDetailResponse(ExpenseDetail expenseDetail,String[] startPoint,String[] startPointName,String[] endPoint,String[] endPointName,List<ExpenseAttachment> subAttachment,String serverUrl,String clientType) {
		super();
		this.procCode = expenseDetail.getProcCode();
		if(expenseDetail.getStartDate() != null){
			this.startDate = expenseDetail.getStartDate().getTime();
		}
		this.startPoint = startPoint;
		this.startPointName = startPointName;
		if(expenseDetail.getEndDate() != null){
			this.endDate = expenseDetail.getEndDate().getTime();
		}
		this.endPoint = endPoint;
		this.endPointName = endPointName;
		this.firstSub = expenseDetail.getFirstSub();
		this.secondSub = expenseDetail.getSecondSub();
		if(Constant.CLIENT_TYPE_APP.equals(clientType)){
			String[] subject = new String[2];
			String[] subjectName = new String[2];
			if(StringUtils.isBlank(expenseDetail.getSecondSub())){

				subject[0] =  expenseDetail.getFirstSub();
				subject[1] =  "no_"+expenseDetail.getFirstSub();
				subjectName = new String[1];
				subjectName[0] = expenseDetail.getFirstSubName();
			}else{
				subject[0] =  expenseDetail.getFirstSub();
				subject[1] =  expenseDetail.getSecondSub();
				subjectName[0] = expenseDetail.getFirstSubName();
				subjectName[1] = expenseDetail.getSecondSubName();
			}
			this.subject = subject;
			this.subjectName = subjectName;
		}
		
		if(StringUtils.isNotBlank(expenseDetail.getPersonNum())){
			this.personNum = Integer.valueOf(expenseDetail.getPersonNum());
		}
		if(StringUtils.isNotBlank(expenseDetail.getDayNum())){
			this.dayNum = Integer.valueOf(expenseDetail.getDayNum());
		}
		if(StringUtils.isNotBlank(expenseDetail.getBillNum())){
			this.billNum = Integer.valueOf(expenseDetail.getBillNum());
		}
		this.expenseAmt = expenseDetail.getExpenseAmt();
		if(expenseDetail.getCreateTime() != null){
			this.createTime = DateUtils.formatDate(expenseDetail.getCreateTime(), DateUtils.YYYY_MM_DD_HH_MM_SS);
		}
		this.remarks = expenseDetail.getRemarks() == null ? "": expenseDetail.getRemarks();
		this.firstSubName = expenseDetail.getFirstSubName();
		this.secondSubName = expenseDetail.getSecondSubName() == null ? "": expenseDetail.getSecondSubName();
		if(subAttachment != null && subAttachment.size() > 0){
			for(ExpenseAttachment a:subAttachment){
				if(StringUtils.equals(a.getExpenseDetailId(), expenseDetail.getId())){
					ExpenseAttachmentResponse r = new  ExpenseAttachmentResponse();
					r.setId(a.getSubImgConfId());
					r.setConfDesc(a.getSubImgDes());
					r.setUrl(a.getExpenseAttachmentUrl());
					r.setUrlPrefix(serverUrl);
					this.subConfList.add(r);
				}
			}
		}
	}

	public String getDetailNo() {
		return detailNo;
	}

	public void setDetailNo(String detailNo) {
		this.detailNo = detailNo;
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

	public Integer getBillNum() {
		return billNum;
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

	public void setBillNum(Integer billNum) {
		this.billNum = billNum;
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
	public List<ExpenseAttachmentResponse> getSubConfList() {
		return subConfList;
	}

	public void setSubConfList(List<ExpenseAttachmentResponse> subConfList) {
		this.subConfList = subConfList;
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

	public String getSubjectExceed() {
		return subjectExceed;
	}

	public void setSubjectExceed(String subjectExceed) {
		this.subjectExceed = subjectExceed;
	}

	public String getExpenseAmtExceed() {
		return expenseAmtExceed;
	}

	public void setExpenseAmtExceed(String expenseAmtExceed) {
		this.expenseAmtExceed = expenseAmtExceed;
	}

	public String getAnomalyAnalysis() {
		return anomalyAnalysis;
	}

	public void setAnomalyAnalysis(String anomalyAnalysis) {
		this.anomalyAnalysis = anomalyAnalysis;
	}

	@Override
	public String toString() {
		return "ExpenseDetailResponse [procCode=" + procCode + ", startDate=" + startDate + ", startPoint="
				+ Arrays.toString(startPoint) + ", startPointName=" + Arrays.toString(startPointName) + ", endDate="
				+ endDate + ", endPoint=" + Arrays.toString(endPoint) + ", endPointName="
				+ Arrays.toString(endPointName) + ", firstSub=" + firstSub + ", firstSubName=" + firstSubName
				+ ", secondSub=" + secondSub + ", secondSubName=" + secondSubName + ", personNum=" + personNum
				+ ", dayNum=" + dayNum + ", billNum=" + billNum + ", expenseAmt=" + expenseAmt + ", createTime="
				+ createTime + ", remarks=" + remarks + ", subConfList=" + subConfList
				+ ", subjectExceed=" + subjectExceed + ", expenseAmtExceed=" + expenseAmtExceed + ", anomalyAnalysis=" + anomalyAnalysis + "]";
	}
}