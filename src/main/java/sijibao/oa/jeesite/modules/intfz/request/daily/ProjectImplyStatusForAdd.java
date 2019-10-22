package sijibao.oa.jeesite.modules.intfz.request.daily;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 新增项目实施情况的请求数据
 *
 * @author Jianghao Zhang
 */
@ApiModel
public class ProjectImplyStatusForAdd implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "项目ID", required = true)
    private String projectId;
    @ApiModelProperty(value = "项目名称", required = true)
    private String projectName;
    @ApiModelProperty(value = "节点ID", required = true)
    private String nodeId;
    @ApiModelProperty(value = "节点名称", required = true)
    private String nodeName;
    @ApiModelProperty(value = "节点具体地址", required = true)
    private String nodeAddress;
    @ApiModelProperty(value = "节点人数", required = true)
    private int nodeEmpNum;
    @ApiModelProperty(value = "节点具体人员name", required = true)
    private String nodeEmpNames;
    @ApiModelProperty(value = "是否有异常情况，0无，1有", required = true)
    private String hasAbnormalStatus;
    @ApiModelProperty(value = "具体异常说明", required = true)
    private String anomalyDescription;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
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
