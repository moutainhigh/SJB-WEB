package sijibao.oa.jeesite.modules.intfz.service.resources;

import java.rmi.ServerException;
import java.text.ParseException;
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
import com.sijibao.oa.modules.act.entity.Act;
import com.sijibao.oa.modules.act.service.ActTaskService;
import com.sijibao.oa.modules.act.utils.ActUtils;
import com.sijibao.oa.modules.flow.dao.ResourcesApplyDao;
import com.sijibao.oa.modules.flow.dao.ResourcesAssignDao;
import com.sijibao.oa.modules.flow.dao.ResourcesHandleFlowDao;
import com.sijibao.oa.modules.flow.entity.ResourcesApply;
import com.sijibao.oa.modules.flow.entity.ResourcesAssign;
import com.sijibao.oa.modules.flow.entity.ResourcesHandleFlow;
import com.sijibao.oa.modules.flow.service.ResourcesHandleFlowService;
import com.sijibao.oa.modules.flow.utils.FlowUtils;
import com.sijibao.oa.modules.intfz.request.resources.ResourcesAssignRequest;
import com.sijibao.oa.modules.intfz.request.resources.ResourcesHandleFlowListReq;
import com.sijibao.oa.modules.intfz.request.resources.ResourcesHandleFlowRequest;
import com.sijibao.oa.modules.intfz.response.common.FlowLogResponse;
import com.sijibao.oa.modules.intfz.response.intfzwebresp.ResourcesAssignResponse;
import com.sijibao.oa.modules.intfz.response.resources.ResourcesHandleFlowListResponse;
import com.sijibao.oa.modules.intfz.response.resources.ResourcesHandleFlowResponse;
import com.sijibao.oa.modules.intfz.utils.Constant;
import com.sijibao.oa.modules.intfz.validator.IntfzValidateUtils;
import com.sijibao.oa.modules.oa.entity.ProjectInfo;
import com.sijibao.oa.modules.oa.service.ProjectInfoService;
import com.sijibao.oa.modules.sys.dao.PostInfoDao;
import com.sijibao.oa.modules.sys.dao.UserDao;
import com.sijibao.oa.modules.sys.entity.PostInfo;
import com.sijibao.oa.modules.sys.entity.User;
import com.sijibao.oa.modules.sys.utils.DictUtils;
import com.sijibao.oa.modules.sys.utils.UserUtils;

/**
 * 资源办理流程服务层
 * @author xuby
 */
@Service
@Transactional(readOnly = true)
public class IntfzResourcesHandleFlowService extends BaseService{
	
	@Autowired
	private ActTaskService actTaskService;
	@Autowired
	private ResourcesHandleFlowDao resourcesHandleFlowDao;
	@Autowired
	private ResourcesHandleFlowService resourcesHandleFlowService;
	@Autowired
	private ProjectInfoService projectInfoService;
	@Autowired
	private ResourcesAssignDao resourcesAssignDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private PostInfoDao postInfoDao;
	@Autowired
	private ResourcesApplyDao resourcesApplyDao;
	
