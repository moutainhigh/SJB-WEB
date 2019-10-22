/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.intfz.response.message;

import com.sijibao.base.common.provider.entity.PagerBase;

import io.swagger.annotations.ApiModelProperty;

/**
 * 消息
 * @author wanxb
 * @version 2019-04-25
 */
public class MainMessageResp extends PagerBase<MainMessageResp> {
	@ApiModelProperty(value="非流程id")
	private String id;		// 非流程id
	@ApiModelProperty(value="流程类型：1市场，2实施，3报销，4接待，5出差，6资源，7资源办理，8开户，9合同，10合同归档")
	private String billType;		// 流程类型：1市场，2实施，3报销，4接待，5出差，6资源，7资源办理，8开户，9合同，10合同归档
	@ApiModelProperty(value="流程id")
	private String businessId;		// 流程id
	@ApiModelProperty(value="流程节点id")
	private String taskId;		// 流程节点id
	@ApiModelProperty(value=" 0已读，1未读")
	private String readStatus;		// 0已读，1未读
	@ApiModelProperty(value="消息内容")
	private String sendMessage;		// 消息内容
	@ApiModelProperty(value="todo或done或myself两种类型")
	private String pathType;		//todo...done两种类型
	@ApiModelProperty(value="红点数量")
	private String redCount;		//红点数量
	@ApiModelProperty(value="按钮：0不显示，1显示")
	private String button;		//按钮：0不显示，1显示

	public String getBillType() {
		return billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}

	@Override
	public String getId() {

		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getReadStatus() {
		return readStatus;
	}

	public void setReadStatus(String readStatus) {
		this.readStatus = readStatus;
	}

	public String getSendMessage() {
		return sendMessage;
	}

	public void setSendMessage(String sendMessage) {
		this.sendMessage = sendMessage;
	}

	public String getPathType() {
		return pathType;
	}

	public void setPathType(String pathType) {
		this.pathType = pathType;
	}

	public String getRedCount() {
		return redCount;
	}

	public void setRedCount(String redCount) {
		this.redCount = redCount;
	}

	public String getButton() {
		return button;
	}

	public void setButton(String button) {
		this.button = button;
	}

	@Override
	public String toString() {
		return "MainMessageResp{" +
				"id='" + id + '\'' +
				", billType='" + billType + '\'' +
				", businessId='" + businessId + '\'' +
				", taskId='" + taskId + '\'' +
				", readStatus='" + readStatus + '\'' +
				", sendMessage='" + sendMessage + '\'' +
				", pathType='" + pathType + '\'' +
				", redCount='" + redCount + '\'' +
				", button='" + button + '\'' +
				'}';
	}
}