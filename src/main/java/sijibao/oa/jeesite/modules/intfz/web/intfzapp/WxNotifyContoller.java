package sijibao.oa.jeesite.modules.intfz.web.intfzapp;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.LdapContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.realm.ldap.LdapContextFactory;
import org.apache.shiro.realm.ldap.LdapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.sijibao.oa.common.config.Global;
import com.sijibao.oa.common.utils.*;
import com.sijibao.oa.common.web.BaseController;
import com.sijibao.oa.modules.intfz.bean.BaseResp;
import com.sijibao.oa.modules.intfz.bean.RoleInfo;
import com.sijibao.oa.modules.intfz.bean.UserInfo;
import com.sijibao.oa.modules.intfz.enums.IntfzRs;
import com.sijibao.oa.modules.intfz.request.common.LoginReq;
import com.sijibao.oa.modules.intfz.request.common.LoginRequest;
import com.sijibao.oa.modules.intfz.request.common.ValidateCodeReq;
import com.sijibao.oa.modules.intfz.response.common.MyApplyCountResponse;
import com.sijibao.oa.modules.intfz.response.common.TodoCountResponse;
import com.sijibao.oa.modules.intfz.response.common.UserInfoResp;
import com.sijibao.oa.modules.intfz.response.common.UserNotifyResp;
import com.sijibao.oa.modules.intfz.service.common.WxNotifyService;
import com.sijibao.oa.modules.intfz.validator.AppAuthority;
import com.sijibao.oa.modules.sys.dao.PostInfoDao;
import com.sijibao.oa.modules.sys.entity.PostInfo;
import com.sijibao.oa.modules.sys.entity.Role;
import com.sijibao.oa.modules.sys.entity.User;
import com.sijibao.oa.modules.sys.service.SystemService;
import com.sijibao.oa.modules.sys.utils.LogUtils;
import com.sijibao.oa.modules.sys.utils.UserUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

//import com.sijibao.oa.sys.api.UserService;

@Controller
@RequestMapping(value = "wechat/notify")
@Api(value="基础服务", tags="基础服务")
public class WxNotifyContoller extends BaseController {

	@Autowired
	private WxNotifyService wxNotifyService;
//	@Autowired
//	private CloadService cloadService;
	@Autowired
	private PostInfoDao postInfoDao;
	private static final int smsOverTimeMins = 10; // 短信超时时间
	@Autowired
	private SystemService systemService;
//	@Autowired
//	private UserService userService;
	@Value("${ldap.base}")
	private String rootDN;

