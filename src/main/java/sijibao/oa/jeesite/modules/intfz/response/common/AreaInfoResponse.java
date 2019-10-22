package sijibao.oa.jeesite.modules.intfz.response.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 区域信息返回实体
 * @author xuby
 *
 */
@ApiModel
public class AreaInfoResponse {
	
	@ApiModelProperty(value="区域编码")
	private String name; //区域编码
	@ApiModelProperty(value="区域名称")
	private String value; //区域名称
	@ApiModelProperty(value="父级编码")
	private String parent; //父级编码
	
	public AreaInfoResponse(){}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}
	
}	
