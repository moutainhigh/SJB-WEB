package sijibao.oa.jeesite.modules.intfz.service.daily;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sijibao.oa.common.service.BaseService;
import com.sijibao.oa.common.utils.UploadUtils;
import com.sijibao.oa.common.utils.excel.ExportExcel;
import com.sijibao.oa.modules.intfz.request.common.DailyReportReq;
import com.sijibao.oa.modules.intfz.response.daily.MarketDailyExport;

@Service
@Transactional(readOnly = true)
public class IntfzWebMarketDailyReportService extends BaseService {

    /**
     * 导出日报报表
     */
    public String exportMarketDaily(DailyReportReq req) {

        String url = "";
        // 创建一个新的Excel
        HSSFWorkbook workBook = new HSSFWorkbook();
        // 创建sheet页
        HSSFSheet sheet = workBook.createSheet();
        // 设置表名
        workBook.setSheetName(0, req.getSheetName());
        
        
        if("1".equals(req.getDailyTemplate())){
        	// 第0行 列头
            HSSFRow row = sheet.createRow(0);
            HSSFCell cell = null;
            HSSFCellStyle style = ExportExcel.getStyle(workBook);
            ExportExcel.myCreateCell(0, "提交人", row, cell, style);
            ExportExcel.myCreateCell(1, "部门", row, cell, style);
            ExportExcel.myCreateCell(2, "日志模板", row, cell, style);
            ExportExcel.myCreateCell(3, "提交时间", row, cell, style);
            ExportExcel.myCreateCell(4, "当日电话拜访", row, cell, style);
            ExportExcel.myCreateCell(5, "当日上门拜访", row, cell, style);
            ExportExcel.myCreateCell(6, "当日意向数量", row, cell, style);
            ExportExcel.myCreateCell(7, "当日签约数量", row, cell, style);
            ExportExcel.myCreateCell(8, "明日拜访数量", row, cell, style);
            ExportExcel.myCreateCell(9, "明日工作计划", row, cell, style);
            ExportExcel.myCreateCell(10, "备注", row, cell, style);
            ExportExcel.myCreateCell(11, "客户名称", row, cell, style);
            ExportExcel.myCreateCell(12, "拜访类型", row, cell, style);
            ExportExcel.myCreateCell(13, "维护时间", row, cell, style);
            ExportExcel.myCreateCell(14, "维护内容", row, cell, style);
            ExportExcel.myCreateCell(15, "发给谁", row, cell, style);
            ExportExcel.myCreateCell(16, "抄送谁", row, cell, style);
            //设置数据

            int rowNum = 1;
            for (MarketDailyExport q : req.getList()) {
                row = sheet.createRow(rowNum);
                ExportExcel.myCreateCell(0, q.getCreateByName(), row, cell, style);
                ExportExcel.myCreateCell(1, q.getOfficeName(), row, cell, style);
                ExportExcel.myCreateCell(2, q.getDailyTemplateName(), row, cell, style);
                ExportExcel.myCreateCell(3, q.getCreateTime(), row, cell, style);
                ExportExcel.myCreateCell(4, q.getCustPhotoCount(), row, cell, style);
                ExportExcel.myCreateCell(5, q.getCustVisitCount(), row, cell, style);
                ExportExcel.myCreateCell(6, q.getCustIntentionCount(), row, cell, style);
                ExportExcel.myCreateCell(7, q.getCustSignCount(), row, cell, style);
                ExportExcel.myCreateCell(8, q.getAfterVisitCount(), row, cell, style);
                ExportExcel.myCreateCell(9, q.getAfterDailyPlan(), row, cell, style);
                ExportExcel.myCreateCell(10, q.getRemarks(), row, cell, style);
                ExportExcel.myCreateCell(11, q.getCustName(), row, cell, style);
                ExportExcel.myCreateCell(12, q.getVisitTypeName(), row, cell, style);
                ExportExcel.myCreateCell(13, q.getCustMaintenanceDate(), row, cell, style);
                ExportExcel.myCreateCell(14, q.getCustMaintenanceContent(), row, cell, style);
                ExportExcel.myCreateCell(15, q.getSendTo(), row, cell, style);
                ExportExcel.myCreateCell(16, q.getCopyTo(), row, cell, style);
                rowNum++;
            }

            try {
                //保存到服务器
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                workBook.write(os);
                url = UploadUtils.uploadDoc(os, req.getFileName());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return url;
        }
		return null;
        
    }

    
}
