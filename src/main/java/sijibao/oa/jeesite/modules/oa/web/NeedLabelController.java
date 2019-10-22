/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.oa.web;

import java.util.ArrayList;

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
import com.sijibao.base.common.api.response.PagerResponse;
import com.sijibao.oa.common.config.Global;
import com.sijibao.oa.common.persistence.Page;
import com.sijibao.oa.common.utils.DateUtils;
import com.sijibao.oa.common.utils.StringUtils;
import com.sijibao.oa.common.web.BaseController;
import com.sijibao.oa.modules.oa.entity.NeedLabel;
import com.sijibao.oa.modules.oa.entity.NeedType;
import com.sijibao.oa.modules.oa.utils.CodeUtils;
import com.sijibao.oa.modules.sys.entity.User;
import com.sijibao.oa.modules.sys.utils.UserUtils;
import com.sijibao.oa.office.api.IntfzNeedLabelService;
import com.sijibao.oa.office.api.exception.ServiceException;
import com.sijibao.oa.office.api.request.need.NeedLabelReq;
import com.sijibao.oa.office.api.request.need.NeedLabelSaveReq;
import com.sijibao.oa.office.api.response.need.NeedLabelResponse;


/**
 * 协作标签管理Controller
 * @author wanxb
 * @version 2018-11-23
 */
@Controller
@RequestMapping(value = "${adminPath}/oa/needLabel")
public class NeedLabelController extends BaseController {

	@Autowired
	private IntfzNeedLabelService needLabelService;
	
	@ModelAttribute
	public NeedLabel get(@RequestParam(required=false) String id) {
		NeedLabel entity = null;
		if (StringUtils.isNotBlank(id)){
			NeedLabelResponse ss = needLabelService.get(id);
			entity = change(ss,NeedLabel.class);
		}
		if (entity == null){
			entity = new NeedLabel();
		}
		return entity;
	}
	
	@RequiresPermissions("oa:needLabel:view")
	@RequestMapping(value = {"list", ""})
	public String list(NeedLabel needLabel, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<NeedType> nee = new Page<NeedType>(request, response);
		NeedLabelReq change = change(needLabel, NeedLabelReq.class);
		change.setPageNo(nee.getPageNo());
		change.setPageSize(nee.getPageSize());
		change.setDelFlag(needLabel.getDel());
		PagerResponse<NeedLabelResponse> resp = needLabelService.findPage(change); 
		Page<NeedLabel> page = new Page<NeedLabel>(request, response);
		ArrayList<NeedLabel> list = Lists.newArrayList();
		for (NeedLabelResponse s : resp.getList()) {
			NeedLabel ss = change(s, NeedLabel.class);
			list.add(ss);
		}
		page.setCount(resp.getCount());
		page.setList(list);
		model.addAttribute("page", page);
		return "modules/oa/needLabelList";
	}

	@RequiresPermissions("oa:needLabel:view")
	@RequestMapping(value = "form")
	public String form(NeedLabel needLabel, Model model) {
		model.addAttribute("needLabel", needLabel);
		return "modules/oa/needLabelForm";
	}

	@RequiresPermissions("oa:needLabel:edit")
	@RequestMapping(value = "save")
	public String save(NeedLabel needLabel, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, needLabel)){
			return form(needLabel, model);
		}
		User user = UserUtils.getUser();
		NeedLabelSaveReq change = change(needLabel, NeedLabelSaveReq.class);
		change.setUserId(user.getId());
		change.setLabelCode(CodeUtils.genCode("BQ", DateUtils.getDate("yyyyMMdd"), 3));
		try {
			needLabelService.save(change);
		} catch (ServiceException e) {
			addMessage(redirectAttributes, e.getMessage());
		}
		addMessage(redirectAttributes, "保存标签管理成功");
		return "redirect:"+Global.getAdminPath()+"/oa/needLabel/?repage";
	}
	
	@RequiresPermissions("oa:needLabel:edit")
	@RequestMapping(value = "delete")
	public String delete(NeedLabel needLabel, RedirectAttributes redirectAttributes) {
		User user = UserUtils.getUser();
		NeedLabelReq req = change(needLabel, NeedLabelReq.class);
		req.setUserId(user.getId());
		needLabelService.delete(req);
		addMessage(redirectAttributes, "删除标签管理成功");
		return "redirect:"+Global.getAdminPath()+"/oa/needLabel/?repage";
	}

}