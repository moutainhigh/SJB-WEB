package sijibao.oa.jeesite.modules.intfz.service.resources;

import java.rmi.ServerException;
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
import com.sijibao.oa.modules.flow.dao.ResourcesApplyDao;
import com.sijibao.oa.modules.flow.dao.ResourcesAssignDao;
import com.sijibao.oa.modules.flow.entity.ResourcesApply;
import com.sijibao.oa.modules.flow.entity.ResourcesAssign;
import com.sijibao.oa.modules.flow.utils.FlowUtils;
import com.sijibao.oa.modules.intfz.request.resources.ResourcesApplyRequest;
import com.sijibao.oa.modules.intfz.request.resources.ResourcesAssignRequest;
import com.sijibao.oa.modules.intfz.response.intfzwebresp.ResourcesAssignResponse;
import com.sijibao.oa.modules.intfz.response.resources.ResourcesApplyFlowResponse;
import com.sijibao.oa.modules.intfz.utils.Constant;
import com.sijibao.oa.modules.intfz.validator.IntfzValidateUtils;
import com.sijibao.oa.modules.oa.entity.ProjectInfo;
import com.sijibao.oa.modules.oa.service.ProjectInfoService;
import com.sijibao.oa.modules.sys.dao.PostInfoDao;
import com.sijibao.oa.modules.sys.entity.PostInfo;
import com.sijibao.oa.modules.sys.entity.User;
import com.sijibao.oa.modules.sys.utils.UserUtils;

/**
 * 资源申请流程服务层
 * @author xuby
 */
@Service
@Transactional(readOnly = true)
public class IntfzResourcesApplyService extends BaseService {
	
