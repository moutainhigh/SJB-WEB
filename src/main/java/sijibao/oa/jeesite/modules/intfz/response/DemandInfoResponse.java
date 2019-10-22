package sijibao.oa.jeesite.modules.intfz.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 需求主题信息返回实体
 * @author xuby
 *
 */
@ApiModel
public class DemandInfoResponse {
	
	@ApiModelProperty(value="区域编码")
	private String name; //区域编码
	@ApiModelProperty(value="区域名称")
	private String value; //区域名称
	
	public DemandInfoResponse(){}

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
}	
