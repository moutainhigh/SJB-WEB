package sijibao.oa.jeesite.modules.intfz.web.intfzapp;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.sijibao.base.common.api.response.PagerResponse;
import com.sijibao.oa.common.utils.DateUtils;
import com.sijibao.oa.common.utils.WebUtils;
import com.sijibao.oa.common.web.BaseController;
import com.sijibao.oa.crm.api.IntfzWebMarketDailyService;
import com.sijibao.oa.crm.api.exception.ServiceException;
import com.sijibao.oa.crm.api.request.daily.*;
import com.sijibao.oa.crm.api.response.daily.*;
import com.sijibao.oa.modules.intfz.bean.BaseResp;
import com.sijibao.oa.modules.intfz.enums.IntfzRs;
import com.sijibao.oa.modules.intfz.request.common.PageHandleReq;
import com.sijibao.oa.modules.intfz.request.daily.*;
import com.sijibao.oa.modules.intfz.response.common.PageResponse;
import com.sijibao.oa.modules.intfz.response.daily.*;
import com.sijibao.oa.modules.intfz.utils.CharChangeUtils;
import com.sijibao.oa.modules.intfz.validator.AppAuthority;
import com.sijibao.oa.modules.sys.entity.User;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * APP工作日志controller
 * @author wanxb
 *
 */
