package sijibao.oa.jeesite.modules.rest.vo;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description: 沟通记录表，用于保存沟通记录主要信息
 * @author: xgx
 * @create: 2019-09-29 14:29
 **/
@ApiModel(value="运营平台沟通记录表对象")
@Data
public class CustomerAccessRecord implements Serializable {

    private static final long serialVersionUID = 6374020796415447102L;

    @ApiModelProperty(notes = "主键id")
    private Integer id;
    @ApiModelProperty(notes = "用户类型(0:其他;1:司机;2:企业;)")
    private Integer userType;
    @ApiModelProperty(notes = "注册账号")
    private String registerAccount;
    @ApiModelProperty(notes = "客户名称")
    private String customerName;
    @ApiModelProperty(notes = "客户编码")
    private String customerCode;
    @ApiModelProperty(notes = "客户联系电话")
    private String customerLinkPhone;
    @ApiModelProperty(notes = "记录类型（1:主动回访;2:用户需求;3被动访问）")
    private Integer recordType;
    @ApiModelProperty(notes = "沟通类型(1:业务相关、2:异常单相关、3:产品体验相关、4:企业需求、5:清结算相关-对账、6:清结算相关-发票、7:清结算相关-流水、8:资金相关-充值、9:资金相关-退款/提现、10:企业配置、11:企业电话拜访、12:前端系统故障、13:后端系统故障)")
    private Integer communicateType;
    @ApiModelProperty(notes = "沟通渠道(1:VIP群、2:大客户经理、3:实施、4:商务助理、4:400或在线、5:清结算、0:其他)")
    private Integer communicatieWay;
    @ApiModelProperty(notes = "处理结果(0:待处理,1:已有解决方案待跟进，2:已解决)")
    private Integer dealResult;
    @ApiModelProperty(notes = "对接部门(1:研发;2:项目管理;3:商务/市场;4:跟单/审单;5:财务;6:清结算)")
    private Integer connectDepartment;
    @ApiModelProperty(notes = "其他联系人")
    private String otherLinkMan;
    @ApiModelProperty(notes = "创建时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createDate;
    @ApiModelProperty(notes = "更新时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateDate;
    @ApiModelProperty(notes = "处理时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date dealDate;
    @ApiModelProperty(notes = "创建人姓名")
    private String createName;
    @ApiModelProperty(notes = "创建人id")
    private String createBy;
    @ApiModelProperty(notes = "更新人姓名")
    private String updateName;
    @ApiModelProperty(notes = "更新人id")
    private String updateId;
    @ApiModelProperty(notes = "是否删除0;否;1是")
    private Integer delFlag;
    @ApiModelProperty(notes = "沟通记录大字段存储")
    private CommunicateRecordBigfield communicateRecordBigfield;
    @ApiModelProperty(notes = "指定处理人姓名")
    private String processer;
    @ApiModelProperty(notes = "指定处理人id，来源于sys_user的userid")
    private String processerId;


}
