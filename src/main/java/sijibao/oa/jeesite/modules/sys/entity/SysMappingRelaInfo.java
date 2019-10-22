package sijibao.oa.jeesite.modules.sys.entity;

import org.hibernate.validator.constraints.Length;

import com.sijibao.oa.common.persistence.DataEntity;

/**
 * 映射关系信息管理Entity
 * @author Petter
 * @version 2016-03-07
 */
public class SysMappingRelaInfo extends DataEntity<SysMappingRelaInfo> {
	
	private static final long serialVersionUID = 1L;
	private String mappingType;		// 映射类型
	private String mappingName;		// 映射名称
	private String mappingValue;		// 映射值
	private String mappingToName;		// 映射后对应名称
	private String mappingToValue;		// 映射后对应值
	private String mappingAttr;		// 映射扩展字段
	
	public SysMappingRelaInfo() {
		super();
	}

	public SysMappingRelaInfo(String id){
		super(id);
	}

	@Length(min=0, max=32, message="映射类型长度必须介于 0 和 32 之间")
	public String getMappingType() {
		return mappingType;
	}

	public void setMappingType(String mappingType) {
		this.mappingType = mappingType;
	}
	
	@Length(min=0, max=100, message="映射名称长度必须介于 0 和 100 之间")
	public String getMappingName() {
		return mappingName;
	}

	public void setMappingName(String mappingName) {
		this.mappingName = mappingName;
	}
	
	@Length(min=0, max=100, message="映射值长度必须介于 0 和 100 之间")
	public String getMappingValue() {
		return mappingValue;
	}

	public void setMappingValue(String mappingValue) {
		this.mappingValue = mappingValue;
	}
	
	@Length(min=0, max=100, message="映射后对应名称长度必须介于 0 和 100 之间")
	public String getMappingToName() {
		return mappingToName;
	}

	public void setMappingToName(String mappingToName) {
		this.mappingToName = mappingToName;
	}
	
	@Length(min=0, max=100, message="映射后对应值长度必须介于 0 和 100 之间")
	public String getMappingToValue() {
		return mappingToValue;
	}

	public void setMappingToValue(String mappingToValue) {
		this.mappingToValue = mappingToValue;
	}
	
	@Length(min=0, max=32, message="映射扩展字段长度必须介于 0 和 32 之间")
	public String getMappingAttr() {
		return mappingAttr;
	}

	public void setMappingAttr(String mappingAttr) {
		this.mappingAttr = mappingAttr;
	}
	
}