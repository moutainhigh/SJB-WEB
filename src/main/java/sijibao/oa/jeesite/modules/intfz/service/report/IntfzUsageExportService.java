package sijibao.oa.jeesite.modules.intfz.service.report;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.hssf.usermodel.*;

import com.sijibao.oa.common.service.BaseService;
import com.sijibao.oa.common.utils.UploadUtils;
import com.sijibao.oa.common.utils.excel.ExportExcel;
import com.sijibao.oa.sys.api.request.usage.ExportUsageWeekOrDayParam;
import com.sijibao.oa.sys.api.response.usage.ExportUsageDetailResult;
import com.sijibao.oa.sys.api.response.usage.ExportUsageWeekResult;

public class IntfzUsageExportService extends BaseService {

    /**
     * 导出功能使用情况周报表
     */
    public static String exportUsageWeek(ExportUsageWeekOrDayParam param, List<ExportUsageWeekResult> list, String fileName) {

        String url = "";
        // 创建一个新的Excel
        HSSFWorkbook workBook = new HSSFWorkbook();
        // 创建sheet页
        HSSFSheet sheet = workBook.createSheet();
        // 设置表名
        workBook.setSheetName(0, "功能使用情况");
        // 第0行 列头
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell = null;
        HSSFCellStyle style = ExportExcel.getStyle(workBook);
        ExportExcel.myCreateCell(0, "功能", row, cell, style);
        ExportExcel.myCreateCell(1, "产品端", row, cell, style);
        ExportExcel.myCreateCell(2, param.getMondayStr().replace("-", "/"), row, cell, style);
        ExportExcel.myCreateCell(3, param.getTuesdayStr().replace("-", "/"), row, cell, style);
        ExportExcel.myCreateCell(4, param.getWednesdayStr().replace("-", "/"), row, cell, style);
        ExportExcel.myCreateCell(5, param.getThursdayStr().replace("-", "/"), row, cell, style);
        ExportExcel.myCreateCell(6, param.getFridayStr().replace("-", "/"), row, cell, style);
        ExportExcel.myCreateCell(7, param.getSaturdayStr().replace("-", "/"), row, cell, style);
        ExportExcel.myCreateCell(8, param.getSundayStr().replace("-", "/"), row, cell, style);

        int rowNum = 1;
        for (ExportUsageWeekResult q : list) {
            row = sheet.createRow(rowNum);
            ExportExcel.myCreateCell(0, q.getFunctionName(), row, cell, style);
            ExportExcel.myCreateCell(1, q.getTerminalName(), row, cell, style);
            //如果某功能某天没有被使用，那么查询出的使用次数字符串就是null，需转换成0
            ExportExcel.myCreateCell(2, q.getMondayNum() == null ? "0" : q.getMondayNum(), row, cell, style);
            ExportExcel.myCreateCell(3, q.getTuesdayNum() == null ? "0" : q.getTuesdayNum(), row, cell, style);
            ExportExcel.myCreateCell(4, q.getWednesdayNum() == null ? "0" : q.getWednesdayNum(), row, cell, style);
            ExportExcel.myCreateCell(5, q.getThursdayNum() == null ? "0" : q.getThursdayNum(), row, cell, style);
            ExportExcel.myCreateCell(6, q.getFridayNum() == null ? "0" : q.getFridayNum(), row, cell, style);
            ExportExcel.myCreateCell(7, q.getSaturdayNum() == null ? "0" : q.getSaturdayNum(), row, cell, style);
            ExportExcel.myCreateCell(8, q.getSundayNum() == null ? "0" : q.getSundayNum(), row, cell, style);
            rowNum++;
        }

        try {
            //保存到服务器
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            workBook.write(os);
            url = UploadUtils.uploadDoc(os, fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return url;
    }

    /**
     * 导出功能使用情况具体报表
     */
    public static String exportUsageDetail(List<ExportUsageDetailResult> list, String fileName) {

        String url = "";
        // 创建一个新的Excel
        HSSFWorkbook workBook = new HSSFWorkbook();
        // 创建sheet页
        HSSFSheet sheet = workBook.createSheet();
        // 设置表名
        workBook.setSheetName(0, "功能使用具体情况");
        // 第0行 列头
        HSSFRow row = sheet.createRow(0);

        HSSFCell cell = null;
        HSSFCellStyle style = ExportExcel.getStyle(workBook);
        ExportExcel.myCreateCell(0, "功能", row, cell, style);
        ExportExcel.myCreateCell(1, "产品端", row, cell, style);
        ExportExcel.myCreateCell(2, "日期", row, cell, style);
        ExportExcel.myCreateCell(3, "使用次数", row, cell, style);
        ExportExcel.myCreateCell(4, "使用人", row, cell, style);

        int rowNum = 1;
        for (ExportUsageDetailResult q : list) {
            row = sheet.createRow(rowNum);
            ExportExcel.myCreateCell(0, q.getFunctionName(), row, cell, style);
            ExportExcel.myCreateCell(1, q.getTerminalName(), row, cell, style);
            ExportExcel.myCreateCell(2, q.getDate(), row, cell, style);
            ExportExcel.myCreateCell(3, q.getNum(), row, cell, style);
            ExportExcel.myCreateCell(4, q.getUserName(), row, cell, style);
            rowNum++;
        }

        try {
            //保存到服务器
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            workBook.write(os);
            url = UploadUtils.uploadDoc(os, fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return url;
    }

}
