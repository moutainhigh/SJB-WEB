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

import com.sijibao.oa.activiti.api.response.contract.ContractFlowNewResponse;
import com.sijibao.oa.activiti.api.service.ContractFlowNewService;
import com.sijibao.oa.common.persistence.Page;
import com.sijibao.oa.common.service.BaseService;
import com.sijibao.oa.common.utils.UploadUtils;
import com.sijibao.oa.common.utils.excel.ExportExcel;
import com.sijibao.oa.modules.flow.entity.ContractOverdue;
import com.sijibao.oa.modules.flow.service.report.ContractOverdueService;
import com.sijibao.oa.modules.intfz.request.report.ContractOverdueRequest;
import com.sijibao.oa.modules.sys.dao.UserDao;
import com.sijibao.oa.modules.sys.entity.User;
import com.sijibao.oa.modules.sys.utils.DictUtils;

/**
 * 合同逾期报表查询服务
 * @author huangkai
 */
@Service
@Transactional(readOnly = true)
public class IntfzContractOverdueService extends BaseService {
	
	@Autowired
	private ContractOverdueService contractOverdueService;
	@Autowired
	private UserDao userDao;
	@Autowired
	private ContractFlowNewService contractFlowNewService;
	/**
	 * 查询合同逾期报表信息
	 * @param req
	 * @return
	 */
	public Page<ContractOverdue> contractOverdueList(ContractOverdueRequest req){
		ContractOverdue contractOverdue = new ContractOverdue();
		contractOverdue.setOverdueType(req.getOverdueType());
		Page<ContractOverdue> page = contractOverdueService.findContractOverduePage(new Page<ContractOverdue>(req.getPageNo(), req.getPageSize()), contractOverdue);
		return page;
	}


	/**
	 * 合同逾期保存
	 * @param businessId
	 * @param loginName
	 */
	@Transactional(readOnly = false)
	public void saveContractOverdue(String businessId, String loginName){
		User user = new User();
		user.setLoginName(loginName);
		//查询人员所属信息
		user = userDao.getByLoginName(user);
		ContractFlowNewResponse contractFlowResponse = contractFlowNewService.get(businessId);
		ContractOverdue contractOverdue = change(contractFlowResponse, ContractOverdue.class);

//		ContractOverdue contractOverdue = new ContractOverdue();
//		contractOverdue.setProcCode("1811121605349263");
//		contractOverdue.setProcInsId("03337aee75bd40f99718ade5dafaf19e");
//		contractOverdue.setOfficeName("测试部11");
//		contractOverdue.setFirstMemberName("我的大戏11");
//		contractOverdue.setSecondMemberName("xxx");
//		contractOverdue.setThirdMemberName("yyy");
//		contractOverdue.setContractName("123");
//		contractOverdue.setApplyPerName("hk");
//		contractOverdue.setContractFlowStatus("1");
		ContractOverdue co = contractOverdueService.queryContractOverdue(contractOverdue);
		if(null == co){
			contractOverdue.setOpenAccountTime(new Date());
			contractOverdueService.save(contractOverdue);
		}
	}
	
	/**
	 * 合同逾期报表导出
	 * @param fileName
	 * @return
	 */
	public String contractOverdueExport(String fileName){
		ContractOverdue contractOverdue = new ContractOverdue();
		List<ContractOverdue> resultList = contractOverdueService.findContractOverdueList(contractOverdue);

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
		workBook.setSheetName(0, "合同逾期报表"+now);
		// 第0行 列头
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell = null;
        HSSFCellStyle style = ExportExcel.getStyle(workBook);
        ExportExcel.myCreateCell(0, "部门", row, cell, style);
        ExportExcel.myCreateCell(1, "提交人", row, cell, style);
        ExportExcel.myCreateCell(2, "开户时间", row, cell, style);
        ExportExcel.myCreateCell(3, "逾期天数", row, cell, style);
        ExportExcel.myCreateCell(4, "流程编号", row, cell, style);
        ExportExcel.myCreateCell(5, "合同名称", row, cell, style);
        ExportExcel.myCreateCell(6, "甲方名称", row, cell, style);
        ExportExcel.myCreateCell(7, "乙方名称", row, cell, style);
        ExportExcel.myCreateCell(8, "丙方名称", row, cell, style);
        ExportExcel.myCreateCell(9, "审批状态", row, cell, style);
        ExportExcel.myCreateCell(10, "合同负责人", row, cell, style);

        //设置数据
        int rowNum = 1;
        for(ContractOverdue q:resultList){
        	row = sheet.createRow(rowNum);
        	ExportExcel.myCreateCell(0, q.getOfficeName(), row, cell, style);
        	ExportExcel.myCreateCell(1, q.getApplyPerName(), row, cell, style);
        	ExportExcel.myCreateCell(2, sdf2.format(q.getOpenAccountTime()), row, cell, style);
        	ExportExcel.myCreateCell(3, q.getOverdueDays(), row, cell, style);
        	ExportExcel.myCreateCell(4, q.getProcCode(), row, cell, style);
        	ExportExcel.myCreateCell(5, q.getContractName(), row, cell, style);
        	ExportExcel.myCreateCell(6, q.getFirstMemberName(), row, cell, style);
        	ExportExcel.myCreateCell(7, q.getSecondMemberName(), row, cell, style);
        	ExportExcel.myCreateCell(8, q.getThirdMemberName(), row, cell, style);
        	ExportExcel.myCreateCell(9, DictUtils.getDictLabel(q.getContractFlowStatus(),"expense_status",""), row, cell, style);
        	ExportExcel.myCreateCell(10, q.getContractLeaderName(), row, cell, style);
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
