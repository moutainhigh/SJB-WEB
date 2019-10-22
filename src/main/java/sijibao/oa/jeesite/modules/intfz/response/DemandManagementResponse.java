package sijibao.oa.jeesite.modules.intfz.response;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

import com.sijibao.oa.common.utils.DateUtils;
import com.sijibao.oa.common.utils.StringUtils;
import com.sijibao.oa.modules.oa.entity.DemandManagement;
import com.sijibao.oa.modules.sys.entity.Office;
import com.sijibao.oa.modules.sys.entity.User;
import com.sijibao.oa.modules.sys.utils.DictUtils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 报销费用明细Entity
 */
@ApiModel(value="需求基本信息")
public class DemandManagementResponse {
	@ApiModelProperty(value="业务主键ID")
	private String id;              //业务主键ID
	@ApiModelProperty(value="流程实例ID")
	private String procInsId;		// 流程实例ID
	@ApiModelProperty(value="流程编号")
	private String procCode;		// 流程编号
	@ApiModelProperty(value="流程名称")
	private String procName;		// 流程名称
	@ApiModelProperty(value="申请人编号")
	private String applyPerCode;		// 申请人编号
	@ApiModelProperty(value="申请人名称")
	private String applyPerName;		// 申请人名称
	@ApiModelProperty(value="申请时间")
	private String applyTime;		// 申请时间
	@ApiModelProperty(value="所属部门ID")
	private Office office;		// 所属部门ID
	@ApiModelProperty(value="所属部门名称")
	private String officeName;		// 所属部门名称
	@ApiModelProperty(value="所属岗位编码")
	private String postCode;		// 所属岗位编码
	@ApiModelProperty(value="所属岗位名称")
	private String postName;		// 所属岗位名称
	@ApiModelProperty(value="需求名称")
	private String demandName;		// 需求名称
	@ApiModelProperty(value="需求类型")
	private String demandType;		// 需求类型
	@ApiModelProperty(value="项目ID")
	private String projectId;		// 项目ID
	@ApiModelProperty(value="项目名称")
	private String projectName;		// 项目名称
	@ApiModelProperty(value="区域编码")
	private String[] areaCode;		// 区域编码
	@ApiModelProperty(value="区域名称")
	private String[] areaName;		// 区域名称
	@ApiModelProperty(value="需求人数")
	private String demandPersonelNum;		// 需求人数
	@ApiModelProperty(value="期望抵达日期")
	private String expectDate;		// 期望抵达日期
	@ApiModelProperty(value="预计时长")
	private String timeLong;		// 预计时长
	@ApiModelProperty(value="预算总金额")
	private String amountSum;		// 预算总金额
	@ApiModelProperty(value="需求状态")
	private String demandStatus;		// 需求状态
	@ApiModelProperty(value="需求状态文本")
	private String demandStatusTxt;		// 需求状态文本
	@ApiModelProperty(value="单据类型(1:市场发起申请,2:实施发起需求)")
	private String billType;  //单据类型(1:市场发起申请,2:实施发起需求)
	@ApiModelProperty(value="需求类型名称")
	private String demandTypeName;		// 需求类型名称
	@ApiModelProperty(value="开始 申请时间")
	private String beginApplyTime;		// 开始 申请时间
	@ApiModelProperty(value="结束 申请时间")
	private String endApplyTime;		// 结束 申请时间
	@ApiModelProperty(value="备注")
	private String remarks;             //备注
	@ApiModelProperty(value="是否当前人:1是0否")
	private int local;			//当前人:1
	@ApiModelProperty(value="当前环节可以指派的人员信息")
	private List<User> employeeList; //当前环节可以指派的人员信息
	@ApiModelProperty(value="当前环节是否可以指派人员(1:允许，0：不允许)")
	private int isAssign; //当前环节可以指派的人员信息
	@ApiModelProperty(value="当前环节是否可以驳回流程(1:允许，0：不允许)")
	private int isBack; //当前环节是否允许驳回
	@ApiModelProperty(value="当前环节是否能够填写明细数据(1:允许，0：不允许)")
	private int isFillDetail;//是否能够填写明细数据
	@ApiModelProperty(value="当前环节是否可编辑信息(1:允许，0：不允许)")
	private int isDeit;      //是否可编辑信息
	public DemandManagementResponse(DemandManagement demandManagement) {
		super();
		DecimalFormat df = new DecimalFormat("0.00");
		this.id = demandManagement.getId();
		this.procInsId = demandManagement.getProcInsId()==null?"":demandManagement.getProcInsId();
		this.procCode = demandManagement.getProcCode();
		this.procName = demandManagement.getProcName();
		this.applyPerCode = demandManagement.getApplyPerCode();
		this.applyPerName = demandManagement.getApplyPerName();
		if(demandManagement.getApplyTime() != null){
			this.applyTime = DateUtils.formatDate(demandManagement.getApplyTime(), DateUtils.YYYY_MM_DD);
		}
//		this.areaCode = areaCodes;
//		this.areaName = areaNames;
		
		this.demandName = demandManagement.getDemandName();
		this.demandType = demandManagement.getDemandType();
		if(StringUtils.isNotBlank(demandManagement.getDemandType())){
			this.demandTypeName = DictUtils.getDictLabel(demandManagement.getDemandType(), "oa_demand_type", "");
		}
		
		this.amountSum = df.format(new BigDecimal(StringUtils.isBlank(demandManagement.getAmountSum())?"0":demandManagement.getAmountSum()));
		this.projectId = demandManagement.getProjectId();
		this.projectName = demandManagement.getProjectName();
		
		this.demandStatus = demandManagement.getDemandStatus();
		this.demandStatusTxt = DictUtils.getDictLabel(demandManagement.getDemandStatus(), "expense_status", "");
		
		this.demandPersonelNum = StringUtils.isBlank(demandManagement.getDemandPersonelNum())?"0":demandManagement.getDemandPersonelNum();
		this.expectDate = DateUtils.formatDate(demandManagement.getExpectDate(),DateUtils.YYYY_MM_DD_HH);
		this.timeLong = demandManagement.getTimeLong();
		this.officeName = demandManagement.getOffice().getName();
		this.remarks = demandManagement.getRemarks();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProcInsId() {
		return procInsId;
	}
	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}
	public String getProcCode() {
		return procCode;
	}
	public void setProcCode(String procCode) {
		this.procCode = procCode;
	}
	public String getProcName() {
		return procName;
	}
	public void setProcName(String procName) {
		this.procName = procName;
	}
	public String getApplyPerCode() {
		return applyPerCode;
	}
	public void setApplyPerCode(String applyPerCode) {
		this.applyPerCode = applyPerCode;
	}
	public String getApplyPerName() {
		return applyPerName;
	}
	public void setApplyPerName(String applyPerName) {
		this.applyPerName = applyPerName;
	}
	public String getApplyTime() {
		return applyTime;
	}
	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
	}
	public Office getOffice() {
		return office;
	}
	public void setOffice(Office office) {
		this.office = office;
	}
	public String getOfficeName() {
		return officeName;
	}
	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}
	public String getPostCode() {
		return postCode;
	}
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	public String getPostName() {
		return postName;
	}
	public void setPostName(String postName) {
		this.postName = postName;
	}
	public String getDemandName() {
		return demandName;
	}
	public void setDemandName(String demandName) {
		this.demandName = demandName;
	}
	public String getDemandType() {
		return demandType;
	}
	public void setDemandType(String demandType) {
		this.demandType = demandType;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String[] getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String[] areaCode) {
		this.areaCode = areaCode;
	}
	public String[] getAreaName() {
		return areaName;
	}
	public void setAreaName(String[] areaName) {
		this.areaName = areaName;
	}
	public String getDemandPersonelNum() {
		return demandPersonelNum;
	}
	public void setDemandPersonelNum(String demandPersonelNum) {
		this.demandPersonelNum = demandPersonelNum;
	}
	public String getExpectDate() {
		return expectDate;
	}
	public void setExpectDate(String expectDate) {
		this.expectDate = expectDate;
	}
	public String getTimeLong() {
		return timeLong;
	}
	public void setTimeLong(String timeLong) {
		this.timeLong = timeLong;
	}
	public String getAmountSum() {
		return amountSum;
	}
	public void setAmountSum(String amountSum) {
		this.amountSum = amountSum;
	}
	public String getDemandStatus() {
		return demandStatus;
	}
	public void setDemandStatus(String demandStatus) {
		this.demandStatus = demandStatus;
	}
	public String getDemandStatusTxt() {
		return demandStatusTxt;
	}
	public void setDemandStatusTxt(String demandStatusTxt) {
		this.demandStatusTxt = demandStatusTxt;
	}
	public String getBillType() {
		return billType;
	}
	public void setBillType(String billType) {
		this.billType = billType;
	}
	public String getDemandTypeName() {
		return demandTypeName;
	}
	public void setDemandTypeName(String demandTypeName) {
		this.demandTypeName = demandTypeName;
	}
	public String getBeginApplyTime() {
		return beginApplyTime;
	}
	public void setBeginApplyTime(String beginApplyTime) {
		this.beginApplyTime = beginApplyTime;
	}
	public String getEndApplyTime() {
		return endApplyTime;
	}
	public void setEndApplyTime(String endApplyTime) {
		this.endApplyTime = endApplyTime;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public int getLocal() {
		return local;
	}
	public void setLocal(int local) {
		this.local = local;
	}
	public List<User> getEmployeeList() {
		return employeeList;
	}
	public void setEmployeeList(List<User> employeeList) {
		this.employeeList = employeeList;
	}
	public int getIsAssign() {
		return isAssign;
	}
	public void setIsAssign(int isAssign) {
		this.isAssign = isAssign;
	}
	public int getIsBack() {
		return isBack;
	}
	public void setIsBack(int isBack) {
		this.isBack = isBack;
	}
	public int getIsFillDetail() {
		return isFillDetail;
	}
	public void setIsFillDetail(int isFillDetail) {
		this.isFillDetail = isFillDetail;
	}
	public int getIsDeit() {
		return isDeit;
	}
	public void setIsDeit(int isDeit) {
		this.isDeit = isDeit;
	}
	@Override
	public String toString() {
		return "DemandManagementResponse [id=" + id + ", procInsId=" + procInsId + ", procCode=" + procCode
				+ ", procName=" + procName + ", applyPerCode=" + applyPerCode + ", applyPerName=" + applyPerName
				+ ", applyTime=" + applyTime + ", office=" + office + ", officeName=" + officeName + ", postCode="
				+ postCode + ", postName=" + postName + ", demandName=" + demandName + ", demandType=" + demandType
				+ ", projectId=" + projectId + ", projectName=" + projectName + ", areaCode="
				+ Arrays.toString(areaCode) + ", areaName=" + Arrays.toString(areaName) + ", demandPersonelNum="
				+ demandPersonelNum + ", expectDate=" + expectDate + ", timeLong=" + timeLong + ", amountSum="
				+ amountSum + ", demandStatus=" + demandStatus + ", demandStatusTxt=" + demandStatusTxt + ", billType="
				+ billType + ", demandTypeName=" + demandTypeName + ", beginApplyTime=" + beginApplyTime
				+ ", endApplyTime=" + endApplyTime + ", remarks=" + remarks + ", local=" + local + ", employeeList="
				+ employeeList + ", isAssign=" + isAssign + "]";
	}
	
}