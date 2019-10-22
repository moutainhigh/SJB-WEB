package sijibao.oa.jeesite.modules.intfz.response.custinfo;


import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 客户线索返回对象信息
 * @author wanxb
 *
 */
@ApiModel(value="客户线索--列表返回对象")
public class CustInfoClueResponse implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value="主键id")
	private String id;				//主键id
	@ApiModelProperty(value="客户编号")
	private String custCode;		// 客户code
	@ApiModelProperty(value="客户名称")
	private String custName;		// 客户名称
	@ApiModelProperty(value="客户简称")
	private String custAbbreviation;		// 客户简称
	@ApiModelProperty(value="客户分类文本：1000+万吨/月   500~1000万吨/月   0~500万吨/月")
	private String custClassifyValue;		// 客户分类：1000+万吨/月   500~1000万吨/月   0~500万吨/月
	@ApiModelProperty(value="所属区域name")
	private String areaName;		// 所属区域id
	@ApiModelProperty(value="规模")
	private Integer custCompanySize;		// 规模
	@ApiModelProperty(value="运输方式文本:汽运，铁运，船运")
	private String tranMethodValue;		// 运输方式:汽运，铁运，船运
	@ApiModelProperty(value="联系人")
	private String custLinkmanName;		// 联系人
	@ApiModelProperty(value="职位")
	private String linkmanPost;		// 职位
	@ApiModelProperty(value="联系方式")
	private String linkmanPhone;		// 联系方式
	@ApiModelProperty(value="市场负责人name")
	private String marketLeaderName;		// 市场负责人name
	@ApiModelProperty(value="更新时间")
	private long updateTime = 0l;		// 更新时间
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getCustAbbreviation() {
		return custAbbreviation;
	}
	public void setCustAbbreviation(String custAbbreviation) {
		this.custAbbreviation = custAbbreviation;
	}
	public String getCustClassifyValue() {
		return custClassifyValue;
	}
	public void setCustClassifyValue(String custClassifyValue) {
		this.custClassifyValue = custClassifyValue;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	
	public Integer getCustCompanySize() {
		return custCompanySize;
	}
	public void setCustCompanySize(Integer custCompanySize) {
		this.custCompanySize = custCompanySize;
	}
	public String getTranMethodValue() {
		return tranMethodValue;
	}
	public void setTranMethodValue(String tranMethodValue) {
		this.tranMethodValue = tranMethodValue;
	}
	public String getCustLinkmanName() {
		return custLinkmanName;
	}
	public void setCustLinkmanName(String custLinkmanName) {
		this.custLinkmanName = custLinkmanName;
	}
	public String getLinkmanPost() {
		return linkmanPost;
	}
	public void setLinkmanPost(String linkmanPost) {
		this.linkmanPost = linkmanPost;
	}
	public String getLinkmanPhone() {
		return linkmanPhone;
	}
	public void setLinkmanPhone(String linkmanPhone) {
		this.linkmanPhone = linkmanPhone;
	}
	public String getMarketLeaderName() {
		return marketLeaderName;
	}
	public void setMarketLeaderName(String marketLeaderName) {
		this.marketLeaderName = marketLeaderName;
	}
	public long getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}
	@Override
	public String toString() {
		return "CustInfoClueResponse [id=" + id + ", custCode=" + custCode + ", custName=" + custName
				+ ", custAbbreviation=" + custAbbreviation + ", custClassifyValue=" + custClassifyValue + ", areaName="
				+ areaName + ", custCompanySize=" + custCompanySize + ", tranMethodValue=" + tranMethodValue
				+ ", custLinkmanName=" + custLinkmanName + ", linkmanPost=" + linkmanPost + ", linkmanPhone="
				+ linkmanPhone + ", marketLeaderName=" + marketLeaderName + ", updateTime=" + updateTime + "]";
	}
	
	
}
