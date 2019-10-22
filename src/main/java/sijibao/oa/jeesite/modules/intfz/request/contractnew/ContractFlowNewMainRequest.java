package sijibao.oa.jeesite.modules.intfz.request.contractnew;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import com.sijibao.oa.modules.intfz.validator.IntfzValid;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 合同管理流程接收对象
 * @author xby
 * @version 2018-11-07
 */
@ApiModel(value="合同管理流程接收对象")
public class ContractFlowNewMainRequest implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value="主键ID")
	private String id; //主键ID
	
	@ApiModelProperty(value="流程实例ID")
	private String procInsId;		// 流程实例ID
	
	@ApiModelProperty(value="流程编号")
	private String procCode;		// 流程编号
	
	@IntfzValid(min=0, max = 100, nullable=false)
	@ApiModelProperty(value="合同名称")
	private String contractName;		// 合同名称
	
	@IntfzValid(min=0, max = 64, nullable=false)
	@ApiModelProperty(value="合同名称模版ID")
	private String contractNameId;		// 合同名称模版ID
	
	@IntfzValid(min=0, max = 100, nullable=false)
	@ApiModelProperty(value="甲方名称")
	private String firstMemberName;		// 甲方名称
	
	@IntfzValid(min=0, max = 64, nullable=false)
	@ApiModelProperty(value="甲方统一社会信用代码")
	private String firstCreditCode;		// 甲方统一社会信用代码
	
	@IntfzValid(min=0, max = 100, nullable=false)
	@ApiModelProperty(value="甲方住所")
	private String firstAddress;		// 甲方住所
	
	@IntfzValid(min=0, max = 64, nullable=false)
	@ApiModelProperty(value="甲方法定代表人")
	private String firstLegalRepresentative;		// 甲方法定代表人
	
	@IntfzValid(min=0, max = 32, nullable=false)
	@ApiModelProperty(value="甲方联系方式")
	private String firstLinkMethod;		// 甲方联系方式
	
	@IntfzValid(min=0, max = 100, nullable=false)
	@ApiModelProperty(value="乙方名称")
	private String secondMemberName;		// 乙方名称
	
	@IntfzValid(min=0, max = 64, nullable=false)
	@ApiModelProperty(value="乙方统一社会信用代码")
	private String secondCreditCode;		// 乙方统一社会信用代码
	
	@IntfzValid(min=0, max = 100, nullable=false)
	@ApiModelProperty(value="乙方住所")
	private String secondAddress;		// 乙方住所
	
	@IntfzValid(min=0, max = 32, nullable=false)
	@ApiModelProperty(value="乙方法定代表人")
	private String secondLegalRepresentative;		// 乙方法定代表人
	
	@IntfzValid(min=0, max = 32, nullable=false)
	@ApiModelProperty(value="乙方联系方式")
	private String secondLinkMethod;		// 乙方联系方式
	
	
	@IntfzValid(min=0, max = 100, nullable=false)
	@ApiModelProperty(value="丙方名称")
	private String thirdMemberName;		// 丙方名称
	
	@IntfzValid(min=0, max = 64, nullable=false)
	@ApiModelProperty(value="丙方统一社会信用代码")
	private String thirdCreditCode;		// 丙方统一社会信用代码
	
	@IntfzValid(min=0, max = 100, nullable=false)
	@ApiModelProperty(value="丙方住所")
	private String thirdAddress;		// 丙方住所
	
	@IntfzValid(min=0, max = 32, nullable=false)
	@ApiModelProperty(value="丙方法定代表人")
	private String thirdLegalRepresentative;		// 丙方法定代表人
	
	@IntfzValid(min=0, max = 32, nullable=false)
	@ApiModelProperty(value="丙方联系方式")
	private String thirdLinkMethod;		// 丙方联系方式
	
	@IntfzValid(min=0, max = 32, nullable=false)
	@ApiModelProperty(value="合同开始日期")
	private long contractStartTime; //合同开始日期
	
	@IntfzValid(min=0, max = 32, nullable=false)
	@ApiModelProperty(value="合同结束日期")
	private long contractEndTime; //合同结束日期
	
	@IntfzValid(min=0, max = 32, nullable=false)
	@ApiModelProperty(value="合同负责人ID")
	private String contractLeaderId; //合同负责人ID
	
	@IntfzValid(min=0, max = 32, nullable=false)
	@ApiModelProperty(value="合同签约人ID")
	private String signLeaderId; //合同签约人ID
	
	@ApiModelProperty(value="快递公司")
	private String expressCompany;		// 快递公司
	
	@ApiModelProperty(value="快递单号")
	private String expressBill;		// 快递单号
	
	@ApiModelProperty(value="备注")
	private String remarks;  //备注
	
	@ApiModelProperty(value="合同附件信息")
	private List<ContractAttachmentNewMainRequest> contractAttachmentList;//合同附件信息
	
	@ApiModelProperty(value="关联项目ID")
	private String[] projectIds; //关联项目ID
	
	@ApiModelProperty(value="主合同ID")
	private String associationMainId;		// 主合同id
	
	@ApiModelProperty(value="主合同名称")
	private String associationMainName;		// 主合同名称
	private String producSide;//产品端
	
	public ContractFlowNewMainRequest(){}
	
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

	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	public String getContractNameId() {
		return contractNameId;
	}

	public void setContractNameId(String contractNameId) {
		this.contractNameId = contractNameId;
	}

	public String getFirstMemberName() {
		return firstMemberName;
	}

	public void setFirstMemberName(String firstMemberName) {
		this.firstMemberName = firstMemberName;
	}

	public String getFirstCreditCode() {
		return firstCreditCode;
	}

	public void setFirstCreditCode(String firstCreditCode) {
		this.firstCreditCode = firstCreditCode;
	}

	public String getFirstAddress() {
		return firstAddress;
	}

	public void setFirstAddress(String firstAddress) {
		this.firstAddress = firstAddress;
	}

	public String getFirstLegalRepresentative() {
		return firstLegalRepresentative;
	}

	public void setFirstLegalRepresentative(String firstLegalRepresentative) {
		this.firstLegalRepresentative = firstLegalRepresentative;
	}

	public String getSecondMemberName() {
		return secondMemberName;
	}

	public void setSecondMemberName(String secondMemberName) {
		this.secondMemberName = secondMemberName;
	}

	public String getSecondCreditCode() {
		return secondCreditCode;
	}

	public void setSecondCreditCode(String secondCreditCode) {
		this.secondCreditCode = secondCreditCode;
	}

	public String getSecondAddress() {
		return secondAddress;
	}

	public void setSecondAddress(String secondAddress) {
		this.secondAddress = secondAddress;
	}

	public String getSecondLegalRepresentative() {
		return secondLegalRepresentative;
	}

	public void setSecondLegalRepresentative(String secondLegalRepresentative) {
		this.secondLegalRepresentative = secondLegalRepresentative;
	}

	public String getThirdMemberName() {
		return thirdMemberName;
	}

	public void setThirdMemberName(String thirdMemberName) {
		this.thirdMemberName = thirdMemberName;
	}

	public String getThirdCreditCode() {
		return thirdCreditCode;
	}

	public void setThirdCreditCode(String thirdCreditCode) {
		this.thirdCreditCode = thirdCreditCode;
	}

	public String getThirdAddress() {
		return thirdAddress;
	}

	public void setThirdAddress(String thirdAddress) {
		this.thirdAddress = thirdAddress;
	}

	public String getThirdLegalRepresentative() {
		return thirdLegalRepresentative;
	}

	public void setThirdLegalRepresentative(String thirdLegalRepresentative) {
		this.thirdLegalRepresentative = thirdLegalRepresentative;
	}

	public long getContractStartTime() {
		return contractStartTime;
	}

	public void setContractStartTime(long contractStartTime) {
		this.contractStartTime = contractStartTime;
	}

	public long getContractEndTime() {
		return contractEndTime;
	}

	public void setContractEndTime(long contractEndTime) {
		this.contractEndTime = contractEndTime;
	}

	public String getContractLeaderId() {
		return contractLeaderId;
	}

	public void setContractLeaderId(String contractLeaderId) {
		this.contractLeaderId = contractLeaderId;
	}

	public String getSignLeaderId() {
		return signLeaderId;
	}

	public void setSignLeaderId(String signLeaderId) {
		this.signLeaderId = signLeaderId;
	}

	public String getExpressCompany() {
		return expressCompany;
	}

	public void setExpressCompany(String expressCompany) {
		this.expressCompany = expressCompany;
	}

	public String getExpressBill() {
		return expressBill;
	}

	public void setExpressBill(String expressBill) {
		this.expressBill = expressBill;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public List<ContractAttachmentNewMainRequest> getContractAttachmentList() {
		return contractAttachmentList;
	}

	public void setContractAttachmentList(List<ContractAttachmentNewMainRequest> contractAttachmentList) {
		this.contractAttachmentList = contractAttachmentList;
	}

	public String[] getProjectIds() {
		return projectIds;
	}

	public void setProjectIds(String[] projectIds) {
		this.projectIds = projectIds;
	}

	public String getAssociationMainId() {
		return associationMainId;
	}

	public void setAssociationMainId(String associationMainId) {
		this.associationMainId = associationMainId;
	}

	public String getAssociationMainName() {
		return associationMainName;
	}

	public void setAssociationMainName(String associationMainName) {
		this.associationMainName = associationMainName;
	}

	public String getFirstLinkMethod() {
		return firstLinkMethod;
	}

	public void setFirstLinkMethod(String firstLinkMethod) {
		this.firstLinkMethod = firstLinkMethod;
	}

	public String getSecondLinkMethod() {
		return secondLinkMethod;
	}

	public void setSecondLinkMethod(String secondLinkMethod) {
		this.secondLinkMethod = secondLinkMethod;
	}

	public String getThirdLinkMethod() {
		return thirdLinkMethod;
	}

	public void setThirdLinkMethod(String thirdLinkMethod) {
		this.thirdLinkMethod = thirdLinkMethod;
	}

	public String getProducSide() {
		return producSide;
	}

	public void setProducSide(String producSide) {
		this.producSide = producSide;
	}

	@Override
	public String toString() {
		return "ContractFlowNewMainRequest [id=" + id + ", procInsId=" + procInsId + ", procCode=" + procCode
				+ ", contractName=" + contractName + ", contractNameId=" + contractNameId + ", firstMemberName="
				+ firstMemberName + ", firstCreditCode=" + firstCreditCode + ", firstAddress=" + firstAddress
				+ ", firstLegalRepresentative=" + firstLegalRepresentative + ", firstLinkMethod=" + firstLinkMethod
				+ ", secondMemberName=" + secondMemberName + ", secondCreditCode=" + secondCreditCode
				+ ", secondAddress=" + secondAddress + ", secondLegalRepresentative=" + secondLegalRepresentative
				+ ", secondLinkMethod=" + secondLinkMethod + ", thirdMemberName=" + thirdMemberName
				+ ", thirdCreditCode=" + thirdCreditCode + ", thirdAddress=" + thirdAddress
				+ ", thirdLegalRepresentative=" + thirdLegalRepresentative + ", thirdLinkMethod=" + thirdLinkMethod
				+ ", contractStartTime=" + contractStartTime + ", contractEndTime=" + contractEndTime
				+ ", contractLeaderId=" + contractLeaderId + ", signLeaderId=" + signLeaderId + ", expressCompany="
				+ expressCompany + ", expressBill=" + expressBill + ", remarks=" + remarks + ", contractAttachmentList="
				+ contractAttachmentList + ", projectIds=" + Arrays.toString(projectIds) + ", associationMainId="
				+ associationMainId + ", associationMainName=" + associationMainName + "]";
	}

}