package sijibao.oa.jeesite.modules.intfz.request.custinfo;

import java.io.Serializable;
import java.util.List;

import com.sijibao.oa.modules.oa.entity.CustLinkman;
import com.sijibao.oa.modules.oa.entity.CustTradeStructure;

import io.swagger.annotations.ApiModel;

/**
 * 客户联系人请求对象
 * @author wanxb
 *
 */
@ApiModel(value="客户联系人请求对象")
public class CustDetailListRequest implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<CustLinkman> custLinkmanList;
	private List<CustTradeStructure> custTradeStructureList;
	
	public List<CustLinkman> getCustLinkmanList() {
		return custLinkmanList;
	}
	public void setCustLinkmanList(List<CustLinkman> custLinkmanList) {
		this.custLinkmanList = custLinkmanList;
	}
	public List<CustTradeStructure> getCustTradeStructureList() {
		return custTradeStructureList;
	}
	public void setCustTradeStructureList(List<CustTradeStructure> custTradeStructureList) {
		this.custTradeStructureList = custTradeStructureList;
	}
}
