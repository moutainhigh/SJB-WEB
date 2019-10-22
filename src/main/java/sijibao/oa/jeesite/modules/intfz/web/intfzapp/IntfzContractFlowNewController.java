package sijibao.oa.jeesite.modules.intfz.web.intfzapp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.google.common.collect.Lists;
import com.sijibao.base.common.api.response.PagerResponse;
import com.sijibao.oa.activiti.api.exception.ServiceException;
import com.sijibao.oa.activiti.api.request.contract.*;
import com.sijibao.oa.activiti.api.response.contract.ContractFlowDetailNewResponse;
import com.sijibao.oa.activiti.api.response.contract.ContractFlowNewResponse;
import com.sijibao.oa.activiti.api.service.ContractFlowNewService;
import com.sijibao.oa.common.utils.SpringContextHolder;
import com.sijibao.oa.common.utils.WebUtils;
import com.sijibao.oa.common.web.BaseController;
import com.sijibao.oa.modules.intfz.bean.BaseResp;
import com.sijibao.oa.modules.intfz.enums.IntfzRs;
import com.sijibao.oa.modules.intfz.request.contractnew.*;
import com.sijibao.oa.modules.intfz.response.common.PageResponse;
import com.sijibao.oa.modules.intfz.response.contract.ContractFlowDetailResponse;
import com.sijibao.oa.modules.intfz.response.contract.MainContractHisResponse;
import com.sijibao.oa.modules.intfz.response.contractnew.*;
import com.sijibao.oa.modules.intfz.utils.CharChangeUtils;
import com.sijibao.oa.modules.intfz.utils.Constant;
import com.sijibao.oa.modules.intfz.validator.AppAuthority;
import com.sijibao.oa.modules.sys.entity.User;
import com.sijibao.oa.modules.sys.utils.UserUtils;
import com.sijibao.oa.office.api.IntfzContractConfigService;
import com.sijibao.oa.office.api.IntfzContractInfoBackService;
import com.sijibao.oa.office.api.IntfzWebContractHisService;
import com.sijibao.oa.office.api.request.contracthis.ContractConfigRequest;
import com.sijibao.oa.office.api.request.contracthis.ContractHisHandleReq;
import com.sijibao.oa.office.api.response.contracthis.ContractConfigResponse;
import com.sijibao.oa.office.api.response.contracthis.ContractHisResponse;
import com.sijibao.oa.office.api.response.contracthis.ContractPartyNameResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * APP合同审核相关服务
 * @author xuby
 */
@Controller
@RequestMapping(value = "wechat/contractFlowNew")
@Api(value="APP合同审核服务(New)",tags="APP合同审核服务(New)")
public class IntfzContractFlowNewController extends BaseController{
	
	private IntfzContractConfigService intfzContractConfigService;
	
	private IntfzContractInfoBackService intfzContractInfoBackService;

	private IntfzWebContractHisService intfzWebContractHisService;
	
	private ContractFlowNewService contractFlowNewService;
//	@Autowired
//	private OfficeService officeService;
	@ModelAttribute
	public void init(){
		if(intfzContractConfigService == null){
			intfzContractConfigService = SpringContextHolder.getBean("intfzContractConfigService");
		}
		if(intfzContractInfoBackService == null){
			intfzContractInfoBackService = SpringContextHolder.getBean("intfzContractInfoBackService");
		}
		if(intfzWebContractHisService == null){
			intfzWebContractHisService = SpringContextHolder.getBean("intfzWebContractHisService");
		}
		if(contractFlowNewService == null){
			contractFlowNewService = SpringContextHolder.getBean("contractFlowNewService");
		}
	}
	
