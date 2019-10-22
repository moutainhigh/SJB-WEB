package sijibao.oa.jeesite.modules.intfz.response.intfzwebresp;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.swagger.annotations.ApiModelProperty;

/**
 * 查询报销关联主题信息 返回对象
 * @author xuby
 */
public class QueryFlowRevenceResponse {
	@ApiModelProperty(value="主键ID")
	private String id; //主键ID
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
	@ApiModelProperty(value="所属部门名称")
	private String officeName;		//所属部门
	@ApiModelProperty(value="申请时间")
	private String applyTime;		// 申请时间
	@ApiModelProperty(value = "关联类型")
	private String relType; //关联类型，1关联主题，2关联项目，3不关联
	@ApiModelProperty(value="项目编号")
	private String projectId;		// 项目编号
	@ApiModelProperty(value="项目名称")
	private String projectName;		// 项目名称
	@ApiModelProperty(value="项目负责人")
	private String projectPersonel;  //项目负责人
	@ApiModelProperty(value="报销分类")
	private List<String> travelExpenseTypeList;// 出差报销分类id列表
	@ApiModelProperty(value="报销分类名称")
	private List<String> travelExpenseTypeListName;// 出差报销分类name列表
	@ApiModelProperty(value="随行人员")
	@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)//字段非必填，不参与序列化，为null依旧返回[]
	private List<String> entourageList;// 随行人员id列表
	@ApiModelProperty(value="随行人员姓名")
	@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)//字段非必填，不参与序列化，为null依旧返回[]
	private List<String> entourageListName;// 随行人员name列表
	@ApiModelProperty(value="预算总额")
	private BigDecimal budgetTotal;		// 预算总额
	@ApiModelProperty(value="申请状态")
	private String status;   //申请状态
	@ApiModelProperty(value="申请状态描述")
	private String statusValue;   //申请状态描述
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
	public String getOfficeName() {
		return officeName;
	}
	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}
	public String getApplyTime() {
		return applyTime;
	}
	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
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
	public String getProjectPersonel() {
		return projectPersonel;
	}
	public void setProjectPersonel(String projectPersonel) {
		this.projectPersonel = projectPersonel;
	}
	public BigDecimal getBudgetTotal() {
		return budgetTotal;
	}
	public void setBudgetTotal(BigDecimal budgetTotal) {
		this.budgetTotal = budgetTotal;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatusValue() {
		return statusValue;
	}
	public void setStatusValue(String statusValue) {
		this.statusValue = statusValue;
	}

	public String getRelType() {
		return relType;
	}

	public void setRelType(String relType) {
		this.relType = relType;
	}

	public List<String> getTravelExpenseTypeList() {
		return travelExpenseTypeList;
	}

	public void setTravelExpenseTypeList(List<String> travelExpenseTypeList) {
		this.travelExpenseTypeList = travelExpenseTypeList;
	}

	public List<String> getTravelExpenseTypeListName() {
		return travelExpenseTypeListName;
	}

	public void setTravelExpenseTypeListName(List<String> travelExpenseTypeListName) {
		this.travelExpenseTypeListName = travelExpenseTypeListName;
	}

	public List<String> getEntourageList() {
		return entourageList;
	}

	public void setEntourageList(List<String> entourageList) {
		this.entourageList = entourageList;
	}

	public List<String> getEntourageListName() {
		return entourageListName;
	}

	public void setEntourageListName(List<String> entourageListName) {
		this.entourageListName = entourageListName;
	}
}
