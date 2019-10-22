package sijibao.oa.jeesite.modules.intfz.service.report;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.sijibao.oa.common.persistence.Page;
import com.sijibao.oa.common.service.BaseService;
import com.sijibao.oa.common.utils.DateUtils;
import com.sijibao.oa.common.utils.UploadUtils;
import com.sijibao.oa.common.utils.excel.ExportExcel;
import com.sijibao.oa.modules.flow.entity.report.EmployeeReport;
import com.sijibao.oa.modules.flow.entity.report.MonthAmountSumReport;
import com.sijibao.oa.modules.flow.service.report.EmployeeReportService;
import com.sijibao.oa.modules.intfz.request.report.EmployeeReportRequest;
import com.sijibao.oa.modules.intfz.response.report.EmployeeReportResponse;
import com.sijibao.oa.modules.intfz.response.report.ReportMonthSumResponse;
import com.sijibao.oa.modules.sys.dao.UserDao;
import com.sijibao.oa.modules.sys.entity.User;
import com.sijibao.oa.modules.sys.service.OfficeService;

/**
 * 员工报表查询服务
 * @author wanxb
 */
@Service
@Transactional(readOnly = true)
public class IntfzEmployeeReportService extends BaseService {
	
	@Autowired
	private OfficeService officeService;
	@Autowired
	private EmployeeReportService employeeReportService;
	@Autowired
	private UserDao userDao;
	
	/**
	 * 员工报表查询(带分页)
	 * @param req
	 * @return
	 */
	public Page<EmployeeReportResponse> queryEmployeeReport(Page<EmployeeReportResponse> page,EmployeeReportRequest req) {
		EmployeeReport e = new EmployeeReport();
		e.setEmployeeName(req.getEmployeeName());
		int year = 0;
		if(StringUtils.isBlank(req.getYear())){
			year = Integer.parseInt(DateUtils.getYear());
		}else{
			year = Integer.parseInt(req.getYear());
		}
		e.setYear(String.valueOf(year));
		if(StringUtils.isNotBlank(req.getOfficeId())){
			e.setOffice(officeService.get(req.getOfficeId()));
		}
		ArrayList<EmployeeReportResponse> newList = Lists.newArrayList();
		User user = new User();
		user.getPage().setPageNo(req.getPageNo());
		user.getPage().setPageSize(req.getPageSize());
		if(StringUtils.isNotBlank(req.getOfficeId())){
			user.setOffice(officeService.get(req.getOfficeId()));
		}
		if(StringUtils.isNotBlank(req.getEmployeeName())){
			user.setName(req.getEmployeeName());
		}
		user.setYear(req.getYear()==""?DateUtils.getYear():req.getYear());
		List<User> findList = userDao.findReportList(user);
		page.setCount(user.getPage().getCount());
		page.setPageNo(user.getPage().getPageNo());
		page.setPageSize(user.getPage().getPageSize());
		for (User em : findList) {
			EmployeeReportResponse resp = new EmployeeReportResponse();
			resp.setEmployeeName(em.getName());
			resp.setIsDetail("0");
			resp.setOfficeName(em.getOffice().getName());
			for (EmployeeReport ee :employeeReportService.queryEmployeeReport(e) ) {
				if(StringUtils.equals(em.getLoginName(),ee.getEmployeeLoginName())){
					resp.setRowTotal(resp.getRowTotal().add(ee.getExpenseAmt())); //行汇总金额
					if(ee.getTaskFinishTime() == null){
						continue;
					}
					switch (DateUtils.getMonth(ee.getTaskFinishTime())) { //判断月份
					case "01":
						resp.setM1(resp.getM1().add(ee.getExpenseAmt()));
						break;
					case "02":
						resp.setM2(resp.getM2().add(ee.getExpenseAmt()));
						break;
					case "03":
						resp.setM3(resp.getM3().add(ee.getExpenseAmt()));
						break;
					case "04":
						resp.setM4(resp.getM4().add(ee.getExpenseAmt()));
						break;
					case "05":
						resp.setM5(resp.getM5().add(ee.getExpenseAmt()));
						break;
					case "06":
						resp.setM6(resp.getM6().add(ee.getExpenseAmt()));
						break;
					case "07":
						resp.setM7(resp.getM7().add(ee.getExpenseAmt()));
						break;
					case "08":
						resp.setM8(resp.getM8().add(ee.getExpenseAmt()));
						break;
					case "09":
						resp.setM9(resp.getM9().add(ee.getExpenseAmt()));
						break;
					case "10":
						resp.setM10(resp.getM10().add(ee.getExpenseAmt()));
						break;
					case "11":
						resp.setM11(resp.getM11().add(ee.getExpenseAmt()));
						break;
					case "12":
						resp.setM12(resp.getM12().add(ee.getExpenseAmt()));
						break;
					default:
						break;
					}
				}
			}
			newList.add(resp);
		}
		page.setList(newList);
		return page;
	}
	
