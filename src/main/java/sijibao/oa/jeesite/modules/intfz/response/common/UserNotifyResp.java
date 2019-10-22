package sijibao.oa.jeesite.modules.intfz.response.common;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

/**
 *
 */
public class UserNotifyResp implements Serializable {
    @ApiModelProperty(value="待我审批条数", dataType="int")
    private int todoCount;
    @ApiModelProperty(value="我的申请条数", dataType="int")
    private int myApplyCount;
    @ApiModelProperty(value="异常单条数", dataType="int")
    private int errorStockOrderCount;

    public int getTodoCount() {
        return todoCount;
    }
    public void setTodoCount(int todoCount) {
        this.todoCount = todoCount;
    }
    public int getMyApplyCount() {
        return myApplyCount;
    }
    public void setMyApplyCount(int myApplyCount) {
        this.myApplyCount = myApplyCount;
    }
    public int getErrorStockOrderCount() {
        return errorStockOrderCount;
    }
    public void setErrorStockOrderCount(int errorStockOrderCount) {
        this.errorStockOrderCount = errorStockOrderCount;
    }
}
