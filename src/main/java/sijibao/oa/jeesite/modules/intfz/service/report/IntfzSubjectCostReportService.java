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
import com.sijibao.oa.modules.flow.entity.report.MonthAmountSumReport;
import com.sijibao.oa.modules.flow.entity.report.SubjectCostReport;
import com.sijibao.oa.modules.flow.service.report.SubjectCostReportService;
import com.sijibao.oa.modules.intfz.request.report.SubjectCostReportRequest;
import com.sijibao.oa.modules.intfz.response.report.ReportMonthSumResponse;
import com.sijibao.oa.modules.intfz.response.report.SubjectCostReportResponse;

/**
 * 科目费用报表查询服务
 * @author xby
 */
@Service
@Transactional(readOnly = true)
public class IntfzSubjectCostReportService extends BaseService {
	
	@Autowired
	private SubjectCostReportService subjectCostReportService;
	
	/**
	 * 查询科目费用报表信息
	 * @param page
	 * @param req
	 * @return
	 */
	public Page<SubjectCostReportResponse> querySubjectCostReportInfo(Page<SubjectCostReportResponse> page,SubjectCostReportRequest req){
		
		//查询科目信息
		SubjectCostReport subjectCostReport = new SubjectCostReport();
		subjectCostReport.setFirstCode(req.getFirstCode());
		Page<SubjectCostReport> subjectInfoPage = 
				subjectCostReportService.querySubjectInfo(new Page<SubjectCostReport>(req.getPageNo(), req.getPageSize()), subjectCostReport);
		page.setCount(subjectInfoPage.getCount());
		page.setPageNo(subjectInfoPage.getPageNo());
		page.setPageSize(subjectInfoPage.getPageSize());
		
		int year = 0;
		if(StringUtils.isBlank(req.getYear())){
			year = Integer.parseInt(DateUtils.getYear());
		}else{
			year = Integer.parseInt(req.getYear());
		}
		SubjectCostReport sc = new SubjectCostReport();
		sc.setFirstCode(req.getFirstCode());
		sc.setBeginTime(DateUtils.getYearFirst(year)); //开始时间
		sc.setEndTime(DateUtils.getYearFirst(year+1)); //结束时间
		//查询科目明细数据
		List<SubjectCostReport> subjectCostList = new ArrayList<SubjectCostReport>();
		if(StringUtils.isNotBlank(req.getFirstCode())){
			subjectCostList = subjectCostReportService.querySecondSubjectCostReport(sc);
		}else{
			subjectCostList = subjectCostReportService.querySubjectCostReport(sc);
		}
		
		for(SubjectCostReport s:subjectInfoPage.getList()){
			SubjectCostReportResponse subjectCostReportResponse = new SubjectCostReportResponse();
			subjectCostReportResponse.setIsDetail(s.getIsDetail()); //是否有明细
			subjectCostReportResponse.setSubjectCode(s.getSubjectCode()); //科目编码
			subjectCostReportResponse.setSubjectName(s.getSubjectName()); //科目名称
			
			for(SubjectCostReport s1:subjectCostList){
				if(StringUtils.equals(s1.getSubjectCode(),s.getSubjectCode())){
					subjectCostReportResponse.setRowTotal(subjectCostReportResponse.getRowTotal().add(s1.getExpenseAmt())); //行汇总金额
					if(s1.getTaskFinishTime() == null){
						continue;
					}
					switch (DateUtils.getMonth(s1.getTaskFinishTime())) { //判断月份
					case "01":
						subjectCostReportResponse.setM1(subjectCostReportResponse.getM1().add(s1.getExpenseAmt()));
						continue;
					case "02":
						subjectCostReportResponse.setM2(subjectCostReportResponse.getM2().add(s1.getExpenseAmt()));
						continue;
					case "03":
						subjectCostReportResponse.setM3(subjectCostReportResponse.getM3().add(s1.getExpenseAmt()));
						continue;
					case "04":
						subjectCostReportResponse.setM4(subjectCostReportResponse.getM4().add(s1.getExpenseAmt()));
						continue;
					case "05":
						subjectCostReportResponse.setM5(subjectCostReportResponse.getM5().add(s1.getExpenseAmt()));
						continue;
					case "06":
						subjectCostReportResponse.setM6(subjectCostReportResponse.getM6().add(s1.getExpenseAmt()));
						continue;
					case "07":
						subjectCostReportResponse.setM7(subjectCostReportResponse.getM7().add(s1.getExpenseAmt()));
						continue;
					case "08":
						subjectCostReportResponse.setM8(subjectCostReportResponse.getM8().add(s1.getExpenseAmt()));
						continue;
					case "09":
						subjectCostReportResponse.setM9(subjectCostReportResponse.getM9().add(s1.getExpenseAmt()));
						continue;
					case "10":
						subjectCostReportResponse.setM10(subjectCostReportResponse.getM10().add(s1.getExpenseAmt()));
						continue;
					case "11":
						subjectCostReportResponse.setM11(subjectCostReportResponse.getM11().add(s1.getExpenseAmt()));
						continue;
					case "12":
						subjectCostReportResponse.setM12(subjectCostReportResponse.getM12().add(s1.getExpenseAmt()));
						continue;
					default:
						continue;
					}
				}
			}
			page.getList().add(subjectCostReportResponse);
		}
		return page;
	}
	
