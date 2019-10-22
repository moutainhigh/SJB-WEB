package sijibao.oa.jeesite.modules.intfz.request.intfzwebreq.projectinfo;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author Jianghao Zhang
 */
public class ProjectImplyStatusReq implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "项目id")
    private String projectId;
    @ApiModelProperty(value = "开始时间")
    private long beginTime;
    @ApiModelProperty(value = "结束时间")
    private long endTime;
    @ApiModelProperty(value = "页数")
    private int pageNo;
    @ApiModelProperty(value = "每页数量")
    private int pageSize;

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

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
