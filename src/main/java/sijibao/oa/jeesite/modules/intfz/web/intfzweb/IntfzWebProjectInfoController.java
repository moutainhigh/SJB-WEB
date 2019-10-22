package sijibao.oa.jeesite.modules.intfz.web.intfzweb;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.curator.shaded.com.google.common.collect.Lists;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sijibao.base.common.api.response.PagerResponse;
import com.sijibao.oa.common.utils.SpringContextHolder;
import com.sijibao.oa.common.utils.WebUtils;
import com.sijibao.oa.common.web.BaseController;
import com.sijibao.oa.crm.api.IntfzWebProjectInfoService;
import com.sijibao.oa.crm.api.exception.ServiceException;
import com.sijibao.oa.crm.api.request.project.*;
import com.sijibao.oa.crm.api.response.project.*;
import com.sijibao.oa.modules.intfz.bean.BaseResp;
import com.sijibao.oa.modules.intfz.enums.IntfzRs;
import com.sijibao.oa.modules.intfz.request.common.HandleReq;
import com.sijibao.oa.modules.intfz.request.contract.MainProjectContractHisReq;
import com.sijibao.oa.modules.intfz.request.intfzwebreq.projectinfo.*;
import com.sijibao.oa.modules.intfz.response.common.PageResponse;
import com.sijibao.oa.modules.intfz.response.intfzwebresp.projectinfo.*;
import com.sijibao.oa.modules.intfz.service.common.IntfzCommonService;
import com.sijibao.oa.modules.intfz.service.report.IntfzProjectImplyStatusExportService;
import com.sijibao.oa.modules.intfz.utils.CharChangeUtils;
import com.sijibao.oa.modules.oa.dao.CustInfoDao;
import com.sijibao.oa.modules.sys.entity.SysParams;
import com.sijibao.oa.modules.sys.entity.User;
import com.sijibao.oa.modules.sys.service.OfficeService;
import com.sijibao.oa.modules.sys.utils.DictUtils;
import com.sijibao.oa.modules.sys.utils.SysParamsUtils;
import com.sijibao.oa.modules.sys.utils.UserUtils;
import com.sijibao.oa.office.api.IntfzWebContractHisService;
import com.sijibao.oa.office.api.request.contracthis.ProjectContractHisReq;
import com.sijibao.oa.office.api.response.contracthis.ProjectContractHisResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 项目管理controller
 * wanxb
 */
@Controller
@RequestMapping(value = "${frontPath}/projectInfo")
@Api(value="WEB项目管理服务",tags="WEB项目管理服务")
public class IntfzWebProjectInfoController extends BaseController {
	
	private IntfzWebProjectInfoService intfzWebProjectInfoService;
	private IntfzWebContractHisService intfzWebContractHisService;

	@Autowired
	private OfficeService officeService;
	@Autowired
	private CustInfoDao custInfoDao;
	@Autowired
	private IntfzCommonService intfzCommonService;
	
	@ModelAttribute
	public void init(){
		if(this.intfzWebProjectInfoService == null){
			this.intfzWebProjectInfoService = SpringContextHolder.getBean("intfzWebProjectInfoService");
		}
		if(this.intfzWebContractHisService == null){
			this.intfzWebContractHisService = SpringContextHolder.getBean("intfzWebContractHisService");
		}
	};