	/**
	 * 查询科目费用报表信息
	 * @param page
	 * @param req
	 * @return
	 */
	public List<SubjectCostReportResponse> querySubjectCostReportInfo(SubjectCostReportRequest req){
		List<SubjectCostReportResponse> resultList = new ArrayList<SubjectCostReportResponse>();
		
		//查询科目信息
		SubjectCostReport subjectCostReport = new SubjectCostReport();
		subjectCostReport.setFirstCode(req.getFirstCode());
		List<SubjectCostReport> subjectInfoList = subjectCostReportService.querySubjectInfo(subjectCostReport);
		
		int year = 0;
		if(StringUtils.isBlank(req.getYear())){
			year = Integer.parseInt(DateUtils.getYear());
		}else{
			year = Integer.parseInt(req.getYear());
		}
		SubjectCostReport sc = new SubjectCostReport();
		sc.setFirstCode(req.getFirstCode());
		sc.setBeginTime(DateUtils.getYearFirst(year)); //开始时间
		sc.setEndTime(DateUtils.getYearFirst(year+1)); //结束时间
		//查询科目明细数据
		List<SubjectCostReport> subjectCostList = new ArrayList<SubjectCostReport>();
		if(StringUtils.isNotBlank(req.getFirstCode())){
			subjectCostList = subjectCostReportService.querySecondSubjectCostReport(sc);
		}else{
			subjectCostList = subjectCostReportService.querySubjectCostReport(sc);
		}
		
		for(SubjectCostReport s:subjectInfoList){
			SubjectCostReportResponse subjectCostReportResponse = new SubjectCostReportResponse();
			subjectCostReportResponse.setIsDetail(s.getIsDetail()); //是否有明细
			subjectCostReportResponse.setSubjectCode(s.getSubjectCode()); //科目编码
			subjectCostReportResponse.setSubjectName(s.getSubjectName()); //科目名称
			
			for(SubjectCostReport s1:subjectCostList){
				if(StringUtils.equals(s1.getSubjectCode(),s.getSubjectCode())){
					subjectCostReportResponse.setRowTotal(subjectCostReportResponse.getRowTotal().add(s1.getExpenseAmt())); //行汇总金额
					if(s1.getTaskFinishTime() == null){
						continue;
					}
					switch (DateUtils.getMonth(s1.getTaskFinishTime())) { //判断月份
					case "01":
						subjectCostReportResponse.setM1(subjectCostReportResponse.getM1().add(s1.getExpenseAmt()));
						continue;
					case "02":
						subjectCostReportResponse.setM2(subjectCostReportResponse.getM2().add(s1.getExpenseAmt()));
						continue;
					case "03":
						subjectCostReportResponse.setM3(subjectCostReportResponse.getM3().add(s1.getExpenseAmt()));
						continue;
					case "04":
						subjectCostReportResponse.setM4(subjectCostReportResponse.getM4().add(s1.getExpenseAmt()));
						continue;
					case "05":
						subjectCostReportResponse.setM5(subjectCostReportResponse.getM5().add(s1.getExpenseAmt()));
						continue;
					case "06":
						subjectCostReportResponse.setM6(subjectCostReportResponse.getM6().add(s1.getExpenseAmt()));
						continue;
					case "07":
						subjectCostReportResponse.setM7(subjectCostReportResponse.getM7().add(s1.getExpenseAmt()));
						continue;
					case "08":
						subjectCostReportResponse.setM8(subjectCostReportResponse.getM8().add(s1.getExpenseAmt()));
						continue;
					case "09":
						subjectCostReportResponse.setM9(subjectCostReportResponse.getM9().add(s1.getExpenseAmt()));
						continue;
					case "10":
						subjectCostReportResponse.setM10(subjectCostReportResponse.getM10().add(s1.getExpenseAmt()));
						continue;
					case "11":
						subjectCostReportResponse.setM11(subjectCostReportResponse.getM11().add(s1.getExpenseAmt()));
						continue;
					case "12":
						subjectCostReportResponse.setM12(subjectCostReportResponse.getM12().add(s1.getExpenseAmt()));
						continue;
					default:
						continue;
					}
				}
			}
			resultList.add(subjectCostReportResponse);
		}
		return resultList;
	}
	
	
	/**
	 * 科目费用报表导出
	 * @param resultList
	 * @param year
	 * @return
	 */
	public String exportSubjectCostReport(SubjectCostReportRequest req,String fileName){
		
		List<SubjectCostReportResponse> resultList = this.querySubjectCostReportInfo(req); //查询所有报表数据
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
		workBook.setSheetName(0, "科目费用报表");
		// 第0行 列头
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell = null;
        HSSFCellStyle style = ExportExcel.getStyle(workBook);
        ExportExcel.myCreateCell(0, "科目名称", row, cell, style);
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
        for(SubjectCostReportResponse q:resultList){
        	row = sheet.createRow(rowNum);
        	ExportExcel.myCreateCell(0, q.getSubjectName(), row, cell, style);
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
	 * 科目费用报表月度汇总统计查询
	 * @param req
	 * @return
	 */
	public ReportMonthSumResponse querySubjectMonthSum(SubjectCostReportRequest req){
		ReportMonthSumResponse resp = new ReportMonthSumResponse();
		int year = 0;
		if(StringUtils.isBlank(req.getYear())){
			year = Integer.parseInt(DateUtils.getYear());
		}else{
			year = Integer.parseInt(req.getYear());
		}
		SubjectCostReport sc = new SubjectCostReport();
		sc.setFirstCode(req.getFirstCode());
		sc.setBeginTime(DateUtils.getYearFirst(year)); //开始时间
		sc.setEndTime(DateUtils.getYearFirst(year+1)); //结束时间
		List<MonthAmountSumReport> monthAmountList = new ArrayList<MonthAmountSumReport>();
		if(StringUtils.isNotBlank(req.getFirstCode())){
			monthAmountList = subjectCostReportService.querySecondSubjectMonthAmountReport(sc);
		}else{
			monthAmountList = subjectCostReportService.queryMonthAmountSum(sc);
		}
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
