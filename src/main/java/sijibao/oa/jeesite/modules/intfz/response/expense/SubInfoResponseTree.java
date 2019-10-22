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
public class SubInfoResponseTree {
	
	@ApiModelProperty(value="科目id")
	private String value;		// 科目编号
	@ApiModelProperty(value="科目名称")
	private String label;         // 科目名称
	@ApiModelProperty(value="子级科目")
	private List<SubInfoResponseTree> children;
	@ApiModelProperty(value="科目配置列表")
	private List<SubConfResp> subConfList;
	
	public SubInfoResponseTree(){}
	public SubInfoResponseTree(String value, String label,List<SubConfResp> subConfList) {
		super();
		this.value = value;
		this.label = label;
		this.subConfList = subConfList;
	}
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public List<SubConfResp> getSubConfList() {
		return subConfList;
	}
	public void setSubConfList(List<SubConfResp> subConfList) {
		this.subConfList = subConfList;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public List<SubInfoResponseTree> getChildren() {
		return children;
	}
	public void setChildren(List<SubInfoResponseTree> children) {
		this.children = children;
	}
}
