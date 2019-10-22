package sijibao.oa.jeesite.modules.intfz.request.intfzwebreq.projectinfo;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author Jianghao Zhang
 */
public class ProjectImplyStatusExportReq implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "项目id")
    private String projectId;
    @ApiModelProperty(value = "开始时间")
    private long beginTime;
    @ApiModelProperty(value = "结束时间")
    private long endTime;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public long getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(long beginTime) {
        this.beginTime = beginTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

}
