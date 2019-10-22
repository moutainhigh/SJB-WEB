package sijibao.oa.jeesite.modules.intfz.response.common;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 待办任务列表
 * @author xuhang
 * @time 2017年12月27日 下午3:04:46
 */
@ApiModel(value="任务响应对象")
public class TaskResponse
{
	@ApiModelProperty(value="流程任务ID")
	private String taskId;
	@ApiModelProperty(value="流程任务执行ID")
	private String taskExecutionId;
	@ApiModelProperty(value="流程任务名称")
	private String taskName;
	@ApiModelProperty(value="流程任务节点KEY")
	private String taskDefinitionKey;
	@ApiModelProperty(value="流程任务执行者编码")
	private String taskAssignee;
	@ApiModelProperty(value="流程实例ID")
	private String taskProcessInstanceId;
	@ApiModelProperty(value="流程定义ID")
	private String taskProcessDefinitionId;
	@ApiModelProperty(value="流程节点描述")
	private String taskDescription;
	@ApiModelProperty(value="流程任务创建时间")
	private String taskCreateTime;
	
	@ApiModelProperty(value="流程定义名称")
	private String procDefName;
	@ApiModelProperty(value="流程定义版本")
	private String procDefVersion;
	
	@ApiModelProperty(value="流程任务状态(todo/claim/finish)")
	private String status;
	@ApiModelProperty(value="流程任务状态描述")
	private String statusDesc;
	@ApiModelProperty(value="流程标题")
	private String varsTitle;
	
	@ApiModelProperty(value="报销总金额")
	private BigDecimal total;	//总金额
	@ApiModelProperty(value="报销申请人名称")
	private String applyName;		// 申请人名称
	
