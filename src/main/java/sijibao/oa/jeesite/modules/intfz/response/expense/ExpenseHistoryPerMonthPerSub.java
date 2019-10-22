package sijibao.oa.jeesite.modules.intfz.response.expense;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModelProperty;

public class ExpenseHistoryPerMonthPerSub {

    @ApiModelProperty(value = "用户登录名")
    private String userLoginName;//用户登录名

    @ApiModelProperty(value = "标题")
    private String title;//标题

    @ApiModelProperty(value = "年月")
    private String time;//年月

    @ApiModelProperty(value = "一级科目id")
    private String firstSubCode;//一级科目id

    @ApiModelProperty(value = "二级科目id")
    private String secondSubCode;//二级科目id

    @ApiModelProperty(value = "一级科目名称")
    private String firstSubName;//一级科目名称

    @ApiModelProperty(value = "二级科目名称")
    private String secondSubName;//二级科目名称

    @ApiModelProperty(value = "金额")
    private BigDecimal amt;//金额

    @ApiModelProperty(value = "科目或金额是否超标")
    private String isExceed;//科目或金额是否超标

    public String getUserLoginName() {
        return userLoginName;
    }

    public void setUserLoginName(String userLoginName) {
        this.userLoginName = userLoginName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getFirstSubName() {
        return firstSubName;
    }

    public void setFirstSubName(String firstSubName) {
        this.firstSubName = firstSubName;
    }

    public String getSecondSubName() {
        return secondSubName;
    }

    public void setSecondSubName(String secondSubName) {
        this.secondSubName = secondSubName;
    }

    public BigDecimal getAmt() {
        return amt;
    }

    public void setAmt(BigDecimal amt) {
        this.amt = amt;
    }

    public String getIsExceed() {
        return isExceed;
    }

    public void setIsExceed(String isExceed) {
        this.isExceed = isExceed;
    }
}