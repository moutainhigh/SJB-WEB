package sijibao.oa.jeesite.modules.intfz.request.custinfo;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 客户管理-批量移动客户请求对象
 * @author wanxb
 *
 */
@ApiModel(value="客户管理-批量移动客户请求对象")
public class BatchMoveReq implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value="市场负责人id")
	private String marketLeaderId;		// 市场负责人id
	@ApiModelProperty(value="客户归属区域id")
	private String custOfficeId;//客户归属区域id
	@ApiModelProperty(value="客户阶段ABCDEFG")
	private String custStage;		// 客户阶段
	@ApiModelProperty(value="所属行业：煤炭，渣土，水泥，钢铁")
	private String custTrades;		// 所属行业：煤炭，渣土，水泥，钢铁
	@ApiModelProperty(value="所选客户id")
	private List<String> custIds;//所选客户id
	public String getMarketLeaderId() {
		return marketLeaderId;
	}
	public void setMarketLeaderId(String marketLeaderId) {
		this.marketLeaderId = marketLeaderId;
	}
	public String getCustOfficeId() {
		return custOfficeId;
	}
	public void setCustOfficeId(String custOfficeId) {
		this.custOfficeId = custOfficeId;
	}
	public String getCustStage() {
		return custStage;
	}
	public void setCustStage(String custStage) {
		this.custStage = custStage;
	}
	public String getCustTrades() {
		return custTrades;
	}
	public void setCustTrades(String custTrades) {
		this.custTrades = custTrades;
	}
	public List<String> getCustIds() {
		return custIds;
	}
	public void setCustIds(List<String> custIds) {
		this.custIds = custIds;
	}
	@Override
	public String toString() {
		return "BatchMoveReq [marketLeaderId=" + marketLeaderId + ", custOfficeId=" + custOfficeId + ", custStage="
				+ custStage + ", custTrades=" + custTrades + ", custIds=" + custIds + "]";
	}
	
	

}
