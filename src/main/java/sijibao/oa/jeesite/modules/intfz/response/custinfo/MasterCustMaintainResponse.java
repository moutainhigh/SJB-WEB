package sijibao.oa.jeesite.modules.intfz.response.custinfo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description: 主客户维护管理--主动维护列表返回对象
 * @author: xgx
 * @create: 2019-09-27 11:24
 **/
@ApiModel(value="主客户维护管理--主动维护列表返回对象")
@Data
public class MasterCustMaintainResponse implements Serializable {

    private static final long serialVersionUID = 4906799678109747995L;

    @ApiModelProperty(value="维护时间:yyyy-MM-dd HH:mm:ss")
    private long custMaintenanceDate = 0l;		// 维护时间:yyyy-MM-dd HH:mm:ss
    @ApiModelProperty(value="归属客户")
    private String custName;		// 维护人
    @ApiModelProperty(value="维护人")
    private String custMaintenanceMan;		// 维护人
    @ApiModelProperty(value="维护人部门")
    private String maintainerDeptName;		// 维护人部门
    @ApiModelProperty(value="维护内容")
    private String custMaintenanceContent;		// 维护内容
    @ApiModelProperty(value="拜访类型:1上门拜访，2电话拜访")
    private String visitType;//拜访类型
    @ApiModelProperty(value=" 拜访类型name:1上门拜访，2电话拜访")
    private String visitTypeName;		// 拜访类型name
    @ApiModelProperty(value="客户类型:一般客户，高意向客户，已签约，已上线，全上线")
    private String custStage;		// 客户类型:一般客户，高意向客户，已签约，已上线，全上线
    @ApiModelProperty(value="客户类型name:一般客户，高意向客户，已签约，已上线，全上线")
    private String custStageName;
    @ApiModelProperty(value="联系人id")
    private String linkmanId;//联系人id
    @ApiModelProperty(value="联系人name")
    private String linkmanName;//联系人name
    @ApiModelProperty(value="问题归类")
    private String issuesClassification;
    @ApiModelProperty(value="备注")
    private String remarks;  //备注
}
