/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.oa.entity;

import com.sijibao.oa.common.persistence.DataEntity;

import io.swagger.annotations.ApiModel;

/**
 * 费用科目配置管理Entity
 * @author Petter
 * @version 2018-03-29
 */
@ApiModel
public class ExpensesSubConf extends DataEntity<ExpensesSubConf> {
	
	private static final long serialVersionUID = 1L;
	private String subCode;		// 科目编号
	private String subName;         // 科目名称
	private String dictType;    //字典类型
	private String confType;    //配置类型
	private String confDesc;    //配置描述
	private String isRequired;   //是否必填
	private Integer sort; 	// 排序
    private String sCode;//科目编号

    public String getsCode() {
        return sCode;
    }

    public void setsCode(String sCode) {
        this.sCode = sCode;
    }

    public ExpensesSubConf() {
		super();
	}
	public ExpensesSubConf(String id){
		super(id);
	}
	public String getSubCode() {
		return subCode;
	}
	public void setSubCode(String subCode) {
		this.subCode = subCode;
	}
	public String getSubName() {
		return subName;
	}
	public void setSubName(String subName) {
		this.subName = subName;
	}
	public String getDictType() {
		return dictType;
	}
	public void setDictType(String dictType) {
		this.dictType = dictType;
	}
	public String getConfType() {
		return confType;
	}
	public void setConfType(String confType) {
		this.confType = confType;
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
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	@Override
	public String toString() {
		return "ExpensesSubConf [subCode=" + subCode + ", subName=" + subName + ", dictType=" + dictType + ", confType="
				+ confType + ", confDesc=" + confDesc + ", isRequired=" + isRequired + ", sort=" + sort + "]";
	}
	
}