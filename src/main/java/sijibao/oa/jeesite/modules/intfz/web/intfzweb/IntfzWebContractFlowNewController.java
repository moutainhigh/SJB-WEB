package sijibao.oa.jeesite.modules.intfz.web.intfzweb;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import com.sijibao.oa.modules.intfz.request.contractnew.MainIdWrapper;
import com.sijibao.oa.modules.intfz.response.common.PageResponse;
import com.sijibao.oa.modules.intfz.response.contract.ContractFlowDetailResponse;
import com.sijibao.oa.modules.intfz.response.contract.MainContractHisResponse;
import com.sijibao.oa.modules.intfz.response.contractnew.*;
import com.sijibao.oa.modules.intfz.utils.CharChangeUtils;
import com.sijibao.oa.modules.intfz.utils.Constant;
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
@RequestMapping(value = "${frontPath}/contractFlowNew")
@Api(value="WEB合同管理流程服务(新)",tags="WEB合同管理流程服务(新)")
public class IntfzWebContractFlowNewController extends BaseController{

	private IntfzContractConfigService intfzContractConfigService;

	private IntfzContractInfoBackService intfzContractInfoBackService;

	private IntfzWebContractHisService intfzWebContractHisService;

	private ContractFlowNewService contractFlowNewService;
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
	 * @param sessionid
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "contractApplyNew")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "WEB端合同申请-申请")
	public BaseResp<String> contractApplyNew(
			@RequestBody ContractFlowNewMainRequest req,
			@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		User u = UserUtils.get(req.getContractLeaderId());
		CharChangeUtils.CharChange(req);//替换英文字符
		req.setProducSide("web");
		if(!"1".equals(u.getUserStatus())){
			return new BaseResp<String>(IntfzRs.ERROR, "合同负责人(" + u.getName() + ")已离职，请重新选择后再提交。", "");
		}
		User user = UserUtils.getUser(sessionid); //获取当前申请人信息
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
			e.printStackTrace();
			logger.error(e.getMessage());
            return new BaseResp<String>(IntfzRs.ERROR,e.getMessage(),null);
		}

		return new BaseResp<String>(IntfzRs.SUCCESS, "申请发起成功", "");
	}

	/**
	 * 合同审批 同意/驳回
	 * @param req
	 * @param sessionid
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "contractFlowNewCompleteTask")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "WEB端合同申请-同意/驳回")
	public BaseResp<String> completeTask(@RequestBody ContractFlowCompleteTaskNewMainReq req,
										 @ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
										 HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		CharChangeUtils.CharChange(req);//替换英文字符
		if (StringUtils.isBlank(req.getComment()) && "no".equals(req.getFlag())) {
			logger.error("=======IntfzWebContractFlowNewController contractFlowNewCompleteTask end=============请填写审核意见");
			return new BaseResp<String>(IntfzRs.ERROR, "请填写审核意见", "");
		}
		if (StringUtils.isBlank(req.getProcInsId())){
			logger.error("=======IntfzWebContractFlowNewController contractFlowNewCompleteTask end=============procInsId is not null");
			return new BaseResp<String>(IntfzRs.ERROR, "procInsId is not null", "");
		}
		if(StringUtils.isBlank(req.getContractFlowId())){
			logger.error("=======IntfzWebContractFlowNewController contractFlowNewCompleteTask end=============contractFlowId is not null");
			return new BaseResp<String>(IntfzRs.ERROR, "contractFlowId is not null", "");
		}

		User user = UserUtils.getUser(sessionid); //获取当前申请人信息

		ContractFlowCompleteTaskNewReq cReq = change(req,ContractFlowCompleteTaskNewReq.class);

		List<ContractAttachmentNewRequest> attachList = new ArrayList<ContractAttachmentNewRequest>();
		if(req.getContractAttachmentList() != null && req.getContractAttachmentList().size() > 0){
			for(ContractAttachmentNewMainRequest m:req.getContractAttachmentList()){
				ContractAttachmentNewRequest r = change(m,ContractAttachmentNewRequest.class);
				attachList.add(r);
			}
			cReq.setContractAttachmentList(attachList);
		}

		try {
			contractFlowNewService.completeTask(cReq, user.getId(),Constant.CLIENT_TYPE_WEB);
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
	 * @param sessionid
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "repealApply")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "WEB端合同申请-删除")
	public BaseResp<String> repealApply(@RequestBody ContractFlowHandleNewMainReq req,
										@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
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
	 * @param sessionid
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "repealTask")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "WEB端合同申请-流程收回")
	public BaseResp<String> repealTask(@RequestBody ContractFlowRepealNewMainRequest req,
									   @ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
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

		User user = UserUtils.getUser(sessionid); //获取当前申请人信息

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
	 * @param sessionid
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "contractFlowDetail")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "WEB端合同申请-查询审批流程详情")
	public BaseResp<ContractFlowDetailResponse> contractFlowDetail(
			@RequestBody ContractFlowHandleNewMainReq req,
			@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);

		if (StringUtils.isBlank(req.getContractFlowId())){
			logger.info("=======IntfzContractFlowNewController contractFlowDetail start=============contractFlowId is not null");
			return new BaseResp<ContractFlowDetailResponse>(IntfzRs.ERROR, "contractFlowId is not null", null);
		}

		User user = UserUtils.getUser(sessionid); //获取当前申请人信息

		ContractFlowHandleNewReq cReq = change(req,ContractFlowHandleNewReq.class);

		try{
			contractFlowNewService.updateReadStatus(cReq, user.getId());
			ContractFlowDetailNewResponse cResponse = contractFlowNewService.contractFlowDetail(cReq, user.getId());
			//如果合同负责人离职，不返回id
			String contractLeaderId = cResponse.getContractFlowDetailInfoNewResponse().getContractLeaderId();
			User contractLeader = UserUtils.get(contractLeaderId);
			if(!"1".equals(contractLeader.getUserStatus())){
				cResponse.getContractFlowDetailInfoNewResponse().setContractLeaderId("");
			}
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
	 * @param sessionid
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "queryMyContractFlowList")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "WEB端合同申请-查询列表")
	public BaseResp<PageResponse<List<ContractFlowNewMainResponse>>> queryMyContractFlowList(
			@RequestBody ContractFlowListNewMainReq req,
			@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		User user = UserUtils.getUser(sessionid); //获取当前申请人信息

		ContractFlowListNewReq cReq = change(req,ContractFlowListNewReq.class);
		cReq.setUserId(user.getId());
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
     * 批量删除合同审核流程
     */
    @RequestMapping(value = "batchDeleteContractFlow")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "WEB端合同申请-批量删除合同审核流程")
    public BaseResp<String> batchDeleteContractFlow(
			@RequestBody ContractFlowBatchDeleteReq req,
			@ApiParam(value = "登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) {
        WebUtils.initPre(request, response);
        List<MainIdWrapper> idList = req.getIdList();
        if(idList == null || idList.size() == 0){
            return new BaseResp<>(IntfzRs.ERROR, "未选择合同！", "");
        }

        User user = UserUtils.getUser(sessionid);

        Map<String,String> resultMap;
        ContractFlowBatchDeleteParam param = change(req,ContractFlowBatchDeleteParam.class);
        List<IdWrapper> idWrapperList = new ArrayList<>();
        for(MainIdWrapper miw : req.getIdList()){
             IdWrapper idWrapper = change(miw,IdWrapper.class);
             idWrapperList.add(idWrapper);
        }
        param.setOperateUserId(user.getId());
        param.setIdList(idWrapperList);
        try {
            resultMap = contractFlowNewService.batchDeleteContractFlow(param);
        } catch (ServiceException e) {
            return new BaseResp<>(IntfzRs.ERROR, "批量删除合同出现异常", null);
        }

        if("fail".equals(resultMap.get("flag"))){
            return new BaseResp<>(IntfzRs.ERROR, resultMap.get("msg"), null);
        }

        logger.info("===IntfzWebContractFlowNewController batchDeleteContractFlow end.[message]====== " + resultMap.get("msg"));
        return new BaseResp<>(IntfzRs.SUCCESS, resultMap.get("msg"), null);
    }

	/**
	 * 查询合同名称下拉列表
	 * @param sessionid
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "queryContractTempletList")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "WEB端合同申请-查询合同名称下拉列表")
	public BaseResp<List<ContractTempletListResponse>> queryContractTempletList(
			@RequestBody ContractConfigMainRequest req,
			@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
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
	 * @param sessionid
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "queryContractConfig")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "WEB端合同申请-查询合同配置信息")
	public BaseResp<ContractConfigMainResponse> queryContractConfig(
			@RequestBody ContractConfigMainRequest req,
			@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
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
	 * @param sessionid
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "queryMainContractList")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "WEB端合同申请-主合同查询")
	public BaseResp<PageResponse<List<MainContractHisResponse>>> queryMainContractList(
			@RequestBody ContractFlowListNewMainReq req,
			@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		User user = UserUtils.getUser(sessionid); //获取当前申请人信息

		ContractHisHandleReq hisReq = new ContractHisHandleReq();
		hisReq.setPageNo(req.getPageNo());
		hisReq.setPageSize(req.getPageSize());
		hisReq.setUserId(user.getId());
		hisReq.setRemoveDelFlag("1");
		hisReq.setFaint(req.getFaint());
        PagerResponse<ContractHisResponse> result = intfzWebContractHisService.queryMainContractList(hisReq);
//		PagerResponse<ContractHisResponse> result = intfzWebContractHisService.contractHisList(hisReq);
		ArrayList<MainContractHisResponse> list = Lists.newArrayList();
		for (ContractHisResponse s : result.getList()) {
			MainContractHisResponse ss = change(s, MainContractHisResponse.class);
			list.add(ss);
		}
		return new BaseResp<>(IntfzRs.SUCCESS, "ok",
				new PageResponse<>(list,req.getPageNo(),result.getCount()));
	}

	/**
	 * 合同方合同公司信息查询
	 * @param sessionid
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "queryContractCompanyList")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "WEB端合同申请-查询合同公司信息列表")
	public BaseResp<List<ContractCompanyListResponse>> queryContractCompanyList(
			@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);


		return new BaseResp<>(IntfzRs.SUCCESS, "ok", null);
	}

	/**
	 * 从合同公司信息获取合同方信息
	 * @param req
	 * @param sessionid
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "queryPartyNameForContractBack")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "WEB端合同申请-从合同公司信息获取合同方信息")
	public BaseResp<List<ContractPartyNameResponse>> queryPartyNameForContractBack(@RequestBody ContractInfoPartyNameReq req,
																				   @ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
																				   HttpServletRequest request, HttpServletResponse response) throws Exception{

		if(StringUtils.isBlank(req.getMemberType())){
			return new BaseResp<List<ContractPartyNameResponse>>(IntfzRs.ERROR,"合同方类型不能为空!",null);
		}

		List<ContractPartyNameResponse> resultList = intfzContractConfigService.getPartyNameForContractBack(req.getMemberType(), req.getMemberName());

		return new BaseResp<List<ContractPartyNameResponse>>(IntfzRs.SUCCESS,"ok",resultList);
	}

	/**
	 * WEB端合同申请-从客户信息获取合同方信息
	 * @param req
	 * @param sessionid
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "queryPartyNameForCustInfo")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "WEB端合同申请-从客户信息获取合同方信息")
	public BaseResp<List<ContractPartyNameResponse>> queryPartyNameForCustInfo(@RequestBody ContractInfoPartyNameReq req,
																			   @ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
																			   HttpServletRequest request, HttpServletResponse response) throws Exception{
		if(StringUtils.isBlank(req.getMemberType())){
			return new BaseResp<List<ContractPartyNameResponse>>(IntfzRs.ERROR,"合同方类型不能为空!",null);
		}

		List<ContractPartyNameResponse> resultList = intfzContractConfigService.getPartyNameForCustInfo(req.getMemberType(), req.getMemberName());

		return new BaseResp<List<ContractPartyNameResponse>>(IntfzRs.SUCCESS,"ok",resultList);
	}

}
