package sijibao.oa.jeesite.modules.intfz.web.LuckPersonel;

import java.io.Serializable;
import java.util.List;

public class LuckyPerson implements Serializable, Comparable<LuckyPerson>{

	private static final long serialVersionUID = 1L;

	private String openid;
	private String username;
	private String group;
	private String phone;
	private Long luckyno;
	private String img;
	
	private List<LuckyPerson> list;
	
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @Title: getGroup 
	 * @Description: 
	 * <select class="weui_select" name="select1">
	 * 	<option selected="" value="01">白求恩士官学校</option>
	 * 	<option value="02">湖北科技学院(实习队)</option>
	 * 	<option value="03">湖北科技学院(见习队)</option>
	 * 	<option value="04">湖北中医药大学</option>
	 * 	<option value="05">第二军医大学</option>
	 * 	<option value="06">南方医科大学</option>
	 * </select>
	 * @return
	 */
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Long getLuckyno() {
		return luckyno;
	}
	public void setLuckyno(Long luckyno) {
		this.luckyno = luckyno;
	}
	
	public List<LuckyPerson> getList() {
		return list;
	}
	public void setList(List<LuckyPerson> list) {
		this.list = list;
	}
	@Override
	public int compareTo(LuckyPerson o) {
		return o.getOpenid().compareTo(this.getOpenid());
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	@Override
	public String toString() {
		return "LuckyPerson [openid=" + openid + ", username=" + username + ", group=" + group + ", phone=" + phone
				+ ", luckyno=" + luckyno + ", img=" + img + ", list=" + list + "]";
	}
	
}
