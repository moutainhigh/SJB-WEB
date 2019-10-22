package sijibao.oa.jeesite.modules.intfz.service.expense;

import static com.sijibao.oa.modules.intfz.utils.Constant.EXPENSE_FLOW_APPLY;

import java.math.BigDecimal;
import java.rmi.ServerException;
import java.text.DecimalFormat;
import java.util.*;

import org.activiti.engine.FormService;
import org.activiti.engine.TaskService;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sijibao.base.common.provider.utils.IdGen;
import com.sijibao.oa.activiti.api.response.contract.ContractFlowNewResponse;
import com.sijibao.oa.activiti.api.service.ContractFlowNewService;
import com.sijibao.oa.common.config.Global;
import com.sijibao.oa.common.persistence.Page;
import com.sijibao.oa.common.service.BaseService;
import com.sijibao.oa.common.service.ServiceException;
import com.sijibao.oa.common.utils.DateUtils;
import com.sijibao.oa.common.utils.SpringContextHolder;
import com.sijibao.oa.message.api.request.MesAppMessageRequest;
import com.sijibao.oa.modules.act.entity.Act;
import com.sijibao.oa.modules.act.entity.TaskInfoEntity;
import com.sijibao.oa.modules.act.service.ActTaskService;
import com.sijibao.oa.modules.act.utils.ActUtils;
import com.sijibao.oa.modules.flow.dao.ExpenseAttachmentDao;
import com.sijibao.oa.modules.flow.dao.ExpenseDetailDao;
import com.sijibao.oa.modules.flow.dao.ExpenseFlowDao;
import com.sijibao.oa.modules.flow.dao.RecpParamsDao;
import com.sijibao.oa.modules.flow.entity.*;
import com.sijibao.oa.modules.flow.service.*;
import com.sijibao.oa.modules.flow.utils.FlowUtils;
import com.sijibao.oa.modules.intfz.request.common.CommonFlowHandleReq;
import com.sijibao.oa.modules.intfz.request.expense.ExpenseFlowDetailRequest;
import com.sijibao.oa.modules.intfz.request.expense.ExpenseFlowRequest;
import com.sijibao.oa.modules.intfz.request.intfzwebreq.BringRemitHandleRequest;
import com.sijibao.oa.modules.intfz.request.intfzwebreq.BringRemitSaveRequest;
import com.sijibao.oa.modules.intfz.request.intfzwebreq.WebAttachmentReq;
import com.sijibao.oa.modules.intfz.response.common.TaskResponse;
import com.sijibao.oa.modules.intfz.response.expense.*;
import com.sijibao.oa.modules.intfz.response.intfzwebresp.BringRemitResponse;
import com.sijibao.oa.modules.intfz.service.util.AppMessageService;
import com.sijibao.oa.modules.intfz.utils.Constant;
import com.sijibao.oa.modules.intfz.validator.IntfzValidateUtils;
import com.sijibao.oa.modules.intfz.websocket.MessageHandler;
import com.sijibao.oa.modules.oa.dao.MessageInfoDao;
import com.sijibao.oa.modules.oa.entity.*;
import com.sijibao.oa.modules.oa.service.DemandManagementService;
import com.sijibao.oa.modules.oa.service.ExpensesSubConfService;
import com.sijibao.oa.modules.oa.service.ExpensesSubInfoService;
import com.sijibao.oa.modules.oa.service.ProjectInfoService;
import com.sijibao.oa.modules.sys.dao.PostInfoDao;
import com.sijibao.oa.modules.sys.entity.Area;
import com.sijibao.oa.modules.sys.entity.Office;
import com.sijibao.oa.modules.sys.entity.PostInfo;
import com.sijibao.oa.modules.sys.entity.User;
import com.sijibao.oa.modules.sys.service.AreaService;
import com.sijibao.oa.modules.sys.service.OfficeService;
import com.sijibao.oa.modules.sys.service.SystemService;
import com.sijibao.oa.modules.sys.utils.DictUtils;
import com.sijibao.oa.modules.sys.utils.SubInfoUtils;
import com.sijibao.oa.modules.sys.utils.SysParamsUtils;
import com.sijibao.oa.modules.sys.utils.UserUtils;

/**
 * 微信报销流程服务层
 * @author Petter
 */
@Service
@Transactional(readOnly = true)
public class IntfzExpenseFlowService extends BaseService {
	@Autowired
	private ExpenseDetailDao expenseDetailDao;
	@Autowired
	private ExpenseFlowDao expenseFlowDao;
	@Autowired
	private ActTaskService actTaskService;
	@Autowired
	private ExpenseFlowService expenseFlowService;
	@Autowired
	private ExpenseAttachmentDao expenseAttachmentDao;
	@Autowired
	private ExpensesSubInfoService expensesSubInfoService;
	@Autowired
	private ExpensesSubConfService expensesSubConfService;
	@Autowired
	private ProjectInfoService projectInfoService;
	@Autowired
	private RecpParamsDao recpParamsDao;
	@Autowired
	private DemandManagementService demandManagementService;
	@Autowired
	private RecpFlowService recpFlowService;
	@Autowired
	private RecpParamsService recpParamsService;
	@Autowired
	private AreaService areaInfoService;
	@Autowired
	private ExpenseDetailService expenseDetailService;
	@Autowired
	private ExpenseAttachmentService expenseAttachmentService;
	@Autowired
	private TravelFlowService travelFlowService;
	@Autowired
	private PostInfoDao postInfoDao;
	@Autowired
	private ResourcesApplyService resourcesApplyService;
	@Autowired
	private ResourcesHandleFlowService resourcesHandleFlowService; 
	@Autowired
	private FlowProcOrgService flowProcOrgService;
	@Autowired
	private AppMessageService appMessageService;
	@Autowired
	private FormService formService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private MessageInfoDao messageInfoDao;
	@Autowired
    private OfficeService officeService;
	@Autowired
	private MessageHandler messageHandler;

	private ContractFlowNewService contractFlowNewService;
	public void init(){
		if(contractFlowNewService == null){
			this.contractFlowNewService = SpringContextHolder.getBean("contractFlowNewService");
		}
	}

