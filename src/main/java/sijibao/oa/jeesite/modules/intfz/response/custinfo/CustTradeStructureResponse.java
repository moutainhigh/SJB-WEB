package sijibao.oa.jeesite.modules.intfz.response.custinfo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;

/**
 * 贸易结构对象
 * @author wanxb
 */
@ApiModel(value="贸易结构对象")
public class CustTradeStructureResponse implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String upstream;		// 上游
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
