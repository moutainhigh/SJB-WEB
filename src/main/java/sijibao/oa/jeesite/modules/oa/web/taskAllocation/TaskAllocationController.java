package sijibao.oa.jeesite.modules.oa.web.taskAllocation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sijibao.base.common.api.response.PagerResponse;
import com.sijibao.oa.common.persistence.Page;
import com.sijibao.oa.common.utils.JedisUtils;
import com.sijibao.oa.common.utils.SpringContextHolder;
import com.sijibao.oa.common.utils.StringUtils;
import com.sijibao.oa.common.web.BaseController;
import com.sijibao.oa.modules.intfz.utils.Constant;
import com.sijibao.oa.modules.sys.entity.User;
import com.sijibao.oa.modules.sys.utils.DictUtils;
import com.sijibao.oa.modules.sys.utils.UserUtils;
import com.sijibao.oa.timedtask.api.TaskAllocationService;
import com.sijibao.oa.timedtask.api.request.TaskAllocationRequest;
import com.sijibao.oa.timedtask.api.response.TaskAllocationResponse;
import com.sijibao.oa.timedtask.api.utils.QuartzManager;

/**
 * 定时任务管理
 * @author xby
 * @date 2018-08-02
 */
@Controller
@RequestMapping(value = "${adminPath}/oa/taskAllocation")
public class TaskAllocationController extends BaseController {
	
	private TaskAllocationService taskAllocationService;
	
	@ModelAttribute
	public void init(){
		if(this.taskAllocationService == null){
			this.taskAllocationService = SpringContextHolder.getBean("taskAllocationService");
		}
	};
	
	/**
	 * 查询list数据
	 * @param taskAllocationRequest
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("oa:task:list")
	@RequestMapping(value = {"list"})
	public String list(TaskAllocationRequest taskAllocationRequest, HttpServletRequest request, HttpServletResponse response, Model model) {
		PagerResponse<TaskAllocationResponse> findPage = taskAllocationService.findPage(taskAllocationRequest); //查询分页数据
		
		Page<TaskAllocationResponse> page = new Page<TaskAllocationResponse>(request, response);
		page.setList(findPage.getList()); //分页查询信息
		page.setCount(findPage.getCount()); //总条数
		
        model.addAttribute("page", page);
		return "modules/oa/task/taskList";
	}
	
	
	/**
	 * 编辑
	 * @param id
	 * @return
	 */
	@RequiresPermissions("oa:task:form")
	@RequestMapping(value = {"form"})
	public String form(String id, Model model){
		TaskAllocationResponse tmpe = null;
		if(StringUtils.isNotBlank(id)){
			TaskAllocationRequest taskAllocationRequest = new TaskAllocationRequest();
			taskAllocationRequest.setId(id);
			tmpe = taskAllocationService.get(taskAllocationRequest);
		}else{
			tmpe = new TaskAllocationResponse();
		}
		model.addAttribute("taskAllocation", tmpe);
		return "modules/oa/task/taskForm";
	}
	
	
	/**
	 * 更新定时器
	 * @param taskAllocationVo
	 * @param beginDate
	 * @param endDate
	 * @return
	 * 
	 */
	@RequiresPermissions("oa:task:saveOrUpdate")
	@RequestMapping(value = {"saveOrUpdate"})
	public String saveOrUpdate(TaskAllocationRequest taskAllocationRequest, RedirectAttributes redirectAttributes){
		try {
			
			//设置任务验证
			if(StringUtils.isBlank(taskAllocationRequest.getIsMacVaild())){
				taskAllocationRequest.setIsMacVaild(DictUtils.getDictValue(Constant.YES_NO_NO, "yes_no", Constant.YES_NO_NO));
			}
			
			User user = UserUtils.getUser();
			//设置定时器状态
			taskAllocationRequest.setCreateBy(user.getId());
			taskAllocationRequest.setUpdateBy(user.getId());
			taskAllocationRequest.getTaskIntfzAddress().setCreateBy(user.getId());
			taskAllocationRequest.getTaskIntfzAddress().setUpdateBy(user.getId());
			taskAllocationRequest.setState(QuartzManager.changeState(QuartzManager.getTriggerState(taskAllocationRequest.getJobName())));
			taskAllocationService.saveTaskAllocation(taskAllocationRequest);
			
			addMessage(redirectAttributes, "更新成功!");
		} catch (Exception e) {
			logger.info("更新定时器失败!",e);
		}
		return "redirect:" + adminPath + "/oa/taskAllocation/list?repage";
	}
	
	/**
	 * 启动定时器
	 * @param id 定时器ID
	 * @return
	 */
	@RequiresPermissions("oa:task:start")
	@RequestMapping(value = {"startTask"})
	public String startTask(TaskAllocationRequest taskAllocation, RedirectAttributes redirectAttributes){
		if(null!=taskAllocation && null!=taskAllocation.getIds() && taskAllocation.getIds().size()>0){
			try {
				taskAllocationService.startTaskAllocation(taskAllocation);
				addMessage(redirectAttributes, "定时任务启动成功!");
			} catch (Exception e) {
				logger.error("批量启动定时器异常!",e);
			}
		}else{
			addMessage(redirectAttributes, "参数错误，启动失败!");
		}
		
		return "redirect:" + adminPath + "/oa/taskAllocation/list?repage";
	}
	
	/**
	 * 停止定时器
	 * @param id
	 * @return
	 */
	@RequiresPermissions("oa:task:stop")
	@RequestMapping(value = {"stopTask"})
	public String stopTask(TaskAllocationRequest taskAllocation, RedirectAttributes redirectAttributes){
		if(null!=taskAllocation && null!=taskAllocation.getIds() && taskAllocation.getIds().size()>0){
			try {
				taskAllocationService.stopTaskAllocation(taskAllocation);
				//删除定时任务缓存
				JedisUtils.del(JedisUtils.KEY_PREFIX + "validation_"+taskAllocation.getJobName());
				addMessage(redirectAttributes, "定时任务停止成功!");
			} catch (Exception e) {
				logger.error("批量停止定时器异常!",e);
			}
		}else{
			addMessage(redirectAttributes, "参数错误，停止失败!");
		}
		return "redirect:" + adminPath + "/oa/taskAllocation/list?repage";
	}
	
}
