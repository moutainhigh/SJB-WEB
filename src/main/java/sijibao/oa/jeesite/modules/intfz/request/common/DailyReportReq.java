package sijibao.oa.jeesite.modules.intfz.request.common;

import java.util.List;

import com.sijibao.oa.modules.intfz.response.daily.MarketDailyExport;

import io.swagger.annotations.ApiModel;

/**
 * 登录请求对象 
 * @author Petter
 */
@ApiModel
public class DailyReportReq {

	private String fileName; //文件名称
	private String sheetName;//sheet页名称
	private List<MarketDailyExport> list;//市场日报列表&实施日报表
	private String dailyTemplate;//日报模板
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getSheetName() {
		return sheetName;
	}
	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}
	public String getDailyTemplate() {
		return dailyTemplate;
	}
	public void setDailyTemplate(String dailyTemplate) {
		this.dailyTemplate = dailyTemplate;
	}
	public List<MarketDailyExport> getList() {
		return list;
	}
	public void setList(List<MarketDailyExport> list) {
		this.list = list;
	}
	@Override
	public String toString() {
		return "DailyReportReq [fileName=" + fileName + ", sheetName=" + sheetName + ", list=" + list
				+ ", dailyTemplate=" + dailyTemplate + "]";
	}

	
}
