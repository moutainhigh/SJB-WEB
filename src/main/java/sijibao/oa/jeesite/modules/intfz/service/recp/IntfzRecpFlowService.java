package sijibao.oa.jeesite.modules.intfz.service.recp;

import java.math.BigDecimal;
import java.rmi.ServerException;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sijibao.base.common.provider.utils.IdGen;
import com.sijibao.oa.common.persistence.Page;
import com.sijibao.oa.common.service.BaseService;
import com.sijibao.oa.common.service.ServiceException;
import com.sijibao.oa.common.utils.DateUtils;
import com.sijibao.oa.message.api.enums.Constants;
import com.sijibao.oa.message.api.request.EmailMessageRequest;
import com.sijibao.oa.message.api.request.MesAppMessageRequest;
import com.sijibao.oa.message.api.request.SmsMessageRequest;
import com.sijibao.oa.modules.act.entity.Act;
import com.sijibao.oa.modules.act.service.ActTaskService;
import com.sijibao.oa.modules.act.utils.ActUtils;
import com.sijibao.oa.modules.flow.dao.RecpFlowDao;
import com.sijibao.oa.modules.flow.dao.RecpParamsDao;
import com.sijibao.oa.modules.flow.entity.RecpFlow;
import com.sijibao.oa.modules.flow.entity.RecpParams;
import com.sijibao.oa.modules.flow.service.RecpFlowService;
import com.sijibao.oa.modules.flow.service.RecpParamsService;
import com.sijibao.oa.modules.flow.utils.FlowUtils;
import com.sijibao.oa.modules.intfz.request.demand.DemandManagementBudgetRequest;
import com.sijibao.oa.modules.intfz.request.intfzwebreq.QueryFlowRevenceRequest;
import com.sijibao.oa.modules.intfz.request.recp.RecpFlowRequest;
import com.sijibao.oa.modules.intfz.response.DemandManagementBudgetResponse;
import com.sijibao.oa.modules.intfz.response.intfzwebresp.QueryFlowRevenceResponse;
import com.sijibao.oa.modules.intfz.response.recp.RecpFlowResponse;
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
import com.sijibao.oa.modules.sys.utils.UserUtils;

/**
 * 接待申请流程服务层
 * @author xuby
 */
@Service
@Transactional(readOnly = true)
public class IntfzRecpFlowService extends BaseService{
	
