package sijibao.oa.jeesite.modules.intfz.web.intfzapp;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.google.common.collect.Lists;
import com.sijibao.base.common.api.response.PagerResponse;
import com.sijibao.oa.common.utils.SpringContextHolder;
import com.sijibao.oa.common.utils.WebUtils;
import com.sijibao.oa.common.web.BaseController;
import com.sijibao.oa.crm.api.IntfzWebProjectInfoService;
import com.sijibao.oa.hamal.api.IntfzWebCompanyLoadAddressService;
import com.sijibao.oa.hamal.api.IntfzWebCompanyService;
import com.sijibao.oa.hamal.api.IntfzWebOrderService;
import com.sijibao.oa.hamal.api.request.HamalAbnormalOrderListForOaRequest;
import com.sijibao.oa.hamal.api.request.HamalStockOrdersErrorListRequest;
import com.sijibao.oa.hamal.api.response.*;
import com.sijibao.oa.modules.intfz.bean.BaseResp;
import com.sijibao.oa.modules.intfz.enums.IntfzRs;
import com.sijibao.oa.modules.intfz.request.common.HandleReq;
import com.sijibao.oa.modules.intfz.request.company.MainAbnormalOrderListForOaRequest;
import com.sijibao.oa.modules.intfz.request.company.QueryCompanyHandleReq;
import com.sijibao.oa.modules.intfz.request.intfzwebreq.projectinfo.OrderCountReq;
import com.sijibao.oa.modules.intfz.response.common.PageResponse;
import com.sijibao.oa.modules.intfz.response.intfzwebresp.company.*;
import com.sijibao.oa.modules.intfz.validator.AppAuthority;
import com.sijibao.oa.modules.sys.entity.User;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * APP企业管理服务controller
 * @author xuby
 *
 */
@Controller
@RequestMapping(value = "wechat/company")
@Api(value="APP企业管理服务",tags="APP企业管理服务")
public class IntfzCompany extends BaseController {
	
	private IntfzWebCompanyService intfzWebCompanyService;
	private IntfzWebProjectInfoService intfzWebProjectInfoService;
	private IntfzWebCompanyLoadAddressService intfzWebCompanyLoadAddressService;
	private IntfzWebOrderService intfzWebOrderService;
//	@Autowired
//	private OfficeService officeService ;
	
