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

import com.sijibao.oa.common.persistence.Page;
import com.sijibao.oa.common.service.BaseService;
import com.sijibao.oa.common.utils.DateUtils;
import com.sijibao.oa.common.utils.UploadUtils;
import com.sijibao.oa.common.utils.excel.ExportExcel;
import com.sijibao.oa.modules.flow.entity.report.OfficeCostReport;
import com.sijibao.oa.modules.flow.service.report.OfficeCostReportService;
import com.sijibao.oa.modules.intfz.request.report.OfficeCostReportRequest;
import com.sijibao.oa.modules.intfz.response.report.OfficeCostReportResponse;
import com.sijibao.oa.modules.intfz.response.report.ReportMonthSumResponse;
import com.sijibao.oa.modules.sys.entity.Office;

/**
 * 部门费用报表查询服务
 * @author xby
 */
@Service
@Transactional(readOnly = true)
public class IntfzOfficeCostReportService extends BaseService {
	
	@Autowired
	private OfficeCostReportService officeCostReportService;
	
	/**
	 * 查询部门费用报表信息
	 * @param page
	 * @param req
	 * @return
	 */									  
	public Page<OfficeCostReportResponse> queryOfficeCostReportInfo(Page<OfficeCostReportResponse> page,OfficeCostReportRequest req){
		//查询部门信息
		Office office = new Office();
		office.setGrade("2");
		Page<Office> officePage = officeCostReportService.queryOfficeInfo(new Page<Office>(req.getPageNo(), req.getPageSize()), office);
		page.setCount(officePage.getCount());
		page.setPageNo(officePage.getPageNo());
		page.setPageSize(officePage.getPageSize());
		int year = 0;
		if(StringUtils.isBlank(req.getYear())){
			year = Integer.parseInt(DateUtils.getYear());
		}else{
			year = Integer.parseInt(req.getYear());
		}
		
		//查询部门费用明细数据
		OfficeCostReport r = new OfficeCostReport();
		r.setBeginTime(DateUtils.getYearFirst(year));
		r.setEndTime(DateUtils.getYearFirst(year+1));
		List<OfficeCostReport> officeCostReportList = officeCostReportService.queryOfficeCostInfo(r);
		
		for(Office o:officePage.getList()){
			OfficeCostReportResponse officeCostReportResponse = new OfficeCostReportResponse();
			officeCostReportResponse.setOfficeId(o.getId()); //部门ID
			officeCostReportResponse.setOfficeName(o.getName()); //部门名称
			officeCostReportResponse.setIsDetail("0");
			for(OfficeCostReport officeCostReport:officeCostReportList){
				if(officeCostReport.getParentIds().startsWith(o.getParentIds())){
					officeCostReportResponse.setRowTotal(officeCostReportResponse.getRowTotal().add(officeCostReport.getExpenseAmt())); //行汇总
					if(officeCostReport.getTaskFinishTime() == null){
						continue;
					}
					switch (DateUtils.getMonth(officeCostReport.getTaskFinishTime())) { //判断月份
					case "01":
						officeCostReportResponse.setM1(officeCostReportResponse.getM1().add(officeCostReport.getExpenseAmt()));
						continue;
					case "02":
						officeCostReportResponse.setM2(officeCostReportResponse.getM2().add(officeCostReport.getExpenseAmt()));
						continue;
					case "03":
						officeCostReportResponse.setM3(officeCostReportResponse.getM3().add(officeCostReport.getExpenseAmt()));
						continue;
					case "04":
						officeCostReportResponse.setM4(officeCostReportResponse.getM4().add(officeCostReport.getExpenseAmt()));
						continue;
					case "05":
						officeCostReportResponse.setM5(officeCostReportResponse.getM5().add(officeCostReport.getExpenseAmt()));
						continue;
					case "06":
						officeCostReportResponse.setM6(officeCostReportResponse.getM6().add(officeCostReport.getExpenseAmt()));
						continue;
					case "07":
						officeCostReportResponse.setM7(officeCostReportResponse.getM7().add(officeCostReport.getExpenseAmt()));
						continue;
					case "08":
						officeCostReportResponse.setM8(officeCostReportResponse.getM8().add(officeCostReport.getExpenseAmt()));
						continue;
					case "09":
						officeCostReportResponse.setM9(officeCostReportResponse.getM9().add(officeCostReport.getExpenseAmt()));
						continue;
					case "10":
						officeCostReportResponse.setM10(officeCostReportResponse.getM10().add(officeCostReport.getExpenseAmt()));
						continue;
					case "11":
						officeCostReportResponse.setM11(officeCostReportResponse.getM11().add(officeCostReport.getExpenseAmt()));
						continue;
					case "12":
						officeCostReportResponse.setM12(officeCostReportResponse.getM12().add(officeCostReport.getExpenseAmt()));
						continue;
					default:
						continue;
					}
				}
			}
			page.getList().add(officeCostReportResponse);
		}
		return page;
	}
	
