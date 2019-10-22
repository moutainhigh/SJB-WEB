/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.oa.entity;

import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.sijibao.oa.common.persistence.DataEntity;

import io.swagger.annotations.ApiModel;

/**
 * 费用科目信息管理Entity
 * @author Petter
 * @version 2017-12-24
 */
@ApiModel
public class ExpensesSubInfo extends DataEntity<ExpensesSubInfo> {
	
	private static final long serialVersionUID = 1L;
	private String subCode;		// 科目编号
	private String parSubCode;		// 父级科目编号
	private String pId;//父级
	private String subName;         // 科目名称
	private String expenseNormal;		// 费用标准
	private String unitType;		// 单位类型
	private String dayCalculation;//天数计算方式：1常规方式，2住宿方式
	
	private String isFirst; //是否一级0否1是
	private String enable;//停用和启用：1停用，0启用

	private List<String> ids;//停用启用需要的ids
	
	public ExpensesSubInfo() {
		super();
	}

	public ExpensesSubInfo(String id){
		super(id);
	}

	public String getSubCode() {
		return subCode;
	}

	public void setSubCode(String subCode) {
		this.subCode = subCode;
	}

	public String getParSubCode() {
		return parSubCode;
	}

	public void setParSubCode(String parSubCode) {
		this.parSubCode = parSubCode;
	}

	public String getSubName() {
		return subName;
	}

	public void setSubName(String subName) {
		this.subName = subName;
	}

	@Length(min=0, max=10, message="费用标准长度必须介于 0 和 10 之间")
	public String getExpenseNormal() {
		return expenseNormal;
	}

	public void setExpenseNormal(String expenseNormal) {
		this.expenseNormal = expenseNormal;
	}
	
	@Length(min=0, max=1, message="单位类型长度必须介于 0 和 1 之间")
	public String getUnitType() {
		return unitType;
	}

	public void setUnitType(String unitType) {
		this.unitType = unitType;
	}

	public String getIsFirst() {
		return isFirst;
	}

	public void setIsFirst(String isFirst) {
		this.isFirst = isFirst;
	}

	public String getEnable() {
		return enable;
	}

	public void setEnable(String enable) {
		this.enable = enable;
	}

	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public String getDayCalculation() {
		return dayCalculation;
	}

	public void setDayCalculation(String dayCalculation) {
		this.dayCalculation = dayCalculation;
	}

	@Override
	public String toString() {
		return "ExpensesSubInfo{" +
				"subCode='" + subCode + '\'' +
				", parSubCode='" + parSubCode + '\'' +
				", pId='" + pId + '\'' +
				", subName='" + subName + '\'' +
				", expenseNormal='" + expenseNormal + '\'' +
				", unitType='" + unitType + '\'' +
				", isFirst='" + isFirst + '\'' +
				", enable='" + enable + '\'' +
				", ids=" + ids +
				'}';
	}
}