/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.intfz.response.projectapproval;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 立项信息Entity
 * @author wanxb
 * @version 2019-08-19
 *
 */
@ApiModel(value="立项流程对象")
public class MainProjectApprovalFlowResp implements Serializable {
	
	private static final long serialVersionUID = 1L;
    @ApiModelProperty(value="主键id")
    private String id;		// 流程实例ID
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
	@ApiModelProperty(value="客户id")
	private String custId;		// 客户id
	@ApiModelProperty(value="客户name")
	private String custName;		// 客户id
	@ApiModelProperty(value="项目名称")
	private String projectName;		// 项目名称
	@ApiModelProperty(value="企业holderCode")
	private String companyHolderCode;		// 企业holderCode
	@ApiModelProperty(value="企业名称")
	private String companyName;		// 企业名称
	@ApiModelProperty(value="承运货物:1煤炭2钢铁3商砼4其他")
	private String[] carrierGoods;		// 承运货物:1煤炭2钢铁3商砼4其他
	@ApiModelProperty(value="承运货物name:1煤炭2钢铁3商砼4其他")
	private String carrierGoodsName;		// 承运货物name:1煤炭2钢铁3商砼4其他
	@ApiModelProperty(value="项目等级abcd")
	private String projectLevel;		// 项目等级abcd
	@ApiModelProperty(value="项目等级abcdName")
	private String projectLevelName;		// 项目等级abcdName
	@ApiModelProperty(value="项目类型0公司项目1市场项目")
	private String projectType;		// 项目类型0公司项目1市场项目
	@ApiModelProperty(value="项目类型name0公司项目1市场项目")
	private String projectTypeName;		// 项目类型name0公司项目1市场项目
	@ApiModelProperty(value="月开票频次（次/月）")
	private String invoicingFrequency;		// 月开票频次（次/月）
	@ApiModelProperty(value="计划月运费(万元)")
	private BigDecimal transExpensesPlan;		// 计划月运费(万元)
	@ApiModelProperty(value="计划上线时间")
	private long onlinePlanTime = 0l;		// 计划上线时间
	@ApiModelProperty(value="实施负责人ID")
	private String implyLeaderId;		// 实施负责人ID
    @ApiModelProperty(value="实施负责人name")
    private String implyLeaderName;		// 实施负责人ID
	@ApiModelProperty(value="开票方式：1一票，2两票")
	private String invoiceMode;		// 开票方式：1一票，2两票
	@ApiModelProperty(value="开票方式name：1一票，2两票")
	private String invoiceModeName;		// 开票方式name：1一票，2两票
	@ApiModelProperty(value="项目托管：1是，2否")
	private String projectTrusteeshipt;		// 项目托管：1是，2否
	@ApiModelProperty(value="项目托管name：1是，2否")
	private String projectTrusteeshiptName;		// 项目托管：1是，2否
	@ApiModelProperty(value="托管渠道：1华夏，2兴业")
	private String trusteeshiptChannel;		// 托管渠道：1华夏，2兴业
	@ApiModelProperty(value="托管渠道name：1华夏，2兴业")
	private String trusteeshiptChannelName;		// 托管渠道：1华夏，2兴业
	@ApiModelProperty(value="自营：1是，2否")
	private String selfMarketing;		// 自营：1是，2否
	@ApiModelProperty(value="自营name：1是，2否")
	private String selfMarketingName;		// 自营：1是，2否
	@ApiModelProperty(value="项目经纪人：1是，2否")
	private String projectAgent;		// 项目经纪人：1是，2否
	@ApiModelProperty(value="项目经纪人name：1是，2否")
	private String projectAgentName;		// 项目经纪人：1是，2否
	@ApiModelProperty(value="车队长：1是，2否")
	private String truckLeader;		// 车队长：1是，2否
	@ApiModelProperty(value="车队长：1是，2否")
	private String truckLeaderName;		// 车队长：1是，2否
	@ApiModelProperty(value="油气：1不做油气，2带油,3带气,4带油和气")
	private String oilGas;		// 油气：1不做油气，2带油,3带气,4带油和气
	@ApiModelProperty(value="油气name：1不做油气，2带油,3带气,4带油和气")
	private String oilGasName;		// 油气：1不做油气，2带油,3带气,4带油和气
	@ApiModelProperty(value="叫车：1是，2否")
	private String callTruck;		// 叫车：1是，2否
	@ApiModelProperty(value="叫车name：1是，2否")
	private String callTruckName;		// 叫车：1是，2否
	@ApiModelProperty(value="贸易：1是，2否")
	private String projectTrade;		// 贸易：1是，2否
	@ApiModelProperty(value="贸易name：1是，2否")
	private String projectTradeName;		// 贸易：1是，2否
	@ApiModelProperty(value=" 账期：1是，2否")
	private String accountPeriod;		// 账期：1是，2否
	@ApiModelProperty(value=" 账期name：1是，2否")
	private String accountPeriodName;		// 账期：1是，2否
	@ApiModelProperty(value="网商：1是，2否")
	private String networkBusiness;		// 网商：1是，2否
	@ApiModelProperty(value="网商name：1是，2否")
	private String networkBusinessName;		// 网商：1是，2否
	@ApiModelProperty(value="项目托盘：1是，2否")
	private String projectTray;		// 项目托盘：1是，2否
	@ApiModelProperty(value="项目托盘name：1是，2否")
	private String projectTrayName;		// 项目托盘：1是，2否
	@ApiModelProperty(value="返点：1是，2否")
	private String returnPoint;		// 返点：1是，2否
	@ApiModelProperty(value="返点name：1是，2否")
	private String returnPointName;		// 返点：1是，2否
	@ApiModelProperty(value="返点比例（%）")
	private String returnPointProportion;		// 返点比例（%）
	@ApiModelProperty(value="项目批准状态")
	private String projectApprovalStatus;		// 项目批准状态
	@ApiModelProperty(value="项目批准状态name")
	private String projectApprovalStatusName;		// 项目批准状态
	@ApiModelProperty(value="审批结束时间")
	private long flowFinishTime = 0l;		// 审批结束时间
	@ApiModelProperty(value="联系人信息")
	private List<MainProjectApprovalLinkmanResp> projectApprovalLinkman;
	@ApiModelProperty(value="备注")
	private String remarks;		// 备注
	@ApiModelProperty(value = "新项目负责人name")
	private String newProjectLeaderName;
	@ApiModelProperty(value = "新归属部门name")
	private String newOfficeName;
	@ApiModelProperty(value = "新归属部门负责人name")
	private String officeLeaderName;


