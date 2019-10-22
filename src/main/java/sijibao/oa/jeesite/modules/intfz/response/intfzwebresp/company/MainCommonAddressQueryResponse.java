package sijibao.oa.jeesite.modules.intfz.response.intfzwebresp.company;

import java.io.Serializable;
import java.util.List;

/**
 * 企业管理-详情返回对象
 * @author wanxb
 *
 */
public class MainCommonAddressQueryResponse implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private List<MainLoadAddress> addresss;
	
	private int count;

	public List<MainLoadAddress> getAddresss() {
		return addresss;
	}

	public void setAddresss(List<MainLoadAddress> addresss) {
		this.addresss = addresss;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "MainCommonAddressQueryResponse [addresss=" + addresss + ", count=" + count + "]";
	}
	
	

	
	
}
