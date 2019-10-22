package sijibao.oa.jeesite.modules.intfz.request.intfzwebreq.projectinfo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 项目管理服务--查询项目节点请求对象
 * @author wanxb
 *
 */
@ApiModel(value="项目管理服务--查询项目节点请求对象")
public class MainProjectReq {
	@ApiModelProperty(value="项目id")
	private String projectId;//项目id

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	@Override
	public String toString() {
		return "ProjectReq [projectId=" + projectId + "]";
	}
	
	
}
