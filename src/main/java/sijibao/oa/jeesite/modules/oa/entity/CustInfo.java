/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.oa.entity;

import java.util.Date;
import java.util.List;

import com.sijibao.oa.common.persistence.DataEntity;
import com.sijibao.oa.modules.sys.entity.User;

/**
 * 客户信息表
 * @author wanxb
 * @version 2018-05-25
 */
public class CustInfo extends DataEntity<CustInfo> {
	
	private static final long serialVersionUID = 1L;
	private String custCode;		// 客户编号yyyymmdd+3位随机数
	private String custName;		// 客户名称
	private String custAbbreviation;//客户简称
	private String areaId;		// 所属区域id
	private String custHolderStructure;		// 股东结构
	private String custNature;		// 公司性质：国企，合资，私企
	private String custSource;		// 客户来源
	private String custAddressCode;		// 客户注册地址编号
	private String custAddress;		// 客户注册地址
	private String custType;		// 客户类别：01企业客户02政府客户99其它
	private String marketLeaderId;		// 市场负责人
	private String marketLeaderName;		// 市场负责人
	private String marketLeaderPhone;		// 市场负责人手机号
	private String custStage;		// 客户阶段
	private String CustStageName;	//客户阶段name
	private String custTrades;		// 所属行业：煤炭，渣土，水泥，钢铁
	private String custTradesName;	//所属行业name
	private String custBusinessType;		// 业务类型：物流，贸易，物流+贸易
	private Integer custCompanySize;		// 规模
	private String custPowerMode;		// 运力组织方式：自有车辆，自行叫车，信息部/第三方物流
	private String custDeliverMode;		// 发货方式：贸易方，物流方，信息部/第三方物流
	private String custReceiveMode;		// 收货方式：贸易方，物流方，信息部/第三方物流
	private String custBalanceObj;		// 结算对象：司机，车队
	private String custBalanceCycle;		// 周期（天数）
	private String payMethod;		// 支付方式：预付，账期，（可多选）
	
	private String custOfficeId;//客户归属区域
	private String custOfficeName;//部门name
	private String custListPlace;//客户所在位置
	private String letGoMan;//开放人
	private Date letGoTime;//开放时间
	private String secondGoMan;//区域公海开放人
	private Date secondGoTime ;//区域公海开放时间
	private String pickUpMan;//捡入
	private Date pickUpTime;//捡入时间

	private String isMainCust;//是主客户，1主客户，2子客户
	private String mainCustId;//主客户id
	private String mainCustName;//主客户name
	private String conflict;//冲突
	
	private String sql;	//数据权限控制sql
	private User user;  //当前用户
	private String updateFlag; //更新标识
	
	private Date beginTime;		// 开始 搜索时间
	private Date endTime;		// 结束 搜索时间
	
	
	private String mergeMainId;//合并的主id
	private List<String> mergeChildIds;//子客户id
	private String creditCode;// 统一社会信用代码
	private String producSide;//产品端

	public String getProducSide() {
		return producSide;
	}

	public void setProducSide(String producSide) {
		this.producSide = producSide;
	}

