package sijibao.oa.jeesite.modules.intfz.web.intfzapp;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.sijibao.base.common.api.response.PagerResponse;
import com.sijibao.oa.activiti.api.exception.ServiceException;
import com.sijibao.oa.activiti.api.request.project.*;
import com.sijibao.oa.activiti.api.response.project.ProjectApprovalFlowDetailResponse;
import com.sijibao.oa.activiti.api.response.project.ProjectApprovalFlowResp;
import com.sijibao.oa.activiti.api.response.project.ProjectApprovalLinkmanResp;
import com.sijibao.oa.activiti.api.service.ProjectApprovalFlowService;
import com.sijibao.oa.common.utils.StringUtils;
import com.sijibao.oa.common.utils.WebUtils;
import com.sijibao.oa.common.web.BaseController;
import com.sijibao.oa.modules.intfz.bean.BaseResp;
import com.sijibao.oa.modules.intfz.enums.IntfzRs;
import com.sijibao.oa.modules.intfz.request.projectapproval.*;
import com.sijibao.oa.modules.intfz.response.common.PageResponse;
import com.sijibao.oa.modules.intfz.response.projectapproval.MainProjectApprovalFlowDetailResponse;
import com.sijibao.oa.modules.intfz.response.projectapproval.MainProjectApprovalFlowResp;
import com.sijibao.oa.modules.intfz.response.projectapproval.MainProjectApprovalLinkmanResp;
import com.sijibao.oa.modules.intfz.utils.Constant;
import com.sijibao.oa.modules.intfz.validator.AppAuthority;
import com.sijibao.oa.modules.sys.entity.User;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * APP项目立项流程
 *
 * @author Petter
 */
@Controller
@RequestMapping(value = "wechat/projectApprovalFlow")
@Api(value = "APP项目立项流程", tags = "APP项目立项流程")
public class IntfzProjectApprovalFlowContoller extends BaseController {

    @Autowired
    private ProjectApprovalFlowService projectApprovalFlowService;