	@ApiModelProperty(value="当前环节是否可以驳回")
	private int isBack;			//当前环节是否可以驳回
	@ApiModelProperty(value="当前环节是否可以撤回")
	private int isCancel;       //当前环节是否可以撤回
	@ApiModelProperty(value="编辑节点")
	private String modify;      //编辑节点

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNewProjectLeaderName() {
		return newProjectLeaderName;
	}

	public void setNewProjectLeaderName(String newProjectLeaderName) {
		this.newProjectLeaderName = newProjectLeaderName;
	}

	public String getNewOfficeName() {
		return newOfficeName;
	}

	public void setNewOfficeName(String newOfficeName) {
		this.newOfficeName = newOfficeName;
	}

	public String getOfficeLeaderName() {
		return officeLeaderName;
	}

	public void setOfficeLeaderName(String officeLeaderName) {
		this.officeLeaderName = officeLeaderName;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getCarrierGoodsName() {
		return carrierGoodsName;
	}

	public void setCarrierGoodsName(String carrierGoodsName) {
		this.carrierGoodsName = carrierGoodsName;
	}

	public String getProjectTypeName() {
		return projectTypeName;
	}

	public void setProjectTypeName(String projectTypeName) {
		this.projectTypeName = projectTypeName;
	}

	public String getInvoiceModeName() {
		return invoiceModeName;
	}

	public void setInvoiceModeName(String invoiceModeName) {
		this.invoiceModeName = invoiceModeName;
	}

	public String getProjectTrusteeshiptName() {
		return projectTrusteeshiptName;
	}

	public void setProjectTrusteeshiptName(String projectTrusteeshiptName) {
		this.projectTrusteeshiptName = projectTrusteeshiptName;
	}

	public String getTrusteeshiptChannelName() {
		return trusteeshiptChannelName;
	}

	public void setTrusteeshiptChannelName(String trusteeshiptChannelName) {
		this.trusteeshiptChannelName = trusteeshiptChannelName;
	}

    public String getImplyLeaderName() {
        return implyLeaderName;
    }

    public void setImplyLeaderName(String implyLeaderName) {
        this.implyLeaderName = implyLeaderName;
    }

    public String getSelfMarketingName() {
		return selfMarketingName;
	}

	public void setSelfMarketingName(String selfMarketingName) {
		this.selfMarketingName = selfMarketingName;
	}

	public String getProjectAgentName() {
		return projectAgentName;
	}

	public void setProjectAgentName(String projectAgentName) {
		this.projectAgentName = projectAgentName;
	}

	public String getTruckLeaderName() {
		return truckLeaderName;
	}

	public void setTruckLeaderName(String truckLeaderName) {
		this.truckLeaderName = truckLeaderName;
	}

	public String getOilGasName() {
		return oilGasName;
	}

	public void setOilGasName(String oilGasName) {
		this.oilGasName = oilGasName;
	}

	public String getCallTruckName() {
		return callTruckName;
	}

	public void setCallTruckName(String callTruckName) {
		this.callTruckName = callTruckName;
	}

	public String getProjectTradeName() {
		return projectTradeName;
	}

	public void setProjectTradeName(String projectTradeName) {
		this.projectTradeName = projectTradeName;
	}

	public String getAccountPeriodName() {
		return accountPeriodName;
	}

	public void setAccountPeriodName(String accountPeriodName) {
		this.accountPeriodName = accountPeriodName;
	}

	public String getNetworkBusinessName() {
		return networkBusinessName;
	}

	public void setNetworkBusinessName(String networkBusinessName) {
		this.networkBusinessName = networkBusinessName;
	}

	public String getProjectTrayName() {
		return projectTrayName;
	}

	public void setProjectTrayName(String projectTrayName) {
		this.projectTrayName = projectTrayName;
	}

	public String getReturnPointName() {
		return returnPointName;
	}

	public void setReturnPointName(String returnPointName) {
		this.returnPointName = returnPointName;
	}

	public String getProjectApprovalStatusName() {
		return projectApprovalStatusName;
	}

	public void setProjectApprovalStatusName(String projectApprovalStatusName) {
		this.projectApprovalStatusName = projectApprovalStatusName;
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

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
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

	public List<MainProjectApprovalLinkmanResp> getProjectApprovalLinkman() {
		return projectApprovalLinkman;
	}

	public void setProjectApprovalLinkman(List<MainProjectApprovalLinkmanResp> projectApprovalLinkman) {
		this.projectApprovalLinkman = projectApprovalLinkman;
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

	public int getIsBack() {
		return isBack;
	}

	public void setIsBack(int isBack) {
		this.isBack = isBack;
	}

	public int getIsCancel() {
		return isCancel;
	}

	public void setIsCancel(int isCancel) {
		this.isCancel = isCancel;
	}

	public String getModify() {
		return modify;
	}

	public void setModify(String modify) {
		this.modify = modify;
	}

	public String getProjectLevelName() {
		return projectLevelName;
	}

	public void setProjectLevelName(String projectLevelName) {
		this.projectLevelName = projectLevelName;
	}

	@Override
	public String toString() {
		return "MainProjectApprovalFlowResp{" +
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
				", carrierGoods=" + Arrays.toString(carrierGoods) +
				", projectLevel='" + projectLevel + '\'' +
				", projectLevelName='" + projectLevelName + '\'' +
				", projectType='" + projectType + '\'' +
				", invoicingFrequency='" + invoicingFrequency + '\'' +
				", transExpensesPlan=" + transExpensesPlan +
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
				", flowFinishTime=" + flowFinishTime +
				", projectApprovalLinkman=" + projectApprovalLinkman +
				", isBack=" + isBack +
				", isCancel=" + isCancel +
				", modify='" + modify + '\'' +
				'}';
	}
}