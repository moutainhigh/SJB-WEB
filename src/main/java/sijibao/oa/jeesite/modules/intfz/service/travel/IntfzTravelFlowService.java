package sijibao.oa.jeesite.modules.intfz.service.travel;

import java.math.BigDecimal;
import java.rmi.ServerException;
import java.text.DecimalFormat;
import java.util.*;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sijibao.oa.common.persistence.Page;
import com.sijibao.oa.common.service.BaseService;
import com.sijibao.oa.common.service.ServiceException;
import com.sijibao.oa.common.utils.DateUtils;
import com.sijibao.oa.common.utils.IdGen;
import com.sijibao.oa.message.api.enums.Constants;
import com.sijibao.oa.message.api.request.EmailMessageRequest;
import com.sijibao.oa.message.api.request.MesAppMessageRequest;
import com.sijibao.oa.message.api.request.SmsMessageRequest;
import com.sijibao.oa.modules.act.entity.Act;
import com.sijibao.oa.modules.act.service.ActTaskService;
import com.sijibao.oa.modules.act.utils.ActUtils;
import com.sijibao.oa.modules.flow.dao.ResourcesHandleFlowDao;
import com.sijibao.oa.modules.flow.dao.TravelFlowDao;
import com.sijibao.oa.modules.flow.entity.ResourcesHandleFlow;
import com.sijibao.oa.modules.flow.entity.TravelFlow;
import com.sijibao.oa.modules.flow.service.TravelFlowService;
import com.sijibao.oa.modules.flow.utils.FlowUtils;
import com.sijibao.oa.modules.intfz.request.demand.DemandManagementBudgetRequest;
import com.sijibao.oa.modules.intfz.request.intfzwebreq.QueryFlowRevenceRequest;
import com.sijibao.oa.modules.intfz.request.travel.TravelFlowRequest;
import com.sijibao.oa.modules.intfz.response.DemandManagementBudgetResponse;
import com.sijibao.oa.modules.intfz.response.intfzwebresp.QueryFlowRevenceResponse;
import com.sijibao.oa.modules.intfz.response.travel.TravelFlowResponse;
import com.sijibao.oa.modules.intfz.service.demand.IntfzDemandManagementBudgetService;
import com.sijibao.oa.modules.intfz.service.util.AppMessageService;
import com.sijibao.oa.modules.intfz.service.util.CommonService;
import com.sijibao.oa.modules.intfz.utils.Constant;
import com.sijibao.oa.modules.intfz.validator.IntfzValidateUtils;
import com.sijibao.oa.modules.intfz.websocket.MessageHandler;
import com.sijibao.oa.modules.oa.dao.MessageInfoDao;
import com.sijibao.oa.modules.oa.entity.DemandManagementBudget;
import com.sijibao.oa.modules.oa.entity.MessageInfo;
import com.sijibao.oa.modules.oa.entity.ProjectInfo;
import com.sijibao.oa.modules.oa.service.DemandManagementBudgetService;
import com.sijibao.oa.modules.oa.service.ExpensesSubInfoService;
import com.sijibao.oa.modules.oa.service.ProjectInfoService;
import com.sijibao.oa.modules.sys.dao.PostInfoDao;
import com.sijibao.oa.modules.sys.entity.PostInfo;
import com.sijibao.oa.modules.sys.entity.User;
import com.sijibao.oa.modules.sys.utils.DictUtils;
import com.sijibao.oa.modules.sys.utils.UserUtils;

/**
 * 出差申请流程服务层
 * @author xuby
 */
@Service
@Transactional(readOnly = true)
public class IntfzTravelFlowService extends BaseService{

	@Autowired
	private ActTaskService actTaskService;
	@Autowired
	private TravelFlowDao travelFlowDao;
	@Autowired
	private TravelFlowService travelFlowService;
	@Autowired
	private ProjectInfoService projectInfoService;
	@Autowired
	private DemandManagementBudgetService demandManagementBudgetService;
	@Autowired
	private PostInfoDao postInfoDao;
	@Autowired
	private ResourcesHandleFlowDao resourcesHandleFlowDao;
	@Autowired
	private IntfzDemandManagementBudgetService intfzDemandManagementBudgetService;
	@Autowired
	private CommonService commonService;
	@Autowired
	private MessageInfoDao messageInfoDao;
	@Autowired
	private AppMessageService appMessageService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private ExpensesSubInfoService expensesSubInfoService;
	@Autowired
	private MessageHandler messageHandler;

