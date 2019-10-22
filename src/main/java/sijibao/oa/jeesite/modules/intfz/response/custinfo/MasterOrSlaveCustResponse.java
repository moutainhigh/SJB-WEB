package sijibao.oa.jeesite.modules.intfz.response.custinfo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @description: 归属客户信息
 * @author: xgx
 * @create: 2019-09-26 17:17
 **/
@ApiModel(value="归属客户信息")
public class MasterOrSlaveCustResponse implements Serializable {

    private static final long serialVersionUID = -5017849260887383773L;
    @ApiModelProperty(value="客户id")
    private String custId;
    @ApiModelProperty(value="客户名称")
    private String custName;

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

}
