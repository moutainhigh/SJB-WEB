/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.intfz.response.need;

import com.sijibao.oa.modules.sys.entity.User;

/**
 * 协作进度Entity
 * @author wanxb
 * @version 2018-11-23
 */
public class MainNeedProgressResponse{
	
	private String typeId;		// 类型id
	private String progressName;		// 进度名称
	private String isSelected;		// 默认状态：0否，1是
	private String isEnd;		// 流程结束状态：0否，1是
	
	private User createBy; //创建人

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

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

	public User getCreateBy() {
		return createBy;
	}

	public void setCreateBy(User createBy) {
		this.createBy = createBy;
	}

	@Override
	public String toString() {
		return "NeedProgressResponse [typeId=" + typeId + ", progressName=" + progressName + ", isSelected="
				+ isSelected + ", isEnd=" + isEnd + ", createBy=" + createBy + "]";
	}
	
}