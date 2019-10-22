package sijibao.oa.jeesite.modules.sys.entity;

import org.hibernate.validator.constraints.Length;

import com.sijibao.oa.common.persistence.DataEntity;

import io.swagger.annotations.ApiModelProperty;

/**
 * 年会活动Entity
 * @author xby
 * @version 2019-01-14
 */
public class LuckyActive extends DataEntity<LuckyActive> {
	
	private static final long serialVersionUID = 1L;
	private String type;		// 活动类别
	@ApiModelProperty(value="入参：活动开始时间")
	private long startDate;
	
	@ApiModelProperty(value="出参：活动状态 0:未开始,1:进行中,2:已结束")
	private Integer activeStatus; //活动状态 0:未开始,1:进行中,2:已结束
	@ApiModelProperty(value="出参：参与状态 0:未答题，2：参与答题")
	private Integer isJoin; //参与状态 0:未答题，2：参与答题
	@ApiModelProperty(value="出参：openID")
	private String openId;  //openId 
	public LuckyActive() {
		super();
	}

	public LuckyActive(String id){
		super(id);
	}

	@Length(min=1, max=1, message="活动类别长度必须介于 1 和 1 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(Integer activeStatus) {
		this.activeStatus = activeStatus;
	}

	public Integer getIsJoin() {
		return isJoin;
	}

	public void setIsJoin(Integer isJoin) {
		this.isJoin = isJoin;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public long getStartDate() {
		return startDate;
	}

	public void setStartDate(long startDate) {
		this.startDate = startDate;
	}
	
}