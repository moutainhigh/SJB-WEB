/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.intfz.request.openaccountflow;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 开户申请request
 * @author wanxb
 * @version 2018-05-30
 */
@ApiModel(value="开户申请-列表请求对象")
public class OpenAccountFlowHandleReq  {
	@ApiModelProperty(value="流程申请ID")
	private String procInsId;//流程申请ID
	@ApiModelProperty(value="页数")
	private int pageNo;//页数
	@ApiModelProperty(value="每页数量")
	private int pageSize;//每页数量
	@ApiModelProperty(value="流程状态:1审批结束2审批中3单据被驳回4单据保存")
	private String Status;//流程状态
	@ApiModelProperty(value="开户编号")
	private String code;//开户编号
	@ApiModelProperty(value="甲方名称")
	private String firstPartyName;		// 甲方名称
	@ApiModelProperty(value="市场负责人name")
	private String marketLeaderName;		// 市场负责人name
	@ApiModelProperty(value="搜索开始时间")
	private long updateTimeStart = 0l;//搜索开始时间
	@ApiModelProperty(value="搜索结束时间")
	private long updateTimeEnd = 0l;//搜索结束时间
	@ApiModelProperty(value="开户状态:0.配置中，1.可使用")
	private String openAccountStatus;		// 开户状态
	public String getProcInsId() {
		return procInsId;
	}
	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public String getFirstPartyName() {
		return firstPartyName;
	}
	public void setFirstPartyName(String firstPartyName) {
		this.firstPartyName = firstPartyName;
	}
	public String getMarketLeaderName() {
		return marketLeaderName;
	}
	public void setMarketLeaderName(String marketLeaderName) {
		this.marketLeaderName = marketLeaderName;
	}
	public long getUpdateTimeStart() {
		return updateTimeStart;
	}
	public void setUpdateTimeStart(long updateTimeStart) {
		this.updateTimeStart = updateTimeStart;
	}
	public long getUpdateTimeEnd() {
		return updateTimeEnd;
	}
	public void setUpdateTimeEnd(long updateTimeEnd) {
		this.updateTimeEnd = updateTimeEnd;
	}
	public String getOpenAccountStatus() {
		return openAccountStatus;
	}
	public void setOpenAccountStatus(String openAccountStatus) {
		this.openAccountStatus = openAccountStatus;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	@Override
	public String toString() {
		return "OpenAccountFlowHandleReq [procInsId=" + procInsId + ", pageNo=" + pageNo + ", pageSize=" + pageSize
				+ ", Status=" + Status + ", code=" + code + ", firstPartyName=" + firstPartyName + ", marketLeaderName="
				+ marketLeaderName + ", updateTimeStart=" + updateTimeStart + ", updateTimeEnd=" + updateTimeEnd
				+ ", openAccountStatus=" + openAccountStatus + "]";
	}
	
}