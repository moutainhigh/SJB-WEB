package sijibao.oa.jeesite.modules.intfz.request.report;

import com.sijibao.oa.modules.intfz.request.common.PageRequest;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 合同逾期报表接收对象
 * @author huangkai
 */
@ApiModel(value="合同逾期报表接收对象")
public class ContractOverdueRequest extends PageRequest{
	
	@ApiModelProperty(value="查询类型(1=逾期4-7天, 2=逾期8-11天, 3=逾期11天以上)")
	private String overdueType;  //年份




	@Override
	public String toString() {
		return "ContractOverdueRequest [overdueType=" + overdueType + "]";
	}

	public String getOverdueType() {
		return overdueType;
	}

	public void setOverdueType(String overdueType) {
		this.overdueType = overdueType;
	}
}
