/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.intfz.response.daily;

import com.sijibao.base.common.api.response.PagerResponse;

import io.swagger.annotations.ApiModelProperty;

/**
 * 日报列表查询对象
 * @author wanxb
 * @version 2018-12-12
 */
public class MainDailyCommentResponse extends PagerResponse<MainCopyToResponse> {
	
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "评论内容")
	private String comment;		// 评论内容
	@ApiModelProperty(value = "创建人")
	private String createBy;		// 创建人
	@ApiModelProperty(value = "创建时间")
	private long createTime;		//创建时间



	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
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

	@Override
	public String toString() {
		return "MainDailyCommentResponse{" +
				"comment='" + comment + '\'' +
				", createBy='" + createBy + '\'' +
				", createTime=" + createTime +
				'}';
	}
}