package sijibao.oa.jeesite.modules.intfz.response.custinfo;


import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 客户信息返回对象信息
 * @author wanxb
 *
 */
@ApiModel(value="客户信息--列表返回对象")
public class CustInfoPageResponse implements Serializable{
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
	@ApiModelProperty(value="客户状态")
	private String custStage;		// 客户状态
	@ApiModelProperty(value="客户状态name")
	private String custStageName;		// 客户状态name
	@ApiModelProperty(value="规模")
	private Integer custCompanySize;		// 规模
	@ApiModelProperty(value=" 所属行业：煤炭，V渣土，水泥，钢铁")
	private String custTradesName;		// 所属行业：煤炭，V渣土，水泥，钢铁
	@ApiModelProperty(value="区域名称")
	private String officeName;		// 区域名称
	@ApiModelProperty(value="联系人职位")
	private String linkManPost;//联系人职位
	@ApiModelProperty(value="联系人/跟进人")
	private String man;		// 联系人/跟进人
	@ApiModelProperty(value="时间")
	private long time = 0l;//时间
	@ApiModelProperty(value="客户所属区域:1公海，2区域公海，3其他个人，4个人，5签约")
	private String custListPlace;//客户所在位置
	@ApiModelProperty(value="客户所属区域name:0当前负责人，1公海，2区域公海，3其他个人，4个人，5签约")
	private String custListPlaceName;//客户所在位置
	@ApiModelProperty(value="统一社会信用代码")
	private String creditCode;// 统一社会信用代码
	@ApiModelProperty(value="主客户id")
	private String mainCustId;//主客户id
	@ApiModelProperty(value="主客户name")
	private String mainCustName;//主客户name
	@ApiModelProperty(value="清结算name")
	private String accountLeaderName;//清结算
	@ApiModelProperty(value="商务助理name")
	private String businessAssistantName;		// 商务助理
	@ApiModelProperty(value="VIP客服name")
	private String vipCustomerName;		// VIP客服

	public String getAccountLeaderName() {
		return accountLeaderName;
	}

	public void setAccountLeaderName(String accountLeaderName) {
		this.accountLeaderName = accountLeaderName;
	}

	public String getBusinessAssistantName() {
		return businessAssistantName;
	}

	public void setBusinessAssistantName(String businessAssistantName) {
		this.businessAssistantName = businessAssistantName;
	}

	public String getVipCustomerName() {
		return vipCustomerName;
	}

	public void setVipCustomerName(String vipCustomerName) {
		this.vipCustomerName = vipCustomerName;
	}

	public String getCustTradesName() {
		return custTradesName;
	}
	public void setCustTradesName(String custTradesName) {
		this.custTradesName = custTradesName;
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
	public String getCustStageName() {
		return custStageName;
	}
	public void setCustStageName(String custStageName) {
		this.custStageName = custStageName;
	}
	public Integer getCustCompanySize() {
		return custCompanySize;
	}
	public void setCustCompanySize(Integer custCompanySize) {
		this.custCompanySize = custCompanySize;
	}
	public String getOfficeName() {
		return officeName;
	}
	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}
	public String getMan() {
		return man;
	}
	public void setMan(String man) {
		this.man = man;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public String getLinkManPost() {
		return linkManPost;
	}
	public void setLinkManPost(String linkManPost) {
		this.linkManPost = linkManPost;
	}
	
	public String getCustListPlace() {
		return custListPlace;
	}
	public void setCustListPlace(String custListPlace) {
		this.custListPlace = custListPlace;
	}
	
	public String getCreditCode() {
		return creditCode;
	}
	public void setCreditCode(String creditCode) {
		this.creditCode = creditCode;
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
	public String getCustListPlaceName() {
		return custListPlaceName;
	}
	public void setCustListPlaceName(String custListPlaceName) {
		this.custListPlaceName = custListPlaceName;
	}
	public String getCustStage() {
		return custStage;
	}
	public void setCustStage(String custStage) {
		this.custStage = custStage;
	}
	@Override
	public String toString() {
		return "CustInfoPageResponse [id=" + id + ", custCode=" + custCode + ", custName=" + custName + ", custStage="
				+ custStage + ", custStageName=" + custStageName + ", custCompanySize=" + custCompanySize
				+ ", custTradesName=" + custTradesName + ", officeName=" + officeName + ", linkManPost=" + linkManPost
				+ ", man=" + man + ", time=" + time + ", custListPlace=" + custListPlace + ", custListPlaceName="
				+ custListPlaceName + ", creditCode=" + creditCode + ", mainCustId=" + mainCustId + ", mainCustName="
				+ mainCustName + "]";
	}
}
