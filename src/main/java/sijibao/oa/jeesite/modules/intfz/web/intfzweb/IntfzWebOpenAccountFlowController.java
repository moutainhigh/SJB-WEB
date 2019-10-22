package sijibao.oa.jeesite.modules.intfz.web.intfzweb;


import java.io.IOException;
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
import com.sijibao.oa.modules.act.service.ActTaskService;
import com.sijibao.oa.modules.flow.entity.OpenAccountFlow;
import com.sijibao.oa.modules.flow.service.OpenAccountFlowService;
import com.sijibao.oa.modules.intfz.bean.BaseResp;
import com.sijibao.oa.modules.intfz.enums.IntfzRs;
import com.sijibao.oa.modules.intfz.request.common.ActRequest;
import com.sijibao.oa.modules.intfz.request.common.HandleReq;
import com.sijibao.oa.modules.intfz.request.openaccountflow.OpenAccountCompleteTaskReq;
import com.sijibao.oa.modules.intfz.request.openaccountflow.OpenAccountFlowHandleReq;
import com.sijibao.oa.modules.intfz.request.openaccountflow.OpenAccountFlowRequest;
import com.sijibao.oa.modules.intfz.response.common.PageResponse;
import com.sijibao.oa.modules.intfz.response.openaccountflow.OpenAccountFlowResponse;
import com.sijibao.oa.modules.intfz.service.intfzweb.IntfzWebOpenAccountFlowService;
import com.sijibao.oa.modules.intfz.utils.CharChangeUtils;
import com.sijibao.oa.modules.intfz.utils.Constant;
import com.sijibao.oa.modules.sys.entity.User;
import com.sijibao.oa.modules.sys.utils.UserUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * WEB端开户申请相关接口
 * @author wanxb
 */
@Controller
@RequestMapping(value = "${frontPath}/webOpenAccountFlow")
@Api(value="WEB端开户申请流程服务",tags="WEB端开户申请流程服务")
public class IntfzWebOpenAccountFlowController extends BaseController {
	
	@Autowired
	private ActTaskService actTaskService;
	@Autowired
	private IntfzWebOpenAccountFlowService intfzWebOpenAccountFlowService;
	@Autowired
	private OpenAccountFlowService openAccountFlowService;
	
