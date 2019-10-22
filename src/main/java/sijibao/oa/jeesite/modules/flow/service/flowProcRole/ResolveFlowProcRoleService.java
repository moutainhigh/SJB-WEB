package sijibao.oa.jeesite.modules.flow.service.flowProcRole;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sijibao.oa.activiti.api.response.contract.ContractFlowNewResponse;
import com.sijibao.oa.activiti.api.service.ContractFlowNewService;
import com.sijibao.oa.common.service.BaseService;
import com.sijibao.oa.common.utils.SpringContextHolder;
import com.sijibao.oa.common.utils.StringUtils;
import com.sijibao.oa.modules.flow.dao.FlowProcRoleResolveDao;
import com.sijibao.oa.modules.flow.entity.*;
import com.sijibao.oa.modules.flow.service.*;
import com.sijibao.oa.modules.intfz.utils.Constant;
import com.sijibao.oa.modules.oa.entity.DemandManagement;
import com.sijibao.oa.modules.oa.service.DemandManagementService;
import com.sijibao.oa.modules.sys.dao.UserDao;
import com.sijibao.oa.modules.sys.entity.Office;
import com.sijibao.oa.modules.sys.entity.User;
import com.sijibao.oa.modules.sys.service.OfficeService;

/**
 * 流程角色解析
 * @author xuby
 */
@Service
@Transactional(readOnly = true)
public class ResolveFlowProcRoleService extends BaseService{
	@Autowired
	private ExpenseFlowService expenseFlowService;
	@Autowired
	private RecpFlowService recpFlowService;
	@Autowired
	private DemandManagementService demandManagementService;
	@Autowired
	private TravelFlowService travelFlowService;
	@Autowired
	private FlowProcRoleResolveDao flowProcRoleResolveDao;
	@Autowired
	private ResourcesApplyService resourcesApplyService;
	@Autowired
	private ResourcesHandleFlowService resourcesHandleFlowService;
	@Autowired
	private OpenAccountFlowService openAccountFlowService;
	@Autowired
	private UserDao userDao;
	@Autowired
	private OfficeService officeService;
	
	private ContractFlowNewService contractFlowNewService;
	public void init(){
		if(contractFlowNewService == null){
			contractFlowNewService = SpringContextHolder.getBean("contractFlowNewService");
		}
	}
	
