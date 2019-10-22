/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.oa.entity;

import com.sijibao.oa.common.persistence.DataEntity;

/**
 * 合并客户实体类
 * @author wanxb
 * @version 2018年8月16日 13:52:03
 */
public class CustMerge extends DataEntity<CustMerge> {
	
	private static final long serialVersionUID = 1L;
	private String mainCustId;		// 主客户id
	private String childCustId;//子客户id
	public String getMainCustId() {
		return mainCustId;
	}
	public void setMainCustId(String mainCustId) {
		this.mainCustId = mainCustId;
	}
	public String getChildCustId() {
		return childCustId;
	}
	public void setChildCustId(String childCustId) {
		this.childCustId = childCustId;
	}
	@Override
	public String toString() {
		return "CustMerge [mainCustId=" + mainCustId + ", childCustId=" + childCustId + "]";
	}
	
}