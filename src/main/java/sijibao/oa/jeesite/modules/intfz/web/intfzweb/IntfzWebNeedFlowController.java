package sijibao.oa.jeesite.modules.intfz.web.intfzweb;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.curator.shaded.com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sijibao.base.common.api.response.PagerResponse;
import com.sijibao.oa.activiti.api.exception.ServiceException;
import com.sijibao.oa.activiti.api.request.need.*;
import com.sijibao.oa.activiti.api.response.need.*;
import com.sijibao.oa.activiti.api.service.NeedFlowService;
import com.sijibao.oa.common.utils.WebUtils;
import com.sijibao.oa.common.web.BaseController;
import com.sijibao.oa.modules.intfz.bean.BaseResp;
import com.sijibao.oa.modules.intfz.enums.IntfzRs;
import com.sijibao.oa.modules.intfz.request.need.*;
import com.sijibao.oa.modules.intfz.request.need.AddCommentReq;
import com.sijibao.oa.modules.intfz.request.need.AddNeedFlowReq;
import com.sijibao.oa.modules.intfz.request.need.PromoteProgressReq;
import com.sijibao.oa.modules.intfz.response.common.PageResponse;
import com.sijibao.oa.modules.intfz.response.need.*;
import com.sijibao.oa.modules.intfz.service.common.IntfzCommonService;
import com.sijibao.oa.modules.intfz.service.report.IntfzNeedExportService;
import com.sijibao.oa.modules.intfz.utils.CharChangeUtils;
import com.sijibao.oa.modules.sys.entity.User;
import com.sijibao.oa.modules.sys.utils.UserUtils;
import com.sijibao.oa.office.api.IntfzNeedLabelService;
import com.sijibao.oa.office.api.IntfzNeedTypeService;
import com.sijibao.oa.office.api.response.need.NeedLabelResponse;
import com.sijibao.oa.office.api.response.need.NeedProgressResponse;
import com.sijibao.oa.office.api.response.need.NeedTypeResponse;
import com.sijibao.oa.office.api.response.need.QueryNeedProgressResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author Jianghao Zhang
 */
@Controller
@RequestMapping(value = "${frontPath}/needFlow")
@Api(value = "WEB协作流程服务", tags = "WEB协作流程服务")
public class IntfzWebNeedFlowController extends BaseController {

    @Autowired
    private NeedFlowService needFlowService;
    @Autowired
    private IntfzNeedTypeService intfzNeedTypeService;
    @Autowired
    private IntfzNeedLabelService intfzNeedLabelService;
    @Autowired
    private IntfzCommonService intfzCommonService;

