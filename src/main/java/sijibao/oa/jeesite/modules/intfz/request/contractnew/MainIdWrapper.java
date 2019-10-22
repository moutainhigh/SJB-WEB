package sijibao.oa.jeesite.modules.intfz.request.contractnew;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class MainIdWrapper implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "合同审核流程业务ID", required = true)
    private String contractFlowId;
    @ApiModelProperty(value = "合同审核流程实例ID", required = true)
    private String procInsId;

    public MainIdWrapper() {
    }

    public String getContractFlowId() {
        return contractFlowId;
    }

    public void setContractFlowId(String contractFlowId) {
        this.contractFlowId = contractFlowId;
    }

    public String getProcInsId() {
        return procInsId;
    }

    public void setProcInsId(String procInsId) {
        this.procInsId = procInsId;
    }
}