	public String getCustOfficeId() {
		return custOfficeId;
	}
	public void setCustOfficeId(String custOfficeId) {
		this.custOfficeId = custOfficeId;
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
	public String getAreaId() {
		return areaId;
	}
	public void setAreaId(String areaId) {
		this.areaId = areaId;
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
	public String getPayMethod() {
		return payMethod;
	}
	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}
	public String getCustListPlace() {
		return custListPlace;
	}
	public void setCustListPlace(String custListPlace) {
		this.custListPlace = custListPlace;
	}
	public String getLetGoMan() {
		return letGoMan;
	}
	public void setLetGoMan(String letGoMan) {
		this.letGoMan = letGoMan;
	}
	public Date getLetGoTime() {
		return letGoTime;
	}
	public void setLetGoTime(Date letGoTime) {
		this.letGoTime = letGoTime;
	}
	public String getSecondGoMan() {
		return secondGoMan;
	}
	public void setSecondGoMan(String secondGoMan) {
		this.secondGoMan = secondGoMan;
	}
	public Date getSecondGoTime() {
		return secondGoTime;
	}
	public void setSecondGoTime(Date secondGoTime) {
		this.secondGoTime = secondGoTime;
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
	
	public String getConflict() {
		return conflict;
	}
	public void setConflict(String conflict) {
		this.conflict = conflict;
	}
	public String getCustStage() {
		return custStage;
	}
	public void setCustStage(String custStage) {
		this.custStage = custStage;
	}
	
	public String getPickUpMan() {
		return pickUpMan;
	}
	public void setPickUpMan(String pickUpMan) {
		this.pickUpMan = pickUpMan;
	}
	public Date getPickUpTime() {
		return pickUpTime;
	}
	public void setPickUpTime(Date pickUpTime) {
		this.pickUpTime = pickUpTime;
	}
	
	public String getCustAddressCode() {
		return custAddressCode;
	}
	public void setCustAddressCode(String custAddressCode) {
		this.custAddressCode = custAddressCode;
	}
	
	public String getCustAbbreviation() {
		return custAbbreviation;
	}
	public void setCustAbbreviation(String custAbbreviation) {
		this.custAbbreviation = custAbbreviation;
	}

	public String getUpdateFlag() {
		return updateFlag;
	}
	public void setUpdateFlag(String updateFlag) {
		this.updateFlag = updateFlag;
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
	public Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	public String getIsMainCust() {
		return isMainCust;
	}
	public void setIsMainCust(String isMainCust) {
		this.isMainCust = isMainCust;
	}
	public String getMainCustId() {
		return mainCustId;
	}
	public void setMainCustId(String mainCustId) {
		this.mainCustId = mainCustId;
	}
	public String getMainCustName() {
		return mainCustName;
	}
	public void setMainCustName(String mainCustName) {
		this.mainCustName = mainCustName;
	}
	public String getMergeMainId() {
		return mergeMainId;
	}
	public void setMergeMainId(String mergeMainId) {
		this.mergeMainId = mergeMainId;
	}
	public List<String> getMergeChildIds() {
		return mergeChildIds;
	}
	public void setMergeChildIds(List<String> mergeChildIds) {
		this.mergeChildIds = mergeChildIds;
	}

	public String getCreditCode() {
		return creditCode;
	}
	public void setCreditCode(String creditCode) {
		this.creditCode = creditCode;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getCustStageName() {
		return CustStageName;
	}

	public void setCustStageName(String custStageName) {
		CustStageName = custStageName;
	}

	public String getCustTradesName() {
		return custTradesName;
	}

	public void setCustTradesName(String custTradesName) {
		this.custTradesName = custTradesName;
	}

	public String getCustOfficeName() {
		return custOfficeName;
	}

	public void setCustOfficeName(String custOfficeName) {
		this.custOfficeName = custOfficeName;
	}


	@Override
	public String toString() {
		return "CustInfo{" +
				"custCode='" + custCode + '\'' +
				", custName='" + custName + '\'' +
				", custAbbreviation='" + custAbbreviation + '\'' +
				", areaId='" + areaId + '\'' +
				", custHolderStructure='" + custHolderStructure + '\'' +
				", custNature='" + custNature + '\'' +
				", custSource='" + custSource + '\'' +
				", custAddressCode='" + custAddressCode + '\'' +
				", custAddress='" + custAddress + '\'' +
				", custType='" + custType + '\'' +
				", marketLeaderId='" + marketLeaderId + '\'' +
				", marketLeaderName='" + marketLeaderName + '\'' +
				", marketLeaderPhone='" + marketLeaderPhone + '\'' +
				", custStage='" + custStage + '\'' +
				", CustStageName='" + CustStageName + '\'' +
				", custTrades='" + custTrades + '\'' +
				", custTradesName='" + custTradesName + '\'' +
				", custBusinessType='" + custBusinessType + '\'' +
				", custCompanySize=" + custCompanySize +
				", custPowerMode='" + custPowerMode + '\'' +
				", custDeliverMode='" + custDeliverMode + '\'' +
				", custReceiveMode='" + custReceiveMode + '\'' +
				", custBalanceObj='" + custBalanceObj + '\'' +
				", custBalanceCycle='" + custBalanceCycle + '\'' +
				", payMethod='" + payMethod + '\'' +
				", custOfficeId='" + custOfficeId + '\'' +
				", custOfficeName='" + custOfficeName + '\'' +
				", custListPlace='" + custListPlace + '\'' +
				", letGoMan='" + letGoMan + '\'' +
				", letGoTime=" + letGoTime +
				", secondGoMan='" + secondGoMan + '\'' +
				", secondGoTime=" + secondGoTime +
				", pickUpMan='" + pickUpMan + '\'' +
				", pickUpTime=" + pickUpTime +
				", isMainCust='" + isMainCust + '\'' +
				", mainCustId='" + mainCustId + '\'' +
				", mainCustName='" + mainCustName + '\'' +
				", conflict='" + conflict + '\'' +
				", sql='" + sql + '\'' +
				", user=" + user +
				", updateFlag='" + updateFlag + '\'' +
				", beginTime=" + beginTime +
				", endTime=" + endTime +
				", mergeMainId='" + mergeMainId + '\'' +
				", mergeChildIds=" + mergeChildIds +
				", creditCode='" + creditCode + '\'' +
				'}';
	}
}