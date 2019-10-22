package sijibao.oa.jeesite.modules.intfz.web.intfzweb;

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

import com.sijibao.oa.common.persistence.Page;
import com.sijibao.oa.common.service.ServiceException;
import com.sijibao.oa.common.utils.StringUtils;
import com.sijibao.oa.common.utils.WebUtils;
import com.sijibao.oa.common.web.BaseController;
import com.sijibao.oa.modules.act.entity.Act;
import com.sijibao.oa.modules.act.entity.TaskInfoEntity;
import com.sijibao.oa.modules.act.service.ActTaskService;
import com.sijibao.oa.modules.flow.entity.ResourcesHandleFlow;
import com.sijibao.oa.modules.flow.service.ResourcesHandleFlowService;
import com.sijibao.oa.modules.intfz.bean.BaseResp;
import com.sijibao.oa.modules.intfz.enums.IntfzRs;
import com.sijibao.oa.modules.intfz.request.resources.*;
import com.sijibao.oa.modules.intfz.response.common.FlowLogResponse;
import com.sijibao.oa.modules.intfz.response.common.PageResponse;
import com.sijibao.oa.modules.intfz.response.resources.ResourcesHandleFlowDetailResponse;
import com.sijibao.oa.modules.intfz.response.resources.ResourcesHandleFlowListResponse;
import com.sijibao.oa.modules.intfz.response.resources.ResourcesHandleFlowResponse;
import com.sijibao.oa.modules.intfz.service.resources.IntfzResourcesHandleFlowService;
import com.sijibao.oa.modules.intfz.utils.CharChangeUtils;
import com.sijibao.oa.modules.intfz.utils.Constant;
import com.sijibao.oa.modules.sys.entity.PostInfo;
import com.sijibao.oa.modules.sys.entity.User;
import com.sijibao.oa.modules.sys.service.PostService;
import com.sijibao.oa.modules.sys.utils.UserUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * WEB端资源申请流程
 * @author xuby
 *
 */
@Controller
@RequestMapping(value = "${frontPath}/webResourcesHandleFlow")
@Api(value="WEB资源申请办理流程服务",tags="WEB资源申请办理流程服务")
public class IntfzWebResourcesHandleFlowController extends BaseController{
	@Autowired
	private IntfzResourcesHandleFlowService intfzResourcesHandleFlowService;
	@Autowired
	private ResourcesHandleFlowService resourcesHandleFlowService;
	@Autowired
	private ActTaskService actTaskService;
	@Autowired
	private PostService postService;
	