	/**
	 * 出差申请-启动流程
	 * @param travelFlowRequest
	 */
	@Transactional(readOnly = false)
	public void travelApplyService(TravelFlowRequest travelFlowRequest, User user,String clientType){
		try {
			/**********出差申请流程业务处理start*******/
			//遍历详情，查询科目字段是否停用
			if(travelFlowRequest.getDemandBudgetList() != null && travelFlowRequest.getDemandBudgetList().size() > 0){
				List<String> ids = Lists.newArrayList();
				for(DemandManagementBudgetRequest det :travelFlowRequest.getDemandBudgetList()){
					if(StringUtils.isNotBlank(det.getFirstSub())){
						ids.add(det.getFirstSub());
					}
					if(StringUtils.isNotBlank(det.getSecondSub())){
						ids.add(det.getSecondSub());
					}
					if(det.getSubject() != null && det.getSubject().length > 0){
						for(String s: det.getSubject()){
							ids.add(s);
						}
					}
				}
				if(ids != null && ids.size() > 0){
					int t = expensesSubInfoService.querySubByIds(ids);//查询所有明细中，被停用的费用科目个数
					if(t > 0){
						throw new ServiceException("请正确选择科目（科目被停用或未填写）！");
					}
				}else{
					throw new ServiceException("请正确选择科目（科目被停用或未填写）！");
				}


			}
			IntfzValidateUtils.valid(travelFlowRequest);
			TravelFlow travelFlow = new TravelFlow();
			travelFlow.setProducSide(travelFlowRequest.getProducSide());
			travelFlow.setApplyPerCode(user.getLoginName()); //申请人编号
			travelFlow.setApplyPerName(user.getName()); //申请人名称
			travelFlow.setApplyTime(new Date());//申请时间
			travelFlow.setOffice(user.getOffice()); //所属部门
			if(StringUtils.isNotBlank(travelFlowRequest.getRelationTheme())){
				ResourcesHandleFlow resourcesHandleFlow = resourcesHandleFlowDao.getByProcCode(travelFlowRequest.getRelationTheme());
				if(resourcesHandleFlow != null){
					travelFlow.setProjectId(resourcesHandleFlow.getProjectId());
				}
			}else{
				travelFlow.setProjectId(travelFlowRequest.getProjectId()); //项目ID
			}
			ProjectInfo projectInfo = projectInfoService.get(travelFlow.getProjectId());
			if(projectInfo != null){
				travelFlow.setProjectName(projectInfo.getProjectName());
				if(projectInfo.getProjectLeader()!=null){
					travelFlow.setProjectPersonel(projectInfo.getProjectLeader().getName()); //项目负责人
				}
			}
			travelFlow.setTravelExpenseTypes(StringUtils.join(travelFlowRequest.getTravelExpenseTypeList(),","));//出差报销分类
			if(travelFlowRequest.getEntourageList().size() > 0){
				if(travelFlowRequest.getEntourageList().size() > 30){
					throw new ServiceException("随行人员不能超过30个");
				}

				travelFlow.setEntourages(StringUtils.join(travelFlowRequest.getEntourageList(),","));//随行人员(非必填)
			}else{
				travelFlow.setEntourages("");
			}
			if(StringUtils.isBlank(travelFlowRequest.getRemarks())){
				travelFlow.setRemarks(""); //备注
			}else{
				travelFlow.setRemarks(travelFlowRequest.getRemarks()); //备注
			}

			travelFlow.setTravelStatus(Constant.expense_save); //出差申请状态保存

			String flowCode = "";
			BigDecimal totalAmount = new BigDecimal(0);
			DecimalFormat df = new DecimalFormat("0.00");
			List<DemandManagementBudgetRequest> demandManagementBudgetRequestList = travelFlowRequest.getDemandBudgetList();
			if(demandManagementBudgetRequestList != null){  //判断明细是否为空
				if(demandManagementBudgetRequestList.size() > 30){
					throw new ServiceException("当前单据明细不能超过30条");
				}
				for(DemandManagementBudgetRequest demandManagementBudgetRequest:demandManagementBudgetRequestList){
					if(demandManagementBudgetRequest.getExpenseAmt() != null) {
						totalAmount = totalAmount.add(demandManagementBudgetRequest.getExpenseAmt());
					}
				}
				travelFlow.setBudgetTotal(new BigDecimal(df.format(totalAmount))); //预算合计
			}
			travelFlow.setRelationTheme(travelFlowRequest.getRelationTheme()); //申请主题

			if(travelFlow.getRemarks() == null){
				travelFlow.setRemarks("");
			}
			// 申请发起
			if (StringUtils.isBlank(travelFlowRequest.getProcInsId())){ //判断流程定义ID
				flowCode = FlowUtils.genFlowCode();
				travelFlow.setProcCode(flowCode);
				String title = user.getName()+"_出差_"+DateUtils.formatDate(travelFlow.getApplyTime(), "yyyyMMdd")+"_"+flowCode;
				travelFlow.setProcName(title);
				travelFlow.setTravelStatus(Constant.expense_approve); //出差申请状态审批中
				travelFlow.preInsertForWeChart(user);
				travelFlowDao.insert(travelFlow);
				// 启动流程
				String procInsd = actTaskService.startProcessForWechat(ActUtils.OA_TRAVEL_FLOW[0], ActUtils.OA_TRAVEL_FLOW[1], travelFlow.getId(), title,user);

				//自动跳过第一个环节
				Map<String, Object> vars = Maps.newHashMap();
				vars.put("pass", "1");
				Act act = new Act();
				act.setProcInsId(procInsd); //流程实例ID
				act = actTaskService.queryThisRunTaskId(act);
				act.setComment("发起申请");
				actTaskService.complete(act.getTaskId(), act.getProcInsId(), act.getComment(),title, vars);

//				//审批人员为空自动跳过
//				actTaskService.completeAutoEmptyPersonsel(act.getProcInsId(), "", vars);

                //非最后一个审批节点，审批人员为空自动跳过，连续两个审批人为同一个审批人时，第二个审批节点跳过   整个流程适用
                actTaskService.completeAutoEmptyAndIdenPersonel(act.getProcInsId(), "", vars, travelFlow.getApplyPerCode());
			}else{// 重新编辑申请直接跳转流程
				/*************查询当前任务IDstart************/
				Act act = new Act();
				act.setAssignee(user.getLoginName()); //当前登录用户
				act.setProcInsId(travelFlowRequest.getProcInsId()); //流程实例ID
				act = actTaskService.queryThisRunTaskId(act);
				if(act == null || StringUtils.isBlank(act.getTaskId())){
					throw new ServiceException("找不到当前流程任务信息，禁止提交！");
				}else{
					travelFlow.getAct().setTaskId(act.getTaskId());
				}
				/*************查询当前任务IDend************/
				travelFlow.setId(travelFlowRequest.getId());
				travelFlow.getAct().setProcInsId(travelFlowRequest.getProcInsId()); //流程实例ID
				travelFlow.getAct().setComment("重新申请");

				// 完成当前流程任务
				Map<String, Object> vars = Maps.newHashMap();
				vars.put("pass", "1"); //设置变量，1：true,0:false
				TravelFlow temp =travelFlowDao.get(travelFlow.getId());
				travelFlow.setProcCode(temp.getProcCode());
				travelFlow.setProcInsId(temp.getProcInsId());
				travelFlow.setProcName(temp.getProcName());
				travelFlow.setTravelStatus(Constant.expense_approve); //出差申请状态审批中
				travelFlow.preUpdateForWechart(user);
				travelFlowDao.update(travelFlow);
				flowCode = temp.getProcCode(); //获取流程编码
				actTaskService.complete(travelFlow.getAct().getTaskId(), travelFlow.getAct().getProcInsId(), travelFlow.getAct().getComment(), temp.getProcName(), vars);

//				//审批人员为空自动跳过
//				actTaskService.completeAutoEmptyPersonsel(travelFlow.getAct().getProcInsId(), "", vars);

                //非最后一个审批节点，审批人员为空自动跳过，连续两个审批人为同一个审批人时，第二个审批节点跳过   整个流程适用
                actTaskService.completeAutoEmptyAndIdenPersonel(travelFlow.getAct().getProcInsId(), "", vars, travelFlow.getApplyPerCode());
			}
			/*****出差申请流程业务处理end*******/

			/*****出差申请明细业务处理start*****/
			intfzDemandManagementBudgetService.saveDemandManagementBudgetInfo(flowCode, user, demandManagementBudgetRequestList, clientType);
			/*****出差申请明细业务处理end*****/

			/*****出差申请流程业务处理end*******/


				//消息推送
				MesAppMessageRequest req = new MesAppMessageRequest();
				travelFlow = travelFlowDao.get(travelFlow.getId());
				req.setFromCode(user.getMobile());
                Task task = taskService.createTaskQuery().processInstanceId(travelFlow.getProcInsId()).active().singleResult();
				if(null != task) {
					String toCode = task.getAssignee();
					logger.info("【出差申请节点信息】：{}", "审批人loginName："+ task.getAssignee()+",当前节点："+task.getTaskDefinitionKey());
					req.setToCode(toCode);
					String loginNo = req.getToCode();
					req.setData("出差申请");//内容
					req.setNoticeItem("您有单据待审批");//主题
					String messages = "您有单据待审批:" + travelFlow.getApplyPerName() + "的出差申请单据待审批，流程编号为:" + travelFlow.getProcCode() + "消息时间：" + DateUtils.format(new Date(), DateUtils.YYYY_MM_DD_HH_MM_SS);
					req.setNoticeContentIos(messages);//ios消息内容
					req.setNoticeContentAndroid(messages);//android 消息内容
					appMessageService.sendAsync(req);
					//保存消息列表信息
					MessageInfo m = new MessageInfo();
					m.setId(IdGen.uuid());
					m.setCommonId("");
					m.setBillType(Constant.MESSAGE_BILL_TYPE_TRAVEL);
					m.setBusinessId(travelFlow.getId());
					m.setProcInsId(travelFlow.getProcInsId());
					m.setReadStatus("1");
					m.setSendMessage(messages);
					m.setRecPersion(UserUtils.getByLoginName(toCode).getId());
					m.setCreateTime(new Date());
					int re = messageInfoDao.insert(m);
					if (re != 1) {
						logger.error("*********保存消息列表信息失败*********");
					} else {
						messageHandler.sendMessageToUser(UserUtils.getByLoginName(toCode).getMobile());
						logger.info("*********保存消息列表信息成功！toCode={}*********", toCode);
					}
					//发送短信
					appMessageService.sendSms(new SmsMessageRequest(loginNo, messages, 938, Constants.Type.BUSINESS.getKey()));
					//发送邮件
					appMessageService.sendEmail(new EmailMessageRequest(loginNo,"(管理助手)您有单据待审批",messages, Constants.Type.BUSINESS.getKey()));
				}


		} catch (ServerException e) {
			throw new ServiceException(e.getMessage());
		}
	}


