package sijibao.oa.jeesite.modules.intfz.service.demand;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.sijibao.oa.common.service.BaseService;
import com.sijibao.oa.common.service.ServiceException;
import com.sijibao.oa.common.utils.DateUtils;
import com.sijibao.oa.modules.act.entity.Act;
import com.sijibao.oa.modules.act.service.ActTaskService;
import com.sijibao.oa.modules.act.utils.ActUtils;
import com.sijibao.oa.modules.flow.utils.FlowUtils;
import com.sijibao.oa.modules.intfz.request.demand.DemandManagementBudgetRequest;
import com.sijibao.oa.modules.intfz.request.demand.DemandManagementRequest;
import com.sijibao.oa.modules.intfz.utils.Constant;
import com.sijibao.oa.modules.oa.dao.DemandManagementBudgetDao;
import com.sijibao.oa.modules.oa.dao.DemandManagementDao;
import com.sijibao.oa.modules.oa.entity.DemandAssign;
import com.sijibao.oa.modules.oa.entity.DemandManagement;
import com.sijibao.oa.modules.oa.entity.DemandManagementBudget;
import com.sijibao.oa.modules.oa.entity.ProjectInfo;
import com.sijibao.oa.modules.oa.service.DemandAssignService;
import com.sijibao.oa.modules.oa.service.ProjectInfoService;
import com.sijibao.oa.modules.sys.dao.PostInfoDao;
import com.sijibao.oa.modules.sys.entity.PostInfo;
import com.sijibao.oa.modules.sys.entity.User;

/**
 * 需求申请流程服务层
 * @author Petter
 */
@Service
@Transactional(readOnly = true)
public class IntfzDemandManagementService extends BaseService {
	
