/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.oa.web;

import java.io.UnsupportedEncodingException;

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
import com.sijibao.oa.modules.oa.entity.ExpensesSubConf;
import com.sijibao.oa.modules.oa.entity.ExpensesSubInfo;
import com.sijibao.oa.modules.oa.service.ExpensesSubConfService;
import com.sijibao.oa.modules.oa.service.ExpensesSubInfoService;

/**
 * 费用科目配置管理Controller
 * 
 * @author Petter
 * @version 2017-12-24
 */
@Controller
@RequestMapping(value = "${adminPath}/oa/expensesSubConf")
public class ExpensesSubConfController extends BaseController {

    @Autowired
    private ExpensesSubConfService expensesSubConfService;
    @Autowired
    private ExpensesSubInfoService expensesSubInfoService;
    @ModelAttribute
    public ExpensesSubConf get(@RequestParam(required = false) String id) {
        ExpensesSubConf entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = expensesSubConfService.get(id);
        }
        if (entity == null) {
            entity = new ExpensesSubConf();
        }
        return entity;
    }

    @RequiresPermissions("oa:expensesSubConf:view")
    @RequestMapping(value = { "list", "" })
    public String list(ExpensesSubConf expensesSubConf, HttpServletRequest request, HttpServletResponse response,
					   Model model) {
        Page<ExpensesSubConf> page = expensesSubConfService.findPage(new Page<ExpensesSubConf>(request, response),
                expensesSubConf);
        model.addAttribute("page", page);
        return "modules/oa/expensesSubConfList";
    }

    @RequiresPermissions("oa:expensesSubConf:view")
    @RequestMapping(value = "form")
    public String form(ExpensesSubConf expensesSubConf, Model model) {
        if(StringUtils.isBlank(expensesSubConf.getId()) && StringUtils.isNotBlank(expensesSubConf.getSubCode())){
            ExpensesSubInfo expensesSubInfo =    expensesSubInfoService.get(expensesSubConf.getSubCode());
            if(expensesSubInfo != null){
                expensesSubConf.setSubName(expensesSubInfo.getSubName());
                expensesSubConf.setsCode(expensesSubInfo.getSubCode());
            }
        }
        model.addAttribute("expensesSubConf", expensesSubConf);
        return "modules/oa/expensesSubConfForm";
    }

    @RequiresPermissions("oa:expensesSubConf:edit")
    @RequestMapping(value = "save")
    public String save(ExpensesSubConf expensesSubConf, Model model, RedirectAttributes redirectAttributes)
            throws UnsupportedEncodingException {
        if (!beanValidator(model, expensesSubConf)) {
            return form(expensesSubConf, model);
        }
        expensesSubConfService.save(expensesSubConf);
        addMessage(redirectAttributes, "保存费用科目配置成功");
        return "redirect:" + Global.getAdminPath() + "/oa/expensesSubConf/?repage&subCode=" + expensesSubConf.getSubCode();
    }

    @RequiresPermissions("oa:expensesSubConf:edit")
    @RequestMapping(value = "delete")
    public String delete(ExpensesSubConf expensesSubConf, RedirectAttributes redirectAttributes)
            throws UnsupportedEncodingException {
        expensesSubConfService.delete(expensesSubConf);
        addMessage(redirectAttributes, "删除费用科目配置成功");
        return "redirect:" + Global.getAdminPath() + "/oa/expensesSubConf/?repage&subCode="
                + expensesSubConf.getSubCode();
    }

}