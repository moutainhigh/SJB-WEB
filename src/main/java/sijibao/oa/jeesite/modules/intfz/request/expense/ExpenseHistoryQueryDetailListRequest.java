package sijibao.oa.jeesite.modules.intfz.request.expense;

import io.swagger.annotations.ApiModelProperty;

public class ExpenseHistoryQueryDetailListRequest {

    @ApiModelProperty(value = "用户登录名")
    private String userLoginName;

    @ApiModelProperty(value = "时间")
    private String time;

    @ApiModelProperty(value = "一级科目编码（id）")
    private String firstSubCode;

    @ApiModelProperty(value = "二级科目编码（id）")
    private String secondSubCode;

    public String getUserLoginName() {
        return userLoginName;
    }

    public void setUserLoginName(String userLoginName) {
        this.userLoginName = userLoginName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFirstSubCode() {
        return firstSubCode;
    }

    public void setFirstSubCode(String firstSubCode) {
        this.firstSubCode = firstSubCode;
    }

    public String getSecondSubCode() {
        return secondSubCode;
    }

    public void setSecondSubCode(String secondSubCode) {
        this.secondSubCode = secondSubCode;
    }
}