package sijibao.oa.jeesite.modules.intfz.response.openaccountflow;

import java.util.List;

import com.sijibao.oa.common.persistence.ActEntity;
import com.sijibao.oa.modules.flow.entity.OpenAccountAttachment;
import com.sijibao.oa.modules.flow.entity.OpenAccountFlow;
import com.sijibao.oa.modules.intfz.response.common.FlowLogResponse;
import com.sijibao.oa.modules.sys.entity.Office;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 开户申请待办任务列表
 * @author wanxb
 */
@ApiModel(value="开户明细")
public class OpenAccountFlowDetailResponse extends ActEntity<OpenAccountFlowDetailResponse> {
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value="主键 id")
	private String id ;//主键 id
	@ApiModelProperty(value="流程实例ID")
	private String procInsId;		// 流程实例ID
	@ApiModelProperty(value="流程编号")
	private String procCode;		// 流程编号
	@ApiModelProperty(value="流程名称")
	private String procName;		// 流程名称
	@ApiModelProperty(value="申请人编号")
	private String applyPerCode;		// 申请人编号
	@ApiModelProperty(value="申请人名称")
	private String applyPerName;		// 申请人名称
	@ApiModelProperty(value="申请人岗位")
	private String postName; //申请人岗位
	@ApiModelProperty(value="申请时间")
	private long applyTime = 0l;		// 申请时间
	@ApiModelProperty(value=" 所属部门ID")
	private Office office;		// 所属部门ID
	@ApiModelProperty(value="所属部门名称")
	private String officeName;		// 所属部门名称
	@ApiModelProperty(value="开户编号")
	private String openAccountCode;		// 开户编号
	@ApiModelProperty(value="合同编号")
	private String contractCode;		// 合同编号
	@ApiModelProperty(value="合同名称")
	private String contractName;		// 合同名称
	@ApiModelProperty(value="甲方名称")
	private String firstPartyName;		// 甲方名称
	@ApiModelProperty(value="市场负责人id")
	private String marketLeaderId;		// 市场负责人id
	@ApiModelProperty(value="市场负责人Name")
	private String marketLeaderName;		// 市场负责人Name
	@ApiModelProperty(value="开户状态")
	private String openAccountStatus;		// 开户状态
	@ApiModelProperty(value="开户状态name")
	private String openAccountStatusValue;		// 开户状态name
	@ApiModelProperty(value="是否当前人:1是0否")
	private int local;			//当前人:1
	@ApiModelProperty(value="编辑节点")
	private String modify; //编辑节点
	@ApiModelProperty(value="报销流程日志信息")
	private List<FlowLogResponse> flowLoglist;
	@ApiModelProperty(value="当前环节是否可编辑信息(1:允许，0：不允许)")
	private int isDeit;      //是否可编辑信息
	@ApiModelProperty(value="附件信息")
	private List<OpenAccountAttachment> openAccountAttachment;//附件信息
	
	public OpenAccountFlowDetailResponse() {
		super();
	}
	
	public OpenAccountFlowDetailResponse(OpenAccountFlow open){
		this.id = open.getId();
		this.procInsId = open.getProcInsId();
		this.procCode = open.getProcCode();
		this.procName = open.getProcName();
		this.applyPerCode = open.getApplyPerCode();
		this.applyPerName = open.getApplyPerName();
		this.officeName = open.getOffice().getName();
		if(open.getApplyTime() != null){
			this.applyTime = open.getApplyTime().getTime();
		}
		if(open.getOffice() != null){
			this.office = open.getOffice();
		}
		this.openAccountCode = open.getOpenAccountCode();
		this.contractName = open.getContractName();
		this.firstPartyName = open.getFirstPartyName();
		if(open.getMarketLeader() != null){
			this.marketLeaderId = open.getMarketLeader().getId();
			this.marketLeaderName = open.getMarketLeader().getName();
		}
		this.openAccountStatus = open.getOpenAccountStatus();
	}
	
	public OpenAccountFlowDetailResponse(String id){
		super(id);
	}
	public String getOpenAccountCode() {
		return openAccountCode;
	}
	public void setOpenAccountCode(String openAccountCode) {
		this.openAccountCode = openAccountCode;
	}
	public String getContractCode() {
		return contractCode;
	}
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	public String getContractName() {
		return contractName;
	}
	public void setContractName(String contractName) {
		this.contractName = contractName;
	}
	public String getFirstPartyName() {
		return firstPartyName;
	}
	public void setFirstPartyName(String firstPartyName) {
		this.firstPartyName = firstPartyName;
	}
	public String getMarketLeaderId() {
		return marketLeaderId;
	}
	public void setMarketLeaderId(String marketLeaderId) {
		this.marketLeaderId = marketLeaderId;
	}
	public String getOpenAccountStatus() {
		return openAccountStatus;
	}
	public void setOpenAccountStatus(String openAccountStatus) {
		this.openAccountStatus = openAccountStatus;
	}
	
	public List<FlowLogResponse> getFlowLoglist() {
		return flowLoglist;
	}
	public void setFlowLoglist(List<FlowLogResponse> flowLoglist) {
		this.flowLoglist = flowLoglist;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProcInsId() {
		return procInsId;
	}

	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
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

	public String getApplyPerCode() {
		return applyPerCode;
	}

	public void setApplyPerCode(String applyPerCode) {
		this.applyPerCode = applyPerCode;
	}

	public String getApplyPerName() {
		return applyPerName;
	}

	public void setApplyPerName(String applyPerName) {
		this.applyPerName = applyPerName;
	}


	public long getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(long applyTime) {
		this.applyTime = applyTime;
	}

	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}

	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

	public String getPostName() {
		return postName;
	}

	public void setPostName(String postName) {
		this.postName = postName;
	}

	public String getMarketLeaderName() {
		return marketLeaderName;
	}

	public void setMarketLeaderName(String marketLeaderName) {
		this.marketLeaderName = marketLeaderName;
	}

	public int getLocal() {
		return local;
	}

	public void setLocal(int local) {
		this.local = local;
	}

	public int getIsDeit() {
		return isDeit;
	}

	public void setIsDeit(int isDeit) {
		this.isDeit = isDeit;
	}

	public String getModify() {
		return modify;
	}

	public void setModify(String modify) {
		this.modify = modify;
	}

	public List<OpenAccountAttachment> getOpenAccountAttachment() {
		return openAccountAttachment;
	}

	public void setOpenAccountAttachment(List<OpenAccountAttachment> openAccountAttachment) {
		this.openAccountAttachment = openAccountAttachment;
	}

	@Override
	public String toString() {
		return "OpenAccountFlowDetailResponse [id=" + id + ", procInsId=" + procInsId + ", procCode=" + procCode
				+ ", procName=" + procName + ", applyPerCode=" + applyPerCode + ", applyPerName=" + applyPerName
				+ ", postName=" + postName + ", applyTime=" + applyTime + ", office=" + office + ", officeName="
				+ officeName + ", openAccountCode=" + openAccountCode + ", contractCode=" + contractCode
				+ ", contractName=" + contractName + ", firstPartyName=" + firstPartyName + ", marketLeaderId="
				+ marketLeaderId + ", marketLeaderName=" + marketLeaderName + ", openAccountStatus=" + openAccountStatus
				+ ", local=" + local + ", modify=" + modify + ", flowLoglist=" + flowLoglist + ", isDeit=" + isDeit
				+ ", openAccountAttachment=" + openAccountAttachment + "]";
	}
}
