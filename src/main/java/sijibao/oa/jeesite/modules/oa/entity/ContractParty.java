package sijibao.oa.jeesite.modules.oa.entity;

/**
 * 合同方配置信息放回对象
 * @author xby
 * @version 2018-10-23
 */
public class ContractParty {
	


	private String memberType; //合同方类型：1甲方，2乙方，3丙方
	
	private String columnLabel; //字段文案
	
	private String columnName; //字段名称
	
	private String columnType; //字段类型(text:文本,select:下拉)
	
	private Integer readonly; //是否只读(0:否，1:是)
	
	private Integer required; //是否必填(0:否，1：是)
	
	private String url; //接口请求url，当合同方为下拉时才有
	
	private String value;  //值

	public String getMemberType() {
		return memberType;
	}

	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}

	public String getColumnLabel() {
		return columnLabel;
	}

	public void setColumnLabel(String columnLabel) {
		this.columnLabel = columnLabel;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getColumnType() {
		return columnType;
	}

	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}

	public Integer getReadonly() {
		return readonly;
	}

	public void setReadonly(Integer readonly) {
		this.readonly = readonly;
	}

	public Integer getRequired() {
		return required;
	}

	public void setRequired(Integer required) {
		this.required = required;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "ContractParty [memberType=" + memberType + ", columnLabel=" + columnLabel + ", columnName=" + columnName
				+ ", columnType=" + columnType + ", readonly=" + readonly + ", required=" + required + ", url=" + url
				+ ", value=" + value + "]";
	}
	
	
}
