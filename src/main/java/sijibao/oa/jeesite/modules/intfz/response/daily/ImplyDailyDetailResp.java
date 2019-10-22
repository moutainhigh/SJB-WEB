package sijibao.oa.jeesite.modules.intfz.response.daily;

import java.io.Serializable;
import java.util.List;

import com.sijibao.base.common.api.response.PagerResponse;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Jianghao Zhang
 */
@ApiModel
public class ImplyDailyDetailResp implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "提交人")
    private String createBy;
    @ApiModelProperty(value = "部门")
    private String createByDept;
    @ApiModelProperty(value = "提交时间")
    private long createTime;
    @ApiModelProperty(value = "今日工作")
    private String todayWork;
    @ApiModelProperty(value = "运力池建设")
    private String transportPoolBuild;
    @ApiModelProperty(value = "回访项目情况")
    private String revisitProjectStatus;
    @ApiModelProperty(value = "需要协助问题")
    private String needAssistProblem;
    @ApiModelProperty(value = "今日感想")
    private String todayThought;
    @ApiModelProperty(value = "备注")
    private String remarks;

    @ApiModelProperty(value = "项目(实施)情况")
    private PagerResponse<ProjectImplyStatusForDetail> projectImplementStatusPage;

    @ApiModelProperty(value = "发给谁")
    private List<String> sendToUserList;

    @ApiModelProperty(value = "抄送")
    private List<String> copyToList;


    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCreateByDept() {
        return createByDept;
    }

    public void setCreateByDept(String createByDept) {
        this.createByDept = createByDept;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getTodayWork() {
        return todayWork;
    }

    public void setTodayWork(String todayWork) {
        this.todayWork = todayWork;
    }

    public String getTransportPoolBuild() {
        return transportPoolBuild;
    }

    public void setTransportPoolBuild(String transportPoolBuild) {
        this.transportPoolBuild = transportPoolBuild;
    }

    public String getRevisitProjectStatus() {
        return revisitProjectStatus;
    }

    public void setRevisitProjectStatus(String revisitProjectStatus) {
        this.revisitProjectStatus = revisitProjectStatus;
    }

    public String getNeedAssistProblem() {
        return needAssistProblem;
    }

    public void setNeedAssistProblem(String needAssistProblem) {
        this.needAssistProblem = needAssistProblem;
    }

    public String getTodayThought() {
        return todayThought;
    }

    public void setTodayThought(String todayThought) {
        this.todayThought = todayThought;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public PagerResponse<ProjectImplyStatusForDetail> getProjectImplementStatusPage() {
        return projectImplementStatusPage;
    }

    public void setProjectImplementStatusPage(PagerResponse<ProjectImplyStatusForDetail> projectImplementStatusPage) {
        this.projectImplementStatusPage = projectImplementStatusPage;
    }

    public List<String> getSendToUserList() {
        return sendToUserList;
    }

    public void setSendToUserList(List<String> sendToUserList) {
        this.sendToUserList = sendToUserList;
    }

    public List<String> getCopyToList() {
        return copyToList;
    }

    public void setCopyToList(List<String> copyToList) {
        this.copyToList = copyToList;
    }

    @Override
    public String toString() {
        return "ImplyDailyDetailResp{" +
                "createBy='" + createBy + '\'' +
                ", createByDept='" + createByDept + '\'' +
                ", createTime=" + createTime +
                ", todayWork='" + todayWork + '\'' +
                ", transportPoolBuild='" + transportPoolBuild + '\'' +
                ", revisitProjectStatus='" + revisitProjectStatus + '\'' +
                ", needAssistProblem='" + needAssistProblem + '\'' +
                ", todayThought='" + todayThought + '\'' +
                ", remarks='" + remarks + '\'' +
                ", projectImplementStatusPage=" + projectImplementStatusPage +
                ", sendToUserList=" + sendToUserList +
                ", copyToList=" + copyToList +
                '}';
    }
}
