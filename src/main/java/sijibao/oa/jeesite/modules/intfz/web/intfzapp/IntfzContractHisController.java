package sijibao.oa.jeesite.modules.intfz.web.intfzapp;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.curator.shaded.com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sijibao.base.common.api.response.PagerResponse;
import com.sijibao.oa.activiti.api.response.contract.ContractFlowDetailPartyResponse;
import com.sijibao.oa.common.utils.StringUtils;
import com.sijibao.oa.common.utils.WebUtils;
import com.sijibao.oa.common.web.BaseController;
import com.sijibao.oa.modules.intfz.bean.BaseResp;
import com.sijibao.oa.modules.intfz.enums.IntfzRs;
import com.sijibao.oa.modules.intfz.request.common.HandleReq;
import com.sijibao.oa.modules.intfz.request.contract.*;
import com.sijibao.oa.modules.intfz.response.common.PageResponse;
import com.sijibao.oa.modules.intfz.response.contract.*;
import com.sijibao.oa.modules.intfz.response.contract.ContractAttachmentResponse;
import com.sijibao.oa.modules.intfz.response.contractnew.ContractFlowDetailPartyMainResponse;
import com.sijibao.oa.modules.intfz.utils.CharChangeUtils;
import com.sijibao.oa.modules.intfz.validator.AppAuthority;
import com.sijibao.oa.modules.sys.entity.User;
import com.sijibao.oa.modules.sys.utils.UserUtils;
import com.sijibao.oa.office.api.IntfzWebContractHisService;
import com.sijibao.oa.office.api.exception.ServiceException;
import com.sijibao.oa.office.api.request.common.HandlerRequest;
import com.sijibao.oa.office.api.request.contracthis.*;
import com.sijibao.oa.office.api.response.contracthis.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 合同归档服务controller
 * @author wanxb
 *
 */
@Controller
@RequestMapping(value = "wechat/contractHis")
@Api(value="APP合同归档服务",tags="APP合同归档服务")
public class IntfzContractHisController extends BaseController {
	
