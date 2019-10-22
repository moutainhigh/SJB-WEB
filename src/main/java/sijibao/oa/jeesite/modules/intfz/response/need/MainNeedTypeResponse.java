/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.intfz.response.need;

import java.util.List;

import com.sijibao.oa.modules.sys.entity.User;


/**
 * 协作类型管理Entity
 * @author wanxb
 * @version 2018-11-23
 */
public class MainNeedTypeResponse{
	
	private String id;
	private String typeCode;		// 标签编号
	private String typeName;		// 标签名称
	private List<MainNeedProgressResponse> needProgressList;//进度
	private String delFlag;
	private String isSelectedName;
	
	private User createBy; //创建人
	private User updateBy; //修改人
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTypeCode() {
		return typeCode;
	}
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public List<MainNeedProgressResponse> getNeedProgressList() {
		return needProgressList;
	}
	public void setNeedProgressList(List<MainNeedProgressResponse> needProgressList) {
		this.needProgressList = needProgressList;
	}
	public String getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	public String getIsSelectedName() {
		return isSelectedName;
	}
	public void setIsSelectedName(String isSelectedName) {
		this.isSelectedName = isSelectedName;
	}
	public User getCreateBy() {
		return createBy;
	}
	public void setCreateBy(User createBy) {
		this.createBy = createBy;
	}
	public User getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(User updateBy) {
		this.updateBy = updateBy;
	}
	@Override
	public String toString() {
		return "MainNeedTypeResponse [id=" + id + ", typeCode=" + typeCode + ", typeName=" + typeName
				+ ", needProgressList=" + needProgressList + ", delFlag=" + delFlag + ", isSelectedName="
				+ isSelectedName + ", createBy=" + createBy + ", updateBy=" + updateBy + "]";
	}
	
}