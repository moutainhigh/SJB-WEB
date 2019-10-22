package sijibao.oa.jeesite.modules.intfz.web.intfzweb;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.sijibao.base.common.api.response.PagerResponse;
import com.sijibao.oa.common.utils.DateUtils;
import com.sijibao.oa.common.utils.WebUtils;
import com.sijibao.oa.common.web.BaseController;
import com.sijibao.oa.crm.api.IntfzImplyDailyService;
import com.sijibao.oa.crm.api.IntfzWebProjectInfoService;
import com.sijibao.oa.crm.api.exception.ServiceException;
import com.sijibao.oa.crm.api.request.daily.DailyHandleReq;
import com.sijibao.oa.crm.api.request.daily.QueryImplyDailyDetailParams;
import com.sijibao.oa.crm.api.request.daily.SaveImplyDailyParams;
import com.sijibao.oa.crm.api.request.daily.SaveProjectImplyStatusParams;
import com.sijibao.oa.crm.api.response.QueryImplyDailyDetailResult;
import com.sijibao.oa.crm.api.response.daily.ImplyDailyExportResponse;
import com.sijibao.oa.crm.api.response.project.ProjectNodeDetailResponse;
import com.sijibao.oa.modules.intfz.bean.BaseResp;
import com.sijibao.oa.modules.intfz.enums.IntfzRs;
import com.sijibao.oa.modules.intfz.request.common.HandleReq;
import com.sijibao.oa.modules.intfz.request.daily.MainDailyHandleReq;
import com.sijibao.oa.modules.intfz.request.daily.ProjectImplyStatusForAdd;
import com.sijibao.oa.modules.intfz.request.daily.QueryImplyDailyDetailReq;
import com.sijibao.oa.modules.intfz.request.daily.SaveImplyDailyReq;
import com.sijibao.oa.modules.intfz.response.daily.ImplyDailyDetailResp;
import com.sijibao.oa.modules.intfz.response.daily.ProjectImplyStatusForDetail;
import com.sijibao.oa.modules.intfz.response.daily.QueryProjectNodeListResp;
import com.sijibao.oa.modules.intfz.service.common.IntfzCommonService;
import com.sijibao.oa.modules.intfz.service.daily.IntfzWebImplyDailyReportService;
import com.sijibao.oa.modules.intfz.utils.CharChangeUtils;
import com.sijibao.oa.modules.sys.entity.User;
import com.sijibao.oa.modules.sys.utils.UserUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * APP工作日志controller
 *
 * @author wanxb
 */
@Controller
@RequestMapping(value = "${frontPath}/implyDaily")
@Api(value = "WEB实施日报服务", tags = "WEB实施日报服务")
public class IntfzWebImplyDailyController extends BaseController {
    @Autowired
    private IntfzImplyDailyService intfzImplyDailyService;
    @Autowired
    private IntfzCommonService intfzCommonService;
    @Autowired
    private IntfzWebProjectInfoService intfzWebProjectInfoService;
    @Autowired
    private IntfzWebImplyDailyReportService intfzWebImplyDailyReportService;