	/**
	 * 出差申请-保存草稿
	 * @param travelFlowRequest
	 */
	@Transactional(readOnly = false)
	public void saveTravelFlowService(TravelFlowRequest travelFlowRequest,User user,String clientType){
		/**********出差申请保存业务处理start*******/
		TravelFlow travelFlow = new TravelFlow();
		travelFlow.setApplyPerCode(user.getLoginName()); //申请人编号
		travelFlow.setApplyPerName(user.getName()); //申请人名称
		travelFlow.setApplyTime(new Date());//申请时间
		travelFlow.setOffice(user.getOffice()); //所属部门
		if(StringUtils.isNotBlank(travelFlowRequest.getRelationTheme())){
			ResourcesHandleFlow resourcesHandleFlow = resourcesHandleFlowDao.getByProcCode(travelFlowRequest.getRelationTheme());
			if(resourcesHandleFlow != null){
				travelFlow.setProjectId(resourcesHandleFlow.getProjectId());
			}
		}else{
			travelFlow.setProjectId(travelFlowRequest.getProjectId()); //项目ID
		}
		ProjectInfo projectInfo = projectInfoService.get(travelFlow.getProjectId());
		if(projectInfo != null){
			travelFlow.setProjectName(projectInfo.getProjectName());// 项目名称
			if(projectInfo.getProjectLeader() != null){
				travelFlow.setProjectPersonel(projectInfo.getProjectLeader().getName()); //项目负责人
			}
		}
		travelFlow.setTravelExpenseTypes(StringUtils.join(travelFlowRequest.getTravelExpenseTypeList(),","));//出差报销分类
		if(travelFlowRequest.getEntourageList().size() > 0){
			if(travelFlowRequest.getEntourageList().size() > 30){
				throw new ServiceException("随行人员不能超过30个");
			}
			travelFlow.setEntourages(StringUtils.join(travelFlowRequest.getEntourageList(),","));//随行人员(非必填)
		}else{
			travelFlow.setEntourages("");
		}
		if(StringUtils.isBlank(travelFlowRequest.getRemarks())){
			travelFlow.setRemarks(""); //备注
		}else{
			travelFlow.setRemarks(travelFlowRequest.getRemarks()); //备注
		}

		travelFlow.setTravelStatus(Constant.expense_save); //出差申请状态保存
		BigDecimal totalAmount = new BigDecimal(0);
		DecimalFormat df = new DecimalFormat("0.00");
		List<DemandManagementBudgetRequest> demandManagementBudgetRequestList = travelFlowRequest.getDemandBudgetList();
		if(demandManagementBudgetRequestList != null){  //判断明细是否为空
			if(demandManagementBudgetRequestList.size() > 30){
				throw new ServiceException("当前单据明细不能超过30条");
			}
			for(DemandManagementBudgetRequest demandManagementBudgetRequest:demandManagementBudgetRequestList){
				if(demandManagementBudgetRequest.getExpenseAmt() != null){
					totalAmount = totalAmount.add(demandManagementBudgetRequest.getExpenseAmt());
				}
			}
			travelFlow.setBudgetTotal(new BigDecimal(df.format(totalAmount))); //预算合计
		}
		travelFlow.setRelationTheme(travelFlowRequest.getRelationTheme()); //申请主题

		if(travelFlow.getRemarks() == null){
			travelFlow.setRemarks("");
		}
		String flowCode;
		// 第一次保存
		if (StringUtils.isBlank(travelFlowRequest.getId())){ //判断单据ID
			flowCode = FlowUtils.genFlowCode();
			travelFlow.setProcCode(flowCode);
			String title = user.getName()+"_出差_"+DateUtils.formatDate(travelFlow.getApplyTime(), "yyyyMMdd")+"_"+flowCode;
			travelFlow.setProcName(title);
			travelFlow.preInsertForWeChart(user);
			travelFlowDao.insert(travelFlow);
		}else{// 重新保存
			travelFlow.setId(travelFlowRequest.getId());

			TravelFlow temp = travelFlowDao.get(travelFlow.getId());
			flowCode = temp.getProcCode(); //获取流程编码
			travelFlow.setProcCode(flowCode);
			travelFlow.setProcName(temp.getProcName());
			travelFlow.setTravelStatus(temp.getTravelStatus());
			travelFlow.setProcInsId(temp.getProcInsId());
			travelFlow.preUpdateForWechart(user);
			travelFlowDao.update(travelFlow);
		}
		/*****出差申请保存业务处理end*******/

		/*****出差申请明细业务处理start*****/
		intfzDemandManagementBudgetService.saveDemandManagementBudgetInfo(flowCode, user, demandManagementBudgetRequestList, clientType);
		/*****出差申请明细业务处理end*****/
	}

