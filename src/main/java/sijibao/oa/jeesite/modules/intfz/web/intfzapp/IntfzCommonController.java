package sijibao.oa.jeesite.modules.intfz.web.intfzapp;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.sijibao.base.common.api.response.PagerResponse;
import com.sijibao.oa.common.persistence.Page;
import com.sijibao.oa.common.utils.StringUtils;
import com.sijibao.oa.common.utils.UploadUtils;
import com.sijibao.oa.common.utils.WebUtils;
import com.sijibao.oa.common.web.BaseController;
import com.sijibao.oa.hamal.api.IntfzWebCompanyService;
import com.sijibao.oa.hamal.api.response.HamalCompanyResponse;
import com.sijibao.oa.modules.intfz.bean.BaseResp;
import com.sijibao.oa.modules.intfz.bean.DictInfo;
import com.sijibao.oa.modules.intfz.enums.IntfzRs;
import com.sijibao.oa.modules.intfz.request.company.QueryCompanyHandleReq;
import com.sijibao.oa.modules.intfz.request.custinfo.CustInfoHandleReq;
import com.sijibao.oa.modules.intfz.request.expense.AttachmentRenameReq;
import com.sijibao.oa.modules.intfz.request.intfzwebreq.OrgAndUserInfoRequest;
import com.sijibao.oa.modules.intfz.request.intfzwebreq.QueryFlowRevenceRequest;
import com.sijibao.oa.modules.intfz.request.intfzwebreq.UserInfoRequest;
import com.sijibao.oa.modules.intfz.request.message.MainMessageHandleReq;
import com.sijibao.oa.modules.intfz.response.common.*;
import com.sijibao.oa.modules.intfz.response.custinfo.CustInfoPageResponse;
import com.sijibao.oa.modules.intfz.response.custinfo.MyCustInfoResponse;
import com.sijibao.oa.modules.intfz.response.expense.SubInfoResponse;
import com.sijibao.oa.modules.intfz.response.expense.SubInfoResponseTree;
import com.sijibao.oa.modules.intfz.response.intfzwebresp.QueryFlowRevenceResponse;
import com.sijibao.oa.modules.intfz.response.intfzwebresp.common.QueryDictResponse;
import com.sijibao.oa.modules.intfz.response.intfzwebresp.company.MainCompanyResponse;
import com.sijibao.oa.modules.intfz.response.message.MainMessageResp;
import com.sijibao.oa.modules.intfz.service.common.IntfzCommonService;
import com.sijibao.oa.modules.intfz.service.expense.IntfzExpenseFlowService;
import com.sijibao.oa.modules.intfz.service.intfzweb.IntfzWebCustInfoService;
import com.sijibao.oa.modules.intfz.service.recp.IntfzRecpFlowService;
import com.sijibao.oa.modules.intfz.service.travel.IntfzTravelFlowService;
import com.sijibao.oa.modules.intfz.utils.Constant;
import com.sijibao.oa.modules.intfz.utils.IntfzWebConstant;
import com.sijibao.oa.modules.intfz.validator.AppAuthority;
import com.sijibao.oa.modules.oa.entity.ExpensesSubInfo;
import com.sijibao.oa.modules.oa.entity.ProjectInfo;
import com.sijibao.oa.modules.oa.service.ProjectInfoService;
import com.sijibao.oa.modules.sys.entity.Area;
import com.sijibao.oa.modules.sys.entity.Dict;
import com.sijibao.oa.modules.sys.entity.Office;
import com.sijibao.oa.modules.sys.entity.User;
import com.sijibao.oa.modules.sys.service.AreaService;
import com.sijibao.oa.modules.sys.service.OfficeService;
import com.sijibao.oa.modules.sys.service.SystemService;
import com.sijibao.oa.modules.sys.utils.DictUtils;
import com.sijibao.oa.modules.sys.utils.UserUtils;
import com.sijibao.oa.office.api.IntfzCommonMessageService;
import com.sijibao.oa.office.api.request.common.MessageHandleReq;
import com.sijibao.oa.office.api.response.common.MessageResp;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * APP客户资料管理
 *
 * @author xuby
 */
@Controller
@RequestMapping(value = "wechat/commonInfo")
@Api(value = "APP公共信息管理服务", tags = "APP公共信息管理服务")
public class IntfzCommonController extends BaseController {

