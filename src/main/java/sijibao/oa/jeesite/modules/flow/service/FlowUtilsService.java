package sijibao.oa.jeesite.modules.flow.service;


import java.util.List;
import java.util.Map;

import org.activiti.engine.FormService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.sijibao.oa.activiti.api.response.contract.ContractFlowNewResponse;
import com.sijibao.oa.activiti.api.service.ContractFlowNewService;
import com.sijibao.oa.common.service.BaseService;
import com.sijibao.oa.common.utils.SpringContextHolder;
import com.sijibao.oa.common.utils.StringUtils;
import com.sijibao.oa.modules.act.service.ActTaskService;
import com.sijibao.oa.modules.flow.dao.ExpenseFlowDao;
import com.sijibao.oa.modules.flow.entity.*;
import com.sijibao.oa.modules.intfz.service.report.IntfzContractOverdueService;
import com.sijibao.oa.modules.intfz.utils.Constant;
import com.sijibao.oa.modules.oa.entity.DemandManagement;
import com.sijibao.oa.modules.oa.entity.ProjectInfo;
import com.sijibao.oa.modules.oa.service.DemandManagementService;
import com.sijibao.oa.modules.oa.service.ProjectInfoService;
import com.sijibao.oa.modules.sys.dao.UserDao;
import com.sijibao.oa.modules.sys.entity.Office;
import com.sijibao.oa.modules.sys.entity.User;
import com.sijibao.oa.modules.sys.service.OfficeService;
import com.sijibao.oa.modules.sys.utils.UserUtils;

/**
 * 流程工具类
 * @author Petter
 */
@Service
@Transactional(readOnly = true)
public class FlowUtilsService extends BaseService{
	@Autowired
	private ExpenseFlowService expenseFlowService;
	@Autowired
	private RecpFlowService recpFlowService;
	@Autowired
	private OfficeService officeService;
	@Autowired
	private ExpenseFlowDao expenseFlowDao;
	@Autowired
	private DemandManagementService demandManagementService;
	@Autowired
	private UserDao userDao;
	@Autowired
	private ProjectInfoService projectInfoService;
	@Autowired
	private TravelFlowService travelFlowService;
	@Autowired
	private ResourcesApplyService resourcesApplyService;
	@Autowired
	private ResourcesHandleFlowService resourcesHandleFlowService;

	private ContractFlowNewService contractFlowNewService;

	private IntfzContractOverdueService intfzContractOverdueService;

	@Autowired
	private TaskService taskService;
	@Autowired
	private ActTaskService actTaskService;

	@Autowired
	private FormService formService;

	public void init(){
		if(contractFlowNewService == null){
			contractFlowNewService = SpringContextHolder.getBean("contractFlowNewService");
		}
	}
	/**
	 * 查询直属领导
	 * @param employee
	 * @return
	 */
	public String findManagerForEmployee(String employee) {
		return expenseFlowDao.findManagerForEmployee(employee)==null?"":expenseFlowDao.findManagerForEmployee(employee);
	}


