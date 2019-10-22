/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.oa.web;

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

import com.sijibao.oa.common.config.Global;
import com.sijibao.oa.common.persistence.Page;
import com.sijibao.oa.common.utils.StringUtils;
import com.sijibao.oa.common.web.BaseController;
import com.sijibao.oa.modules.oa.entity.CustInfoClue;
import com.sijibao.oa.modules.oa.service.CustInfoClueService;


/**
 * 客户线索Controller
 * @author wanxb
 * @version 2018-05-29
 */
@Controller
@RequestMapping(value = "${adminPath}/oa/clue/custinfoClue")
public class CustinfoClueController extends BaseController {

	@Autowired
	private CustInfoClueService custInfoClueService;
	
	@ModelAttribute
	public CustInfoClue get(@RequestParam(required=false) String id) {
		CustInfoClue entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = custInfoClueService.get(id);
		}
		if (entity == null){
			entity = new CustInfoClue();
		}
		return entity;
	}
	/**
	 * 列表
	 * @param custinfoClue
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("oa:clue:custinfoClue:view")
	@RequestMapping(value = {"list", ""})
	public String list(CustInfoClue custinfoClue, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CustInfoClue> page = custInfoClueService.findPage(new Page<CustInfoClue>(request, response), custinfoClue); 
		model.addAttribute("page", page);
		return "wxb/oa/clue/custinfoClueList";
	}
	/**
	 * 新增页面
	 * @param custinfoClue
	 * @param model
	 * @return
	 */
	@RequiresPermissions("oa:clue:custinfoClue:view")
	@RequestMapping(value = "form")
	public String form(CustInfoClue custinfoClue, Model model) {
		model.addAttribute("custinfoClue", custinfoClue);
		return "wxb/oa/clue/custinfoClueForm";
	}
	/**
	 * 保存
	 * @param custinfoClue
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("oa:clue:custinfoClue:edit")
	@RequestMapping(value = "save")
	public String save(CustInfoClue custinfoClue, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, custinfoClue)){
			return form(custinfoClue, model);
		}
		custInfoClueService.save(custinfoClue);
		addMessage(redirectAttributes, "保存clue成功");
		return "redirect:"+Global.getAdminPath()+"/oa/clue/custinfoClue/?repage";
	}
	/**
	 * 删除
	 * @param custinfoClue
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("oa:clue:custinfoClue:edit")
	@RequestMapping(value = "delete")
	public String delete(CustInfoClue custinfoClue, RedirectAttributes redirectAttributes) {
		custInfoClueService.delete(custinfoClue);
		addMessage(redirectAttributes, "删除clue成功");
		return "redirect:"+Global.getAdminPath()+"/oa/clue/custinfoClue/?repage";
	}

}