	@ApiModelProperty(value="报销流程编号")
	private String procCode;	//流程编号
	@ApiModelProperty(value="备注")
	private String remark;		//备注
	@ApiModelProperty(value="发票所属公司编号")
	private String taxCityCode; //发票所属城市
	@ApiModelProperty(value="发票所属公司名称")
	private String taxCityName; //发票所属城市名称
	@ApiModelProperty(value="业务绑定ID")
	private String businessId; //报销业务ID
	@ApiModelProperty(value="单据所属部门")
	private String officeName; //单据所属部门
	@ApiModelProperty(value="业务单据类型,1:市场需求申请，2：实施需求发起，3：报销申请，4：接待申请")
	private String billType; //单据类型
	@ApiModelProperty(value="单据状态")
	private String billStatus; //单据状态
	@ApiModelProperty(value="单据状态文案")
	private String billStatusTxt; //单据状态文案
	public TaskResponse(){}
	public TaskResponse(String taskId, String taskExecutionId, String taskName, String taskDefinitionKey,
			String taskAssignee, String taskProcessInstanceId, String taskProcessDefinitionId, String taskDescription,
			String taskCreateTime, String procDefName, String procDefVersion, String status, String statusDesc, String varsTitle, 
			BigDecimal total, String applyName, String procCode, String remark,String taxCityCode,String taxCityName,String businessId,
			String officeName,String billType,String billStatus,String billStatusTxt) {
		super();
		this.taskId = taskId;
		this.taskExecutionId = taskExecutionId;
		this.taskName = taskName;
		this.taskDefinitionKey = taskDefinitionKey;
		this.taskAssignee = taskAssignee;
		this.taskProcessInstanceId = taskProcessInstanceId;
		this.taskProcessDefinitionId = taskProcessDefinitionId;
		this.taskDescription = taskDescription;
		this.taskCreateTime = taskCreateTime;
		this.procDefName = procDefName;
		this.procDefVersion = procDefVersion;
		this.status = status;
		this.statusDesc = statusDesc;
		this.varsTitle = varsTitle;
		this.total = total;
		this.applyName = applyName;
		this.procCode = procCode;
		this.remark = remark;
		this.taxCityCode = taxCityCode;
		this.taxCityName = taxCityName;
		this.businessId = businessId;
		this.officeName = officeName;
		this.billType = billType;
		this.billStatus = billStatus;
		this.billStatusTxt = billStatusTxt;
	}
	public TaskResponse(String taskId, String taskExecutionId, String taskName, String taskDefinitionKey,
			String taskAssignee, String taskProcessInstanceId, String taskProcessDefinitionId, String taskDescription,
			String taskCreateTime, String procDefName, String procDefVersion, String status, String statusDesc, String varsTitle, 
			BigDecimal total, String applyName, String procCode, String remark,String taxCityCode,String taxCityName,String businessId,
			String officeName,String billType) {
		super();
		this.taskId = taskId;
		this.taskExecutionId = taskExecutionId;
		this.taskName = taskName;
		this.taskDefinitionKey = taskDefinitionKey;
		this.taskAssignee = taskAssignee;
		this.taskProcessInstanceId = taskProcessInstanceId;
		this.taskProcessDefinitionId = taskProcessDefinitionId;
		this.taskDescription = taskDescription;
		this.taskCreateTime = taskCreateTime;
		this.procDefName = procDefName;
		this.procDefVersion = procDefVersion;
		this.status = status;
		this.statusDesc = statusDesc;
		this.varsTitle = varsTitle;
		this.total = total;
		this.applyName = applyName;
		this.procCode = procCode;
		this.remark = remark;
		this.taxCityCode = taxCityCode;
		this.taxCityName = taxCityName;
		this.businessId = businessId;
		this.officeName = officeName;
		this.billType = billType;
	}
	public String getTaskId() {
		return taskId;
	}
	public String getTaskExecutionId() {
		return taskExecutionId;
	}
	public String getTaskName() {
		return taskName;
	}
	public String getTaskDefinitionKey() {
		return taskDefinitionKey;
	}
	public String getTaskAssignee() {
		return taskAssignee;
	}
	public String getTaskProcessInstanceId() {
		return taskProcessInstanceId;
	}
	public String getTaskProcessDefinitionId() {
		return taskProcessDefinitionId;
	}
	public String getTaskDescription() {
		return taskDescription;
	}
	public String getTaskCreateTime() {
		return taskCreateTime;
	}
	public String getProcDefName() {
		return procDefName;
	}
	public String getProcDefVersion() {
		return procDefVersion;
	}
	public String getStatus() {
		return status;
	}
	public String getVarsTitle() {
		return varsTitle;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public void setTaskExecutionId(String taskExecutionId) {
		this.taskExecutionId = taskExecutionId;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public void setTaskDefinitionKey(String taskDefinitionKey) {
		this.taskDefinitionKey = taskDefinitionKey;
	}
	public void setTaskAssignee(String taskAssignee) {
		this.taskAssignee = taskAssignee;
	}
	public void setTaskProcessInstanceId(String taskProcessInstanceId) {
		this.taskProcessInstanceId = taskProcessInstanceId;
	}
	public void setTaskProcessDefinitionId(String taskProcessDefinitionId) {
		this.taskProcessDefinitionId = taskProcessDefinitionId;
	}
	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}
	public void setTaskCreateTime(String taskCreateTime) {
		this.taskCreateTime = taskCreateTime;
	}
	public void setProcDefName(String procDefName) {
		this.procDefName = procDefName;
	}
	public void setProcDefVersion(String procDefVersion) {
		this.procDefVersion = procDefVersion;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setVarsTitle(String varsTitle) {
		this.varsTitle = varsTitle;
	}
	public BigDecimal getTotal() {
		return total;
	}
	public String getApplyName() {
		return applyName;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	public void setApplyName(String applyName) {
		this.applyName = applyName;
	}
	public String getProcCode() {
		return procCode;
	}
	public String getRemark() {
		return remark;
	}
	public void setProcCode(String procCode) {
		this.procCode = procCode;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getStatusDesc() {
		return statusDesc;
	}
	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}
	public String getTaxCityCode() {
		return taxCityCode;
	}
	public void setTaxCityCode(String taxCityCode) {
		this.taxCityCode = taxCityCode;
	}
	public String getTaxCityName() {
		return taxCityName;
	}
	public void setTaxCityName(String taxCityName) {
		this.taxCityName = taxCityName;
	}
	public String getBusinessId() {
		return businessId;
	}
	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}
	public String getOfficeName() {
		return officeName;
	}
	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}
	public String getBillType() {
		return billType;
	}
	public void setBillType(String billType) {
		this.billType = billType;
	}
	public String getBillStatus() {
		return billStatus;
	}
	public void setBillStatus(String billStatus) {
		this.billStatus = billStatus;
	}
	public String getBillStatusTxt() {
		return billStatusTxt;
	}
	public void setBillStatusTxt(String billStatusTxt) {
		this.billStatusTxt = billStatusTxt;
	}
}
