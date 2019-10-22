/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.oa.entity;


import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.sijibao.oa.common.persistence.DataEntity;
import com.sijibao.oa.modules.sys.entity.User;

/**
 * 客户线索Entity
 * @author wanxb
 * @version 2018-05-29
 */
public class CustInfoClue extends DataEntity<CustInfoClue> {
	
	private static final long serialVersionUID = 1L;
	private String clueCode;		// 线索编号:"D"+yyyyMMdd+随机3位数
	private String custName;		// 客户名称
	private String custAbbreviation;		// 客户简称
	private String custClassify;		// 客户分类：1000+万吨/月   500~1000万吨/月   0~500万吨/月
	private String areaId;		// 所属区域id
	private String areaName;		// 所属区域name
	private Integer custCompanySize;		// 规模
	private String tranMethod;		// 运输方式:汽运，铁运，船运
	private String tranMethodValue;		// 运输方式:汽运，铁运，船运
	private String custLinkmanName;		// 联系人
	private String linkmanPost;		// 职位
	private String linkmanPhone;		// 联系方式
	private User marketLeader;		// 市场负责人id
	private String custActuality;		// 客户现状
	private String custBusinessProfile;		// 业务概况
	private String custClueExplain;		// 线索说明
	private String marketLeaderName;//市场负责人name
	
	private Date clueTimeStart;//开始搜索时间
	private Date clueTimeEnd;//结束搜索时间
	
	
	private User user;  //当前登入人
	private String sql;	//数据权限控制sql
	
	
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public CustInfoClue() {
		super();
	}

	public CustInfoClue(String id){
		super(id);
	}

	@Length(min=1, max=20, message="线索编号长度必须介于 1 和 20 之间")
	public String getClueCode() {
		return clueCode;
	}

	public void setClueCode(String clueCode) {
		this.clueCode = clueCode;
	}
	
	@Length(min=1, max=90, message="客户名称长度必须介于 1 和 90 之间")
	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}
	
	@Length(min=0, max=90, message="客户简称长度必须介于 0 和 90 之间")
	public String getCustAbbreviation() {
		return custAbbreviation;
	}

	public void setCustAbbreviation(String custAbbreviation) {
		this.custAbbreviation = custAbbreviation;
	}
	
	@Length(min=1, max=8, message="客户分类：1000+万吨/月   500~1000万吨/月   0~500万吨/月长度必须介于 1 和 8 之间")
	public String getCustClassify() {
		return custClassify;
	}

	public void setCustClassify(String custClassify) {
		this.custClassify = custClassify;
	}
	
	
	
	
	public Integer getCustCompanySize() {
		return custCompanySize;
	}

	public void setCustCompanySize(Integer custCompanySize) {
		this.custCompanySize = custCompanySize;
	}

	@Length(min=0, max=32, message="联系人长度必须介于 0 和 32 之间")
	public String getCustLinkmanName() {
		return custLinkmanName;
	}

	public void setCustLinkmanName(String custLinkmanName) {
		this.custLinkmanName = custLinkmanName;
	}
	
	@Length(min=0, max=32, message="职位长度必须介于 0 和 32 之间")
	public String getLinkmanPost() {
		return linkmanPost;
	}

	public void setLinkmanPost(String linkmanPost) {
		this.linkmanPost = linkmanPost;
	}
	
	@Length(min=0, max=16, message="联系方式长度必须介于 0 和 16 之间")
	public String getLinkmanPhone() {
		return linkmanPhone;
	}

	public void setLinkmanPhone(String linkmanPhone) {
		this.linkmanPhone = linkmanPhone;
	}
	
	
	public User getMarketLeader() {
		return marketLeader;
	}

	public void setMarketLeader(User marketLeader) {
		this.marketLeader = marketLeader;
	}

	@Length(min=0, max=1000, message="客户现状长度必须介于 0 和 1000 之间")
	public String getCustActuality() {
		return custActuality;
	}

	public void setCustActuality(String custActuality) {
		this.custActuality = custActuality;
	}
	
	@Length(min=0, max=1000, message="业务概况长度必须介于 0 和 1000 之间")
	public String getCustBusinessProfile() {
		return custBusinessProfile;
	}

	public void setCustBusinessProfile(String custBusinessProfile) {
		this.custBusinessProfile = custBusinessProfile;
	}
	
	@Length(min=0, max=1000, message="线索说明长度必须介于 0 和 1000 之间")
	public String getCustClueExplain() {
		return custClueExplain;
	}

	public void setCustClueExplain(String custClueExplain) {
		this.custClueExplain = custClueExplain;
	}

	public String getTranMethod() {
		return tranMethod;
	}

	public void setTranMethod(String tranMethod) {
		this.tranMethod = tranMethod;
	}

	public String getTranMethodValue() {
		return tranMethodValue;
	}

	public void setTranMethodValue(String tranMethodValue) {
		this.tranMethodValue = tranMethodValue;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getMarketLeaderName() {
		return marketLeaderName;
	}

	public void setMarketLeaderName(String marketLeaderName) {
		this.marketLeaderName = marketLeaderName;
	}

	public Date getClueTimeStart() {
		return clueTimeStart;
	}

	public void setClueTimeStart(Date clueTimeStart) {
		this.clueTimeStart = clueTimeStart;
	}

	public Date getClueTimeEnd() {
		return clueTimeEnd;
	}

	public void setClueTimeEnd(Date clueTimeEnd) {
		this.clueTimeEnd = clueTimeEnd;
	}

	

}