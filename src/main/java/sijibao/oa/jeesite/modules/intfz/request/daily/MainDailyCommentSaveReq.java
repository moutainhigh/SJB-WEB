/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.intfz.request.daily;

import java.io.Serializable;

/**
 * 日志评论Entity
 * @author wanxb
 * @version 2019-04-09
 */

public class MainDailyCommentSaveReq implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String dailyId;		// 日志id
	private String comment;		// 评论内容
	private String producSide;//产品端

	public String getProducSide() {
		return producSide;
	}

	public void setProducSide(String producSide) {
		this.producSide = producSide;
	}

	public String getDailyId() {
		return dailyId;
	}

	public void setDailyId(String dailyId) {
		this.dailyId = dailyId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override
	public String toString() {
		return "MainDailyCommentSaveReq{" +
				"dailyId='" + dailyId + '\'' +
				", comment='" + comment + '\'' +
				'}';
	}
}