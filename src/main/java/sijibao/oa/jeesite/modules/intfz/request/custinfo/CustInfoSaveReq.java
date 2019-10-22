package sijibao.oa.jeesite.modules.intfz.request.custinfo;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 客户信信保存对象信息
 * @author wanxb
 */
@ApiModel(value="客户保存请求对象")
public class CustInfoSaveReq implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value="主键ID")
	private String id;
	@ApiModelProperty(value="客户名称")
	private String custName;		// 客户名称
	@ApiModelProperty(value="客户简称")
	private String custAbbreviation;//客户简称
	@ApiModelProperty(value="修改页面带入客户名称")
	private String oldCustName;	// 修改页面带入客户名称
	@ApiModelProperty(value="股东结构")
	private String custHolderStructure;	// 股东结构
	@ApiModelProperty(value="公司性质：国企，合资，私企")
	private String custNature;	// 公司性质：国企，合资，私企
	@ApiModelProperty(value="客户来源")
	private String custSource;		// 客户来源
	@ApiModelProperty(value="客户注册地址编号")
	private String[] custAddressCode;	// 客户注册地址编号
	@ApiModelProperty(value="客户注册地址")
	private String custAddress;		// 客户注册地址
	@ApiModelProperty(value="客户类别：01企业客户02政府客户99其它")
	private String custType;		// 客户类别：01企业客户02政府客户99其它
	@ApiModelProperty(value="客户阶段：01考察02合作")
	private String custStage;		// 客户阶段：01考察02合作
	@ApiModelProperty(value="市场负责人ID")
	private String marketLeaderId;		// 市场负责人ID
	@ApiModelProperty(value="市场负责人手机号")
	private String marketLeaderPhone;		// 市场负责人手机号
	@ApiModelProperty(value=" 所属行业：煤炭，V渣土，水泥，钢铁")
	private String custTrades;	// 所属行业：煤炭，V渣土，水泥，钢铁
	@ApiModelProperty(value="业务类型：物流，贸易，物流+贸易")
	private String custBusinessType;	// 业务类型：物流，贸易，物流+贸易
	@ApiModelProperty(value="规模")
	private Integer custCompanySize;	// 规模
	@ApiModelProperty(value="运力组织方式：自有车辆，自行叫车，信息部/第三方物流")
	private String custPowerMode;		// 运力组织方式：自有车辆，自行叫车，信息部/第三方物流
	@ApiModelProperty(value="发货方式：贸易方，物流方，信息部/第三方物流")
	private String custDeliverMode;		// 发货方式：贸易方，物流方，信息部/第三方物流
	@ApiModelProperty(value="收货方式：贸易方，物流方，信息部/第三方物流")
	private String custReceiveMode;		// 收货方式：贸易方，物流方，信息部/第三方物流
	@ApiModelProperty(value="结算对象：司机，车队")
	private String custBalanceObj;	// 结算对象：司机，车队
	@ApiModelProperty(value="周期（天数）")
	private String custBalanceCycle;	// 周期（天数）
	@ApiModelProperty(value="支付方式：预付，账期，（可多选）")
	private String[] payMethod;		// 支付方式：预付，账期，（可多选）
	@ApiModelProperty(value="备注")
	private String remarks;		//备注
	@ApiModelProperty(value="联系人信息")
	private List<CustLinkmanReq> custLinkman;  	//联系人信息
	@ApiModelProperty(value="贸易结构")
	private CustTradeStructureReq custTradeStructureReq;		//贸易结构
	@ApiModelProperty(value="合同信息")
	private ContractInfoSaveReq contractInfoSaveReq;//合同信息
	@ApiModelProperty(value="投放区域公海：传入2")
	private String custListPlace;//投放区域公海：传2
	
	@ApiModelProperty(value="客户归属区域")
	private String custOfficeId;//客户归属区域
	private String producSide;//产品端

	public String getProducSide() {
		return producSide;
	}

	public void setProducSide(String producSide) {
		this.producSide = producSide;
	}

	public Integer getCustCompanySize() {
		return custCompanySize;
	}
	public void setCustCompanySize(Integer custCompanySize) {
		this.custCompanySize = custCompanySize;
	}
	public String getOldCustName() {
		return oldCustName;
	}
	public void setOldCustName(String oldCustName) {
		this.oldCustName = oldCustName;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getCustStage() {
		return custStage;
	}
	public void setCustStage(String custStage) {
		this.custStage = custStage;
	}
	public String getMarketLeaderId() {
		return marketLeaderId;
	}
	public void setMarketLeaderId(String marketLeaderId) {
		this.marketLeaderId = marketLeaderId;
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
	public String getCustBusinessType() {
		return custBusinessType;
	}
	public void setCustBusinessType(String custBusinessType) {
		this.custBusinessType = custBusinessType;
	}
	public String getCustPowerMode() {
		return custPowerMode;
	}
	public void setCustPowerMode(String custPowerMode) {
		this.custPowerMode = custPowerMode;
	}
	public String getCustDeliverMode() {
		return custDeliverMode;
	}
	public void setCustDeliverMode(String custDeliverMode) {
		this.custDeliverMode = custDeliverMode;
	}
	public String getCustReceiveMode() {
		return custReceiveMode;
	}
	public void setCustReceiveMode(String custReceiveMode) {
		this.custReceiveMode = custReceiveMode;
	}
	public String getCustBalanceObj() {
		return custBalanceObj;
	}
	public void setCustBalanceObj(String custBalanceObj) {
		this.custBalanceObj = custBalanceObj;
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
	public List<CustLinkmanReq> getCustLinkman() {
		return custLinkman;
	}
	public void setCustLinkman(List<CustLinkmanReq> custLinkman) {
		this.custLinkman = custLinkman;
	}
	public CustTradeStructureReq getCustTradeStructureReq() {
		return custTradeStructureReq;
	}
	public void setCustTradeStructureReq(CustTradeStructureReq custTradeStructureReq) {
		this.custTradeStructureReq = custTradeStructureReq;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public String getCustListPlace() {
		return custListPlace;
	}
	public void setCustListPlace(String custListPlace) {
		this.custListPlace = custListPlace;
	}
	public ContractInfoSaveReq getContractInfoSaveReq() {
		return contractInfoSaveReq;
	}
	public void setContractInfoSaveReq(ContractInfoSaveReq contractInfoSaveReq) {
		this.contractInfoSaveReq = contractInfoSaveReq;
	}
	
	public String getCustOfficeId() {
		return custOfficeId;
	}
	public void setCustOfficeId(String custOfficeId) {
		this.custOfficeId = custOfficeId;
	}
	
	public String[] getCustAddressCode() {
		return custAddressCode;
	}
	public void setCustAddressCode(String[] custAddressCode) {
		this.custAddressCode = custAddressCode;
	}
	
	public String getCustAbbreviation() {
		return custAbbreviation;
	}
	public void setCustAbbreviation(String custAbbreviation) {
		this.custAbbreviation = custAbbreviation;
	}

	@Override
	public String toString() {
		return "CustInfoSaveReq{" +
				"id='" + id + '\'' +
				", custName='" + custName + '\'' +
				", custAbbreviation='" + custAbbreviation + '\'' +
				", oldCustName='" + oldCustName + '\'' +
				", custHolderStructure='" + custHolderStructure + '\'' +
				", custNature='" + custNature + '\'' +
				", custSource='" + custSource + '\'' +
				", custAddressCode=" + Arrays.toString(custAddressCode) +
				", custAddress='" + custAddress + '\'' +
				", custType='" + custType + '\'' +
				", custStage='" + custStage + '\'' +
				", marketLeaderId='" + marketLeaderId + '\'' +
				", marketLeaderPhone='" + marketLeaderPhone + '\'' +
				", custTrades='" + custTrades + '\'' +
				", custBusinessType='" + custBusinessType + '\'' +
				", custCompanySize=" + custCompanySize +
				", custPowerMode='" + custPowerMode + '\'' +
				", custDeliverMode='" + custDeliverMode + '\'' +
				", custReceiveMode='" + custReceiveMode + '\'' +
				", custBalanceObj='" + custBalanceObj + '\'' +
				", custBalanceCycle='" + custBalanceCycle + '\'' +
				", payMethod=" + Arrays.toString(payMethod) +
				", remarks='" + remarks + '\'' +
				", custLinkman=" + custLinkman +
				", custTradeStructureReq=" + custTradeStructureReq +
				", contractInfoSaveReq=" + contractInfoSaveReq +
				", custListPlace='" + custListPlace + '\'' +
				", custOfficeId='" + custOfficeId + '\'' +
				'}';
	}
}
