package sijibao.oa.jeesite.modules.intfz.bean;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;

@SuppressWarnings("serial")
@ApiModel
public class MetaInfo implements Serializable {

	private String title;
	private String icon;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	@Override
	public String toString() {
		return "MetaInfo [title=" + title + ", icon=" + icon + "]";
	}
	
}