	/**
	 * 资源办理服务
	 * @param resourcesHandleFlowRequest
	 * @throws ParseException 
	 */
	@Transactional(readOnly = false)
	public void resourcesHandleApplyService(ResourcesHandleFlowRequest resourcesHandleFlowRequest, User user){
		try {
			/**********资源办理流程业务处理start*******/
			IntfzValidateUtils.valid(resourcesHandleFlowRequest);
			if(resourcesHandleFlowRequest.getAssignList() == null || resourcesHandleFlowRequest.getAssignList().size() == 0){
				throw new ServiceException("指派人员为空，禁止提交"); 
			}
			//校验指派人员是否重复
			if(checkResourcesAssign(resourcesHandleFlowRequest.getAssignList())){
				throw new ServiceException("当前单据指派人员不能重复,请仔细核对指派人员信息"); 
			}
			List<ResourcesAssignRequest> resourcesAssignRequestList = resourcesHandleFlowRequest.getAssignList();
			ResourcesHandleFlow resourcesHandleFlow = new ResourcesHandleFlow();
			resourcesHandleFlow.setProducSide(resourcesHandleFlowRequest.getProducSide());
			ResourcesApply resourcesApply = new ResourcesApply();
			if(Constant.HANDLE_TYPE_TWO.equals(resourcesHandleFlowRequest.getHandleType()) && StringUtils.isNotBlank(resourcesHandleFlowRequest.getRelationTheme())){
				resourcesApply = resourcesApplyDao.getByProcCode(resourcesHandleFlowRequest.getRelationTheme());
			}
			resourcesHandleFlow.setApplyPerCode(user.getLoginName()); //申请人编号
			resourcesHandleFlow.setApplyPerName(user.getName()); //申请人名称
			resourcesHandleFlow.setApplyTime(new Date());//申请时间
			resourcesHandleFlow.setOffice(user.getOffice()); //所属部门
			resourcesHandleFlow.setProjectId(resourcesHandleFlowRequest.getProjectId()); //项目ID
			resourcesHandleFlow.setHandleType(resourcesHandleFlowRequest.getHandleType()); //办理类型
			if(StringUtils.isNotBlank(user.getPostIds())){
				PostInfo postInfo = new PostInfo();
				postInfo = postInfoDao.get(user.getPostIds());
				resourcesHandleFlow.setPostCode(postInfo.getPostCode());
				resourcesHandleFlow.setPostName(postInfo.getPostName());
			}
			ProjectInfo projectInfo = projectInfoService.get(resourcesHandleFlowRequest.getProjectId());
			if(projectInfo != null){
				resourcesHandleFlow.setProjectName(projectInfo.getProjectName());
				if(projectInfo.getProjectLeader() != null){
					resourcesHandleFlow.setProjectPersonel(projectInfo.getProjectLeader().getName()); //项目负责人
				}
			}
			if(StringUtils.isBlank(resourcesHandleFlowRequest.getRemarks())){
				resourcesHandleFlow.setRemarks(""); //备注
			}else{
				resourcesHandleFlow.setRemarks(resourcesHandleFlowRequest.getRemarks()); //备注
			}

			resourcesHandleFlow.setResourcesHandleStatus(Constant.expense_save); //资源办理状态保存
			if(Constant.HANDLE_TYPE_TWO.equals(resourcesHandleFlowRequest.getHandleType()) && resourcesApply!=null){
				resourcesHandleFlow.setResourcesType(resourcesApply.getResourcesType());
				resourcesHandleFlow.setTimeLong(resourcesApply.getTimeLong()); //预计时长
				resourcesHandleFlow.setExpectDate(resourcesApply.getExpectDate()); //期望抵达日期
				resourcesHandleFlow.setAmountSum(resourcesApply.getAmountSum()); //总金额
				resourcesHandleFlow.setPersonelNum(Integer.parseInt(resourcesApply.getDemandPersonelNum())); 
			}else{
				resourcesHandleFlow.setResourcesType(resourcesHandleFlowRequest.getResourcesType());
				resourcesHandleFlow.setTimeLong(resourcesHandleFlowRequest.getTimeLong()); //预计时长
				resourcesHandleFlow.setExpectDate(DateUtils.parseDate(resourcesHandleFlowRequest.getExpectDate())); // 期望抵达日期
				resourcesHandleFlow.setAmountSum(resourcesHandleFlowRequest.getAmountSum()); //总金额
				int totalPersonelNum = 0;
				if(resourcesAssignRequestList != null){  //判断明细是否为空
					for(ResourcesAssignRequest resourcesAssignRequest:resourcesAssignRequestList){
						totalPersonelNum += resourcesAssignRequest.getPersonelNum();
					}
				}
				resourcesHandleFlow.setPersonelNum(totalPersonelNum);
			}
			if(resourcesHandleFlow.getRemarks() == null){
			    resourcesHandleFlow.setRemarks("");
            }
			String flowCode = "";
			resourcesHandleFlow.setRelationTheme(resourcesHandleFlowRequest.getRelationTheme()); //申请主题
			List<Act> actList = new ArrayList<Act>();
			// 申请发起
			if (StringUtils.isBlank(resourcesHandleFlowRequest.getProcInsId())){ //判断流程定义ID
				flowCode = FlowUtils.genFlowCode();
				resourcesHandleFlow.setProcCode(flowCode);
				String title = user.getName()+"_资源办理_"+DateUtils.formatDate(resourcesHandleFlow.getApplyTime(), "yyyyMMdd")+"_"+flowCode;
				resourcesHandleFlow.setProcName(title);
				resourcesHandleFlow.setResourcesHandleStatus(Constant.expense_approve); //资源办理状态审批中
				resourcesHandleFlow.preInsertForWeChart(user);
				resourcesHandleFlowDao.insert(resourcesHandleFlow);
				Map<String, Object> vars = Maps.newHashMap();
				// 启动流程
				String procInsd = actTaskService.startProcessForResourcesHandle(ActUtils.OA_RESOURCES_HANDLE_FLOW[0], ActUtils.OA_RESOURCES_HANDLE_FLOW[1], resourcesHandleFlow.getId(), title,user,vars);
				resourcesHandleFlow.setProcInsId(procInsd);
				//设置指派人员
				if(Constant.HANDLE_TYPE_TWO.equals(resourcesHandleFlow.getHandleType()) 
						&& resourcesAssignRequestList !=null 
						&& resourcesAssignRequestList.size() > 0){
					List<String> employeeList = new ArrayList<String>();
					for(int i = 0; i < resourcesAssignRequestList.size();i++){
						User u = UserUtils.get(resourcesAssignRequestList.get(i).getTargetAssign());
						employeeList.add(u.getLoginName());
					}
					vars.put("employeeList",employeeList);
				}
				//自动跳过第一个环节
				vars.put("pass", "1");
				Act act = new Act();
				act.setProcInsId(procInsd); //流程实例ID
				act = actTaskService.queryThisRunTaskId(act);
				act.setComment("发起申请");
				actTaskService.complete(act.getTaskId(), act.getProcInsId(), act.getComment(),title, vars);
				//查询当前流程的执行者信息
				Act a = new Act();
				a.setProcInsId(resourcesHandleFlow.getProcInsId());
				actList = actTaskService.queryThisRunTaskIdListForProcInsId(a);
				
			}else{// 重新编辑申请直接跳转流程
				/*************查询当前任务IDstart************/
				Act act = new Act();
				act.setAssignee(user.getLoginName()); //当前登录用户
				act.setProcInsId(resourcesHandleFlowRequest.getProcInsId()); //流程实例ID
				act = actTaskService.queryThisRunTaskId(act);
				if(act == null || StringUtils.isBlank(act.getTaskId())){
					throw new ServiceException("找不到当前流程任务信息，禁止提交！");
				}else{
					resourcesHandleFlow.getAct().setTaskId(act.getTaskId()); 
				}
				/*************查询当前任务IDend************/
				resourcesHandleFlow.setId(resourcesHandleFlowRequest.getId());
				resourcesHandleFlow.getAct().setProcInsId(resourcesHandleFlowRequest.getProcInsId()); //流程实例ID
				resourcesHandleFlow.getAct().setComment("重新申请");
				
				ResourcesHandleFlow temp =resourcesHandleFlowDao.get(resourcesHandleFlow.getId());
				resourcesHandleFlow.setProcCode(temp.getProcCode());
				resourcesHandleFlow.setProcInsId(temp.getProcInsId());
				resourcesHandleFlow.setProcName(temp.getProcName());
				resourcesHandleFlow.setResourcesHandleStatus(Constant.expense_approve); //资源办理状态审批中
				resourcesHandleFlow.preUpdateForWechart(user);
				resourcesHandleFlowDao.update(resourcesHandleFlow);
				flowCode = temp.getProcCode(); //获取流程编码
				// 完成当前流程任务
				Map<String, Object> vars = Maps.newHashMap();
				vars.put("pass", "1"); //设置变量，1：true,0:false
				//设置指派人员
				if(Constant.HANDLE_TYPE_TWO.equals(resourcesHandleFlow.getHandleType()) 
						&& resourcesAssignRequestList !=null 
						&& resourcesAssignRequestList.size() > 0){
					List<String> employeeList = new ArrayList<String>();
					for(int i = 0; i < resourcesAssignRequestList.size();i++){
						User u = UserUtils.get(resourcesAssignRequestList.get(i).getTargetAssign());
						employeeList.add(u.getLoginName());
					}
					vars.put("employeeList",employeeList);
				}
				actTaskService.complete(resourcesHandleFlow.getAct().getTaskId(), resourcesHandleFlow.getAct().getProcInsId(), resourcesHandleFlow.getAct().getComment(), temp.getProcName(), vars);
				//查询当前流程的执行者信息
				Act a = new Act();
				a.setProcInsId(resourcesHandleFlow.getProcInsId());
				actList = actTaskService.queryThisRunTaskIdListForProcInsId(a);
			}
			/*****资源办理流程业务处理end*******/
			
			
			/*****资源办理指派人员业务处理start*****/
			ResourcesAssign resourcesAssign = new ResourcesAssign();
			resourcesAssign.setProcCode(flowCode);
			resourcesAssignDao.deleteForProcCode(resourcesAssign); //删除之前的明细数据，再重新保存
			resourcesAssign.setSourceAssign(user.getId());
			resourcesAssign.setSourceAssignPost(user.getPostIds());
			resourcesAssign.setSourceTaskId("start");
			resourcesAssign.setSourceTaskName("start");
			if(resourcesAssignRequestList !=null && resourcesAssignRequestList.size() > 0){
				for(int i = 0; i < resourcesAssignRequestList.size();i++){
					ResourcesAssignRequest resourcesAssignRequest = resourcesAssignRequestList.get(i);
					IntfzValidateUtils.valid(resourcesAssignRequest);
					resourcesAssign.setTargetAssign(resourcesAssignRequest.getTargetAssign());
					User targetAssign = UserUtils.get(resourcesAssignRequest.getTargetAssign()); 
					resourcesAssign.setPersonelNum(resourcesAssignRequest.getPersonelNum());

					if(StringUtils.isBlank(resourcesAssignRequest.getRemarks())){
						resourcesAssign.setRemarks("");
					}else {
						resourcesAssign.setRemarks(resourcesAssignRequest.getRemarks());
					}

					if(actList != null && actList.size() > 0){
						for(Act a:actList){
							if(a.getAssignee().equals(targetAssign.getLoginName())){
								resourcesAssign.setTargetTaskId(a.getTaskId());
								resourcesAssign.setTargetTaskName(a.getTaskDefKey());
							}
						}
					}
					resourcesAssign.preInsertForWeChart(user);
					resourcesAssignDao.insert(resourcesAssign);
				}
			}
			/*****资源办理指派人员业务处理end*****/
		} catch (ServerException e) {
			throw new ServiceException(e.getMessage());
		} 
	}
	
