package sijibao.oa.jeesite.modules.intfz.response.intfzwebresp.projectinfo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 项目管理--明细返回对象
 *
 * @author wuys
 */
@ApiModel(value = "项目管理--节点明细返回对象")
public class MainProjectNodeDetailResponse implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "节点名")
    private String nodeName;// 节点名
    @ApiModelProperty(value = "节点地址")
    private String nodeAddress; // 节点地址
    @ApiModelProperty(value = "节点ID")
    private String nodeId; //节点ID
    @ApiModelProperty(value = "节点地址纬度")
    private String lat;
    @ApiModelProperty(value = "节点地址经度")
    private String lng;

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

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    @Override
    public String toString() {
        return "MainProjectNodeDetailResponse{" +
                "nodeName='" + nodeName + '\'' +
                ", nodeAddress='" + nodeAddress + '\'' +
                ", nodeId='" + nodeId + '\'' +
                ", lat='" + lat + '\'' +
                ", lng='" + lng + '\'' +
                '}';
    }
}
