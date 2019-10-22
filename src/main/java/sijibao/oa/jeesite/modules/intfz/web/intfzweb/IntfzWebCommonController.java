package sijibao.oa.jeesite.modules.intfz.web.intfzweb;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

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
import com.sijibao.oa.common.utils.DateUtils;
import com.sijibao.oa.common.utils.StringUtils;
import com.sijibao.oa.common.utils.UploadUtils;
import com.sijibao.oa.common.utils.WebUtils;
import com.sijibao.oa.common.utils.pdf.PdfTemplate;
import com.sijibao.oa.common.web.BaseController;
import com.sijibao.oa.modules.flow.entity.ContractCompanyInfo;
import com.sijibao.oa.modules.flow.service.ContractCompanyInfoService;
import com.sijibao.oa.modules.intfz.bean.BaseResp;
import com.sijibao.oa.modules.intfz.bean.DictInfo;
import com.sijibao.oa.modules.intfz.enums.IntfzRs;
import com.sijibao.oa.modules.intfz.request.common.DictQueryReq;
import com.sijibao.oa.modules.intfz.request.expense.AttachmentRenameReq;
import com.sijibao.oa.modules.intfz.request.intfzwebreq.OrgAndUserInfoRequest;
import com.sijibao.oa.modules.intfz.request.intfzwebreq.QueryFlowRevenceRequest;
import com.sijibao.oa.modules.intfz.request.intfzwebreq.UserInfoRequest;
import com.sijibao.oa.modules.intfz.request.message.MainMessageHandleReq;
import com.sijibao.oa.modules.intfz.response.common.*;
import com.sijibao.oa.modules.intfz.response.expense.SubInfoResponse;
import com.sijibao.oa.modules.intfz.response.expense.SubInfoResponseTree;
import com.sijibao.oa.modules.intfz.response.intfzwebresp.QueryFlowRevenceResponse;
import com.sijibao.oa.modules.intfz.response.intfzwebresp.common.QueryDictResponse;
import com.sijibao.oa.modules.intfz.response.message.MainMessageResp;
import com.sijibao.oa.modules.intfz.service.common.IntfzSysVersionService;
import com.sijibao.oa.modules.intfz.service.expense.IntfzExpenseFlowService;
import com.sijibao.oa.modules.intfz.service.recp.IntfzRecpFlowService;
import com.sijibao.oa.modules.intfz.service.travel.IntfzTravelFlowService;
import com.sijibao.oa.modules.intfz.utils.Constant;
import com.sijibao.oa.modules.intfz.utils.IntfzWebConstant;
import com.sijibao.oa.modules.oa.entity.ExpensesSubInfo;
import com.sijibao.oa.modules.oa.entity.FirstCompanyInfo;
import com.sijibao.oa.modules.oa.entity.ProjectInfo;
import com.sijibao.oa.modules.oa.service.CustInfoService;
import com.sijibao.oa.modules.oa.service.ProjectInfoService;
import com.sijibao.oa.modules.sys.entity.*;
import com.sijibao.oa.modules.sys.service.AreaService;
import com.sijibao.oa.modules.sys.service.OfficeService;
import com.sijibao.oa.modules.sys.service.SystemService;
import com.sijibao.oa.modules.sys.utils.DictUtils;
import com.sijibao.oa.modules.sys.utils.SysParamsUtils;
import com.sijibao.oa.modules.sys.utils.UserUtils;
import com.sijibao.oa.office.api.IntfzCommonMessageService;
import com.sijibao.oa.office.api.request.common.MessageHandleReq;
import com.sijibao.oa.office.api.response.common.MessageResp;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 客户资料管理
 *
 * @author xuby
 */
@Controller
@RequestMapping(value = "${frontPath}/commonInfo")
@Api(value = "WEB公共信息管理服务", tags = "WEB公共信息管理服务")
public class IntfzWebCommonController extends BaseController {

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
    private CustInfoService custInfoService;
    @Autowired
    private ContractCompanyInfoService contractCompanyInfoService;
    @Autowired
    private IntfzCommonMessageService intfzCommonMessageService;
    @Autowired
    private IntfzSysVersionService intfzSysVersionService;

