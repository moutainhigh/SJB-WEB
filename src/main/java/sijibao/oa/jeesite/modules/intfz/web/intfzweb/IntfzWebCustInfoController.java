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

import com.google.common.collect.Lists;
import com.sijibao.base.common.api.response.PagerResponse;
import com.sijibao.oa.common.utils.WebUtils;
import com.sijibao.oa.common.web.BaseController;
import com.sijibao.oa.crm.api.CustInfoMicroService;
import com.sijibao.oa.crm.api.exception.ServiceException;
import com.sijibao.oa.crm.api.request.custinfo.*;
import com.sijibao.oa.crm.api.response.custinfo.*;
import com.sijibao.oa.modules.intfz.bean.BaseResp;
import com.sijibao.oa.modules.intfz.enums.IntfzRs;
import com.sijibao.oa.modules.intfz.request.common.HandleReq;
import com.sijibao.oa.modules.intfz.request.custinfo.*;
import com.sijibao.oa.modules.intfz.response.common.PageResponse;
import com.sijibao.oa.modules.intfz.response.custinfo.*;
import com.sijibao.oa.modules.intfz.utils.CharChangeUtils;
import com.sijibao.oa.modules.oa.entity.CustLinkman;
import com.sijibao.oa.modules.sys.entity.User;
import com.sijibao.oa.modules.sys.utils.UserUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 客户资料管理controller
 * @author xuby
 *
 */
@Controller
@RequestMapping(value = "${frontPath}/custInfo")
@Api(value="WEB客户资料管理服务",tags="WEB客户资料管理服务")
public class IntfzWebCustInfoController extends BaseController {
	
	@Autowired
    private CustInfoMicroService custInfoMicroService;

