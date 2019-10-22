package sijibao.oa.jeesite.modules.intfz.response.lucky;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;

/**
 * 年会活动Entity
 * @author xby
 * @version 2019-01-14
 */
public class LuckyQueryWeightRes implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value="体重")
	private List<LuckyWeightRes> luckyWeightList;
	public List<LuckyWeightRes> getLuckyWeightList() {
		return luckyWeightList;
	}
	public void setLuckyWeightList(List<LuckyWeightRes> luckyWeightList) {
		this.luckyWeightList = luckyWeightList;
	}
}