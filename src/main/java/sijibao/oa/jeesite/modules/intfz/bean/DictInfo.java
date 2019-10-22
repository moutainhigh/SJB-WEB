/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.intfz.bean;

import com.sijibao.oa.modules.oa.entity.ProjectInfo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 字典Entity
 * @author ThinkGem
 * @version 2013-05-15
 */
@ApiModel(value="字典信息对象")
public class DictInfo {

	@ApiModelProperty(value="数据值，传说中的value")
	private String value;	// 数据值
	@ApiModelProperty(value="标签名，传说中的key")
	private String label;	// 标签名
	@ApiModelProperty(value="字典类型")
	private String dictType;	// 类型
	@ApiModelProperty(value="字典描述")
	private String description;// 描述
	@ApiModelProperty(value="排序号")
	private Integer sort;	// 排序
	@ApiModelProperty(value="父节点")
	private String parentId;//父Id
	@ApiModelProperty(value="项目类型0:公司项目,1:市场项目")
	private String projectType;//项目类型0公司项目1市场项目
	@ApiModelProperty(value="项目负责人姓名")
	private String projectLeaderName;//项目负责人名称
	@ApiModelProperty(value="项目负责人编码")
	private String projectLeaderCode;//项目负责人编码
	@ApiModelProperty(value="所属区域名称")
	private String areaName; //所属区域名称
	@ApiModelProperty(value="所属区域编码")
	private String areaCode; //所属区域编码
	@ApiModelProperty(value="项目状态0-待上线,1：已上线,2:已关闭")
	private String projectState;//项目状态0待上线1已上线2已关闭
	
	@ApiModelProperty(value="项目名称")
	private String projectName;//项目名称
	public DictInfo() {
		super();
	}
	public DictInfo(String value, String label, String dictType, String description, Integer sort, String parentId){
		this.value = value;
		this.label = label;
		this.dictType = dictType;
		this.description = description;
		this.sort = sort;
		this.parentId = parentId;
	}
	
	public DictInfo(String value, String label, String dictType, String description, Integer sort, String parentId,ProjectInfo projectInfo){
		this.value = value;
		this.label = label;
		this.dictType = dictType;
		this.description = description;
		this.sort = sort;
		this.parentId = parentId;
		this.projectType = projectInfo.getProjectType();
		if(projectInfo.getProjectLeader() != null){
			this.projectLeaderName = projectInfo.getProjectLeader().getName();
			this.projectLeaderCode = projectInfo.getProjectLeader().getId();
		}
//		this.areaCode = projectInfo.getArea().getId();
//		this.areaName = projectInfo.getArea().getName();
		this.projectState = projectInfo.getProjectState();
	}
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getDictType() {
		return dictType;
	}
	public void setDictType(String dictType) {
		this.dictType = dictType;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getProjectType() {
		return projectType;
	}
	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}
	public String getProjectLeaderName() {
		return projectLeaderName;
	}
	public void setProjectLeaderName(String projectLeaderName) {
		this.projectLeaderName = projectLeaderName;
	}
	public String getProjectLeaderCode() {
		return projectLeaderCode;
	}
	public void setProjectLeaderCode(String projectLeaderCode) {
		this.projectLeaderCode = projectLeaderCode;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getProjectState() {
		return projectState;
	}
	public void setProjectState(String projectState) {
		this.projectState = projectState;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	
}