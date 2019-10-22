package sijibao.oa.jeesite.modules.intfz.request.bi;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "获取员工状态变更表oa_employee_statusrecord数据，入参")
public class QueryEmployeeStatusRecordTableForBIReq implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "起始提交时间")
    private Date beginTime;

    @ApiModelProperty(value = "截止提交时间")
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
