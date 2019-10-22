package sijibao.oa.jeesite.modules.intfz.request.contract;

import java.io.Serializable;

import com.sijibao.base.common.api.resquest.PagerBaseRequest;

/**
 * WEB合同后台添加-请求对象
 * @author wanxb
 * 2018-10-22 14:32:24
 */
public class MainProjectContractHisReq extends PagerBaseRequest implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String projectId;
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	@Override
	public String toString() {
		return "MainProjectContractHisReq [projectId=" + projectId + "]";
	}
	
	
}
