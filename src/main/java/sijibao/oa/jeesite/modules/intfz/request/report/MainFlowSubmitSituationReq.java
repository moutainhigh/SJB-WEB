package sijibao.oa.jeesite.modules.intfz.request.report;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="单据提交情况请求对象")
public class MainFlowSubmitSituationReq implements Serializable {
    @ApiModelProperty(value="年份")
    private String year;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "MainFlowSubmitSituationReq{" +
                "year='" + year + '\'' +
                '}';
    }
}
