package sijibao.oa.jeesite.modules.intfz.response.expense;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 科目配置信息 
 * @author Petter
 */
@ApiModel(value="科目配置信息")
public class SubConfResp {

	@ApiModelProperty(value="配置类型：0附件上传、1下拉选择、2文本输入")
	private String confType;
	@ApiModelProperty(value="字典类型，当配置类型为下拉选择时必填")
	private String dictType;
	@ApiModelProperty(value="配置描述")
	private String confDesc;
	@ApiModelProperty(value="是否必填")
	private String isRequired;
	@ApiModelProperty(value="序号，排序时使用")
	private int sort;
	@ApiModelProperty(value="id，主键ID")
	private String id;
	public SubConfResp(){}
	public SubConfResp(String confType, String dictType, String confDesc, String isRequired, int sort,String id) {
		super();
		this.confType = confType;
		this.dictType = dictType;
		this.confDesc = confDesc;
		this.isRequired = isRequired;
		this.sort = sort;
		this.id = id;
	}
	public String getConfType() {
		return confType;
	}
	public void setConfType(String confType) {
		this.confType = confType;
	}
	public String getDictType() {
		return dictType;
	}
	public void setDictType(String dictType) {
		this.dictType = dictType;
	}
	public String getConfDesc() {
		return confDesc;
	}
	public void setConfDesc(String confDesc) {
		this.confDesc = confDesc;
	}
	public String getIsRequired() {
		return isRequired;
	}
	public void setIsRequired(String isRequired) {
		this.isRequired = isRequired;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
}
