package sijibao.oa.jeesite.modules.intfz.web.LuckPersonel;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.sijibao.oa.common.utils.DateUtils;
import com.sijibao.oa.common.utils.JedisUtils;
import com.sijibao.oa.common.utils.StringUtils;
import com.sijibao.oa.common.web.BaseController;
import com.sijibao.oa.modules.intfz.bean.BaseResp;
import com.sijibao.oa.modules.intfz.enums.IntfzRs;
import com.sijibao.oa.modules.intfz.request.lucky.*;
import com.sijibao.oa.modules.intfz.response.lucky.LuckyQueryVoteInfoRes;
import com.sijibao.oa.modules.intfz.response.lucky.LuckyQueryWeightRes;
import com.sijibao.oa.modules.intfz.response.lucky.LuckyUserBindQueryRes;
import com.sijibao.oa.modules.intfz.response.lucky.LuckyWeightRes;
import com.sijibao.oa.modules.intfz.utils.SerializeUtil;
import com.sijibao.oa.modules.sys.entity.*;
import com.sijibao.oa.modules.sys.service.*;
import com.sijibao.oa.modules.sys.utils.DictUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisException;

/**
 * 年会Controller
 */
@Controller
@RequestMapping(value = "intfz/lucklyPerson")
@Api(value="a年会接口服务",tags="a年会接口服务")
public class LucklyPersonController extends BaseController {
	
