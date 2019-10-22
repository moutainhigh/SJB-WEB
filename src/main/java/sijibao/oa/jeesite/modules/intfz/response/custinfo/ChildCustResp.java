package sijibao.oa.jeesite.modules.intfz.response.custinfo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @description: 子客户信息
 * @author: wanxb
 * @create: 2019-10-15 14:25
 **/
@ApiModel(value="客户信息--子客户返回对象")
public class ChildCustResp implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value="子客户id")
    private String childCustId ;  //子客户id
    @ApiModelProperty(value="子客户名称")
    private String childCustName ;		// 子客户名称

    public String getChildCustId() {
        return childCustId;
    }

    public void setChildCustId(String childCustId) {
        this.childCustId = childCustId;
    }

    public String getChildCustName() {
        return childCustName;
    }

    public void setChildCustName(String childCustName) {
        this.childCustName = childCustName;
    }
}