	/**
	 * 资源申请办理列表页面
	 * @param page
	 * @param req
	 * @param user
	 * @return
	 * @throws ParseException 
	 */
	public Page<ResourcesHandleFlowListResponse> findPage(Page<ResourcesHandleFlow> page, ResourcesHandleFlowListReq req,
			User user,String clientType) {
		ResourcesHandleFlow resours = new ResourcesHandleFlow();
		resours.setId(req.getId());
		resours.setBeginApplyTime(DateUtils.parseDate(req.getStart()));
		resours.setEndApplyTime(DateUtils.parseDate(req.getEnd()));
		resours.setProjectId(req.getProjectId());
		if(StringUtils.isNotBlank(req.getProcCode())){
			resours.setProcCode(req.getProcCode());
		}
		if(StringUtils.isNotBlank(req.getProcName())){
			resours.setProcName(req.getProcName());
		}
		if(Constant.CLIENT_TYPE_WEB.equals(clientType)){
			if(StringUtils.isNotBlank(req.getResourcesHandleStatus())){
				resours.setResourcesHandleStatus(req.getResourcesHandleStatus());
			}
		}
		if(Constant.CLIENT_TYPE_APP.equals(clientType)){
			if(Constant.expense_approve.equals(String.valueOf(req.getResourcesHandleStatus()))){ //运行中搜索包括审批中和被驳回
				resours.setResourcesHandleStatusIn("2,3");
				resours.setResourcesHandleStatus("");
			}else if(Constant.expense_approve_end.equals(String.valueOf(req.getResourcesHandleStatus()))){
				resours.setResourcesHandleStatusIn("1,0");
				resours.setResourcesHandleStatus("");
			}else{
				resours.setResourcesHandleStatusIn("4");
			}
		}
		resours.setCreateBy(user);
		Page<ResourcesHandleFlow> find = resourcesHandleFlowService.findPage(page, resours);
		Page<ResourcesHandleFlowListResponse> newPage = new Page<ResourcesHandleFlowListResponse>();
		newPage.setCount(find.getCount());
		newPage.setPageNo(find.getPageNo());
		newPage.setPageSize(find.getPageSize());
		ArrayList<ResourcesHandleFlowListResponse> list = Lists.newArrayList();
		for (ResourcesHandleFlow re : find.getList()) {
			ResourcesHandleFlowListResponse rhr = new ResourcesHandleFlowListResponse();
			rhr.setId(re.getId());
			rhr.setProcCode(re.getProcCode());
			rhr.setProcName(re.getProcName());
			rhr.setApplyPerName(re.getApplyPerName());
			rhr.setHandleType(re.getHandleType());
			rhr.setHandleTypeValue(re.getHandleTypeValue());
			rhr.setAmountSum(re.getAmountSum());
			rhr.setResourcesType(re.getResourcesType());
			rhr.setResourcesTypeValue(re.getResourcesTypeValue());
			rhr.setProjectId(re.getProjectId());
			rhr.setProjectName(re.getProjectName());
			if(re.getApplyTime() != null){
				rhr.setApplyTime(DateUtils.format(re.getApplyTime(), DateUtils.YYYY_MM_DD_HH_MM_SS));
			}
			if(re.getPersonelNum() != null){
				rhr.setPersonelNum(re.getPersonelNum());
			}
			rhr.setResourcesHandleStatus(re.getResourcesHandleStatus());
			rhr.setResourcesHandleStatusValue(re.getResourcesHandleStatusValue());
			list.add(rhr);
		}
		newPage.setList(list);
		return newPage;
	}
	
