/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.intfz.request.need;

/**
 * 协作类型管理Entity
 * @author wanxb
 * @version 2018-11-23
 */
public class MainNeedTypeReq{
	
	private String typeId;

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	@Override
	public String toString() {
		return "MainNeedTypeReq [typeId=" + typeId + "]";
	}
	
}