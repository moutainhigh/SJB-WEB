package sijibao.oa.jeesite.modules.intfz.web.intfzapp;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sijibao.oa.common.persistence.Page;
import com.sijibao.oa.common.service.ServiceException;
import com.sijibao.oa.common.utils.StringUtils;
import com.sijibao.oa.common.utils.WebUtils;
import com.sijibao.oa.common.web.BaseController;
import com.sijibao.oa.modules.intfz.bean.BaseResp;
import com.sijibao.oa.modules.intfz.bean.WorkInfo;
import com.sijibao.oa.modules.intfz.enums.IntfzRs;
import com.sijibao.oa.modules.intfz.request.WorkHandleReq;
import com.sijibao.oa.modules.intfz.request.WorkReq;
import com.sijibao.oa.modules.intfz.response.common.PageResponse;
import com.sijibao.oa.modules.intfz.service.common.IntfzHouseKeepingService;
import com.sijibao.oa.modules.intfz.utils.CharChangeUtils;
import com.sijibao.oa.modules.intfz.validator.AppAuthority;
import com.sijibao.oa.modules.oa.entity.HouseKeeping;
import com.sijibao.oa.modules.sys.entity.User;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 内务管理相关服务
 * @author Petter
 */
@Controller
@RequestMapping(value = "wechat/work")
@Api(value="APP工作内务管理服务",tags="APP工作内务管理服务")
public class IntfzHouseKeepingController extends BaseController {
	@Autowired
	private IntfzHouseKeepingService intfzHouseKeepingService;
	/**
	 * 内务管理-提交OR草稿
	 */
	@RequestMapping(value = "save")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "内务管理-提交OR草稿")
    @AppAuthority
	public BaseResp<String> save(
			@RequestBody WorkReq req,
			@ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
			@ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		User user = super.getCurrWxUser(sjboacert, clientType); //获取当前申请人信息
		CharChangeUtils.CharChange(req);//替换英文字符
		if(user == null){
			return new BaseResp<String>(IntfzRs.ERROR, "未找到用户信息", "");
		}
		if(StringUtils.isBlank(req.getType())){
			return new BaseResp<String>(IntfzRs.ERROR, "操作类型未传", "");
		}
		try {
			String msg = intfzHouseKeepingService.save(req, user);
			if(StringUtils.isNotBlank(msg)){
				return new BaseResp<String>(IntfzRs.ERROR, msg, "");
			}
			return new BaseResp<String>(IntfzRs.SUCCESS, "操作成功", "");
		} catch (ServiceException e) {
			return new BaseResp<String>(IntfzRs.ERROR, e.getMessage(), "");
		}
		
	}
	/**
	 * 内务管理-评分
	 */
	@RequestMapping(value = "score")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "内务管理-评分")
    @AppAuthority
	public BaseResp<String> score(
			@RequestBody WorkReq req,
			@ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
			@ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		User user = super.getCurrWxUser(sjboacert, clientType); //获取当前申请人信息
		if(user == null){
			return new BaseResp<String>(IntfzRs.ERROR, "未找到用户信息", "");
		}
		if(StringUtils.isBlank(req.getUserId())){
			return new BaseResp<String>(IntfzRs.ERROR, "评分时签到人信息必填", "");
		}
		try {
			String msg = intfzHouseKeepingService.score(req, user);
			if(StringUtils.isNotBlank(msg)){
				return new BaseResp<String>(IntfzRs.ERROR, msg, "");
			}
			return new BaseResp<String>(IntfzRs.SUCCESS, "评分成功", "");
		} catch (ServiceException e) {
			return new BaseResp<String>(IntfzRs.ERROR, e.getMessage(), "");
		}
	}
	/**
	 * 内务管理-年月列表
	 */
	@RequestMapping(value = "monthList")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "内务管理-年月列表")
    @AppAuthority
	public BaseResp<PageResponse<List<WorkInfo>>> monthList(
			@RequestBody WorkHandleReq req,
			@ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
			@ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		User user = super.getCurrWxUser(sjboacert, clientType); //获取当前申请人信息
		if(user == null){
			return new BaseResp<PageResponse<List<WorkInfo>>>(IntfzRs.ERROR, "未找到用户信息", 
					new PageResponse<List<WorkInfo>>(null, req.getPageNo(), 0));
		}
		Page<HouseKeeping> page = intfzHouseKeepingService.findMonthPage(
				new Page<HouseKeeping>(req.getPageNo(), req.getPageSize()), req, user);
		List<WorkInfo> workList = intfzHouseKeepingService.monthList(req, user, page);
		return new BaseResp<PageResponse<List<WorkInfo>>>(IntfzRs.SUCCESS, "ok", 
				new PageResponse<List<WorkInfo>>(workList, page.getPageNo(), page.getCount()));
	}
	/**
	 * 内务管理-天列表
	 */
	@RequestMapping(value = "dayList")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "内务管理-天列表")
    @AppAuthority
	public BaseResp<PageResponse<List<WorkInfo>>> dayList(
			@RequestBody WorkHandleReq req,
			@ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
			@ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		User user = super.getCurrWxUser(sjboacert, clientType); //获取当前申请人信息
		if(user == null){
			return new BaseResp<PageResponse<List<WorkInfo>>>(IntfzRs.ERROR, "未找到用户信息", 
					new PageResponse<List<WorkInfo>>(null, req.getPageNo(), 0));
		}
		if(StringUtils.isBlank(req.getUserId())){
			return new BaseResp<PageResponse<List<WorkInfo>>>(IntfzRs.ERROR, "签到人信息未填充", 
					new PageResponse<List<WorkInfo>>(null, req.getPageNo(), 0));
		}
		Page<HouseKeeping> page = intfzHouseKeepingService.findDayPage(
		new Page<HouseKeeping>(req.getPageNo(), req.getPageSize()), req, user);
		List<WorkInfo> workList = intfzHouseKeepingService.dayList(req, user, page);
		return new BaseResp<PageResponse<List<WorkInfo>>>(IntfzRs.SUCCESS, "ok", 
				new PageResponse<List<WorkInfo>>(workList, page.getPageNo(), page.getCount()));
	}
	
	/**
	 * 内务管理-草稿列表
	 */
	@RequestMapping(value = "draftList")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "内务管理-草稿列表")
    @AppAuthority
	public BaseResp<PageResponse<List<WorkInfo>>> draftList(
			@RequestBody WorkHandleReq req,
			@ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
			@ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		User user = super.getCurrWxUser(sjboacert, clientType); //获取当前申请人信息
		if(user == null){
			return new BaseResp<PageResponse<List<WorkInfo>>>(IntfzRs.ERROR, "未找到用户信息", 
					new PageResponse<List<WorkInfo>>(null, req.getPageNo(), 0));
		}
		if(StringUtils.isBlank(req.getUserId())){
			return new BaseResp<PageResponse<List<WorkInfo>>>(IntfzRs.ERROR, "签到人信息未填充", 
					new PageResponse<List<WorkInfo>>(null, req.getPageNo(), 0));
		}
		Page<HouseKeeping> page = intfzHouseKeepingService.findDraftPage(
				new Page<HouseKeeping>(req.getPageNo(), req.getPageSize()), user);
		List<WorkInfo> workList = intfzHouseKeepingService.draftList(req, user, page);

		return new BaseResp<PageResponse<List<WorkInfo>>>(IntfzRs.SUCCESS, "ok", 
				new PageResponse<List<WorkInfo>>(workList, page.getPageNo(), page.getCount()));
	}
	/**
	 * 内务管理-详情
	 */
	@RequestMapping(value = "detail")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "内务管理-详情")
    @AppAuthority
	public BaseResp<WorkInfo> detail(
			@RequestBody WorkHandleReq req,
			@ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
			@ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		WorkInfo work = intfzHouseKeepingService.getDetail(req);
		return new BaseResp<WorkInfo>(IntfzRs.SUCCESS, "ok", work);
	}
}
