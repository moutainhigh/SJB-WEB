package sijibao.oa.jeesite.modules.intfz.response.custinfo;


import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 客户管理-模糊查询10条客户数据返回对象
 * @author wanxb
 *
 */
@ApiModel(value="客户管理-模糊查询10条客户数据返回对象")
public class CustInstantResponse implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value="客户id")
	private String custId;		// 客户id
	@ApiModelProperty(value="客户名称")
	private String custName;		// 客户名称
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	
}
