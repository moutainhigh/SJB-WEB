package sijibao.oa.jeesite.modules.intfz.request.intfzwebreq.projectinfo;

import com.sijibao.oa.modules.intfz.request.common.PageRequest;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="异常运单数量统计")
public class OrderCountReq extends PageRequest{
	@ApiModelProperty(value="企业名称")
	private String companyName;

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	@Override
	public String toString() {
		return "OrderCountReq [companyName=" + companyName + "]";
	}
	
}
