package sijibao.oa.jeesite.modules.intfz.response.expense;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 查询费用科目
 * @author xuhang
 * @time 2017年12月27日 下午3:04:46
 */
@ApiModel(value="费用科目信息")
public class SubInfoResponse {
	
	@ApiModelProperty(value="科目编号")
	private String value;		// 科目id
	@ApiModelProperty(value="父级科目编号")
	private String parent;		// 父级科目id
	@ApiModelProperty(value="科目名称")
	private String name;         // 科目名称
	@ApiModelProperty(value="创建日期")
	private String createTime;	// 创建日期
	@ApiModelProperty(value="科目配置列表")
	private List<SubConfResp> subConfList;
	
	public SubInfoResponse(){}
	public SubInfoResponse(String value, String parent, String name, String createTime,
			List<SubConfResp> subConfList) {
		super();
		this.value = value;
		this.parent = parent;
		this.name = name;
		this.createTime = createTime;
		this.subConfList = subConfList;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public List<SubConfResp> getSubConfList() {
		return subConfList;
	}
	public void setSubConfList(List<SubConfResp> subConfList) {
		this.subConfList = subConfList;
	}

}
