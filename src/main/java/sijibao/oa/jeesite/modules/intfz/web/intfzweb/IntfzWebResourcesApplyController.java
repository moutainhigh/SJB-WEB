package sijibao.oa.jeesite.modules.intfz.web.intfzweb;


import java.io.IOException;
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
import com.sijibao.oa.common.config.Global;
import com.sijibao.oa.common.persistence.Page;
import com.sijibao.oa.common.service.ServiceException;
import com.sijibao.oa.common.utils.DateUtils;
import com.sijibao.oa.common.utils.StringUtils;
import com.sijibao.oa.common.utils.WebUtils;
import com.sijibao.oa.common.web.BaseController;
import com.sijibao.oa.modules.act.entity.Act;
import com.sijibao.oa.modules.act.service.ActTaskService;
import com.sijibao.oa.modules.flow.entity.ResourcesApply;
import com.sijibao.oa.modules.flow.service.ResourcesApplyService;
import com.sijibao.oa.modules.intfz.bean.BaseResp;
import com.sijibao.oa.modules.intfz.enums.IntfzRs;
import com.sijibao.oa.modules.intfz.request.resources.*;
import com.sijibao.oa.modules.intfz.response.common.FlowLogResponse;
import com.sijibao.oa.modules.intfz.response.common.PageResponse;
import com.sijibao.oa.modules.intfz.response.resources.ResourcesApplyFlowDetailResponse;
import com.sijibao.oa.modules.intfz.response.resources.ResourcesApplyFlowResponse;
import com.sijibao.oa.modules.intfz.service.resources.IntfzResourcesApplyService;
import com.sijibao.oa.modules.intfz.utils.CharChangeUtils;
import com.sijibao.oa.modules.intfz.utils.Constant;
import com.sijibao.oa.modules.sys.entity.User;
import com.sijibao.oa.modules.sys.utils.SysParamsUtils;
import com.sijibao.oa.modules.sys.utils.UserUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * WEB端资源申请相关接口
 * @author xuby
 */
@Controller
@RequestMapping(value = "${frontPath}/webResourcesApplyFlow")
@Api(value="WEB端资源申请流程服务",tags="WEB端资源申请流程服务")
public class IntfzWebResourcesApplyController extends BaseController {
	
	@Autowired
	private ActTaskService actTaskService;
	@Autowired
	private IntfzResourcesApplyService intfzResourcesApplyService;
	@Autowired
	private ResourcesApplyService resourcesApplyService;
	
