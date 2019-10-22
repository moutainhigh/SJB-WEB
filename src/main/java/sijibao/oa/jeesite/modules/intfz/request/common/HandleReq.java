package sijibao.oa.jeesite.modules.intfz.request.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 各明细请求实体对象
 * @author wanxb
 */
@ApiModel(value="明细请求实体对象")
public class HandleReq {
	
	@ApiModelProperty(value="列表页面带入id（所有详情页面只传Id）")
	private String id;
	@ApiModelProperty(value="code(企业查询：holderCode；企业常用地址查询：companyCode)")
	private String code;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "HandleReq [id=" + id + ", code=" + code + "]";
	}
}