	/**
	 * WEB端开户申请发起
	 * @param req
	 * @param sessionid
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	 
	 
	@RequestMapping(value = "openAccountFlow")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "WEB端开户申请-流程申请")
	public BaseResp<String> openAccountFlow(
			@RequestBody OpenAccountFlowRequest req,
			@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		User user = UserUtils.getUser(sessionid); //获取当前申请人信息
		CharChangeUtils.CharChange(req);//替换英文字符
		if(user == null){
			return new BaseResp<String>(IntfzRs.ERROR, "未找到用户信息", "");
		}
		try {
			intfzWebOpenAccountFlowService.openAccountFlowService(req, user); //开户申请
		} catch (ServiceException e) {
			return new BaseResp<String>(IntfzRs.ERROR, e.getMessage(), "");
		}
		return new BaseResp<String>(IntfzRs.SUCCESS, "申请发起成功", "");
	}
	
	/**
	 * WEB端开户申请审批 同意/驳回
	 * @param actFlowRequest
	 * @param response
	 * @throws Exception 
	 * @throws IOException
	 */
	@RequestMapping(value = "openAccountFlowCompleteTask")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "WEB端开户申请-同意/驳回")
	public BaseResp<String> openAccountFlowCompleteTask(
			@RequestBody OpenAccountCompleteTaskReq req,
			@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		//接收复杂json格式的参数
		User user = UserUtils.getUser(sessionid);
		CharChangeUtils.CharChange(req);//替换英文字符
		if (StringUtils.isBlank(req.getProcInsId())){
			logger.error("=======IntfzWebOpenAccountFlowController openAccountFlowCompleteTask end=============procInsId is not null");
			return new BaseResp<String>(IntfzRs.ERROR, "procInsId is not null", "");
		}
		if(StringUtils.isBlank(req.getBusinessId())){
			logger.error("=======IntfzWebOpenAccountFlowController openAccountFlowCompleteTask end=============businessId is not null");
			return new BaseResp<String>(IntfzRs.ERROR, "businessId is not null", "");
		}
		OpenAccountFlow open = openAccountFlowService.get(req.getBusinessId());
		open.getAct().setFlag(req.getFlag()); //意见状态yes/no
		open.getAct().setComment("同意"); //审批意见
		open.getAct().setProcInsId(req.getProcInsId()); //流程实例ID
		
		Act act = new Act();
		act.setAssignee(user.getLoginName()); //当前登录用户
		act.setProcInsId(req.getProcInsId()); //流程实例ID
		act = actTaskService.queryThisRunTaskId(act);
		if(act == null || StringUtils.isBlank(act.getTaskId())){
			logger.error("=======IntfzWebOpenAccountFlowController openAccountFlowCompleteTask end=============找不到当前流程任务信息，禁止提交");
			return new BaseResp<String>(IntfzRs.ERROR, "找不到当前流程任务信息，禁止提交", "");
		}else{
			open.getAct().setTaskId(act.getTaskId());
			open.getAct().setTaskName(act.getTaskName());
		}
		try {
			openAccountFlowService.openAccountFlowCompleteTask(open,user); //完成当前任务
		} catch (ServiceException e) {
			return new BaseResp<String>(IntfzRs.ERROR, e.getMessage(), "");
		}
		return new BaseResp<String>(IntfzRs.SUCCESS, "审批成功", "");
	}
	
	
	/**
	 * Web端查询我的开户申请列表
	 * @param OpenAccountFlow
	 * @param request  pageNo:当前页, pageSize:每页记录数
	 * @param response data:{"list":[], pageNo:"当前页", total:"当前页数"}
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "queryMyOpenAccountFlowList")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "Web端开户申请-查询列表")
	public BaseResp<PageResponse<List<OpenAccountFlowResponse>>> queryMyOpenAccountFlowFlowList(
			@RequestBody OpenAccountFlowHandleReq req,
			@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		if(req.getPageNo()==0 || req.getPageSize()==0){
			return new BaseResp<PageResponse<List<OpenAccountFlowResponse>>>(IntfzRs.ERROR, "分页参数不能为空！", 
					new PageResponse<List<OpenAccountFlowResponse>>(null, 0, 0));
		}
		User user = UserUtils.getUser(sessionid); //获取当前申请人信息
		
		Page<OpenAccountFlowResponse> page = intfzWebOpenAccountFlowService.findPage(new Page<OpenAccountFlow>(req.getPageNo(), req.getPageSize()), req, user);; //查询个人所有单据信息
		List<OpenAccountFlowResponse> resultList = page.getList();
		return new BaseResp<PageResponse<List<OpenAccountFlowResponse>>>(IntfzRs.SUCCESS, "ok", 
				new PageResponse<List<OpenAccountFlowResponse>>(resultList, page.getPageNo(), page.getCount()));
	}
//	/**
//	 * 获取审批流程详情接口
//	 * url:flowdetail
//	 * request procInsId:流程实例ID(必填), procDefId:流程定义ID(必填), taskId:任务ID(必填)
//	 * response data:{"list":[]}
//	 * @throws Exception 
//	 */
//	@RequestMapping(value = "openAccountFlowDetail")
//	@ResponseBody
//	@ApiOperation(httpMethod = "POST", value = "Web端开户申请-查询审批流程详情")
//	public BaseResp<FlowDetailResponse> openAccountFlowDetail(
//			@RequestBody HandleReq act,
//			@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
//			HttpServletRequest request, HttpServletResponse response) throws Exception{
//		WebUtils.initPre(request, response);
//		logger.info("=====IntfzWebOpenAccountFlowController openAccountFlowDetail[req]======{}", act.toString());
//		User user = UserUtils.getUser(sessionid); //获取当前申请人信息
//		// 获取流程实例对象
//		if (act.getId() == null){
//			logger.info("=======IntfzWebOpenAccountFlowController openAccountFlowDetail end=============参数不能为空");
//			return new BaseResp<FlowDetailResponse>(IntfzRs.ERROR, "参数不能为空", new FlowDetailResponse());
//		}
//		OpenAccountFlow open = openAccountFlowService.get(act.getId()); 
//		OpenAccountFlowDetailResponse openAccountFlowDetailResponse = null;
//		if(open != null){
//			ActRequest actreq = new ActRequest();
//			actreq.setBusinessId(act.getId());
//			openAccountFlowDetailResponse = intfzWebOpenAccountFlowService.openAccountFlowDetail(openAccountFlowDetailResponse, open, user, actreq);
//		}else{
//			return new BaseResp<FlowDetailResponse>(IntfzRs.ERROR, "查询不到当前申请详细信息", new FlowDetailResponse());
//		}
//		//查询日志
//		List<Act> histoicFlowList = new ArrayList<>();
//		if(StringUtils.isNotBlank(act.getId())){ //实例ID不为空则进行查询
//			histoicFlowList = actTaskService.histoicFlowList(act.getId(), "", "");
//		}
//		
//		List<FlowLogResponse> flowloglist = Lists.newArrayList();
//		for (int i = 0; i < histoicFlowList.size(); i++) {
//			Act tmpAct = histoicFlowList.get(i);
//			String startTime = DateUtils.formatDate(tmpAct.getHistIns().getStartTime(), DateUtils.YYYY_MM_DD_HH_MM_SS);
//			String endTime = "";
//			if(tmpAct.getHistIns().getEndTime() != null){
//				endTime = DateUtils.formatDate(tmpAct.getHistIns().getEndTime(), DateUtils.YYYY_MM_DD_HH_MM_SS);
//			}
//			flowloglist.add(new FlowLogResponse(tmpAct.getHistIns().getActivityName(), tmpAct.getAssigneeName(), startTime, endTime, tmpAct.getComment(), tmpAct.getDurationTime()));
//		}
//		if(ExpenseConstatnt.expense_delete.equals(open.getStatus())){
//			flowloglist.add(new FlowLogResponse("已删除",open.getApplyPerName(), "", "", "",""));
//		}
//		
//		logger.info("=======IntfzWebOpenAccountFlowController openAccountFlowDetail end=============");
//		return new BaseResp<FlowDetailResponse>(IntfzRs.SUCCESS, "ok", 
//				new FlowDetailResponse(openAccountFlowDetailResponse, flowloglist));
//	}
	
	/**
	 * WEB端删除开户申请
	 * @param OpenAccountFlow 业务ID
	 * @param taskId 当前任务ID
	 * @param response
	 * @throws Exception 
	 * @throws IOException 
	 */
	@RequestMapping(value = "deleteOpenAccountFlow")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "WEB端开户申请-删除单据")
	public BaseResp<String> deleteOpenAccountFlow(
			@RequestBody HandleReq req,
			@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		if (StringUtils.isBlank(req.getId())){
			logger.info("=======IntfzWebOpenAccountFlowController deleteOpenAccountFlow end=============businessId is not null");
			return new BaseResp<String>(IntfzRs.ERROR, "businessId is not null", "");
		}
		
		OpenAccountFlow openAccountFlow = openAccountFlowService.get(req.getId()); //查询需求实体
		if(openAccountFlow == null){
			return new BaseResp<String>(IntfzRs.ERROR, "未找到需要删除的单据信息", "");
		}
		if(Constant.expense_save.equals(openAccountFlow.getStatus())){ //保存状态不需要删除task
			openAccountFlow.getAct().setTaskId(null); //任务ID
		}
	
		openAccountFlowService.delete(openAccountFlow);  //删除单据
		return new BaseResp<String>(IntfzRs.SUCCESS, "删除单据成功", "");
	}
	/**
	 * 开户申请流程收回接口
	 * @return
	 */
	@RequestMapping(value = "openAccountFlowRepealTask")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "Web端开户申请-流程撤销")
	public BaseResp<String> openAccountFlowRepealTask(@RequestBody ActRequest actRequest,
													  @ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
													  HttpServletRequest request, HttpServletResponse response){
		if(StringUtils.isBlank(actRequest.getTaskId())){
			return new BaseResp<String>(IntfzRs.ERROR, "任务ID不能为空!", "");
		}
		if(StringUtils.isBlank(actRequest.getProcInsId())){
			return new BaseResp<String>(IntfzRs.ERROR, "流程实例ID不能为空!", "");
		}
		//判断当前单据已经审批结束
		OpenAccountFlow e = openAccountFlowService.getByProcInsId(actRequest.getProcInsId());
		if(Constant.expense_approve_end.equals(e.getStatus())){
			return new BaseResp<String>(IntfzRs.ERROR, "当前单据已经审批结束，无需进行收回!", "");
		}
		User user = UserUtils.getUser(sessionid); //获取当前申请人信息
		//判断当前任务是否已经在本人待办任务中
		Act act = new Act();
		act.setProcInsId(actRequest.getProcInsId());
		act = actTaskService.queryThisRunTaskId(act);
		if(act != null && StringUtils.isNotBlank(act.getAssignee()) && user.getLoginName().equals(act.getAssignee())){
			return new BaseResp<String>(IntfzRs.ERROR, "当前单据已在个人待处理任务中，无需进行收回!", "");
		}
		//发起撤销
		OpenAccountFlow openAccountFlow = new OpenAccountFlow();
		openAccountFlow.getAct().setProcInsId(actRequest.getProcInsId());
		openAccountFlow.getAct().setTaskId(actRequest.getTaskId());
		openAccountFlowService.repealTask(openAccountFlow,user);
		return new BaseResp<String>(IntfzRs.SUCCESS, "开户流程收回成功!", "");
	}
	
}
