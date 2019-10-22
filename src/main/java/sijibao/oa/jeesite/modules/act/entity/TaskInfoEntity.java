package sijibao.oa.jeesite.modules.act.entity;

import java.util.Date;

import lombok.Data;
import sijibao.oa.jeesite.comment.BaseEntity;
import sijibao.oa.jeesite.modules.sys.entity.User;

/**
 * 工作流对象
 * @author Petter
 *
 */
@Data
public class TaskInfoEntity extends BaseEntity<TaskInfoEntity> {
	private static final long serialVersionUID = 1L;
	
	private String id;  //taskId 任务ID
	private String executionId; //执行ID
	private String processInstanceId; //流程实例ID
	private String processDefinitionId; //流程定义ID
	private String taskDefinitionKey; //节点Key
	private Date createTime; //任务发起时间
	private String taskName; //节点名称
	private String assignee; //执行者code
	private String assigneeName; //执行者名称
	private String title; //任务标题
	private String procDefName; //流程名称
	private String version; //流程版本
	private String status; //任务状态
	private Date completedTime; //任务结束时间
	private String businessId; //报销业务员ID
	private String officeId; //归属部门编码
	private String officeName; //归属部门名称
	private Date beginDate;
	private Date endDate;
	private Date beginApplyTime;//申请开始时间
	private Date endApplyTime;//申请结束时间
	private String procCode;
	private String applyPerName;
	private String expenseTotal;
	private String remarks;
	private String taxCityCode;
	private String procKey; //流程标识
	private String businessStatus; //业务状态
	private String businessTable; //业务表名
	private String businessStatusColumn;  //业务状态字段名
	private String gateGory; //流程字典值
	
	//已办列表查询当前审批人
	private String approvalAssigneeId;
	public TaskInfoEntity() {
		super();
	}

	@Override
	public void preInsert() {

	}

	@Override
	public void preUpdate() {

	}
}
