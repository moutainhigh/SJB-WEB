package sijibao.oa.jeesite.modules.sys.entity;

import org.hibernate.validator.constraints.Length;

import com.sijibao.oa.common.persistence.DataEntity;


/**
 * 系统参数管理Entity
 */
@SuppressWarnings("serial")
public class SysParams extends DataEntity<SysParams> {
	
	private String paramName;		// 参数名称
	private String paramValue;		// 参数值
	private String paramType;		// 参数类型
	private String isModify;		// 是否可修改
	
	public SysParams() {
		super();
	}

	public SysParams(String id){
		super(id);
	}

	@Length(min=0, max=100, message="参数中文名长度必须介于 0 和 100 之间")
	public String getParamName() {
		return paramName;
	}
	
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}
	
	@Length(min=0, max=255, message="参数值长度必须介于 0 和 255 之间")
	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}
	
	@Length(min=0, max=30, message="参数类型长度必须介于 0 和 30 之间")
	public String getParamType() {
		return paramType;
	}

	public void setParamType(String paramType) {
		this.paramType = paramType;
	}
	
	@Length(min=0, max=1, message="是否可修改长度必须介于 0 和 1 之间")
	public String getIsModify() {
		return isModify;
	}

	public void setIsModify(String isModify) {
		this.isModify = isModify;
	}
	
}