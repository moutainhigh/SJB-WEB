package sijibao.oa.jeesite.modules.intfz.response.contractnew;

import java.io.Serializable;

/**
 * 合同详情展示返回对象
 * @author xby
 * @version 2018-11-12
 */
public class ContractFlowDetailProjectNewMainResponse implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String projectId; //项目ID
	
	private String projectName; //项目名称

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	@Override
	public String toString() {
		return "ContractFlowDetailProjectNewMainResponse [projectId=" + projectId + ", projectName=" + projectName
				+ "]";
	}
}
