package sijibao.oa.jeesite.modules.intfz.response.custinfo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description: 被动维护实体
 * @author: xgx
 * @create: 2019-09-30 14:50
 **/
@ApiModel(value="主客户维护管理--被动维护列表返回对象")
@Data
public class PassiveMaintainResponse implements Serializable {

    private static final long serialVersionUID = 8096986308267500840L;
    @ApiModelProperty(value="企业code")
    private String customerCode;
    @ApiModelProperty(value="项目名称")
    private String projectName;
    @ApiModelProperty(value="创建时间")
    private Long createDate;
    @ApiModelProperty(value="记录人")
    private String createName;
    @ApiModelProperty(value="记录类型")
    private String recordName;
    @ApiModelProperty(value="沟通类型")
    private String communicateName;
    @ApiModelProperty(value="沟通渠道")
    private String communicatieName;
    @ApiModelProperty(value="用户反馈")
    private String customerFeedBack;
    @ApiModelProperty(value="客服回复")
    private String customerServicerReply;
    @ApiModelProperty(value="处理结果")
    private String dealResultName;
    @ApiModelProperty(value="对接部门")
    private String connectDepartmentName;
    @ApiModelProperty(value="其他部门回复")
    private String otherDepartmentReply;
    @ApiModelProperty(value="处理人")
    private String updateName;
    @ApiModelProperty(value="处理时间")
    private Long dealDate;


}
