/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.oa.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.sijibao.oa.common.config.Global;
import com.sijibao.oa.common.utils.DateUtils;
import com.sijibao.oa.common.utils.excel.ExportExcel;
import com.sijibao.oa.common.web.BaseController;
import com.sijibao.oa.modules.oa.entity.*;
import com.sijibao.oa.modules.oa.service.FeatureToolService;

/**
 * wanxb
 * 2019-06-25 10:50:59
 * KPI数据的导出
 */
@Controller
@RequestMapping(value = "${adminPath}/oa/featureTool")
public class FeatureToolController extends BaseController {

    @Autowired
    private FeatureToolService featureToolService;

    /**
     * 页面
     */
    @RequestMapping(value = "view")
    public String view(HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        return "modules/oa/featureTool";
    }

    /**
     * 导出审批人审批时间
     */
    @RequestMapping(value = "flow", method= RequestMethod.POST)
    public String exportFile(FeatureTools featureTools,
							 HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        if(featureTools.getStart() == null || featureTools.getEnd() == null){
            addMessage(redirectAttributes, "开始和截止时间不能为空！");
            return "redirect:"+ Global.getAdminPath()+"/oa/featureTool/view?repage";
        }
        try {
            String fileName = "审批人审批时间"+ DateUtils.getDate("yyyyMMdd")+".xlsx";
            List<FeatureTools> feature = featureToolService.queryFlowData(featureTools);
            List<FeatureToolFlow> tools = Lists.newArrayList();
            for(FeatureTools too : feature){
                tools.add(change(too,FeatureToolFlow.class));
            }
            new ExportExcel("审批人审批时间", FeatureToolFlow.class).setDataList(tools).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            logger.error("导出审批人审批时间失败！失败信息：{}",e.getMessage());
            addMessage(redirectAttributes, "导出审批人审批时间失败！失败信息："+e.getMessage());
        }
        return "redirect:" + adminPath + "modules/oa/featureTool/view?repage";
    }

    /**
     * 导出提交人修改时间
     */
    @RequestMapping(value = "funct", method= RequestMethod.POST)
    public String exportFunct(FeatureTools featureTools,
							  HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        if(featureTools.getStart() == null || featureTools.getEnd() == null){
            addMessage(redirectAttributes, "开始和截止时间不能为空！");
            return "redirect:"+ Global.getAdminPath()+"/oa/featureTool/view?repage";
        }
        try {
            String fileName = "提交人修改时间"+ DateUtils.getDate("yyyyMMdd")+".xlsx";
            List<FeatureTools> feature = featureToolService.queryFunctionData(featureTools);
            List<FeatureToolFun> tools = Lists.newArrayList();
            for(FeatureTools too : feature){
                tools.add(change(too,FeatureToolFun.class));
            }
            new ExportExcel("提交人修改时间", FeatureToolFun.class).setDataList(tools).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            logger.error("导出提交人修改时间！失败信息：{}",e.getMessage());
            addMessage(redirectAttributes, "导出提交人修改时间！失败信息："+e.getMessage());
        }
        return "redirect:" + adminPath + "modules/oa/featureTool/view?repage";
    }

    /**
     * 导出个人KPI数据
     */
    @RequestMapping(value = "own", method= RequestMethod.POST)
    public String exportOwn(FeatureTools featureTools,
							HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        if(featureTools.getStart() == null || featureTools.getEnd() == null){
            addMessage(redirectAttributes, "开始和截止时间不能为空！");
            return "redirect:"+ Global.getAdminPath()+"/oa/featureTool/view?repage";
        }
        try {
            String fileName = "个人KPI数据"+ DateUtils.getDate("yyyyMMdd")+".xlsx";
            List<FeatureTools> feature = featureToolService.queryOwnData(featureTools);
            List<FeatureToolOwn> tools = Lists.newArrayList();
            for(FeatureTools too : feature){
                tools.add(change(too,FeatureToolOwn.class));
            }
            new ExportExcel("个人KPI数据", FeatureToolOwn.class).setDataList(tools).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            logger.error("导出个人KPI数据失败！失败信息：{}",e.getMessage());
            addMessage(redirectAttributes, "导出个人KPI数据失败！失败信息："+e.getMessage());
        }
        return "redirect:" + adminPath + "modules/oa/featureTool/view?repage";
    }

    /**
     * 导出单据审批时间
     */
    @RequestMapping(value = "ownUn", method= RequestMethod.POST)
    public String exportOwnUn(FeatureTools featureTools,
							  HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        if(featureTools.getStart() == null || featureTools.getEnd() == null){
            addMessage(redirectAttributes, "开始和截止时间不能为空！");
            return "redirect:"+ Global.getAdminPath()+"/oa/featureTool/view?repage";
        }
        try {
            String fileName = "单据审批时间"+ DateUtils.getDate("yyyyMMdd")+".xlsx";
            List<FeatureTools> feature = featureToolService.queryOwnUnData(featureTools);
            List<FeatureToolOwnUn> tools = Lists.newArrayList();
            for(FeatureTools too : feature){
                tools.add(change(too,FeatureToolOwnUn.class));
            }
            new ExportExcel("单据审批时间", FeatureToolOwnUn.class).setDataList(tools).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            logger.error("导出审批人审批时间失败！失败信息：{}",e.getMessage());
            addMessage(redirectAttributes, "导出审批人审批时间失败！失败信息："+e.getMessage());
        }
        return "redirect:" + adminPath + "modules/oa/featureTool/view?repage";
    }

}
