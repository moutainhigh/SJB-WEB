package sijibao.oa.jeesite.modules.intfz.response.expense;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.sijibao.oa.common.utils.DateUtils;
import com.sijibao.oa.common.utils.StringUtils;
import com.sijibao.oa.modules.flow.entity.ExpenseFlow;
import com.sijibao.oa.modules.sys.utils.DictUtils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 报销费用明细Entity
 */
@ApiModel(value="报销基本信息")
public class ExpenseFlowResponse {
	
	@ApiModelProperty(value="主键")
	private String id;
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
	@ApiModelProperty(value="申请人岗位")
	private String postName; //申请人岗位
	@ApiModelProperty(value="申请时间")
	private String applyTime;		// 申请时间
	@ApiModelProperty(value="票据数量")
	private Integer billNum;		// 票据数量
	@ApiModelProperty(value="报销费用合计")
	private BigDecimal expenseTotal;		// 费用合计
	@ApiModelProperty(value="项目名称")
	private String projectLabel;		// 项目名称
	@ApiModelProperty(value="项目编号")
	private String projectName;		// 项目ID
	@ApiModelProperty(value="项目ID")
	private String projectId;		// 项目ID
	@ApiModelProperty(value="项目负责人")
	private String projectPersonel;  //项目负责人
	@ApiModelProperty(value="查询-申请时间-开始")
	private String beginApplyTime;		// 开始 申请时间
	@ApiModelProperty(value="查询-申请时间-结束")
	private String endApplyTime;		// 结束 申请时间
	@ApiModelProperty(value="报销状态:1审批结束2审批中3单据被驳回4单据保存")
	private String expenseStatus;   //报销状态
	@ApiModelProperty(value="报销状态描述")
	private String expenseStatusDesc;	//报销状态描述
	@ApiModelProperty(value="所属部门名称")
	private String officeName;		//所属部门
    @ApiModelProperty(value="成本中心ID")
    private String costCenterId;		//成本中心部门ID
    @ApiModelProperty(value="成本中心名称")
    private String costCenterName;		//成本中心部门名称
	@ApiModelProperty(value="备注")
	private String remarks;			//备注
	@ApiModelProperty(value="是否当前人:1是0否")
	private int local;			//当前人:1
	@ApiModelProperty(value="发票所属公司编号")
	private String taxCity; //发票所属城市
	@ApiModelProperty(value="发票所属公司名称")
	private String taxCityName; //发票所属城市名称
	@ApiModelProperty(value="附件信息")
	private String[] expenseAttachment; //附件信息
	@ApiModelProperty(value="附件前缀")
	private String expenseAttachmentPrefix; //附件前缀
	@ApiModelProperty(value="web端附件信息(包含附件名称)")
	private List<Map<String,Object>>  expenseAttachmentWeb; //web端附件信息(包含附件名称)
	@ApiModelProperty(value="当前环节是否可编辑信息(1:允许，0：不允许)")
	private int isDeit;      //是否可编辑 todo 单词拼写错误 isEdit
	@ApiModelProperty(value="接待申请流程编号")
	private String relationTheme; //关联主题，即接待申请流程编号
	@ApiModelProperty(value="接待申请主题")
	private String relationThemeName; //关联主题名称，即接待申请流程名称
    @ApiModelProperty(value = "接待客户情况")
    private String customerSituation;//注：当报销类型为“接待报销”时
    @ApiModelProperty(value="陪客人数")
    private Integer recpParticNum;		// 陪客人数
	@ApiModelProperty(value="陪客人员")
	private String[] employees;  //陪客人员
	@ApiModelProperty(value="陪客人员姓名")
	private String[] employeesName;  //陪客人员姓名
	@ApiModelProperty(value="报销类型 (1:常规报销,2:接待报销)")
	private String applyType;  //报销类型 (1:常规报销,2:接待报销)
	@ApiModelProperty(value="报销类型名称")
	private String applyTypeName; //报销类型名称

	@ApiModelProperty(value = "出差报销关联类型")
	private String relType; //关联类型，1关联主题，2关联项目，3不关联
//	@ApiModelProperty(value="出差报销分类")
//	private List<String> travelExpenseTypeList;// 出差报销分类id列表
	@ApiModelProperty(value="出差报销分类名称")
	private List<String> travelExpenseTypeListName;// 出差报销分类name列表
//	@ApiModelProperty(value="随行人员")
//	private List<String> entourageList;// 随行人员id列表
	@ApiModelProperty(value="随行人员姓名")
	private List<String> entourageListName;// 随行人员name列表

