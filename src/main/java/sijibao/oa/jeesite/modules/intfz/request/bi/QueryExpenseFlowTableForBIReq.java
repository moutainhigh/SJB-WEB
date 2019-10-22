package sijibao.oa.jeesite.modules.intfz.request.bi;

import java.io.Serializable;
import java.sql.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "查询费用报销流程表oa_expense_flow数据，入参")
public class QueryExpenseFlowTableForBIReq implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "起始提交时间")
    private Date applyTimeBegin;
    @ApiModelProperty(value = "截止提交时间")
    private Date applyTimeEnd;
    @ApiModelProperty(value = "审批状态，对应的字典类型是expense_status")
    private String approveStatus;

    public Date getApplyTimeBegin() {
        return applyTimeBegin;
    }

    public void setApplyTimeBegin(Date applyTimeBegin) {
        this.applyTimeBegin = applyTimeBegin;
    }

    public Date getApplyTimeEnd() {
        return applyTimeEnd;
    }

    public void setApplyTimeEnd(Date applyTimeEnd) {
        this.applyTimeEnd = applyTimeEnd;
    }

    public String getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(String approveStatus) {
        this.approveStatus = approveStatus;
    }

}
