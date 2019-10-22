package sijibao.oa.jeesite.modules.intfz.request.daily;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Jianghao Zhang
 */
@ApiModel
public class SaveImplyDailyReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "今日工作", required = true)
    private String todayWork;
    @ApiModelProperty(value = "运力池建设", required = true)
    private String transportPoolBuild;
    @ApiModelProperty(value = "回访项目情况", required = true)
    private String revisitProjectStatus;
    @ApiModelProperty(value = "需要协助问题", required = true)
    private String needAssistProblem;
    @ApiModelProperty(value = "今日感想", required = true)
    private String todayThought;
    @ApiModelProperty(value = "备注")
    private String remarks;

    @ApiModelProperty(value = "项目(实施)情况", required = true)
    private List<ProjectImplyStatusForAdd> projectImplementStatusList;

    @ApiModelProperty(value = "发给谁", required = true)
    private List<String> sendToUserList;

    @ApiModelProperty(value = "抄送")
    private List<String> copyToList;
    private String producSide;//产品端

    public String getProducSide() {
        return producSide;
    }

    public void setProducSide(String producSide) {
        this.producSide = producSide;
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

    public List<ProjectImplyStatusForAdd> getProjectImplementStatusList() {
        return projectImplementStatusList;
    }

    public void setProjectImplementStatusList(List<ProjectImplyStatusForAdd> projectImplementStatusList) {
        this.projectImplementStatusList = projectImplementStatusList;
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
        return "SaveImplyDailyReq{" +
                "todayWork='" + todayWork + '\'' +
                ", transportPoolBuild='" + transportPoolBuild + '\'' +
                ", revisitProjectStatus='" + revisitProjectStatus + '\'' +
                ", needAssistProblem='" + needAssistProblem + '\'' +
                ", todayThought='" + todayThought + '\'' +
                ", remarks='" + remarks + '\'' +
                ", projectImplementStatusList=" + projectImplementStatusList +
                ", sendToUserList=" + sendToUserList +
                ", copyToList=" + copyToList +
                '}';
    }
}
