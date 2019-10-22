package sijibao.oa.jeesite.modules.intfz.service.daily;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.hssf.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sijibao.oa.common.service.BaseService;
import com.sijibao.oa.common.utils.UploadUtils;
import com.sijibao.oa.common.utils.excel.ExportExcel;
import com.sijibao.oa.crm.api.response.daily.ImplyDailyExportResponse;

@Service
@Transactional(readOnly = true)
public class IntfzWebImplyDailyReportService extends BaseService {

    /**
     * 导出日报报表
     */
    public String exportImplyDaily(List<ImplyDailyExportResponse> list, String fileName, String sheetName) {

        String url = "";
        // 创建一个新的Excel
        HSSFWorkbook workBook = new HSSFWorkbook();
        // 创建sheet页
        HSSFSheet sheet = workBook.createSheet();
        // 设置表名
        workBook.setSheetName(0, sheetName);

        // 第0行 列头
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell = null;
        HSSFCellStyle style = ExportExcel.getStyle(workBook);
        ExportExcel.myCreateCell(0, "提交人", row, cell, style);
        ExportExcel.myCreateCell(1, "部门", row, cell, style);
        ExportExcel.myCreateCell(2, "日志模板", row, cell, style);
        ExportExcel.myCreateCell(3, "提交时间", row, cell, style);
        ExportExcel.myCreateCell(4, "今日工作", row, cell, style);
        ExportExcel.myCreateCell(5, "运力池建设", row, cell, style);
        ExportExcel.myCreateCell(6, "回访项目情况", row, cell, style);
        ExportExcel.myCreateCell(7, "需要协助问题", row, cell, style);
        ExportExcel.myCreateCell(8, "今日感想", row, cell, style);
        ExportExcel.myCreateCell(9, "备注", row, cell, style);
        ExportExcel.myCreateCell(10, "项目名称", row, cell, style);
        ExportExcel.myCreateCell(11, "节点", row, cell, style);
        ExportExcel.myCreateCell(12, "节点具体地址", row, cell, style);
        ExportExcel.myCreateCell(13, "节点人数", row, cell, style);
        ExportExcel.myCreateCell(14, "节点具体人员", row, cell, style);
        ExportExcel.myCreateCell(15, "异常情况", row, cell, style);
        ExportExcel.myCreateCell(16, "具体异常说明", row, cell, style);
        ExportExcel.myCreateCell(17, "发给谁", row, cell, style);
        ExportExcel.myCreateCell(18, "抄送谁", row, cell, style);
        //设置数据
        int rowNum = 1;
        for (ImplyDailyExportResponse q : list) {
            row = sheet.createRow(rowNum);
            ExportExcel.myCreateCell(0, q.getCreateByName(), row, cell, style);
            ExportExcel.myCreateCell(1, q.getOfficeName(), row, cell, style);
            ExportExcel.myCreateCell(2, q.getDailyTemplateName(), row, cell, style);
            ExportExcel.myCreateCell(3, q.getCreateTime(), row, cell, style);
            ExportExcel.myCreateCell(4, q.getTodayWork(), row, cell, style);
            ExportExcel.myCreateCell(5, q.getTransportPoolBuild(), row, cell, style);
            ExportExcel.myCreateCell(6, q.getRevisitProjectStatus(), row, cell, style);
            ExportExcel.myCreateCell(7, q.getNeedAssistProblem(), row, cell, style);
            ExportExcel.myCreateCell(8, q.getTodayThought(), row, cell, style);
            ExportExcel.myCreateCell(9, q.getRemarks(), row, cell, style);
            ExportExcel.myCreateCell(10, q.getProjectName(), row, cell, style);
            ExportExcel.myCreateCell(11, q.getNodeName(), row, cell, style);
            ExportExcel.myCreateCell(12, q.getNodeAddress(), row, cell, style);
            ExportExcel.myCreateCell(13, q.getNodeEmpNum(), row, cell, style);
            ExportExcel.myCreateCell(14, q.getNodeEmpNames(), row, cell, style);
            ExportExcel.myCreateCell(15, q.getHasAbnormalStatus(), row, cell, style);
            ExportExcel.myCreateCell(16, q.getAnomalyDescription(), row, cell, style);
            ExportExcel.myCreateCell(17, q.getSendTo(), row, cell, style);
            ExportExcel.myCreateCell(18, q.getCopyTo(), row, cell, style);
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
