package sijibao.oa.jeesite.modules.intfz.response.openaccountflow;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 开户管理列表返回对象
 * @author wanxb
 */
@ApiModel(value="开户列表返回对象")
public class OpenAccountFlowResponse  {
	@ApiModelProperty(value="主键 id")
	private String id ;//主键 id
	@ApiModelProperty(value="开户编号")
	private String openAccountCode;		// 开户编号
	@ApiModelProperty(value="合同编号")
	private String contractCode;		// 合同编号
	@ApiModelProperty(value="甲方名称")
	private String firstPartyName;		// 甲方名称
	@ApiModelProperty(value="市场负责人Name")
	private String marketLeaderName;		// 市场负责人Name
	@ApiModelProperty(value="开户状态name：0.配置中，1.可使用")
	private String openAccountStatusValue;		// 开户状态
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOpenAccountCode() {
		return openAccountCode;
	}
	public void setOpenAccountCode(String openAccountCode) {
		this.openAccountCode = openAccountCode;
	}
	public String getContractCode() {
		return contractCode;
	}
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	public String getFirstPartyName() {
		return firstPartyName;
	}
	public void setFirstPartyName(String firstPartyName) {
		this.firstPartyName = firstPartyName;
	}
	public String getMarketLeaderName() {
		return marketLeaderName;
	}
	public void setMarketLeaderName(String marketLeaderName) {
		this.marketLeaderName = marketLeaderName;
	}
	
	public String getOpenAccountStatusValue() {
		return openAccountStatusValue;
	}
	public void setOpenAccountStatusValue(String openAccountStatusValue) {
		this.openAccountStatusValue = openAccountStatusValue;
	}
	@Override
	public String toString() {
		return "OpenAccountFlowResponse [id=" + id + ", openAccountCode=" + openAccountCode + ", contractCode="
				+ contractCode + ", firstPartyName=" + firstPartyName + ", marketLeaderName=" + marketLeaderName
				+ ", openAccountStatusValue=" + openAccountStatusValue + "]";
	}

}