	/**
	 * 根据业务ID查询总监信息
	 * @param employee
	 * @return
	 */
	public String findManagerForDeptName(String businessId,String billType) {
		init();
		String deptId = "";
		@SuppressWarnings("unused")
		String personel = "";
		if(StringUtils.isBlank(billType) || Constant.EXPENSE_FLOW_APPLY.equals(billType)){
			ExpenseFlow expenseFlow = expenseFlowService.get(businessId); //获取报销主体信息
			personel = expenseFlow.getApplyPerCode();
			deptId = expenseFlow.getOffice().getId(); //部门ID
		}else if(Constant.RECP_FLOW_APPLY.equals(billType)){
			RecpFlow recpFlow = recpFlowService.get(businessId);
			personel = recpFlow.getApplyPerCode();
			deptId = recpFlow.getOffice().getId(); //部门ID
		}else if(Constant.DEMAND_MANAGEMENT_MARKET.equals(billType) ||
				Constant.DEMAND_MANAGEMENT_IMPLEMENT.equals(billType)){
			DemandManagement demandManagement = demandManagementService.get(businessId);
			personel = demandManagement.getApplyPerCode();
			deptId = demandManagement.getOffice().getId(); //部门ID
		}else if(Constant.TRAVEL_FLOW_APPLY.equals(billType)){
			TravelFlow travelFlow = travelFlowService.get(businessId);
			personel = travelFlow.getApplyPerCode();
			deptId = travelFlow.getOffice().getId(); //部门ID
		}else if(Constant.RESOURCES_APPLY_FLOW_APPLY.equals(billType)){
			ResourcesApply resourcesApply = resourcesApplyService.get(businessId);
			personel = resourcesApply.getApplyPerCode();
			deptId = resourcesApply.getOffice().getId(); //部门ID
		}else if(Constant.RESOURCES_HANDLE_FLOW_APPLY.equals(billType)){
			ResourcesHandleFlow resourcesHandleFlow = resourcesHandleFlowService.get(businessId);
			personel = resourcesHandleFlow.getApplyPerCode();
			deptId = resourcesHandleFlow.getOffice().getId(); //部门ID
		}else if(Constant.CONTRACT_FLOW_APPLY.equals(billType)){
			ContractFlowNewResponse response = contractFlowNewService.get(businessId);
			deptId = response.getOfficeId();
		}
		Office office = officeService.get(deptId);
		return office.getPrimaryPerson().getLoginName() == null?"":office.getPrimaryPerson().getLoginName();
	}

	/**
	 * 找到上级部门的主负责人
	 * @param employee
	 * @return
	 */
	public String findManagerForUpDeptName(String businessId,String billType) {
		init();
		String deptId = "";
		@SuppressWarnings("unused")
		String personel = "";
		if(StringUtils.isBlank(billType) || Constant.EXPENSE_FLOW_APPLY.equals(billType)){
			ExpenseFlow expenseFlow = expenseFlowService.get(businessId); //获取报销主体信息
			personel = expenseFlow.getApplyPerCode();
			deptId = expenseFlow.getOffice().getId(); //部门ID
		}else if(Constant.RECP_FLOW_APPLY.equals(billType)){
			RecpFlow recpFlow = recpFlowService.get(businessId);
			personel = recpFlow.getApplyPerCode();
			deptId = recpFlow.getOffice().getId(); //部门ID
		}else if(Constant.DEMAND_MANAGEMENT_MARKET.equals(billType) ||
				Constant.DEMAND_MANAGEMENT_IMPLEMENT.equals(billType)){
			DemandManagement demandManagement = demandManagementService.get(businessId);
			personel = demandManagement.getApplyPerCode();
			deptId = demandManagement.getOffice().getId(); //部门ID
		}else if(Constant.TRAVEL_FLOW_APPLY.equals(billType)){
			TravelFlow travelFlow = travelFlowService.get(businessId);
			personel = travelFlow.getApplyPerCode();
			deptId = travelFlow.getOffice().getId(); //部门ID
		}else if(Constant.RESOURCES_APPLY_FLOW_APPLY.equals(billType)){
			ResourcesApply resourcesApply = resourcesApplyService.get(businessId);
			personel = resourcesApply.getApplyPerCode();
			deptId = resourcesApply.getOffice().getId(); //部门ID
		}else if(Constant.RESOURCES_HANDLE_FLOW_APPLY.equals(billType)){
			ResourcesHandleFlow resourcesHandleFlow = resourcesHandleFlowService.get(businessId);
			personel = resourcesHandleFlow.getApplyPerCode();
			deptId = resourcesHandleFlow.getOffice().getId(); //部门ID
		}else if(Constant.CONTRACT_FLOW_APPLY.equals(billType)){
			ContractFlowNewResponse response = contractFlowNewService.get(businessId);
			deptId = response.getOfficeId();
		}
		Office office = officeService.get(deptId);
		Office upOffice = officeService.get(office.getParentId());
		return upOffice.getPrimaryPerson().getLoginName() == null?"":upOffice.getPrimaryPerson().getLoginName();
	}


