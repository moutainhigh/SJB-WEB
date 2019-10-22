package sijibao.oa.jeesite.modules.intfz.request.report;

import com.sijibao.oa.modules.intfz.request.common.PageRequest;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 项目科目明细费用报表接收对象
 * @author xby
 */
@ApiModel(value="项目科目明细费用报表接收对象")
public class ProjectSubjectCostReportRequest extends PageRequest{
	
	@ApiModelProperty(value="年份")
	private String year;  //年份
	@ApiModelProperty(value="项目ID")
	private String projectId; //项目ID
	
	
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	@Override
	public String toString() {
		return "ProjectSubjectCostReportRequest [year=" + year + ", projectId=" + projectId + "]";
	}
	
}
