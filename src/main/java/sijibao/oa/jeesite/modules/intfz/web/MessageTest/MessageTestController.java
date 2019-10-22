package sijibao.oa.jeesite.modules.intfz.web.MessageTest;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.sijibao.base.common.provider.utils.IdGen;
import com.sijibao.oa.common.utils.CacheUtils;
import com.sijibao.oa.common.utils.DateUtils;
import com.sijibao.oa.common.utils.StringUtils;
import com.sijibao.oa.common.web.BaseController;
import com.sijibao.oa.message.api.IntfzMessageService;
import com.sijibao.oa.message.api.request.EmailMessageRequest;
import com.sijibao.oa.message.api.request.MesAppMessageRequest;
import com.sijibao.oa.message.api.request.SmsMessageRequest;
import com.sijibao.oa.modules.intfz.bean.BaseResp;
import com.sijibao.oa.modules.intfz.enums.IntfzRs;
import com.sijibao.oa.modules.oa.dao.TestAuditDao;
import com.sijibao.oa.modules.oa.entity.test.ContracthisMoveHis;
import com.sijibao.oa.modules.oa.entity.test.NeedMoveHis;
import com.sijibao.oa.modules.oa.entity.test.ProjectMoveHis;
import com.sijibao.oa.modules.oa.utils.CodeUtils;
import com.sijibao.oa.modules.sys.dao.UserDao;
import com.sijibao.oa.modules.sys.entity.User;
import com.sijibao.oa.modules.sys.utils.DictUtils;
import com.sijibao.oa.modules.sys.utils.UserUtils;
import com.sijibao.oa.office.api.IntfzWebContractHisService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 定时任务客户信息状态更新
 * @author wxb
 * @date 2018-12-04 15:49:50
 */
@Controller
@RequestMapping(value = "quartz/messageTest")
@Api(value="测试服务",tags="消息测试服务")
public class MessageTestController extends BaseController {
	
	
	@Autowired
	private IntfzMessageService intfzMessageService;
	@Autowired
	private UserDao userDao;
	@Autowired
	private TestAuditDao testAuditDao ;
	@Autowired
	private IntfzWebContractHisService intfzWebContractHisService;

	/**
	 * 合同过期自动更新 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "test1")
	@ResponseBody
    @ApiOperation(httpMethod = "POST", value = "测试异步发送消息")
	public BaseResp<String> sendAsync(MesAppMessageRequest req, HttpServletRequest request, HttpServletResponse response){
		logger.info("===================MessageTestController test 测试异步发送消息 start===================");
		req.setFromAppCode(DictUtils.getDictLabel("1", "app_code", ""));
		req.setToAppCode(DictUtils.getDictLabel("1", "app_code", ""));
		req.setType(0x1000);
        intfzMessageService.sendAsync(req);
		logger.info("===================MessageTestController  测试异步发送消息end===================");
		return new BaseResp<String>(IntfzRs.SUCCESS,"执行成功!","");
	}
	
	@RequestMapping(value = "test2")
	@ResponseBody
    @ApiOperation(httpMethod = "POST", value = "清除缓存")
	public BaseResp<String> removeCache(String key){
		logger.info("===================MessageTestController removeCache 清除缓存 start===================");
		/*清除缓存的所有用户数据*/
		List<String> ids = userDao.queryAllUserId();
		if(ids != null && ids.size() > 0){
			for (String s : ids) {
				UserUtils.clearCache(UserUtils.get(s));
				UserUtils.clearUserCacheListByOfficeId(UserUtils.get(s).getOffice().getId());
			}
		}

		/*清除系统级缓存*/
        CacheUtils.remove(CacheUtils.SYS_CACHE_PARAMS_MAP);//系统参数
        CacheUtils.remove(CacheUtils.SYS_CACHE_MENU_NAME_PATH_MAP);//菜单名称-路径映射
        CacheUtils.remove(CacheUtils.SYS_CACHE_LOGIN_FAIL_MAP);//登录计数
		CacheUtils.remove(CacheUtils.SYS_CACHE_DICT_MAP);//全部字典
        CacheUtils.remove(CacheUtils.SYS_CACHE_OFFICE_ALL_LIST);//全部部门
        CacheUtils.remove(CacheUtils.SYS_CACHE_POST_ALL_LIST);//全部岗位

