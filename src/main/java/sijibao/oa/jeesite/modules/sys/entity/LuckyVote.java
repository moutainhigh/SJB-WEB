package sijibao.oa.jeesite.modules.sys.entity;

import org.hibernate.validator.constraints.Length;

import com.sijibao.oa.common.persistence.DataEntity;

/**
 * 年会投票Entity
 * @author xby
 * @version 2019-01-24
 */
public class LuckyVote extends DataEntity<LuckyVote> {
	
	private static final long serialVersionUID = 1L;
	private String showId;		// 节目ID
	private String showName;		// 节目名称
	private String showType;		// 节目类型
	
	private Integer voteNum; //票数
	public LuckyVote() {
		super();
	}

	public LuckyVote(String id){
		super(id);
	}

	@Length(min=1, max=64, message="节目ID长度必须介于 1 和 64 之间")
	public String getShowId() {
		return showId;
	}

	public void setShowId(String showId) {
		this.showId = showId;
	}
	
	@Length(min=1, max=128, message="节目名称长度必须介于 1 和 128 之间")
	public String getShowName() {
		return showName;
	}

	public void setShowName(String showName) {
		this.showName = showName;
	}
	
	@Length(min=1, max=32, message="节目类型长度必须介于 1 和 32 之间")
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