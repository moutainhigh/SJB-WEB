package sijibao.oa.jeesite.modules.intfz.request.custinfo;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 客户管理-合并客户
 * @author wanxb
 *
 */
@ApiModel(value="客户管理-合并客户")
public class CustMergeReq implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value="主客户id")
	private String mainCustId;		// 客户名称
	@ApiModelProperty(value="子客户ids")
	private List<String> childCustIds;		// 子客户ids
	public String getMainCustId() {
		return mainCustId;
	}
	public void setMainCustId(String mainCustId) {
		this.mainCustId = mainCustId;
	}
	public List<String> getChildCustIds() {
		return childCustIds;
	}
	public void setChildCustIds(List<String> childCustIds) {
		this.childCustIds = childCustIds;
	}
	@Override
	public String toString() {
		return "CustMergeReq [mainCustId=" + mainCustId + ", childCustIds=" + childCustIds + "]";
	}
	
}
