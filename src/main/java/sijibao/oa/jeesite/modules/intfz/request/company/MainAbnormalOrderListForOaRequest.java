package sijibao.oa.jeesite.modules.intfz.request.company;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;

public class MainAbnormalOrderListForOaRequest{
	
	
	/**
	 * 企业编码
	 */
	@ApiModelProperty(value="企业编码")
	@NotBlank
	private String companyCode;
	/**
	 * 查询关键字 运单号/车牌号
	 */
	@ApiModelProperty(value="查询关键字 运单号/车牌号")
	private String queryKey;
	
	/**
	 * 装货地编码
	 */
	@ApiModelProperty(value="装货地编码")
	private String loadAddressCode;
	
	/**
	 * 卸货地编码
	 */
	@ApiModelProperty(value="卸货地编码")
	private String unloadAddressCode;
	
    /**
     * 装货时间-查询开始时间
     */
	@ApiModelProperty(value="装货时间-查询开始时间")
    private long loadTimeStart;
    
    /**
     * 装货时间-查询截止时间
     */
	@ApiModelProperty(value="装货时间-查询截止时间")
    private long loadTimeEnd;

    /**
     * 卸货时间-查询开始时间
     */
	@ApiModelProperty(value="卸货时间-查询开始时间")
    private long unloadTimeStart;
    
    /**
     * 卸货时间-查询截止时间
     */
	@ApiModelProperty(value="卸货时间-查询截止时间")
    private long unloadTimeEnd;
    
    /**
     * 异常时间-查询开始时间
     */
	@ApiModelProperty(value="异常时间-查询开始时间")
    private long abnormalTimeStart;
    /**
     * 异常时间-查询截止时间
     */
	@ApiModelProperty(value="异常时间-查询截止时间")
    private long abnormalTimeEnd;
	
	@Min(0)
	@ApiModelProperty(value="分页参数-pageNo")
    private int start;
    
    @Min(0)
    @Max(1000)
    @ApiModelProperty(value="分页参数-size")
    private int limit;
	
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getQueryKey() {
		return queryKey;
	}
	public void setQueryKey(String queryKey) {
		this.queryKey = queryKey;
	}
	public String getLoadAddressCode() {
		return loadAddressCode;
	}
	public void setLoadAddressCode(String loadAddressCode) {
		this.loadAddressCode = loadAddressCode;
	}
	public String getUnloadAddressCode() {
		return unloadAddressCode;
	}
	public void setUnloadAddressCode(String unloadAddressCode) {
		this.unloadAddressCode = unloadAddressCode;
	}
	public long getLoadTimeStart() {
		return loadTimeStart;
	}
	public void setLoadTimeStart(long loadTimeStart) {
		this.loadTimeStart = loadTimeStart;
	}
	public long getLoadTimeEnd() {
		return loadTimeEnd;
	}
	public void setLoadTimeEnd(long loadTimeEnd) {
		this.loadTimeEnd = loadTimeEnd;
	}
	public long getUnloadTimeStart() {
		return unloadTimeStart;
	}
	public void setUnloadTimeStart(long unloadTimeStart) {
		this.unloadTimeStart = unloadTimeStart;
	}
	public long getUnloadTimeEnd() {
		return unloadTimeEnd;
	}
	public void setUnloadTimeEnd(long unloadTimeEnd) {
		this.unloadTimeEnd = unloadTimeEnd;
	}
	public long getAbnormalTimeStart() {
		return abnormalTimeStart;
	}
	public void setAbnormalTimeStart(long abnormalTimeStart) {
		this.abnormalTimeStart = abnormalTimeStart;
	}
	public long getAbnormalTimeEnd() {
		return abnormalTimeEnd;
	}
	public void setAbnormalTimeEnd(long abnormalTimeEnd) {
		this.abnormalTimeEnd = abnormalTimeEnd;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	@Override
	public String toString() {
		return "MainAbnormalOrderListForOaRequest [companyCode=" + companyCode + ", queryKey=" + queryKey
				+ ", loadAddressCode=" + loadAddressCode + ", unloadAddressCode=" + unloadAddressCode
				+ ", loadTimeStart=" + loadTimeStart + ", loadTimeEnd=" + loadTimeEnd + ", unloadTimeStart="
				+ unloadTimeStart + ", unloadTimeEnd=" + unloadTimeEnd + ", abnormalTimeStart=" + abnormalTimeStart
				+ ", abnormalTimeEnd=" + abnormalTimeEnd + ", start=" + start + ", limit=" + limit + "]";
	}

	
}
