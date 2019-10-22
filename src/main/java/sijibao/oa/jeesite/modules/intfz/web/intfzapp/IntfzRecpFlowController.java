package sijibao.oa.jeesite.modules.intfz.web.intfzapp;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
import com.sijibao.oa.modules.flow.entity.RecpFlow;
import com.sijibao.oa.modules.flow.service.RecpFlowService;
import com.sijibao.oa.modules.intfz.bean.BaseResp;
import com.sijibao.oa.modules.intfz.enums.IntfzRs;
import com.sijibao.oa.modules.intfz.request.recp.*;
import com.sijibao.oa.modules.intfz.response.DemandManagementBudgetResponse;
import com.sijibao.oa.modules.intfz.response.common.FlowLogResponse;
import com.sijibao.oa.modules.intfz.response.common.PageResponse;
import com.sijibao.oa.modules.intfz.response.recp.RecpFlowDetailResponse;
import com.sijibao.oa.modules.intfz.response.recp.RecpFlowResponse;
import com.sijibao.oa.modules.intfz.service.recp.IntfzRecpFlowService;
import com.sijibao.oa.modules.intfz.utils.CharChangeUtils;
import com.sijibao.oa.modules.intfz.utils.Constant;
import com.sijibao.oa.modules.intfz.validator.AppAuthority;
import com.sijibao.oa.modules.sys.entity.User;
import com.sijibao.oa.modules.sys.service.SystemService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 接待流程相关
 * @author xuby
 */
@Controller
@RequestMapping(value = "wechat/recpFlow")
@Api(value="APP接待申请流程服务",tags="APP接待申请流程服务")
public class IntfzRecpFlowController extends BaseController  {
	