	/**
	 * 资源申请办理关联列表页面
	 * @param page
	 * @param req
	 * @param user
	 * @return
	 * @throws ParseException 
	 */
	public Page<ResourcesHandleFlowListResponse> findRelationPage(Page<ResourcesHandleFlow> page, ResourcesHandleFlowListReq req,
			User user) {
		ResourcesHandleFlow resours = new ResourcesHandleFlow();
		resours.setId(req.getId());
		resours.setBeginApplyTime(DateUtils.parseDate(req.getStart()));
		resours.setEndApplyTime(DateUtils.parseDate(req.getEnd()));
		if(StringUtils.isNotBlank(req.getProcCode())){
			resours.setProcCode(req.getProcCode());
		}
		if(StringUtils.isNotBlank(req.getProcName())){
			resours.setProcName(req.getProcName());
		}
		if(StringUtils.isNotBlank(req.getResourcesHandleStatus())){
			resours.setResourcesHandleStatus(req.getResourcesHandleStatus());
		}
//		resours.setCreateBy(user);
		resours.setTargetAssign(user.getId());
		Page<ResourcesHandleFlow> find = resourcesHandleFlowService.findRelationPage(page, resours);
		Page<ResourcesHandleFlowListResponse> newPage = new Page<ResourcesHandleFlowListResponse>();
		newPage.setCount(find.getCount());
		newPage.setPageNo(find.getPageNo());
		newPage.setPageSize(find.getPageSize());
		ArrayList<ResourcesHandleFlowListResponse> list = Lists.newArrayList();
		for (ResourcesHandleFlow re : find.getList()) {
			ResourcesHandleFlowListResponse rhr = new ResourcesHandleFlowListResponse();
			rhr.setId(re.getId());
			rhr.setProcCode(re.getProcCode());
			rhr.setProcName(re.getProcName());
			rhr.setApplyPerName(re.getApplyPerName());
			rhr.setHandleType(re.getHandleType());
			rhr.setHandleTypeValue(re.getHandleTypeValue());
			rhr.setProjectId(re.getProjectId());
			rhr.setProjectName(re.getProjectName());
			rhr.setProjectPersonel(re.getProjectPersonel());
			rhr.setResourcesType(re.getResourcesType());
			rhr.setResourcesTypeValue(re.getResourcesTypeValue());
			if(re.getApplyTime() != null){
				rhr.setApplyTime(DateUtils.format(re.getApplyTime(), DateUtils.YYYY_MM_DD_HH_MM_SS));
			}
			if(re.getPersonelNum() != null){
				rhr.setPersonelNum(re.getPersonelNum());
			}
			rhr.setResourcesHandleStatus(re.getResourcesHandleStatus());
			rhr.setResourcesHandleStatusValue(re.getResourcesHandleStatusValue());
			list.add(rhr);
		}
		newPage.setList(list);
		return newPage;
	}
	
