package sijibao.oa.jeesite.modules.intfz.request.custinfo;

import java.util.List;

import com.sijibao.oa.modules.oa.entity.CustLinkmanHis;

import io.swagger.annotations.ApiModelProperty;

/**
 * 保存联系人变更历史对象
 *
 * @author zhanglianhui
 * @Date 2019/9/26 16:27
 */

public class CustLinkmanHisSaveReq {

    @ApiModelProperty(value = "主客户id")
    private String mainCustId;

    @ApiModelProperty(value = "联系人集合")
    private List<CustLinkmanHis> custLinkmanHisList;

    public String getMainCustId() {
        return mainCustId;
    }

    public void setMainCustId(String mainCustId) {
        this.mainCustId = mainCustId;
    }

    public List<CustLinkmanHis> getCustLinkmanHisList() {
        return custLinkmanHisList;
    }

    public void setCustLinkmanHisList(List<CustLinkmanHis> custLinkmanHisList) {
        this.custLinkmanHisList = custLinkmanHisList;
    }

    @Override
    public String toString() {
        return "CustLinkmanHisSaveReq{" + "mainCustId='" + mainCustId + '\'' + ", custLinkmanHisList="
                + custLinkmanHisList + '}';
    }
}
