package sijibao.oa.jeesite.modules.intfz.web.intfzweb;

import java.util.Date;
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
import com.sijibao.oa.common.persistence.Page;
import com.sijibao.oa.common.utils.DateUtils;
import com.sijibao.oa.common.utils.WebUtils;
import com.sijibao.oa.common.web.BaseController;
import com.sijibao.oa.modules.flow.entity.ContractOverdue;
import com.sijibao.oa.modules.intfz.bean.BaseResp;
import com.sijibao.oa.modules.intfz.enums.IntfzRs;
import com.sijibao.oa.modules.intfz.request.report.*;
import com.sijibao.oa.modules.intfz.response.common.PageResponse;
import com.sijibao.oa.modules.intfz.response.employeestatus.EmployeeStatusrecordResponse;
import com.sijibao.oa.modules.intfz.response.report.*;
import com.sijibao.oa.modules.intfz.service.common.IntfzCommonService;
import com.sijibao.oa.modules.intfz.service.employeestatus.IntfzEmployeeStatusMonthReportService;
import com.sijibao.oa.modules.intfz.service.employeestatus.IntfzEmployeeStatusrecordService;
import com.sijibao.oa.modules.intfz.service.report.*;
import com.sijibao.oa.modules.oa.entity.EmployeeStatusrecord;
import com.sijibao.oa.modules.sys.entity.User;
import com.sijibao.oa.modules.sys.utils.UserUtils;
import com.sijibao.oa.office.api.IntfzReportService;
import com.sijibao.oa.office.api.request.report.ApprovalSituationReq;
import com.sijibao.oa.office.api.request.report.FlowSubmitSituationReq;
import com.sijibao.oa.office.api.response.report.ApprovalSituationResponse;
import com.sijibao.oa.office.api.response.report.FlowSubmitSituationResponse;
import com.sijibao.oa.sys.api.IntfzWebUsageService;
import com.sijibao.oa.sys.api.request.usage.AddUsageParam;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * Web端报表服务
 * @author xby
 */
@Controller
@RequestMapping(value = "${frontPath}/webReport")
@Api(value="WEB报表服务管理",tags="WEB报表服务管理")
public class IntfzWebReportController extends BaseController{
	
	@Autowired
	private IntfzSubjectCostReportService intfzSubjectCostReportService;
	@Autowired
	private IntfzCommonService intfzCommonService;
	@Autowired
	private IntfzEmployeeReportService intfzEmployeeReportService;
	@Autowired
	private IntfzProjectReportService intfzProjectReportService;
	@Autowired
	private IntfzOfficeCostReportService intfzOfficeCostReportService;
	@Autowired
	private IntfzProjectSubjectCostReportService intfzProjectSubjectCostReportService;
	@Autowired
	private IntfzEmployeeStatusReportService intfzEmployeeStatusReportService;
	@Autowired
	private IntfzEmployeeStatusrecordService intfzEmployeeStatusrecordService;
	@Autowired
	private IntfzEmployeeStatusMonthReportService intfzEmployeeStatusMonthReportService;

