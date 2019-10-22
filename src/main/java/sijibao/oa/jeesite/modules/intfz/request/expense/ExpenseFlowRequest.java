package sijibao.oa.jeesite.modules.intfz.request.expense;

import java.util.Arrays;
import java.util.List;

import com.sijibao.oa.modules.intfz.request.intfzwebreq.WebAttachmentReq;
import com.sijibao.oa.modules.intfz.validator.IntfzValid;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 报销流程接收实体
 * @author xuby
 */
@ApiModel(value="报销流程对象")
public class ExpenseFlowRequest {
	
	@IntfzValid(min=0, max = 32, nullable=true)
	@ApiModelProperty(value="流程申请ID")
	private String id;           //ID，第一次申请不需要传递，重新申请时需要传递
	
	@IntfzValid(min=0, max = 32, nullable=true)
	@ApiModelProperty(value="流程实例ID")
	private String procInsId;		// 流程实例ID,第一次申请不需要传递，重新申请时需要传递
	
	@IntfzValid(min=0, max = 32, nullable=true)
	@ApiModelProperty(value="流程任务ID")
	private String taskId;          //任务ID,第一次申请不需要传递，重新申请时需要传递
	
	@IntfzValid(min=0, max = 32, nullable=true)
	@ApiModelProperty(value="申请时间")
	private String applyTime;		// 申请时间

    @ApiModelProperty(value = "成本中心部门ID")
    private String costCenterId;
	
//	@IntfzValid(min=0, max = 32, nullable=true)
//	@ApiModelProperty(value="费用合计")
//	private String expenseTotal;		// 费用合计
	
	@IntfzValid(min=0, max = 32, nullable=true)
	@ApiModelProperty(value="项目ID")
	private String projectId;       //项目ID
	
	@ApiModelProperty(value="项目负责人")
	private String projectPersonel;  //项目负责人
	
	@ApiModelProperty(value="报销明细列表")
	private List<ExpenseFlowDetailRequest> expenseDetail;
	
	@IntfzValid(min=0, max = 4000)
	@ApiModelProperty(value="备注")
	private String remarks; //备注
	
	@IntfzValid(min=0, max = 32, nullable=false)
	@ApiModelProperty(value="发票所属城市")
	private String taxCity; //发票所属城市
	@ApiModelProperty(value="附件信息")
	private String[] expenseAttachment; //附件信息
	@ApiModelProperty(value="附件信息(Web端)")
	private List<WebAttachmentReq> expenseAttachmentWeb; //附件信息(Web)
	@ApiModelProperty(value="关联主题流程编号")
	private String relationTheme; //接待申请流程编号
	@ApiModelProperty(value="陪客人员")
	private String[] employees;  //陪客人员
	@ApiModelProperty(value="报销类型1:常规报销,2:接待报销")
	private String applyType;  //报销类型 (1:常规报销,2:接待报销)

    @ApiModelProperty(value = "接待客户情况")//当报销类型为“接待报销”时
    @IntfzValid(max = 1000)
    private String customerSituation;

    private String producSide;//产品端

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
	public String getApplyTime() {
		return applyTime;
	}
	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getTaxCity() {
		return taxCity;
	}
	public void setTaxCity(String taxCity) {
		this.taxCity = taxCity;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public List<ExpenseFlowDetailRequest> getExpenseDetail() {
		return expenseDetail;
	}
	public void setExpenseDetail(List<ExpenseFlowDetailRequest> expenseDetail) {
		this.expenseDetail = expenseDetail;
	}
	public String[] getExpenseAttachment() {
		return expenseAttachment;
	}
	public void setExpenseAttachment(String[] expenseAttachment) {
		this.expenseAttachment = expenseAttachment;
	}
	public String getProjectPersonel() {
		return projectPersonel;
	}
	public void setProjectPersonel(String projectPersonel) {
		this.projectPersonel = projectPersonel;
	}
	public String[] getEmployees() {
		return employees;
	}
	public void setEmployees(String[] employees) {
		this.employees = employees;
	}
	public String getApplyType() {
		return applyType;
	}
	public void setApplyType(String applyType) {
		this.applyType = applyType;
	}
	public List<WebAttachmentReq> getExpenseAttachmentWeb() {
		return expenseAttachmentWeb;
	}
	public void setExpenseAttachmentWeb(List<WebAttachmentReq> expenseAttachmentWeb) {
		this.expenseAttachmentWeb = expenseAttachmentWeb;
	}
	public String getRelationTheme() {
		return relationTheme;
	}
	public void setRelationTheme(String relationTheme) {
		this.relationTheme = relationTheme;
	}
    public String getCustomerSituation() {
        return customerSituation;
    }
    public void setCustomerSituation(String customerSituation) {
        this.customerSituation = customerSituation;
    }
	public String getProducSide() {
		return producSide;
	}
	public void setProducSide(String producSide) {
		this.producSide = producSide;
	}

    public String getCostCenterId() {
        return costCenterId;
    }

    public void setCostCenterId(String costCenterId) {
        this.costCenterId = costCenterId;
    }

    @Override
    public String toString() {
        return "ExpenseFlowRequest{" +
                "id='" + id + '\'' +
                ", procInsId='" + procInsId + '\'' +
                ", taskId='" + taskId + '\'' +
                ", applyTime='" + applyTime + '\'' +
                ", costCenterId='" + costCenterId + '\'' +
                ", projectId='" + projectId + '\'' +
                ", projectPersonel='" + projectPersonel + '\'' +
                ", remarks='" + remarks + '\'' +
                ", taxCity='" + taxCity + '\'' +
                ", expenseAttachment=" + Arrays.toString(expenseAttachment) +
                ", relationTheme='" + relationTheme + '\'' +
                ", employees=" + Arrays.toString(employees) +
                ", applyType='" + applyType + '\'' +
                ", customerSituation='" + customerSituation + '\'' +
                ", producSide='" + producSide + '\'' +
                '}';
    }
}
