package sijibao.oa.jeesite.modules.intfz.service.intfzweb;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
import com.sijibao.oa.modules.act.dao.ActDao;
import com.sijibao.oa.modules.act.entity.Act;
import com.sijibao.oa.modules.act.service.ActTaskService;
import com.sijibao.oa.modules.act.utils.ActUtils;
import com.sijibao.oa.modules.flow.dao.FlowProcRoleResolveDao;
import com.sijibao.oa.modules.flow.dao.OpenAccountAttachmentDao;
import com.sijibao.oa.modules.flow.dao.OpenAccountFlowDao;
import com.sijibao.oa.modules.flow.entity.OpenAccountAttachment;
import com.sijibao.oa.modules.flow.entity.OpenAccountFlow;
import com.sijibao.oa.modules.flow.service.OpenAccountFlowService;
import com.sijibao.oa.modules.flow.utils.FlowUtils;
import com.sijibao.oa.modules.intfz.request.common.ActRequest;
import com.sijibao.oa.modules.intfz.request.openaccountflow.OpenAccountAttachmentRequest;
import com.sijibao.oa.modules.intfz.request.openaccountflow.OpenAccountFlowHandleReq;
import com.sijibao.oa.modules.intfz.request.openaccountflow.OpenAccountFlowRequest;
import com.sijibao.oa.modules.intfz.response.openaccountflow.OpenAccountFlowDetailResponse;
import com.sijibao.oa.modules.intfz.response.openaccountflow.OpenAccountFlowResponse;
import com.sijibao.oa.modules.intfz.utils.Constant;
import com.sijibao.oa.modules.sys.dao.PostInfoDao;
import com.sijibao.oa.modules.sys.entity.PostInfo;
import com.sijibao.oa.modules.sys.entity.User;
import com.sijibao.oa.modules.sys.utils.DictUtils;
import com.sijibao.oa.modules.sys.utils.UserUtils;

/**
 * 开户申请流程服务层
 * @author wanxb
 */
@Service
@Transactional(readOnly = true)
public class IntfzWebOpenAccountFlowService extends BaseService {
	
