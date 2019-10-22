package sijibao.oa.jeesite.modules.intfz.response.bi;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "项目节点表oa_project_node（全字段）")
public class ProjectNodeRespForBI implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private String id;//主键

    @ApiModelProperty(value = "项目id")
    private String projectId;// 项目id

    @ApiModelProperty(value = "节点名")
    private String nodeName;//节点名

    @ApiModelProperty(value = "节点地址")
    private String nodeAddress;//节点地址

    @ApiModelProperty(value = "节点地址经度")
    private String lng;//节点地址经度

    @ApiModelProperty(value = "节点地址纬度")
    private String lat;//节点地址纬度

    @ApiModelProperty(value = "排序用序号")
    private Integer orderNum;//排序用序号

    @ApiModelProperty(value = "创建人")
    private String createBy;//创建人

    @ApiModelProperty(value = "创建时间")
    private Date createTime;// 创建时间

    @ApiModelProperty(value = "更新人")
    private String updateBy;//更新人

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;//更新时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
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

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
