/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.oa.entity;

import java.util.Date;

import com.sijibao.oa.common.persistence.TreeEntity;

/**
 * 消耗品类别管理Entity
 * @author wanxb
 * @version 2019-03-16
 */
public class ConsumablesType extends TreeEntity<ConsumablesType> {
	
	private static final long serialVersionUID = 1L;
	private String ctCode;		// 分类编号
	private String ctName;		// 分类名称
	private String parentName;
	private ConsumablesType parent;		// 父级id
	private String parentIds;		// 所有父级id
	private Date createTime;		// 创建时间
	private Date updateTime;		// 更新时间
	
	public ConsumablesType() {
		super();
	}

	public ConsumablesType(String id){
		super(id);
	}

	public String getCtCode() {
		return ctCode;
	}

	public void setCtCode(String ctCode) {
		this.ctCode = ctCode;
	}
	
	public String getCtName() {
		return ctName;
	}

	public void setCtName(String ctName) {
		this.ctName = ctName;
	}
	

	
	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}
	
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	@Override
	public ConsumablesType getParent() {
		return parent;
	}

	@Override
	public void setParent(ConsumablesType parent) {
		this.parent = parent;
	}

	@Override
	public String toString() {
		return "ConsumablesType{" +
				"ctCode='" + ctCode + '\'' +
				", ctName='" + ctName + '\'' +
				", parent=" + parent +
				", parentIds='" + parentIds + '\'' +
				", createTime=" + createTime +
				", updateTime=" + updateTime +
				'}';
	}
}