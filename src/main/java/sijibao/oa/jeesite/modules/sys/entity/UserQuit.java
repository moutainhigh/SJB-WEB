/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.sys.entity;

import java.util.List;

import com.sijibao.oa.common.persistence.DataEntity;

/**
 * 离职表Entity
 * @author wanxb
 * @version 2019-01-04
 */
public class UserQuit extends DataEntity<UserQuit> {
	
	private static final long serialVersionUID = 1L;
	private String userId;		// 用户id
	private String handover;//交接人
	private String userType;		// 本人或交接人:0本人，1交接人
	private String isFinish;		// 个人申请单据是否都完结:0是，1否
	private String unfinishRemarks;//未完结备注
	
	private List<String> handovers;//交接人id 
	
	public UserQuit() {
		super();
	}

	public UserQuit(String id){
		super(id);
	}

	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}
	
	public String getIsFinish() {
		return isFinish;
	}

	public void setIsFinish(String isFinish) {
		this.isFinish = isFinish;
	}

	public String getUnfinishRemarks() {
		return unfinishRemarks;
	}

	public void setUnfinishRemarks(String unfinishRemarks) {
		this.unfinishRemarks = unfinishRemarks;
	}

	public String getHandover() {
		return handover;
	}

	public void setHandover(String handover) {
		this.handover = handover;
	}

	public List<String> getHandovers() {
		return handovers;
	}

	public void setHandovers(List<String> handovers) {
		this.handovers = handovers;
	}

	@Override
	public String toString() {
		return "UserQuit [userId=" + userId + ", handover=" + handover + ", userType=" + userType + ", isFinish="
				+ isFinish + ", unfinishRemarks=" + unfinishRemarks + ", handovers=" + handovers + "]";
	}
	
	
}