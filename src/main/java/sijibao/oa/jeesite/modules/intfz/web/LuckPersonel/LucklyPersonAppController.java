package sijibao.oa.jeesite.modules.intfz.web.LuckPersonel;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.sijibao.oa.common.utils.StringUtils;
import com.sijibao.oa.common.web.BaseController;
import com.sijibao.oa.modules.intfz.bean.BaseResp;
import com.sijibao.oa.modules.intfz.enums.IntfzRs;
import com.sijibao.oa.modules.intfz.request.lucky.LuckyActiveQueryReq;
import com.sijibao.oa.modules.intfz.request.lucky.LuckyUserReq;
import com.sijibao.oa.modules.intfz.response.lucky.LuckyUserBindQueryRes;
import com.sijibao.oa.modules.sys.entity.LuckyUser;
import com.sijibao.oa.modules.sys.service.LuckyUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 年会Controller
 */
@Controller
@RequestMapping(value = "wechat/lucklyPerson")
@Api(value="a年会接口服务",tags="a年会接口服务")
public class LucklyPersonAppController extends BaseController {
	
	@Autowired
	private LuckyUserService luckyUserService;
	
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
	
}
