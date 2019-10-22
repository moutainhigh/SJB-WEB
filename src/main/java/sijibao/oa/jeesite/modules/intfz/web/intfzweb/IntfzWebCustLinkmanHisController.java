package sijibao.oa.jeesite.modules.intfz.web.intfzweb;

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
import com.sijibao.oa.crm.api.CustInfoMicroService;
import com.sijibao.oa.crm.api.request.custinfo.*;
import com.sijibao.oa.crm.api.response.custinfo.CustLinkmanHisResult;
import com.sijibao.oa.modules.intfz.bean.BaseResp;
import com.sijibao.oa.modules.intfz.enums.IntfzRs;
import com.sijibao.oa.modules.intfz.request.custinfo.*;
import com.sijibao.oa.modules.intfz.response.common.PageResponse;
import com.sijibao.oa.modules.intfz.response.custinfo.CustLinkmanHisResponse;
import com.sijibao.oa.modules.oa.entity.CustLinkman;
import com.sijibao.oa.modules.oa.entity.CustLinkmanHis;
import com.sijibao.oa.modules.oa.service.CustInfoService;
import com.sijibao.oa.modules.sys.entity.User;
import com.sijibao.oa.modules.sys.utils.UserUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 联系人变更记录controller
 *
 * @author zhanglianhui
 * @Date 2019/9/26 11:22
 */
@Controller
@RequestMapping(value = "${frontPath}/custLinkmanHisInfo")
@Api(value = "WEB联系人变更记录", tags = "WEB联系人变更记录")
public class IntfzWebCustLinkmanHisController extends BaseController {

    @Autowired
    private CustInfoService custInfoService;

    @Autowired
    private CustInfoMicroService custInfoMicroService;

    /**
     * 查询客户联系人
     * 
     * @param req
     * @param sessionid
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "custLinkmanList")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "查询客户联系人")
    public BaseResp<List<CustLinkman>> custLinkmanList(@RequestBody CustLinkmanHisHandleReq req,
													   @ApiParam(value = "登录成功时返回的sessionid") @RequestHeader String sessionid, HttpServletRequest request,
													   HttpServletResponse response) throws Exception {
        WebUtils.initPre(request, response);
        List<CustLinkman> custLinkman = new ArrayList<>();
        custLinkman = custInfoService.findCustLinkmanListByCustId(req.getMainCustId());
        return new BaseResp<>(IntfzRs.SUCCESS, "ok", custLinkman);
    }

    /**
     * 保存联系人
     * 
     * @param sessionid
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "saveCustLinkman")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "客户联系人保存")
    public BaseResp<String> saveCustInfo(@ApiParam(value = "登录成功时返回的sessionid") @RequestHeader String sessionid,
										 @RequestBody CustLinkmanHisSaveReq req, HttpServletRequest request, HttpServletResponse response) {
        WebUtils.initPre(request, response);
        User user = UserUtils.getUser(sessionid);
        CustLinkmanHisSaveParam custLinkmanHisSaveParam = change(req, CustLinkmanHisSaveParam.class);
        List<CustLinkmanHisParam> linkmanHisParams = new ArrayList<>();
        for (CustLinkmanHis his : req.getCustLinkmanHisList()) {
            linkmanHisParams.add(change(his, CustLinkmanHisParam.class));
        }
        custLinkmanHisSaveParam.setCustLinkmanHisList(linkmanHisParams);
        custInfoMicroService.saveCustLinkmanHis(custLinkmanHisSaveParam, user.getId());
        return new BaseResp<>(IntfzRs.SUCCESS, "保存客户联系人成功!", null);
    }

    /**
     * 查询客户联系人变更历史
     * 
     * @param req
     * @param sessionid
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "custLinkmanHisList")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "查询客户联系人变更历史")
    public BaseResp<PageResponse<List<CustLinkmanHisResponse>>> custLinkmanHisList(
			@RequestBody CustLinkmanHisHandleReq req,
			@ApiParam(value = "登录成功时返回的sessionid") @RequestHeader String sessionid, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
        WebUtils.initPre(request, response);
        CustLinkmanHisHandleParam param = change(req, CustLinkmanHisHandleParam.class);
        PagerResponse<CustLinkmanHisResult> page = custInfoMicroService.findCustLinkmanHisList(param);
        List<CustLinkmanHisResponse> list = new LinkedList<>();
        if (page.getList() != null) {
            for (CustLinkmanHisResult item : page.getList()) {
                CustLinkmanHisResponse result = change(item, CustLinkmanHisResponse.class);
                if (CustLinkmanHis.CUST_LINKMAN_HIS_TYPE_INSERT.equals(result.getOperateType())) {
                    result.setNowLinkmanRemark("联系人" + StringUtils.defaultString(result.getLinkmanName(), "") + "；联系电话"
                            + StringUtils.defaultString(result.getLinkmanPhone(), "") + "；邮箱"
                            + StringUtils.defaultString(result.getLinkmanMail(), "") + "；职位"
                            + StringUtils.defaultString(result.getLinkmanPost(), ""));
                } else if (CustLinkmanHis.CUST_LINKMAN_HIS_TYPE_UPDATE.equals(result.getOperateType())) {
                    result.setPreLinkmanRemark("联系人" + StringUtils.defaultString(result.getPreLinkmanName(), "")
                            + "；联系电话" + StringUtils.defaultString(result.getPreLinkmanPhone(), "") + "；邮箱"
                            + StringUtils.defaultString(result.getPreLinkmanMail(), "") + "；职位"
                            + StringUtils.defaultString(result.getPreLinkmanPost(), ""));
                    result.setNowLinkmanRemark("联系人" + StringUtils.defaultString(result.getLinkmanName(), "") + "；联系电话"
                            + StringUtils.defaultString(result.getLinkmanPhone(), "") + "；邮箱"
                            + StringUtils.defaultString(result.getLinkmanMail(), "") + "；职位"
                            + StringUtils.defaultString(result.getLinkmanPost(), ""));
                } else {
                    result.setPreLinkmanRemark("联系人" + StringUtils.defaultString(result.getLinkmanName(), "") + "；联系电话"
                            + StringUtils.defaultString(result.getLinkmanPhone(), "") + "；邮箱"
                            + StringUtils.defaultString(result.getLinkmanMail(), "") + "；职位"
                            + StringUtils.defaultString(result.getLinkmanPost(), ""));
                }
                list.add(result);
            }
        }
        return new BaseResp<>(IntfzRs.SUCCESS, "ok", new PageResponse<>(list, req.getPageNo(), page.getCount()));
    }

}