	@Autowired
	private ResourcesApplyDao resourcesApplyDao;
	@Autowired
	private ActTaskService actTaskService;
	@Autowired
	private PostInfoDao postInfoDao;
	@Autowired
	private ProjectInfoService projectInfoService;
	@Autowired
	private ResourcesAssignDao resourcesAssignDao;
	@Autowired
	private IntfzResourcesHandleFlowService intfzResourcesHandleFlowService;
	/**
	 * 资源申请服务
	 * @param resourcesApplyRequest
	 * @param user
	 */
	@Transactional(readOnly = false)
	public void resourcesApplyService(ResourcesApplyRequest resourcesApplyRequest, User user){
		try {
			/**********资源申请业务处理start*******/
			IntfzValidateUtils.valid(resourcesApplyRequest);
			ResourcesApply resourcesApply = new ResourcesApply();
			resourcesApply.setProducSide(resourcesApplyRequest.getProducSide());
			resourcesApply.setApplyPerCode(user.getLoginName()); //申请人编号
			resourcesApply.setApplyPerName(user.getName()); //申请人名称
			resourcesApply.setApplyTime(new Date());//申请时间
			resourcesApply.setOffice(user.getOffice()); //所属部门
			resourcesApply.setOfficeName(user.getOffice().getName()); //所属部门名称
			resourcesApply.setProjectId(resourcesApplyRequest.getProjectId()); //项目ID
			ProjectInfo projectInfo = projectInfoService.get(resourcesApplyRequest.getProjectId());
			if(projectInfo != null){
				resourcesApply.setProjectName(projectInfo.getProjectName()); //项目名称
				if(projectInfo.getProjectLeader() != null){
					resourcesApply.setProjectPersonel(projectInfo.getProjectLeader().getName());
				}
			}
			resourcesApply.setResourcesType(resourcesApplyRequest.getResourcesType()); //资源类型
			if(StringUtils.isBlank(resourcesApplyRequest.getRemarks())){
				resourcesApply.setRemarks(""); //备注
			}else{
				resourcesApply.setRemarks(resourcesApplyRequest.getRemarks()); //备注
			}
			resourcesApply.setResourcesStatus(Constant.expense_save); //资源状态保存
			resourcesApply.setTimeLong(resourcesApplyRequest.getTimeLong()); //预计时长
			resourcesApply.setExpectDate(DateUtils.parseDate(resourcesApplyRequest.getExpectDate())); //期望抵达日期
			resourcesApply.setAmountSum(resourcesApplyRequest.getAmountSum()); //总金额
			resourcesApply.setDemandPersonelNum(resourcesApplyRequest.getDemandPersonelNum()); //资源人数
			
			PostInfo postInfo = new PostInfo();
			postInfo = postInfoDao.get(user.getPostIds());
			if(postInfo != null){
				resourcesApply.setPostCode(postInfo.getPostCode());
				resourcesApply.setPostName(postInfo.getPostName());
			}
            if(resourcesApply.getRemarks() == null){
                resourcesApply.setRemarks("");
            }
            // 申请发起
            String flowCode = "";
            if (StringUtils.isBlank(resourcesApplyRequest.getProcInsId())){ //判断流程定义ID
				flowCode = FlowUtils.genFlowCode();
				resourcesApply.setProcCode(flowCode);
				String title = user.getName()+"_资源申请_"+DateUtils.formatDate(resourcesApply.getApplyTime(), "yyyyMMdd")+"_"+flowCode;
				resourcesApply.setProcName(title);
				resourcesApply.setResourcesStatus(Constant.expense_approve); //资源状态审批中
				resourcesApply.preInsertForWeChart(user);
				resourcesApplyDao.insert(resourcesApply);
				
				// 启动流程
				String procInsd = actTaskService.startProcessForWechat(ActUtils.OA_RESOURCES_APPLY_FLOW[0], ActUtils.OA_RESOURCES_APPLY_FLOW[1], resourcesApply.getId(), title,user);
				
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
				act.setProcInsId(resourcesApplyRequest.getProcInsId()); //流程实例ID
				act = actTaskService.queryThisRunTaskId(act);
				if(act == null || StringUtils.isBlank(act.getTaskId())){
					throw new ServiceException("找不到当前流程任务信息，禁止提交！");
				}else{
					resourcesApply.getAct().setTaskId(act.getTaskId()); 
				}
				/*************查询当前任务IDend************/
				resourcesApply.setId(resourcesApplyRequest.getId());
				resourcesApply.getAct().setProcInsId(resourcesApplyRequest.getProcInsId()); //流程实例ID
				resourcesApply.getAct().setComment("重新申请");
				
				// 完成当前流程任务
				Map<String, Object> vars = Maps.newHashMap();
				vars.put("pass", "1"); //设置变量，1：true,0:false
				ResourcesApply dm = resourcesApplyDao.get(resourcesApply.getId());
				resourcesApply.setProcCode(dm.getProcCode());
				resourcesApply.setProcInsId(dm.getProcInsId());
				resourcesApply.setProcName(dm.getProcName());
				resourcesApply.setResourcesStatus(Constant.expense_approve); //资源申请状态审批中
				resourcesApply.preUpdateForWechart(user);
				resourcesApplyDao.update(resourcesApply);
				flowCode = dm.getProcCode(); //获取流程编码
				actTaskService.complete(resourcesApply.getAct().getTaskId(), resourcesApply.getAct().getProcInsId(), resourcesApply.getAct().getComment(), dm.getProcName(), vars);
			}
			/*****资源申请业务处理end*******/
		} catch (ServerException e) {
			throw new ServiceException(e.getMessage());
		} 
	}
	
	
	/**
	 * 资源申请保存服务
	 * @param resourcesApplyRequest
	 */
	@Transactional(readOnly = false)
	public void saveResourceApplyInfoService(ResourcesApplyRequest resourcesApplyRequest,User user){
		/**********资源申请申请业务处理start*******/
		ResourcesApply resourcesApply = new ResourcesApply();
		resourcesApply.setApplyPerCode(user.getLoginName()); //申请人编号
		resourcesApply.setApplyPerName(user.getName()); //申请人名称
		resourcesApply.setApplyTime(new Date());//申请时间
		resourcesApply.setOffice(user.getOffice()); //所属部门
		resourcesApply.setOfficeName(user.getOffice().getName()); //所属部门名称
		resourcesApply.setProjectId(resourcesApplyRequest.getProjectId()); //项目ID
		ProjectInfo projectInfo = projectInfoService.get(resourcesApplyRequest.getProjectId());
		if(projectInfo != null){
			resourcesApply.setProjectName(projectInfo.getProjectName()); //项目名称
			if(projectInfo.getProjectLeader() != null){
				resourcesApply.setProjectPersonel(projectInfo.getProjectLeader().getName());
			}
		}
		resourcesApply.setResourcesType(resourcesApplyRequest.getResourcesType()); //资源类型
		if(StringUtils.isBlank(resourcesApplyRequest.getRemarks())){
			resourcesApply.setRemarks(""); //备注
		}else{
			resourcesApply.setRemarks(resourcesApplyRequest.getRemarks()); //备注
		}
		resourcesApply.setResourcesStatus(Constant.expense_save); //资源状态保存
		resourcesApply.setTimeLong(resourcesApplyRequest.getTimeLong()); //预计时长
		resourcesApply.setExpectDate(DateUtils.parseDate(resourcesApplyRequest.getExpectDate())); //期望抵达日期
		resourcesApply.setAmountSum(resourcesApplyRequest.getAmountSum()); //总金额
		resourcesApply.setDemandPersonelNum(resourcesApplyRequest.getDemandPersonelNum()); //需求人数
		PostInfo postInfo = new PostInfo();
		postInfo = postInfoDao.get(user.getPostIds());
		if(postInfo != null){
			resourcesApply.setPostCode(postInfo.getPostCode());
			resourcesApply.setPostName(postInfo.getPostName());
		}
        if(resourcesApply.getRemarks() == null){
            resourcesApply.setRemarks("");
        }
        String flowCode = "";
		// 第一次保存
		if (StringUtils.isBlank(resourcesApplyRequest.getId())){ //判断单据ID
			flowCode = FlowUtils.genFlowCode();
			resourcesApply.setProcCode(flowCode);
			String title = user.getName()+"_资源申请_"+DateUtils.formatDate(resourcesApply.getApplyTime(), "yyyyMMdd")+"_"+flowCode;
			resourcesApply.setProcName(title);
			resourcesApply.preInsertForWeChart(user);
			resourcesApplyDao.insert(resourcesApply);
		}else{// 重新保存
			resourcesApply.setId(resourcesApplyRequest.getId());
			
			ResourcesApply temp = resourcesApplyDao.get(resourcesApply.getId());			
			flowCode = temp.getProcCode(); //获取流程编码
			resourcesApply.setProcCode(flowCode);
			resourcesApply.setProcName(temp.getProcName());
			resourcesApply.preUpdateForWechart(user);
			resourcesApplyDao.update(resourcesApply);
		}
		/*****资源申请申请业务处理end*******/
	}
	
