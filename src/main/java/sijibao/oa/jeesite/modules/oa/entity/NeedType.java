/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.oa.entity;

import java.io.Serializable;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.sijibao.oa.common.persistence.DataEntity;

/**
 * 协作类型管理Entity
 * @author wanxb
 * @version 2018-11-23
 */
public class NeedType extends DataEntity<NeedType> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String typeCode;		// 标签编号
	private String typeName;		// 标签名称
	private String isSelectedName;//默认状态名称
	private String flag;
	private String oldTypeName;//验证标签名不能重复
	
	private List<NeedProgress> needProgressList;//进度
	
	private List<NeedProgress> needProgressFirst;
	
	private List<NeedProgress> needProgressSecond;
	
	private String del;
	
	
	
	public String getDel() {
		return del;
	}

	public void setDel(String del) {
		this.del = del;
	}

	public NeedType() {
		super();
	}

	public NeedType(String id){
		super(id);
	}

	@Length(min=1, max=64, message="标签编号长度必须介于 1 和 64 之间")
	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	
	@Length(min=1, max=64, message="标签名称长度必须介于 1 和 64 之间")
	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	

	public List<NeedProgress> getNeedProgressList() {
		return needProgressList;
	}

	public void setNeedProgressList(List<NeedProgress> needProgressList) {
		this.needProgressList = needProgressList;
	}

	public String getIsSelectedName() {
		return isSelectedName;
	}

	public void setIsSelectedName(String isSelectedName) {
		this.isSelectedName = isSelectedName;
	}

	public List<NeedProgress> getNeedProgressFirst() {
		return needProgressFirst;
	}

	public void setNeedProgressFirst(List<NeedProgress> needProgressFirst) {
		this.needProgressFirst = needProgressFirst;
	}

	public List<NeedProgress> getNeedProgressSecond() {
		return needProgressSecond;
	}

	public void setNeedProgressSecond(List<NeedProgress> needProgressSecond) {
		this.needProgressSecond = needProgressSecond;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getOldTypeName() {
		return oldTypeName;
	}

	public void setOldTypeName(String oldTypeName) {
		this.oldTypeName = oldTypeName;
	}

	@Override
	public String toString() {
		return "NeedType [typeCode=" + typeCode + ", typeName=" + typeName + ", isSelectedName=" + isSelectedName
				+ ", flag=" + flag + ", needProgressList=" + needProgressList + ", needProgressFirst="
				+ needProgressFirst + ", needProgressSecond=" + needProgressSecond + ", del=" + del + "]";
	}


}