	@Autowired
	private LuckyUserService luckyUserService;
	@Autowired
	private LuckyMessageService luckyMessageService;
	@Autowired
	private LuckyResultService luckyResultService;
	@Autowired
	private LuckyVoteService luckyVoteService;
	@Autowired
	private LuckyWeightService luckyWeightService;
	
//	private SmsService smsService;
//	private void init(){
//		if(smsService == null){
//			smsService = SpringContextHolder.getBean("smsService");
//		}
//	}
	/**
	 * 年会签到号码
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "signIn")
	@ResponseBody
	public BaseResp<String> signIn(HttpServletRequest request, HttpServletResponse response) {
		
		try{
			String openid = request.getParameter("openid");
			String username = request.getParameter("username");
			String type = request.getParameter("type");
			String phone = request.getParameter("phone");
			String imgUrl = request.getParameter("imgUrl");
			
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Access-Control-Allow-Methods", "POST,GET");
			response.setHeader("Access-Control-Allow-Headers", "NCZ");
			response.setHeader("Access-Control-Max-Age", "1728000");
			
			LuckyPerson lucky = new LuckyPerson();
			lucky.setOpenid(openid);
			lucky.setUsername(username);
			lucky.setGroup(type);
			lucky.setPhone(phone);
			lucky.setImg(imgUrl);
			long luckyNo = 0l;
			List<LuckyPerson> partList = null;
			try(Jedis jedis = JedisUtils.getResource()) {
				if (jedis.exists(StringUtils.getBytes("bams_participants"))) {
					byte[] data = jedis.get(StringUtils.getBytes("bams_participants"));
					partList = (List<LuckyPerson>) SerializeUtil.unserializeList(data);
				}
				boolean isExist = false;
				if(partList != null && partList.size() > 0){
					for(Object obj : partList){
						LuckyPerson temp = (LuckyPerson) obj;
						if(temp.compareTo(lucky) == 0){
							isExist = true;
							luckyNo = temp.getLuckyno();
							break;
						}
					}
				}else{
					partList = Lists.newArrayList();
				}
				
				if(!isExist){
					luckyNo = jedis.incr("bams_lucky-no");
					if(luckyNo == 0l){
						luckyNo = 1l;
						jedis.set("bams_lucky-no", "1");
						jedis.expire("bams_lucky-no", 86400);
					}
					lucky.setLuckyno(luckyNo);
					partList.add(lucky);
					jedis.del(StringUtils.getBytes("bams_participants"));
					jedis.set(StringUtils.getBytes("bams_participants"), SerializeUtil.serializeList(partList));  
					jedis.expire(StringUtils.getBytes("bams_participants"), 86400);
				}else{
					logger.info(lucky.toString() + ":::::exist luckyNo:"+luckyNo);
					return new BaseResp<String>(IntfzRs.ERROR, "重复", "");
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(lucky.toString() + ":::::fail luckyNo:"+luckyNo);
				return new BaseResp<String>(IntfzRs.ERROR, "失败", "");
			} 
			
			logger.info(lucky.toString() + ":::::success luckyNo:"+luckyNo);
			return new BaseResp<String>(IntfzRs.SUCCESS, "ok", String.valueOf(luckyNo));
		} catch (Exception e){
			e.printStackTrace();
			return new BaseResp<String>(IntfzRs.ERROR, "失败", "");
		}
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "parters")
	@ResponseBody
	public BaseResp<String> parters(HttpServletRequest request, HttpServletResponse response){
		List<LuckyPerson> partList = Lists.newArrayList();
		try(Jedis jedis = JedisUtils.getResource();) {
			if (jedis.exists(StringUtils.getBytes("bams_participants"))) {
				byte[] data = jedis.get(StringUtils.getBytes("bams_participants"));
				partList = (List<LuckyPerson>) SerializeUtil.unserializeList(data);
			}
			String luckyNo = request.getParameter("luckyNo");
			if(!StringUtils.isBlank(luckyNo)){
				for(Object obj : partList){
					logger.info(obj.toString());
					LuckyPerson temp = (LuckyPerson) obj;
					Long luckyNum = temp.getLuckyno();
					if(luckyNum.equals(Long.valueOf(luckyNo))){
						return new BaseResp<String>(IntfzRs.SUCCESS, "ok", temp.toString());
					}
				}
			}
		} catch (JedisException e) {
			logger.error(e.getMessage(),e);
		}
		return new BaseResp<String>(IntfzRs.SUCCESS, "ok", "人数：" +partList.size());
	}
	
	@RequestMapping(value = "removeCache")
	@ResponseBody
	public BaseResp<String> removeCache(HttpServletRequest request, HttpServletResponse response){
		
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST,GET");
		response.setHeader("Access-Control-Allow-Headers", "NCZ");
		response.setHeader("Access-Control-Max-Age", "1728000");
		
		JedisUtils.delObject("lucky-no");
		JedisUtils.delObject("participants");
		return new BaseResp<String>(IntfzRs.SUCCESS,"ok","幸运编号清空成功！");
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "initData")
	@ResponseBody
	public BaseResp<String> initData(HttpServletRequest request, HttpServletResponse response){
		
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST,GET");
		response.setHeader("Access-Control-Allow-Headers", "NCZ");
		response.setHeader("Access-Control-Max-Age", "1728000");
		try(Jedis jedis = JedisUtils.getResource()){
			for(int i = 0; i < 130; i++){
				LuckyPerson lucky = new LuckyPerson();
				lucky.setOpenid(i+"");
				lucky.setUsername("冬"+i);
				lucky.setGroup("0"+(i%7+1));
				lucky.setPhone(String.valueOf(Long.valueOf("18800000000") + i));
				long luckyNo = 0l;
				List<LuckyPerson> partList = null;
				
				if (jedis.exists(StringUtils.getBytes("bams_participants"))) {
					byte[] data = jedis.get(StringUtils.getBytes("bams_participants"));
					partList = (List<LuckyPerson>) SerializeUtil.unserializeList(data);
				}
				boolean isExist = false;
				if(partList != null && partList.size() > 0){
					for(Object obj : partList){
						LuckyPerson temp = (LuckyPerson) obj;
						if(temp.compareTo(lucky) == 0){
							isExist = true;
							luckyNo = temp.getLuckyno();
							break;
						}
					}
				}else{
					partList = Lists.newArrayList();
				}
				
				if(!isExist){
					luckyNo = jedis.incr("bams_lucky-no");
					if(luckyNo == 0l){
						luckyNo = 1l;
						jedis.set("bams_lucky-no", "1");
						jedis.expire("bams_lucky-no", 86400);
					}
					lucky.setLuckyno(luckyNo);
					partList.add(lucky);
					jedis.del(StringUtils.getBytes("bams_participants"));
					jedis.set(StringUtils.getBytes("bams_participants"), SerializeUtil.serializeList(partList));  
					jedis.expire(StringUtils.getBytes("bams_participants"), 86400);
				}else{
					logger.info(lucky.toString() + ":::::exist luckyNo:"+luckyNo);
				}
				logger.info(lucky.toString() + ":::::success luckyNo:"+luckyNo);
			}
		}catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return new BaseResp<String>(IntfzRs.SUCCESS,"ok","初始化数据成功！");
	}
	
//	/**
//	 * 消息入库
//	 */
//	@SuppressWarnings("unchecked")
//	public BaseResp<String> messageSave(Message message,HttpServletRequest request, HttpServletResponse response){
//		if(message == null || StringUtils.isNotBlank(message.getOpenId())){
//			return new BaseResp<String>(IntfzRs.ERROR,"消息保存失败,openId不能为空","");
//		}
//		try(Jedis jedis = JedisUtils.getResource()){
//			List<Message> messageList = new ArrayList<Message>();
//			if (jedis.exists(StringUtils.getBytes("bams_barrage"))) {
//				byte[] data = jedis.get(StringUtils.getBytes("bams_barrage"));
//				messageList = (List<Message>) SerializeUtil.unserializeList(data);
//			}
//			messageList.add(message);
//			jedis.set(StringUtils.getBytes("bams_barrage"), SerializeUtil.serializeList(messageList));
//		}
//		return new BaseResp<String>(IntfzRs.SUCCESS,"ok","成功");
//	}
	
	
	/**
	 * 帐号绑定
	 */
	@RequestMapping(value = "saveUser")
	@ApiOperation(httpMethod = "POST", value = "账号绑定")
	@ResponseBody
	public BaseResp<String> saveUser(@RequestBody LuckyUserReq req, HttpServletRequest request, HttpServletResponse response){
		
		if(req == null){
			return new BaseResp<String>(IntfzRs.ERROR,"用户信息不能为空",""); 
		}
		if(StringUtils.isBlank(req.getOpenId())){
			return new BaseResp<String>(IntfzRs.ERROR,"用户openId不能为空","");
		}
		if(StringUtils.isBlank(req.getUserName())){
			return new BaseResp<String>(IntfzRs.ERROR,"userName不能为空","");
		}
		if(StringUtils.isBlank(req.getPhone())){
			return new BaseResp<String>(IntfzRs.ERROR,"phone不能为空","");
		}
		
		LuckyUser luckyUser = change(req,LuckyUser.class);
		List<LuckyUser> queryList = luckyUserService.findList(luckyUser);
		if(queryList.size() > 0){
			return new BaseResp<String>(IntfzRs.ERROR,"当前用户已绑定，不允许重复绑定",""); 
		}
		
		luckyUserService.save(luckyUser);
		return new BaseResp<String>(IntfzRs.SUCCESS,"ok","绑定成功!");
	}
	
	
	/**
	 * 帐号绑定(新)
	 */
	@RequestMapping(value = "saveUserNew")
	@ApiOperation(httpMethod = "POST", value = "账号绑定(新)")
	@ResponseBody
	public BaseResp<String> saveUserNew(@RequestBody LuckyUserReq req, HttpServletRequest request, HttpServletResponse response){
		
		if(req == null){
			return new BaseResp<String>(IntfzRs.ERROR,"用户信息不能为空",""); 
		}
//		if(StringUtils.isBlank(req.getOpenId())){
//			return new BaseResp<String>(IntfzRs.ERROR,"用户openId不能为空","");
//		}
		if(StringUtils.isBlank(req.getUserName())){
			return new BaseResp<String>(IntfzRs.ERROR,"userName不能为空","");
		}
		if(StringUtils.isBlank(req.getPhone())){
			return new BaseResp<String>(IntfzRs.ERROR,"phone不能为空","");
		}
		
		LuckyUser phoneLuckyUser = new LuckyUser();
		phoneLuckyUser.setPhone(req.getPhone());
		List<LuckyUser> queryList1 = luckyUserService.findList(phoneLuckyUser);
		if(queryList1.size() > 0){
			return new BaseResp<String>(IntfzRs.ERROR,"当前用户已绑定，不允许重复绑定",""); 
		}
		
//		LuckyUser nameLuckyUser = new LuckyUser();
//		nameLuckyUser.setUserName(req.getUserName());
//		List<LuckyUser> queryList2 = luckyUserService.findList(nameLuckyUser);
//		if(queryList2.size() > 0){
//			return new BaseResp<String>(IntfzRs.ERROR,"当前用户已绑定，不允许重复绑定",""); 
//		}
		
		LuckyUser luckyUser = change(req,LuckyUser.class);
		luckyUserService.save(luckyUser);
		return new BaseResp<String>(IntfzRs.SUCCESS,"ok","绑定成功!");
	}
	
	
	
	
	@RequestMapping(value = "queryUserIsBind")
	@ApiOperation(httpMethod = "POST", value = "查询用户是否账号绑定")
	@ResponseBody
	public BaseResp<LuckyUserBindQueryRes> queryUserIsBind(@RequestBody LuckyActiveQueryReq req, HttpServletRequest request, HttpServletResponse response){
		logger.info("==============queryUserIsBind===========req:{}", JSON.toJSONString(req));
		if(req == null){
			return new BaseResp<LuckyUserBindQueryRes>(IntfzRs.ERROR,"用户信息不能为空",null); 
		}
		if(StringUtils.isBlank(req.getOpenId())){
			return new BaseResp<LuckyUserBindQueryRes>(IntfzRs.ERROR,"用户openId不能为空",null);
		}
		LuckyUserBindQueryRes res = new LuckyUserBindQueryRes();
		LuckyUser luckyUser = change(req,LuckyUser.class);
		List<LuckyUser> queryList = luckyUserService.findList(luckyUser);
		if(queryList.size() > 0){
			if(StringUtils.isNotBlank(queryList.get(0).getPhone())){
				res.setIsBind(1);
				return new BaseResp<LuckyUserBindQueryRes>(IntfzRs.SUCCESS,"ok",res); //已绑定 
			}else{
				res.setIsBind(0);
				return new BaseResp<LuckyUserBindQueryRes>(IntfzRs.SUCCESS,"ok",res); //未绑定
			}
		}else{
			res.setIsBind(0);
			return new BaseResp<LuckyUserBindQueryRes>(IntfzRs.SUCCESS,"ok",res); //未绑定
		}
	}
	
