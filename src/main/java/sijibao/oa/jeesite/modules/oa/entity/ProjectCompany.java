/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.oa.entity;

import org.hibernate.validator.constraints.Length;

import com.sijibao.oa.common.persistence.DataEntity;

/**
 * 企业与项目关联关系Entity
 * @author wanxb
 * @version 2018-09-29
 */
public class ProjectCompany extends DataEntity<ProjectCompany> {
	
	private static final long serialVersionUID = 1L;
	private String projectId;		// 项目id
	private String projectName;		// 项目name
	private String companyCode;		// 企业编号
	private String companyName;		// 企业name
	private String holderCode;		//账户编号
	
	public ProjectCompany() {
		super();
	}

	public ProjectCompany(String id){
		super(id);
	}

	@Length(min=1, max=32, message="项目id长度必须介于 1 和 32 之间")
	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	
	@Length(min=1, max=64, message="项目name长度必须介于 1 和 64 之间")
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	@Length(min=1, max=32, message="企业编号长度必须介于 1 和 32 之间")
	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	
	@Length(min=1, max=64, message="企业name长度必须介于 1 和 64 之间")
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getHolderCode() {
		return holderCode;
	}

	public void setHolderCode(String holderCode) {
		this.holderCode = holderCode;
	}

	@Override
	public String toString() {
		return "ProjectCompany [projectId=" + projectId + ", projectName=" + projectName + ", companyCode="
				+ companyCode + ", companyName=" + companyName + ", holderCode=" + holderCode + "]";
	}

	
	
}