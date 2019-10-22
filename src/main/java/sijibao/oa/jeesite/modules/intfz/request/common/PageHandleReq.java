package sijibao.oa.jeesite.modules.intfz.request.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 各明细请求实体对象
 * @author wanxb
 */
@ApiModel(value="明细请求实体对象")
public class PageHandleReq{
	
	@ApiModelProperty(value="列表页面带入id（所有详情页面只传Id）")
	private String id;
	@ApiModelProperty(value="页数")
	private int pageNo;//页数
	@ApiModelProperty(value="每页数量")
	private int pageSize;//每页数量

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
		return "PageHandleReq{" +
				"id='" + id + '\'' +
				", pageNo=" + pageNo +
				", pageSize=" + pageSize +
				'}';
	}
}
