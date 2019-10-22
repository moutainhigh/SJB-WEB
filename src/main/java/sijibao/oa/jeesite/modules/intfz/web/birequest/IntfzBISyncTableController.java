package sijibao.oa.jeesite.modules.intfz.web.birequest;//package com.sijibao.oa.modules.intfz.web.birequest;
//
//import com.sijibao.oa.activiti.api.request.expense.QueryExpenseFlowTableForBIParam;
//import com.sijibao.oa.activiti.api.response.expense.QueryExpenseDetailTableForBIResult;
//import com.sijibao.oa.activiti.api.response.expense.QueryExpenseFlowTableForBIResult;
//import com.sijibao.oa.activiti.api.response.expense.QueryExpenseSubInfoTableForBIResult;
//import com.sijibao.oa.activiti.api.service.ExpenseFlowNewService;
//import com.sijibao.oa.common.utils.WebUtils;
//import com.sijibao.oa.common.web.BaseController;
//import com.sijibao.oa.crm.api.IntfzWebProjectInfoService;
//import com.sijibao.oa.crm.api.request.QueryCpcRelationForBIParam;
//import com.sijibao.oa.crm.api.response.QueryCpcRelationForBIResult;
//import com.sijibao.oa.crm.api.response.project.QueryProjectNodeTableForBIResult;
//import com.sijibao.oa.crm.api.response.project.QueryProjectTableForBIResult;
//import com.sijibao.oa.modules.intfz.bean.BaseResp;
//import com.sijibao.oa.modules.intfz.enums.IntfzRs;
//import com.sijibao.oa.modules.intfz.request.bi.QueryCpcRelationForBIReq;
//import com.sijibao.oa.modules.intfz.request.bi.QueryEmployeeStatusRecordTableForBIReq;
//import com.sijibao.oa.modules.intfz.request.bi.QueryExpenseFlowTableForBIReq;
//import com.sijibao.oa.modules.intfz.response.bi.*;
//import com.sijibao.oa.modules.intfz.service.employeestatus.IntfzEmployeeStatusrecordService;
//import com.sijibao.oa.modules.sys.service.DictService;
//import com.sijibao.oa.modules.sys.service.OfficeService;
//import com.sijibao.oa.modules.sys.service.PostService;
//import com.sijibao.oa.modules.sys.service.SystemService;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.LinkedList;
//import java.util.List;
//
///**
// * BI同步OA表数据
// */
//@Controller
//@RequestMapping(value = "BI/syncTable")
//@Api(value = "BI同步OA表数据", tags = "BI同步OA表数据")
//public class IntfzBISyncTableController extends BaseController {
//
//    @Autowired
//    SystemService systemService;
//    @Autowired
//    OfficeService officeService;
//    @Autowired
//    DictService dictService;
//    @Autowired
//    PostService postService;
//    @Autowired
//    ExpenseFlowNewService expenseFlowNewService;
//    @Autowired
//    IntfzEmployeeStatusrecordService intfzEmployeeStatusrecordService;
//    @Autowired
//    IntfzWebProjectInfoService intfzWebProjectInfoService;
//
//    /**
//     * 查询人员表数据
//     */
//    @RequestMapping(value = "queryUserTableForBI")
//    @ResponseBody
//    @ApiOperation(httpMethod = "POST", value = "查询人员表sys_user数据")
//    public BaseResp<List<UserRespForBI>> queryUserTableForBI(
//            HttpServletRequest request, HttpServletResponse response) {
//        WebUtils.initPre(request, response);
//        logger.info("===IntfzBISyncTableController queryUserTableForBI begin=== ");
//
//        List<UserRespForBI> userList = systemService.findAllColumnsForBI();
//
//        logger.info("===IntfzBISyncTableController queryUserTableForBI end===");
//        return new BaseResp<>(IntfzRs.SUCCESS, "ok", userList);
//    }
//
//    /**
//     * 查询机构表数据
//     */
//    @RequestMapping(value = "queryOfficeTableForBI")
//    @ResponseBody
//    @ApiOperation(httpMethod = "POST", value = "查询机构表sys_office数据")
//    public BaseResp<List<OfficeRespForBI>> queryOfficeTableForBI(
//            HttpServletRequest request, HttpServletResponse response) {
//        WebUtils.initPre(request, response);
//        logger.info("===IntfzBISyncTableController queryOfficeTableForBI begin=== ");
//
//        List<OfficeRespForBI> userList = officeService.findAllColumnsForBI();
//
//        logger.info("===IntfzBISyncTableController queryOfficeTableForBI end===");
//        return new BaseResp<>(IntfzRs.SUCCESS, "ok", userList);
//    }
//
//    /**
//     * 查询字典表数据
//     */
//    @RequestMapping(value = "queryDictTableForBI")
//    @ResponseBody
//    @ApiOperation(httpMethod = "POST", value = "查询字典表sys_dict数据")
//    public BaseResp<List<DictRespForBI>> queryDictTableForBI(
//            HttpServletRequest request, HttpServletResponse response) {
//        WebUtils.initPre(request, response);
//        logger.info("===IntfzBISyncTableController queryDictTableForBI begin=== ");
//
//        List<DictRespForBI> list = dictService.queryDictTableForBI();
//
//        logger.info("===IntfzBISyncTableController queryDictTableForBI end===");
//        return new BaseResp<>(IntfzRs.SUCCESS, "ok", list);
//    }
//
//    /**
//     * 查询岗位表数据
//     */
//    @RequestMapping(value = "queryPostTableForBI")
//    @ResponseBody
//    @ApiOperation(httpMethod = "POST", value = "查询岗位表sys_post数据")
//    public BaseResp<List<PostRespForBI>> queryPostTableForBI(
//            HttpServletRequest request, HttpServletResponse response) {
//        WebUtils.initPre(request, response);
//        logger.info("===IntfzBISyncTableController queryPostTableForBI begin=== ");
//
//        List<PostRespForBI> list = postService.queryPostTableForBI();
//
//        logger.info("===IntfzBISyncTableController queryPostTableForBI end===");
//        return new BaseResp<>(IntfzRs.SUCCESS, "ok", list);
//    }
//
//    /**
//     * 查询报销主表数据
//     */
//    @RequestMapping(value = "queryExpenseFlowTableForBI")
//    @ResponseBody
//    @ApiOperation(httpMethod = "POST", value = "查询报销主表oa_expense_flow数据")
//    public BaseResp<List<ExpenseFlowRespForBI>> queryExpenseFlowTableForBI(
//            @RequestBody QueryExpenseFlowTableForBIReq req,
//            HttpServletRequest request, HttpServletResponse response) {
//        WebUtils.initPre(request, response);
//        logger.info("===IntfzBISyncTableController queryExpenseFlowTableForBI req=== ", req.toString());
//        if (req.getApplyTimeBegin() == null || req.getApplyTimeEnd() == null) {
//            logger.error("======报销提交时间段必选======");
//            return new BaseResp<>(IntfzRs.PARAM, "报销提交时间段必选", null);
//        }
//
//        QueryExpenseFlowTableForBIParam param = change(req, QueryExpenseFlowTableForBIParam.class);
//        List<QueryExpenseFlowTableForBIResult> list = expenseFlowNewService.queryExpenseFlowTableForBI(param);
//        List<ExpenseFlowRespForBI> li = new LinkedList<>();
//        for (QueryExpenseFlowTableForBIResult i : list) {
//            ExpenseFlowRespForBI expenseFlowRespForBI = change(i, ExpenseFlowRespForBI.class);
//            li.add(expenseFlowRespForBI);
//        }
//
//        logger.info("===IntfzBISyncTableController queryExpenseFlowTableForBI end===");
//        return new BaseResp<>(IntfzRs.SUCCESS, "ok", li);
//    }
//
//    /**
//     * 查询报销明细表数据
//     */
//    @RequestMapping(value = "queryExpenseDetailTableForBI")
//    @ResponseBody
//    @ApiOperation(httpMethod = "POST", value = "查询报销明细表oa_expense_detail数据")
//    public BaseResp<List<ExpenseDetailRespForBI>> queryExpenseDetailTableForBI(
//            HttpServletRequest request, HttpServletResponse response) {
//        WebUtils.initPre(request, response);
//        logger.info("===IntfzBISyncTableController queryExpenseDetailTableForBI begin=== ");
//
//        List<QueryExpenseDetailTableForBIResult> list = expenseFlowNewService.queryExpenseDetailTableForBI();
//        List<ExpenseDetailRespForBI> li = new LinkedList<>();
//        for (QueryExpenseDetailTableForBIResult i : list) {
//            ExpenseDetailRespForBI expenseFlowRespForBI = change(i, ExpenseDetailRespForBI.class);
//            li.add(expenseFlowRespForBI);
//        }
//
//        logger.info("===IntfzBISyncTableController queryExpenseDetailTableForBI end===");
//        return new BaseResp<>(IntfzRs.SUCCESS, "ok", li);
//    }
//
//    /**
//     * 查询报销费用科目信息表数据
//     */
//    @RequestMapping(value = "queryExpenseSubInfoTableForBI")
//    @ResponseBody
//    @ApiOperation(httpMethod = "POST", value = "查询报销费用科目信息表oa_expenses_sub_info数据")
//    public BaseResp<List<ExpenseSubInfoRespForBI>> queryExpenseSubInfoTableForBI(
//            HttpServletRequest request, HttpServletResponse response) {
//        WebUtils.initPre(request, response);
//        logger.info("===IntfzBISyncTableController queryExpenseSubInfoTableForBI begin=== ");
//
//        List<QueryExpenseSubInfoTableForBIResult> list = expenseFlowNewService.queryExpenseSubInfoTableForBI();
//        List<ExpenseSubInfoRespForBI> li = new LinkedList<>();
//        for (QueryExpenseSubInfoTableForBIResult i : list) {
//            ExpenseSubInfoRespForBI expenseSubInfoRespForBI = change(i, ExpenseSubInfoRespForBI.class);
//            li.add(expenseSubInfoRespForBI);
//        }
//
//        logger.info("===IntfzBISyncTableController queryExpenseSubInfoTableForBI end===");
//        return new BaseResp<>(IntfzRs.SUCCESS, "ok", li);
//    }
//
//    /**
//     * 查询实施人员状态变更明细表数据
//     */
//    @RequestMapping(value = "queryEmployStatusRecordTableForBI")
//    @ResponseBody
//    @ApiOperation(httpMethod = "POST", value = "查询实施人员状态变更明细表oa_employee_statusrecord数据")
//    public BaseResp<List<EmployStatusRecordRespForBI>> queryEmployStatusRecordTableForBI(
//            @RequestBody QueryEmployeeStatusRecordTableForBIReq req,
//            HttpServletRequest request, HttpServletResponse response) {
//        WebUtils.initPre(request, response);
//        logger.info("===IntfzBISyncTableController queryEmployStatusRecordTableForBI req=== ", req.toString());
//        if (req.getBeginTime() == null || req.getEndTime() == null) {
//            logger.error("======状态变更提交时间段必选======");
//            return new BaseResp<>(IntfzRs.PARAM, "状态变更提交时间段必选", null);
//        }
//
//        List<EmployStatusRecordRespForBI> list = intfzEmployeeStatusrecordService.queryEmployStatusRecordTableForBI(req);
//
//        logger.info("===IntfzBISyncTableController queryEmployStatusRecordTableForBI end===");
//        return new BaseResp<>(IntfzRs.SUCCESS, "ok", list);
//    }
//
//    /**
//     * 查询项目表数据
//     */
//    @RequestMapping(value = "queryProjectTableForBI")
//    @ResponseBody
//    @ApiOperation(httpMethod = "POST", value = "查询项目表oa_project_info数据")
//    public BaseResp<List<ProjectRespForBI>> queryProjectTableForBI(
//            HttpServletRequest request, HttpServletResponse response) {
//        WebUtils.initPre(request, response);
//        logger.info("===IntfzBISyncTableController queryProjectTableForBI begin=== ");
//
//        List<QueryProjectTableForBIResult> list = intfzWebProjectInfoService.queryProjectTableForBI();
//        List<ProjectRespForBI> li = new LinkedList<>();
//        for (QueryProjectTableForBIResult i : list) {
//            ProjectRespForBI projectResp = change(i, ProjectRespForBI.class);
//            li.add(projectResp);
//        }
//
//        logger.info("===IntfzBISyncTableController queryProjectTableForBI end===");
//        return new BaseResp<>(IntfzRs.SUCCESS, "ok", li);
//    }
//
//    /**
//     * 查询项目节点表数据
//     */
//    @RequestMapping(value = "queryProjectNodeTableForBI")
//    @ResponseBody
//    @ApiOperation(httpMethod = "POST", value = "查询项目节点表oa_project_node数据")
//    public BaseResp<List<ProjectNodeRespForBI>> queryProjectNodeTableForBI(
//            HttpServletRequest request, HttpServletResponse response) {
//        WebUtils.initPre(request, response);
//        logger.info("===IntfzBISyncTableController queryProjectNodeTableForBI begin=== ");
//
//        List<QueryProjectNodeTableForBIResult> list = intfzWebProjectInfoService.queryProjectNodeTableForBI();
//        List<ProjectNodeRespForBI> li = new LinkedList<>();
//        for (QueryProjectNodeTableForBIResult i : list) {
//            ProjectNodeRespForBI projectNodeResp = change(i, ProjectNodeRespForBI.class);
//            li.add(projectNodeResp);
//        }
//
//        logger.info("===IntfzBISyncTableController queryProjectNodeTableForBI end===");
//        return new BaseResp<>(IntfzRs.SUCCESS, "ok", li);
//    }
//
//    /**
//     * 查询客户/项目/企业关联关系数据
//     */
//    @RequestMapping(value = "queryCustomerProjectCompanyRelationForBI")
//    @ResponseBody
//    @ApiOperation(httpMethod = "POST", value = "查询客户/项目/企业关联关系数据")
//    public BaseResp<List<CpcRelationRespForBI>> queryCustomerProjectCompanyRelationForBI(
//            @RequestBody QueryCpcRelationForBIReq req,
//            HttpServletRequest request, HttpServletResponse response) {
//        WebUtils.initPre(request, response);
//        logger.info("===IntfzBISyncTableController queryCustomerProjectCompanyRelationForBI req=== ", req.toString());
//        if (req.getBeginTime() == null || req.getEndTime() == null) {
//            logger.error("======更新时间区间必选======");
//            return new BaseResp<>(IntfzRs.PARAM, "更新时间区间必选", null);
//        }
//
//        QueryCpcRelationForBIParam param = change(req, QueryCpcRelationForBIParam.class);
//        List<QueryCpcRelationForBIResult> resultList = intfzWebProjectInfoService.queryCustomerProjectCompanyRelationForBI(param);
//        List<CpcRelationRespForBI> respList = new LinkedList<>();
//        for (QueryCpcRelationForBIResult i : resultList) {
//            CpcRelationRespForBI resp = change(i, CpcRelationRespForBI.class);
//            respList.add(resp);
//        }
//
//        logger.info("===IntfzBISyncTableController queryCustomerProjectCompanyRelationForBI end===");
//        return new BaseResp<>(IntfzRs.SUCCESS, "ok", respList);
//    }
//}