	/**
	 * 查询部门费用报表信息
	 * @param page
	 * @param req
	 * @return
	 */
	public List<OfficeCostReportResponse> queryOfficeCostReportInfo(OfficeCostReportRequest req){
		List<OfficeCostReportResponse> resultList = new ArrayList<OfficeCostReportResponse>();
		
		//查询部门信息
		Office office = new Office();
		office.setGrade("2");
		List<Office> officeList = officeCostReportService.queryOfficeInfo(office);
		
		int year = 0;
		if(StringUtils.isBlank(req.getYear())){
			year = Integer.parseInt(DateUtils.getYear());
		}else{
			year = Integer.parseInt(req.getYear());
		}
		
		//查询部门费用明细数据
		OfficeCostReport r = new OfficeCostReport();
		r.setBeginTime(DateUtils.getYearFirst(year));
		r.setEndTime(DateUtils.getYearFirst(year+1));
		List<OfficeCostReport> officeCostReportList = officeCostReportService.queryOfficeCostInfo(r);
		
		for(Office o:officeList){
			OfficeCostReportResponse officeCostReportResponse = new OfficeCostReportResponse();
			officeCostReportResponse.setOfficeId(o.getId()); //部门ID
			officeCostReportResponse.setOfficeName(o.getName()); //部门名称
			officeCostReportResponse.setIsDetail("0");
			for(OfficeCostReport officeCostReport:officeCostReportList){
				if(officeCostReport.getParentIds().startsWith(o.getParentIds())){
					officeCostReportResponse.setRowTotal(officeCostReportResponse.getRowTotal().add(officeCostReport.getExpenseAmt())); //行汇总
					if(officeCostReport.getTaskFinishTime() == null){
						continue;
					}
					switch (DateUtils.getMonth(officeCostReport.getTaskFinishTime())) { //判断月份
					case "01":
						officeCostReportResponse.setM1(officeCostReportResponse.getM1().add(officeCostReport.getExpenseAmt()));
						continue;
					case "02":
						officeCostReportResponse.setM2(officeCostReportResponse.getM2().add(officeCostReport.getExpenseAmt()));
						continue;
					case "03":
						officeCostReportResponse.setM3(officeCostReportResponse.getM3().add(officeCostReport.getExpenseAmt()));
						continue;
					case "04":
						officeCostReportResponse.setM4(officeCostReportResponse.getM4().add(officeCostReport.getExpenseAmt()));
						continue;
					case "05":
						officeCostReportResponse.setM5(officeCostReportResponse.getM5().add(officeCostReport.getExpenseAmt()));
						continue;
					case "06":
						officeCostReportResponse.setM6(officeCostReportResponse.getM6().add(officeCostReport.getExpenseAmt()));
						continue;
					case "07":
						officeCostReportResponse.setM7(officeCostReportResponse.getM7().add(officeCostReport.getExpenseAmt()));
						continue;
					case "08":
						officeCostReportResponse.setM8(officeCostReportResponse.getM8().add(officeCostReport.getExpenseAmt()));
						continue;
					case "09":
						officeCostReportResponse.setM9(officeCostReportResponse.getM9().add(officeCostReport.getExpenseAmt()));
						continue;
					case "10":
						officeCostReportResponse.setM10(officeCostReportResponse.getM10().add(officeCostReport.getExpenseAmt()));
						continue;
					case "11":
						officeCostReportResponse.setM11(officeCostReportResponse.getM11().add(officeCostReport.getExpenseAmt()));
						continue;
					case "12":
						officeCostReportResponse.setM12(officeCostReportResponse.getM12().add(officeCostReport.getExpenseAmt()));
						continue;
					default:
						continue;
					}
				}
			}
			resultList.add(officeCostReportResponse);
		}
		return resultList;
	}
	
