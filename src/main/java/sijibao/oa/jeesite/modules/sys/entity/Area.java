/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.sys.entity;

import com.sijibao.oa.common.persistence.TreeEntity;
import com.sijibao.oa.common.utils.StringUtils;
import com.sijibao.oa.modules.sys.utils.DictUtils;

/**
 * 区域实体对象
 * @author wanxiongbo
 * @version 2018-03-22
 */
public class Area extends TreeEntity<Area> {
	
	private static final long serialVersionUID = 1L;
	private String code;		// code
	private String type;		// type
	private String grade;		// grade
	private String gradeName;
	
	public String getGradeName() {
		if(StringUtils.isNotBlank(grade)){
			return DictUtils.getDictLabel(grade, "oa_city_grade", null);
		}
		return gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}

	public Area() {
		super();
	}
	
	public Area(String id) {
		super(id);
	}
	@Override
	public Area getParent() {
		return parent;
	}
	@Override
	public void setParent(Area parent) {
		this.parent = parent;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}

}