	@Autowired
	private IntfzContractOverdueService intfzContractOverdueService;
	@Autowired
	private IntfzWebUsageService intfzWebUsageService;
	@Autowired
	private IntfzReportService intfzReportService ;
	@Autowired
	private IntfzWebReportService intfzWebReportService;
	/**
	 * 科目费用报表查询
	 * @param req
	 * @param sessionid
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "querySubjectCostReportInfo")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "WEB报表管理-科目费用报表查询")
	public BaseResp<SubjectListReportResponse> querySubjectCostReportInfo(@RequestBody SubjectCostReportRequest req,
																		  @ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
																		  HttpServletRequest request, HttpServletResponse response) throws Exception{
		Page<SubjectCostReportResponse> page = new Page<SubjectCostReportResponse>();
		
		if(req.getPageNo()==0 || req.getPageSize()==0){
			return new BaseResp<SubjectListReportResponse>(IntfzRs.ERROR, "分页参数不能为空！", null);
		}
		//查询科目费用报表信息
		page = intfzSubjectCostReportService.querySubjectCostReportInfo(page,req);
		
		//查询科目费用列表月份汇总金额
		ReportMonthSumResponse reportMonthSumResponse = new ReportMonthSumResponse();
		if(req.getPageNo() == 1){
			reportMonthSumResponse = intfzSubjectCostReportService.querySubjectMonthSum(req);
		}

		return new BaseResp<SubjectListReportResponse>(IntfzRs.SUCCESS, "ok", new SubjectListReportResponse(
				new PageResponse<List<SubjectCostReportResponse>>(
						page.getList(), page.getPageNo(), page.getCount()),
				reportMonthSumResponse, StringUtils.isNotBlank(req.getYear())?req.getYear():DateUtils.getYear()));
	}
	
	/**
	 * 科目费用报表导出
	 * @param req
	 * @param sessionid
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "querySubjectCostReportInfoExport")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "WEB报表管理-科目费用报表导出excel")
	public BaseResp<String> querySubjectCostReportInfoExport(@RequestBody SubjectCostReportRequest req,
															 @ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
															 HttpServletRequest request, HttpServletResponse response) throws Exception{
		String fileName = System.currentTimeMillis() +"科目费用报表.xls";
		String url = intfzSubjectCostReportService.exportSubjectCostReport(req,fileName); //获取附件url
		String downLoadUrl = intfzCommonService.setFileDownLoadUrl(url, fileName);
		return new BaseResp<String>(IntfzRs.SUCCESS,"",downLoadUrl);
	}
	
	/**
	 * 项目科目明细费用报表查询
	 * @param req
	 * @param sessionid
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "queryProjectSubjectCostReportInfo")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "WEB报表管理-项目科目明细费用报表查询")
	public BaseResp<SubjectListReportResponse> queryProjectSubjectCostReportInfo(@RequestBody ProjectSubjectCostReportRequest req,
																				 @ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
																				 HttpServletRequest request, HttpServletResponse response) throws Exception{
		Page<SubjectCostReportResponse> page = new Page<SubjectCostReportResponse>();
		
		if(req.getPageNo()==0 || req.getPageSize()==0){
			return new BaseResp<SubjectListReportResponse>(IntfzRs.ERROR, "分页参数不能为空！", null);
		}
		
		if(StringUtils.isBlank(req.getProjectId())){
			return new BaseResp<SubjectListReportResponse>(IntfzRs.ERROR, "项目ID不能为空！", null);
		}
		
		page = intfzProjectSubjectCostReportService.queryProjectSubjectCostReportInfo(page,req); //查询科目费用报表信息
		
		//查询项目科目费用列表月份汇总金额
		ReportMonthSumResponse reportMonthSumResponse = new ReportMonthSumResponse();
		if(req.getPageNo() == 1){
			reportMonthSumResponse = intfzProjectSubjectCostReportService.querySubjectMonthSum(req);
		}
		return new BaseResp<SubjectListReportResponse>(IntfzRs.SUCCESS, "ok", new SubjectListReportResponse(
				new PageResponse<List<SubjectCostReportResponse>>(
						page.getList(), page.getPageNo(), page.getCount()),
				reportMonthSumResponse, StringUtils.isNotBlank(req.getYear())?req.getYear():DateUtils.getYear()));
	}
	
	/**
	 * 项目科目费用报表导出
	 * @param req
	 * @param sessionid
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "queryProjectSubjectCostReportInfoExport")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "WEB报表管理-项目科目费用报表导出excel")
	public BaseResp<String> queryProjectSubjectCostReportInfoExport(@RequestBody ProjectSubjectCostReportRequest req,
																	@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
																	HttpServletRequest request, HttpServletResponse response) throws Exception{
		if(StringUtils.isBlank(req.getProjectId())){
			return new BaseResp<String>(IntfzRs.ERROR, "项目ID不能为空！", null);
		}
		String fileName = System.currentTimeMillis() +"科目费用报表.xls";
		String url = intfzProjectSubjectCostReportService.exportProjectSubjectCostReport(req,fileName); //获取附件url
		String downLoadUrl = intfzCommonService.setFileDownLoadUrl(url, fileName);
		return new BaseResp<String>(IntfzRs.SUCCESS,"",downLoadUrl);
	}
	
	/**
	 * 员工费用统计
	 * @param req
	 * @param sessionid
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "queryEmployeeReport")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "WEB报表管理-员工费用统计查询")
	public BaseResp<EmployeeReportListResponse> queryEmployeeReport(
			@RequestBody EmployeeReportRequest req,
			@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		Page<EmployeeReportResponse> page = new Page<EmployeeReportResponse>();
		
		if(req.getPageNo()==0 || req.getPageSize()==0){
			return new BaseResp<EmployeeReportListResponse>(IntfzRs.ERROR, "分页参数不能为空！",null);
		}
		
		page = intfzEmployeeReportService.queryEmployeeReport(page,req); //查询员工信息
		//查询员工费用列表月份汇总金额
		ReportMonthSumResponse reportMonthSumResponse = new ReportMonthSumResponse();
		if(req.getPageNo() == 1){
			reportMonthSumResponse = intfzEmployeeReportService.querySubjectMonthSum(req);
		}else{
			reportMonthSumResponse = null;
		}
				return new BaseResp<EmployeeReportListResponse>(IntfzRs.SUCCESS, "ok", new EmployeeReportListResponse(
						new PageResponse<List<EmployeeReportResponse>>(page.getList(), page.getPageNo(), page.getCount()),reportMonthSumResponse));
	}
	/**
	 * WEB报表管理-员工费用报表导出excel
	 * @param req
	 * @param sessionid
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "queryEmployeeReportExport")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "WEB报表管理-员工费用报表导出excel")
	public BaseResp<String> queryEmployeeReportExport(@RequestBody EmployeeReportRequest req,
													  @ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
													  HttpServletRequest request, HttpServletResponse response) throws Exception{
		String fileName = System.currentTimeMillis() +"员工费用报表.xls";
		String url = intfzEmployeeReportService.exportEmployeeReport(req,fileName); //获取附件url
		String downLoadUrl = intfzCommonService.setFileDownLoadUrl(url, fileName);
		return new BaseResp<String>(IntfzRs.SUCCESS,"",downLoadUrl);
	}
	
	/**
	 * 项目报销列表
	 * @param req
	 * @param sessionid
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "queryProjectReport")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "WEB报表管理-项目费用统计查询")
	public BaseResp<ProjectReportListResponse> queryProjectReport(
			@RequestBody ProjectReportRequest req,
			@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		Page<ProjectReportResponse> page = new Page<ProjectReportResponse>();
		
		if(req.getPageNo()==0 || req.getPageSize()==0){
			return new BaseResp<ProjectReportListResponse>(IntfzRs.ERROR, "分页参数不能为空！", null);
		}
		
		page = intfzProjectReportService.queryProjectReport(page,req); //查询员工信息
		//查询项目费用列表月份汇总金额
		ReportMonthSumResponse reportMonthSumResponse = new ReportMonthSumResponse();
		if(req.getPageNo() == 1){
			reportMonthSumResponse = intfzProjectReportService.querySubjectMonthSum(req);
		}else{
			reportMonthSumResponse = null;
		}
		return new BaseResp<ProjectReportListResponse>(IntfzRs.SUCCESS, "ok", new ProjectReportListResponse(
				new PageResponse<List<ProjectReportResponse>>(page.getList(), page.getPageNo(), page.getCount()),reportMonthSumResponse));
	}
	/**
	 * WEB报表管理-项目报表导出excel
	 * @param req
	 * @param sessionid
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "exportProjectReport")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "WEB报表管理-项目报表导出excel")
	public BaseResp<String> queryProjectReportExport(@RequestBody ProjectReportRequest req,
													 @ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
													 HttpServletRequest request, HttpServletResponse response) throws Exception{
		String fileName = System.currentTimeMillis() +"员工费用报表.xls";
		String url = intfzProjectReportService.exportProjectReport(req,fileName); //获取附件url
		String downLoadUrl = intfzCommonService.setFileDownLoadUrl(url, fileName);
		logger.info("====IntfzWebReportController queryProjectReportExport=====}", downLoadUrl);
		return new BaseResp<String>(IntfzRs.SUCCESS,"",downLoadUrl);
	}
	
	/**
	 * 部门费用报表查询
	 * @param req
	 * @param sessionid
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "queryOfficeCostReportInfo")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "WEB报表管理-部门费用报表查询")
	public BaseResp<OfficeListReportResponse> queryOfficeCostReportInfo(@RequestBody OfficeCostReportRequest req,
																		@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
																		HttpServletRequest request, HttpServletResponse response) throws Exception{
		if(req.getPageNo()==0 || req.getPageSize()==0){
			return new BaseResp<OfficeListReportResponse>(IntfzRs.ERROR, "分页参数不能为空！", null);
		}
		
		Page<OfficeCostReportResponse> page = new Page<OfficeCostReportResponse>();
		
		page = intfzOfficeCostReportService.queryOfficeCostReportInfo(page, req);
		
		ReportMonthSumResponse reportMonthSumResponse = new ReportMonthSumResponse();
		if(req.getPageNo() == 1){
			reportMonthSumResponse = intfzOfficeCostReportService.queryOfficeMonthAmountSum(req);
		}

		return new BaseResp<OfficeListReportResponse>(IntfzRs.SUCCESS, "ok", new OfficeListReportResponse(
				new PageResponse<List<OfficeCostReportResponse>>(page.getList(), page.getPageNo(), page.getCount()),reportMonthSumResponse));
	}
	
	/**
	 * 部门费用报表查询导出
	 * @param req
	 * @param sessionid
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "queryOfficeCostReportInfoExport")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "WEB报表管理-部门费用报表导出excel")
	public BaseResp<String> queryOfficeCostReportInfoExport(@RequestBody OfficeCostReportRequest req,
															@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
															HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		String fileName = System.currentTimeMillis() +"部门费用报表.xls";
		String url = intfzOfficeCostReportService.exportOfficeCostReport(req,fileName); //获取附件url
		String downLoadUrl = intfzCommonService.setFileDownLoadUrl(url, fileName);

		return new BaseResp<String>(IntfzRs.SUCCESS,"",downLoadUrl);
	}
	
	/**
	 * 员工状态日报表查询
	 * @param req
	 * @param sessionid
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "queryEmpStatusDayReportInfo")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "WEB报表管理-员工状态日报表查询")
	public BaseResp<PageResponse<List<EmpStatusDayReportResponse>>> queryEmpStatusDayReportInfo(
			@RequestBody EmpStatusDayReportRequest req,
			@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		if(req.getPageNo()==0 || req.getPageSize()==0){
			return new BaseResp<PageResponse<List<EmpStatusDayReportResponse>>>(IntfzRs.ERROR, "分页参数不能为空！", null);
		}
		
		if(req.getDateTime()==0){
			return new BaseResp<PageResponse<List<EmpStatusDayReportResponse>>>(IntfzRs.ERROR, "查询日期不能为空！", null);
		}
		
		User user = UserUtils.getUser(sessionid); //获取当前申请人信息
		
		Page<EmpStatusDayReportResponse> page = intfzEmployeeStatusReportService.queryEmpStatusDayReportInfo(req, user);

		return new BaseResp<PageResponse<List<EmpStatusDayReportResponse>>>(IntfzRs.SUCCESS, "ok",
				new PageResponse<List<EmpStatusDayReportResponse>>(page.getList(), page.getPageNo(), page.getCount()));
	}
	
	/**
	 * 员工日报表导出
	 * @param req
	 * @param sessionid
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "queryEmpStatusDayReportInfoExport")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "WEB报表管理-员工状态日报表导出excel")
	public BaseResp<String> queryEmpStatusDayReportInfoExport(@RequestBody EmpStatusDayReportRequest req,
															  @ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
															  HttpServletRequest request, HttpServletResponse response) throws Exception{

		if(req.getDateTime()==0){
			return new BaseResp<String>(IntfzRs.ERROR, "查询日期不能为空！", "");
		}
		
		String fileName = DateUtils.formatDateFromUnix(req.getDateTime(), DateUtils.YYYY_MM_DD) +"员工状态日报表.xls";
		String url = intfzEmployeeStatusReportService.queryEmpStatusDayReportInfoExport(req,fileName); //获取附件url
		String downLoadUrl = intfzCommonService.setFileDownLoadUrl(url, fileName);

		return new BaseResp<String>(IntfzRs.SUCCESS,"",downLoadUrl);
	} 
	
	/**
	 * 员工状态日报表明细查询
	 * @param req
	 * @param sessionid
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "queryEmpStatusDayReportDetailInfo")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "WEB报表管理-员工状态日报表明细查询")
	public BaseResp<PageResponse<List<EmployeeStatusrecordResponse>>> queryEmpStatusDayReportDetailInfo(@RequestBody EmpStatusDayReportRequest req,
																										@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
																										HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		if(StringUtils.isBlank(req.getUserId())){
			return new BaseResp<PageResponse<List<EmployeeStatusrecordResponse>>>(IntfzRs.ERROR, "员工ID不能为空！", null);
		}
		
		User user = UserUtils.get(req.getUserId());
		Page<EmployeeStatusrecordResponse> resultPage = intfzEmployeeStatusrecordService.findEmployeeStatusList(user,
				new Page<EmployeeStatusrecord>(req.getPageNo(), req.getPageSize()),req);

		
		return new BaseResp<PageResponse<List<EmployeeStatusrecordResponse>>>(IntfzRs.SUCCESS, "ok",
				new PageResponse<List<EmployeeStatusrecordResponse>>(resultPage.getList(), resultPage.getPageNo(),
						resultPage.getCount()));
	}
	
	/**
	 * 员工月报表查询
	 * @param req
	 * @param sessionid
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "queryEmpStatusMonthReportInfo")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "WEB报表管理-员工状态月报表查询")
	public BaseResp<PageResponse<List<EmpStatusMonthReportResponse>>> queryEmpStatusMonthReportInfo(@RequestBody EmpStatusMonthReportRequest req,
																									@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
																									HttpServletRequest request, HttpServletResponse response) throws Exception{
		if(req.getPageNo()==0 || req.getPageSize()==0){
			return new BaseResp<PageResponse<List<EmpStatusMonthReportResponse>>>(IntfzRs.ERROR, "分页参数不能为空！", null);
		}
		
		if(req.getDateTime()==0){
			return new BaseResp<PageResponse<List<EmpStatusMonthReportResponse>>>(IntfzRs.ERROR, "查询日期不能为空！", null);
		}
		
		Page<EmpStatusMonthReportResponse> page = intfzEmployeeStatusMonthReportService.queryEmpStatusMonthReport(req);

		return new BaseResp<PageResponse<List<EmpStatusMonthReportResponse>>>(IntfzRs.SUCCESS, "ok",
				new PageResponse<List<EmpStatusMonthReportResponse>>(page.getList(), page.getPageNo(), page.getCount()));
	}
	
	/**
	 * 员工月报表明细查询
	 * @param req
	 * @param sessionid
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "queryEmpStatusMonthDetailReportInfo")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "WEB报表管理-员工状态月报表明细查询")
	public BaseResp<PageResponse<List<EmpStatusMonthDetailReportResponse>>> queryEmpStatusMonthDetailReportInfo(@RequestBody EmpStatusMonthReportRequest req,
																												@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
																												HttpServletRequest request, HttpServletResponse response) throws Exception{
		if(req.getPageNo()==0 || req.getPageSize()==0){
			return new BaseResp<PageResponse<List<EmpStatusMonthDetailReportResponse>>>(IntfzRs.ERROR, "分页参数不能为空！", null);
		}
		
		if(req.getDateTime()==0){
			return new BaseResp<PageResponse<List<EmpStatusMonthDetailReportResponse>>>(IntfzRs.ERROR, "查询日期不能为空！", null);
		}
		
		Page<EmpStatusMonthDetailReportResponse> page = intfzEmployeeStatusMonthReportService.queryEmpStatusMonthDetailReport(req);

		return new BaseResp<PageResponse<List<EmpStatusMonthDetailReportResponse>>>(IntfzRs.SUCCESS, "ok",
				new PageResponse<List<EmpStatusMonthDetailReportResponse>>(page.getList(), page.getPageNo(), page.getCount()));
	}
	
	/**
	 * 员工月报表导出
	 * @param req
	 * @param sessionid
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "empStatusMonthReportExport")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "WEB报表管理-员工状态月报表导出")
	public BaseResp<String> empStatusMonthReportExport(@RequestBody EmpStatusMonthReportRequest req,
													   @ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
													   HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		if(req.getDateTime()==0){
			return new BaseResp<String>(IntfzRs.ERROR, "查询日期不能为空！", "");
		}
		
		String fileName = DateUtils.formatDateFromUnix(req.getDateTime(), DateUtils.YYYY_MM) +"员工状态月报表.xls";
		String url = intfzEmployeeStatusMonthReportService.queryEmpStatusMonthReportInfoExport(req, fileName); //获取附件url
		String downLoadUrl = intfzCommonService.setFileDownLoadUrl(url, fileName);

		return new BaseResp<String>(IntfzRs.SUCCESS,"",downLoadUrl);
	}


	/**
	 * 合同逾期报表查询
	 * @param req
	 * @param sessionid
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "contractOverdueList")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "WEB报表管理-合同逾期报表查询")
	public BaseResp<PageResponse<List<ContractOverdue>>> contractOverdueList(@RequestBody ContractOverdueRequest req,
																			 @ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
																			 HttpServletRequest request, HttpServletResponse response) throws Exception{
//		Page<ContractOverdueResponse> page = new Page<ContractOverdueResponse>();

		if(req.getPageNo()==0 || req.getPageSize()==0){
			return new BaseResp<PageResponse<List<ContractOverdue>>>(IntfzRs.ERROR, "分页参数不能为空！",
					new PageResponse<List<ContractOverdue>>(null, 0, 0));
		}
		//查询合同逾期报表信息
		Page<ContractOverdue> page = intfzContractOverdueService.contractOverdueList(req);

		User user = UserUtils.getUser(sessionid);
		logger.info("功能使用情况表插入数据");
		AddUsageParam addUsageParam = new AddUsageParam();
		addUsageParam.setFunctionCode("htyq");
		addUsageParam.setFunctionName("合同逾期查询");
		addUsageParam.setTerminalCode("1");
		addUsageParam.setTerminalName("web");
		addUsageParam.setUserId(user.getId());
		addUsageParam.setUserName(user.getName());
		addUsageParam.setCreateBy(user.getId());
		addUsageParam.setDate(new Date());
		intfzWebUsageService.addUsage(addUsageParam);

		return new BaseResp<PageResponse<List<ContractOverdue>>>(IntfzRs.SUCCESS, "ok",
				new PageResponse<List<ContractOverdue>>(page.getList(), page.getPageNo(), page.getCount()));
	}


	/**
	 * 合同逾期报表添加
	 * @param req
	 * @param sessionid
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "saveContractOverdue")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "WEB报表管理-合同逾期报表添加")
	public BaseResp<String> saveContractOverdue(@RequestBody ContractOverdueRequest req,
												@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
												HttpServletRequest request, HttpServletResponse response) throws Exception{

		intfzContractOverdueService.saveContractOverdue("11613d366fcc43789bfd353d751f11b2","13026338335");

		return new BaseResp<String>(IntfzRs.SUCCESS, "添加合同逾期报表成功", "");
	}


	/**
	 * 合同逾期报表导出
	 * @param req
	 * @param sessionid
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "contractOverdueExport")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "WEB报表管理-合同逾期报表导出")
	public BaseResp<String> contractOverdueExport(@RequestBody MainFlowSubmitSituationReq req,
												  @ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
												  HttpServletRequest request, HttpServletResponse response) throws Exception{

		String fileName = DateUtils.formatDateFromUnix(new Date().getTime(), DateUtils.YYYY_MM_DD) +"合同逾期报表.xls";
		String url = intfzContractOverdueService.contractOverdueExport(fileName); //获取附件url
		String downLoadUrl = intfzCommonService.setFileDownLoadUrl(url, fileName);

		return new BaseResp<String>(IntfzRs.SUCCESS,"",downLoadUrl);
	}

	/**
	 * WEB报表管理-单据提交情况
	 * @param req
	 * @param sessionid
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "flowSubmitSituation")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "WEB报表管理-单据提交情况")
	public BaseResp<List<MainFlowSubmitSituationResponse>> flowSubmitSituation(@RequestBody MainFlowSubmitSituationReq req,
																			   @ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
																			   HttpServletRequest request, HttpServletResponse response) throws Exception{
		FlowSubmitSituationReq stion = change(req, FlowSubmitSituationReq.class);
		List<FlowSubmitSituationResponse> list = intfzReportService.flowSubmitSituation(stion);
		List<MainFlowSubmitSituationResponse> resp = Lists.newArrayList();
		for(FlowSubmitSituationResponse res :list){
			resp.add(change(res,MainFlowSubmitSituationResponse.class));
		}
		return new BaseResp<List<MainFlowSubmitSituationResponse>>(IntfzRs.SUCCESS, "", resp);
	}

	@RequestMapping(value = "flowSubmitSituationExport")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "WEB报表管理-单据提交情况导出")
	public BaseResp<String> flowSubmitSituationExport(@RequestBody MainFlowSubmitSituationReq req,
													  @ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
													  HttpServletRequest request, HttpServletResponse response) throws Exception{
		String fileName = DateUtils.formatDateFromUnix(new Date().getTime(), DateUtils.YYYY_MM_DD) +"单据提交情况导出.xls";
		String url = intfzWebReportService.flowSubmitSituationExport(req,fileName); //获取附件url
		String downLoadUrl = intfzCommonService.setFileDownLoadUrl(url, fileName);

		return new BaseResp<String>(IntfzRs.SUCCESS,"",downLoadUrl);
	}

	/**
	 * WEB报表管理-单据审批情况
	 * @param req
	 * @param sessionid
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "approvalSituation")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "WEB报表管理-单据审批情况")
	public BaseResp<List<MainApprovalSituationResponse>> approvalSituation(@RequestBody MainApprovalSituationReq req,
																		   @ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
																		   HttpServletRequest request, HttpServletResponse response) throws Exception{
		ApprovalSituationReq stion = change(req, ApprovalSituationReq.class);
		List<ApprovalSituationResponse> list = intfzReportService.approvalSituation(stion);
		List<MainApprovalSituationResponse> resp = Lists.newArrayList();
		for(ApprovalSituationResponse res : list ){
			resp.add(change(res,MainApprovalSituationResponse.class));
		}
		return new BaseResp<List<MainApprovalSituationResponse>>(IntfzRs.SUCCESS, "", resp);
	}

	/**
	 * WEB报表管理-单据审批情况导出
	 * @param req
	 * @param sessionid
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "approvalSituationExport")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "WEB报表管理-单据审批情况导出")
	public BaseResp<String> approvalSituationExport(@RequestBody MainApprovalSituationReq req,
													@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
													HttpServletRequest request, HttpServletResponse response) throws Exception{


		String fileName = DateUtils.formatDateFromUnix(new Date().getTime(), DateUtils.YYYY_MM_DD) +"单据审批情况.xls";
		String url = intfzWebReportService.approvalSituationExport(req,fileName); //获取附件url
		String downLoadUrl = intfzCommonService.setFileDownLoadUrl(url, fileName);

		return new BaseResp<String>(IntfzRs.SUCCESS,"",downLoadUrl);
	}


}