	/**
	 * 查询用户是否账号绑定(H5)
	 * @param req
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "queryUserIsBindForNew")
	@ApiOperation(httpMethod = "POST", value = "查询用户是否账号绑定(H5)")
	@ResponseBody
	public BaseResp<LuckyUserBindQueryRes> queryUserIsBindNew(@RequestBody LuckyActiveQueryReq req, HttpServletRequest request, HttpServletResponse response){
		logger.info("==============queryUserIsBind===========req:{}", JSON.toJSONString(req));
		if(req == null){
			return new BaseResp<LuckyUserBindQueryRes>(IntfzRs.ERROR,"用户信息不能为空",null); 
		}
		if(StringUtils.isBlank(req.getPhone())){
			return new BaseResp<LuckyUserBindQueryRes>(IntfzRs.ERROR,"手机号不能为空",null);
		}
		LuckyUserBindQueryRes res = new LuckyUserBindQueryRes();
		LuckyUser luckyUser = new LuckyUser();
		luckyUser.setPhone(req.getPhone());
		List<LuckyUser> queryList = luckyUserService.findList(luckyUser);
		if(queryList.size() > 0){
			if(StringUtils.isNotBlank(queryList.get(0).getPhone())){
				res.setIsBind(1);
				return new BaseResp<LuckyUserBindQueryRes>(IntfzRs.SUCCESS,"ok",res); //已绑定 
			}else{
				res.setIsBind(0);
				return new BaseResp<LuckyUserBindQueryRes>(IntfzRs.SUCCESS,"ok",res); //未绑定
			}
		}else{
			res.setIsBind(0);
			return new BaseResp<LuckyUserBindQueryRes>(IntfzRs.SUCCESS,"ok",res); //未绑定
		}
	}
	
	/**
	 * 消息入库
	 */
	@RequestMapping(value = "saveMessage")
	@ApiOperation(httpMethod = "POST", value = "消息入库")
	@ResponseBody
	public BaseResp<String> saveMessage(@RequestBody LuckyMessageReq req, HttpServletRequest request, HttpServletResponse response){
		if(req == null){
			return new BaseResp<String>(IntfzRs.ERROR,"消息信息不能为空",""); 
		}
		if(StringUtils.isBlank(req.getOpenId())){
			return new BaseResp<String>(IntfzRs.ERROR,"openId不能为空","");
		}
		if(StringUtils.isBlank(req.getMessageText())){
			return new BaseResp<String>(IntfzRs.ERROR,"消息内容不能为空","");
		}
		if(req.getDateTimes() == 0l){
			return new BaseResp<String>(IntfzRs.ERROR,"时间不能为空","");
		}
		LuckyMessage luckyMessage = new LuckyMessage();
		luckyMessage.setDateTime(DateUtils.parseDate(req.getDateTimes()));
		luckyMessage.setOpenId(req.getOpenId());
		luckyMessage.setMessageText(req.getMessageText());
		luckyMessageService.save(luckyMessage);
		return new BaseResp<String>(IntfzRs.SUCCESS,"ok","消息保存成功!");
	}
	
	
	/**
	 * 保存活动参与状态
	 */
	@RequestMapping(value = "saveActive")
	@ApiOperation(httpMethod = "POST", value = "活动状态保存")
	@ResponseBody
	public BaseResp<String> saveActive(@RequestBody LuckyActiveReq req, HttpServletRequest request, HttpServletResponse response){
		if(req == null){
			return new BaseResp<String>(IntfzRs.ERROR,"活动信息不能为空",""); 
		}
		logger.info("=======saveActive req{}:", JSON.toJSONString(req));
		LuckyActive luckyActive = new LuckyActive();
		luckyActive.setCreateDate(DateUtils.parseDate(luckyActive.getStartDate()));
		try(Jedis jedis = JedisUtils.getResource()) {
			if(jedis.exists("lucky_active")){
				return new BaseResp<String>(IntfzRs.ERROR,"活动已开始,请勿重复开始!","");
			}
			jedis.set("lucky_active", req.getStartDate());
		}
		
		return new BaseResp<String>(IntfzRs.SUCCESS,"ok","保存成功!");
	}
	