	@Autowired
	private DemandManagementDao demandManagementDao;
	@Autowired
	private DemandManagementBudgetDao demandManagementBudgetDao; 
	@Autowired
	private ActTaskService actTaskService;
	@Autowired
	private PostInfoDao postInfoDao;
	@Autowired
	private DemandAssignService demandAssignService;
	@Autowired
	private ProjectInfoService projectInfoService;
	/**
	 * 实施需求申请服务
	 * @param demandManagementRequest
	 * @param user
	 */
	@Transactional(readOnly = false)
	public void demandImplemetApplyService(DemandManagementRequest demandManagementRequest, User user){
		/**********实施需求申请业务处理start*******/
		DemandManagement demandManagement = new DemandManagement();
		demandManagement.setApplyPerCode(user.getLoginName()); //申请人编号
		demandManagement.setApplyPerName(user.getName()); //申请人名称
		demandManagement.setApplyTime(new Date());//申请时间
		demandManagement.setOffice(user.getOffice()); //所属部门
		demandManagement.setOfficeName(user.getOffice().getName()); //所属部门名称
		demandManagement.setProjectId(demandManagementRequest.getProjectId()); //项目ID
		ProjectInfo projectInfo = projectInfoService.get(demandManagementRequest.getProjectId());
		if(projectInfo != null){
			demandManagement.setProjectName(projectInfo.getProjectName()); //项目名称
		}
		demandManagement.setRemarks(demandManagementRequest.getRemarks()); //备注
		demandManagement.setDemandStatus(Constant.expense_save); //需求状态保存
		demandManagement.setTimeLong(demandManagementRequest.getTimeLong()); //预计时长
		demandManagement.setExpectDate(DateUtils.parse(demandManagementRequest.getExpectDate(), DateUtils.YYYY_MM_DD_HH)); //期望抵达日期
		demandManagement.setAmountSum(demandManagementRequest.getAmountSum()); //总金额
		demandManagement.setDemandType(demandManagementRequest.getDemandType()); //需求类型
		demandManagement.setDemandPersonelNum(demandManagementRequest.getDemandPersonelNum()); //需求人数
		demandManagement.setBillType(Constant.DEMAND_MANAGEMENT_IMPLEMENT); //实施发起需求
		demandManagement.setDemandName(demandManagementRequest.getDemandName()); //需求主题
		PostInfo postInfo = new PostInfo();
		postInfo = postInfoDao.get(user.getPostIds());
		if(postInfo != null){
			demandManagement.setPostCode(postInfo.getPostCode());
			demandManagement.setPostName(postInfo.getPostName());
		}
		String flowCode = "";
		logger.info("demandBudgetList:"+demandManagementRequest.getDemandBudgetList());
		List<DemandManagementBudgetRequest> demandManagementBudgetRequestList = demandManagementRequest.getDemandBudgetList();
		if(demandManagementBudgetRequestList.size() > 30){
			throw new ServiceException("当前单据明细不能超过30条");
		}
		// 申请发起
		if (StringUtils.isBlank(demandManagementRequest.getProcInsId())){ //判断流程定义ID
			flowCode = FlowUtils.genFlowCode();
			demandManagement.setProcCode(flowCode);
			String title = user.getName()+"_发起实施申请_"+DateUtils.formatDate(demandManagement.getApplyTime(), "yyyyMMdd")+"_"+flowCode;
			demandManagement.setProcName(title);
			demandManagement.setDemandStatus(Constant.expense_approve); //需求状态审批中
			demandManagement.preInsertForWeChart(user);
			demandManagementDao.insert(demandManagement);
			// 启动流程
			String procInsd = actTaskService.startProcessForWechat(ActUtils.OA_IMPLEMENT_FLOW[0], ActUtils.OA_IMPLEMENT_FLOW[1], demandManagement.getId(), title,user);
			
			//自动跳过第一个环节
			Map<String, Object> vars = Maps.newHashMap();
			vars.put("pass", "1");
			Act act = new Act();
			act.setProcInsId(procInsd); //流程实例ID
			act = actTaskService.queryThisRunTaskId(act);
			act.setComment("发起申请");
			actTaskService.complete(act.getTaskId(), act.getProcInsId(), act.getComment(),title, vars);
			
		}else{// 重新编辑申请直接跳转流程
			/*************查询当前任务IDstart************/
			Act act = new Act();
			act.setAssignee(user.getLoginName()); //当前登录用户
			act.setProcInsId(demandManagementRequest.getProcInsId()); //流程实例ID
			act = actTaskService.queryThisRunTaskId(act);
			if(act == null || StringUtils.isBlank(act.getTaskId())){
				throw new ServiceException("找不到当前流程任务信息，禁止提交！");
			}else{
				demandManagement.getAct().setTaskId(act.getTaskId()); 
			}
			/*************查询当前任务IDend************/
			demandManagement.setId(demandManagementRequest.getId());
			demandManagement.getAct().setProcInsId(demandManagementRequest.getProcInsId()); //流程实例ID
			demandManagement.getAct().setComment("重新申请");
			
			// 完成当前流程任务
			Map<String, Object> vars = Maps.newHashMap();
			vars.put("pass", "1"); //设置变量，1：true,0:false
			DemandManagement dm = demandManagementDao.get(demandManagement.getId());
			actTaskService.complete(demandManagement.getAct().getTaskId(), demandManagement.getAct().getProcInsId(), demandManagement.getAct().getComment(), dm.getProcName(), vars);
			demandManagement.setProcCode(dm.getProcCode());
			demandManagement.setProcInsId(dm.getProcInsId());
			demandManagement.setProcName(dm.getProcName());
			demandManagement.setDemandStatus(Constant.expense_approve); //需求申请状态审批中
			demandManagement.preUpdateForWechart(user);
			demandManagementDao.update(demandManagement);
			flowCode = dm.getProcCode(); //获取流程编码
		}
		/*****实施需求申请业务处理end*******/
		
		/*****实施需求申请明细业务处理start*****/
		DemandManagementBudget deleteBudget = new DemandManagementBudget();
		deleteBudget.setProcCode(flowCode);
		demandManagementBudgetDao.deleteForProcCode(deleteBudget); //删除之前的明细数据，再重新保存
		if(demandManagementBudgetRequestList !=null && demandManagementBudgetRequestList.size() > 0){
			for(int i = 0; i < demandManagementBudgetRequestList.size();i++){
				DemandManagementBudgetRequest demandManagementBudgetRequest = demandManagementBudgetRequestList.get(i);
				DemandManagementBudget demandManagementBudget = new DemandManagementBudget();	
				demandManagementBudget.setProcCode(flowCode); //流程编号
				demandManagementBudget.setUserCode(user.getLoginName());
				demandManagementBudget.setStartDate(DateUtils.parseDateFormUnix(demandManagementBudgetRequest.getStartDate(), DateUtils.YYYY_MM_DD)); //开始日期
				if(demandManagementBudgetRequest.getStartPoint() != null && demandManagementBudgetRequest.getStartPoint().length > 1){
					demandManagementBudget.setStartPoint(demandManagementBudgetRequest.getStartPoint()[1]); //起点
				}else{
					demandManagementBudget.setStartPoint(demandManagementBudgetRequest.getStartPoint()[0]); //起点
				}
				demandManagementBudget.setEndDate(DateUtils.parseDateFormUnix(demandManagementBudgetRequest.getEndDate(), DateUtils.YYYY_MM_DD)); //结束日期
				if(demandManagementBudgetRequest.getEndPoint() != null && demandManagementBudgetRequest.getEndPoint().length > 1){
					demandManagementBudget.setEndPoint(demandManagementBudgetRequest.getEndPoint()[1]); //终点
				}else{
					demandManagementBudget.setEndPoint(demandManagementBudgetRequest.getEndPoint()[0]); //终点
				}
				demandManagementBudget.setFirstSub(demandManagementBudgetRequest.getFirstSub()); //一级科目
				demandManagementBudget.setSecondSub(demandManagementBudgetRequest.getSecondSub()); //二级科目
				demandManagementBudget.setPersonNum(Integer.toString(demandManagementBudgetRequest.getPersonNum())); //人数
				demandManagementBudget.setDayNum(demandManagementBudgetRequest.getDayNum()); //天数
				demandManagementBudget.setExpenseAmt(demandManagementBudgetRequest.getExpenseAmt().toString()); //预算金额
				demandManagementBudget.setRemarks(demandManagementBudgetRequest.getRemarks()); //备注
				demandManagementBudget.setRowNum(i);
				demandManagementBudget.preInsertForWeChart(user);
				demandManagementBudgetDao.insert(demandManagementBudget); //保存预算明细
			}
		}
		/******实施需求申请明细业务处理end*****/
	}
	
	
	/**
	 * 市场需求申请服务
	 * @param demandManagementRequest
	 * @param user
	 */
	@Transactional(readOnly = false)
	public void demandMarketApplyService(DemandManagementRequest demandManagementRequest, User user){
		/**********市场需求申请业务处理start*******/
		DemandManagement demandManagement = new DemandManagement();
		demandManagement.setApplyPerCode(user.getLoginName()); //申请人编号
		demandManagement.setApplyPerName(user.getName()); //申请人名称
		demandManagement.setApplyTime(new Date());//申请时间
		demandManagement.setOffice(user.getOffice()); //所属部门
		demandManagement.setOfficeName(user.getOffice().getName()); //所属部门名称
		demandManagement.setProjectId(demandManagementRequest.getProjectId()); //项目ID
		ProjectInfo projectInfo = projectInfoService.get(demandManagementRequest.getProjectId());
		if(projectInfo != null){
			demandManagement.setProjectName(projectInfo.getProjectName()); //项目名称
		}
		demandManagement.setRemarks(demandManagementRequest.getRemarks()); //备注
		demandManagement.setDemandStatus(Constant.expense_save); //需求状态保存
		demandManagement.setTimeLong(demandManagementRequest.getTimeLong()); //预计时长
		demandManagement.setExpectDate(DateUtils.parse(demandManagementRequest.getExpectDate(), DateUtils.YYYY_MM_DD_HH)); //期望抵达日期
		demandManagement.setAmountSum(demandManagementRequest.getAmountSum()); //总金额
		demandManagement.setDemandType(demandManagementRequest.getDemandType()); //需求类型
		demandManagement.setDemandPersonelNum(demandManagementRequest.getDemandPersonelNum()); //需求人数
		demandManagement.setBillType(Constant.DEMAND_MANAGEMENT_MARKET); //市场发起需求
		demandManagement.setDemandName(demandManagementRequest.getDemandName()); //需求主题
		PostInfo postInfo = new PostInfo();
		postInfo = postInfoDao.get(user.getPostIds());
		if(postInfo != null){
			demandManagement.setPostCode(postInfo.getPostCode());
			demandManagement.setPostName(postInfo.getPostName());
		}
		String flowCode = "";
		// 申请发起
		if (StringUtils.isBlank(demandManagementRequest.getProcInsId())){ //判断流程定义ID
			flowCode = FlowUtils.genFlowCode();
			demandManagement.setProcCode(flowCode);
			String title = user.getName()+"_发起市场需求申请_"+DateUtils.formatDate(demandManagement.getApplyTime(), "yyyyMMdd")+"_"+flowCode;
			demandManagement.setProcName(title);
			demandManagement.setDemandStatus(Constant.expense_approve); //需求状态审批中
			demandManagement.preInsertForWeChart(user);
			demandManagementDao.insert(demandManagement);
			// 启动流程
			actTaskService.startProcessForWechat(ActUtils.OA_DEMAND_FLOW[0], ActUtils.OA_DEMAND_FLOW[1], demandManagement.getId(), title,user);
		}
		/*****市场需求申请业务处理end*******/
	}
	