	/**
	 * 项目管理-查询列表
	 * @param req
	 * @param sessionid
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "projectInfoList")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "项目管理-查询列表")
	BaseResp<PageResponse<List<MainProjectInfoResponse>>> projectInfoList(
			@RequestBody MainProjectInfoHandleReq req,
			@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		//拦截敏感词
		String value = req.getProjectName();
		if(StringUtils.isNotBlank(value)){
			List<SysParams> list = SysParamsUtils.getParamsList("alive_word");
			for (SysParams sysParams : list) {
				if(StringUtils.isNotBlank(sysParams.getParamValue())){
					String[] split = sysParams.getParamValue().split(",");
					for (String ss : split) {
						if(StringUtils.isNotBlank(value) && ss.indexOf(value) > -1){
							return new  BaseResp<PageResponse<List<MainProjectInfoResponse>>>(IntfzRs.ERROR, "该搜索范围过广，请输入精确信息!", null);
						}
					}
				}
			}
		}
		
		ProjectInfoHandleReq handleReq = change(req, ProjectInfoHandleReq.class);
//		change(req, ProjectInfoHandleReq.class);
		PagerResponse<ProjectInfoResponse> page = intfzWebProjectInfoService.findPage(handleReq);
		ArrayList<MainProjectInfoResponse> list = Lists.newArrayList();
		for (ProjectInfoResponse cc : page.getList()) {
			MainProjectInfoResponse ss = change(cc, MainProjectInfoResponse.class);
			list.add(ss);
		}
		return new BaseResp<PageResponse<List<MainProjectInfoResponse>>>(IntfzRs.SUCCESS, "ok", 
				new PageResponse<List<MainProjectInfoResponse>>(list, req.getPageNo(), page.getCount()));
	}

	/**
	 * 项目详情信息查询
	 * @param sessionid
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "projectInfoDetail")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "项目管理-详细信息")
	BaseResp<MainProjectInfoDetailResponse> projectInfoDetail(
			@RequestBody HandleReq req,
			@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		if(StringUtils.isBlank(req.getId())){
			return new BaseResp<MainProjectInfoDetailResponse>(IntfzRs.ERROR, "id不能为空",null);
		}
		User user = UserUtils.getUser(sessionid); //获取当前申请人信息
		if(user == null){
			return new BaseResp<MainProjectInfoDetailResponse>(IntfzRs.ERROR, "未找到用户信息",null);
		}
		ProjectInfoDetailResponse resp = intfzWebProjectInfoService.getProjectInfoDetailById(req.getId(),user.getId());
		MainProjectInfoDetailResponse projectInfoDetailResponse = change(resp, MainProjectInfoDetailResponse.class);

		List<MainProjectNodeDetailResponse> list = Lists.newArrayList();
		for (ProjectNodeDetailResponse cc : resp.getProjectNodeDetailResponse()) {
			MainProjectNodeDetailResponse change = change(cc, MainProjectNodeDetailResponse.class);
			if(change.getNodeAddress() == null){
				change.setNodeAddress("");
			}
//			if(change.getLat() == null){
//				change.setLat("");
//			}
//			if(change.getLng() == null){
//				change.setLng("");
//			}
			list.add(change);
		}
		projectInfoDetailResponse.setProjectNodeDetailResponse(list);

		ArrayList<MainCompanyShirtResponse> company = Lists.newArrayList();
		for (CompanyShirtResponse ss : resp.getCompanyResponse()) {
			MainCompanyShirtResponse change = change(ss, MainCompanyShirtResponse.class);
			company.add(change);
		}
		projectInfoDetailResponse.setMainCompany(company);

        List<MainProjectLinkmanDetailResponse> projectLinkmanDetailResponses = Lists.newArrayList();
        for(ProjectLinkmanResponse linkmanResponse:resp.getProjectLinkman()){
            MainProjectLinkmanDetailResponse linkmanDetailResponse=change(linkmanResponse,MainProjectLinkmanDetailResponse.class);
            projectLinkmanDetailResponses.add(linkmanDetailResponse);
        }
        projectInfoDetailResponse.setProjectLinkmanDetailResponse(projectLinkmanDetailResponses);
        if(StringUtils.isNotBlank(resp.getCarrierGoods())){
			String[] split = resp.getCarrierGoods().split(",");
			List<Object> carrierGoodsList=Lists.newArrayList();
			for(String carrierGoods:split){
				JSONObject jsonObject=new JSONObject();
				jsonObject.put("carrierGood",carrierGoods);
				jsonObject.put("carrierGoodName",DictUtils.getDictLabel(carrierGoods,"carrier_goods",""));
				carrierGoodsList.add(jsonObject);
			}
			projectInfoDetailResponse.setCarrierGoods(carrierGoodsList);
		}

        if(StringUtils.isNotBlank(resp.getGeneralRequires())){
            logger.info("【项目管理】一般需求:{}",resp.getGeneralRequires());
            MainProjectGeneralRequireResponse jsonObject = JSON.parseObject(resp.getGeneralRequires(),MainProjectGeneralRequireResponse.class);
            projectInfoDetailResponse.setGeneralRequireResponse(jsonObject);
        }else{
			projectInfoDetailResponse.setGeneralRequireResponse(new MainProjectGeneralRequireResponse());
		}

        if(StringUtils.isNotBlank(resp.getSpecialRequires())){
            logger.info("【项目管理】特殊需求:{}",resp.getSpecialRequires());
            MainProjectSpecialRequireResponse jsonObject = JSON.parseObject(resp.getSpecialRequires(),MainProjectSpecialRequireResponse.class);
            projectInfoDetailResponse.setSpecialRequireResponse(jsonObject);
        }else{
			projectInfoDetailResponse.setSpecialRequireResponse(new MainProjectSpecialRequireResponse());
		}

        //（详情里）查找项目负责人
		/*if(StringUtils.isNotBlank(resp.getCustInfoId())){
			CustInfo custInfo = custInfoDao.get(resp.getCustInfoId());
			if(custInfo!=null){
				projectInfoDetailResponse.setNewProjectLeaderName(custInfo.getMarketLeaderName());
				User projectUser = UserUtils.get(custInfo.getMarketLeaderId());
				if(projectUser!=null&&projectUser.getOffice()!=null){
					projectInfoDetailResponse.setNewOfficeName(projectUser.getOffice().getName());
					projectInfoDetailResponse.setOfficeLeaderName(projectUser.getOffice().getPrimaryPerson().getName());
				}
			}
		}*/
		projectInfoDetailResponse.setNewProjectLeaderName(resp.getProjectLeaderName());
		projectInfoDetailResponse.setNewOfficeName(resp.getOfficeName());
		if(StringUtils.isNotBlank(resp.getOfficeLeaderNameId())){
            projectInfoDetailResponse.setOfficeLeaderName(UserUtils.get(resp.getOfficeLeaderNameId()).getName());
        }
		projectInfoDetailResponse.setMainCustName(resp.getMainCustName());
		return new BaseResp<MainProjectInfoDetailResponse>(IntfzRs.SUCCESS, "ok", projectInfoDetailResponse);
	}

	/**
	 * 项目详情-节点列表-分页查询
	 */
	@RequestMapping(value = "pagedQueryNodeList")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "项目管理-详细信息-节点列表(分页查询)")
	BaseResp<PageResponse<List<PagedQueryNodeListResp>>> pagedQueryNodeList(
			@RequestBody PagedQueryNodeListReq req,
			@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) {
		WebUtils.initPre(request, response);
		if(StringUtils.isBlank(req.getProjectId())){
			return new BaseResp<>(IntfzRs.ERROR, "id不能为空",null);
		}

		PagerResponse<PagedQueryNodeListResult> page = intfzWebProjectInfoService.pagedQueryNodeList(change(req, PagedQueryNodeListParams.class));

		List<PagedQueryNodeListResp> list = Lists.newArrayList();
		for (PagedQueryNodeListResult cc : page.getList()) {
			PagedQueryNodeListResp change = change(cc, PagedQueryNodeListResp.class);
			list.add(change);
		}
		return new BaseResp<>(IntfzRs.SUCCESS, "ok", new PageResponse<>(list, req.getPageNo(), page.getCount()));
	}


	/**
	 * 项目管理-保存项目信息
	 * @param sessionid
	 * @param projectInfoSaveReq
	 * @param request
	 * @param response
	 * @return
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws BeansException
	 */
	@RequestMapping(value = "saveProjectInfo")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "项目管理-保存项目信息")
	public BaseResp<String> saveProjectInfo(@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
											@Valid @RequestBody MainProjectInfoSaveReq projectInfoSaveReq,
											HttpServletRequest request, HttpServletResponse response){
		WebUtils.initPre(request, response);
		User user = UserUtils.getUser(sessionid);
		CharChangeUtils.CharChange(projectInfoSaveReq);//替换英文字符
		projectInfoSaveReq.setProducSide("web");
        ArrayList<ProjectNodeReq> list = Lists.newArrayList();
		if(projectInfoSaveReq.getProjectNodeReqs()!=null&&projectInfoSaveReq.getProjectNodeReqs().size()>0){
            for(MainProjectNodeReq req:projectInfoSaveReq.getProjectNodeReqs()){
                CharChangeUtils.CharChange(req);//替换英文字符
                ProjectNodeReq cc = change(req, ProjectNodeReq.class);
                list.add(cc);
            }
        }

		try {
			if(projectInfoSaveReq.getHolderCode().size()>1){
				return new BaseResp<>(IntfzRs.ERROR,"企业名称不可多选", null);
			}
			//校验重复名称
			//List<ProjectInfoResponse> pList = intfzWebProjectInfoService.findSameNameList(projectInfoSaveReq.getId(),projectInfoSaveReq.getProjectName());
			int byNameCount = intfzWebProjectInfoService.findByNameCount(projectInfoSaveReq.getId(),projectInfoSaveReq.getProjectName());
			if(byNameCount > 0){
				return new BaseResp<>(IntfzRs.ERROR,"项目名称重复", null);
			}
			int projectByNameCount = intfzWebProjectInfoService.findProjectByNameCount(projectInfoSaveReq.getProcessFlag(),projectInfoSaveReq.getProjectName());
			if(projectByNameCount > 0){
				return new BaseResp<>(IntfzRs.ERROR,"项目名称已在流程立项中重复", null);
			}
			ProjectInfoSaveReq saveReq =  change(projectInfoSaveReq, ProjectInfoSaveReq.class);
            saveReq.setProjectNodeReqs(list);
			if(StringUtils.isNotBlank(projectInfoSaveReq.getCustInfoId()) && custInfoDao.get(projectInfoSaveReq.getCustInfoId()) != null){
				saveReq.setCustInfoName(custInfoDao.get(projectInfoSaveReq.getCustInfoId()).getCustName());
			}
			if(StringUtils.isNotBlank(projectInfoSaveReq.getImpleLeaderId()) && UserUtils.get(projectInfoSaveReq.getImpleLeaderId()) != null){
				saveReq.setImpleLeaderName(UserUtils.get(projectInfoSaveReq.getImpleLeaderId()).getName());
			}
			if(StringUtils.isNotBlank(projectInfoSaveReq.getProjectManagerId()) && UserUtils.get(projectInfoSaveReq.getProjectManagerId()) != null){
				saveReq.setProjectManager(UserUtils.get(projectInfoSaveReq.getProjectManagerId()).getName());
			}
			
            if(projectInfoSaveReq.getCarrierGoods()!=null&&projectInfoSaveReq.getCarrierGoods().size()>0){
                saveReq.setCarrierGoods(StringUtils.join(projectInfoSaveReq.getCarrierGoods(),","));
            }

			if(projectInfoSaveReq.getGeneralRequire()!=null){
				MainProjectGeneralRequire generalRequire = projectInfoSaveReq.getGeneralRequire();
				if(0==generalRequire.getProjectTrusteeshipt()){
					generalRequire.setTrusteeshiptChannel(null);
				}
				saveReq.setGeneralRequires(JSON.toJSONString(generalRequire));
			}
			if(projectInfoSaveReq.getSpecialRequire()!=null){
				MainProjectSpecialRequire specialRequire = projectInfoSaveReq.getSpecialRequire();
				if(0==specialRequire.getReturnPoint()){
					specialRequire.setReturnPointProportion(null);
				}
				saveReq.setSpecialRequires(JSON.toJSONString(specialRequire));
			}
			ArrayList<ProjectContact> contacts=Lists.newArrayList();
			for(MainProjectContact contact:projectInfoSaveReq.getMainProjectContacts()){
				ProjectContact projectContact=change(contact,ProjectContact.class);
				contacts.add(projectContact);
			}
			saveReq.setProjectContacts(contacts);
			intfzWebProjectInfoService.saveProjectInfo(saveReq, user.getId());
		} catch (ServiceException e) {
			e.getStackTrace();
			return new BaseResp<String>(IntfzRs.ERROR,e.getMessage(),null);
		}
		return new BaseResp<String>(IntfzRs.SUCCESS,"保存项目信息成功!",null);
	}
	
	/**
	 * 项目信息维护管理保存接口
	 * @param sessionid
	 * @param req
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "saveProjectMaintenance")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "项目维护管理-保存信息")
	public BaseResp<String> saveProjectMaintenance(
			@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
			@RequestBody MainProjectMaintenanceSaveReq req,
			HttpServletRequest request, HttpServletResponse response){
		WebUtils.initPre(request, response);
		CharChangeUtils.CharChange(req);//替换英文字符
		try {
			User user = UserUtils.getUser(sessionid);

			ProjectMaintenanceSaveReq saveReq = change(req, ProjectMaintenanceSaveReq.class);
		
			intfzWebProjectInfoService.saveProjectMaintenance(saveReq,user.getId());
		} catch (Exception e) {
			e.printStackTrace();
			return new BaseResp<String>(IntfzRs.ERROR,"保存失败！",null);
		}
		return new BaseResp<String>(IntfzRs.SUCCESS,"保存成功！",null);
	}
	/**
	 * 项目维护管理-列表查询
	 * @param req
	 * @param sessionid
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "projectMaintenanceList")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "项目维护管理-列表查询")
	BaseResp<PageResponse<List<MainProjectMaintenanceResponse>>> projectMaintenanceList(
			@RequestBody MainProjectMaintenanceHandleReq req,
			@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		User user = UserUtils.getUser(sessionid);
		ProjectMaintenanceHandleReq handleReq = change(req, ProjectMaintenanceHandleReq.class);
		PagerResponse<ProjectMaintenanceResponse> page = intfzWebProjectInfoService.findProjectMaintenancePage(handleReq, user.getId());
		ArrayList<MainProjectMaintenanceResponse> list = Lists.newArrayList();
		for (ProjectMaintenanceResponse cc : page.getList()) {
			MainProjectMaintenanceResponse ss = change(cc, MainProjectMaintenanceResponse.class);
			list.add(ss);
		}
		return new BaseResp<PageResponse<List<MainProjectMaintenanceResponse>>>(IntfzRs.SUCCESS, "ok", 
				new PageResponse<List<MainProjectMaintenanceResponse>>(list, req.getPageNo(), page.getCount()));
	}
	
//	@RequestMapping(value = "projectContractList")
//	@ResponseBody
//	@ApiOperation(httpMethod = "POST", value = "项目管理-项目与合同关联查询（签约合同列表查询）")
//	BaseResp<PageResponse<List<ProjectContractResponse>>> projectContractList(
//			@RequestBody HandleReq req,
//			@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
//			HttpServletRequest request,HttpServletResponse response) throws Exception{
//		WebUtils.initPre(request, response);
//		logger.info("=================projectInfo projectMaintenanceList start==============="+req.toString());
//		User user = UserUtils.getUser(sessionid);
//		com.sijibao.oa.sys.provider.entity.User user2 = officeService.getUserByUser(user);
//		ProjectMaintenanceHandleReq handleReq = change(req, ProjectMaintenanceHandleReq.class);
//		PagerResponse<ProjectMaintenanceResponse> page = intfzWebProjectInfoService.findProjectMaintenancePage(handleReq, user2);
//		ArrayList<MainProjectMaintenanceResponse> list = Lists.newArrayList();
//		for (ProjectMaintenanceResponse cc : page.getList()) {
//			MainProjectMaintenanceResponse ss = change(cc, MainProjectMaintenanceResponse.class);
//			list.add(ss);
//		}
//		logger.info("=======projectInfo custMaintenanceList end=============");
//		return null;
//	}
	
	/**
	 * 不带分页的查询
	 * @param sessionid
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "queryProjectInfo")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "项目管理-查询(不带分页)")
	BaseResp<List<MainProjectInfoResponse>> queryProjectInfo(
			@RequestBody MainProjectInfoHandleReq req,
			@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		ProjectInfoHandleReq change = change(req,ProjectInfoHandleReq.class);
		req.setPageNo(1);
		req.setPageSize(8000);
		PagerResponse<ProjectInfoResponse> page = intfzWebProjectInfoService.findPage(change);
		List<ProjectInfoResponse> list = page.getList();
		ArrayList<MainProjectInfoResponse> nlist = Lists.newArrayList();
		for (ProjectInfoResponse cc : list) {
			MainProjectInfoResponse ss = change(cc, MainProjectInfoResponse.class);
			nlist.add(ss);
		}
		return new BaseResp<List<MainProjectInfoResponse>>(IntfzRs.SUCCESS, "ok", nlist);
	}
	
	
	/**
	 * 查询签约合同
	 * @param sessionid
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "queryProjectContractHis")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "项目管理-查询签约合同信息")
	BaseResp<PageResponse<List<MainProjectContractHisResponse>>> queryProjectContractHis(
			@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
			@RequestBody MainProjectContractHisReq req,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		ProjectContractHisReq change = change(req, ProjectContractHisReq.class);
		PagerResponse<ProjectContractHisResponse> page = intfzWebContractHisService.queryProjectContractHis(change);
		ArrayList<MainProjectContractHisResponse> nlist = Lists.newArrayList();
		for (ProjectContractHisResponse s : page.getList()) {
			MainProjectContractHisResponse ss = change(s, MainProjectContractHisResponse.class);
			nlist.add(ss);
		}
		return new BaseResp<PageResponse<List<MainProjectContractHisResponse>>>(IntfzRs.SUCCESS, "",
				new PageResponse<List<MainProjectContractHisResponse>>(nlist, req.getPageNo(), page.getCount()));
	}

	/**
	 * 项目实施情况-分页查询
	 */
	@RequestMapping(value = "projectImplyStatusList")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "项目实施情况-分页查询")
	BaseResp<PageResponse<List<ProjectImplyStatusResp>>> projectImplyStatusList(
			@RequestBody ProjectImplyStatusReq req,
			@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) {
		WebUtils.initPre(request, response);

		PagedQueryProjectImplyStatusParams params = change(req, PagedQueryProjectImplyStatusParams.class);
		//默认展示当前月份一个月的数据
		Calendar day = Calendar.getInstance();
		day.add(Calendar.MONTH, -1);
		day.set(Calendar.DAY_OF_MONTH,1);
		if(req.getBeginTime() == 0){
			params.setBeginTime(day.getTime());
		}else {
			params.setBeginTime(new Date(req.getBeginTime()));
		}
		if(req.getBeginTime() == 0) {
			params.setEndTime(new Date());
		}else {
			params.setEndTime(new Date(req.getEndTime()));
		}

		PagerResponse<PagedQueryProjectImplyStatusResult> page = intfzWebProjectInfoService.pagedQueryProjectImplyStatus(params);
		List<ProjectImplyStatusResp> list = new LinkedList<>();
		for(PagedQueryProjectImplyStatusResult result:page.getList()){
			ProjectImplyStatusResp resp = new ProjectImplyStatusResp();
			resp.setDate(result.getDate().getTime());
			resp.setReporter(result.getReporter());
			resp.setNodeName(result.getNodeName());
			resp.setNodeAddress(result.getNodeAddress());
			resp.setAnomalyDescription(result.getAnomalyDescription());

			list.add(resp);
		}
		return new BaseResp<>(IntfzRs.SUCCESS, "ok",
				new PageResponse<>(list, req.getPageNo(), page.getCount()));
	}

	/**
	 * 导出项目实施情况
	 */
	@RequestMapping(value = "exportProjectImplyStatus")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "导出项目实施情况excel")
	public BaseResp<String> exportProjectImplyStatus(
			@RequestBody ProjectImplyStatusExportReq req,
			@ApiParam(value = "登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) {
		WebUtils.initPre(request, response);
//		User user = UserUtils.getUser(sessionid);

		String fileName;
		String url;
		DateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		fileName = "项目实施情况报表" + sdf.format(new Date()) + ".xls";

		QueryProjectImplyStatusListParams params = new QueryProjectImplyStatusListParams();
		params.setProjectId(req.getProjectId());
		//默认展示当前月份一个月的数据
		Calendar firstDayOfMonth =Calendar.getInstance();//获取当前日期
		firstDayOfMonth.add(Calendar.MONTH, -1);
		firstDayOfMonth.set(Calendar.DAY_OF_MONTH,1);
		if(req.getBeginTime() == 0){
			params.setBeginTime(firstDayOfMonth.getTime());
		}else{
			params.setBeginTime(new Date(req.getBeginTime()));
		}
		if(req.getEndTime() == 0){
			params.setBeginTime(new Date());
		}else {
			params.setEndTime(new Date(req.getEndTime()));
		}

		//查询数据
		List<PagedQueryProjectImplyStatusResult> list = intfzWebProjectInfoService.queryProjectImplyStatusList(params);

		//导出并上传文件至服务器
		url = new IntfzProjectImplyStatusExportService().exportProjectImplyStatus(list, fileName); //获取附件url
		String downLoadUrl = intfzCommonService.setFileDownLoadUrl(url, fileName);
		return new BaseResp<>(IntfzRs.SUCCESS, "", downLoadUrl);
	}


	/**
	 * 项目管理-批量移动项目
	 * @param req
	 * @param sessionid
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "projectBatchMove")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "项目管理-批量移动项目")
	public BaseResp<String> projectBatchMove(
			@RequestBody MainProjectBatchMoveReq req,
			@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		User user = UserUtils.getUser(sessionid);//获取当前申请人信息
		try {
			ProjectBatchMoveReq change = change(req,ProjectBatchMoveReq.class);
			change.setUpdateBy(user.getId());
			intfzWebProjectInfoService.projectBatchMove(change);
		} catch (ServiceException e) {
			e.printStackTrace();
			return new BaseResp<String>(IntfzRs.ERROR,e.getMessage(),"");
		}
		return new BaseResp<String>(IntfzRs.SUCCESS,"操作成功！","");

	}


}

