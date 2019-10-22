/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.flow.entity;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sijibao.oa.common.persistence.ActEntity;
import com.sijibao.oa.modules.sys.entity.Office;
import com.sijibao.oa.modules.sys.entity.User;

/**
 * 合同管理流程Entity
 * @author wanxb
 * @version 2018-07-05
 */
public class ContractFlow extends ActEntity<ContractFlow> {
	
	private static final long serialVersionUID = 1L;
	private String procInsId;		// 流程实例ID
	private String procCode;		// 流程编号
	private String procName;		// 流程名称
	private String applyPerCode;		// 申请人编号
	private String applyPerName;		// 申请人名称
	private Date applyTime;		// 申请时间
	private Office office;		// 所属部门ID
	private String officeName;		// 所属部门名称
	private String contractCode;		// 合同编号
	private String contractName;		// 合同名称
	private String contractNameCode;		// 合同名称编码
	private String custId;       //客户ID
	private String firstPartyName;		// 甲方名称
	private String firstCreditCode;		// 甲方统一社会信用代码
	private String firstAddress;		// 甲方住所
	private String firstLegalRepresentative;		// 甲方法定代表人
	private String firstLinkman;		// 甲方联系人
	private String firstLinkmanPhone;		// 甲方联系人电话
	private String firstLinkmanMail;		// 甲方联系人邮箱
	private String secondPartyName;		// 乙方名称
	private String secondCreditCode;		// 乙方统一社会信用代码
	private String secondAddress;		// 乙方住所
	private String secondLegalRepresentative;		// 乙方法定代表人
	private String secondLinkman;		// 乙方联系人
	private String secondLinkmanPhone;		// 乙方联系人电话
	private String secondLinkmanMail;		// 乙方联系人邮箱
	private String dispatchProportion;		// 调度费比例
	private String penaltyProportion;		// 违约金比例
	private String validityDate;		// 有效期（年）
	private Date contractDate;		// 签约日期
	private String expressCompany;		// 快递公司
	private String expressBill;		// 快递单号
	private String marketLeaderId;		// 市场负责人id
	private String marketLeaderName;//市场负责人name
	private String contractFlowStatus;		// 合同状态
	private Date flowFinishTime;		// 流程审批结束时间
	private String configId ;//合同模版id
	
	
	private List<ContractAttachment> contractAttachmentList;//附件信息
	private Date beginApplyTime ;//搜索开始时间
	private Date endApplyTime ;//搜索结束时间
	private Date printingTimeStart; //合同用印日期开始
	private Date printingTimeEnd; //合同用印日期结束 
	private String companyName;   //公司名称
	private String sql;	//数据权限控制sql
	private User user;  //当前用户
	
	public ContractFlow() {
		super();
	}

	public ContractFlow(String id){
		super(id);
	}

