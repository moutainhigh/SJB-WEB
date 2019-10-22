package sijibao.oa.jeesite.modules.intfz.response.intfzwebresp.projectinfo;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 项目管理--明细返回对象
 *
 * @author wuys
 */
@ApiModel(value = "项目管理--明细返回对象")
public class MainProjectInfoDetailResponse implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "主键id")
    private String id;                //主键id
    @ApiModelProperty(value = "项目名称")
    private String projectName;//项目名称
    @ApiModelProperty(value = "项目编码")
    private String projectCode;//项目Code
    @ApiModelProperty(value = "项目类型0公司项目1市场项目")
    private String projectType;//项目类型0公司项目1市场项目
    @ApiModelProperty(value = "项目类型名称")
    private String projectTypeName;//项目类型名称
    @ApiModelProperty(value = "归属部门id")
    private String officeId;//归属部门id
    @ApiModelProperty(value = "归属部门name")
    private String officeName;//归属部门name
    @ApiModelProperty(value = "归属客户id")
    private String custInfoId;//归属客户id
    @ApiModelProperty(value = "归属客户name")
    private String custInfoName;//归属客户name
    @ApiModelProperty(value = "项目状态0待上线1已上线2已关闭")
    private String projectState;//项目状态0待上线1已上线2已关闭
    @ApiModelProperty(value = "上线时间格式yyyy-MM-dd")
    private long onLineDate = 0l;//上线时间格式yyyy-MM-dd
    @ApiModelProperty(value = "项目负责人id")
    private String projectLeaderId;//项目负责人id
    @ApiModelProperty(value = "项目负责人name")
    private String projectLeaderName;//项目负责人name
    @ApiModelProperty(value = "市场负责人id")
    private String marketLeaderId;//市场负责人id
    @ApiModelProperty(value = "市场负责人name")
    private String marketLeaderName;//市场负责人name
    @ApiModelProperty(value = "实施负责人id")
    private String impleLeaderId;//实施负责人id
    @ApiModelProperty(value = "实施负责人name")
    private String impleLeaderName;//实施负责人name
    @ApiModelProperty(value = "备注")
    private String remarks;//备注

    @ApiModelProperty(value = "企业信息")
    private List<MainCompanyShirtResponse> mainCompany;

    @ApiModelProperty(value = "项目节点")
    private List<MainProjectNodeDetailResponse> projectNodeDetailResponse;//项目节点返回对象


    @ApiModelProperty(value="计划上线时间")
    private Long onlinePlanTime;
    @ApiModelProperty(value="项目等级ABCD")
    private String projectLevel;
    @ApiModelProperty(value="商务助理id")
    private String businessAssistantId;
    @ApiModelProperty(value="商务助理name")
    private String businessAssistantName;
    @ApiModelProperty(value="VIP客服id")
    private String vipCustomerId;
    @ApiModelProperty(value="VIP客服name")
    private String vipCustomerName;
    @ApiModelProperty(value="月开票频次（次/月）")
    private Integer invoicingFrequency;
    @ApiModelProperty(value="计划月运费(万元)")
    private Double transExpenssPlan;
    @ApiModelProperty(value="承运货物:1煤炭2钢铁3商砼4其他;object:{'carrierGood':'','carrierGoodName':''}")
    private List<Object> carrierGoods;

    @ApiModelProperty(value="项目管理负责人id")
    private String projectManagerId;
    @ApiModelProperty(value="项目管理负责人")
    private String projectManager;
    @ApiModelProperty(value="清结算id")
    private String accountLeaderId;
    @ApiModelProperty(value="清结算name")
    private String accountLeaderName;
    @ApiModelProperty(value="主客户name")
    private String mainCustName;//归属主客户name

    @ApiModelProperty(value="一般要求")
    private MainProjectGeneralRequireResponse generalRequireResponse;
    @ApiModelProperty(value="特殊要求")
    private MainProjectSpecialRequireResponse specialRequireResponse;

    @ApiModelProperty(value = "项目联系人")
    private List<MainProjectLinkmanDetailResponse> projectLinkmanDetailResponse;//项目联系人返回对象


    @ApiModelProperty(value = "新项目负责人name")
    private String newProjectLeaderName;
    @ApiModelProperty(value = "新归属部门name")
    private String newOfficeName;
    @ApiModelProperty(value = "新归属部门负责人name")
    private String officeLeaderName;
    @ApiModelProperty(value="流程项目调研标识")
    private String processFlag;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getProjectTypeName() {
        return projectTypeName;
    }

    public void setProjectTypeName(String projectTypeName) {
        this.projectTypeName = projectTypeName;
    }

    public String getOfficeId() {
        return officeId;
    }

    public void setOfficeId(String officeId) {
        this.officeId = officeId;
    }

    public String getCustInfoId() {
        return custInfoId;
    }

    public void setCustInfoId(String custInfoId) {
        this.custInfoId = custInfoId;
    }

    public String getProjectState() {
        return projectState;
    }

    public void setProjectState(String projectState) {
        this.projectState = projectState;
    }

    public long getOnLineDate() {
        return onLineDate;
    }

    public void setOnLineDate(long onLineDate) {
        this.onLineDate = onLineDate;
    }

    public String getProjectLeaderId() {
        return projectLeaderId;
    }

    public void setProjectLeaderId(String projectLeaderId) {
        this.projectLeaderId = projectLeaderId;
    }

    public String getMarketLeaderId() {
        return marketLeaderId;
    }

    public void setMarketLeaderId(String marketLeaderId) {
        this.marketLeaderId = marketLeaderId;
    }

    public String getImpleLeaderId() {
        return impleLeaderId;
    }

    public void setImpleLeaderId(String impleLeaderId) {
        this.impleLeaderId = impleLeaderId;
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

    public String getCustInfoName() {
        return custInfoName;
    }

    public void setCustInfoName(String custInfoName) {
        this.custInfoName = custInfoName;
    }

    public String getProjectLeaderName() {
        return projectLeaderName;
    }

    public void setProjectLeaderName(String projectLeaderName) {
        this.projectLeaderName = projectLeaderName;
    }

    public String getMarketLeaderName() {
        return marketLeaderName;
    }

    public void setMarketLeaderName(String marketLeaderName) {
        this.marketLeaderName = marketLeaderName;
    }

    public String getImpleLeaderName() {
        return impleLeaderName;
    }

    public void setImpleLeaderName(String impleLeaderName) {
        this.impleLeaderName = impleLeaderName;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public List<MainCompanyShirtResponse> getMainCompany() {
        return mainCompany;
    }

    public void setMainCompany(List<MainCompanyShirtResponse> mainCompany) {
        this.mainCompany = mainCompany;
    }

    public List<MainProjectNodeDetailResponse> getProjectNodeDetailResponse() {
        return projectNodeDetailResponse;
    }

    public void setProjectNodeDetailResponse(List<MainProjectNodeDetailResponse> projectNodeDetailResponse) {
        this.projectNodeDetailResponse = projectNodeDetailResponse;
    }

    public Long getOnlinePlanTime() {
        return onlinePlanTime;
    }

    public void setOnlinePlanTime(Long onlinePlanTime) {
        this.onlinePlanTime = onlinePlanTime;
    }

    public String getProjectLevel() {
        return projectLevel;
    }

    public void setProjectLevel(String projectLevel) {
        this.projectLevel = projectLevel;
    }

    public String getBusinessAssistantId() {
        return businessAssistantId;
    }

    public void setBusinessAssistantId(String businessAssistantId) {
        this.businessAssistantId = businessAssistantId;
    }

    public String getBusinessAssistantName() {
        return businessAssistantName;
    }

    public void setBusinessAssistantName(String businessAssistantName) {
        this.businessAssistantName = businessAssistantName;
    }

    public String getVipCustomerId() {
        return vipCustomerId;
    }

    public void setVipCustomerId(String vipCustomerId) {
        this.vipCustomerId = vipCustomerId;
    }

    public String getVipCustomerName() {
        return vipCustomerName;
    }

    public void setVipCustomerName(String vipCustomerName) {
        this.vipCustomerName = vipCustomerName;
    }

    public Integer getInvoicingFrequency() {
        return invoicingFrequency;
    }

    public void setInvoicingFrequency(Integer invoicingFrequency) {
        this.invoicingFrequency = invoicingFrequency;
    }

    public Double getTransExpenssPlan() {
        return transExpenssPlan;
    }

    public void setTransExpenssPlan(Double transExpenssPlan) {
        this.transExpenssPlan = transExpenssPlan;
    }

    public List<Object> getCarrierGoods() {
        return carrierGoods;
    }

    public void setCarrierGoods(List<Object> carrierGoods) {
        this.carrierGoods = carrierGoods;
    }

    public String getProjectManagerId() {
        return projectManagerId;
    }

    public void setProjectManagerId(String projectManagerId) {
        this.projectManagerId = projectManagerId;
    }

    public String getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(String projectManager) {
        this.projectManager = projectManager;
    }

    public String getAccountLeaderId() {
        return accountLeaderId;
    }

    public void setAccountLeaderId(String accountLeaderId) {
        this.accountLeaderId = accountLeaderId;
    }

    public String getAccountLeaderName() {
        return accountLeaderName;
    }

    public void setAccountLeaderName(String accountLeaderName) {
        this.accountLeaderName = accountLeaderName;
    }

    public List<MainProjectLinkmanDetailResponse> getProjectLinkmanDetailResponse() {
        return projectLinkmanDetailResponse;
    }

    public void setProjectLinkmanDetailResponse(List<MainProjectLinkmanDetailResponse> projectLinkmanDetailResponse) {
        this.projectLinkmanDetailResponse = projectLinkmanDetailResponse;
    }

    public MainProjectGeneralRequireResponse getGeneralRequireResponse() {
        return generalRequireResponse;
    }

    public void setGeneralRequireResponse(MainProjectGeneralRequireResponse generalRequireResponse) {
        this.generalRequireResponse = generalRequireResponse;
    }

    public MainProjectSpecialRequireResponse getSpecialRequireResponse() {
        return specialRequireResponse;
    }

    public void setSpecialRequireResponse(MainProjectSpecialRequireResponse specialRequireResponse) {
        this.specialRequireResponse = specialRequireResponse;
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

    public String getProcessFlag() {
        return processFlag;
    }

    public void setProcessFlag(String processFlag) {
        this.processFlag = processFlag;
    }

    public String getMainCustName() {
        return mainCustName;
    }

    public void setMainCustName(String mainCustName) {
        this.mainCustName = mainCustName;
    }

    @Override
    public String toString() {
        return "MainProjectInfoDetailResponse{" +
                "id='" + id + '\'' +
                ", projectName='" + projectName + '\'' +
                ", projectCode='" + projectCode + '\'' +
                ", projectType='" + projectType + '\'' +
                ", projectTypeName='" + projectTypeName + '\'' +
                ", officeId='" + officeId + '\'' +
                ", officeName='" + officeName + '\'' +
                ", custInfoId='" + custInfoId + '\'' +
                ", custInfoName='" + custInfoName + '\'' +
                ", projectState='" + projectState + '\'' +
                ", onLineDate=" + onLineDate +
                ", projectLeaderId='" + projectLeaderId + '\'' +
                ", projectLeaderName='" + projectLeaderName + '\'' +
                ", marketLeaderId='" + marketLeaderId + '\'' +
                ", marketLeaderName='" + marketLeaderName + '\'' +
                ", impleLeaderId='" + impleLeaderId + '\'' +
                ", impleLeaderName='" + impleLeaderName + '\'' +
                ", remarks='" + remarks + '\'' +
                ", mainCompany=" + mainCompany +
                ", projectNodeDetailResponse=" + projectNodeDetailResponse +
                ", onlinePlanTime=" + onlinePlanTime +
                ", projectLevel='" + projectLevel + '\'' +
                ", businessAssistantId='" + businessAssistantId + '\'' +
                ", businessAssistantName='" + businessAssistantName + '\'' +
                ", vipCustomerId='" + vipCustomerId + '\'' +
                ", vipCustomerName='" + vipCustomerName + '\'' +
                ", invoicingFrequency=" + invoicingFrequency +
                ", transExpenssPlan=" + transExpenssPlan +
                ", carrierGoods=" + carrierGoods +
                ", projectManagerId='" + projectManagerId + '\'' +
                ", projectManager='" + projectManager + '\'' +
                ", accountLeaderId='" + accountLeaderId + '\'' +
                ", accountLeaderName='" + accountLeaderName + '\'' +
                ", generalRequireResponse=" + generalRequireResponse +
                ", specialRequireResponse=" + specialRequireResponse +
                ", projectLinkmanDetailResponse=" + projectLinkmanDetailResponse +
                ", newProjectLeaderName='" + newProjectLeaderName + '\'' +
                ", newOfficeName='" + newOfficeName + '\'' +
                ", officeLeaderName='" + officeLeaderName + '\'' +
                ", processFlag='" + processFlag + '\'' +
                '}';
    }
}
