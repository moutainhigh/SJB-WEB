package sijibao.oa.jeesite.modules.intfz.request.need;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Jianghao Zhang
 */
@ApiModel
public class AddCommentReq {

    @ApiModelProperty(value = "协作ID", required = true)
    private String needFlowId;
    @ApiModelProperty(value = "评论内容", required = true)
    private String content;

    public String getNeedFlowId() {
        return needFlowId;
    }

    public void setNeedFlowId(String needFlowId) {
        this.needFlowId = needFlowId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "AddCommentReq [" + "needFlowId=" + needFlowId + ", " + "content=" + content + "]";
    }
}
