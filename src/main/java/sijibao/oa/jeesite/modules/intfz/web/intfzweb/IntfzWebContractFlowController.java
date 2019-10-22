package sijibao.oa.jeesite.modules.intfz.web.intfzweb;

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
import com.sijibao.oa.modules.flow.entity.ContractFlow;
import com.sijibao.oa.modules.flow.service.ContractFlowService;
import com.sijibao.oa.modules.intfz.bean.BaseResp;
import com.sijibao.oa.modules.intfz.enums.IntfzRs;
import com.sijibao.oa.modules.intfz.request.contract.*;
import com.sijibao.oa.modules.intfz.response.common.PageResponse;
import com.sijibao.oa.modules.intfz.response.contract.ContractCompanyInfoResponse;
import com.sijibao.oa.modules.intfz.response.contract.ContractFlowResponse;
import com.sijibao.oa.modules.intfz.response.contract.FirstCompanyInfoResponse;
import com.sijibao.oa.modules.intfz.service.contract.IntfzContractFlowService;
import com.sijibao.oa.modules.intfz.utils.CharChangeUtils;
import com.sijibao.oa.modules.intfz.utils.Constant;
import com.sijibao.oa.modules.sys.entity.User;
import com.sijibao.oa.modules.sys.utils.UserUtils;
import com.sijibao.oa.office.api.IntfzContractConfigService;
import com.sijibao.oa.office.api.response.contracthis.ContractConfigAttachmentResponse;
import com.sijibao.oa.office.api.response.contracthis.ContractConfigResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * WEB端合同管理流程
 * @author xuby
 */
@Controller
@RequestMapping(value = "${frontPath}/webContractFlow")
@Api(value="WEB合同管理流程服务（废弃）",tags="WEB合同管理流程服务")
public class IntfzWebContractFlowController extends BaseController {
	
	@Autowired
	private ContractFlowService contractFlowService;
	
	@Autowired
	private ActTaskService actTaskService;
	
	@Autowired
	private IntfzContractFlowService intfzContractFlowService;
	@Autowired
    private IntfzContractConfigService intfzContractConfigService;
	/**
	 * Web端合同管理流程发起
	 * @param req
	 * @param sessionid
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "contractFlowApply")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "Web端合同管理-发起申请")
	public BaseResp<String> contractFlowApply(
			@RequestBody ContractFlowRequest req,
			@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		User user = UserUtils.getUser(sessionid); //获取当前申请人信息
		CharChangeUtils.CharChange(req);//替换英文字符
		if(user == null){
			return new BaseResp<String>(IntfzRs.ERROR, "未找到用户信息", "");
		}
		try {
			//发起申请
			intfzContractFlowService.contractApplyService(req, user,Constant.CLIENT_TYPE_WEB);
		} catch (ServiceException e) {
			return new BaseResp<String>(IntfzRs.ERROR, e.getMessage(), "");
		}
		return new BaseResp<String>(IntfzRs.SUCCESS, "申请发起成功", "");
	}
	
	/**
	 * 合同管理流程撤销
	 * @param contractFlowRepealRequest
	 * @param sessionid
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "contractFlowRepealTask")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "Web端合同管理-流程撤销")
	public BaseResp<String> contractFlowRepealTask(@RequestBody ContractFlowRepealRequest contractFlowRepealRequest,
												   @ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
												   HttpServletRequest request, HttpServletResponse response){
		if(StringUtils.isBlank(contractFlowRepealRequest.getTaskId())){
			return new BaseResp<String>(IntfzRs.ERROR, "任务ID不能为空!", "");
		}
		if(StringUtils.isBlank(contractFlowRepealRequest.getProcInsId())){
			return new BaseResp<String>(IntfzRs.ERROR, "流程实例ID不能为空!", "");
		}
		//判断当前单据已经审批结束
		ContractFlow e = contractFlowService.getByProcInsId(contractFlowRepealRequest.getProcInsId());
		if(Constant.expense_approve_end.equals(e.getContractFlowStatus())){
			return new BaseResp<String>(IntfzRs.ERROR, "当前单据已经审批结束，无需进行收回!", "");
		}
		User user = UserUtils.getUser(sessionid); //获取当前申请人信息
		//判断当前任务是否已经在本人待办任务中
		Act act = new Act();
		act.setProcInsId(contractFlowRepealRequest.getProcInsId());
		act = actTaskService.queryThisRunTaskId(act);
		if(act != null && StringUtils.isNotBlank(act.getAssignee()) && user.getLoginName().equals(act.getAssignee())){
			return new BaseResp<String>(IntfzRs.ERROR, "当前单据已在个人待处理任务中，无需进行收回!", "");
		}
		if(act != null && Constant.CONTRACT_FLOW_NODE_PRINTING.equals(act.getTaskDefKey())){
			return new BaseResp<String>(IntfzRs.ERROR, "当前流程已到财务用印环节，不能进行收回!", "");
		}
		
		//发起撤销
		ContractFlow contractFlow = new ContractFlow();
		contractFlow.getAct().setProcInsId(contractFlowRepealRequest.getProcInsId());
		contractFlow.getAct().setTaskId(contractFlowRepealRequest.getTaskId());
		intfzContractFlowService.contractRepealTask(contractFlow, user);

		return new BaseResp<String>(IntfzRs.SUCCESS, "流程收回成功!", "");
	}
	
	/**
	 * 合同管理保存草稿
	 * @param req
	 * @param sessionid
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	/*@RequestMapping(value = "saveContractFlowInfo")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "Web端合同管理-单据保存草稿")
	public BaseResp<String> saveContractFlowInfo(@RequestBody ContractFlowRequest req,
			@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		logger.info("=====IntfzWebContractFlowController saveContractFlowInfo[req]====={}", req.toString());
		User user = UserUtils.getUser(sessionid); //获取当前申请人信息
		
		//保存草稿
		intfzContractFlowService.saveContractFlowService(req, user, Constatnt.CLIENT_TYPE_WEB);
		logger.info("======= IntfzWebContractFlowController saveContractFlowInfo end=============");
		return new  BaseResp<String>(IntfzRs.SUCCESS, "单据保存成功", "");
	}*/
	
