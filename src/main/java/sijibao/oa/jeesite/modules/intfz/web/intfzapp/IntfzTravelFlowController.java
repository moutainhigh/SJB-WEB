package sijibao.oa.jeesite.modules.intfz.web.intfzapp;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.sijibao.oa.common.persistence.Page;
import com.sijibao.oa.common.service.ServiceException;
import com.sijibao.oa.common.utils.DateUtils;
import com.sijibao.oa.common.utils.StringUtils;
import com.sijibao.oa.common.utils.WebUtils;
import com.sijibao.oa.common.web.BaseController;
import com.sijibao.oa.modules.act.entity.Act;
import com.sijibao.oa.modules.act.service.ActTaskService;
import com.sijibao.oa.modules.flow.entity.TravelFlow;
import com.sijibao.oa.modules.flow.service.TravelFlowService;
import com.sijibao.oa.modules.intfz.bean.BaseResp;
import com.sijibao.oa.modules.intfz.enums.IntfzRs;
import com.sijibao.oa.modules.intfz.request.travel.*;
import com.sijibao.oa.modules.intfz.response.DemandManagementBudgetResponse;
import com.sijibao.oa.modules.intfz.response.common.FlowLogResponse;
import com.sijibao.oa.modules.intfz.response.common.PageResponse;
import com.sijibao.oa.modules.intfz.response.travel.TravelFlowDetailResponse;
import com.sijibao.oa.modules.intfz.response.travel.TravelFlowResponse;
import com.sijibao.oa.modules.intfz.service.travel.IntfzTravelFlowService;
import com.sijibao.oa.modules.intfz.utils.CharChangeUtils;
import com.sijibao.oa.modules.intfz.utils.Constant;
import com.sijibao.oa.modules.intfz.validator.AppAuthority;
import com.sijibao.oa.modules.sys.entity.User;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * app端出差申请流程
 * @author xuby
 *
 */
