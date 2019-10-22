package sijibao.oa.jeesite.modules.intfz.service.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.sijibao.base.common.api.response.PagerResponse;
import com.sijibao.oa.activiti.api.request.contract.ContractFlowListNewReq;
import com.sijibao.oa.activiti.api.request.project.ProjectApprovalFlowListReq;
import com.sijibao.oa.activiti.api.service.ContractFlowNewService;
import com.sijibao.oa.activiti.api.service.ProjectApprovalFlowService;
import com.sijibao.oa.common.config.Global;
import com.sijibao.oa.common.service.BaseService;
import com.sijibao.oa.common.utils.SpringContextHolder;
import com.sijibao.oa.crm.api.IntfzWebProjectInfoService;
import com.sijibao.oa.hamal.api.IntfzWebOrderService;
import com.sijibao.oa.hamal.api.response.HamalAbnormalOrderCountRsp;
import com.sijibao.oa.modules.act.dao.ActDao;
import com.sijibao.oa.modules.act.entity.TaskInfoEntity;
import com.sijibao.oa.modules.flow.dao.*;
import com.sijibao.oa.modules.flow.entity.*;
import com.sijibao.oa.modules.intfz.response.common.UserInfoResp;
import com.sijibao.oa.modules.intfz.utils.Constant;
import com.sijibao.oa.modules.oa.dao.DemandManagementDao;
import com.sijibao.oa.modules.oa.entity.DemandManagement;
import com.sijibao.oa.modules.sys.dao.MenuDao;
import com.sijibao.oa.modules.sys.dao.UserDao;
import com.sijibao.oa.modules.sys.entity.Menu;
import com.sijibao.oa.modules.sys.entity.User;
import com.sijibao.oa.modules.sys.utils.SysParamsUtils;

/**
 * 微信服务层
 *
 * @author Petter
 */
@Service
@Transactional(readOnly = true)
public class WxNotifyService extends BaseService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private ActDao actDao;
    @Autowired
    private ExpenseFlowDao expenseFLowDao;
    @Autowired
    private DemandManagementDao demandManagementDao;
    @Autowired
    private RecpFlowDao recpFlowDao;
    @Autowired
    private ResourcesApplyDao resourcesApplyDao;
    @Autowired
    private ResourcesHandleFlowDao resourcesHandleFlowDao;
    @Autowired
    private TravelFlowDao travelFlowDao;
    //	@Autowired
