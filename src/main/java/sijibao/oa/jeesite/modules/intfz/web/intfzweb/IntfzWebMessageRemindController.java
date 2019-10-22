package sijibao.oa.jeesite.modules.intfz.web.intfzweb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.curator.shaded.com.google.common.collect.Lists;
import org.springframework.beans.BeansException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.sijibao.base.common.api.response.PagerResponse;
import com.sijibao.oa.common.utils.SpringContextHolder;
import com.sijibao.oa.common.web.BaseController;
import com.sijibao.oa.modules.intfz.bean.BaseResp;
import com.sijibao.oa.modules.intfz.enums.IntfzRs;
import com.sijibao.oa.modules.intfz.request.messageremind.MainMessageRemindHandleReq;
import com.sijibao.oa.modules.intfz.request.messageremind.MainMessageRemindSaveReq;
import com.sijibao.oa.modules.intfz.response.common.PageResponse;
import com.sijibao.oa.modules.intfz.response.intfzwebresp.messageremind.MainMessageRemindDetailResp;
import com.sijibao.oa.modules.intfz.response.intfzwebresp.messageremind.MainMessageRemindListResp;
import com.sijibao.oa.modules.sys.entity.User;
import com.sijibao.oa.modules.sys.utils.UserUtils;
import com.sijibao.oa.timedtask.api.TaskAllocationService;
import com.sijibao.oa.timedtask.api.common.ServiceException;
import com.sijibao.oa.timedtask.api.request.MessageRemindListReq;
import com.sijibao.oa.timedtask.api.request.MessageRemindReq;
import com.sijibao.oa.timedtask.api.response.MessageRemindResp;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 消息提醒controller
 * xgx
 */
@Controller
@RequestMapping(value = "${frontPath}/messageRemind")
@Api(value="WEB提醒管理服务",tags="WEB提醒管理服务")
public class IntfzWebMessageRemindController extends BaseController {
	
	private TaskAllocationService taskAllocationService;

	
	@ModelAttribute
	public void init(){
		if(this.taskAllocationService == null){
			this.taskAllocationService = SpringContextHolder.getBean("taskAllocationService");
		}

	};


	/**
	 * 提醒管理-查询列表
	 * @param req
	 * @param sessionid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "messageRemindList")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "提醒管理-查询列表")
	public BaseResp<PageResponse<List<MainMessageRemindListResp>>> messageRemindList(
			@RequestBody MainMessageRemindHandleReq req,
			@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid
			) throws Exception{
		MessageRemindListReq messageRemindListReq=change(req,MessageRemindListReq.class);
		PagerResponse<MessageRemindResp> page = taskAllocationService.findPage(messageRemindListReq);
		ArrayList<MainMessageRemindListResp> list = Lists.newArrayList();
		for(MessageRemindResp messageRemindResp:page.getList()){
			MainMessageRemindListResp resp=change(messageRemindResp,MainMessageRemindListResp.class);
			String remindWay = messageRemindResp.getRemindWay();
			String[] split = remindWay.split(",");
			List<String> strings = Arrays.asList(split);
			resp.setRemindWays(strings);
			resp.setUpdateTime(messageRemindResp.getUpdateTime().getTime());
			resp.setUpdateBy(UserUtils.get(messageRemindResp.getUpdateBy()).getName());
			list.add(resp);
		}
		return new BaseResp<PageResponse<List<MainMessageRemindListResp>>>(IntfzRs.SUCCESS, "ok",
				new PageResponse<List<MainMessageRemindListResp>>(list, req.getPageNo(), page.getCount()));
	}

	/**
	 * 提醒管理-详情信息查询
	 * @param sessionid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "messageRemindDetail")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "提醒管理--详细信息")
	public BaseResp<MainMessageRemindDetailResp> messageRemindDetail(
			@RequestBody MainMessageRemindHandleReq req,
			@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid
			) throws Exception{
		User user = UserUtils.getUser(sessionid);
		MessageRemindResp messageRemindDetail = taskAllocationService.findMessageRemindDetail(req.getId(), user.getId());
		MainMessageRemindDetailResp mainMessageRemindDetailResp=change(messageRemindDetail,MainMessageRemindDetailResp.class);
		String remindWays = messageRemindDetail.getRemindWay();
		String[] split = remindWays.split(",");
		List<String> strings = Arrays.asList(split);
		mainMessageRemindDetailResp.setRemindWays(strings);
		if(messageRemindDetail.getStartTime()!=null){
			mainMessageRemindDetailResp.setStartTime(messageRemindDetail.getStartTime().getTime());
		}
		return new BaseResp<MainMessageRemindDetailResp>(IntfzRs.SUCCESS, "ok", mainMessageRemindDetailResp);
	}



	/**
	 * 提醒管理-保存提醒信息
	 * @param sessionid
	 * @return
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws BeansException
	 */
	@RequestMapping(value = "saveMessageRemind")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "提醒管理-保存提醒信息")
	public BaseResp<String> saveMessageRemind(@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
										@RequestBody MainMessageRemindSaveReq messageRemindSaveReq){
		User user = UserUtils.getUser(sessionid);
		try {
			MessageRemindReq messageRemindReq=change(messageRemindSaveReq,MessageRemindReq.class);
			messageRemindReq.setProducSide("web");
			taskAllocationService.saveMessageRemind(messageRemindReq,user.getId());
		} catch (ServiceException e) {
			return new BaseResp<String>(IntfzRs.ERROR,e.getMessage(),null);
		}
		return new BaseResp<String>(IntfzRs.SUCCESS,"保存成功！",null);
	}


	/**
	 * 提醒管理-删除提醒信息
	 * @param sessionid
	 * @return
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws BeansException
	 */
	@RequestMapping(value = "delMessageRemind")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "提醒管理-删除提醒信息")
	public BaseResp<String> delMessageRemind(@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
											@RequestBody MainMessageRemindHandleReq req){
		User user = UserUtils.getUser(sessionid);
		try {
			taskAllocationService.delMessageRemind(req.getId(),user.getId());
		} catch (Exception e) {
			e.printStackTrace();
			return new BaseResp<String>(IntfzRs.ERROR,"删除失败！",null);
		}
		return new BaseResp<String>(IntfzRs.SUCCESS,"删除成功！",null);
	}


}