	@Length(min=0, max=64, message="流程实例ID长度必须介于 0 和 64 之间")
	public String getProcInsId() {
		return procInsId;
	}

	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}
	
	@Length(min=0, max=16, message="流程编号长度必须介于 0 和 16 之间")
	public String getProcCode() {
		return procCode;
	}

	public void setProcCode(String procCode) {
		this.procCode = procCode;
	}
	
	@Length(min=0, max=300, message="流程名称长度必须介于 0 和 300 之间")
	public String getProcName() {
		return procName;
	}

	public void setProcName(String procName) {
		this.procName = procName;
	}
	
	@Length(min=1, max=32, message="申请人编号长度必须介于 1 和 32 之间")
	public String getApplyPerCode() {
		return applyPerCode;
	}

	public void setApplyPerCode(String applyPerCode) {
		this.applyPerCode = applyPerCode;
	}
	
	@Length(min=1, max=100, message="申请人名称长度必须介于 1 和 100 之间")
	public String getApplyPerName() {
		return applyPerName;
	}

	public void setApplyPerName(String applyPerName) {
		this.applyPerName = applyPerName;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="申请时间不能为空")
	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}
	
	@NotNull(message="所属部门ID不能为空")
	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}
	
	@Length(min=1, max=100, message="所属部门名称长度必须介于 1 和 100 之间")
	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}
	
	@Length(min=1, max=64, message="合同编号长度必须介于 1 和 64 之间")
	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	
	@Length(min=1, max=64, message="合同名称长度必须介于 1 和 64 之间")
	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}
	
	@Length(min=1, max=64, message="甲方名称长度必须介于 1 和 64 之间")
	public String getFirstPartyName() {
		return firstPartyName;
	}

	public void setFirstPartyName(String firstPartyName) {
		this.firstPartyName = firstPartyName;
	}
	
	@Length(min=1, max=64, message="甲方统一社会信用代码长度必须介于 1 和 64 之间")
	public String getFirstCreditCode() {
		return firstCreditCode;
	}

	public void setFirstCreditCode(String firstCreditCode) {
		this.firstCreditCode = firstCreditCode;
	}
	
	@Length(min=1, max=64, message="甲方住所长度必须介于 1 和 64 之间")
	public String getFirstAddress() {
		return firstAddress;
	}

	public void setFirstAddress(String firstAddress) {
		this.firstAddress = firstAddress;
	}
	
	@Length(min=1, max=32, message="甲方法定代表人长度必须介于 1 和 32 之间")
	public String getFirstLegalRepresentative() {
		return firstLegalRepresentative;
	}

	public void setFirstLegalRepresentative(String firstLegalRepresentative) {
		this.firstLegalRepresentative = firstLegalRepresentative;
	}
	
	@Length(min=1, max=32, message="甲方联系人长度必须介于 1 和 32 之间")
	public String getFirstLinkman() {
		return firstLinkman;
	}

	public void setFirstLinkman(String firstLinkman) {
		this.firstLinkman = firstLinkman;
	}
	
	@Length(min=1, max=16, message="甲方联系人电话长度必须介于 1 和 16 之间")
	public String getFirstLinkmanPhone() {
		return firstLinkmanPhone;
	}

	public void setFirstLinkmanPhone(String firstLinkmanPhone) {
		this.firstLinkmanPhone = firstLinkmanPhone;
	}
	
	@Length(min=1, max=32, message="甲方联系人邮箱长度必须介于 1 和 32 之间")
	public String getFirstLinkmanMail() {
		return firstLinkmanMail;
	}

	public void setFirstLinkmanMail(String firstLinkmanMail) {
		this.firstLinkmanMail = firstLinkmanMail;
	}
	
	@Length(min=1, max=64, message="乙方名称长度必须介于 1 和 64 之间")
	public String getSecondPartyName() {
		return secondPartyName;
	}

	public void setSecondPartyName(String secondPartyName) {
		this.secondPartyName = secondPartyName;
	}
	
	@Length(min=1, max=64, message="乙方统一社会信用代码长度必须介于 1 和 64 之间")
	public String getSecondCreditCode() {
		return secondCreditCode;
	}

	public void setSecondCreditCode(String secondCreditCode) {
		this.secondCreditCode = secondCreditCode;
	}
	
	@Length(min=1, max=64, message="乙方住所长度必须介于 1 和 64 之间")
	public String getSecondAddress() {
		return secondAddress;
	}

	public void setSecondAddress(String secondAddress) {
		this.secondAddress = secondAddress;
	}
	
	@Length(min=1, max=32, message="乙方法定代表人长度必须介于 1 和 32 之间")
	public String getSecondLegalRepresentative() {
		return secondLegalRepresentative;
	}

	public void setSecondLegalRepresentative(String secondLegalRepresentative) {
		this.secondLegalRepresentative = secondLegalRepresentative;
	}
	
	@Length(min=1, max=32, message="乙方联系人长度必须介于 1 和 32 之间")
	public String getSecondLinkman() {
		return secondLinkman;
	}

	public void setSecondLinkman(String secondLinkman) {
		this.secondLinkman = secondLinkman;
	}
	
	@Length(min=1, max=16, message="乙方联系人电话长度必须介于 1 和 16 之间")
	public String getSecondLinkmanPhone() {
		return secondLinkmanPhone;
	}

	public void setSecondLinkmanPhone(String secondLinkmanPhone) {
		this.secondLinkmanPhone = secondLinkmanPhone;
	}
	
	@Length(min=1, max=32, message="乙方联系人邮箱长度必须介于 1 和 32 之间")
	public String getSecondLinkmanMail() {
		return secondLinkmanMail;
	}

	public void setSecondLinkmanMail(String secondLinkmanMail) {
		this.secondLinkmanMail = secondLinkmanMail;
	}
	
	@Length(min=1, max=16, message="调度费比例长度必须介于 1 和 16 之间")
	public String getDispatchProportion() {
		return dispatchProportion;
	}

	public void setDispatchProportion(String dispatchProportion) {
		this.dispatchProportion = dispatchProportion;
	}
	
	@Length(min=1, max=16, message="违约金比例长度必须介于 1 和 16 之间")
	public String getPenaltyProportion() {
		return penaltyProportion;
	}

	public void setPenaltyProportion(String penaltyProportion) {
		this.penaltyProportion = penaltyProportion;
	}
	
	@Length(min=1, max=16, message="有效期（年）长度必须介于 1 和 16 之间")
	public String getValidityDate() {
		return validityDate;
	}

	public void setValidityDate(String validityDate) {
		this.validityDate = validityDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getContractDate() {
		return contractDate;
	}

	public void setContractDate(Date contractDate) {
		this.contractDate = contractDate;
	}
	
	@Length(min=0, max=64, message="快递公司长度必须介于 0 和 64 之间")
	public String getExpressCompany() {
		return expressCompany;
	}

	public void setExpressCompany(String expressCompany) {
		this.expressCompany = expressCompany;
	}
	
	@Length(min=0, max=32, message="快递单号长度必须介于 0 和 32 之间")
	public String getExpressBill() {
		return expressBill;
	}

	public void setExpressBill(String expressBill) {
		this.expressBill = expressBill;
	}
	
	@Length(min=1, max=32, message="市场负责人id长度必须介于 1 和 32 之间")
	public String getMarketLeaderId() {
		return marketLeaderId;
	}

	public void setMarketLeaderId(String marketLeaderId) {
		this.marketLeaderId = marketLeaderId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="提交时间不能为空")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public String getContractFlowStatus() {
		return contractFlowStatus;
	}

	public void setContractFlowStatus(String contractFlowStatus) {
		this.contractFlowStatus = contractFlowStatus;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getFlowFinishTime() {
		return flowFinishTime;
	}

	public void setFlowFinishTime(Date flowFinishTime) {
		this.flowFinishTime = flowFinishTime;
	}

	public List<ContractAttachment> getContractAttachmentList() {
		return contractAttachmentList;
	}

	public void setContractAttachmentList(List<ContractAttachment> contractAttachmentList) {
		this.contractAttachmentList = contractAttachmentList;
	}

	public String getMarketLeaderName() {
		return marketLeaderName;
	}

	public void setMarketLeaderName(String marketLeaderName) {
		this.marketLeaderName = marketLeaderName;
	}

	public Date getBeginApplyTime() {
		return beginApplyTime;
	}

	public void setBeginApplyTime(Date beginApplyTime) {
		this.beginApplyTime = beginApplyTime;
	}

	public Date getEndApplyTime() {
		return endApplyTime;
	}

	public void setEndApplyTime(Date endApplyTime) {
		this.endApplyTime = endApplyTime;
	}

	public String getContractNameCode() {
		return contractNameCode;
	}

	public void setContractNameCode(String contractNameCode) {
		this.contractNameCode = contractNameCode;
	}

	public Date getPrintingTimeStart() {
		return printingTimeStart;
	}

	public void setPrintingTimeStart(Date printingTimeStart) {
		this.printingTimeStart = printingTimeStart;
	}

	public Date getPrintingTimeEnd() {
		return printingTimeEnd;
	}

	public void setPrintingTimeEnd(Date printingTimeEnd) {
		this.printingTimeEnd = printingTimeEnd;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getConfigId() {
		return configId;
	}

	public void setConfigId(String configId) {
		this.configId = configId;
	}
}