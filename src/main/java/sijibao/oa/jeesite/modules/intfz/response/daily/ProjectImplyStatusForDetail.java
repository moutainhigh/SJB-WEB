package sijibao.oa.jeesite.modules.intfz.response.daily;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Jianghao Zhang
 */
@ApiModel
public class ProjectImplyStatusForDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "项目名称")
    private String projectName;
    @ApiModelProperty(value = "节点名称")
    private String nodeName;
    @ApiModelProperty(value = "节点具体地址")
    private String nodeAddress;
    @ApiModelProperty(value = "节点人数")
    private int nodeEmpNum;
    @ApiModelProperty(value = "节点具体人员name")
    private String nodeEmpNames;
    @ApiModelProperty(value = "是否有异常情况")
    private String hasAbnormalStatus;
    @ApiModelProperty(value = "具体异常说明")
    private String anomalyDescription;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
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

    public int getNodeEmpNum() {
        return nodeEmpNum;
    }

    public void setNodeEmpNum(int nodeEmpNum) {
        this.nodeEmpNum = nodeEmpNum;
    }

    public String getNodeEmpNames() {
        return nodeEmpNames;
    }

    public void setNodeEmpNames(String nodeEmpNames) {
        this.nodeEmpNames = nodeEmpNames;
    }

    public String getHasAbnormalStatus() {
        return hasAbnormalStatus;
    }

    public void setHasAbnormalStatus(String hasAbnormalStatus) {
        this.hasAbnormalStatus = hasAbnormalStatus;
    }

    public String getAnomalyDescription() {
        return anomalyDescription;
    }

    public void setAnomalyDescription(String anomalyDescription) {
        this.anomalyDescription = anomalyDescription;
    }
}
