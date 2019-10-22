package sijibao.oa.jeesite.modules.intfz.request.need;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Jianghao Zhang
 */
@ApiModel(description = "APP端-查询协作分页列表-请求参数")
public class QueryNeedFlowListAppReq {

    @ApiModelProperty(value = "页数", required = true)
    private int pageNo;

    @ApiModelProperty(value = "每页数量", required = true)
    private int pageSize;

    @ApiModelProperty(value = "tab页，1我参与的，2我发起的，3全部", required = true)
    private String tab;

    @ApiModelProperty(value = "标题/描述")
    private String titleOrDescription;

    @ApiModelProperty(value = "类型")
    private String type;

    @ApiModelProperty(value = "标签（ID数组）")
    private List<String> label;

    @ApiModelProperty(value = "进度状态")
    private String progressStatus;

    @ApiModelProperty(value = "提交人用户ID")
    private String initiator;

    @ApiModelProperty(value = "当前负责人用户ID")
    private String curPrincipal;

    public String getTab() {
        return tab;
    }

    public void setTab(String tab) {
        this.tab = tab;
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

    public String getTitleOrDescription() {
        return titleOrDescription;
    }

    public void setTitleOrDescription(String titleOrDescription) {
        this.titleOrDescription = titleOrDescription;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getInitiator() {
        return initiator;
    }

    public void setInitiator(String initiator) {
        this.initiator = initiator;
    }

    public String getCurPrincipal() {
        return curPrincipal;
    }

    public void setCurPrincipal(String curPrincipal) {
        this.curPrincipal = curPrincipal;
    }

    @Override
    public String toString() {

        StringBuilder labelStr = new StringBuilder("[");
        if (label != null) {
            for (String s : label) {
                labelStr.append(s).append(", ");
            }
//            labelStr = new StringBuilder(labelStr.substring(0, labelStr.length() - 2));
        }
        labelStr.append("]");

        return "QueryNeedFlowListAppReq{" +
                "pageNo=" + pageNo +
                ", pageSize=" + pageSize +
                ", tab='" + tab + '\'' +
                ", titleOrDescription='" + titleOrDescription + '\'' +
                ", type='" + type + '\'' +
                ", label=" + labelStr.toString() +
                ", progressStatus='" + progressStatus + '\'' +
                ", initiator='" + initiator + '\'' +
                ", curPrincipal='" + curPrincipal + '\'' +
                '}';
    }
}
