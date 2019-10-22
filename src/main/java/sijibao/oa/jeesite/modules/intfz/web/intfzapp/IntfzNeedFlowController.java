package sijibao.oa.jeesite.modules.intfz.web.intfzapp;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
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
import com.sijibao.oa.modules.intfz.utils.CharChangeUtils;
import com.sijibao.oa.modules.intfz.validator.AppAuthority;
import com.sijibao.oa.modules.sys.entity.User;
import com.sijibao.oa.office.api.IntfzNeedLabelService;
import com.sijibao.oa.office.api.IntfzNeedTypeService;
import com.sijibao.oa.office.api.response.need.NeedLabelResponse;
import com.sijibao.oa.office.api.response.need.NeedProgressResponse;
import com.sijibao.oa.office.api.response.need.NeedTypeResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * APP协作流程相关
 *
 * @author Jianghao Zhang
 */
@Controller
@RequestMapping(value = "wechat/needFlow")
@Api(value = "APP协作流程服务", tags = "APP协作流程服务")
public class IntfzNeedFlowController extends BaseController {

    @Autowired
    private NeedFlowService needFlowService;
    @Autowired
    private IntfzNeedTypeService intfzNeedTypeService;
    @Autowired
    private IntfzNeedLabelService intfzNeedLabelService;

