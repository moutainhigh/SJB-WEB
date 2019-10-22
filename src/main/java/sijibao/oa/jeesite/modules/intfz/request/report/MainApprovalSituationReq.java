package sijibao.oa.jeesite.modules.intfz.request.report;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="单据审批情况请求对象")
public class MainApprovalSituationReq implements Serializable {
    @ApiModelProperty(value="月份")
    private long month;

    public long getMonth() {
        return month;
    }

    public void setMonth(long month) {
        this.month = month;
    }

    @Override
    public String toString() {
        return "MainApprovalSituationReq{" +
                "month=" + month +
                '}';
    }
}
