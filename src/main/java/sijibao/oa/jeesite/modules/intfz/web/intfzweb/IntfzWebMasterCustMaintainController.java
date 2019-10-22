package sijibao.oa.jeesite.modules.intfz.web.intfzweb;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.sijibao.base.common.api.response.PagerResponse;
import com.sijibao.oa.common.web.BaseController;
import com.sijibao.oa.crm.api.MasterCustMaintainService;
import com.sijibao.oa.crm.api.request.custinfo.MasterCustMaintainHandleParam;
import com.sijibao.oa.crm.api.request.custinfo.MasterCustMaintainSaveParam;
import com.sijibao.oa.crm.api.response.custinfo.MasterCustMaintainResult;
import com.sijibao.oa.crm.api.response.custinfo.MasterOrSlaveCustResult;
import com.sijibao.oa.crm.api.response.custinfo.PassiveMaintainResult;
import com.sijibao.oa.modules.intfz.bean.BaseResp;
import com.sijibao.oa.modules.intfz.enums.IntfzRs;
import com.sijibao.oa.modules.intfz.request.custinfo.MasterCustMaintainHandleReq;
import com.sijibao.oa.modules.intfz.request.custinfo.MasterCustMaintainSaveReq;
import com.sijibao.oa.modules.intfz.response.common.PageResponse;
import com.sijibao.oa.modules.intfz.response.custinfo.MasterCustMaintainResponse;
import com.sijibao.oa.modules.intfz.response.custinfo.MasterOrSlaveCustResponse;
import com.sijibao.oa.modules.intfz.response.custinfo.PassiveMaintainResponse;
import com.sijibao.oa.modules.sys.entity.User;
import com.sijibao.oa.modules.sys.utils.UserUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @description: 主客户维护管理
 * @author: xgx
 * @create: 2019-09-26 17:10
 **/
@Controller
@RequestMapping(value = "${frontPath}/masterCust")
@Api(value="WEB主客户维护管理",tags="WEB主客户维护管理")
public class IntfzWebMasterCustMaintainController extends BaseController {
    @Autowired
    private MasterCustMaintainService masterCustMaintainService;

    /**
     * 归属客户-查询
     * @param masterCustId
     * @return
     */
    @RequestMapping(value = "getBelongCust")
    @ResponseBody
    @ApiOperation(httpMethod = "GET", value = "归属客户-查询")
    public BaseResp<List<MasterOrSlaveCustResponse>> getBelongCust(@ApiParam(value="主客户的id") @RequestParam(value = "masterCustId") String masterCustId){
        List<MasterOrSlaveCustResult> belongCust = masterCustMaintainService.getBelongCust(masterCustId);
        List<MasterOrSlaveCustResponse> responses=new ArrayList<>();
        for(MasterOrSlaveCustResult result:belongCust){
            MasterOrSlaveCustResponse change = change(result, MasterOrSlaveCustResponse.class);
            responses.add(change);
        }
        return new BaseResp<List<MasterOrSlaveCustResponse>>(IntfzRs.SUCCESS, "ok", responses);
    }


    /**
     * 主客户维护管理-保存列表
     * @param req
     * @param sessionid
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "saveMasterCustMaintain")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "主客户维护管理-保存列表")
    public BaseResp<String> saveMasterCustMaintain(
            @RequestBody MasterCustMaintainSaveReq req,
            @ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid) throws Exception{
        User user = UserUtils.getUser(sessionid);
        req.setProducSide("web");
        MasterCustMaintainSaveParam masterCustMaintainSaveParam=change(req,MasterCustMaintainSaveParam.class);
        if(req.getIssuesClassification()!=null){
            StringBuilder sb=new StringBuilder();
            for(String s:req.getIssuesClassification()){
                sb.append(s).append(",");
            }
            masterCustMaintainSaveParam.setIssuesClassification(sb.substring(0,sb.length()-1));
        }
        masterCustMaintainService.saveMasterCustMaintain(masterCustMaintainSaveParam,user.getId());
        return new BaseResp<String>(IntfzRs.SUCCESS,"保存成功!",null);
    }


    /**
     * 主客户维护管理--主动维护列表
     * @param req
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "masterCustMaintainList")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "主客户维护管理--主动维护列表")
    public BaseResp<PageResponse<List<MasterCustMaintainResponse>>> masterCustMaintainList(
            @RequestBody MasterCustMaintainHandleReq req
            ) throws Exception{

        MasterCustMaintainHandleParam param = change(req,MasterCustMaintainHandleParam.class);
        PagerResponse<MasterCustMaintainResult> masterCustMaintainPage = masterCustMaintainService.findMasterCustMaintainPage(param);
        List<MasterCustMaintainResponse> list = new LinkedList<>();
        if(masterCustMaintainPage.getList()!=null){
            for(MasterCustMaintainResult item : masterCustMaintainPage.getList()){
                list.add(change(item,MasterCustMaintainResponse.class));
            }
        }
        return new BaseResp<PageResponse<List<MasterCustMaintainResponse>>>(IntfzRs.SUCCESS, "ok",
                new PageResponse<List<MasterCustMaintainResponse>>(list, req.getPageNo(), masterCustMaintainPage.getCount()));
    }


    /**
     * 主客户维护管理--被动维护列表
     * @param req
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "passiveMaintainList")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "主客户维护管理--被动维护列表")
    public BaseResp<PageResponse<List<PassiveMaintainResponse>>> passiveMaintainList(
            @RequestBody MasterCustMaintainHandleReq req
    ) throws Exception{

        MasterCustMaintainHandleParam param = change(req,MasterCustMaintainHandleParam.class);
        PagerResponse<PassiveMaintainResult> passiveMaintainPage = masterCustMaintainService.findPassiveMaintainPage(param);
        List<PassiveMaintainResponse> list=new LinkedList<>();
        if(passiveMaintainPage.getList()!=null){
            for(PassiveMaintainResult item:passiveMaintainPage.getList()){
                list.add(change(item,PassiveMaintainResponse.class));
            }
        }
        return new BaseResp<PageResponse<List<PassiveMaintainResponse>>>(IntfzRs.SUCCESS, "ok",
                new PageResponse<List<PassiveMaintainResponse>>(list, req.getPageNo(), passiveMaintainPage.getCount()));
    }


}