	@ApiModelProperty(value="编辑节点")
	private String modify; //编辑节点
	@ApiModelProperty(value="提前打款金额")
	private BigDecimal bringAmount; //提前打款金额
	@ApiModelProperty(value="提前打款状态(0:未处理，1：已处理)")
	private String bringRemitStatus; //提前打款状态(0:未处理，1：已处理)

    public ExpenseFlowResponse(){}

	public ExpenseFlowResponse(ExpenseFlow expenseFlow) {
		super();
		this.id = expenseFlow.getId();
		this.procInsId = expenseFlow.getProcInsId();
		this.procCode = expenseFlow.getProcCode();
		this.procName = expenseFlow.getProcName();
		this.applyPerCode = expenseFlow.getApplyPerCode();
		this.applyPerName = expenseFlow.getApplyPerName();
		if(expenseFlow.getApplyTime() != null){
			this.applyTime = DateUtils.formatDate(expenseFlow.getApplyTime(), DateUtils.YYYY_MM_DD);
		}
		if(StringUtils.isNotBlank(expenseFlow.getBillNum())){
			this.billNum = Integer.valueOf(expenseFlow.getBillNum());
		}
		this.expenseTotal = expenseFlow.getExpenseTotal()==null?BigDecimal.ZERO:expenseFlow.getExpenseTotal();
		this.projectLabel = expenseFlow.getProjectName();
		this.projectName = expenseFlow.getProjectId();
		this.projectId = expenseFlow.getProjectId();
		this.projectPersonel = expenseFlow.getProjectPersonel();
		if(expenseFlow.getBeginApplyTime() != null){
			this.beginApplyTime = DateUtils.formatDate(expenseFlow.getBeginApplyTime(), DateUtils.YYYY_MM_DD);
		}
		if(expenseFlow.getEndApplyTime() != null){
			this.endApplyTime = DateUtils.formatDate(expenseFlow.getEndApplyTime(), DateUtils.YYYY_MM_DD);
		}
		this.expenseStatus = expenseFlow.getExpenseStatus();
		this.expenseStatusDesc = DictUtils.getDictLabel(expenseFlow.getExpenseStatus(), "expense_status", "");

		this.costCenterId = expenseFlow.getCostCenterId();
		this.costCenterName = expenseFlow.getCostCenterName();
		this.officeName = expenseFlow.getOffice().getName();
		this.remarks = expenseFlow.getRemarks();
		this.taxCity = expenseFlow.getTaxCityCode();
		this.taxCityName = expenseFlow.getTaxCityName();
		this.relationTheme = expenseFlow.getRecpProcCode();
		this.recpParticNum = expenseFlow.getRecpParticNum();
		this.customerSituation = expenseFlow.getCustomerSituation();
		this.applyType = expenseFlow.getApplyType();
		this.applyTypeName = DictUtils.getDictLabel(expenseFlow.getApplyType(), "oa_expense_type", "");
		this.bringAmount = expenseFlow.getBringAmount();
		this.bringRemitStatus = expenseFlow.getBringRemitStatus();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getProcInsId() {
		return procInsId;
	}

	public String getProcCode() {
		return procCode;
	}

	public String getProcName() {
		return procName;
	}

	public String getApplyPerCode() {
		return applyPerCode;
	}

	public String getApplyPerName() {
		return applyPerName;
	}

	public String getApplyTime() {
		return applyTime;
	}

	public Integer getBillNum() {
		return billNum;
	}

	public BigDecimal getExpenseTotal() {
		return expenseTotal;
	}

	public String getProjectName() {
		return projectName;
	}

	public String getBeginApplyTime() {
		return beginApplyTime;
	}

	public String getEndApplyTime() {
		return endApplyTime;
	}

	public String getExpenseStatus() {
		return expenseStatus;
	}

	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}

	public void setProcCode(String procCode) {
		this.procCode = procCode;
	}

	public void setProcName(String procName) {
		this.procName = procName;
	}

	public void setApplyPerCode(String applyPerCode) {
		this.applyPerCode = applyPerCode;
	}

