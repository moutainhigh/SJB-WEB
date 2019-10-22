package sijibao.oa.jeesite.modules.act.service.expression;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sijibao.oa.common.service.BaseService;
import com.sijibao.oa.modules.flow.entity.ExpenseFlow;
import com.sijibao.oa.modules.flow.entity.RecpFlow;
import com.sijibao.oa.modules.flow.entity.ResourcesHandleFlow;
import com.sijibao.oa.modules.flow.service.ExpenseFlowService;
import com.sijibao.oa.modules.flow.service.RecpFlowService;
import com.sijibao.oa.modules.flow.service.ResourcesHandleFlowService;
import com.sijibao.oa.modules.intfz.utils.Constant;
import com.sijibao.oa.modules.oa.entity.ProjectInfo;
import com.sijibao.oa.modules.oa.service.ProjectInfoService;
import com.sijibao.oa.modules.sys.dao.UserDao;
import com.sijibao.oa.modules.sys.entity.Office;
import com.sijibao.oa.modules.sys.entity.User;
import com.sijibao.oa.modules.sys.service.OfficeService;
import com.sijibao.oa.modules.sys.utils.DictUtils;
import com.sijibao.oa.modules.sys.utils.UserUtils;

/**
 * 流程流转表达式解析
 * @author xuby
 *
 */
@Service
@Transactional(readOnly = true)
public class ResolverExpression extends BaseService {
	@Autowired
	private UserDao userDao;
	@Autowired
	private ExpenseFlowService expenseFlowService;
	@Autowired
	private OfficeService officeService;
	@Autowired
	private ProjectInfoService projectInfoService;
	@Autowired
	private ResourcesHandleFlowService resourcesHandleFlowService;
	@Autowired
	private RecpFlowService recpFlowService;

	/**
	 * 解析流程表达式，根据申请人所属部门判断流程走向
     *
     * @param loginName 申请人登录名（唯一标识）
     * @param orgName 部门名称
     * @param orgType 为“dept”表示找申请人的所属部门，为“org”表示找申请人的所属部门的上一级部门
     * @return true匹配，false不匹配
	 */
	public boolean resolverExpression(String loginName,String orgName,String orgType) {
		boolean result = false;
		User user = new User();
		user.setLoginName(loginName);
		user = userDao.getByLoginName(user);
		String name = user.getOffice().getName();//申请人所属部门的名称
		if("org".equals(orgType)){
			Office office = officeService.get(user.getOffice().getParentId());
			if(office != null){
				name = office.getName();//申请人所属部门的上一级部门名称
			}else{
				name = "";
			}
		}

		//如果<申请人所属部门名称>或者<申请人所属部门的上一级部门名称>与<orgName>匹配，则返回true。否则返回false。
		if(orgName.equals(name)){
			result = true;
		}
		return result;
	}

    /**
     * 解析流程表达式，根据成本中心判断流程走向。注意：目前仅适用于报销流程！
     *
     * @param businessId 报销流程业务主表记录id
     * @param orgId 部门ID，待校验是否与成本中心部门ID（或其上一级部门ID）匹配
     * @param orgName 部门名称，仅在流程图中作展示用，提高表达式可读性
     * @param orgType 为“dept”表示尝试匹配成本中心部门，为“org”表示尝试匹配成本中心部门的上一级部门
     * @return true匹配，false不匹配
     */
	public boolean expenseExpression(String businessId,String orgId,String orgName,String orgType){
        ExpenseFlow expenseFlow = expenseFlowService.get(businessId);
        String costCenterId = expenseFlow.getCostCenterId();
        boolean flag = false;
        if("org".equals(orgType)){
            Office office = officeService.get(costCenterId).getParent();
            if(office != null){
                costCenterId = office.getId();//成本中心部门的上一级部门ID
            }else{
                costCenterId = "";
            }
        }

        if(orgId.equals(costCenterId)){
            flag = true;
        }
        return flag;
    }
	
	/**
	 * 解析发票所属城市
	 * @param businessId
	 * @return
	 */
	public boolean taxCityResolverExpression(String businessId,String cityName){
		boolean flag = false;
		logger.debug("businessId"+businessId);
		String cityCode = DictUtils.getDictValue(cityName, "tax_city", "");//获取数据字典
		ExpenseFlow expenseFlow = expenseFlowService.get(businessId); //获取报销主体信息
		if(cityCode.equals(expenseFlow.getTaxCityCode())){
			flag = true;
		}
		return flag;
	}
	
	/**
	 * 判断是否是项目负责人审批
	 * @param businessId
	 * @return
	 */
	public boolean isProjectLeaderExperssion(String businessId){
		boolean flag = false;
		ExpenseFlow expenseFlow = expenseFlowService.get(businessId); //获取报销主体信息
		if(expenseFlow != null && StringUtils.isNotBlank(expenseFlow.getProjectId())){
			ProjectInfo projectInfo = projectInfoService.get(expenseFlow.getProjectId()); //获取项目相关信息
			if(projectInfo != null && projectInfo.getProjectLeader() != null && StringUtils.isNotBlank(projectInfo.getProjectLeader().getId())){
				User u = UserUtils.get(projectInfo.getProjectLeader().getId());
				if(u != null && StringUtils.equals(expenseFlow.getApplyPerCode(), u.getLoginName())){
					flag = true;
				}
			}else{
				flag = true;
			}
		}else{
			flag = true;
		}
		return flag;
	}
	
	/**
	 * 判断资源办理是否是被动办理(被动：true，主动:false)
	 * @param businessId
	 * @return
	 */
	public boolean checkResourcesHandleType(String businessId){
		boolean flag = false;
		ResourcesHandleFlow resourcesHandleFlow = resourcesHandleFlowService.get(businessId);
		if(resourcesHandleFlow != null && 
				StringUtils.isNotBlank(resourcesHandleFlow.getHandleType()) &&
				Constant.HANDLE_TYPE_TWO.equals(resourcesHandleFlow.getHandleType())){
			flag = true;
		}
		return flag;
	}

	/**
	 * 判断接待申请关联的项目是否是市场级(市场级:true 公司级:false)
	 * @param businessId
	 * @return
	 */
	public boolean checkProjectType(String businessId){
		boolean flag = false;
		RecpFlow recpFlow = recpFlowService.get(businessId);
		if(null != recpFlow) {
			String projectId = recpFlow.getProjectId();
			ProjectInfo projectInfo = projectInfoService.get(projectId);
			String type = projectInfo.getProjectType();
			if (Constant.PROJECT_TYPE_MARKET.equals(type)) {
				flag = true;
			}
		}
		return flag;
	}
}
