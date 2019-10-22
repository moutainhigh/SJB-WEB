package sijibao.oa.jeesite.modules.intfz.request.daily;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Jianghao Zhang
 */
@ApiModel
public class QueryImplyDailyDetailReq implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "日志id", required = true)
    private String dailyId;
    @ApiModelProperty(value = "页数", required = true)
    private int pageNo;
    @ApiModelProperty(value = "每页条数", required = true)
    private int pageSize;

    public String getDailyId() {
        return dailyId;
    }

    public void setDailyId(String dailyId) {
        this.dailyId = dailyId;
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
}
