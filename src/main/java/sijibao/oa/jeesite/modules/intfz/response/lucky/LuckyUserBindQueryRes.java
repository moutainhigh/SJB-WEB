package sijibao.oa.jeesite.modules.intfz.response.lucky;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

/**
 * 年会活动Entity
 * @author xby
 * @version 2019-01-14
 */
public class LuckyUserBindQueryRes implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value="1：已绑定，0:未绑定")
	private Integer isBind; 

	public Integer getIsBind() {
		return isBind;
	}
	public void setIsBind(Integer isBind) {
		this.isBind = isBind;
	}

	public LuckyUserBindQueryRes(){}
}