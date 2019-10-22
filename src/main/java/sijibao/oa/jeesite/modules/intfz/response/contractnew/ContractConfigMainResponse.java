package sijibao.oa.jeesite.modules.intfz.response.contractnew;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 合同配置返回对象
 * @author xby
 * @version 2018-10-22
 */
@ApiModel(value="合同配置返回对象")
public class ContractConfigMainResponse {
	
	@ApiModelProperty(value="主键ID")
	private String id; //配置ID
	
	@ApiModelProperty(value="合同名称")
	private String contractName;		// 合同名称
	
	@ApiModelProperty(value="配置版本号")
	private Integer version; //配置版本号
	
	@ApiModelProperty(value="是否关联主合同:0否，1是")
	private Integer associationMain; //是否关联主合同:0否，1是
	
	@ApiModelProperty(value="是否限定合同结束日期跟主合同一致:0否，1是")
	private Integer endTimeConsistent; //是否限定合同结束日期跟主合同一致:0否，1是
	
	@ApiModelProperty(value="合同方个数2方/3方")
	private Integer contractMemberCount; //合同方个数2方/3方
	
	@ApiModelProperty(value="合同模板编号")
	private String contractCode; //合同模板编号
	
	/*@ApiModelProperty(value="预签合同附件最大上传张数")
	private Integer appointmentCount; //预签合同附件最大上传张数
	
	@ApiModelProperty(value="预签合同必传附件张数")
	private Integer appointmentMustCount; //预签合同必传附件张数
	
	@ApiModelProperty(value="合同扫描件最大上传张数")
	private Integer scanCount; //合同扫描件最大上传张数
	
	@ApiModelProperty(value="合同扫描件必传附件张数")
	private Integer scanMustCount; //合同扫描件必传附件张数
	
	@ApiModelProperty(value="是否存在附件资料：0无，1有")
	private Integer haveAccessories; //是否存在附件资料：0无，1有
	
	@ApiModelProperty(value="附件资料最大上传张数")
	private Integer accessoriesCount; //附件资料最大上传张数
	
	@ApiModelProperty(value="附件资料必传张数")
	private Integer accessoriesMustCount; //附件资料必传张数
	
	@ApiModelProperty(value="附件上传时间类型：1上传预签合同时间，2上传合同扫描件时间")
	private Integer uploadTimeType; //附件上传时间类型：1上传预签合同时间，2上传合同扫描件时间*/
	
	@ApiModelProperty(value="提示文案")
	private String remarks; //提示文案
	
	@ApiModelProperty(value="合同方字段配置")
	private List<ContractPartyTypeMainResponse> contractPartyList; //合同方字段配置
    
	@ApiModelProperty(value="合同附件配置")
	private List<ContractConfigAttachmentMainResponse> contractConfigAttachmentList;
	
	public ContractConfigMainResponse(){}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Integer getAssociationMain() {
		return associationMain;
	}

	public void setAssociationMain(Integer associationMain) {
		this.associationMain = associationMain;
	}

	public Integer getEndTimeConsistent() {
		return endTimeConsistent;
	}

	public void setEndTimeConsistent(Integer endTimeConsistent) {
		this.endTimeConsistent = endTimeConsistent;
	}

	public Integer getContractMemberCount() {
		return contractMemberCount;
	}

	public void setContractMemberCount(Integer contractMemberCount) {
		this.contractMemberCount = contractMemberCount;
	}

	/*public Integer getAppointmentCount() {
		return appointmentCount;
	}

	public void setAppointmentCount(Integer appointmentCount) {
		this.appointmentCount = appointmentCount;
	}

	public Integer getAppointmentMustCount() {
		return appointmentMustCount;
	}

	public void setAppointmentMustCount(Integer appointmentMustCount) {
		this.appointmentMustCount = appointmentMustCount;
	}

	public Integer getScanCount() {
		return scanCount;
	}

	public void setScanCount(Integer scanCount) {
		this.scanCount = scanCount;
	}

	public Integer getScanMustCount() {
		return scanMustCount;
	}

	public void setScanMustCount(Integer scanMustCount) {
		this.scanMustCount = scanMustCount;
	}

	public Integer getHaveAccessories() {
		return haveAccessories;
	}

	public void setHaveAccessories(Integer haveAccessories) {
		this.haveAccessories = haveAccessories;
	}

	public Integer getAccessoriesCount() {
		return accessoriesCount;
	}

	public void setAccessoriesCount(Integer accessoriesCount) {
		this.accessoriesCount = accessoriesCount;
	}

	public Integer getAccessoriesMustCount() {
		return accessoriesMustCount;
	}

	public void setAccessoriesMustCount(Integer accessoriesMustCount) {
		this.accessoriesMustCount = accessoriesMustCount;
	}

	public Integer getUploadTimeType() {
		return uploadTimeType;
	}

	public void setUploadTimeType(Integer uploadTimeType) {
		this.uploadTimeType = uploadTimeType;
	}*/

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	public List<ContractPartyTypeMainResponse> getContractPartyList() {
		return contractPartyList;
	}

	public void setContractPartyList(List<ContractPartyTypeMainResponse> contractPartyList) {
		this.contractPartyList = contractPartyList;
	}

	public List<ContractConfigAttachmentMainResponse> getContractConfigAttachmentList() {
		return contractConfigAttachmentList;
	}

	public void setContractConfigAttachmentList(List<ContractConfigAttachmentMainResponse> contractConfigAttachmentList) {
		this.contractConfigAttachmentList = contractConfigAttachmentList;
	}


	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	@Override
	public String toString() {
		return "ContractConfigMainResponse [id=" + id + ", contractName=" + contractName + ", version=" + version
				+ ", associationMain=" + associationMain + ", endTimeConsistent=" + endTimeConsistent
				+ ", contractMemberCount=" + contractMemberCount + ", contractCode=" + contractCode + ", remarks="
				+ remarks + ", contractPartyList=" + contractPartyList + ", contractConfigAttachmentList="
				+ contractConfigAttachmentList + "]";
	}

	
}