	/**
	 * 流程角色解析
	 * @param
	 * @param procRoleCode
	 * @return
	 */
	public String resolveFlowProcRole(String businessId,String procRoleCode,String billType,String employee) {
		init();
		String deptId = "";
		Boolean isSkip = false;
		if (StringUtils.isBlank(billType) || Constant.EXPENSE_FLOW_APPLY.equals(billType)) {
			ExpenseFlow expenseFlow = expenseFlowService.get(businessId); //获取报销主体信息
			deptId = expenseFlow.getOffice().getId(); //部门ID
		} else if (Constant.RECP_FLOW_APPLY.equals(billType)) {
			RecpFlow recpFlow = recpFlowService.get(businessId);
			deptId = recpFlow.getOffice().getId(); //部门ID
		} else if (Constant.DEMAND_MANAGEMENT_MARKET.equals(billType) ||
				Constant.DEMAND_MANAGEMENT_IMPLEMENT.equals(billType)) {
			DemandManagement demandManagement = demandManagementService.get(businessId);
			deptId = demandManagement.getOffice().getId(); //部门ID
		} else if (Constant.TRAVEL_FLOW_APPLY.equals(billType)) {
			TravelFlow travelFlow = travelFlowService.get(businessId);
			deptId = travelFlow.getOffice().getId(); //部门ID
		} else if (Constant.RESOURCES_APPLY_FLOW_APPLY.equals(billType)) {
			ResourcesApply resourcesApply = resourcesApplyService.get(businessId);
			deptId = resourcesApply.getOffice().getId(); //部门ID
		} else if (Constant.RESOURCES_HANDLE_FLOW_APPLY.equals(billType)) {
			ResourcesHandleFlow resourcesHandleFlow = resourcesHandleFlowService.get(businessId);
			deptId = resourcesHandleFlow.getOffice().getId(); //部门ID
		} else if (Constant.OPEN_ACCOUNT_FLOW_APPLY.equals(billType)) {
			OpenAccountFlow openAccountFlow = openAccountFlowService.get(businessId);
			deptId = openAccountFlow.getOffice().getId();
		} else if (Constant.CONTRACT_FLOW_APPLY.equals(billType)) {
			ContractFlowNewResponse response = contractFlowNewService.get(businessId);
			deptId = response.getOfficeId();
		}

		//改为实时去获取提交人的部门  不是从原来的流程表中获取了
		User user = new User();
		user.setLoginName(employee);
		deptId = userDao.getByLoginName(user).getOffice().getId();


		//从缓存中拿出所有部门数据
		List<Office> officeList = officeService.findList(true);

		//查询对应的流程角色审批人信息
		FlowProcRoleResolve flowProcRoleResolve = new FlowProcRoleResolve();
//		flowProcRoleResolve.setOrgId(deptId);
		flowProcRoleResolve.setFlowPorcRoleCode(procRoleCode);

		Office dept = officeService.get(deptId);
		List<FlowProcRoleResolve> resultList = flowProcRoleResolveDao.queryUserByOrgId(flowProcRoleResolve);
		if(null != resultList && resultList.size() > 0){

			int index = 0;
			FlowProcRoleResolve result = null;

			for(FlowProcRoleResolve detail : resultList) {

				if(dept.getParentIds().indexOf(detail.getOrgId()) > 0 ){

					int temp = dept.getParentIds().indexOf(detail.getOrgId());
					if(temp > index){
						index = temp;
						result = detail;
					}

				}
			}
			String userLoginName = getSubOffices(officeList,result,dept);
			if(!"".equals(userLoginName)){

				//如果审批人为离职或离职未完结，则找上级部门主负责人,如果上级部门主负责人为离职或离职未完结，则返回空
				 User temp = new User();
				 temp.setLoginName(userLoginName);
				 User user2 = userDao.getByLoginName(temp);
				 if("2".equals(user2.getUserStatus()) || "3".equals(user2.getUserStatus())){
					 Office upOffice = officeService.get(user2.getOffice().getParent().getId());
					 //如果上级部门主负责人为空
					 if(null == upOffice.getPrimaryPerson() || null == upOffice.getPrimaryPerson().getId() || "".equals(upOffice.getPrimaryPerson().getId())){
						 return "";
					 }else {
						 User upUser = userDao.get(upOffice.getPrimaryPerson().getId());
						 if ("2".equals(upUser.getUserStatus()) || "3".equals(upUser.getUserStatus())) {
							 return "";
						 } else {
							 return upUser.getLoginName();
						 }
					 }
				 }else{
					 return userLoginName;
				 }
			}

		}
//		List<FlowProcRoleResolve> resultList = flowProcRoleResolveDao.queryUserForRoleCodeAndOrgId(flowProcRoleResolve);
//		if(resultList != null && resultList.size() > 0){
//			 String emp = resultList.get(0).getUserLoginName();
//			 if(emp.equals(employee)){
//				 return "";
//			 }else{
//
//				 //如果审批人为离职或离职未完结，则找上级部门主负责人,如果上级部门主负责人为离职或离职未完结，则返回空
//				 User temp = new User();
//				 temp.setLoginName(emp);
//				 User user = userDao.getByLoginName(temp);
//				 if("2".equals(user.getUserStatus()) || "3".equals(user.getUserStatus())){
//					 Office upOffice = officeService.get(user.getOffice().getParent().getId());
//					 User upUser = userDao.get(upOffice.getPrimaryPerson().getId());
//					 if("2".equals(upUser.getUserStatus()) || "3".equals(upUser.getUserStatus())){
//					 	return "";
//					 }else{
//					 	return upUser.getLoginName();
//					 }
//				 }else{
//					 return emp;
//				 }
//
//
////				return emp;
//			 }
		return "";
	}

