package sijibao.oa.jeesite.modules.intfz.response.report;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="单据审批情况返回对象")
public class MainFlowSubmitSituationResponse implements Serializable {
    @ApiModelProperty(value="功能编号")
    private String subjectCode;
    @ApiModelProperty(value="序号")
    private int number;
    @ApiModelProperty(value="功能")
    private String function;
    @ApiModelProperty(value="产品端")
    private String producSide;
    @ApiModelProperty(value="上线时间")
    private String onlineTime;
    @ApiModelProperty(value="1月")
    private String tJan;
    @ApiModelProperty(value="2月")
    private String tFeb;
    @ApiModelProperty(value="3月")
    private String tMar;
    @ApiModelProperty(value="4月")
    private String tApr;
    @ApiModelProperty(value="5月")
    private String tMay;
    @ApiModelProperty(value="6月")
    private String tJun;
    @ApiModelProperty(value="7月")
    private String tJul;
    @ApiModelProperty(value="8月")
    private String tAug;
    @ApiModelProperty(value="9月")
    private String tSept;
    @ApiModelProperty(value="10月")
    private String tOct;
    @ApiModelProperty(value="11月")
    private String tNov;
    @ApiModelProperty(value="12月")
    private String tDec;

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getProducSide() {
        return producSide;
    }

    public void setProducSide(String producSide) {
        this.producSide = producSide;
    }

    public String getOnlineTime() {
        return onlineTime;
    }

    public void setOnlineTime(String onlineTime) {
        this.onlineTime = onlineTime;
    }

    public String gettJan() {
        return tJan;
    }

    public void settJan(String tJan) {
        this.tJan = tJan;
    }

    public String gettFeb() {
        return tFeb;
    }

    public void settFeb(String tFeb) {
        this.tFeb = tFeb;
    }

    public String gettMar() {
        return tMar;
    }

    public void settMar(String tMar) {
        this.tMar = tMar;
    }

    public String gettApr() {
        return tApr;
    }

    public void settApr(String tApr) {
        this.tApr = tApr;
    }

    public String gettMay() {
        return tMay;
    }

    public void settMay(String tMay) {
        this.tMay = tMay;
    }

    public String gettJun() {
        return tJun;
    }

    public void settJun(String tJun) {
        this.tJun = tJun;
    }

    public String gettJul() {
        return tJul;
    }

    public void settJul(String tJul) {
        this.tJul = tJul;
    }

    public String gettAug() {
        return tAug;
    }

    public void settAug(String tAug) {
        this.tAug = tAug;
    }

    public String gettSept() {
        return tSept;
    }

    public void settSept(String tSept) {
        this.tSept = tSept;
    }

    public String gettOct() {
        return tOct;
    }

    public void settOct(String tOct) {
        this.tOct = tOct;
    }

    public String gettNov() {
        return tNov;
    }

    public void settNov(String tNov) {
        this.tNov = tNov;
    }

    public String gettDec() {
        return tDec;
    }

    public void settDec(String tDec) {
        this.tDec = tDec;
    }

    @Override
    public String toString() {
        return "MainFlowSubmitSituationResponse{" +
                "subjectCode=" + subjectCode +
                ", number=" + number +
                ", function='" + function + '\'' +
                ", producSide='" + producSide + '\'' +
                ", onlineTime='" + onlineTime + '\'' +
                ", tJan='" + tJan + '\'' +
                ", tFeb='" + tFeb + '\'' +
                ", tMar='" + tMar + '\'' +
                ", tApr='" + tApr + '\'' +
                ", tMay='" + tMay + '\'' +
                ", tJun='" + tJun + '\'' +
                ", tJul='" + tJul + '\'' +
                ", tAug='" + tAug + '\'' +
                ", tSept='" + tSept + '\'' +
                ", tOct='" + tOct + '\'' +
                ", tNov='" + tNov + '\'' +
                ", tDec='" + tDec + '\'' +
                '}';
    }
}
