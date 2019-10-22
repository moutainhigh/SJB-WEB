package sijibao.oa.jeesite.modules.intfz.web.intfzapp;

import java.util.ArrayList;
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

import com.sijibao.base.common.api.response.PagerResponse;
import com.sijibao.oa.common.utils.WebUtils;
import com.sijibao.oa.common.web.BaseController;
import com.sijibao.oa.crm.api.IntfzImplyDailyService;
import com.sijibao.oa.crm.api.IntfzWebProjectInfoService;
import com.sijibao.oa.crm.api.exception.ServiceException;
import com.sijibao.oa.crm.api.request.daily.QueryImplyDailyDetailParams;
import com.sijibao.oa.crm.api.request.daily.SaveImplyDailyParams;
import com.sijibao.oa.crm.api.request.daily.SaveProjectImplyStatusParams;
import com.sijibao.oa.crm.api.response.QueryImplyDailyDetailResult;
import com.sijibao.oa.crm.api.response.project.ProjectNodeDetailResponse;
import com.sijibao.oa.modules.intfz.bean.BaseResp;
import com.sijibao.oa.modules.intfz.enums.IntfzRs;
import com.sijibao.oa.modules.intfz.request.common.HandleReq;
import com.sijibao.oa.modules.intfz.request.daily.ProjectImplyStatusForAdd;
import com.sijibao.oa.modules.intfz.request.daily.QueryImplyDailyDetailReq;
import com.sijibao.oa.modules.intfz.request.daily.SaveImplyDailyReq;
import com.sijibao.oa.modules.intfz.response.daily.ImplyDailyDetailResp;
import com.sijibao.oa.modules.intfz.response.daily.ProjectImplyStatusForDetail;
import com.sijibao.oa.modules.intfz.response.daily.QueryProjectNodeListResp;
import com.sijibao.oa.modules.intfz.utils.CharChangeUtils;
import com.sijibao.oa.modules.intfz.validator.AppAuthority;
import com.sijibao.oa.modules.sys.entity.User;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author Jianghao Zhang
 */
@Controller
@RequestMapping(value = "wechat/implyDaily")
@Api(value = "APP实施工作日志服务", tags = "APP实施工作日志服务")
public class IntfzImplyDailyController extends BaseController {

    @Autowired
    private IntfzImplyDailyService intfzImplyDailyService;
    @Autowired
    private IntfzWebProjectInfoService intfzWebProjectInfoService;

    /**
     * 保存实施工作日志
     */
    @RequestMapping(value = "saveImplyDaily")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "APP工作日志-保存实施工作日志")
    @AppAuthority
    public BaseResp<String> saveImplyDaily(
			@ApiParam(value = "clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
			@ApiParam(value = "客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
			@RequestBody SaveImplyDailyReq req,
			HttpServletRequest request, HttpServletResponse response) {
        WebUtils.initPre(request, response);
        CharChangeUtils.CharChange(req);//替换英文字符
        req.setProducSide("APP");
        for(ProjectImplyStatusForAdd add :req.getProjectImplementStatusList()){
            CharChangeUtils.CharChange(add);//替换英文字符
        }
        User user = super.getCurrWxUser(sjboacert, clientType); //获取当前操作用户信息

        try {
            SaveImplyDailyParams params = change(req, SaveImplyDailyParams.class);

            List<SaveProjectImplyStatusParams> statusList = new LinkedList<>();
            for (ProjectImplyStatusForAdd forAdd : req.getProjectImplementStatusList()) {
                SaveProjectImplyStatusParams ss = change(forAdd, SaveProjectImplyStatusParams.class);
                statusList.add(ss);
            }
            params.setProjectImplementStatusList(statusList);
            params.setSendToList(req.getSendToUserList());
            params.setCopyToUserList(req.getCopyToList());
            params.setOperateUserId(user.getId());
            intfzImplyDailyService.saveImplyDaily(params);
        } catch (ServiceException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return new BaseResp<>(IntfzRs.ERROR, e.getMessage(), null);
        }
        return new BaseResp<>(IntfzRs.SUCCESS, "保存实施工作日志成功!", null);
    }

    /**
     * 实施工作日志详情
     */
    @RequestMapping(value = "queryImplyDailyDetail")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "APP工作日志-实施工作日志详情")
    @AppAuthority
    BaseResp<ImplyDailyDetailResp> queryImplyDailyDetail(
			@RequestBody QueryImplyDailyDetailReq req,
			@ApiParam(value = "clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
			@ApiParam(value = "客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
        WebUtils.initPre(request, response);
        User user = super.getCurrWxUser(sjboacert, clientType); //获取当前操作用户信息
        if (StringUtils.isBlank(req.getDailyId()) && !"0".equals(req.getDailyId())) {
            return new BaseResp<>(IntfzRs.ERROR, "id不能为空", null);
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
    @ApiOperation(httpMethod = "POST", value = "APP工作日志-项目节点列表-不带分页查询")
    @AppAuthority
    BaseResp<List<QueryProjectNodeListResp>> queryProjectNodeList(
			@RequestBody HandleReq req,
			@ApiParam(value = "clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
			@ApiParam(value = "客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
        WebUtils.initPre(request, response);

        List<QueryProjectNodeListResp> list = new ArrayList<>();
        List<ProjectNodeDetailResponse> projectNodeDetailResponseList = intfzWebProjectInfoService.findProjectNode(req.getId());
        for (ProjectNodeDetailResponse it : projectNodeDetailResponseList) {
            QueryProjectNodeListResp nodeResp = new QueryProjectNodeListResp();
            nodeResp.setNodeId(it.getNodeId());
            nodeResp.setNodeName(it.getNodeName());
            if(it.getNodeAddress() == null){//如果节点具体地址为null，则返回给前端空字符串
                nodeResp.setNodeAddress("");
            }else {
                nodeResp.setNodeAddress(it.getNodeAddress());
            }

            list.add(nodeResp);
        }

        return new BaseResp<>(IntfzRs.SUCCESS, "ok", list);
    }

}
