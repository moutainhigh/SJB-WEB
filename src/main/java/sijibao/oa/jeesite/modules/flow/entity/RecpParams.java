/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.flow.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sijibao.oa.common.persistence.DataEntity;

/**
 * 招待申请参数Entity
 * @author xuby
 * @version 2018-04-17
 */
public class RecpParams extends DataEntity<RecpParams> {
	
	private static final long serialVersionUID = 1L;
	private String procCode;		// 流程编号
	private String paramType;		// 参数类型：01接待申请流程02报销申请流程
	private String paramName;		// 参数名称
	private String paramValue;		// 参数值
	private Date createTime;		// 创建时间
	
	public RecpParams() {
		super();
	}

	public RecpParams(String id){
		super(id);
	}

	@Length(min=1, max=16, message="流程编号长度必须介于 1 和 16 之间")
	public String getProcCode() {
		return procCode;
	}

	public void setProcCode(String procCode) {
		this.procCode = procCode;
	}
	
	@Length(min=1, max=2, message="参数类型：01接待申请流程02报销申请流程长度必须介于 1 和 2 之间")
	public String getParamType() {
		return paramType;
	}

	public void setParamType(String paramType) {
		this.paramType = paramType;
	}
	
	@Length(min=1, max=100, message="参数名称长度必须介于 1 和 100 之间")
	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}
	
	@Length(min=1, max=100, message="参数值长度必须介于 1 和 100 之间")
	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}