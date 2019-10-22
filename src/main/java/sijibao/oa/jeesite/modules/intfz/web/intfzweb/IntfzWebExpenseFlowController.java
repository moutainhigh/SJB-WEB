package sijibao.oa.jeesite.modules.intfz.web.intfzweb;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
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
import com.sijibao.base.common.api.response.PagerResponse;
import com.sijibao.oa.activiti.api.exception.ServiceException;
import com.sijibao.oa.activiti.api.request.CommonFlowHandleParam;
import com.sijibao.oa.activiti.api.request.expense.*;
import com.sijibao.oa.activiti.api.response.TaskResult;
import com.sijibao.oa.activiti.api.response.expense.BringRemitResult;
import com.sijibao.oa.activiti.api.response.expense.ExpenseFlowPaginationResult;
import com.sijibao.oa.activiti.api.response.expense.ExpenseFlowResult;
import com.sijibao.oa.activiti.api.response.expense.ExpenseReportResult;
import com.sijibao.oa.activiti.api.service.ExpenseFlowNewService;
import com.sijibao.oa.common.utils.StringUtils;
import com.sijibao.oa.common.utils.UploadUtils;
import com.sijibao.oa.common.utils.WebUtils;
import com.sijibao.oa.common.web.BaseController;
import com.sijibao.oa.modules.flow.entity.ApproveTimeInfo;
import com.sijibao.oa.modules.flow.entity.ExpenseFlow;
import com.sijibao.oa.modules.flow.service.ApproveTimeInfoService;
import com.sijibao.oa.modules.flow.service.ExpenseFlowService;
import com.sijibao.oa.modules.intfz.bean.BaseResp;
import com.sijibao.oa.modules.intfz.enums.IntfzRs;
import com.sijibao.oa.modules.intfz.request.common.CommonFlowHandleReq;
import com.sijibao.oa.modules.intfz.request.expense.*;
import com.sijibao.oa.modules.intfz.request.intfzwebreq.BringRemitHandleRequest;
import com.sijibao.oa.modules.intfz.request.intfzwebreq.BringRemitSaveRequest;
import com.sijibao.oa.modules.intfz.request.intfzwebreq.WebAttachmentReq;
import com.sijibao.oa.modules.intfz.response.common.PageResponse;
import com.sijibao.oa.modules.intfz.response.common.TaskResponse;
import com.sijibao.oa.modules.intfz.response.expense.ExpenseFlowDetailResponse;
import com.sijibao.oa.modules.intfz.response.expense.ExpenseFlowResponse;
import com.sijibao.oa.modules.intfz.response.intfzwebresp.BringRemitResponse;
import com.sijibao.oa.modules.intfz.service.common.IntfzCommonService;
import com.sijibao.oa.modules.intfz.utils.CharChangeUtils;
import com.sijibao.oa.modules.intfz.utils.Constant;
import com.sijibao.oa.modules.sys.entity.Office;
import com.sijibao.oa.modules.sys.entity.User;
import com.sijibao.oa.modules.sys.utils.UserUtils;
import com.sijibao.oa.sys.api.IntfzWebUsageService;
import com.sijibao.oa.sys.api.request.usage.AddUsageParam;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * WEB端报销流程相关
 * @author xuby
 */
@Controller
@RequestMapping(value = "${frontPath}/webExpenseFlow")
@Api(value="WEB端报销流程服务",tags="WEB端报销流程服务")
public class IntfzWebExpenseFlowController extends BaseController {
	
	@Autowired
	private ExpenseFlowNewService expenseFlowNewService;
//	@Autowired
//	private ActTaskService actTaskService;
	@Autowired
	private ExpenseFlowService expenseFlowService;
//	@Autowired
//	private TravelFlowService travelFlowService;
	@Autowired
	private IntfzWebUsageService intfzWebUsageService;
	@Autowired
	private IntfzCommonService intfzCommonService;

	@Autowired
	private ApproveTimeInfoService approveTimeInfoService;

