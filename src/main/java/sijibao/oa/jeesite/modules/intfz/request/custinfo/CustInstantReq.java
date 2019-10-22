package sijibao.oa.jeesite.modules.intfz.request.custinfo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 客户管理-批量移动客户请求对象
 * @author wanxb
 *
 */
@ApiModel(value="客户管理-批量移动客户请求对象")
public class CustInstantReq implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value="客户名称")
	private String custName;		// 客户名称
	@ApiModelProperty(value="主客户或子客户：1主，2子")
	private String custType;		// 主客户或子客户：1主，2子
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getCustType() {
		return custType;
	}
	public void setCustType(String custType) {
		this.custType = custType;
	}
	@Override
	public String toString() {
		return "CustInstantReq [custName=" + custName + ", custType=" + custType + "]";
	}
	
}
