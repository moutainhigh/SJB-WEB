package sijibao.oa.jeesite.modules.intfz.response.daily;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Jianghao Zhang
 */
@ApiModel
public class QueryProjectNodeListResp implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "节点id")
    private String nodeId;
    @ApiModelProperty(value = "节点name")
    private String nodeName;
    @ApiModelProperty(value = "节点具体地址")
    private String nodeAddress;

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

}