    /**
     * 查看协作列表（分页查询）
     */
    @RequestMapping(value = "queryNeedFlowList")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "查看协作列表（分页查询）")
    @AppAuthority
    public BaseResp<PageResponse<List<QueryNeedFlowListAppResp>>> queryNeedFlowList(
			@RequestBody QueryNeedFlowListAppReq req,
			@ApiParam(value = "clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
			@ApiParam(value = "客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
			HttpServletRequest request, HttpServletResponse response) {

        WebUtils.initPre(request, response);

        if (req.getPageNo() == 0 || req.getPageSize() == 0) {
            logger.info("===WxNeedFlowController  queryNeedFlowList res===: " + "null");
            return new BaseResp<>(IntfzRs.ERROR, "分页参数不能为空！",
                    new PageResponse<List<QueryNeedFlowListAppResp>>(null, 0, 0));
        }
        User user = super.getCurrWxUser(sjboacert, clientType); //获取当前操作用户信息

        QueryNeedFlowListAppParams req1 = change(req, QueryNeedFlowListAppParams.class);
        req1.setUserId(user.getId());
        PagerResponse<QueryNeedFlowListAppResult> page = needFlowService.appQueryNeedFlowList(req1);
        List<QueryNeedFlowListAppResult> list = page.getList();

        List<QueryNeedFlowListAppResp> resultList = new LinkedList<>();
        for (QueryNeedFlowListAppResult resp : list) {
            resultList.add(change(resp, QueryNeedFlowListAppResp.class));
        }

        return new BaseResp<>(IntfzRs.SUCCESS, "ok",
                new PageResponse<>(resultList, req.getPageNo(), page.getCount()));
    }

    /**
     * 发起新的协作流程
     */
    @RequestMapping(value = "addNeedFlow")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "新增协作")
    @AppAuthority
    public BaseResp<String> addNeedFlow(@RequestBody AddNeedFlowReq req,
										@ApiParam(value = "clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
										@ApiParam(value = "客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
										HttpServletRequest request, HttpServletResponse response) {

        BaseResp<String> resp = new BaseResp<>(IntfzRs.SUCCESS, "协作发起成功", "");
        WebUtils.initPre(request, response);
        User user = super.getCurrWxUser(sjboacert, clientType); //获取当前操作用户信息
        CharChangeUtils.CharChange(req);//替换英文字符
        req.setProducSide("APP");
        try {
            com.sijibao.oa.activiti.api.request.need.AddNeedFlowReq req1 = change(req, com.sijibao.oa.activiti.api.request.need.AddNeedFlowReq.class);
            needFlowService.addAndStartWorkFlow(req1, user.getId());
        } catch (ServiceException e) {
            resp = new BaseResp<>(IntfzRs.ERROR, e.getMessage(), "");
        }

        return resp;
    }

    /**
     * 查看协作详情
     */
    @RequestMapping(value = "queryDetail")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "查看协作详情")
    @AppAuthority
    public BaseResp<QueryNeedFlowDetailAppResp> queryDetail(@RequestBody QueryNeedFlowDetailReq req,
															@ApiParam(value = "clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
															@ApiParam(value = "客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
															HttpServletRequest request, HttpServletResponse response) {
        WebUtils.initPre(request, response);
        User user = super.getCurrWxUser(sjboacert, clientType); //获取当前操作用户信息

        QueryDetailAppReq req1 = change(req, QueryDetailAppReq.class);
        req1.setUserId(user.getId());
        QueryDetailAppResp resp = needFlowService.queryDetail(req1);
        QueryNeedFlowDetailAppResp resp1 = change(resp, QueryNeedFlowDetailAppResp.class);
        resp1.setIsEnd(resp.getIsEnd());
        return new BaseResp<>(IntfzRs.SUCCESS, "ok", resp1);
    }

    /**
     * 增加进度
     */
    @RequestMapping(value = "promoteProgress")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "增加进度")
    @AppAuthority
    public BaseResp<String> promoteProgress(@RequestBody PromoteProgressReq req,
											@ApiParam(value = "clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
											@ApiParam(value = "客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
											HttpServletRequest request, HttpServletResponse response) {
        BaseResp<String> resp = new BaseResp<>(IntfzRs.SUCCESS, "增加进度成功", "");
        WebUtils.initPre(request, response);
        User user = super.getCurrWxUser(sjboacert, clientType); //获取当前操作用户信息

        String msg = "";
        try {
            com.sijibao.oa.activiti.api.request.need.PromoteProgressReq params = change(req, com.sijibao.oa.activiti.api.request.need.PromoteProgressReq.class);
            params.setUserId(user.getId());
            msg = needFlowService.promoteProgress(params);
        } catch (Exception e) {
            resp = new BaseResp<>(IntfzRs.ERROR, e.getMessage(), "");
        }

        if (!"success".equals(msg)) {
            return new BaseResp<>(IntfzRs.ERROR, msg, "");
        }
        return resp;
    }

    /**
     * 添加评论
     */
    @RequestMapping(value = "addComment")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "添加评论")
    @AppAuthority
    public BaseResp<String> addComment(@RequestBody AddCommentReq req,
									   @ApiParam(value = "clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
									   @ApiParam(value = "客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
									   HttpServletRequest request, HttpServletResponse response) {

        BaseResp<String> resp = new BaseResp<>(IntfzRs.SUCCESS, "添加评论成功", "");
        WebUtils.initPre(request, response);
        User user = super.getCurrWxUser(sjboacert, clientType); //获取当前操作用户信息
        CharChangeUtils.CharChange(req);//替换英文字符
        String msg = "";
        try {
            com.sijibao.oa.activiti.api.request.need.AddCommentReq params = change(req, com.sijibao.oa.activiti.api.request.need.AddCommentReq.class);
            params.setUserId(user.getId());
            msg = needFlowService.addComment(params);
        } catch (Exception e) {
            resp = new BaseResp<>(IntfzRs.ERROR, e.getMessage(), "");
        }

        if (!"success".equals(msg)) {
            return new BaseResp<>(IntfzRs.ERROR, msg, "");
        }
        return resp;
    }

    /**
     * 指定下级负责人
     */
    @RequestMapping(value = "setNextPrincipal")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "指定下级负责人")
    @AppAuthority
    public BaseResp<String> setNextPrincipal(@RequestBody SetNextPrincipalReq req,
											 @ApiParam(value = "clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
											 @ApiParam(value = "客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
											 HttpServletRequest request, HttpServletResponse response) {

        BaseResp<String> resp = new BaseResp<>(IntfzRs.SUCCESS, "指定下级负责人成功", "");
        WebUtils.initPre(request, response);
        User user = super.getCurrWxUser(sjboacert, clientType); //获取当前操作用户信息

        String msg = "";
        try {
            SetNextPrincipalParams params = change(req, SetNextPrincipalParams.class);
            params.setOperateUserId(user.getId());
            msg = needFlowService.setNextPrincipal(params);
        } catch (Exception e) {
            resp = new BaseResp<>(IntfzRs.ERROR, e.getMessage(), "");
        }

        if (!"success".equals(msg)) {
            return new BaseResp<>(IntfzRs.ERROR, msg, "");
        }
        return resp;
    }

    /**
     * 查询当前参与人列表(不带分页)
     */
    @RequestMapping(value = "queryCurParticipantList")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "查询当前参与人列表(不带分页)")
    @AppAuthority
    public BaseResp<List<QueryCurParticipantListResp>> queryCurParticipantList(
			@RequestBody QueryCurParticipantListReq req,
			@ApiParam(value = "clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
			@ApiParam(value = "客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
			HttpServletRequest request, HttpServletResponse response) {
        WebUtils.initPre(request, response);
        QueryCurParticipantListParams params = change(req, QueryCurParticipantListParams.class);
        List<QueryCurParticipantListResult> list = needFlowService.queryCurParticipantList(params);

        List<QueryCurParticipantListResp> aList = new LinkedList<>();
        for (QueryCurParticipantListResult curParticipant : list) {
            QueryCurParticipantListResp participant = change(curParticipant, QueryCurParticipantListResp.class);
            aList.add(participant);
        }

        return new BaseResp<>(IntfzRs.SUCCESS, "ok", aList);
    }

    /**
     * 编辑参与人列表
     */
    @RequestMapping(value = "editParticipantList")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "编辑参与人列表")
    @AppAuthority
    public BaseResp<String> editParticipantList(@RequestBody EditParticipantListReq req,
												@ApiParam(value = "clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
												@ApiParam(value = "客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
												HttpServletRequest request, HttpServletResponse response) {

        BaseResp<String> resp = new BaseResp<>(IntfzRs.SUCCESS, "编辑参与人列表成功", "");
        WebUtils.initPre(request, response);
        User user = super.getCurrWxUser(sjboacert, clientType); //获取当前操作用户信息

        String msg = "";
        try {
            EditParticipantListParams params = change(req, EditParticipantListParams.class);
            params.setOperateUserId(user.getId());
            msg = needFlowService.editParticipantList(params);
        } catch (Exception e) {
            resp = new BaseResp<>(IntfzRs.ERROR, e.getMessage(), e.getMessage());
        }

        if (!"success".equals(msg)) {
            return new BaseResp<>(IntfzRs.ERROR, msg, "");
        }
        return resp;
    }

    /**
     * 查看评论列表（分页查询）
     */
    @RequestMapping(value = "queryCommentList")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "查看评论列表（分页查询）")
    @AppAuthority
    public BaseResp<PageResponse<List<QueryCommentListAppResp>>> queryCommentList(
			@RequestBody QueryCommentListReq req,
			@ApiParam(value = "clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
			@ApiParam(value = "客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
			HttpServletRequest request, HttpServletResponse response) {
        WebUtils.initPre(request, response);
        if (req.getPageNo() == 0 || req.getPageSize() == 0) {
            logger.info("===WxNeedFlowController  queryCommentList res===: " + "null");
            return new BaseResp<>(IntfzRs.ERROR, "分页参数不能为空！",
                    new PageResponse<List<QueryCommentListAppResp>>(null, 0, 0));
        }
//        User user = super.getCurrWxUser(sjboacert, clientType); //获取当前操作用户信息

        PagerResponse<QueryCommentListAppResult> page = needFlowService.appQueryCommentList(change(req, QueryCommentListParams.class));

        List<QueryCommentListAppResp> list = new LinkedList<>();
        for (QueryCommentListAppResult result : page.getList()) {
            QueryCommentListAppResp resp = change(result, QueryCommentListAppResp.class);

            list.add(resp);
        }

        return new BaseResp<>(IntfzRs.SUCCESS, "ok",
                new PageResponse<>(list, req.getPageNo(), page.getCount()));
    }

    /**
     * 查看历史进度（分页查询）
     */
    @RequestMapping(value = "queryHisProgress")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "查看历史进度（分页查询）")
    @AppAuthority
    public BaseResp<PageResponse<List<QueryHisProgressResp>>> queryHisProgress(
			@RequestBody QueryHisProgressReq req,
			@ApiParam(value = "clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
			@ApiParam(value = "客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
			HttpServletRequest request, HttpServletResponse response) {
        WebUtils.initPre(request, response);
        if (req.getPageNo() == 0 || req.getPageSize() == 0) {
            logger.info("===WxNeedFlowController  queryHisProgress res===: " + "null");
            return new BaseResp<>(IntfzRs.ERROR, "分页参数不能为空！",
                    new PageResponse<List<QueryHisProgressResp>>(null, 0, 0));
        }
//        User user = super.getCurrWxUser(sjboacert, clientType); //获取当前操作用户信息

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

        return new BaseResp<>(IntfzRs.SUCCESS, "ok",
                new PageResponse<>(aList, req.getPageNo(), page.getCount()));
    }

    /**
     * 查看历史负责人（分页查询）
     */
    @RequestMapping(value = "queryHisPrincipal")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "查看历史负责人（分页查询）")
    @AppAuthority
    public BaseResp<PageResponse<List<QueryHisPrincipalAppResp>>> queryHisPrincipal(
			@RequestBody QueryHisPrincipalReq req,
			@ApiParam(value = "clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
			@ApiParam(value = "客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
			HttpServletRequest request, HttpServletResponse response) {
        WebUtils.initPre(request, response);
        if (req.getPageNo() == 0 || req.getPageSize() == 0) {
            logger.info("===WxNeedFlowController  queryHisPrincipal resp===: " + "null");
            return new BaseResp<>(IntfzRs.ERROR, "分页参数不能为空！",
                    new PageResponse<List<QueryHisPrincipalAppResp>>(null, 0, 0));
        }
//        User user = super.getCurrWxUser(sjboacert, clientType); //获取当前操作用户信息

        List<QueryHisPrincipalAppResp> list = new LinkedList<>();

        PagerResponse<QueryHisPrincipalAppResult> page = needFlowService.appQueryHisPrincipal(change(req, QueryHisPrincipalParams.class));
        for (QueryHisPrincipalAppResult result : page.getList()) {
            QueryHisPrincipalAppResp resp = new QueryHisPrincipalAppResp();
            resp.setName(result.getName());
            resp.setStartTime(result.getStartTime().getTime());

            list.add(resp);
        }

        return new BaseResp<>(IntfzRs.SUCCESS, "ok",
                new PageResponse<>(list, req.getPageNo(), page.getCount()));
    }

    /**
     * 类型列表查询(不带分页)
     */
    @RequestMapping(value = "queryNeedType")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "类型列表查询(不带分页)")
    @AppAuthority
    public BaseResp<List<MainNeedTypeResponse>> queryNeedType(
			@RequestBody MainNeedTypeReq req,
			@ApiParam(value = "clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
			@ApiParam(value = "客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
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
    @AppAuthority
    public BaseResp<List<MainNeedLabelResponse>> queryNeedLabel(
			@ApiParam(value = "clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
			@ApiParam(value = "客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
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


}