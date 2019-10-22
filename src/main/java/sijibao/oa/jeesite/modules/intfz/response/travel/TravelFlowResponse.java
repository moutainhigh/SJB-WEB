/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.intfz.response.travel;

import java.math.BigDecimal;
import java.util.*;

import com.sijibao.oa.common.persistence.ActEntity;
import com.sijibao.oa.common.utils.DateUtils;
import com.sijibao.oa.common.utils.StringUtils;
import com.sijibao.oa.modules.flow.entity.TravelFlow;
import com.sijibao.oa.modules.sys.utils.DictUtils;
import com.sijibao.oa.modules.sys.utils.UserUtils;

import io.swagger.annotations.ApiModelProperty;

/**
 * 出差申请Entity
 * @author xuby
 * @version 2018-05-23
 */
public class TravelFlowResponse extends ActEntity<TravelFlowResponse> {

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
    @ApiModelProperty(value = "关联类型")
    private String relType; //关联类型，1关联主题，2关联项目，3不关联
	@ApiModelProperty(value="关联主题ID")
	private String relationTheme;		// 关联主题ID
	@ApiModelProperty(value="关联主题名称")
	private String relationThemeName;		// 关联主题名称
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
	private List<String> entourageList;// 随行人员id列表
	@ApiModelProperty(value="随行人员姓名")
	private List<String> entourageListName;// 随行人员name列表
	@ApiModelProperty(value="预算费用总额")
	private BigDecimal budgetTotal;		// 预算费用总额
	@ApiModelProperty(value="申请状态")
	private String recpStatus;   //申请状态
	@ApiModelProperty(value="申请状态")
	private String recpStatusValue;   //申请状态
	@ApiModelProperty(value="当前环节是否可编辑信息(1:允许，0：不允许)")
	private int isDeit;      //是否可编辑信息
	@ApiModelProperty(value="是否当前人:1是0否")
	private int local;			//当前人:1
	@ApiModelProperty(value="备注")
	private String remarks;
	@ApiModelProperty(value="编辑节点")
	private String modify; //编辑节点
	public TravelFlowResponse() {
		super();
	}

	public TravelFlowResponse(TravelFlow travelFlow){
		this.id = travelFlow.getId();
		this.procInsId = travelFlow.getProcInsId();
		this.procCode = travelFlow.getProcCode();
		this.procName = travelFlow.getProcName();
		this.applyPerCode = travelFlow.getApplyPerCode();
		this.applyPerName = travelFlow.getApplyPerName();
		this.officeName = travelFlow.getOffice().getName();
		if(travelFlow.getApplyTime() != null){
			this.applyTime = DateUtils.formatDate(travelFlow.getApplyTime(), DateUtils.YYYY_MM_DD);
		}
		this.relationTheme = travelFlow.getRelationTheme();
		this.projectId = travelFlow.getProjectId();
		this.projectName = travelFlow.getProjectName();
		this.projectPersonel = travelFlow.getProjectPersonel();

		// 出差报销分类(id列表和name列表)
        List<String> expenseTypeList;
        if(!"".equals(travelFlow.getTravelExpenseTypes())){
            expenseTypeList = Arrays.asList(travelFlow.getTravelExpenseTypes().split(","));
        }else {
            expenseTypeList = new ArrayList<>();
        }
		List<String> expenseTypeListName = new LinkedList<>();
		for(String s : expenseTypeList){
			expenseTypeListName.add(DictUtils.getDictLabel(s,"travel_expense_type",""));
		}
		this.travelExpenseTypeList = expenseTypeList;
		this.travelExpenseTypeListName = expenseTypeListName;
		// 随行人员(id列表和name列表)（字段非必填）
        List<String> entourageList;
        if(StringUtils.isNotBlank(travelFlow.getEntourages())){
            entourageList = Arrays.asList(travelFlow.getEntourages().split(","));
        }else {
            entourageList = new ArrayList<>();
        }
		List<String> entourageListName = new LinkedList<>();
		if(entourageList.size() > 0){
            for(String s : entourageList){
                entourageListName.add(UserUtils.get(s).getName());
            }
        }
		this.entourageList = entourageList;
		this.entourageListName = entourageListName;

		this.budgetTotal = travelFlow.getBudgetTotal();
		this.recpStatus = travelFlow.getTravelStatus();
		this.recpStatusValue =DictUtils.getDictLabel(travelFlow.getTravelStatus(), "expense_status", "");
		this.remarks = travelFlow.getRemarks();
	}

	public TravelFlowResponse(String id){
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

    public String getRelType() {
        return relType;
    }

    public void setRelType(String relType) {
        this.relType = relType;
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

	public String getRelationTheme() {
		return relationTheme;
	}

	public void setRelationTheme(String relationTheme) {
		this.relationTheme = relationTheme;
	}

	public String getPostName() {
		return postName;
	}

	public void setPostName(String postName) {
		this.postName = postName;
	}

	public String getRelationThemeName() {
		return relationThemeName;
	}

	public void setRelationThemeName(String relationThemeName) {
		this.relationThemeName = relationThemeName;
	}

}