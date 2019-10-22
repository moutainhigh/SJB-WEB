/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.oa.web;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.sijibao.oa.common.persistence.Page;
import com.sijibao.oa.common.utils.DateUtils;
import com.sijibao.oa.common.utils.StringUtils;
import com.sijibao.oa.common.web.BaseController;
import com.sijibao.oa.modules.oa.entity.HouseKeeping;
import com.sijibao.oa.modules.oa.service.HouseKeepingService;
import com.sijibao.oa.modules.sys.entity.User;
import com.sijibao.oa.modules.sys.utils.UserUtils;

/**
 * 内务工作管理Controller
 * @author Petter
 * @version 2018-04-16
 */
@Controller
@RequestMapping(value = "${adminPath}/oa/houseKeepingScore")
public class HouseKeepingScoreController extends BaseController {

	@Autowired
	private HouseKeepingService houseKeepingService;
	
	@ModelAttribute
	public HouseKeeping get(@RequestParam(required=false) String id) {
		HouseKeeping entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = houseKeepingService.get(id);
		}
		if (entity == null){
			entity = new HouseKeeping();
		}
		return entity;
	}
	
	@RequiresPermissions("oa:houseKeepingScore:view")
	@RequestMapping(value = {"list", ""})
	public String list(HouseKeeping houseKeeping, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		Page<HouseKeeping> page = houseKeepingService.findMonthPage(new Page<HouseKeeping>(request, response), houseKeeping, user); 
		model.addAttribute("page", page);
		return "modules/oa/scoreList";
	}

	@RequiresPermissions("oa:houseKeepingScore:view")
	@RequestMapping(value = "form")
	public String form(HouseKeeping houseKeeping, Model model) {
		User user = UserUtils.getUser();
		Page<HouseKeeping> page = houseKeepingService.findDayPage(new Page<HouseKeeping>(1, 35), houseKeeping, user);
		List<HouseKeeping> list1 = Lists.newArrayList();
		List<HouseKeeping> list2 = Lists.newArrayList();
		Date compareDate = DateUtils.parse(DateUtils.getDate(), "yyyy-MM-dd");
		for(HouseKeeping hk : page.getList()){
			if(DateUtils.compareDate(DateUtils.parse(hk.getWorkDate(), "yyyy-MM-dd"),
					compareDate) > 0){
				list2.add(hk);
			}else{
				list1.add(hk);
			}
		}
		model.addAttribute("houseKeeping", houseKeeping);
		model.addAttribute("list1", list1);
		model.addAttribute("list2", list2);
		return "modules/oa/scoreForm";
	}

	@RequiresPermissions("oa:houseKeepingScore:view")
	@RequestMapping(value = "score")
	public String score(HouseKeeping houseKeeping, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, houseKeeping)){
			return form(houseKeeping, model);
		}
		String msg = houseKeepingService.score(houseKeeping, UserUtils.getUser());
		houseKeeping.setId(null);
		houseKeeping.setIsSign(null);
		houseKeeping.setWorkDate(houseKeeping.getWorkDate().substring(0, 7));
		if(StringUtils.isNotBlank(msg)){
			model.addAttribute("message", "评分失败！"+msg);
		}else{
			model.addAttribute("message", "评分成功！");
		}
		return form(houseKeeping, model);
	}
	
}