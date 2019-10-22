package sijibao.oa.jeesite.modules.intfz.request.custinfo;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;

/**
 * @description: 批量修改客户
 * @author: wanxb
 * @create: 2019-09-29 09:38
 **/
public class BatchUpdateReq {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value="清结算ID")
    private String accountLeaderId;//清结算ID
    @ApiModelProperty(value="商务助理ID")
    private String businessAssistantId;		// 商务助理ID
    @ApiModelProperty(value="VIP客服ID")
    private String vipCustomerId;		// VIP客服ID
    @ApiModelProperty(value="所选客户id")
    private List<String> custIds;//所选客户id

    public String getAccountLeaderId() {
        return accountLeaderId;
    }

    public void setAccountLeaderId(String accountLeaderId) {
        this.accountLeaderId = accountLeaderId;
    }

    public String getBusinessAssistantId() {
        return businessAssistantId;
    }

    public void setBusinessAssistantId(String businessAssistantId) {
        this.businessAssistantId = businessAssistantId;
    }

    public String getVipCustomerId() {
        return vipCustomerId;
    }

    public void setVipCustomerId(String vipCustomerId) {
        this.vipCustomerId = vipCustomerId;
    }

    public List<String> getCustIds() {
        return custIds;
    }

    public void setCustIds(List<String> custIds) {
        this.custIds = custIds;
    }

    @Override
    public String toString() {
        return "BatchUpdateReq{" +
                "accountLeaderId='" + accountLeaderId + '\'' +
                ", businessAssistantId='" + businessAssistantId + '\'' +
                ", vipCustomerId='" + vipCustomerId + '\'' +
                ", custIds=" + custIds +
                '}';
    }
}
