/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.oa.entity;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.sijibao.base.common.provider.entity.PagerBase;
import com.sijibao.oa.common.supcan.annotation.treelist.cols.SupCol;

/**
 * 财务科目表Entity
 * @author wanxb
 * @version 2019-07-09
 */

public class FinanceSubInfo extends PagerBase<FinanceSubInfo> {
	
	private static final long serialVersionUID = 1L;
	private String parId;		// 父id
    private String parName;     //父name
	private String parIds;		// 父ids
	private String subCode;		// 科目编号
	private String parSubCode;		// 父级科目编号
	private String subName;		// 科目名称
	private String conType;		// 控制类别（字典）
	private String createBy;//创建人
	private String updateBy;//修改人
	private String updateByname;//修改人
	private String remarks;//备注

	private List<String> expensesSubIds;//费用科目ids
	private String expensesSubIdStr;//费用科目（多选）拼接字符串
	private String expensesSubNameStr;//费用科目名拼接字符串，以英文逗号分隔
	private List<String> costCenterIds;//成本中心ids

	private String costCenterIdStr;//成本中心（多选）拼接字符串
	private String costCenterNameStr;//成本中心名拼接字符串，以英文逗号分隔
	private String keyWord;//关键字搜索

	private List<String> expensesSubCodes;//费用科目Codes

	public String getParId() {
		return parId;
	}

	public void setParId(String parId) {
		this.parId = parId;
	}

	public String getParIds() {
		return parIds;
	}

	public void setParIds(String parIds) {
		this.parIds = parIds;
	}
	@SupCol(isUnique="true", isHide="false")
	@Length(min=1, max=14, message="最大限制输入14个字符，不可重复；")
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
	@Length(min=1, max=32, message="最大限制输入32字符；")
	@NotNull(message="科目名称不能为空！")
	public String getSubName() {
		return subName;
	}

	public void setSubName(String subName) {
		this.subName = subName;
	}

	public String getConType() {
		return conType;
	}

	public void setConType(String conType) {
		this.conType = conType;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	@Override
	@Length(max=200, message="最大限制输入200字符；")
	public String getRemarks() {
		return remarks;
	}

	@Override
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public String getUpdateByname() {
		return updateByname;
	}

	public void setUpdateByname(String updateByname) {
		this.updateByname = updateByname;
	}

	public String getExpensesSubIdStr() {
		return expensesSubIdStr;
	}

	public void setExpensesSubIdStr(String expensesSubIdStr) {
		this.expensesSubIdStr = expensesSubIdStr;
	}

	public String getExpensesSubNameStr() {
		return expensesSubNameStr;
	}

	public void setExpensesSubNameStr(String expensesSubNameStr) {
		this.expensesSubNameStr = expensesSubNameStr;
	}

	public String getCostCenterIdStr() {
		return costCenterIdStr;
	}

	public void setCostCenterIdStr(String costCenterIdStr) {
		this.costCenterIdStr = costCenterIdStr;
	}

	public String getCostCenterNameStr() {
		return costCenterNameStr;
	}

	public void setCostCenterNameStr(String costCenterNameStr) {
		this.costCenterNameStr = costCenterNameStr;
	}

	public List<String> getExpensesSubCodes() {

		return expensesSubCodes;
	}

	public void setExpensesSubCodes(List<String> expensesSubCodes) {
		this.expensesSubCodes = expensesSubCodes;
	}

    public String getParName() {

        return parName;
    }

    public void setParName(String parName) {
        this.parName = parName;
    }

	public List<String> getExpensesSubIds() {
		return expensesSubIds;
	}

	public void setExpensesSubIds(List<String> expensesSubIds) {
		this.expensesSubIds = expensesSubIds;
	}

	public List<String> getCostCenterIds() {
		return costCenterIds;
	}

	public void setCostCenterIds(List<String> costCenterIds) {
		this.costCenterIds = costCenterIds;
	}

	@Override
    public String toString() {
        return "FinanceSubInfo{" +
                "parId='" + parId + '\'' +
                ", parName='" + parName + '\'' +
                ", parIds='" + parIds + '\'' +
                ", subCode='" + subCode + '\'' +
                ", parSubCode='" + parSubCode + '\'' +
                ", subName='" + subName + '\'' +
                ", conType='" + conType + '\'' +
                ", createBy='" + createBy + '\'' +
                ", updateBy='" + updateBy + '\'' +
                ", updateByname='" + updateByname + '\'' +
                ", remarks='" + remarks + '\'' +
                ", expensesSubIds=" + expensesSubIds +
                ", expensesSubIdStr='" + expensesSubIdStr + '\'' +
                ", expensesSubNameStr='" + expensesSubNameStr + '\'' +
                ", costCenterIds=" + costCenterIds +
                ", costCenterIdStr='" + costCenterIdStr + '\'' +
                ", costCenterNameStr='" + costCenterNameStr + '\'' +
                ", keyWord='" + keyWord + '\'' +
                ", expensesSubCodes=" + expensesSubCodes +
                '}';
    }
}