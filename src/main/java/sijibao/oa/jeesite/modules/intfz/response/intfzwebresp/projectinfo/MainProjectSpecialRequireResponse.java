package sijibao.oa.jeesite.modules.intfz.response.intfzwebresp.projectinfo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @description: 项目管理--明细返回
 * @author: xgx
 * @create: 2019-08-20 10:35
 **/
@ApiModel(value="项目管理--特殊要求明细")
public class MainProjectSpecialRequireResponse implements Serializable {

    private static final long serialVersionUID = -3259532912104414201L;
    @ApiModelProperty(value="自营：1是，0否")
    private String selfMarketing;
    @ApiModelProperty(value="自营名称")
    private String selfMarketingName;
    @ApiModelProperty(value="项目经纪人：1是，0否")
    private String projectAgent;
    @ApiModelProperty(value="项目经纪人名称")
    private String projectAgentName;
    @ApiModelProperty(value="车队长：1是，0否")
    private String truckLeader;
    @ApiModelProperty(value="车队长名称")
    private String truckLeaderName;
    @ApiModelProperty(value="油气：1不做油气，2带油,3带气,4带油和气")
    private String oilGas;
    @ApiModelProperty(value="油气名称")
    private String oilGasName;
    @ApiModelProperty(value="叫车：1是，0否")
    private String callTruck;
    @ApiModelProperty(value="叫车名称")
    private String callTruckName;
    @ApiModelProperty(value="贸易：1是，0否")
    private String projectTrade;
    @ApiModelProperty(value="贸易名称")
    private String projectTradeName;
    @ApiModelProperty(value="账期：1是，0否")
    private String accountPeriod;
    @ApiModelProperty(value="账期名称")
    private String accountPeriodName;
    @ApiModelProperty(value="网商：1是，0否")
    private String networkBusiness;
    @ApiModelProperty(value="网商名称")
    private String networkBusinessName;
    @ApiModelProperty(value="项目托盘：1是，0否")
    private String projectTray;
    @ApiModelProperty(value="项目托盘名称")
    private String projectTrayName;
    @ApiModelProperty(value="返点：1是，0否")
    private String returnPoint;
    @ApiModelProperty(value="返点名称")
    private String returnPointName;
    @ApiModelProperty(value="返点比例（%）")
    private Double returnPointProportion;

    public MainProjectSpecialRequireResponse() {
        this.setSelfMarketing("0");
        this.setAccountPeriod("0");
        this.setCallTruck("0");
        this.setNetworkBusiness("0");
        this.setOilGas("1");
        this.setProjectAgent("0");
        this.setProjectTrade("0");
        this.setProjectTray("0");
        this.setReturnPoint("0");
        this.setTruckLeader("0");
    }

    public String getSelfMarketing() {
        return selfMarketing;
    }

    public void setSelfMarketing(String selfMarketing) {
        this.selfMarketing = selfMarketing;
        this.selfMarketingName="1".equals(this.selfMarketing)?"是":"否";
    }

    public String getProjectAgent() {
        return projectAgent;
    }

    public void setProjectAgent(String projectAgent) {
        this.projectAgent = projectAgent;
        this.projectAgentName="1".equals(this.projectAgent)?"是":"否";
    }

    public String getTruckLeader() {
        return truckLeader;
    }

    public void setTruckLeader(String truckLeader) {
        this.truckLeader = truckLeader;
        this.truckLeaderName="1".equals(this.truckLeader)?"是":"否";
    }

    public String getOilGas() {
        return oilGas;
    }

    public void setOilGas(String oilGas) {
        this.oilGas = oilGas;
        switch (this.oilGas){
            case "1":
                this.oilGasName="不做油汽";break;
            case "2":
                this.oilGasName="带油";break;
            case "3":
                this.oilGasName="带汽";break;
            case "4":
                this.oilGasName="带油带汽";break;
            default:
                this.oilGasName="不做油汽";break;
        }
    }

    public String getCallTruck() {
        return callTruck;
    }

    public void setCallTruck(String callTruck) {
        this.callTruck = callTruck;
        this.callTruckName="1".equals(this.callTruck)?"是":"否";
    }

    public String getProjectTrade() {
        return projectTrade;
    }

    public void setProjectTrade(String projectTrade) {
        this.projectTrade = projectTrade;
        this.projectTradeName="1".equals(this.projectTrade)?"是":"否";
    }

    public String getAccountPeriod() {
        return accountPeriod;
    }

    public void setAccountPeriod(String accountPeriod) {
        this.accountPeriod = accountPeriod;
        this.accountPeriodName="1".equals(this.accountPeriod)?"是":"否";
    }

    public String getNetworkBusiness() {
        return networkBusiness;
    }

    public void setNetworkBusiness(String networkBusiness) {
        this.networkBusiness = networkBusiness;
        this.networkBusinessName="1".equals(this.networkBusiness)?"是":"否";
    }

    public String getProjectTray() {
        return projectTray;
    }

    public void setProjectTray(String projectTray) {
        this.projectTray = projectTray;
        this.projectTrayName="1".equals(this.projectTray)?"是":"否";
    }

    public String getReturnPoint() {
        return returnPoint;
    }

    public void setReturnPoint(String returnPoint) {
        this.returnPoint = returnPoint;
        this.returnPointName="1".equals(this.returnPoint)?"是":"否";
    }

    public String getSelfMarketingName() {
        return selfMarketingName;
    }

    public void setSelfMarketingName(String selfMarketingName) {
        this.selfMarketingName = selfMarketingName;
    }

    public String getProjectAgentName() {
        return projectAgentName;
    }

    public void setProjectAgentName(String projectAgentName) {
        this.projectAgentName = projectAgentName;
    }

    public String getTruckLeaderName() {
        return truckLeaderName;
    }

    public void setTruckLeaderName(String truckLeaderName) {
        this.truckLeaderName = truckLeaderName;
    }

    public String getOilGasName() {
        return oilGasName;
    }

    public void setOilGasName(String oilGasName) {
        this.oilGasName = oilGasName;
    }

    public String getCallTruckName() {
        return callTruckName;
    }

    public void setCallTruckName(String callTruckName) {
        this.callTruckName = callTruckName;
    }

    public String getProjectTradeName() {
        return projectTradeName;
    }

    public void setProjectTradeName(String projectTradeName) {
        this.projectTradeName = projectTradeName;
    }

    public String getAccountPeriodName() {
        return accountPeriodName;
    }

    public void setAccountPeriodName(String accountPeriodName) {
        this.accountPeriodName = accountPeriodName;
    }

    public String getNetworkBusinessName() {
        return networkBusinessName;
    }

    public void setNetworkBusinessName(String networkBusinessName) {
        this.networkBusinessName = networkBusinessName;
    }

    public String getProjectTrayName() {
        return projectTrayName;
    }

    public void setProjectTrayName(String projectTrayName) {
        this.projectTrayName = projectTrayName;
    }

    public String getReturnPointName() {
        return returnPointName;
    }

    public void setReturnPointName(String returnPointName) {
        this.returnPointName = returnPointName;
    }

    public Double getReturnPointProportion() {
        return returnPointProportion;
    }

    public void setReturnPointProportion(Double returnPointProportion) {
        this.returnPointProportion = returnPointProportion;
    }
}