	/**
	 * web端，报销申请
	 * @param req
	 * @param sessionid
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "expenseApply")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "WEB端报销-申请")
	public BaseResp<String> expenseApply(
			@RequestBody ExpenseFlowRequest req,
			@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		User user = UserUtils.getUser(sessionid); //获取当前申请人信息
		CharChangeUtils.CharChange(req);//替换英文字符
		if(user == null){
			return new BaseResp<String>(IntfzRs.ERROR, "未找到用户信息", "");
		}
		req.setProducSide("web");
		try {
            ExpenseFlowParam expenseFlow = change(req,ExpenseFlowParam.class);
            List<ExpenseFlowDetailParam> detailList = Lists.newLinkedList();
            for(ExpenseFlowDetailRequest i : req.getExpenseDetail()){
                List<ExpenseFlowAttachmentParam> attachmentList = Lists.newLinkedList();
                for(ExpenseFlowAttachmentRequest j : i.getSubConfList()){
                    attachmentList.add(change(j,ExpenseFlowAttachmentParam.class));
                }
                ExpenseFlowDetailParam detail = change(i,ExpenseFlowDetailParam.class);
                detail.setSubConfList(attachmentList);
                detailList.add(detail);
            }
            List<WebAttachmentParam> webAttachmentList = Lists.newLinkedList();
            for(WebAttachmentReq item : req.getExpenseAttachmentWeb()){
                webAttachmentList.add(change(item,WebAttachmentParam.class));
            }
            expenseFlow.setExpenseDetail(detailList);
            expenseFlow.setExpenseAttachmentWeb(webAttachmentList);

            expenseFlowNewService.expenseApply(expenseFlow, user.getId(),Constant.CLIENT_TYPE_WEB); //报销申请
		} catch (ServiceException e) {
			return new BaseResp<String>(IntfzRs.ERROR, e.getMessage(), "");
		}

		return new BaseResp<String>(IntfzRs.SUCCESS, "申请发起成功", "");
	}

	/**
	 * 报销单据保存
	 * @param req
	 * @param sessionid
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "saveExpenseInfo")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "WEB端报销-保存")
	public BaseResp<String> saveExpenseInfo(@RequestBody ExpenseFlowRequest req,
											@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
											HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		User user = UserUtils.getUser(sessionid); //获取当前申请人信息
		CharChangeUtils.CharChange(req);//替换英文字符
		try{
            ExpenseFlowParam expenseFlow = change(req,ExpenseFlowParam.class);
            List<ExpenseFlowDetailParam> detailList = Lists.newLinkedList();
            for(ExpenseFlowDetailRequest i : req.getExpenseDetail()){
                List<ExpenseFlowAttachmentParam> attachmentList = Lists.newLinkedList();
                for(ExpenseFlowAttachmentRequest j : i.getSubConfList()){
                    attachmentList.add(change(j,ExpenseFlowAttachmentParam.class));
                }
                ExpenseFlowDetailParam detail = change(i,ExpenseFlowDetailParam.class);
                detail.setSubConfList(attachmentList);
                detailList.add(detail);
            }
            List<WebAttachmentParam> webAttachmentList = Lists.newLinkedList();
            for(WebAttachmentReq item : req.getExpenseAttachmentWeb()){
                webAttachmentList.add(change(item,WebAttachmentParam.class));
            }
            expenseFlow.setExpenseDetail(detailList);
            expenseFlow.setExpenseAttachmentWeb(webAttachmentList);

			expenseFlowNewService.saveExpense(expenseFlow, user.getId(),Constant.CLIENT_TYPE_WEB); //报销保存
		}catch (ServiceException e){
			return new BaseResp<String>(IntfzRs.ERROR, e.getMessage(), "");
		}

		return new  BaseResp<String>(IntfzRs.SUCCESS, "单据保存成功", "");
	}

	/**
	 * 单据审批 同意/驳回
	 * @param req
	 * @param sessionid
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "completeTask")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "WEB端报销-同意/驳回")
	public BaseResp<String> completeTask(@RequestBody ExpenseFlowCompleteTaskReq req,
										 @ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
										 HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		//接收复杂json格式的参数
		User user = UserUtils.getUser(sessionid); //获取当前申请人信息
		CharChangeUtils.CharChange(req);//替换英文字符
		if (StringUtils.isBlank(req.getComment()) && "no".equals(req.getFlag())) {
			logger.error("=======IntfzWebExpenseFlowController completeTask end=============请填写审核意见");
			return new BaseResp<String>(IntfzRs.ERROR, "请填写审核意见", "");
		}
		if (StringUtils.isBlank(req.getProcInsId())){
			logger.error("=======IntfzWebExpenseFlowController completeTask end=============procInsId is not null");
			return new BaseResp<String>(IntfzRs.ERROR, "procInsId is not null", "");
		}
		if(StringUtils.isBlank(req.getExpenseFlowId())){
			logger.error("=======IntfzWebExpenseFlowController completeTask end=============expenseFlowId is not null");
			return new BaseResp<String>(IntfzRs.ERROR, "expenseFormId is not null", "");
		}

		try {
            ExpenseFlowCompleteTaskParam expenseFlowCompleteTaskParam = change(req,ExpenseFlowCompleteTaskParam.class);
            expenseFlowNewService.completeTask(expenseFlowCompleteTaskParam,user.getId()); //完成当前任务
        }catch (ServiceException e){
            return new BaseResp<>(IntfzRs.ERROR, e.getMessage(), "");
        }
		return new BaseResp<String>(IntfzRs.SUCCESS, "审批成功", "");
	}
	
	/**
	 * 删除报销申请
	 * @param req
	 * @param sessionid
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "repealApply")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "WEB端报销-删除")
	public BaseResp<String> repealApply(@RequestBody ExpenseFlowHandleReq req,
										@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
										HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
        User user = UserUtils.getUser(sessionid); //获取当前申请人信息
		if (StringUtils.isBlank(req.getExpenseFlowId())){
			logger.info("=======IntfzWebExpenseFlowController repealApply end=============expenseFlowId is not null");
			return new BaseResp<String>(IntfzRs.ERROR, "expenseFlowId is not null", "");
		}

		try {
            expenseFlowNewService.deleteExpenseFlowInfo(req.getExpenseFlowId(),user.getId());  //删除单据
        }catch (ServiceException e){
            return new BaseResp<>(IntfzRs.ERROR, e.getMessage(), "");
        }
		return new BaseResp<String>(IntfzRs.SUCCESS, "删除费用报销成功", "");
	}


	/**
	 * 查询我的单据列表
	 * @param req
	 * @param sessionid
	 * @param request
	 * @param response
	 * expenseFlowId
	 * @param request  pageNo:当前页, pageSize:每页记录数
	 * @param response data:{"list":[], pageNo:"当前页", total:"当前页数"}
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "queryMyExpenseList")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "WEB端报销-查询列表")
	public BaseResp<PageResponse<List<ExpenseFlow>>> queryMyExpenseList(
			@RequestBody FlowHandleReq req,
			@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		if(req.getPageNo()==0 || req.getPageSize()==0){
			logger.info("===IntfzWebExpenseFlowController queryMyExpenseList response===: " + "null");
			return new BaseResp<PageResponse<List<ExpenseFlow>>>(IntfzRs.ERROR, "分页参数不能为空！", 
					new PageResponse<List<ExpenseFlow>>(null, 0, 0));
		}
		User user = UserUtils.getUser(sessionid); //获取当前申请人信息
        PagerResponse<ExpenseFlowPaginationResult> page = new PagerResponse<>();
        List<ExpenseFlow> resultList = new LinkedList<>();
        try {
            ExpenseFlowParam expenseFlowParam = change(req,ExpenseFlowParam.class);
            page = expenseFlowNewService.queryMyExpenseList(expenseFlowParam,user.getLoginName());
            Office temp = new Office();
            for(ExpenseFlowPaginationResult item : page.getList()){
                ExpenseFlow expenseFlow = change(item,ExpenseFlow.class);
                temp.setId(item.getOfficeId());
                temp.setName(item.getOfficeName());
                expenseFlow.setOffice(temp);
                resultList.add(expenseFlow);
            }
        }catch (ServiceException e){
            logger.error("===IntfzWebExpenseFlowController queryMyExpenseList throws ServiceException:{}"+e.getMessage());
        }
		return new BaseResp<>(IntfzRs.SUCCESS, "ok",
				new PageResponse<List<ExpenseFlow>>(resultList, req.getPageNo(), page.getCount()));
	}

	/**
	 * 导出个人报销列表
	 */
	@RequestMapping(value = "exportMyExpenseList")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "WEB端报销-导出个人报销列表")
	public BaseResp<String> exportMyExpenseList(@RequestBody FlowHandleReq req,
												@ApiParam(value = "登录成功时返回的sessionid") @RequestHeader String sessionid,
												HttpServletRequest request, HttpServletResponse response) {
		WebUtils.initPre(request, response);
		User user = UserUtils.getUser(sessionid);

        ExpenseFlowParam expenseFlowParam = change(req,ExpenseFlowParam.class);
        List<ExpenseReportResult> list = expenseFlowNewService.queryAllListIncludeDeleteForReport(expenseFlowParam,user.getLoginName()); //查询个人所有单据信息

		DateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String	fileName = "个人报销情况" + sdf.format(new Date()) + ".xls";

		String	url = expenseFlowService.exportMyExpenseList(list, fileName); //获取附件url
		String downLoadUrl = intfzCommonService.setFileDownLoadUrl(url, fileName);

		logger.info("====IntfzWebExpenseFlowController exportMyExpenseList=====}", downLoadUrl);
		return new BaseResp<>(IntfzRs.SUCCESS, "", downLoadUrl);
	}