	/**
	 * 审核审批保存
	 */
	@Transactional(readOnly = false)
	public void travelFlowCompleteTask(TravelFlow travelFlow,User user) {
		// 设置审批意见
		travelFlow.getAct().setComment(("yes".equals(travelFlow.getAct().getFlag())?"[同意] ":"[驳回] ")+travelFlow.getAct().getComment());
		String flag = travelFlow.getAct().getFlag();
		// 提交流程任务
		Map<String, Object> vars = Maps.newHashMap();
		vars.put("pass", "yes".equals(travelFlow.getAct().getFlag())? "1" : "0");

		if("no".equals(flag)){
			Act act = new Act();
			act.setProcInsId(travelFlow.getAct().getProcInsId()); //流程实例ID
			act = actTaskService.queryThisRunTaskId(act); //查询当前任务信息

			//会签或签的时候再打开
//			List<Act> actList = actTaskService.queryThisRunTaskIdList(act);

			//查询开始环节任务
			String targetTaskDefinitionKey = "modify";
			//会签或签的时候再打开
			actTaskService.jumpTaskAndAddComent(travelFlow.getAct().getProcInsId(),act.getTaskId(),travelFlow.getAct().getComment(),user.getLoginName(),targetTaskDefinitionKey, vars);
			//会签或签的时候再打开
//			actTaskService.jumpTaskAndAddComentForChildList(travelFlow.getAct().getProcInsId(), actList, travelFlow.getAct().getComment(), user.getLoginName(), targetTaskDefinitionKey, vars);
			//判断流程是否退回并更新出差申请状态
			travelFlow.setTravelStatus(Constant.expense_back); //出差申请状态被退回
			travelFlowDao.updateTravelStatus(travelFlow);
		}else{
			actTaskService.complete(travelFlow.getAct().getTaskId(), travelFlow.getAct().getProcInsId(), travelFlow.getAct().getComment(), vars);

//			//审批人员为空自动跳过
//			actTaskService.completeAutoEmptyPersonsel(travelFlow.getAct().getProcInsId(), "", vars);

            //非最后一个审批节点，审批人员为空自动跳过，连续两个审批人为同一个审批人时，第二个审批节点跳过   整个流程适用
            actTaskService.completeAutoEmptyAndIdenPersonel(travelFlow.getAct().getProcInsId(), "", vars, travelFlow.getApplyPerCode());
		}

		//判断流程是否结束并更新出差申请状态
		Act e = new Act();
		e.setProcInsId(travelFlow.getAct().getProcInsId()); //流程实例ID
		e = actTaskService.queryThisRunTaskId(e);
		if(e == null){
			//流程已结束
			travelFlow.setFlowFinishTime(new Date());
			travelFlow.setTravelStatus(Constant.expense_approve_end); //出差申请状态审批结束
			travelFlowDao.updateTravelStatusAndFlowFinshTime(travelFlow);


			travelFlow = travelFlowDao.get(travelFlow.getId());
			MesAppMessageRequest req = new MesAppMessageRequest();
			req.setFromCode(user.getMobile());
			String toCode = travelFlow.getApplyPerCode();
			req.setToCode(toCode);
			req.setData("出差申请");//内容
			req.setNoticeItem("您有单据待审批");//主题
			String messages = "您的出差申请单据审批已通过，流程编号为"+travelFlow.getProcCode() + "消息时间：" + DateUtils.format(new Date(),DateUtils.YYYY_MM_DD_HH_MM_SS);
			req.setNoticeContentIos(messages);//ios消息内容
			req.setNoticeContentAndroid(messages);//android 消息内容
			appMessageService.sendAsync(req);
			//保存消息列表信息
			MessageInfo m = new MessageInfo();
			m.setId(com.sijibao.base.common.provider.utils.IdGen.uuid());
			m.setCommonId("");
			m.setBillType(Constant.MESSAGE_BILL_TYPE_TRAVEL);
			m.setBusinessId(travelFlow.getId());
			m.setProcInsId(travelFlow.getProcInsId());
			m.setReadStatus("1");
			m.setSendMessage(messages);
			m.setRecPersion(UserUtils.getByLoginName(toCode).getId());
			m.setCreateTime(new Date());
			int re = messageInfoDao.insert(m);
			if(re != 1 ){
				logger.error("*********保存消息列表信息失败*********");
			}else {
				messageHandler.sendMessageToUser(UserUtils.getByLoginName(toCode).getMobile());
			}

			//发送短信
			appMessageService.sendSms(new SmsMessageRequest(travelFlow.getApplyPerCode(),messages,936, Constants.Type.BUSINESS.getKey()));

			//发送邮件
			appMessageService.sendEmail(new EmailMessageRequest(travelFlow.getApplyPerCode(),"(管理助手)您有单据待审批",messages, Constants.Type.BUSINESS.getKey()));


		}else {
			MesAppMessageRequest req = new MesAppMessageRequest();
			req.setFromCode(user.getMobile());
			req.setData("出差申请");//内容
			if ("yes".equals(travelFlow.getAct().getFlag())) {
				travelFlow = travelFlowDao.get(travelFlow.getId());
				String toCode = e.getAssignee();
				req.setToCode(e.getAssignee());
				req.setNoticeItem("您有单据待审批");//主题
				String messages = "您有单据待审批:" + travelFlow.getApplyPerName() + "的出差申请单据待审批，流程编号为:" + travelFlow.getProcCode() + "消息时间：" + DateUtils.format(new Date(),DateUtils.YYYY_MM_DD_HH_MM_SS);
				req.setNoticeContentIos(messages);//ios消息内容
				req.setNoticeContentAndroid(messages);//android 消息内容
				//保存消息列表信息
				MessageInfo m = new MessageInfo();
				m.setId(com.sijibao.base.common.provider.utils.IdGen.uuid());
				m.setCommonId("");
				m.setBillType(Constant.MESSAGE_BILL_TYPE_TRAVEL);
				m.setBusinessId(travelFlow.getId());
				m.setProcInsId(travelFlowDao.get(travelFlow.getId()).getProcInsId());
				m.setReadStatus("1");
				m.setSendMessage(messages);
				m.setRecPersion(UserUtils.getByLoginName(toCode).getId());
				m.setCreateTime(new Date());
				int re = messageInfoDao.insert(m);
				if (re != 1) {
					logger.error("*********保存消息列表信息失败*********");
				}else {
					messageHandler.sendMessageToUser(UserUtils.getByLoginName(toCode).getMobile());
				}

//			}
				//打印节点信息
				logger.info("【出差申请节点信息】{}","审批人loginName："+ e.getAssignee() +"，当前节点："+ e.getTaskDefKey());
				//发送短信
				appMessageService.sendSms(new SmsMessageRequest(e.getAssignee(),messages,936, Constants.Type.BUSINESS.getKey()));
				//发送邮件
				appMessageService.sendEmail(new EmailMessageRequest(e.getAssignee(),"(管理助手)您有单据待审批",messages, Constants.Type.BUSINESS.getKey()));


			} else {
				travelFlow = travelFlowDao.get(travelFlow.getId());
				String toCode = travelFlow.getApplyPerCode();
				req.setToCode(toCode);
				req.setNoticeItem("您有单据被驳回");//主题
				String messages = "您有单据被驳回:您的出差申请单据被驳回，流程编号为" + travelFlow.getProcCode() + "消息时间：" + DateUtils.format(new Date(),DateUtils.YYYY_MM_DD_HH_MM_SS);
				req.setNoticeContentIos(messages);//ios消息内容
				req.setNoticeContentAndroid(messages);//android 消息内容
				//保存消息列表信息
				MessageInfo m = new MessageInfo();
				m.setId(com.sijibao.base.common.provider.utils.IdGen.uuid());
				m.setCommonId("");
				m.setBillType(Constant.MESSAGE_BILL_TYPE_TRAVEL);
				m.setBusinessId(travelFlow.getId());
				m.setProcInsId(travelFlowDao.get(travelFlow.getId()).getProcInsId());
				m.setReadStatus("1");
				m.setSendMessage(messages);
				m.setRecPersion(UserUtils.getByLoginName(toCode).getId());
				m.setCreateTime(new Date());
				int re = messageInfoDao.insert(m);
				if (re != 1) {
					logger.error("*********保存消息列表信息失败*********");
				}else {
					messageHandler.sendMessageToUser(UserUtils.getByLoginName(toCode).getMobile());
				}
				//发送短信
				appMessageService.sendSms(new SmsMessageRequest(toCode,messages,936, Constants.Type.BUSINESS.getKey()));
				//发送邮件
				appMessageService.sendEmail(new EmailMessageRequest(toCode,"(管理助手)您有单据待审批",messages, Constants.Type.BUSINESS.getKey()));
			}

		}
	}

