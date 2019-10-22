package sijibao.oa.jeesite.modules.intfz.request.contract;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 合同归档废弃请求对象
 * @author wanxb
 * 2018-10-22 14:33:15
 */
@ApiModel(value="WEB合同归档服务-续签请求对象")
public class MainContractHisRenewalReq  {
	
	@ApiModelProperty(value="归档合同id")
	private String contractHisId;  //归档合同id
	@ApiModelProperty(value="合同结束日期")
	private long contractEndTime = 0l;		// 合同结束日期
	@ApiModelProperty(value="合同附件信息")
	private List<MainContractAttachmentRequest> contractAttachmentRequest; //合同附件信息

	public List<MainContractAttachmentRequest> getContractAttachmentRequest() {
		return contractAttachmentRequest;
	}

	public void setContractAttachmentRequest(List<MainContractAttachmentRequest> contractAttachmentRequest) {
		this.contractAttachmentRequest = contractAttachmentRequest;
	}


	public long getContractEndTime() {
		return contractEndTime;
	}

	public void setContractEndTime(long contractEndTime) {
		this.contractEndTime = contractEndTime;
	}

	public String getContractHisId() {
		return contractHisId;
	}

	public void setContractHisId(String contractHisId) {
		this.contractHisId = contractHisId;
	}

	@Override
	public String toString() {
		return "MainContractHisRenewalReq [contractHisId=" + contractHisId + ", contractEndTime=" + contractEndTime
				+ ", contractAttachmentRequest=" + contractAttachmentRequest + "]";
	}
	
	
}
