package sijibao.oa.jeesite.modules.intfz.web.birequest;//package com.sijibao.oa.modules.intfz.web.birequest;
//
//import com.google.common.collect.Lists;
//import com.sijibao.base.common.api.response.PagerResponse;
//import com.sijibao.oa.common.utils.WebUtils;
//import com.sijibao.oa.common.web.BaseController;
//import com.sijibao.oa.crm.api.IntfzWebMarketDailyService;
//import com.sijibao.oa.crm.api.request.daily.DailyHandleReq;
//import com.sijibao.oa.crm.api.response.daily.MarketCustBIResp;
//import com.sijibao.oa.crm.api.response.daily.MarketDailyBIResp;
//import com.sijibao.oa.modules.intfz.bean.BaseResp;
//import com.sijibao.oa.modules.intfz.enums.IntfzRs;
//import com.sijibao.oa.modules.intfz.request.daily.BIReq;
//import com.sijibao.oa.modules.intfz.response.common.PageResponse;
//import com.sijibao.oa.modules.intfz.response.daily.MainMarketCustBIResp;
//import com.sijibao.oa.modules.intfz.response.daily.MainMarketDailyBIResp;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * WEB市场日报-大数据统计
// * 2019-03-21 14:31:59
// * wanxb
// */
//@Controller
//@RequestMapping(value = "quartz/marketForBI")
//@Api(value = "BI市场日报-大数据统计", tags = "市场日报及客户维护管理")
//public class IntfzWebMarketBIController extends BaseController{
//
//	@Autowired
//	private IntfzWebMarketDailyService intfzWebMarketDailyService;
//
//	@RequestMapping(value = "queryMarketForBI")
//	@ResponseBody
//	@ApiOperation(httpMethod = "POST", value = "WEB市场日报-（市场日报及客户维护管理）")
//	public BaseResp<PageResponse<List<MainMarketDailyBIResp>>> queryMarketForBI(
//			@RequestBody BIReq req,
//			HttpServletRequest request, HttpServletResponse response) {
//		WebUtils.initPre(request, response);
//		logger.info("===IntfzWebMarketBIController  queryMarketForBI req===: " + req.toString());
//
//		DailyHandleReq change = change(req,DailyHandleReq.class);
//		PagerResponse<MarketDailyBIResp> findPage = intfzWebMarketDailyService.queryMarketForBI(change);
//		ArrayList<MainMarketDailyBIResp> list = Lists.newArrayList();
//		for (MarketDailyBIResp s : findPage.getList()) {
//			MainMarketDailyBIResp ss = change(s, MainMarketDailyBIResp.class );
//			List<MainMarketCustBIResp> li = Lists.newArrayList();
//			for(MarketCustBIResp ma :s.getMarket()){
//				li.add(change(ma, MainMarketCustBIResp.class));
//			}
//			ss.setMarket(li);
//			list.add(ss);
//		}
//		logger.info("=======IntfzWebMarketBIController queryMarketForBI end=============");
//		return new BaseResp<PageResponse<List<MainMarketDailyBIResp>>>(IntfzRs.SUCCESS, "ok",
//				new PageResponse<List<MainMarketDailyBIResp>>(list, req.getPageNo(), findPage.getCount()));
//	}
//
//}
