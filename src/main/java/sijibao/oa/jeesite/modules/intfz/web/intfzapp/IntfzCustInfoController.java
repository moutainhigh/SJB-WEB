package sijibao.oa.jeesite.modules.intfz.web.intfzapp;

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
import com.sijibao.oa.modules.intfz.validator.AppAuthority;
import com.sijibao.oa.modules.oa.entity.CustLinkman;
import com.sijibao.oa.modules.sys.entity.User;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * APP客户资料管理controller
 * @author xuby
 *
 */
@Controller
@RequestMapping(value = "wechat/custInfo")
@Api(value="APP客户资料管理服务",tags="APP客户资料管理服务")
public class IntfzCustInfoController extends BaseController {
	
//	@Autowired
//	private IntfzWebCustInfoService intfzWebCustInfoService;
//	@Autowired
//	private CustInfoService custInfoService;
//	@Autowired
//	private ContractInfoDao contractInfoDao;
	
	@Autowired
    private CustInfoMicroService custInfoMicroService;
	
	/**
	 * 客户资料管理-查询列表（数量）
	 * @param req
	 * @param sjboacert
	 * @param clientType
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "custInfoListNum")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "客户资料管理-查询列表（数量）")
    @AppAuthority
	BaseResp<CustInfoResponse> custInfoListNum(
			@RequestBody CustInfoHandleReq req,
			@ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
			@ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		User user = super.getCurrWxUser(sjboacert, clientType); //获取当前申请人信息

        CustInfoHandleParam param = change(req,CustInfoHandleParam.class);
        CustInfoResult result = custInfoMicroService.findNum(user.getId(),param);
        CustInfoResponse custInfoResponse = change(result,CustInfoResponse.class);

		return new BaseResp<CustInfoResponse>(IntfzRs.SUCCESS, "ok", custInfoResponse);
	}
	/**
	 * 查询列表
	 * @param req
	 * @param sjboacert
	 * @param clientType
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "custInfoList")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "APP客户资料管理-查询列表")
    @AppAuthority
	BaseResp<PageResponse<List<CustInfoPageResponse>>> custInfoList(
			@RequestBody CustInfoReq req,
			@ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
			@ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		User user = super.getCurrWxUser(sjboacert, clientType); //获取当前申请人信息

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
	 * 客户资料管理-不带分页查询
	 * @param req
	 * @param sessionid
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
//	@RequestMapping(value = "custInfos")
//	@ResponseBody
//	@ApiOperation(httpMethod = "POST", value = "APP客户资料管理-不带分页查询")
//	BaseResp<List<CustInfoResponse>> custInfos(
//			@RequestBody CustInfoHandleReq req,
//			@ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert, 
//			@ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
//			HttpServletRequest request,HttpServletResponse response) throws Exception{
//		WebUtils.initPre(request, response);
//		logger.info("====IntfzCustInfoController custInfos[req]====={}", req.toString());
//		List<CustInfoResponse> custInfos = intfzWebCustInfoService.findCustInfos(req);
//		logger.info("=======IntfzCustInfoController custInfos end=============");
//		return new BaseResp<List<CustInfoResponse>>(IntfzRs.SUCCESS, "",custInfos);
//	}

	/**
	 * 保存客户信息
	 * @param sjboacert
	 * @param clientType
	 * @param custInfoSaveReq
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "saveCustInfo")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "APP客户资料管理-保存客户信息")
    @AppAuthority
	public BaseResp<String> saveCustInfo(
			@ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
			@ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
			@RequestBody CustInfoSaveReq custInfoSaveReq,
			HttpServletRequest request, HttpServletResponse response){
		User user = super.getCurrWxUser(sjboacert, clientType); //获取当前申请人信息
		custInfoSaveReq.setProducSide("APP");
        try {
            CustInfoSaveParam param = change(custInfoSaveReq,CustInfoSaveParam.class);
            List<CustLinkmanParam> linkmanParams = new ArrayList<>();
            for(CustLinkmanReq item : custInfoSaveReq.getCustLinkman()){
                linkmanParams.add(change(item,CustLinkmanParam.class));
            }
            param.setCustLinkmanParam(linkmanParams);
            param.setCustTradeStructureParam(change(custInfoSaveReq.getCustTradeStructureReq(), CustTradeStructureParam.class));
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
	 * @param sjboacert
	 * @param clientType
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "custInfoDetail")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "APP客户资料管理-客户信息详情查询")
	BaseResp<CustInfoDetailResponse> custInfoDetail(
			@RequestBody HandleReq req,
			@ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
			@ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		if(StringUtils.isBlank(req.getId())){
			return new BaseResp<CustInfoDetailResponse>(IntfzRs.ERROR, "id不能为空",null);
		}
		User user = super.getCurrWxUser(sjboacert, clientType); //获取当前申请人信息
		if(user == null){
			return new BaseResp<CustInfoDetailResponse>(IntfzRs.ERROR, "未找到用户信息",null);
		}

        CustInfoDetailResult result = custInfoMicroService.getCustInfoById(req.getId());
        CustInfoDetailResponse resp = change(result,CustInfoDetailResponse.class);
		if(result.getContractInfoDetailResult() != null){
			resp.setContractInfoDetailResponse(change(result.getContractInfoDetailResult(), ContractInfoDetailResponse.class));
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
	 * @param sjboacert
	 * @param clientType
	 * @param req
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "deleteCustInfo")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "APP客户资料管理-删除客户信息")
	public BaseResp<String> deleteCustInfo(
			@ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
			@ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
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
	 * @param sjboacert
	 * @param clientType
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "custMaintenanceList")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "APP客户维护管理-查询列表")
    @AppAuthority
	public BaseResp<PageResponse<List<CustMaintenanceResponse>>> custMaintenanceList(
			@RequestBody CustMaintenanceHandleReq req,
			@ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
			@ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);

		User user = super.getCurrWxUser(sjboacert, clientType); //获取当前申请人信息

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
	 * 不带分页的查询
	 * @param req
	 * @param sjboacert
	 * @param clientType
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
//	@RequestMapping(value = "custMaintenances")
//	@ResponseBody
//	@ApiOperation(httpMethod = "POST", value = "APP客户维护管理-查询列表（不带分页的查询）")
//	BaseResp<List<CustMaintenanceResponse>> custMaintenances(
//			@RequestBody CustMaintenanceHandleReq req,
//			@ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert, 
//			@ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
//			HttpServletRequest request,HttpServletResponse response) throws Exception{
//		WebUtils.initPre(request, response);
//		logger.info("====IntfzCustInfoController custMaintenances[req]====={}", req.toString());
//		User user = super.getCurrWxUser(sjboacert, clientType); //获取当前申请人信息
//		List<CustMaintenanceResponse> list = intfzWebCustInfoService.findCustMaintenances( req, user);
//		logger.info("=======IntfzCustInfoController custMaintenances end=============");
//		return new BaseResp<List<CustMaintenanceResponse>>(IntfzRs.SUCCESS,"ok",list);
//	}
	/**
	 * 保存
	 * @param req
	 * @param sjboacert
	 * @param clientType
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "saveCustMaintenance")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "客户维护管理-保存列表")
    @AppAuthority
	public BaseResp<String> saveCustMaintenance(
			@RequestBody CustMaintenanceSaveReq req,
			@ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
			@ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		CharChangeUtils.CharChange(req);//替换英文字符
        User user = super.getCurrWxUser(sjboacert, clientType); //获取当前申请人信息
        req.setProducSide("APP");
		try {
            CustMaintenanceSaveParam param = change(req,CustMaintenanceSaveParam.class);
            custInfoMicroService.saveCustMaintenance(param, user.getId());
        } catch (ServiceException e) {
            return new BaseResp<String>(IntfzRs.ERROR,e.getMessage(),null);
        }
		return new BaseResp<String>(IntfzRs.SUCCESS,"保存成功!",null);
		
	}
	
	/**
	 * 客户维护管理-查询联系人
	 * @param req
	 * @param sjboacert
	 * @param clientType
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
			@ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
			@ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);

        CustMaintenanceSaveParam param = change(req,CustMaintenanceSaveParam.class);
        List<CustLinkmanResult> list = custInfoMicroService.findCustLinkman(param);
        List<CustLinkman> l = new LinkedList<>();
        for(CustLinkmanResult item:list){
            l.add(change(item,CustLinkman.class));
        }

		return new BaseResp<List<CustLinkman>>(IntfzRs.SUCCESS,"",l);
	}
	
	/**
	 * 客户维护管理-开放客户/捡入客户
	 * @param req
	 * @param sjboacert
	 * @param clientType
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
			@ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
			@ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		User user = super.getCurrWxUser(sjboacert, clientType); //获取当前申请人信息
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
	 * 客户资料管理-不带分页查询
	 * @param req
	 * @param sjboacert
	 * @param clientType
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
			@ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
			@ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		User user = super.getCurrWxUser(sjboacert, clientType); //获取当前申请人信息
        CustInfoHandleParam param = change(req,CustInfoHandleParam.class);
        List<CustInfoPageResult> l = custInfoMicroService.findCustInfos(param,user.getId());
        List<CustInfoPageResponse> list = Lists.newLinkedList();
        for(CustInfoPageResult item : l){
            list.add(change(item,CustInfoPageResponse.class));
        }
		return new BaseResp<List<CustInfoPageResponse>>(IntfzRs.SUCCESS, "ok",list);
	}
}