    @Autowired
    private OfficeService officeService;
    @Autowired
    private SystemService systemService;
    @Autowired
    private AreaService areaInfoService;
    @Autowired
    private IntfzExpenseFlowService wxExpenseFlowService;
    @Autowired
    private ProjectInfoService projectInfoService;
    @Autowired
    private IntfzRecpFlowService intfzRecpFlowService;
    @Autowired
    private IntfzTravelFlowService intfzTravelFlowService;
    @Autowired
    private IntfzCommonService intfzCommonService;
    @Autowired
    private IntfzWebCustInfoService intfzWebCustInfoService;
    @Autowired
    private IntfzWebCompanyService intfzWebCompanyService;
    @Autowired
    private IntfzCommonMessageService intfzCommonMessageService;

    /**
     * APP公共信息管理服务-查询客户信息（不带分页）
     */
    @RequestMapping(value = "queryCustInfos")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "APP公共信息管理服务-查询客户信息（不带分页）")
    @AppAuthority
    public BaseResp<List<CustInfoPageResponse>> queryCustInfos(
			@RequestBody CustInfoHandleReq req,
			@ApiParam(value = "clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
			@ApiParam(value = "客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
			HttpServletRequest request, HttpServletResponse response) {
        WebUtils.initPre(request, response);

        User user = super.getCurrWxUser(sjboacert, clientType); //获取当前操作用户信息
        List<CustInfoPageResponse> resultList = intfzWebCustInfoService.appFindCustInfos(req, user);

        return new BaseResp<>(IntfzRs.SUCCESS, "ok", resultList);
    }


    /**
     * APP公共信息管理服务-查询我的ABC客户信息（不带分页）
     */
    @RequestMapping(value = "queryMyCustInfos")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "APP公共信息管理服务-查询客户信息（不带分页）")
    @AppAuthority
    public BaseResp<List<MyCustInfoResponse>> queryMyCustInfos(
			@ApiParam(value = "clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
			@ApiParam(value = "客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
			HttpServletRequest request, HttpServletResponse response) {
        WebUtils.initPre(request, response);
        User user = super.getCurrWxUser(sjboacert, clientType); //获取当前操作用户信息
        List<MyCustInfoResponse> resultList = intfzWebCustInfoService.queryMyCustInfos(user);
        return new BaseResp<>(IntfzRs.SUCCESS, "ok", resultList);
    }

    /**
     * APP公共信息管理服务-查询企业名称（不带分页）
     */
    @RequestMapping(value = "queryCompanyName")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "APP公共信息管理服务-查询企业名称（不带分页）")
    @AppAuthority
    public BaseResp<List<MainCompanyResponse>> queryCompanyName(
			@RequestBody QueryCompanyHandleReq req,
			@ApiParam(value = "clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
			@ApiParam(value = "客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
			HttpServletRequest request, HttpServletResponse response) {
        WebUtils.initPre(request, response);

        //这里变通一下，app端需要的是不分页的结果，那我们就设定特定的分页参数，先分页查询，然后返回list
        PagerResponse<HamalCompanyResponse> page = intfzWebCompanyService.queryListByNameAndState(req.getCompanyName(), 1, 30, 4);
        List<MainCompanyResponse> list = com.google.common.collect.Lists.newArrayList();
        for (HamalCompanyResponse hamal : page.getList()) {
            MainCompanyResponse change = change(hamal, MainCompanyResponse.class);
            list.add(change);
        }

        return new BaseResp<>(IntfzRs.SUCCESS, "ok", list);
    }

    /**
     * APP公共信息管理服务-查询机构部门信息（树状结构）（不带分页）
     */
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "APP公共信息管理服务-获取机构人员信息")
    @RequestMapping(value = "orgAndUserInfo", method = RequestMethod.POST)
    @AppAuthority
    public BaseResp<List<OrgAndUserInfoResponse>> orgAndUserInfo(
			@ApiParam(value = "clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
			@ApiParam(value = "客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
			@RequestBody OrgAndUserInfoRequest orgAndUserInfoRequest,
			HttpServletRequest request, HttpServletResponse response, Model model) {
        //校验queryType是否为空
        if (StringUtils.isBlank(orgAndUserInfoRequest.getQueryType())) {
            return new BaseResp<>(IntfzRs.ERROR, "queryType不能为空", null);
        }

        List<Office> list = officeService.findList(true);
        List<OrgAndUserInfoResponse> resultList = new ArrayList<>();
        for (Office e : list) {
            OrgAndUserInfoResponse res = new OrgAndUserInfoResponse();
            res.setId(e.getId());
            res.setpId(e.getParentId());
            res.setName(e.getName());
            res.setType(OrgAndUserInfoResponse.TYPE_ORG);/*设置节点的类型，方便前端判断节点是机构还是人员*/
            if (Constant.OFFICE_USABLE.equals(e.getUseable())) {
                //机构的状态为启用，可选择并且展示
                res.setStatus(OrgAndUserInfoResponse.STATUS_SELECTABLE);
                res.setShow(OrgAndUserInfoResponse.SHOW_YES);
            } else if (Constant.OFFICE_DISABLE.equals(e.getUseable())) {
                //机构的状态为停用，不可选择并且不展示
                res.setStatus(OrgAndUserInfoResponse.STATUS_NOT_SELECTABLE);
                res.setShow(OrgAndUserInfoResponse.SHOW_NO);
            }

            if (IntfzWebConstant.QUERY_ORGTYPE_TWO.equals(orgAndUserInfoRequest.getQueryType())) {
                /*查询部门下面的人员*/
                List<User> userList = systemService.findUserByOfficeId(e.getId());
                List<UserExtend> userExtendList = new LinkedList<>();
                for (User u : userList) {
                    u.setLoginName("");
                    u.setMobile("");

                    UserExtend userExtend = change(u, UserExtend.class);
                    userExtend.setType(OrgAndUserInfoResponse.TYPE_USER);/*设置节点的类型，方便前端判断节点是机构还是人员*/

                    if(Constant.USER_WORKING.equals(u.getUserStatus())){
                        userExtend.setStatus(OrgAndUserInfoResponse.STATUS_SELECTABLE);
                    }else {
                        //非在职人员不可选择
                        userExtend.setStatus(OrgAndUserInfoResponse.STATUS_NOT_SELECTABLE);
                    }

                    if(Constant.USER_QUITED.equals(u.getUserStatus())){
                        //已离职人员不展示
                        userExtend.setShow(OrgAndUserInfoResponse.SHOW_NO);
                    }else {
                        userExtend.setShow(OrgAndUserInfoResponse.SHOW_YES);
                    }

                    userExtendList.add(userExtend);
                }
                res.setUserInfo(userExtendList);
            }
            resultList.add(res);
        }

        return new BaseResp<>(IntfzRs.SUCCESS, "", resultList);
    }

    /**
     * 字典信息查询
     */
    @RequestMapping(value = "queryDict")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "APP公共信息管理服务-字典查询")
    @AppAuthority
    public BaseResp<List<QueryDictResponse>> queryDict(
			@ApiParam(value = "clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
			@ApiParam(value = "客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
			HttpServletRequest request, HttpServletResponse response) {
        WebUtils.initPre(request, response);
        List<QueryDictResponse> resultList = new ArrayList<QueryDictResponse>();
        List<Dict> dictList = DictUtils.getDictList(new ArrayList<>());
        if (dictList != null && dictList.size() > 0) {
            for (Dict dict : dictList) {
                QueryDictResponse queryDictResponse = new QueryDictResponse();
                queryDictResponse.setType(dict.getType().trim());
                queryDictResponse.setName(dict.getLabel().trim());
                queryDictResponse.setValue(dict.getValue().trim());
                resultList.add(queryDictResponse);
            }
        }
        return new BaseResp<List<QueryDictResponse>>(IntfzRs.SUCCESS, "ok", resultList);
    }

    /**
     * 查询区域信息
     */
    @RequestMapping(value = "queryAreaInfo")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "APP公共信息管理服务-区域信息查询")
    @AppAuthority
    public BaseResp<List<AreaInfoResponse>> areaInfoList(
			@ApiParam(value = "clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
			@ApiParam(value = "客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
			HttpServletRequest request, HttpServletResponse response) {
        Area area = new Area();
        List<Area> areaInfoList = areaInfoService.findList(area);
        List<AreaInfoResponse> resultList = areaInfoService.setIntfzAreaInfo(areaInfoList);
        return new BaseResp<List<AreaInfoResponse>>(IntfzRs.SUCCESS, "ok", resultList);
    }

    /**
     * 查询费用科目
     * url:subinfo
     * request subCode:科目编号(非必填), parSubCode:父级科目编号(非必填), subName:科目名称(非必填)
     * response data:{"list":["subCode":"科目编号", "parSubCode":"父级科目编号", "subName":"科目名称","expenseNormal":"费用标准","unitType":"单位类型"]}
     *
     * @throws Exception
     */
    @RequestMapping(value = "querySubInfo")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "APP公共信息管理服务-费用科目信息查询")
    @AppAuthority
    public BaseResp<PageResponse<List<SubInfoResponse>>> querySubInfolist(
			@ApiParam(value = "clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
			@ApiParam(value = "客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
			@RequestBody ExpensesSubInfo expensesSubInfo,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
        WebUtils.initPre(request, response);
        List<SubInfoResponse> subInfolist = wxExpenseFlowService.getSubInfoList(expensesSubInfo);
        return new BaseResp<PageResponse<List<SubInfoResponse>>>(IntfzRs.SUCCESS, "ok",
                new PageResponse<List<SubInfoResponse>>(subInfolist, 1, subInfolist.size()));
    }

    /**
     * 费用科目信息查询(APP专用,默认追加二级科目)
     * url:subinfo
     * request subCode:科目编号(非必填), parSubCode:父级科目编号(非必填), subName:科目名称(非必填)
     * response data:{"list":["subCode":"科目编号", "parSubCode":"父级科目编号", "subName":"科目名称","expenseNormal":"费用标准","unitType":"单位类型"]}
     *
     * @throws Exception
     */
    @RequestMapping(value = "querySubInfolistForSecond")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "APP公共信息管理服务-费用科目信息查询(APP专用,默认追加二级科目)")
    @AppAuthority
    public BaseResp<PageResponse<List<SubInfoResponse>>> querySubInfolistForSecond(
			@ApiParam(value = "clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
			@ApiParam(value = "客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
			@RequestBody ExpensesSubInfo expensesSubInfo,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
        WebUtils.initPre(request, response);
        List<SubInfoResponse> subInfolist = wxExpenseFlowService.getSubInfoList(expensesSubInfo);
        if (subInfolist != null && subInfolist.size() > 0) {
            intfzCommonService.setSecondSub(subInfolist); //设置默认二级科目
        }
        return new BaseResp<PageResponse<List<SubInfoResponse>>>(IntfzRs.SUCCESS, "ok",
                new PageResponse<List<SubInfoResponse>>(subInfolist, 1, subInfolist.size()));
    }


    /**
     * 查询科目信息，树形结构
     */
    @RequestMapping(value = "querySubInfoNew")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "APP公共信息管理服务-费用科目信息查询(树形结构)")
    @AppAuthority
    public BaseResp<PageResponse<List<SubInfoResponseTree>>> querySubInfoNew(
			@ApiParam(value = "clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
			@ApiParam(value = "客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
			@RequestBody ExpensesSubInfo expensesSubInfo,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
        WebUtils.initPre(request, response);
        List<SubInfoResponseTree> subInfolist = wxExpenseFlowService.getSubInfoListNew(expensesSubInfo);
        return new BaseResp<PageResponse<List<SubInfoResponseTree>>>(IntfzRs.SUCCESS, "ok",
                new PageResponse<List<SubInfoResponseTree>>(subInfolist, 1, subInfolist.size()));
    }

    /**
     * 根据字典类型查询数据字典信息
     * 字典类型tax_city:发票所属城市,oa_project:项目
     */
    @RequestMapping(value = "queryDictInfo")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "APP公共信息管理服务-获取字典信息,tax_city:发票所属城市,oa_project:项目,oa_expense_type:报销类型")
    @AppAuthority
    public BaseResp<List<DictInfo>> queryDictInfo(
			@RequestBody DictInfo dictInfo,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
        WebUtils.initPre(request, response);
        if (dictInfo != null && StringUtils.isBlank(dictInfo.getDictType())) {
            return new BaseResp<List<DictInfo>>(IntfzRs.ERROR, "请输入要查询的数据字典类型", new ArrayList<DictInfo>());
        }
        List<DictInfo> dictList = Lists.newArrayList();
        if ("oa_project".equals(dictInfo.getDictType())) {
            ProjectInfo query = new ProjectInfo();
            List<ProjectInfo> projectList = projectInfoService.findList(query);
            for (ProjectInfo project : projectList) {
                dictList.add(new DictInfo(project.getId(), project.getProjectName(), dictInfo.getDictType(),
                        project.getProjectName(), 0, null, project));
            }
        } else {
            List<Dict> resultList = DictUtils.getDictList(dictInfo.getDictType());
            for (Dict dict : resultList) {
                dictList.add(new DictInfo(dict.getValue(), dict.getLabel(), dict.getType(),
                        dict.getDescription(), dict.getSort(), dict.getParentId()));
            }
        }
        if (dictList == null || dictList.size() == 0) {
            return new BaseResp<List<DictInfo>>(IntfzRs.ERROR, "未搜索到信息！", new ArrayList<DictInfo>());
        }
        return new BaseResp<List<DictInfo>>(IntfzRs.SUCCESS, "ok", dictList);
    }

    /**
     * 附件上传
     */
    @RequestMapping(value = "fileUpload")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "APP公共信息管理服务-附件上传")
    @AppAuthority
    public BaseResp<JSONObject> fileUpload(
			@ApiParam(value = "clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
			@ApiParam(value = "客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
			@RequestBody MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
        JSONObject resJson = new JSONObject();
        try {
            JSONObject storfiles = new JSONObject();

            if (file == null) {
                return new BaseResp<JSONObject>(IntfzRs.ERROR, "请重新选择文件", null);
            }
            String fileName = file.getOriginalFilename(); //文件名称
            String fileType = file.getContentType(); //文件类型

            if (fileType.startsWith("image") || fileType.indexOf("pdf") != -1) {
                storfiles.put("fileType", "0");
            } else {
                storfiles.put("fileType", "1");
            }
            String serverUrl = UploadUtils.getServerUrl(); //附件服务器地址

            String storfile = UploadUtils.upload(file, fileName);
            storfiles.put("url", storfile);
            storfiles.put("fileName", fileName);
            storfiles.put("serverUrl", serverUrl);

            resJson.put("resCode", 1);
            resJson.put("resDesc", "上传成功.");
            resJson.put("storfiles", storfiles);
            if (StringUtils.isNotBlank(request.getParameter("id"))) {
                resJson.put("id", request.getParameter("id"));
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            resJson.put("resCode", 0);
            resJson.put("resDesc", e.getMessage());
        }
        return new BaseResp<JSONObject>(IntfzRs.SUCCESS, "ok", resJson);
    }

    /**
     * 关联主题查询列表
     */
    @RequestMapping(value = "flowRevencelist")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "APP公共信息管理服务-关联查询列表")
    @AppAuthority
    public BaseResp<PageResponse<List<QueryFlowRevenceResponse>>> flowRevencelist(@RequestBody QueryFlowRevenceRequest queryFlowRevenceRequest,
																				  @ApiParam(value = "clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
																				  @ApiParam(value = "客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
																				  HttpServletRequest request, HttpServletResponse response) throws Exception {
        WebUtils.initPre(request, response);
        if (StringUtils.isBlank(queryFlowRevenceRequest.getApplyType())) {
            return new BaseResp<PageResponse<List<QueryFlowRevenceResponse>>>(IntfzRs.ERROR, "报销类型不能为空!", null);
        }
        User user = super.getCurrWxUser(sjboacert, clientType); //获取当前申请人信息
        if (user == null) {
            return new BaseResp<PageResponse<List<QueryFlowRevenceResponse>>>(IntfzRs.ERROR, "未找到当前登录人信息!", null);
        }
        Page<QueryFlowRevenceResponse> page = new Page<QueryFlowRevenceResponse>();
        if (Constant.OA_EXPENSE_TYPE_TWO.equals(queryFlowRevenceRequest.getApplyType())) {
            page = intfzRecpFlowService.queryRecpFlowRevencelist(user, queryFlowRevenceRequest);
        } else if (Constant.OA_EXPENSE_TYPE_THREE.equals(queryFlowRevenceRequest.getApplyType())) {
            page = intfzTravelFlowService.queryTravelFlowRevencelist(user, queryFlowRevenceRequest);
        }
        return new BaseResp<PageResponse<List<QueryFlowRevenceResponse>>>(IntfzRs.SUCCESS, "ok",
                new PageResponse<List<QueryFlowRevenceResponse>>(page.getList(), page.getPageNo(), page.getCount()));
    }

    /**
     * 根据用户ID查询用户信息
     */
    @RequestMapping(value = "queryUserInfoForId")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "APP公共信息管理服务-根据用户ID查询用户信息")
    @AppAuthority
    public BaseResp<User> queryUserInfoForId(@RequestBody UserInfoRequest userInfoRequest,
											 @ApiParam(value = "clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
											 @ApiParam(value = "客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
											 HttpServletRequest request, HttpServletResponse response) {
        if (StringUtils.isBlank(userInfoRequest.getUserId())) {
            return new BaseResp<User>(IntfzRs.ERROR, "用户ID不能为空!", null);
        }
        User user = UserUtils.get(userInfoRequest.getUserId());

        return new BaseResp<User>(IntfzRs.SUCCESS, "ok", user);
    }

    /**
     * 附件下载
     */
    @RequestMapping(value = "downFiles")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "APP公共信息管理服务-下载附件并重新赋值文件名")
    @AppAuthority
    public BaseResp<String> downFiles(
			@ApiParam(value = "clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
			@ApiParam(value = "客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
			@RequestBody AttachmentRenameReq req,
			HttpServletResponse response, HttpServletRequest request) {

        String serverUrl = UploadUtils.getServerUrl(); //附件服务器地址
        String fileUrl = serverUrl + req.getUrl();
        String url = "/f/commonInfo/downloadFiles?url=" + fileUrl + "&fileName=" + req.getFileName();

        return new BaseResp<String>(IntfzRs.SUCCESS, "附件下载成功!", url);
    }

    /**
     * 下载文件并重新赋值文件名
     *
     * @param response HTTP响应
     * @param url      附件地址
     * @param fileName 附件名称
     */
    @RequestMapping(value = "downloadFiles")
    @ResponseBody
    public void downloadFiles(
			String fileName, String url,
			HttpServletResponse response, HttpServletRequest request) {
        HttpURLConnection httpUrl = null;
        InputStream in = null;
        OutputStream out = null;
        try {

            URL urlfile = new URL(url);
            httpUrl = (HttpURLConnection) urlfile.openConnection();

            byte[] buffer = new byte[1024];
            in = new BufferedInputStream(httpUrl.getInputStream());
            response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("utf-8"), "ISO-8859-1"));
            out = response.getOutputStream();

            int len = 0;
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
            out.close();
            in.close();
            httpUrl.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
                in.close();
                httpUrl.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * app公共信息管理服务-消息查询
     * @param req
     * @param sjboacert
     * @param clientType
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "findMessagePage")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "app公共信息管理服务-消息查询")
    @AppAuthority
    public BaseResp<PageResponse<List<MainMessageResp>>> findMessagePage(
			@RequestBody MainMessageHandleReq req,
			@ApiParam(value = "clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
			@ApiParam(value = "客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
			HttpServletRequest request, HttpServletResponse response) {
        WebUtils.initPre(request, response);
       if(StringUtils.isBlank(req.getReadStatus())){
           return new BaseResp<PageResponse<List<MainMessageResp>>>(IntfzRs.ERROR, "读取状态不能为空!", null);
       }
        MessageHandleReq change = change(req, MessageHandleReq.class);
        User user = super.getCurrWxUser(sjboacert, clientType); //获取当前操作用户信息
        change.setRecPersion(user.getId());
        PagerResponse<MessageResp> findPage = intfzCommonMessageService.findMessagePage(change);
        ArrayList<MainMessageResp> list = Lists.newArrayList();
        for (MessageResp s : findPage.getList()) {
            MainMessageResp ss = change(s,MainMessageResp.class );
            if(StringUtils.isNotBlank(s.getMyself())){
                ss.setPathType("myself");
            }
            if(StringUtils.isBlank(s.getPathType())){
                ss.setPathType("done");
            }
            list.add(ss);
        }
        PageResponse<List<MainMessageResp>> resp = new PageResponse<List<MainMessageResp>>(list, req.getPageNo(), findPage.getCount());
        return new BaseResp<PageResponse<List<MainMessageResp>>>(IntfzRs.SUCCESS, "ok",resp);
    }

    @RequestMapping(value = "findRedCount")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "app公共信息管理服务-消息查询红点")
    @AppAuthority
    public BaseResp<RedCountResp> findRedCount(
			@RequestBody MainMessageHandleReq req,
			@ApiParam(value = "clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
			@ApiParam(value = "客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
			HttpServletRequest request, HttpServletResponse response) {
        WebUtils.initPre(request, response);

        MessageHandleReq change = change(req, MessageHandleReq.class);
        User user = super.getCurrWxUser(sjboacert, clientType); //获取当前操作用户信息
        change.setRecPersion(user.getId());
        change.setReadStatus("0");
        int redCount = intfzCommonMessageService.findRedCount(change);
        RedCountResp red = new RedCountResp();
        if(redCount > 99){
            red.setRedCount("99+");
        }else{
            red.setRedCount(String.valueOf(redCount));
        }

        return new BaseResp<RedCountResp>(IntfzRs.SUCCESS, "ok",red);
    }



}
