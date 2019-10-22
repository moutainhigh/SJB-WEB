/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.oa.entity;

import org.hibernate.validator.constraints.Length;

import com.sijibao.oa.common.persistence.DataEntity;

/**
 * 协作进度Entity
 * @author wanxb
 * @version 2018-11-23
 */
public class NeedProgress extends DataEntity<NeedProgress> {
	
	private static final long serialVersionUID = 1L;
	private String typeId;		// 类型id
	private String progressName;		// 进度名称
	private String isSelected;		// 默认状态：0否，1是
	private String isEnd;		// 流程结束状态：0否，1是
	
	public NeedProgress() {
		super();
	}

	public NeedProgress(String id){
		super(id);
	}

	@Length(min=1, max=32, message="类型id长度必须介于 1 和 32 之间")
	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	
	@Length(min=1, max=64, message="进度名称长度必须介于 1 和 64 之间")
	public String getProgressName() {
		return progressName;
	}

	public void setProgressName(String progressName) {
		this.progressName = progressName;
	}
	
	public String getIsSelected() {
		return isSelected;
	}

	public void setIsSelected(String isSelected) {
		this.isSelected = isSelected;
	}
	
	public String getIsEnd() {
		return isEnd;
	}

	public void setIsEnd(String isEnd) {
		this.isEnd = isEnd;
	}
	
}