	private LdapContextFactory ldapContextFactory = SpringContextHolder.getBean("contextFactory");
	/**
	 * 同步OPENID，获取session
     * 已废弃，已废弃，已废弃。重要的事情说3遍！！！
	 * @param
	 * @param
	 * @param
	 * @throws IOException 
	 */
	@ResponseBody
	@RequestMapping(value = "bindOpenId")
	@ApiOperation(httpMethod = "POST", value = "绑定OPENID")
	public BaseResp<String> bindOpenId(@RequestBody ValidateCodeReq req,
									   @ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
									   @ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
									   HttpServletRequest request, HttpServletResponse response) throws IOException{
		WebUtils.initPre(request, response);
		String userName = req.getUserName();
		logger.debug("======bindOpenId[req]======openID:::"+sjboacert+";username:::"+userName+";");
		if("1".equals(clientType)){
			//1.验证用户名是否为空
			if(StringUtils.isBlank(userName)){
//				response.getWriter().write(JsonMapper.toJsonString(new BaseResp(IntfzRs.ERROR, "用户名不能为空", "")));
				return new BaseResp<String>(IntfzRs.ERROR, "用户名不能为空", "");
			}
			//2.验证验证码是否正确
			String valCode = JedisUtils.get("sjb_oa_wechat_validate_code_" + userName);
			if(valCode == null || "".equals(valCode)){
//				response.getWriter().write(JsonMapper.toJsonString(new BaseResp(IntfzRs.ERROR, "验证码验证失效，请重新发送", "")));
				return new BaseResp<String>(IntfzRs.ERROR, "验证码验证失效，请重新发送", "");
			}else{
				String[] array = String.valueOf(valCode).split(",");
				String smsNumber = array[0];
				String smsDate = array[1];
				//验证码核对
				if(StringUtils.isBlank(smsNumber) || !req.getValidateCode().toUpperCase().equals(smsNumber)){
//					response.getWriter().write(JsonMapper.toJsonString(new BaseResp(IntfzRs.ERROR, "验证码输入错误，请重试", "")));
					return new BaseResp<String>(IntfzRs.ERROR, "验证码输入错误，请重试", "");
				}else{
					JedisUtils.del("sjb_oa_wechat_validate_code_" + userName);//用完即销毁【验证通过销毁】
				}
				//验证码超时校验
				Date d = DateUtils.parse(smsDate, DateUtils.YYYY_MM_DD_HH_MM);
				long pastMins = DateUtils.pastMinutes(d);
				if(pastMins>smsOverTimeMins){
					JedisUtils.del("sjb_oa_wechat_validate_code_" + userName);//用完即销毁【验证超时销毁】
//					response.getWriter().write(JsonMapper.toJsonString(new BaseResp(IntfzRs.ERROR, "验证码已超时，请重试", "")));
					return new BaseResp<String>(IntfzRs.ERROR, "验证码已超时，请重试", "");
				}
			}
			//3.验证用户是否存在
			User user = UserUtils.getByPhone(userName);
			if(user == null){
//				response.getWriter().write(JsonMapper.toJsonString(new BaseResp(IntfzRs.ERROR, "用户不存在", "")));
				return new BaseResp<String>(IntfzRs.ERROR, "用户不存在", "");
			}
			//4.验证OPENID是否为空
			if(StringUtils.isBlank(sjboacert)){
//				response.getWriter().write(JsonMapper.toJsonString(new BaseResp(IntfzRs.ERROR, "OPENID不能为空", "")));
				return new BaseResp<String>(IntfzRs.ERROR, "OPENID不能为空", "");
			}
			//5.如果OPENID已存在且一致直接返回
			if(sjboacert.equals(user.getWechatId())){
//				response.getWriter().write(JsonMapper.toJsonString(new BaseResp(IntfzRs.SUCCESS, "ok", "")));
				return new BaseResp<String>(IntfzRs.SUCCESS, "ok", "");
			}
			//6.给用户绑定OPENID
			if(!wxNotifyService.bindOpenId(user, sjboacert)){
//				response.getWriter().write(JsonMapper.toJsonString(new BaseResp(IntfzRs.ERROR, "系统异常，请联系管理员", "")));
				return new BaseResp<String>(IntfzRs.ERROR, "系统异常，请联系管理员", "");
			}
		}else if("2".equals(clientType)){
			if(StringUtils.isBlank(sjboacert)){
//				response.getWriter().write(JsonMapper.toJsonString(new BaseResp(IntfzRs.ERROR, "SESSION不能为空", "")));
				return new BaseResp<String>(IntfzRs.ERROR, "SESSION不能为空", "");
			}
			//1.验证用户名是否为空
			if(StringUtils.isBlank(userName)){
//				response.getWriter().write(JsonMapper.toJsonString(new BaseResp(IntfzRs.ERROR, "用户名不能为空", "")));
				return new BaseResp<String>(IntfzRs.ERROR, "用户名不能为空", "");
			}
		}else{
//			response.getWriter().write(JsonMapper.toJsonString(new BaseResp(IntfzRs.ERROR, "clientType不能为空", "")));
			return new BaseResp<String>(IntfzRs.ERROR, "clientType不能为空", "");
		}
		return new BaseResp<String>(IntfzRs.SUCCESS, "ok", "");
	}
	