	@ModelAttribute
	public void init(){
		if(this.intfzWebCompanyService == null){
			this.intfzWebCompanyService = SpringContextHolder.getBean("intfzWebCompanyService");
		}
		if(this.intfzWebProjectInfoService == null){
			this.intfzWebProjectInfoService = SpringContextHolder.getBean("intfzWebProjectInfoService");
		}
		if(this.intfzWebCompanyLoadAddressService == null){
			this.intfzWebCompanyLoadAddressService = SpringContextHolder.getBean("intfzWebCompanyLoadAddressService");
		}
		if(this.intfzWebOrderService == null){
			this.intfzWebOrderService = SpringContextHolder.getBean("intfzWebOrderService");
		}
	};
	/**
	 * WEB企业管理服务-查询企业名称
	 * @param req
	 * @param sjboacert
	 * @param clientType
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "queryForOne")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "WEB企业管理服务-详情")
    @AppAuthority
	public BaseResp<MainCompanyResponse> queryForOne(
			@RequestBody HandleReq req,
			@ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
			@ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		HamalCompanyResponse custInfoResponse = intfzWebCompanyService.queryForOne(req.getCode());
		MainCompanyResponse change = change(custInfoResponse, MainCompanyResponse.class);
		return new BaseResp<MainCompanyResponse>(IntfzRs.SUCCESS, "ok", change);
	}
	/**
	 * APP企业管理服务-企业名模糊查询
	 * @param req
	 * @param sjboacert
	 * @param clientType
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "queryListByNameAndState")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "WEB企业管理服务-企业名模糊查询")
    @AppAuthority
	public BaseResp<PageResponse<List<MainCompanyResponse>>> queryListByNameAndState(
			@RequestBody QueryCompanyHandleReq req,
			@ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
			@ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		PagerResponse<HamalCompanyResponse> page = intfzWebCompanyService.queryListByNameAndState(req.getCompanyName(), 0, 30, 0);
		ArrayList<MainCompanyResponse> list = Lists.newArrayList();
		for (HamalCompanyResponse hamal : page.getList()) {
			MainCompanyResponse change = change(hamal, MainCompanyResponse.class);
			list.add(change);
		}
		return new BaseResp<PageResponse<List<MainCompanyResponse>>>(IntfzRs.SUCCESS, "ok", 
				new PageResponse<List<MainCompanyResponse>>(list, req.getPageNo(), page.getCount()));
	}
	
	/**
	 * WEB企业管理服务-查询企业常用装卸货地址
	 * @param req
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "queryCommonLoadAddress")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "WEB企业管理服务-查询企业常用装卸货地址")
    @AppAuthority
	public BaseResp<MainCommonAddressQueryResponse> queryCommonLoadAddress(
			@RequestBody HandleReq req,
			@ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
			@ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		HamalCommonAddressQueryResponse resp = intfzWebCompanyLoadAddressService.queryCommonLoadAddress(req.getCode(),null,0,900);
		List<HamalLoadAddress> hamal = resp.getAddresss();
		ArrayList<MainLoadAddress> list = Lists.newArrayList();
		for (HamalLoadAddress ss : hamal) {
			MainLoadAddress change = change(ss, MainLoadAddress.class);
			list.add(change);
		}
		MainCommonAddressQueryResponse change = change(resp, MainCommonAddressQueryResponse.class);
		change.setAddresss(list);
		return new BaseResp<MainCommonAddressQueryResponse>(IntfzRs.SUCCESS, "ok", change);
	}
	/**
	 * app企业管理服务-异常单数量查询
	 * @param req
	 * @param sjboacert
	 * @param clientType
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "abnormalOrderCountByCompanyCode")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "app企业管理服务-异常单数量查询")
    @AppAuthority
	public BaseResp<PageResponse<List<MainAbnormalOrderCountRsp>>> abnormalOrderCountByCompanyCode(
			@ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
			@ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
			@RequestBody OrderCountReq req,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		User user = super.getCurrWxUser(sjboacert, clientType); //获取当前申请人信息
		List<String> companyCodes = intfzWebProjectInfoService.queryCompanyCodesByUser(user.getId());
		if(companyCodes == null || companyCodes.size() == 0){
			return new BaseResp<PageResponse<List<MainAbnormalOrderCountRsp>>>(IntfzRs.ERROR, "未关联任何企业！", null);
		}
		PagerResponse<HamalAbnormalOrderCountRsp> resp = intfzWebOrderService.abnormalOrderCountByCompanyCode(companyCodes,req.getCompanyName(),(req.getPageNo()-1)*req.getPageSize()+1,req.getPageSize());
		ArrayList<MainAbnormalOrderCountRsp> list = Lists.newArrayList();
		if(resp.getList() == null){
			return new BaseResp<PageResponse<List<MainAbnormalOrderCountRsp>>>(IntfzRs.ERROR, "未搜索到数据！", null);
		}
		for (HamalAbnormalOrderCountRsp re : resp.getList()) {
			MainAbnormalOrderCountRsp change = change(re, MainAbnormalOrderCountRsp.class);
			list.add(change);
		}
		return new BaseResp<PageResponse<List<MainAbnormalOrderCountRsp>>>(IntfzRs.SUCCESS, "ok", 
					new PageResponse<List<MainAbnormalOrderCountRsp>>(list, req.getPageNo(), resp.getCount()));
	}
	/**
	 * app企业管理服务-异常单列表
	 * @param req
	 * @param sjboacert
	 * @param clientType
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "abnormalOrderListForOA")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "app企业管理服务-异常单列表")
    @AppAuthority
	public BaseResp<PageResponse<List<MainAbnormalOrderListForOaRsp>>> abnormalOrderListForOA(
			@RequestBody MainAbnormalOrderListForOaRequest req,
			@ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
			@ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		HamalAbnormalOrderListForOaRequest re = change(req, HamalAbnormalOrderListForOaRequest.class);
		re.setStart(req.getStart()*req.getLimit()+1);
		PagerResponse<HamalAbnormalOrderListForOaRsp> back = intfzWebOrderService.abnormalOrderListForOA(re);
		ArrayList<MainAbnormalOrderListForOaRsp> list = Lists.newArrayList();
		for (HamalAbnormalOrderListForOaRsp r : back.getList()) {
			MainAbnormalOrderListForOaRsp change = change(r, MainAbnormalOrderListForOaRsp.class);
			list.add(change);
		}
//		return new BaseResp<List<MainAbnormalOrderListForOaRsp>>(IntfzRs.SUCCESS, "ok", list);
		return new BaseResp<PageResponse<List<MainAbnormalOrderListForOaRsp>>>(IntfzRs.SUCCESS, "ok", 
				new PageResponse<List<MainAbnormalOrderListForOaRsp>>(list, req.getStart(), back.getCount()));
	}
	
	/**
	 * 运营开票异常单列表查询
	 * @param req
	 * @param sjboacert
	 * @param clientType
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "stockOrderErrorListForOA")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "app企业管理服务-开票异常单列表")
    @AppAuthority
	public BaseResp<PageResponse<List<MainAbnormalOrderListForOaRsp>>> stockOrderErrorListForOA(
			@RequestBody MainAbnormalOrderListForOaRequest req,
			@ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
			@ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		
		HamalStockOrdersErrorListRequest sreq = new HamalStockOrdersErrorListRequest();
		sreq.setCompanyCodes(req.getCompanyCode());
		sreq.setOrderNumberOrCarNumber(req.getQueryKey());
		sreq.setLoadingSite(req.getLoadAddressCode());
		sreq.setUnloadSite(req.getUnloadAddressCode());
		if(req.getLoadTimeStart() != 0l){
			sreq.setSignLoadDate(req.getLoadTimeStart());
		}
		if(req.getUnloadTimeStart() != 0l){
			sreq.setSignUploadDate(req.getUnloadTimeStart());
		}
		if(req.getAbnormalTimeStart() != 0l){
			sreq.setTreaCreateTime(req.getAbnormalTimeStart());
		}
		sreq.setPageNo(String.valueOf(req.getStart()+1));
		sreq.setPageSize(String.valueOf(req.getLimit()));
		PagerResponse<HamalAbnormalOrderListForOaRsp> back = intfzWebOrderService.queryErrAssureOrderInfoForOA(sreq);
		ArrayList<MainAbnormalOrderListForOaRsp> list = Lists.newArrayList();
		for (HamalAbnormalOrderListForOaRsp r : back.getList()) {
			MainAbnormalOrderListForOaRsp change = change(r, MainAbnormalOrderListForOaRsp.class);
			list.add(change);
		}
		return new BaseResp<PageResponse<List<MainAbnormalOrderListForOaRsp>>>(IntfzRs.SUCCESS, "ok", 
				new PageResponse<List<MainAbnormalOrderListForOaRsp>>(list, req.getStart(), back.getCount()));
	}
	
}