@Controller
@RequestMapping(value = "wechat/marketDaily")
@Api(value="APP市场日报服务",tags="APP市场日报服务")
public class
IntfzMarketDailyController extends BaseController {
	@Autowired
	private IntfzWebMarketDailyService intfzWebMarketDailyService;

	/**
	 * 市场日报-公共列表查询（分页查询）
	 */
	@RequestMapping(value = "findPage")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "市场日报-公共列表查询（市场、实施日报分页查询）")
    @AppAuthority
	public BaseResp<PageResponse<List<MainDailyResponse>>> findPage(
			@RequestBody MainDailyHandleReq req,
			@ApiParam(value = "clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
			@ApiParam(value = "客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
			HttpServletRequest request, HttpServletResponse response) {

		WebUtils.initPre(request, response);
		DailyHandleReq change = change(req, DailyHandleReq.class);
		User user = super.getCurrWxUser(sjboacert, clientType); //获取当前操作用户信息
		change.setUserId(user.getId());
		PagerResponse<DailyResponse> findPage = intfzWebMarketDailyService.findPage(change);
		ArrayList<MainDailyResponse> list = Lists.newArrayList();
		for (DailyResponse s : findPage.getList()) {
			MainDailyResponse ss = change(s,MainDailyResponse.class );
			list.add(ss);
		}
		return new BaseResp<PageResponse<List<MainDailyResponse>>>(IntfzRs.SUCCESS, "ok",
				new PageResponse<List<MainDailyResponse>>(list, req.getPageNo(), findPage.getCount()));
	}

	/**
	 * 市场日报-保存日报信息
	 */
	@RequestMapping(value = "saveMarketDaily")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "市场日报-保存日报信息")
    @AppAuthority
	public BaseResp<String> saveMarketDaily(
			@RequestBody MainMarketDailySaveReq req,
			@ApiParam(value = "clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
			@ApiParam(value = "客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
			HttpServletRequest request, HttpServletResponse response) {

		WebUtils.initPre(request, response);
		User user = super.getCurrWxUser(sjboacert, clientType); //获取当前操作用户信息
		CharChangeUtils.CharChange(req);//替换英文字符
		req.setProducSide("APP");
		for(MainDailyCustMaintenanceReq daily :req.getDailyCustMaintenanceList()){
			CharChangeUtils.CharChange(daily);//替换英文字符
		}
		MarketDailySaveReq change = change(req, MarketDailySaveReq.class );
		ArrayList<DailyCustMaintenanceReq> list = Lists.newArrayList();
		for (MainDailyCustMaintenanceReq s : req.getDailyCustMaintenanceList()) {
			DailyCustMaintenanceReq ss = change(s,DailyCustMaintenanceReq.class);
			list.add(ss);
		}
		ArrayList<SendToReq> arr = Lists.newArrayList();
		for (String s : req.getSendToList()) {
			SendToReq ss = new SendToReq();
			ss.setSendToId(s);
			arr.add(ss);
		}
		change.setUserId(user.getId());
		change.setDailyCustMaintenanceList(list);
		change.setSendToList(arr);
		change.setCopyToUserList(req.getCopyToList());
		try {
			intfzWebMarketDailyService.saveMarketDaily(change);
		} catch (ServiceException e) {
			return new BaseResp<>(IntfzRs.ERROR, e.getMessage(), "");
		}

		return new BaseResp<>(IntfzRs.SUCCESS, "提交成功！", "");
	}

	/**
	 * 市场日报-详情页面
	 * @param
	 * @param
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getMarketDailyById")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "市场日报-详情页面")
    @AppAuthority
	BaseResp<MainMarketDailyDetailResponse> getMarketDailyById(
			@RequestBody MainMarketDailyReq req,
			@ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
			@ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		if(StringUtils.isBlank(req.getId())){
			return new BaseResp<MainMarketDailyDetailResponse>(IntfzRs.ERROR, "id不能为空",null);
		}
		User user = super.getCurrWxUser(sjboacert, clientType); //获取当前申请人信息
		if(user == null){
			return new BaseResp<MainMarketDailyDetailResponse>(IntfzRs.ERROR, "未找到用户信息",null);
		}
		MarketDailyReq change = change(req, MarketDailyReq.class);
		change.setUserId(user.getId());
		MarketDailyDetailResponse res = intfzWebMarketDailyService.getMarketDailyById(change);
		MainMarketDailyDetailResponse resp = change(res,MainMarketDailyDetailResponse.class);
		ArrayList<MainSendToResponse> list = Lists.newArrayList();
		for (SendToResponse s : res.getSendToList()) {
			MainSendToResponse ss = change(s, MainSendToResponse.class);
			list.add(ss);
		}
		resp.setSendToList(list);
		resp.setCopyToList(res.getCopyToUserList());
		return new BaseResp<MainMarketDailyDetailResponse>(IntfzRs.SUCCESS, "", resp);
	}

	/**
	 * 查询客户维护信息（分页查询）
	 */
	@RequestMapping(value = "getDailyCustMaintenance")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "市场日报-查询客户维护信息（分页查询）")
    @AppAuthority
	public BaseResp<PageResponse<List<MainDailyCustMaintenanceResponse>>> getDailyCustMaintenance(
			@RequestBody MainMarketDailyReq req,
			@ApiParam(value = "clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
			@ApiParam(value = "客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
			HttpServletRequest request, HttpServletResponse response) {

		WebUtils.initPre(request, response);
		MarketDailyReq change = change(req, MarketDailyReq.class);
		PagerResponse<DailyCustMaintenanceResponse> findPage = intfzWebMarketDailyService.queryDailyCustMaintenance(change);
		ArrayList<MainDailyCustMaintenanceResponse> arr = Lists.newArrayList();
		for (DailyCustMaintenanceResponse s : findPage.getList()) {
			MainDailyCustMaintenanceResponse ss = change(s,MainDailyCustMaintenanceResponse.class);
			ss.setCustMaintenanceDateTime(DateUtils.formatDateFromUnix(s.getCustMaintenanceDate(),DateUtils.YYYY_MM_DD_HH_MM_SS));
			arr.add(ss);
		}
		return new BaseResp<PageResponse<List<MainDailyCustMaintenanceResponse>>>(IntfzRs.SUCCESS, "ok",
				new PageResponse<List<MainDailyCustMaintenanceResponse>>(arr, req.getPageNo(), findPage.getCount()));
	}



	/**
	 * 查询当前登入人客户信息及最后一条维护记录
	 * @param
	 * @return
	 */
	@RequestMapping(value = "queryDailyCustMaintenance")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "查询当前登入人客户信息及最后一条维护记录")
    @AppAuthority
	public BaseResp<List<MainDailyCustMaintenanceResponse>> queryDailyCustMaintenance(
			@ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
			@ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
			@RequestBody MainMarketDailyReq req,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		User user = super.getCurrWxUser(sjboacert, clientType); //获取当前申请人信息
		MarketDailyReq change = change(req, MarketDailyReq.class);
		change.setUserId(user.getId());
		List<DailyCustMaintenanceResponse> list = intfzWebMarketDailyService.queryDailyCustMaintenances(change);
		ArrayList<MainDailyCustMaintenanceResponse> arr = Lists.newArrayList();
		for (DailyCustMaintenanceResponse s : list) {
			MainDailyCustMaintenanceResponse ss = change(s,MainDailyCustMaintenanceResponse.class);
			ss.setCustMaintenanceDateTime(DateUtils.formatDateFromUnix(s.getCustMaintenanceDate(),DateUtils.YYYY_MM_DD_HH_MM_SS));
			arr.add(ss);
		}
		return new BaseResp<List<MainDailyCustMaintenanceResponse>>(IntfzRs.SUCCESS,"",arr);
	}


	/**
	 * app端，搜索上次发送给谁
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "querySendToByUserId")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "日报——搜索上次发送给谁")
    @AppAuthority
	public BaseResp<MainToResponse> querySendToByUserId(
			@RequestBody MainDailyReq req,
			@ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
			@ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		User user = super.getCurrWxUser(sjboacert, clientType); //获取当前申请人信息
		SendToReq re = new SendToReq();
		re.setCreateBy(user.getId());
		re.setDailyTemplate(req.getDailyTemplate());
		List<SendToResponse> list = intfzWebMarketDailyService.querySendToByUserId(re);
		ArrayList<MainSendToResponse> lis = Lists.newArrayList();
		ArrayList<MainCopyToResponse> lisc = Lists.newArrayList();
		for (SendToResponse s : list) {
			if("0".equals(s.getSendType())){
				MainSendToResponse ss = change(s,MainSendToResponse.class);
				lis.add(ss);
			}
			if("1".equals(s.getSendType())){
				MainCopyToResponse ss = change(s,MainCopyToResponse.class);
				ss.setCopyToId(s.getSendToId());
				ss.setCopyToName(s.getSendToName());
				lisc.add(ss);
			}
		}
		MainToResponse resp = new MainToResponse();
		resp.setCopyTo(lisc);
		resp.setSendTo(lis);
		return new BaseResp<MainToResponse>(IntfzRs.SUCCESS,"",resp);
	}


	/**
	 * 日报-保存评论
	 */
	@RequestMapping(value = "saveDailyComment")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "WEB日报-保存评论")
    @AppAuthority
	public BaseResp<String> saveDailyComment(
			@RequestBody MainDailyCommentSaveReq req,
			@ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
			@ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
			HttpServletRequest request, HttpServletResponse response) {

		WebUtils.initPre(request, response);
		User user = super.getCurrWxUser(sjboacert, clientType); //获取当前申请人信息
		CharChangeUtils.CharChange(req);//替换英文字符
		req.setProducSide("APP");
		DailyCommentSaveReq change = change(req, DailyCommentSaveReq.class );
		change.setUserId(user.getId());
		try {
			intfzWebMarketDailyService.saveDailyComment(change);
		} catch (ServiceException e) {
			return new BaseResp<>(IntfzRs.ERROR, e.getMessage(), "");
		}

		return new BaseResp<>(IntfzRs.SUCCESS, "批阅成功！", "");
	}


	/**
	 * 日报-批阅查询
	 * @param
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "queryDailyComment")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "WEB日报-批阅查询")
    @AppAuthority
	BaseResp<PageResponse<List<MainDailyCommentResponse>>> queryDailyComment(
			@RequestBody PageHandleReq req,
			@ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
			@ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		PagerHandleReq hRep = change(req,PagerHandleReq.class);
		PagerResponse<DailyCommentResponse> resp = intfzWebMarketDailyService.queryDailyComment(hRep);
		List<MainDailyCommentResponse> list = Lists.newArrayList();
		for(DailyCommentResponse daily :resp.getList()){
			MainDailyCommentResponse ss = change(daily,MainDailyCommentResponse.class);
			ss.setCreateTime(daily.getCreateTime().getTime());
			list.add(ss);
		}
		return new BaseResp<PageResponse<List<MainDailyCommentResponse>>>(IntfzRs.SUCCESS, "ok",
				new PageResponse<List<MainDailyCommentResponse>>(list, req.getPageNo(), resp.getCount()));
	}

}
