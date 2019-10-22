package sijibao.oa.jeesite.modules.intfz.request.lucky;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

/**
 * 年会活动Entity
 * @author xby
 * @version 2019-01-14
 */
public class LuckyQueryWeightReq implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value="查询类型(0:按体重排序正序，1：按时间排序正序，2:按体重倒序，3：按时间倒序)")
	private Integer orderByType;
	public Integer getOrderByType() {
		return orderByType;
	}
	public void setOrderByType(Integer orderByType) {
		this.orderByType = orderByType;
	}
	
}