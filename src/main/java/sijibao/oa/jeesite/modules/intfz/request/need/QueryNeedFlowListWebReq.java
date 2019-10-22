package sijibao.oa.jeesite.modules.intfz.request.need;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Jianghao Zhang
 */
@ApiModel
public class QueryNeedFlowListWebReq {
    @ApiModelProperty(value = "tab页，1我参与的，2我发起的，3全部", required = true)
    private String tab;
    @ApiModelProperty(value = "页数", required = true)
    private int pageNo;
    @ApiModelProperty(value = "每页数量", required = true)
    private int pageSize;

    @ApiModelProperty(value = "标题/描述/进度状态")
    private String titleOrDescOrProgress;

    @ApiModelProperty(value = "状态变更时间-起始时间")
    private long statusChangeTimeBegin;

    @ApiModelProperty(value = "状态变更时间-截止时间")
    private long statusChangeTimeEnd;

    @ApiModelProperty(value = "人员类型，1提交人，2当前负责人（单选）")
    private String personType;

    @ApiModelProperty(value = "提交人/当前负责人的部门ID或用户ID（单选）")
    private String deptOrUserId;

    @ApiModelProperty(value = "类型（单选）")
    private String type;

    @ApiModelProperty(value = "标签（多选）")
    private List<String> label;

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

    public String getTitleOrDescOrProgress() {
        return titleOrDescOrProgress;
    }

    public void setTitleOrDescOrProgress(String titleOrDescOrProgress) {
        this.titleOrDescOrProgress = titleOrDescOrProgress;
    }

    public long getStatusChangeTimeBegin() {
        return statusChangeTimeBegin;
    }

    public void setStatusChangeTimeBegin(long statusChangeTimeBegin) {
        this.statusChangeTimeBegin = statusChangeTimeBegin;
    }

    public long getStatusChangeTimeEnd() {
        return statusChangeTimeEnd;
    }

    public void setStatusChangeTimeEnd(long statusChangeTimeEnd) {
        this.statusChangeTimeEnd = statusChangeTimeEnd;
    }

    public String getPersonType() {
        return personType;
    }

    public void setPersonType(String personType) {
        this.personType = personType;
    }

    public String getDeptOrUserId() {
        return deptOrUserId;
    }

    public void setDeptOrUserId(String deptOrUserId) {
        this.deptOrUserId = deptOrUserId;
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

        return "QueryNeedFlowListWebReq{" +
                "tab='" + tab + '\'' +
                ", pageNo=" + pageNo +
                ", pageSize=" + pageSize +
                ", titleOrDescOrProgress='" + titleOrDescOrProgress + '\'' +
                ", statusChangeTimeBegin=" + statusChangeTimeBegin +
                ", statusChangeTimeEnd=" + statusChangeTimeEnd +
                ", personType='" + personType + '\'' +
                ", deptOrUserId='" + deptOrUserId + '\'' +
                ", type='" + type + '\'' +
                ", label=" + labelStr.toString() +
                '}';
    }
}
