package sijibao.oa.jeesite.modules.intfz.request.lucky;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;

/**
 * 年会节目投票请求对象
 * @author xby
 * @version 2019-01-14
 */
public class LuckyVoteInfoReq implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value="节目信息")
	List<LuckySaveVoteInfoReq> saveVoteInfoList;
	
	public LuckyVoteInfoReq() {}

	public List<LuckySaveVoteInfoReq> getSaveVoteInfoList() {
		return saveVoteInfoList;
	}

	public void setSaveVoteInfoList(List<LuckySaveVoteInfoReq> saveVoteInfoList) {
		this.saveVoteInfoList = saveVoteInfoList;
	}
	
}