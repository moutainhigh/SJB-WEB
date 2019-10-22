/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.oa.entity;

import java.util.Date;

import com.sijibao.oa.common.persistence.DataEntity;


/**
 * 消息Entity
 * @author wanxb
 * @version 2019-04-25
 */
public class MessageInfo  extends DataEntity<NeedLabel> {
	
	private static final long serialVersionUID = 1L;
	private String commonId;		//非流程id
	private String billType;		// 流程类型：1市场，2实施，3报销，4接待，5出差，6资源，7资源办理，8开户，9合同,10合同归档
	private String businessId;		// 流程id
	private String procInsId;		//流程实例id
	private String todoTaskId;		// 待办流程节点id
	private String doneTaskId;		// 已办流程节点id
	private String readStatus;		// 0已读，1未读
	private String sendMessage;		// 消息内容
	private String recPersion;    //接收消息人id
	private String contractLeaderId; //合同负责人id
	private Date contractEndTime; //合同逾期时间
	private String receiptStatus;	//合同回执状态

	public String getCommonId() {
		return commonId;
	}

	public void setCommonId(String commonId) {
		this.commonId = commonId;
	}

	public String getBillType() {
		return billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public String getProcInsId() {
		return procInsId;
	}

	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}

	public String getTodoTaskId() {
		return todoTaskId;
	}

	public void setTodoTaskId(String todoTaskId) {
		this.todoTaskId = todoTaskId;
	}

	public String getDoneTaskId() {
		return doneTaskId;
	}

	public void setDoneTaskId(String doneTaskId) {
		this.doneTaskId = doneTaskId;
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

	public String getRecPersion() {
		return recPersion;
	}

	public void setRecPersion(String recPersion) {
		this.recPersion = recPersion;
	}

	public String getContractLeaderId() {
		return contractLeaderId;
	}

	public void setContractLeaderId(String contractLeaderId) {
		this.contractLeaderId = contractLeaderId;
	}

	public Date getContractEndTime() {
		return contractEndTime;
	}

	public void setContractEndTime(Date contractEndTime) {
		this.contractEndTime = contractEndTime;
	}

	public String getReceiptStatus() {
		return receiptStatus;
	}

	public void setReceiptStatus(String receiptStatus) {
		this.receiptStatus = receiptStatus;
	}

	@Override
	public String toString() {
		return "MessageInfo{" +
				"commonId='" + commonId + '\'' +
				", billType='" + billType + '\'' +
				", businessId='" + businessId + '\'' +
				", procInsId='" + procInsId + '\'' +
				", todoTaskId='" + todoTaskId + '\'' +
				", doneTaskId='" + doneTaskId + '\'' +
				", readStatus='" + readStatus + '\'' +
				", sendMessage='" + sendMessage + '\'' +
				", recPersion='" + recPersion + '\'' +
				", contractLeaderId='" + contractLeaderId + '\'' +
				", contractEndTime=" + contractEndTime +
				", receiptStatus='" + receiptStatus + '\'' +
				'}';
	}
}