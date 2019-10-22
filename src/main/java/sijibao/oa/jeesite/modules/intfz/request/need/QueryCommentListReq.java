package sijibao.oa.jeesite.modules.intfz.request.need;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Jianghao Zhang
 */
@ApiModel
public class QueryCommentListReq {
    @ApiModelProperty(value = "协作ID", required = true)
    private String needFlowId;
    @ApiModelProperty(value = "页数", required = true)
    private int pageNo;
    @ApiModelProperty(value = "每页数量", required = true)
    private int pageSize;

    public String getNeedFlowId() {
        return needFlowId;
    }

    public void setNeedFlowId(String needFlowId) {
        this.needFlowId = needFlowId;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return "QueryCommentListReq{" +
                "needFlowId='" + needFlowId + '\'' +
                ", pageNo=" + pageNo +
                ", pageSize=" + pageSize +
                '}';
    }
}
