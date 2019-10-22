package sijibao.oa.jeesite.modules.intfz.request.lucky;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;

/**
 * 年会活动Entity
 * @author xby
 * @version 2019-01-14
 */
public class LuckyMInfoReq implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value="发送短信List")
	private List<LuckyMInfoListReq> infoList;
	public List<LuckyMInfoListReq> getInfoList() {
		return infoList;
	}
	public void setInfoList(List<LuckyMInfoListReq> infoList) {
		this.infoList = infoList;
	}
	
}