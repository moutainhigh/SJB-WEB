package sijibao.oa.jeesite.modules.intfz.service.report;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.sijibao.oa.common.service.BaseService;
import com.sijibao.oa.common.utils.UploadUtils;
import com.sijibao.oa.common.utils.excel.ExportExcel;
import com.sijibao.oa.modules.intfz.request.report.MainApprovalSituationReq;
import com.sijibao.oa.modules.intfz.request.report.MainFlowSubmitSituationReq;
import com.sijibao.oa.modules.intfz.response.report.MainApprovalSituationResponse;
import com.sijibao.oa.modules.intfz.response.report.MainFlowSubmitSituationResponse;
import com.sijibao.oa.office.api.IntfzReportService;
import com.sijibao.oa.office.api.request.report.ApprovalSituationReq;
import com.sijibao.oa.office.api.request.report.FlowSubmitSituationReq;
import com.sijibao.oa.office.api.response.report.ApprovalSituationResponse;
import com.sijibao.oa.office.api.response.report.FlowSubmitSituationResponse;

/**
 * 单据报表查询服务
 * @author huangkai
 */
@Service
@Transactional(readOnly = true)
public class IntfzWebReportService extends BaseService {
	@Autowired
	private IntfzReportService intfzReportService ;


	/**
	 * 单据提交情况报表导出
	 * @param fileName
	 * @return
	 */
	public String flowSubmitSituationExport(MainFlowSubmitSituationReq req, String fileName){
		FlowSubmitSituationReq stion = change(req, FlowSubmitSituationReq.class);
		List<FlowSubmitSituationResponse> list = intfzReportService.flowSubmitSituation(stion);
		List<MainFlowSubmitSituationResponse> resp = Lists.newArrayList();
		for(FlowSubmitSituationResponse res :list){
			resp.add(change(res,MainFlowSubmitSituationResponse.class));
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		String now = sdf.format(new Date());
		DecimalFormat df = new DecimalFormat("0.00");
		String url = "";
		// 创建一个新的Excel
		HSSFWorkbook  workBook = new HSSFWorkbook();
		// 创建sheet页
		HSSFSheet sheet = workBook.createSheet();
		// 设置表名
		workBook.setSheetName(0, "单据提交情况"+now);
		// 第0行 列头
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell = null;
		HSSFCellStyle style = ExportExcel.getStyle(workBook);
		ExportExcel.myCreateCell(0, "功能编号", row, cell, style);
		ExportExcel.myCreateCell(1, "编号", row, cell, style);
		ExportExcel.myCreateCell(2, "功能", row, cell, style);
		ExportExcel.myCreateCell(3, "产品端", row, cell, style);
		ExportExcel.myCreateCell(4, "上线时间", row, cell, style);
		ExportExcel.myCreateCell(5, "1月", row, cell, style);
		ExportExcel.myCreateCell(6, "2月", row, cell, style);
		ExportExcel.myCreateCell(7, "3月", row, cell, style);
		ExportExcel.myCreateCell(8, "4月", row, cell, style);
		ExportExcel.myCreateCell(9, "5月", row, cell, style);
		ExportExcel.myCreateCell(10, "6月", row, cell, style);
		ExportExcel.myCreateCell(11, "7月", row, cell, style);
		ExportExcel.myCreateCell(12, "8月", row, cell, style);
		ExportExcel.myCreateCell(13, "9月", row, cell, style);
		ExportExcel.myCreateCell(14, "10月", row, cell, style);
		ExportExcel.myCreateCell(15, "11月", row, cell, style);
		ExportExcel.myCreateCell(16, "12月", row, cell, style);
		//设置数据
		int rowNum = 1;
		for(MainFlowSubmitSituationResponse q:resp){
			row = sheet.createRow(rowNum);
			ExportExcel.myCreateCell(0, String.valueOf(q.getSubjectCode()), row, cell, style);
			ExportExcel.myCreateCell(1, String.valueOf(q.getNumber()), row, cell, style);
			ExportExcel.myCreateCell(2, q.getFunction(), row, cell, style);
			ExportExcel.myCreateCell(3, q.getProducSide(), row, cell, style);
			ExportExcel.myCreateCell(4, q.getOnlineTime(), row, cell, style);
			ExportExcel.myCreateCell(5, q.gettJan(), row, cell, style);
			ExportExcel.myCreateCell(6, q.gettFeb(), row, cell, style);
			ExportExcel.myCreateCell(7, q.gettMar(), row, cell, style);
			ExportExcel.myCreateCell(8, q.gettApr(), row, cell, style);
			ExportExcel.myCreateCell(9, q.gettMay(), row, cell, style);
			ExportExcel.myCreateCell(10, q.gettJun(), row, cell, style);
			ExportExcel.myCreateCell(11, q.gettJul(), row, cell, style);
			ExportExcel.myCreateCell(12, q.gettAug(), row, cell, style);
			ExportExcel.myCreateCell(13, q.gettSept(), row, cell, style);
			ExportExcel.myCreateCell(14, q.gettOct(), row, cell, style);
			ExportExcel.myCreateCell(15, q.gettNov(), row, cell, style);
			ExportExcel.myCreateCell(16, q.gettDec(), row, cell, style);
			rowNum++;
		}

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
     * 单据审批情况报表导出
     * @param req
     * @param fileName
     * @return
     */
	public String approvalSituationExport(MainApprovalSituationReq req, String fileName){
		ApprovalSituationReq stion = change(req, ApprovalSituationReq.class);
		List<ApprovalSituationResponse> list = intfzReportService.approvalSituation(stion);
		List<MainApprovalSituationResponse> resp = Lists.newArrayList();
		for(ApprovalSituationResponse res :list){
			resp.add(change(res,MainApprovalSituationResponse.class));
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		String now = sdf.format(new Date());
		DecimalFormat df = new DecimalFormat("0.00");
		String url = "";
		// 创建一个新的Excel
		HSSFWorkbook  workBook = new HSSFWorkbook();
		// 创建sheet页
		HSSFSheet sheet = workBook.createSheet();
		// 设置表名
		workBook.setSheetName(0, "单据审批情况"+now);

		// 第0行 列头
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell = null;
		HSSFCellStyle style = ExportExcel.getStyle(workBook);
		HSSFDataFormat format = workBook.createDataFormat();
		style.setDataFormat(format.getFormat("@"));
		ExportExcel.myCreateCell(0, "功能编号", row, cell, style);
		ExportExcel.myCreateCell(1, "编号", row, cell, style);
		ExportExcel.myCreateCell(2, "功能", row, cell, style);
		ExportExcel.myCreateCell(3, "产品端", row, cell, style);
		ExportExcel.myCreateCell(4, "上线时间", row, cell, style);
		ExportExcel.myCreateCell(5, "使用次数", row, cell, style);
		ExportExcel.myCreateCell(6, "未完结（本月未完结）", row, cell, style);
		ExportExcel.myCreateCell(7, "已完结（本月提交，本月完结）", row, cell, style);
		ExportExcel.myCreateCell(8, "本月完结", row, cell, style);
		ExportExcel.myCreateCell(9, "本月完结审批时间（天）", row, cell, style);
		ExportExcel.myCreateCell(10, "本月完结总审批时间（秒）", row, cell, style);
		ExportExcel.myCreateCell(11, "上月使用次数", row, cell, style);
		ExportExcel.myCreateCell(12, "上月完结审批时间（天）", row, cell, style);
		ExportExcel.myCreateCell(13, "上月完结总审批时间（秒）", row, cell, style);
		ExportExcel.myCreateCell(14, "年平均使用次数", row, cell, style);


		//设置数据
		int rowNum = 1;
		for(MainApprovalSituationResponse q:resp){
			row = sheet.createRow(rowNum);
			ExportExcel.myCreateCell(0, String.valueOf(q.getSubjectCode()), row, cell, style);
			ExportExcel.myCreateCell(1, String.valueOf(q.getNumber()), row, cell, style);
			ExportExcel.myCreateCell(2, q.getFunction(), row, cell, style);
			ExportExcel.myCreateCell(3, q.getProducSide(), row, cell, style);
			ExportExcel.myCreateCell(4, q.getOnlineTime(), row, cell, style);
			ExportExcel.myCreateCell(5, q.getUseCount(), row, cell, style);
			ExportExcel.myCreateCell(6, q.getUnfinishedThis(), row, cell, style);
			ExportExcel.myCreateCell(7, q.getFinishedThis(), row, cell, style);
			ExportExcel.myCreateCell(8, q.getFinished(), row, cell, style);
			ExportExcel.myCreateCell(9, String.valueOf(q.getSituationSumDate()), row, cell, style);
			ExportExcel.myCreateCell(10, String.valueOf(q.getSituationSumTime()), row, cell, style);
			ExportExcel.myCreateCell(11, String.valueOf(q.getUseCountLast()), row, cell, style);
			ExportExcel.myCreateCell(12, String.valueOf(q.getSituationSumDateLast()), row, cell, style);
			ExportExcel.myCreateCell(13, String.valueOf(q.getSituationSumTimeLast()), row, cell, style);
			ExportExcel.myCreateCell(14, String.valueOf(q.getYearAverageCount()), row, cell, style);
			rowNum++;
		}

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
}
