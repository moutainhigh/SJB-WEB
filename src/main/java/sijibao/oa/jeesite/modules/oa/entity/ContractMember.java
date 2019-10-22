/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.oa.entity;

import java.util.Date;

import com.sijibao.oa.modules.sys.entity.User;


/**
 * 合同方信息Entity
 * @author xby
 * @version 2018-10-23
 */
public class ContractMember  {
	
	private String contractId;		// 合同模版表id
	private String memberType;		// 合同方：1甲方，2乙方，3丙方
	private String contractType;		// 合同方类型：1公司，2个人
	private String dataSource;		// 数据来源：1客户数据，2合同信息,3非系统数据
	private Date createTime;		// 创建时间
	private Date updateTime;		// 更新时间
	private User createBy; //创建人
	private User updateBy; //修改人
	public String getContractId() {
		return contractId;
	}
	public void setContractId(String contractId) {
		this.contractId = contractId;
	}
	public String getMemberType() {
		return memberType;
	}
	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}
	public String getContractType() {
		return contractType;
	}
	public void setContractType(String contractType) {
		this.contractType = contractType;
	}
	public String getDataSource() {
		return dataSource;
	}
	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public User getCreateBy() {
		return createBy;
	}
	public void setCreateBy(User createBy) {
		this.createBy = createBy;
	}
	public User getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(User updateBy) {
		this.updateBy = updateBy;
	}
	@Override
	public String toString() {
		return "ContractMember [contractId=" + contractId + ", memberType=" + memberType + ", contractType="
				+ contractType + ", dataSource=" + dataSource + ", createTime=" + createTime + ", updateTime="
				+ updateTime + ", createBy=" + createBy + ", updateBy=" + updateBy + "]";
	}
	
}