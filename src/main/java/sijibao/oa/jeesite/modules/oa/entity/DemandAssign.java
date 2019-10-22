/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.oa.entity;

import org.hibernate.validator.constraints.Length;

import com.sijibao.oa.common.persistence.DataEntity;

/**
 * 指派记录表实体
 * @author xuby
 * @version 2018-03-28
 */
public class DemandAssign extends DataEntity<DemandAssign> {
	
	private static final long serialVersionUID = 1L;
	private String procCode;		// 流程编号
	private String sourceAssign;		// 指派发起人
	private String sourceAssignPost;		// 指派发起人所属岗位
	private String targetAssign;		// 被指派者
	private String targetAssignPost;		// 被指派者所属岗位
	private String sourceTaskName;		// 发起人流程节点名称
	private String sourceTaskId;		// 发起人流程任务ID
	private String targetTaskName;		// 被指派者任务流程节点名称
	private String targetTaskId;		// 被指派者任务流程任务ID
	private String isLast;		// 是否是最末级受理人
	
	private String procInsId; //流程实例ID 
	public DemandAssign() {
		super();
	}

	public DemandAssign(String id){
		super(id);
	}

	@Length(min=0, max=16, message="流程编号长度必须介于 0 和 16 之间")
	public String getProcCode() {
		return procCode;
	}

	public void setProcCode(String procCode) {
		this.procCode = procCode;
	}
	
	@Length(min=0, max=32, message="指派发起人长度必须介于 0 和 32 之间")
	public String getSourceAssign() {
		return sourceAssign;
	}

	public void setSourceAssign(String sourceAssign) {
		this.sourceAssign = sourceAssign;
	}
	
	@Length(min=0, max=32, message="指派发起人所属岗位长度必须介于 0 和 32 之间")
	public String getSourceAssignPost() {
		return sourceAssignPost;
	}

	public void setSourceAssignPost(String sourceAssignPost) {
		this.sourceAssignPost = sourceAssignPost;
	}
	
	@Length(min=0, max=32, message="被指派者长度必须介于 0 和 32 之间")
	public String getTargetAssign() {
		return targetAssign;
	}

	public void setTargetAssign(String targetAssign) {
		this.targetAssign = targetAssign;
	}
	
	@Length(min=0, max=32, message="被指派者所属岗位长度必须介于 0 和 32 之间")
	public String getTargetAssignPost() {
		return targetAssignPost;
	}

	public void setTargetAssignPost(String targetAssignPost) {
		this.targetAssignPost = targetAssignPost;
	}
	
	@Length(min=0, max=64, message="发起人流程节点名称长度必须介于 0 和 64 之间")
	public String getSourceTaskName() {
		return sourceTaskName;
	}

	public void setSourceTaskName(String sourceTaskName) {
		this.sourceTaskName = sourceTaskName;
	}
	
	@Length(min=0, max=64, message="发起人流程任务ID长度必须介于 0 和 64 之间")
	public String getSourceTaskId() {
		return sourceTaskId;
	}

	public void setSourceTaskId(String sourceTaskId) {
		this.sourceTaskId = sourceTaskId;
	}
	
	@Length(min=0, max=64, message="被指派者任务流程节点名称长度必须介于 0 和 64 之间")
	public String getTargetTaskName() {
		return targetTaskName;
	}

	public void setTargetTaskName(String targetTaskName) {
		this.targetTaskName = targetTaskName;
	}
	
	@Length(min=0, max=64, message="被指派者任务流程任务ID长度必须介于 0 和 64 之间")
	public String getTargetTaskId() {
		return targetTaskId;
	}

	public void setTargetTaskId(String targetTaskId) {
		this.targetTaskId = targetTaskId;
	}
	
	@Length(min=0, max=8, message="是否是最末级受理人长度必须介于 0 和 8 之间")
	public String getIsLast() {
		return isLast;
	}

	public void setIsLast(String isLast) {
		this.isLast = isLast;
	}

	public String getProcInsId() {
		return procInsId;
	}

	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}
	
}