	/**
	 * 资源办理保存服务
	 * @param resourcesApplyRequest
	 * @throws ParseException
	 */
	@Transactional(readOnly = false)
	public void saveResourceHandleService(ResourcesHandleFlowRequest resourcesHandleFlowRequest,User user){
		/**********资源办理流程业务处理start*******/
		ResourcesHandleFlow resourcesHandleFlow = new ResourcesHandleFlow();
		resourcesHandleFlow.setApplyPerCode(user.getLoginName()); //申请人编号
		resourcesHandleFlow.setApplyPerName(user.getName()); //申请人名称
		resourcesHandleFlow.setApplyTime(new Date());//申请时间
		resourcesHandleFlow.setOffice(user.getOffice()); //所属部门
		resourcesHandleFlow.setProjectId(resourcesHandleFlowRequest.getProjectId()); //项目ID
		resourcesHandleFlow.setHandleType(resourcesHandleFlowRequest.getHandleType()); //办理类型
		ResourcesApply resourcesApply = new ResourcesApply();
		List<ResourcesAssignRequest> resourcesAssignRequestList = resourcesHandleFlowRequest.getAssignList();
		if(Constant.HANDLE_TYPE_TWO.equals(resourcesHandleFlowRequest.getHandleType()) && StringUtils.isNotBlank(resourcesHandleFlowRequest.getRelationTheme())){
			resourcesApply = resourcesApplyDao.getByProcCode(resourcesHandleFlowRequest.getRelationTheme());
		}
		ProjectInfo projectInfo = projectInfoService.get(resourcesHandleFlowRequest.getProjectId());
		if(projectInfo != null){
			resourcesHandleFlow.setProjectName(projectInfo.getProjectName());
		}
		if(StringUtils.isNotBlank(user.getPostIds())){
			PostInfo postInfo = new PostInfo();
			postInfo = postInfoDao.get(user.getPostIds());
			resourcesHandleFlow.setPostCode(postInfo.getPostCode());
			resourcesHandleFlow.setPostName(postInfo.getPostName());
		}
		if(StringUtils.isBlank(resourcesHandleFlowRequest.getRemarks())){
			resourcesHandleFlow.setRemarks(""); //备注
		}else{
			resourcesHandleFlow.setRemarks(resourcesHandleFlowRequest.getRemarks()); //备注
		}

		resourcesHandleFlow.setResourcesHandleStatus(Constant.expense_save); //资源办理状态保存
		if(Constant.HANDLE_TYPE_TWO.equals(resourcesHandleFlowRequest.getHandleType()) && resourcesApply!=null){
			resourcesHandleFlow.setResourcesType(resourcesApply.getResourcesType());
			resourcesHandleFlow.setTimeLong(resourcesApply.getTimeLong()); //预计时长
			resourcesHandleFlow.setExpectDate(resourcesApply.getExpectDate()); //期望抵达日期
			resourcesHandleFlow.setAmountSum(resourcesApply.getAmountSum()); //总金额
			resourcesHandleFlow.setPersonelNum(Integer.parseInt(resourcesApply.getDemandPersonelNum()));
		}else{
			resourcesHandleFlow.setResourcesType(resourcesHandleFlowRequest.getResourcesType());
			resourcesHandleFlow.setTimeLong(resourcesHandleFlowRequest.getTimeLong()); //预计时长
			resourcesHandleFlow.setExpectDate(DateUtils.parseDate(resourcesHandleFlowRequest.getExpectDate())); // 期望抵达日期
			resourcesHandleFlow.setAmountSum(resourcesHandleFlowRequest.getAmountSum()); //总金额
			int totalPersonelNum = 0;
			if(resourcesAssignRequestList != null){  //判断明细是否为空
				for(ResourcesAssignRequest resourcesAssignRequest:resourcesAssignRequestList){
					totalPersonelNum += resourcesAssignRequest.getPersonelNum();
				}
			}
			resourcesHandleFlow.setPersonelNum(totalPersonelNum);
		}
        if(resourcesHandleFlow.getRemarks() == null){
            resourcesHandleFlow.setRemarks("");
        }
		resourcesHandleFlow.setRelationTheme(resourcesHandleFlowRequest.getRelationTheme()); //申请主题
		if(projectInfo.getProjectLeader()!=null){
			resourcesHandleFlow.setProjectPersonel(projectInfo.getProjectLeader().getName()); //项目负责人
		}
        String flowCode = "";
		// 申请发起
		if (StringUtils.isBlank(resourcesHandleFlowRequest.getId())){ //判断流程定义ID
			flowCode = FlowUtils.genFlowCode();
			resourcesHandleFlow.setProcCode(flowCode);
			String title = user.getName()+"_资源办理_"+DateUtils.formatDate(resourcesHandleFlow.getApplyTime(), "yyyyMMdd")+"_"+flowCode;
			resourcesHandleFlow.setProcName(title);
			resourcesHandleFlow.preInsertForWeChart(user);
			resourcesHandleFlowDao.insert(resourcesHandleFlow);
		}else{// 重新编辑申请直接跳转流程
			resourcesHandleFlow.setId(resourcesHandleFlowRequest.getId());
			ResourcesHandleFlow temp =resourcesHandleFlowDao.get(resourcesHandleFlow.getId());
			
			resourcesHandleFlow.setProcCode(temp.getProcCode());
			resourcesHandleFlow.setProcName(temp.getProcName());
			resourcesHandleFlow.preUpdateForWechart(user);
			resourcesHandleFlowDao.update(resourcesHandleFlow);
			flowCode = temp.getProcCode(); //获取流程编码
		}
		/*****资源办理流程业务处理end*******/
		
		/*****资源办理指派人员业务处理start*****/
		ResourcesAssign resourcesAssign = new ResourcesAssign();
		resourcesAssign.setProcCode(flowCode);
		resourcesAssignDao.deleteForProcCode(resourcesAssign); //删除之前的明细数据，再重新保存
		resourcesAssign.setSourceAssign(user.getId());
		resourcesAssign.setSourceAssignPost(user.getPostIds());
		resourcesAssign.setSourceTaskId("start");
		resourcesAssign.setSourceTaskName("start");
		if(resourcesAssignRequestList !=null && resourcesAssignRequestList.size() > 0){
			for(int i = 0; i < resourcesAssignRequestList.size();i++){
				ResourcesAssignRequest resourcesAssignRequest = resourcesAssignRequestList.get(i);
				resourcesAssign.setTargetAssign(resourcesAssignRequest.getTargetAssign());
				resourcesAssign.setPersonelNum(resourcesAssignRequest.getPersonelNum());
				if(StringUtils.isBlank(resourcesAssignRequest.getRemarks())){
					resourcesAssign.setRemarks("");
				}else {
					resourcesAssign.setRemarks(resourcesAssignRequest.getRemarks());
				}
				resourcesAssign.preInsertForWeChart(user);
				resourcesAssignDao.insert(resourcesAssign);
			}
		}
		/*****资源办理指派人员业务处理end*****/
	}
	/**
	 * 删除流程
	 * @param req
	 */
	@Transactional(readOnly = false)
	public void deleteResourcesHandleInfo(ResourcesHandleFlow flow) {
		if(Constant.expense_save.equals(flow.getResourcesHandleStatus())){
			//删除人员指派记录
			ResourcesAssign resourcesAssign = new ResourcesAssign();
			resourcesAssign.setProcCode(flow.getProcCode());
			resourcesAssignDao.deleteForProcCode(resourcesAssign);
			//删除资源办理信息
			resourcesHandleFlowDao.deleteResHandleInfo(flow);
		}else{
			if(StringUtils.isNotBlank(flow.getProcInsId())){
				//删除任务
				actTaskService.deleteRunTask(flow.getProcInsId()); //删除运行任务
				actTaskService.deleteHisTask(flow.getProcInsId()); //删除历史任务
			}
			resourcesHandleFlowDao.delete(flow);
			//修改单据状态为已删除
			flow.setResourcesHandleStatus(Constant.expense_delete); //需求申请状态被删除
			resourcesHandleFlowDao.updateResHandleStatus(flow);
		}
	}
	