    /**
     * 查看协作列表（分页查询）
     */
    @RequestMapping(value = "queryNeedFlowList")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "查看协作列表（分页查询）")
    public BaseResp<PageResponse<List<QueryNeedFlowListWebResp>>> queryNeedFlowList(
			@RequestBody QueryNeedFlowListWebReq req,
			@ApiParam(value = "登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) {

        WebUtils.initPre(request, response);
        User user = UserUtils.getUser(sessionid);

        List<QueryNeedFlowListWebResp> list = new LinkedList<>();
        QueryNeedFlowListWebParams params = change(req, QueryNeedFlowListWebParams.class);
        params.setUserId(user.getId());
        PagerResponse<QueryNeedFlowListWebResult> page = needFlowService.webQueryNeedFlowList(params);
        List<QueryNeedFlowListWebResult> resultList = page.getList();
        for (QueryNeedFlowListWebResult result : resultList) {
            list.add(change(result, QueryNeedFlowListWebResp.class));
        }

        return new BaseResp<>(IntfzRs.SUCCESS, "ok", new PageResponse<>(list, req.getPageNo(), page.getCount()));
    }

    /**
     * 发起新的协作
     */
    @RequestMapping(value = "addNeedFlow")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "新增协作")
    public BaseResp<String> addNeedFlow(
			@RequestBody AddNeedFlowReq req,
			@ApiParam(value = "登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) {

        WebUtils.initPre(request, response);
        User user = UserUtils.getUser(sessionid); //获取当前发起人信息
        CharChangeUtils.CharChange(req);//替换英文字符
        req.setProducSide("web");
        try {
            com.sijibao.oa.activiti.api.request.need.AddNeedFlowReq req1 = change(req, com.sijibao.oa.activiti.api.request.need.AddNeedFlowReq.class);
            needFlowService.addAndStartWorkFlow(req1, user.getId());
        } catch (ServiceException e) {
            return new BaseResp<>(IntfzRs.ERROR, e.getMessage(), "");
        }

        return new BaseResp<>(IntfzRs.SUCCESS, "协作发起成功", "");
    }

    /**
     * 类型列表查询(不带分页)
     */
    @RequestMapping(value = "queryNeedType")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "类型列表查询(不带分页)")
    public BaseResp<List<MainNeedTypeResponse>> queryNeedType(
			@RequestBody MainNeedTypeReq req,
			@ApiParam(value = "登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
        WebUtils.initPre(request, response);
        List<NeedTypeResponse> resp = intfzNeedTypeService.queryNeedType(req.getTypeId());
        ArrayList<MainNeedTypeResponse> list = Lists.newArrayList();
        for (NeedTypeResponse s : resp) {
            MainNeedTypeResponse ss = change(s, MainNeedTypeResponse.class);
            ArrayList<MainNeedProgressResponse> st = Lists.newArrayList();
            for (NeedProgressResponse sss : s.getNeedProgressList()) {
                MainNeedProgressResponse ssss = change(sss, MainNeedProgressResponse.class);
                st.add(ssss);
            }
            ss.setNeedProgressList(st);
            list.add(ss);
        }
        return new BaseResp<List<MainNeedTypeResponse>>(IntfzRs.SUCCESS, "成功！", list);
    }

    /**
     * 标签列表查询(不带分页)
     */
    @RequestMapping(value = "queryNeedLabel")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "标签列表查询(不带分页)")
    public BaseResp<List<MainNeedLabelResponse>> queryNeedLabel(
			@ApiParam(value = "登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
        WebUtils.initPre(request, response);
        List<NeedLabelResponse> resp = intfzNeedLabelService.queryNeedLabel();
        ArrayList<MainNeedLabelResponse> list = Lists.newArrayList();
        for (NeedLabelResponse s : resp) {
            MainNeedLabelResponse ss = change(s, MainNeedLabelResponse.class);
            list.add(ss);
        }
        return new BaseResp<List<MainNeedLabelResponse>>(IntfzRs.SUCCESS, "成功！", list);
    }

    /**
     * 查看协作详情
     */
    @RequestMapping(value = "queryDetail")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "查看协作详情")
    public BaseResp<QueryNeedFlowDetailWebResp> queryDetail(
			@RequestBody QueryNeedFlowDetailReq req,
			@ApiParam(value = "登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) {

        WebUtils.initPre(request, response);

        QueryDetailWebReq req1 = change(req, QueryDetailWebReq.class);
        QueryDetailWebResp resp = needFlowService.queryDetail(req1);
        QueryNeedFlowDetailWebResp resp1 = change(resp, QueryNeedFlowDetailWebResp.class);

        return new BaseResp<>(IntfzRs.SUCCESS, "ok", resp1);
    }

    /**
     * 查看历史进度（分页查询）
     */
    @RequestMapping(value = "queryHisProgress")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "查看历史进度（分页查询）")
    public BaseResp<PageResponse<List<QueryHisProgressResp>>> queryHisProgress(
			@RequestBody QueryHisProgressReq req,
			@ApiParam(value = "登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) {

        List<QueryHisProgressResp> aList = new LinkedList<>();

        PagerResponse<QueryHisProgressResult> page = needFlowService.queryHisProgressList(change(req, QueryHisProgressParams.class));
        for (QueryHisProgressResult result : page.getList()) {
            QueryHisProgressResp hisProgressResp = new QueryHisProgressResp();
            hisProgressResp.setProgressName(result.getProgressName());
            hisProgressResp.setIsEnd(result.getIsEnd());
            hisProgressResp.setChangeTime(result.getChangeTime().getTime());
            hisProgressResp.setPrincipal(result.getPrincipal());
            hisProgressResp.setHandleExplain(result.getHandleExplain());
            hisProgressResp.setUrgency(result.getUrgency());
            hisProgressResp.setPriority(result.getPriority());
            hisProgressResp.setProductEnd(result.getProductEnd());
            if (result.getPlanDesignTime() != null) {
                hisProgressResp.setPlanDesignTime(result.getPlanDesignTime().getTime());
            }
            if (result.getPlanDevTime() != null) {
                hisProgressResp.setPlanDevTime(result.getPlanDevTime().getTime());
            }
            if (result.getPlanTestTime() != null) {
                hisProgressResp.setPlanTestTime(result.getPlanTestTime().getTime());
            }
            if (result.getPlanReleaseTime() != null) {
                hisProgressResp.setPlanReleaseTime(result.getPlanReleaseTime().getTime());
            }
            if (result.getActualReleaseTime() != null) {
                hisProgressResp.setActualReleaseTime(result.getActualReleaseTime().getTime());
            }
            if (result.getTrainTime() != null) {
                hisProgressResp.setTrainTime(result.getTrainTime().getTime());
            }

            aList.add(hisProgressResp);
        }

        return new BaseResp<>(IntfzRs.SUCCESS, "ok", new PageResponse<>(aList, req.getPageNo(), page.getCount()));
    }

    /**
     * 查看历史负责人（分页查询）
     */
    @RequestMapping(value = "queryHisPrincipal")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "查看历史负责人（分页查询）")
    public BaseResp<PageResponse<List<QueryHisPrincipalWebResp>>> queryHisPrincipal(
			@RequestBody QueryHisPrincipalReq req,
			@ApiParam(value = "登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) {

        List<QueryHisPrincipalWebResp> list = new LinkedList<>();

        PagerResponse<QueryHisPrincipalWebResult> page = needFlowService.webQueryHisPrincipal(change(req, QueryHisPrincipalParams.class));
        for (QueryHisPrincipalWebResult result : page.getList()) {
            QueryHisPrincipalWebResp resp = new QueryHisPrincipalWebResp();
            resp.setName(result.getName());
            resp.setStartTime(result.getStartTime().getTime());
            resp.setDept(result.getDept());
            resp.setLoginName(result.getLoginName());

            list.add(resp);
        }

        return new BaseResp<>(IntfzRs.SUCCESS, "ok", new PageResponse<>(list, req.getPageNo(), page.getCount()));
    }

    /**
     * 添加评论
     */
    @RequestMapping(value = "addComment")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "添加评论")
    public BaseResp<String> addComment(
			@RequestBody AddCommentReq req,
			@ApiParam(value = "登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) {

        WebUtils.initPre(request, response);
        User user = UserUtils.getUser(sessionid); //获取当前操作用户信息
        CharChangeUtils.CharChange(req);//替换英文字符
        String msg;
        try {
            com.sijibao.oa.activiti.api.request.need.AddCommentReq req1 = change(req, com.sijibao.oa.activiti.api.request.need.AddCommentReq.class);
            req1.setUserId(user.getId());
            msg = needFlowService.addComment(req1);
        } catch (Exception e) {
            return new BaseResp<>(IntfzRs.ERROR, e.getMessage(), "");
        }
        if (!"success".equals(msg)) {
            return new BaseResp<>(IntfzRs.ERROR, msg, "");
        }
        return new BaseResp<>(IntfzRs.SUCCESS, "添加评论成功", "");
    }

    /**
     * 查看评论列表（分页查询）
     */
    @RequestMapping(value = "queryCommentList")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "查看评论列表（分页查询）")
    public BaseResp<PageResponse<List<QueryCommentListWebResp>>> queryCommentList(
			@RequestBody QueryCommentListReq req,
			@ApiParam(value = "登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) {

        PagerResponse<QueryCommentListWebResult> page = needFlowService.webQueryCommentList(change(req, QueryCommentListParams.class));

        List<QueryCommentListWebResp> list = new LinkedList<>();
        for (QueryCommentListWebResult result : page.getList()) {
            QueryCommentListWebResp resp = change(result, QueryCommentListWebResp.class);

            list.add(resp);
        }

        return new BaseResp<>(IntfzRs.SUCCESS, "ok", new PageResponse<>(list, req.getPageNo(), page.getCount()));
    }

    /**
     * 新增(推进)进度
     */
    @RequestMapping(value = "promoteProgress")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "新增进度")
    public BaseResp<String> promoteProgress(
			@RequestBody PromoteProgressReq req,
			@ApiParam(value = "登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) {

        WebUtils.initPre(request, response);
        User user = UserUtils.getUser(sessionid); //获取当前操作用户信息
        CharChangeUtils.CharChange(req);//替换英文字符
        String msg;
        try {
            com.sijibao.oa.activiti.api.request.need.PromoteProgressReq req1 = change(req, com.sijibao.oa.activiti.api.request.need.PromoteProgressReq.class);
            req1.setUserId(user.getId());
            msg = needFlowService.promoteProgress(req1);
        } catch (Exception e) {
            return new BaseResp<>(IntfzRs.ERROR, e.getMessage(), "");
        }

        if (!"success".equals(msg)) {
            return new BaseResp<>(IntfzRs.ERROR, msg, "");
        }
        return new BaseResp<>(IntfzRs.SUCCESS, "新增进度成功", "");
    }

    /**
     * 指定下级负责人
     */
    @RequestMapping(value = "setNextPrincipal")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "指定下级负责人")
    public BaseResp<String> setNextPrincipal(
			@RequestBody SetNextPrincipalReq req,
			@ApiParam(value = "登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) {

        WebUtils.initPre(request, response);
        User user = UserUtils.getUser(sessionid); //获取当前操作用户信息

        String msg;
        try {
            SetNextPrincipalParams params = change(req, SetNextPrincipalParams.class);
            params.setOperateUserId(user.getId());
            msg = needFlowService.setNextPrincipal(params);
        } catch (ServiceException e) {
            return new BaseResp<>(IntfzRs.ERROR, e.getMessage(), "");
        }
        if (!"success".equals(msg)) {
            return new BaseResp<>(IntfzRs.ERROR, msg, "");
        }
        return new BaseResp<>(IntfzRs.SUCCESS, "指定下级负责人成功", "");
    }

    /**
     * 查询当前参与人列表(不带分页)
     */
    @RequestMapping(value = "queryCurParticipantList")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "查询当前参与人列表(不带分页)")
    public BaseResp<List<QueryCurParticipantListResp>> queryCurParticipantList(
			@RequestBody QueryCurParticipantListReq req,
			@ApiParam(value = "登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) {
        WebUtils.initPre(request, response);

        List<QueryCurParticipantListResult> list = needFlowService.queryCurParticipantList(change(req, QueryCurParticipantListParams.class));
        List<QueryCurParticipantListResp> aList = new LinkedList<>();
        for (QueryCurParticipantListResult result : list) {
            QueryCurParticipantListResp resp = change(result, QueryCurParticipantListResp.class);
            aList.add(resp);
        }

        return new BaseResp<>(IntfzRs.SUCCESS, "成功！", aList);
    }

    /**
     * 编辑参与人列表
     */
    @RequestMapping(value = "editParticipantList")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "编辑参与人列表")
    public BaseResp<String> editParticipantList(
			@RequestBody EditParticipantListReq req,
			@ApiParam(value = "登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) {

        WebUtils.initPre(request, response);
        User user = UserUtils.getUser(sessionid);

        String msg;
        try {
            EditParticipantListParams params = change(req, EditParticipantListParams.class);
            params.setOperateUserId(user.getId());
            msg = needFlowService.editParticipantList(params);
        } catch (Exception e) {
            return new BaseResp<>(IntfzRs.ERROR, e.getMessage(), "");
        }

        if (!"success".equals(msg)) {
            return new BaseResp<>(IntfzRs.ERROR, msg, "");
        }
        return new BaseResp<>(IntfzRs.SUCCESS, "编辑参与人列表成功", "");
    }


    /**
     * 标签列表查询(不带分页)
     */
    @RequestMapping(value = "queryNeedProgress")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "增加进度-查询进度")
    public BaseResp<AddNeedProgressResponse> queryNeedProgress(
			@RequestBody QueryNeedFlowDetailReq req,
			@ApiParam(value = "登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
        WebUtils.initPre(request, response);
        QueryNeedProgressResponse res = intfzNeedTypeService.queryNeedProgress(req.getId());
        AddNeedProgressResponse resp = change(res, AddNeedProgressResponse.class);
        MainNeedTypeReq re = new MainNeedTypeReq();
        re.setTypeId(res.getTypeId());
        BaseResp<List<MainNeedTypeResponse>> type = this.queryNeedType(re, sessionid, request, response);
        resp.setMainNeedTypeResponse(type.getData().get(0));
        return new BaseResp<>(IntfzRs.SUCCESS, "成功！", resp);
    }

    /**
     * 导出进度情况或导出评论
     */
    @RequestMapping(value = "exportProgressOrComment")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "导出进度情况或评论excel")
    public BaseResp<String> exportProgressOrComment(@RequestBody ExportProgressOrCommentReq req,
													@ApiParam(value = "登录成功时返回的sessionid") @RequestHeader String sessionid,
													HttpServletRequest request, HttpServletResponse response) {
        WebUtils.initPre(request, response);
        User user = UserUtils.getUser(sessionid);

        String fileName = "";
        String url = "";
        DateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        if ("1".equals(req.getExportContent())) {
            fileName = "协作进度情况报表" + sdf.format(new Date()) + ".xls";

            ExportProgressOrCommentParams params = change(req, ExportProgressOrCommentParams.class);
            params.setUserId(user.getId());
            List<ExportProgressResult> list = needFlowService.queryProgressListForExport(params);

            url = new IntfzNeedExportService().exportProgress(list, fileName); //获取附件url
        } else if ("2".equals(req.getExportContent())) {
            fileName = "协作评论报表" + sdf.format(new Date()) + ".xls";

            ExportProgressOrCommentParams params = change(req, ExportProgressOrCommentParams.class);
            params.setUserId(user.getId());
            List<ExportCommentResult> list = needFlowService.queryCommentListForExport(params);

            url = new IntfzNeedExportService().exportComment(list, fileName); //获取附件url
        }

        String downLoadUrl = intfzCommonService.setFileDownLoadUrl(url, fileName);
        return new BaseResp<>(IntfzRs.SUCCESS, "", downLoadUrl);
    }

    /**
	 * WEB协作流程服务-当前负责人批量移动
	 * @param req
	 * @param sessionid
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "needFlowBatchMove")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "WEB协作流程服务-当前负责人批量移动")
	public BaseResp<String> needFlowBatchMove(
			@RequestBody MainNeedFlowBatchMoveReq req,
			@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		User user = UserUtils.getUser(sessionid);//获取当前申请人信息
		try {
			NeedFlowBatchMoveReq change = change(req,NeedFlowBatchMoveReq.class);
			change.setUpdateBy(user.getId());
			needFlowService.needFlowBatchMove(change);
		} catch (ServiceException e) {
			e.printStackTrace();
			return new BaseResp<String>(IntfzRs.ERROR,e.getMessage(),"");
		}
		return new BaseResp<String>(IntfzRs.SUCCESS,"操作成功！","");

	}

}
