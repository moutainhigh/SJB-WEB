package sijibao.oa.jeesite.modules.intfz.response.custinfo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 客户线索信息返回对象
 * @author wanxb
 *
 */
@ApiModel(value="客户线索--明细返回对象")
public class CustInfoClueDetailResponse implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value="主键id")
	private String id;				//主键id
	@ApiModelProperty(value="客户名称")
	private String custName;		// 客户名称
	@ApiModelProperty(value="客户简称")
	private String custAbbreviation;		// 客户简称
	@ApiModelProperty(value="客户分类：1000+万吨/月   500~1000万吨/月   0~500万吨/月")
	private String custClassify;		// 客户分类：1000+万吨/月   500~1000万吨/月   0~500万吨/月
	@ApiModelProperty(value="客户分类文本：1000+万吨/月   500~1000万吨/月   0~500万吨/月")
	private String custClassifyValue;		// 客户分类：1000+万吨/月   500~1000万吨/月   0~500万吨/月
	@ApiModelProperty(value="所属区域id")
	private String[] areaId;		// 所属区域id
	@ApiModelProperty(value="所属区域name")
	private String[] areaName;		// 所属区域name
	@ApiModelProperty(value="规模")
	private Integer custCompanySize;		// 规模
	@ApiModelProperty(value="运输方式:汽运，铁运，船运")
	private String[] tranMethod;		// 运输方式:汽运，铁运，船运
	@ApiModelProperty(value="运输方式文本:汽运，铁运，船运")
	private String[] tranMethodValue;		// 运输方式:汽运，铁运，船运
	@ApiModelProperty(value="联系人")
	private String custLinkmanName;		// 联系人
	@ApiModelProperty(value="职位")
	private String linkmanPost;		// 职位
	@ApiModelProperty(value="联系方式")
	private String linkmanPhone;		// 联系方式
	@ApiModelProperty(value="市场负责人id")
	private String marketLeaderId;		// 市场负责人id
	@ApiModelProperty(value="市场负责人name")
	private String marketLeaderName;		// 市场负责人name
	@ApiModelProperty(value="客户现状")
	private String custActuality;		// 客户现状
	@ApiModelProperty(value="业务概况")
	private String custBusinessProfile;		// 业务概况
	@ApiModelProperty(value="线索说明")
	private String custClueExplain;		// 线索说明
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getCustClassify() {
		return custClassify;
	}
	public void setCustClassify(String custClassify) {
		this.custClassify = custClassify;
	}
	public String[] getAreaName() {
		return areaName;
	}
	public void setAreaName(String[] areaName) {
		this.areaName = areaName;
	}
	
	public Integer getCustCompanySize() {
		return custCompanySize;
	}
	public void setCustCompanySize(Integer custCompanySize) {
		this.custCompanySize = custCompanySize;
	}
	public String getCustLinkmanName() {
		return custLinkmanName;
	}
	public String[] getTranMethod() {
		return tranMethod;
	}
	public void setTranMethod(String[] tranMethod) {
		this.tranMethod = tranMethod;
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
	public String getMarketLeaderId() {
		return marketLeaderId;
	}
	public void setMarketLeaderId(String marketLeaderId) {
		this.marketLeaderId = marketLeaderId;
	}
	public String getCustClassifyValue() {
		return custClassifyValue;
	}
	public void setCustClassifyValue(String custClassifyValue) {
		this.custClassifyValue = custClassifyValue;
	}
	public String[] getTranMethodValue() {
		return tranMethodValue;
	}
	public void setTranMethodValue(String[] tranMethodValue) {
		this.tranMethodValue = tranMethodValue;
	}
	public String getMarketLeaderName() {
		return marketLeaderName;
	}
	public void setMarketLeaderName(String marketLeaderName) {
		this.marketLeaderName = marketLeaderName;
	}
	public String[] getAreaId() {
		return areaId;
	}
	public void setAreaId(String[] areaId) {
		this.areaId = areaId;
	}
	public String getCustActuality() {
		return custActuality;
	}
	public void setCustActuality(String custActuality) {
		this.custActuality = custActuality;
	}
	public String getCustBusinessProfile() {
		return custBusinessProfile;
	}
	public void setCustBusinessProfile(String custBusinessProfile) {
		this.custBusinessProfile = custBusinessProfile;
	}
	public String getCustClueExplain() {
		return custClueExplain;
	}
	public void setCustClueExplain(String custClueExplain) {
		this.custClueExplain = custClueExplain;
	}
	
	
	
}
