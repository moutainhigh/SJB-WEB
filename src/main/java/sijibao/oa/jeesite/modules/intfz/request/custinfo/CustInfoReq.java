package sijibao.oa.jeesite.modules.intfz.request.custinfo;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import com.sijibao.oa.modules.intfz.request.common.PageRequest;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 客户资料管理-区域请求对象
 * @author wanxb
 *
 */
@ApiModel(value="客户资料管理-区域请求对象")
public class CustInfoReq extends PageRequest implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value="冲突name")
	private String conflictName;//冲突name
	@ApiModelProperty(value="客户所在位置：1公海，2区域公海，3其他个人客户，4个人客户，5签约客户")
	private String custListPlace;//客户所在位置：1公海，2区域公海，3其他个人客户，4个人客户，5签约客户
	@ApiModelProperty(value="市场负责人")
	private String marketLeaderId;//市场负责人
	@ApiModelProperty(value="归属行业")
	private String custTrades;//归属行业
	@ApiModelProperty(value="开始 搜索时间")
	private long beginTime;		// 开始 搜索时间
	@ApiModelProperty(value="结束 搜索时间")
	private long endTime;		// 结束 搜索时间
	@ApiModelProperty(value="客户归属区域")
	private String[] custOfficeId;//客户归属区域
	@ApiModelProperty(value="主客户查询")
	private String isMain ;//0是以前的，1主客户查询

    @ApiModelProperty(value="主客户查询")
    private String mainCustName ;//主客户查询
    @ApiModelProperty(value="客户级别")
    private List<String> custStage ;//客户级别
    @ApiModelProperty(value="负责人类型")
    private String leaderType ;//负责人类型
    @ApiModelProperty(value="负责人")
    private String leader ;//负责人



	public String getConflictName() {
		return conflictName;
	}
	public void setConflictName(String conflictName) {
		this.conflictName = conflictName;
	}
	public String getCustListPlace() {
		return custListPlace;
	}
	public void setCustListPlace(String custListPlace) {
		this.custListPlace = custListPlace;
	}
	public String getCustTrades() {
		return custTrades;
	}
	public void setCustTrades(String custTrades) {
		this.custTrades = custTrades;
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
	public String[] getCustOfficeId() {
		return custOfficeId;
	}
	public void setCustOfficeId(String[] custOfficeId) {
		this.custOfficeId = custOfficeId;
	}
	public String getMarketLeaderId() {
		return marketLeaderId;
	}
	public void setMarketLeaderId(String marketLeaderId) {
		this.marketLeaderId = marketLeaderId;
	}

	public String getIsMain() {
		return isMain;
	}

	public void setIsMain(String isMain) {
		this.isMain = isMain;
	}

    public String getMainCustName() {
        return mainCustName;
    }

    public void setMainCustName(String mainCustName) {
        this.mainCustName = mainCustName;
    }

    public List<String> getCustStage() {
        return custStage;
    }

    public void setCustStage(List<String> custStage) {
        this.custStage = custStage;
    }

    public String getLeaderType() {
        return leaderType;
    }

    public void setLeaderType(String leaderType) {
        this.leaderType = leaderType;
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    @Override
    public String toString() {
        return "CustInfoReq{" +
                "conflictName='" + conflictName + '\'' +
                ", custListPlace='" + custListPlace + '\'' +
                ", marketLeaderId='" + marketLeaderId + '\'' +
                ", custTrades='" + custTrades + '\'' +
                ", beginTime=" + beginTime +
                ", endTime=" + endTime +
                ", custOfficeId=" + Arrays.toString(custOfficeId) +
                ", isMain='" + isMain + '\'' +
                ", mainCustName='" + mainCustName + '\'' +
                ", custStage=" + custStage +
                ", leaderType='" + leaderType + '\'' +
                ", leader='" + leader + '\'' +
                '}';
    }
}
