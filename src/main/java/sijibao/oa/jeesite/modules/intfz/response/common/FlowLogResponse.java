package sijibao.oa.jeesite.modules.intfz.response.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 待办任务列表
 * @author xuhang
 * @time 2017年12月27日 下午3:04:46
 */
@ApiModel(value="报销流程日志信息")
public class FlowLogResponse {
	
	@ApiModelProperty(value="任务环节名称")
	private String activityName;	//执行环节
	@ApiModelProperty(value="任务环节执行人")
	private String assigneeName;	//执行人
	@ApiModelProperty(value="任务环节开始时间")
	private String startTime;		//开始时间
	@ApiModelProperty(value="任务环节结束时间")
	private String endTime;			//结束时间
	@ApiModelProperty(value="提交日志信息")
	private String comment;		//提交日志
	@ApiModelProperty(value="任务环节历时")
	private String durationTime;		//任务历时
	
	public FlowLogResponse(){}
	public FlowLogResponse(String activityName, String assigneeName, String startTime, String endTime, String comment,
			String durationTime) {
		super();
		this.activityName = activityName;
		this.assigneeName = assigneeName;
		this.startTime = startTime;
		this.endTime = endTime;
		this.comment = comment;
		this.durationTime = durationTime;
	}
	
	public String getActivityName() {
		return activityName;
	}
	public String getAssigneeName() {
		return assigneeName;
	}
	public String getStartTime() {
		return startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public String getComment() {
		return comment;
	}
	public String getDurationTime() {
		return durationTime;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public void setAssigneeName(String assigneeName) {
		this.assigneeName = assigneeName;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public void setDurationTime(String durationTime) {
		this.durationTime = durationTime;
	}
	

}
