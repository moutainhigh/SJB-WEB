package sijibao.oa.jeesite.modules.intfz.response.intfzwebresp.projectinfo;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author Jianghao Zhang
 */
public class ProjectImplyStatusResp implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "日期")
    private long date;
    @ApiModelProperty(value = "汇报人")
    private String reporter;
    @ApiModelProperty(value = "节点")
    private String nodeName;
    @ApiModelProperty(value = "节点具体地址")
    private String nodeAddress;
    @ApiModelProperty(value = "具体异常说明")
    private String anomalyDescription;

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getReporter() {
        return reporter;
    }

    public void setReporter(String reporter) {
        this.reporter = reporter;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getNodeAddress() {
        return nodeAddress;
    }

    public void setNodeAddress(String nodeAddress) {
        this.nodeAddress = nodeAddress;
    }

    public String getAnomalyDescription() {
        return anomalyDescription;
    }

    public void setAnomalyDescription(String anomalyDescription) {
        this.anomalyDescription = anomalyDescription;
    }
}
