package sijibao.oa.jeesite.modules.intfz.web.intfzquartz;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sijibao.oa.common.utils.DateUtils;
import com.sijibao.oa.common.utils.StringUtils;
import com.sijibao.oa.common.web.BaseController;
import com.sijibao.oa.modules.intfz.bean.BaseResp;
import com.sijibao.oa.modules.intfz.enums.IntfzRs;
import com.sijibao.oa.modules.intfz.service.intfzweb.IntfzWebCustInfoService;
import com.sijibao.oa.modules.intfz.utils.Constant;
import com.sijibao.oa.modules.oa.entity.CustInfo;
import com.sijibao.oa.modules.oa.service.CustInfoService;
import com.sijibao.oa.modules.sys.utils.DictUtils;

/**
 * 定时任务客户信息状态更新
 * @author xuby
 * @date 2018-08-07
 */
@Controller
@RequestMapping(value = "quartz/custInfo")
public class IntfzQuartzCustInfoController extends BaseController {
	
	@Autowired
	private CustInfoService custInfoService;
	@Autowired
	private IntfzWebCustInfoService intfzWebCustInfoService;
	/**
	 * 区域公海客户信息归属自动更新 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "custInfoAreaTaskJob")
	@ResponseBody
	public BaseResp<String> custInfoAreaTaskJob(HttpServletRequest request, HttpServletResponse response){
		logger.info("===================IntfzQuartzCustInfoController CustInfoAreaTaskJob 客户信息归属自动更新 start===================");
		
		CustInfo custInfo = new CustInfo();
		custInfo.setCustListPlace("2"); //区域公海
		List<CustInfo> custInfoList = custInfoService.queryCustInfoTask(custInfo);
		
		String maxDay = DictUtils.getDictLabel("1", "cust_max_day", "999999"); //系统设置最大开放时间
		
		if(custInfoList != null && custInfoList.size() > 0){
			for(CustInfo c:custInfoList){
				if(c.getLetGoTime() != null && DateUtils.pastDays(c.getLetGoTime()) > Integer.parseInt(maxDay)){
					logger.info("====="+c.getCustName()+"超过"+maxDay+"天没有人捡入了，需要释放到公海=====");
					
					c.setCustListPlace("1"); //公海
					c.setSecondGoMan(StringUtils.isBlank(c.getMarketLeaderId())?c.getLetGoMan():c.getMarketLeaderId());  //系统定时任务主动释放
					c.setSecondGoTime(new Date()); //释放时间
					c.setUpdateFlag("1");
					c.setUpdateTime(new Date());
					custInfoService.updateCustListPlaceInfo(c);
					intfzWebCustInfoService.saveCustMaintenanceForTask(c);
					logger.info("====="+c.getCustName()+"需要释放到公海成功!=====");
				}
			}
		}
		
		logger.info("===================IntfzQuartzCustInfoController CustInfoAreaTaskJob 客户信息归属自动更新 end===================");
		return new BaseResp<String>(IntfzRs.SUCCESS,"执行成功!","");
	}
	
	
	/**
	 * 个人客户信息归属自动更新 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "custInfoPersonelTaskJob")
	@ResponseBody
	public BaseResp<String> custInfoPersonelTaskJob(HttpServletRequest request, HttpServletResponse response){
		logger.info("===================IntfzQuartzCustInfoController CustInfoPersonelTaskJob 客户信息归属自动更新 start===================");
		
		CustInfo custInfo = new CustInfo();
		custInfo.setCustListPlace("4"); //个人
		List<CustInfo> custInfoList = custInfoService.queryCustInfoTask(custInfo);
		
		String maxDay = DictUtils.getDictLabel("1", "cust_max_day", "999999"); //系统设置最大开放时间
		
		if(custInfoList != null && custInfoList.size() > 0){
			for(CustInfo c:custInfoList){
				if(c.getUpdateTime() != null && DateUtils.pastDays(c.getUpdateTime()) > Integer.parseInt(maxDay)){
					//F,G级客户直接开放到公海
					if(StringUtils.equals(Constant.CUST_STAGE_F, c.getCustStage()) || StringUtils.equals(Constant.CUST_STAGE_G, c.getCustStage())){
						logger.info("=====F,G级客户："+c.getCustName()+"超过"+maxDay+"天没有更新，需要释放到区域公海=======");
						
						c.setCustListPlace("1"); //公海
						c.setLetGoMan(c.getMarketLeaderId());
						c.setLetGoTime(new Date());
						c.setSecondGoMan(c.getMarketLeaderId());  //系统定时任务主动释放，默认
						c.setSecondGoTime(new Date()); //释放时间
						c.setUpdateFlag("1");
						
					}else if(StringUtils.equals(Constant.CUST_STAGE_D, c.getCustStage()) || StringUtils.equals(Constant.CUST_STAGE_E, c.getCustStage())){ //D,E级别客户开放到区域公海
						logger.info("=====D,E级客户："+c.getCustName()+"超过15天没有更新，需要释放到区域公海=======");
						
						c.setCustListPlace("2"); //区域公海
						c.setLetGoMan(c.getMarketLeaderId());  //系统定时任务主动释放
						c.setLetGoTime(new Date()); //释放时间
						c.setUpdateFlag("0");
						
					}
					c.setUpdateTime(new Date());
					custInfoService.updateCustListPlaceInfo(c);
					intfzWebCustInfoService.saveCustMaintenanceForTask(c);
					logger.info("======"+c.getCustName()+"释放到区域公海/公海成功!======");
				}
			}
		}
		logger.info("===================IntfzQuartzCustInfoController CustInfoPersonelTaskJob 客户信息归属自动更新 end===================");
		return new BaseResp<String>(IntfzRs.SUCCESS,"执行成功!","");
	}
}
