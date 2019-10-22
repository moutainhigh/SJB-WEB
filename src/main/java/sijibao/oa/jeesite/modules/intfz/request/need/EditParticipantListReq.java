package sijibao.oa.jeesite.modules.intfz.request.need;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Jianghao Zhang
 */
@ApiModel
public class EditParticipantListReq {

    @ApiModelProperty(value = "协作ID", required = true)
    private String needFlowId;
    @ApiModelProperty(value = "用户ID列表", required = true)
    private List<String> userIdList;

    public String getNeedFlowId() {
        return needFlowId;
    }

    public void setNeedFlowId(String needFlowId) {
        this.needFlowId = needFlowId;
    }

    public List<String> getUserIdList() {
        return userIdList;
    }

    public void setUserIdList(List<String> userIdList) {
        this.userIdList = userIdList;
    }

    @Override
    public String toString() {
        StringBuilder userIdStr = new StringBuilder("[");
        if (userIdList != null) {
            for (String s : userIdList) {
                userIdStr.append(s).append(", ");
            }
//            userIdStr = new StringBuilder(userIdStr.substring(0, userIdStr.length() - 2));
        }
        userIdStr.append("]");
        return "EditParticipantListReq{" +
                "needFlowId='" + needFlowId + '\'' +
                ", userIdList=[" + userIdStr.toString() +
                "]}";
    }
}
