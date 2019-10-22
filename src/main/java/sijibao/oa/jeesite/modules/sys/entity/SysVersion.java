/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.sys.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.sijibao.oa.common.persistence.DataEntity;

/**
 * 系统版本维护Entity
 * @author wanxb
 * @version 2018-06-06
 */
public class SysVersion extends DataEntity<SysVersion> {
	
	private static final long serialVersionUID = 1L;
	private String versionNo;		// 版本编号
	private String context;		// 内容
	
	private Date start;//开始时间
	private Date end;//结束时间
	
	public SysVersion() {
		super();
	}

	public SysVersion(String id){
		super(id);
	}

	@Length(min=1, max=32, message="版本编号长度必须介于 1 和 32 之间")
	public String getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}
	
	@Length(min=1, max=1000, message="内容长度必须介于 1 和 1000 之间")
	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}
	
	
}