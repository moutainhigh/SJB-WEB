package sijibao.oa.jeesite.modules.intfz.request.custinfo;

import com.sijibao.base.common.provider.entity.PagerBase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 客户联系人变更历史对象
 * 
 * @author zlh
 */
@ApiModel(value = "客户联系人变更历史对象")
public class CustLinkmanHisHandleReq extends PagerBase<CustLinkmanHisHandleReq> {
    @ApiModelProperty(value = "主客户Id")
    private String mainCustId; // 主客户Id
    @ApiModelProperty(value = "主客户名称或编号")
    private String mainCustName; // 主客户名称或编号
    @ApiModelProperty(value = "操作人")
    private String operateUserName; // 操作人
    @ApiModelProperty(value = "操作人部门")
    private String operateOfficeName; // 联系人邮箱
    @ApiModelProperty(value = "开始 搜索时间")
    private long beginTime = 0l; // 开始 搜索时间
    @ApiModelProperty(value = "结束 搜索时间")
    private long endTime = 0l; // 结束 搜索时间

    public String getMainCustId() {
        return mainCustId;
    }

    public void setMainCustId(String mainCustId) {
        this.mainCustId = mainCustId;
    }

    public String getMainCustName() {
        return mainCustName;
    }

    public void setMainCustName(String mainCustName) {
        this.mainCustName = mainCustName;
    }

    public String getOperateUserName() {
        return operateUserName;
    }

    public void setOperateUserName(String operateUserName) {
        this.operateUserName = operateUserName;
    }

    public String getOperateOfficeName() {
        return operateOfficeName;
    }

    public void setOperateOfficeName(String operateOfficeName) {
        this.operateOfficeName = operateOfficeName;
    }

    public long getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(long beginTime) {
        this.beginTime = beginTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "CustLinkmanHisHandleReq{" + "mainCustId='" + mainCustId + '\'' + ", mainCustName='" + mainCustName
                + '\'' + ", operateUserName='" + operateUserName + '\'' + ", operateOfficeName='" + operateOfficeName
                + '\'' + ", beginTime=" + beginTime + ", endTime=" + endTime + '}';
    }
}