	/**
	 * 草稿发起流程
	 * @param resourcesApply
	 * @param user
	 */
	@Transactional(readOnly = false)
	public void startWorkFlow(ResourcesApply resourcesApply,User user){
		// 启动流程
		String procInsd = actTaskService.startProcessForWechat(ActUtils.OA_RESOURCES_APPLY_FLOW[0], ActUtils.OA_RESOURCES_APPLY_FLOW[1], resourcesApply.getId(), resourcesApply.getProcName(),user);
		
		//自动跳过第一个环节
		Map<String, Object> vars = Maps.newHashMap();
		vars.put("pass", "1");
		Act act = new Act();   
		act.setProcInsId(procInsd); //流程实例ID
		act = actTaskService.queryThisRunTaskId(act);
		act.setComment("发起申请");
		actTaskService.complete(act.getTaskId(), act.getProcInsId(), act.getComment(),resourcesApply.getProcName(), vars);
		resourcesApply.setResourcesStatus(Constant.expense_approve); //资源申请状态审批中
		resourcesApplyDao.updateResApplyStatus(resourcesApply);
	}
	
	/**
	 * 审核审批保存
	 * @param testAudit
	 */
	@Transactional(readOnly = false)
	public void resourcesApplyCompleteTask(ResourcesApply resourcesApply,User user,List<ResourcesAssignRequest> assignList) {
		//校验指派人员是否重复
		if(intfzResourcesHandleFlowService.checkResourcesAssign(assignList)){
			throw new ServiceException("当前单据指派人员不能重复,请仔细核对指派人员信息"); 
		}
		// 设置审批意见
		resourcesApply.getAct().setComment(("yes".equals(resourcesApply.getAct().getFlag())?"[同意] ":"[驳回] ")+resourcesApply.getAct().getComment());
		String flag = resourcesApply.getAct().getFlag();
		// 提交流程任务
		Map<String, Object> vars = Maps.newHashMap();
		vars.put("pass", "yes".equals(resourcesApply.getAct().getFlag())? "1" : "0");
		
		if("no".equals(flag)){
			Act act = new Act();
			act.setProcInsId(resourcesApply.getAct().getProcInsId()); //流程实例ID
			act = actTaskService.queryThisRunTaskId(act); //查询当前任务信息
			
			//查询开始环节任务
			String targetTaskDefinitionKey = "modify";
			actTaskService.jumpTaskAndAddComent(resourcesApply.getAct().getProcInsId(),act.getTaskId(),resourcesApply.getAct().getComment(),user.getLoginName(),targetTaskDefinitionKey, vars);
			//判断流程是否退回并更新资源申请状态
			resourcesApply.setResourcesStatus(Constant.expense_back); //资源申请状态被退回
			resourcesApplyDao.updateResApplyStatus(resourcesApply);
		}else{
			actTaskService.complete(resourcesApply.getAct().getTaskId(), resourcesApply.getAct().getProcInsId(), resourcesApply.getAct().getComment(), vars);
		}
		
		ResourcesAssign resourcesAssign = new ResourcesAssign();
		resourcesAssign.setProcCode(resourcesApply.getProcCode());
		resourcesAssignDao.deleteForProcCode(resourcesAssign); //删除之前的指派数据，再重新保存
		resourcesAssign.setSourceAssign(user.getId());
		resourcesAssign.setSourceAssignPost(user.getPostIds());
		resourcesAssign.setSourceTaskId(resourcesApply.getAct().getTaskId());
		resourcesAssign.setSourceTaskName(resourcesApply.getAct().getTaskName());
		//保存指派人员信息
		for(ResourcesAssignRequest r:assignList){
			resourcesAssign.setTargetAssign(r.getTargetAssign());
			resourcesAssign.setPersonelNum(r.getPersonelNum());
			if(StringUtils.isBlank(r.getRemarks())){
				resourcesAssign.setRemarks("");
			}else{
				resourcesAssign.setRemarks(r.getRemarks());
			}
			resourcesAssign.preInsertForWeChart(user);
			resourcesAssignDao.insert(resourcesAssign);
		}
		
		//判断流程是否结束并更新资源申请状态
		Act e = new Act();
		e.setProcInsId(resourcesApply.getAct().getProcInsId()); //流程实例ID
		e = actTaskService.queryThisRunTaskId(e);
		if(e == null){
			//流程已结束
			resourcesApply.setFlowFiniskTime(new Date());
			resourcesApply.setResourcesStatus(Constant.expense_approve_end); //资源申请状态审批结束
			resourcesApplyDao.updateResApplyStatusAndFlowFinshTime(resourcesApply);
		}
	}
	