	public String findManagerForDeptName(String businessId) {
		String employee = "";

		String deptId = "";
		String officeName = "";
		String personel = "";
		ExpenseFlow expenseFlow = expenseFlowService.get(businessId); //获取报销主体信息
		personel = expenseFlow.getApplyPerCode();
		deptId = expenseFlow.getOffice().getParentId(); //父及部门ID
		officeName = expenseFlow.getOffice().getName();
		Office office = officeService.get(deptId);
		String deptName = office.getName(); //部门名称
		switch(deptName){
			case "人力行政中心":
				employee = "13307147318"; //孙林芳
				break;
			case "研发中心":
				employee = "18062412311";  //王义
				break;
			case "大数据中心":
				employee = "15013036246";  //林程程
				break;
			case "运营中心":
			case "清结算部":
				employee = "18108674081";  //龚志琪
				break;
			case "销售部":
			case "市场部":
			case "市场中心":
				employee = "18507166902"; //李广
				break;
		}
		if("".equals(employee)){
			switch(officeName){
				case "人力行政中心":
					employee = "13307147318"; //孙林芳
					break;
				case "研发中心":
					employee = "18062412311";  //王义
					break;
				case "大数据中心":
					employee = "15013036246";  //林程程
					break;
				case "运营中心":
				case "清结算部":
					employee = "18108674081";  //龚志琪
					break;
				case "财务管理":
				case "财务中心":
					employee = "18995619186";  //胡安逸
					break;
				case "销售部":
				case "市场部":
				case "市场中心":
					employee = "18507166902"; //李广
					break;
				default:
					employee = personel;
					break;
			}
		}
		return "";
	}


	/**
	 * 根据岗位编码查询相关用户信息
	 * @param postCode
	 * @return
	 */
	public String findUserInfoForPostCode(String postCode,String businessId){
		String employee = "";
		//根据业务ID查询相关业务信息
		DemandManagement demandManagement = demandManagementService.get(businessId);
		String officeId = demandManagement.getOffice().getId();

		//根据岗位编码和部门ID查询关联的人员信息
		User user = new User();
		user.getOffice().setId(officeId); //单据人员所属部门ID
		user.setPostIds(postCode); //岗位编码
		List<User> listUser = userDao.queryUserInfoForPostCodeAndOffice(user);
		if(listUser != null && listUser.size() > 0){
			user = listUser.get(0);
			employee = user.getLoginName();
		}
		return employee;
	}

	/**
	 * 报销获取项目负责人
	 * @param businessId
	 * @return
	 */
	public String queryProjectLeader(String businessId){
		String employee = "";
		ExpenseFlow expenseFlow = expenseFlowService.get(businessId);
		if(StringUtils.isNotBlank(expenseFlow.getProjectId())){
			ProjectInfo projectInfo = projectInfoService.get(expenseFlow.getProjectId());
			if(projectInfo.getProjectLeader() != null){
				User user = UserUtils.get(projectInfo.getProjectLeader().getId());
				employee = user.getLoginName();
			}
		}
		return employee;
	}

	/**
	 * 获取单据项目负责人(所有类型单据)
	 * @param businessId
	 * @param billType
	 * @return
	 */
	public String queryProjectLeaderAllBussiness(String businessId,String billType){
		String employee = "";
		String projectId = "";

		if(StringUtils.isBlank(billType) || Constant.EXPENSE_FLOW_APPLY.equals(billType)){
			ExpenseFlow expenseFlow = expenseFlowService.get(businessId); //获取报销主体信息
			projectId = expenseFlow.getProjectId(); //项目ID
		}else if(Constant.RECP_FLOW_APPLY.equals(billType)){
			RecpFlow recpFlow = recpFlowService.get(businessId);
			projectId = recpFlow.getProjectId(); //项目ID
		}else if(Constant.DEMAND_MANAGEMENT_MARKET.equals(billType) ||
				Constant.DEMAND_MANAGEMENT_IMPLEMENT.equals(billType)){
			DemandManagement demandManagement = demandManagementService.get(businessId);
			projectId = demandManagement.getProjectId(); //项目ID
		}else if(Constant.TRAVEL_FLOW_APPLY.equals(billType)){
			TravelFlow travelFlow = travelFlowService.get(businessId);
			projectId = travelFlow.getProjectId(); //项目ID
		}else if(Constant.RESOURCES_APPLY_FLOW_APPLY.equals(billType)){
			ResourcesApply resourcesApply = resourcesApplyService.get(businessId);
			projectId = resourcesApply.getProjectId(); //项目ID
		}else if(Constant.RESOURCES_HANDLE_FLOW_APPLY.equals(billType)){
			ResourcesHandleFlow resourcesHandleFlow = resourcesHandleFlowService.get(businessId);
			projectId = resourcesHandleFlow.getProjectId(); //项目ID
		}
		if(StringUtils.isNotBlank(projectId)){
			ProjectInfo projectInfo = projectInfoService.get(projectId);
			if(projectInfo.getProjectLeader() != null){
				User user = UserUtils.get(projectInfo.getProjectLeader().getId());
				employee = user.getLoginName();
			}
		}
		return employee;
	}