    /**
     * 解析报销流程角色配置，注意：仅适用于报销流程！
     * @param businessId 流程业务主表记录ID
     * @param procRoleCode 流程角色编码
     * @return 匹配的审批人的登录名（唯一标识）
     */
    public String resolveExpenseProcRole(String businessId,String procRoleCode) {
        init();

        ExpenseFlow expenseFlow = expenseFlowService.get(businessId);
        String costCenterId = expenseFlow.getCostCenterId();
        //成本中心部门
        Office dept = officeService.get(costCenterId);
        //从缓存中拿出所有部门
        List<Office> officeList = officeService.findList(true);

        //查询对应的流程角色审批人信息
        FlowProcRoleResolve flowProcRoleResolve = new FlowProcRoleResolve();
        flowProcRoleResolve.setFlowPorcRoleCode(procRoleCode);
        List<FlowProcRoleResolve> resultList = flowProcRoleResolveDao.queryUserByOrgId(flowProcRoleResolve);
        if(null != resultList && resultList.size() > 0){

            int index = 0;
            FlowProcRoleResolve result = null;

            for(FlowProcRoleResolve detail : resultList) {

                if(dept.getParentIds().indexOf(detail.getOrgId()) > 0 ){

                    int temp = dept.getParentIds().indexOf(detail.getOrgId());
                    if(temp > index){
                        index = temp;
                        result = detail;
                    }

                }
            }
            String userLoginName = getSubOffices(officeList,result,dept);
            if(StringUtils.isNotBlank(userLoginName)){

                //如果审批人为离职或离职未完结，则找上级部门主负责人,如果上级部门主负责人为离职或离职未完结，则返回空
                User temp = new User();
                temp.setLoginName(userLoginName);
                User user2 = userDao.getByLoginName(temp);
                if("2".equals(user2.getUserStatus()) || "3".equals(user2.getUserStatus())){
                    Office upOffice = officeService.get(user2.getOffice().getParent().getId());
                    //如果上级部门主负责人为空
                    if(null == upOffice.getPrimaryPerson() || null == upOffice.getPrimaryPerson().getId() || "".equals(upOffice.getPrimaryPerson().getId())){
                        return "";
                    }else {
                        User upUser = userDao.get(upOffice.getPrimaryPerson().getId());
                        if ("2".equals(upUser.getUserStatus()) || "3".equals(upUser.getUserStatus())) {
                            return "";
                        } else {
                            return upUser.getLoginName();
                        }
                    }
                }else{
                    return userLoginName;
                }
            }

        }
        return "";
    }

	/**
	 * 获取对应部门的流程角色
	 * @param officeList
	 * @return
	 */
	public String getSubOffices(List<Office> officeList,FlowProcRoleResolve detail,Office dept) {
        //没有找到对应的流程角色明细配置，则自动跳过
        if(detail == null){
            return "";
        }

		List<Office> resultList = new ArrayList<>();
		String userLoginName = "";
		//筛选出符合的流程角色配置  减少循环遍历
		if(dept.getParentIds().indexOf(detail.getOrgId()) > 0 ){
				//把流程角色对应的部门的所有下级部门查出
				for (Office office : officeList) {
					if (office.getParentIds().indexOf(detail.getOrgId()) > 0) {
						resultList.add(office);
					}
				}
				//提交人所属部门是否在 对应的下级部门集合中  如果在返回对应的流程角色loginName
				if (resultList.size() > 0) {
					for (Office office : resultList) {
						if (office.getId().equals(dept.getId())) {
							userLoginName = detail.getUserLoginName();
						}
					}
				}
		}
		return userLoginName;
	}
}
