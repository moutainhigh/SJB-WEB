package sijibao.oa.jeesite.modules.intfz.service.report;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
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
import com.sijibao.oa.modules.intfz.request.report.EmpStatusDayReportRequest;
import com.sijibao.oa.modules.intfz.response.report.EmpStatusDayReportResponse;
import com.sijibao.oa.modules.oa.entity.EmployeeStatusrecord;
import com.sijibao.oa.modules.oa.service.EmployeeStatusrecordService;
import com.sijibao.oa.modules.sys.entity.PostInfo;
import com.sijibao.oa.modules.sys.entity.User;
import com.sijibao.oa.modules.sys.service.PostService;
import com.sijibao.oa.modules.sys.utils.DictUtils;

/**
 * 员工状态报表查询服务
 * @author xuby
 */
@Service
@Transactional(readOnly = true)
public class IntfzEmployeeStatusReportService extends BaseService{
	
	@Autowired
	private EmployeeStatusrecordService employeeStatusrecordService;
	@Autowired
	private PostService postService;
	
	/**
	 * 员工状态日报表查询服务
	 * @return
	 */
	public Page<EmpStatusDayReportResponse> queryEmpStatusDayReportInfo(EmpStatusDayReportRequest req,User user){
		
		Page<EmpStatusDayReportResponse> resultPage = new Page<EmpStatusDayReportResponse>();
		List<EmpStatusDayReportResponse> resultList = new ArrayList<EmpStatusDayReportResponse>();
		
		EmployeeStatusrecord employeeStatusrecord = new EmployeeStatusrecord();
		employeeStatusrecord.setBaseId(req.getBaseId());//基地id
		employeeStatusrecord.setUserIdOrDeptId(req.getUserIdOrDeptId());//人员id或部门id
		employeeStatusrecord.setTextQuery(req.getTextQuery()); //项目或节点模糊查询
		employeeStatusrecord.setStatus(req.getEmpStatus()); //人员状态
		if(req.getDateTime() != 0l){
			employeeStatusrecord.setCreateTime(DateUtils.parseDateFormUnix(req.getDateTime(), DateUtils.YYYY_MM_DD)); //提交时间
		}
		Page<EmployeeStatusrecord> queryPage = new Page<EmployeeStatusrecord>(req.getPageNo(), req.getPageSize());
		employeeStatusrecord.setPage(queryPage);
		queryPage.setList(employeeStatusrecordService.queryEmpStatusDayReportInfo(employeeStatusrecord)); //查询报表数据
		
		if(queryPage.getList() != null && queryPage.getList().size() > 0){
			for(EmployeeStatusrecord e:queryPage.getList()){
				EmpStatusDayReportResponse response = new EmpStatusDayReportResponse();
				User u = e.getUser();
				if(u != null && u.getOffice() != null){
					response.setOfficeName(u.getOffice().getName()); //部门名称
				}else{
					response.setOfficeName(""); //部门名称
				}
				PostInfo postInfo = postService.get(e.getUser().getPostIds());
				response.setPostName(postInfo != null?postInfo.getPostName():""); //岗位名称
				response.setUserId(u == null?"":u.getId()); //用户ID
				response.setUserName(u == null?"":u.getName()); //用户名称
				response.setEmpStatus(e.getStatus()); //用户状态
				response.setEmpStatusTxt(DictUtils.getDictLabel(e.getStatus(), "oa_employee_status", "")); //用户状态文案
				response.setProjectName(e.getProjectName()); //项目名称
				response.setProjectId(e.getProjectId()); //项目ID
				response.setNodeId(e.getProjectNodeId()); //节点ID
				response.setNodeName(e.getProjectNodeName()); //节点名称
				response.setBaseValue(e.getBaseId()); //基地
				response.setBaseValueTxt(DictUtils.getDictLabel(e.getBaseId(),"oa_employee_base", "")); //基地名称
				response.setUserAction(e.getUserAction()); //人员动作
				response.setUserActionTxt(DictUtils.getDictLabel(e.getUserAction(),"oa_employee_action", "")); //人员动作文案
				response.setRemarks(e.getRemarks()); //备注
				response.setDatetime(e.getCreateTime().getTime()); //提交日期
				resultList.add(response);
			}
		}
		
		resultPage.setPageNo(queryPage.getPageNo());
		resultPage.setPageSize(queryPage.getPageSize());
		resultPage.setCount(queryPage.getCount());
		resultPage.setList(resultList);
		return resultPage;
	}
	