	@Autowired
	private OpenAccountFlowDao openAccountFlowDao;
	@Autowired
	private ActTaskService actTaskService;
	@Autowired
	private PostInfoDao postInfoDao;
	@Autowired
	private OpenAccountFlowService openAccountFlowService;
	@Autowired
	private OpenAccountAttachmentDao openAccountAttachmentDao;
	@Autowired
	private FlowProcRoleResolveDao flowProcRoleResolveDao;
	@Autowired
	private ActDao actDao;
	/**
	 * 开户申请服务
	 * @param resourcesApplyRequest
	 * @param user
	 */
	@Transactional(readOnly = false)
	public void openAccountFlowService(OpenAccountFlowRequest req, User user){
		try {
			/**********资源申请业务处理start*******/
			OpenAccountFlow open = new OpenAccountFlow();
			open.setApplyPerCode(user.getLoginName()); //申请人编号
			open.setApplyPerName(user.getName()); //申请人名称
			open.setApplyTime(new Date());//申请时间
			open.setOffice(user.getOffice()); //所属部门
			open.setOfficeName(user.getOffice().getName()); //所属部门名称
			open.setOpenAccountCode(req.getOpenAccountCode());
			open.setContractCode(req.getContractCode());
			open.setContractName(req.getContractCode());
			open.setFirstPartyName(req.getFirstPartyName());
			if(StringUtils.isNotBlank(req.getMarketLeaderId()) ){
				open.setMarketLeader(UserUtils.get(req.getMarketLeaderId()));
			}
			ArrayList<OpenAccountAttachment> list = Lists.newArrayList();
			if(req.getOpenAccountAttachmentRequest() != null){
				for (OpenAccountAttachmentRequest at : req.getOpenAccountAttachmentRequest()) {
					OpenAccountAttachment ac = new OpenAccountAttachment();
					ac.setOpenAccountCode(open.getOpenAccountCode());
					ac.setOpenAccountAttachmentUrl(at.getOpenAccountAttachmentUrl());
					ac.setFileName(at.getFileName());
					ac.setFileType(at.getFileType());
					list.add(ac);
				}
			}
			
			open.setOpenAccountAttachment(list);
			
			PostInfo postInfo = new PostInfo();
			postInfo = postInfoDao.get(user.getPostIds());
			if(postInfo != null){
				open.setPostCode(postInfo.getPostCode());
				open.setPostName(postInfo.getPostName());
			}
			String flowCode = "";
			// 申请发起
			if (StringUtils.isBlank(req.getProcInsId())){ //判断流程定义ID
				flowCode = FlowUtils.genFlowCode();
				open.setProcCode(flowCode);
				String title = user.getName()+"_开户申请_"+DateUtils.formatDate(open.getApplyTime(), "yyyyMMdd")+"_"+flowCode;
				open.setProcName(title);
				open.setStatus(Constant.expense_approve); //资源状态审批中
				open.preInsertForWeChart(user);
				open.setOpenAccountStatus("0");
				openAccountFlowDao.insert(open);
				
				// 启动流程
				String procInsd = actTaskService.startProcessForWechat(ActUtils.OA_OPEN_ACCOUNT_FLOW[0], ActUtils.OA_OPEN_ACCOUNT_FLOW[1], open.getId(), title,user);
				
				//自动跳过第一个环节
				Map<String, Object> vars = Maps.newHashMap();
				vars.put("pass", "1");
				Act act = new Act();
				act.setProcInsId(procInsd); //流程实例ID
				act = actTaskService.queryThisRunTaskId(act);
				act.setComment("发起申请");
				List<String> employeeList = flowProcRoleResolveDao.queryUserForRoleCode(ActUtils.OA_OPEN_ACCOUNT_FLOW[2]);
				vars.put("employeeList", employeeList);
				actTaskService.complete(act.getTaskId(), act.getProcInsId(), act.getComment(),title, vars);
				
			}else{// 重新编辑申请直接跳转流程
				/*************查询当前任务IDstart************/
				Map<String, Object> vars = Maps.newHashMap();
				Act act = new Act();
				act.setAssignee(user.getLoginName()); //当前登录用户
				act.setProcInsId(req.getProcInsId()); //流程实例ID
				act = actTaskService.queryThisRunTaskId(act);
				List<String> employeeList = actDao.queryThisHiTaskIdForExecutionId(req.getProcInsId());
				vars.put("employeeList", employeeList);
				if(act == null || StringUtils.isBlank(act.getTaskId())){
					throw new ServiceException("找不到当前流程任务信息，禁止提交！");
				}else{
					open.getAct().setTaskId(act.getTaskId()); 
				}
				/*************查询当前任务IDend************/
				open.setId(req.getId());
				open.getAct().setProcInsId(req.getProcInsId()); //流程实例ID
				open.getAct().setComment("重新申请");
				
				// 完成当前流程任务
				vars.put("pass", "1"); //设置变量，1：true,0:false
				OpenAccountFlow flow = openAccountFlowDao.get(open.getId());
				open.setProcCode(flow.getProcCode());
				open.setProcInsId(flow.getProcInsId());
				open.setProcName(flow.getProcName());
				open.setStatus(Constant.expense_approve); //资源申请状态审批中
				open.preUpdateForWechart(user);
				openAccountFlowDao.update(open);
				flowCode = flow.getProcCode(); //获取流程编码
				actTaskService.complete(open.getAct().getTaskId(), open.getAct().getProcInsId(), open.getAct().getComment(), flow.getProcName(), vars);
			}
			/*****资源申请业务处理end*******/
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		} 
	}
	
	
	/**
	 * 开户申请保存服务
	 * @param OpenAccountFlowRequest
	 */
	@Transactional(readOnly = false)
	public void saveOpenAccountFlowInfoService(OpenAccountFlowRequest req,User user){
		/**********资源申请申请业务处理start*******/
		OpenAccountFlow open = new OpenAccountFlow();
		open.setApplyPerCode(user.getLoginName()); //申请人编号
		open.setApplyPerName(user.getName()); //申请人名称
		open.setApplyTime(new Date());//申请时间
		open.setOffice(user.getOffice()); //所属部门
		open.setOfficeName(user.getOffice().getName()); //所属部门名称
		
		open.setOpenAccountCode(req.getOpenAccountCode());
		open.setContractName(req.getContractName());
		open.setFirstPartyName(req.getFirstPartyName());
		if(StringUtils.isNotBlank(req.getMarketLeaderId())){
			open.setMarketLeader(UserUtils.get(req.getMarketLeaderId()));
		}
		
		PostInfo postInfo = new PostInfo();
		postInfo = postInfoDao.get(user.getPostIds());
		if(postInfo != null){
			open.setPostCode(postInfo.getPostCode());
			open.setPostName(postInfo.getPostName());
		}
		String flowCode = "";
		// 第一次保存
		if (StringUtils.isBlank(req.getId())){ //判断单据ID
			flowCode = FlowUtils.genFlowCode();
			open.setProcCode(flowCode);
			String title = user.getName()+"_开户_"+DateUtils.formatDate(open.getApplyTime(), "yyyyMMdd")+"_"+flowCode;
			open.setProcName(title);
			open.preInsertForWeChart(user);
			openAccountFlowDao.insert(open);
			openAccountAttachmentDao.deleteAttachments(open.getOpenAccountCode());
			for (OpenAccountAttachment attachment : open.getOpenAccountAttachment()) {
				attachment.preInsertForWeChart(user);
				openAccountAttachmentDao.insert(attachment);
			}
		}else{// 重新保存
			open.setId(req.getId());
			
			OpenAccountFlow temp = openAccountFlowDao.get(open.getId());			
			flowCode = temp.getProcCode(); //获取流程编码
			open.setProcCode(flowCode);
			open.setProcName(temp.getProcName());
			open.preUpdateForWechart(user);
			openAccountFlowDao.update(open);
			openAccountAttachmentDao.deleteAttachments(open.getOpenAccountCode());
			for (OpenAccountAttachment attachment : open.getOpenAccountAttachment()) {
				attachment.preInsertForWeChart(user);
				openAccountAttachmentDao.insert(attachment);
			}
			
		}
		/*****资源申请申请业务处理end*******/
	}
	