	/**
	 * 合同审批，发起流程
	 * @param req
	 * @param sjboacert
	 * @param clientType
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "contractApplyNew")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "APP端合同申请-申请")
    @AppAuthority
	public BaseResp<String> contractApplyNew(
			@RequestBody ContractFlowNewMainRequest req,
			@ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
			@ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		User user = super.getCurrWxUser(sjboacert, clientType); //获取当前申请人信息
		User u = UserUtils.get(req.getContractLeaderId());
		CharChangeUtils.CharChange(req);//替换英文字符
		req.setProducSide("APP");
		if(!"1".equals(u.getUserStatus())){
			return new BaseResp<String>(IntfzRs.ERROR, "合同负责人(" + u.getName() + ")已离职，请重新选择后再提交。", "");
		}
		ContractFlowNewRequest cReq = change(req,ContractFlowNewRequest.class);
		
		List<ContractAttachmentNewRequest> attachList = new ArrayList<ContractAttachmentNewRequest>();
		if(req.getContractAttachmentList() != null && req.getContractAttachmentList().size() > 0){
			for(ContractAttachmentNewMainRequest m:req.getContractAttachmentList()){
				ContractAttachmentNewRequest r = change(m,ContractAttachmentNewRequest.class);
				attachList.add(r);
			}
			cReq.setContractAttachmentList(attachList);
		}
		try {
			contractFlowNewService.contractApplyService(cReq, user.getId());
		} catch (ServiceException e) {
			e.getStackTrace();
			return new BaseResp<String>(IntfzRs.ERROR,e.getMessage(),null);
		}
		return new BaseResp<String>(IntfzRs.SUCCESS, "申请发起成功", "");
	}
	
	/**
	 * 合同审批 同意/驳回
	 * @param response
	 * @throws Exception 
	 * @throws IOException
	 */
	@RequestMapping(value = "contractFlowNewCompleteTask")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "APP端合同申请-同意/驳回")
    @AppAuthority
	public BaseResp<String> completeTask(@RequestBody ContractFlowCompleteTaskNewMainReq req,
										 @ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
										 @ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
										 HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		CharChangeUtils.CharChange(req);//替换英文字符
		if (StringUtils.isBlank(req.getComment()) && "no".equals(req.getFlag())) {
			logger.error("=======IntfzContractFlowNewController contractFlowNewCompleteTask end=============请填写审核意见");
			return new BaseResp<String>(IntfzRs.ERROR, "请填写审核意见", "");
		}
		if (StringUtils.isBlank(req.getProcInsId())){
			logger.error("=======IntfzContractFlowNewController contractFlowNewCompleteTask end=============procInsId is not null");
			return new BaseResp<String>(IntfzRs.ERROR, "procInsId is not null", "");
		}
		if(StringUtils.isBlank(req.getContractFlowId())){
			logger.error("=======IntfzContractFlowNewController contractFlowNewCompleteTask end=============contractFlowId is not null");
			return new BaseResp<String>(IntfzRs.ERROR, "contractFlowId is not null", "");
		}
		
		User user = super.getCurrWxUser(sjboacert, clientType); //获取当前申请人信息
		
		ContractFlowCompleteTaskNewReq cReq = change(req,ContractFlowCompleteTaskNewReq.class);
		
		try {
			contractFlowNewService.completeTask(cReq, user.getId(),Constant.CLIENT_TYPE_APP);
		} catch (ServiceException e) {
			logger.error(e.getMessage());
            e.printStackTrace();
            return new BaseResp<String>(IntfzRs.ERROR,e.getMessage(),null);
		}

		return new BaseResp<String>(IntfzRs.SUCCESS, "审批成功", "");
	}
	
	/**
	 * 删除合同申请
	 * @param req
	 * @param sjboacert
	 * @param clientType
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "repealApply")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "APP端合同申请-删除")
    @AppAuthority
	public BaseResp<String> repealApply(@RequestBody ContractFlowHandleNewMainReq req,
										@ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
										@ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
										HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		
		if (StringUtils.isBlank(req.getContractFlowId())){
			logger.info("=======IntfzContractFlowNewController repealApply start=============contractFlowId is not null");
			return new BaseResp<String>(IntfzRs.ERROR, "contractFlowId is not null", "");
		}
		
		ContractFlowHandleNewReq cReq = change(req,ContractFlowHandleNewReq.class);
		
		try{
			contractFlowNewService.deleteContractFlowInfo(cReq); //删除合同申请
		} catch (ServiceException e) {
			e.getStackTrace();
			return new BaseResp<String>(IntfzRs.ERROR,e.getMessage(),null);
		}

		return new BaseResp<String>(IntfzRs.SUCCESS, "删除合同申请成功", "");
	}
	
	/**
	 * 合同申请流程撤销
	 * @param req
	 * @param sjboacert
	 * @param clientType
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "repealTask")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "APP端合同申请-流程收回")
    @AppAuthority
	public BaseResp<String> repealTask(@RequestBody ContractFlowRepealNewMainRequest req,
									   @ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
									   @ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
									   HttpServletRequest request, HttpServletResponse response){
		WebUtils.initPre(request, response);
		
		if(StringUtils.isBlank(req.getTaskId())){
			return new BaseResp<String>(IntfzRs.ERROR, "任务ID不能为空!", "");
		}
		if(StringUtils.isBlank(req.getProcInsId())){
			return new BaseResp<String>(IntfzRs.ERROR, "流程实例ID不能为空!", "");
		}
		//判断当前单据已经审批结束
		ContractFlowNewResponse c = contractFlowNewService.getByProcInsId(req.getProcInsId());
		if(Constant.expense_approve_end.equals(c.getContractFlowStatus())){
			return new BaseResp<String>(IntfzRs.ERROR, "当前单据已经审批结束，无需进行收回!", "");
		}
		
		User user = super.getCurrWxUser(sjboacert, clientType); //获取当前申请人信息
		
		ContractFlowRepealNewRequest cReq = change(req,ContractFlowRepealNewRequest.class);
		
		try{
			contractFlowNewService.repealTask(cReq, user.getId()); //任务撤销
		} catch (ServiceException e) {
			e.getStackTrace();
			return new BaseResp<String>(IntfzRs.ERROR,e.getMessage(),null);
		}

		return new BaseResp<String>(IntfzRs.SUCCESS, "流程收回成功!", "");
	}
	
	/**
	 * 合同管理详情展示
	 * @param req
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "contractFlowDetail")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "APP端合同申请-查询审批流程详情")
    @AppAuthority
	public BaseResp<ContractFlowDetailResponse> contractFlowDetail(
			@RequestBody ContractFlowHandleNewMainReq req,
			@ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
			@ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		
		if (StringUtils.isBlank(req.getContractFlowId())){
			logger.info("=======IntfzContractFlowNewController contractFlowDetail start=============contractFlowId is not null");
			return new BaseResp<ContractFlowDetailResponse>(IntfzRs.ERROR, "contractFlowId is not null", null);
		}
		
		User user = super.getCurrWxUser(sjboacert, clientType); //获取当前申请人信息
		
		ContractFlowHandleNewReq cReq = change(req,ContractFlowHandleNewReq.class);
		try{
			contractFlowNewService.updateReadStatus(cReq, user.getId());
			ContractFlowDetailNewResponse cResponse = contractFlowNewService.contractFlowDetail(cReq, user.getId());
			ContractFlowDetailResponse res = change(cResponse,ContractFlowDetailResponse.class);
			res.setContractFlowDetailInfoNewResponse(change(cResponse.getContractFlowDetailInfoNewResponse(),ContractFlowDetailInfoNewMainResponse.class));

			return new BaseResp<ContractFlowDetailResponse>(IntfzRs.SUCCESS, "ok", res);
		} catch (ServiceException e) {
			e.getStackTrace();
			return new BaseResp<ContractFlowDetailResponse>(IntfzRs.ERROR,e.getMessage(),null);
		}
	}
	
	/**
	 * 列表查询
	 * @param req
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "queryMyContractFlowList")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "APP端合同申请-查询列表")
    @AppAuthority
	public BaseResp<PageResponse<List<ContractFlowNewMainResponse>>> queryMyContractFlowList(
			@RequestBody ContractFlowListNewMainReq req,
			@ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
			@ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		User user = super.getCurrWxUser(sjboacert, clientType); //获取当前申请人信息
		
		ContractFlowListNewReq cReq = change(req,ContractFlowListNewReq.class);
		cReq.setUserId(user.getId());
		
		if(Constant.expense_approve.equals(String.valueOf(req.getContractFlowStatus()))){ //运行中搜索包括审批中和被驳回
			cReq.setContractFlowStatusIn("2,3");
			cReq.setContractFlowStatus("");
		}else if(Constant.expense_approve_end.equals(String.valueOf(req.getContractFlowStatus()))){
			cReq.setContractFlowStatusIn("1,0");
			cReq.setContractFlowStatus("");
		}else{
			cReq.setContractFlowStatus("4");
		}
		
		PagerResponse<ContractFlowNewResponse> resultPage = contractFlowNewService.findPage(cReq);
		List<ContractFlowNewMainResponse> resultList = new ArrayList<ContractFlowNewMainResponse>();
		if(resultPage.getList() != null && resultPage.getList().size() > 0){
			for(ContractFlowNewResponse r:resultPage.getList()){
				ContractFlowNewMainResponse rm = change(r,ContractFlowNewMainResponse.class);
				resultList.add(rm);
			}
		}

		return new BaseResp<PageResponse<List<ContractFlowNewMainResponse>>>(IntfzRs.SUCCESS, "ok", 
				new PageResponse<List<ContractFlowNewMainResponse>>(resultList, req.getPageNo(), resultPage.getCount()));
	}
	
	/**
	 * 查询合同名称下拉列表
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "queryContractTempletList")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "APP端合同申请-查询合同名称下拉列表")
    @AppAuthority
	public BaseResp<List<ContractTempletListResponse>> queryContractTempletList(
			@RequestBody ContractConfigMainRequest req,
			@ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
			@ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		
		ContractConfigRequest creq = change(req,ContractConfigRequest.class);
		creq.setDelFlag("0");
		List<ContractConfigResponse> resultList = intfzContractConfigService.fingList(creq); //查询合同配置集合
		
		List<ContractTempletListResponse> resultResponse = new ArrayList<ContractTempletListResponse>();
		for(ContractConfigResponse res:resultList){
			ContractTempletListResponse contractTempletListResponse = change(res,ContractTempletListResponse.class);
			contractTempletListResponse.setCode(res.getContractCode());
			resultResponse.add(contractTempletListResponse);
		}

		return new BaseResp<List<ContractTempletListResponse>>(IntfzRs.SUCCESS,"ok",resultResponse);
	}
	
	/**
	 * 合同配置信息获取
	 * @param req
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "queryContractConfig")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "APP端合同申请-查询合同配置信息")
    @AppAuthority
	public BaseResp<ContractConfigMainResponse> queryContractConfig(
			@RequestBody ContractConfigMainRequest req,
			@ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
			@ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		
		if(StringUtils.isBlank(req.getId())){
			return new BaseResp<ContractConfigMainResponse>(IntfzRs.ERROR,"合同模版id不能为空!",null);
		}
		
		ContractConfigResponse contractConfigResponse = intfzContractConfigService.get(req.getId());
		ContractConfigMainResponse mResponse = change(contractConfigResponse,ContractConfigMainResponse.class);
		return new BaseResp<ContractConfigMainResponse>(IntfzRs.SUCCESS,"ok",mResponse);
	}
	
	/**
	 * 主合同列表查询
	 * @param req
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "queryMainContractList")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "APP端合同申请-主合同查询")
    @AppAuthority
	public BaseResp<PageResponse<List<MainContractHisResponse>>> queryMainContractList(
			@RequestBody ContractFlowListNewMainReq req,
			@ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
			@ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		User user = super.getCurrWxUser(sjboacert, clientType); //获取当前申请人信息
		
		ContractHisHandleReq hisReq = new ContractHisHandleReq();
		hisReq.setAppFaint(req.getQueryText());
		hisReq.setPageNo(req.getPageNo());
		hisReq.setPageSize(req.getPageSize());
		hisReq.setUserId(user.getId());
		hisReq.setRemoveDelFlag("1");
		PagerResponse<ContractHisResponse> result = intfzWebContractHisService.queryMainContractList(hisReq);
//		PagerResponse<ContractHisResponse> result = intfzWebContractHisService.contractHisList(hisReq);
		ArrayList<MainContractHisResponse> list = Lists.newArrayList();
		for (ContractHisResponse s : result.getList()) {
			MainContractHisResponse ss = change(s, MainContractHisResponse.class);
			list.add(ss);
		}
		return new BaseResp<PageResponse<List<MainContractHisResponse>>>(IntfzRs.SUCCESS, "ok", 
				new PageResponse<List<MainContractHisResponse>>(list,req.getPageNo(),result.getCount()));
	}
	
	/**
	 * 合同方合同公司信息查询
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "queryContractCompanyList")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "APP端合同申请-查询合同公司信息列表")
    @AppAuthority
	public BaseResp<List<ContractCompanyListResponse>> queryContractCompanyList(
			@ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
			@ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		

		return new BaseResp<List<ContractCompanyListResponse>>(IntfzRs.SUCCESS, "ok", null);
	}

	
	/**
	 * 关联主合同（从公司合同信息中获取）
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
/*	@RequestMapping(value = "queryMainContract")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "APP端合同申请-关联主合同（从公司合同信息中获取）")
	@AppAuthority
	public BaseResp<PageResponse<List<MainContractInfoBackResponse>>> queryMainContract(
			@RequestBody MainContractInfoBackReq req,
			@ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert, 
			@ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		logger.info("===IntfzContractFlowNewController queryMainContract===",req.toString());
		WebUtils.initPre(request, response);
		ContractInfoBackReq change = change(req, ContractInfoBackReq.class);
		change.setPageNo(req.getPageNo());
		change.setPageSize(req.getPageSize());
		PagerResponse<ContractInfoBackResponse> findPage = contractInfoBackService.findPage(change);
		ArrayList<MainContractInfoBackResponse> list = Lists.newArrayList();
		for (ContractInfoBackResponse ss : findPage.getList()) {
			MainContractInfoBackResponse s = change(ss, MainContractInfoBackResponse.class);
			list.add(s);
		}
		
		logger.info("=======IntfzContractFlowNewController queryMainContract end=============");
		return new BaseResp<PageResponse<List<MainContractInfoBackResponse>>>(IntfzRs.SUCCESS, "ok", 
				new PageResponse<List<MainContractInfoBackResponse>>(list, req.getPageNo(), findPage.getCount()));
	}*/
	
	/**
	 * 从合同公司信息获取合同方信息
	 * @param req
	 * @param sjboacert
	 * @param clientType
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "queryPartyNameForContractBack")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "APP端合同申请-从合同公司信息获取合同方信息")
    @AppAuthority
	public BaseResp<List<ContractPartyNameResponse>> queryPartyNameForContractBack(@RequestBody ContractInfoPartyNameReq req,
																				   @ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
																				   @ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
																				   HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		if(StringUtils.isBlank(req.getMemberType())){
			return new BaseResp<List<ContractPartyNameResponse>>(IntfzRs.ERROR,"合同方类型不能为空!",null);
		}
		
		List<ContractPartyNameResponse> resultList = intfzContractConfigService.getPartyNameForContractBack(req.getMemberType(), req.getMemberName());

		return new BaseResp<List<ContractPartyNameResponse>>(IntfzRs.SUCCESS,"ok",resultList);
	}
	
	/**
	 * 从客户信息中获取合同方信息
	 * @param req
	 * @param sjboacert
	 * @param clientType
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "queryPartyNameForCustInfo")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "APP端合同申请-从客户信息获取合同方信息")
    @AppAuthority
	public BaseResp<List<ContractPartyNameResponse>> queryPartyNameForCustInfo(@RequestBody ContractInfoPartyNameReq req,
																			   @ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
																			   @ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
																			   HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		if(StringUtils.isBlank(req.getMemberType())){
			return new BaseResp<List<ContractPartyNameResponse>>(IntfzRs.ERROR,"合同方类型不能为空!",null);
		}
		
		List<ContractPartyNameResponse> resultList = intfzContractConfigService.getPartyNameForCustInfo(req.getMemberType(), req.getMemberName());

		return new BaseResp<List<ContractPartyNameResponse>>(IntfzRs.SUCCESS,"ok",resultList);
	}
}