	@Autowired
	private IntfzWebContractHisService intfzWebContractHisService;

	
	/**
	 * APP合同归档服务-提交(保存)
	 * @param req
	 * @param
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "saveContractHis")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "APP合同归档服务-提交(保存)")
    @AppAuthority
	public BaseResp<String> saveContractHis(
			@RequestBody MainContractHisSaveRequest req,
			@ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
			@ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		User user = super.getCurrWxUser(sjboacert, clientType); //获取当前申请人信息
		CharChangeUtils.CharChange(req);//替换英文字符
		User u = UserUtils.get(req.getContractLeaderId());
		if(!"1".equals(u.getUserStatus())){
			return new BaseResp<String>(IntfzRs.ERROR, "合同负责人(" + u.getName() + ")已离职，请重新选择后再提交。", "");
		}
		ContractHisSaveRequest change = change(req, ContractHisSaveRequest.class);
		change.setUserId(user.getId());
		if(StringUtils.isNotBlank(req.getContractNameId())){
			change.setConfigId(req.getContractNameId());
		}
		//附件信息
		ArrayList<ContractAttachmentRequest> list = Lists.newArrayList();
		if(req.getContractAttachmentRequest() != null && req.getContractAttachmentRequest().size() > 0){
			for (MainContractAttachmentRequest s : req.getContractAttachmentRequest()) {
				ContractAttachmentRequest sss = change(s, ContractAttachmentRequest.class);
				sss.setFileName(s.getName());
				list.add(sss);
			}
		}
		change.setContractAttachmentRequest(list);
		intfzWebContractHisService.saveContractHis(change);

		return new BaseResp<String>(IntfzRs.SUCCESS,"保存合同归档信息成功!",null);
	}
	
	/**
	 * APP合同归档服务-修改
	 * @param req
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "changeContractHis")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "APP合同归档服务-修改")
    @AppAuthority
	public BaseResp<String> changeContractHis(
			@RequestBody MainChangeContractHisRequest req,
			@ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
			@ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		User user = super.getCurrWxUser(sjboacert, clientType); //获取当前申请人信息
		ChangeContractHisRequest change = change(req, ChangeContractHisRequest.class);
		CharChangeUtils.CharChange(req);//替换英文字符
		change.setUserId(user.getId());
		try {
			intfzWebContractHisService.changeContractHis(change);
		} catch (ServiceException e) {
			return new BaseResp<String>(IntfzRs.ERROR,e.getMessage(),null);
		}
		return new BaseResp<String>(IntfzRs.SUCCESS,"修改合同归档信息成功!",null);
	}

	/**
	 * APP合同归档服务-详情
	 * @param req
	 * @param
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "contractHisDetail")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "APP合同归档服务-详情")
    @AppAuthority
	public BaseResp<MainContractHisDataResponse> contractHisDetail(
			@RequestBody HandleReq req,
			@ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
			@ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		User user = super.getCurrWxUser(sjboacert, clientType); //获取当前申请人信息
		HandlerRequest ch = new HandlerRequest();
		ch.setId(req.getId());
		ch.setUserId(user.getId());
		intfzWebContractHisService.updateReadStatus(ch);
		ContractHisDataResponse resp = intfzWebContractHisService.contractHisDetail(ch);
		ContractHisDetailResponse tail = resp.getContractHisDetailResponse();
		MainContractHisDetailResponse change = change(tail, MainContractHisDetailResponse.class);
		change.setRemarks(tail.getRemarks());
		ArrayList<ContractAttachmentResponse> list = Lists.newArrayList();
		for (com.sijibao.oa.office.api.response.contracthis.ContractAttachmentResponse ss : tail.getContractAttachmentResponse()) {
			ContractAttachmentResponse sss = change(ss, ContractAttachmentResponse.class);
			sss.setName(ss.getFileName());
			sss.setUrlPrefix(ss.getUrlPrefix());
			list.add(sss);
		}
		change.setContractAttachmentResponse(list);
		ArrayList<ContractFlowDetailPartyMainResponse> list2 = Lists.newArrayList();
		for (ContractFlowDetailPartyResponse s : tail.getContractPartyList()) {
			ContractFlowDetailPartyMainResponse ss = change(s, ContractFlowDetailPartyMainResponse.class);
			list2.add(ss);
		}
		change.setContractPartyList(list2);
		ArrayList<MainContractSuppResp> list3 = Lists.newArrayList();
		for (ContractSuppResp s : tail.getSupp()) {
			MainContractSuppResp ss = change(s, MainContractSuppResp.class);
			list3.add(ss);
		}
		change.setSuppResp(list3);
		MainContractHisDataResponse resps = new MainContractHisDataResponse();
		resps.setContractHisDetailResponse(change);
		
		return new BaseResp<MainContractHisDataResponse>(IntfzRs.SUCCESS,"",resps);
	}
	
	/**
	 * APP合同归档服务-列表查询(带分页)
	 * @param req
	 * @param
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "contractHisList")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "APP合同归档服务-列表查询(带分页)")
    @AppAuthority
	public BaseResp<PageResponse<List<MainContractHisResponse>>> contractHisList(
			@RequestBody MainContractHisHandleReq req,
			@ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
			@ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
			ContractHisHandleReq change = change(req, ContractHisHandleReq.class);
			change.setPageNo(req.getPageNo());
			change.setPageSize(req.getPageSize());
			change.setUserId(super.getCurrWxUser(sjboacert, clientType).getId());
			PagerResponse<ContractHisResponse> page = intfzWebContractHisService.contractHisList(change);
			ArrayList<MainContractHisResponse> list = Lists.newArrayList();
			for (ContractHisResponse ss : page.getList()) {
				MainContractHisResponse s = change(ss, MainContractHisResponse.class);
				list.add(s);
			}
		
		return new BaseResp<PageResponse<List<MainContractHisResponse>>>(IntfzRs.SUCCESS, "ok", 
				new PageResponse<List<MainContractHisResponse>>(list, req.getPageNo(), page.getCount()));
	}
	
	/**
	 * APP合同归档服务-查询(不带分页)
	 * @param req
	 * @param
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "queryContractHis")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "APP合同归档服务-列表查询(不带分页)")
    @AppAuthority
	public BaseResp<List<MainContractHisResponse>> queryContractHis(
			@RequestBody MainContractHisHandleReq req,
			@ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
			@ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		User user = super.getCurrWxUser(sjboacert, clientType); //获取当前申请人信息
			ContractHisHandleReq change = change(req, ContractHisHandleReq.class);
			change.setUserId(user.getId());
			List<ContractHisResponse> res = intfzWebContractHisService.queryContractHis(change);
			ArrayList<MainContractHisResponse> list = Lists.newArrayList();
			for (ContractHisResponse ss : res) {
				MainContractHisResponse s = change(ss, MainContractHisResponse.class);
				list.add(s);
			}
		
		return new BaseResp<List<MainContractHisResponse>>(IntfzRs.SUCCESS, "ok", list);
	}
	
	/**
	 * WEB合同归档服务-续签
	 * @param req
	 * @param
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "contractHisRenewal")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "APP合同归档服务-续签")
    @AppAuthority
	public BaseResp<String> contractHisRenewal(
			@RequestBody MainContractHisRenewalReq req,
			@ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
			@ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		User user = super.getCurrWxUser(sjboacert, clientType); //获取当前申请人信息
		ArrayList<ContractAttachmentRequest> list = Lists.newArrayList();
		for (MainContractAttachmentRequest s : req.getContractAttachmentRequest()) {
			ContractAttachmentRequest ss = change(s,ContractAttachmentRequest.class );
			ss.setFileName(s.getName());
			list.add(ss);
		}
		ContractHisRenewalReq change = change(req, ContractHisRenewalReq.class);
		change.setContractAttachmentRequest(list);
		change.setUserId(user.getId());
		try {
			intfzWebContractHisService.contractHisRenewal(change);
		} catch (ServiceException e) {
			return new BaseResp<String>(IntfzRs.ERROR,e.getMessage(),null);
		}
		
		return new BaseResp<String>(IntfzRs.SUCCESS,"续签信息保存成功!",null);
	}
	
	
	/**
	 * APP合同归档服务-废弃
	 * @param req
	 * @param
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "contractHisAbandon")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "APP合同归档服务-废弃")
    @AppAuthority
	public BaseResp<String> contractHisAbandon(
			@RequestBody MainContractHisAbandonReq req,
			@ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
			@ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		User user = super.getCurrWxUser(sjboacert, clientType); //获取当前申请人信息
		ContractHisAbandonReq change = change(req, ContractHisAbandonReq.class);
		change.setUserId(user.getId());
		List<ContractAttachmentRequest> list = Lists.newArrayList();
		for (MainContractAttachmentRequest ss : req.getContractAttachmentRequest()) {
			ContractAttachmentRequest s = change(ss, ContractAttachmentRequest.class);
			s.setFileName(ss.getName());
			list.add(s);
		}
		change.setContractAttachmentRequest(list);
		intfzWebContractHisService.contractHisAbandon(change);
		return new BaseResp<String>(IntfzRs.SUCCESS,"废弃信息保存成功!",null);
	}
	
	
	/**
	 * APP合同归档服务-负责人修改记录
	 * @param req
	 * @param
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "contractHisChangeLider")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "APP合同归档服务-负责人修改记录查询")
    @AppAuthority
	public BaseResp<PageResponse<List<ContractChangeLeaderResponse>>> contractHisChangeLider(
			@RequestBody MainContractHisChangeLiderReq req,
			@ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
			@ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		User user = super.getCurrWxUser(sjboacert, clientType); //获取当前申请人信息
		ChangeContractHisRequest change = change(req, ChangeContractHisRequest.class);
		change.setContractHisId(req.getContractId());
		change.setUserId(user.getId());
		PagerResponse<ContractChangeLeaderResponse> page = intfzWebContractHisService.queryContractHisChangeLider(change);
		
		
		return new BaseResp<PageResponse<List<ContractChangeLeaderResponse>>>(IntfzRs.SUCCESS,"",
				new PageResponse<List<ContractChangeLeaderResponse>>(page.getList(), req.getPageNo(), page.getCount()));
	}
	
	/**
	 * APP合同归档服务-负责人批量移动
	 * @param req
	 * @param
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "contractHisBatchMove")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "APP合同归档服务-负责人批量移动")
    @AppAuthority
	public BaseResp<String> contractHisBatchMove(
			@RequestBody MainContractHisBatchMoveReq req,
			@ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
			@ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		User u = UserUtils.get(req.getContractLeaderId());
		if(!"1".equals(u.getUserStatus())){
			return new BaseResp<String>(IntfzRs.ERROR, "负责人【"+u.getName()+"】在离职状态！", "");
		}
		WebUtils.initPre(request, response);
		User user = super.getCurrWxUser(sjboacert, clientType); //获取当前申请人信息
		try {
			ContractHisBatchMoveReq change = change(req,ContractHisBatchMoveReq.class);
			change.setUpdateBy(user.getId());
			intfzWebContractHisService.contractHisBatchMove(change);
		} catch (ServiceException e) {
			e.printStackTrace();
			return new BaseResp<String>(IntfzRs.ERROR,e.getMessage(),"");
		}
		return new BaseResp<String>(IntfzRs.SUCCESS,"操作成功！","");
		
	}

	/**
	 * 修改回执状态
	 * @param req
	 * @param sjboacert
	 * @param clientType
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "changeReceipt")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "WEB合同归档服务-改变回执状态")
    @AppAuthority
	public BaseResp<String> changeReceipt(
			@RequestBody MainChangeReceiptReq req,
			@ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
			@ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		User user = super.getCurrWxUser(sjboacert, clientType); //获取当前申请人信息
		try {
			ChangeReceiptReq change = change(req,ChangeReceiptReq.class);
			change.setUserId(user.getId());
			intfzWebContractHisService.changeReceipt(change);
		} catch (ServiceException e) {
			e.printStackTrace();
			return new BaseResp<String>(IntfzRs.ERROR,e.getMessage(),"");
		}
		return new BaseResp<String>(IntfzRs.SUCCESS,"操作成功！","");

	}

}