	/**
	 * 获取用户基本信息及菜单权限
	 * @param
	 * @throws IOException 
	 */
	@ResponseBody
	@RequestMapping(value = "isBind")
	@ApiOperation(httpMethod = "POST", value = "获取用户基本信息及菜单权限")
    @AppAuthority
	public BaseResp<UserInfoResp> isBind(
			@ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
			@ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
			HttpServletRequest request, HttpServletResponse response) throws IOException{
		WebUtils.initPre(request, response);
		if(StringUtils.isBlank(sjboacert)){
			return new BaseResp<UserInfoResp>(IntfzRs.ERROR, "OPENID不能为空", new UserInfoResp(false, new UserInfo()));
		}
		if(StringUtils.isBlank(clientType)){
			return new BaseResp<UserInfoResp>(IntfzRs.ERROR, "客户端类型不能为空", new UserInfoResp(false, new UserInfo()));
		}
		logger.debug("======WxNotifyController isBind[req]======openID:::"+sjboacert+";clientType:::"+clientType+";");
		User user = null;
		switch(clientType){
			case "1":
				//根据OPENID获取用户基本信息
				user = UserUtils.getByWechatId(sjboacert);
				break;
			case "2":
				//根据OPENID获取用户基本信息
				user = UserUtils.getByPhone(sjboacert);
				break;
			default:
				break;
		}
		//判断是否绑定
		UserInfoResp userInfoResp = new UserInfoResp();
		if(user == null){
			userInfoResp.setIsBind(false);
			userInfoResp.setUserInfo(new UserInfo());
		}else{
			userInfoResp.setIsBind(true);
			UserInfo userInfo = new UserInfo();
			userInfo.setUserName(user.getName());
			userInfo.setOfficeId(user.getOffice().getId());

			userInfo.setOfficeName(user.getOffice().getName());
			userInfo.setPhone(user.getMobile());
			userInfo.setLoginName(user.getLoginName());
			List<RoleInfo> roleInfos = Lists.newArrayList();
			for(Role role : user.getRoleList()){
				RoleInfo roleInfo = new RoleInfo();
				roleInfo.setName(role.getName());
				roleInfos.add(roleInfo);
			}
			userInfo.setRoleList(roleInfos);
			userInfo.setUserId(user.getId());
			userInfo.setUseable(user.getOffice().getUseable());
			userInfoResp.setUserInfo(userInfo);
			PostInfo postInfo = new PostInfo();
			if(StringUtils.isNotBlank(user.getPostIds())){
				postInfo = postInfoDao.get(user.getPostIds());
				userInfoResp.getUserInfo().setPostName(postInfo.getPostName());
				userInfoResp.getUserInfo().setPostCode(postInfo.getPostCode());
			}
		}
		
		//菜单权限
		Map<String,List<String>> resultMap = wxNotifyService.getPermissionList(user);
		if(resultMap != null && !resultMap.isEmpty()){
			userInfoResp.setPermissionList(resultMap.get("mainList")); //首页菜单权限
			userInfoResp.setListRecived(resultMap.get("list")); //列表权限
			userInfoResp.setListTools(resultMap.get("tools"));//小工具
			userInfoResp.setListSend(resultMap.get("list"));
		}


//		//待办条数
//		int todoCount = wxNotifyService.queryTodoListCount(user,userInfoResp);
//		userInfoResp.setTodoCount(todoCount);
//
//		//申请条数
//		int myApplyCount = wxNotifyService.queryMyApplyCount(user,userInfoResp);
//		userInfoResp.setMyApplyCount(myApplyCount);
//
//		//异常单列表
//		int errOrderCount = wxNotifyService.queryErrorStockOrderCount(user);
//		userInfoResp.setErrorStockOrderCount(errOrderCount);

		//appCode暂时值为1，用于强制登录，后期如果要做记录到user表中
		userInfoResp.setAppCode(1);
        logger.debug("======WxNotifyController isBind end======");
        return new BaseResp<UserInfoResp>(IntfzRs.SUCCESS, "ok", userInfoResp);
	}

    /**
     * 获取小红点提示信息：待办任务条数、申请条数、异常单数
     */
    @ResponseBody
    @RequestMapping(value = "queryNotifyTotalCount")
    @ApiOperation(httpMethod = "POST", value = "获取用户待办任务条数、申请条数、异常单数")
    @AppAuthority
    public BaseResp<UserNotifyResp> queryNotifyTotalCount(
			@ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
			@ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
			HttpServletRequest request, HttpServletResponse response) throws IOException{
        WebUtils.initPre(request, response);
        if(StringUtils.isBlank(sjboacert)){
            return new BaseResp<>(IntfzRs.ERROR, "OPENID不能为空", null);
        }
        if(StringUtils.isBlank(clientType)){
            return new BaseResp<>(IntfzRs.ERROR, "客户端类型不能为空", null);
        }
        logger.debug("======WxNotifyController queryNotifyTotalCount[req]======sjboacert:::"+sjboacert+";clientType:::"+clientType+";");
        User user = null;
        switch(clientType){
            case "1":
                //根据OPENID获取用户基本信息
                user = UserUtils.getByWechatId(sjboacert);
                break;
            case "2":
                //根据OPENID获取用户基本信息
                user = UserUtils.getByPhone(sjboacert);
                break;
            default:
                break;
        }

        UserInfoResp userInfoResp = new UserInfoResp();

        //菜单权限
        Map<String,List<String>> resultMap = wxNotifyService.getPermissionList(user);
        if(resultMap != null && !resultMap.isEmpty()){
            userInfoResp.setListRecived(resultMap.get("list")); //列表权限
        }

        UserNotifyResp resp = new UserNotifyResp();
        //待办条数
        int todoCount = wxNotifyService.queryTodoListCount(user,userInfoResp);
        resp.setTodoCount(todoCount);
        //申请条数
        int myApplyCount = wxNotifyService.queryMyApplyCount(user,userInfoResp);
        resp.setMyApplyCount(myApplyCount);
//        //异常单数量
//        int errOrderCount = wxNotifyService.queryErrorStockOrderCount(user);
//        resp.setErrorStockOrderCount(errOrderCount);

        return new BaseResp<>(IntfzRs.SUCCESS, "ok", resp);
    }