//	/**
//	 * 流程发起（废弃！！！）
//	 * @param req
//	 * @param sessionid
//	 * @param request
//	 * @param response
//	 * @return
//	 * @throws Exception
//	 * expenseFlowId 报销业务ID
//	 */
//	@RequestMapping(value = "startWorkFlow")
//	@ResponseBody
//	@ApiOperation(httpMethod = "POST", value = "WEB端报销-发起申请")
//	public BaseResp<String> startWorkFlow(@RequestBody MainProjectApprovalFlowHandleReq req,
//			@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
//			HttpServletRequest request,HttpServletResponse response) throws Exception{
//		WebUtils.initPre(request, response);
//		String expenseFlowId = req.getExpenseFlowId();
//		logger.info("===IntfzWebExpenseFlowController startWorkFlow[req]====== " + expenseFlowId);
//		User user = UserUtils.getUser(sessionid); //获取当前申请人信息
//		if(StringUtils.isBlank(expenseFlowId)){
//			return new  BaseResp<String>(IntfzRs.ERROR, "报销ID不能为空", "");
//		}
//		ExpenseFlow expenseFLow = expenseFlowService.get(expenseFlowId);
//
//		if(expenseFLow.getExpenseTotal() == null || expenseFLow.getExpenseTotal().compareTo(BigDecimal.ZERO) < 1){
//			return new BaseResp<String>(IntfzRs.ERROR, "当前报销金额不能小于等于0，禁止提交!", "");
//		}
//		try {
//			intfzExpenseFlowService.startWorkFlow(expenseFLow, user);
//		} catch (ServiceException e) {
//			logger.info("=======IntfzWebExpenseFlowController startWorkFlow end============="+e.getMessage());
//			return new BaseResp<String>(IntfzRs.ERROR, e.getMessage(), "");
//		}
//
//		logger.info("=======IntfzWebExpenseFlowController startWorkFlow end=============");
//		return new  BaseResp<String>(IntfzRs.SUCCESS, "报销发起成功", "");
//	}

	/**
	 * 流程收回接口
	 * @return
	 */
	@RequestMapping(value = "repealTask")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "WEB端报销-流程收回")
	public BaseResp<String> repealTask(@RequestBody ExpenseFlowRepealRequest expenseRepealRequest,
									   @ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
									   HttpServletRequest request, HttpServletResponse response){
		if(StringUtils.isBlank(expenseRepealRequest.getTaskId())){
			return new BaseResp<String>(IntfzRs.ERROR, "任务ID不能为空!", "");
		}
		if(StringUtils.isBlank(expenseRepealRequest.getProcInsId())){
			return new BaseResp<String>(IntfzRs.ERROR, "流程实例ID不能为空!", "");
		}
        //获取当前申请人信息
        User user = UserUtils.getUser(sessionid); //获取当前申请人信息

        try {
            ExpenseFlowRepealParam expenseFlowRepealParam = change(expenseRepealRequest,ExpenseFlowRepealParam.class);
            expenseFlowNewService.repealTask(expenseFlowRepealParam,user.getId());
        }catch (ServiceException e){
            return new BaseResp<>(IntfzRs.ERROR, e.getMessage(), "");
        }
		return new BaseResp<String>(IntfzRs.SUCCESS, "流程收回成功!", "");
	}
	
	/**
	 * 获取待办列表（分页查询）
	 * url:todo
	 * request 
	 * response data:{"list":[]}
	 */
	@RequestMapping(value = "todo")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "WEB端报销-获取待办列表")
	public BaseResp<PageResponse<List<TaskResponse>>> todoList(
			@RequestBody CommonFlowHandleReq req,
			@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response){
		WebUtils.initPre( request, response);
		User curUser = UserUtils.getUser(sessionid); //获取当前申请人信息
		if(req.getPageNo()==0 || req.getPageSize()==0){
			return new BaseResp<PageResponse<List<TaskResponse>>>(IntfzRs.ERROR, "分页参数不能为空！", 
					new PageResponse<List<TaskResponse>>(null, 0, 0));
		}
        CommonFlowHandleParam commonFlowHandleParam = change(req,CommonFlowHandleParam.class);
        PagerResponse<TaskResult> page = expenseFlowNewService.queryTodoList(commonFlowHandleParam, curUser.getId());
        List<TaskResponse> list = new LinkedList<>();
        for(TaskResult item:page.getList()){
            list.add(change(item,TaskResponse.class));
        }
		return new BaseResp<PageResponse<List<TaskResponse>>>(IntfzRs.SUCCESS, "ok", 
				new PageResponse<List<TaskResponse>>(list, req.getPageNo(), page.getCount()));
	}
	
	/**
	 * 获取已办列表（分页查询）
	 * url:historic
	 * request pageNo:当前页, pageSize:每页记录数
	 * response data:{"list":[], pageNo:"当前页", total:"当前页数"}
	 * @throws Exception 
	 */
	@RequestMapping(value = "historic")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "WEB端报销-获取已办列表")
	public BaseResp<PageResponse<List<TaskResponse>>> historicList(
			@RequestBody CommonFlowHandleReq req,
			@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		User curUser = UserUtils.getUser(sessionid); //获取当前申请人信息
		if(req.getPageNo() == 0 || req.getPageSize() == 0){
			return new BaseResp<PageResponse<List<TaskResponse>>>(IntfzRs.ERROR, "分页参数不能为空！", 
					new PageResponse<List<TaskResponse>>(null, 0, 0));
		}
//		if(StringUtils.isBlank(req.getBillType())){
//			return new BaseResp<PageResponse<List<TaskResponse>>>(IntfzRs.ERROR, "billType不能为空！", 
//					new PageResponse<List<TaskResponse>>(null, 0, 0));
//		}
        CommonFlowHandleParam commonFlowHandleParam = change(req,CommonFlowHandleParam.class);
        PagerResponse<TaskResult> todoListPage = expenseFlowNewService.queryHistoricList(commonFlowHandleParam, curUser.getId());
        List<TaskResponse> list = new LinkedList<>();
        for(TaskResult item:todoListPage.getList()){
            list.add(change(item,TaskResponse.class));
        }

		return new BaseResp<PageResponse<List<TaskResponse>>>(IntfzRs.SUCCESS, "ok", 
				new PageResponse<List<TaskResponse>>(list, req.getPageNo(), todoListPage.getCount()));
	}
	
	/**
	 * 获取审批流程详情接口
	 * url:flowdetail
	 * request procInsId:流程实例ID(必填), procDefId:流程定义ID(必填), taskId:任务ID(必填)
	 * response data:{"list":[]}
	 * @throws Exception 
	 */
	@RequestMapping(value = "flowdetail")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "WEB端报销-查询审批流程详情")
	public BaseResp<ExpenseFlowDetailResponse> flowDetail(
			@RequestBody ExpenseFlowHandleReq expenseFlowHandleReq,
			@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		User curUser = UserUtils.getUser(sessionid); //获取当前申请人信息
		String serverUrl = UploadUtils.getServerUrl(); //附件服务器地址
		// 获取流程实例对象
		if (StringUtils.isBlank(expenseFlowHandleReq.getExpenseFlowId())){
			logger.info("=======IntfzWebExpenseFlowController flowDetail end=============参数不能为空");
			return new BaseResp<ExpenseFlowDetailResponse>(IntfzRs.ERROR, "参数不能为空", new ExpenseFlowDetailResponse());
		}

		ExpenseFlowHandleParam expenseFlowHandleParam = change(expenseFlowHandleReq,ExpenseFlowHandleParam.class);
        expenseFlowHandleParam.setClientType(Constant.CLIENT_TYPE_WEB);
        ExpenseFlowResult expenseFlowResult =  expenseFlowNewService.queryExpenseFLowDetail(expenseFlowHandleParam, serverUrl,curUser.getId());
        ExpenseFlowResponse mainInfo = change(expenseFlowResult, ExpenseFlowResponse.class);
        ExpenseFlowDetailResponse resp =  change(expenseFlowResult,ExpenseFlowDetailResponse.class);
        resp.setDetail(mainInfo);

		return new BaseResp<ExpenseFlowDetailResponse>(IntfzRs.SUCCESS, "ok", resp);
	}

	/**
	 * 财务提前打款列表
	 * @param sessionid
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "bringRemitList")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "WEB端报销-财务提前打款列表")
	public BaseResp<PageResponse<List<BringRemitResponse>>> bringRemitList(
			@RequestBody BringRemitHandleRequest req,
			@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		User user = UserUtils.getUser(sessionid); //获取当前申请人信息

        BringRemitHandleParam bringRemitHandleParam = change(req,BringRemitHandleParam.class);
		PagerResponse<BringRemitResult> page = expenseFlowNewService.queryBringRemitList(bringRemitHandleParam,user.getId());
		List<BringRemitResponse> list = new LinkedList<>();
		for(BringRemitResult item:page.getList()){
		    list.add(change(item,BringRemitResponse.class));
        }

		return new BaseResp<PageResponse<List<BringRemitResponse>>>(IntfzRs.SUCCESS, "ok", 
				new PageResponse<List<BringRemitResponse>>(list, req.getPageNo(), page.getCount()));
	}

	/**
	 * 财务提前打款保存
	 * @param sessionid
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "bringRemitSave")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "WEB端报销-财务提前打款保存")
	public BaseResp<String> bringRemitSave(
			@RequestBody BringRemitSaveRequest req,
			@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
        User user = UserUtils.getUser(sessionid); //获取当前申请人信息

        BringRemitSaveParam bringRemitSaveParam = change(req,BringRemitSaveParam.class);
		Boolean flag = expenseFlowNewService.saveBringRemit(bringRemitSaveParam,user.getId());
		if(flag){
			return new BaseResp<String>(IntfzRs.SUCCESS, "保存成功！", "");
		}
		return new BaseResp<String>(IntfzRs.ERROR, "保存失败，未找到信息！", "");
	}

	/**
	 * 报销单据查询
	 * @param
	 * @param request  pageNo:当前页, pageSize:每页记录数
	 * @param response data:{"list":[], pageNo:"当前页", total:"当前页数"}
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "queryExpenseList")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "WEB端报销-报销单据查询")
	public BaseResp<PageResponse<List<ExpenseFlow>>> queryExpenseList(
			@RequestBody ExpenseFlowListRequest req,
			@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		//校验入参
		if (req.getPageNo() < 1 || req.getPageSize() < 1) {
			logger.error("=======IntfzWebExpenseFlowController queryExpenseList end=============请传入合法的分页参数");
			return new BaseResp<>(IntfzRs.ERROR, "请传入合法的分页参数", null);
		}
        User user = UserUtils.getUser(sessionid); //获取当前申请人信息

        ExpenseFlowQueryPageParam param = change(req,ExpenseFlowQueryPageParam.class);
        PagerResponse<ExpenseFlowPaginationResult> page =  expenseFlowNewService.queryAllExpenseList(param,user.getId());
        List<ExpenseFlow> list = new LinkedList<>();
        Office temp = new Office();
        for(ExpenseFlowPaginationResult item : page.getList()){
            ExpenseFlow e = change(item,ExpenseFlow.class);
            temp.setId(item.getOfficeId());
            temp.setName(item.getOfficeName());
            e.setOffice(temp);
            list.add(e);
        }


		logger.info("功能使用情况表插入数据");
		AddUsageParam addUsageParam = new AddUsageParam();
		addUsageParam.setFunctionCode("bxdj");
		addUsageParam.setFunctionName("报销单据查询");
		addUsageParam.setTerminalCode("1");
		addUsageParam.setTerminalName("web");
		addUsageParam.setUserId(user.getId());
		addUsageParam.setUserName(user.getName());
		addUsageParam.setCreateBy(user.getId());
		addUsageParam.setDate(new Date());
		intfzWebUsageService.addUsage(addUsageParam);

		return new BaseResp<PageResponse<List<ExpenseFlow>>>(IntfzRs.SUCCESS, "ok",
				new PageResponse<List<ExpenseFlow>>(list, req.getPageNo(), page.getCount()));
	}


	/**
	 * 获取审批时长统计数据
	 */
	@RequestMapping(value = "approveTimeInfo")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "WEB端报销-获取审批时长统计数据")
	public BaseResp<List<ApproveTimeInfo>> approveTimeInfo(
			@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);

		List<ApproveTimeInfo> todolist = approveTimeInfoService.findList(new ApproveTimeInfo());

		return new BaseResp<>(IntfzRs.SUCCESS, "ok",todolist);
	}
}
