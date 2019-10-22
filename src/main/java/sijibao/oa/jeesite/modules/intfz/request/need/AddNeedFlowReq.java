package sijibao.oa.jeesite.modules.intfz.request.need;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Jianghao Zhang
 */
@ApiModel(description = "协作功能-新增-参数")
public class AddNeedFlowReq {

    @ApiModelProperty(value = "标题", required = true)
    private String title;
    @ApiModelProperty(value = "类型", required = true)
    private String type;
    @ApiModelProperty(value = "标签", required = true)
    private List<String> labelList;
    @ApiModelProperty(value = "描述", required = true)
    private String description;
    @ApiModelProperty(value = "当前负责人", required = true)
    private String principal;
    @ApiModelProperty(value = "参与人列表", required = true)
    private List<String> participantList;
    private String producSide;//产品端

    public String getProducSide() {
        return producSide;
    }

    public void setProducSide(String producSide) {
        this.producSide = producSide;
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

    public List<String> getLabelList() {
        return labelList;
    }

    public void setLabelList(List<String> labelList) {
        this.labelList = labelList;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public List<String> getParticipantList() {
        return participantList;
    }

    public void setParticipantList(List<String> participantList) {
        this.participantList = participantList;
    }

    @Override
    public String toString() {
        StringBuilder participantStr = new StringBuilder("[");
        if (labelList != null) {
            for (String s : participantList) {
                participantStr.append(s).append(", ");
            }
//            participantStr = new StringBuilder(participantStr.substring(0, participantStr.length() - 2));
        }
        participantStr.append("]");

        StringBuilder labelStr = new StringBuilder("[");
        if (labelList != null) {
            for (String s : labelList) {
                labelStr.append(s).append(", ");
            }
//            labelStr = new StringBuilder(labelStr.substring(0, labelStr.length() - 2));
        }
        labelStr.append("]");

        return "AddNeedFlowReq ["
                + "title=" + title + ", "
                + "type=" + type + ", "
                + "labelList=" + labelStr.toString() + ", "
                + "description=" + description + ", "
                + "principal=" + principal + ", "
                + "participantList=" + participantStr.toString() + "]";
    }
}
