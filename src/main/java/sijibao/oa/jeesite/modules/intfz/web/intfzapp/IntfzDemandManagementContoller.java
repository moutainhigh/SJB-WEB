package sijibao.oa.jeesite.modules.intfz.web.intfzapp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sijibao.oa.common.web.BaseController;

import io.swagger.annotations.Api;

/**
 * 需求管理相关服务
 * @author xuby
 */
@Controller
@RequestMapping(value = "wechat/demand")
@Api(value="APP需求管理服务",tags="APP需求管理服务")
public class IntfzDemandManagementContoller extends BaseController {
//	
//	@Autowired
//	private IntfzDemandManagementService intfzDemandManagementService;
//	@Autowired
//	private DemandManagementService demandManagementService;
//	@Autowired
//	private ActTaskService actTaskService;
//	@Autowired
//	private DemandManagementBudgetService demandManagementBudgetService;
//	@Autowired
//	private AreaService areaInfoService;
//	/**
//	 * 实施需求申请
//	 * @param req
//	 * @param sjboacert
//	 * @param clientType
//	 * @param request
//	 * @param response
//	 * @return
//	 * @throws Exception
//	 */
//	@RequestMapping(value = "demandImplemetApply")
//	@ResponseBody
//	@ApiOperation(httpMethod = "POST", value = "实施需求-流程申请")
//	public BaseResp<String> demandImplemetApply(
//			@RequestBody DemandManagementRequest req,
//			@ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert, 
//			@ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
//			HttpServletRequest request,HttpServletResponse response) throws Exception{
//		WebUtils.initPre(request, response);
//		logger.info("====IntfzDemandManagementContoller demandImplemetApply[req]====={}", req.toString());
//		User user = super.getCurrWxUser(sjboacert, clientType); //获取当前申请人信息
//		if(user == null){
//			return new BaseResp<String>(IntfzRs.ERROR, "未找到用户信息", "");
//		}
//		try {
//			intfzDemandManagementService.demandImplemetApplyService(req, user); //实施需求申请
//		} catch (ServiceException e) {
//			logger.info("=======IntfzDemandManagementContoller demandImplemetApply end============="+e.getMessage());
//			return new BaseResp<String>(IntfzRs.ERROR, e.getMessage(), "");
//		}
//		
//		logger.info("=======IntfzDemandManagementContoller demandImplemetApply end=============");
//		return new BaseResp<String>(IntfzRs.SUCCESS, "申请发起成功", "");
//	}
//	
//	/**
//	 * 市场需求申请
//	 * @param req
//	 * @param sjboacert
//	 * @param clientType
//	 * @param request
//	 * @param response
//	 * @return
//	 * @throws Exception
//	 */
//	@RequestMapping(value = "demandMarketApply")
//	@ResponseBody
//	@ApiOperation(httpMethod = "POST", value = "市场需求-流程申请")
//	public BaseResp<String> demandMarketApply(
//			@RequestBody DemandManagementRequest req,
//			@ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert, 
//			@ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
//			HttpServletRequest request,HttpServletResponse response) throws Exception{
//		WebUtils.initPre(request, response);
//		logger.info("====IntfzDemandManagementContoller demandMarketApply[req]====={}", req.toString());
//		User user = super.getCurrWxUser(sjboacert, clientType); //获取当前申请人信息
//		if(user == null){
//			return new BaseResp<String>(IntfzRs.ERROR, "未找到用户信息", "");
//		}
//		try {
//			intfzDemandManagementService.demandMarketApplyService(req, user); //市场需求申请
//		} catch (ServiceException e) {
//			logger.info("=======IntfzDemandManagementContoller demandMarketApply end============="+e.getMessage());
//			return new BaseResp<String>(IntfzRs.ERROR, e.getMessage(), "");
//		}
//		
//		logger.info("=======IntfzDemandManagementContoller demandMarketApply end=============");
//		return new BaseResp<String>(IntfzRs.SUCCESS, "申请发起成功", "");
//	}
//	
//	
//	/**
//	 * 实施需求保存
//	 * @param req
//	 * @param sjboacert
//	 * @param clientType
//	 * @param request
//	 * @param response
//	 * @return
//	 * @throws Exception
//	 */
//	@RequestMapping(value = "saveDemandImplementInfo")
//	@ResponseBody
//	@ApiOperation(httpMethod = "POST", value = "实施需求-保存草稿")
//	public BaseResp<String> saveDemandImplementInfo(@RequestBody DemandManagementRequest req,
//			@ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert, 
//			@ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
//			HttpServletRequest request,HttpServletResponse response) throws Exception{
//		WebUtils.initPre(request, response);
//		logger.info("=====IntfzDemandManagementContoller saveDemandImplementInfo[req]====={}", req.toString());
//		User user = super.getCurrWxUser(sjboacert, clientType); //获取当前申请人信息
//		intfzDemandManagementService.saveDemandImplementInfoService(req, user); //实施需求保存
//		logger.info("=======IntfzDemandManagementContoller saveDemandImplementInfo end=============");
//		return new  BaseResp<String>(IntfzRs.SUCCESS, "单据保存成功", "");
//	}
//	
//	/**
//	 * 市场需求保存
//	 * @param req
//	 * @param sjboacert
//	 * @param clientType
//	 * @param request
//	 * @param response
//	 * @return
//	 * @throws Exception
//	 */
//	@RequestMapping(value = "saveDemandMarketInfo")
//	@ResponseBody
//	@ApiOperation(httpMethod = "POST", value = "市场需求-保存草稿")
//	public BaseResp<String> saveDemandMarketInfo(@RequestBody DemandManagementRequest req,
//			@ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert, 
//			@ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
//			HttpServletRequest request,HttpServletResponse response) throws Exception{
//		WebUtils.initPre(request, response);
//		logger.info("=====IntfzDemandManagementContoller saveDemandMarketInfo[req]====={}", req.toString());
//		User user = super.getCurrWxUser(sjboacert, clientType); //获取当前申请人信息
//		intfzDemandManagementService.saveDemandMarketInfoService(req, user); //实施需求保存
//		logger.info("=======IntfzDemandManagementContoller saveDemandMarketInfo end=============");
//		return new  BaseResp<String>(IntfzRs.SUCCESS, "单据保存成功", "");
//	}
//	
//	/**
//	 * 实施需求流程发起
//	 * @param req
//	 * @param sjboacert
//	 * @param clientType
//	 * @param request
//	 * @param response
//	 * @return
//	 * @throws Exception
//	 */
//	@RequestMapping(value = "startWorkFlowDemandImplement")
//	@ResponseBody
//	@ApiOperation(httpMethod = "POST", value = "实施需求-草稿流程发起")
//	public BaseResp<String> startWorkFlowDemandImplement(@RequestBody FlowHandleReq req,
//			@ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert, 
//			@ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
//			HttpServletRequest request,HttpServletResponse response) throws Exception{
//		WebUtils.initPre(request, response);
//		String businessId = req.getExpenseFlowId();
//		logger.info("===startWorkFlowDemandImplement[req]====== " + businessId);
//		User user = this.getCurrWxUser(sjboacert, clientType);
//		if(StringUtils.isBlank(businessId)){
//			return new  BaseResp<String>(IntfzRs.ERROR, "业务ID不能为空", "");
//		}
//		DemandManagement demandManagement = demandManagementService.get(businessId);
//		intfzDemandManagementService.startWorkFlow(demandManagement, user);
//		logger.info("=======startWorkFlowDemandImplement end=============");
//		return new  BaseResp<String>(IntfzRs.SUCCESS, "申请发起成功", "");
//	}
//	
//	/**
//	 * 市场需求流程发起
//	 * @param req
//	 * @param sjboacert
//	 * @param clientType
//	 * @param request
//	 * @param response
//	 * @return
//	 * @throws Exception
//	 */
//	@RequestMapping(value = "startWorkFlowDemandMarket")
//	@ResponseBody
//	@ApiOperation(httpMethod = "POST", value = "市场需求-草稿流程发起")
//	public BaseResp<String> startWorkFlowDemandMarket(@RequestBody FlowHandleReq req,
//			@ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert, 
//			@ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
//			HttpServletRequest request,HttpServletResponse response) throws Exception{
//		WebUtils.initPre(request, response);
//		String businessId = req.getExpenseFlowId();
//		logger.info("===startWorkFlowDemandMarket[req]====== " + businessId);
//		User user = this.getCurrWxUser(sjboacert, clientType);
//		if(StringUtils.isBlank(businessId)){
//			return new  BaseResp<String>(IntfzRs.ERROR, "业务ID不能为空", "");
//		}
//		DemandManagement demandManagement = demandManagementService.get(businessId);
//		actTaskService.startProcessForWechat(ActUtils.OA_DEMAND_FLOW[0], ActUtils.OA_DEMAND_FLOW[1], demandManagement.getId(), demandManagement.getProcName(),user);
//		demandManagement.setDemandStatus(ExpenseConstatnt.expense_approve); //需求申请状态审批中
//		demandManagementService.updateDemandStatus(demandManagement);
//		logger.info("=======startWorkFlowDemandMarket end=============");
//		return new  BaseResp<String>(IntfzRs.SUCCESS, "申请发起成功", "");
//	}
//	
//	/**
//	 * 查询需求类型信息
//	 * @param sjboacert
//	 * @param clientType
//	 * @param request
//	 * @param response
//	 * @return
//	 */
//	@RequestMapping(value = "queryDemandInfo")
//	@ResponseBody
//	@ApiOperation(httpMethod = "POST", value = "查询-需求类型")
//	public BaseResp<List<DemandInfoResponse>> queryDemandInfo(@ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert, 
//			@ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
//			HttpServletRequest request,HttpServletResponse response){
//		logger.info("======demandInfo start=======");
//		WebUtils.initPre(request, response);
//		List<DemandInfoResponse> resultList = new ArrayList<DemandInfoResponse>();
//		List<Dict> dictList = DictUtils.getDictList("oa_demand_type");
//		if(dictList != null && dictList.size() > 0){
//			for(Dict dict:dictList){
//				DemandInfoResponse demandInfoResponse = new DemandInfoResponse();
//				demandInfoResponse.setName(dict.getLabel());
//				demandInfoResponse.setValue(dict.getValue());
//				resultList.add(demandInfoResponse);
//			}
//		}
//		logger.info("======demandInfo end=======");
//		return new  BaseResp<List<DemandInfoResponse>>(IntfzRs.SUCCESS, "ok", resultList);
//	}
//	
//	/**
//	 * 获取审批流程详情接口（实施/市场申请流程）
//	 * url:flowdetail
//	 * request procInsId:流程实例ID(必填), procDefId:流程定义ID(必填), taskId:任务ID(必填)
//	 * response data:{"list":[]}
//	 * @throws Exception 
//	 */
//	@RequestMapping(value = "flowDetailDemandImplementOrMarket")
//	@ResponseBody
//	@ApiOperation(httpMethod = "POST", value = "实施/市场需求-查询详情")
//	public BaseResp<FlowDetailResponse> flowDetailDemandImplementOrMarket(
//			@RequestBody ActRequest act,
//			@ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert, 
//			@ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
//			HttpServletRequest request, HttpServletResponse response) throws Exception{
//		WebUtils.initPre(request, response);
//		logger.info("=====flowDetailDemandImplementOrMarket[req]======{}", act.toString());
//		User curUser = this.getCurrWxUser(sjboacert, clientType);
//		
//		// 获取流程实例对象
//		if (act.getBusinessId() == null){
//			logger.info("=======flowDetailDemandImplementOrMarket end=============参数不能为空");
//			return new BaseResp<FlowDetailResponse>(IntfzRs.ERROR, "参数不能为空", new FlowDetailResponse());
//		}
//		DemandManagement demandManagement = demandManagementService.get(act.getBusinessId()); 
//		DemandManagementResponse demandManagementResponse = null;
//		Act a = new Act();
//		if(demandManagement != null){
//			logger.info("=======flowDetailDemandImplementOrMarket demandManagement============="+demandManagement.toString());
//			demandManagementResponse = new DemandManagementResponse(demandManagement);
//			if(StringUtils.isNotBlank(act.getTaskId()) && !"0".equals(act.getTaskId())){
//				a.setProcInsId(demandManagement.getProcInsId());
//				a.setTaskId(act.getTaskId());
//				a = actTaskService.queryHisTask(a);
//				act.setTaskDefKey(a.getTaskDefKey());
//			}
//			else if("0".equals(act.getTaskId())){
//				a.setProcInsId(demandManagement.getProcInsId());
//				a = actTaskService.queryThisRunTaskId(a);
//				if(a != null){
//					act.setTaskDefKey(a.getTaskDefKey());
//				}
//			}
//			demandManagementResponse.setIsAssign(0); //是否可以指派人员
//			demandManagementResponse.setIsBack(0); //是否允许驳回
//			demandManagementResponse.setIsFillDetail(0); //是否能够填写明细
//			demandManagementResponse.setIsDeit(0); //是否可编辑页面
//			if(ExpenseConstatnt.expense_save.equals(demandManagement.getDemandStatus())){
//				demandManagementResponse.setIsDeit(1);
//			}
//			//查询当前环节可以分配的人员信息
//			if(a != null && ExpenseConstatnt.DEMAND_MANAGEMENT_MARKET.equals(act.getBillType())){
//				if((ExpenseConstatnt.DEMAND_MAIN_NODE.equals(act.getTaskDefKey()) 
//						|| ExpenseConstatnt.DEMAND_CHIILD_NODE1.equals(act.getTaskDefKey())
//						|| ExpenseConstatnt.DEMAND_CHIILD_NODE2.equals(act.getTaskDefKey())) 
//						&& !"completed".equals(a.getStatus()) && !"0".equals(act.getTaskId()) 
//						){
//					PostInfo postInfo = new PostInfo();
//					String postCode = SysParamsUtils.getParamValue(act.getTaskDefKey(), Global.SYS_PARAMS, ""); //指派人员岗位
//					postInfo.setPostCode(postCode);
//					if(ExpenseConstatnt.DEMAND_CHIILD_NODE2.equals(act.getTaskDefKey())){
//						postInfo.setOfficeId(curUser.getOffice().getId());
//					}
//					List<User> employeeList = demandManagementService.queryUserInfoForPostCode(postInfo);
//					demandManagementResponse.setEmployeeList(employeeList);
//					demandManagementResponse.setIsAssign(1);
//				}
//				
//				if(StringUtils.isNotBlank(act.getTaskDefKey()) && act.getTaskDefKey().startsWith(ExpenseConstatnt.DEMAND_ASSIGNEE_NODE) && !ExpenseConstatnt.DEMAIND_CHILD_NODE3.equals(act.getTaskDefKey())){
//					demandManagementResponse.setIsBack(1);
//				}
//				if(!"completed".equals(a.getStatus()) && ExpenseConstatnt.DEMAIND_CHILD_NODE3.equals(act.getTaskDefKey())){
//					demandManagementResponse.setIsFillDetail(1);
//					demandManagementResponse.setIsDeit(1);
//				}
//			}else if(ExpenseConstatnt.DEMAND_MANAGEMENT_IMPLEMENT.equals(act.getBillType())){
//				if(a != null){
//					if((!"completed".equals(a.getStatus()) || "0".equals(act.getTaskId())) && ExpenseConstatnt.DEFAULT_NOE_MODIFY.equals(act.getTaskDefKey())){
//						demandManagementResponse.setIsDeit(1);
//					}
//				}
//				demandManagementResponse.setIsFillDetail(1);
//			}
//			
//			//比较是否是当前人
//			if(StringUtils.equals(curUser.getLoginName(), demandManagement.getApplyPerCode())){
//				demandManagementResponse.setLocal(1);
//			}
//			act.setProcInsId(demandManagement.getProcInsId()); //设置流程实例ID
//		}else{
//			return new BaseResp<FlowDetailResponse>(IntfzRs.ERROR, "查询不到当前单据详细信息", new FlowDetailResponse());
//		}
//		
//		//查询日志
//		List<Act> histoicFlowList = new ArrayList<>();
//		if(StringUtils.isNotBlank(act.getProcInsId())){ //实例ID不为空则进行查询
//			if(ExpenseConstatnt.DEMAND_MANAGEMENT_MARKET.equals(act.getBillType())){
//				String execution = "";
//				String taskDefKey = "";
//				if(StringUtils.isNotBlank(act.getTaskDefKey())){
//					taskDefKey = act.getTaskDefKey();
//					if(taskDefKey.startsWith(ExpenseConstatnt.DEMAND_ASSIGNEE_NODE) && !ExpenseConstatnt.DEMAIND_CHILD_NODE3.equals(taskDefKey)){
//						execution = a.getExecutionId();
//					}
//				}
//				histoicFlowList = actTaskService.histoicFlowListForChildFlow(act.getProcInsId(),execution, "","",taskDefKey);
//			}else{
//				histoicFlowList = actTaskService.histoicFlowList(act.getProcInsId(), "", "");
//			}
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
//		if(ExpenseConstatnt.expense_delete.equals(demandManagement.getDemandStatus())){
//			flowloglist.add(new FlowLogResponse("已删除",demandManagement.getApplyPerName(), "", "", "",""));
//		}
//		List<DemandManagementBudgetResponse> detailsList = Lists.newArrayList();
//		// 查看审批申请单
//		if (StringUtils.isNotBlank(demandManagement.getId())){//.getAct().getProcInsId())){
//			DemandManagementBudget query = new DemandManagementBudget();
//			query.setProcCode(demandManagement.getProcCode());
//			if(ExpenseConstatnt.DEMAND_MANAGEMENT_MARKET.equals(act.getBillType())){
//				if(StringUtils.isNotBlank(act.getTaskDefKey()) &&
//						act.getTaskDefKey().startsWith(ExpenseConstatnt.DEMAND_ASSIGNEE_NODE)){
//					if(!ExpenseConstatnt.DEMAIND_CHILD_NODE3.equals(act.getTaskDefKey())){
//						//根据节点key和流程执行ID查询当前需求发起人ID
//						TaskInfoEntity taskInfo = new TaskInfoEntity();
//						taskInfo.setTaskDefinitionKey(ExpenseConstatnt.DEMAIND_CHILD_NODE3);
//						taskInfo.setExecutionId(a.getExecutionId());
//						taskInfo.setProcessInstanceId(demandManagement.getProcInsId());
//						taskInfo = actTaskService.queryAssigneeForExecutionId(taskInfo);
//						if(taskInfo != null && StringUtils.isNotBlank(taskInfo.getAssignee())){
//							query.setUserCode(taskInfo.getAssignee());
//						}
//					}else{
//						query.setUserCode(curUser.getLoginName());
//					}
//				}else{
//					query.setUserCode("1");
//				}
//			}
//			List<DemandManagementBudget> demandManagementBudgetList = demandManagementBudgetService.findList(query);
//			for (int i = 0; i < demandManagementBudgetList.size(); i++) {
//				//处理开始城市信息
//				Area start = new Area();
//				start.setCode(demandManagementBudgetList.get(i).getStartPoint());
//				start = areaInfoService.getForCode(start);
//				String[] startArea = new String[1];
//				String[] startAreaName = new String[1];
//				if(start != null){
//					if("1".equals(start.getType())){
//						startArea[0] = start.getCode();
//						startAreaName[0] = start.getName();
//					}else{
//						startArea = new String[2];
//						startAreaName = new String[2];
//						Area parentStart = areaInfoService.get(start.getParentId());
//						startArea[0] = parentStart.getCode();
//						startAreaName[0] = parentStart.getName();
//						startArea[1] = start.getCode();
//						startAreaName[1] = start.getName();
//					}
//				}
//				//处理结束城市信息
//				Area end = new Area();
//				end.setCode(demandManagementBudgetList.get(i).getEndPoint());
//				end = areaInfoService.getForCode(end);
//				String[] endArea = new String[1];
//				String[] endAreaName = new String[1];
//				if(end != null){
//					if("1".equals(end.getType())){
//						
//						endArea[0] = end.getCode();
//						endAreaName[0] = end.getName();
//					}else{
//						endArea = new String[2];
//						endAreaName = new String[2];
//						Area parentEnd = areaInfoService.get(end.getParentId());
//						if(parentEnd != null){
//							endArea[0] = parentEnd.getCode();
//							endAreaName[0] = parentEnd.getName();
//							endArea[1] = end.getCode();
//							endAreaName[1] = end.getName();
//						}else{
//							endArea = new String[1];
//							endAreaName = new String[1];
//							endArea[0] = end.getCode();
//							endAreaName[0] = end.getName();
//						}
//					}
//				}
//				detailsList.add(new DemandManagementBudgetResponse(demandManagementBudgetList.get(i),startArea,startAreaName,endArea,endAreaName,ExpenseConstatnt.CLIENT_TYPE_APP));
//			}
//		}
//		logger.info("=======flowDetail end=============");
//		return new BaseResp<FlowDetailResponse>(IntfzRs.SUCCESS, "ok", 
//				new FlowDetailResponse(demandManagementResponse, detailsList, flowloglist));
//	}
//	
//	/**
//	 * 查询列表--实施/市场发起需求
//	 * @param expenseFlow
//	 * @param request  pageNo:当前页, pageSize:每页记录数
//	 * @param response data:{"list":[], pageNo:"当前页", total:"当前页数"}
//	 * @return
//	 * @throws Exception 
//	 */
//	@RequestMapping(value = "queryMyDemandImplementOrMarketList")
//	@ResponseBody
//	@ApiOperation(httpMethod = "POST", value = "实施/市场-查询列表")
//	public BaseResp<PageResponse<List<DemandManagement>>> queryMyDemandImplementOrMarketList(
//			@RequestBody FlowHandleReq req,
//			@ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert, 
//			@ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
//			HttpServletRequest request,HttpServletResponse response) throws Exception{
//		WebUtils.initPre(request, response);
//		logger.info("===IntfzDemandManagementContoller queryMyDemandImplementOrMarketList req===: " + req.toString());
//		if(req.getPageNo()==0 || req.getPageSize()==0){
//			logger.info("===IntfzDemandManagementContoller queryMyDemandImplementOrMarketList res===: " + "null");
//			return new BaseResp<PageResponse<List<DemandManagement>>>(IntfzRs.ERROR, "分页参数不能为空！", 
//					new PageResponse<List<DemandManagement>>(null, 0, 0));
//		}
//		if(StringUtils.isBlank(req.getBillType())){
//			return new BaseResp<PageResponse<List<DemandManagement>>>(IntfzRs.ERROR, "billType不能为空！", 
//					new PageResponse<List<DemandManagement>>(null, 0, 0));
//		}
//		User user = super.getCurrWxUser(sjboacert, clientType); //获取当前申请人信息
//		logger.info(user.getLoginName());
//		DemandManagement query = new DemandManagement();
//		query.setCreateBy(user);
//		if(ExpenseConstatnt.expense_approve.equals(String.valueOf(req.getExpenseStatus()))){ //运行中搜索包括审批中和被驳回
//			query.setDemandStatusIn("2,3");
//			query.setDemandStatus("");
//		}else if(ExpenseConstatnt.expense_approve_end.equals(String.valueOf(req.getExpenseStatus()))){
//			query.setDemandStatusIn("1,0");
//			query.setDemandStatus("");
//		}else{
//			query.setDemandStatus("4");
//		}
//		query.setApplyPerName(req.getApplyName());
//		query.setProcCode(req.getProcCode());
//		query.setBeginApplyTime(DateUtils.parseDate(req.getApplyTimeStart()));
//		query.setEndApplyTime(DateUtils.parseDate(req.getApplyTimeEnd()));
//		query.setBillType(req.getBillType());
//		Page<DemandManagement> page = demandManagementService.findPage(
//				new Page<DemandManagement>(req.getPageNo(), req.getPageSize()), query); //查询个人所有单据信息
//		List<DemandManagement> resultList = page.getList();
//		logger.info("=======IntfzDemandManagementContoller queryMyDemandImplementOrMarketList end=============");
//		return new BaseResp<PageResponse<List<DemandManagement>>>(IntfzRs.SUCCESS, "ok", 
//				new PageResponse<List<DemandManagement>>(resultList, page.getPageNo(), page.getCount()));
//	}
//	
//	/**
//	 * 实施需求单据审批 同意/驳回
//	 * @param req
//	 * @param sjboacert
//	 * @param clientType
//	 * @param request
//	 * @param response
//	 * @return
//	 * @throws Exception
//	 */
//	@RequestMapping(value = "completeTaskDemandImplement")
//	@ResponseBody
//	@ApiOperation(httpMethod = "POST", value = "实施需求-同意/驳回")
//	public BaseResp<String> completeTaskDemandImplement(@RequestBody CompleteTaskReq req,
//			@ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert, 
//			@ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
//			HttpServletRequest request,HttpServletResponse response) throws Exception{
//		WebUtils.initPre(request, response);
//		//接收复杂json格式的参数
//		logger.info("=====completeTaskDemandImplement[req]====={}", req.toString());
//		User user = super.getCurrWxUser(sjboacert, clientType); //获取当前申请人信息
//		if (StringUtils.isBlank(req.getComment()) && "no".equals(req.getComment())) {
//			logger.error("=======IntfzDemandManagementContoller completeTaskDemandImplement end=============请填写审核意见");
//			return new BaseResp<String>(IntfzRs.ERROR, "请填写审核意见", "");
//		}
//		if (StringUtils.isBlank(req.getProcInsId())){
//			logger.error("=======IntfzDemandManagementContoller completeTaskDemandImplement end=============procInsId is not null");
//			return new BaseResp<String>(IntfzRs.ERROR, "procInsId is not null", "");
//		}
//		if(StringUtils.isBlank(req.getExpenseFormId())){
//			logger.error("=======IntfzDemandManagementContoller completeTaskDemandImplement end=============expenseFormId is not null");
//			return new BaseResp<String>(IntfzRs.ERROR, "expenseFormId is not null", "");
//		}
//		DemandManagement demandManagement = new DemandManagement();
//		demandManagement.getAct().setFlag(req.getFlag()); //意见状态yes/no
//		demandManagement.getAct().setComment(StringUtils.isBlank(req.getComment())?"同意":req.getComment()); //审批意见
//		demandManagement.getAct().setProcInsId(req.getProcInsId()); //流程实例ID
//		demandManagement.setId(req.getExpenseFormId()); //申请业务ID
//		
//		Act act = new Act();
//		act.setAssignee(user.getLoginName()); //当前登录用户
//		act.setProcInsId(req.getProcInsId()); //流程实例ID
//		act = actTaskService.queryThisRunTaskId(act);
//		if(act == null || StringUtils.isBlank(act.getTaskId())){
//			logger.error("=======IntfzDemandManagementContoller completeTaskDemandImplement end=============找不到当前流程任务信息，禁止提交");
//			return new BaseResp<String>(IntfzRs.ERROR, "找不到当前流程任务信息，禁止提交", "");
//		}else{
//			demandManagement.getAct().setTaskId(act.getTaskId());
//		}
//		intfzDemandManagementService.completeTaskImplement(demandManagement,user); //完成当前任务
//		logger.info("=======IntfzDemandManagementContoller completeTaskDemandImplement end=============");
//		return new BaseResp<String>(IntfzRs.SUCCESS, "审批成功", "");
//	}
//	
//	
//	/**
//	 * 市场需求单据审批 同意/驳回
//	 * @param req
//	 * @param sjboacert
//	 * @param clientType
//	 * @param request
//	 * @param response
//	 * @return
//	 * @throws Exception
//	 */
//	@RequestMapping(value = "completeTaskDemandMarket")
//	@ResponseBody
//	@ApiOperation(httpMethod = "POST", value = "市场需求-同意/驳回")
//	public BaseResp<String> completeTaskDemandMarket(@RequestBody CompleteTaskReq req,
//			@ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert, 
//			@ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
//			HttpServletRequest request,HttpServletResponse response) throws Exception{
//		WebUtils.initPre(request, response);
//		//接收复杂json格式的参数
//		logger.info("=====completeTaskDemandImplement[req]====={}", req.toString());
//		User user = super.getCurrWxUser(sjboacert, clientType); //获取当前申请人信息
//		if (StringUtils.isBlank(req.getComment()) && "no".equals(req.getComment())) {
//			logger.error("=======IntfzDemandManagementContoller completeTaskDemandImplement end=============请填写审核意见");
//			return new BaseResp<String>(IntfzRs.ERROR, "请填写审核意见", "");
//		}
//		if (StringUtils.isBlank(req.getProcInsId())){
//			logger.error("=======IntfzDemandManagementContoller completeTaskDemandImplement end=============procInsId is not null");
//			return new BaseResp<String>(IntfzRs.ERROR, "procInsId is not null", "");
//		}
//		if(StringUtils.isBlank(req.getExpenseFormId())){
//			logger.error("=======IntfzDemandManagementContoller completeTaskDemandImplement end=============expenseFormId is not null");
//			return new BaseResp<String>(IntfzRs.ERROR, "expenseFormId is not null", "");
//		}
//		DemandManagement demandManagement = new DemandManagement();
//		demandManagement = demandManagementService.get(req.getExpenseFormId());
//		if(req.getEmployees() != null && req.getEmployees().length > 0){
//			demandManagement.setEmployees(req.getEmployees());
//		}
//		
//		if(req.getDemandManagementBudgetList() != null){
//			List<DemandManagementBudget> demandManagementBudgetList = new ArrayList<DemandManagementBudget>();
//			for(DemandManagementBudgetRequest demandManagementBudgetRequest:req.getDemandManagementBudgetList()){
//				DemandManagementBudget demandManagementBudget = new DemandManagementBudget();	
//				demandManagementBudget.setStartDate(DateUtils.parseDateFormUnix(demandManagementBudgetRequest.getStartDate(),DateUtils.YYYY_MM_DD)); //开始日期
//				if(demandManagementBudgetRequest.getStartPoint() != null && demandManagementBudgetRequest.getStartPoint().length > 1){
//					demandManagementBudget.setStartPoint(demandManagementBudgetRequest.getStartPoint()[1]); //起点
//				}else{
//					demandManagementBudget.setStartPoint(demandManagementBudgetRequest.getStartPoint()[0]); //起点
//				}
//				demandManagementBudget.setEndDate(DateUtils.parseDateFormUnix(demandManagementBudgetRequest.getEndDate(), DateUtils.YYYY_MM_DD)); //结束日期
//				if(demandManagementBudgetRequest.getEndPoint() != null && demandManagementBudgetRequest.getEndPoint().length > 1){
//					demandManagementBudget.setEndPoint(demandManagementBudgetRequest.getEndPoint()[1]); //终点
//				}else{
//					demandManagementBudget.setEndPoint(demandManagementBudgetRequest.getEndPoint()[0]); //终点
//				}
//				demandManagementBudget.setFirstSub(demandManagementBudgetRequest.getFirstSub()); //一级科目
//				demandManagementBudget.setSecondSub(demandManagementBudgetRequest.getSecondSub()); //二级科目
//				demandManagementBudget.setPersonNum(Integer.toString(demandManagementBudgetRequest.getPersonNum())); //人数
//				demandManagementBudget.setDayNum(demandManagementBudgetRequest.getDayNum()); //天数
//				demandManagementBudget.setExpenseAmt(demandManagementBudgetRequest.getExpenseAmt().toString()); //预算金额
//				demandManagementBudget.setRemarks(demandManagementBudgetRequest.getRemarks()); //备注
//				demandManagementBudgetList.add(demandManagementBudget);
//			}
//			demandManagement.setDemandBudgetList(demandManagementBudgetList);
//		}
//		Act act = new Act();
//		act.setAssignee(user.getLoginName()); //当前登录用户
//		act.setProcInsId(req.getProcInsId()); //流程实例ID
//		act = actTaskService.queryThisRunTaskId(act);
//		if(act == null || StringUtils.isBlank(act.getTaskId())){
//			logger.error("=======IntfzDemandManagementContoller completeTaskDemandImplement end=============找不到当前流程任务信息，禁止提交");
//			return new BaseResp<String>(IntfzRs.ERROR, "找不到当前流程任务信息，禁止提交", "");
//		}else{
//			demandManagement.setAct(act);
//			demandManagement.getAct().setFlag(req.getFlag()); //意见状态yes/no
//			demandManagement.getAct().setComment(StringUtils.isBlank(req.getComment())?"同意":req.getComment()); //审批意见
//			demandManagement.getAct().setTaskId(req.getTaskId()); //当前任务ID
//		}
//		intfzDemandManagementService.completeTaskMarket(demandManagement,user); //完成当前任务
//		logger.info("=======IntfzDemandManagementContoller completeTaskDemandImplement end=============");
//		return new BaseResp<String>(IntfzRs.SUCCESS, "审批成功", "");
//	}
//	
//	/**
//	 * 删除实施/市场需要求申请
//	 * @param expenseFlowId 业务ID
//	 * @param taskId 当前任务ID
//	 * @param response
//	 * @throws Exception 
//	 * @throws IOException 
//	 */
//	@RequestMapping(value = "repealApplyDemandImplementOrMarket")
//	@ResponseBody
//	@ApiOperation(httpMethod = "POST", value = "实施/市场需求-删除")
//	public BaseResp<String> repealApplyDemandImplementOrMarket(@RequestBody FlowHandleReq req,
//			@ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert, 
//			@ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
//			HttpServletRequest request,HttpServletResponse response) throws Exception{
//		WebUtils.initPre(request, response);
//		logger.info("===repealApplyDemandImplementOrMarket[req]=== " + req.toString());
//		if (StringUtils.isBlank(req.getExpenseFlowId())){
//			logger.info("=======repealApplyDemandImplementOrMarket end=============expenseFlowId is not null");
//			return new BaseResp<String>(IntfzRs.ERROR, "expenseFlowId is not null", "");
//		}
//		
//		DemandManagement demandManagement = demandManagementService.get(req.getExpenseFlowId()); //查询需求实体
//		if(demandManagement == null){
//			return new BaseResp<String>(IntfzRs.ERROR, "未找到需要删除的单据信息", "");
//		}
//		if(ExpenseConstatnt.expense_save.equals(demandManagement.getDemandStatus())){ //保存状态不需要删除task
//			demandManagement.getAct().setTaskId(null); //任务ID
//		}
//	
//		demandManagementService.delete(demandManagement);  //删除单据
//		logger.info("=======repealApplyDemandImplementOrMarket end=============");
//		return new BaseResp<String>(IntfzRs.SUCCESS, "删除单据成功", "");
//	}
//	
//	/**
//	 * 流程收回接口(市场/实施)
//	 * @return
//	 */
//	@RequestMapping(value = "repealTaskDemanadImplementOrMarket")
//	@ResponseBody
//	@ApiOperation(httpMethod = "POST", value = "实施/市场需求-流程收回")
//	public BaseResp<String> repealTask(@RequestBody ActRequest actRequest,
//			@ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert, 
//			@ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
//			HttpServletRequest request, HttpServletResponse response){
//		logger.info("===repealTask[req]=== " + actRequest.toString());
//		if(StringUtils.isBlank(actRequest.getTaskId())){
//			return new BaseResp<String>(IntfzRs.ERROR, "任务ID不能为空!", "");
//		}
//		
//		if(StringUtils.isBlank(actRequest.getProcInsId())){
//			return new BaseResp<String>(IntfzRs.ERROR, "流程实例ID不能为空!", "");
//		}
//		
//		//判断当前单据已经审批结束
//		DemandManagement demandManagement = demandManagementService.getDemandByProcInsId(actRequest.getProcInsId());
//		if(ExpenseConstatnt.expense_approve_end.equals(demandManagement.getDemandStatus())){
//			
//			return new BaseResp<String>(IntfzRs.ERROR, "当前单据已经审批结束，无需进行收回!", "");
//		}
//		//获取当前申请人信息
//		User user = super.getCurrWxUser(sjboacert, clientType);
//		
//		//判断当前任务是否已经在本人待办任务中
//		Act act = new Act();
//		act.setProcInsId(actRequest.getProcInsId());
//		act = actTaskService.queryThisRunTaskId(act);
//		if(act != null && StringUtils.isNotBlank(act.getAssignee()) && user.getLoginName().equals(act.getAssignee())){
//			return new BaseResp<String>(IntfzRs.ERROR, "当前单据已在个人待处理任务中，无需进行收回!", "");
//		}
//		
//		//校验当前单据是否有任务需要收回
//		Act actExec = new Act();
//		actExec.setTaskId(actRequest.getTaskId());
//		actExec.setProcInsId(actRequest.getProcInsId());
//		List<Act> actExecList = actTaskService.queryThisRunTaskIdForExecutionId(actExec);
//		if(actExecList == null || actExecList.size() == 0){
//			return new BaseResp<String>(IntfzRs.ERROR, "当前单据未找到需要收回的任务，无需进行收回!", "");
//		}
//		//发起撤销
//		String taskDefKey = "";
//		if("0".equals(actRequest.getTaskId())){
//			taskDefKey= "modify";
//		}else{
//			Act a = new Act();
//			a.setProcInsId(actRequest.getProcInsId());
//			a.setTaskId(actRequest.getTaskId());
//			a = actTaskService.queryHisTask(a);
//			if(StringUtils.isBlank(a.getTaskDefKey())){
//				return new BaseResp<String>(IntfzRs.ERROR, "当前单据审批任务异常，无法进行收回!", "");
//			}
//			taskDefKey = a.getTaskDefKey();
//		}
//		demandManagement.getAct().setTaskDefKey(taskDefKey);
//		demandManagement.getAct().setProcInsId(actRequest.getProcInsId());
//		demandManagement.getAct().setTaskId(actRequest.getTaskId());
//		String message = demandManagementService.repealTask(demandManagement,user);
//		if(!"success".equals(message)){
//			return new BaseResp<String>(IntfzRs.ERROR, message, "");
//		}
//		logger.info("===repealTask[end]=== ");
//		return new BaseResp<String>(IntfzRs.SUCCESS, "流程收回成功!", "");
//	}
//	
}
