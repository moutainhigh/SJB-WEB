/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.oa.entity;

import java.util.Date;
import java.util.List;

import com.sijibao.base.common.provider.entity.PagerBase;
import com.sijibao.oa.modules.sys.entity.User;


/**
 * 合同模版配置Entity
 * @author xby
 * @version 2018-10-23
 */
public class ContractConfig extends PagerBase<ContractConfig> {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String contractCode;		// 合同模板编码
	private String contractName;		// 合同名称
	private String oldContractName;//验证合同名称重复的问题
	private String sort;//合同缩写
	private Integer associationMain = 0;		// 是否联系主合同:0否，1是
	private Integer endTimeConsistent = 0;		// 是否限定合同结束日期跟主合同一致:0否，1是
	private Integer contractType;		// 合同类型:1业务合同，2日常合同
	private Integer contractMemberCount;		// 合同成员个数：2两方，3三方
	private Integer haveAccessories = 0;
	private Integer allowOpenAccount = 0;		// 能否允许提前开户：0否，1能
	private Integer version;		// 版本号
	private Date createTime;		// 创建时间
	private Date updateTime;		// 更新时间
	private User createBy; //创建人
	private User updateBy; //修改人
	private String delFlag;//合同模板状态
	private List<ContractMember> contractMemberList; //合同方字段  
	private List<ContractConfigAttachment> contractConfigAttachmentList; //合同附件配置
	public String getContractCode() {
		return contractCode;
	}
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	public String getContractName() {
		return contractName;
	}
	public void setContractName(String contractName) {
		this.contractName = contractName;
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
	public Integer getContractType() {
		return contractType;
	}
	public void setContractType(Integer contractType) {
		this.contractType = contractType;
	}
	public Integer getContractMemberCount() {
		return contractMemberCount;
	}
	public void setContractMemberCount(Integer contractMemberCount) {
		this.contractMemberCount = contractMemberCount;
	}
	public Integer getAllowOpenAccount() {
		return allowOpenAccount;
	}
	public void setAllowOpenAccount(Integer allowOpenAccount) {
		this.allowOpenAccount = allowOpenAccount;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
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
	public User getCreateBy() {
		return createBy;
	}
	public void setCreateBy(User createBy) {
		this.createBy = createBy;
	}
	public User getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(User updateBy) {
		this.updateBy = updateBy;
	}
	public List<ContractConfigAttachment> getContractConfigAttachmentList() {
		return contractConfigAttachmentList;
	}
	public void setContractConfigAttachmentList(List<ContractConfigAttachment> contractConfigAttachmentList) {
		this.contractConfigAttachmentList = contractConfigAttachmentList;
	}
	public String getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public List<ContractMember> getContractMemberList() {
		return contractMemberList;
	}
	public void setContractMemberList(List<ContractMember> contractMemberList) {
		this.contractMemberList = contractMemberList;
	}
	
	public String getOldContractName() {
		return oldContractName;
	}
	public void setOldContractName(String oldContractName) {
		this.oldContractName = oldContractName;
	}
	
	public Integer getHaveAccessories() {
		return haveAccessories;
	}
	public void setHaveAccessories(Integer haveAccessories) {
		this.haveAccessories = haveAccessories;
	}
	@Override
	public String toString() {
		return "ContractConfig [contractCode=" + contractCode + ", contractName=" + contractName + ", oldContractName="
				+ oldContractName + ", sort=" + sort + ", associationMain=" + associationMain + ", endTimeConsistent="
				+ endTimeConsistent + ", contractType=" + contractType + ", contractMemberCount=" + contractMemberCount
				+ ", haveAccessories=" + haveAccessories + ", allowOpenAccount=" + allowOpenAccount + ", version="
				+ version + ", createTime=" + createTime + ", updateTime=" + updateTime + ", createBy=" + createBy
				+ ", updateBy=" + updateBy + ", delFlag=" + delFlag + ", contractMemberList=" + contractMemberList
				+ ", contractConfigAttachmentList=" + contractConfigAttachmentList + "]";
	}
	
	
}