	/**
	 * 草稿发起流程
	 * @param resourcesApply
	 * @param user
	 */
	@Transactional(readOnly = false)
	public void startWorkFlow(OpenAccountFlow open,User user){
		// 启动流程
		String procInsd = actTaskService.startProcessForWechat(ActUtils.OA_RESOURCES_APPLY_FLOW[0], ActUtils.OA_RESOURCES_APPLY_FLOW[1], open.getId(), open.getProcName(),user);
		
		//自动跳过第一个环节
		Map<String, Object> vars = Maps.newHashMap();
		vars.put("pass", "1");
		Act act = new Act();   
		act.setProcInsId(procInsd); //流程实例ID
		act = actTaskService.queryThisRunTaskId(act);
		act.setComment("发起申请");
		actTaskService.complete(act.getTaskId(), act.getProcInsId(), act.getComment(),open.getProcName(), vars);
		open.setStatus(Constant.expense_approve); //资源申请状态审批中
		openAccountFlowDao.updateOpenAccountFlowStatus(open);
	}
	
	/**
	 * 审核审批保存
	 * @param testAudit
	 */
	@Transactional(readOnly = false)
	public void openAccountFlowCompleteTask(OpenAccountFlow open,User user) {
		
		// 设置审批意见
		open.getAct().setComment(("yes".equals(open.getAct().getFlag())?"[同意] ":"[驳回] ")+open.getAct().getComment());
		String flag = open.getAct().getFlag();
		// 提交流程任务
		Map<String, Object> vars = Maps.newHashMap();
		vars.put("pass", "yes".equals(open.getAct().getFlag())? "1" : "0");
		
		if("no".equals(flag)){
			Act act = new Act();
			act.setProcInsId(open.getAct().getProcInsId()); //流程实例ID
			act = actTaskService.queryThisRunTaskId(act); //查询当前任务信息
			
			//查询开始环节任务
			String targetTaskDefinitionKey = "modify";
			actTaskService.jumpTaskAndAddComent(open.getAct().getProcInsId(),act.getTaskId(),open.getAct().getComment(),user.getLoginName(),targetTaskDefinitionKey, vars);
		}else{
			actTaskService.complete(open.getAct().getTaskId(), open.getAct().getProcInsId(), open.getAct().getComment(), vars);
		}
		
		//判断流程是否退回并更新资源申请状态
		if("no".equals(flag)){ 
			open.setStatus(Constant.expense_back); //开户申请状态被退回
			openAccountFlowDao.updateOpenAccountFlowStatus(open);
		}
		
		//判断流程是否结束并更新资源申请状态
		Act e = new Act();
		e.setProcInsId(open.getAct().getProcInsId()); //流程实例ID
		e = actTaskService.queryThisRunTaskId(e);
		if(e == null){
			//流程已结束
			open.setFlowFinishTime(new Date());
			open.setStatus(Constant.expense_approve_end); //开户申请状态审批结束
			open.setOpenAccountCode("1");
			openAccountFlowDao.updateStatusAndFlowFinshTime(open);
		}
	}
	public Page<OpenAccountFlowResponse> findPage(Page<OpenAccountFlow> page, OpenAccountFlowHandleReq req, User user) {
		OpenAccountFlow flow = new OpenAccountFlow();
		flow.setProcInsId(req.getProcInsId());
		flow.setStatus(req.getStatus());
		flow.setCode(req.getCode());
		flow.setFirstPartyName(req.getFirstPartyName());
		flow.setMarketLeaderName(req.getMarketLeaderName());
		if(req.getUpdateTimeStart() != 0l){
			flow.setUpdateTimeStart(DateUtils.parseDateFormUnix(req.getUpdateTimeStart(), DateUtils.YYYY_MM_DD_HH_MM_SS));
		}
		if(req.getUpdateTimeEnd() != 0l){
			flow.setUpdateTimeEnd(DateUtils.parseDateFormUnix(req.getUpdateTimeEnd(), DateUtils.YYYY_MM_DD_HH_MM_SS));
		}
		Page<OpenAccountFlow> findPage = openAccountFlowService.findPage(page, flow);
		Page<OpenAccountFlowResponse> newPage = new Page<OpenAccountFlowResponse>();
		newPage.setPageNo(findPage.getPageNo());
		newPage.setPageSize(findPage.getPageSize());
		newPage.setCount(findPage.getCount());
		ArrayList<OpenAccountFlowResponse> list = Lists.newArrayList();
		for (OpenAccountFlow f : findPage.getList()) {
			OpenAccountFlowResponse o = new OpenAccountFlowResponse();
			o.setId(f.getId());
			o.setOpenAccountCode(f.getOpenAccountCode());
			o.setContractCode(f.getContractCode());
			o.setFirstPartyName(f.getFirstPartyName());
			if(f.getMarketLeader() != null && StringUtils.isNotBlank(f.getMarketLeader().getId())){
				f.setMarketLeader(UserUtils.get(f.getMarketLeader().getId()));
				o.setMarketLeaderName(f.getMarketLeader().getName());
			}
			if(StringUtils.isNotBlank(f.getOpenAccountStatus())){
				o.setOpenAccountStatusValue(DictUtils.getDictLabel(f.getOpenAccountStatus(), "open_account_status", ""));
			}
			list.add(o);
		}
		newPage.setList(list);
		return newPage;
		
	}
	/**
	 * 开户申请单据明细(主表数据)
	 * @param openAccountFlow
	 */
	public OpenAccountFlowDetailResponse openAccountFlowDetail(OpenAccountFlowDetailResponse openAccountFlowDetailResponse,OpenAccountFlow open,User user,ActRequest act){
		logger.info("=======openAccountFlow openAccountFlow============="+open.toString());
		openAccountFlowDetailResponse = new OpenAccountFlowDetailResponse(open);
		//查询申请人所属岗位
		User u = UserUtils.getByLoginName(open.getApplyPerCode());
		PostInfo postInfo = postInfoDao.get(u.getPostIds());
		openAccountFlowDetailResponse.setPostName(postInfo.getPostName());
		//比较是否是当前人
		if(StringUtils.equals(user.getLoginName(), openAccountFlowDetailResponse.getApplyPerCode())){
			openAccountFlowDetailResponse.setLocal(1);
		}
		act.setProcInsId(open.getProcInsId()); //设置流程实例ID
		//判断当前环节是否可以编辑页面
		openAccountFlowDetailResponse.setIsDeit(0);
		if(Constant.expense_save.equals(open.getStatus())){
			openAccountFlowDetailResponse.setIsDeit(1);
			openAccountFlowDetailResponse.setModify(Constant.DEFAULT_NOE_MODIFY);
		}
		Act a = new Act();
		a.setProcInsId(open.getProcInsId());
		a = actTaskService.queryThisRunTaskId(a);
		if(a != null && Constant.DEFAULT_NOE_MODIFY.equals(a.getTaskDefKey()) && StringUtils.equals(user.getLoginName(), a.getAssignee())){
			openAccountFlowDetailResponse.setIsDeit(1);
			openAccountFlowDetailResponse.setModify(Constant.DEFAULT_NOE_MODIFY);
		}
		List<OpenAccountAttachment> list = openAccountAttachmentDao.findByopenAccountCode(open);
		openAccountFlowDetailResponse.setOpenAccountAttachment(list);
		return openAccountFlowDetailResponse;
	}

}
