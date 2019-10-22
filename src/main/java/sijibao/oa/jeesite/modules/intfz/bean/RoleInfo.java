package sijibao.oa.jeesite.modules.intfz.bean;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
@ApiModel
public class RoleInfo implements Serializable {

	@ApiModelProperty(value="角色名称", required = false)
	private String name;//角色名称

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
