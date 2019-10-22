package sijibao.oa.jeesite.modules.intfz.request.intfzwebreq;


import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 机构用户返回结果对象
 * @author xuby
 *
 */
@ApiModel(value="机构用户返回结果对象")
public class UserInfoRequest implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value="用户Id")
	private String userId; //用户ID

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "UserInfoRequest [userId=" + userId + "]";
	}
	
}
