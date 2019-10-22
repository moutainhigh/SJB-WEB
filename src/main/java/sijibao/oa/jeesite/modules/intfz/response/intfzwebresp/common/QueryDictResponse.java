package sijibao.oa.jeesite.modules.intfz.response.intfzwebresp.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 字典查询返回实体
 * @author wanxb
 *
 */
@ApiModel
public class QueryDictResponse {
	@ApiModelProperty(value="类型")
	private String type; //名称
	@ApiModelProperty(value="名称")
	private String name; //名称
	@ApiModelProperty(value="编码")
	private String value; //编码
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
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
	@Override
	public String toString() {
		return "QueryDictResponse [type=" + type + ", name=" + name + ", value=" + value + "]";
	}
	
}	