	/**
	 * 详情页面
	 * @param id
	 * @return
	 */
	public ResourcesHandleFlowResponse getResourcesHandleFlowById(String id,User user) {
		ResourcesHandleFlow flow = resourcesHandleFlowService.get(id);
		ResourcesHandleFlowResponse resp = new ResourcesHandleFlowResponse();
		resp.setId(flow.getId());
		resp.setProcCode(flow.getProcCode());
		resp.setProcName(flow.getProcName());
		resp.setApplyPerName(flow.getApplyPerName());
		resp.setResourcesType(flow.getResourcesType());
		if(flow.getResourcesType() != null){
			resp.setResourcesTypeValue(DictUtils.getDictLabel(flow.getResourcesType(), "resources_type", ""));
		}
		if(flow.getApplyTime() != null){
			resp.setApplyTime(DateUtils.format(flow.getApplyTime(), DateUtils.YYYY_MM_DD));
		}
		if(flow.getOffice() != null){
			resp.setOfficeId(flow.getOffice().getId());
			resp.setOfficeName(flow.getOffice().getName());
		}
		resp.setPostCode(flow.getPostCode());
		resp.setPostName(flow.getPostName());
		resp.setRelationTheme(flow.getRelationTheme());
		ResourcesApply resourcesApply = resourcesApplyDao.getByProcCode(flow.getRelationTheme());
		if(resourcesApply != null){
			resp.setRelationThemeName(resourcesApply.getProcName());
		}
		resp.setHandleType(flow.getHandleType());
		resp.setHandleTypeValue(DictUtils.getDictLabel(flow.getHandleType(), "handle_type", ""));
		resp.setResourcesType(flow.getResourcesType());
		resp.setResourcesTypeValue(DictUtils.getDictLabel(flow.getResourcesType(), "resources_type", ""));
		resp.setProjectId(flow.getProjectId());
		resp.setProjectName(flow.getProjectName());
		resp.setProjectPersonel(flow.getProjectPersonel());
		if(StringUtils.isBlank(flow.getRemarks())){
			resp.setRemarks("");
		}else{
			resp.setRemarks(flow.getRemarks());
		}

		ProjectInfo projectInfo = projectInfoService.get(flow.getProjectId());
		if(projectInfo != null && projectInfo.getProjectLeader() != null){
			resp.setProjectPersonel(projectInfo.getProjectLeader().getName());
			resp.setProjectPersonelId(projectInfo.getProjectLeader().getId());
		}
		resp.setProcInsId(flow.getProcInsId());
		resp.setAmountSum(flow.getAmountSum());
		if(flow.getExpectDate() != null){
			resp.setExpectDate(flow.getExpectDate().getTime());
		}
		resp.setTimeLong(flow.getTimeLong());
		if(flow.getPersonelNum() != null){
			resp.setPersonelNum(flow.getPersonelNum());
		}
		resp.setResourcesHandleStatus(flow.getResourcesHandleStatus());
		resp.setResourcesHandleStatusValue(DictUtils.getDictLabel(flow.getResourcesHandleStatus(), "expense_status", ""));
		//指派列表
		List<ResourcesAssignResponse> array = Lists.newArrayList();
		ResourcesAssign resourcesAssign = new ResourcesAssign();
		resourcesAssign.setProcCode(flow.getProcCode());
		List<ResourcesAssign> list = resourcesAssignDao.findList(resourcesAssign);
		if(list != null && list.size() > 0){
			for (ResourcesAssign r : list) {
				ResourcesAssignResponse re = new ResourcesAssignResponse();
				if(StringUtils.isNotBlank(r.getTargetAssign()) && StringUtils.isNotBlank(r.getSourceAssign())){
					User sourceUser = UserUtils.get(r.getSourceAssign());
					if(sourceUser != null){
						re.setSourceAssign(r.getSourceAssign());
						re.setSourceAssignName(sourceUser.getName());
					}
					User targetUser = UserUtils.get(r.getTargetAssign());
					if(targetUser != null){
						re.setTargetAssign(r.getTargetAssign());
						re.setTargetAssignName(targetUser.getName());
					}
				}
				re.setPersonelNum(r.getPersonelNum());
				if(StringUtils.isBlank(r.getRemarks())){
					re.setRemarks("");
				}else{
					re.setRemarks(r.getRemarks());
				}

				array.add(re);
			}
		}
		resp.setResourcesAssignResponseList(array);
		
		resp.setIsAssign(1); //是否可以指派人员
		resp.setIsBack(1); //是否可以驳回
		//判断当前环节是否可以编辑页面
		resp.setIsDeit(0);
		if(Constant.expense_save.equals(resp.getResourcesHandleStatus())){
			resp.setIsDeit(1);
			resp.setModify(Constant.DEFAULT_NOE_MODIFY);
			resp.setIsBack(0); //不可驳回
		}else{
			resp.setModify("");
		}
		
		Act a = new Act();	
		a.setProcInsId(flow.getProcInsId());
		a = actTaskService.queryThisRunTaskId(a);
		if(a != null && a.getTaskDefKey().startsWith("audit")){
			resp.setIsAssign(0); //不可指派人员
			resp.setIsBack(0); //不可驳回
		}
		if(a != null && "modify".equals(a.getTaskDefKey())){
			resp.setIsAssign(0); //不可指派人员
			resp.setIsDeit(1); //可以编辑
			resp.setModify(Constant.DEFAULT_NOE_MODIFY); //可以编辑
			resp.setIsBack(0); //不可驳回
		}
		//判断当前任务是否已经在本人待办任务中,或者单据状态为已删除或审批结束
		if ((a != null && StringUtils.isNotBlank(a.getAssignee()) && user.getLoginName().equals(a.getAssignee()))
				|| (Constant.expense_approve_end.equals(resp.getResourcesHandleStatus())) 
				|| (Constant.expense_delete.equals(resp.getResourcesHandleStatus()))) {
			resp.setIsCancel(0); //不可撤销
		}else{
			resp.setIsCancel(1); //可撤销
		}
		//判断当前环节人员是否有下级岗位
		List<PostInfo> postInfoList = postInfoDao.getPostInfoByParentId(user.getPostIds());
		if(postInfoList == null || postInfoList.size() == 0){
			resp.setIsAssign(0); //不可指派人员
			resp.setIsCancel(0); //不可撤销
		}
		return resp;
	}
	