	/**
	 * 发起流程
	 */
	@Transactional(readOnly = false)
	public void travelStartWorkFlow(TravelFlow travelFlow,User user){
		Map<String, Object> vars = Maps.newHashMap();
		vars.put("pass", "1");
		Act act = new Act();

		if(StringUtils.isNotBlank(travelFlow.getProcInsId())){
			act.setProcInsId(travelFlow.getProcInsId()); //流程实例ID
			act = actTaskService.queryThisRunTaskId(act);
			actTaskService.complete(act.getTaskId(), travelFlow.getAct().getProcInsId(), "重新申请", travelFlow.getProcName(), vars);
		}else{
			// 启动流程
			String procInsd = actTaskService.startProcessForWechat(ActUtils.OA_TRAVEL_FLOW[0], ActUtils.OA_TRAVEL_FLOW[1], travelFlow.getId(), travelFlow.getProcName(),user);

			//自动跳过第一个环节
			act.setProcInsId(procInsd); //流程实例ID
			act = actTaskService.queryThisRunTaskId(act);
			act.setComment("发起申请");
			actTaskService.complete(act.getTaskId(), act.getProcInsId(), act.getComment(),travelFlow.getProcName(), vars);
		}

		travelFlow.setTravelStatus(Constant.expense_approve); //出差申请状态审批中
		travelFlowDao.updateTravelStatus(travelFlow);
	}

