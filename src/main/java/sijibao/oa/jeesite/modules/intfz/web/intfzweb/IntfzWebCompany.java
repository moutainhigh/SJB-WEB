package sijibao.oa.jeesite.modules.intfz.web.intfzweb;

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
import com.sijibao.oa.hamal.api.IntfzWebCompanyService;
import com.sijibao.oa.hamal.api.response.HamalCompanyResponse;
import com.sijibao.oa.modules.intfz.bean.BaseResp;
import com.sijibao.oa.modules.intfz.enums.IntfzRs;
import com.sijibao.oa.modules.intfz.request.company.QueryCompanyHandleReq;
import com.sijibao.oa.modules.intfz.response.common.PageResponse;
import com.sijibao.oa.modules.intfz.response.intfzwebresp.company.MainCompanyResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 客户资料管理controller
 * @author xuby
 *
 */
@Controller
@RequestMapping(value = "${frontPath}/company")
@Api(value="WEB企业管理服务",tags="WEB企业管理服务")
public class IntfzWebCompany extends BaseController {
	
	private IntfzWebCompanyService intfzWebCompanyService;
	
	@ModelAttribute
	public void init(){
		if(this.intfzWebCompanyService == null){
			this.intfzWebCompanyService = SpringContextHolder.getBean("intfzWebCompanyService");
		}
	};
//	@Autowired
//	private IntfzWebCompanyLoadAddressService intfzWebCompanyLoadAddressService;
	
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
//	@RequestMapping(value = "queryForOne")
//	@ResponseBody
//	@ApiOperation(httpMethod = "POST", value = "WEB企业管理服务-详情")
//	public BaseResp<MainCompanyResponse> queryForOne(
//			@RequestBody HandleReq req,
//			@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
//			HttpServletRequest request,HttpServletResponse response) throws Exception{
//		WebUtils.initPre(request, response);
//		logger.info("====IntfzWebCompany queryForOne[req]====={}", req.toString());
//		HamalCompanyResponse custInfoResponse = intfzWebCompanyService.queryForOne(req.getCode());
//		MainCompanyResponse change = change(custInfoResponse, MainCompanyResponse.class);
//		logger.info("=======IntfzWebCompany queryForOne end=============");
//		return new BaseResp<MainCompanyResponse>(IntfzRs.SUCCESS, "ok", change);
//	}
//	
	/**
	 * WEB企业管理服务-企业名模糊查询
	 * @param req
	 * @param sessionid
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "queryListByNameAndState")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "WEB企业管理服务-企业名模糊查询")
	public BaseResp<PageResponse<List<MainCompanyResponse>>> queryListByNameAndState(
			@RequestBody QueryCompanyHandleReq req,
			@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		PagerResponse<HamalCompanyResponse> page = intfzWebCompanyService.queryListByNameAndState(req.getCompanyName(), (req.getPageNo()-1)*req.getPageSize() + 1, req.getPageSize(), 4);
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
	 * @param sessionid
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
//	@RequestMapping(value = "queryCommonLoadAddress")
//	@ResponseBody
//	@ApiOperation(httpMethod = "POST", value = "WEB企业管理服务-查询企业常用装卸货地址")
//	public BaseResp<MainCommonAddressQueryResponse> queryCommonLoadAddress(
//			@RequestBody HandleReq req,
//			@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
//			HttpServletRequest request,HttpServletResponse response) throws Exception{
//		WebUtils.initPre(request, response);
//		logger.info("====IntfzWebCompany queryCommonLoadAddress[req]====={}", req.toString());
//		HamalCommonAddressQueryResponse resp = intfzWebCompanyLoadAddressService.queryCommonLoadAddress(req.getCode(),null,0,30);
//		List<HamalLoadAddress> hamal = resp.getAddresss();
//		ArrayList<MainLoadAddress> list = Lists.newArrayList();
//		for (HamalLoadAddress ss : hamal) {
//			MainLoadAddress change = change(ss, MainLoadAddress.class);
//			list.add(change);
//		}
//		MainCommonAddressQueryResponse change = change(resp, MainCommonAddressQueryResponse.class);
//		change.setAddresss(list);
//		logger.info("=======IntfzWebCompany queryCommonLoadAddress end=============");
//		return new BaseResp<MainCommonAddressQueryResponse>(IntfzRs.SUCCESS, "ok", change);
//	}
	
}