	/**
	 * 流程结束
	 */
	public void end(String loginName,String businessId) {
		logger.info("Activiti监听器：合同管理流程 任务结束...................................");
		intfzContractOverdueService.saveContractOverdue(businessId,loginName);
	}


	/**
	 * 自动查找申请人所属部门的上级部门主（或副）负责人，直到一级部门
	 * @param employee
	 * @return
	 */
	public String findParentLeader(String employee,String num,String deputy) {
		User user = new User();
		user.setLoginName(employee);
		User result = userDao.getByLoginName(user);
		String parentId = result.getOffice().getParentIds();
		String[] parentIds = parentId.split(",");
		//后续改成一级一级的往上找 再打开
		int level = Integer.parseInt(num) + 2;

		if(parentIds.length >= level){
			return getLeader(parentIds,deputy,num);
		}else{
			return "";
		}
//		return getLeader(parentIds,deputy,num);
	}

    /**
     * 自动查找成本中心部门的上级部门主（或副）负责人，直到一级部门。注意：仅适用于报销流程！
     * @param businessId 报销流程业务主表记录ID
     * @param num 级数
     * @param deputy 0表示主负责人，1表示副负责人
     * @return 登录名（唯一标识）
     */
    public String findCostCenterParentLeader(String businessId,String num,String deputy) {
        ExpenseFlow expenseFlow = expenseFlowDao.get(businessId);
        String parentIds = officeService.get(expenseFlow.getCostCenterId()).getParentIds();
        String[] parentIdList = parentIds.split(",");
        //后续改成一级一级的往上找 再打开
        int level = Integer.parseInt(num) + 2;

        if(parentIdList.length >= level){
            return getLeader(parentIdList,deputy,num);
        }else{
            return "";
        }
//		return getLeader(parentIds,deputy,num);
    }

	/**
	 * 查找副负责人或主负责人
	 * @param parentIds
	 * @param deputy
	 * @return
	 */
	private String getLeader(String[] parentIds,String deputy,String num){
		int index = Integer.parseInt(num) + 1;
//		int index = parentIds.length - 1;
		logger.info("================FlowUtilsService:getLeader parentIds="+parentIds.toString()+",deputy="+deputy+",num="+num+", officeId="+parentIds[index]+"================");
		Office off = officeService.get(parentIds[index]);
		String loginName = "";
		//查找副负责人
		if("1".equals(deputy)){
			if(!"".equals(off.getDeputyPerson())) {
				User temp = userDao.get(off.getDeputyPerson());
				//如果副负责人为离职或离职未完结，则找部门主负责人
				if("2".equals(temp.getUserStatus()) || "3".equals(temp.getUserStatus())){
					User upUser = userDao.get(off.getPrimaryPerson().getId());
					if("2".equals(upUser.getUserStatus()) || "3".equals(upUser.getUserStatus())){
						loginName = "";
					}else{
						loginName = upUser.getLoginName();
					}

				}else{
					loginName = temp.getLoginName();
				}

			}
		}
		//查找主负责人
		else if("0".equals(deputy)){
			if(null != off.getPrimaryPerson() && !"".equals(off.getPrimaryPerson().getId())) {
				User temp = userDao.get(off.getPrimaryPerson().getId());
				//如果主负责人为离职或离职未完结，则找上级部门主负责人
				if("2".equals(temp.getUserStatus()) || "3".equals(temp.getUserStatus())){
					Office upOff = officeService.get(temp.getOffice().getParent().getId());
					//如果上级部门主负责人为空
					if(null == upOff.getPrimaryPerson() || null == upOff.getPrimaryPerson().getId() || "".equals(upOff.getPrimaryPerson().getId())){
						loginName = "";
					}else {
						User upUser = userDao.get(upOff.getPrimaryPerson().getId());
						if ("2".equals(upUser.getUserStatus()) || "3".equals(upUser.getUserStatus())) {
							loginName = "";
						} else {
							loginName = upUser.getLoginName();
						}
					}
				}else{
					loginName = userDao.get(off.getPrimaryPerson().getId()).getLoginName();
				}
			}
		}

		return loginName;
	}


