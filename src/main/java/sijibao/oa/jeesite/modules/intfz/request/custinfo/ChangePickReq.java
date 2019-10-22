package sijibao.oa.jeesite.modules.intfz.request.custinfo;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 客户资料管理-开放客户/捡入客户请求对象
 * @author wanxb
 *
 */
@ApiModel(value="开放客户/捡入客户请求对象")
public class ChangePickReq implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value="主键ID")
	private String id;//主键id
	@ApiModelProperty(value="开放/捡入：1开放客户，2捡入客户")
	private String isChange;//开放/捡入：1开放客户，2捡入客户
	@ApiModelProperty(value="批量开发客户ids")
	private List<String> ids;//批量开发客户ids
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIsChange() {
		return isChange;
	}
	public void setIsChange(String isChange) {
		this.isChange = isChange;
	}
	public List<String> getIds() {
		return ids;
	}
	public void setIds(List<String> ids) {
		this.ids = ids;
	}
	@Override
	public String toString() {
		return "ChangePickReq [id=" + id + ", isChange=" + isChange + ", ids=" + ids + "]";
	}
}