//	private OfficeService officeService ;
    @Autowired
    private MenuDao menuDao;
    @Autowired
    private ProjectApprovalFlowService projectApprovalFlowService ;

    private IntfzWebOrderService intfzWebOrderService;
    private IntfzWebProjectInfoService intfzWebProjectInfoService;
    private ContractFlowNewService contractFlowNewService;

    public void init() {
        if (intfzWebOrderService == null) {
            intfzWebOrderService = SpringContextHolder.getBean("intfzWebOrderService");
        }
        if (intfzWebProjectInfoService == null) {
            intfzWebProjectInfoService = SpringContextHolder.getBean("intfzWebProjectInfoService");
        }
        if (contractFlowNewService == null) {
            contractFlowNewService = SpringContextHolder.getBean("contractFlowNewService");
        }
    }

    @Transactional(readOnly = false)
    public boolean bindOpenId(User user, String openId) {
        user.setWechatId(openId);
        user.preUpdate();
        int i = userDao.update(user);
        if (i > 0) {
            return true;
        }
        return false;
    }

    /**
     * 查询待我审批总条数
     *
     * @param user
     * @param userInfoResp
     * @return
     */
    public int queryTodoListCount(User user, UserInfoResp userInfoResp) {
        int count = 0;
        List<String> list = userInfoResp.getListRecived();

        TaskInfoEntity taskInfoEntity = new TaskInfoEntity();
        taskInfoEntity.setAssignee(user.getLoginName());
        List<Map<String, Object>> maps = actDao.queryTodoListCountAll(taskInfoEntity);
        JSONObject json = new JSONObject();
        if(list != null && list.size() > 0) {
            for (int i = 0; i < maps.size(); i++) {
                Map<String, Object> each =  maps.get(i);
                json.put((String) each.get("type"), each.get("count"));
            }
        }

        if (list != null && list.size() > 0) {
            int expenseTodoListCount = json.optInt(Constant.EXPENSE_FLOW_APPLY_PROC,0); //报销申请待办
            if (list.contains("approval")) {
                count += expenseTodoListCount;
            }

            int recpTodoListCount = json.optInt(Constant.RECP_FLOW_APPLY_PROC,0); //接待申请待办
            if (list.contains("reception")) {
                count += recpTodoListCount;
            }

            int resourcesApplyTodoListCount = json.optInt(Constant.RESOURCES_APPLY_FLOW_APPLY_PROC,0); //资源申请待办
            if (list.contains("resource")) {
                count += resourcesApplyTodoListCount;
            }

            int resourcesHandleTodoListCount = json.optInt(Constant.RESOURCES_HANDLE_FLOW_APPLY_PROC,0); //资源办理待办
            if (list.contains("resHandle")) {
                count += resourcesHandleTodoListCount;
            }

            int travelFlowTodoListCount = json.optInt(Constant.TRAVEL_FLOW_APPLY_PROC,0); //出差申请待办
            if (list.contains("trip")) {
                count += travelFlowTodoListCount;
            }

                int contractFlowTodoListCount = json.optInt(Constant.CONTRACT_FLOW_APPLY_PROC,0); //合同申请待办
            if (list.contains("contract")) {
                count += contractFlowTodoListCount;
            }

            int projectApprovalFlowTodoListCount = json.optInt(Constant.PROJECT_APPROVAL_FLOW_APPLY_PROC,0); //立项申请待办
            if (list.contains("projectApproval")) {
                count += projectApprovalFlowTodoListCount;
            }
        }
        return count;
    }

    /**
     * 查询报销申请待审批任务
     *
     * @param user
     * @param list
     * @return
     */
    public int queryTodoExpenseCount(User user, List<String> list) {
        int count = 0;
        TaskInfoEntity taskInfoEntity = new TaskInfoEntity();
        taskInfoEntity.setAssignee(user.getLoginName());
        List<TaskInfoEntity> todoList = actDao.queryTodoExpenseFlow(taskInfoEntity);
        if (todoList != null && list != null && list.size() > 0) {
            for (String s : list) {
                if (s.equals("approval")) {
                    count = todoList.size();
                }
            }
        }
        return count;
    }


    /**
     * 查询接待申请待审批任务
     *
     * @param user
     * @param list
     * @return
     */
    public int queryTodoRecpFlowCount(User user, List<String> list) {
        int count = 0;
        TaskInfoEntity taskInfoEntity = new TaskInfoEntity();
        taskInfoEntity.setAssignee(user.getLoginName());
        List<TaskInfoEntity> todoList = actDao.queryTodoRecpFlow(taskInfoEntity);
        if (todoList != null && list != null && list.size() > 0) {
            for (String s : list) {
                if (s.equals("reception")) {
                    count = todoList.size();
                }
            }
        }
        return count;
    }

    /**
     * 查询市场需求申请待审批任务
     *
     * @param user
     * @return
     */
    public int queryTodoDemandmanagementMarketCount(User user) {
        int count = 0;
        TaskInfoEntity taskInfoEntity = new TaskInfoEntity();
        taskInfoEntity.setAssignee(user.getLoginName());
        List<TaskInfoEntity> todoList = actDao.queryTodoDemandManagementMarketFlow(taskInfoEntity);
        count = todoList.size();
        return count;
    }

    /**
     * 查询实施需求发起待审批任务
     * * @param user
     *
     * @return
     */
    public int queryTodoEDemandmanagementImplementCount(User user) {
        int count = 0;
        TaskInfoEntity taskInfoEntity = new TaskInfoEntity();
        taskInfoEntity.setAssignee(user.getLoginName());
        List<TaskInfoEntity> todoList = actDao.queryTodoDemandManagementImplementFlow(taskInfoEntity);
        if (todoList != null) {
            count = todoList.size();
        }
        return count;
    }

    /**
     * 查询出差申请待审批任务
     * * @param user
     *
     * @param list
     * @return
     */
    public int queryTodoTravelFlowCount(User user, List<String> list) {
        int count = 0;
        TaskInfoEntity taskInfoEntity = new TaskInfoEntity();
        taskInfoEntity.setAssignee(user.getLoginName());
        List<TaskInfoEntity> todoList = actDao.queryTodoTravelFlow(taskInfoEntity);
        if (todoList != null && list != null && list.size() > 0) {
            for (String s : list) {
                if (s.equals("trip")) {
                    count = todoList.size();
                }
            }
        }
        return count;
    }

    /**
     * 查询资源申请待审批任务
     * * @param user
     *
     * @param list
     * @return
     */
    public int queryTodoResourcesApplyCount(User user, List<String> list) {
        int count = 0;
        TaskInfoEntity taskInfoEntity = new TaskInfoEntity();
        taskInfoEntity.setAssignee(user.getLoginName());
        List<TaskInfoEntity> todoList = actDao.queryTodoResourcesApplyFlow(taskInfoEntity);
        if (todoList != null && list != null && list.size() > 0) {
            for (String s : list) {
                if (s.equals("resource")) {
                    count = todoList.size();
                }
            }
        }
        return count;
    }

    /**
     * 查询资源办理待审批任务
     * * @param user
     *
     * @param list
     * @return
     */
    public int queryTodoResourcesHandleCount(User user, List<String> list) {
        int count = 0;
        TaskInfoEntity taskInfoEntity = new TaskInfoEntity();
        taskInfoEntity.setAssignee(user.getLoginName());
        List<TaskInfoEntity> todoList = actDao.queryTodoResourcesHandleFlow(taskInfoEntity);
        if (todoList != null) {
            if (todoList != null && list != null && list.size() > 0) {
                for (String s : list) {
                    if (s.equals("resHandle")) {
                        count = todoList.size();
                    }
                }
            }
        }
        return count;
    }

    /**
     * 合同申请待办任务
     *
     * @param user
     * @param list
     * @return
     */
    public int queryTodoContractFlowCount(User user, List<String> list) {
        int count = 0;
        TaskInfoEntity taskInfoEntity = new TaskInfoEntity();
        taskInfoEntity.setAssignee(user.getLoginName());
        List<TaskInfoEntity> todoList = actDao.queryTodoContractFlow(taskInfoEntity);
        if (todoList != null) {
            if (todoList != null && list != null && list.size() > 0) {
                for (String s : list) {
                    if (s.equals("contract")) {
                        count = todoList.size();
                    }
                }
            }
        }
        return count;
    }

    /**
     * 立项申请待办任务
     *
     * @param user
     * @param list
     * @return
     */
    public int queryTodoProjectApprovalCountFlowCount(User user, List<String> list) {
        int count = 0;
        TaskInfoEntity taskInfoEntity = new TaskInfoEntity();
        taskInfoEntity.setAssignee(user.getLoginName());
        List<TaskInfoEntity> todoList = actDao.queryTodoProjectApprovalFlow(taskInfoEntity);
        if (todoList != null) {
            if (todoList != null && list != null && list.size() > 0) {
                for (String s : list) {
                    if (s.equals("projectApproval")) {
                        count = todoList.size();
                    }
                }
            }
        }
        return count;
    }

    /**
     * 查询我的申请总条数
     *
     * @param user
     * @param userInfoResp
     * @return
     */
    public int queryMyApplyCount(User user, UserInfoResp userInfoResp) {
        int count = 0;
        List<String> list = userInfoResp.getListRecived();

        List<Map<String, Object>> maps = expenseFLowDao.queryMyApplyCount("2,3".split(","), user.getLoginName(), user.getId());

        JSONObject json = new JSONObject();
        if(list != null && list.size() > 0) {
            for (int i = 0; i < maps.size(); i++) {
                Map<String, Object> each =  maps.get(i);
                json.put((String) each.get("type"), each.get("count"));
            }
        }

        if (list != null && list.size() > 0) {
            try {
                //查询报销条目
                int expenseCount = json.optInt(Constant.EXPENSE_FLOW_APPLY_PROC,0);
                if (list.contains("approval")) {
                    count += expenseCount;
                    logger.info("报销申请数:" + expenseCount);
                }

                //查询接待条目
                int recpFlowCount = json.optInt(Constant.RECP_FLOW_APPLY_PROC,0);
                if (list.contains("reception")) {
                    count += recpFlowCount;
                    logger.info("接待申请数:" + recpFlowCount);
                }

                //资源申请
                int resourcesApplyCount = json.optInt(Constant.RESOURCES_APPLY_FLOW_APPLY_PROC,0);
                if (list.contains("resource")) {
                    count += resourcesApplyCount;
                    logger.info("资源申请数:" + resourcesApplyCount);
                }

                //资源办理
                int resourcesHandleCount = json.optInt(Constant.RESOURCES_HANDLE_FLOW_APPLY_PROC,0);
                if (list.contains("resHandle")) {
                    count += resourcesHandleCount;
                    logger.info("资源办理申请数:" + resourcesHandleCount);
                }

                //出差申请
                int travelFlowCount = json.optInt(Constant.TRAVEL_FLOW_APPLY_PROC,0);
                if (list.contains("trip")) {
                    count += travelFlowCount;
                    logger.info("出差申请数:" + travelFlowCount);
                }

                //合同申请
                int contractFlowCount = json.optInt(Constant.CONTRACT_FLOW_APPLY_PROC,0);
                if (list.contains("contract")) {
                    count += contractFlowCount;
                    logger.info("合同审批申请数:" + contractFlowCount);
                }
                //立项申请
                int projectApprovalFlowCount = json.optInt(Constant.PROJECT_APPROVAL_FLOW_APPLY_PROC,0);
                if (list.contains("projectApproval")) {
                    count += projectApprovalFlowCount;
                    logger.info("立项审批申请数:" + projectApprovalFlowCount);
                }
            } catch (Exception e) {
                logger.error("======查询我的申请总条数出现异常======");
                logger.error(e.getMessage());
                e.printStackTrace();
                count = 0;
            }

        }
        return count;
    }

    /**
     * 查询报销申请条数
     *
     * @param user
     * @param list
     * @param list
     * @return
     */
    public int queryMyApplyExpenseCount(User user, List<String> list) {
        ExpenseFlow expenseFlow = new ExpenseFlow();
        expenseFlow.setExpenseStatusIn("2,3");
        expenseFlow.setCreateBy(user);
        int count = expenseFLowDao.findMyExpenseCount(expenseFlow);
        if (list.contains("approval")) {
            return count;
        } else {
            return 0;
        }
    }

    /**
     * 查询接待申请条数
     *
     * @param user
     * @param list
     * @return
     */
    public int queryMyApplyRecpFlowCount(User user, List<String> list) {
        RecpFlow recpFlow = new RecpFlow();
        recpFlow.setRecpStatusIn("2,3");
        recpFlow.setCreateBy(user);
        int count = recpFlowDao.findMyRecpFlowCount(recpFlow);
        if (list.contains("reception")) {
            return count;
        } else {
            return 0;
        }
    }

    /**
     * 查询市场发起需求申请条数
     *
     * @param user
     * @return
     */
    public int queryMyApplyDemandManagementMarketCount(User user) {
        int count = 0;
        DemandManagement d = new DemandManagement();
        d.setDemandStatusIn("2,3");
        d.setBillType("1");
        d.setCreateBy(user);
        List<DemandManagement> dList = demandManagementDao.findList(d);
        if (dList != null) {
            count = dList.size();
        }
        return count;
    }

    /**
     * 查询实施发起需求条数
     *
     * @param user
     * @return
     */
    public int queryMyApplyDemandManagementImplementCount(User user) {
        int count = 0;
        DemandManagement d = new DemandManagement();
        d.setDemandStatusIn("2,3");
        d.setBillType("2");
        d.setCreateBy(user);
        List<DemandManagement> dList = demandManagementDao.findList(d);
        if (dList != null) {
            count = dList.size();
        }
        return count;
    }

    /**
     * 查询资源申请发起条数
     *
     * @param user
     * @param list
     * @return
     */
    public int queryMyApplyResourcesApplyCount(User user, List<String> list) {
        ResourcesApply r = new ResourcesApply();
        r.setResourcesStatusIn("2,3");
        r.setCreateBy(user);
        int count = resourcesApplyDao.findMyApplyResourcesApplyCount(r);
        if (list.contains("resource")) {
            return count;
        } else {
            return 0;
        }
    }

    /**
     * 查询资源办理发起条数
     *
     * @param user
     * @param list
     * @return
     */
    public int queryMyApplyResourcesHandleCount(User user, List<String> list) {
        ResourcesHandleFlow r = new ResourcesHandleFlow();
        r.setResourcesHandleStatusIn("2,3");
        r.setCreateBy(user);
        int count = resourcesHandleFlowDao.findMyApplyResourcesHandleCount(r);
        if (list.contains("resHandle")) {
            return count;
        } else {
            return 0;
        }
    }

    /**
     * 查询出差申请发起条数
     *
     * @param user
     * @param list
     * @return
     */
    public int queryMyApplyTravelFlowCount(User user, List<String> list) {
        TravelFlow r = new TravelFlow();
        r.setTravelStatusIn("2,3");
        r.setCreateBy(user);
        int count = travelFlowDao.findMyApplyTravelFlowCount(r);
        if (list.contains("trip")) {
            return count;
        } else {
            return 0;
        }
    }

    /**
     * 查询合同申请发起条数
     *
     * @param user
     * @param list
     * @return
     */
    public int queryMyApplyContractFlowCount(User user, List<String> list) {
        init();
        ContractFlowListNewReq req = new ContractFlowListNewReq();
        req.setContractFlowStatusIn("2,3");
        req.setUserId(user.getId());
        int count = contractFlowNewService.findMyContractFlowCount(req);
        if (list.contains("contract")) {
            return count;
        } else {
            return 0;
        }
    }
    /**
     * 查询立项申请发起条数
     *
     * @param user
     * @param list
     * @return
     */
    public int queryMyApplyProjectApprovalFlowCount(User user, List<String> list) {
        ProjectApprovalFlowListReq projectApproval = new ProjectApprovalFlowListReq();
        projectApproval.setProjectApprovalStatusIn("2,3");
        projectApproval.setUserId(user.getId());
        projectApproval.setCreateBy(user.getId());
        int count = projectApprovalFlowService.findMyProjectApprovalFlowCount(projectApproval);
        if (list.contains("contract")) {
            return count;
        } else {
            return 0;
        }
    }

    /**
     * 获取异常单总条数
     *
     * @param user
     * @return
     */
    public int queryErrorStockOrderCount(User user) {
        init();
        int count = 0;
        try {
            List<String> companyCodes = intfzWebProjectInfoService.queryCompanyCodesByUser(user.getId());
            if (companyCodes == null || companyCodes.size() == 0) {
                return count;
            }

            PagerResponse<HamalAbnormalOrderCountRsp> resp = intfzWebOrderService.abnormalOrderCountByCompanyCode(companyCodes, "", 1, 1000);
            if (resp != null && resp.getList() != null) {
                for (HamalAbnormalOrderCountRsp o : resp.getList()) {
                    count += o.getCount();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        return count;
    }


    public Map<String, List<String>> getPermissionList(User user) {
        Map<String, List<String>> resultMap = new HashMap<String, List<String>>();

        String rootId = SysParamsUtils.getParamValue(Global.APP_MENU_ROOT, Global.SYS_PARAMS, "");
        Menu query = new Menu();
        query.setUserId(user.getId());
        query.setParentIds("%," + rootId + ",%");
        List<Menu> menuList = menuDao.findByUserIdAndParentIds(query);
        if (menuList != null && menuList.size() > 0) {
            String listMenuParentId = "";
            String listToolParentId = "";
            String listParentId = "";
            for (Menu menu : menuList) {
                if (Global.APP_MENU_LIST_ROOT.equals(menu.getRemarks())) {
                    listMenuParentId = menu.getId(); //获取列表权限的根节点
//					break;
                }
                if (Global.APP_MENU_LIST_TOOL.equals(menu.getRemarks())) {
                    listToolParentId = menu.getId();
                }
                if (Global.APP_MENU_ROOT.equals(menu.getRemarks())) {
                    listParentId = menu.getId();
                }
            }

            List<String> mainList = Lists.newArrayList(); //首页菜单
            List<String> list = Lists.newArrayList(); //列表菜单
            List<String> toolsList = Lists.newArrayList(); //小工具菜单
            for (Menu menu : menuList) {
                if (StringUtils.isBlank(listMenuParentId)) { //如果没有列表权限，则直接返回首页菜单权限
                    if (StringUtils.isNotBlank(menu.getRemarks())) {
                        mainList.add(menu.getRemarks());
                    }
                } else { //有列表权限，则分开返回
                    if (StringUtils.isNotBlank(menu.getRemarks())) {
                        if (listMenuParentId.equals(menu.getParentId())) {
                            list.add(menu.getRemarks());
                        } else if (listParentId.equals(menu.getParentId())) {
                            mainList.add(menu.getRemarks());
                        } else if (listToolParentId.equals(menu.getParentId())) {
                            toolsList.add(menu.getRemarks());
                        }
                    }
                }
            }

            resultMap.put("mainList", mainList);
            resultMap.put("list", list);
            resultMap.put("tools", toolsList);
            return resultMap;
        }
        return null;
    }
}