	/**
	 * 员工日报表导出
	 * @param req
	 * @param user
	 * @return
	 */
	public String queryEmpStatusDayReportInfoExport(EmpStatusDayReportRequest req,String fileName){
		
		EmployeeStatusrecord employeeStatusrecord = new EmployeeStatusrecord();
		employeeStatusrecord.setBaseId(req.getBaseId());//基地id
		employeeStatusrecord.setUserIdOrDeptId(req.getUserIdOrDeptId());//人员id或部门id
		employeeStatusrecord.setTextQuery(req.getTextQuery()); //项目或节点模糊查询
		employeeStatusrecord.setStatus(req.getEmpStatus()); //人员状态
		if(req.getDateTime() != 0l){
			employeeStatusrecord.setCreateTime(DateUtils.parseDateFormUnix(req.getDateTime(), DateUtils.YYYY_MM_DD)); //提交时间
		}
		List<EmployeeStatusrecord> resultList = employeeStatusrecordService.queryEmpStatusDayReportInfo(employeeStatusrecord);
		
		String url = "";
		// 创建一个新的Excel
		HSSFWorkbook  workBook = new HSSFWorkbook();
        // 创建sheet页
		HSSFSheet sheet = workBook.createSheet();
        // 设置表名
	    workBook.setSheetName(0, "员工状态日报表");
	   
	    HSSFRow row = sheet.createRow(1);
        HSSFCell cell = null;
        HSSFCellStyle style = ExportExcel.getStyle(workBook);
        String dateTime = "";
        if(req.getDateTime() != 0l){
        	dateTime = DateUtils.formatDateFromUnix(req.getDateTime(), DateUtils.YYYY_MM_DD);
        }
        
	    // 第0行 列头
        row = sheet.createRow(1);
        ExportExcel.myCreateCell(0, "部门", row, cell, style);
        ExportExcel.myCreateCell(1, "岗位名称", row, cell, style);
        ExportExcel.myCreateCell(2, "姓名", row, cell, style);
        ExportExcel.myCreateCell(3, "提交时间", row, cell, style);
        ExportExcel.myCreateCell(4, "人员状态", row, cell, style);
        ExportExcel.myCreateCell(5, "人员动作", row, cell, style);
        ExportExcel.myCreateCell(6, "项目", row, cell, style);
        ExportExcel.myCreateCell(7, "节点", row, cell, style);
        ExportExcel.myCreateCell(8, "待命基地", row, cell, style);
        ExportExcel.myCreateCell(9, "备注", row, cell, style);
        
        //设置数据
        int rowNum = 0;
        row = sheet.createRow(rowNum);
        ExportExcel.myCreateCell(0, dateTime+"实施人员日报表", row, cell, style);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
        CellRangeAddress cra = new CellRangeAddress(0, 0, 0, 9);
        sheet.addMergedRegion(cra);
        
        rowNum = 2;
        int width0 = 0;
        int width1 = 0;
        int width2 = 0;
        int width3 = 0;
        int width4 = 0;
        int width5 = 0;
        int width6 = 0;
        int width7 = 0;
        int width8 = 0;
        int width9 = 0;
        for(EmployeeStatusrecord e:resultList){
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
	       	
	       	ExportExcel.myCreateCell(3, DateUtils.format(e.getCreateTime(),DateUtils.YYYY_MM_DD_HH_MM_SS), row, cell, style);
       		if(DateUtils.format(e.getCreateTime(),DateUtils.YYYY_MM_DD_HH_MM_SS).getBytes().length*2*256 > width3){
       			width3 = DateUtils.format(e.getCreateTime(),DateUtils.YYYY_MM_DD_HH_MM_SS).getBytes().length*2*256;
       		}
	       	
	       	ExportExcel.myCreateCell(4, DictUtils.getDictLabel(e.getStatus(), "oa_employee_status", ""), row, cell, style);
	       	String value4 = DictUtils.getDictLabel(e.getStatus(), "oa_employee_status", "");
	       	if(StringUtils.isNotBlank(value4)){
	       		if(value4.getBytes().length*2*256 > width4){
	       			width4 = value4.getBytes().length*2*256;
	       		}
	       	}
	       	
	       	ExportExcel.myCreateCell(5, DictUtils.getDictLabel(e.getUserAction(), "oa_employee_action", ""), row, cell, style);
	       	String value5 = DictUtils.getDictLabel(e.getUserAction(), "oa_employee_action", "");
	       	if(StringUtils.isNotBlank(value5)){
	       		if(value5.getBytes().length*2*256 > width5){
	       			width5 = value5.getBytes().length*2*256;
	       		}
	       	}
	       	
	       	
	       	ExportExcel.myCreateCell(6, e.getProjectName(), row, cell, style);
	       	if(StringUtils.isNotBlank(e.getProjectName())){
	       		if(e.getProjectName().getBytes().length*2*256 > width6){
	       			width6 = e.getProjectName().getBytes().length*2*256;
	       		}
	       	}
	       	
	       	ExportExcel.myCreateCell(7, e.getProjectNodeName(), row, cell, style);
	       	if(StringUtils.isNotBlank(e.getProjectNodeName())){
	       		if(e.getProjectNodeName().getBytes().length*2*256 > width7){
	       			width7 = e.getProjectNodeName().getBytes().length*2*256;
	       		}
	       	}
	       	
	       	ExportExcel.myCreateCell(8, DictUtils.getDictLabel(e.getBaseId(),"oa_employee_base", ""), row, cell, style);
	       	String value8 = DictUtils.getDictLabel(e.getBaseId(),"oa_employee_base", "");
	       	if(StringUtils.isNotBlank(value8)){
	       		if(value8.getBytes().length*2*256 > width8){
	       			width8 = value8.getBytes().length*2*256;
	       		}
	       	}
	       	
	       	ExportExcel.myCreateCell(9, e.getRemarks(), row, cell, style);
	       	if(StringUtils.isNotBlank(e.getRemarks())){
	       		if(e.getRemarks().getBytes().length*2*256 > width9){
	       			width9 = e.getRemarks().getBytes().length*2*256;
	       		}
	       	}
	       	rowNum++;
        }
        
        sheet.setColumnWidth(0, width0);
        sheet.setColumnWidth(1, width1);
        sheet.setColumnWidth(2, width2);
        sheet.setColumnWidth(3, width3);
        sheet.setColumnWidth(4, width4);
        sheet.setColumnWidth(5, width5);
        sheet.setColumnWidth(6, width6);
        sheet.setColumnWidth(7, width7);
        sheet.setColumnWidth(8, width8);
        if(width9<255*256){
        	sheet.setColumnWidth(9, width9);
        }else{
        	sheet.setColumnWidth(9, 6000);
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