	/**
	 * WEB端资源申请流程
	 * @param req
	 * @param sessionid
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "resourcesHandleFlowList")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "Web端资源申请办理-查询列表")
	BaseResp<PageResponse<List<ResourcesHandleFlowListResponse>>> resourcesHandleFlowList(
			@RequestBody ResourcesHandleFlowListReq req,
			@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		User user = UserUtils.getUser(sessionid);
		Page<ResourcesHandleFlowListResponse> page = intfzResourcesHandleFlowService.findPage(new Page<ResourcesHandleFlow>(req.getPageNo(), req.getPageSize()), req, user,Constant.CLIENT_TYPE_WEB);
		return new BaseResp<PageResponse<List<ResourcesHandleFlowListResponse>>>(IntfzRs.SUCCESS, "ok", 
				new PageResponse<List<ResourcesHandleFlowListResponse>>(page.getList(), page.getPageNo(), page.getCount()));
	}
	
	
	/**
	 * WEB端资源申请流程
	 * @param req
	 * @param sessionid
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "resourcesRelationHandleFlowList")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "Web端资源申请办理-关联列表")
	BaseResp<PageResponse<List<ResourcesHandleFlowListResponse>>> resourcesRelationHandleFlowList(
			@RequestBody ResourcesHandleFlowListReq req,
			@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		User user = UserUtils.getUser(sessionid);
		//判断当前环节人员是否有下级岗位
		List<PostInfo> postInfoList = postService.getPostInfoByParentId(user.getPostIds());
		if(postInfoList != null && postInfoList.size() > 0){
			return new BaseResp<PageResponse<List<ResourcesHandleFlowListResponse>>>(IntfzRs.SUCCESS, "ok", 
					new PageResponse<List<ResourcesHandleFlowListResponse>>(null, req.getPageNo(), 0));
		}else{
			Page<ResourcesHandleFlowListResponse> page = intfzResourcesHandleFlowService.findRelationPage(new Page<ResourcesHandleFlow>(req.getPageNo(), req.getPageSize()), req, user);
			return new BaseResp<PageResponse<List<ResourcesHandleFlowListResponse>>>(IntfzRs.SUCCESS, "ok", 
					new PageResponse<List<ResourcesHandleFlowListResponse>>(page.getList(), page.getPageNo(), page.getCount()));
		}
	}
	
	/**
	 * Web资源申请办理
	 * @param TravelFlowRequest
	 * @return
	 * @throws UnsupportedEncodingException 
	 * @throws IOException 
	 */
	@RequestMapping(value = "resourcesHandleApply")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "Web端资源办理-发起申请")
	public BaseResp<String> resourcesHandleApply(
			@RequestBody ResourcesHandleFlowRequest req,
			@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		User user = UserUtils.getUser(sessionid); //获取当前申请人信息
		CharChangeUtils.CharChange(req);//替换英文字符
		req.setProducSide("web");
		if(user == null){
			return new BaseResp<String>(IntfzRs.ERROR, "未找到用户信息", "");
		}
		//判断是主动发起还是被动发起，1主动发起：关联主题必填；2被动发起：项目名称必填。
		if(req.getHandleType() != null &&
				Constant.HANDLE_TYPE_ONE.equals(req.getHandleType()) &&
				StringUtils.isBlank(req.getProjectId())){
			return new BaseResp<String>(IntfzRs.ERROR, "主动发起，项目名称必填", "");
		}
		if(req.getHandleType() != null &&
				Constant.HANDLE_TYPE_TWO.equals(req.getHandleType()) &&
				 StringUtils.isBlank(req.getRelationTheme())){
			return new BaseResp<String>(IntfzRs.ERROR, "被动发起，关联主题必填", "");
		}
		try {
			intfzResourcesHandleFlowService.resourcesHandleApplyService(req, user); //资源办理
		} catch (ServiceException e) {
			return new BaseResp<String>(IntfzRs.ERROR, e.getMessage(), "");
		}
		return new BaseResp<String>(IntfzRs.SUCCESS, "申请发起成功", "");
	}
	
	/**
	 * WEB端资源办理保存
	 * @param req
	 * @param sessionid
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "saveResourcesHandleInfo")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "WEB端资源办理-保存草稿")
	public BaseResp<String> saveResourcesHandleInfo(@RequestBody ResourcesHandleFlowRequest req,
													@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
													HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		User user = UserUtils.getUser(sessionid); //获取当前申请人信息
		CharChangeUtils.CharChange(req);//替换英文字符
		intfzResourcesHandleFlowService.saveResourceHandleService(req, user); //资源申请保存
		return new  BaseResp<String>(IntfzRs.SUCCESS, "单据保存成功", "");
	}
	
	/**
	 * 删除资源办理
	 * @param req
	 * @param sessionid
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "deleteResourcesHandleInfo")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "WEB端资源办理-删除")
	public BaseResp<String> deleteResourcesHandleInfo(
			@RequestBody ResourcesHandleFlowHandleReq req,
			@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		if(StringUtils.isBlank(req.getResHandleFlowId())){
			return new BaseResp<String>(IntfzRs.ERROR,"resHandleFlowId不能为空!",null);
		}
		try {
			ResourcesHandleFlow flow = resourcesHandleFlowService.get(req.getResHandleFlowId());
			if(flow == null){
				return new BaseResp<String>(IntfzRs.ERROR,"删除失败，未找到流程信息!",null);
			}
			if(Constant.expense_save.equals(flow.getResourcesHandleStatus())){ //保存状态不需要删除task
				flow.getAct().setTaskId(null); //任务ID
			}
			intfzResourcesHandleFlowService.deleteResourcesHandleInfo(flow);
		} catch (Exception e) {
			e.printStackTrace();
			return new  BaseResp<String>(IntfzRs.SUCCESS, "删除失败！", "");
		}
		
		return new  BaseResp<String>(IntfzRs.SUCCESS, "删除成功！", "");
		
	}
	
	/**
	 * 详情展示
	 * @param req
	 * @param sessionid
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "resourcesHandleInfoDetail")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "WEB端资源办理-资源申请流程详细信息")
	public BaseResp<ResourcesHandleFlowDetailResponse> resourcesHandleInfoDetail(
			@RequestBody ResourcesHandleFlowHandleReq resourcesHandleFlowHandleReq,
			@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		if (resourcesHandleFlowHandleReq.getResHandleFlowId() == null){
			logger.info("=======IntfzWebResourcesHandleFlowController resourcesHandleInfoDetail end=============参数不能为空");
			return new BaseResp<ResourcesHandleFlowDetailResponse>(IntfzRs.ERROR, "参数不能为空", new ResourcesHandleFlowDetailResponse());
		}
		User user = UserUtils.getUser(sessionid); //获取当前申请人信息
		if(user == null){
			return new BaseResp<ResourcesHandleFlowDetailResponse>(IntfzRs.ERROR, "未找到用户信息",null);
		}
		ResourcesHandleFlowResponse resource = intfzResourcesHandleFlowService.getResourcesHandleFlowById(resourcesHandleFlowHandleReq.getResHandleFlowId(),user);
		
		//查询日志
		List<Act> histoicFlowList = new ArrayList<>();
		if(StringUtils.isNotBlank(resource.getProcInsId())){ //实例ID不为空则进行查询
			histoicFlowList = actTaskService.histoicFlowListForChildFlow(resource.getProcInsId(),"", "","","");
		}
		
		List<FlowLogResponse> flowloglist = intfzResourcesHandleFlowService.setFlowLog(histoicFlowList,resource);

		return new BaseResp<ResourcesHandleFlowDetailResponse>(IntfzRs.SUCCESS, "ok", new ResourcesHandleFlowDetailResponse(resource, flowloglist));
	}
	
	/**
	 * 查询资源办理指派人员信息
	 * @param sessionid
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "queryHandleEmployeeList")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "WEB端资源办理-查询指派人员信息")
	public BaseResp<List<User>> queryHandleEmployeeList(
			@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response){
		List<User> userList = new ArrayList<User>();
		User user = UserUtils.getUser(sessionid); //获取当前申请人信息
		userList = intfzResourcesHandleFlowService.queryHandlerUserInfo(user);
		return new BaseResp<List<User>>(IntfzRs.SUCCESS, "ok", userList);
	}
	
	/**
	 * Web端单据审批 同意/驳回
	 * @param actFlowRequest
	 * @param response
	 * @throws Exception 
	 * @throws IOException
	 */
	@RequestMapping(value = "resourcesHandleFlowCompleteTask")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "Web端资源办理-同意")
	public BaseResp<String> resourcesHandleFlowCompleteTask(@RequestBody ResourcesHandleFlowCompleteTaskReq req,
															@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
															HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		//接收复杂json格式的参数
		User user = UserUtils.getUser(sessionid); //获取当前申请人信息
		CharChangeUtils.CharChange(req);//替换英文字符
		if (StringUtils.isBlank(req.getComment()) && "no".equals(req.getFlag())) {
			logger.error("=======IntfzWebRecpFlowController recpFlowCompleteTask end=============请填写审核意见");
			return new BaseResp<String>(IntfzRs.ERROR, "请填写拒绝原因", "");
		}
		if (StringUtils.isBlank(req.getProcInsId())){
			logger.error("=======IntfzWebResourcesHandleFlowController resourcesHandleFlowCompleteTask end=============procInsId is not null");
			return new BaseResp<String>(IntfzRs.ERROR, "procInsId is not null", "");
		}
		if(StringUtils.isBlank(req.getResHandleFlowId())){
			logger.error("=======IntfzWebResourcesHandleFlowController resourcesHandleFlowCompleteTask end=============expenseFormId is not null");
			return new BaseResp<String>(IntfzRs.ERROR, "expenseFormId is not null", "");
		}
		ResourcesHandleFlow resourcesHandleFlow = resourcesHandleFlowService.get(req.getResHandleFlowId());
		resourcesHandleFlow.getAct().setFlag(req.getFlag()); //意见状态yes/no
		resourcesHandleFlow.getAct().setComment(StringUtils.isBlank(req.getComment())?"同意":req.getComment()); //审批意见
		resourcesHandleFlow.getAct().setProcInsId(req.getProcInsId()); //流程实例ID
//		resourcesHandleFlow.setId(req.getResHandleFlowId()); //资源办理ID
		
		Act act = new Act();
		act.setAssignee(user.getLoginName()); //当前登录用户
		act.setProcInsId(req.getProcInsId()); //流程实例ID
		act = actTaskService.queryThisRunTaskId(act);
		if(act == null || StringUtils.isBlank(act.getTaskId())){
			logger.error("=======IntfzWebResourcesHandleFlowController resourcesHandleFlowCompleteTask end=============找不到当前流程任务信息，禁止提交");
			return new BaseResp<String>(IntfzRs.ERROR, "找不到当前流程任务信息，禁止提交", "");
		}else{
			resourcesHandleFlow.getAct().setTaskId(act.getTaskId());
			resourcesHandleFlow.getAct().setTaskDefKey(act.getTaskDefKey());
		}
		try {
			intfzResourcesHandleFlowService.resourcesHandleCompleteTask(resourcesHandleFlow,user,req.getAssignList()); //完成当前任务
		} catch (ServiceException e) {
			logger.info("=======IntfzWebResourcesHandleFlowController resourcesHandleFlowCompleteTask end============="+e.getMessage());
			return new BaseResp<String>(IntfzRs.ERROR, e.getMessage(), "");
		}
		return new BaseResp<String>(IntfzRs.SUCCESS, "审批成功", "");
	}
	
	/**
	 * 流程收回接口
	 * @return
	 */
	@RequestMapping(value = "resourcesHandleFlowRepealTask")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "Web端资源办理-流程撤销")
	public BaseResp<String> resourcesHandleFlowRepealTask(@RequestBody ResourcesHandleFlowRepealRequest resourcesHandleFlowRepealRequest,
														  @ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
														  HttpServletRequest request, HttpServletResponse response){
		if(StringUtils.isBlank(resourcesHandleFlowRepealRequest.getTaskId())){
			return new BaseResp<String>(IntfzRs.ERROR, "任务ID不能为空!", "");
		}
		if(StringUtils.isBlank(resourcesHandleFlowRepealRequest.getProcInsId())){
			return new BaseResp<String>(IntfzRs.ERROR, "流程实例ID不能为空!", "");
		}
		//判断当前单据已经审批结束
		ResourcesHandleFlow e = resourcesHandleFlowService.getByProcInsId(resourcesHandleFlowRepealRequest.getProcInsId());
		if(Constant.expense_approve_end.equals(e.getResourcesHandleStatus())){
			return new BaseResp<String>(IntfzRs.ERROR, "当前单据已经审批结束，无需进行收回!", "");
		}
		
		//判断当前环节的下个环节是否已经审批通过
		TaskInfoEntity taskInfoEntity = new TaskInfoEntity();
		taskInfoEntity.setProcCode(e.getProcCode());
		if(StringUtils.isBlank(resourcesHandleFlowRepealRequest.getTaskId()) || "0".equals(resourcesHandleFlowRepealRequest.getTaskId())){
			taskInfoEntity.setId("start");
		}else{
			Act hisTask = new Act();
			hisTask.setProcInsId(resourcesHandleFlowRepealRequest.getProcInsId());
			hisTask.setTaskId(resourcesHandleFlowRepealRequest.getTaskId());
			hisTask = actTaskService.queryHisTask(hisTask);
			if(hisTask != null && "modify".equals(hisTask.getTaskDefKey())){
				taskInfoEntity.setId("start");
			}else{
				taskInfoEntity.setId(resourcesHandleFlowRepealRequest.getTaskId());
			}
		}
		List<TaskInfoEntity> taskInfoEntityList = actTaskService.queryChildTaskInfoForParentTaskId(taskInfoEntity);
		for(TaskInfoEntity t:taskInfoEntityList){
			if(StringUtils.isNotBlank(t.getStatus())){
				return new BaseResp<String>(IntfzRs.ERROR, "当前单据的下个审批环节已经审批，无法进行收回!", "");
			}
		}

		User user = UserUtils.getUser(sessionid); //获取当前申请人信息
		//判断当前任务是否已经在本人待办任务中
		Act act = new Act();
		act.setProcInsId(resourcesHandleFlowRepealRequest.getProcInsId());
		act = actTaskService.queryThisRunTaskId(act);
		if(act != null && StringUtils.isNotBlank(act.getAssignee()) && user.getLoginName().equals(act.getAssignee())){
			return new BaseResp<String>(IntfzRs.ERROR, "当前单据已在个人待处理任务中，无需进行收回!", "");
		}
		//发起撤销
		ResourcesHandleFlow resourcesHandleFlow = new ResourcesHandleFlow();
		resourcesHandleFlow.getAct().setProcInsId(resourcesHandleFlowRepealRequest.getProcInsId());
		resourcesHandleFlow.getAct().setTaskId(resourcesHandleFlowRepealRequest.getTaskId());
		resourcesHandleFlow.setProcCode(e.getProcCode());
		if("start".equals(taskInfoEntity.getId())){
			resourcesHandleFlow.getAct().setTaskDefKey("start");
		}
		try {
			intfzResourcesHandleFlowService.repealTask(resourcesHandleFlow,user); //撤销
		} catch (ServiceException ex) {
			return new BaseResp<String>(IntfzRs.ERROR, ex.getMessage(), "");
		}
		return new BaseResp<String>(IntfzRs.SUCCESS, "流程收回成功!", "");
	}
}