    /**
     * 发起立项申请
     */
    @RequestMapping(value = "projectApprovalApply")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "APP端立项-申请")
    @AppAuthority
    public BaseResp<String> projectApprovalApply(
			@Valid @RequestBody MainProjectApprovalFlowReq req,
			@ApiParam(value = "clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
			@ApiParam(value = "客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
			HttpServletRequest request, HttpServletResponse response) {
        WebUtils.initPre(request, response);
        User user = super.getCurrWxUser(sjboacert, clientType); //获取当前申请人信息
        try {
            ProjectApprovalFlowReq projectApprovalFlow = change(req, ProjectApprovalFlowReq.class);
            projectApprovalFlow.setProductSide("APP");
            List<MainProjectApprovalLinkmanReq> linkMen = req.getProjectApprovalLinkman();
            List<ProjectApprovalLinkmanReq> linkmenReq = Lists.newArrayList();
            if(linkMen != null &&linkMen.size() > 0){
                for(MainProjectApprovalLinkmanReq linkman : linkMen){
                    linkmenReq.add(change(linkman,ProjectApprovalLinkmanReq.class));
                }
            }
            projectApprovalFlow.setProjectApprovalLinkman(linkmenReq);
            projectApprovalFlowService.apply(projectApprovalFlow, user.getId(), Constant.CLIENT_TYPE_APP);
        } catch (ServiceException e) {
            return new BaseResp<>(IntfzRs.ERROR, e.getMessage(), "");
        }
        return new BaseResp<>(IntfzRs.SUCCESS, "申请发起成功", "");
    }

    /**
     * 同意/驳回
     */
    @RequestMapping(value = "completeTask")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "APP端立项审批-同意/驳回")
    @AppAuthority
    public BaseResp<String> completeTask(
			@RequestBody MainProjectApprovalFlowCompleteTaskReq req,
			@ApiParam(value = "clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
			@ApiParam(value = "客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
			HttpServletRequest request, HttpServletResponse response) {
        WebUtils.initPre(request, response);
        User user = super.getCurrWxUser(sjboacert, clientType); //获取当前申请人信息
        if (StringUtils.isBlank(req.getComment()) && "no".equals(req.getFlag())) {
            return new BaseResp<>(IntfzRs.ERROR, "请填写审核意见", "");
        }
        if (StringUtils.isBlank(req.getProcInsId())) {
            return new BaseResp<>(IntfzRs.ERROR, "procInsId is not null", "");
        }
        if (StringUtils.isBlank(req.getProjectApprovalFlowId())) {
            return new BaseResp<>(IntfzRs.ERROR, "expenseFormId is not null", "");
        }

        try {
            ProjectApprovalFlowCompleteTaskReq projectApprovalFlowCompleteTask = change(req, ProjectApprovalFlowCompleteTaskReq.class);
            projectApprovalFlowCompleteTask.setProductSide("APP");
            projectApprovalFlowService.completeTask(projectApprovalFlowCompleteTask, user.getId()); //完成当前任务
        } catch (ServiceException e) {
            return new BaseResp<>(IntfzRs.ERROR, e.getMessage(), "");
        }
        return new BaseResp<>(IntfzRs.SUCCESS, "审批成功", "");
    }

    /**
     * 撤销
     */
    @RequestMapping(value = "repealTask")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "APP端立项-流程收回")
    @AppAuthority
    public BaseResp<String> repealTask(@RequestBody MainProjectApprovalFlowRepealRequest req,
                                       @ApiParam(value = "clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
                                       @ApiParam(value = "客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType) {
        if (StringUtils.isBlank(req.getTaskId())) {
            return new BaseResp<>(IntfzRs.ERROR, "任务ID不能为空!", "");
        }
        if (StringUtils.isBlank(req.getProcInsId())) {
            return new BaseResp<>(IntfzRs.ERROR, "流程实例ID不能为空!", "");
        }
        //获取当前申请人信息
        User user = super.getCurrWxUser(sjboacert, clientType);
        try {
            ProjectApprovalFlowRepealReq projectApprovalFlowRepeal = change(req, ProjectApprovalFlowRepealReq.class);
            projectApprovalFlowService.repealTask(projectApprovalFlowRepeal, user.getId());
        } catch (ServiceException e) {
            return new BaseResp<>(IntfzRs.ERROR, e.getMessage(), "");
        }
        return new BaseResp<>(IntfzRs.SUCCESS, "流程收回成功!", "");
    }

    /**
     * 删除申请
     */
    @RequestMapping(value = "repealApply")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "APP端立项-删除")
    @AppAuthority
    public BaseResp<String> repealApply(@RequestBody MainProjectApprovalFlowHandleReq req,
										@ApiParam(value = "clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
										@ApiParam(value = "客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
										HttpServletRequest request, HttpServletResponse response) {
        WebUtils.initPre(request, response);
        if (StringUtils.isBlank(req.getProjectApprovalFlowId())) {
            logger.info("=======WxExpenseFlowContoller repealApply end=============expenseFlowId is not null");
            return new BaseResp<>(IntfzRs.ERROR, "expenseFlowId is not null", "");
        }
        User user = super.getCurrWxUser(sjboacert, clientType); //获取当前申请人信息

        try {
            projectApprovalFlowService.delete(req.getProjectApprovalFlowId(), user.getId());  //删除单据
        } catch (ServiceException e) {
            return new BaseResp<>(IntfzRs.ERROR, e.getMessage(), "");
        }
        return new BaseResp<>(IntfzRs.SUCCESS, "删除立项申请成功", "");
    }

    /**
     * 查询我的单据列表
     *
     * @param request  pageNo:当前页, pageSize:每页记录数
     * @param response data:{"list":[], pageNo:"当前页", total:"当前页数"}
     */
    @RequestMapping(value = "queryMyProjectApprovalFlowList")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "APP端立项-查询列表")
    @AppAuthority
    public BaseResp<PageResponse<List<MainProjectApprovalFlowResp>>> queryMyProjectApprovalFlowList(
			@RequestBody MainProjectApprovalFlowListReq req,
			@ApiParam(value = "clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
			@ApiParam(value = "客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
			HttpServletRequest request, HttpServletResponse response) {
        WebUtils.initPre(request, response);
        if (req.getPageNo() == 0 || req.getPageSize() == 0) {
            return new BaseResp<>(IntfzRs.ERROR, "分页参数不能为空！",
                    new PageResponse<>(null, 0, 0));
        }
        User user = super.getCurrWxUser(sjboacert, clientType); //获取当前申请人信息
        ProjectApprovalFlowListReq handleReq = change(req,ProjectApprovalFlowListReq.class);
        handleReq.setUserId(user.getId());
        if(Constant.expense_approve.equals(String.valueOf(req.getProjectApprovalStatus()))){ //运行中搜索包括审批中和被驳回
            handleReq.setProjectApprovalStatusIn("2,3");
            handleReq.setProjectApprovalStatus("");
        }else if(Constant.expense_approve_end.equals(String.valueOf(req.getProjectApprovalStatus()))){
            handleReq.setProjectApprovalStatusIn("1,0");
            handleReq.setProjectApprovalStatus("");
        }else{
            handleReq.setProjectApprovalStatus("4");
        }
        if(req.getApplyTimeEnd() != 0 && req.getEndApplyTime() == 0){
            handleReq.setEndApplyTime(req.getApplyTimeEnd());
        }
        if(req.getApplyTimeStart() != 0 && req.getBeginApplyTime() == 0){
            handleReq.setBeginApplyTime(req.getApplyTimeStart());
        }
        PagerResponse<ProjectApprovalFlowResp> resp =  projectApprovalFlowService.findPage(handleReq);
        //转换对象
        List<ProjectApprovalFlowResp> list = resp.getList();
        List<MainProjectApprovalFlowResp> projectApproval = Lists.newArrayList();
        if(list != null && list.size() > 0){
            for(ProjectApprovalFlowResp approvalFlow : list){
                projectApproval.add(change(approvalFlow,MainProjectApprovalFlowResp.class));
            }
        }
		return new BaseResp<>(IntfzRs.SUCCESS, "ok",
				new PageResponse<>(projectApproval, req.getPageNo(), resp.getCount()));
    }

    /**
     * 获取立项流程详情接口
     * url:flowdetail
     * request procInsId:流程实例ID(必填), procDefId:流程定义ID(必填), taskId:任务ID(必填)
     */
    @RequestMapping(value = "flowdetail")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "APP端立项-查询立项流程详情")
    @AppAuthority
    public BaseResp<MainProjectApprovalFlowDetailResponse> flowDetail(
			@RequestBody MainProjectApprovalFlowHandleReq req,
			@ApiParam(value = "clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
			@ApiParam(value = "客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
			HttpServletRequest request, HttpServletResponse response)  {
        WebUtils.initPre(request, response);
        User curUser = this.getCurrWxUser(sjboacert, clientType);
        // 获取流程实例对象
        if (StringUtils.isBlank(req.getProjectApprovalFlowId())) {
            return new BaseResp<>(IntfzRs.ERROR, "参数不能为空", new MainProjectApprovalFlowDetailResponse());
        }
        ProjectApprovalFlowHandleReq handleReq = change(req, ProjectApprovalFlowHandleReq.class);
        handleReq.setUserId(curUser.getId());
        ProjectApprovalFlowDetailResponse detail = projectApprovalFlowService.flowDetail(handleReq);
        MainProjectApprovalFlowDetailResponse resp = change(detail,MainProjectApprovalFlowDetailResponse.class);
        ProjectApprovalFlowResp approval = detail.getProjectApprovalFlowResp();
        MainProjectApprovalFlowResp projectApproval = change(approval,MainProjectApprovalFlowResp.class);
        List<MainProjectApprovalLinkmanResp> linkmanList = Lists.newArrayList();
        if(approval.getProjectApprovalLinkman() != null && approval.getProjectApprovalLinkman().size() > 0){
            for(ProjectApprovalLinkmanResp linkman : approval.getProjectApprovalLinkman()){
                linkmanList.add(change(linkman,MainProjectApprovalLinkmanResp.class));
            }
        }
        projectApproval.setProjectApprovalLinkman(linkmanList);
        resp.setProjectApprovalFlowResp(projectApproval);
		return new BaseResp<>(IntfzRs.SUCCESS, "ok", resp);
    }

}