@Controller
@RequestMapping(value = "wechat/travelFlow")
@Api(value="APP出差申请流程服务",tags="APP出差申请流程服务")
public class IntfzTravelFlowController extends BaseController{
	@Autowired
	private IntfzTravelFlowService intfzTravelFlowService;
	@Autowired
	private ActTaskService actTaskService;
	@Autowired
	private TravelFlowService travelFlowService;
	/**
	 * app端出差申请
	 * @param TravelFlowRequest
	 * @return
	 * @throws UnsupportedEncodingException 
	 * @throws IOException 
	 */
	@RequestMapping(value = "travelApply")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "app端出差申请-发起申请")
    @AppAuthority
	public BaseResp<String> travelApply(
			@RequestBody TravelFlowRequest req,
			@ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
			@ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		User user = super.getCurrWxUser(sjboacert, clientType); //获取当前申请人信息
		CharChangeUtils.CharChange(req);//替换英文字符
		req.setProducSide("APP");
		if(user == null){
			return new BaseResp<String>(IntfzRs.ERROR, "未找到用户信息", "");
		}
		try {
			intfzTravelFlowService.travelApplyService(req, user,Constant.CLIENT_TYPE_APP); //出差申请
		} catch (ServiceException e) {
			return new BaseResp<String>(IntfzRs.ERROR, e.getMessage(), "");
		}
		return new BaseResp<String>(IntfzRs.SUCCESS, "申请发起成功", "");
	}
	
	/**
	 * 流程发起
	 * @param travelFlowId 业务ID
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "travelFlowStartWorkFlow")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "Web出差申请-草稿发起申请")
    @AppAuthority
	public BaseResp<String> travelFlowStartWorkFlow(@RequestBody TravelFlowHandleReq req,
													@ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
													@ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
													HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		String travelFlowId = req.getTravelFlowId();
		User user = super.getCurrWxUser(sjboacert, clientType); //获取当前申请人信息
		CharChangeUtils.CharChange(req);//替换英文字符
		if(StringUtils.isBlank(travelFlowId)){
			return new  BaseResp<String>(IntfzRs.ERROR, "出差申请ID不能为空", "");
		}
		TravelFlow recpFLow = travelFlowService.get(travelFlowId);
		intfzTravelFlowService.travelStartWorkFlow(recpFLow, user);
		return new  BaseResp<String>(IntfzRs.SUCCESS, "出差申请提交成功", "");
	}
	
	/**
	 * 流程收回接口
	 * @return
	 */
	@RequestMapping(value = "travelFlowRepealTask")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "Web出差申请-流程撤销")
    @AppAuthority
	public BaseResp<String> travelFlowRepealTask(@RequestBody TravelFlowRepealRequest travelFlowRepealRequest,
												 @ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
												 @ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
												 HttpServletRequest request, HttpServletResponse response){
		if(StringUtils.isBlank(travelFlowRepealRequest.getTaskId())){
			return new BaseResp<String>(IntfzRs.ERROR, "任务ID不能为空!", "");
		}
		if(StringUtils.isBlank(travelFlowRepealRequest.getProcInsId())){
			return new BaseResp<String>(IntfzRs.ERROR, "流程实例ID不能为空!", "");
		}
		//判断当前单据已经审批结束
		TravelFlow e = travelFlowService.getByProcInsId(travelFlowRepealRequest.getProcInsId());
		if(Constant.expense_approve_end.equals(e.getTravelStatus())){
			return new BaseResp<String>(IntfzRs.ERROR, "当前单据已经审批结束，无需进行收回!", "");
		}
		User user = super.getCurrWxUser(sjboacert, clientType); //获取当前申请人信息
		//判断当前任务是否已经在本人待办任务中
		Act act = new Act();
		act.setProcInsId(travelFlowRepealRequest.getProcInsId());
		act = actTaskService.queryThisRunTaskId(act);
		if(act != null && StringUtils.isNotBlank(act.getAssignee()) && user.getLoginName().equals(act.getAssignee())){
			return new BaseResp<String>(IntfzRs.ERROR, "当前单据已在个人待处理任务中，无需进行收回!", "");
		}
		//发起撤销
		TravelFlow travelFlow = new TravelFlow();
		travelFlow.getAct().setProcInsId(travelFlowRepealRequest.getProcInsId());
		travelFlow.getAct().setTaskId(travelFlowRepealRequest.getTaskId());
		intfzTravelFlowService.repealTask(travelFlow,user);
		return new BaseResp<String>(IntfzRs.SUCCESS, "流程收回成功!", "");
	}
	
	/**
	 * 获取审批流程详情接口
	 * url:flowdetail
	 * request procInsId:流程实例ID(必填), procDefId:流程定义ID(必填), taskId:任务ID(必填)
	 * response data:{"list":[]}
	 * @throws Exception 
	 */
	@RequestMapping(value = "retravelFlowDetail")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "Web出差申请-查询审批流程详情")
    @AppAuthority
	public BaseResp<TravelFlowDetailResponse> retravelFlowDetail(
			@RequestBody TravelFlowHandleReq travelFlowHandleReq,
			@ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
			@ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		User curUser = super.getCurrWxUser(sjboacert, clientType); //获取当前申请人信息
		// 获取流程实例对象
		if (StringUtils.isBlank(travelFlowHandleReq.getTravelFlowId())){
			logger.info("=======IntfzTravelFlowController travelFlowDetail end=============参数不能为空");
			return new BaseResp<TravelFlowDetailResponse>(IntfzRs.ERROR, "参数不能为空", new TravelFlowDetailResponse());
		}
		TravelFlow travelFlow = travelFlowService.get(travelFlowHandleReq.getTravelFlowId()); 
		TravelFlowResponse travelFlowResponse = null;
		if(travelFlow != null){
			intfzTravelFlowService.updateReadStatus(travelFlow, curUser);
			travelFlowResponse = intfzTravelFlowService.travelFlowDetail(travelFlow, curUser);
		}else{
			return new BaseResp<TravelFlowDetailResponse>(IntfzRs.ERROR, "查询不到当前申请详细信息", new TravelFlowDetailResponse());
		}
		//判断关联类型
		if(StringUtils.isNotBlank(travelFlowResponse.getRelationTheme())){//关联的是主题
			travelFlowResponse.setRelType("1");
		}else if(StringUtils.isNotBlank(travelFlowResponse.getProjectId())){//关联的是项目
			travelFlowResponse.setRelType("2");
		}else {//不关联
			travelFlowResponse.setRelType("3");
		}
		//查询日志
		List<Act> histoicFlowList = new ArrayList<>();
		if(StringUtils.isNotBlank(travelFlowResponse.getProcInsId())){ //实例ID不为空则进行查询
			histoicFlowList = actTaskService.histoicFlowList(travelFlowResponse.getProcInsId(), "", "");
		}
		
		List<FlowLogResponse> flowloglist = Lists.newArrayList();
		for (int i = 0; i < histoicFlowList.size(); i++) {
			Act tmpAct = histoicFlowList.get(i);
			String startTime = DateUtils.formatDate(tmpAct.getHistIns().getStartTime(), DateUtils.YYYY_MM_DD_HH_MM_SS);
			String endTime = "";
			if(tmpAct.getHistIns().getEndTime() != null){
				endTime = DateUtils.formatDate(tmpAct.getHistIns().getEndTime(), DateUtils.YYYY_MM_DD_HH_MM_SS);
			}
			flowloglist.add(new FlowLogResponse(tmpAct.getHistIns().getActivityName(), tmpAct.getAssigneeName(), startTime, endTime, tmpAct.getComment(), tmpAct.getDurationTime()));
		}
		if(Constant.expense_delete.equals(travelFlow.getTravelStatus())){
			flowloglist.add(new FlowLogResponse("已删除",travelFlow.getApplyPerName(), "", "", "",""));
		}
		
		List<DemandManagementBudgetResponse> detailsList = Lists.newArrayList();
		detailsList = intfzTravelFlowService.travelDemandBudgetDetail(detailsList, travelFlow,Constant.CLIENT_TYPE_APP);
		return new BaseResp<TravelFlowDetailResponse>(IntfzRs.SUCCESS, "ok", 
				new TravelFlowDetailResponse(travelFlowResponse, detailsList, flowloglist));
	}

	
	/**
	 * app端出差申请单据保存
	 * @param TravelFlowRequest
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "saveTravelFlowInfo")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "app端出差申请-单据保存草稿")
    @AppAuthority
	public BaseResp<String> saveTravelFlowInfo(@RequestBody TravelFlowRequest req,
											   @ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
											   @ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
											   HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		User user = super.getCurrWxUser(sjboacert, clientType); //获取当前申请人信息
		CharChangeUtils.CharChange(req);//替换英文字符

		intfzTravelFlowService.saveTravelFlowService(req, user,Constant.CLIENT_TYPE_APP); //出差申请保存
		return new  BaseResp<String>(IntfzRs.SUCCESS, "单据保存成功", "");
	}
	
	/**
	 * app端单据审批 同意/驳回
	 * @param response
	 * @throws Exception 
	 * @throws IOException
	 */
	@RequestMapping(value = "travelFlowCompleteTask")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "app端出差申请-同意/驳回")
    @AppAuthority
	public BaseResp<String> travelFlowCompleteTask(@RequestBody TravelFlowCompleteTaskReq req,
												   @ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
												   @ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
												   HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		//接收复杂json格式的参数
		User user = super.getCurrWxUser(sjboacert, clientType); //获取当前申请人信息
		CharChangeUtils.CharChange(req);//替换英文字符
		if (StringUtils.isBlank(req.getComment()) && "no".equals(req.getFlag())) {
			logger.error("=======IntfzTravelFlowController travelFlowCompleteTask end=============请填写审核意见");
			return new BaseResp<String>(IntfzRs.ERROR, "请填写审核意见", "");
		}
		if (StringUtils.isBlank(req.getProcInsId())){
			logger.error("=======IntfzTravelFlowController travelFlowCompleteTask end=============procInsId is not null");
			return new BaseResp<String>(IntfzRs.ERROR, "procInsId is not null", "");
		}
		if(StringUtils.isBlank(req.getTravelFlowId())){
			logger.error("=======IntfzTravelFlowController travelFlowCompleteTask end=============travelFlowId is not null");
			return new BaseResp<String>(IntfzRs.ERROR, "travelFlowId is not null", "");
		}
		TravelFlow travelFlow = new TravelFlow();
		travelFlow.getAct().setFlag(req.getFlag()); //意见状态yes/no
		travelFlow.getAct().setComment(StringUtils.isBlank(req.getComment())?"同意":req.getComment()); //审批意见
		travelFlow.getAct().setProcInsId(req.getProcInsId()); //流程实例ID
		travelFlow.setId(req.getTravelFlowId()); //申请出差申请ID
		
		Act act = new Act();
		act.setAssignee(user.getLoginName()); //当前登录用户
		act.setProcInsId(req.getProcInsId()); //流程实例ID
		act = actTaskService.queryThisRunTaskId(act);
		if(act == null || StringUtils.isBlank(act.getTaskId())){
			logger.error("=======IntfzTravelFlowController travelFlowCompleteTask end=============找不到当前流程任务信息，禁止提交");
			return new BaseResp<String>(IntfzRs.ERROR, "找不到当前流程任务信息，禁止提交", "");
		}else{
			travelFlow.getAct().setTaskId(act.getTaskId());
		}
		intfzTravelFlowService.travelFlowCompleteTask(travelFlow,user); //完成当前任务
		return new BaseResp<String>(IntfzRs.SUCCESS, "审批成功", "");
	}
	
	/**
	 * app端删除出差申请
	 * @param travelFlow 出差申请ID
	 * @param taskId 当前任务ID
	 * @param response
	 * @throws Exception 
	 * @throws IOException 
	 */
	@RequestMapping(value = "travelFlowRepealApply")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "app端出差申请-删除单据")
    @AppAuthority
	public BaseResp<String> travelFlowRepealApply(@RequestBody TravelFlowHandleReq req,
												  @ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
												  @ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
												  HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		if (StringUtils.isBlank(req.getTravelFlowId())){
			logger.info("=======IntfzTravelFlowController travelFlowRepealApply start=============travelFlowId is not null");
			return new BaseResp<String>(IntfzRs.ERROR, "travelFlowId is not null", "");
		}
		TravelFlow travelFlowTmp = travelFlowService.get(req.getTravelFlowId()); //查询出差申请实体
		if(travelFlowTmp == null){
			return new BaseResp<String>(IntfzRs.ERROR, "未找到需要删除的单据信息", "");
		}
		if(Constant.expense_save.equals(travelFlowTmp.getTravelStatus())){ //保存状态不需要删除task
			travelFlowTmp.getAct().setTaskId(null); //任务ID
		}
		intfzTravelFlowService.deleteTravelFlowInfo(travelFlowTmp);  //删除单据
		return new BaseResp<String>(IntfzRs.SUCCESS, "删除出差申请成功", "");
	}
	
	/**
	 * app端查询我的单据列表
	 * @param travelFlow
	 * @param request  pageNo:当前页, pageSize:每页记录数
	 * @param response data:{"list":[], pageNo:"当前页", total:"当前页数"}
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "queryMyTravelFlowList")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "app端出差申请-查询列表")
    @AppAuthority
	public BaseResp<PageResponse<List<TravelFlow>>> queryMyTravelFlowList(
			@RequestBody TravelFlowListReq req,
			@ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
			@ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		if(req.getPageNo()==0 || req.getPageSize()==0){
			return new BaseResp<PageResponse<List<TravelFlow>>>(IntfzRs.ERROR, "分页参数不能为空！", 
					new PageResponse<List<TravelFlow>>(null, 0, 0));
		}
		User user = super.getCurrWxUser(sjboacert, clientType); //获取当前申请人信息
		TravelFlow query = new TravelFlow();
		query.setCreateBy(user);
		
		if(Constant.expense_approve.equals(String.valueOf(req.getTravelStatus()))){ //运行中搜索包括审批中和被驳回
			query.setTravelStatusIn("2,3");
			query.setTravelStatus("");
		}else if(Constant.expense_approve_end.equals(String.valueOf(req.getTravelStatus()))){
			query.setTravelStatusIn("1,0");
			query.setTravelStatus("");
		}else{
			query.setTravelStatus("4");
		}
		
		query.setProcName(req.getProcName());
		query.setProjectId(req.getProjectId());
		query.setApplyPerName(req.getApplyName());
		query.setProcCode(req.getProcCode());
		query.setBeginApplyTime(DateUtils.parseDate(req.getApplyTimeStart()));
		query.setEndApplyTime(DateUtils.parseDate(req.getApplyTimeEnd()));
		
		Page<TravelFlow> page = travelFlowService.findPage(
				new Page<TravelFlow>(req.getPageNo(), req.getPageSize()), query); //查询个人所有单据信息
		List<TravelFlow> resultList = page.getList();
		for(TravelFlow t:resultList){
			t.setBudgetTotal(t.getBudgetTotal()==null?BigDecimal.ZERO:t.getBudgetTotal());
		}
		return new BaseResp<PageResponse<List<TravelFlow>>>(IntfzRs.SUCCESS, "ok", 
				new PageResponse<List<TravelFlow>>(resultList, page.getPageNo(), page.getCount()));
	}
}
