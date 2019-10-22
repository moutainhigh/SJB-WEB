package sijibao.oa.jeesite.modules.intfz.request.contractnew;

import com.sijibao.oa.modules.intfz.request.common.PageRequest;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 合同管理列表查询
 * @author xby
 */
@ApiModel
public class ContractFlowListNewMainReq extends PageRequest{

	@ApiModelProperty(value="合同管理申请ID")
	private String contractFlowId;//合同管理申请ID
	@ApiModelProperty(value="页数")
	private int pageNo;//页数
	@ApiModelProperty(value="每页数量")
	private int pageSize;//每页数量
	@ApiModelProperty(value="流程状态:1审批结束2审批中3单据被驳回4单据保存")
	private String contractFlowStatus;//流程状态
	@ApiModelProperty(value="申请人姓名(模糊匹配)")
	private String applyPerName;
	@ApiModelProperty(value="流程编号/合同号(模糊匹配)")
	private String queryText;
	@ApiModelProperty(value="合同方名称(模糊搜索)")
	private String contractPartyName;
	@ApiModelProperty(value="合同名称ID")
	private String configId;		// 合同名称ID
	@ApiModelProperty(value="合同签约人名称(模糊搜索)")
	private String signLeaderName; //合同签约人名称
	@ApiModelProperty(value="部门名称(模糊搜索)")
	private String officeName; //部门名称
	@ApiModelProperty(value="开始申请时间")
	private long applyTimeStart; //开始申请时间
	@ApiModelProperty(value="结束申请时间")
	private long applyTimeEnd; //结束申请时间
	@ApiModelProperty(value="模糊查询字段")
	private String faint;
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
	public String getQueryText() {
		return queryText;
	}
	public void setQueryText(String queryText) {
		this.queryText = queryText;
	}
	public String getContractPartyName() {
		return contractPartyName;
	}
	public void setContractPartyName(String contractPartyName) {
		this.contractPartyName = contractPartyName;
	}
	
	public String getConfigId() {
		return configId;
	}
	public void setConfigId(String configId) {
		this.configId = configId;
	}
	public String getApplyPerName() {
		return applyPerName;
	}
	public void setApplyPerName(String applyPerName) {
		this.applyPerName = applyPerName;
	}
	public String getSignLeaderName() {
		return signLeaderName;
	}
	public void setSignLeaderName(String signLeaderName) {
		this.signLeaderName = signLeaderName;
	}
	public String getOfficeName() {
		return officeName;
	}
	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}
	public long getApplyTimeStart() {
		return applyTimeStart;
	}
	public void setApplyTimeStart(long applyTimeStart) {
		this.applyTimeStart = applyTimeStart;
	}
	public long getApplyTimeEnd() {
		return applyTimeEnd;
	}
	public void setApplyTimeEnd(long applyTimeEnd) {
		this.applyTimeEnd = applyTimeEnd;
	}
	
	public String getFaint() {
		return faint;
	}
	public void setFaint(String faint) {
		this.faint = faint;
	}
	@Override
	public String toString() {
		return "ContractFlowListNewMainReq [contractFlowId=" + contractFlowId + ", pageNo=" + pageNo + ", pageSize="
				+ pageSize + ", contractFlowStatus=" + contractFlowStatus + ", applyPerName=" + applyPerName
				+ ", queryText=" + queryText + ", contractPartyName=" + contractPartyName + ", configId=" + configId
				+ ", signLeaderName=" + signLeaderName + ", officeName=" + officeName + ", applyTimeStart="
				+ applyTimeStart + ", applyTimeEnd=" + applyTimeEnd + ", faint=" + faint + "]";
	}
}
