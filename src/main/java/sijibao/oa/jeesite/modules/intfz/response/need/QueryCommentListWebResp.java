package sijibao.oa.jeesite.modules.intfz.response.need;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Jianghao Zhang
 */
@ApiModel
public class QueryCommentListWebResp {

    @ApiModelProperty(value = "编号")
    private String no;
    @ApiModelProperty(value = "评论人")
    private String commentator;
    @ApiModelProperty(value = "时间")
    private long time;
    @ApiModelProperty(value = "评论内容")
    private String content;

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getCommentator() {
        return commentator;
    }

    public void setCommentator(String commentator) {
        this.commentator = commentator;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
