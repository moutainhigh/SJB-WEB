package sijibao.oa.jeesite.modules.oa.web;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.sijibao.oa.common.utils.DateUtils;
import com.sijibao.oa.common.utils.StringUtils;
import com.sijibao.oa.common.utils.excel.ExportExcel;
import com.sijibao.oa.common.web.BaseController;
import com.sijibao.oa.modules.oa.entity.ExpensesReportInfo;
import com.sijibao.oa.modules.oa.entity.ProjectInfo;
import com.sijibao.oa.modules.oa.entity.ReportSpan;
import com.sijibao.oa.modules.oa.service.ExpenseSubInfoReportPCTService;
import com.sijibao.oa.modules.oa.service.ExpenseSubInfoReportService;
import com.sijibao.oa.modules.oa.service.ProjectInfoService;
import com.sijibao.oa.modules.sys.service.OfficeService;

/**
 * 百分比报销统计
 * 
 * @author wanxb
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/flow/expenseFlow/expenseSubInfoReport/cent")
public class ExpenseSubInfoReportPCTController extends BaseController {
	@Autowired
	private ExpenseSubInfoReportPCTService expenseSubInfoReportPCTService;
	@Autowired
	private ExpenseSubInfoReportService expenseSubInfoReportService;
	@Autowired
	private OfficeService officeService;
	@Autowired
	private ProjectInfoService projectInfoService;
	/**
	 * 百分比统计报销
	 * @param expensesReportInfo 	查询条件对象
	 * @param request				请求参数
	 * @param redirectAttributes	返回页面信息
	 * @param response				响应数据
	 * @param model					模版
	 * @return
	 */
	@RequiresPermissions("flow:expenseFlow:expenseSubInfoReport:cent:view")
	@RequestMapping(value = { "list", "" })
	public String list (ExpensesReportInfo expensesReportInfo, HttpServletRequest request, RedirectAttributes redirectAttributes, HttpServletResponse response,
						Model model) {
		
		if(StringUtils.isNotBlank(expensesReportInfo.getIsInit()) && "1".equals(expensesReportInfo.getIsInit())){
			model.addAttribute("ReportSpanLists", expenseSubInfoReportService.findReportSpan(null));
			model.addAttribute("findSecondRows", expenseSubInfoReportService.findSecondRow(null));
			model.addAttribute("data", null);
			model.addAttribute("items", expensesReportInfo);
			return "modules/oa/reportPCTList";
		}
		model.addAttribute("projectList",projectInfoService.findList(new ProjectInfo()));
		System.out.println(expensesReportInfo.getIsInit());
		// 获取所有的数字信息
		List<List<String>> data = expenseSubInfoReportPCTService.findData(expensesReportInfo);
		
		List<ReportSpan> firstRowList = Lists.newArrayList();
		List<ReportSpan> secondRowList = Lists.newArrayList();
		if(StringUtils.isBlank(expensesReportInfo.getIsHide()) || "1".equals(expensesReportInfo.getIsHide())){
			//隐藏数值并获取需要隐藏的列号
			List<Integer> hideNum = expenseSubInfoReportService.hideNumPCT(data);
			//隐藏列名
			// 查询第一行
			firstRowList = expenseSubInfoReportService.findReportSpan(hideNum);
			// 查询第二行
			secondRowList = expenseSubInfoReportService.findSecondRow(hideNum);
		}else{
			// 查询第一行
			firstRowList = expenseSubInfoReportService.findReportSpan(null);
			// 查询第二行
			secondRowList = expenseSubInfoReportService.findSecondRow(null);
		}
		if(StringUtils.isNotBlank(expensesReportInfo.getQuerOffice())){
			expensesReportInfo.setQuerOfficeName(officeService.get(expensesReportInfo.getQuerOffice()).getName());
		}
		
		model.addAttribute("ReportSpanLists", firstRowList);
		model.addAttribute("findSecondRows", secondRowList);
		model.addAttribute("data", data);
		model.addAttribute("items", expensesReportInfo);
		return "modules/oa/reportPCTList";
	}
	

	/**
	 * 导出表格
	 * @param expensesReportInfo
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequiresPermissions("oa:report:expenseFlow:view")
    @RequestMapping(value = "expenseFlowExportPCT", method= RequestMethod.POST)
    public String exportFile(ExpensesReportInfo expensesReportInfo, HttpServletRequest request,
							 HttpServletResponse response, RedirectAttributes redirectAttributes) throws UnsupportedEncodingException {
		// 获取所有的数字信息
		List<List<String>> data = expenseSubInfoReportPCTService.findData(expensesReportInfo);
		if(data == null || data.size() <= 1){
			return "redirect:" + adminPath + "/oa/report/expenseFlow?repage&isInit=1";
		}
		
		List<ReportSpan> firstRowList = Lists.newArrayList();
		List<ReportSpan> secondRowList = Lists.newArrayList();
		if(StringUtils.isBlank(expensesReportInfo.getIsHide()) || "1".equals(expensesReportInfo.getIsHide())){
			//隐藏数值并获取需要隐藏的列号
			List<Integer> hideNum = expenseSubInfoReportService.hideNum(data);
			//隐藏列名
			// 查询第一行
			firstRowList = expenseSubInfoReportService.findReportSpan(hideNum);
			// 查询第二行
			secondRowList = expenseSubInfoReportService.findSecondRow(hideNum);
		}else{
			// 查询第一行
			firstRowList = expenseSubInfoReportService.findReportSpan(null);
			// 查询第二行
			secondRowList = expenseSubInfoReportService.findSecondRow(null);
		}
		ReportSpan rs = new ReportSpan();
		rs.setRowName("部门");
		rs.setRowspan("2");
		rs.setColspan("1");
		firstRowList.add(0, rs);
		rs = new ReportSpan();
		rs.setRowName("姓名");
		rs.setRowspan("2");
		rs.setColspan("1");
		firstRowList.add(1, rs);
		rs = new ReportSpan();
		rs.setRowName("项目名称");
		rs.setRowspan("2");
		rs.setColspan("1");
		firstRowList.add(2, rs);
		String fileName = "报销百分比统计报表-"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
		try {
			ExportExcel ee = new ExportExcel("报销百分比统计报表", firstRowList, secondRowList);
			for (List<String> strList : data) {
				org.apache.poi.ss.usermodel.Row row = ee.addRow();
				for(int i = 0;i<strList.size();i++){
					ee.addCell(row, i, strList.get(i), 2, Class.class);
				}
			}
			ee.writeUtf8(response, fileName);
			ee.dispose();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:" + adminPath + "/oa/sys?repage";
	}
}
