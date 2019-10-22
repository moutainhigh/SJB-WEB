package sijibao.oa.jeesite.modules.intfz.request.company;

import io.swagger.annotations.ApiModelProperty;

public class QueryCompanyHandleReq{
	@ApiModelProperty(value="企业名称")
	private String companyName;

	@ApiModelProperty(value="页数")
	private int pageNo;//页数
	@ApiModelProperty(value="每页数量")
	private int pageSize;//每页数量
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	@Override
	public String toString() {
		return "QueryCompanyHandleReq [companyName=" + companyName + ", pageNo=" + pageNo + ", pageSize=" + pageSize
				+ "]";
	}

	
}
