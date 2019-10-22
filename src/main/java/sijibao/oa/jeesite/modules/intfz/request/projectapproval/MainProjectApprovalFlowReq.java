/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.intfz.request.projectapproval;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 立项联申请保存Entity
 * @author wanxb
 * @version 2019-08-19
 */
@ApiModel(value="立项联申请保存对象")
public class MainProjectApprovalFlowReq implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value="逐渐ID")
	private String id;		//ID
	@ApiModelProperty(value="流程实例ID")
	private String procInsId;		// 流程实例ID
	@ApiModelProperty(value="流程编号")
	private String procCode;		// 流程编号
	@ApiModelProperty(value="流程编号")
	private String procName;		// 流程编号
	@ApiModelProperty(value="申请人编号")
	private String applyPerCode;		// 申请人编号
	@ApiModelProperty(value="申请人名称")
	private String applyPerName;		// 申请人名称
	@ApiModelProperty(value="申请时间")
	private long applyTime = 0l;		// 申请时间
	@ApiModelProperty(value="所属部门ID")
	private String officeId;		// 所属部门ID
	@ApiModelProperty(value="所属部门名称")
	private String officeName;		// 所属部门名称
	@NotNull
	@ApiModelProperty(value="客户id")
	private String custId;		// 客户id
	@NotNull
	@Length(max = 64)
	@ApiModelProperty(value="项目名称")
	private String projectName;		// 项目名称
	@NotNull
	@ApiModelProperty(value="企业holderCode")
	private String companyHolderCode;		// 企业holderCode
	@ApiModelProperty(value="企业名称")
	private String companyName;		// 企业名称
	@NotNull
	@ApiModelProperty(value="承运货物:1煤炭2钢铁3商砼4其他")
	private String[] carrierGoods;		// 承运货物:1煤炭2钢铁3商砼4其他
	@NotNull
	@ApiModelProperty(value="项目等级abcd")
	private String projectLevel;		// 项目等级abcd
	@ApiModelProperty(value="项目类型0公司项目1市场项目")
	private String projectType;		// 项目类型0公司项目1市场项目
	@ApiModelProperty(value="月开票频次（次/月）")
	private String invoicingFrequency;		// 月开票频次（次/月）
	@ApiModelProperty(value="计划月运费(万元)")
	private BigDecimal transExpensesPlan;		// 计划月运费(万元)
	@ApiModelProperty(value="计划上线时间")
	private long onlinePlanTime = 0l;		// 计划上线时间
	@ApiModelProperty(value="实施负责人ID")
	private String implyLeaderId;		// 实施负责人ID
	@ApiModelProperty(value="开票方式：1一票，2两票")
	private String invoiceMode;		// 开票方式：1一票，2两票
	@ApiModelProperty(value="项目托管：1是，2否")
	private String projectTrusteeshipt;		// 项目托管：1是，2否
	@ApiModelProperty(value="托管渠道：1华夏，2兴业")
	private String trusteeshiptChannel;		// 托管渠道：1华夏，2兴业
	@ApiModelProperty(value="自营：1是，2否")
	private String selfMarketing;		// 自营：1是，2否
	@ApiModelProperty(value="项目经纪人：1是，2否")
	private String projectAgent;		// 项目经纪人：1是，2否
	@ApiModelProperty(value="车队长：1是，2否")
	private String truckLeader;		// 车队长：1是，2否
	@ApiModelProperty(value="油气：1不做油气，2带油,3带气,4带油和气")
	private String oilGas;		// 油气：1不做油气，2带油,3带气,4带油和气
	@ApiModelProperty(value="叫车：1是，2否")
	private String callTruck;		// 叫车：1是，2否
	@ApiModelProperty(value="贸易：1是，2否")
	private String projectTrade;		// 贸易：1是，2否
	@ApiModelProperty(value=" 账期：1是，2否")
	private String accountPeriod;		// 账期：1是，2否
	@ApiModelProperty(value="网商：1是，2否")
	private String networkBusiness;		// 网商：1是，2否
	@ApiModelProperty(value="项目托盘：1是，2否")
	private String projectTray;		// 项目托盘：1是，2否
	@ApiModelProperty(value="返点：1是，2否")
	private String returnPoint;		// 返点：1是，2否
	@ApiModelProperty(value="返点比例（%）")
	private String returnPointProportion;		// 返点比例（%）
	@ApiModelProperty(value="项目批准状态")
	private String projectApprovalStatus;		// 项目批准状态
	@ApiModelProperty(value="审批结束时间")
	private long flowFinishTime = 0l;		// 审批结束时间
	@ApiModelProperty(value="备注")
	private String remarks;


	private List<MainProjectApprovalLinkmanReq> projectApprovalLinkman;//联系人信息

	public List<MainProjectApprovalLinkmanReq> getProjectApprovalLinkman() {
		return projectApprovalLinkman;
	}

	public void setProjectApprovalLinkman(List<MainProjectApprovalLinkmanReq> projectApprovalLinkman) {
		this.projectApprovalLinkman = projectApprovalLinkman;
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

	public long getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(long applyTime) {
		this.applyTime = applyTime;
	}

	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}

	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getCompanyHolderCode() {
		return companyHolderCode;
	}

	public void setCompanyHolderCode(String companyHolderCode) {
		this.companyHolderCode = companyHolderCode;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String[] getCarrierGoods() {
		return carrierGoods;
	}

	public void setCarrierGoods(String[] carrierGoods) {
		this.carrierGoods = carrierGoods;
	}

	public String getProjectLevel() {
		return projectLevel;
	}

	public void setProjectLevel(String projectLevel) {
		this.projectLevel = projectLevel;
	}

	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	public String getInvoicingFrequency() {
		return invoicingFrequency;
	}

	public void setInvoicingFrequency(String invoicingFrequency) {
		this.invoicingFrequency = invoicingFrequency;
	}

	public BigDecimal getTransExpensesPlan() {
		return transExpensesPlan;
	}

	public void setTransExpensesPlan(BigDecimal transExpensesPlan) {
		this.transExpensesPlan = transExpensesPlan;
	}

	public long getOnlinePlanTime() {
		return onlinePlanTime;
	}

	public void setOnlinePlanTime(long onlinePlanTime) {
		this.onlinePlanTime = onlinePlanTime;
	}


	public String getInvoiceMode() {
		return invoiceMode;
	}

	public void setInvoiceMode(String invoiceMode) {
		this.invoiceMode = invoiceMode;
	}

	public String getProjectTrusteeshipt() {
		return projectTrusteeshipt;
	}

	public void setProjectTrusteeshipt(String projectTrusteeshipt) {
		this.projectTrusteeshipt = projectTrusteeshipt;
	}

	public String getTrusteeshiptChannel() {
		return trusteeshiptChannel;
	}

	public void setTrusteeshiptChannel(String trusteeshiptChannel) {
		this.trusteeshiptChannel = trusteeshiptChannel;
	}

	public String getSelfMarketing() {
		return selfMarketing;
	}

	public void setSelfMarketing(String selfMarketing) {
		this.selfMarketing = selfMarketing;
	}

	public String getProjectAgent() {
		return projectAgent;
	}

	public void setProjectAgent(String projectAgent) {
		this.projectAgent = projectAgent;
	}

	public String getTruckLeader() {
		return truckLeader;
	}

	public void setTruckLeader(String truckLeader) {
		this.truckLeader = truckLeader;
	}

	public String getOilGas() {
		return oilGas;
	}

	public void setOilGas(String oilGas) {
		this.oilGas = oilGas;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCallTruck() {
		return callTruck;
	}

	public void setCallTruck(String callTruck) {
		this.callTruck = callTruck;
	}

	public String getProjectTrade() {
		return projectTrade;
	}

	public void setProjectTrade(String projectTrade) {
		this.projectTrade = projectTrade;
	}

	public String getAccountPeriod() {
		return accountPeriod;
	}

	public void setAccountPeriod(String accountPeriod) {
		this.accountPeriod = accountPeriod;
	}

	public String getNetworkBusiness() {
		return networkBusiness;
	}

	public void setNetworkBusiness(String networkBusiness) {
		this.networkBusiness = networkBusiness;
	}

	public String getProjectTray() {
		return projectTray;
	}

	public void setProjectTray(String projectTray) {
		this.projectTray = projectTray;
	}

	public String getReturnPoint() {
		return returnPoint;
	}

	public void setReturnPoint(String returnPoint) {
		this.returnPoint = returnPoint;
	}

	public String getReturnPointProportion() {
		return returnPointProportion;
	}

	public void setReturnPointProportion(String returnPointProportion) {
		this.returnPointProportion = returnPointProportion;
	}

	public String getProjectApprovalStatus() {
		return projectApprovalStatus;
	}

	public void setProjectApprovalStatus(String projectApprovalStatus) {
		this.projectApprovalStatus = projectApprovalStatus;
	}



	public String getImplyLeaderId() {
		return implyLeaderId;
	}

	public void setImplyLeaderId(String implyLeaderId) {
		this.implyLeaderId = implyLeaderId;
	}

	public long getFlowFinishTime() {
		return flowFinishTime;
	}

	public void setFlowFinishTime(long flowFinishTime) {
		this.flowFinishTime = flowFinishTime;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Override
	public String toString() {
		return "MainProjectApprovalFlowReq{" +
				"procInsId='" + procInsId + '\'' +
				", procCode='" + procCode + '\'' +
				", procName='" + procName + '\'' +
				", applyPerCode='" + applyPerCode + '\'' +
				", applyPerName='" + applyPerName + '\'' +
				", applyTime=" + applyTime +
				", officeId='" + officeId + '\'' +
				", officeName='" + officeName + '\'' +
				", custId='" + custId + '\'' +
				", projectName='" + projectName + '\'' +
				", companyHolderCode='" + companyHolderCode + '\'' +
				", companyName='" + companyName + '\'' +
				", carrierGoods='" + carrierGoods + '\'' +
				", projectLevel='" + projectLevel + '\'' +
				", projectType='" + projectType + '\'' +
				", invoicingFrequency='" + invoicingFrequency + '\'' +
				", transExpensesPlan='" + transExpensesPlan + '\'' +
				", onlinePlanTime=" + onlinePlanTime +
				", implyLeaderId='" + implyLeaderId + '\'' +
				", invoiceMode='" + invoiceMode + '\'' +
				", projectTrusteeshipt='" + projectTrusteeshipt + '\'' +
				", trusteeshiptChannel='" + trusteeshiptChannel + '\'' +
				", selfMarketing='" + selfMarketing + '\'' +
				", projectAgent='" + projectAgent + '\'' +
				", truckLeader='" + truckLeader + '\'' +
				", oilGas='" + oilGas + '\'' +
				", callTruck='" + callTruck + '\'' +
				", projectTrade='" + projectTrade + '\'' +
				", accountPeriod='" + accountPeriod + '\'' +
				", networkBusiness='" + networkBusiness + '\'' +
				", projectTray='" + projectTray + '\'' +
				", returnPoint='" + returnPoint + '\'' +
				", returnPointProportion='" + returnPointProportion + '\'' +
				", projectApprovalStatus='" + projectApprovalStatus + '\'' +
				'}';
	}
}