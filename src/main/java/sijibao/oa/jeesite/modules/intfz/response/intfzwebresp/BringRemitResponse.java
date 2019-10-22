package sijibao.oa.jeesite.modules.intfz.response.intfzwebresp;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModelProperty;

/**
 * 返回提前打款信息
 * @author wanxb
 *
 */
public class BringRemitResponse {
	@ApiModelProperty(value="报销id")
	private String id ;		//报销id
	@ApiModelProperty(value="流程编号")
	private String procCode;		// 流程编号
	@ApiModelProperty(value="流程名称")
	private String procName;		// 流程名称
	@ApiModelProperty(value="申请人名称")
	private String applyPerName;		// 申请人名称
	@ApiModelProperty(value="申请时间")
	private String applyTime;		// 申请时间
	@ApiModelProperty(value="费用合计")
	private BigDecimal expenseTotal;		// 费用合计
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProcCode() {
		return procCode;
	}
	public void setProcCode(String procCode) {
		this.procCode = procCode;
	}
	public String getProcName() {
		return procName;
	}
	public void setProcName(String procName) {
		this.procName = procName;
	}
	public String getApplyPerName() {
		return applyPerName;
	}
	public void setApplyPerName(String applyPerName) {
		this.applyPerName = applyPerName;
	}

	public String getApplyTime() {
		return applyTime;
	}
	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
	}
	public BigDecimal getExpenseTotal() {
		return expenseTotal;
	}
	public void setExpenseTotal(BigDecimal expenseTotal) {
		this.expenseTotal = expenseTotal;
	}
	@Override
	public String toString() {
		return "BringRemitResponse [procCode=" + procCode + ", procName=" + procName + ", applyPerName=" + applyPerName
				+ ", applyTime=" + applyTime + ", expenseTotal=" + expenseTotal + "]";
	}
	
	
	
}
