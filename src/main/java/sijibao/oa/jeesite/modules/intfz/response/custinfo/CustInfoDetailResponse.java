package sijibao.oa.jeesite.modules.intfz.response.custinfo;


import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 客户信息返回对象信息
 * @author wanxb
 *
 */
@ApiModel(value="客户信息--明细返回对象")
public class CustInfoDetailResponse implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value="主键ID")
	private String id; //主键ID
	@ApiModelProperty(value="客户编号")
	private String custCode;		// 客户编号
	@ApiModelProperty(value="客户名称")
	private String custName;		// 客户名称
	@ApiModelProperty(value="主客户查询")
	private String mainCustName ;//主客户查询
	@ApiModelProperty(value="客户简称")
	private String custAbbreviation;//客户简称
	@ApiModelProperty(value="所属区域id")
	private String[] areaId;		// 所属区域id
	@ApiModelProperty(value="区域名称")
	private String areaName;		// 区域名称
	@ApiModelProperty(value="客户归属区域")
	private String custOfficeName;//客户归属区域
	@ApiModelProperty(value="股东结构")
	private String custHolderStructure;		// 股东结构
	@ApiModelProperty(value="公司性质：国企，合资，私企")
	private String custNature;		// 公司性质：国企，合资，私企
	@ApiModelProperty(value="公司性质name：国企，合资，私企")
	private String custNatureAppName;		// 公司性质name：国企，合资，私企
	@ApiModelProperty(value="客户来源")
	private String custSource;		// 客户来源
	@ApiModelProperty(value="客户注册地址编号")
	private String[] custAddressCode;		// 客户注册地址编号
	@ApiModelProperty(value="客户注册地址编号Name")
	private String custAddressCodeName;		// 客户注册地址编号
	@ApiModelProperty(value="客户注册地址")
	private String custAddress;		// 客户注册地址
	@ApiModelProperty(value="客户类别：01企业客户02政府客户99其它")
	private String custType;		// 客户类别：01企业客户02政府客户99其它
	@ApiModelProperty(value="客户类别name：01企业客户02政府客户99其它")
	private String custTypeAppName;		// 客户类别：01企业客户02政府客户99其它
	@ApiModelProperty(value="市场负责人ID")
	private String marketLeaderId;		// 市场负责人ID
	@ApiModelProperty(value="市场负责人name")
	private String marketLeaderName;		// 市场负责人name
	@ApiModelProperty(value="市场负责人手机号")
	private String marketLeaderPhone;		// 市场负责人手机号
	@ApiModelProperty(value=" 所属行业：煤炭，V渣土，水泥，钢铁")
	private String custTrades;		// 所属行业：煤炭，V渣土，水泥，钢铁
	@ApiModelProperty(value=" 所属行业name：煤炭，V渣土，水泥，钢铁")
	private String custTradesAppName;		// 所属行业name：煤炭，V渣土，水泥，钢铁
	@ApiModelProperty(value="业务类型：物流，贸易，物流+贸易")
	private String custBusinessType;		// 业务类型：物流，贸易，物流+贸易
	@ApiModelProperty(value="业务类型name：物流，贸易，物流+贸易")
	private String custBusinessTypeAppName;		// 业务类型name：物流，贸易，物流+贸易
	@ApiModelProperty(value="规模")
	private Integer custCompanySize;		// 规模
	@ApiModelProperty(value="运力组织方式：自有车辆，自行叫车，信息部/第三方物流")
	private String custPowerMode;		// 运力组织方式：自有车辆，自行叫车，信息部/第三方物流
	@ApiModelProperty(value="运力组织方式name：自有车辆，自行叫车，信息部/第三方物流")
	private String custPowerModeAppName;		// 运力组织方式name：自有车辆，自行叫车，信息部/第三方物流
	@ApiModelProperty(value="发货方式：贸易方，物流方，信息部/第三方物流")
	private String custDeliverMode;		// 发货方式：贸易方，物流方，信息部/第三方物流
	@ApiModelProperty(value="发货方式name：贸易方，物流方，信息部/第三方物流")
	private String custDeliverModeAppName;		// 发货方式name：贸易方，物流方，信息部/第三方物流
	@ApiModelProperty(value="收货方式：贸易方，物流方，信息部/第三方物流")
	private String custReceiveMode;		// 收货方式：贸易方，物流方，信息部/第三方物流
	@ApiModelProperty(value="收货方式name：贸易方，物流方，信息部/第三方物流")
	private String custReceiveModeAppName;		// 收货方式：贸易方，物流方，信息部/第三方物流
	@ApiModelProperty(value="结算对象：司机，车队")
	private String custBalanceObj;		// 结算对象：司机，车队
	@ApiModelProperty(value="结算对象name：司机，车队")
	private String custBalanceObjAppName;		// 结算对象：司机，车队
	@ApiModelProperty(value="周期（天数）")
	private String custBalanceCycle;		// 周期（天数）
	@ApiModelProperty(value="支付方式：预付，账期，（可多选）")
	private String[] payMethod;		// 支付方式：预付，账期，（可多选）
	@ApiModelProperty(value="支付方式name：预付，账期，（可多选）")
	private String payMethodName;		// 支付方式name：预付，账期，（可多选）

	@ApiModelProperty(value="客户类别name")
	private String custStageAppName;//客户类别name
	@ApiModelProperty(value="备注")
	private String remarks;		//备注
	
	@ApiModelProperty(value="客户所属区域:1公海，2区域公海，3其他个人，4个人，5签约")
	private String custListPlace;//客户所在位置
	@ApiModelProperty(value="客户状态，ABCDEFG")
	private String custStage;//客户阶段
	@ApiModelProperty(value="开放人")
	private String letGoMan;//开放人
	@ApiModelProperty(value="开放人手机号")
	private String letGoManMoble;//开放人手机号
	
	@ApiModelProperty(value="客户结构")
	private CustTradeStructureResponse custTradeStructureResponse;
	@ApiModelProperty(value="联系人信息")
	private List<CustLinkmanResponse> custLinkmanResponse;
	@ApiModelProperty(value="合同信息")
	private ContractInfoDetailResponse contractInfoDetailResponse ;//合同信息
	@ApiModelProperty(value="VIP客服name")
	private String vipCustomerName;
	@ApiModelProperty(value="商务助理name")
	private String businessAssistantName;
	@ApiModelProperty(value="清结算name")
	private String accountLeaderName;

	@ApiModelProperty(value="子客户信息")
	private List<ChildCustResp> childCustList;		// 子客户信息

	public String getMainCustName() {
		return mainCustName;
	}

	public void setMainCustName(String mainCustName) {
		this.mainCustName = mainCustName;
	}

	public String getVipCustomerName() {
		return vipCustomerName;
	}

	public void setVipCustomerName(String vipCustomerName) {
		this.vipCustomerName = vipCustomerName;
	}

	public String getBusinessAssistantName() {
		return businessAssistantName;
	}

	public void setBusinessAssistantName(String businessAssistantName) {
		this.businessAssistantName = businessAssistantName;
	}

	public String getAccountLeaderName() {
		return accountLeaderName;
	}

	public void setAccountLeaderName(String accountLeaderName) {
		this.accountLeaderName = accountLeaderName;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getCustHolderStructure() {
		return custHolderStructure;
	}
	public void setCustHolderStructure(String custHolderStructure) {
		this.custHolderStructure = custHolderStructure;
	}
	public String getCustNature() {
		return custNature;
	}
	public void setCustNature(String custNature) {
		this.custNature = custNature;
	}
	public String getCustNatureAppName() {
		return custNatureAppName;
	}
	public void setCustNatureAppName(String custNatureAppName) {
		this.custNatureAppName = custNatureAppName;
	}
	public String getCustSource() {
		return custSource;
	}
	public void setCustSource(String custSource) {
		this.custSource = custSource;
	}
	public String getCustAddress() {
		return custAddress;
	}
	public void setCustAddress(String custAddress) {
		this.custAddress = custAddress;
	}
	public String getCustType() {
		return custType;
	}
	public void setCustType(String custType) {
		this.custType = custType;
	}
	public String getCustTypeAppName() {
		return custTypeAppName;
	}
	public void setCustTypeAppName(String custTypeAppName) {
		this.custTypeAppName = custTypeAppName;
	}
	public String getMarketLeaderId() {
		return marketLeaderId;
	}
	public void setMarketLeaderId(String marketLeaderId) {
		this.marketLeaderId = marketLeaderId;
	}
	public String getMarketLeaderName() {
		return marketLeaderName;
	}
	public void setMarketLeaderName(String marketLeaderName) {
		this.marketLeaderName = marketLeaderName;
	}
	public String getMarketLeaderPhone() {
		return marketLeaderPhone;
	}
	public void setMarketLeaderPhone(String marketLeaderPhone) {
		this.marketLeaderPhone = marketLeaderPhone;
	}
	public String getCustTrades() {
		return custTrades;
	}
	public void setCustTrades(String custTrades) {
		this.custTrades = custTrades;
	}
	public String getCustTradesAppName() {
		return custTradesAppName;
	}
	public void setCustTradesAppName(String custTradesAppName) {
		this.custTradesAppName = custTradesAppName;
	}
	public String getCustBusinessType() {
		return custBusinessType;
	}
	public void setCustBusinessType(String custBusinessType) {
		this.custBusinessType = custBusinessType;
	}
	public String getCustBusinessTypeAppName() {
		return custBusinessTypeAppName;
	}
	public void setCustBusinessTypeAppName(String custBusinessTypeAppName) {
		this.custBusinessTypeAppName = custBusinessTypeAppName;
	}
	public Integer getCustCompanySize() {
		return custCompanySize;
	}
	public void setCustCompanySize(Integer custCompanySize) {
		this.custCompanySize = custCompanySize;
	}
	public String getCustPowerMode() {
		return custPowerMode;
	}
	public void setCustPowerMode(String custPowerMode) {
		this.custPowerMode = custPowerMode;
	}
	public String getCustPowerModeAppName() {
		return custPowerModeAppName;
	}
	public void setCustPowerModeAppName(String custPowerModeAppName) {
		this.custPowerModeAppName = custPowerModeAppName;
	}
	public String getCustDeliverMode() {
		return custDeliverMode;
	}
	public void setCustDeliverMode(String custDeliverMode) {
		this.custDeliverMode = custDeliverMode;
	}
	public String getCustDeliverModeAppName() {
		return custDeliverModeAppName;
	}
	public void setCustDeliverModeAppName(String custDeliverModeAppName) {
		this.custDeliverModeAppName = custDeliverModeAppName;
	}
	public String getCustReceiveMode() {
		return custReceiveMode;
	}
	public void setCustReceiveMode(String custReceiveMode) {
		this.custReceiveMode = custReceiveMode;
	}
	public String getCustReceiveModeAppName() {
		return custReceiveModeAppName;
	}
	public void setCustReceiveModeAppName(String custReceiveModeAppName) {
		this.custReceiveModeAppName = custReceiveModeAppName;
	}
	public String getCustBalanceObj() {
		return custBalanceObj;
	}
	public void setCustBalanceObj(String custBalanceObj) {
		this.custBalanceObj = custBalanceObj;
	}
	public String getCustBalanceObjAppName() {
		return custBalanceObjAppName;
	}
	public void setCustBalanceObjAppName(String custBalanceObjAppName) {
		this.custBalanceObjAppName = custBalanceObjAppName;
	}
	public String getCustBalanceCycle() {
		return custBalanceCycle;
	}
	public void setCustBalanceCycle(String custBalanceCycle) {
		this.custBalanceCycle = custBalanceCycle;
	}
	public String[] getPayMethod() {
		return payMethod;
	}
	public void setPayMethod(String[] payMethod) {
		this.payMethod = payMethod;
	}
	public ContractInfoDetailResponse getContractInfoDetailResponse() {
		return contractInfoDetailResponse;
	}
	public void setContractInfoDetailResponse(ContractInfoDetailResponse contractInfoDetailResponse) {
		this.contractInfoDetailResponse = contractInfoDetailResponse;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public List<CustLinkmanResponse> getCustLinkmanResponse() {
		return custLinkmanResponse;
	}
	public void setCustLinkmanResponse(List<CustLinkmanResponse> custLinkmanResponse) {
		this.custLinkmanResponse = custLinkmanResponse;
	}
	public String getCustStage() {
		return custStage;
	}
	public void setCustStage(String custStage) {
		this.custStage = custStage;
	}
	
	public String getCustStageAppName() {
		return custStageAppName;
	}
	public void setCustStageAppName(String custStageAppName) {
		this.custStageAppName = custStageAppName;
	}
	
	public String[] getCustAddressCode() {
		return custAddressCode;
	}
	public void setCustAddressCode(String[] custAddressCode) {
		this.custAddressCode = custAddressCode;
	}
	public String getCustListPlace() {
		return custListPlace;
	}
	public void setCustListPlace(String custListPlace) {
		this.custListPlace = custListPlace;
	}
	public String getCustAbbreviation() {
		return custAbbreviation;
	}
	public void setCustAbbreviation(String custAbbreviation) {
		this.custAbbreviation = custAbbreviation;
	}
	public String[] getAreaId() {
		return areaId;
	}
	public void setAreaId(String[] areaId) {
		this.areaId = areaId;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public String getPayMethodName() {
		return payMethodName;
	}
	public void setPayMethodName(String payMethodName) {
		this.payMethodName = payMethodName;
	}
	public String getCustAddressCodeName() {
		return custAddressCodeName;
	}
	public void setCustAddressCodeName(String custAddressCodeName) {
		this.custAddressCodeName = custAddressCodeName;
	}
	public String getCustOfficeName() {
		return custOfficeName;
	}
	public void setCustOfficeName(String custOfficeName) {
		this.custOfficeName = custOfficeName;
	}
	public String getLetGoMan() {
		return letGoMan;
	}
	public void setLetGoMan(String letGoMan) {
		this.letGoMan = letGoMan;
	}
	
	public String getLetGoManMoble() {
		return letGoManMoble;
	}
	public void setLetGoManMoble(String letGoManMoble) {
		this.letGoManMoble = letGoManMoble;
	}
	public CustTradeStructureResponse getCustTradeStructureResponse() {
		return custTradeStructureResponse;
	}
	public void setCustTradeStructureResponse(CustTradeStructureResponse custTradeStructureResponse) {
		this.custTradeStructureResponse = custTradeStructureResponse;
	}

	public List<ChildCustResp> getChildCustList() {
		return childCustList;
	}

	public void setChildCustList(List<ChildCustResp> childCustList) {
		this.childCustList = childCustList;
	}

	@Override
	public String toString() {
		return "CustInfoDetailResponse [id=" + id + ", custCode=" + custCode + ", custName=" + custName
				+ ", custAbbreviation=" + custAbbreviation + ", areaId=" + Arrays.toString(areaId) + ", areaName="
				+ areaName + ", custOfficeName=" + custOfficeName + ", custHolderStructure=" + custHolderStructure
				+ ", custNature=" + custNature + ", custNatureAppName=" + custNatureAppName + ", custSource="
				+ custSource + ", custAddressCode=" + Arrays.toString(custAddressCode) + ", custAddressCodeName="
				+ custAddressCodeName + ", custAddress=" + custAddress + ", custType=" + custType + ", custTypeAppName="
				+ custTypeAppName + ", marketLeaderId=" + marketLeaderId + ", marketLeaderName=" + marketLeaderName
				+ ", marketLeaderPhone=" + marketLeaderPhone + ", custTrades=" + custTrades + ", custTradesAppName="
				+ custTradesAppName + ", custBusinessType=" + custBusinessType + ", custBusinessTypeAppName="
				+ custBusinessTypeAppName + ", custCompanySize=" + custCompanySize + ", custPowerMode=" + custPowerMode
				+ ", custPowerModeAppName=" + custPowerModeAppName + ", custDeliverMode=" + custDeliverMode
				+ ", custDeliverModeAppName=" + custDeliverModeAppName + ", custReceiveMode=" + custReceiveMode
				+ ", custReceiveModeAppName=" + custReceiveModeAppName + ", custBalanceObj=" + custBalanceObj
				+ ", custBalanceObjAppName=" + custBalanceObjAppName + ", custBalanceCycle=" + custBalanceCycle
				+ ", payMethod=" + Arrays.toString(payMethod) + ", payMethodName=" + payMethodName
				+ ", contractInfoDetailResponse=" + contractInfoDetailResponse + ", custStageAppName="
				+ custStageAppName + ", remarks=" + remarks + ", custListPlace=" + custListPlace + ", custStage="
				+ custStage + ", letGoMan=" + letGoMan + ", letGoManMoble=" + letGoManMoble
				+ ", custTradeStructureResponse=" + custTradeStructureResponse + ", custLinkmanResponse="
				+ custLinkmanResponse + "]";
	}

}
