package sijibao.oa.jeesite.modules.intfz.request.contract;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 合同管理列表查询
 * @author xby
 */
@ApiModel
public class ContractFlowListReq {

	@ApiModelProperty(value="合同管理申请ID")
	private String contractFlowId;//合同管理申请ID
	@ApiModelProperty(value="页数")
	private int pageNo;//页数
	@ApiModelProperty(value="每页数量")
	private int pageSize;//每页数量
	@ApiModelProperty(value="流程状态:1审批结束2审批中3单据被驳回4单据保存")
	private String contractFlowStatus;//流程状态
	@ApiModelProperty(value="申请人姓名(模糊匹配)")
	private String applyName;
	@ApiModelProperty(value="流程编号(模糊匹配)")
	private String procCode;
	@ApiModelProperty(value="合同编号")
	private String contractCode;
	@ApiModelProperty(value="公司名称")
	private String companyName;
	@ApiModelProperty(value="用印日期开始(格式：时间戳)")
	private long printingTimeStart;
	@ApiModelProperty(value="用印日期结束(格式：时间戳)")
	private long printingTimeEnd;
	@ApiModelProperty(value="合同名称编码")
	private String contractNameCode;		// 合同名称编码
	
	public String getContractFlowId() {
		return contractFlowId;
	}
	public void setContractFlowId(String contractFlowId) {
		this.contractFlowId = contractFlowId;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public String getContractFlowStatus() {
		return contractFlowStatus;
	}
	public void setContractFlowStatus(String contractFlowStatus) {
		this.contractFlowStatus = contractFlowStatus;
	}
	public String getApplyName() {
		return applyName;
	}
	public void setApplyName(String applyName) {
		this.applyName = applyName;
	}
	public String getProcCode() {
		return procCode;
	}
	public void setProcCode(String procCode) {
		this.procCode = procCode;
	}
	public String getContractCode() {
		return contractCode;
	}
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public long getPrintingTimeStart() {
		return printingTimeStart;
	}
	public void setPrintingTimeStart(long printingTimeStart) {
		this.printingTimeStart = printingTimeStart;
	}
	public long getPrintingTimeEnd() {
		return printingTimeEnd;
	}
	public void setPrintingTimeEnd(long printingTimeEnd) {
		this.printingTimeEnd = printingTimeEnd;
	}
	public String getContractNameCode() {
		return contractNameCode;
	}
	public void setContractNameCode(String contractNameCode) {
		this.contractNameCode = contractNameCode;
	}
	@Override
	public String toString() {
		return "ContractFlowListReq [contractFlowId=" + contractFlowId + ", pageNo=" + pageNo + ", pageSize=" + pageSize
				+ ", contractFlowStatus=" + contractFlowStatus + ", applyName=" + applyName + ", procCode=" + procCode
				+ ", contractCode=" + contractCode + ", companyName=" + companyName + ", printingTimeStart="
				+ printingTimeStart + ", printingTimeEnd=" + printingTimeEnd + ", contractNameCode=" + contractNameCode
				+ "]";
	}
}
