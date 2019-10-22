package sijibao.oa.jeesite.modules.intfz.response.contract;

import java.io.Serializable;


/**
 * 合同归档详情返回对象
 * @author wxb
 * @time 2018-10-22 11:00:50
 */
public class MainContractHisDataResponse implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MainContractHisDetailResponse contractHisDetailResponse;
	public MainContractHisDetailResponse getContractHisDetailResponse() {
		return contractHisDetailResponse;
	}
	public void setContractHisDetailResponse(MainContractHisDetailResponse contractHisDetailResponse) {
		this.contractHisDetailResponse = contractHisDetailResponse;
	}
	@Override
	public String toString() {
		return "MainContractHisDataResponse [contractHisDetailResponse=" + contractHisDetailResponse + "]";
	}

	
}