	/**
	 * 修改已读状态
	 * @param curUser
	 */
	@Transactional(readOnly = false)
	public void updateReadStatus(TravelFlow travelFlow, User curUser){
		//修改已读状态
		MessageInfo mess = new MessageInfo();
		mess.setRecPersion(curUser.getId());
		mess.setBusinessId(travelFlow.getId());
		messageInfoDao.updateReadStatus(mess);
	}
	/**
	 * 出差申请单据明细(主表数据)
	 * @param travelFlow
	 * @param curUser
	 * @return
	 */
	public TravelFlowResponse travelFlowDetail(TravelFlow travelFlow, User curUser){
		logger.info("=======travelFlowDetail travelFlow============="+travelFlow.toString());

		TravelFlowResponse travelFlowResponse = new TravelFlowResponse(travelFlow);
		//查询申请人所属岗位
		User u = UserUtils.getByLoginName(travelFlow.getApplyPerCode());
		PostInfo postInfo = postInfoDao.get(u.getPostIds());
		travelFlowResponse.setPostName(postInfo.getPostName());
		//比较是否是当前人
		if(StringUtils.equals(curUser.getLoginName(), travelFlowResponse.getApplyPerCode())){
			travelFlowResponse.setLocal(1);
		}
		//判断当前环节是否可以编辑页面
		travelFlowResponse.setIsDeit(0);
		if(Constant.expense_save.equals(travelFlow.getTravelStatus())){
			travelFlowResponse.setIsDeit(1);
			travelFlowResponse.setModify(Constant.DEFAULT_NOE_MODIFY);
		}
		//查询关联主题名称
		if(StringUtils.isNotBlank(travelFlow.getRelationTheme())){
			ResourcesHandleFlow resourcesHandleFlow = resourcesHandleFlowDao.getByProcCode(travelFlow.getRelationTheme());
			if(resourcesHandleFlow != null){
				travelFlowResponse.setRelationThemeName(resourcesHandleFlow.getProcName());
			}
		}
		Act a = new Act();
		a.setProcInsId(travelFlow.getProcInsId());
		a = actTaskService.queryThisRunTaskId(a);
		if(a != null && Constant.DEFAULT_NOE_MODIFY.equals(a.getTaskDefKey()) && StringUtils.equals(curUser.getLoginName(), a.getAssignee())){
			travelFlowResponse.setIsDeit(1);
			travelFlowResponse.setModify(Constant.DEFAULT_NOE_MODIFY);
		}
		return travelFlowResponse;
	}

