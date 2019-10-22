package sijibao.oa.jeesite.modules.intfz.service.employeestatus;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sijibao.oa.common.persistence.Page;
import com.sijibao.oa.common.service.BaseService;
import com.sijibao.oa.common.utils.DateUtils;
import com.sijibao.oa.common.utils.StringUtils;
import com.sijibao.oa.common.utils.UploadUtils;
import com.sijibao.oa.common.utils.excel.ExportExcel;
import com.sijibao.oa.modules.intfz.request.report.EmpStatusMonthReportRequest;
import com.sijibao.oa.modules.intfz.response.report.EmpStatusMonthDetailReportResponse;
import com.sijibao.oa.modules.intfz.response.report.EmpStatusMonthReportResponse;
import com.sijibao.oa.modules.oa.dao.EmployeeStatusMonthDao;
import com.sijibao.oa.modules.oa.entity.EmployeeStatusMonth;
import com.sijibao.oa.modules.sys.entity.PostInfo;
import com.sijibao.oa.modules.sys.entity.User;
import com.sijibao.oa.modules.sys.service.PostService;
import com.sijibao.oa.modules.sys.utils.DictUtils;
import com.sijibao.oa.modules.sys.utils.UserUtils;

/**
 * 员工月报表service服务
 * @author xuby
 * @date 2018-09-26
 */
@Service
@Transactional(readOnly = true)
public class IntfzEmployeeStatusMonthReportService extends BaseService{
	
	@Autowired
	private EmployeeStatusMonthDao employeeStatusMonthDao;
	@Autowired
	private PostService postService;
	/**
	 * 员工月报表查询
	 * @param req
	 * @return
	 */
	public Page<EmpStatusMonthReportResponse> queryEmpStatusMonthReport(EmpStatusMonthReportRequest req){
		Page<EmpStatusMonthReportResponse> response = new Page<EmpStatusMonthReportResponse>();
		List<EmpStatusMonthReportResponse> resultList = new ArrayList<EmpStatusMonthReportResponse>();
		
		EmployeeStatusMonth employeeStatusMonth = new EmployeeStatusMonth();
		employeeStatusMonth.setDatetime(DateUtils.format(DateUtils.parseDate(req.getDateTime()), DateUtils.YYYY_MM));
		Page<EmployeeStatusMonth> empPage = new Page<EmployeeStatusMonth>(req.getPageNo(),req.getPageSize());
		employeeStatusMonth.setPage(empPage);
		empPage.setList(employeeStatusMonthDao.queryEmpMonthReportCount(employeeStatusMonth));
		
		if(empPage.getList() != null && empPage.getList().size() > 0){
			for(EmployeeStatusMonth em:empPage.getList()){
				EmpStatusMonthReportResponse ep = new EmpStatusMonthReportResponse();
				User user = UserUtils.get(em.getUser().getId());
				ep.setOfficeName(user == null?"":user.getOffice().getName());
				PostInfo postInfo = postService.get(user == null?"":user.getPostIds());
				ep.setPostName(postInfo != null?postInfo.getPostName():"");
				ep.setUserName(user == null?"":user.getName());
				ep.setUserId(em.getUser().getId());
				ep.setProjectInDays(em.getProjectInDays());
				ep.setInOutDays(em.getInOutDays());
				//判断人员是否处于离职
				String date1 = DateUtils.format(user.getUpdateDate(), DateUtils.YYYY_MM);
				Date parse1 = DateUtils.parse(date1,DateUtils.YYYY_MM);
				String date2 = DateUtils.format(DateUtils.parseDate(req.getDateTime()), DateUtils.YYYY_MM);
				Date parse2 = DateUtils.parse(date2,DateUtils.YYYY_MM);
				int compareDate = DateUtils.compareDate(parse1,parse2);
				if(!"1".equals(user.getUserStatus()) && compareDate < 0){
					
				}else{
					resultList.add(ep);
				}
			}
		}
		
		response.setCount(empPage.getCount());
		response.setPageNo(empPage.getPageNo());
		response.setPageSize(empPage.getPageSize());
		response.setList(resultList);
		return response;
	}
	
	/**
	 * 员工月报表明细查询
	 * @param req
	 * @return
	 */
	public Page<EmpStatusMonthDetailReportResponse> queryEmpStatusMonthDetailReport(EmpStatusMonthReportRequest req){
		Page<EmpStatusMonthDetailReportResponse> response = new Page<EmpStatusMonthDetailReportResponse>();
		List<EmpStatusMonthDetailReportResponse> resultList = new ArrayList<EmpStatusMonthDetailReportResponse>();
		
		EmployeeStatusMonth employeeStatusMonth = new EmployeeStatusMonth();
		employeeStatusMonth.setDatetime(DateUtils.format(DateUtils.parseDate(req.getDateTime()), DateUtils.YYYY_MM));
		User user = UserUtils.get(req.getUserId());
		employeeStatusMonth.setUser(user);
		Page<EmployeeStatusMonth> empPage = new Page<EmployeeStatusMonth>(req.getPageNo(),req.getPageSize());
		employeeStatusMonth.setPage(empPage);
		empPage.setList(employeeStatusMonthDao.queryEmpMonthDetailReportCount(employeeStatusMonth));
		
		if(empPage.getList() != null && empPage.getList().size() > 0){
			for(EmployeeStatusMonth em:empPage.getList()){
				EmpStatusMonthDetailReportResponse ep = new EmpStatusMonthDetailReportResponse();
				ep.setProjectId(em.getProjectId()); //项目ID
				ep.setProjectName(em.getProjectName()); //项目名称
				ep.setProjectInDays(em.getProjectInDays()); //项目所在天数
				ep.setProjectNodeId(em.getNodeId()); //项目节点ID
				ep.setProjectNodeName(em.getNodeName()); //项目节点名称
				resultList.add(ep);
			}
		}
		
		response.setCount(empPage.getCount());
		response.setPageNo(empPage.getPageNo());
		response.setPageSize(empPage.getPageSize());
		response.setList(resultList);
		return response;
	}
	
