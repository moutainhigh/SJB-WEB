package sijibao.oa.jeesite.modules.intfz.response.intfzwebresp.company;

public class MainAbnormalOrderCountRsp {


	/**
	 * 企业名称
	 */
	private String CompanyName;
	
	/**
	 * 数量
	 */
	private int count;
	
	/**
	 * 企业编码
	 */
	private String companyCode;

	public String getCompanyName() {
		return CompanyName;
	}

	public void setCompanyName(String companyName) {
		CompanyName = companyName;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	@Override
	public String toString() {
		return "MainAbnormalOrderCountRsp [CompanyName=" + CompanyName + ", count=" + count + ", companyCode="
				+ companyCode + "]";
	}
	
	
}