	/**
	 * 部门费用报表导出
	 * @param resultList
	 * @param year
	 * @return
	 */
	public String exportOfficeCostReport(OfficeCostReportRequest req,String fileName){
		
		List<OfficeCostReportResponse> resultList = this.queryOfficeCostReportInfo(req); //查询所有报表数据
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
		workBook.setSheetName(0, "部门费用报表");
		// 第0行 列头
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell = null;
        HSSFCellStyle style = ExportExcel.getStyle(workBook);
        ExportExcel.myCreateCell(0, "部门名称", row, cell, style);
        ExportExcel.myCreateCell(1, year+"年1月", row, cell, style);
        ExportExcel.myCreateCell(2, year+"年2月", row, cell, style);
        ExportExcel.myCreateCell(3, year+"年3月", row, cell, style);
        ExportExcel.myCreateCell(4, year+"年4月", row, cell, style);
        ExportExcel.myCreateCell(5, year+"年5月", row, cell, style);
        ExportExcel.myCreateCell(6, year+"年6月", row, cell, style);
        ExportExcel.myCreateCell(7, year+"年7月", row, cell, style);
        ExportExcel.myCreateCell(8, year+"年8月", row, cell, style);
        ExportExcel.myCreateCell(9, year+"年9月", row, cell, style);
        ExportExcel.myCreateCell(10, year+"年10月", row, cell, style);
        ExportExcel.myCreateCell(11, year+"年11月", row, cell, style);
        ExportExcel.myCreateCell(12, year+"年12月", row, cell, style);
        ExportExcel.myCreateCell(13, year+"总计", row, cell, style);
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
        for(OfficeCostReportResponse q:resultList){
        	row = sheet.createRow(rowNum);
        	ExportExcel.myCreateCell(0, q.getOfficeName(), row, cell, style);
        	ExportExcel.myCreateCell(1, df.format(q.getM1()), row, cell, style);
        	ExportExcel.myCreateCell(2, df.format(q.getM2()), row, cell, style);
        	ExportExcel.myCreateCell(3, df.format(q.getM3()), row, cell, style);
        	ExportExcel.myCreateCell(4, df.format(q.getM4()), row, cell, style);
        	ExportExcel.myCreateCell(5, df.format(q.getM5()), row, cell, style);
        	ExportExcel.myCreateCell(6, df.format(q.getM6()), row, cell, style);
        	ExportExcel.myCreateCell(7, df.format(q.getM7()), row, cell, style);
        	ExportExcel.myCreateCell(8, df.format(q.getM8()), row, cell, style);
        	ExportExcel.myCreateCell(9, df.format(q.getM9()), row, cell, style);
        	ExportExcel.myCreateCell(10, df.format(q.getM10()), row, cell, style);
        	ExportExcel.myCreateCell(11, df.format(q.getM11()), row, cell, style);
        	ExportExcel.myCreateCell(12, df.format(q.getM12()), row, cell, style);
        	ExportExcel.myCreateCell(13, df.format(q.getRowTotal()), row, cell, style);
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
    	ExportExcel.myCreateCell(1, df.format(totalM1), row, cell, style);
    	ExportExcel.myCreateCell(2, df.format(totalM2), row, cell, style);
    	ExportExcel.myCreateCell(3, df.format(totalM3), row, cell, style);
    	ExportExcel.myCreateCell(4, df.format(totalM4), row, cell, style);
    	ExportExcel.myCreateCell(5, df.format(totalM5), row, cell, style);
    	ExportExcel.myCreateCell(6, df.format(totalM6), row, cell, style);
    	ExportExcel.myCreateCell(7, df.format(totalM7), row, cell, style);
    	ExportExcel.myCreateCell(8, df.format(totalM8), row, cell, style);
    	ExportExcel.myCreateCell(9, df.format(totalM9), row, cell, style);
    	ExportExcel.myCreateCell(10, df.format(totalM10), row, cell, style);
    	ExportExcel.myCreateCell(11, df.format(totalM11), row, cell, style);
    	ExportExcel.myCreateCell(12, df.format(totalM12), row, cell, style);
    	ExportExcel.myCreateCell(13, df.format(totalRow), row, cell, style);
    	
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
	 * 查询部门月度费用汇总
	 * @param req
	 * @return
	 */
	public ReportMonthSumResponse queryOfficeMonthAmountSum(OfficeCostReportRequest req){
		ReportMonthSumResponse resp = new ReportMonthSumResponse();
		int year = 0;
		if(StringUtils.isBlank(req.getYear())){
			year = Integer.parseInt(DateUtils.getYear());
		}else{
			year = Integer.parseInt(req.getYear());
		}
		OfficeCostReport r = new OfficeCostReport();
		r.setBeginTime(DateUtils.getYearFirst(year));
		r.setEndTime(DateUtils.getYearFirst(year+1));
		List<OfficeCostReport> officeCostReportList = officeCostReportService.queryOfficeMonthAmounySumCostInfo(r);
		for(OfficeCostReport o:officeCostReportList){
			resp.setRowTotal(resp.getRowTotal().add(o.getExpenseAmt()));
			switch (o.getFlowFinishTime().substring(5, 7)) { //判断月份
			case "01":
				resp.setM1(o.getExpenseAmt());
				continue;
			case "02":
				resp.setM2(o.getExpenseAmt());
				continue;
			case "03":
				resp.setM3(o.getExpenseAmt());
				continue;
			case "04":
				resp.setM4(o.getExpenseAmt());
				continue;
			case "05":
				resp.setM5(o.getExpenseAmt());
				continue;
			case "06":
				resp.setM6(o.getExpenseAmt());
				continue;
			case "07":
				resp.setM7(o.getExpenseAmt());
				continue;
			case "08":
				resp.setM8(o.getExpenseAmt());
				continue;
			case "09":
				resp.setM9(o.getExpenseAmt());
				continue;
			case "10":
				resp.setM10(o.getExpenseAmt());
				continue;
			case "11":
				resp.setM11(o.getExpenseAmt());
				continue;
			case "12":
				resp.setM12(o.getExpenseAmt());
				continue;
			default:
				continue;
			}
		}
		return resp;
	}
	
}