	/**
	 * 资源申请单据明细(主表数据)
	 * @param recpFlowResponse
	 * @param recpFlow
	 * @param curUser
	 * @param act
	 * @return
	 */
	public ResourcesApplyFlowResponse resourcesApplyFlowDetail(ResourcesApplyFlowResponse resourcesApplyFlowResponse,ResourcesApply resourcesApply,User curUser){
		logger.info("=======resourcesApplyFlowDetail recpFlow============="+resourcesApply.toString());
		resourcesApplyFlowResponse = new ResourcesApplyFlowResponse(resourcesApply);
		//查询申请人所属岗位
		User u = UserUtils.getByLoginName(resourcesApply.getApplyPerCode());
		PostInfo postInfo = postInfoDao.get(u.getPostIds());
		resourcesApplyFlowResponse.setPostName(postInfo.getPostName());
		//查询项目负责人
		if(StringUtils.isNotBlank(resourcesApply.getProjectId())){
			ProjectInfo projectInfo = projectInfoService.get(resourcesApply.getProjectId());
			if(projectInfo != null && projectInfo.getProjectLeader() != null){
				resourcesApplyFlowResponse.setProjectPersonel(projectInfo.getProjectLeader().getName());
			}
		}
		
		//查询指派明细信息
		List<ResourcesAssignResponse> assignList = new ArrayList<ResourcesAssignResponse>();
		ResourcesAssign resourcesAssign = new ResourcesAssign();
		resourcesAssign.setProcCode(resourcesApplyFlowResponse.getProcCode());
		List<ResourcesAssign> resourcesAssignList = resourcesAssignDao.findList(resourcesAssign);
		if(resourcesAssignList != null && resourcesAssignList.size() > 0){
			for(ResourcesAssign r:resourcesAssignList){
				ResourcesAssignResponse resourcesAssignResponse = new ResourcesAssignResponse();
				resourcesAssignResponse.setSourceAssign(r.getSourceAssign());
				resourcesAssignResponse.setSourceAssignName(r.getSourceUser().getName());
				resourcesAssignResponse.setTargetAssign(r.getTargetAssign());
				resourcesAssignResponse.setTargetAssignName(r.getTargetUser().getName());
				resourcesAssignResponse.setPersonelNum(r.getPersonelNum());
				if(StringUtils.isBlank(r.getRemarks())){
					resourcesAssignResponse.setRemarks("");
				}else{
					resourcesAssignResponse.setRemarks(r.getRemarks());
				}

				resourcesAssignResponse.setUpdateTime(DateUtils.format(r.getUpdateDate(), DateUtils.YYYY_MM_DD_HH_MM_SS));
				assignList.add(resourcesAssignResponse);
			}
		}
		resourcesApplyFlowResponse.setAssignList(assignList);
		//比较是否是当前人
		if(StringUtils.equals(curUser.getLoginName(), resourcesApplyFlowResponse.getApplyPerCode())){
			resourcesApplyFlowResponse.setLocal(1);
		}
		//判断当前环节是否可以编辑页面
		resourcesApplyFlowResponse.setIsDeit(0);
		if(Constant.expense_save.equals(resourcesApply.getResourcesStatus())){
			resourcesApplyFlowResponse.setIsDeit(1);
			resourcesApplyFlowResponse.setModify(Constant.DEFAULT_NOE_MODIFY);
		}
		Act a = new Act();
		a.setProcInsId(resourcesApply.getProcInsId());
		a = actTaskService.queryThisRunTaskId(a);
		if(a != null && Constant.DEFAULT_NOE_MODIFY.equals(a.getTaskDefKey()) && StringUtils.equals(curUser.getLoginName(), a.getAssignee())){
			resourcesApplyFlowResponse.setIsDeit(1);
			resourcesApplyFlowResponse.setModify(Constant.DEFAULT_NOE_MODIFY);
		}
		return resourcesApplyFlowResponse;
	}
	