	/**
	 * 实施发起流程
	 */
	@Transactional(readOnly = false)
	public void startWorkFlow(DemandManagement demandManagement,User user){
		// 启动流程
		String procInsd = actTaskService.startProcessForWechat(ActUtils.OA_IMPLEMENT_FLOW[0], ActUtils.OA_IMPLEMENT_FLOW[1], demandManagement.getId(), demandManagement.getProcName(),user);
		
		//自动跳过第一个环节
		Map<String, Object> vars = Maps.newHashMap();
		vars.put("pass", "1");
		Act act = new Act();   
		act.setProcInsId(procInsd); //流程实例ID
		act = actTaskService.queryThisRunTaskId(act);
		act.setComment("发起申请");
		actTaskService.complete(act.getTaskId(), act.getProcInsId(), act.getComment(),demandManagement.getProcName(), vars);
		demandManagement.setDemandStatus(Constant.expense_approve); //需求申请状态审批中
		demandManagementDao.updateDemandStatus(demandManagement);
	}
	
	/**
	 * 实施需求保存服务
	 * @param expenseFlowRequest
	 */
	@Transactional(readOnly = false)
	public void saveDemandImplementInfoService(DemandManagementRequest demandManagementRequest,User user){
		/**********实施需求申请业务处理start*******/
		DemandManagement demandManagement = new DemandManagement();
		demandManagement.setApplyPerCode(user.getLoginName()); //申请人编号
		demandManagement.setApplyPerName(user.getName()); //申请人名称
		demandManagement.setApplyTime(new Date());//申请时间
		demandManagement.setOffice(user.getOffice()); //所属部门
		demandManagement.setOfficeName(user.getOffice().getName()); //所属部门名称
		demandManagement.setProjectId(demandManagementRequest.getProjectId()); //项目ID
		ProjectInfo projectInfo = projectInfoService.get(demandManagementRequest.getProjectId());
		if(projectInfo != null){
			demandManagement.setProjectName(projectInfo.getProjectName()); //项目名称
		}
		demandManagement.setRemarks(demandManagementRequest.getRemarks()); //备注
		demandManagement.setDemandStatus(Constant.expense_save); //需求状态保存
		demandManagement.setTimeLong(demandManagementRequest.getTimeLong()); //预计时长
		demandManagement.setExpectDate(DateUtils.parse(demandManagementRequest.getExpectDate(), DateUtils.YYYY_MM_DD_HH)); //期望抵达日期
		demandManagement.setAmountSum(demandManagementRequest.getAmountSum()); //总金额
		demandManagement.setDemandType(demandManagementRequest.getDemandType()); //需求类型
		demandManagement.setDemandPersonelNum(demandManagementRequest.getDemandPersonelNum()); //需求人数
		demandManagement.setBillType(Constant.DEMAND_MANAGEMENT_IMPLEMENT); //实施发起需求
		demandManagement.setDemandName(demandManagementRequest.getDemandName()); //需求主题
		PostInfo postInfo = new PostInfo();
		postInfo = postInfoDao.get(user.getPostIds());
		if(postInfo != null){
			demandManagement.setPostCode(postInfo.getPostCode());
			demandManagement.setPostName(postInfo.getPostName());
		}
		String flowCode = "";
		logger.info("demandBudgetList:"+demandManagementRequest.getDemandBudgetList());
		List<DemandManagementBudgetRequest> demandManagementBudgetRequestList = demandManagementRequest.getDemandBudgetList();
		if(demandManagementBudgetRequestList.size() > 30){
			throw new ServiceException("当前单据明细不能超过30条");
		}
		// 第一次保存
		if (StringUtils.isBlank(demandManagementRequest.getId())){ //判断单据ID
			flowCode = FlowUtils.genFlowCode();
			demandManagement.setProcCode(flowCode);
			String title = user.getName()+"_发起实施需求_"+DateUtils.formatDate(demandManagement.getApplyTime(), "yyyyMMdd")+"_"+flowCode;
			demandManagement.setProcName(title);
			demandManagement.preInsertForWeChart(user);
			demandManagementDao.insert(demandManagement);
		}else{// 重新保存
			demandManagement.setId(demandManagementRequest.getId());
			
			DemandManagement temp = demandManagementDao.get(demandManagement.getId());			
			flowCode = temp.getProcCode(); //获取流程编码
			demandManagement.setProcCode(flowCode);
			demandManagement.setProcName(temp.getProcName());
			demandManagement.preUpdateForWechart(user);
			demandManagementDao.update(demandManagement);
		}
		/*****实施需求申请业务处理end*******/
		
		/*****实施需求申请明细业务处理start*****/
		DemandManagementBudget deleteBudget = new DemandManagementBudget();
		deleteBudget.setProcCode(flowCode);
		demandManagementBudgetDao.deleteForProcCode(deleteBudget); //删除之前的明细数据，再重新保存
		if(demandManagementBudgetRequestList !=null && demandManagementBudgetRequestList.size() > 0){
			for(int i = 0; i < demandManagementBudgetRequestList.size();i++){
				DemandManagementBudgetRequest demandManagementBudgetRequest = demandManagementBudgetRequestList.get(i);
				DemandManagementBudget demandManagementBudget = new DemandManagementBudget();	
				demandManagementBudget.setProcCode(flowCode); //流程编号
				demandManagementBudget.setUserCode(user.getLoginName());
				demandManagementBudget.setStartDate(DateUtils.parseDateFormUnix(demandManagementBudgetRequest.getStartDate(), DateUtils.YYYY_MM_DD)); //开始日期
				if(demandManagementBudgetRequest.getStartPoint() != null && demandManagementBudgetRequest.getStartPoint().length > 1){
					demandManagementBudget.setStartPoint(demandManagementBudgetRequest.getStartPoint()[1]); //起点
				}else{
					demandManagementBudget.setStartPoint(demandManagementBudgetRequest.getStartPoint()[0]); //起点
				}
				demandManagementBudget.setEndDate(DateUtils.parseDateFormUnix(demandManagementBudgetRequest.getEndDate(), DateUtils.YYYY_MM_DD)); //结束日期
				if(demandManagementBudgetRequest.getEndPoint() != null && demandManagementBudgetRequest.getEndPoint().length > 1){
					demandManagementBudget.setEndPoint(demandManagementBudgetRequest.getEndPoint()[1]); //终点
				}else{
					demandManagementBudget.setEndPoint(demandManagementBudgetRequest.getEndPoint()[0]); //终点
				}
				demandManagementBudget.setFirstSub(demandManagementBudgetRequest.getFirstSub()); //一级科目
				demandManagementBudget.setSecondSub(demandManagementBudgetRequest.getSecondSub()); //二级科目
				demandManagementBudget.setPersonNum(Integer.toString(demandManagementBudgetRequest.getPersonNum())); //人数
				demandManagementBudget.setDayNum(demandManagementBudgetRequest.getDayNum()); //天数
				demandManagementBudget.setExpenseAmt(demandManagementBudgetRequest.getExpenseAmt().toString()); //预算金额
				demandManagementBudget.setRemarks(demandManagementBudgetRequest.getRemarks()); //备注
				demandManagementBudget.setRowNum(i);
				demandManagementBudget.preInsertForWeChart(user);
				demandManagementBudgetDao.insert(demandManagementBudget); //保存预算明细
			}
		}
		/*****实施需求申请明细业务处理end*****/
	}
	
	
	/**
	 * 市场需求保存服务
	 * @param expenseFlowRequest
	 */
	@Transactional(readOnly = false)
	public void saveDemandMarketInfoService(DemandManagementRequest demandManagementRequest,User user){
		/**********市场需求申请业务处理start*******/
		DemandManagement demandManagement = new DemandManagement();
		demandManagement.setApplyPerCode(user.getLoginName()); //申请人编号
		demandManagement.setApplyPerName(user.getName()); //申请人名称
		demandManagement.setApplyTime(new Date());//申请时间
		demandManagement.setOffice(user.getOffice()); //所属部门
		demandManagement.setOfficeName(user.getOffice().getName()); //所属部门名称
		demandManagement.setProjectId(demandManagementRequest.getProjectId()); //项目ID
		ProjectInfo projectInfo = projectInfoService.get(demandManagementRequest.getProjectId());
		if(projectInfo != null){
			demandManagement.setProjectName(projectInfo.getProjectName()); //项目名称
		}
		demandManagement.setRemarks(demandManagementRequest.getRemarks()); //备注
		demandManagement.setDemandStatus(Constant.expense_save); //需求状态保存
		demandManagement.setTimeLong(demandManagementRequest.getTimeLong()); //预计时长
		demandManagement.setExpectDate(DateUtils.parse(demandManagementRequest.getExpectDate(), DateUtils.YYYY_MM_DD_HH)); //期望抵达日期
		demandManagement.setAmountSum(demandManagementRequest.getAmountSum()); //总金额
		demandManagement.setDemandType(demandManagementRequest.getDemandType()); //需求类型
		demandManagement.setDemandPersonelNum(demandManagementRequest.getDemandPersonelNum()); //需求人数
		demandManagement.setBillType(Constant.DEMAND_MANAGEMENT_MARKET); //实施发起需求
		demandManagement.setDemandName(demandManagementRequest.getDemandName()); //需求主题
		PostInfo postInfo = new PostInfo();
		postInfo = postInfoDao.get(user.getPostIds());
		if(postInfo != null){
			demandManagement.setPostCode(postInfo.getPostCode()); 
			demandManagement.setPostName(postInfo.getPostName());
		}
		String flowCode = "";
		// 第一次保存
		if (StringUtils.isBlank(demandManagementRequest.getId())){ //判断单据ID
			flowCode = FlowUtils.genFlowCode();
			demandManagement.setProcCode(flowCode);
			String title = user.getName()+"_发起市场需求申请_"+DateUtils.formatDate(demandManagement.getApplyTime(), "yyyyMMdd")+"_"+flowCode;
			demandManagement.setProcName(title);
			demandManagement.preInsertForWeChart(user);
			demandManagementDao.insert(demandManagement);
		}else{// 重新保存
			demandManagement.setId(demandManagementRequest.getId());
			DemandManagement temp = demandManagementDao.get(demandManagement.getId());			
			flowCode = temp.getProcCode(); //获取流程编码
			demandManagement.setProcCode(flowCode);
			demandManagement.setProcName(temp.getProcName());
			demandManagement.preUpdateForWechart(user);
			demandManagementDao.update(demandManagement);
		}
		/*****市场需求申请业务处理end*******/
	}
	
	
	/**
	 * 实施需求-审核审批保存
	 * @param testAudit
	 */
	@Transactional(readOnly = false)
	public void completeTaskImplement(DemandManagement demandManagement,User user) {
		// 设置审批意见
		demandManagement.getAct().setComment(("yes".equals(demandManagement.getAct().getFlag())?"[同意] ":"[驳回] ")+demandManagement.getAct().getComment());
		String flag = demandManagement.getAct().getFlag();
		// 提交流程任务
		Map<String, Object> vars = Maps.newHashMap();
		vars.put("pass", "yes".equals(demandManagement.getAct().getFlag())? "1" : "0");
		
		if("no".equals(flag)){
			Act act = new Act();
			act.setProcInsId(demandManagement.getAct().getProcInsId()); //流程实例ID
			act = actTaskService.queryThisRunTaskId(act); //查询当前任务信息
			
			//查询开始环节任务
			String targetTaskDefinitionKey = "modify";
			actTaskService.jumpTaskAndAddComent(demandManagement.getAct().getProcInsId(),act.getTaskId(),demandManagement.getAct().getComment(),user.getLoginName(),targetTaskDefinitionKey, vars);
		}else{
			actTaskService.complete(demandManagement.getAct().getTaskId(), demandManagement.getAct().getProcInsId(), demandManagement.getAct().getComment(), vars);
		}
		//判断流程是否退回并更新单据状态
		if("no".equals(flag)){ 
			demandManagement.setDemandStatus(Constant.expense_back); //状态被退回
			demandManagementDao.updateDemandStatus(demandManagement);
		}
		
		//判断流程是否结束并更新单据状态
		Act e = new Act();
		e.setProcInsId(demandManagement.getAct().getProcInsId()); //流程实例ID
		e = actTaskService.queryThisRunTaskId(e);
		if(e == null){
			//流程已结束
			demandManagement.setFlowFinishTime(new Date());
			demandManagement.setDemandStatus(Constant.expense_approve_end); //状态为审批结束
			demandManagementDao.updateDemandStatusAndFlowFinshTime(demandManagement);
		}
	}
	
	
	/**
	 * 市场需求-审核审批保存
	 * @param testAudit
	 */
	@Transactional(readOnly = false)
	public void completeTaskMarket(DemandManagement demandManagement,User user) {
		
		if(demandManagement.getDemandBudgetList() != null && demandManagement.getDemandBudgetList().size() > 0){
			//删除之前的明细信息
			DemandManagementBudget db = new DemandManagementBudget();
			db.setProcCode(demandManagement.getProcCode());
			db.setUserCode(user.getLoginName());
			demandManagementBudgetDao.deleteForProcCode(db);
			for(int i = 0;i < demandManagement.getDemandBudgetList().size();i++){
				DemandManagementBudget demandManagementBudget = demandManagement.getDemandBudgetList().get(i);
				demandManagementBudget.setProcCode(demandManagement.getProcCode());
				demandManagementBudget.setRowNum(i);
				demandManagementBudget.setUserCode(user.getLoginName());
				demandManagementBudget.preInsertForWeChart(user);
				demandManagementBudgetDao.insert(demandManagementBudget);
			}
		}
		
		// 设置审批意见
		demandManagement.getAct().setComment(("yes".equals(demandManagement.getAct().getFlag())?"[同意] ":"[驳回] ")+demandManagement.getAct().getComment());
		String flag = demandManagement.getAct().getFlag();
		// 提交流程任务
		Map<String, Object> vars = Maps.newHashMap();
		vars.put("pass", "yes".equals(demandManagement.getAct().getFlag())? "1" : "0");
		//设置指派人员
		if(demandManagement.getEmployees() != null){
			List<String> employeeList = new ArrayList<String>();
			for(String employee:demandManagement.getEmployees()){
				employeeList.add(employee);
			}
			vars.put("employeeList",employeeList);
		}
		if("no".equals(flag)){
			//查询开始环节任务
			String targetTaskDefinitionKey = Constant.LAST_NODE_KEY;
			actTaskService.jumpTaskAndAddComentForChild(demandManagement.getAct().getProcInsId(),demandManagement.getAct().getTaskId(),demandManagement.getAct().getComment(),user.getLoginName(),targetTaskDefinitionKey, vars);
		}else{
			/******************动态设置审批执行者start***************/
			if(demandManagement.getAct().getTaskDefKey().startsWith(Constant.DEMAND_ASSIGNEE_NODE)){
				DemandAssign demandAssign = new DemandAssign();
				demandAssign.setProcCode(demandManagement.getProcCode());
				demandAssign.setTargetAssign(user.getLoginName());
				/************查询发起分配人员************/
				demandAssign = demandAssignService.querySourceAssignByTargetAssign(demandAssign);
				if(demandAssign != null){
					vars.put("demandAssignee",demandAssign.getSourceAssign());
				}
			}
			/******************动态设置审批执行者end***************/
			
			actTaskService.complete(demandManagement.getAct().getTaskId(), demandManagement.getAct().getProcInsId(), demandManagement.getAct().getComment(), vars);
		}
		//判断流程是否退回并更新单据状态
//		if("no".equals(flag)){ 
//			demandManagement.setDemandStatus(ExpenseConstatnt.demand_back); //状态被退回
//			demandManagementDao.updateDemandStatus(demandManagement);
//		}
		
		//判断流程是否结束并更新单据状态
		Act e = new Act();
		e.setProcInsId(demandManagement.getAct().getProcInsId()); //流程实例ID
		List<Act> actList = actTaskService.queryThisRunTaskIdList(e);
		if(actList == null || actList.size() == 0){
			//流程已结束
			demandManagement.setFlowFinishTime(new Date());
			demandManagement.setDemandStatus(Constant.expense_approve_end); //状态为审批结束
			demandManagementDao.updateDemandStatusAndFlowFinshTime(demandManagement);
		}else{
			if(demandManagement.getEmployees() != null){
				/******************保存分配办理记录start********************/
				for(String employee:demandManagement.getEmployees()){
					DemandAssign demandAssign = new DemandAssign();
					demandAssign.setProcCode(demandManagement.getProcCode()); //流程编号
					demandAssign.setSourceAssign(user.getLoginName()); //指派发起人
					demandAssign.setSourceAssignPost(user.getPostIds()); //指派发起人所属岗位
					demandAssign.setTargetAssign(employee); //被指派者 
					demandAssign.setTargetAssignPost(""); //被指派者所属岗位
					demandAssign.setSourceTaskId(demandManagement.getAct().getTaskId()); //发起人任务ID
					demandAssign.setSourceTaskName(demandManagement.getAct().getTaskDefKey());//发起人节点名称
					demandAssign.setIsLast(Constant.IS_NOT_LAST); //是否是最末级受理人
					for(Act a:actList){
						if(employee.equals(a.getAssignee())){
							demandAssign.setTargetTaskId(a.getTaskId()); //被指派者任务ID
							demandAssign.setTargetTaskName(a.getTaskDefKey()); //被指派者节点名称
							if(Constant.LAST_NODE_KEY.equals(a.getTaskDefKey())){
								demandAssign.setIsLast(Constant.IS_LAST);
							}
							break;
						}
					}
					//保存分配信息
					demandAssignService.save(demandAssign);
				}
			/******************保存分配办理记录end********************/	
			}
		}
	}
	
}