    /**
     * 查询机构部门/人员信息
     */
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "公共信息管理服务-获取机构人员信息")
    @RequestMapping(value = "orgAndUserInfo", method = RequestMethod.POST)
    public BaseResp<List<OrgAndUserInfoResponse>> orgAndUserInfo(
			@ApiParam(value = "登录成功时返回的sessionid") @RequestHeader String sessionid,
			@RequestBody OrgAndUserInfoRequest orgAndUserInfoRequest,
			HttpServletRequest request, HttpServletResponse response) {
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
                res.setStatus(OrgAndUserInfoResponse.STATUS_SELECTABLE);//机构的状态为启用，节点可选择
            } else if (Constant.OFFICE_DISABLE.equals(e.getUseable())) {
                res.setStatus(OrgAndUserInfoResponse.STATUS_NOT_SELECTABLE);//机构的状态为停用，节点不可选择
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
                    if (Constant.USER_WORKING.equals(u.getUserStatus())) {
                        userExtend.setStatus(OrgAndUserInfoResponse.STATUS_SELECTABLE);//人员在职，节点可选择
                    } else {
                        userExtend.setStatus(OrgAndUserInfoResponse.STATUS_NOT_SELECTABLE);//人员非在职，节点不可选择
                    }

                    userExtendList.add(userExtend);
                }
                res.setUserInfo(userExtendList);
            }
            resultList.add(res);
        }

        return new BaseResp<>(IntfzRs.SUCCESS, null, resultList);
    }

    /**
     * 字典信息查询
     */
    @RequestMapping(value = "queryDict")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "公共信息管理服务-字典查询")
    public BaseResp<List<QueryDictResponse>> queryDict(
			@ApiParam(value = "登录成功时返回的sessionid") @RequestHeader String sessionid,
			@RequestBody DictQueryReq req,
			HttpServletRequest request, HttpServletResponse response) {
        WebUtils.initPre(request, response);
        List<QueryDictResponse> resultList = new ArrayList<QueryDictResponse>();
        List<Dict> dictList = DictUtils.getDictList(req.getTypeList());
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
     * 根据字典类型查询数据字典信息
     * 字典类型tax_city:发票所属城市,oa_project:项目
     */
    @RequestMapping(value = "queryDictInfo")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "公共信息管理服务-获取字典信息,tax_city:发票所属城市,oa_project:项目,oa_expense_type:报销类型")
    public BaseResp<List<DictInfo>> queryDictInfo(
			@RequestBody DictInfo dictInfo,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
        WebUtils.initPre(request, response);
        if (dictInfo != null && StringUtils.isBlank(dictInfo.getDictType())) {
            return new BaseResp<List<DictInfo>>(IntfzRs.ERROR, "请输入要查询的数据字典类型", new ArrayList<DictInfo>());
        }
        List<DictInfo> dictList = Lists.newArrayList();

        if ("oa_project".equals(dictInfo.getDictType())) {
            String value = dictInfo.getProjectName();
            List<SysParams> list = SysParamsUtils.getParamsList("alive_word");
            ProjectInfo query = new ProjectInfo();
            for (SysParams sysParams : list) {
                if (StringUtils.isNotBlank(sysParams.getParamValue())) {
                    String[] split = sysParams.getParamValue().split(",");
                    for (String ss : split) {
                        if (StringUtils.isNotBlank(value) && ss.indexOf(value) > -1) {
                            return new BaseResp<List<DictInfo>>(IntfzRs.ERROR, "该搜索范围过广，请输入精确信息!", null);
                        }
                    }
                }
            }
            query.setProjectName(value);
            List<ProjectInfo> projectList = projectInfoService.findList(query);
            for (ProjectInfo project : projectList) {
                dictList.add(new DictInfo(project.getId(), project.getProjectName().trim(), dictInfo.getDictType(),
                        project.getProjectName().trim(), 0, null, project));
            }
        } else {
            List<Dict> resultList = DictUtils.getDictList(dictInfo.getDictType());
            for (Dict dict : resultList) {
                dictList.add(new DictInfo(dict.getValue().trim(), dict.getLabel().trim(), dict.getType().trim(),
                        dict.getDescription().trim(), dict.getSort(), dict.getParentId()));
            }
        }
        return new BaseResp<List<DictInfo>>(IntfzRs.SUCCESS, "ok", dictList);
    }

    /**
     * 查询区域信息
     */
    @RequestMapping(value = "queryAreaInfo")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "公共信息管理服务-区域信息查询")
    public BaseResp<List<AreaInfoResponse>> areaInfoList(
			@ApiParam(value = "登录成功时返回的sessionid") @RequestHeader String sessionid,
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
     */
    @RequestMapping(value = "querySubInfo")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "公共信息管理服务-费用科目信息查询")
    public BaseResp<PageResponse<List<SubInfoResponse>>> querySubInfolist(
			@ApiParam(value = "登录成功时返回的sessionid") @RequestHeader String sessionid,
			@RequestBody ExpensesSubInfo expensesSubInfo,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
        WebUtils.initPre(request, response);
        List<SubInfoResponse> subInfolist = wxExpenseFlowService.getSubInfoList(expensesSubInfo);
        return new BaseResp<PageResponse<List<SubInfoResponse>>>(IntfzRs.SUCCESS, "ok",
                new PageResponse<List<SubInfoResponse>>(subInfolist, 1, subInfolist.size()));
    }

    /**
     * 查询科目信息，树形结构
     */
    @RequestMapping(value = "querySubInfoNew")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "公共信息管理服务-费用科目信息查询(树形结构)")
    public BaseResp<PageResponse<List<SubInfoResponseTree>>> querySubInfoNew(
			@ApiParam(value = "登录成功时返回的sessionid") @RequestHeader String sessionid,
			@RequestBody ExpensesSubInfo expensesSubInfo,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
        WebUtils.initPre(request, response);
        List<SubInfoResponseTree> subInfolist = wxExpenseFlowService.getSubInfoListNew(expensesSubInfo);
        return new BaseResp<>(IntfzRs.SUCCESS, "ok",
                new PageResponse<>(subInfolist, 1, subInfolist.size()));
    }

    /**
     * 附件上传
     */
    @RequestMapping(value = "fileUpload")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "公共信息管理服务-附件上传")
    public BaseResp<JSONObject> fileUpload(
			@ApiParam(value = "登录成功时返回的sessionid") @RequestHeader String sessionid,
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
    @ApiOperation(httpMethod = "POST", value = "公共信息管理服务-关联查询列表")
    public BaseResp<PageResponse<List<QueryFlowRevenceResponse>>> flowRevencelist(@RequestBody QueryFlowRevenceRequest queryFlowRevenceRequest,
																				  @ApiParam(value = "登录成功时返回的sessionid") @RequestHeader String sessionid,
																				  HttpServletRequest request, HttpServletResponse response) throws Exception {
        WebUtils.initPre(request, response);
        if (StringUtils.isBlank(queryFlowRevenceRequest.getApplyType())) {
            return new BaseResp<PageResponse<List<QueryFlowRevenceResponse>>>(IntfzRs.ERROR, "报销类型不能为空!", null);
        }
        User user = UserUtils.getUser(sessionid); //获取当前申请人信息
        if (user == null) {
            return new BaseResp<PageResponse<List<QueryFlowRevenceResponse>>>(IntfzRs.ERROR, "未找到当前登录人信息!", null);
        }
        Page<QueryFlowRevenceResponse> page = new Page<QueryFlowRevenceResponse>();
        if (Constant.OA_EXPENSE_TYPE_TWO.equals(queryFlowRevenceRequest.getApplyType())) {
            page = intfzRecpFlowService.queryRecpFlowRevencelist(user, queryFlowRevenceRequest);//接待申请流程
        } else if (Constant.OA_EXPENSE_TYPE_THREE.equals(queryFlowRevenceRequest.getApplyType())) {
            page = intfzTravelFlowService.queryTravelFlowRevencelist(user, queryFlowRevenceRequest);//报销申请流程
        }
        return new BaseResp<PageResponse<List<QueryFlowRevenceResponse>>>(IntfzRs.SUCCESS, "ok",
                new PageResponse<List<QueryFlowRevenceResponse>>(page.getList(), page.getPageNo(), page.getCount()));
    }

    /**
     * 根据用户ID查询用户信息
     */
    @RequestMapping(value = "queryUserInfoForId")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "公共信息管理服务-根据用户ID查询用户信息")
    public BaseResp<User> queryUserInfoForId(@RequestBody UserInfoRequest userInfoRequest,
											 @ApiParam(value = "登录成功时返回的sessionid") @RequestHeader String sessionid,
											 HttpServletRequest request, HttpServletResponse response) {
        if (StringUtils.isBlank(userInfoRequest.getUserId())) {
            return new BaseResp<User>(IntfzRs.ERROR, "用户ID不能为空!", null);
        }
        User user = UserUtils.get(userInfoRequest.getUserId());

        return new BaseResp<User>(IntfzRs.SUCCESS, "ok", user);
    }


    /**
     * 公共信息管理服务-查询所有人员
     */
    @RequestMapping(value = "queryUserInfo")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "公共信息管理服务-查询所有人员")
    public BaseResp<List<User>> escortEmployee(
			@ApiParam(value = "登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
        WebUtils.initPre(request, response);
        List<User> userList = systemService.findUserNotAccess(new User());
        return new BaseResp<List<User>>(IntfzRs.SUCCESS, "ok", userList);
    }

    /**
     * 附件下载
     */
    @RequestMapping(value = "downFiles")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "公共信息管理服务-下载附件并重新赋值文件名")
    public BaseResp<String> downFiles(
			@ApiParam(value = "登录成功时返回的sessionid") @RequestHeader String sessionid,
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
            logger.info("=====downloadFiles=======", url);
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
     * 下载合同pdf
     */
    @RequestMapping(value = "downloadContractPdf")
    @ResponseBody
    public void downloadContractPdf(String custId, String contractCompanyCode,
									HttpServletResponse response, HttpServletRequest request) {

        Map<String, Object> date = new HashMap<String, Object>();

        //查询客户合同信息
        List<FirstCompanyInfo> firstCompanyInfoList = custInfoService.findCustContractAndLinkmanInfoForCustId(custId);
        if (firstCompanyInfoList != null && firstCompanyInfoList.size() > 0) {
            FirstCompanyInfo f = firstCompanyInfoList.get(0);
            date.put("name", f.getCustName()); //甲方客户名称
            date.put("name_encode", "1"); //甲方客户名称

            date.put("code", f.getCreditCode()); //甲方统一社会信用代码
            date.put("code_encode", "0"); //甲方客户名称

            date.put("dispath", f.getDispatchProportion() + "%");  //调度费比例
            date.put("dispath_encode", "1"); //甲方客户名称

            date.put("address", f.getCustAddress());       //甲方注册地址
            date.put("address_encode", "1"); //甲方客户名称

            date.put("personel", f.getLegalRepresentative());   //甲方法定代表人
            date.put("personel_encode", "1"); //甲方客户名称

            date.put("lianxirenjiafang", f.getLinkmanName());   //甲方联系人
            date.put("lianxirenjiafang_encode", "1"); //甲方客户名称

            date.put("dianhua", f.getLinkmanPhone());   //甲方联系人电话
            date.put("dianhua_encode", "1"); //甲方客户名称

            date.put("youxiang", f.getLinkmanMail());  //甲方邮箱
            date.put("youxiang_encode", "0"); //甲方客户名称
        }

        //查询乙方合同模版名称
        ContractCompanyInfo c = contractCompanyInfoService.getForContractCompanyCode(contractCompanyCode);
        if (c != null) {
            date.put("lianxirenyifang", c.getSecondLinkman()); //乙方联系人
            date.put("lianxirenyifang_encode", "1"); //甲方客户名称

            date.put("dianhuayifang", c.getSecondLinkmanPhone());  //乙方联系人电话
            date.put("dianhuayifang_encode", "1"); //甲方客户名称

            date.put("youxiangyifang", c.getSecondLinkmanMail());  //乙方邮箱
            date.put("youxiangyifang_encode", "0"); //甲方客户名称
			/*date.put("year", "");  //年
			date.put("month", ""); //月
			date.put("day", "");  //日*/
            String sourcesUrl = "/doc/" + c.getRemarks();
            String fileName = c.getRemarks() + "-" + DateUtils.format(new Date(), DateUtils.YYYYMMDDHHMMSS) + ".pdf";
            PdfTemplate pdfTemplate = new PdfTemplate();
            pdfTemplate.createPdfFormTemplate(4, date, sourcesUrl, fileName, response);  //生成合同pdf
        }
    }

    /**
     * 公共信息管理服务-根据当前登入人数据权限查询组员信息
     */
    @RequestMapping(value = "queryMemberInfo")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "公共信息管理服务-根据当前登入人数据权限查询组员信息")
    public BaseResp<List<User>> queryMemberInfo(
			@ApiParam(value = "登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
        WebUtils.initPre(request, response);
        User user = UserUtils.getUser(sessionid);
        List<User> list = systemService.queryMemberInfo(user);
        return new BaseResp<List<User>>(IntfzRs.SUCCESS, "ok", list);
    }

    /**
     * 公共信息管理服务-根据当前登入人数据权限查询部门
     */
    @RequestMapping(value = "queryMarketOffice")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "公共信息管理服务-根据当前登入人数据权限查询部门")
    public BaseResp<List<OrgAndUserInfoResponse>> queryMarketOffice(
			@ApiParam(value = "登录成功时返回的sessionid") @RequestHeader String sessionid,
			@RequestBody OrgAndUserInfoRequest orgAndUserInfoRequest,
			HttpServletRequest request, HttpServletResponse response, Model model) {
        List<OrgAndUserInfoResponse> resultList = new ArrayList<>();
        //校验queryType是否为空
        if (StringUtils.isBlank(orgAndUserInfoRequest.getQueryType())) {
            return new BaseResp<>(IntfzRs.ERROR, "queryType不能为空", null);
        }
        User user = UserUtils.getUser(sessionid);
        List<Office> list = officeService.queryMarketOffice(user);//查询营销中心下面的部门
        for (int i = 0; i < list.size(); i++) {
            Office e = list.get(i);
            OrgAndUserInfoResponse res = new OrgAndUserInfoResponse();
            res.setId(e.getId());
            if(e.getParent() != null){
                res.setpId(e.getParent().getId());
            }
            res.setName(e.getName());
            res.setType(OrgAndUserInfoResponse.TYPE_ORG);/*设置节点的类型，方便前端判断节点是机构还是人员*/
            if (Constant.OFFICE_USABLE.equals(e.getUseable())) {
                res.setStatus(OrgAndUserInfoResponse.STATUS_SELECTABLE);//机构的状态为启用，节点可选择
            } else if (Constant.OFFICE_DISABLE.equals(e.getUseable())) {
                res.setStatus(OrgAndUserInfoResponse.STATUS_NOT_SELECTABLE);//机构的状态为停用，节点不可选择
            }
            if (IntfzWebConstant.QUERY_ORGTYPE_TWO.equals(orgAndUserInfoRequest.getQueryType())) {
                List<User> userList = systemService.findUserByOfficeId(e.getId());
                List<UserExtend> userExtendList = new LinkedList<>();
                for (User u : userList) {
                    u.setLoginName("");
                    u.setMobile("");

                    UserExtend userExtend = change(u, UserExtend.class);
                    userExtend.setType(OrgAndUserInfoResponse.TYPE_USER);/*设置节点的类型，方便前端判断节点是机构还是人员*/
                    if (Constant.USER_WORKING.equals(u.getUserStatus())) {
                        userExtend.setStatus(OrgAndUserInfoResponse.STATUS_SELECTABLE);//人员在职，节点可选择
                    } else {
                        userExtend.setStatus(OrgAndUserInfoResponse.STATUS_NOT_SELECTABLE);//人员非在职，节点不可选择
                    }

                    userExtendList.add(userExtend);
                }
                res.setUserInfo(userExtendList);
            }
            resultList.add(res);
        }
        if (resultList != null && resultList.size() != 0) {
            String pid = resultList.get(0).getpId();
            String queryId = this.queryPid(resultList,pid);
            while (StringUtils.isNotBlank(queryId) && !queryId.equals(pid)){
                pid = queryId ;
                queryId = this.queryPid(resultList,pid);
            }

            logger.info("【pid的值为】:"+pid);
            for (OrgAndUserInfoResponse f : resultList) {
                if (StringUtils.isNotBlank(f.getpId()) && f.getpId().equals(pid)) {
                    f.setpId("null");
                }
            }
        }
        return new BaseResp<>(IntfzRs.SUCCESS, null, resultList);
    }

    String queryPid(List<OrgAndUserInfoResponse> resultList,String pid){
        for (OrgAndUserInfoResponse o : resultList) {
            if (StringUtils.equals(pid, o.getId())) {
                pid = o.getpId();
            }
        }
        return pid ;
    }

    /**
     * app公共信息管理服务-消息查询
     *
     * @param req
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "findMessagePage")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "app公共信息管理服务-消息查询")
    public BaseResp<PageResponse<List<MainMessageResp>>> findMessagePage(
			@RequestBody MainMessageHandleReq req,
			@ApiParam(value = "登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) {
        WebUtils.initPre(request, response);
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (StringUtils.isBlank(req.getReadStatus())) {
            return new BaseResp<PageResponse<List<MainMessageResp>>>(IntfzRs.ERROR, "读取状态不能为空!", null);
        }
        MessageHandleReq change = change(req, MessageHandleReq.class);
        User user = UserUtils.getUser(sessionid);
        change.setRecPersion(user.getId());
        PagerResponse<MessageResp> findPage = intfzCommonMessageService.findMessagePage(change);
        ArrayList<MainMessageResp> list = Lists.newArrayList();
        if (findPage.getList() != null && findPage.getList().size() > 0) {
            for (MessageResp s : findPage.getList()) {
                MainMessageResp ss = change(s, MainMessageResp.class);
                if (StringUtils.isBlank(s.getPathType())) {
                    ss.setPathType("done");
                }
                list.add(ss);
            }
        }
        PageResponse<List<MainMessageResp>> resp = new PageResponse<List<MainMessageResp>>(list, req.getPageNo(), findPage.getCount());

        int redCount = intfzCommonMessageService.findRedCount(change);
        if (redCount > 99) {
            resp.setRedCount("99+");
        } else {
            resp.setRedCount(String.valueOf(redCount));
        }
        return new BaseResp<PageResponse<List<MainMessageResp>>>(IntfzRs.SUCCESS, "ok", resp);
    }

    /**
     * 查询消息红点数量
     *
     * @param req       请求数据
     * @param sessionid 会话id
     * @param request   HttpServletRequest
     * @param response  HttpServletResponse
     * @return 响应数据
     */
    @RequestMapping(value = "findRedCount")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "web公共信息管理服务-消息查询红点")
    public BaseResp<RedCountResp> findRedCount(
			@RequestBody MainMessageHandleReq req,
			@ApiParam(value = "登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) {
        WebUtils.initPre(request, response);

        MessageHandleReq change = change(req, MessageHandleReq.class);
        User user = UserUtils.getUser(sessionid);
        change.setRecPersion(user.getId());
        change.setReadStatus("0");
        int redCount = intfzCommonMessageService.findRedCount(change);
        RedCountResp red = new RedCountResp();
        if (redCount > 99) {
            red.setRedCount("99+");
        } else {
            red.setRedCount(String.valueOf(redCount));
        }

        return new BaseResp<>(IntfzRs.SUCCESS, "ok", red);
    }

    /**
     * 查询当前登录用户实时个人信息
     *
     * @param sessionid 会话id
     * @param request   HttpServletRequest
     * @param response  HttpServletResponse
     * @return 响应数据
     */
    @RequestMapping(value = "findUserRealTimeInfo")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "web公共信息管理服务-查询当前登录用户实时个人信息")
    public BaseResp<UserRealTimeInfoResp> findUserRealTimeInfo(
			@ApiParam(value = "登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) {
        WebUtils.initPre(request, response);

        UserRealTimeInfoResp resp = new UserRealTimeInfoResp();
        User user = UserUtils.getUser(sessionid);
        user = UserUtils.get(user.getId());

        resp.setId(user.getId());
        resp.setLoginName(user.getLoginName());
        resp.setMobile(user.getMobile());
        resp.setName(user.getName());
        resp.setOfficeId(user.getOffice().getId());
        resp.setOfficeName(user.getOffice().getName());
        resp.setUseable(user.getOffice().getUseable());
        resp.setPostId(user.getPostIds());
        resp.setPostName(user.getPostName());
        resp.setVersion(intfzSysVersionService.queryNewVersion());
        return new BaseResp<>(IntfzRs.SUCCESS, "ok", resp);
    }
}