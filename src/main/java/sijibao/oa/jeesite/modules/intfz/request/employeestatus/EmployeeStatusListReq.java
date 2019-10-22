package sijibao.oa.jeesite.modules.intfz.request.employeestatus;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 人员状态列表查询请求对象
 * @author xuby
 * @date 2018-09-14
 */
@ApiModel(value="人员状态列表查询请求对象")
public class EmployeeStatusListReq {
	@ApiModelProperty(value="页数")
	private int pageNo;//页数
	@ApiModelProperty(value="每页数量")
	private int pageSize;//每页数量
	
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
		return "EmployeeStatusListReq [pageNo=" + pageNo + ", pageSize=" + pageSize + "]";
	}
}
