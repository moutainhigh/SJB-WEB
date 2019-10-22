package sijibao.oa.jeesite.modules.intfz.request.expense;

import io.swagger.annotations.ApiModelProperty;

public class ExpenseHistoryQueryRequest {

    @ApiModelProperty(value = "报销申请人loginName")
    private String applyPerCode;

    public String getApplyPerCode() {
        return applyPerCode;
    }

    public void setApplyPerCode(String applyPerCode) {
        this.applyPerCode = applyPerCode;
    }
}