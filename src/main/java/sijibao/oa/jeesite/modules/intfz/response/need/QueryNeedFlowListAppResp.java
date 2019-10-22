package sijibao.oa.jeesite.modules.intfz.response.need;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Jianghao Zhang
 */
@ApiModel(description = "协作功能-列表-APP端返回结果")
public class QueryNeedFlowListAppResp {
    @ApiModelProperty(value = "协作ID")
    private String id;

    @ApiModelProperty(value = "发起人")
    private String initiator;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "标签")
    private List<String> label;

    @ApiModelProperty(value = "进度状态")
    private String progressStatus;

    @ApiModelProperty(value = "当前负责人")
    private String curPrincipal;

    @ApiModelProperty(value = "状态变更时间")
    private long statusChangeTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInitiator() {
        return initiator;
    }

    public void setInitiator(String initiator) {
        this.initiator = initiator;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getLabel() {
        return label;
    }

    public void setLabel(List<String> label) {
        this.label = label;
    }

    public String getProgressStatus() {
        return progressStatus;
    }

    public void setProgressStatus(String progressStatus) {
        this.progressStatus = progressStatus;
    }

    public String getCurPrincipal() {
        return curPrincipal;
    }

    public void setCurPrincipal(String curPrincipal) {
        this.curPrincipal = curPrincipal;
    }

    public long getStatusChangeTime() {
        return statusChangeTime;
    }

    public void setStatusChangeTime(long statusChangeTime) {
        this.statusChangeTime = statusChangeTime;
    }
}
