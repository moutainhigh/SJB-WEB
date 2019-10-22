package sijibao.oa.jeesite.modules.intfz.request.bi;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "查询客户/项目/企业关联关系数据，请求入参")
public class QueryCpcRelationForBIReq implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "更新时间-起始")
    private Date beginTime;

    @ApiModelProperty(value = "更新时间-截止")
    private Date endTime;

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