	/**
	 * 员工报表查询(不带分页)
	 * @param req
	 * @return
	 */
	public List<EmployeeReportResponse> queryEmployeeReport(EmployeeReportRequest req) {
		EmployeeReport e = new EmployeeReport();
		e.setEmployeeName(req.getEmployeeName());
		int year = 0;
		if(StringUtils.isBlank(req.getYear())){
			year = Integer.parseInt(DateUtils.getYear());
		}else{
			year = Integer.parseInt(req.getYear());
		}
		e.setYear(String.valueOf(year));
		e.setOffice(officeService.get(req.getOfficeId()));
		List<EmployeeReport> list = employeeReportService.queryEmployeeReport(e);
		List<EmployeeReportResponse> resultList = Lists.newArrayList();
		User user = new User();
		List<User> findList = userDao.findList(user);
		for (User em :findList) {
			EmployeeReportResponse resp = new EmployeeReportResponse();
			resp.setEmployeeName(em.getName());
			resp.setIsDetail("0");
			resp.setOfficeName(em.getOffice().getName());
			for (EmployeeReport ee : list) {
				if(StringUtils.equals(em.getLoginName(),ee.getEmployeeLoginName())){
					resp.setRowTotal(resp.getRowTotal().add(ee.getExpenseAmt())); //行汇总金额
					if(ee.getTaskFinishTime() == null){
						continue;
					}
					switch (DateUtils.getMonth(ee.getTaskFinishTime())) { //判断月份
					case "01":
						resp.setM1(resp.getM1().add(ee.getExpenseAmt()));
						break;
					case "02":
						resp.setM2(resp.getM2().add(ee.getExpenseAmt()));
						break;
					case "03":
						resp.setM3(resp.getM3().add(ee.getExpenseAmt()));
						break;
					case "04":
						resp.setM4(resp.getM4().add(ee.getExpenseAmt()));
						break;
					case "05":
						resp.setM5(resp.getM5().add(ee.getExpenseAmt()));
						break;
					case "06":
							resp.setM6(resp.getM6().add(ee.getExpenseAmt()));
						break;
					case "07":
						resp.setM7(resp.getM7().add(ee.getExpenseAmt()));
						break;
					case "08":
						resp.setM8(resp.getM8().add(ee.getExpenseAmt()));
						break;
					case "09":
						resp.setM9(resp.getM9().add(ee.getExpenseAmt()));
						break;
					case "10":
						resp.setM10(resp.getM10().add(ee.getExpenseAmt()));
						break;
					case "11":
						resp.setM11(resp.getM11().add(ee.getExpenseAmt()));
						break;
					case "12":
						resp.setM12(resp.getM12().add(ee.getExpenseAmt()));
						break;
					default:
						break;
					}
				}
			}
			resultList.add(resp);
		}
		return resultList;
	}
	
	
	/**
	 * 员工报表导出
	 * @param req
	 * @param fileName
	 * @return
	 */
	public String exportEmployeeReport(EmployeeReportRequest req,String fileName){
		
		List<EmployeeReportResponse> resultList = this.queryEmployeeReport(req); //查询所有报表数据
		String year = req.getYear();
		if(StringUtils.isBlank(req.getYear())){
			year = DateUtils.getYear();
		}
		DecimalFormat df = new DecimalFormat("0.00");
		String url = "";
		 // 创建一个新的Excel
		HSSFWorkbook  workBook = new HSSFWorkbook();
        // 创建sheet页
		HSSFSheet sheet = workBook.createSheet();
        // 设置表名
		workBook.setSheetName(0, "员工费用报表");
		// 第0行 列头
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell = null;
        HSSFCellStyle style = ExportExcel.getStyle(workBook);
		ExportExcel.myCreateCell(0, "部门名称", row, cell, style);
        ExportExcel.myCreateCell(1, "员工名称", row, cell, style);
        ExportExcel.myCreateCell(2, year+"年1月", row, cell, style);
        ExportExcel.myCreateCell(3, year+"年2月", row, cell, style);
        ExportExcel.myCreateCell(4, year+"年3月", row, cell, style);
        ExportExcel.myCreateCell(5, year+"年4月", row, cell, style);
        ExportExcel.myCreateCell(6, year+"年5月", row, cell, style);
        ExportExcel.myCreateCell(7, year+"年6月", row, cell, style);
        ExportExcel.myCreateCell(8, year+"年7月", row, cell, style);
        ExportExcel.myCreateCell(9, year+"年8月", row, cell, style);
        ExportExcel.myCreateCell(10, year+"年9月", row, cell, style);
        ExportExcel.myCreateCell(11, year+"年10月", row, cell, style);
        ExportExcel.myCreateCell(12, year+"年11月", row, cell, style);
        ExportExcel.myCreateCell(13, year+"年12月", row, cell, style);
        ExportExcel.myCreateCell(14, year+"总计", row, cell, style);
        BigDecimal totalM1 = BigDecimal.ZERO;
        BigDecimal totalM2 = BigDecimal.ZERO;
        BigDecimal totalM3 = BigDecimal.ZERO;
        BigDecimal totalM4 = BigDecimal.ZERO;
        BigDecimal totalM5 = BigDecimal.ZERO;
        BigDecimal totalM6 = BigDecimal.ZERO;
        BigDecimal totalM7 = BigDecimal.ZERO;
        BigDecimal totalM8 = BigDecimal.ZERO;
        BigDecimal totalM9 = BigDecimal.ZERO;
        BigDecimal totalM10 = BigDecimal.ZERO;
        BigDecimal totalM11 = BigDecimal.ZERO;
        BigDecimal totalM12 = BigDecimal.ZERO;
        BigDecimal totalRow = BigDecimal.ZERO;
        
        //设置数据
        int rowNum = 1;
        for(EmployeeReportResponse q:resultList){
        	row = sheet.createRow(rowNum);
			ExportExcel.myCreateCell(0, q.getOfficeName(), row, cell, style);
        	ExportExcel.myCreateCell(1, q.getEmployeeName(), row, cell, style);
        	ExportExcel.myCreateCell(2, df.format(q.getM1()), row, cell, style);
        	ExportExcel.myCreateCell(3, df.format(q.getM2()), row, cell, style);
        	ExportExcel.myCreateCell(4, df.format(q.getM3()), row, cell, style);
        	ExportExcel.myCreateCell(5, df.format(q.getM4()), row, cell, style);
        	ExportExcel.myCreateCell(6, df.format(q.getM5()), row, cell, style);
        	ExportExcel.myCreateCell(7, df.format(q.getM6()), row, cell, style);
        	ExportExcel.myCreateCell(8, df.format(q.getM7()), row, cell, style);
        	ExportExcel.myCreateCell(9, df.format(q.getM8()), row, cell, style);
        	ExportExcel.myCreateCell(10, df.format(q.getM9()), row, cell, style);
        	ExportExcel.myCreateCell(11, df.format(q.getM10()), row, cell, style);
        	ExportExcel.myCreateCell(12, df.format(q.getM11()), row, cell, style);
        	ExportExcel.myCreateCell(13, df.format(q.getM12()), row, cell, style);
        	ExportExcel.myCreateCell(14, df.format(q.getRowTotal()), row, cell, style);
        	totalM1 = totalM1.add(q.getM1());
        	totalM2 = totalM2.add(q.getM2());
        	totalM3 = totalM3.add(q.getM3());
        	totalM4 = totalM4.add(q.getM4());
        	totalM5 = totalM5.add(q.getM5());
        	totalM6 = totalM6.add(q.getM6());
        	totalM7 = totalM7.add(q.getM7());
        	totalM8 = totalM8.add(q.getM8());
        	totalM9 = totalM9.add(q.getM9());
        	totalM10 = totalM10.add(q.getM10());
        	totalM11 = totalM11.add(q.getM11());
        	totalM12 = totalM12.add(q.getM12());
        	totalRow = totalRow.add(q.getRowTotal());
        	rowNum++;
        }
        
        row = sheet.createRow(rowNum);
		ExportExcel.myCreateCell(0, "合计", row, cell, style);
        ExportExcel.myCreateCell(1, "", row, cell, style);
    	ExportExcel.myCreateCell(2, df.format(totalM1), row, cell, style);
    	ExportExcel.myCreateCell(3, df.format(totalM2), row, cell, style);
    	ExportExcel.myCreateCell(4, df.format(totalM3), row, cell, style);
    	ExportExcel.myCreateCell(5, df.format(totalM4), row, cell, style);
    	ExportExcel.myCreateCell(6, df.format(totalM5), row, cell, style);
    	ExportExcel.myCreateCell(7, df.format(totalM6), row, cell, style);
    	ExportExcel.myCreateCell(8, df.format(totalM7), row, cell, style);
    	ExportExcel.myCreateCell(9, df.format(totalM8), row, cell, style);
    	ExportExcel.myCreateCell(10, df.format(totalM9), row, cell, style);
    	ExportExcel.myCreateCell(11, df.format(totalM10), row, cell, style);
    	ExportExcel.myCreateCell(12, df.format(totalM11), row, cell, style);
    	ExportExcel.myCreateCell(13, df.format(totalM12), row, cell, style);
    	ExportExcel.myCreateCell(14, df.format(totalRow), row, cell, style);
        
        try {
        	//保存到服务器
        	ByteArrayOutputStream os=new ByteArrayOutputStream(); 
			workBook.write(os);
			url = UploadUtils.uploadDoc(os, fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return url;
	}
	/**
	 * 查询月度汇总
	 * @param req
	 * @return
	 */
	public ReportMonthSumResponse querySubjectMonthSum(EmployeeReportRequest req) {
		ReportMonthSumResponse resp = new ReportMonthSumResponse();
		int year = 0;
		if(StringUtils.isBlank(req.getYear())){
			year = Integer.parseInt(DateUtils.getYear());
		}else{
			year = Integer.parseInt(req.getYear());
		}
		EmployeeReport em = new EmployeeReport();
		em.setYear(String.valueOf(year));
		em.setEmployeeName(req.getEmployeeName());
		em.setOffice(officeService.get(req.getOfficeId()));
		em.setIsDetail("0");
		List<MonthAmountSumReport> monthAmountList = employeeReportService.queryMonthAmountSum(em);
		for(MonthAmountSumReport m:monthAmountList){
			resp.setRowTotal(resp.getRowTotal().add(m.getExpenseAmt()));
			switch (m.getTaskFinishTime().substring(5, 7)) { //判断月份
			case "01":
				resp.setM1(m.getExpenseAmt());
				continue;
			case "02":
				resp.setM2(m.getExpenseAmt());
				continue;
			case "03":
				resp.setM3(m.getExpenseAmt());
				continue;
			case "04":
				resp.setM4(m.getExpenseAmt());
				continue;
			case "05":
				resp.setM5(m.getExpenseAmt());
				continue;
			case "06":
				resp.setM6(m.getExpenseAmt());
				continue;
			case "07":
				resp.setM7(m.getExpenseAmt());
				continue;
			case "08":
				resp.setM8(m.getExpenseAmt());
				continue;
			case "09":
				resp.setM9(m.getExpenseAmt());
				continue;
			case "10":
				resp.setM10(m.getExpenseAmt());
				continue;
			case "11":
				resp.setM11(m.getExpenseAmt());
				continue;
			case "12":
				resp.setM12(m.getExpenseAmt());
				continue;
			default:
				continue;
			}
		}
		return resp;
	}
	
}
