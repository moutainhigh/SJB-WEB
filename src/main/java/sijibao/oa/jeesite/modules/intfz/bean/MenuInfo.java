package sijibao.oa.jeesite.modules.intfz.bean;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;

/**
 * 菜单基本信息
 * @author Petter
 */
@SuppressWarnings("serial")
@ApiModel
public class MenuInfo implements Serializable {

	private String id;
	private String path;
	private String name;
	private String component;
	private String parentId;
	private String redirect;
	private MetaInfo meta;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getComponent() {
		return component;
	}
	public void setComponent(String component) {
		this.component = component;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getRedirect() {
		return redirect;
	}
	public void setRedirect(String redirect) {
		this.redirect = redirect;
	}
	public MetaInfo getMeta() {
		return meta;
	}
	public void setMeta(MetaInfo meta) {
		this.meta = meta;
	}
	@Override
	public String toString() {
		return "MenuInfo [id=" + id + ", path=" + path + ", name=" + name + ", component=" + component + ", parentId="
				+ parentId + ", redirect=" + redirect + ", meta=" + meta.toString() + "]";
	}
	
}
