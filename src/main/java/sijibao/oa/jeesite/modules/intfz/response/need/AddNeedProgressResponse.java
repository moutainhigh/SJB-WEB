package sijibao.oa.jeesite.modules.intfz.response.need;

/**
 * 合同方配置信息放回对象
 * @author wanxb
 * @version 2018-11-23 09:41:38
 */
public class AddNeedProgressResponse{
	
	private String id;
	private String curNeedProgressName;
	private String isEnd;
	private MainNeedTypeResponse mainNeedTypeResponse;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCurNeedProgressName() {
		return curNeedProgressName;
	}
	public void setCurNeedProgressName(String curNeedProgressName) {
		this.curNeedProgressName = curNeedProgressName;
	}
	public String getIsEnd() {
		return isEnd;
	}
	public void setIsEnd(String isEnd) {
		this.isEnd = isEnd;
	}
	public MainNeedTypeResponse getMainNeedTypeResponse() {
		return mainNeedTypeResponse;
	}
	public void setMainNeedTypeResponse(MainNeedTypeResponse mainNeedTypeResponse) {
		this.mainNeedTypeResponse = mainNeedTypeResponse;
	}
	@Override
	public String toString() {
		return "AddNeedProgressResponse [id=" + id + ", curNeedProgressName=" + curNeedProgressName + ", isEnd=" + isEnd
				+ ", mainNeedTypeResponse=" + mainNeedTypeResponse + "]";
	}

	
}
