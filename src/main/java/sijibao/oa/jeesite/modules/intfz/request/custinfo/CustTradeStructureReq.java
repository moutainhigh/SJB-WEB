package sijibao.oa.jeesite.modules.intfz.request.custinfo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 贸易结构对象
 * @author wanxb
 */
@ApiModel(value="贸易结构对象")
public class CustTradeStructureReq implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value="上游")
	private String upstream;		// 上游
	@ApiModelProperty(value="下游")
	private String downstream;		// 下游
	public String getUpstream() {
		return upstream;
	}
	public void setUpstream(String upstream) {
		this.upstream = upstream;
	}
	public String getDownstream() {
		return downstream;
	}
	public void setDownstream(String downstream) {
		this.downstream = downstream;
	}
	
}
