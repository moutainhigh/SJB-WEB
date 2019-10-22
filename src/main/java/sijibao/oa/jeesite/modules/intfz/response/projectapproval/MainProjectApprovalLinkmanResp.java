/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.intfz.response.projectapproval;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
 * 立项联系人信息Entity
 * @author wanxb
 * @version 2019-08-19
 */
@ApiModel(value="联系人明细")
public class MainProjectApprovalLinkmanResp implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value="主键 id")
	private String commonId;		// 立项或项目id
	@ApiModelProperty(value="联系人类型：1立项联系人，2项目联系人")
	private String linkmanType;		// 联系人类型：1立项联系人，2项目联系人
	@ApiModelProperty(value="联系人姓名")
	private String linkmanName;		// 联系人姓名
	@ApiModelProperty(value="联系人电话")
	private String linkmanPhone;		// 联系人电话
	@ApiModelProperty(value="联系人职位")
	private String linkmanPost;		// 联系人职位
	@ApiModelProperty(value="联系人邮箱")
	private String linkmanMail;		// 联系人邮箱
	@ApiModelProperty(value="备注")
	private String remarks;		// 备注

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getCommonId() {
		return commonId;
	}

	public void setCommonId(String commonId) {
		this.commonId = commonId;
	}

	public String getLinkmanType() {
		return linkmanType;
	}

	public void setLinkmanType(String linkmanType) {
		this.linkmanType = linkmanType;
	}

	public String getLinkmanName() {
		return linkmanName;
	}

	public void setLinkmanName(String linkmanName) {
		this.linkmanName = linkmanName;
	}

	public String getLinkmanPhone() {
		return linkmanPhone;
	}

	public void setLinkmanPhone(String linkmanPhone) {
		this.linkmanPhone = linkmanPhone;
	}

	public String getLinkmanPost() {
		return linkmanPost;
	}

	public void setLinkmanPost(String linkmanPost) {
		this.linkmanPost = linkmanPost;
	}

	public String getLinkmanMail() {
		return linkmanMail;
	}

	public void setLinkmanMail(String linkmanMail) {
		this.linkmanMail = linkmanMail;
	}

	@Override
	public String toString() {
		return "MainProjectApprovalLinkmanResp{" +
				"commonId='" + commonId + '\'' +
				", linkmanType='" + linkmanType + '\'' +
				", linkmanName='" + linkmanName + '\'' +
				", linkmanPhone='" + linkmanPhone + '\'' +
				", linkmanPost='" + linkmanPost + '\'' +
				", linkmanMail='" + linkmanMail + '\'' +
				'}';
	}
}