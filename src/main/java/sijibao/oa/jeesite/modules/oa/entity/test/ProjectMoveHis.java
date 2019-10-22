/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.oa.entity.test;

import com.sijibao.oa.common.persistence.DataEntity;

/**
 * 11Entity
 * @author wanxb
 * @version 2019-01-24
 */

public class ProjectMoveHis extends DataEntity<ProjectMoveHis> {
	
	private static final long serialVersionUID = 1L;
	private String pId;//id
	private String moveCode;			//编号
	private String projectLeaderId;		// 项目负责人id
	private String marketLeaderId;		// 市场负责人id
	private String impleLeaderId;		// 实施负责人id
	private String projectId;		// 所选项目ids


	private String projectIds;		// 所选项目ids

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public String getMoveCode() {
		return moveCode;
	}

	public void setMoveCode(String moveCode) {
		this.moveCode = moveCode;
	}

	public String getProjectLeaderId() {
		return projectLeaderId;
	}

	public void setProjectLeaderId(String projectLeaderId) {
		this.projectLeaderId = projectLeaderId;
	}

	public String getMarketLeaderId() {
		return marketLeaderId;
	}

	public void setMarketLeaderId(String marketLeaderId) {
		this.marketLeaderId = marketLeaderId;
	}

	public String getImpleLeaderId() {
		return impleLeaderId;
	}

	public void setImpleLeaderId(String impleLeaderId) {
		this.impleLeaderId = impleLeaderId;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getProjectIds() {
		return projectIds;
	}

	public void setProjectIds(String projectIds) {
		this.projectIds = projectIds;
	}

	@Override
	public String toString() {
		return "ProjectMoveHis{" +
				"pId='" + pId + '\'' +
				", moveCode='" + moveCode + '\'' +
				", projectLeaderId='" + projectLeaderId + '\'' +
				", marketLeaderId='" + marketLeaderId + '\'' +
				", impleLeaderId='" + impleLeaderId + '\'' +
				", projectId='" + projectId + '\'' +
				", updateBy='" + updateBy + '\'' +
				", createBy='" + createBy + '\'' +
				", projectIds='" + projectIds + '\'' +
				'}';
	}
}