	/**
	 * 合同管理流程同意/驳回
	 * @param req
	 * @param sessionid
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "contractFlowCompleteTask")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "Web端合同管理-同意/驳回")
	public BaseResp<String> contractFlowCompleteTask(@RequestBody ContractFlowCompleteTaskReq req,
													 @ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
													 HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		//接收复杂json格式的参数
		User user = UserUtils.getUser(sessionid); //获取当前申请人信息
		CharChangeUtils.CharChange(req);//替换英文字符
		if (StringUtils.isBlank(req.getComment()) && "no".equals(req.getFlag())) {
			logger.error("=======IntfzWebContractFlowController contractFlowCompleteTask end=============请填写审核意见");
			return new BaseResp<String>(IntfzRs.ERROR, "请填写审核意见", "");
		}
		if (StringUtils.isBlank(req.getProcInsId())){
			logger.error("=======IntfzWebContractFlowController contractFlowCompleteTask end=============procInsId is not null");
			return new BaseResp<String>(IntfzRs.ERROR, "procInsId is not null", "");
		}
		if(StringUtils.isBlank(req.getContractFlowId())){
			logger.error("=======IntfzWebContractFlowController contractFlowCompleteTask end=============contractFlowId is not null");
			return new BaseResp<String>(IntfzRs.ERROR, "contractFlowId is not null", "");
		}
		
		ContractFlow contractFlow = new ContractFlow();
		contractFlow = contractFlowService.get(req.getContractFlowId());
		contractFlow.getAct().setFlag(req.getFlag()); //意见状态yes/no
		contractFlow.getAct().setComment(StringUtils.isBlank(req.getComment())?"同意":req.getComment()); //审批意见
		contractFlow.getAct().setProcInsId(req.getProcInsId()); //流程实例ID
		
		Act act = new Act();
		act.setAssignee(user.getLoginName()); //当前登录用户
		act.setProcInsId(req.getProcInsId()); //流程实例ID
		act = actTaskService.queryThisRunTaskId(act);
		
		if(act == null || StringUtils.isBlank(act.getTaskId())){
			logger.error("=======IntfzWebContractFlowController contractFlowCompleteTask end=============找不到当前流程任务信息，禁止提交");
			return new BaseResp<String>(IntfzRs.ERROR, "找不到当前流程任务信息，禁止提交", "");
		}else{
			contractFlow.getAct().setTaskId(act.getTaskId());
			contractFlow.getAct().setTaskDefKey(act.getTaskDefKey());
		}
		
		//财务用印环节，进行扫描件上传
		ContractConfigResponse config = intfzContractConfigService.get(contractFlow.getConfigId());//获取模版，判断附件是否必须上传
		List<ContractConfigAttachmentResponse> list = config.getContractConfigAttachmentList();
		for(ContractConfigAttachmentResponse res : list ){
			if("2".equals(res.getAttachmentType()) && !"0".equals(res.getMustCount()) ){
				if(act != null && Constant.CONTRACT_FLOW_NODE_PRINTING.equals(act.getTaskDefKey()) &&
						(req.getContractAttachmentList() == null || req.getContractAttachmentList().size() == 0)){
					logger.error("=======IntfzWebContractFlowController contractFlowCompleteTask end=============contractAttachmentList is not null");
					return new BaseResp<String>(IntfzRs.ERROR, "请上传附件!", "");
				}
			}
		}

		try {
			//流程审批
			intfzContractFlowService.contractFlowCompleteTask(contractFlow, user,req.getContractAttachmentList());
		} catch (ServiceException e) {
			return new BaseResp<String>(IntfzRs.ERROR, e.getMessage(), "");
		}
		return new BaseResp<String>(IntfzRs.SUCCESS, "审批成功", "");
	}
	
	/**
	 * 删除合同管理申请
	 * @param req
	 * @param sessionid
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	/*@RequestMapping(value = "contractFlowRepealApply")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "Web端合同管理-删除单据")
	public BaseResp<String> contractFlowRepealApply(@RequestBody ContractFlowHandleReq req,
			@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		logger.info("===IntfzWebContractFlowController contractFlowRepealApply[req]=== " + req.toString());
		if (StringUtils.isBlank(req.getContractFlowId())){
			logger.info("=======IntfzWebContractFlowController contractFlowRepealApply start=============contractFlowId is not null");
			return new BaseResp<String>(IntfzRs.ERROR, "contractFlowId is not null", "");
		}
		ContractFlow contractFlow = contractFlowService.get(req.getContractFlowId()); //查询合同申请实体
		if(contractFlow == null){
			return new BaseResp<String>(IntfzRs.ERROR, "未找到需要删除的单据信息", "");
		}
		if(Constatnt.expense_save.equals(contractFlow.getContractFlowStatus())){ //保存状态不需要删除task
			contractFlow.getAct().setTaskId(null); //任务ID
		}
		//删除申请
		intfzContractFlowService.deleteContractFlow(contractFlow);
		
		logger.info("=======IntfzWebContractFlowController contractFlowRepealApply end=============");
		return new BaseResp<String>(IntfzRs.SUCCESS, "删除合同申请成功", "");
	}*/
	
//	/**
//	 * 合同管理详情展示
//	 * @param contractFlowHandleReq
//	 * @param sessionid
//	 * @param request
//	 * @param response
//	 * @return
//	 * @throws Exception
//	 */
//	@RequestMapping(value = "contractFlowDetail")
//	@ResponseBody
//	@ApiOperation(httpMethod = "POST", value = "Web合同管理-查询审批流程详情")
//	public BaseResp<ContractFlowDetailResponse> contractFlowDetail(
//			@RequestBody ContractFlowHandleReq contractFlowHandleReq,
//			@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
//			HttpServletRequest request, HttpServletResponse response) throws Exception{
//		WebUtils.initPre(request, response);
//		logger.info("=====IntfzWebContractFlowController contractFlowDetail[req]======{}", contractFlowHandleReq.toString());
//		User curUser = UserUtils.getUser(sessionid); //获取当前申请人信息
//		// 获取流程实例对象
//		if (StringUtils.isBlank(contractFlowHandleReq.getContractFlowId())){
//			logger.info("=======IntfzWebContractFlowController contractFlowDetail end=============参数不能为空");
//			return new BaseResp<ContractFlowDetailResponse>(IntfzRs.ERROR, "参数不能为空", new ContractFlowDetailResponse());
//		}
//		ContractFlow contractFlow = contractFlowService.get(contractFlowHandleReq.getContractFlowId()); 
//		ContractFlowResponse contractFlowResponse = null;
//		if(contractFlow != null){
//			contractFlowResponse = intfzContractFlowService.contractFlowDetail(contractFlowResponse, contractFlow, curUser);
//		}else{
//			return new BaseResp<ContractFlowDetailResponse>(IntfzRs.ERROR, "查询不到当前申请详细信息", new ContractFlowDetailResponse());
//		}
//		//查询日志
//		List<Act> histoicFlowList = new ArrayList<>();
//		if(StringUtils.isNotBlank(contractFlowResponse.getProcInsId())){ //实例ID不为空则进行查询
//			histoicFlowList = actTaskService.histoicFlowList(contractFlowResponse.getProcInsId(), "", "");
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
//		if(Constatnt.expense_delete.equals(contractFlow.getContractFlowStatus())){
//			flowloglist.add(new FlowLogResponse("已删除",contractFlow.getApplyPerName(), "", "", "",""));
//		}
//		
//		logger.info("=======IntfzWebContractFlowController contractFlowDetail end=============");
//		return new BaseResp<ContractFlowDetailResponse>(IntfzRs.SUCCESS, "ok", 
//				new ContractFlowDetailResponse(contractFlowResponse, flowloglist));
//	}
	
	
	/**
	 * 查询合同管理流程我的申请列表
	 * @param req
	 * @param sessionid
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "queryMyContractFlowList")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "Web端合同管理-查询列表")
	public BaseResp<PageResponse<List<ContractFlowResponse>>> queryMyTravelFlowList(
			@RequestBody ContractFlowListReq req,
			@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		if(req.getPageNo()==0 || req.getPageSize()==0){
			return new BaseResp<PageResponse<List<ContractFlowResponse>>>(IntfzRs.ERROR, "分页参数不能为空！", 
					new PageResponse<List<ContractFlowResponse>>(null, 0, 0));
		}
		User user = UserUtils.getUser(sessionid); //获取当前申请人信息
		
		Page<ContractFlowResponse> resultPage = new Page<ContractFlowResponse>();
		resultPage = intfzContractFlowService.queryMyContractFlowList(req, user,resultPage); //查询个人所有单据信息

		return new BaseResp<PageResponse<List<ContractFlowResponse>>>(IntfzRs.SUCCESS, "ok", 
				new PageResponse<List<ContractFlowResponse>>(resultPage.getList(), resultPage.getPageNo(), resultPage.getCount()));
	}
	
	/**
	 * Web端合同管理-查询合同公司信息
	 * @param sessionid
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "contractCompanyInfoList")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "Web端合同管理-查询合同公司信息")
	public BaseResp<List<ContractCompanyInfoResponse>> contractCompanyInfoList(
			@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		List<ContractCompanyInfoResponse> contractCompanyInfoResponseList = intfzContractFlowService.contractCompanyInfoList();
		return new BaseResp<List<ContractCompanyInfoResponse>>(IntfzRs.SUCCESS, "ok", contractCompanyInfoResponseList);
	}
	
	/**
	 * Web端合同管理-查询甲方公司信息
	 * @param sessionid
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "firstCompanyInfoList")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "Web端合同管理-查询甲方公司信息")
	public BaseResp<PageResponse<List<FirstCompanyInfoResponse>>> firstCompanyInfoList(
			@RequestBody FirstCompanyInfoHandleRequest req,
			@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		User user = UserUtils.getUser(sessionid);
		Page<FirstCompanyInfoResponse> page = intfzContractFlowService.firstCompanyInfoList(req,user);
		return new BaseResp<PageResponse<List<FirstCompanyInfoResponse>>>(IntfzRs.SUCCESS, "ok",
				new PageResponse<List<FirstCompanyInfoResponse>>(page.getList(), page.getPageNo(), page.getCount()));
	}
	
	
}