	/**
	 * WEB端资源申请发起
	 * @param req
	 * @param sessionid
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "resourcesApply")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "WEB端资源申请-流程申请")
	public BaseResp<String> resourcesApply(
			@RequestBody ResourcesApplyRequest req,
			@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		User user = UserUtils.getUser(sessionid); //获取当前申请人信息
		CharChangeUtils.CharChange(req);//替换英文字符
		req.setProducSide("web");
		if(user == null){
			return new BaseResp<String>(IntfzRs.ERROR, "未找到用户信息", "");
		}
		try {
			intfzResourcesApplyService.resourcesApplyService(req, user); //资源申请
		} catch (ServiceException e) {
			return new BaseResp<String>(IntfzRs.ERROR, e.getMessage(), "");
		}
		return new BaseResp<String>(IntfzRs.SUCCESS, "申请发起成功", "");
	}
	
	/**
	 * WEB端删除资源申请
	 * @param expenseFlowId 业务ID
	 * @param taskId 当前任务ID
	 * @param response
	 * @throws Exception 
	 * @throws IOException 
	 */
	@RequestMapping(value = "deleteResourcesApplyFlow")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "WEB端资源申请-删除单据")
	public BaseResp<String> deleteResourcesApplyFlow(@RequestBody ResourcesApplyFlowHandleReq req,
													 @ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
													 HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		CharChangeUtils.CharChange(req);//替换英文字符
		if (StringUtils.isBlank(req.getResApplyFlowId())){
			logger.info("=======IntfzWebResourcesApplyController deleteResourcesApplyFlow end=============resApplyFlowId is not null");
			return new BaseResp<String>(IntfzRs.ERROR, "resApplyFlowId is not null", "");
		}
		
		ResourcesApply resourcesApply = resourcesApplyService.get(req.getResApplyFlowId()); //查询需求实体
		if(resourcesApply == null){
			return new BaseResp<String>(IntfzRs.ERROR, "未找到需要删除的单据信息", "");
		}
		if(Constant.expense_save.equals(resourcesApply.getResourcesStatus())){ //保存状态不需要删除task
			resourcesApply.getAct().setTaskId(null); //任务ID
		}
	
		intfzResourcesApplyService.deleteResourcesApply(resourcesApply);  //删除单据
		return new BaseResp<String>(IntfzRs.SUCCESS, "删除单据成功", "");
	}

	
	/**
	 * WEB端资源申请保存
	 * @param req
	 * @param sessionid
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "saveResourcesApplyInfo")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "WEB端资源申请-保存草稿")
	public BaseResp<String> saveResourcesApplyInfo(@RequestBody ResourcesApplyRequest req,
												   @ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
												   HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		User user = UserUtils.getUser(sessionid); //获取当前申请人信息
		CharChangeUtils.CharChange(req);//替换英文字符
		intfzResourcesApplyService.saveResourceApplyInfoService(req, user); //资源申请保存
		return new  BaseResp<String>(IntfzRs.SUCCESS, "单据保存成功", "");
	}
	
	/**
	 * WEB端资源申请流程发起
	 * @param req
	 * @param sessionid
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "startWorkFlowResourcesApplyInfo")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "WEB端资源申请-草稿流程发起")
	public BaseResp<String> startWorkFlowResourcesApplyInfo(@RequestBody ResourcesApplyFlowHandleReq req,
															@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
															HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		String businessId = req.getResApplyFlowId();
		logger.info("===IntfzWebResourcesApplyController startWorkFlowResourcesApplyInfo[req]====== " + businessId);
		User user = UserUtils.getUser(sessionid);
		CharChangeUtils.CharChange(req);//替换英文字符
		if(StringUtils.isBlank(businessId)){
			return new  BaseResp<String>(IntfzRs.ERROR, "resApplyFlowId不能为空", "");
		}
		ResourcesApply resourcesApply = resourcesApplyService.get(businessId);
		intfzResourcesApplyService.startWorkFlow(resourcesApply, user);
		return new  BaseResp<String>(IntfzRs.SUCCESS, "申请发起成功", "");
	}
	
	/**
	 * WEB端资源申请审批 同意/驳回
	 * @param actFlowRequest
	 * @param response
	 * @throws Exception 
	 * @throws IOException
	 */
	@RequestMapping(value = "resourcesApplyCompleteTask")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "WEB端资源申请-同意/驳回")
	public BaseResp<String> resourcesApplyCompleteTask(@RequestBody ResourcesApplyFlowCompleteTaskReq req,
													   @ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
													   HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		//接收复杂json格式的参数
		User user = UserUtils.getUser(sessionid);
		CharChangeUtils.CharChange(req);//替换英文字符
		if (req.getAssignList() == null || req.getAssignList().size() == 0) {
			logger.error("=======IntfzWebResourcesApplyController resourcesApplyCompleteTask end=============请选择指派人员和审批意见");
			return new BaseResp<String>(IntfzRs.ERROR, "请选择指派人员和审批意见", "");
		}
		if (StringUtils.isBlank(req.getProcInsId())){
			logger.error("=======IntfzWebResourcesApplyController resourcesApplyCompleteTask end=============procInsId is not null");
			return new BaseResp<String>(IntfzRs.ERROR, "procInsId is not null", "");
		}
		if(StringUtils.isBlank(req.getResApplyFlowId())){
			logger.error("=======IntfzWebResourcesApplyController resourcesApplyCompleteTask end=============resApplyFlowId is not null");
			return new BaseResp<String>(IntfzRs.ERROR, "resApplyFlowId is not null", "");
		}
		ResourcesApply resourcesApply = resourcesApplyService.get(req.getResApplyFlowId());
		resourcesApply.getAct().setFlag(req.getFlag()); //意见状态yes/no
		resourcesApply.getAct().setComment("同意"); //审批意见
		resourcesApply.getAct().setProcInsId(req.getProcInsId()); //流程实例ID
		
		Act act = new Act();
		act.setAssignee(user.getLoginName()); //当前登录用户
		act.setProcInsId(req.getProcInsId()); //流程实例ID
		act = actTaskService.queryThisRunTaskId(act);
		if(act == null || StringUtils.isBlank(act.getTaskId())){
			logger.error("=======IntfzWebResourcesApplyController resourcesApplyCompleteTask end=============找不到当前流程任务信息，禁止提交");
			return new BaseResp<String>(IntfzRs.ERROR, "找不到当前流程任务信息，禁止提交", "");
		}else{
			resourcesApply.getAct().setTaskId(act.getTaskId());
			resourcesApply.getAct().setTaskName(act.getTaskName());
		}
		try {
			intfzResourcesApplyService.resourcesApplyCompleteTask(resourcesApply,user,req.getAssignList()); //完成当前任务
		} catch (ServiceException e) {
			return new BaseResp<String>(IntfzRs.ERROR, e.getMessage(), "");
		}
		return new BaseResp<String>(IntfzRs.SUCCESS, "审批成功", "");
	}
	
	
	/**
	 * Web端查询我的资源申请列表
	 * @param ResourcesApply
	 * @param request  pageNo:当前页, pageSize:每页记录数
	 * @param response data:{"list":[], pageNo:"当前页", total:"当前页数"}
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "queryMyResourcesApplyFlowList")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "Web端资源申请-查询列表")
	public BaseResp<PageResponse<List<ResourcesApply>>> queryMyResourcesApplyFlowList(
			@RequestBody ResourcesApplyFlowListReq req,
			@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		if(req.getPageNo()==0 || req.getPageSize()==0){
			return new BaseResp<PageResponse<List<ResourcesApply>>>(IntfzRs.ERROR, "分页参数不能为空！", 
					new PageResponse<List<ResourcesApply>>(null, 0, 0));
		}
		User user = UserUtils.getUser(sessionid); //获取当前申请人信息
		ResourcesApply query = new ResourcesApply();
		query.setCreateBy(user);
		if(StringUtils.isNotBlank(req.getResourcesApplyStatus())){
			query.setResourcesStatus(String.valueOf(req.getResourcesApplyStatus()));
		}
		query.setProcName(req.getProcName());
		query.setProjectId(req.getProjectId());
		query.setApplyPerName(req.getApplyName());
		query.setProcCode(req.getProcCode());
		query.setBeginApplyTime(DateUtils.parseDate(req.getApplyTimeStart()));
		query.setEndApplyTime(DateUtils.parseDate(req.getApplyTimeEnd()));
		
		Page<ResourcesApply> page = resourcesApplyService.findPage(
				new Page<ResourcesApply>(req.getPageNo(), req.getPageSize()), query); //查询个人所有单据信息
		List<ResourcesApply> resultList = page.getList();
		return new BaseResp<PageResponse<List<ResourcesApply>>>(IntfzRs.SUCCESS, "ok", 
				new PageResponse<List<ResourcesApply>>(resultList, page.getPageNo(), page.getCount()));
	}
	
	/**
	 * Web端查询关联资源申请列表
	 * @param ResourcesApply
	 * @param request  pageNo:当前页, pageSize:每页记录数
	 * @param response data:{"list":[], pageNo:"当前页", total:"当前页数"}
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "queryRelationResourcesApplyFlowList")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "Web端资源申请-查询关联列表")
	public BaseResp<PageResponse<List<ResourcesApply>>> queryRelationResourcesApplyFlowList(
			@RequestBody ResourcesApplyFlowListReq req,
			@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		if(req.getPageNo()==0 || req.getPageSize()==0){
			return new BaseResp<PageResponse<List<ResourcesApply>>>(IntfzRs.ERROR, "分页参数不能为空！", 
					new PageResponse<List<ResourcesApply>>(null, 0, 0));
		}
		User user = UserUtils.getUser(sessionid); //获取当前申请人信息
		ResourcesApply query = new ResourcesApply();
//		query.setCreateBy(user);
		if(StringUtils.isNotBlank(req.getResourcesApplyStatus())){
			query.setResourcesStatus(String.valueOf(req.getResourcesApplyStatus()));
		}
		query.setProcName(req.getProcName());
		query.setProjectId(req.getProjectId());
		query.setApplyPerName(req.getApplyName());
		query.setProcCode(req.getProcCode());
		query.setBeginApplyTime(DateUtils.parseDate(req.getApplyTimeStart()));
		query.setEndApplyTime(DateUtils.parseDate(req.getApplyTimeEnd()));
		query.setTargetAssign(user.getId());
		
		Page<ResourcesApply> page = resourcesApplyService.findRelationPage(
				new Page<ResourcesApply>(req.getPageNo(), req.getPageSize()), query); //查询个人所有单据信息
		List<ResourcesApply> resultList = page.getList();
		return new BaseResp<PageResponse<List<ResourcesApply>>>(IntfzRs.SUCCESS, "ok", 
				new PageResponse<List<ResourcesApply>>(resultList, page.getPageNo(), page.getCount()));
	}
	
	
	/**
	 * 获取审批流程详情接口
	 * url:flowdetail
	 * request procInsId:流程实例ID(必填), procDefId:流程定义ID(必填), taskId:任务ID(必填)
	 * response data:{"list":[]}
	 * @throws Exception 
	 */
	@RequestMapping(value = "resourcesApplyFlowDetail")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "Web端资源申请-查询审批流程详情")
	public BaseResp<ResourcesApplyFlowDetailResponse> resourcesApplyFlowDetail(
			@RequestBody ResourcesApplyFlowHandleReq resourcesApplyFlowHandleReq,
			@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		User user = UserUtils.getUser(sessionid); //获取当前申请人信息
		// 获取流程实例对象
		if (StringUtils.isBlank(resourcesApplyFlowHandleReq.getResApplyFlowId())){
			logger.info("=======IntfzWebResourcesApplyController resourcesApplyFlowDetail end=============参数不能为空");
			return new BaseResp<ResourcesApplyFlowDetailResponse>(IntfzRs.ERROR, "参数不能为空", new ResourcesApplyFlowDetailResponse());
		}
		ResourcesApply resourcesApply = resourcesApplyService.get(resourcesApplyFlowHandleReq.getResApplyFlowId()); 
		ResourcesApplyFlowResponse resourcesApplyFlowResponse = null;
		if(resourcesApply != null){
			resourcesApplyFlowResponse = intfzResourcesApplyService.resourcesApplyFlowDetail(resourcesApplyFlowResponse, resourcesApply, user);
		}else{
			return new BaseResp<ResourcesApplyFlowDetailResponse>(IntfzRs.ERROR, "查询不到当前申请详细信息", new ResourcesApplyFlowDetailResponse());
		}
		//查询日志
		List<Act> histoicFlowList = new ArrayList<>();
		if(StringUtils.isNotBlank(resourcesApplyFlowResponse.getProcInsId())){ //实例ID不为空则进行查询
			histoicFlowList = actTaskService.histoicFlowList(resourcesApplyFlowResponse.getProcInsId(), "", "");
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
		if(Constant.expense_delete.equals(resourcesApply.getResourcesStatus())){
			flowloglist.add(new FlowLogResponse("已删除",resourcesApply.getApplyPerName(), "", "", "",""));
		}

		return new BaseResp<ResourcesApplyFlowDetailResponse>(IntfzRs.SUCCESS, "ok", 
				new ResourcesApplyFlowDetailResponse(resourcesApplyFlowResponse, flowloglist));
	}
	
	
	/**
	 * 流程收回接口
	 * @return
	 */
	@RequestMapping(value = "resourcesApplyFlowRepealTask")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "Web端资源申请-流程撤销")
	public BaseResp<String> resourcesApplyFlowRepealTask(@RequestBody ResourcesApplyFlowRepealRequest resourcesApplyFlowRepealRequest,
														 @ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
														 HttpServletRequest request, HttpServletResponse response){
		if(StringUtils.isBlank(resourcesApplyFlowRepealRequest.getTaskId())){
			return new BaseResp<String>(IntfzRs.ERROR, "任务ID不能为空!", "");
		}
		if(StringUtils.isBlank(resourcesApplyFlowRepealRequest.getProcInsId())){
			return new BaseResp<String>(IntfzRs.ERROR, "流程实例ID不能为空!", "");
		}
		//判断当前单据已经审批结束
		ResourcesApply e = resourcesApplyService.getByProcInsId(resourcesApplyFlowRepealRequest.getProcInsId());
		if(Constant.expense_approve_end.equals(e.getResourcesStatus())){
			return new BaseResp<String>(IntfzRs.ERROR, "当前单据已经审批结束，无需进行收回!", "");
		}
		User user = UserUtils.getUser(sessionid); //获取当前申请人信息
		//判断当前任务是否已经在本人待办任务中
		Act act = new Act();
		act.setProcInsId(resourcesApplyFlowRepealRequest.getProcInsId());
		act = actTaskService.queryThisRunTaskId(act);
		if(act != null && StringUtils.isNotBlank(act.getAssignee()) && user.getLoginName().equals(act.getAssignee())){
			return new BaseResp<String>(IntfzRs.ERROR, "当前单据已在个人待处理任务中，无需进行收回!", "");
		}
		//发起撤销
		ResourcesApply resourcesApply = new ResourcesApply();
		resourcesApply.getAct().setProcInsId(resourcesApplyFlowRepealRequest.getProcInsId());
		resourcesApply.getAct().setTaskId(resourcesApplyFlowRepealRequest.getTaskId());
		intfzResourcesApplyService.repealTask(resourcesApply,user);
		return new BaseResp<String>(IntfzRs.SUCCESS, "流程收回成功!", "");
	}
	
	/**
	 * 查询资源办理指派人员信息
	 * @param sessionid
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "queryResourcesApplyEmployeeList")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "WEB端资源申请-查询指派人员信息")
	public BaseResp<List<User>> queryResourcesApplyEmployeeList(
			@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response){
		List<User> userList = new ArrayList<User>();
		String bringRemitOrg = SysParamsUtils.getParamValue(Global.RESOURCES_APPLY_ASSIGN, Global.SYS_PARAMS, "");
		for(int i = 0;i< bringRemitOrg.split(",").length;i++){
			User user = UserUtils.getByLoginName(bringRemitOrg.split(",")[i]);
			userList.add(user);
		}
		return new BaseResp<List<User>>(IntfzRs.SUCCESS, "ok", userList);
	}
	
}
