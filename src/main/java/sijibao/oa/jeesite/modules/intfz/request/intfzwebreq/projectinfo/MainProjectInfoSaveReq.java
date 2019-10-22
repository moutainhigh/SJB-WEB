package sijibao.oa.jeesite.modules.intfz.request.intfzwebreq.projectinfo;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 项目管理服务--保存列表对象
 * @author wanxb
 *
 */
@ApiModel(value="项目管理服务--保存列表对象")
public class MainProjectInfoSaveReq implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value="主键id")
	private String id;

	@NotBlank(message = "项目名称不允许为空")
	@Length(min=1, max=64,message = "项目名称有误，最大限制64个字符")
	@ApiModelProperty(value="项目名称")
	private String projectName;

	@ApiModelProperty(value="项目类型0公司项目1市场项目")
	@NotBlank(message = "项目类型不允许为空")
	private String projectType;

	@ApiModelProperty(value="归属部门")
	private String officeId;

	@ApiModelProperty(value="归属客户")
	@NotBlank(message = "客户名称不允许为空")
	private String custInfoId;

	@ApiModelProperty(value="项目负责人")
	private String projectLeaderId;

	@ApiModelProperty(value="市场负责人")
	private String marketLeaderId;

	@ApiModelProperty(value="实施负责人")
	private String impleLeaderId;

	@ApiModelProperty(value="备注")
	private String remarks;

	@Size(min = 1,message = "企业名称不允许为空")
	@NotNull(message = "企业名称不允许为空")
	@ApiModelProperty(value="企业编号")
	private List<String> holderCode ;
	
	@ApiModelProperty(value="项目节点")
	private List<MainProjectNodeReq> projectNodeReqs;
	private String producSide;

	@ApiModelProperty(value="计划上线时间")
	private Long onlinePlanTime;

	@NotBlank(message = "项目等级不允许为空")
	@ApiModelProperty(value="项目等级ABCD")
	private String projectLevel;

	@ApiModelProperty(value="商务助理")
	private String businessAssistantId;

	@ApiModelProperty(value="VIP客服")
	private String vipCustomerId;

	@Digits(integer=5,fraction=0,message = "月开票频次有误")
	@ApiModelProperty(value="月开票频次（次/月）")
	private Integer invoicingFrequency;

	@Digits(integer=15,fraction=2,message = "计划月运费有误")
	@ApiModelProperty(value="计划月运费(万元)")
	private Double transExpenssPlan;

	@ApiModelProperty(value="承运货物:1煤炭2钢铁3商砼4其他")
	@Size(min = 1,message = "承运货物不允许为空")
	@NotNull(message = "承运货物不允许为空")
	private List<String> carrierGoods;

	@ApiModelProperty(value="项目管理负责人")
	@NotBlank(message = "项目管理负责人不允许为空")
	private String projectManagerId;

	@ApiModelProperty(value="清结算")
	private String accountLeaderId;

	@ApiModelProperty(value="一般要求")
	private MainProjectGeneralRequire generalRequire;

	@Valid
	@ApiModelProperty(value="特殊要求")
	private MainProjectSpecialRequire specialRequire;

	@ApiModelProperty(value="项目联系人")
	private List<MainProjectContact> mainProjectContacts;

    @ApiModelProperty(value="流程项目调研标识")
    private String processFlag;

	public String getProducSide() {
		return producSide;
	}

	public void setProducSide(String producSide) {
		this.producSide = producSide;
	}

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
	public List<MainProjectNodeReq> getProjectNodeReqs() {
		return projectNodeReqs;
	}
	public void setProjectNodeReqs(List<MainProjectNodeReq> projectNodeReqs) {
		this.projectNodeReqs = projectNodeReqs;
	}
	public List<String> getHolderCode() {
		return holderCode;
	}
	public void setHolderCode(List<String> holderCode) {
		this.holderCode = holderCode;
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

	public String getVipCustomerId() {
		return vipCustomerId;
	}

	public void setVipCustomerId(String vipCustomerId) {
		this.vipCustomerId = vipCustomerId;
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

	public List<String> getCarrierGoods() {
		return carrierGoods;
	}

	public void setCarrierGoods(List<String> carrierGoods) {
		this.carrierGoods = carrierGoods;
	}

	public String getProjectManagerId() {
		return projectManagerId;
	}

	public void setProjectManagerId(String projectManagerId) {
		this.projectManagerId = projectManagerId;
	}

	public String getAccountLeaderId() {
		return accountLeaderId;
	}

	public void setAccountLeaderId(String accountLeaderId) {
		this.accountLeaderId = accountLeaderId;
	}

	public MainProjectGeneralRequire getGeneralRequire() {
		return generalRequire;
	}

	public void setGeneralRequire(MainProjectGeneralRequire generalRequire) {
		this.generalRequire = generalRequire;
	}

	public MainProjectSpecialRequire getSpecialRequire() {
		return specialRequire;
	}

	public void setSpecialRequire(MainProjectSpecialRequire specialRequire) {
		this.specialRequire = specialRequire;
	}

	public List<MainProjectContact> getMainProjectContacts() {
		return mainProjectContacts;
	}

	public void setMainProjectContacts(List<MainProjectContact> mainProjectContacts) {
		this.mainProjectContacts = mainProjectContacts;
	}

    public String getProcessFlag() {
        return processFlag;
    }

    public void setProcessFlag(String processFlag) {
        this.processFlag = processFlag;
    }

    @Override
    public String toString() {
        return "MainProjectInfoSaveReq{" +
                "id='" + id + '\'' +
                ", projectName='" + projectName + '\'' +
                ", projectType='" + projectType + '\'' +
                ", officeId='" + officeId + '\'' +
                ", custInfoId='" + custInfoId + '\'' +
                ", projectLeaderId='" + projectLeaderId + '\'' +
                ", marketLeaderId='" + marketLeaderId + '\'' +
                ", impleLeaderId='" + impleLeaderId + '\'' +
                ", remarks='" + remarks + '\'' +
                ", holderCode=" + holderCode +
                ", projectNodeReqs=" + projectNodeReqs +
                ", producSide='" + producSide + '\'' +
                ", onlinePlanTime=" + onlinePlanTime +
                ", projectLevel='" + projectLevel + '\'' +
                ", businessAssistantId='" + businessAssistantId + '\'' +
                ", vipCustomerId='" + vipCustomerId + '\'' +
                ", invoicingFrequency=" + invoicingFrequency +
                ", transExpenssPlan=" + transExpenssPlan +
                ", carrierGoods=" + carrierGoods +
                ", projectManagerId='" + projectManagerId + '\'' +
                ", accountLeaderId='" + accountLeaderId + '\'' +
                ", generalRequire=" + generalRequire +
                ", specialRequire=" + specialRequire +
                ", mainProjectContacts=" + mainProjectContacts +
                ", processFlag='" + processFlag + '\'' +
                '}';
    }
}
