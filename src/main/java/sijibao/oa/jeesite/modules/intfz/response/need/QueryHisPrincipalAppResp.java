package sijibao.oa.jeesite.modules.intfz.response.need;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Jianghao Zhang
 */
@ApiModel
public class QueryHisPrincipalAppResp {
    @ApiModelProperty(value = "开始负责时间")
    private long startTime;
    @ApiModelProperty(value = "人员姓名")
    private String name;

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