	/**
	 * 删除需求申请
	 */
	@Transactional(readOnly = false)
	public void deleteResourcesApply(ResourcesApply resourcesApply) {
		if(Constant.expense_save.equals(resourcesApply.getResourcesStatus())){
			//删除人员指派记录
			resourcesApplyDao.deleteResApplyInfo(resourcesApply);
		}else{
			if(StringUtils.isNotBlank(resourcesApply.getProcInsId())){
				//删除任务
				actTaskService.deleteRunTask(resourcesApply.getProcInsId()); //删除运行任务
				actTaskService.deleteHisTask(resourcesApply.getProcInsId()); //删除历史任务
			}
			resourcesApplyDao.delete(resourcesApply);
			//修改单据状态为已删除
			resourcesApply.setResourcesStatus(Constant.expense_delete); //需求申请状态被删除
			resourcesApplyDao.updateResApplyStatus(resourcesApply);
		}
	}
	/**
	 * 撤销流程接口
	 * @param resourcesApply
	 * @param user
	 */
	@Transactional(readOnly = false)
	public void repealTask(ResourcesApply resourcesApply, User user) {
		/********判断当前节点的下一个节点任务是否已经完成*********/
		
		/**********流程跳转，发起撤销start********/
		Map<String, Object> vars = Maps.newHashMap();
		vars.put("pass", "1");
		
		//查询被撤销的节点信息
		Act act = new Act();
		act.setProcInsId(resourcesApply.getAct().getProcInsId()); //流程实例ID
		act = actTaskService.queryThisRunTaskId(act); //查询当前任务信息
		
		//查询发起撤销节点key
		Act targetAct = new Act();
		if("0".equals(resourcesApply.getAct().getTaskId())){
			targetAct.setTaskDefKey("modify");
		}else{
			targetAct.setTaskId(resourcesApply.getAct().getTaskId());
			targetAct.setProcInsId(resourcesApply.getAct().getProcInsId());
			targetAct = actTaskService.queryHisTask(targetAct);
		}
		
		String targetTaskDefinitionKey = targetAct.getTaskDefKey(); //发起撤销的节点key
		actTaskService.jumpTaskAndAddComent(resourcesApply.getAct().getProcInsId(), act.getTaskId(),
				"[流程撤回]当前审核任务被"+user.getName()+"撤回", user.getLoginName(), targetTaskDefinitionKey, vars);
		/**********流程跳转，发起撤销end********/
	}
}