        //按指定key清除
		if(StringUtils.isNotBlank(key)){
			CacheUtils.remove(key);
		}
		logger.info("===================MessageTestController removeCache 清除缓存 end===================");
		return new BaseResp<String>(IntfzRs.SUCCESS,"执行成功!","");
	}


	@RequestMapping(value = "test3")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "项目、合同、协作，批量移动表数据同步")

	public BaseResp<String> changeBiao(){
		logger.info("===================MessageTestController changeBiao 项目、合同、协作，批量移动表数据同步 start===================");
		//移动项目历史表
		List<ProjectMoveHis> pros = testAuditDao.queryProjectHi();
		for (ProjectMoveHis pro :pros) {
			if(StringUtils.isNotBlank(pro.getProjectIds())){
				List<ProjectMoveHis> lis = Lists.newArrayList();
				String[] ids = pro.getProjectIds().split(",");


				String code = CodeUtils.genCode("PM",DateUtils.getDate("yyyyMMdd"), 3);
				for(String proId :ids){
					ProjectMoveHis change = change(pro,ProjectMoveHis.class);
					change.setProjectId(proId);
					change.setpId(IdGen.uuid());
					change.setMoveCode(code);
					lis.add(change);
				}
				testAuditDao.insertProjectHis(lis);
			}
		}


		//移动合同历史表
		List<ContracthisMoveHis> cons = testAuditDao.queryContracthisMoveHi();
		for (ContracthisMoveHis con :cons) {
			if(StringUtils.isNotBlank(con.getContractHisIds())){
				List<ContracthisMoveHis> lis = Lists.newArrayList();
				String[] ids = con.getContractHisIds().split(",");
				String code = CodeUtils.genCode("CM", DateUtils.getDate("yyyyMMdd"), 3);
				for(String hisId :ids){
					ContracthisMoveHis change = change(con,ContracthisMoveHis.class);
					change.setCreateBy(UserUtils.get(con.getCreateBy().getId()));
					change.setContractHisId(hisId);
					change.setcId(IdGen.uuid());
					change.setMoveCode(code);
					lis.add(change);
				}
				testAuditDao.insertContracthisMoveHis(lis);
			}
		}


		//移动协作历史表
		List<NeedMoveHis> needs = testAuditDao.queryNeedMoveHi();
		for (NeedMoveHis need :needs) {
			if(StringUtils.isNotBlank(need.getNeedFlowIds())){
				List<NeedMoveHis> list = Lists.newArrayList();
				String[] ids = need.getNeedFlowIds().split(",");
				String code = CodeUtils.genCode("NM", DateUtils.getDate("yyyyMMdd"), 3);
				for (String nId : ids) {
					NeedMoveHis change = new NeedMoveHis();
					change.setnId(IdGen.uuid());
					change.setMoveCode(code);
					change.setNeedFlowId(nId);
					change.setPrincipal(need.getPrincipal());
					change.setCreateTime(new Date());
					change.setCreateBy(need.getUpdateBy());
					change.setCreateBy(need.getUpdateBy());
					change.setCreateBy(UserUtils.get(need.getCreateBy().getId()));
					list.add(change);
				}
				testAuditDao.insertNeedMoveHis(list);
			}
		}
		logger.info("===================MessageTestController changeBiao 项目、合同、协作，批量移动表数据同步 end===================");
		return new BaseResp<String>(IntfzRs.SUCCESS,"执行成功!","");
	}


	@RequestMapping(value = "test4",produces = "application/json")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "添加短信黑名单")
	public BaseResp<String> smsPhoneBlackListAdd(@RequestBody List<String> phones){
		logger.info("===================MessageTestController smsPhoneBlackListAdd 添加短信黑名单 start===================",phones);
		for(String phone : phones){
			SmsMessageRequest req = new SmsMessageRequest();
			req.setMobile(phone);
			req.setContent("");
			intfzMessageService.smsPhoneBlackListAdd(req);
		}
		logger.info("===================MessageTestController smsPhoneBlackListAdd 添加短信黑名单 end===================");
		return new BaseResp<String>(IntfzRs.SUCCESS,"执行成功!","");
	}
	@RequestMapping(value = "test5",produces = "application/json")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "移除短信黑名单")
	public BaseResp<String> smsPhoneBlackListDelete(@RequestBody List<String> phones){
		logger.info("===================MessageTestController smsPhoneBlackListAdd 移除短信黑名单 start===================",phones);
		for(String phone : phones){
			SmsMessageRequest req = new SmsMessageRequest();
			req.setMobile(phone);
			req.setContent("");
			intfzMessageService.smsPhoneBlackListDelete(req);
		}
		logger.info("===================MessageTestController smsPhoneBlackListAdd 移除短信黑名单 end===================");
		return new BaseResp<String>(IntfzRs.SUCCESS,"执行成功!","");
	}


	@RequestMapping(value = "test6")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "合同即将逾期列表插入")
	public BaseResp<String> ContractHisMessageJob(){
		logger.info("===================IntfzQuartzContractHisController ContractHisMessageJob 合同即将逾期列表插入 start===================");
		intfzWebContractHisService.ContractHisMessageJob();
		logger.info("===================IntfzQuartzContractHisController ContractHisMessageJob 合同即将逾期列表插入 end===================");
		return new BaseResp<String>(IntfzRs.SUCCESS,"执行成功!","");
	}

	@RequestMapping(value = "test7")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "发送短信")
	public BaseResp<String> sendSms(){
		logger.info("===================IntfzQuartzContractHisController ContractHisMessageJob 合同即将逾期列表插入 start===================");
		SmsMessageRequest req = new SmsMessageRequest();
		req.setMobile("17612759425");
		req.setContent("OA测试");
		intfzMessageService.sendSms(req);
		logger.info("===================IntfzQuartzContractHisController ContractHisMessageJob 合同即将逾期列表插入 end===================");
		return new BaseResp<String>(IntfzRs.SUCCESS,"执行成功!","");
	}

	@RequestMapping(value = "test8")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "发送短信")
	public BaseResp<String> sendMail(){
		EmailMessageRequest req = new EmailMessageRequest();
		User usr = UserUtils.getByLoginName("17612759425");
		req.setEmail(usr.getEmail());
		req.setContent("测试数据");
		req.setSubject("123");
		intfzMessageService.sendEmail(req);
		return new BaseResp<String>(IntfzRs.SUCCESS,"执行成功!","");
	}

	
}
