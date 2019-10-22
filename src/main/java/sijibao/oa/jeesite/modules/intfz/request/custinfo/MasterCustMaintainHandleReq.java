package sijibao.oa.jeesite.modules.intfz.request.custinfo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description: 主客户维护管理请求对象
 * @author: xgx
 * @create: 2019-09-27 11:02
 **/
@ApiModel(value="主客户维护管理请求对象")
@Data
public class MasterCustMaintainHandleReq implements Serializable {

    private static final long serialVersionUID = 2217407604220231295L;
    @ApiModelProperty(value="页数")
    private int pageNo;//页数
    @ApiModelProperty(value="每页数量")
    private int pageSize;//每页数量
    @ApiModelProperty(value="客户id")
    private String custId ;//客户id
    @ApiModelProperty(value="搜索类型：1-记录，2-处理")
    private String searchType ;//搜索类型：1-记录，2-处理
    @ApiModelProperty(value="维护开始时间")
    private Long maintainStartTime;
    @ApiModelProperty(value="维护结束时间")
    private Long maintainEndTime;
    @ApiModelProperty(value="维护人")
    private String custMaintenanceMan;
    @ApiModelProperty(value="维护人部门")
    private String maintainerDeptName;
}
