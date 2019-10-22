package sijibao.oa.jeesite.modules.intfz.response.lucky;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

/**
 * 年会节目投票请求对象
 * @author xby
 * @version 2019-01-14
 */
public class LuckyQueryVoteInfoRes implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value="节目名称")
	private String showName;		// 节目名称
	@ApiModelProperty(value="节目ID")
	private String showId;		// 节目ID
	@ApiModelProperty(value="节目类型")
	private String showType;		// 节目类型
	@ApiModelProperty(value="票数")
	private Integer voteNum; //票数
	public LuckyQueryVoteInfoRes() {}

	public String getShowName() {
		return showName;
	}

	public void setShowName(String showName) {
		this.showName = showName;
	}

	public String getShowId() {
		return showId;
	}

	public void setShowId(String showId) {
		this.showId = showId;
	}

	public String getShowType() {
		return showType;
	}

	public void setShowType(String showType) {
		this.showType = showType;
	}

	public Integer getVoteNum() {
		return voteNum;
	}

	public void setVoteNum(Integer voteNum) {
		this.voteNum = voteNum;
	}
	
}