	/**
	 * 员工月报表导出
	 * @param req
	 * @param user
	 * @return
	 */
	public String queryEmpStatusMonthReportInfoExport(EmpStatusMonthReportRequest req,String fileName){
		
		EmployeeStatusMonth employeeStatusMonth = new EmployeeStatusMonth();
		
		if(req.getDateTime() != 0l){
			employeeStatusMonth.setDatetime(DateUtils.format(DateUtils.parseDate(req.getDateTime()), DateUtils.YYYY_MM)); //提交时间
		}
		List<EmployeeStatusMonth> resultList = employeeStatusMonthDao.findList(employeeStatusMonth);
		
		String url = "";
		// 创建一个新的Excel
		HSSFWorkbook  workBook = new HSSFWorkbook();
        // 创建sheet页
		HSSFSheet sheet = workBook.createSheet();
        // 设置表名
	    workBook.setSheetName(0, "实施人员状态月报表");
	    
	    HSSFRow row = sheet.createRow(1);
        HSSFCell cell = null;
        HSSFCellStyle style = ExportExcel.getStyle(workBook);
        String dateTime = "";
        if(req.getDateTime() != 0l){
        	dateTime = DateUtils.formatDateFromUnix(req.getDateTime(), DateUtils.YYYY_MM);
        }
        
	    // 第0行 列头
        row = sheet.createRow(1);
        ExportExcel.myCreateCell(0, "部门", row, cell, style);
        ExportExcel.myCreateCell(1, "岗位名称", row, cell, style);
        ExportExcel.myCreateCell(2, "姓名", row, cell, style);
        ExportExcel.myCreateCell(3, "人员状态", row, cell, style);
        ExportExcel.myCreateCell(4, "项目", row, cell, style);
        ExportExcel.myCreateCell(5, "节点", row, cell, style);
        ExportExcel.myCreateCell(6, "天数", row, cell, style);
        
        //设置数据
        int rowNum = 0;
        row = sheet.createRow(rowNum);
        ExportExcel.myCreateCell(0, dateTime+"实施人员月报表", row, cell, style);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
        CellRangeAddress cra = new CellRangeAddress(0, 0, 0, 6);
        sheet.addMergedRegion(cra);
        
        rowNum = 2;
        int width0 = 0;
        int width1 = 0;
        int width2 = 0;
        int width3 = 0;
        int width4 = 0;
        int width5 = 0;
        int width6 = 0;
        
        for(EmployeeStatusMonth e:resultList){
    	    PostInfo postInfo = postService.get(e.getUser().getPostIds());
	       	row = sheet.createRow(rowNum);
	       	ExportExcel.myCreateCell(0, e.getUser().getOffice().getName(), row, cell, style);
	       	if(StringUtils.isNotBlank(e.getUser().getOffice().getName())){
	       		if(e.getUser().getOffice().getName().getBytes().length*2*256 > width0){
	       			width0 = e.getUser().getOffice().getName().getBytes().length*2*256;
	       		}
	       	}
	       	
	       	ExportExcel.myCreateCell(1, postInfo != null?postInfo.getPostName():"", row, cell, style);
	       	if(postInfo != null && StringUtils.isNotBlank(postInfo.getPostName())){
	       		if(postInfo.getPostName().getBytes().length*2*256 > width1){
	       			width1 = postInfo.getPostName().getBytes().length*2*256;
	       		}
	       	}
	       	
	       	ExportExcel.myCreateCell(2, e.getUser().getName(), row, cell, style);
	       	if(StringUtils.isNotBlank(e.getUser().getName())){
	       		if(e.getUser().getName().getBytes().length*2*256 > width2){
	       			width2 = e.getUser().getName().getBytes().length*2*256;
	       		}
	       	}
	       	
	       	ExportExcel.myCreateCell(3, DictUtils.getDictLabel(e.getStatus(), "oa_employee_status", ""), row, cell, style);
	       	String value3 = DictUtils.getDictLabel(e.getStatus(), "oa_employee_status", "");
	       	if(StringUtils.isNotBlank(value3)){
	       		if(value3.getBytes().length*2*256 > width3){
	       			width3 = value3.getBytes().length*2*256;
	       		}
	       	}
	       	
	       	ExportExcel.myCreateCell(4, e.getProjectName(), row, cell, style);
	       	if(StringUtils.isNotBlank(e.getProjectName())){
	       		if(e.getProjectName().getBytes().length*2*256 > width4){
	       			width4 = e.getProjectName().getBytes().length*2*256;
	       		}
	       	}
	       	
	       	ExportExcel.myCreateCell(5, e.getNodeName(), row, cell, style);
	       	if(StringUtils.isNotBlank(e.getNodeName())){
	       		if(e.getNodeName().getBytes().length*2*256 > width5){
	       			width5 = e.getNodeName().getBytes().length*2*256;
	       		}
	       	}
	       	
	       	ExportExcel.myCreateCell(6, e.getDays(), row, cell, style);
	       	width6 = e.getUser().getOffice().getName().getBytes().length*2*256;
	       	rowNum++;
        }
        sheet.setColumnWidth(0, width0);
        sheet.setColumnWidth(1, width1);
        sheet.setColumnWidth(2, width2);
        sheet.setColumnWidth(3, width3);
        sheet.setColumnWidth(4, width4);
        sheet.setColumnWidth(5, width5);
        sheet.setColumnWidth(6, width6);
        
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