	@RequestMapping(value = "deleteActive")
	@ApiOperation(httpMethod = "POST", value = "活动状态删除")
	@ResponseBody
	public BaseResp<String> deleteActive(HttpServletRequest request, HttpServletResponse response){
		try(Jedis jedis = JedisUtils.getResource()) {
			jedis.del("lucky_active");
		}
		return new BaseResp<String>(IntfzRs.SUCCESS,"ok","删除成功!");
	}
	
	/**
	 * 查询活跃状态
	 */
	@RequestMapping(value = "queryActive")
	@ApiOperation(httpMethod = "POST", value = "查询活动状态")
	@ResponseBody
	public BaseResp<LuckyActive> queryActive(@RequestBody LuckyActiveQueryReq req, HttpServletRequest request, HttpServletResponse response){
		if(StringUtils.isBlank(req.getOpenId())){
			return new BaseResp<LuckyActive>(IntfzRs.ERROR,"openId不能为空",null);
		}
		LuckyActive result = new LuckyActive();
		
		try(Jedis jedis = JedisUtils.getResource()) {
			if(!jedis.exists("lucky_active")){
				result.setActiveStatus(0); //未开始
				result.setIsJoin(0); //未答题
			}else{
				String date = jedis.get("lucky_active");
				logger.info("========queryActive date:{}",date);
				String redisActive = date;
				logger.info("========queryActive redisActive:{}",redisActive);
				result.setOpenId(req.getOpenId());
				Integer timeLong = Integer.parseInt(DictUtils.getDictValue("lucky_time_long", "lucky_time_long", "1440"));
				if(DateUtils.pastMinutes(DateUtils.parseDate(redisActive)) > timeLong){ //活动已结束
					result.setActiveStatus(2); //已结束
				}else{
					result.setActiveStatus(1); //进行中
				}
				
				//查询是否答题
				LuckyResult r = new LuckyResult();
				r.setOpenId(req.getOpenId());
				List<LuckyResult> resultList = luckyResultService.findList(r);
				if(resultList != null && resultList.size() > 0){
					result.setIsJoin(2); //已完成答题
				}else{
					result.setIsJoin(0); //未答题
				}
			}
		}
		return new BaseResp<LuckyActive>(IntfzRs.SUCCESS,"ok",result);
	}
	
	
	/**
	 * 保存答题名次
	 */
	@RequestMapping(value = "saveResult")
	@ApiOperation(httpMethod = "POST", value = "保存答题信息")
	@ResponseBody
	public BaseResp<String> saveResult(@RequestBody LuckyResultReq req, HttpServletRequest request, HttpServletResponse response){
		
		if(req == null){
			return new BaseResp<String>(IntfzRs.ERROR,"答题信息不能为空","");  
		}
		
		if(StringUtils.isBlank(req.getOpenId())){
			return new BaseResp<String>(IntfzRs.ERROR,"openid不能为空","");
		}
		if(req.getAllNum() == null){
			return new BaseResp<String>(IntfzRs.ERROR,"答题个数不能为空","");
		}
		if(req.getRightNum() == null){
			return new BaseResp<String>(IntfzRs.ERROR,"答对个数不能为空","");
		}
		if(req.getTimeLong() == null){
			return new BaseResp<String>(IntfzRs.ERROR,"答题时长不能为空","");
		}
		
		LuckyResult luckyResult = change(req,LuckyResult.class);
		List<LuckyResult> resultList = luckyResultService.findList(luckyResult);
		if(resultList.size() > 0){
			return new BaseResp<String>(IntfzRs.ERROR,"该用户答题结果已提交，无需重复提交!","");
		}
		luckyResultService.save(luckyResult);
		return new BaseResp<String>(IntfzRs.SUCCESS,"ok","保存成功!");
	}
	