	/**
	 * 出差申请明细(预算明细数据)
	 * @param detailsList
	 * @param travelFlow
	 * @return
	 */
	public List<DemandManagementBudgetResponse> travelDemandBudgetDetail(List<DemandManagementBudgetResponse> detailsList,TravelFlow travelFlow,String clientType){
		//查询预算明细信息
		DemandManagementBudget query = new DemandManagementBudget();
		query.setProcCode(travelFlow.getProcCode());
		query.setUserCode(travelFlow.getApplyPerCode());
		List<DemandManagementBudget> demandManagementBudgetLists = demandManagementBudgetService.findList(query);
		List<DemandManagementBudget> demandManagementBudgetList = Lists.newArrayList();
		if(demandManagementBudgetLists != null && demandManagementBudgetLists.size() > 0){
			for(DemandManagementBudget bud : demandManagementBudgetLists){
				if("1".equals(bud.getFirstEnable()) || "1".equals(bud.getSecondEnable())){
					bud.setFirstSub("");
					bud.setSecondSub("");
				}
				demandManagementBudgetList.add(bud);
			}
		}
		for (int i = 0; i < demandManagementBudgetList.size(); i++) {
			//处理城市信息
			Map<String, String[]> areaMap = commonService.getBusinessArea(
					demandManagementBudgetList.get(i).getStartPoint(), demandManagementBudgetList.get(i).getEndPoint());
			detailsList.add(new DemandManagementBudgetResponse(demandManagementBudgetList.get(i),areaMap.get("startArea"),areaMap.get("startAreaName"),areaMap.get("endArea"),areaMap.get("endAreaName"),clientType));
		}
		return detailsList;
	}