	/**
	 * 出差流程中判断提交人是否等于部门主负责人
	 */
	public boolean checkSamePerson(String employee,String businessId,String billType) {
		boolean result = false;
		String loginName = this.findManagerForDeptName(businessId,billType);
		if(StringUtils.equals(loginName, employee)){ //如果执行者与申请人相同
			result = true;
		}
		return result;
	}


	public void findManagerForDeptName2(String employee,String businessId,String billType) {
//		String loginName = expenseFlowDao.findManagerForEmployee(employee);


		String loginName = this.findManagerForDeptName(businessId,billType);

		String projectId = "";
		String procInsId = "";
		if(StringUtils.isBlank(billType) || Constant.EXPENSE_FLOW_APPLY.equals(billType)){
			ExpenseFlow expenseFlow = expenseFlowService.get(businessId); //获取报销主体信息
//			projectId = expenseFlow.getProjectId(); //项目ID
			procInsId = expenseFlow.getProcInsId();
			Task task = taskService.createTaskQuery().processInstanceId(procInsId).active().singleResult();

			if(task != null){
				if(StringUtils.equals(loginName, employee)){ //如果执行者与申请人相同，则自动跳过
					String comment = "[同意]相同审批人员，自动审批跳过";
					String flag = expenseFlow.getAct().getFlag();
					// 提交流程任务
					Map<String, Object> vars = Maps.newHashMap();
					vars.put("pass", "yes".equals(expenseFlow.getAct().getFlag())? "1" : "0");
					actTaskService.complete(task.getId(), procInsId, comment, vars);
//					this.findManagerForDeptName2(employee,businessId, billType);
				}else{
//					this.findManagerForDeptName2(employee,businessId, billType);

				}
			}


		}else if(Constant.RECP_FLOW_APPLY.equals(billType)){
			RecpFlow recpFlow = recpFlowService.get(businessId);
			projectId = recpFlow.getProjectId(); //项目ID
		}else if(Constant.DEMAND_MANAGEMENT_MARKET.equals(billType) ||
				Constant.DEMAND_MANAGEMENT_IMPLEMENT.equals(billType)){
			DemandManagement demandManagement = demandManagementService.get(businessId);
			projectId = demandManagement.getProjectId(); //项目ID
		}else if(Constant.TRAVEL_FLOW_APPLY.equals(billType)){
			TravelFlow travelFlow = travelFlowService.get(businessId);
			projectId = travelFlow.getProjectId(); //项目ID
		}else if(Constant.RESOURCES_APPLY_FLOW_APPLY.equals(billType)){
			ResourcesApply resourcesApply = resourcesApplyService.get(businessId);
			projectId = resourcesApply.getProjectId(); //项目ID
		}else if(Constant.RESOURCES_HANDLE_FLOW_APPLY.equals(billType)){
			ResourcesHandleFlow resourcesHandleFlow = resourcesHandleFlowService.get(businessId);
			projectId = resourcesHandleFlow.getProjectId(); //项目ID
		}
		if(StringUtils.isNotBlank(projectId)){
			ProjectInfo projectInfo = projectInfoService.get(projectId);
			if(projectInfo.getProjectLeader() != null){
				User user = UserUtils.get(projectInfo.getProjectLeader().getId());
				employee = user.getLoginName();
			}
		}

	}
}