	/**
	 * 登入
	 * @param
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "login")
	@ApiOperation(httpMethod = "POST", value = "登录")
	public BaseResp<UserInfo> login(@RequestBody LoginReq req,
									HttpServletRequest request, HttpServletResponse response)throws IOException{
		WebUtils.initPre(request, response);
		if(StringUtils.isBlank(req.getLoginName())){
			return new BaseResp<UserInfo>(IntfzRs.ERROR, "用户名为空！", new UserInfo());
		}
		if(StringUtils.isBlank(req.getPassWord())){
			return new BaseResp<UserInfo>(IntfzRs.ERROR, "密码为空！", new UserInfo());
		}
		
		logger.debug("======login[req]======loginName:::"+req.getLoginName()+";");
		//如果登入名不存在
		User user = UserUtils.getByPhone(req.getLoginName());
		if(user != null){
//			return new BaseResp<UserInfo>(IntfzRs.ERROR, "用户名不存在！", new UserInfo());
			//校验密码是否正确
			if(!SystemService.validatePassword(req.getPassWord(),user.getPassword())){
				return new BaseResp<UserInfo>(IntfzRs.ERROR, "密码错误，请重新输入！", new UserInfo());
			}
		}else{
			try {
				user = this.queryUserByLdap(req.getLoginName(),req.getPassWord());
			} catch (NamingException e) {
				String msg = "登入名或密码错误！";
				throw new AuthenticationException(msg, e);
			} catch (Exception e){
				logger.info(e.getMessage());
			}
		}
		if(user == null){
			String msg = "登入名或密码错误！";
			return new BaseResp<UserInfo>(IntfzRs.ERROR, msg, null);
		}
		UserInfo userInfo = new UserInfo();
		userInfo.setUserName(user.getName());
		userInfo.setOfficeName(user.getOffice().getName());
		userInfo.setPhone(user.getMobile());
		List<RoleInfo> roleInfos = Lists.newArrayList();
		for(Role role : user.getRoleList()){
			RoleInfo roleInfo = new RoleInfo();
			roleInfo.setName(role.getName());
			roleInfos.add(roleInfo);
		}
		userInfo.setRoleList(roleInfos);
		return new BaseResp<UserInfo>(IntfzRs.SUCCESS, "ok", userInfo);
	}
	
	
	/**
	 * 获取登录验证码
	 * @param
	 * @throws IOException 
	 */
	@ResponseBody
	@RequestMapping(value = "getValidateCode")
	@ApiOperation(httpMethod = "POST", value = "获取登录验证码")
	public BaseResp<String> getValidateCode(@RequestBody ValidateCodeReq req,
											HttpServletRequest request, HttpServletResponse response) throws IOException{
		WebUtils.initPre(request, response);
		if(StringUtils.isBlank(req.getUserName())){
			return new BaseResp<String>(IntfzRs.ERROR, "手机号不能为空", "");
		}
		logger.debug("======getValidateCode[req]======UserName:::"+req.getUserName()+";");
		String valCodeOld = null;
		//验证该用户验证码是否已经产生，产生则返回旧的验证码，无需产生新的
		String valCode = JedisUtils.get("sjb_oa_wechat_validate_code_" + req.getUserName());
		if(!StringUtils.isBlank(valCode)){
			logger.debug("缓存历史验证码：：："+valCode);
			String[] array = String.valueOf(valCode).split(",");
			String smsNumber = array[0];
			String smsDate = array[1];
			if(!StringUtils.isBlank(smsNumber) && !StringUtils.isBlank(smsDate)){
				Date d = DateUtils.parse(smsDate, DateUtils.YYYY_MM_DD_HH_MM);
				long pastMins = DateUtils.pastMinutes(d);
				if(pastMins<smsOverTimeMins){
					valCodeOld = smsNumber;
				}
			}
		}
		valCode = valCodeOld==null?genValidateCode():valCodeOld;
//		String template = Global.getConfig("sms.template.1");
//		try {
//			//发送验证码短信通知用户
//			cloadService.CloadSMS(req.getUserName(), template.replace("[smsNumber]", valCode), 2);
//		} catch (RopException e) {
//			return new BaseResp<String>(IntfzRs.ERROR, "验证发发送失败，请重试", "");
//		}
		JedisUtils.set("sjb_oa_wechat_validate_code_" + req.getUserName(), 
				valCode+","+DateUtils.formatDate(new Date(), DateUtils.YYYY_MM_DD_HH_MM), smsOverTimeMins * 60);
		return new BaseResp<String>(IntfzRs.SUCCESS, "ok", valCode);
	}
	
