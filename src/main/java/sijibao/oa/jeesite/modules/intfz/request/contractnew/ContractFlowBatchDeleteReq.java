package sijibao.oa.jeesite.modules.intfz.request.contractnew;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 合同审核批量删除请求参数
 */
@ApiModel(value = "合同审核批量删除请求参数")
public class ContractFlowBatchDeleteReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "封装id的数组", required = true)
    private List<MainIdWrapper> idList;

    public List<MainIdWrapper> getIdList() {
        return idList;
    }

    public void setIdList(List<MainIdWrapper> idList) {
        this.idList = idList;
    }

    public ContractFlowBatchDeleteReq() {
    }

    @Override
    public String toString() {

        StringBuilder s = new StringBuilder("[");
        for (MainIdWrapper c : idList) {
            s.append("{contractFlowId:").append(c.getContractFlowId()).append(",");
            s.append("procInsId:").append(c.getContractFlowId()).append("},");
        }

        return s.append("]").toString();
    }
}