    /**
     * 实施日报-保存日报信息
     */
    @RequestMapping(value = "saveImplyDaily")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "WEB实施日报-保存日报信息")
    public BaseResp<String> saveImplyDaily(
			@RequestBody SaveImplyDailyReq req,
			@ApiParam(value = "登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) {
        WebUtils.initPre(request, response);
        User user = UserUtils.getUser(sessionid);
        CharChangeUtils.CharChange(req);//替换英文字符
        req.setProducSide("web");
        SaveImplyDailyParams change = change(req, SaveImplyDailyParams.class);
        change.setCopyToUserList(req.getCopyToList());
        ArrayList<SaveProjectImplyStatusParams> list = Lists.newArrayList();
        for (ProjectImplyStatusForAdd s : req.getProjectImplementStatusList()) {
            SaveProjectImplyStatusParams ss = change(s, SaveProjectImplyStatusParams.class);
            list.add(ss);
        }
        ArrayList<String> arr = Lists.newArrayList();
        arr.addAll(req.getSendToUserList());

        change.setOperateUserId(user.getId());
        change.setProjectImplementStatusList(list);
        change.setSendToList(arr);
        try {
            intfzImplyDailyService.saveImplyDaily(change);
        } catch (ServiceException e) {
            return new BaseResp<>(IntfzRs.ERROR, e.getMessage(), "");
        }

        return new BaseResp<>(IntfzRs.SUCCESS, "提交成功", "");
    }

    /**
     * 实施日报-详情页面
     */
    @RequestMapping(value = "queryImplyDailyDetail")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "WEB实施日报-详情页面")
    BaseResp<ImplyDailyDetailResp> queryImplyDailyDetail(
			@RequestBody QueryImplyDailyDetailReq req,
			@ApiParam(value = "登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) {
        WebUtils.initPre(request, response);
        if (StringUtils.isBlank(req.getDailyId())) {
            return new BaseResp<>(IntfzRs.ERROR, "id不能为空", null);
        }
        User user = UserUtils.getUser(sessionid);
        if (user == null) {
            return new BaseResp<>(IntfzRs.ERROR, "未找到用户信息", null);
        }

        QueryImplyDailyDetailParams params = change(req, QueryImplyDailyDetailParams.class);
        params.setOperateUserId(user.getId());
        QueryImplyDailyDetailResult result = intfzImplyDailyService.queryImplyDailyDetail(params);

        ImplyDailyDetailResp resp = change(result, ImplyDailyDetailResp.class);
        resp.setCreateTime(result.getCreateTime().getTime());//属性类型不同，无法复制

        PagerResponse<ProjectImplyStatusForDetail> page = new PagerResponse<>();
        List<ProjectImplyStatusForDetail> list = new LinkedList<>();
        for (com.sijibao.oa.crm.api.response.ProjectImplyStatusForDetail detail : result.getProjectImplementStatusPage().getList()) {
            list.add(change(detail, ProjectImplyStatusForDetail.class));
        }
        page.setList(list);
        page.setCount(result.getProjectImplementStatusPage().getCount());

        resp.setProjectImplementStatusPage(page);
        resp.setSendToUserList(result.getSendToUserList());
        resp.setCopyToList(result.getCopyToUserList());
        return new BaseResp<>(IntfzRs.SUCCESS, "ok", resp);
    }

    /**
     * 项目节点列表-不带分页查询
     */
    @RequestMapping(value = "queryProjectNodeList")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "项目节点列表-不带分页查询")
    BaseResp<List<QueryProjectNodeListResp>> queryProjectNodeList(
			@RequestBody HandleReq req,
			@ApiParam(value = "登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) {
        WebUtils.initPre(request, response);
        if (StringUtils.isBlank(req.getId())) {
            return new BaseResp<>(IntfzRs.ERROR, "id不能为空", null);
        }

        List<QueryProjectNodeListResp> list = new ArrayList<>();
        List<ProjectNodeDetailResponse> projectNodeDetailResponseList = intfzWebProjectInfoService.findProjectNode(req.getId());
        for (ProjectNodeDetailResponse it : projectNodeDetailResponseList) {
            QueryProjectNodeListResp nodeResp = new QueryProjectNodeListResp();
            nodeResp.setNodeId(it.getNodeId());
            nodeResp.setNodeName(it.getNodeName());
            if (it.getNodeAddress() == null) {//如果节点具体地址为null，则返回给前端空字符串
                nodeResp.setNodeAddress("");
            } else {
                nodeResp.setNodeAddress(it.getNodeAddress());
            }

            list.add(nodeResp);
        }
        return new BaseResp<>(IntfzRs.SUCCESS, "ok", list);
    }

    /**
     * 实施日报-导出
     */
    @RequestMapping(value = "implyDailyExport")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "WEB实施日报-实施员工日报导出")
    public BaseResp<String> implyDailyExport(
			@RequestBody MainDailyHandleReq req,
			@ApiParam(value = "登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) {

        DailyHandleReq change = change(req, DailyHandleReq.class);
        change.setUserId(UserUtils.getUser(sessionid).getId());
        List<ImplyDailyExportResponse> li = intfzImplyDailyService.implyDailyExportList(change);

        ArrayList<ImplyDailyExportResponse> list = Lists.newArrayList();
        for (ImplyDailyExportResponse s : li) {
            ImplyDailyExportResponse ss = change(s, ImplyDailyExportResponse.class);
            list.add(ss);
        }

        String fileName = "实施工作日志" + DateUtils.formatDateFromUnix(new Date().getTime(), DateUtils.YYYYMMDD) + ".xls";
        String url = intfzWebImplyDailyReportService.exportImplyDaily(list, fileName, "实施员工日报"); //获取附件url
        String downLoadUrl = intfzCommonService.setFileDownLoadUrl(url, fileName);

        return new BaseResp<>(IntfzRs.SUCCESS, "", downLoadUrl);
    }

}
