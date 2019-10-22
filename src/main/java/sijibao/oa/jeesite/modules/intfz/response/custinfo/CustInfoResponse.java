package sijibao.oa.jeesite.modules.intfz.response.custinfo;


import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 客户信息--列表(数量)返回对象
 * @author wanxb
 *
 */
@ApiModel(value="客户信息--列表(数量)返回对象")
public class CustInfoResponse implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value="公海数量")
	private Integer highSeasNum;//公海数量
	@ApiModelProperty(value="区域公海数量")
	private Integer regionSeasNum;//区域公海数量
	@ApiModelProperty(value="其他个人数量")
	private Integer otherNum;//其他个人数量
	@ApiModelProperty(value="个人数量")
	private Integer ownNum;//个人数量
	@ApiModelProperty(value="签约数量")
	private Integer signingNum;//签约数量
	public Integer getHighSeasNum() {
		return highSeasNum;
	}
	public void setHighSeasNum(Integer highSeasNum) {
		this.highSeasNum = highSeasNum;
	}
	public Integer getRegionSeasNum() {
		return regionSeasNum;
	}
	public void setRegionSeasNum(Integer regionSeasNum) {
		this.regionSeasNum = regionSeasNum;
	}
	public Integer getOtherNum() {
		return otherNum;
	}
	public void setOtherNum(Integer otherNum) {
		this.otherNum = otherNum;
	}
	public Integer getOwnNum() {
		return ownNum;
	}
	public void setOwnNum(Integer ownNum) {
		this.ownNum = ownNum;
	}
	public Integer getSigningNum() {
		return signingNum;
	}
	public void setSigningNum(Integer signingNum) {
		this.signingNum = signingNum;
	}
	@Override
	public String toString() {
		return "CustInfoResponse [highSeasNum=" + highSeasNum + ", regionSeasNum=" + regionSeasNum + ", otherNum="
				+ otherNum + ", ownNum=" + ownNum + ", signingNum=" + signingNum + "]";
	}
}
