/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.sys.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.sijibao.base.common.api.response.PagerResponse;
import com.sijibao.oa.common.config.Global;
import com.sijibao.oa.common.persistence.Page;
import com.sijibao.oa.common.utils.StringUtils;
import com.sijibao.oa.common.web.BaseController;
import com.sijibao.oa.modules.sys.entity.JobLevel;
import com.sijibao.oa.modules.sys.entity.User;
import com.sijibao.oa.modules.sys.service.SystemService;
import com.sijibao.oa.modules.sys.utils.UserUtils;
import com.sijibao.oa.office.api.exception.ServiceException;
import com.sijibao.oa.sys.api.JobLevelService;
import com.sijibao.oa.sys.api.request.JobLevelReq;
import com.sijibao.oa.sys.api.request.JobLevelSaveReq;
import com.sijibao.oa.sys.api.response.JobLevelResponse;

/**
 * 职级管理Controller
 * @author huangkai
 * @version 2019-01-03
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/jobLevel")
public class JobLevelController extends BaseController {

	@Autowired
	private JobLevelService jobLevelService;
	@Autowired
	private SystemService systemService;


	@ModelAttribute
	public JobLevel get(@RequestParam(required=false) String id) {
		JobLevel entity = null;
		if (StringUtils.isNotBlank(id)){
			JobLevelResponse ss = jobLevelService.get(id);
			entity = change(ss,JobLevel.class);
		}
		if (entity == null){
			entity = new JobLevel();
		}
		return entity;
	}

	@RequestMapping(value = {"list", ""})
	public String list(JobLevel jobLevel, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<JobLevel> nee = new Page<JobLevel>(request, response);
		JobLevelReq change = change(jobLevel, JobLevelReq.class);
		change.setPageNo(nee.getPageNo());
		change.setPageSize(nee.getPageSize());
		PagerResponse<JobLevelResponse> resp = jobLevelService.findPage(change);
		Page<JobLevel> page = new Page<JobLevel>(request, response);
		ArrayList<JobLevel> list = Lists.newArrayList();
		for (JobLevelResponse s : resp.getList()) {
			JobLevel ss = change(s, JobLevel.class);
			User u = systemService.getUser(s.getUpdateBy());
			ss.setUpdateByName(u.getName());
			//查出每个职级有哪些人员
			User u2 = new User();
			u2.setRank(ss.getId());
			List<User> userList = systemService.findUserNotAccess(u2);
			if(null != userList && userList.size() > 0){
				StringBuilder sb = new StringBuilder();
				for(int i=0; i < userList.size(); i++){
					sb.append(userList.get(i).getName());
					if(i < userList.size() - 1){
						sb.append(",");
					}
				}
				ss.setUserNames(sb.toString());
			}

			list.add(ss);
		}
		page.setCount(resp.getCount());
		page.setList(list);
		model.addAttribute("page", page);
		return "modules/sys/jobLevelList";
	}


	@RequestMapping(value = "form")
	public String form(JobLevel jobLevel, Model model) {
		model.addAttribute("jobLevel", jobLevel);
		return "modules/sys/jobLevelForm";
	}

	@RequestMapping(value = "save")
	public String save(JobLevel jobLevel, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, jobLevel)){
			return form(jobLevel, model);
		}
		User user = UserUtils.getUser();
		JobLevelSaveReq change = change(jobLevel, JobLevelSaveReq.class);
		change.setUserId(user.getId());
		if(change.getLevelCode().indexOf("ZJ") < 0) {
			change.setLevelCode("ZJ" + change.getLevelCode());
		}

		try {

			if(StringUtils.isBlank(jobLevel.getId())){
				if (jobLevel.getLevelCode() !=null ){
					JobLevelReq req = new JobLevelReq();
					req.setLevelCode(change.getLevelCode());
					if(jobLevelService.getJobLevelByLevelCode(req) != null) {
						addMessage(model, "保存职级失败，职级编号已存在");
						return form(jobLevel, model);
					}
				}
			}

			jobLevelService.save(change);
		} catch (ServiceException e) {
			addMessage(redirectAttributes, e.getMessage());
		}
		addMessage(redirectAttributes, "保存职级成功");
		return "redirect:"+Global.getAdminPath()+"/sys/jobLevel?repage";
	}

	@RequestMapping(value = "delete")
	public String delete(JobLevel jobLevel, RedirectAttributes redirectAttributes) {
		User user = UserUtils.getUser();
		JobLevelSaveReq req = change(jobLevel, JobLevelSaveReq.class);
		req.setUserId(user.getId());


		User u2 = new User();
		u2.setRank(req.getId());
		List<User> userList = systemService.findUserNotAccess(u2);
		if(null != userList && userList.size() > 0){
			addMessage(redirectAttributes, "该职级正在使用中，不可删除！");
			return "redirect:"+Global.getAdminPath()+"/sys/jobLevel?repage";
		}
		jobLevelService.delete(req);
		addMessage(redirectAttributes, "删除职级成功");
		return "redirect:"+Global.getAdminPath()+"/sys/jobLevel?repage";
	}
}