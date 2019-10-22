package sijibao.oa.jeesite.modules.rest.vo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description: 沟通记录大字段存储表
 * @author: xgx
 * @create: 2019-09-29 14:41
 **/
@ApiModel(value="运营平台沟通记录大字段表对象")
@Data
public class CommunicateRecordBigfield implements Serializable {

    private static final long serialVersionUID = 3840523305117695130L;
    @ApiModelProperty(notes = "主键id")
    private Integer id;
    @ApiModelProperty(notes = "customer_access_record 主键")
    private Integer recordId;
    @ApiModelProperty(notes = "社会信用代码")
    private String taxIdNumber;
    @ApiModelProperty(notes = "企业法人")
    private String legalName;
    @ApiModelProperty(notes = "法人身份证号码")
    private String legalIdCard;
    @ApiModelProperty(notes = "用户反馈")
    private String customerFeedBack;
    @ApiModelProperty(notes = "客服回复")
    private String customerServicerReply;
    @ApiModelProperty(notes = "其他部门回复")
    private String otherDepartmentReply;
}
