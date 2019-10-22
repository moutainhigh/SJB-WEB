package sijibao.oa.jeesite.modules.intfz.response.need;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Jianghao Zhang
 */
@ApiModel
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QueryNeedFlowListWebResp {
    @ApiModelProperty(value = "协作ID")
    private String id;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "类型name")
    private String type;

    @ApiModelProperty(value = "类型ID")
    private String typeId;

    @ApiModelProperty(value = "发起人")
    private String initiator;

    @ApiModelProperty(value = "标签")
    private List<String> label;

    @ApiModelProperty(value = "进度状态")
    private String progressStatus;

    @ApiModelProperty(value = "当前负责人")
    private String curPrincipal;

    @ApiModelProperty(value = "状态变更时间")
    private long statusChangeTime;

    @ApiModelProperty(value = "可见的操作按钮，1评论，2增加进度，3下级负责人，4编辑参与人")
    private List<String> operationList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getInitiator() {
        return initiator;
    }

    public void setInitiator(String initiator) {
        this.initiator = initiator;
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

    public List<String> getOperationList() {
        return operationList;
    }

    public void setOperationList(List<String> operationList) {
        this.operationList = operationList;
    }
}
