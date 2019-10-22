package sijibao.oa.jeesite.modules.intfz.service.report;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.poi.hssf.usermodel.*;

import com.sijibao.oa.activiti.api.response.need.ExportCommentResult;
import com.sijibao.oa.activiti.api.response.need.ExportProgressResult;
import com.sijibao.oa.common.service.BaseService;
import com.sijibao.oa.common.utils.UploadUtils;
import com.sijibao.oa.common.utils.excel.ExportExcel;

//@Service
//@Transactional(readOnly = true)
public class IntfzNeedExportService extends BaseService {

    /**
     * 导出进度情况
     */
    public String exportProgress(List<ExportProgressResult> list, String fileName) {

        String url = "";
        // 创建一个新的Excel
        HSSFWorkbook workBook = new HSSFWorkbook();
        // 创建sheet页
        HSSFSheet sheet = workBook.createSheet();
        // 设置表名
        workBook.setSheetName(0, "协作进度情况");
        // 第0行 列头
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell = null;
        HSSFCellStyle style = ExportExcel.getStyle(workBook);
        ExportExcel.myCreateCell(0, "提交人", row, cell, style);
        ExportExcel.myCreateCell(1, "部门", row, cell, style);
        ExportExcel.myCreateCell(2, "类型", row, cell, style);
        ExportExcel.myCreateCell(3, "标题", row, cell, style);
        ExportExcel.myCreateCell(4, "描述", row, cell, style);
        ExportExcel.myCreateCell(5, "标签", row, cell, style);
        ExportExcel.myCreateCell(6, "进度状态", row, cell, style);
        ExportExcel.myCreateCell(7, "进度负责人", row, cell, style);
        ExportExcel.myCreateCell(8, "状态变更时间", row, cell, style);
        ExportExcel.myCreateCell(9, "处理说明", row, cell, style);

        //设置数据
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        int rowNum = 1;
        for (ExportProgressResult q : list) {
            row = sheet.createRow(rowNum);
            ExportExcel.myCreateCell(0, q.getInitiator(), row, cell, style);
            ExportExcel.myCreateCell(1, q.getDept(), row, cell, style);
            ExportExcel.myCreateCell(2, q.getType(), row, cell, style);
            ExportExcel.myCreateCell(3, q.getTitle(), row, cell, style);
            ExportExcel.myCreateCell(4, q.getDescription(), row, cell, style);
            ExportExcel.myCreateCell(5, q.getLabel(), row, cell, style);
            ExportExcel.myCreateCell(6, q.getProgressStatus(), row, cell, style);
            ExportExcel.myCreateCell(7, q.getPrincipal(), row, cell, style);
            ExportExcel.myCreateCell(8, format.format(q.getChangeTime()), row, cell, style);
            ExportExcel.myCreateCell(9, q.getHandleExplain(), row, cell, style);
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
     * 导出评论
     */
    public String exportComment(List<ExportCommentResult> list, String fileName) {

        String url = "";
        // 创建一个新的Excel
        HSSFWorkbook workBook = new HSSFWorkbook();
        // 创建sheet页
        HSSFSheet sheet = workBook.createSheet();
        // 设置表名
        workBook.setSheetName(0, "协作评论");
        // 第0行 列头
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell = null;
        HSSFCellStyle style = ExportExcel.getStyle(workBook);
        ExportExcel.myCreateCell(0, "提交人", row, cell, style);
        ExportExcel.myCreateCell(1, "部门", row, cell, style);
        ExportExcel.myCreateCell(2, "类型", row, cell, style);
        ExportExcel.myCreateCell(3, "标题", row, cell, style);
        ExportExcel.myCreateCell(4, "描述", row, cell, style);
        ExportExcel.myCreateCell(5, "标签", row, cell, style);
        ExportExcel.myCreateCell(6, "评论人", row, cell, style);
        ExportExcel.myCreateCell(7, "评论时间", row, cell, style);
        ExportExcel.myCreateCell(8, "评论内容", row, cell, style);

        //设置数据
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        int rowNum = 1;
        for (ExportCommentResult q : list) {
            row = sheet.createRow(rowNum);
            ExportExcel.myCreateCell(0, q.getInitiator(), row, cell, style);
            ExportExcel.myCreateCell(1, q.getDept(), row, cell, style);
            ExportExcel.myCreateCell(2, q.getType(), row, cell, style);
            ExportExcel.myCreateCell(3, q.getTitle(), row, cell, style);
            ExportExcel.myCreateCell(4, q.getDescription(), row, cell, style);
            ExportExcel.myCreateCell(5, q.getLabel(), row, cell, style);
            ExportExcel.myCreateCell(6, q.getCommentator(), row, cell, style);
            ExportExcel.myCreateCell(7, format.format(q.getTime()), row, cell, style);
            ExportExcel.myCreateCell(8, q.getContent(), row, cell, style);
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
