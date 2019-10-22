package sijibao.oa.jeesite.modules.intfz.request.intfzwebreq;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModelProperty;

/**
 * 提前打款保存
 * @author wanxb
 *
 */
public class BringRemitSaveRequest {
	@ApiModelProperty(value="报销id")
	private String id ;		//报销id
	@ApiModelProperty(value="提前打款金额")
	private BigDecimal bringAmount; //提前打款金额
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public BigDecimal getBringAmount() {
		return bringAmount;
	}
	public void setBringAmount(BigDecimal bringAmount) {
		this.bringAmount = bringAmount;
	}
	@Override
	public String toString() {
		return "BringRemitSaveRequest [id=" + id + ", bringAmount=" + bringAmount + "]";
	}
}