	/**
	 * 客户资料管理-查询列表（数量）
	 * @param req
	 * @param sessionid
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "custInfoListNum")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "客户资料管理-查询列表（数量）")
	public BaseResp<CustInfoResponse> custInfoListNum(
			@RequestBody CustInfoHandleReq req,
			@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		User user = UserUtils.getUser(sessionid);

        CustInfoHandleParam param = change(req,CustInfoHandleParam.class);
		CustInfoResult result = custInfoMicroService.findNum(user.getId(),param);
        CustInfoResponse custInfoResponse = change(result,CustInfoResponse.class);

		return new BaseResp<CustInfoResponse>(IntfzRs.SUCCESS, "ok", custInfoResponse);
	}

	/**
	 * 查询列表
	 * @param req
	 * @param sessionid
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "custInfoList")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "客户资料管理-查询列表")
	public BaseResp<PageResponse<List<CustInfoPageResponse>>> custInfoList(
			@RequestBody CustInfoReq req,
			@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		User user = UserUtils.getUser(sessionid);

        CustInfoParam param = change(req,CustInfoParam.class);
		PagerResponse<CustInfoPageResult> page = custInfoMicroService.findPage(param, user.getId());
		List<CustInfoPageResponse> list = new LinkedList<>();
		if(page.getList() != null){
			for(CustInfoPageResult item : page.getList()){
				list.add(change(item,CustInfoPageResponse.class));
			}
		}
		return new BaseResp<PageResponse<List<CustInfoPageResponse>>>(IntfzRs.SUCCESS, "ok",
				new PageResponse<List<CustInfoPageResponse>>(list, req.getPageNo(), page.getCount()));
	}
	/**
	 * 保存客户信息
	 * @param sessionid
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "saveCustInfo")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "客户资料管理-保存客户信息")
	public BaseResp<String> saveCustInfo(@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
										 @RequestBody CustInfoSaveReq custInfoSaveReq,
										 HttpServletRequest request, HttpServletResponse response){
		CharChangeUtils.CharChange(custInfoSaveReq);//替换英文字符
		CharChangeUtils.CharChange(custInfoSaveReq.getContractInfoSaveReq());//替换英文字符
		CharChangeUtils.CharChange(custInfoSaveReq.getCustTradeStructureReq());//替换英文字符
		for(CustLinkmanReq man :custInfoSaveReq.getCustLinkman()){
			CharChangeUtils.CharChange(man);//替换英文字符
		}
		if(StringUtils.isBlank(custInfoSaveReq.getCustName()) ||
				StringUtils.isBlank(custInfoSaveReq.getCustName().replace(" ", ""))	){
			return new BaseResp<String>(IntfzRs.ERROR,"客户名称不能为空!",null);
		}

        custInfoSaveReq.setProducSide("web");
		try {
			User user = UserUtils.getUser(sessionid);

            CustInfoSaveParam param = change(custInfoSaveReq,CustInfoSaveParam.class);
            List<CustLinkmanParam> linkmanParams = new ArrayList<>();
            for(CustLinkmanReq item : custInfoSaveReq.getCustLinkman()){
                linkmanParams.add(change(item,CustLinkmanParam.class));
            }
            param.setCustLinkmanParam(linkmanParams);
            param.setCustTradeStructureParam(change(custInfoSaveReq.getCustTradeStructureReq(),CustTradeStructureParam.class));
            param.setContractInfoSaveParam(change(custInfoSaveReq.getContractInfoSaveReq(),ContractInfoSaveParam.class));

			custInfoMicroService.saveCustInfo(param, user.getId());
		} catch (ServiceException e) {
			logger.error(e.getMessage());
			return new BaseResp<String>(IntfzRs.ERROR,e.getMessage(),null);
		}
		return new BaseResp<String>(IntfzRs.SUCCESS,"保存客户信息成功!",null);
	}

	/**
	 * 客户详情查询
	 * @param req
	 * @param sessionid
     * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "custInfoDetail")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "客户资料管理-客户信息详情查询")
	public BaseResp<CustInfoDetailResponse> custInfoDetail(
			@RequestBody HandleReq req,
			@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		if(StringUtils.isBlank(req.getId())){
			return new BaseResp<CustInfoDetailResponse>(IntfzRs.ERROR, "id不能为空",null);
		}
		User user = UserUtils.getUser(sessionid); //获取当前申请人信息
		if(user == null){
			return new BaseResp<CustInfoDetailResponse>(IntfzRs.ERROR, "未找到用户信息",null);
		}
		CustInfoDetailResult result = custInfoMicroService.getCustInfoById(req.getId());
        CustInfoDetailResponse resp = change(result,CustInfoDetailResponse.class);
		if(result.getContractInfoDetailResult() != null){
			resp.setContractInfoDetailResponse(change(result.getContractInfoDetailResult(),ContractInfoDetailResponse.class));
        }
        if(result.getCustTradeStructureResult() != null){
        	resp.setCustTradeStructureResponse(change(result.getCustTradeStructureResult(),CustTradeStructureResponse.class));
		}
		List<CustLinkmanResponse> custLinkmanResponse = Lists.newArrayList();
		if(result.getCustLinkmanForDetailResult() != null && result.getCustLinkmanForDetailResult().size() > 0){
			for(CustLinkmanForDetailResult linkmans : result.getCustLinkmanForDetailResult()){
				custLinkmanResponse.add(change(linkmans,CustLinkmanResponse.class));
			}
			resp.setCustLinkmanResponse(custLinkmanResponse);
		}
		return new BaseResp<CustInfoDetailResponse>(IntfzRs.SUCCESS, "ok", resp);
	}
	/**
	 * 删除客户信息
	 * @param sessionid
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "deleteCustInfo")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "客户资料管理-删除客户信息")
	public BaseResp<String> deleteCustInfo(@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
										   @RequestBody HandleReq req,
										   HttpServletRequest request, HttpServletResponse response){
		if(StringUtils.isBlank(req.getId())){
			return new BaseResp<String>(IntfzRs.ERROR,"主键ID不能为空!",null);
		}
		try {
			custInfoMicroService.deleteCustInfo(req.getId());
		} catch (ServiceException e) {
			logger.error(e.getMessage());
			return new BaseResp<String>(IntfzRs.ERROR,"删除客户信息失败!",null);
		}
		return new BaseResp<String>(IntfzRs.SUCCESS,"删除客户信息成功!",null);
	}

	/**
	 * 客户维护管理查询列表
	 * @param req
	 * @param sessionid
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "custMaintenanceList")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "客户维护管理-查询列表")
	public BaseResp<PageResponse<List<CustMaintenanceResponse>>> custMaintenanceList(
			@RequestBody CustMaintenanceHandleReq req,
			@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		User user = UserUtils.getUser(sessionid);

        CustMaintenanceHandleParam param = change(req,CustMaintenanceHandleParam.class);
		PagerResponse<CustMaintenanceResult> page = custInfoMicroService.findCustMaintenancePage(param);
		List<CustMaintenanceResponse> list = new LinkedList<>();
		for(CustMaintenanceResult item : page.getList()){
		    list.add(change(item,CustMaintenanceResponse.class));
        }

		return new BaseResp<PageResponse<List<CustMaintenanceResponse>>>(IntfzRs.SUCCESS, "ok", 
				new PageResponse<List<CustMaintenanceResponse>>(list, req.getPageNo(), page.getCount()));
	}
	/**
	 * 客户维护管理-保存列表
	 * @param req
	 * @param sessionid
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "saveCustMaintenance")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "客户维护管理-保存列表")
	public BaseResp<String> saveCustMaintenance(
			@RequestBody CustMaintenanceSaveReq req,
			@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		User user = UserUtils.getUser(sessionid);
		CharChangeUtils.CharChange(req);//替换英文字符

		req.setProducSide("web");
		try {
            CustMaintenanceSaveParam param = change(req,CustMaintenanceSaveParam.class);
			custInfoMicroService.saveCustMaintenance(param, user.getId());
		} catch (ServiceException e) {
			return new BaseResp<String>(IntfzRs.ERROR,e.getMessage(),null);
		}
		return new BaseResp<String>(IntfzRs.SUCCESS,"保存成功!",null);
	}
	
	/**
	 * 下载合同管理ID
	 * @param sessionid
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "downLoadContractPdf")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "客户维护管理-合同模版下载")
	public BaseResp<String> downLoadContractPdf(@RequestBody CustDownloadContractPdfReq req,
												@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
												HttpServletRequest request, HttpServletResponse response){
		if(StringUtils.isBlank(req.getCustId())){
			return new BaseResp<String>(IntfzRs.ERROR,"客户信息id不能为空!",null);
		}
		
		if(StringUtils.isBlank(req.getContractCompanyCode())){
			return new BaseResp<String>(IntfzRs.ERROR,"合同名称编码不能为空!",null);
		}

		try{
            custInfoMicroService.downCheck(req.getCustId());
        }catch (ServiceException e){
		    logger.error("下载合同模版异常：{}"+e.getMessage());
            return new BaseResp<String>(IntfzRs.SUCCESS,e.getMessage(),null);
        }

		String url ="/f/commonInfo/downloadContractPdf?custId="+req.getCustId()+"&contractCompanyCode="+req.getContractCompanyCode();  //下载地址
		
		return new BaseResp<String>(IntfzRs.SUCCESS,"",url);
	}

	/**
	 * 客户维护管理-查询联系人
	 * @param req
	 * @param sessionid
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "findCustLinkman")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "客户维护管理-查询联系人")
	public BaseResp<List<CustLinkman>> findCustLinkman(
			@RequestBody CustMaintenanceSaveReq req,
			@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);

        CustMaintenanceSaveParam param = change(req,CustMaintenanceSaveParam.class);
		List<CustLinkmanResult> list = custInfoMicroService.findCustLinkman(param);
		List<CustLinkman> linkmen = new LinkedList<>();
		for(CustLinkmanResult item:list){
			linkmen.add(change(item,CustLinkman.class));
        }

		return new BaseResp<List<CustLinkman>>(IntfzRs.SUCCESS,"",linkmen);
	}

	/**
	 * 客户维护管理-开放客户/捡入客户
	 * @param req
	 * @param sessionid
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "custChangePick")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "客户管理-开放客户/捡入客户")
	public BaseResp<String> custChangePick(
			@RequestBody ChangePickReq req,
			@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		User user = UserUtils.getUser(sessionid);//获取当前申请人信息
		try {
            ChangePickParam param = change(req,ChangePickParam.class);
			custInfoMicroService.custChangePick(param,user.getId());
		} catch (ServiceException e) {
			e.printStackTrace();
			return new BaseResp<String>(IntfzRs.ERROR,e.getMessage(),"");
		}
		return new BaseResp<String>(IntfzRs.SUCCESS,"操作成功！","");
		
	}
	
	/**
	 * 客户管理-客户池管理
	 * @param req
	 * @param sessionid
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "custPoolList")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "客户管理-客户池管理")
	public BaseResp<PageResponse<List<CustInfoPageResponse>>> custPoolList(
			@RequestBody CustInfoReq req,
			@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		User user = UserUtils.getUser(sessionid);
		PagerResponse<CustInfoPageResult> page;
		List<CustInfoPageResponse> list = Lists.newLinkedList();
		try {
            CustInfoParam param = change(req,CustInfoParam.class);
			page = custInfoMicroService.findPoolPage(param, user.getId());
			for(CustInfoPageResult item : page.getList()){
			    list.add(change(item,CustInfoPageResponse.class));
            }
		} catch (ServiceException e) {
			return new BaseResp<>(IntfzRs.ERROR, e.getMessage(), null	);
		}
		return new BaseResp<>(IntfzRs.SUCCESS, "ok",
				new PageResponse<>(list, req.getPageNo(), page.getCount()));
	}
	
	/**
	 * 客户管理-批量移动客户
	 * @param req
	 * @param sessionid
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "custBatchMove")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "客户管理-批量移动客户")
	public BaseResp<String> custBatchMove(
			@RequestBody BatchMoveReq req,
			@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		User user = UserUtils.getUser(sessionid);//获取当前申请人信息
		try {
            BatchMoveParam param = change(req,BatchMoveParam.class);
			int errorCount = custInfoMicroService.custBatchMove(param,user.getId());
			int count = 0;
			if(req.getCustIds() != null && req.getCustIds().size() > 0){
				count = req.getCustIds().size();
			}
			if(errorCount > 0 ){
				String successCount = String.valueOf(count - errorCount);
				return new BaseResp<String>(IntfzRs.HALF,successCount + "个客户移动成功，" +
						errorCount + "个客户移动失败，失败原因：当前市场负责人个人客户超过上限，超过个数" +
						errorCount + "个!","");
//				return new BaseResp<String>(IntfzRs.HALF,"当前市场负责人个人客户超过上限，超过个数"+errorCount+"个!","");
			}
		} catch (ServiceException e) {
			e.printStackTrace();
			return new BaseResp<String>(IntfzRs.ERROR,e.getMessage(),"");
		}
		return new BaseResp<String>(IntfzRs.SUCCESS,"操作成功！","");
		
	}
	/**
	 * 客户管理-模糊查询10条客户数据
	 * @param req
	 * @param sessionid
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "queryCustInstant")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "客户管理-模糊查询10条客户数据")
	public BaseResp<List<CustInstantResponse>> queryCustInstant(
			@RequestBody CustInstantReq req,
			@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		User user = UserUtils.getUser(sessionid);//获取当前申请人信息
		List<CustInstantResponse> list = Lists.newArrayList();
		try {
            CustInstantParam param = change(req,CustInstantParam.class);
            List<CustInstantResult> l = custInfoMicroService.queryCustInstant(param,user.getId());
            for(CustInstantResult item : l){
                list.add(change(item,CustInstantResponse.class));
            }
		} catch (ServiceException e) {
			e.printStackTrace();
			return new BaseResp<List<CustInstantResponse>>(IntfzRs.ERROR,e.getMessage(),null);
		}
		return new BaseResp<List<CustInstantResponse>>(IntfzRs.SUCCESS,"",list);
		
	}
	/**
	 * 客户管理-合并客户
	 * @param req
	 * @param sessionid
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "custMerge")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "客户管理-合并客户")
	public BaseResp<String> custMerge(
			@RequestBody CustMergeReq req,
			@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		User user = UserUtils.getUser(sessionid);//获取当前申请人信息
		try {
            CustMergeParam param = change(req,CustMergeParam.class);
			custInfoMicroService.custMerge(param,user.getId());
		} catch (ServiceException e) {
			e.printStackTrace();
			return new BaseResp<String>(IntfzRs.ERROR,e.getMessage(),"");
		}
		return new BaseResp<String>(IntfzRs.SUCCESS,"操作成功！","");
		
	}
	
	/**
	 * 客户管理-批量开放
	 * @param req
	 * @param sessionid
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "custBatchOpen")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "客户管理-批量开放")
	public BaseResp<String> custBatchOpen(
			@RequestBody ChangePickReq req,
			@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		User user = UserUtils.getUser(sessionid);//获取当前申请人信息
		try {
            ChangePickParam param = change(req,ChangePickParam.class);
			custInfoMicroService.custBatchOpen(param,user.getId());
		} catch (ServiceException e) {
			e.printStackTrace();
			return new BaseResp<String>(IntfzRs.ERROR,e.getMessage(),"");
		}
		return new BaseResp<String>(IntfzRs.SUCCESS,"操作成功！","");
	}
	
	/**
	 * 客户资料管理-不带分页查询
	 * @param req
	 * @param sessionid
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "custInfos")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "客户资料管理-不带分页查询")
	public BaseResp<List<CustInfoPageResponse>> custInfos(
			@RequestBody CustInfoHandleReq req,
			@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		User user = UserUtils.getUser(sessionid);
        CustInfoHandleParam param = change(req,CustInfoHandleParam.class);
		List<CustInfoPageResult> l = custInfoMicroService.findCustInfos(param,user.getId());
		List<CustInfoPageResponse> list = Lists.newLinkedList();
		for(CustInfoPageResult item : l){
		    list.add(change(item,CustInfoPageResponse.class));
        }
		return new BaseResp<List<CustInfoPageResponse>>(IntfzRs.SUCCESS, "ok",list);
	}


	/**
	 * 客户管理-批量修改客户
	 * @param req
	 * @param sessionid
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "custBatchUpdate")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "客户管理-批量修改客户")
	public BaseResp<String> custBatchUpdate(
			@RequestBody BatchUpdateReq req,
			@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		User user = UserUtils.getUser(sessionid);//获取当前申请人信息
		try {
			BatchUpdateParam param = change(req,BatchUpdateParam.class);
			param.setUserId(user.getId());
			custInfoMicroService.custBatchUpdate(param);
		} catch (ServiceException e) {
			e.printStackTrace();
			return new BaseResp<String>(IntfzRs.ERROR,e.getMessage(),"");
		}
		return new BaseResp<String>(IntfzRs.SUCCESS,"操作成功！","");

	}
}
