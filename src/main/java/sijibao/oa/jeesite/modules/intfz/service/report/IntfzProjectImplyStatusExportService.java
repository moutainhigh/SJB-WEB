package sijibao.oa.jeesite.modules.intfz.service.report;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.poi.hssf.usermodel.*;

import com.sijibao.oa.common.service.BaseService;
import com.sijibao.oa.common.utils.UploadUtils;
import com.sijibao.oa.common.utils.excel.ExportExcel;
import com.sijibao.oa.crm.api.response.project.PagedQueryProjectImplyStatusResult;

//@Service
//@Transactional(readOnly = true)
public class IntfzProjectImplyStatusExportService extends BaseService {

    /**
     * 导出进度情况
     */
    public String exportProjectImplyStatus(List<PagedQueryProjectImplyStatusResult> list, String fileName) {

        String url = "";
        // 创建一个新的Excel
        HSSFWorkbook workBook = new HSSFWorkbook();
        // 创建sheet页
        HSSFSheet sheet = workBook.createSheet();
        // 设置表名
        workBook.setSheetName(0, "项目实施情况");
        // 第0行 列头
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell = null;
        HSSFCellStyle style = ExportExcel.getStyle(workBook);
        ExportExcel.myCreateCell(0, "日期", row, cell, style);
        ExportExcel.myCreateCell(1, "节点", row, cell, style);
        ExportExcel.myCreateCell(2, "节点具体地址", row, cell, style);
        ExportExcel.myCreateCell(3, "汇报人", row, cell, style);
        ExportExcel.myCreateCell(4, "具体异常说明", row, cell, style);

        //设置数据
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        int rowNum = 1;
        for (PagedQueryProjectImplyStatusResult q : list) {
            row = sheet.createRow(rowNum);
            ExportExcel.myCreateCell(0, format.format(q.getDate()), row, cell, style);
            ExportExcel.myCreateCell(1, q.getNodeName(), row, cell, style);
            ExportExcel.myCreateCell(2, q.getNodeAddress(), row, cell, style);
            ExportExcel.myCreateCell(3, q.getReporter(), row, cell, style);
            ExportExcel.myCreateCell(4, q.getAnomalyDescription(), row, cell, style);
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
