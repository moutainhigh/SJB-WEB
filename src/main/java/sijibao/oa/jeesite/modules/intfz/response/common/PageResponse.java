package sijibao.oa.jeesite.modules.intfz.response.common;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 分页包装
 * @author xuhang
 * @time 2017年11月14日 上午10:53:06
 * @param <T>
 */
@ApiModel(value="分页响应对象")
public class PageResponse<T> {

	@JsonSerialize(include= JsonSerialize.Inclusion.NON_NULL)
	@ApiModelProperty(value="响应对象列表")
	private T list;
	@ApiModelProperty(value="页数")
	private int pageNo;
	@ApiModelProperty(value="总记录数")
	private long total;
	@ApiModelProperty(value="红点数")
	private String redCount;
	@ApiModelProperty(value="响应对象")
	private T entity; 
	public PageResponse(T list, int pageNo, long total){
		this.list = list;
		this.pageNo = pageNo;
		this.total = total;
	}
	
	public PageResponse(T list, int pageNo, long total,T entity){
		this.list = list;
		this.pageNo = pageNo;
		this.total = total;
		this.entity = entity;
	}

	public T getList() {
		return list;
	}

	public int getPageNo() {
		return pageNo;
	}

	public long getTotal() {
		return total;
	}

	public void setList(T list) {
		this.list = list;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public T getEntity() {
		return entity;
	}

	public void setEntity(T entity) {
		this.entity = entity;
	}

	public String getRedCount() {
		return redCount;
	}

	public void setRedCount(String redCount) {
		this.redCount = redCount;
	}
}