	@Autowired
	private IntfzRecpFlowService intfzRecpFlowService;
	@Autowired
	private ActTaskService actTaskService;
	@Autowired
	private RecpFlowService recpFlowService;
	@Autowired
	private SystemService systemService;
	/**
	 * 接待申请
	 * @return
	 * @throws UnsupportedEncodingException 
	 * @throws IOException 
	 */
	@RequestMapping(value = "recpApply")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "接待申请-发起申请")
    @AppAuthority
	public BaseResp<String> recpApply(
			@RequestBody RecpFlowRequest req,
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
			//查询手机号
			List<String> ids = recpFlowService.queryLoginNames(req.getEmployees());
			String[] employees = ids.toArray(new String[ids.size()]);
			req.setEmployees(employees);
			intfzRecpFlowService.recpApplyService(req, user,Constant.CLIENT_TYPE_APP); //接待申请
		} catch (ServiceException e) {
			return new BaseResp<String>(IntfzRs.ERROR, e.getMessage(), "");
		}
		return new BaseResp<String>(IntfzRs.SUCCESS, "申请发起成功", "");
	}
	
	/**
	 * 查询陪客人员
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "escortEmployee")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "接待申请-查询陪客人员")
    @AppAuthority
	public BaseResp<List<User>> escortEmployee(@ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
											   @ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
											   HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		List<User> userLists = systemService.findUserNotAccess(new User());
		//过滤离职人员
		List<User> userList = Lists.newArrayList();
		for(User u : userLists){
			if("1".equals(u.getUserStatus())){
				userList.add(u);
			}
		}
		return new BaseResp<List<User>>(IntfzRs.SUCCESS,"ok",userList);
	}
	
	/**
	 * 接待申请单据保存
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "saveRecpFlowInfo")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "接待申请-单据保存草稿")
    @AppAuthority
	public BaseResp<String> saveRecpFlowInfo(@RequestBody RecpFlowRequest req,
											 @ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
											 @ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
											 HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		User user = super.getCurrWxUser(sjboacert, clientType); //获取当前申请人信息
		CharChangeUtils.CharChange(req);//替换英文字符
		//查询手机号
		List<String> ids = recpFlowService.queryLoginNames(req.getEmployees());
		String[] employees = ids.toArray(new String[ids.size()]);
		req.setEmployees(employees);
		intfzRecpFlowService.saveRecpFlowService(req, user,Constant.CLIENT_TYPE_APP); //接待申请保存
		return new  BaseResp<String>(IntfzRs.SUCCESS, "单据保存成功", "");
	}
	
	/**
	 * 单据审批 同意/驳回
	 * @param response
	 * @throws Exception 
	 * @throws IOException
	 */
	@RequestMapping(value = "recpFlowCompleteTask")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "接待申请-同意/驳回")
    @AppAuthority
	public BaseResp<String> recpFlowCompleteTask(@RequestBody RecpFlowCompleteTaskReq req,
												 @ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
												 @ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
												 HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		//接收复杂json格式的参数
		User user = super.getCurrWxUser(sjboacert, clientType); //获取当前申请人信息
		CharChangeUtils.CharChange(req);//替换英文字符
		if (StringUtils.isBlank(req.getComment()) && "no".equals(req.getFlag())) {
			logger.error("=======IntfzRecpFlowController recpFlowCompleteTask end=============请填写审核意见");
			return new BaseResp<String>(IntfzRs.ERROR, "请填写审核意见", "");
		}
		if (StringUtils.isBlank(req.getProcInsId())){
			logger.error("=======IntfzRecpFlowController recpFlowCompleteTask end=============procInsId is not null");
			return new BaseResp<String>(IntfzRs.ERROR, "procInsId is not null", "");
		}
		if(StringUtils.isBlank(req.getRecpFlowId())){
			logger.error("=======IntfzRecpFlowController recpFlowCompleteTask end=============recpFlowId is not null");
			return new BaseResp<String>(IntfzRs.ERROR, "expenseFormId is not null", "");
		}
//		RecpFlow recpFlow = new RecpFlow();
		RecpFlow recpFlow = recpFlowService.get(req.getRecpFlowId());
		recpFlow.getAct().setFlag(req.getFlag()); //意见状态yes/no
		recpFlow.getAct().setComment(StringUtils.isBlank(req.getComment())?"同意":req.getComment()); //审批意见
		recpFlow.getAct().setProcInsId(req.getProcInsId()); //流程实例ID
		recpFlow.setId(req.getRecpFlowId()); //申请接待申请ID
		
		Act act = new Act();
		act.setAssignee(user.getLoginName()); //当前登录用户
		act.setProcInsId(req.getProcInsId()); //流程实例ID
		act = actTaskService.queryThisRunTaskId(act);
		if(act == null || StringUtils.isBlank(act.getTaskId())){
			logger.error("=======IntfzRecpFlowController recpFlowCompleteTask end=============找不到当前流程任务信息，禁止提交");
			return new BaseResp<String>(IntfzRs.ERROR, "找不到当前流程任务信息，禁止提交", "");
		}else{
			recpFlow.getAct().setTaskId(act.getTaskId());
		}
		intfzRecpFlowService.recpFlowCompleteTask(recpFlow,user); //完成当前任务
		return new BaseResp<String>(IntfzRs.SUCCESS, "审批成功", "");
	}
	
	/**
	 * 删除接待申请
	 * @param response
	 * @throws Exception 
	 * @throws IOException 
	 */
	@RequestMapping(value = "recpFlowRepealApply")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "接待申请-删除单据")
    @AppAuthority
	public BaseResp<String> recpFlowRepealApply(@RequestBody RecpFlowHandleReq req,
												@ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
												@ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
												HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		if (StringUtils.isBlank(req.getRecpFlowId())){
			logger.info("=======IntfzRecpFlowController recpFlowRepealApply start=============recpFlowId is not null");
			return new BaseResp<String>(IntfzRs.ERROR, "recpFlowId is not null", "");
		}
		RecpFlow recpFlowTmp = recpFlowService.get(req.getRecpFlowId()); //查询接待申请实体
		if(recpFlowTmp == null){
			return new BaseResp<String>(IntfzRs.ERROR, "未找到需要删除的单据信息", "");
		}
		if(Constant.expense_save.equals(recpFlowTmp.getRecpStatus())){ //保存状态不需要删除task
			recpFlowTmp.getAct().setTaskId(null); //任务ID
		}
		intfzRecpFlowService.deleteRecpFlowInfo(recpFlowTmp);  //删除单据
		return new BaseResp<String>(IntfzRs.SUCCESS, "删除接待申请成功", "");
	}
	
	/**
	 * 查询我的单据列表
	 * @param request  pageNo:当前页, pageSize:每页记录数
	 * @param response data:{"list":[], pageNo:"当前页", total:"当前页数"}
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "queryMyRecpFlowList")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "接待申请-查询列表")
    @AppAuthority
	public BaseResp<PageResponse<List<RecpFlow>>> queryMyRecpFlowList(
			@RequestBody RecpFlowListReq req,
			@ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
			@ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		if(req.getPageNo()==0 || req.getPageSize()==0){
			return new BaseResp<PageResponse<List<RecpFlow>>>(IntfzRs.ERROR, "分页参数不能为空！", 
					new PageResponse<List<RecpFlow>>(null, 0, 0));
		}
		User user = super.getCurrWxUser(sjboacert, clientType); //获取当前申请人信息
		RecpFlow query = new RecpFlow();
		query.setCreateBy(user);
		if(Constant.expense_approve.equals(String.valueOf(req.getRecpStatus()))){ //运行中搜索包括审批中和被驳回
			query.setRecpStatusIn("2,3");
			query.setRecpStatus("");
		}else if(Constant.expense_approve_end.equals(String.valueOf(req.getRecpStatus()))){
			query.setRecpStatusIn("1,0");
			query.setRecpStatus("");
		}else{
			query.setRecpStatus("4");
		}
		query.setApplyPerName(req.getApplyName());
		query.setProcCode(req.getProcCode());
		query.setBeginApplyTime(DateUtils.parseDate(req.getApplyTimeStart()));
		query.setEndApplyTime(DateUtils.parseDate(req.getApplyTimeEnd()));
		Page<RecpFlow> page = recpFlowService.findPage(
				new Page<RecpFlow>(req.getPageNo(), req.getPageSize()), query); //查询个人所有单据信息
		List<RecpFlow> resultList = page.getList();
		return new BaseResp<PageResponse<List<RecpFlow>>>(IntfzRs.SUCCESS, "ok", 
				new PageResponse<List<RecpFlow>>(resultList, page.getPageNo(), page.getCount()));
	}
	
	/**
	 * 流程发起
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "recpFlowStartWorkFlow")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "接待申请-草稿发起申请")
    @AppAuthority
	public BaseResp<String> recpFlowStartWorkFlow(@RequestBody RecpFlowHandleReq req,
												  @ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
												  @ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
												  HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		String recpFlowId = req.getRecpFlowId();
		logger.info("===IntfzRecpFlowController recpFlowStartWorkFlow[req]====== " + recpFlowId);
		User user = this.getCurrWxUser(sjboacert, clientType);
		if(StringUtils.isBlank(recpFlowId)){
			return new  BaseResp<String>(IntfzRs.ERROR, "recpFlowId不能为空", "");
		}
		RecpFlow recpFLow = recpFlowService.get(recpFlowId);
		intfzRecpFlowService.recpStartWorkFlow(recpFLow, user);
		return new  BaseResp<String>(IntfzRs.SUCCESS, "接待申请提交成功", "");
	}
	
	/**
	 * 流程收回接口
	 * @return
	 */
	@RequestMapping(value = "recpFlowRepealTask")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "接待申请-流程撤销")
    @AppAuthority
	public BaseResp<String> recpFlowRepealTask(@RequestBody RecpFlowRepealRequest recpFlowRepealRequest,
											   @ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
											   @ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
											   HttpServletRequest request, HttpServletResponse response){
		if(StringUtils.isBlank(recpFlowRepealRequest.getTaskId())){
			return new BaseResp<String>(IntfzRs.ERROR, "任务ID不能为空!", "");
		}
		if(StringUtils.isBlank(recpFlowRepealRequest.getProcInsId())){
			return new BaseResp<String>(IntfzRs.ERROR, "流程实例ID不能为空!", "");
		}
		//判断当前单据已经审批结束
		RecpFlow e = recpFlowService.getByProcInsId(recpFlowRepealRequest.getProcInsId());
		if(Constant.expense_approve_end.equals(e.getRecpStatus())){
			return new BaseResp<String>(IntfzRs.ERROR, "当前单据已经审批结束，无需进行收回!", "");
		}
		//获取当前申请人信息
		User user = super.getCurrWxUser(sjboacert, clientType);
		//判断当前任务是否已经在本人待办任务中
		Act act = new Act();
		act.setProcInsId(recpFlowRepealRequest.getProcInsId());
		act = actTaskService.queryThisRunTaskId(act);
		if(act != null && StringUtils.isNotBlank(act.getAssignee()) && user.getLoginName().equals(act.getAssignee())){
			return new BaseResp<String>(IntfzRs.ERROR, "当前单据已在个人待处理任务中，无需进行收回!", "");
		}
		//发起撤销
		RecpFlow recpFlow = new RecpFlow();
		recpFlow.getAct().setProcInsId(recpFlowRepealRequest.getProcInsId());
		recpFlow.getAct().setTaskId(recpFlowRepealRequest.getTaskId());
		intfzRecpFlowService.repealTask(recpFlow,user);
		return new BaseResp<String>(IntfzRs.SUCCESS, "流程收回成功!", "");
	}
	
	
	/**
	 * 获取审批流程详情接口
	 * url:flowdetail
	 * request procInsId:流程实例ID(必填), procDefId:流程定义ID(必填), taskId:任务ID(必填)
	 * response data:{"list":[]}
	 * @throws Exception 
	 */
	@RequestMapping(value = "recpFlowDetail")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "接待申请-查询审批流程详情")
    @AppAuthority
	public BaseResp<RecpFlowDetailResponse> recpFlowDetail(
			@RequestBody RecpFlowHandleReq act,
			@ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
			@ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		User curUser = this.getCurrWxUser(sjboacert, clientType);
		// 获取流程实例对象
		if (StringUtils.isBlank(act.getRecpFlowId())){
			logger.info("=======IntfzRecpFlowController recpFlowDetail end=============参数不能为空");
			return new BaseResp<RecpFlowDetailResponse>(IntfzRs.ERROR, "参数不能为空", new RecpFlowDetailResponse());
		}
		RecpFlow recpFlow = recpFlowService.get(act.getRecpFlowId()); 
		RecpFlowResponse recpFlowResponse = null;
		if(recpFlow != null){
			intfzRecpFlowService.updateReadStatus(recpFlow, curUser);
			recpFlowResponse = intfzRecpFlowService.recpFlowDetailApp(recpFlowResponse, recpFlow, curUser);
		}else{
			return new BaseResp<RecpFlowDetailResponse>(IntfzRs.ERROR, "查询不到当前申请详细信息", new RecpFlowDetailResponse());
		}
		//查询日志
		List<Act> histoicFlowList = new ArrayList<>();
		if(StringUtils.isNotBlank(recpFlowResponse.getProcInsId())){ //实例ID不为空则进行查询
			histoicFlowList = actTaskService.histoicFlowList(recpFlowResponse.getProcInsId(), "", "");
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
		if(Constant.expense_delete.equals(recpFlow.getRecpStatus())){
			flowloglist.add(new FlowLogResponse("已删除",recpFlow.getApplyPerName(), "", "", "",""));
		}
		
		List<DemandManagementBudgetResponse> detailsList = Lists.newArrayList();
		detailsList = intfzRecpFlowService.recpDemandBudgetDetail(detailsList, recpFlow,Constant.CLIENT_TYPE_APP);
		return new BaseResp<RecpFlowDetailResponse>(IntfzRs.SUCCESS, "ok", 
				new RecpFlowDetailResponse(recpFlowResponse, detailsList, flowloglist));
	}
	
	/**
	 * 接待关联查询列表
	 * @param sjboacert
	 * @param clientType
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * POST 
	 */
	@RequestMapping(value = "recpFlowRevencelist")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "接待申请-关联查询列表")
    @AppAuthority
	public BaseResp<List<RecpFlowResponse>> recpFlowRevencelist(
			@ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
			@ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		User user = super.getCurrWxUser(sjboacert, clientType); //获取当前申请人信息
		if(user == null){
			return new BaseResp<List<RecpFlowResponse>>(IntfzRs.ERROR, "未找到用户信息", null);
		}
		List<RecpFlowResponse> list = intfzRecpFlowService.recpFlowRevencelist(user);
		return new BaseResp<List<RecpFlowResponse>>(IntfzRs.SUCCESS,"",list);
	}
}