	/**
	 * 设置流程审批记录
	 * @param histoicFlowList
	 * @param resource
	 * @return
	 */
	public List<FlowLogResponse> setFlowLog(List<Act> histoicFlowList,ResourcesHandleFlowResponse resource){
		List<FlowLogResponse> flowloglist = Lists.newArrayList();
		for (int i = 0; i < histoicFlowList.size(); i++) {
			Act tmpAct = histoicFlowList.get(i);
			String startTime = DateUtils.formatDate(tmpAct.getHistIns().getStartTime(), DateUtils.YYYY_MM_DD_HH_MM_SS);
			String endTime = "";
			if(tmpAct.getHistIns().getEndTime() != null){
				endTime = DateUtils.formatDate(tmpAct.getHistIns().getEndTime(), DateUtils.YYYY_MM_DD_HH_MM_SS);
			}
			if(Constant.expense_delete.equals(resource.getResourcesHandleStatus())){
				if(Constant.DEFAULT_NOE_MODIFY.equals(tmpAct.getTaskDefKey()) && i == histoicFlowList.size()-1){
					flowloglist.add(new FlowLogResponse("已删除", tmpAct.getAssigneeName(), startTime, endTime, tmpAct.getComment(), tmpAct.getDurationTime()));
				}else{
					if(StringUtils.isNotBlank(tmpAct.getDurationTime())){
						flowloglist.add(new FlowLogResponse("已处理", tmpAct.getAssigneeName(), startTime, endTime, tmpAct.getComment(), tmpAct.getDurationTime()));
					}else{
						flowloglist.add(new FlowLogResponse(tmpAct.getHistIns().getActivityName(), tmpAct.getAssigneeName(), startTime, endTime, tmpAct.getComment(), tmpAct.getDurationTime()));
					}
				}
			}else{
				if(StringUtils.isNotBlank(tmpAct.getDurationTime())){
					flowloglist.add(new FlowLogResponse("已处理", tmpAct.getAssigneeName(), startTime, endTime, tmpAct.getComment(), tmpAct.getDurationTime()));
				}else{
					flowloglist.add(new FlowLogResponse(tmpAct.getHistIns().getActivityName(), tmpAct.getAssigneeName(), startTime, endTime, tmpAct.getComment(), tmpAct.getDurationTime()));
				}
			}
			
		}
		if(Constant.expense_delete.equals(resource.getResourcesHandleStatus())){
			if(flowloglist != null && flowloglist.size() > 0){
				FlowLogResponse flr = flowloglist.get(flowloglist.size()-1);
				if(!"已删除".equals(flr.getActivityName())){
					flowloglist.add(new FlowLogResponse("已删除",resource.getApplyPerName(), "", "", "",""));
				}
			}
		}
		return flowloglist;
	}
	
	
	/**
	 * 查询资源办理人员列表信息
	 * @param user
	 * @return
	 */
	public List<User> queryHandlerUserInfo(User user){
		return userDao.queryHandlerUserInfo(user);
	}
	