	/**
	 * 验证码生成器
	 * @return
	 */
	private String genValidateCode(){
//		char[] codeSeq = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J',
//				'K', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
//				'X', 'Y', 'Z', '2', '3', '4', '5', '6', '7', '8', '9' };
		char[] codeSeq = {'1', '2', '3', '4', '5', '6', '7', '8', '9' };
		Random random = new Random();
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < 4; i++) {
			String r = String.valueOf(codeSeq[random.nextInt(codeSeq.length)]);
			s.append(r);
		}
		return s.toString();
	}
	
	/**
	 * 查询各类单据的待办任务条数
	 * @param sjboacert
	 * @param clientType
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "queryTodoCount")
	@ApiOperation(httpMethod = "POST", value = "获取待办任务条数")
    @AppAuthority
	public BaseResp<TodoCountResponse> queryTodoCount(@ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
													  @ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
													  HttpServletRequest request, HttpServletResponse response){
		WebUtils.initPre(request, response);
		User user = super.getCurrWxUser(sjboacert, clientType); //获取当前登录人信息
		Map<String,List<String>> resultMap = wxNotifyService.getPermissionList(user);
		List<String> list = resultMap.get("list");
		TodoCountResponse todoCount = new TodoCountResponse();
		todoCount.setExpenseFlowTodoCount(wxNotifyService.queryTodoExpenseCount(user,list)); //报销申请待办
		todoCount.setRecpFlowTodoCount(wxNotifyService.queryTodoRecpFlowCount(user,list)); //接待申请待办
		todoCount.setResourcesApplyTodoCount(wxNotifyService.queryTodoResourcesApplyCount(user,list)); //资源申请待办
		todoCount.setResourcesHandleTodoCount(wxNotifyService.queryTodoResourcesHandleCount(user,list)); //资源办理待办
		todoCount.setTravelFlowTodoCount(wxNotifyService.queryTodoTravelFlowCount(user,list)); //出差申请待办
		todoCount.setContractFlowTodoCount(wxNotifyService.queryTodoContractFlowCount(user,list)); //合同申请待办
		todoCount.setProjectApprovalCount(wxNotifyService.queryTodoProjectApprovalCountFlowCount(user,list)); //立项申请待办
//		todoCount.setDemandManagementMarketTodoCount(wxNotifyService.queryTodoDemandmanagementMarketCount(user));
//		todoCount.setDemandManagementImplementTodoCount(wxNotifyService.queryTodoEDemandmanagementImplementCount(user));
		return new BaseResp<TodoCountResponse>(IntfzRs.SUCCESS, "ok", todoCount);
	}
	
	
	/**
	 * 查询各类单据我的申请条数
	 * @param sjboacert
	 * @param clientType
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "queryMyApplyCount")
	@ApiOperation(httpMethod = "POST", value = "获取我的申请条数")
    @AppAuthority
	public BaseResp<MyApplyCountResponse> queryMyApplyCount(@ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
															@ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
															HttpServletRequest request, HttpServletResponse response){
		WebUtils.initPre(request, response);
		User user = super.getCurrWxUser(sjboacert, clientType); //获取当前登录人信息
		Map<String,List<String>> resultMap = wxNotifyService.getPermissionList(user);
		List<String> list = resultMap.get("list");
		MyApplyCountResponse applyCount = new MyApplyCountResponse();
		applyCount.setExpenseFlowApplyCount(wxNotifyService.queryMyApplyExpenseCount(user,list)); //报销申请
		applyCount.setRecpFlowApplyCount(wxNotifyService.queryMyApplyRecpFlowCount(user,list));  //接待申请
		applyCount.setResourcesApplyCount(wxNotifyService.queryMyApplyResourcesApplyCount(user,list)); //资源申请
		applyCount.setResourcesHandleCount(wxNotifyService.queryMyApplyResourcesHandleCount(user,list)); //资源办理
		applyCount.setTravelApplyCount(wxNotifyService.queryMyApplyTravelFlowCount(user,list)); //出差申请
		applyCount.setContractApplyCount(wxNotifyService.queryMyApplyContractFlowCount(user,list)); //合同申请
		applyCount.setProjectApprovalCount(wxNotifyService.queryMyApplyProjectApprovalFlowCount(user,list));
//		applyCount.setDemandManagementMarketApplyCount(wxNotifyService.queryMyApplyDemandManagementMarketCount(user));
//		applyCount.setDemandManagementImplementApplyCount(wxNotifyService.queryMyApplyDemandManagementImplementCount(user));
		return new BaseResp<MyApplyCountResponse>(IntfzRs.SUCCESS, "ok", applyCount);
	}

	/**
	 * 记录用户登录信息
	 * @param req
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "getLoginInfo")
	@ApiOperation(httpMethod = "POST", value = "记录用户登录信息")
    @AppAuthority
	public BaseResp<String> getLoginInfo(@RequestBody LoginRequest req,
										 @ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
										 @ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
										 HttpServletRequest request, HttpServletResponse response) throws IOException{
		WebUtils.initPre(request, response);
		if(StringUtils.isBlank(sjboacert)){
			return new BaseResp<String>(IntfzRs.ERROR, "OPENID不能为空", "");
		}
		if(StringUtils.isBlank(clientType)){
			return new BaseResp<String>(IntfzRs.ERROR, "客户端类型不能为空", "");
		}
		logger.debug("======isBind[req]======openID:::"+sjboacert+";clientType:::"+clientType+";");
		User user = super.getCurrWxUser(sjboacert, clientType); //获取当前登录人信息
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
		Date date = new Date();
		String loginInfo = "loginName=" + sjboacert +
				"&userName=" + user.getName() +
				"&loginDate=" + sdf.format(date) +
				"&appVersion=" + req.getAppVersion() +
				"&phoneType=" + req.getPhoneType() +
				"&deviceCode=" + req.getDeviceCode();
		request.setAttribute("loginInfo",loginInfo);
		LogUtils.saveLoginLogForApp(request, null,null,"系统登录",user);
		return new BaseResp<String>(IntfzRs.SUCCESS, "ok", "");
	}


	private User queryUserByLdap(String userName, String password) throws NamingException{

		LdapContext systemCtx = null;
		LdapContext ctx = null;
		try {
			// 使用系统配置的用户连接LDAP
			systemCtx = ldapContextFactory.getSystemLdapContext();
			SearchControls constraints = new SearchControls();
			constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);// 搜索范围是包括子树
			NamingEnumeration<SearchResult> results = systemCtx.search(rootDN, "cn=" + userName, constraints);
			if (results != null && !results.hasMore()) {
				throw new UnknownAccountException();
			} else {
				String mail = null;
				while (results.hasMore()) {
					SearchResult si = (SearchResult) results.next();
					userName = si.getName() + "," + rootDN;
					mail = si.getAttributes().get("mail").get(0).toString();
					logger.debug(si.getAttributes().get("mail").toString());
				}
//				logger.info("DN=[" + principal + "]");
				try {
					// 根据查询到的用户与输入的密码连接LDAP，用户密码正确才能连接
					ctx = ldapContextFactory.getLdapContext((Object) userName, (Object)password);
//					dealUser(userName, password);
				} catch (NamingException e) {
					throw new IncorrectCredentialsException("用户名或密码错误！");
				}
				// 校验用户名密码
				if (org.apache.commons.lang3.StringUtils.isNotBlank(mail)) {
					// 通过手机号匹配来登录
					User user = systemService.getUserByMail(mail);
//					Gson gson = new Gson();
					if (user != null) {
						if (Global.NO.equals(user.getLoginFlag())) {
							throw new AuthenticationException("msg:该已帐号禁止登录.");
						}
						return user;
					} else {
						// throw new AuthenticationException("msg:"+"系统错误");
						throw new AuthenticationException("msg:" + mail + "未找到账号");
					}
				} else {
					throw new AuthenticationException("msg:" + "ldap未配置用户的邮箱");
				}
			}
		} finally {
			// 关闭连接
			LdapUtils.closeContext(systemCtx);
			LdapUtils.closeContext(ctx);
		}
	}


}