	/**
	 * 查询用户名次
	 * @param luckyResult
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "queryUserResult")
	@ApiOperation(httpMethod = "POST", value = "查询用户名次")
	@ResponseBody
	public BaseResp<LuckyUserResult> queryUserResult(@RequestBody LuckyActiveQueryReq req, HttpServletRequest request, HttpServletResponse response){
		
		if(StringUtils.isBlank(req.getOpenId())){
			return new BaseResp<LuckyUserResult>(IntfzRs.ERROR,"openId不能为空",null);
		}
		
		LuckyUserResult result = new LuckyUserResult();
		
		LuckyUser user = new LuckyUser();
		user.setOpenId(req.getOpenId());
		List<LuckyUser> userList = luckyUserService.findList(user);
		if(userList == null || userList.size() == 0){
			return new BaseResp<LuckyUserResult>(IntfzRs.ERROR,"当前用户不存在",null);
		}
		user = userList.get(0);
		result.setOpenId(user.getOpenId());
		result.setImgUrl(user.getImgUrl());
		result.setPhone(user.getPhone());
		result.setUserName(user.getUserName());
		
		int k = 0;
		List<LuckyResult> resultList = luckyResultService.queryResultDesc();
		if(resultList != null && resultList.size() > 0){
			for(int i = 0;i<resultList.size();i++){
				LuckyResult r = resultList.get(i);
				if(req.getOpenId().equals(r.getOpenId())){ //匹配到用户
					k++;
					result.setRank(i+1); //排名
					result.setAllNum(r.getAllNum());
					result.setRightNum(r.getRightNum());
					result.setTimeLong(r.getTimeLong());
					break;
				}
			}
		}
		
		if(k == 0){
			return new BaseResp<LuckyUserResult>(IntfzRs.ERROR,"当前用户答题记录不存在",null);
		}
		logger.info("==============queryUserResult result:{}", JSON.toJSON(result));
		return new BaseResp<LuckyUserResult>(IntfzRs.SUCCESS,"ok",result);
	}
	
	
	
	/**
	 * 查询用户名次TOP10
	 * @param luckyResult
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "queryUserResultTopTen")
	@ApiOperation(httpMethod = "POST", value = "查询用户名次TOP10")
	@ResponseBody
	public BaseResp<List<LuckyUserResult>> queryUserResultTopTen(@RequestBody LuckyActiveQueryReq req, HttpServletRequest request, HttpServletResponse response){
		
		LuckyUser user = new LuckyUser();
		user.setOpenId(req.getOpenId());
		List<LuckyUser> userList = luckyUserService.findList(user);
		if(userList == null || userList.size() == 0){
			return new BaseResp<List<LuckyUserResult>>(IntfzRs.ERROR,"当前用户不存在",null);
		}
		
		List<LuckyUserResult> resultList = new ArrayList<LuckyUserResult>();
		List<LuckyResult> rList = luckyResultService.queryResultDesc();
		if(rList != null && rList.size() > 0){
			for(int i = 0;i<rList.size();i++){
				LuckyResult r = rList.get(i);
				LuckyUserResult result = new LuckyUserResult();
				result.setOpenId(r.getOpenId());
				result.setImgUrl(r.getImgUrl());
				result.setPhone(r.getPhone());
				result.setUserName(r.getUserName());
				result.setRank(i+1); //排名
				result.setAllNum(r.getAllNum());
				result.setRightNum(r.getRightNum());
				result.setTimeLong(r.getTimeLong());
				resultList.add(result);
				if(i==9){
					break;
				}
			}
		}
		return new BaseResp<List<LuckyUserResult>>(IntfzRs.SUCCESS,"ok",resultList);
	}
	
	/**
	 * 保存投票结果
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "saveVoteInfo")
	@ApiOperation(httpMethod = "POST", value = "保存投票结果")
	@ResponseBody
	public BaseResp<String> saveVoteInfo(@RequestBody LuckyVoteInfoReq luckyVoteInfoReq, HttpServletRequest request, HttpServletResponse response){
		
		logger.info("==========saveVoteInfo=========req:{}", JSON.toJSONString(luckyVoteInfoReq));
		
		if(luckyVoteInfoReq == null || luckyVoteInfoReq.getSaveVoteInfoList() == null || luckyVoteInfoReq.getSaveVoteInfoList().size() == 0){
			return new BaseResp<String>(IntfzRs.ERROR,"投票失败，投票信息不能为空!","");
		}
		
		for(LuckySaveVoteInfoReq req:luckyVoteInfoReq.getSaveVoteInfoList()){
			LuckyVote luckyVote = new LuckyVote();
			luckyVote.setShowId(req.getShowId());
			luckyVote.setShowName(req.getShowName());
			luckyVote.setShowType(req.getShowType());
			luckyVoteService.save(luckyVote);
		}
		
		logger.info("==========saveVoteInfo end=========");
		return new BaseResp<String>(IntfzRs.SUCCESS,"ok","投票成功!");
	}
	
	/**
	 * 查询投票结果
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "queryVoteResultInfo")
	@ApiOperation(httpMethod = "POST", value = "查询投票结果")
	@ResponseBody
	public BaseResp<List<LuckyQueryVoteInfoRes>> queryVoteResultInfo(HttpServletRequest request, HttpServletResponse response){
		logger.info("==========queryVoteResultInfo start===========");
		
		List<LuckyQueryVoteInfoRes> resultList = new ArrayList<LuckyQueryVoteInfoRes>();
		
		List<LuckyVote> voteList = luckyVoteService.queryVoteResultList(new LuckyVote());
		if(voteList != null && voteList.size() > 0){
			for(LuckyVote luckyVote:voteList){
				LuckyQueryVoteInfoRes res = new LuckyQueryVoteInfoRes();
				res.setShowId(luckyVote.getShowId());
				res.setShowName(luckyVote.getShowName());
				res.setShowType(luckyVote.getShowType());
				res.setVoteNum(luckyVote.getVoteNum());
				resultList.add(res);
			}
		}
		
		logger.info("==========queryVoteResultInfo end===========res:{}", JSON.toJSONString(resultList));
		return new BaseResp<List<LuckyQueryVoteInfoRes>>(IntfzRs.SUCCESS,"ok",resultList);
	}
	
	/**
	 * 保存体重信息
	 * @param req
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "saveWeight")
	@ApiOperation(httpMethod = "POST", value = "保存体重信息")
	@ResponseBody
	public BaseResp<String> saveWeight(@RequestBody LuckyWeightReq req, HttpServletRequest request, HttpServletResponse response){
		if(req == null){
			return new BaseResp<String>(IntfzRs.ERROR,"保存失败，体重信息不能为空!","");
		}
		if(StringUtils.isBlank(req.getName())){
			return new BaseResp<String>(IntfzRs.ERROR,"保存失败，姓名不能为空!","");
		}
		LuckyWeight query = new LuckyWeight();
		query.setName(req.getName());
		LuckyWeight luckyWeight = luckyWeightService.getForName(query);
		if(luckyWeight == null){
			luckyWeight = new LuckyWeight();
		}
		luckyWeight.setName(req.getName());
		luckyWeight.setWeight(req.getWeight());
		luckyWeight.setHigh(req.getHeight());
		luckyWeightService.save(luckyWeight);
		return new BaseResp<String>(IntfzRs.SUCCESS,"ok","体重信息保存成功!");
	}
	
	/**
	 * 查询体重信息
	 * @param req
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "queryWeightList")
	@ApiOperation(httpMethod = "POST", value = "查询体重信息")
	@ResponseBody
	public BaseResp<LuckyQueryWeightRes> queryWeightList(@RequestBody LuckyQueryWeightReq req, HttpServletRequest request, HttpServletResponse response){
		if(req == null){
			return new BaseResp<LuckyQueryWeightRes>(IntfzRs.ERROR,"查询失败，查询类型不能为空!",null);
		}
		if(req.getOrderByType() == null){
			return new BaseResp<LuckyQueryWeightRes>(IntfzRs.ERROR,"查询失败，查询类型不能为空!",null);
		}
		LuckyQueryWeightRes res = new LuckyQueryWeightRes();
		
		List<LuckyWeightRes> luckyWeightResList = new ArrayList<LuckyWeightRes>();
		
		LuckyWeight luckyWeight = new LuckyWeight();
		luckyWeight.setOrderByType(String.valueOf(req.getOrderByType()));
		List<LuckyWeight> luckyWeightList = luckyWeightService.findList(luckyWeight);
		
		if(luckyWeightList != null && luckyWeightList.size() > 0){
			for(LuckyWeight l:luckyWeightList){
				LuckyWeightRes lr = new LuckyWeightRes();
				lr.setName(l.getName());
				lr.setWeight(l.getWeight());
				lr.setHeight(l.getHigh());
				luckyWeightResList.add(lr);
			}
		}
		res.setLuckyWeightList(luckyWeightResList);
		return new BaseResp<LuckyQueryWeightRes>(IntfzRs.SUCCESS,"ok",res);
	}
	
	/**
	 * 删除所有签到用户信息
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "deleteUser")
	@ApiOperation(httpMethod = "POST", value = "删除所有签到用户信息")
	@ResponseBody
	public BaseResp<String> deleteUser(HttpServletRequest request, HttpServletResponse response){
		luckyUserService.deleteAll();
		return new BaseResp<String>(IntfzRs.SUCCESS,"ok","删除用户信息成功!");
	}
	
	/**
	 * 删除所有答题信息
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "deleteResult")
	@ApiOperation(httpMethod = "POST", value = "删除所有答题信息")
	@ResponseBody
	public BaseResp<String> deleteResult(HttpServletRequest request, HttpServletResponse response){
		luckyResultService.deleteAll();
		return new BaseResp<String>(IntfzRs.SUCCESS,"ok","删除答题信息成功!");
	}
	
	/**
	 * 删除所有体重信息
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "deleteWeight")
	@ApiOperation(httpMethod = "POST", value = "删除所有体重信息")
	@ResponseBody
	public BaseResp<String> deleteWeight(HttpServletRequest request, HttpServletResponse response){
		luckyWeightService.deleteAll();
		return new BaseResp<String>(IntfzRs.SUCCESS,"ok","删除体重信息成功!");
	}
	
	/**
	 * 删除所有投票信息
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "deleteVote")
	@ApiOperation(httpMethod = "POST", value = "删除所有投票信息")
	@ResponseBody
	public BaseResp<String> deleteVote(HttpServletRequest request, HttpServletResponse response){
		luckyVoteService.deleteAll();
		return new BaseResp<String>(IntfzRs.SUCCESS,"ok","删除投票信息成功!");
	}
	
	
	/**
	 * 查询所有签到用户信息
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "queryAllUserInfo")
	@ApiOperation(httpMethod = "POST", value = "查询所有签到用户信息")
	@ResponseBody
	public BaseResp<List<LuckyUserResult>> queryAllUserInfo(HttpServletRequest request, HttpServletResponse response){
		List<LuckyUserResult> resultList = new ArrayList<LuckyUserResult>();
		
		List<LuckyUser> userList = luckyUserService.findList(new LuckyUser());
		if(userList != null && userList.size() > 0){
			for(LuckyUser l:userList){
				LuckyUserResult lr = new LuckyUserResult();
				lr.setOpenId(l.getOpenId());
				lr.setPhone(l.getPhone());
				lr.setUserName(l.getUserName());
				lr.setImgUrl(l.getImgUrl());
				resultList.add(lr);
			}
		}
		return new BaseResp<List<LuckyUserResult>>(IntfzRs.SUCCESS,"ok",resultList);
	}
	
//	/**
//	 * 查询弹幕是否开放
//	 * @return
//	 */
//	@RequestMapping(value = "queryBarrageIsStart")
//	@ApiOperation(httpMethod = "POST", value = "查询弹幕是否开放,true:开放;false:未开放")
//	@ResponseBody
//	public BaseResp<String> queryBarrageIsStart(HttpServletRequest request,HttpServletResponse response){
//		String flag = DictUtils.getDictValue("lucky_barrage", "lucky_barrage", "false");
//		return new BaseResp<String>(IntfzRs.SUCCESS,"ok",flag);
//	}
//
//	/**
//	 * 发送获奖短信
//	 * @param request
//	 * @param response
//	 * @return
//	 */
//	@RequestMapping(value = "sendInfo")
//	@ApiOperation(httpMethod = "POST", value = "发送获奖短信")
//	@ResponseBody
//	public BaseResp<String> sendInfo(@RequestBody LuckyMInfoReq req,HttpServletRequest request,HttpServletResponse response){
//		init();
//		if(req == null || req.getInfoList() == null){
//			return new BaseResp<String>(IntfzRs.SUCCESS,"获奖人员信息不能为空!","");
//		}
//		List<LuckyUser> luckyUserList = new ArrayList<LuckyUser>();
//		for(LuckyMInfoListReq info:req.getInfoList()){
//			LuckyUser luckyUser = new LuckyUser();
//			luckyUser.setUserName(info.getUserName());
//			luckyUser.setPhone(info.getPhone());
//			luckyUserList.add(luckyUser);
//			StringBuffer content = new StringBuffer();
//			content.append("恭喜您!");
//			content.append(info.getUserName());
//			content.append("["+info.getPhone()+"]");
//			content.append("获得");
//			switch (info.getPrize()) {
//			case 1:
//				content.append("华为P20 PRO手机(一部)");
//				break;
//			case 2:
//				content.append("科沃斯扫地机器人(一台)");
//				break;
//			case 3:
//				content.append("Tiger保温杯、象印保温杯、飞利浦吹风机(任选一个)");
//				break;
//			case 4:
//				content.append("天猫精灵方糖AI智能音箱(一个)");
//				break;
//			default:
//				break;
//			}
//			smsService.sent(new SmsSentRequest(info.getPhone(), content.toString(), 0));
//		}
//
//		try(Jedis jedis = JedisUtils.getResource()) {
//			jedis.set(StringUtils.getBytes("minfo_"+req.getInfoList().get(0).getPrize()), SerializeUtil.serializeList(luckyUserList));
//		} catch (Exception e) {}
//		return new BaseResp<String>(IntfzRs.SUCCESS,"ok","发送成功");
//	}
	
}
