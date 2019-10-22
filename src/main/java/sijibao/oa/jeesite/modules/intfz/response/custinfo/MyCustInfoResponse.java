package sijibao.oa.jeesite.modules.intfz.response.custinfo;


import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 客户信息返回对象信息
 * @author wanxb
 *
 */
@ApiModel(value="客户信息--列表返回对象")
public class MyCustInfoResponse implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value="主键ID")
	private String custId; //主键ID
	@ApiModelProperty(value="客户名称")
	private String custName;		// 客户名称
	@ApiModelProperty(value="联系人信息")
	private List<CustLinkmanResponse> custLinkman ;

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

	public List<CustLinkmanResponse> getCustLinkman() {
		return custLinkman;
	}

	public void setCustLinkman(List<CustLinkmanResponse> custLinkman) {
		this.custLinkman = custLinkman;
	}
}
