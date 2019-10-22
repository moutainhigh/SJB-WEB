package sijibao.oa.jeesite.modules.intfz.response.contractnew;

/**
 * WEB合同后台添加-请求对象
 * @author wanxb
 * 2018-10-22 14:32:24
 */
public class MainContractInfoBackResponse {

	/**
	 * 
	 */
	private String id ;//合同id
	private String contractCode;		// 合同编号
	private String companyName;		// 公司名称
	private String abbreviation;	//公司简称
	private String creditCode;		// 统一社会信用代码
	private String address;		// 住址
	private String legalRepresentative;		// 法定代表人
	
	private String createBy;//创建人
	private long createTime = 0l;//创建时间
	private String updateBy;//修改时间
	private long updateTime = 0l;//修改时间
	private String delFlag; //合同状态：0启用，1停用
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContractCode() {
		return contractCode;
	}
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getAbbreviation() {
		return abbreviation;
	}
	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}
	public String getCreditCode() {
		return creditCode;
	}
	public void setCreditCode(String creditCode) {
		this.creditCode = creditCode;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getLegalRepresentative() {
		return legalRepresentative;
	}
	public void setLegalRepresentative(String legalRepresentative) {
		this.legalRepresentative = legalRepresentative;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public long getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}
	public String getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	@Override
	public String toString() {
		return "ContractInfoBackResponse [id=" + id + ", contractCode=" + contractCode + ", companyName=" + companyName
				+ ", abbreviation=" + abbreviation + ", creditCode=" + creditCode + ", address=" + address
				+ ", legalRepresentative=" + legalRepresentative + ", createBy=" + createBy + ", createTime="
				+ createTime + ", updateBy=" + updateBy + ", updateTime=" + updateTime + ", delFlag=" + delFlag + "]";
	}
	
	
	
	

	
}