	/**
	 * 审核审批保存
	 * @param testAudit
	 */
	@Transactional(readOnly = false)
	public void resourcesHandleCompleteTask(ResourcesHandleFlow resourcesHandleFlow,User user, List<ResourcesAssignRequest> resourcesAssignRequestList) {
		//校验指派人员是否重复
		if(checkResourcesAssign(resourcesAssignRequestList)){
			throw new ServiceException("当前单据指派人员不能重复,请仔细核对指派人员信息"); 
		}
		String flag = resourcesHandleFlow.getAct().getFlag();
		//判断当前环节人员是否有下级岗位
		List<PostInfo> postInfoList = postInfoDao.getPostInfoByParentId(user.getPostIds());
		if(!"no".equals(flag)){
			if(postInfoList!= null && postInfoList.size() > 0 && StringUtils.isNotBlank(resourcesHandleFlow.getAct().getTaskDefKey()) &&
					!resourcesHandleFlow.getAct().getTaskDefKey().startsWith("audit") && 
					(resourcesAssignRequestList == null || resourcesAssignRequestList.size() == 0)){
				throw new ServiceException("指派人员为空，禁止提交"); 
			}
		}
		// 设置审批意见
		resourcesHandleFlow.getAct().setComment(("yes".equals(resourcesHandleFlow.getAct().getFlag())?"[同意] ":"[拒绝] ")+resourcesHandleFlow.getAct().getComment());
		
		// 提交流程任务
		Map<String, Object> vars = Maps.newHashMap();
		vars.put("pass", "yes".equals(resourcesHandleFlow.getAct().getFlag())? "1" : "0");
		//判断当前环节人员是否有下级岗位
		if(postInfoList != null && postInfoList.size() > 0){
			vars.put("pass", "1");
		}else{
			vars.put("pass", "3");
		}
		List<String> employeeList = new ArrayList<String>(); //指派人员
		if("no".equals(flag)){
			actTaskService.completedTaskAndNotJump(resourcesHandleFlow.getAct().getTaskId(), vars, user, resourcesHandleFlow.getAct().getComment()); //直接完成当前任务
		}else{
			if(StringUtils.isNotBlank(resourcesHandleFlow.getAct().getTaskDefKey()) && resourcesHandleFlow.getAct().getTaskDefKey().startsWith("audit")){
				ResourcesAssign ra = new ResourcesAssign();
				ra.setSourceAssign(resourcesHandleFlow.getCreateBy().getId());
				ra.setProcCode(resourcesHandleFlow.getProcCode());
				List<ResourcesAssign> resourcesAssignList = resourcesAssignDao.findList(ra);
				for(ResourcesAssign r:resourcesAssignList){
					employeeList.add(r.getTargetUser().getLoginName());
				}
			}else{
				/*****资源办理指派人员业务处理start*****/
				//删除当前审批人之前的指派记录
				ResourcesAssign r = new ResourcesAssign();
				r.setProcCode(resourcesHandleFlow.getProcCode());
				r.setSourceAssign(user.getId());
				resourcesAssignDao.deleteResourcesAssignForSourceAssign(r);
				
				ResourcesAssign resourcesAssign = new ResourcesAssign();
				resourcesAssign.setProcCode(resourcesHandleFlow.getProcCode());
				resourcesAssign.setSourceAssign(user.getId());
				resourcesAssign.setSourceAssignPost(user.getPostIds());
				resourcesAssign.setSourceTaskId(resourcesHandleFlow.getAct().getTaskId());
				resourcesAssign.setSourceTaskName(resourcesHandleFlow.getAct().getTaskDefKey());
				if(resourcesAssignRequestList !=null && resourcesAssignRequestList.size() > 0){
					for(int i = 0; i < resourcesAssignRequestList.size();i++){
						ResourcesAssignRequest resourcesAssignRequest = resourcesAssignRequestList.get(i);
						resourcesAssign.setTargetAssign(resourcesAssignRequest.getTargetAssign());
						resourcesAssign.setPersonelNum(resourcesAssignRequest.getPersonelNum());
						if(StringUtils.isBlank(resourcesAssignRequest.getRemarks())){
							resourcesAssign.setRemarks("");
						}else {
							resourcesAssign.setRemarks(resourcesAssignRequest.getRemarks());
						}
						resourcesAssign.preInsertForWeChart(user);
						resourcesAssignDao.insert(resourcesAssign);
						//设置指派人员
						User u = UserUtils.get(resourcesAssignRequest.getTargetAssign());
						employeeList.add(u.getLoginName());
					}
				}
				/*****资源办理指派人员业务处理end*****/
			}
			vars.put("employeeList",employeeList);
			actTaskService.complete(resourcesHandleFlow.getAct().getTaskId(), resourcesHandleFlow.getAct().getProcInsId(), resourcesHandleFlow.getAct().getComment(), vars);
		}
		//判断流程是否退回并更新接待申请状态
		if("no".equals(flag)){}
		
		//判断流程是否结束并更新资源办理状态
		Act e = new Act();
		e.setProcInsId(resourcesHandleFlow.getAct().getProcInsId()); //流程实例ID
		List<Act> actList = actTaskService.queryThisRunTaskIdList(e);
		for(Act aa:actList){
			if(aa.getTaskDefKey().startsWith(Constant.NO_NODE)){
				actTaskService.deleteRunTaskForTaskId(aa.getTaskId());
			}
		}
		actList = actTaskService.queryThisRunTaskIdList(e);
		
		if((actList == null || actList.size() == 0) && employeeList.size() == 0){
			//流程已结束
			resourcesHandleFlow.setFlowFinishTime(new Date());
			resourcesHandleFlow.setResourcesHandleStatus(Constant.expense_approve_end); //接待申请状态审批结束
			resourcesHandleFlowDao.updateResHandleStatusAndFlowFinshTime(resourcesHandleFlow);
		}else{
			
			//更新被指派人员taskId
			if(!"no".equals(flag)){
				ResourcesAssign ra = new ResourcesAssign();
				if(StringUtils.isNotBlank(resourcesHandleFlow.getAct().getTaskDefKey()) && resourcesHandleFlow.getAct().getTaskDefKey().startsWith("audit")){
					ra.setSourceAssign(resourcesHandleFlow.getCreateBy().getId());
				}else{
					ra.setSourceAssign(user.getId());
				}
				ra.setProcCode(resourcesHandleFlow.getProcCode());
				List<ResourcesAssign> resourcesAssignList = resourcesAssignDao.findList(ra);
				if(resourcesAssignList != null && resourcesAssignList.size() > 0){
					for(ResourcesAssign a:resourcesAssignList){
						User targetUser = UserUtils.get(a.getTargetAssign());
						for(Act act:actList){
							if(targetUser.getLoginName().equals(act.getAssignee())){
								a.setTargetTaskId(act.getTaskId());
								a.setTargetTaskName(act.getTaskDefKey());
								if(a.getRemarks() == null){
								    a.setRemarks("");
                                }
								resourcesAssignDao.update(a);
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * 撤销流程接口
	 * @param resourcesApply
	 * @param user
	 */
	@Transactional(readOnly = false)
	public void repealTask(ResourcesHandleFlow resourcesHandleFlow, User user) {
		/********判断当前节点的下一个节点任务是否已经完成*********/
		List<String> employeeList = new ArrayList<String>();
		employeeList.add(user.getLoginName());
		/**********流程跳转，发起撤销start********/
		Map<String, Object> vars = Maps.newHashMap();
		vars.put("pass", "1");
		vars.put("employeeList",employeeList);
		vars.put("employee",user.getLoginName());
		
		//查询当前环节的指派记录
		ResourcesAssign a = new ResourcesAssign();
		a.setSourceAssign(user.getId());
		a.setProcCode(resourcesHandleFlow.getProcCode());
		List<ResourcesAssign> resourcesAssignList = resourcesAssignDao.findList(a);
		StringBuffer taskIds = new StringBuffer();
		if(resourcesAssignList != null && resourcesAssignList.size() > 0){
			for(int i = 0;i<resourcesAssignList.size();i++){
				if(!"start".equals(resourcesAssignList.get(i).getSourceTaskName())){
					taskIds.append("'"+resourcesAssignList.get(i).getTargetTaskId()+"'");
					if(i < resourcesAssignList.size()-1){
						taskIds.append(",");
					}
				}
			}
		}
		
		if(!"start".equals(resourcesHandleFlow.getAct().getTaskDefKey()) && StringUtils.isBlank(taskIds)){
			throw new ServiceException("当前流程任务已结束，无法进行收回!");
		}
		
		//查询被撤销的节点信息
		Act act = new Act();
		act.setProcInsId(resourcesHandleFlow.getAct().getProcInsId()); //流程实例ID
		act.setTaskIds(taskIds.toString());
		List<Act> actList = actTaskService.queryThisRunTaskIdList(act); //查询当前任务信息
		
		//查询发起撤销节点key
		Act targetAct = new Act();
		if("0".equals(resourcesHandleFlow.getAct().getTaskId())){
			targetAct.setTaskDefKey("modify");
		}else{
			targetAct.setTaskId(resourcesHandleFlow.getAct().getTaskId());
			targetAct.setProcInsId(resourcesHandleFlow.getAct().getProcInsId());
			targetAct = actTaskService.queryHisTask(targetAct);
		}
		
		String targetTaskDefinitionKey = targetAct.getTaskDefKey(); //发起撤销的节点key
		actTaskService.jumpTaskAndAddComentForChildList(resourcesHandleFlow.getAct().getProcInsId(), actList,
				"[流程撤回]当前审核任务被"+user.getName()+"撤回", user.getLoginName(), targetTaskDefinitionKey, vars);
		/**********流程跳转，发起撤销end********/
		
	}
	
	/**
	 * 校验指派人员是否重复
	 * @param assignList
	 * @return
	 */
	public boolean checkResourcesAssign(List<ResourcesAssignRequest> assignList){
		boolean flag = false;
		List<String> newList = new ArrayList<String>();
		if(assignList != null && assignList.size() > 0){
			for(ResourcesAssignRequest resourcesAssignRequest:assignList){
				if(newList.contains(resourcesAssignRequest.getTargetAssign())){
					flag = true;
					return flag;
				}else{
					newList.add(resourcesAssignRequest.getTargetAssign());
				}
			}
		}
		return flag;
	}
}
