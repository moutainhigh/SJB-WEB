/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.sys.entity;

import com.sijibao.oa.common.persistence.DataEntity;

/**
 * 职级Entity
 * @author huangkai
 * @version 2019-01-03
 */
public class JobLevel extends DataEntity<JobLevel> {

	private static final long serialVersionUID = 1L;
	private String id;		// id
	private String levelCode;		// 职级编号
	private String levelName;		// 职级名称
	private String description;		// 职级说明
	private String remark;		// 备注
	private String userNames;   //职级人员
	private String updateByName;   //更新人



	public JobLevel() {
		super();
	}

	public JobLevel(String id){
		super(id);
	}

	@Override
	public String toString() {
		return "JobLevelResponse [id=" + id + ", levelCode=" + levelCode + ", levelName=" + levelName
				+ ", description=" + description + ", remark=" + remark + ", userNames="
				+ userNames + ", updateByName=" + updateByName
				+  "]";
	}


	public String getLevelCode() {
		return levelCode;
	}

	public void setLevelCode(String levelCode) {
		this.levelCode = levelCode;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUserNames() {
		return userNames;
	}

	public void setUserNames(String userNames) {
		this.userNames = userNames;
	}






	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUpdateByName() {
		return updateByName;
	}

	public void setUpdateByName(String updateByName) {
		this.updateByName = updateByName;
	}

}