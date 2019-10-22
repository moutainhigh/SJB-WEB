package sijibao.oa.jeesite.modules.intfz.request.intfzwebreq.projectinfo;

import java.io.Serializable;

import javax.validation.constraints.Digits;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @description: 特殊要求
 * @author: xgx
 * @create: 2019-08-20 10:35
 **/
@ApiModel(value="项目管理--特殊要求")
public class MainProjectSpecialRequire implements Serializable {

    private static final long serialVersionUID = -3259532912104414201L;
    @ApiModelProperty(value="自营：1是，0否")
    private Integer selfMarketing;
    @ApiModelProperty(value="项目经纪人：1是，0否")
    private Integer projectAgent;
    @ApiModelProperty(value="车队长：1是，0否")
    private Integer truckLeader;
    @ApiModelProperty(value="油气：1不做油气，2带油,3带气,4带油和气")
    private Integer oilGas;
    @ApiModelProperty(value="叫车：1是，0否")
    private Integer callTruck;
    @ApiModelProperty(value="贸易：1是，0否")
    private Integer projectTrade;
    @ApiModelProperty(value="账期：1是，0否")
    private Integer accountPeriod;
    @ApiModelProperty(value="网商：1是，0否")
    private Integer networkBusiness;
    @ApiModelProperty(value="项目托盘：1是，0否")
    private Integer projectTray;
    @ApiModelProperty(value="返点：1是，0否")
    private Integer returnPoint;
    @Digits(integer=5,fraction=2,message = "返点比例有误")
    @ApiModelProperty(value="返点比例（%）")
    private Double returnPointProportion;


    public Integer getSelfMarketing() {
        return selfMarketing;
    }

    public void setSelfMarketing(Integer selfMarketing) {
        this.selfMarketing = selfMarketing;
    }

    public Integer getProjectAgent() {
        return projectAgent;
    }

    public void setProjectAgent(Integer projectAgent) {
        this.projectAgent = projectAgent;
    }

    public Integer getTruckLeader() {
        return truckLeader;
    }

    public void setTruckLeader(Integer truckLeader) {
        this.truckLeader = truckLeader;
    }

    public Integer getOilGas() {
        return oilGas;
    }

    public void setOilGas(Integer oilGas) {
        this.oilGas = oilGas;
    }

    public Integer getCallTruck() {
        return callTruck;
    }

    public void setCallTruck(Integer callTruck) {
        this.callTruck = callTruck;
    }

    public Integer getProjectTrade() {
        return projectTrade;
    }

    public void setProjectTrade(Integer projectTrade) {
        this.projectTrade = projectTrade;
    }

    public Integer getAccountPeriod() {
        return accountPeriod;
    }

    public void setAccountPeriod(Integer accountPeriod) {
        this.accountPeriod = accountPeriod;
    }

    public Integer getNetworkBusiness() {
        return networkBusiness;
    }

    public void setNetworkBusiness(Integer networkBusiness) {
        this.networkBusiness = networkBusiness;
    }

    public Integer getProjectTray() {
        return projectTray;
    }

    public void setProjectTray(Integer projectTray) {
        this.projectTray = projectTray;
    }

    public Integer getReturnPoint() {
        return returnPoint;
    }

    public void setReturnPoint(Integer returnPoint) {
        this.returnPoint = returnPoint;
    }

    public Double getReturnPointProportion() {
        return returnPointProportion;
    }

    public void setReturnPointProportion(Double returnPointProportion) {
        this.returnPointProportion = returnPointProportion;
    }
}