	@Autowired
	private SystemService systemService;
	/**
	 * 发起报销申请
	 * @param expenseFlowRequest
	 * @throws ServerException 
	 */
	@Transactional(readOnly = false)
	public void expenseApplyService(ExpenseFlowRequest expenseFlowRequest, User user,String clientType){
		try {
			//遍历详情，查询科目字段是否停用
			if(expenseFlowRequest.getExpenseDetail() != null && expenseFlowRequest.getExpenseDetail().size() > 0){
				List<String> ids = Lists.newArrayList();
				for(ExpenseFlowDetailRequest det :expenseFlowRequest.getExpenseDetail()){
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
			IntfzValidateUtils.valid(expenseFlowRequest);
			/**********报销流程业务处理start*******/
			ExpenseFlow expenseFlow = new ExpenseFlow();
			expenseFlow.setProducSide(expenseFlowRequest.getProducSide());
			expenseFlow.setApplyPerCode(user.getLoginName()); //申请人编号
			expenseFlow.setApplyPerName(user.getName()); //申请人名称
			if(StringUtils.isNotBlank(expenseFlowRequest.getApplyTime())){
				expenseFlow.setApplyTime(DateUtils.parse(expenseFlowRequest.getApplyTime(), DateUtils.YYYY_MM_DD));//申请时间
			}else{
				expenseFlow.setApplyTime(new Date());//申请时间
			}
            Office costOffice = officeService.get(expenseFlowRequest.getCostCenterId());
			if(Constant.OFFICE_DISABLE.equals(costOffice.getUseable())||"1".equals(costOffice.getDelFlag())){
				throw new ServiceException("成本中心所属机构已停用，无法提交!");
			}
            expenseFlow.setCostCenterId(costOffice.getId()); //成本中心部门ID
            expenseFlow.setCostCenterName(costOffice.getName()); //成本中心部门名称
            expenseFlow.setOffice(user.getOffice()); //申请人所属部门
            if(Constant.OA_EXPENSE_TYPE_ONE.equals(expenseFlowRequest.getApplyType())){//普通报销
				ProjectInfo projectInfo = new ProjectInfo();
				expenseFlow.setProjectId(expenseFlowRequest.getProjectId()); //项目ID
				projectInfo = projectInfoService.get(expenseFlowRequest.getProjectId());
				if(projectInfo != null){
					expenseFlow.setProjectName(projectInfo.getProjectName());
					if(projectInfo.getProjectLeader()!=null){
						expenseFlow.setProjectPersonel(projectInfo.getProjectLeader().getName()); //项目负责人
					}
				}
			}else if(Constant.OA_EXPENSE_TYPE_TWO.equals(expenseFlowRequest.getApplyType())){//接待报销
				RecpFlow recpFlow = recpFlowService.getByProcCode(expenseFlowRequest.getRelationTheme());
				if(recpFlow != null){
					//校验该接待申请是否已经被关联过
					if(recpFlow.getRelated() == 1){
						throw new ServiceException("该接待申请已经被关联过，无法提交!");
					}

					expenseFlow.setProjectId(recpFlow.getProjectId()); //项目ID
					expenseFlow.setProjectName(recpFlow.getProjectName()); //项目名称
					expenseFlow.setProjectPersonel(recpFlow.getProjectPersonel()); //项目负责人
					expenseFlow.setRecpProcName(recpFlow.getProcName()); //接待申请流程名称

                    //更新所关联的接待申请  状态改为已关联
					recpFlow.setRelated(1);
					recpFlowService.updateRecp(recpFlow);
				}
            }else if(Constant.OA_EXPENSE_TYPE_THREE.equals(expenseFlowRequest.getApplyType())){//出差报销
				TravelFlow travelFlow = travelFlowService.getByProcCode(expenseFlowRequest.getRelationTheme());
				if(travelFlow != null){
					//校验该出差申请已经被关联过的次数，判断是否已达上限
					int typeCount = 0;//“出差申请”能被关联的次数为所选“出差报销分类”的数量
					if(StringUtils.isNotBlank(travelFlow.getTravelExpenseTypes())){
						typeCount = travelFlow.getTravelExpenseTypes().split(",").length;
					}
					if(travelFlow.getIncidenceTimes() == typeCount){
						throw new ServiceException("该出差申请被关联次数已达上限，无法提交!");
					}

					//更新所关联的出差申请 被关联的次数
					travelFlow.setIncidenceTimes(travelFlow.getIncidenceTimes()+1);
					travelFlowService.save(travelFlow);

					expenseFlow.setProjectId(travelFlow.getProjectId()); //项目ID
					expenseFlow.setProjectName(travelFlow.getProjectName()); //项目名称
					expenseFlow.setProjectPersonel(travelFlow.getProjectPersonel()); //项目负责人
					expenseFlow.setRecpProcName(travelFlow.getProcName()); //接待申请流程名称
				}
			}
			expenseFlow.setApplyType(expenseFlowRequest.getApplyType()); //报销类型
			expenseFlow.setRemarks(expenseFlowRequest.getRemarks()); //备注
			expenseFlow.setExpenseStatus(Constant.expense_save); //报销状态保存
			expenseFlow.setTaxCityCode(expenseFlowRequest.getTaxCity()); //发票归属城市
			expenseFlow.setRecpProcCode(expenseFlowRequest.getRelationTheme()); //接待申请流程编号
			
			if(expenseFlowRequest.getEmployees() != null){
				expenseFlow.setRecpParticNum(expenseFlowRequest.getEmployees().length);
			}
			String flowCode = "";
			BigDecimal totalAmount = new BigDecimal(0); 
			int billNum = 0;
			DecimalFormat df = new DecimalFormat("0.00");
			logger.info("detailInfo:"+expenseFlowRequest.getExpenseDetail());
			List<ExpenseFlowDetailRequest> expenseDetailRequestList = expenseFlowRequest.getExpenseDetail();
			if(expenseDetailRequestList == null || expenseDetailRequestList.size() == 0){
				throw new ServiceException("请填写报销明细信息!");
			}
			if(expenseDetailRequestList.size() > 30){
				throw new ServiceException("当前单据明细不能超过30条");
			}
			if(expenseFlowRequest.getExpenseDetail() != null){  //判断明细是否为空
				for(ExpenseFlowDetailRequest expenseDetailRequest:expenseDetailRequestList){
				    if(expenseDetailRequest.getExpenseAmt() != null){
                        totalAmount = totalAmount.add(expenseDetailRequest.getExpenseAmt());
                    }
					billNum += expenseDetailRequest.getBillNum();
				}
				expenseFlow.setExpenseTotal(new BigDecimal(df.format(totalAmount))); //费用合计
				expenseFlow.setBillNum(Integer.toString(billNum)); //票据数量
			}
			if(totalAmount.compareTo(BigDecimal.ZERO) < 1){
				throw new ServiceException("当前报销金额不能小于等于0，禁止提交!");
			}
            //接待客户情况
			if(expenseFlowRequest.getCustomerSituation() != null){
                expenseFlow.setCustomerSituation(expenseFlowRequest.getCustomerSituation());
            }else {
			    expenseFlow.setCustomerSituation("");
            }
            if(expenseFlow.getRemarks() == null){
                expenseFlow.setRemarks("");
            }
			String procInsId;

			/**发起申请*/
			if (StringUtils.isBlank(expenseFlowRequest.getProcInsId())){
				flowCode = FlowUtils.genFlowCode();
				expenseFlow.setProcCode(flowCode);
				String title = user.getName()+"_报销_"+DateUtils.formatDate(expenseFlow.getApplyTime(), "yyyyMMdd")+"_"+flowCode;
				expenseFlow.setProcName(title);
				expenseFlow.setExpenseStatus(Constant.expense_approve); //报销状态审批中
				expenseFlow.preInsertForWeChart(user);
				expenseFlowDao.insert(expenseFlow);

				String procFlowCode;//流程标识
//              List<FlowProcOrg> flowProcOrgList = flowProcOrgService.findProcOrgForBusOrgId(expenseFlowRequest.getCostOfficeId(),Constant.EXPENSE_FLOW_APPLY);
//				if(flowProcOrgList != null && flowProcOrgList.size() == 1){
//					procFlowCode = flowProcOrgList.get(0).getProcKey();
//				}else if(flowProcOrgList != null && flowProcOrgList.size() > 1){
//					throw new ServiceException("当前单据未找到相关流程信息，无法提交!");
//				}else{
					procFlowCode = ActUtils.OA_EXPENSE_FLOW[0];
//				}

				// 启动流程
				procInsId = actTaskService.startProcessForWechat(procFlowCode, ActUtils.OA_EXPENSE_FLOW[1], expenseFlow.getId(), title,user);
				
				//自动跳过第一个环节
				Map<String, Object> vars = Maps.newHashMap();
				vars.put("pass", "1");
				Act act = new Act();
				act.setProcInsId(procInsId); //流程实例ID
				act = actTaskService.queryThisRunTaskId(act);
				act.setComment("发起申请");
				actTaskService.complete(act.getTaskId(), act.getProcInsId(), act.getComment(),title, vars);

//              //相同环节自动跳过
//              actTaskService.completeAutoIdenPersonel(act.getProcInsId(), "", vars, expenseFlow.getApplyPerCode());
//              //审批人员为空自动跳过
//              actTaskService.completeAutoEmptyPersonsel(act.getProcInsId(), "", vars);

                //非最后一个审批节点，审批人员为空自动跳过，连续两个审批人为同一个审批人时，第二个审批节点跳过（整个流程适用）
                actTaskService.completeAutoEmptyAndIdenPersonel(act.getProcInsId(), "", vars, expenseFlow.getApplyPerCode());

                //非最后一个审批节点，审批人与申请人相同自动跳过（放在单个节点进行判断）
                skipTask(act.getProcInsId(),vars,expenseFlow);

			/**被驳回后，重新编辑申请直接跳转流程*/
			}else{
				//查询当前任务ID start
				Act act = new Act();
				act.setAssignee(user.getLoginName()); //当前登录用户
				act.setProcInsId(expenseFlowRequest.getProcInsId()); //流程实例ID
				act = actTaskService.queryThisRunTaskId(act);
				if(act == null || StringUtils.isBlank(act.getTaskId())){
					throw new ServiceException("找不到当前流程任务信息，禁止提交！");
				}else{
					expenseFlow.getAct().setTaskId(act.getTaskId()); 
				}
				//查询当前任务ID end
				expenseFlow.setId(expenseFlowRequest.getId());
				expenseFlow.getAct().setProcInsId(expenseFlowRequest.getProcInsId()); //流程实例ID
				expenseFlow.getAct().setComment("重新申请");
				
				// 完成当前流程任务
				Map<String, Object> vars = Maps.newHashMap();
				vars.put("pass", "1"); //设置变量，1：true,0:false
                ExpenseFlow temp = expenseFlowDao.get(expenseFlow.getId());
				expenseFlow.setProcCode(temp.getProcCode());
				expenseFlow.setProcInsId(temp.getProcInsId());
				expenseFlow.setProcName(temp.getProcName());
				expenseFlow.setExpenseStatus(Constant.expense_approve); //报销状态审批中
				expenseFlow.preUpdateForWechart(user);
				expenseFlowDao.update(expenseFlow);
				flowCode = temp.getProcCode(); //获取流程编码
				
				actTaskService.complete(expenseFlow.getAct().getTaskId(), expenseFlow.getAct().getProcInsId(), expenseFlow.getAct().getComment(), temp.getProcName(), vars);

                ////相同环节自动跳过
                //actTaskService.completeAutoIdenPersonel(act.getProcInsId(), "", vars, expenseFlow.getApplyPerCode());
                ////审批人员为空自动跳过
                //actTaskService.completeAutoEmptyPersonsel(act.getProcInsId(), "", vars);

                //非最后一个审批节点，审批人为空自动跳过，连续两个审批人为同一个审批人时，第二个审批节点跳过（整个流程适用）
                actTaskService.completeAutoEmptyAndIdenPersonel(act.getProcInsId(), "", vars, expenseFlow.getApplyPerCode());

                //非最后一个审批节点，审批人与申请人相同自动跳过（放在单个节点进行判断）
                skipTask(act.getProcInsId(), vars, expenseFlow);

                procInsId = temp.getProcInsId();
			}
			/*****报销流程业务处理end*******/

			//消息推送
			MesAppMessageRequest req = new MesAppMessageRequest();
			String messages = "您有单据待审批:"+expenseFlow.getApplyPerName()+"的报销申请单据待审批，流程编号为:"+expenseFlow.getProcCode() + "消息时间：" + DateUtils.format(new Date(),DateUtils.YYYY_MM_DD_HH_MM_SS);
			req.setFromCode(user.getMobile());
			String toCode = taskService.createTaskQuery().processInstanceId(procInsId).active().singleResult().getAssignee();
			req.setToCode(toCode);
			req.setData("报销申请");//内容
			req.setNoticeItem("您有单据待审批");//主题
			req.setNoticeContentIos(messages);//ios消息内容
			req.setNoticeContentAndroid(messages);//android 消息内容
			appMessageService.sendAsync(req);
			//保存消息列表信息
			MessageInfo m = new MessageInfo();
			m.setId(IdGen.uuid());
			m.setCommonId("");
			m.setBillType(Constant.MESSAGE_BILL_TYPE_EXPENSE);
			m.setBusinessId(expenseFlow.getId());
			m.setProcInsId(expenseFlowDao.get(expenseFlow.getId()).getProcInsId());
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
//			//发送短信
//			String smsContent = "(管理助手)您有单据待审批:"+expenseFlow.getApplyPerName()+"的报销申请单据待审批，流程编号为:"+expenseFlow.getProcCode();
//			appMessageService.sendSms(new SmsMessageRequest(req.getToCode(),smsContent,938));
//
//			//发送邮件
//			appMessageService.sendEmail(new EmailMessageRequest(req.getToCode(),"(管理助手)您有单据待审批",smsContent));

			//保存陪客人数信息
			if(expenseFlowRequest.getEmployees() != null){
				//保存陪客人员明细信息
				recpParamsDao.deleteForProcCode(flowCode);
				for(String employee:expenseFlowRequest.getEmployees()){
					RecpParams recpParams = new RecpParams();
					recpParams.setProcCode(flowCode); //流程编号
					recpParams.setParamType(Constant.RECP_PARAM_TYPE_02); //类型
					User u = UserUtils.getByLoginName(employee);
					recpParams.setParamName(u.getName()); //人员名称
					recpParams.setParamValue(employee); //人员信息
					recpParams.preInsert();
					recpParamsDao.insert(recpParams);
				}
			}
			
			/*****报销明细业务处理start*****/
			expenseDetailDao.deleteByProcCode(flowCode); //删除之前的明细数据，再重新保存
			List<ExpenseDetail> detailList = Lists.newArrayList();
			
			//删除之前的附件信息
			ExpenseAttachment e = new ExpenseAttachment();
			e.setExpenseCode(flowCode);
			expenseAttachmentDao.deleteForExpenseCode(e);
			
			for(int i = 0;i < expenseDetailRequestList.size();i++){
				ExpenseFlowDetailRequest expenseDetailRequest = expenseDetailRequestList.get(i);
				if(expenseDetailRequest.getExpenseAmt() != null){
					IntfzValidateUtils.valid(expenseDetailRequest);
					ExpenseDetail expenseDetail = new ExpenseDetail();	
					expenseDetail.setProcCode(flowCode); //流程编号
					expenseDetail.setStartDate(DateUtils.parseDateFormUnix(expenseDetailRequest.getStartDate(), DateUtils.YYYY_MM_DD)); //开始日期
					if(expenseDetailRequest.getStartPoint() != null && expenseDetailRequest.getStartPoint().length > 1){
						expenseDetail.setStartPoint(expenseDetailRequest.getStartPoint()[1]); //起点
					}else{
						expenseDetail.setStartPoint(expenseDetailRequest.getStartPoint()[0]); //起点
					}
					expenseDetail.setEndDate(DateUtils.parseDateFormUnix(expenseDetailRequest.getEndDate(), DateUtils.YYYY_MM_DD)); //结束日期
					if(expenseDetailRequest.getEndPoint() != null && expenseDetailRequest.getEndPoint().length > 1){
						expenseDetail.setEndPoint(expenseDetailRequest.getEndPoint()[1]); //终点
					}else{
						expenseDetail.setEndPoint(expenseDetailRequest.getEndPoint()[0]); //终点
					}
					this.setExpenseDetailInfo(expenseDetailRequest, expenseDetail, clientType); //设置科目
					expenseDetail.setPersonNum(Integer.toString(expenseDetailRequest.getPersonNum())); //人数
					//计算天数
                    String dayNum = null;
                    if(com.sijibao.oa.common.utils.StringUtils.isNotBlank(expenseDetailRequest.getSecondSub())){
						dayNum = SubInfoUtils.getDayCalculation(DateUtils.parseDate(expenseDetailRequest.getStartDate()),DateUtils.parseDate(expenseDetailRequest.getEndDate()),expenseDetail.getSecondSub());
                    }else{
						dayNum = SubInfoUtils.getDayCalculation(DateUtils.parseDate(expenseDetailRequest.getStartDate()),DateUtils.parseDate(expenseDetailRequest.getEndDate()),expenseDetail.getFirstSub());
                    }
					expenseDetail.setDayNum(dayNum); //天数
					expenseDetail.setBillNum(Integer.toString(expenseDetailRequest.getBillNum())); //单据数量
					expenseDetail.setExpenseAmt(expenseDetailRequest.getExpenseAmt()); //报销金额
					expenseDetail.setRemarks(expenseDetailRequest.getRemarks()); //备注
					expenseDetail.setRowNum(i);
					expenseDetail.preInsertForWeChart(user);
					expenseDetailDao.insert(expenseDetail); //保存报销明细
					expenseDetail.setExpenseFlow(expenseFlow);
					detailList.add(expenseDetail);
					//保存科目明细附件
					if(expenseDetailRequest.getSubConfList() != null && expenseDetailRequest.getSubConfList().size() > 0){
						for(int j = 0;j < expenseDetailRequest.getSubConfList().size();j++){
							ExpenseAttachment expenseAttachment = new ExpenseAttachment();
							expenseAttachment.setExpenseCode(flowCode);
							expenseAttachment.setFileName("图片"+(j+1));
							expenseAttachment.setFileType("0");
							expenseAttachment.setExpenseDetailId(expenseDetail.getId());
							expenseAttachment.setSubImgDes(expenseDetailRequest.getSubConfList().get(j).getConfDesc());
							expenseAttachment.setSubImgConfId(expenseDetailRequest.getSubConfList().get(j).getId());
							expenseAttachment.setDetailLineNumber(i);
							expenseAttachment.setExpenseAttachmentUrl(expenseDetailRequest.getSubConfList().get(j).getUrl());
							expenseAttachment.setSubCode(expenseDetail.getSecondSub());
							expenseAttachment.preInsert();
							expenseAttachmentDao.insert(expenseAttachment);
						}
					}
				}
			}
			/*****报销明细业务处理end*****/
			
			/**************保存附件信息start****************/
			if(expenseFlowRequest.getExpenseAttachment() != null){
				//保存新的附件
				for(int i = 0 ;i<expenseFlowRequest.getExpenseAttachment().length;i++){
					ExpenseAttachment expenseAttachment = new ExpenseAttachment();
					expenseAttachment.setExpenseCode(flowCode);
					expenseAttachment.setFileName("附件"+(i+1));
					expenseAttachment.setFileType("0");
					expenseAttachment.setExpenseAttachmentUrl(expenseFlowRequest.getExpenseAttachment()[i]);
					expenseAttachment.preInsert();
					expenseAttachmentDao.insert(expenseAttachment);
				}
			}
			//web端添加附件名称
			if(expenseFlowRequest.getExpenseAttachmentWeb() != null && expenseFlowRequest.getExpenseAttachmentWeb().size() > 0){
				for(WebAttachmentReq w:expenseFlowRequest.getExpenseAttachmentWeb()){
					ExpenseAttachment expenseAttachment = new ExpenseAttachment();
					expenseAttachment.setExpenseCode(flowCode);
					expenseAttachment.setFileName(w.getName());
					expenseAttachment.setFileType("0");
					expenseAttachment.setExpenseAttachmentUrl(w.getUrl());
					expenseAttachment.preInsert();
					expenseAttachmentDao.insert(expenseAttachment);
				}
			}
			/**************保存附件信息end****************/
			
			/************费控标准校验start***********/
			Map<String,Object> resultMap = expenseFlowService.checkExpenseStandard(expenseFlow);
			logger.info("************费控标准校验start***********");
			if(!(boolean) resultMap.get("checkFlag")){
                logger.info("************费控标准校验end***********");
				throw new ServiceException(resultMap.get("message").toString());
			}
            logger.info("************费控标准校验start***********");
			/************费控标准校验end***********/
		} catch (ServerException e) {
			throw new ServiceException(e.getMessage());
		}
	}
	
	/**
	 * 设置预算明细信息
	 * @param clientType
	 */
	public void setExpenseDetailInfo(ExpenseFlowDetailRequest expenseDetailRequest,ExpenseDetail expenseDetail,String clientType){
		if(Constant.CLIENT_TYPE_APP.equals(clientType)){ //APP端接口
			if(expenseDetailRequest.getSubject() != null){
				expenseDetail.setFirstSub(expenseDetailRequest.getSubject()[0]); //一级科目
			}
			if(expenseDetailRequest.getSubject() != null && expenseDetailRequest.getSubject().length > 1
					&& !expenseDetailRequest.getSubject()[1].startsWith("no_")){
				expenseDetail.setSecondSub(expenseDetailRequest.getSubject()[1]); //二级科目
			}
		}else{
			expenseDetail.setFirstSub(expenseDetailRequest.getFirstSub()); //一级科目
			expenseDetail.setSecondSub(expenseDetailRequest.getSecondSub()); //二级科目
		}
	}
	
	
	
	/**
	 * 发起流程（废弃！！！）
	 */
	@Transactional(readOnly = false)
	public void startWorkFlow(ExpenseFlow expenseFlow,User user){
		
		Act act = new Act();
		Map<String, Object> vars = Maps.newHashMap();
		vars.put("pass", "1"); //设置变量，1：true,0:false
		if(StringUtils.isNotBlank(expenseFlow.getProcInsId())){
			act.setProcInsId(expenseFlow.getProcInsId()); //流程实例ID
			act = actTaskService.queryThisRunTaskId(act);
			actTaskService.complete(act.getTaskId(), expenseFlow.getAct().getProcInsId(), "重新申请", expenseFlow.getProcName(), vars);
		}else{
			// 启动流程
			String procInsd = actTaskService.startProcessForWechat(ActUtils.OA_EXPENSE_FLOW[0], ActUtils.OA_EXPENSE_FLOW[1], expenseFlow.getId(), expenseFlow.getProcName(),user);

			//自动跳过第一个环节
			act.setProcInsId(procInsd); //流程实例ID
			act = actTaskService.queryThisRunTaskId(act);
			act.setComment("发起申请");
			actTaskService.complete(act.getTaskId(), act.getProcInsId(), act.getComment(),expenseFlow.getProcName(), vars);
		}
        //非最后一个审批节点，相同环节自动跳过
        actTaskService.completeAutoIdenPersonel(act.getProcInsId(), "", vars, expenseFlow.getApplyPerCode());

		expenseFlow.setExpenseStatus(Constant.expense_approve); //报销状态审批中
		expenseFlowDao.updateExpenseStatus(expenseFlow);

		/************控制标准校验start***********/
		Map<String,Object> resultMap = expenseFlowService.checkExpenseStandard(expenseFlow);
		if(!(boolean) resultMap.get("checkFlag")){
			throw new ServiceException(resultMap.get("message").toString());
		}
		/************控制标准校验***********/
	}
	
	/**
	 * 保存报销单据
	 * @param expenseFlowRequest
	 */
	@Transactional(readOnly = false)
	public void saveExpenseService(ExpenseFlowRequest expenseFlowRequest,User user,String clientType)throws ServiceException{
		/**********报销保存业务处理start*******/
		ExpenseFlow expenseFlow = new ExpenseFlow();
		expenseFlow.setApplyPerCode(user.getLoginName()); //申请人编号
		expenseFlow.setApplyPerName(user.getName()); //申请人名称

		if(StringUtils.isNotBlank(expenseFlowRequest.getApplyTime())){
			expenseFlow.setApplyTime(DateUtils.parse(expenseFlowRequest.getApplyTime(), DateUtils.YYYY_MM_DD));//申请时间
		}else{
			expenseFlow.setApplyTime(new Date());//申请时间
		}
        Office costOffice = officeService.get(expenseFlowRequest.getCostCenterId());
        expenseFlow.setCostCenterId(costOffice.getId()); //成本中心部门ID
        expenseFlow.setCostCenterName(costOffice.getName()); //成本中心部门名称
		expenseFlow.setOffice(user.getOffice()); //所属部门
		if(Constant.OA_EXPENSE_TYPE_ONE.equals(expenseFlowRequest.getApplyType())){ //普通报销
			ProjectInfo projectInfo = new ProjectInfo();
			expenseFlow.setProjectId(expenseFlowRequest.getProjectId()); //项目ID
			projectInfo = projectInfoService.get(expenseFlowRequest.getProjectId());
			if(projectInfo != null){
				expenseFlow.setProjectName(projectInfo.getProjectName());
				expenseFlow.setProjectPersonel(projectInfo.getProjectLeader().getName()); //项目负责人
			}
		}else if(Constant.OA_EXPENSE_TYPE_TWO.equals(expenseFlowRequest.getApplyType())){  //接待报销
			RecpFlow recpFlow = recpFlowService.getByProcCode(expenseFlowRequest.getRelationTheme());
			if(recpFlow != null){
				expenseFlow.setProjectId(recpFlow.getProjectId()); //项目ID
				expenseFlow.setProjectName(recpFlow.getProjectName()); //项目名称
				expenseFlow.setProjectPersonel(recpFlow.getProjectPersonel()); //项目负责人
				expenseFlow.setRecpProcName(recpFlow.getProcName()); //关联主题流程名称
			}
        }else if(Constant.OA_EXPENSE_TYPE_THREE.equals(expenseFlowRequest.getApplyType())){ //出差报销
			TravelFlow travelFlow = travelFlowService.getByProcCode(expenseFlowRequest.getRelationTheme()); 
			if(travelFlow != null){
				expenseFlow.setProjectId(travelFlow.getProjectId()); //项目ID
				expenseFlow.setProjectName(travelFlow.getProjectName()); //项目名称
				expenseFlow.setProjectPersonel(travelFlow.getProjectPersonel()); //项目负责人
				expenseFlow.setRecpProcName(travelFlow.getProcName()); //关联主题流程名称
			}
		}
		expenseFlow.setApplyType(expenseFlowRequest.getApplyType()); //报销类型
		expenseFlow.setRemarks(expenseFlowRequest.getRemarks()); //备注
		expenseFlow.setExpenseStatus(Constant.expense_save); //报销状态保存
		expenseFlow.setTaxCityCode(expenseFlowRequest.getTaxCity()); //发票归属城市
		expenseFlow.setRecpProcCode(expenseFlowRequest.getRelationTheme()); //关联主题流程编号
		
		if(expenseFlowRequest.getEmployees() != null){
			expenseFlow.setRecpParticNum(expenseFlowRequest.getEmployees().length);
		}
		String flowCode = null;
		BigDecimal totalAmount = new BigDecimal(0); 
		
		int billNum = 0;
		DecimalFormat df = new DecimalFormat("0.00");
		logger.info("detailInfo:"+expenseFlowRequest.getExpenseDetail());
		List<ExpenseFlowDetailRequest> expenseDetailRequestList = expenseFlowRequest.getExpenseDetail();
		if(expenseDetailRequestList.size() > 30){
			throw new ServiceException("当前单据明细不能超过30条");
		}
		if(expenseFlowRequest.getExpenseDetail() != null && expenseFlowRequest.getExpenseDetail().size() > 0){  //判断明细是否为空
			for(ExpenseFlowDetailRequest expenseDetailRequest:expenseDetailRequestList){
			    if(expenseDetailRequest.getExpenseAmt() != null){
                    totalAmount = totalAmount.add(expenseDetailRequest.getExpenseAmt());
                }
				billNum += expenseDetailRequest.getBillNum();
			}
			expenseFlow.setExpenseTotal(new BigDecimal(df.format(totalAmount))); //费用合计
			expenseFlow.setBillNum(Integer.toString(billNum)); //票据数量
		}
        //接待客户情况
        if(expenseFlowRequest.getCustomerSituation() != null){
            expenseFlow.setCustomerSituation(expenseFlowRequest.getCustomerSituation());
        }else {
            expenseFlow.setCustomerSituation("");
        }
        if(expenseFlow.getRemarks() == null){
            expenseFlow.setRemarks("");
        }
		// 第一次保存
		if (StringUtils.isBlank(expenseFlowRequest.getId())){ //判断单据ID
			flowCode = FlowUtils.genFlowCode();
			expenseFlow.setProcCode(flowCode);
			String title = user.getName()+"_报销_"+DateUtils.formatDate(expenseFlow.getApplyTime(), "yyyyMMdd")+"_"+flowCode;
			expenseFlow.setProcName(title);
			expenseFlow.preInsertForWeChart(user);
			expenseFlowDao.insert(expenseFlow);
		}else{// 重新保存
			expenseFlow.setId(expenseFlowRequest.getId());
			ExpenseFlow temp = expenseFlowDao.get(expenseFlow.getId());			
			flowCode = temp.getProcCode(); //获取流程编码
			expenseFlow.setProcCode(flowCode);
			expenseFlow.setProcName(temp.getProcName());
			expenseFlow.setExpenseStatus(temp.getExpenseStatus());
			expenseFlow.setProcInsId(temp.getProcInsId());
			expenseFlow.preUpdateForWechart(user);
			expenseFlowDao.update(expenseFlow);
		}
		/*****报销保存业务处理end*******/
		//保存陪客人数信息
		if(expenseFlowRequest.getEmployees() != null){
			//保存陪客人员明细信息
			recpParamsDao.deleteForProcCode(flowCode);
			for(String employee:expenseFlowRequest.getEmployees()){
				RecpParams recpParams = new RecpParams();
				recpParams.setProcCode(flowCode); //流程编号
				recpParams.setParamType(Constant.RECP_PARAM_TYPE_02); //类型
				User u = UserUtils.getByLoginName(employee);
				recpParams.setParamName(u.getName()); //人员名称
				recpParams.setParamValue(employee); //人员信息
				recpParams.preInsert();
				recpParamsDao.insert(recpParams);
			}
		}
		/*****报销明细业务处理start*****/
		expenseDetailDao.deleteByProcCode(flowCode); //删除之前的明细数据，再重新保存
		//删除之前的附件信息
		ExpenseAttachment e = new ExpenseAttachment();
		e.setExpenseCode(flowCode);
		expenseAttachmentDao.deleteForExpenseCode(e);
		for(int i = 0;i < expenseDetailRequestList.size();i++){
			ExpenseFlowDetailRequest expenseDetailRequest = expenseDetailRequestList.get(i);
			ExpenseDetail expenseDetail = new ExpenseDetail();	
			expenseDetail.setProcCode(flowCode); //流程编号
			expenseDetail.setStartDate(DateUtils.parseDateFormUnix(expenseDetailRequest.getStartDate(), DateUtils.YYYY_MM_DD)); //开始日期
			if(expenseDetailRequest.getStartPoint() != null && expenseDetailRequest.getStartPoint().length > 1){
				expenseDetail.setStartPoint(expenseDetailRequest.getStartPoint()[1]); //起点
			}else if(expenseDetailRequest.getStartPoint() != null && expenseDetailRequest.getStartPoint().length > 0){
				expenseDetail.setStartPoint(expenseDetailRequest.getStartPoint()[0]); //起点
			}
			expenseDetail.setEndDate(DateUtils.parseDateFormUnix(expenseDetailRequest.getEndDate(), DateUtils.YYYY_MM_DD)); //结束日期
			if(expenseDetailRequest.getEndPoint() != null && expenseDetailRequest.getEndPoint().length > 1){
				expenseDetail.setEndPoint(expenseDetailRequest.getEndPoint()[1]); //终点
			}else if(expenseDetailRequest.getEndPoint() != null && expenseDetailRequest.getEndPoint().length > 0){
				expenseDetail.setEndPoint(expenseDetailRequest.getEndPoint()[0]); //终点
			}
			this.setExpenseDetailInfo(expenseDetailRequest, expenseDetail, clientType); //设置科目
			expenseDetail.setPersonNum(Integer.toString(expenseDetailRequest.getPersonNum())); //人数
			//天数
			//计算天数
			String dayNum = null;
			if(com.sijibao.oa.common.utils.StringUtils.isNotBlank(expenseDetailRequest.getSecondSub())){
				dayNum = SubInfoUtils.getDayCalculation(DateUtils.parseDate(expenseDetailRequest.getStartDate()),DateUtils.parseDate(expenseDetailRequest.getEndDate()),expenseDetail.getSecondSub());
			}else{
				dayNum = SubInfoUtils.getDayCalculation(DateUtils.parseDate(expenseDetailRequest.getStartDate()),DateUtils.parseDate(expenseDetailRequest.getEndDate()),expenseDetail.getFirstSub());
			}
			expenseDetail.setDayNum(dayNum); //天数
			expenseDetail.setBillNum(Integer.toString(expenseDetailRequest.getBillNum())); //单据数量
			expenseDetail.setExpenseAmt(expenseDetailRequest.getExpenseAmt()); //报销金额
			expenseDetail.setRemarks(expenseDetailRequest.getRemarks()); //备注
			expenseDetail.setRowNum(i);
			expenseDetail.preInsertForWeChart(user);
			expenseDetailDao.insert(expenseDetail); //保存报销明细
			//保存科目明细附件
			if(expenseDetailRequest.getSubConfList() != null && expenseDetailRequest.getSubConfList().size() > 0){
				for(int j = 0;j < expenseDetailRequest.getSubConfList().size();j++){
					ExpenseAttachment expenseAttachment = new ExpenseAttachment();
					expenseAttachment.setExpenseCode(flowCode);
					expenseAttachment.setFileName("图片"+(j+1));
					expenseAttachment.setFileType("0");
					expenseAttachment.setExpenseDetailId(expenseDetail.getId());
					expenseAttachment.setSubImgDes(expenseDetailRequest.getSubConfList().get(j).getConfDesc());
					expenseAttachment.setSubImgConfId(expenseDetailRequest.getSubConfList().get(j).getId());
					expenseAttachment.setDetailLineNumber(i);
					expenseAttachment.setExpenseAttachmentUrl(expenseDetailRequest.getSubConfList().get(j).getUrl());
					expenseAttachment.setSubCode(expenseDetail.getSecondSub());
					expenseAttachment.preInsert();
					expenseAttachmentDao.insert(expenseAttachment);
				}
			}
		}
		/*****报销明细业务处理end*****/
		
		/**************保存附件信息start****************/
		if(expenseFlowRequest.getExpenseAttachment() != null){
			//保存新的附件
			for(int i = 0 ;i<expenseFlowRequest.getExpenseAttachment().length;i++){
				ExpenseAttachment expenseAttachment = new ExpenseAttachment();
				expenseAttachment.setExpenseCode(flowCode);
				expenseAttachment.setFileName("图片"+(i+1));
				expenseAttachment.setFileType("0");
				expenseAttachment.setExpenseAttachmentUrl(expenseFlowRequest.getExpenseAttachment()[i]);
				expenseAttachment.preInsert();
				expenseAttachmentDao.insert(expenseAttachment);
			}
		}
		//web端添加附件名称
		if(expenseFlowRequest.getExpenseAttachmentWeb() != null && expenseFlowRequest.getExpenseAttachmentWeb().size() > 0){
			for(WebAttachmentReq w:expenseFlowRequest.getExpenseAttachmentWeb()){
				ExpenseAttachment expenseAttachment = new ExpenseAttachment();
				expenseAttachment.setExpenseCode(flowCode);
				expenseAttachment.setFileName(w.getName());
				expenseAttachment.setFileType("0");
				expenseAttachment.setExpenseAttachmentUrl(w.getUrl());
				expenseAttachment.preInsert();
				expenseAttachmentDao.insert(expenseAttachment);
			}
		}
		/**************保存附件信息end****************/
	}
	
	/**
	 * 保存报销明细数据
	 * @param flowCode
	 * @param expenseDetailRequestList
	 * @param clientType
	 * @param user
	 */
	public void saveExpenseDetailInfo(String flowCode,List<ExpenseFlowDetailRequest> expenseDetailRequestList,String clientType,User user){
		expenseDetailDao.deleteByProcCode(flowCode); //删除之前的明细数据，再重新保存
		//删除之前的附件信息
		ExpenseAttachment e = new ExpenseAttachment();
		e.setExpenseCode(flowCode);
		expenseAttachmentDao.deleteForExpenseCode(e);
		for(int i = 0;i < expenseDetailRequestList.size();i++){
			ExpenseFlowDetailRequest expenseDetailRequest = expenseDetailRequestList.get(i);
			ExpenseDetail expenseDetail = new ExpenseDetail();	
			expenseDetail.setProcCode(flowCode); //流程编号
			expenseDetail.setStartDate(DateUtils.parseDateFormUnix(expenseDetailRequest.getStartDate(), DateUtils.YYYY_MM_DD)); //开始日期
			if(expenseDetailRequest.getStartPoint() != null && expenseDetailRequest.getStartPoint().length > 1){
				expenseDetail.setStartPoint(expenseDetailRequest.getStartPoint()[1]); //起点
			}else if(expenseDetailRequest.getStartPoint() != null && expenseDetailRequest.getStartPoint().length > 0){
				expenseDetail.setStartPoint(expenseDetailRequest.getStartPoint()[0]); //起点
			}
			expenseDetail.setEndDate(DateUtils.parseDateFormUnix(expenseDetailRequest.getEndDate(), DateUtils.YYYY_MM_DD)); //结束日期
			if(expenseDetailRequest.getEndPoint() != null && expenseDetailRequest.getEndPoint().length > 1){
				expenseDetail.setEndPoint(expenseDetailRequest.getEndPoint()[1]); //终点
			}else if(expenseDetailRequest.getEndPoint() != null && expenseDetailRequest.getEndPoint().length > 0){
				expenseDetail.setEndPoint(expenseDetailRequest.getEndPoint()[0]); //终点
			}
			this.setExpenseDetailInfo(expenseDetailRequest, expenseDetail, clientType); //设置科目
			expenseDetail.setPersonNum(Integer.toString(expenseDetailRequest.getPersonNum())); //人数
			expenseDetail.setDayNum(expenseDetailRequest.getDayNum()); //天数
			expenseDetail.setBillNum(Integer.toString(expenseDetailRequest.getBillNum())); //单据数量
			expenseDetail.setExpenseAmt(expenseDetailRequest.getExpenseAmt()); //报销金额
			expenseDetail.setRemarks(expenseDetailRequest.getRemarks()); //备注
			expenseDetail.setRowNum(i);
			expenseDetail.preInsertForWeChart(user);
			expenseDetailDao.insert(expenseDetail); //保存报销明细
			//保存科目明细附件
			if(expenseDetailRequest.getSubConfList() != null && expenseDetailRequest.getSubConfList().size() > 0){
				for(int j = 0;j < expenseDetailRequest.getSubConfList().size();j++){
					ExpenseAttachment expenseAttachment = new ExpenseAttachment();
					expenseAttachment.setExpenseCode(flowCode);
					expenseAttachment.setFileName("图片"+(j+1));
					expenseAttachment.setFileType("0");
					expenseAttachment.setExpenseDetailId(expenseDetail.getId());
					expenseAttachment.setSubImgDes(expenseDetailRequest.getSubConfList().get(j).getConfDesc());
					expenseAttachment.setSubImgConfId(expenseDetailRequest.getSubConfList().get(j).getId());
					expenseAttachment.setDetailLineNumber(i);
					expenseAttachment.setExpenseAttachmentUrl(expenseDetailRequest.getSubConfList().get(j).getUrl());
					expenseAttachment.setSubCode(expenseDetail.getSecondSub());
					expenseAttachment.preInsert();
					expenseAttachmentDao.insert(expenseAttachment);
				}
			}
		}
	}
	
	
	/**
	 * 审核审批保存（同意或驳回）
	 */
	@Transactional(readOnly = false)
	public void completeTask(ExpenseFlow expenseFlow,User user) {
		// 设置审批意见
		expenseFlow.getAct().setComment(("yes".equals(expenseFlow.getAct().getFlag())?"[同意] ":"[驳回] ")+expenseFlow.getAct().getComment());
		String flag = expenseFlow.getAct().getFlag();
		// 提交流程任务
		Map<String, Object> vars = Maps.newHashMap();
		vars.put("pass", "yes".equals(expenseFlow.getAct().getFlag())? "1" : "0");
		
		if("no".equals(flag)){
			Act act = new Act();
			act.setProcInsId(expenseFlow.getAct().getProcInsId()); //流程实例ID
			act = actTaskService.queryThisRunTaskId(act); //查询当前任务信息
			
			//查询开始环节任务
			String targetTaskDefinitionKey = "modify";
			actTaskService.jumpTaskAndAddComent(expenseFlow.getAct().getProcInsId(),act.getTaskId(),expenseFlow.getAct().getComment(),user.getLoginName(),targetTaskDefinitionKey, vars);
			//判断流程是否退回并更新报销状态
			expenseFlow.setExpenseStatus(Constant.expense_back); //报销状态被退回
			expenseFlowDao.updateExpenseStatus(expenseFlow);

			// 驳回时，更新所关联的接待申请，状态改为未关联
			if(Constant.OA_EXPENSE_TYPE_TWO.equals(expenseFlow.getApplyType())) {
				RecpFlow recpFlow = recpFlowService.getByProcCode(expenseFlow.getRecpProcCode());
				if(null != recpFlow){
					recpFlow.setRelated(0);
					recpFlowService.updateRecp(recpFlow);
				}
			}

			// 驳回时，更新所关联的出差申请，被关联次数减1
			if(Constant.OA_EXPENSE_TYPE_THREE.equals(expenseFlow.getApplyType())){
				TravelFlow travelFlow = travelFlowService.getByProcCode(expenseFlow.getRecpProcCode());
				if(travelFlow != null){
					travelFlow.setIncidenceTimes(travelFlow.getIncidenceTimes() - 1);
					travelFlowService.save(travelFlow);
				}
			}

		}else{
			actTaskService.complete(expenseFlow.getAct().getTaskId(), expenseFlow.getAct().getProcInsId(), expenseFlow.getAct().getComment(), vars);

            ////审批人员为空自动跳过
            //actTaskService.completeAutoEmptyPersonsel(expenseFlow.getAct().getProcInsId(), "", vars);
            ////相同环节自动跳过
            //actTaskService.completeAutoIdenPersonel(expenseFlow.getAct().getProcInsId(), "", vars, expenseFlow.getApplyPerCode());

            //非最后一个审批节点，审批人为空自动跳过，连续两个审批人为同一个审批人时，第二个审批节点跳过（整个流程适用）
            actTaskService.completeAutoEmptyAndIdenPersonel(expenseFlow.getAct().getProcInsId(), "", vars, expenseFlow.getApplyPerCode());

            //非最后一个审批节点，审批人与申请人相同自动跳过（放在单个节点进行判断）
            skipTask(expenseFlow.getAct().getProcInsId(), vars, expenseFlow);
		}
		
		//判断流程是否结束并更新报销状态
		Act e = new Act();
		e.setProcInsId(expenseFlow.getAct().getProcInsId()); //流程实例ID
		e = actTaskService.queryThisRunTaskId(e);

		String smsContent = "";
		if(e == null){
			//流程已结束
			expenseFlow.setFlowFinishTime(new Date());
			expenseFlow.setExpenseStatus(Constant.expense_approve_end); //报销状态审批结束
			expenseFlowDao.updateExpenseStatusAndFlowFinshTime(expenseFlow);

			MesAppMessageRequest req = new MesAppMessageRequest();
			req.setFromCode(user.getMobile());
			String toCode = expenseFlow.getApplyPerCode();
			req.setToCode(toCode);
			req.setData("报销申请");//内容
			req.setNoticeItem("您有单据待审批已通过");//主题
			String messages = "您的报销申请单据审批已通过，流程编号为"+expenseFlow.getProcCode() + "消息时间：" + DateUtils.format(new Date(),DateUtils.YYYY_MM_DD_HH_MM_SS);
			req.setNoticeContentIos(messages);//ios消息内容
			req.setNoticeContentAndroid(messages);//android 消息内容
			appMessageService.sendAsync(req);
			//保存消息列表信息
			MessageInfo m = new MessageInfo();
			m.setId(IdGen.uuid());
			m.setCommonId("");
			m.setBillType(Constant.MESSAGE_BILL_TYPE_EXPENSE);
			m.setBusinessId(expenseFlow.getId());
			m.setProcInsId(expenseFlowDao.get(expenseFlow.getId()).getProcInsId());
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
//
//			//发送短信
//			smsContent = "(管理助手)您的报销申请单据审批已通过，流程编号为"+expenseFlow.getProcCode();
//			appMessageService.sendSms(new SmsMessageRequest(expenseFlow.getApplyPerCode(),smsContent,936));
//
//			//发送邮件
//			appMessageService.sendEmail(new EmailMessageRequest(expenseFlow.getApplyPerCode(),"(管理助手)您有单据待审批",smsContent));

		}else{
			MesAppMessageRequest req = new MesAppMessageRequest();
			req.setFromCode(user.getMobile());
			req.setData("报销申请");//内容
			if("yes".equals(expenseFlow.getAct().getFlag()) && StringUtils.isNotBlank(e.getAssignee())){
				String toCode = e.getAssignee();
				req.setToCode(e.getAssignee());
				req.setNoticeItem("您有单据待审批");//主题
				String messages = "您有单据待审批:"+expenseFlow.getApplyPerName()+"的报销申请单据待审批，流程编号为:"+expenseFlow.getProcCode() + "消息时间：" + DateUtils.format(new Date(),DateUtils.YYYY_MM_DD_HH_MM_SS);
				req.setNoticeContentIos(messages);//ios消息内容
				req.setNoticeContentAndroid(messages);//android 消息内容
				//保存消息列表信息
				MessageInfo m = new MessageInfo();
				m.setId(IdGen.uuid());
				m.setCommonId("");
				m.setBillType(Constant.MESSAGE_BILL_TYPE_EXPENSE);
				m.setBusinessId(expenseFlow.getId());
				m.setProcInsId(expenseFlowDao.get(expenseFlow.getId()).getProcInsId());
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
//
//				//发送短信
//				smsContent = "(管理助手)您有单据待审批:"+expenseFlow.getApplyPerName()+"的报销申请单据待审批，流程编号为:"+expenseFlow.getProcCode();
//				appMessageService.sendSms(new SmsMessageRequest(e.getAssignee(),smsContent,938));
//
//				//发送邮件
//				appMessageService.sendEmail(new EmailMessageRequest(e.getAssignee(),"(管理助手)您有单据待审批",smsContent));

			}else if(!"yes".equals(expenseFlow.getAct().getFlag())  && StringUtils.isNotBlank(e.getAssignee())){
				String toCode = expenseFlow.getApplyPerCode();
				req.setToCode(toCode);
				req.setNoticeItem("您有单据被驳回");//主题
				String messages = "您有单据被驳回:您的报销申请单据被驳回，流程编号为"+expenseFlow.getProcCode() + "消息时间：" + DateUtils.format(new Date(),DateUtils.YYYY_MM_DD_HH_MM_SS);
				req.setNoticeContentIos(messages);//ios消息内容
				req.setNoticeContentAndroid(messages);//android 消息内容
				//保存消息列表信息
				MessageInfo m = new MessageInfo();
				m.setId(IdGen.uuid());
				m.setCommonId("");
				m.setBillType(Constant.MESSAGE_BILL_TYPE_EXPENSE);
				m.setBusinessId(expenseFlow.getId());
				m.setProcInsId(expenseFlowDao.get(expenseFlow.getId()).getProcInsId());
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
//				//发送短信
//				smsContent = "(管理助手)您有单据被驳回:您的报销申请单据被驳回，流程编号为"+expenseFlow.getProcCode();
//				appMessageService.sendSms(new SmsMessageRequest(expenseFlow.getApplyPerCode(),smsContent,937));
//
//
//				//发送邮件
//				appMessageService.sendEmail(new EmailMessageRequest(expenseFlow.getApplyPerCode(),"(管理助手)您有单据被驳回",smsContent));
			}
			appMessageService.sendAsync(req);

		}
	}

	/**
	 * 如果非最后一个审批节点且执行者与申请人相同，则自动跳过  放在单个节点进行判断
	 * @param expenseFlow
	 * @param vars
	 */
	public void skipTask(String procInsId,Map<String, Object> vars,ExpenseFlow expenseFlow) {
		Task task = taskService.createTaskQuery().processInstanceId(procInsId).active().singleResult();
		if (null != task && !task.getTaskDefinitionKey().contains("last")) {
			List<FormProperty> list = formService.getTaskFormData(task.getId()).getFormProperties();

			if (list != null && list.size() > 0) {
				for (FormProperty formProperty : list) {
					System.out.println(formProperty.getId() + "		" + formProperty.getName() + "		" + formProperty.getValue());

					//是否跳过
					if ("isSkip".equals(formProperty.getId()) && "1".equals(formProperty.getValue())) {
						if (StringUtils.equals(task.getAssignee(), expenseFlow.getApplyPerCode())) { //如果执行者与申请人相同，则自动跳过
							String comment = "[同意]相同审批人员，自动审批跳过";
							actTaskService.complete(task.getId(), procInsId, comment, vars);
							actTaskService.completeAutoEmptyAndIdenPersonel(procInsId, "", vars, expenseFlow.getApplyPerCode());
						}
					}

					//费用合计<2000  跳过龚总
					if ("totalExpense".equals(formProperty.getId())) {
						String totalExpense = formProperty.getValue();
						BigDecimal bd = new BigDecimal(totalExpense);
						if (expenseFlow.getExpenseTotal().compareTo(bd) < 0) {
							String comment = "[同意]费用合计小于"+totalExpense+"，自动审批跳过";
							actTaskService.complete(task.getId(), procInsId, comment, vars);
							actTaskService.completeAutoEmptyAndIdenPersonel(procInsId, "", vars, expenseFlow.getApplyPerCode());
						}
					}
				}
			}

//			//费用合计<2000  跳过龚总
//			if ("13907157896".equals(task.getAssignee())) {
//				BigDecimal bd = new BigDecimal(2000);
//				if (expenseFlow.getExpenseTotal().compareTo(bd) < 0) {
//					String comment = "[同意]费用合计小于2000，自动审批跳过";
//					actTaskService.complete(task.getId(), procInsId, comment, vars);
//				}
//			}
		}
	}


	/**
	 * 查询科目信息
	 * @param expensesSubInfo
	 * @return
	 */
	public List<SubInfoResponse> getSubInfoList(ExpensesSubInfo expensesSubInfo) {
		List<ExpensesSubInfo> expensesSubInfolist = SubInfoUtils.findListNew(expensesSubInfo);
		List<SubInfoResponse> subInfolist = Lists.newArrayList();
		for (int i = 0; i < expensesSubInfolist.size(); i++) {
			ExpensesSubInfo esInfo = expensesSubInfolist.get(i);
			ExpensesSubConf query = new ExpensesSubConf();
			query.setSubCode(esInfo.getId());
			query.setSubName(esInfo.getSubName());
			if("1".equals(expensesSubInfo.getIsFirst())){
				subInfolist.add(new SubInfoResponse(esInfo.getId(), StringUtils.isBlank(esInfo.getpId())?"0":esInfo.getpId(),
						esInfo.getSubName(), DateUtils.formatDate(esInfo.getCreateTime(), DateUtils.YYYY_MM_DD_HH_MM_SS), null));
			}else{
				List<ExpensesSubConf> confList = SubInfoUtils.querySubConfList(query);
				if(confList == null || confList.size() <= 0){
					subInfolist.add(new SubInfoResponse(esInfo.getId(), StringUtils.isBlank(esInfo.getpId())?"0":esInfo.getpId(),
							esInfo.getSubName(), DateUtils.formatDate(esInfo.getCreateTime(), DateUtils.YYYY_MM_DD_HH_MM_SS), null));
				}else{
					List<SubConfResp> confRespList = Lists.newArrayList();
					for(ExpensesSubConf conf : confList){
						confRespList.add(new SubConfResp(conf.getConfType(),conf.getDictType(),conf.getConfDesc(),
								conf.getIsRequired(),conf.getSort(),conf.getId()));
					}
					subInfolist.add(new SubInfoResponse(esInfo.getId(), StringUtils.isBlank(esInfo.getpId())?"0":esInfo.getpId(),
							esInfo.getSubName(), DateUtils.formatDate(esInfo.getCreateTime(), DateUtils.YYYY_MM_DD_HH_MM_SS), confRespList));
				}
			}
		}
		return subInfolist;
	}
	
	public List<SubInfoResponseTree> getSubInfoListNew(ExpensesSubInfo expensesSubInfo) {
		expensesSubInfo.setIsFirst("1");
		List<ExpensesSubInfo> expensesSubInfolist = expensesSubInfoService.findListNew(expensesSubInfo);
		List<SubInfoResponseTree> subInfolist = Lists.newArrayList();
		for (int i = 0; i < expensesSubInfolist.size(); i++) {
			ExpensesSubInfo esInfo = expensesSubInfolist.get(i);
			
			SubInfoResponseTree parentSesponse = new SubInfoResponseTree();
			parentSesponse.setLabel(esInfo.getSubName());
			parentSesponse.setValue(esInfo.getId());
			List<SubInfoResponseTree> childSesponseList = Lists.newArrayList();
			//查询子级科目
			ExpensesSubInfo queryChildSub = new ExpensesSubInfo();
			queryChildSub.setParSubCode(esInfo.getSubCode());
			List<ExpensesSubInfo> childSubInfoList = expensesSubInfoService.findListNew(queryChildSub);
			
			for(ExpensesSubInfo childSubInfo:childSubInfoList){
				SubInfoResponseTree childSesponse = new SubInfoResponseTree();
				childSesponse.setLabel(childSubInfo.getSubName());
				childSesponse.setValue(childSubInfo.getId());
				//查询子级科目附件
				ExpensesSubConf query = new ExpensesSubConf();
				query.setSubCode(childSubInfo.getId());
				query.setSubName(childSubInfo.getSubName());
				List<ExpensesSubConf> confList = SubInfoUtils.querySubConfList(query);
				List<SubConfResp> confRespList = Lists.newArrayList();
				for(ExpensesSubConf conf : confList){
					confRespList.add(new SubConfResp(conf.getConfType(),conf.getDictType(),conf.getConfDesc(),
							conf.getIsRequired(),conf.getSort(),conf.getId()));
				}
				childSesponse.setSubConfList(confRespList);
				childSesponseList.add(childSesponse);
				parentSesponse.setChildren(childSesponseList);
			}
			subInfolist.add(parentSesponse);
		}
		return subInfolist;
	}
	

	/**
	 * 生成任务查询对象
	 * @param req
	 */
	public TaskInfoEntity genTaskQueryObj(CommonFlowHandleReq req, User curUser) {
		TaskInfoEntity query = new TaskInfoEntity();
		query.setAssignee(curUser.getLoginName());
		if(StringUtils.isNotBlank(req.getProcCode())){
			query.setTitle(req.getProcCode());
		}
		if(StringUtils.isNotBlank(req.getApplyName())){
			query.setTitle(req.getApplyName());
		}
		if(StringUtils.isNotBlank(req.getProcName())){
			query.setTitle(req.getProcName());
		}
		if(StringUtils.isNotBlank(req.getExpenseStatus())){
			query.setBusinessStatus(req.getExpenseStatus());
		}
		if(StringUtils.isNotBlank(req.getApprovalAssigneeId())){
			query.setApprovalAssigneeId(req.getApprovalAssigneeId());
		}
		query.setBeginApplyTime(DateUtils.parseDateFormUnix(req.getApplyTimeStart(), DateUtils.YYYY_MM_DD));
		query.setEndApplyTime(DateUtils.parseDateFormUnix(req.getApplyTimeEnd(), DateUtils.YYYY_MM_DD));
		if(StringUtils.isNotBlank(req.getOfficeId())){
			query.setOfficeId(req.getOfficeId());
		}
		if(EXPENSE_FLOW_APPLY.equals(req.getBillType())){
			query.setProcKey(Constant.EXPENSE_FLOW_APPLY_PROC);
			query.setBusinessTable(ActUtils.OA_EXPENSE_FLOW[1]);
			query.setBusinessStatusColumn(ActUtils.OA_EXPENSE_FLOW[2]);
		}
		if(Constant.DEMAND_MANAGEMENT_MARKET.equals(req.getBillType())){
			query.setProcKey(Constant.DEMAND_MANAGEMENT_MARKET_PROC);
			query.setBusinessTable(ActUtils.OA_DEMAND_FLOW[1]);
			query.setBusinessStatusColumn(ActUtils.OA_DEMAND_FLOW[2]);
		}
		if(Constant.DEMAND_MANAGEMENT_IMPLEMENT.equals(req.getBillType())){
			query.setProcKey(Constant.DEMAND_MANAGEMENT_IMPLEMENT_PROC);
			query.setBusinessTable(ActUtils.OA_IMPLEMENT_FLOW[1]);
			query.setBusinessStatusColumn(ActUtils.OA_IMPLEMENT_FLOW[2]);
		}
		if(Constant.RECP_FLOW_APPLY.equals(req.getBillType())){
			query.setProcKey(Constant.RECP_FLOW_APPLY_PROC);
			query.setBusinessTable(ActUtils.OA_RECP_FLOW[1]);
			query.setBusinessStatusColumn(ActUtils.OA_RECP_FLOW[2]);
		}
		if(Constant.TRAVEL_FLOW_APPLY.equals(req.getBillType())){
			query.setProcKey(Constant.TRAVEL_FLOW_APPLY_PROC);
			query.setBusinessTable(ActUtils.OA_TRAVEL_FLOW[1]);
			query.setBusinessStatusColumn(ActUtils.OA_TRAVEL_FLOW[2]);
		}
		if(Constant.RESOURCES_APPLY_FLOW_APPLY.equals(req.getBillType())){
			query.setProcKey(Constant.RESOURCES_APPLY_FLOW_APPLY_PROC);
			query.setBusinessTable(ActUtils.OA_RESOURCES_APPLY_FLOW[1]);
			query.setBusinessStatusColumn(ActUtils.OA_RESOURCES_APPLY_FLOW[2]);
		}
		if(Constant.RESOURCES_HANDLE_FLOW_APPLY.equals(req.getBillType())){
			query.setProcKey(Constant.RESOURCES_HANDLE_FLOW_APPLY_PROC);
			query.setBusinessTable(ActUtils.OA_RESOURCES_HANDLE_FLOW[1]);
			query.setBusinessStatusColumn(ActUtils.OA_RESOURCES_HANDLE_FLOW[2]);
		}
		if(Constant.CONTRACT_FLOW_APPLY.equals(req.getBillType())){
			query.setProcKey(Constant.CONTRACT_FLOW_APPLY_PROC);
			query.setBusinessTable(ActUtils.OA_CONSTRACT_FLOW[1]);
			query.setBusinessStatusColumn(ActUtils.OA_CONSTRACT_FLOW[2]);
		}
		if(StringUtils.isNotBlank(req.getOfficeName())){
			query.setOfficeName(req.getOfficeName());
			
		}
		return query;
	}
	
	/**
	 * 待办任务列表
	 * @param req
	 * @param curUser
	 * @param page
	 */
	public List<TaskResponse> queryTodoList(CommonFlowHandleReq req,User curUser,Page<TaskInfoEntity> page,List<TaskResponse> todolist){
		init();
		for (int i = 0; i < page.getList().size(); i++) {
			TaskInfoEntity tempTask = page.getList().get(i);
			
			String procInsId = tempTask.getProcessInstanceId();
			BigDecimal total = new BigDecimal(0.0);
			String businessId = "";
			String applyName = "";
			String procCode	= "";	//流程编号
			String remark	= "";		//备注
			String taxCityCode = ""; //发票所属城市编码
			String taxCityName = ""; //发票所属城市名称
			String officeName = ""; //所属部门名称
			String billType = ""; //单据类型
			if(StringUtils.isNotBlank(procInsId) && !StringUtils.equals("null", procInsId)){
				logger.info("======================="+procInsId);
				if(tempTask.getProcKey().contains(Constant.EXPENSE_FLOW_APPLY_PROC)){
					ExpenseFlow expenseFlow = expenseFlowService.getByProcInsId(procInsId);
					if(expenseFlow != null){
						businessId = expenseFlow.getId();
						total = expenseFlow.getExpenseTotal();
						applyName = expenseFlow.getApplyPerName();
						procCode = expenseFlow.getProcCode();
						remark = expenseFlow.getRemarks();
						taxCityCode = expenseFlow.getTaxCityCode();
						taxCityName = DictUtils.getDictLabel(taxCityCode, "tax_city", "");
						officeName = expenseFlow.getOffice().getName();
						billType = EXPENSE_FLOW_APPLY;
					}
				}else if(Constant.DEMAND_MANAGEMENT_MARKET_PROC.equals(tempTask.getProcKey()) || 
						Constant.DEMAND_MANAGEMENT_IMPLEMENT_PROC.equals(tempTask.getProcKey())){ //需求申请
					DemandManagement demandManagement = demandManagementService.getDemandByProcInsId(procInsId);
					if(demandManagement != null){
						businessId = demandManagement.getId();
						total = new BigDecimal(demandManagement.getAmountSum());
						applyName = demandManagement.getApplyPerName();
						procCode = demandManagement.getProcCode();
						remark = demandManagement.getRemarks();
						officeName = demandManagement.getOffice().getName();
						if(Constant.DEMAND_MANAGEMENT_MARKET_PROC.equals(tempTask.getProcKey())){
							billType = Constant.DEMAND_MANAGEMENT_MARKET;
						}else{
							billType = Constant.DEMAND_MANAGEMENT_IMPLEMENT;
						}
					}
				}else if(Constant.RECP_FLOW_APPLY_PROC.equals(tempTask.getProcKey())){
					RecpFlow recpFlow = recpFlowService.getByProcInsId(procInsId);
					if(recpFlow != null){
						businessId = recpFlow.getId();
						total = recpFlow.getBudgetTotal();
						applyName = recpFlow.getApplyPerName();
						procCode = recpFlow.getProcCode();
						remark = recpFlow.getRemarks();
						officeName = recpFlow.getOffice().getName();
						billType = Constant.RECP_FLOW_APPLY;
					}
				}else if(Constant.TRAVEL_FLOW_APPLY_PROC.equals(tempTask.getProcKey())){
					TravelFlow travelFlow = travelFlowService.getByProcInsId(procInsId);
					if(travelFlow != null){
						businessId = travelFlow.getId();
						total = travelFlow.getBudgetTotal();
						applyName = travelFlow.getApplyPerName();
						procCode = travelFlow.getProcCode();
						remark = travelFlow.getRemarks();
						officeName = travelFlow.getOffice().getName();
						billType = Constant.TRAVEL_FLOW_APPLY;
					}
				}else if(Constant.RESOURCES_APPLY_FLOW_APPLY_PROC.equals(tempTask.getProcKey())){
					ResourcesApply resourcesApply = resourcesApplyService.getByProcInsId(procInsId);
					if(resourcesApply != null){
						businessId = resourcesApply.getId();
						total = resourcesApply.getAmountSum();
						applyName = resourcesApply.getApplyPerName();
						procCode = resourcesApply.getProcCode();
						remark = resourcesApply.getRemarks();
						officeName = resourcesApply.getOffice().getName();
						billType = Constant.RESOURCES_APPLY_FLOW_APPLY;
					}
				}else if(Constant.RESOURCES_HANDLE_FLOW_APPLY_PROC.equals(tempTask.getProcKey())){
					ResourcesHandleFlow resourcesHandleFlow = resourcesHandleFlowService.getByProcInsId(procInsId);
					if(resourcesHandleFlow != null){
						businessId = resourcesHandleFlow.getId();
						total = resourcesHandleFlow.getAmountSum();
						applyName = resourcesHandleFlow.getApplyPerName();
						procCode = resourcesHandleFlow.getProcCode();
						remark = resourcesHandleFlow.getRemarks();
						officeName = resourcesHandleFlow.getOffice().getName();
						billType = Constant.RESOURCES_HANDLE_FLOW_APPLY;
					}
				}else if(Constant.CONTRACT_FLOW_APPLY_PROC.equals(tempTask.getProcKey())){
					ContractFlowNewResponse contractFlow = contractFlowNewService.getByProcInsId(procInsId);
					if(contractFlow != null){
						businessId = contractFlow.getId();
						total = BigDecimal.ZERO;
						applyName = contractFlow.getApplyPerName();
						procCode = contractFlow.getProcCode();
						remark = contractFlow.getRemarks();
						officeName = contractFlow.getOfficeName();
						billType = Constant.CONTRACT_FLOW_APPLY;
					}
				}
			}
			String version = "V:" + tempTask.getVersion();
			TaskResponse todo = new TaskResponse(tempTask.getId(), 
					tempTask.getExecutionId(), 
					tempTask.getTaskName(), 
					tempTask.getTaskDefinitionKey(), 
					tempTask.getAssignee(), 
					tempTask.getProcessInstanceId(), 
					tempTask.getProcessDefinitionId(), 
					null, 
					DateUtils.formatDate(tempTask.getCreateTime(), DateUtils.YYYY_MM_DD_HH_MM_SS), 
					tempTask.getProcDefName(), 
					version, 
					"todo", 
					DictUtils.getDictLabel("todo", "task_status", ""), 
					tempTask.getTitle(), 
					total, 
					applyName, 
					procCode, remark,taxCityCode,taxCityName,
					businessId,officeName,billType);
			todolist.add(todo);
		}
		return todolist;
	}
	
	/**
	 * 已办任务列表
	 * @param req
	 * @param page
	 * @param todolist
	 * @return
	 */
	public List<TaskResponse> historicList(CommonFlowHandleReq req,Page<TaskInfoEntity> page,List<TaskResponse> todolist){
		init();
		List<TaskInfoEntity> list = page.getList();
		for (int i = 0; i < list.size(); i++) {
			TaskInfoEntity tmpTask = list.get(i);
			
			String status = tmpTask.getStatus();
			
			BigDecimal total = new BigDecimal(0.0);
			String applyName = "";
			String procCode	= "";	//流程编号
			String remark	= "";		//备注
			String taxCityCode = ""; //发票所属城市编码
			String taxCityName = ""; //发票所属城市名称
			String businessId = ""; //业务ID
			String officeName = ""; //所属部门名称
			String billType = ""; //单据类型
			String billStatus = "";  //单据状态
			if(StringUtils.isNotBlank(tmpTask.getProcessInstanceId()) && !StringUtils.equals("null", tmpTask.getProcessInstanceId())){
				if(tmpTask.getProcKey().contains(Constant.EXPENSE_FLOW_APPLY_PROC)){
					ExpenseFlow expenseFlow = expenseFlowService.getByProcInsId(tmpTask.getProcessInstanceId());
					if(expenseFlow != null){
						total = expenseFlow.getExpenseTotal();
						applyName = expenseFlow.getApplyPerName();
						procCode = expenseFlow.getProcCode();
						remark = expenseFlow.getRemarks();
						taxCityCode = expenseFlow.getTaxCityCode();
						taxCityName = DictUtils.getDictLabel(taxCityCode, "tax_city", "");
						businessId = expenseFlow.getId();
						officeName = expenseFlow.getOffice().getName();
						billType = EXPENSE_FLOW_APPLY;
						billStatus = expenseFlow.getExpenseStatus();
					}
				}else if(Constant.DEMAND_MANAGEMENT_MARKET_PROC.equals(tmpTask.getProcKey()) || 
						Constant.DEMAND_MANAGEMENT_IMPLEMENT_PROC.equals(tmpTask.getProcKey())){ //需求申请
					DemandManagement demandManagement = demandManagementService.getDemandByProcInsId(tmpTask.getProcessInstanceId());
					if(demandManagement != null){
						businessId = demandManagement.getId();
						total = new BigDecimal(demandManagement.getAmountSum());
						applyName = demandManagement.getApplyPerName();
						procCode = demandManagement.getProcCode();
						remark = demandManagement.getRemarks();
						officeName = demandManagement.getOffice().getName();
						if(Constant.DEMAND_MANAGEMENT_MARKET_PROC.equals(tmpTask.getProcKey())){
							billType = Constant.DEMAND_MANAGEMENT_MARKET;
						}else{
							billType = Constant.DEMAND_MANAGEMENT_IMPLEMENT;
						}
						billStatus = demandManagement.getDemandStatus();
					}
				}else if(Constant.RECP_FLOW_APPLY_PROC.equals(tmpTask.getProcKey())){
					RecpFlow recpFlow = recpFlowService.getByProcInsId(tmpTask.getProcessInstanceId());
					if(recpFlow != null){
						businessId = recpFlow.getId();
						total = recpFlow.getBudgetTotal();
						applyName = recpFlow.getApplyPerName();
						procCode = recpFlow.getProcCode();
						remark = recpFlow.getRemarks();
						officeName = recpFlow.getOffice().getName();
						billType = Constant.RECP_FLOW_APPLY;
						billStatus = recpFlow.getRecpStatus();
					}
				}else if(Constant.TRAVEL_FLOW_APPLY_PROC.equals(tmpTask.getProcKey())){
					TravelFlow travelFlow = travelFlowService.getByProcInsId(tmpTask.getProcessInstanceId());
					if(travelFlow != null){
						businessId = travelFlow.getId();
						total = travelFlow.getBudgetTotal();
						applyName = travelFlow.getApplyPerName();
						procCode = travelFlow.getProcCode();
						remark = travelFlow.getRemarks();
						officeName = travelFlow.getOffice().getName();
						billType = Constant.TRAVEL_FLOW_APPLY;
						billStatus = travelFlow.getTravelStatus();
					}
				}else if(Constant.RESOURCES_APPLY_FLOW_APPLY_PROC.equals(tmpTask.getProcKey())){
					ResourcesApply resourcesApply = resourcesApplyService.getByProcInsId(tmpTask.getProcessInstanceId());
					if(resourcesApply != null){
						businessId = resourcesApply.getId();
						total = resourcesApply.getAmountSum();
						applyName = resourcesApply.getApplyPerName();
						procCode = resourcesApply.getProcCode();
						remark = resourcesApply.getRemarks();
						officeName = resourcesApply.getOffice().getName();
						billType = Constant.RESOURCES_APPLY_FLOW_APPLY;
						billStatus = resourcesApply.getResourcesStatus();
					}
				}else if(Constant.RESOURCES_HANDLE_FLOW_APPLY_PROC.equals(tmpTask.getProcKey())){
					ResourcesHandleFlow resourcesHandleFlow = resourcesHandleFlowService.getByProcInsId(tmpTask.getProcessInstanceId());
					if(resourcesHandleFlow != null){
						businessId = resourcesHandleFlow.getId();
						total = resourcesHandleFlow.getAmountSum();
						applyName = resourcesHandleFlow.getApplyPerName();
						procCode = resourcesHandleFlow.getProcCode();
						remark = resourcesHandleFlow.getRemarks();
						officeName = resourcesHandleFlow.getOffice().getName();
						billType = Constant.RESOURCES_HANDLE_FLOW_APPLY;
						billStatus = resourcesHandleFlow.getResourcesHandleStatus();
					}
				}else if(Constant.CONTRACT_FLOW_APPLY_PROC.equals(tmpTask.getProcKey())){
					ContractFlowNewResponse contractFlow = contractFlowNewService.getByProcInsId(tmpTask.getProcessInstanceId());
					if(contractFlow != null){
						businessId = contractFlow.getId();
						total = BigDecimal.ZERO;
						applyName = contractFlow.getApplyPerName();
						procCode = contractFlow.getProcCode();
						remark = contractFlow.getRemarks();
						officeName = contractFlow.getOfficeName();
						billType = Constant.CONTRACT_FLOW_APPLY;
						billStatus = contractFlow.getContractFlowStatus();
					}
				}
			}
			String title = tmpTask.getTitle();
			String version = "V:" + tmpTask.getVersion();
			
			TaskResponse todo = new TaskResponse(tmpTask.getId(), 
					tmpTask.getExecutionId(), 
					tmpTask.getTaskName(), 
					tmpTask.getTaskDefinitionKey(), 
					tmpTask.getAssignee(), 
					tmpTask.getProcessInstanceId(), 
					tmpTask.getProcessDefinitionId(), 
					null, 
					DateUtils.formatDate(tmpTask.getCompletedTime(), DateUtils.YYYY_MM_DD_HH_MM_SS), 
					tmpTask.getProcDefName(), 
					version, 
					status, 
					DictUtils.getDictLabel(status, "task_status", ""), 
					title, 
					total, 
					applyName, 
					procCode, remark,taxCityCode,taxCityName,businessId,officeName,billType,billStatus,DictUtils.getDictLabel(billStatus, "expense_status", ""));
			todolist.add(todo);
		}
		return todolist;
	}

	/**
	 * 修改已读状态
	 * @param expenseFlow
	 * @param curUser
	 */
	@Transactional(readOnly = false)
	public void updateReadStatus(ExpenseFlow expenseFlow,User curUser){
		//修改已读状态
		MessageInfo mess = new MessageInfo();
		mess.setRecPersion(curUser.getId());
		mess.setBusinessId(expenseFlow.getId());
		messageInfoDao.updateReadStatus(mess);
	}
	/**
	 * 报销明细（报销主表信息）
	 * @param expenseFlow
	 * @param curUser
	 * @return
	 */

	public ExpenseFlowResponse flowDetailExpenseFlow(ExpenseFlow expenseFlow, User curUser){
		logger.info("=======flowDetail expenseFlow============="+expenseFlow.toString());
		expenseFlow.setTaxCityName(DictUtils.getDictLabel(expenseFlow.getTaxCityCode(), "tax_city", "")); //发票所属城市
        ExpenseFlowResponse expenseFlowResponse = new ExpenseFlowResponse(expenseFlow);
		expenseFlowResponse.setModify("");
		//查询申请人所属岗位
		User u = UserUtils.getByLoginName(expenseFlow.getApplyPerCode());
		PostInfo postInfo = postInfoDao.get(u.getPostIds());
		expenseFlowResponse.setPostName(postInfo.getPostName());
		//查询陪客人员信息
		if(StringUtils.isNotBlank(expenseFlow.getProcCode())){
			RecpParams recpParams = new RecpParams();
			recpParams.setProcCode(expenseFlow.getProcCode()); //流程编号
			List<RecpParams> recpParamsList = recpParamsService.findList(recpParams);
			if(recpParamsList != null && recpParamsList.size() > 0){
				String[] recpParticPersonels = new String[recpParamsList.size()];
				String[] recpParticPersonelsName = new String[recpParamsList.size()];
				for(int i = 0;i < recpParamsList.size();i++){
					recpParticPersonels[i] = recpParamsList.get(i).getParamValue();
					recpParticPersonelsName[i] = recpParamsList.get(i).getParamName();
				}
				expenseFlowResponse.setEmployees(recpParticPersonels);
				expenseFlowResponse.setEmployeesName(recpParticPersonelsName);
			}
		}
		//设置关联申请信息
		if(StringUtils.isNotBlank(expenseFlow.getRecpProcCode())){
			//接待申请
			if(Constant.OA_EXPENSE_TYPE_TWO.equals(expenseFlow.getApplyType())){
				RecpFlow recpFlow = recpFlowService.getByProcCode(expenseFlow.getRecpProcCode());
				if(recpFlow != null){
					expenseFlowResponse.setRelationThemeName(recpFlow.getProcName());
					if(Constant.expense_approve_end.equals(recpFlow.getRecpStatus())) {
						//接待报销通过后，隐藏该单据的“项目名称”，使用固定文案代替
						expenseFlowResponse.setProjectName("客户接待");
                    }
                    expenseFlowResponse.setCustomerSituation(expenseFlow.getCustomerSituation());//接待客户情况
				}

			//出差申请
			}else if(Constant.OA_EXPENSE_TYPE_THREE.equals(expenseFlow.getApplyType())){
				TravelFlow travelFlow = travelFlowService.getByProcCode(expenseFlow.getRecpProcCode());
				if(travelFlow != null){
					expenseFlowResponse.setRelationThemeName(travelFlow.getProcName());//关联主题名称

					//判断出差关联类型
					if(StringUtils.isNotBlank(travelFlow.getRelationTheme())){//关联的是主题
						expenseFlowResponse.setRelType("1");
					}else if(StringUtils.isNotBlank(travelFlow.getProjectId())){//关联的是项目
						expenseFlowResponse.setRelType("2");
					}else {//不关联
						expenseFlowResponse.setRelType("3");
					}

					//出差报销分类(id列表和name列表)
					List<String> expenseTypeList = Arrays.asList(travelFlow.getTravelExpenseTypes().split(","));
					List<String> expenseTypeListName = new LinkedList<>();
					for(String s : expenseTypeList){
						expenseTypeListName.add(DictUtils.getDictLabel(s,"travel_expense_type",""));
					}
					expenseFlowResponse.setTravelExpenseTypeListName(expenseTypeListName);

					// 随行人员(id列表和name列表)（字段非必填）
					List<String> entourageList;
					if(com.sijibao.oa.common.utils.StringUtils.isNotBlank(travelFlow.getEntourages())){
						entourageList = Arrays.asList(travelFlow.getEntourages().split(","));
					}else {
						entourageList = new ArrayList<>();
					}
					List<String> entourageListName = new LinkedList<>();
					if(entourageList.size() > 0){
						for(String s : entourageList){
							entourageListName.add(UserUtils.get(s).getName());
						}
					}
					expenseFlowResponse.setEntourageListName(entourageListName);
				}
			}
		}
		//比较是否是当前人
		if(StringUtils.equals(curUser.getLoginName(), expenseFlowResponse.getApplyPerCode())){
			expenseFlowResponse.setLocal(1);
		}
		//判断当前环节是否可以编辑页面
		expenseFlowResponse.setIsDeit(0);
		if(Constant.expense_save.equals(expenseFlow.getExpenseStatus())){
			expenseFlowResponse.setIsDeit(1);
			expenseFlowResponse.setModify(Constant.DEFAULT_NOE_MODIFY);
		}
		Act a = new Act();
		a.setProcInsId(expenseFlow.getProcInsId());
		a = actTaskService.queryThisRunTaskId(a);
		if(a != null && Constant.DEFAULT_NOE_MODIFY.equals(a.getTaskDefKey()) && StringUtils.equals(curUser.getLoginName(), a.getAssignee())){
			expenseFlowResponse.setIsDeit(1);
			expenseFlowResponse.setModify(Constant.DEFAULT_NOE_MODIFY);
		}
		return expenseFlowResponse;
	}
	
	public List<ExpenseDetailResponse> flowDetailExpenseDetail(List<ExpenseDetailResponse> detailsList,ExpenseFlow	expenseFlow,String serverUrl,ExpenseFlowResponse expenseFlowResponse,String clientType){

		//查询附件信息
		ExpenseAttachment expenseAttachment = new ExpenseAttachment();
		expenseAttachment.setExpenseCode(expenseFlowResponse.getProcCode());
		List<ExpenseAttachment> resultLists = expenseAttachmentService.findListByProcCode(expenseAttachment);
		List<ExpenseAttachment> resultList = Lists.newArrayList();
		for(ExpenseAttachment ment :resultLists){
			if("1".equals(ment.getEnable())){
				ment.setSubCode("");
			}
			resultList.add(ment);
		}
		List<ExpenseAttachment> expenseAttachmentList = new ArrayList<ExpenseAttachment>();
		List<ExpenseAttachment> subAttachmentList = new ArrayList<ExpenseAttachment>();
		logger.info("attachment:"+resultList.size());
		if(resultList != null && resultList.size() > 0){
			for(ExpenseAttachment e:resultList){
				if(StringUtils.isBlank(e.getExpenseDetailId())){
					expenseAttachmentList.add(e);
				}else{
					subAttachmentList.add(e);
				}
			}
			if(expenseAttachmentList != null && expenseAttachmentList.size() > 0){
				String[] expenseAttachments = new String[expenseAttachmentList.size()]; 
				List<Map<String,Object>> attachMentList = new ArrayList<Map<String,Object>>();
				for(int i = 0 ;i<expenseAttachmentList.size();i++){
					expenseAttachments[i] = expenseAttachmentList.get(i).getExpenseAttachmentUrl();
					Map<String,Object> attachMentMap = new HashMap<String,Object>();
					attachMentMap.put("url", expenseAttachmentList.get(i).getExpenseAttachmentUrl());
					attachMentMap.put("fileName", expenseAttachmentList.get(i).getFileName());
					attachMentList.add(attachMentMap);
				}
				expenseFlowResponse.setExpenseAttachment(expenseAttachments);
				expenseFlowResponse.setExpenseAttachmentWeb(attachMentList);
				expenseFlowResponse.setExpenseAttachmentPrefix(serverUrl);
			}
		}
		
		ExpenseDetail query = new ExpenseDetail();
		query.setProcCode(expenseFlow.getProcCode());
		List<ExpenseDetail> expenseDetailLists = expenseDetailService.findList(query);
		List<ExpenseDetail> expenseDetailList = Lists.newArrayList();
		if(expenseDetailLists != null && expenseDetailLists.size() > 0){
			for(ExpenseDetail tail :expenseDetailLists){
				if("1".equals(tail.getFirstEnable()) || "1".equals(tail.getSecondEnable())){
					tail.setFirstSub("");
					tail.setSecondSub("");
				}
				expenseDetailList.add(tail);
			}
		}
		for (int i = 0; i < expenseDetailList.size(); i++) {
			//处理开始城市信息
			Area start = new Area();
			start.setCode(expenseDetailList.get(i).getStartPoint());
			start = areaInfoService.getForCode(start);
			String[] startArea = new String[1];
			String[] startAreaName = new String[1];
			if(start != null){
				if("1".equals(start.getType())){
					startArea[0] = start.getCode();
					startAreaName[0] = start.getName();
				}else{
					startArea = new String[2];
					startAreaName = new String[2];
					Area parentStart = areaInfoService.get(start.getParentId());
					startArea[0] = parentStart.getCode();
					startAreaName[0] = parentStart.getName();
					startArea[1] = start.getCode();
					startAreaName[1] = start.getName();
				}
			}
			
			//处理结束城市信息
			Area end = new Area();
			end.setCode(expenseDetailList.get(i).getEndPoint());
			end = areaInfoService.getForCode(end);
			String[] endArea = new String[1];
			String[] endAreaName = new String[1];
			if(end != null){
				if("1".equals(end.getType())){
					endArea[0] = end.getCode();
					endAreaName[0] = end.getName();
				}else{
					endArea = new String[2];
					endAreaName = new String[2];
					Area parentEnd = areaInfoService.get(end.getParentId());
					if(parentEnd != null){
						endArea[0] = parentEnd.getCode();
						endAreaName[0] = parentEnd.getName();
						endArea[1] = end.getCode();
						endAreaName[1] = end.getName();
					}else{
						endArea = new String[1];
						endAreaName = new String[1];
						endArea[0] = end.getCode();
						endAreaName[0] = end.getName();
					}
				}
			}
			
			//查询科目附件信息
			detailsList.add(new ExpenseDetailResponse(expenseDetailList.get(i),startArea,startAreaName,endArea,endAreaName,subAttachmentList,serverUrl,clientType));
		}
		return detailsList;
	}
	/**
	 * 合并相同明细金额
	 * @param expenseFlowId
	 * @return
	 */
	public List<AmtDetailResponse> getAmtList(String expenseFlowId) {
		DecimalFormat df = new DecimalFormat("0.00");
		List<AmtDetail> amt = expenseFlowDao.getAmtList(expenseFlowId);
		ArrayList<AmtDetailResponse> list = Lists.newArrayList();
		if(amt != null && amt.size() > 0){
			for (AmtDetail a : amt) {
				AmtDetailResponse c = change(a,AmtDetailResponse.class);
				if(c != null && c.getAmt() != null){
					if(BigDecimal.ZERO.compareTo(c.getAmt()) != 0){
						c.setAmt(new BigDecimal(df.format(c.getAmt())));
					}
				}
				list.add(c);
			}
		}
		return list;
	}
	
	public Page<BringRemitResponse> findBringRemitList(User user, BringRemitHandleRequest req) {
		ExpenseFlow expenseFlow = new ExpenseFlow();
		if (!user.isAdmin()){
			expenseFlow.setCreateBy(user);
		}
		expenseFlow.setProcCode(req.getProcCode());
		expenseFlow.setProcName(req.getProcName());
		expenseFlow.setApplyPerCode(req.getApplyPerCode());
		expenseFlow.setApplyPerName(req.getApplyPerName());
		expenseFlow.setBeginApplyTime(DateUtils.parseDate(req.getBeginApplyTime()));
		expenseFlow.setEndApplyTime(DateUtils.parseDate(req.getEndApplyTime()));
		expenseFlow.setProjectId(req.getProjectId());
		expenseFlow.setExpenseStatus(req.getExpenseStatus());
		
		String bringRemitOrg = SysParamsUtils.getParamValue(Global.BRING_REMIT_ORG, Global.SYS_PARAMS, "");
		expenseFlow.setBringRemitOrg(bringRemitOrg);
		expenseFlow.setCurrentTaskAssignee(user.getLoginName());
		Page<ExpenseFlow> page = expenseFlowService.queryBringRemitListForPage(new Page<ExpenseFlow>(req.getPageNo(), req.getPageSize()), expenseFlow);
		ArrayList<BringRemitResponse> list = Lists.newArrayList();
		for (ExpenseFlow f : page.getList()) {
			BringRemitResponse b = new BringRemitResponse();
			b.setId(f.getId());
			b.setProcCode(f.getProcCode());
			b.setProcName(f.getProcName());
			b.setApplyPerName(f.getApplyPerName());
			if(f.getApplyTime() != null){
				b.setApplyTime(DateUtils.format(f.getApplyTime(),DateUtils.YYYY_MM_DD));
			}
			b.setExpenseTotal(f.getExpenseTotal());
			list.add(b);
		}
		Page<BringRemitResponse> newPage = new Page<BringRemitResponse>();
		newPage.setPageNo(page.getPageNo());
		newPage.setPageSize(page.getPageSize());
		newPage.setCount(page.getCount());
		newPage.setList(list);
		return newPage;
		
		
	}
	/**
	 * 提前打款保存
	 * @param req
	 * @return
	 */
	@Transactional(readOnly = false)
	public Boolean saveBringRemit(BringRemitSaveRequest req) {
		ExpenseFlow expenseFlow = expenseFlowService.get(req.getId());
		if(expenseFlow == null){
			return false;
		}
		expenseFlow.setBringAmount((BigDecimal)req.getBringAmount());
		expenseFlow.setBringRemitStatus("1");
		expenseFlowService.updateBringInfo(expenseFlow);
		return true;
	}
	
	/**
	 * 任务撤销
	 * @param expenseFlow
	 */
	@Transactional(readOnly = false)
	public void repealTask(ExpenseFlow expenseFlow,User user){
		
		/********判断当前节点的下一个节点任务是否已经完成*********/
		
		/**********流程跳转，发起撤销start********/
		Map<String, Object> vars = Maps.newHashMap();
		vars.put("pass", "1");
		
		//查询被撤销的节点信息
		Act act = new Act();
		act.setProcInsId(expenseFlow.getAct().getProcInsId()); //流程实例ID
		act = actTaskService.queryThisRunTaskId(act); //查询当前任务信息
		
		//查询发起撤销节点key
		Act targetAct = new Act();
		if("0".equals(expenseFlow.getAct().getTaskId())){
			targetAct.setTaskDefKey("modify");
		}else{
			targetAct.setTaskId(expenseFlow.getAct().getTaskId());
			targetAct.setProcInsId(expenseFlow.getAct().getProcInsId());
			targetAct = actTaskService.queryHisTask(targetAct);
		}
		
		String targetTaskDefinitionKey = targetAct.getTaskDefKey(); //发起撤销的节点key
		actTaskService.jumpTaskAndAddComent(expenseFlow.getAct().getProcInsId(), act.getTaskId(),
				"[流程撤回]当前审核任务被"+user.getName()+"撤回", user.getLoginName(), targetTaskDefinitionKey, vars);

		//根据传入的报销流程对象的procInsId属性值，查询得到完整的报销流程对象信息
		ExpenseFlow ef = expenseFlowDao.getByProcInsId(expenseFlow.getAct().getProcInsId());
		// 撤销时，更新所关联的接待申请，状态改为未关联
		if(Constant.OA_EXPENSE_TYPE_TWO.equals(ef.getApplyType())) {
			RecpFlow recpFlow = recpFlowService.getByProcCode(ef.getRecpProcCode());
			if(recpFlow != null){
				recpFlow.setRelated(0);
				recpFlowService.updateRecp(recpFlow);
			}
		}
		// 撤销时，更新所关联的出差申请，被关联次数减1
		if(Constant.OA_EXPENSE_TYPE_THREE.equals(ef.getApplyType())){
			TravelFlow travelFlow = travelFlowService.getByProcCode(ef.getRecpProcCode());
			if(travelFlow != null){
				travelFlow.setIncidenceTimes(travelFlow.getIncidenceTimes() - 1);
				travelFlowService.save(travelFlow);
			}
		}

		/**********流程跳转，发起撤销end********/
	}
	
	/**
	 * 删除任务
	 * @param expenseFlow
	 */
	@Transactional(readOnly = false)
	public void deleteExpenseFlowInfo(ExpenseFlow expenseFlow){
		if(Constant.expense_save.equals(expenseFlow.getExpenseStatus())){ //保存的单据进行物理删除
			expenseDetailDao.deleteByProcCode(expenseFlow.getProcCode()); //删除报销明细信息
			expenseFlowDao.deleteExpenseFlowInfo(expenseFlow); //删除报销主表信息
		}else{
			if(StringUtils.isNotBlank(expenseFlow.getProcInsId())){
				//删除任务
				actTaskService.deleteRunTask(expenseFlow.getProcInsId()); //删除运行任务
				actTaskService.deleteHisTask(expenseFlow.getProcInsId()); //删除历史任务
			}
			//删除单据
			expenseFlowDao.delete(expenseFlow);
			//修改单据状态为已删除
			expenseFlow.setExpenseStatus(Constant.expense_delete); //修改报销状态为已删除
			expenseFlowDao.updateExpenseStatus(expenseFlow);

			// 删除时，更新所关联的接待申请，状态改为未关联
			if(Constant.OA_EXPENSE_TYPE_TWO.equals(expenseFlow.getApplyType())) {
				RecpFlow recpFlow = recpFlowService.getByProcCode(expenseFlow.getRecpProcCode());
				if(recpFlow != null){
					recpFlow.setRelated(0);
					recpFlowService.updateRecp(recpFlow);
				}
			}
			// 删除时，更新所关联的出差申请，被关联次数减1
			if(Constant.OA_EXPENSE_TYPE_THREE.equals(expenseFlow.getApplyType())){
				TravelFlow travelFlow = travelFlowService.getByProcCode(expenseFlow.getRecpProcCode());
				if(travelFlow != null){
					travelFlow.setIncidenceTimes(travelFlow.getIncidenceTimes() - 1);
					travelFlowService.save(travelFlow);
				}
			}
		}
	}

	
}
