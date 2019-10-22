package sijibao.oa.jeesite.modules.oa.entity;

import com.sijibao.oa.common.persistence.DataEntity;

public class ReportSpan extends DataEntity<ReportSpan> {
	private static final long serialVersionUID = 1L;
	private String rowCode;//列号
	private String rowName;//列名
	private String rowspan;//列数
	private String colspan;//行数
	
	public String getRowCode() {
		return rowCode;
	}
	public void setRowCode(String rowCode) {
		this.rowCode = rowCode;
	}
	public String getRowName() {
		return rowName;
	}
	public void setRowName(String rowName) {
		this.rowName = rowName;
	}
	public String getRowspan() {
		return rowspan;
	}
	public void setRowspan(String rowspan) {
		this.rowspan = rowspan;
	}
	public String getColspan() {
		return colspan;
	}
	public void setColspan(String colspan) {
		this.colspan = colspan;
	}
	@Override
	public String toString() {
		return "ReportSpan [rowCode=" + rowCode + ", rowName=" + rowName + ", rowspan=" + rowspan + ", colspan="
				+ colspan + "]";
	}
}
