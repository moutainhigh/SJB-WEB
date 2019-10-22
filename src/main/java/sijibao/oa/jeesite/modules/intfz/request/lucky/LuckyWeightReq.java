package sijibao.oa.jeesite.modules.intfz.request.lucky;

import java.io.Serializable;
import java.math.BigDecimal;

import io.swagger.annotations.ApiModelProperty;

/**
 * 年会活动Entity
 * @author xby
 * @version 2019-01-14
 */
public class LuckyWeightReq implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value="体重")
	private BigDecimal weight;
	@ApiModelProperty(value="姓名")
	private String name;
	@ApiModelProperty(value="身高")
	private BigDecimal height;
	public BigDecimal getWeight() {
		return weight;
	}
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public BigDecimal getHeight() {
		return height;
	}
	public void setHeight(BigDecimal height) {
		this.height = height;
	}
	
}