	@Autowired
	private ActTaskService actTaskService;
	@Autowired
	private RecpFlowDao recpFlowDao;
	@Autowired
	private ProjectInfoService projectInfoService;
	@Autowired
	private RecpParamsDao recpParamsDao;
	@Autowired
	private RecpFlowService recpFlowService;
	@Autowired
	private RecpParamsService recpParamsService;
	@Autowired
	private DemandManagementBudgetService demandManagementBudgetService;
	@Autowired
	private PostInfoDao postInfoDao;
	@Autowired
	private IntfzDemandManagementBudgetService intfzDemandManagementBudgetService;
	@Autowired
	private CommonService commonService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private AppMessageService appMessageService;
	@Autowired
	private MessageInfoDao messageInfoDao ;
	@Autowired
	private ExpensesSubInfoService expensesSubInfoService;
	@Autowired
	private MessageHandler messageHandler;
	/**
	 * 接待服务
	 * @param recpFlowRequest
	 */
	@Transactional(readOnly = false)
	public void recpApplyService(RecpFlowRequest recpFlowRequest, User user,String clientType){
		try {
			/**********接待申请流程业务处理start*******/
			//遍历详情，查询科目字段是否停用
			if(recpFlowRequest.getDemandBudgetList() != null && recpFlowRequest.getDemandBudgetList().size() > 0){
				List<String> ids = Lists.newArrayList();
				for(DemandManagementBudgetRequest det :recpFlowRequest.getDemandBudgetList()){
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
			IntfzValidateUtils.valid(recpFlowRequest);
			RecpFlow recpFlow = new RecpFlow();
			recpFlow.setProducSide(recpFlowRequest.getProducSide());
			recpFlow.setApplyPerCode(user.getLoginName()); //申请人编号
			recpFlow.setApplyPerName(user.getName()); //申请人名称
			recpFlow.setApplyTime(new Date());//申请时间
			recpFlow.setOffice(user.getOffice()); //所属部门
			recpFlow.setProjectId(recpFlowRequest.getProjectId()); //项目ID
			ProjectInfo projectInfo = projectInfoService.get(recpFlowRequest.getProjectId());
			if(projectInfo != null){
				recpFlow.setProjectName(projectInfo.getProjectName());
				if(projectInfo.getProjectLeader() != null){
                    recpFlow.setProjectPersonel(projectInfo.getProjectLeader().getName()); //项目负责人

                }
			}
			if(StringUtils.isBlank(recpFlowRequest.getRemarks())){
				recpFlow.setRemarks(""); //备注
			}else{
				recpFlow.setRemarks(recpFlowRequest.getRemarks()); //备注
			}

			recpFlow.setRecpStatus(Constant.expense_save); //接待申请状态保存
			
			String flowCode = "";
			BigDecimal totalAmount = new BigDecimal(0); 
			DecimalFormat df = new DecimalFormat("0.00");
			List<DemandManagementBudgetRequest> demandManagementBudgetRequestList = recpFlowRequest.getDemandBudgetList();
			if(demandManagementBudgetRequestList != null){  //判断明细是否为空
				if(demandManagementBudgetRequestList.size() > 30){
					throw new ServiceException("当前单据明细不能超过30条");
				}
				for(DemandManagementBudgetRequest demandManagementBudgetRequest:demandManagementBudgetRequestList){
                    if(demandManagementBudgetRequest.getExpenseAmt() != null) {
                        totalAmount = totalAmount.add(demandManagementBudgetRequest.getExpenseAmt());
                    }
				}
				recpFlow.setBudgetTotal(new BigDecimal(df.format(totalAmount))); //预算合计
			}

			//保存陪客人数信息
			if(recpFlowRequest.getEmployees() != null){
				if(recpFlowRequest.getEmployees().length > 30){
					throw new ServiceException("陪客人数不能超过30个");
				}
				recpFlow.setRecpParticNum(recpFlowRequest.getEmployees().length); //陪客人数
			}
			recpFlow.setRecpTheme(recpFlowRequest.getRecpTheme()); //申请主题
			recpFlow.setRecpNum(recpFlowRequest.getRecpNum()); //接待人数
			recpFlow.setRecpTime(DateUtils.parseDateFormUnix(recpFlowRequest.getRecpTime(), DateUtils.YYYY_MM_DD_HH_MM)); //预计接待时间

            if(recpFlow.getRemarks() == null){
                recpFlow.setRemarks("");
            }
            String procInsd = "";
			// 申请发起
			if (StringUtils.isBlank(recpFlowRequest.getProcInsId())){ //判断流程定义ID
				flowCode = FlowUtils.genFlowCode();
				recpFlow.setProcCode(flowCode);
				String title = user.getName()+"_接待_"+DateUtils.formatDate(recpFlow.getApplyTime(), "yyyyMMdd")+"_"+flowCode;
				recpFlow.setProcName(title);
				recpFlow.setRecpStatus(Constant.expense_approve); //接待申请状态审批中
				recpFlow.preInsertForWeChart(user);
				recpFlowDao.insert(recpFlow);
				// 启动流程
				procInsd = actTaskService.startProcessForWechat(ActUtils.OA_RECP_FLOW[0], ActUtils.OA_RECP_FLOW[1], recpFlow.getId(), title,user);
				
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

                //非最后一个审批节点，审批人员为空自动跳过，连续两个审批人为同一个审批人时，第二个审批节点跳过（整个流程适用）
                actTaskService.completeAutoEmptyAndIdenPersonel(act.getProcInsId(), "", vars, recpFlow.getApplyPerCode());
			}else{// 重新编辑申请直接跳转流程
				/*************查询当前任务IDstart************/
				Act act = new Act();
				act.setAssignee(user.getLoginName()); //当前登录用户
				act.setProcInsId(recpFlowRequest.getProcInsId()); //流程实例ID
				act = actTaskService.queryThisRunTaskId(act);
				if(act == null || StringUtils.isBlank(act.getTaskId())){
					throw new ServiceException("找不到当前流程任务信息，禁止提交！");
				}else{
					recpFlow.getAct().setTaskId(act.getTaskId()); 
				}
				/*************查询当前任务IDend************/
				recpFlow.setId(recpFlowRequest.getId());
				recpFlow.getAct().setProcInsId(recpFlowRequest.getProcInsId()); //流程实例ID
				recpFlow.getAct().setComment("重新申请");
				
				// 完成当前流程任务
				Map<String, Object> vars = Maps.newHashMap();
				vars.put("pass", "1"); //设置变量，1：true,0:false
				RecpFlow temp =recpFlowDao.get(recpFlow.getId());
				recpFlow.setProcCode(temp.getProcCode());
				recpFlow.setProcInsId(temp.getProcInsId());
				recpFlow.setProcName(temp.getProcName());
				recpFlow.setRecpStatus(Constant.expense_approve); //接待申请状态审批中
				recpFlow.preUpdateForWechart(user);
				recpFlow.setRelated(0);
				recpFlowDao.update(recpFlow);
				flowCode = temp.getProcCode(); //获取流程编码
				
				actTaskService.complete(recpFlow.getAct().getTaskId(), recpFlow.getAct().getProcInsId(), recpFlow.getAct().getComment(), temp.getProcName(), vars);

				//审批人员为空自动跳过
				//actTaskService.completeAutoEmptyPersonsel(recpFlow.getAct().getProcInsId(), "", vars);

                //非最后一个审批节点，审批人员为空自动跳过，连续两个审批人为同一个审批人时，第二个审批节点跳过（整个流程适用）
                actTaskService.completeAutoEmptyAndIdenPersonel(act.getProcInsId(), "", vars, recpFlow.getApplyPerCode());

                procInsd = temp.getProcInsId();
			}
			/*****接待申请流程业务处理end*******/
			//打印节点信息
			Task task = taskService.createTaskQuery().processInstanceId(procInsd).active().singleResult();
			logger.info("【接待申请节点信息】{}","审批人loginName："+ task.getAssignee() +"，当前节点："+ task.getTaskDefinitionKey());
			//消息推送
			MesAppMessageRequest req = new MesAppMessageRequest();
			req.setFromCode(user.getMobile());
			String toCode = task.getAssignee();
			req.setToCode(toCode);
			String loginNo = req.getToCode();
			req.setData("接待申请");//内容
			req.setNoticeItem("您有单据待审批");//主题
			String messages = "您有单据待审批:"+recpFlow.getApplyPerName()+"的接待申请单据待审批，流程编号为:"+recpFlow.getProcCode() + "消息时间：" + DateUtils.format(new Date(),DateUtils.YYYY_MM_DD_HH_MM_SS);
			req.setNoticeContentIos(messages);//ios消息内容
			req.setNoticeContentAndroid(messages);//android 消息内容
			appMessageService.sendAsync(req);
			//保存消息列表信息
			MessageInfo m = new MessageInfo();
			m.setId(IdGen.uuid());
			m.setCommonId("");
			m.setBillType(Constant.MESSAGE_BILL_TYPE_RECP);
			m.setBusinessId(recpFlow.getId());
			m.setProcInsId(recpFlowDao.get(recpFlow.getId()).getProcInsId());
			m.setReadStatus("1");
			m.setSendMessage(messages);
			m.setRecPersion(UserUtils.getByLoginName(toCode).getId());
			m.setCreateTime(new Date());
			int re = messageInfoDao.insert(m);
			if(re != 1 ){
				logger.error("*********保存消息列表信息失败*********");
			}else{
				messageHandler.sendMessageToUser(UserUtils.getByLoginName(toCode).getMobile());
			}
			//发送短信
			appMessageService.sendSms(new SmsMessageRequest(loginNo,messages,938,Constants.Type.RECEIVE.getKey()));
			//发送邮件
			appMessageService.sendEmail(new EmailMessageRequest(toCode,"(管理助手)您有单据待审批",messages, Constants.Type.RECEIVE.getKey()));

			//保存陪客人数信息
			if(recpFlowRequest.getEmployees() != null){
				//保存陪客人员明细信息
				recpParamsDao.deleteForProcCode(flowCode);
				for(String employee:recpFlowRequest.getEmployees()){
					RecpParams recpParams = new RecpParams();
					recpParams.setProcCode(flowCode); //流程编号
					recpParams.setParamType(Constant.RECP_PARAM_TYPE_01); //类型
					User u = UserUtils.getByLoginName(employee);
					recpParams.setParamName(u.getName()); //人员名称
					recpParams.setParamValue(employee); //人员信息
					recpParams.preInsert();
					recpParamsDao.insert(recpParams);
				}
			}
			
			/*****接待申请明细业务处理start*****/
			intfzDemandManagementBudgetService.saveDemandManagementBudgetInfo(flowCode, user, demandManagementBudgetRequestList, clientType);
			/*****接待申请明细业务处理end*****/
		} catch (ServerException e) {
			throw new ServiceException(e.getMessage());
		} 
	}
	
	/**
	 * 接待申请保存服务
	 * @param recpFlowRequest
	 */
	@Transactional(readOnly = false)
	public void saveRecpFlowService(RecpFlowRequest recpFlowRequest,User user,String clientType){
		/**********接待申请保存业务处理start*******/
		RecpFlow recpFlow = new RecpFlow();
		recpFlow.setApplyPerCode(user.getLoginName()); //申请人编号
		recpFlow.setApplyPerName(user.getName()); //申请人名称
		recpFlow.setApplyTime(new Date());//申请时间
		recpFlow.setOffice(user.getOffice()); //所属部门
		recpFlow.setProjectId(recpFlowRequest.getProjectId()); //项目ID
		ProjectInfo projectInfo = projectInfoService.get(recpFlowRequest.getProjectId());
		if(projectInfo != null){
			recpFlow.setProjectName(projectInfo.getProjectName());
			if(projectInfo.getProjectLeader()!=null){
				recpFlow.setProjectPersonel(projectInfo.getProjectLeader().getName()); //项目负责人
			}
		}
		if(StringUtils.isBlank(recpFlowRequest.getRemarks())){
			recpFlow.setRemarks("");
		}else{
			recpFlow.setRemarks(recpFlowRequest.getRemarks()); //备注
		}
		recpFlow.setRecpStatus(Constant.expense_save); //接待申请状态保存
		String flowCode = null;
		BigDecimal totalAmount = new BigDecimal(0); 
		
		DecimalFormat df = new DecimalFormat("0.00");
		List<DemandManagementBudgetRequest> demandManagementBudgetRequestList = recpFlowRequest.getDemandBudgetList();
		if(demandManagementBudgetRequestList != null){  //判断明细是否为空
			if(demandManagementBudgetRequestList.size() > 30){
				throw new ServiceException("当前单据明细不能超过30条");
			}
			for(DemandManagementBudgetRequest demandManagementBudgetRequest:demandManagementBudgetRequestList){
			    if(demandManagementBudgetRequest.getExpenseAmt() != null){
                    totalAmount = totalAmount.add(demandManagementBudgetRequest.getExpenseAmt());
                }
			}
			recpFlow.setBudgetTotal(new BigDecimal(df.format(totalAmount))); //预算合计
		}
		//保存陪客人数信息
		if(recpFlowRequest.getEmployees() != null){
			if(recpFlowRequest.getEmployees().length > 30){
				throw new ServiceException("陪客人数不能超过30个");
			}
			recpFlow.setRecpParticNum(recpFlowRequest.getEmployees().length); //陪客人数
		}
		recpFlow.setRecpTheme(recpFlowRequest.getRecpTheme()); //申请主题
		recpFlow.setRecpNum(recpFlowRequest.getRecpNum()); //接待人数
		recpFlow.setRecpTime(DateUtils.parseDateFormUnix(recpFlowRequest.getRecpTime(), DateUtils.YYYY_MM_DD_HH_MM)); //预计接待时间

        if(recpFlow.getRemarks() == null){
            recpFlow.setRemarks("");
        }
		// 第一次保存
		if (StringUtils.isBlank(recpFlowRequest.getId())){ //判断单据ID
			flowCode = FlowUtils.genFlowCode();
			recpFlow.setProcCode(flowCode);
			String title = user.getName()+"_接待_"+DateUtils.formatDate(recpFlow.getApplyTime(), "yyyyMMdd")+"_"+flowCode;
			recpFlow.setProcName(title);
			recpFlow.preInsertForWeChart(user);
			recpFlowDao.insert(recpFlow);
		}else{// 重新保存
			recpFlow.setId(recpFlowRequest.getId());
			
			RecpFlow temp = recpFlowDao.get(recpFlow.getId());			
			flowCode = temp.getProcCode(); //获取流程编码
			recpFlow.setProcCode(flowCode);
			recpFlow.setProcName(temp.getProcName());
			recpFlow.setRecpStatus(temp.getRecpStatus());
			recpFlow.setProcInsId(temp.getProcInsId());
			recpFlow.preUpdateForWechart(user);
			recpFlow.setRelated(0);
			recpFlowDao.update(recpFlow);
		}
		/*****接待申请保存业务处理end*******/
		
		//保存陪客人数信息
		if(recpFlowRequest.getEmployees() != null){
			//保存陪客人员明细信息
			recpParamsDao.deleteForProcCode(flowCode);
			for(String employee:recpFlowRequest.getEmployees()){
				RecpParams recpParams = new RecpParams();
				recpParams.setProcCode(flowCode); //流程编号
				recpParams.setParamType(Constant.RECP_PARAM_TYPE_01); //类型
				User u = UserUtils.getByLoginName(employee);
				recpParams.setParamName(u.getName()); //人员名称
				recpParams.setParamValue(employee); //人员信息
				recpParams.preInsert();
				recpParamsDao.insert(recpParams);
			}
		}
		
		/*****接待申请明细业务处理start*****/
		intfzDemandManagementBudgetService.saveDemandManagementBudgetInfo(flowCode, user, demandManagementBudgetRequestList, clientType);
		/*****接待申请明细业务处理end*****/
	}
	
	/**
	 * 审核审批保存
	 * @param recpFlow
	 */
	@Transactional(readOnly = false)
	public void recpFlowCompleteTask(RecpFlow recpFlow,User user) {
		// 设置审批意见
		recpFlow.getAct().setComment(("yes".equals(recpFlow.getAct().getFlag())?"[同意] ":"[驳回] ")+recpFlow.getAct().getComment());
		String flag = recpFlow.getAct().getFlag();
		// 提交流程任务
		Map<String, Object> vars = Maps.newHashMap();
		vars.put("pass", "yes".equals(recpFlow.getAct().getFlag())? "1" : "0");
		
		if("no".equals(flag)){
			Act act = new Act();
			act.setProcInsId(recpFlow.getAct().getProcInsId()); //流程实例ID
			act = actTaskService.queryThisRunTaskId(act); //查询当前任务信息
			
			//查询开始环节任务
			String targetTaskDefinitionKey = "modify";
			actTaskService.jumpTaskAndAddComent(recpFlow.getAct().getProcInsId(),act.getTaskId(),recpFlow.getAct().getComment(),user.getLoginName(),targetTaskDefinitionKey, vars);
			//判断流程是否退回并更新接待申请状态
			recpFlow.setRecpStatus(Constant.expense_back); //接待申请状态被退回
			recpFlowDao.updateRecpStatus(recpFlow);
		}else{
			actTaskService.complete(recpFlow.getAct().getTaskId(), recpFlow.getAct().getProcInsId(), recpFlow.getAct().getComment(), vars);

 			//审批人员为空自动跳过
			//actTaskService.completeAutoEmptyPersonsel(recpFlow.getAct().getProcInsId(), "", vars);

            //非最后一个审批节点，审批人员为空自动跳过，连续两个审批人为同一个审批人时，第二个审批节点跳过   整个流程适用
            actTaskService.completeAutoEmptyAndIdenPersonel(recpFlow.getAct().getProcInsId(), "", vars, recpFlow.getApplyPerCode());
		}
		
		//判断流程是否结束并更新接待申请状态
		Act e = new Act();
		e.setProcInsId(recpFlow.getAct().getProcInsId()); //流程实例ID
		e = actTaskService.queryThisRunTaskId(e);
		String smsContent = "";
		if(e == null){
			//流程已结束
			recpFlow.setFlowFinishTime(new Date());
			recpFlow.setRecpStatus(Constant.expense_approve_end); //接待申请状态审批结束
			recpFlowDao.updateRecpStatusAndFlowFinshTime(recpFlow);
			User u = UserUtils.getByLoginName(recpFlow.getApplyPerCode());
			MesAppMessageRequest req = new MesAppMessageRequest();
			req.setFromCode(user.getMobile());
			req.setToCode(recpFlow.getApplyPerCode());
			req.setData("接待申请");//内容
			req.setNoticeItem("您有单据待审批");//主题
			String messages = "您的接待申请单据审批已通过，流程编号为"+recpFlow.getProcCode() + DateUtils.format(new Date(),DateUtils.YYYY_MM_DD_HH_MM_SS);
			req.setNoticeContentIos(messages);//ios消息内容
			req.setNoticeContentAndroid(messages);//android 消息内容
			appMessageService.sendAsync(req);
			//保存消息列表信息
			MessageInfo m = new MessageInfo();
			m.setId(IdGen.uuid());
			m.setCommonId("");
			m.setBillType(Constant.MESSAGE_BILL_TYPE_RECP);
			m.setBusinessId(recpFlow.getId());
			m.setProcInsId(recpFlowDao.get(recpFlow.getId()).getProcInsId());
			m.setReadStatus("1");
			m.setSendMessage(messages);
			m.setRecPersion(u.getId());
			m.setCreateTime(new Date());
			int re = messageInfoDao.insert(m);
			if(re != 1 ){
				logger.error("*********保存消息列表信息失败*********");
			}else{
				messageHandler.sendMessageToUser(u.getMobile());
			}
			//发送短信
			appMessageService.sendSms(new SmsMessageRequest(recpFlow.getApplyPerCode(),messages,936,Constants.Type.RECEIVE.getKey()));
			//发送邮件
			appMessageService.sendEmail(new EmailMessageRequest(recpFlow.getApplyPerCode(),"(管理助手)您有单据待审批",messages, Constants.Type.RECEIVE.getKey()));
		}else{
			MesAppMessageRequest req = new MesAppMessageRequest();
			req.setFromCode(user.getMobile());
			req.setData("接待申请");//内容
			if("yes".equals(recpFlow.getAct().getFlag())){
				req.setToCode(e.getAssignee());
				req.setNoticeItem("您有单据待审批");//主题
				String messages = "您有单据待审批:"+recpFlow.getApplyPerName()+"的接待申请单据待审批，流程编号为:"+recpFlow.getProcCode() + "消息时间：" + DateUtils.format(new Date(),DateUtils.YYYY_MM_DD_HH_MM_SS);
				req.setNoticeContentIos(messages);//ios消息内容
				req.setNoticeContentAndroid(messages);//android 消息内容
				User u = UserUtils.getByLoginName(e.getAssignee());
				//打印节点信息
				logger.info("【接待申请节点信息】{}","审批人loginName："+ e.getAssignee() +"，当前节点："+ e.getTaskDefKey());
				//发送短信
				appMessageService.sendSms(new SmsMessageRequest(e.getAssignee(),messages,938,Constants.Type.RECEIVE.getKey()));
				//发送邮件
				appMessageService.sendEmail(new EmailMessageRequest(e.getAssignee(),"(管理助手)您有单据待审批",messages, Constants.Type.RECEIVE.getKey()));
				//保存消息列表信息
				MessageInfo m = new MessageInfo();
				m.setId(IdGen.uuid());
				m.setCommonId("");
				m.setBillType(Constant.MESSAGE_BILL_TYPE_RECP);
				m.setBusinessId(recpFlow.getId());
				m.setProcInsId(recpFlowDao.get(recpFlow.getId()).getProcInsId());
				m.setReadStatus("1");
				m.setSendMessage(messages);
				m.setRecPersion(u.getId());
				m.setCreateTime(new Date());
				int re = messageInfoDao.insert(m);
				if(re != 1 ){
					logger.error("*********保存消息列表信息失败*********");
				}else{
					messageHandler.sendMessageToUser(u.getMobile());
				}


			}else{
				req.setToCode(recpFlow.getApplyPerCode());
				User u = UserUtils.getByLoginName(recpFlow.getApplyPerCode());
				req.setNoticeItem("您有单据被驳回");//主题
				String messages = "您有单据被驳回:您的接待申请单据被驳回，流程编号为"+recpFlow.getProcCode() + "消息时间：" + DateUtils.format(new Date(),DateUtils.YYYY_MM_DD_HH_MM_SS);
				req.setNoticeContentIos(messages);//ios消息内容
				req.setNoticeContentAndroid(messages);//android 消息内容
				//保存消息列表信息
				MessageInfo m = new MessageInfo();
				m.setId(IdGen.uuid());
				m.setCommonId("");
				m.setBillType(Constant.MESSAGE_BILL_TYPE_RECP);
				m.setBusinessId(recpFlow.getId());
				m.setProcInsId(recpFlowDao.get(recpFlow.getId()).getProcInsId());
				m.setReadStatus("1");
				m.setSendMessage(messages);
				m.setRecPersion(u.getId());
				m.setCreateTime(new Date());
				int re = messageInfoDao.insert(m);
				if(re != 1 ){
					logger.error("*********保存消息列表信息失败*********");
				}else{
					messageHandler.sendMessageToUser(u.getMobile());
				}
				//发送短信
				appMessageService.sendSms(new SmsMessageRequest(recpFlow.getApplyPerCode(),messages,937,Constants.Type.RECEIVE.getKey()));
				//发送邮件
				appMessageService.sendEmail(new EmailMessageRequest(recpFlow.getApplyPerCode(),"(管理助手)您有单据待审批",messages, Constants.Type.RECEIVE.getKey()));

			}
			appMessageService.sendAsync(req);

		}
	}
	
	/**
	 * 发起流程
	 */
	@Transactional(readOnly = false)
	public void recpStartWorkFlow(RecpFlow recpFlow,User user){
		Map<String, Object> vars = Maps.newHashMap();
		vars.put("pass", "1");
		Act act = new Act();
		
		if(StringUtils.isNotBlank(recpFlow.getProcInsId())){
			act.setProcInsId(recpFlow.getProcInsId()); //流程实例ID
			act = actTaskService.queryThisRunTaskId(act);
			actTaskService.complete(act.getTaskId(), recpFlow.getAct().getProcInsId(), "重新申请", recpFlow.getProcName(), vars);
		}else{
			// 启动流程
			String procInsd = actTaskService.startProcessForWechat(ActUtils.OA_RECP_FLOW[0], ActUtils.OA_RECP_FLOW[1], recpFlow.getId(), recpFlow.getProcName(),user);
			
			//自动跳过第一个环节
			act.setProcInsId(procInsd); //流程实例ID
			act = actTaskService.queryThisRunTaskId(act);
			act.setComment("发起申请");
			actTaskService.complete(act.getTaskId(), act.getProcInsId(), act.getComment(),recpFlow.getProcName(), vars);
		}
		
		recpFlow.setRecpStatus(Constant.expense_approve); //接单申请状态审批中
		recpFlowDao.updateRecpStatus(recpFlow);
	}
	/**
	 * 接待关联查询列表
	 * @param user
	 * @return
	 */
	public Page<QueryFlowRevenceResponse> queryRecpFlowRevencelist(User user, QueryFlowRevenceRequest queryFlowRevenceRequest) {
		RecpFlow rec = new RecpFlow();
		rec.setRecpStatus(Constant.expense_approve_end);
		rec.setProcName(queryFlowRevenceRequest.getProcName());
		rec.setProjectName(queryFlowRevenceRequest.getProjectName());
		if(queryFlowRevenceRequest.getBeginApplyTime() != 0l){
			rec.setBeginApplyTime(DateUtils.parseDateFormUnix(queryFlowRevenceRequest.getBeginApplyTime(),DateUtils.YYYY_MM_DD_HH_MM_SS));
		}
		if(queryFlowRevenceRequest.getEndApplyTime() != 0l){
			rec.setEndApplyTime(DateUtils.parseDateFormUnix(queryFlowRevenceRequest.getEndApplyTime(),DateUtils.YYYY_MM_DD_HH_MM_SS));
		}
		rec.setCreateBy(user);
		//过滤掉已被接待报销关联的接待申请
		rec.setRelated(1);
		Page<RecpFlow> page = recpFlowService.findPage(new Page<RecpFlow>(queryFlowRevenceRequest.getPageNo(), queryFlowRevenceRequest.getPageSize()),rec);
		Page<QueryFlowRevenceResponse> newPage = new Page<QueryFlowRevenceResponse>();
		newPage.setPageNo(page.getPageNo());
		newPage.setPageNo(page.getPageSize());
		newPage.setCount(page.getCount());
		for (RecpFlow r : page.getList()) {
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
			rf.setBudgetTotal(r.getBudgetTotal());
			rf.setStatus(r.getRecpStatus());
			rf.setStatusValue(r.getRecpStatusValue());
			newPage.getList().add(rf);
		}
		return newPage;
	}
	
	public List<RecpFlowResponse> recpFlowRevencelist(User user) {
		RecpFlow rec = new RecpFlow();
		rec.setRecpStatus(Constant.expense_approve_end);
		rec.setCreateBy(user);
		List<RecpFlow> rfr = recpFlowService.findList(rec);
		List<RecpFlowResponse> list = Lists.newArrayList();
		for (RecpFlow r : rfr) {
			RecpFlowResponse rf = new RecpFlowResponse();
			rf.setProcInsId(r.getProcInsId());
			rf.setProcCode(r.getProcCode());
			rf.setProcName(r.getProcName());
			rf.setApplyPerCode(r.getApplyPerCode());
			rf.setApplyPerName(r.getApplyPerName());
			rf.setApplyTime(DateUtils.formatDate(r.getApplyTime(), DateUtils.YYYY_MM_DD));
			rf.setRecpTheme(r.getRecpTheme());
			rf.setProjectId(r.getProjectId());
			rf.setProjectName(r.getProjectName());
			rf.setProjectPersonel(r.getProjectPersonel());
			rf.setRecpNum(r.getRecpNum());
			rf.setRecpTime(r.getRecpTime().getTime());
			rf.setRecpParticNum(r.getRecpParticNum());
			rf.setBudgetTotal(r.getBudgetTotal());
			rf.setRecpStatus(r.getRecpStatus());
			rf.setRecpStatusValue(r.getRecpStatusValue());
			rf.setEmployees(r.getEmployees());
			list.add(rf);
		}
		return list;
	}
	/**
	 * 修改已读状态
	 * @param recpFlow
	 * @param curUser
	 */
	@Transactional(readOnly = false)
	public void updateReadStatus(RecpFlow recpFlow,User curUser){
		//修改已读状态
		MessageInfo mess = new MessageInfo();
		mess.setRecPersion(curUser.getId());
		mess.setBusinessId(recpFlow.getId());
		messageInfoDao.updateReadStatus(mess);
	}
	/**
	 * 接待申请单据明细(主表数据)
	 * @param recpFlowResponse
	 * @param recpFlow
	 * @param curUser
	 * @return
	 */
	public RecpFlowResponse recpFlowDetail(RecpFlowResponse recpFlowResponse,RecpFlow recpFlow,User curUser){
		logger.info("=======recpFlowDetail recpFlow============="+recpFlow.toString());
		recpFlowResponse = new RecpFlowResponse(recpFlow);
		//查询申请人所属岗位
		User u = UserUtils.getByLoginName(recpFlow.getApplyPerCode());
		PostInfo postInfo = postInfoDao.get(u.getPostIds());
		recpFlowResponse.setPostName(postInfo.getPostName());
		//查询陪客人员信息
		if(StringUtils.isNotBlank(recpFlow.getProcCode())){
			RecpParams recpParams = new RecpParams();
			recpParams.setProcCode(recpFlow.getProcCode()); //流程编号
			List<RecpParams> recpParamsList = recpParamsService.findList(recpParams);
			if(recpParamsList != null && recpParamsList.size() > 0){
				String[] recpParticPersonels = new String[recpParamsList.size()];
				String[] recpParticPersonelsName = new String[recpParamsList.size()];
				for(int i = 0;i < recpParamsList.size();i++){
					recpParticPersonels[i] = recpParamsList.get(i).getParamValue();
					recpParticPersonelsName[i] = recpParamsList.get(i).getParamName();
				}
				recpFlowResponse.setEmployees(recpParticPersonels);
				recpFlowResponse.setEmployeesName(recpParticPersonelsName);
			}
		}
		//比较是否是当前人
		if(StringUtils.equals(curUser.getLoginName(), recpFlowResponse.getApplyPerCode())){
			recpFlowResponse.setLocal(1);
		}
		//判断当前环节是否可以编辑页面
		recpFlowResponse.setIsDeit(0);
		if(Constant.expense_save.equals(recpFlow.getRecpStatus())){
			recpFlowResponse.setIsDeit(1);
			recpFlowResponse.setModify(Constant.DEFAULT_NOE_MODIFY);
		}

		//接待申请/接待报销通过后，隐藏该单据的“项目名称“，仅用固定文案：“接待对象：客户接待”替代
		if(Constant.expense_approve_end.equals(recpFlow.getRecpStatus())){
			recpFlowResponse.setProjectName("客户接待");
		}

		Act a = new Act();
		a.setProcInsId(recpFlow.getProcInsId());
		a = actTaskService.queryThisRunTaskId(a);
		if(a != null && Constant.DEFAULT_NOE_MODIFY.equals(a.getTaskDefKey()) && StringUtils.equals(curUser.getLoginName(), a.getAssignee())){
			recpFlowResponse.setIsDeit(1);
			recpFlowResponse.setModify(Constant.DEFAULT_NOE_MODIFY);
		}
		return recpFlowResponse;
	}

	/**
	 * 接待申请单据明细APP端(主表数据)
	 * @param recpFlowResponse
	 * @param recpFlow
	 * @param curUser
	 * @return
	 */
	public RecpFlowResponse recpFlowDetailApp(RecpFlowResponse recpFlowResponse,RecpFlow recpFlow,User curUser){
		logger.info("=======recpFlowDetailApp recpFlow============="+recpFlow.toString());
		recpFlowResponse = new RecpFlowResponse(recpFlow);
		//查询申请人所属岗位
		User u = UserUtils.getByLoginName(recpFlow.getApplyPerCode());
		PostInfo postInfo = postInfoDao.get(u.getPostIds());
		recpFlowResponse.setPostName(postInfo.getPostName());
		//查询陪客人员信息
		if(StringUtils.isNotBlank(recpFlow.getProcCode())){
			RecpParams recpParams = new RecpParams();
			recpParams.setProcCode(recpFlow.getProcCode()); //流程编号
			List<RecpParams> recpParamsList = recpParamsService.findListForApp(recpParams);
			if(recpParamsList != null && recpParamsList.size() > 0){
				String[] recpParticPersonels = new String[recpParamsList.size()];
				String[] recpParticPersonelsName = new String[recpParamsList.size()];
				for(int i = 0;i < recpParamsList.size();i++){
					recpParticPersonels[i] = recpParamsList.get(i).getParamValue();
					recpParticPersonelsName[i] = recpParamsList.get(i).getParamName();
				}
				recpFlowResponse.setEmployees(recpParticPersonels);
				recpFlowResponse.setEmployeesName(recpParticPersonelsName);
			}
		}
		//比较是否是当前人
		if(StringUtils.equals(curUser.getLoginName(), recpFlowResponse.getApplyPerCode())){
			recpFlowResponse.setLocal(1);
		}
		//判断当前环节是否可以编辑页面
		recpFlowResponse.setIsDeit(0);
		if(Constant.expense_save.equals(recpFlow.getRecpStatus())){
			recpFlowResponse.setIsDeit(1);
			recpFlowResponse.setModify(Constant.DEFAULT_NOE_MODIFY);
		}

		//接待申请/接待报销通过后，隐藏该单据的“项目名称“，仅用固定文案：“接待对象：客户接待”替代
		if(Constant.expense_approve_end.equals(recpFlow.getRecpStatus())){
			recpFlowResponse.setProjectName("客户接待");
		}

		Act a = new Act();
		a.setProcInsId(recpFlow.getProcInsId());
		a = actTaskService.queryThisRunTaskId(a);
		if(a != null && Constant.DEFAULT_NOE_MODIFY.equals(a.getTaskDefKey()) && StringUtils.equals(curUser.getLoginName(), a.getAssignee())){
			recpFlowResponse.setIsDeit(1);
			recpFlowResponse.setModify(Constant.DEFAULT_NOE_MODIFY);
		}
		return recpFlowResponse;
	}
	
	/**
	 * 接待申请明细(预算明细数据)
	 * @param detailsList
	 * @param recpFlow
	 * @return
	 */
	public List<DemandManagementBudgetResponse> recpDemandBudgetDetail(List<DemandManagementBudgetResponse> detailsList,RecpFlow recpFlow,String clientType){
		//查询预算明细信息
		DemandManagementBudget query = new DemandManagementBudget();
		query.setProcCode(recpFlow.getProcCode());
		query.setUserCode(recpFlow.getApplyPerCode());
		List<DemandManagementBudget> demandManagementBudgetLists = demandManagementBudgetService.findList(query);
		List<DemandManagementBudget> demandManagementBudgetList = Lists.newArrayList();
		if(demandManagementBudgetLists!= null && demandManagementBudgetLists.size() > 0){
			for(DemandManagementBudget bud:demandManagementBudgetLists){
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
	 * 删除任务
	 * @param recpFlow
	 */
	@Transactional(readOnly = false)
	public void deleteRecpFlowInfo(RecpFlow recpFlow){
		if(Constant.expense_save.equals(recpFlow.getRecpStatus())){ //保存的单据进行物理删除
			DemandManagementBudget demandManagementBudget = new DemandManagementBudget();
			demandManagementBudget.setProcCode(recpFlow.getProcCode());
			demandManagementBudgetService.deleteForProcCode(demandManagementBudget); //删除接待申请明细信息
			recpFlowDao.deleteRecpFlowInfo(recpFlow); //删除接待申请信息
		}else{
			if(StringUtils.isNotBlank(recpFlow.getProcInsId())){
				//删除任务
				actTaskService.deleteRunTask(recpFlow.getProcInsId()); //删除运行任务
				actTaskService.deleteHisTask(recpFlow.getProcInsId()); //删除历史任务
			}
			//删除单据
			recpFlowDao.delete(recpFlow);
			//修改单据状态为已删除
			recpFlow.setRecpStatus(Constant.expense_delete); //接待申请状态被删除
			recpFlowDao.updateRecpStatus(recpFlow);
		}
	}
	
	/**
	 * 任务撤销
	 * @param recpFlow
	 */
	@Transactional(readOnly = false)
	public void repealTask(RecpFlow recpFlow,User user){
		
		/********判断当前节点的下一个节点任务是否已经完成*********/
		
		/**********流程跳转，发起撤销start********/
		Map<String, Object> vars = Maps.newHashMap();
		vars.put("pass", "1");
		
		//查询被撤销的节点信息
		Act act = new Act();
		act.setProcInsId(recpFlow.getAct().getProcInsId()); //流程实例ID
		act = actTaskService.queryThisRunTaskId(act); //查询当前任务信息
		
		//查询发起撤销节点key
		Act targetAct = new Act();
		if("0".equals(recpFlow.getAct().getTaskId())){
			targetAct.setTaskDefKey("modify");
		}else{
			targetAct.setTaskId(recpFlow.getAct().getTaskId());
			targetAct.setProcInsId(recpFlow.getAct().getProcInsId());
			targetAct = actTaskService.queryHisTask(targetAct);
		}
		
		String targetTaskDefinitionKey = targetAct.getTaskDefKey(); //发起撤销的节点key
		actTaskService.jumpTaskAndAddComent(recpFlow.getAct().getProcInsId(), act.getTaskId(),
				"[流程撤回]当前审核任务被"+user.getName()+"撤回", user.getLoginName(), targetTaskDefinitionKey, vars);
		/**********流程跳转，发起撤销end********/
	}
}
