/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.intfz.response.consumables;

/**
 * 消耗品类别管理Entity
 * @author wanxb
 * @version 2019-03-08
 */

public class MainConsumablesTypeResp {
	
	private String id;		// 分类编号
	private String name;		// 分类名称
	private String pId;		// 父级id

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    @Override
    public String toString() {
        return "MainConsumablesTypeResp{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", pId='" + pId + '\'' +
                '}';
    }
}