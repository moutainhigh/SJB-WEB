package sijibao.oa.jeesite.modules.intfz.web.intfzweb;

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
import com.sijibao.oa.crm.api.CustInfoClueMicroService;
import com.sijibao.oa.crm.api.exception.ServiceException;
import com.sijibao.oa.crm.api.request.custclue.CustInfoClueHandleParam;
import com.sijibao.oa.crm.api.request.custclue.CustInfoClueSaveParam;
import com.sijibao.oa.crm.api.response.custclue.CustInfoClueDetailResult;
import com.sijibao.oa.crm.api.response.custclue.CustInfoClueResult;
import com.sijibao.oa.modules.intfz.bean.BaseResp;
import com.sijibao.oa.modules.intfz.enums.IntfzRs;
import com.sijibao.oa.modules.intfz.request.common.HandleReq;
import com.sijibao.oa.modules.intfz.request.custinfo.CustInfoClueHandleReq;
import com.sijibao.oa.modules.intfz.request.custinfo.CustInfoClueSaveReq;
import com.sijibao.oa.modules.intfz.response.common.PageResponse;
import com.sijibao.oa.modules.intfz.response.custinfo.CustInfoClueDetailResponse;
import com.sijibao.oa.modules.intfz.response.custinfo.CustInfoClueResponse;
import com.sijibao.oa.modules.intfz.utils.CharChangeUtils;
import com.sijibao.oa.modules.sys.entity.User;
import com.sijibao.oa.modules.sys.utils.UserUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 客户线索管理controller
 * @author wanxb
 *
 */
@Controller
@RequestMapping(value = "${frontPath}/custInfoClue")
@Api(value="WEB客户线索管理服务",tags="WEB客户线索管理服务")
public class IntfzWebCustInfoClueController extends BaseController {
//	@Autowired
//	private IntfzWebCustInfoClueService intfzWebCustInfoClueService;
//	@Autowired
//	private CustInfoClueService custInfoClueService;
//	@Autowired
//	private CustInfoClueDao custInfoClueDao;

	@Autowired
    private CustInfoClueMicroService custInfoClueMicroService;

	/**
	 * 线索信息查询列表
	 * @param req
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "custInfoClueList")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "WEB客户线索管理-查询列表")
	BaseResp<PageResponse<List<CustInfoClueResponse>>> custInfoList(
			@RequestBody CustInfoClueHandleReq req,
			@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		User user = UserUtils.getUser(sessionid);

        CustInfoClueHandleParam param = change(req,CustInfoClueHandleParam.class);
		PagerResponse<CustInfoClueResult> page = custInfoClueMicroService.findPage(param, user.getId());
		List<CustInfoClueResponse> list = Lists.newLinkedList();
		for(CustInfoClueResult item : page.getList()){
		    list.add(change(item,CustInfoClueResponse.class));
        }

		return new BaseResp<PageResponse<List<CustInfoClueResponse>>>(IntfzRs.SUCCESS, "ok", 
				new PageResponse<List<CustInfoClueResponse>>(list, req.getPageNo(), page.getCount()));
	}

	/**
	 * 保存客户线索信息
	 * @param sessionid
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "saveCustInfoClue")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "WEB客户线索管理-保存客户信息")
	public BaseResp<String> saveCustInfoClue(@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
											 @RequestBody CustInfoClueSaveReq req,
											 HttpServletRequest request, HttpServletResponse response){
		CharChangeUtils.CharChange(req);//替换英文字符
        User user = UserUtils.getUser(sessionid);
		try {
            CustInfoClueSaveParam param = change(req,CustInfoClueSaveParam.class);
			custInfoClueMicroService.saveCustInfoClue(param, user.getId());
		} catch (Exception e) {
			logger.error(e.getMessage());
            return new BaseResp<String>(IntfzRs.ERROR,e.getMessage(),null);
		}
		return new BaseResp<String>(IntfzRs.SUCCESS,"保存客户信息成功!",null);
	}
	/**
	 * 客户线索详情查询
	 * @param req
	 * @param sessionid
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "custInfoClueDetail")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "WEB客户线索管理-客户线索详情查询")
	BaseResp<CustInfoClueDetailResponse> custInfoDetail(
			@RequestBody HandleReq req,
			@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		if(StringUtils.isBlank(req.getId())){
			return new BaseResp<CustInfoClueDetailResponse>(IntfzRs.ERROR, "id不能为空",null);
		}
		User user = UserUtils.getUser(sessionid); //获取当前申请人信息
		if(user == null){
			return new BaseResp<CustInfoClueDetailResponse>(IntfzRs.ERROR, "未找到用户信息",null);
		}

		CustInfoClueDetailResult detailResult = custInfoClueMicroService.getCustInCluefoById(req.getId());
        CustInfoClueDetailResponse resp = change(detailResult,CustInfoClueDetailResponse.class);

		return new BaseResp<CustInfoClueDetailResponse>(IntfzRs.SUCCESS, "ok", resp);
	}

	/**
	 * 删除线索信息
	 * @param sessionid
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "deleteCustInfoClue")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "WEB客户线索管理-删除客户线索信息")
	public BaseResp<String> deleteCustInfo(@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
										   @RequestBody HandleReq req,
										   HttpServletRequest request, HttpServletResponse response){
		if(StringUtils.isBlank(req.getId())){
			return new BaseResp<String>(IntfzRs.ERROR,"主键ID不能为空!",null);
		}
		try {
			custInfoClueMicroService.deleteCustInfo(req.getId());
		} catch (ServiceException e) {
			logger.error(e.getMessage());
			return new BaseResp<String>(IntfzRs.ERROR,e.getMessage(),null);
		}catch (Exception e){
		    logger.error(e.getMessage());
        }
		return new BaseResp<String>(IntfzRs.SUCCESS,"删除客户信息成功!",null);
	}
}