	public void setApplyPerName(String applyPerName) {
		this.applyPerName = applyPerName;
	}

	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
	}

	public void setBillNum(Integer billNum) {
		this.billNum = billNum;
	}

	public void setExpenseTotal(BigDecimal expenseTotal) {
		this.expenseTotal = expenseTotal;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public void setBeginApplyTime(String beginApplyTime) {
		this.beginApplyTime = beginApplyTime;
	}

	public void setEndApplyTime(String endApplyTime) {
		this.endApplyTime = endApplyTime;
	}

	public void setExpenseStatus(String expenseStatus) {
		this.expenseStatus = expenseStatus;
	}

	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

	public String getExpenseStatusDesc() {
		return expenseStatusDesc;
	}

	public void setExpenseStatusDesc(String expenseStatusDesc) {
		this.expenseStatusDesc = expenseStatusDesc;
	}

	public int getLocal() {
		return local;
	}

	public void setLocal(int local) {
		this.local = local;
	}

	public String getTaxCityName() {
		return taxCityName;
	}

	public void setTaxCityName(String taxCityName) {
		this.taxCityName = taxCityName;
	}

	public String getProjectLabel() {
		return projectLabel;
	}

	public void setProjectLabel(String projectLabel) {
		this.projectLabel = projectLabel;
	}

	public String getTaxCity() {
		return taxCity;
	}

	public void setTaxCity(String taxCity) {
		this.taxCity = taxCity;
	}

	public String[] getExpenseAttachment() {
		return expenseAttachment;
	}

	public void setExpenseAttachment(String[] expenseAttachment) {
		this.expenseAttachment = expenseAttachment;
	}

	public String getExpenseAttachmentPrefix() {
		return expenseAttachmentPrefix;
	}

	public void setExpenseAttachmentPrefix(String expenseAttachmentPrefix) {
		this.expenseAttachmentPrefix = expenseAttachmentPrefix;
	}

	public int getIsDeit() {
		return isDeit;
	}

	public void setIsDeit(int isDeit) {
		this.isDeit = isDeit;
	}

	public String getProjectPersonel() {
		return projectPersonel;
	}

	public void setProjectPersonel(String projectPersonel) {
		this.projectPersonel = projectPersonel;
	}

	public Integer getRecpParticNum() {
		return recpParticNum;
	}

	public void setRecpParticNum(Integer recpParticNum) {
		this.recpParticNum = recpParticNum;
	}

    public String getCustomerSituation() {
        return customerSituation;
    }

    public void setCustomerSituation(String customerSituation) {
        this.customerSituation = customerSituation;
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

	public String getApplyTypeName() {
		return applyTypeName;
	}

	public void setApplyTypeName(String applyTypeName) {
		this.applyTypeName = applyTypeName;
	}

	public String[] getEmployeesName() {
		return employeesName;
	}

	public void setEmployeesName(String[] employeesName) {
		this.employeesName = employeesName;
	}

	public String getModify() {
		return modify;
	}

	public void setModify(String modify) {
		this.modify = modify;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public List<Map<String, Object>> getExpenseAttachmentWeb() {
		return expenseAttachmentWeb;
	}

	public void setExpenseAttachmentWeb(List<Map<String, Object>> expenseAttachmentWeb) {
		this.expenseAttachmentWeb = expenseAttachmentWeb;
	}

	public String getPostName() {
		return postName;
	}

	public void setPostName(String postName) {
		this.postName = postName;
	}

	public BigDecimal getBringAmount() {
		return bringAmount;
	}

	public void setBringAmount(BigDecimal bringAmount) {
		this.bringAmount = bringAmount;
	}

	public String getBringRemitStatus() {
		return bringRemitStatus;
	}

	public void setBringRemitStatus(String bringRemitStatus) {
		this.bringRemitStatus = bringRemitStatus;
	}

	public String getRelationTheme() {
		return relationTheme;
	}

	public void setRelationTheme(String relationTheme) {
		this.relationTheme = relationTheme;
	}

	public String getRelationThemeName() {
		return relationThemeName;
	}

	public void setRelationThemeName(String relationThemeName) {
		this.relationThemeName = relationThemeName;
	}

	public String getRelType() {
		return relType;
	}

	public void setRelType(String relType) {
		this.relType = relType;
	}

	public List<String> getTravelExpenseTypeListName() {
		return travelExpenseTypeListName;
	}

	public void setTravelExpenseTypeListName(List<String> travelExpenseTypeListName) {
		this.travelExpenseTypeListName = travelExpenseTypeListName;
	}

	public List<String> getEntourageListName() {
		return entourageListName;
	}

	public void setEntourageListName(List<String> entourageListName) {
		this.entourageListName = entourageListName;
	}

    public String getCostCenterId() {
        return costCenterId;
    }

    public void setCostCenterId(String costCenterId) {
        this.costCenterId = costCenterId;
    }

    public String getCostCenterName() {
        return costCenterName;
    }

    public void setCostCenterName(String costCenterName) {
        this.costCenterName = costCenterName;
    }
}