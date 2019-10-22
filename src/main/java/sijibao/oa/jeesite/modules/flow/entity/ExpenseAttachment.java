/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.flow.entity;

import com.sijibao.oa.common.persistence.ActEntity;

/**
 * 费用附件Entity
 * @author xuby
 * @version 2018-01-22
 */
public class ExpenseAttachment extends ActEntity<ExpenseAttachment> {
	
	private static final long serialVersionUID = 1L;
	private String expenseCode; //报销流程编码
	private String expenseAttachmentUrl; //文件路径
	private String subImgUrl; //科目图片路径
	private String imgServerUrl; //图片服务器路径
	private String fileName; //文件名称
	private String fileType; //文件类型
	private String subCode = "";//科目编号
	private String subImgDes; //科目附件描述
	private String subImgConfId; //科目附件配置表ID
	private String expenseDetailId; //报销明细行ID
	private int detailLineNumber; //报销明细行行号
	private String enable;//科目状态：1停用，0启用
	
	private String isRequired; //是否必填
	public ExpenseAttachment() {
		super();
	}

	public ExpenseAttachment(String id){
		super(id);
	}

	public String getExpenseCode() {
		return expenseCode;
	}

	public void setExpenseCode(String expenseCode) {
		this.expenseCode = expenseCode;
	}

	public String getExpenseAttachmentUrl() {
		return expenseAttachmentUrl;
	}

	public void setExpenseAttachmentUrl(String expenseAttachmentUrl) {
		this.expenseAttachmentUrl = expenseAttachmentUrl;
	}
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getSubCode() {
		return subCode;
	}

	public void setSubCode(String subCode) {
		this.subCode = subCode;
	}

	public String getSubImgDes() {
		return subImgDes;
	}

	public void setSubImgDes(String subImgDes) {
		this.subImgDes = subImgDes;
	}
	
	public String getSubImgConfId() {
		return subImgConfId;
	}

	public void setSubImgConfId(String subImgConfId) {
		this.subImgConfId = subImgConfId;
	}
	
	public String getExpenseDetailId() {
		return expenseDetailId;
	}

	public void setExpenseDetailId(String expenseDetailId) {
		this.expenseDetailId = expenseDetailId;
	}

	public int getDetailLineNumber() {
		return detailLineNumber;
	}

	public void setDetailLineNumber(int detailLineNumber) {
		this.detailLineNumber = detailLineNumber;
	}

	public String getSubImgUrl() {
		return subImgUrl;
	}

	public void setSubImgUrl(String subImgUrl) {
		this.subImgUrl = subImgUrl;
	}

	public String getImgServerUrl() {
		return imgServerUrl;
	}

	public void setImgServerUrl(String imgServerUrl) {
		this.imgServerUrl = imgServerUrl;
	}
	
	public String getIsRequired() {
		return isRequired;
	}

	public void setIsRequired(String isRequired) {
		this.isRequired = isRequired;
	}

	public String getEnable() {
		return enable;
	}

	public void setEnable(String enable) {
		this.enable = enable;
	}

	@Override
	public String toString() {
		return "ExpenseAttachment{" +
				"expenseCode='" + expenseCode + '\'' +
				", expenseAttachmentUrl='" + expenseAttachmentUrl + '\'' +
				", subImgUrl='" + subImgUrl + '\'' +
				", imgServerUrl='" + imgServerUrl + '\'' +
				", fileName='" + fileName + '\'' +
				", fileType='" + fileType + '\'' +
				", subCode='" + subCode + '\'' +
				", subImgDes='" + subImgDes + '\'' +
				", subImgConfId='" + subImgConfId + '\'' +
				", expenseDetailId='" + expenseDetailId + '\'' +
				", detailLineNumber=" + detailLineNumber +
				", enable='" + enable + '\'' +
				", isRequired='" + isRequired + '\'' +
				'}';
	}
}