	/**
	 * 关联出差查询列表
	 * @param user
	 * @return
	 */
	public Page<QueryFlowRevenceResponse> queryTravelFlowRevencelist(User user, QueryFlowRevenceRequest queryFlowRevenceRequest) {
		TravelFlow rec = new TravelFlow();
		rec.setTravelStatus(Constant.expense_approve_end);
		rec.setProcName(queryFlowRevenceRequest.getProcName());
		rec.setProjectName(queryFlowRevenceRequest.getProjectName());
		if(queryFlowRevenceRequest.getBeginApplyTime() != 0l){
			rec.setBeginApplyTime(DateUtils.parseDateFormUnix(queryFlowRevenceRequest.getBeginApplyTime(),DateUtils.YYYY_MM_DD_HH_MM_SS));
		}
		if(queryFlowRevenceRequest.getEndApplyTime() != 0l){
			rec.setEndApplyTime(DateUtils.parseDateFormUnix(queryFlowRevenceRequest.getEndApplyTime(),DateUtils.YYYY_MM_DD_HH_MM_SS));
		}
		rec.setCreateBy(user);
		rec.setFilterExhausted(true);//过滤掉被报销流程关联次数已达上限的出差申请
		Page<TravelFlow> page = travelFlowService.findPage(new Page<TravelFlow>(queryFlowRevenceRequest.getPageNo(), queryFlowRevenceRequest.getPageSize()),rec);
		Page<QueryFlowRevenceResponse> newPage = new Page<>();
		newPage.setPageNo(page.getPageNo());
		newPage.setPageSize(page.getPageSize());
		newPage.setCount(page.getCount());

		for (TravelFlow r : page.getList()) {
			QueryFlowRevenceResponse rf = new QueryFlowRevenceResponse();
			rf.setProcInsId(r.getProcInsId());
			rf.setProcCode(r.getProcCode());
			rf.setProcName(r.getProcName());
			rf.setApplyPerCode(r.getApplyPerCode());
			rf.setApplyPerName(r.getApplyPerName());
			rf.setApplyTime(DateUtils.formatDate(r.getApplyTime(), DateUtils.YYYY_MM_DD));
			rf.setProjectId(r.getProjectId());
			rf.setProjectName(r.getProjectName());
			rf.setProjectPersonel(r.getProjectPersonel());

			//判断关联类型
			if(StringUtils.isNotBlank(r.getRelationTheme())){//关联的是主题
				rf.setRelType("1");
			}else if(StringUtils.isNotBlank(r.getProjectId())){//关联的是项目
				rf.setRelType("2");
			}else {//不关联
				rf.setRelType("3");
			}

			// 出差报销分类(id列表和name列表)
			List<String> expenseTypeList = Arrays.asList(r.getTravelExpenseTypes().split(","));
			List<String> expenseTypeListName = new LinkedList<>();
			for(String s : expenseTypeList){
				expenseTypeListName.add(DictUtils.getDictLabel(s,"travel_expense_type",""));
			}
			rf.setTravelExpenseTypeList(expenseTypeList);
			rf.setTravelExpenseTypeListName(expenseTypeListName);

			// 随行人员(id列表和name列表)（字段非必填）
			List<String> entourageList;
			if(StringUtils.isNotBlank(r.getEntourages())){
				entourageList = Arrays.asList(r.getEntourages().split(","));
			}else {
				entourageList = new ArrayList<>();
			}
			List<String> entourageListName = new LinkedList<>();
			if(entourageList.size() > 0){
				for(String s : entourageList){
					entourageListName.add(UserUtils.get(s).getName());
				}
			}
			rf.setEntourageList(entourageList);
			rf.setEntourageListName(entourageListName);

			rf.setBudgetTotal(r.getBudgetTotal());
			rf.setStatus(r.getTravelStatus());
			rf.setStatusValue(r.getTravelStatusValue());
			newPage.getList().add(rf);
		}
		return newPage;
	}

	/**
	 * 任务撤销
	 * @param travelFlow
	 */
	@Transactional(readOnly = false)
	public void repealTask(TravelFlow travelFlow,User user){
		/**********流程跳转，发起撤销start********/
		Map<String, Object> vars = Maps.newHashMap();
		vars.put("pass", "1");

		//查询被撤销的节点信息
		Act act = new Act();
		act.setProcInsId(travelFlow.getAct().getProcInsId()); //流程实例ID
		act = actTaskService.queryThisRunTaskId(act); //查询当前任务信息

		//会签或签的时候再开打
//		List<Act> list = actTaskService.queryThisRunTaskIdList(act);

		//查询发起撤销节点key
		Act targetAct = new Act();
		if("0".equals(travelFlow.getAct().getTaskId())){
			targetAct.setTaskDefKey("modify");
		}else{
			targetAct.setTaskId(travelFlow.getAct().getTaskId());
			targetAct.setProcInsId(travelFlow.getAct().getProcInsId());
			targetAct = actTaskService.queryHisTask(targetAct);
		}

		String targetTaskDefinitionKey = targetAct.getTaskDefKey(); //发起撤销的节点key
		actTaskService.jumpTaskAndAddComent(travelFlow.getAct().getProcInsId(), act.getTaskId(),
				"[流程撤回]当前审核任务被"+user.getName()+"撤回", user.getLoginName(), targetTaskDefinitionKey, vars);

		//会签或签的时候再开打
//		actTaskService.jumpTaskAndAddComentForChildList(travelFlow.getAct().getProcInsId(), list,
//				"[流程撤回]当前审核任务被"+user.getName()+"撤回", user.getLoginName(), targetTaskDefinitionKey, vars);
		/**********流程跳转，发起撤销end********/
	}


	/**
	 * 删除任务
	 * @param travelFlow
	 */
	@Transactional(readOnly = false)
	public void deleteTravelFlowInfo(TravelFlow travelFlow){
		if(Constant.expense_save.equals(travelFlow.getTravelStatus())){ //保存的单据进行物理删除
			DemandManagementBudget demandManagementBudget = new DemandManagementBudget();
			demandManagementBudget.setProcCode(travelFlow.getProcCode());
			demandManagementBudgetService.deleteForProcCode(demandManagementBudget); //删除出差申请明细信息
			travelFlowDao.deleteTravelFlowInfo(travelFlow); //删除出差主表信息
		}else{
			if(StringUtils.isNotBlank(travelFlow.getProcInsId())){
				//删除任务
				actTaskService.deleteRunTask(travelFlow.getProcInsId()); //删除运行任务
				actTaskService.deleteHisTask(travelFlow.getProcInsId()); //删除历史任务
			}
			//删除单据
			travelFlowDao.delete(travelFlow);
			//修改单据状态为已删除
			travelFlow.setTravelStatus(Constant.expense_delete); //出差状态被删除
			travelFlowDao.updateTravelStatus(travelFlow);
		}
	}
}
