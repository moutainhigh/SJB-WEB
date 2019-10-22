/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.intfz.response.recp;

import java.math.BigDecimal;
import java.util.Date;

import com.sijibao.oa.common.persistence.ActEntity;
import com.sijibao.oa.common.utils.DateUtils;
import com.sijibao.oa.modules.flow.entity.RecpFlow;
import com.sijibao.oa.modules.sys.utils.DictUtils;

import io.swagger.annotations.ApiModelProperty;

/**
 * 接待申请Entity
 * @author wanxb
 * @version 2018-04-23
 */
public class RecpFlowResponse extends ActEntity<RecpFlowResponse> {
	
	private static final long serialVersionUID = 1L;
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
	@ApiModelProperty(value="申请人岗位")
	private String postName; //申请人岗位
	@ApiModelProperty(value="所属部门名称")
	private String officeName;		//所属部门
	@ApiModelProperty(value="申请时间")
	private String applyTime;		// 申请时间
	@ApiModelProperty(value="接待主题")	
	private String recpTheme;		// 接待主题
	@ApiModelProperty(value="项目编号")
	private String projectId;		// 项目编号
	@ApiModelProperty(value="项目名称")
	private String projectName;		// 项目名称
	@ApiModelProperty(value="项目负责人")
	private String projectPersonel;  //项目负责人
	@ApiModelProperty(value="接待人数")
	private Integer recpNum;		// 接待人数
	@ApiModelProperty(value="预计接待时间：yyyy-MM-dd HH")
	private long recpTime=0l;		// 预计接待时间：yyyy-MM-dd HH
	@ApiModelProperty(value="陪客人数")
	private Integer recpParticNum;		// 陪客人数
	@ApiModelProperty(value="预算总额")
	private BigDecimal budgetTotal;		// 预算总额
	@ApiModelProperty(value="申请状态")
	private String recpStatus;   //申请状态
	@ApiModelProperty(value="申请状态描述")
	private String recpStatusValue;   //申请状态描述
	@ApiModelProperty(value="陪客人员")
	private String[] employees; //陪客人员
	@ApiModelProperty(value="陪客人员姓名")
	private String[] employeesName; //陪客人员姓名
	@ApiModelProperty(value="当前环节是否可编辑信息(1:允许，0：不允许)")
	private int isDeit;      //是否可编辑信息
	@ApiModelProperty(value="是否当前人:1是0否")
	private int local;			//当前人:1
	@ApiModelProperty(value="备注")
	private String remarks;
	@ApiModelProperty(value="编辑节点")
	private String modify; //编辑节点
	public RecpFlowResponse() {
		super();
	}
	
	public RecpFlowResponse(RecpFlow recpFlow) {
		this.id = recpFlow.getId();
		this.procInsId = recpFlow.getProcInsId();
		this.procCode = recpFlow.getProcCode();
		this.procName = recpFlow.getProcName();
		this.applyPerCode = recpFlow.getApplyPerCode();
		this.applyPerName = recpFlow.getApplyPerName();
		if(recpFlow.getApplyTime() != null){
			this.applyTime = DateUtils.formatDate(recpFlow.getApplyTime(), DateUtils.YYYY_MM_DD);
		}
		if(recpFlow.getRecpTime() != null){
			this.recpTime = recpFlow.getRecpTime().getTime();			
		}
		this.recpTheme = recpFlow.getRecpTheme();
		this.projectId = recpFlow.getProjectId();
		this.projectName = recpFlow.getProjectName();
		this.projectPersonel = recpFlow.getProjectPersonel();
		this.recpNum = recpFlow.getRecpNum();
		this.recpParticNum = recpFlow.getRecpParticNum();
		this.budgetTotal = recpFlow.getBudgetTotal();
		this.recpStatus = recpFlow.getRecpStatus();
		this.recpStatusValue = DictUtils.getDictLabel(recpFlow.getRecpStatus(), "expense_status", "");
		this.remarks = recpFlow.getRemarks();
		this.officeName = recpFlow.getOffice().getName();
	}

	public RecpFlowResponse(String id){
		super(id);
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
	
	public String getRecpTheme() {
		return recpTheme;
	}

	public void setRecpTheme(String recpTheme) {
		this.recpTheme = recpTheme;
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
	
	public Integer getRecpNum() {
		return recpNum;
	}

	public void setRecpNum(Integer recpNum) {
		this.recpNum = recpNum;
	}
	
	public long getRecpTime() {
		return recpTime;
	}

	public void setRecpTime(long recpTime) {
		this.recpTime = recpTime;
	}
	
	public Integer getRecpParticNum() {
		return recpParticNum;
	}

	public void setRecpParticNum(Integer recpParticNum) {
		this.recpParticNum = recpParticNum;
	}
	
	public BigDecimal getBudgetTotal() {
		return budgetTotal;
	}

	public void setBudgetTotal(BigDecimal budgetTotal) {
		this.budgetTotal = budgetTotal;
	}
	
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getRecpStatus() {
		return recpStatus;
	}

	public void setRecpStatus(String recpStatus) {
		this.recpStatus = recpStatus;
	}

	public String getRecpStatusValue() {
		return recpStatusValue;
	}

	public void setRecpStatusValue(String recpStatusValue) {
		this.recpStatusValue = recpStatusValue;
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

	public int getIsDeit() {
		return isDeit;
	}

	public void setIsDeit(int isDeit) {
		this.isDeit = isDeit;
	}

	public int getLocal() {
		return local;
	}

	public void setLocal(int local) {
		this.local = local;
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

	public String[] getEmployeesName() {
		return employeesName;
	}

	public void setEmployeesName(String[] employeesName) {
		this.employeesName = employeesName;
	}

	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

	public String getModify() {
		return modify;
	}

	public void setModify(String modify) {
		this.modify = modify;
	}

	public String getPostName() {
		return postName;
	}

	public void setPostName(String postName) {
		this.postName = postName;
	}
	
}