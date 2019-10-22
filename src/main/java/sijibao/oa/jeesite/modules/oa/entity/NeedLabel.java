/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.oa.entity;

import java.util.Date;

import com.sijibao.oa.common.persistence.DataEntity;

/**
 * 协作标签管理Entity
 * @author wanxb
 * @version 2018-11-23
 */
public class NeedLabel extends DataEntity<NeedLabel> {
	
	private static final long serialVersionUID = 1L;
	private String labelCode;		// 标签编号
	private String labelName;		// 标签名称
	private String del;
	
	
	public String getDel() {
		return del;
	}

	public void setDel(String del) {
		this.del = del;
	}

	public NeedLabel() {
		super();
	}

	public NeedLabel(String id){
		super(id);
	}

	public String getLabelCode() {
		return labelCode;
	}

	public void setLabelCode(String labelCode) {
		this.labelCode = labelCode;
	}
	
	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}
	
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "NeedLabel [labelCode=" + labelCode + ", labelName=" + labelName + "]";
	}
	
}