package sijibao.oa.jeesite.modules.intfz.service.asset;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.sijibao.oa.asset.api.AssetBasicConfigService;
import com.sijibao.oa.asset.api.AssetService;
import com.sijibao.oa.asset.api.request.asset.AssetPickOrTransferParam;
import com.sijibao.oa.asset.api.request.asset.AssetQueryPageParam;
import com.sijibao.oa.asset.api.request.asset.AssetSaveOrUpdateParam;
import com.sijibao.oa.asset.api.request.assetplace.AssetPlaceQueryOneParam;
import com.sijibao.oa.asset.api.request.assetsupplier.AssetSupplierQueryOneParam;
import com.sijibao.oa.asset.api.request.assettype.AssetTypeQueryOneParam;
import com.sijibao.oa.asset.api.response.asset.AssetQueryDetailResult;
import com.sijibao.oa.asset.api.response.asset.AssetQueryListResult;
import com.sijibao.oa.asset.api.response.assetplace.AssetPlaceQueryPageResult;
import com.sijibao.oa.asset.api.response.assetsupplier.AssetSupplierQueryPageResult;
import com.sijibao.oa.asset.api.response.assettype.AssetTypeQueryDetailResult;
import com.sijibao.oa.common.service.BaseService;
import com.sijibao.oa.common.utils.UploadUtils;
import com.sijibao.oa.common.utils.excel.ExportExcel;
import com.sijibao.oa.modules.asset.entity.Asset;
import com.sijibao.oa.modules.intfz.request.asset.AssetQueryPageReq;
import com.sijibao.oa.modules.sys.entity.Office;
import com.sijibao.oa.modules.sys.entity.User;
import com.sijibao.oa.modules.sys.service.OfficeService;
import com.sijibao.oa.modules.sys.service.SystemService;
import com.sijibao.oa.modules.sys.utils.DictUtils;

/**
 * 固定资产
 * @author huangkai
 */
@Service
@Transactional(readOnly = true)
public class IntfzAssetService extends BaseService {

	@Autowired
	private AssetService assetService;
	@Autowired
	private AssetBasicConfigService assetBasicConfigService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private OfficeService officeService;



	/**
	 *  固定资产导出
	 * @param fileName
	 * @return
	 */
	public String assetExport(AssetQueryPageReq req,String fileName){

//		AssetQueryPageReq req2 = new AssetQueryPageReq();
		AssetQueryPageParam params = change(req, AssetQueryPageParam.class);

		List<AssetQueryListResult> resultList = assetService.queryAssetList(params);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmss");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		String now = sdf1.format(new Date());
		DecimalFormat df = new DecimalFormat("0.00");
		String url = "";
		 // 创建一个新的Excel
		HSSFWorkbook  workBook = new HSSFWorkbook();
        // 创建sheet页
		HSSFSheet sheet = workBook.createSheet();
        // 设置表名
		workBook.setSheetName(0, "固定资产导出"+now);
		// 第0行 列头
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell = null;
        HSSFCellStyle style = ExportExcel.getStyle(workBook);
        ExportExcel.myCreateCell(0, "资产状态", row, cell, style);
        ExportExcel.myCreateCell(1, "资产编号", row, cell, style);
        ExportExcel.myCreateCell(2, "资产名称", row, cell, style);
        ExportExcel.myCreateCell(3, "资产类别", row, cell, style);
        ExportExcel.myCreateCell(4, "入库时间", row, cell, style);
        ExportExcel.myCreateCell(5, "来源", row, cell, style);
        ExportExcel.myCreateCell(6, "规格型号", row, cell, style);
        ExportExcel.myCreateCell(7, "计量单位", row, cell, style);
        ExportExcel.myCreateCell(8, "供应商", row, cell, style);
        ExportExcel.myCreateCell(9, "供应商联系方式", row, cell, style);
        ExportExcel.myCreateCell(10, "金额", row, cell, style);
		ExportExcel.myCreateCell(11, "剩余付费时间(天)", row, cell, style);
		ExportExcel.myCreateCell(12, "租用/购入时间", row, cell, style);
		ExportExcel.myCreateCell(13, "保修起始时间", row, cell, style);
		ExportExcel.myCreateCell(14, "过保时间", row, cell, style);
		ExportExcel.myCreateCell(15, "放置地", row, cell, style);
		ExportExcel.myCreateCell(16, "使用部门", row, cell, style);
		ExportExcel.myCreateCell(17, "使用人", row, cell, style);
		ExportExcel.myCreateCell(18, "工位", row, cell, style);
		ExportExcel.myCreateCell(19, "领用日期", row, cell, style);
		ExportExcel.myCreateCell(20, "所属公司", row, cell, style);
		ExportExcel.myCreateCell(21, "备注", row, cell, style);


        //设置数据
        int rowNum = 1;
        for(AssetQueryListResult q:resultList){
        	row = sheet.createRow(rowNum);
//			DictUtils.getDictLabel(q.getContractFlowStatus(),"expense_status","")
        	ExportExcel.myCreateCell(0, q.getStatus(), row, cell, style);
        	ExportExcel.myCreateCell(1, q.getCode(), row, cell, style);
        	ExportExcel.myCreateCell(2, q.getName(), row, cell, style);
        	ExportExcel.myCreateCell(3, q.getAssetTypeName(), row, cell, style);
        	ExportExcel.myCreateCell(4, sdf.format(q.getEntryTime()), row, cell, style);
        	ExportExcel.myCreateCell(5, DictUtils.getDictLabel(q.getSource(),"asset_source",""), row, cell, style);
        	ExportExcel.myCreateCell(6, q.getSpecificationType(), row, cell, style);
        	ExportExcel.myCreateCell(7, q.getUnit(), row, cell, style);
        	ExportExcel.myCreateCell(8, q.getAssetSupplierName(), row, cell, style);
        	ExportExcel.myCreateCell(9, q.getContactWay(), row, cell, style);
        	ExportExcel.myCreateCell(10, q.getMoney().setScale(2,BigDecimal.ROUND_DOWN).toString(), row, cell, style);
        	if("3".equals(q.getSource())){
				ExportExcel.myCreateCell(11, q.getRemainingTime(), row, cell, style);
			}else{
				ExportExcel.myCreateCell(11, "", row, cell, style);
			}

			ExportExcel.myCreateCell(12, sdf.format(q.getBuyTime()), row, cell, style);

        	if(null !=q.getGuaranteeBeginTime()){
				ExportExcel.myCreateCell(13, sdf.format(q.getGuaranteeBeginTime()), row, cell, style);
			}else{
				ExportExcel.myCreateCell(13, "", row, cell, style);
			}
			if(null != q.getGuaranteeEndTime()){
				ExportExcel.myCreateCell(14, sdf.format(q.getGuaranteeEndTime()), row, cell, style);
			}else{
				ExportExcel.myCreateCell(14, "", row, cell, style);
			}

			ExportExcel.myCreateCell(15, q.getAssetPlaceName(), row, cell, style);
			ExportExcel.myCreateCell(16, q.getUsingOffice(), row, cell, style);
			ExportExcel.myCreateCell(17, q.getUsingPerson(), row, cell, style);
			ExportExcel.myCreateCell(18, q.getUsingWorkplace(), row, cell, style);
			if(null == q.getDoneDate()){
				ExportExcel.myCreateCell(19, "", row, cell, style);
			}else{
				ExportExcel.myCreateCell(19, sdf.format(q.getDoneDate()), row, cell, style);
			}

			ExportExcel.myCreateCell(20, q.getCompany(), row, cell, style);
			ExportExcel.myCreateCell(21, q.getRemarks(), row, cell, style);
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


	public static boolean isRowEmpty(Row row) {
		for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
			Cell cell = row.getCell(c);
			if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK){
				return false;
			}
		}
		return true;
	}

	/**
	 *  固定资产导入
	 * @return
	 */
	public Map readXls(MultipartFile file) throws Exception {
		InputStream is = file.getInputStream();
		 HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
		 Asset asset = null;
		 List<Asset> list = new ArrayList<Asset>();
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		 String errMsg = "";
		 // 循环工作表Sheet
		 for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
			 HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
			 if (hssfSheet == null) {
					 continue;
				 }
			 // 循环行Row
			 for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
				 HSSFRow hssfRow = hssfSheet.getRow(rowNum);
				 if (!isRowEmpty(hssfRow)) {
					 asset = new Asset();
					 HSSFCell code = hssfRow.getCell(0);
					 HSSFCell name = hssfRow.getCell(1);
					 HSSFCell assetType = hssfRow.getCell(2);
					 HSSFCell brand = hssfRow.getCell(3);
					 HSSFCell serialNo = hssfRow.getCell(4);

					 HSSFCell specificationType = hssfRow.getCell(5);

					 HSSFCell source = hssfRow.getCell(6);
					 HSSFCell money = hssfRow.getCell(7);
					 HSSFCell entryTime = hssfRow.getCell(8);
					 HSSFCell dueTime = hssfRow.getCell(9);
					 HSSFCell assetSupplierName = hssfRow.getCell(10);
					 HSSFCell buyTime = hssfRow.getCell(11);
					 HSSFCell guaranteeBeginTime = hssfRow.getCell(12);
					 HSSFCell guaranteeEndTime = hssfRow.getCell(13);
					 HSSFCell assetPlaceName = hssfRow.getCell(14);
					 HSSFCell usingOffice = hssfRow.getCell(15);
					 HSSFCell usingPerson = hssfRow.getCell(16);
					 HSSFCell usingWorkplace = hssfRow.getCell(17);

					 HSSFCell doneDate = hssfRow.getCell(18);
					 HSSFCell company = hssfRow.getCell(19);
					 HSSFCell remarks = hssfRow.getCell(20);

					 //资产编号
					 if("".equals(getValue(code))){
						 errMsg = errMsg + "第"+rowNum+"行资产编号不能为空!";
					 }else{
						 String codeStr ="";
					 	if(code.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							DecimalFormat df = new DecimalFormat("0");
							codeStr = String.valueOf(df.format(code.getNumericCellValue()));
						}else{
							codeStr = getValue(code);
						}

						 Pattern pattern=Pattern.compile("^[a-zA-Z0-9]{1,18}$");
						 Matcher match=pattern.matcher(codeStr);
						 if(match.matches()==false){
							 errMsg = errMsg + "第"+rowNum+"行资产编号格式不正确!";
						 }else{
							 //校验资产编号是否重复，如果已经存在则不能导入
							 AssetQueryDetailResult result = assetService.queryAssetByCode(codeStr);
							 if(null != result){
								 errMsg = errMsg + "第"+rowNum+"行资产编号"+codeStr+"重复! ";
							 }else{

								 asset.setCode(codeStr);
							 }
						 }
					 }

					 //资产名称
					 if("".equals(getValue(name))){
						 errMsg = errMsg + "第"+rowNum+"行资产名称不能为空!";
					 }else{
						 if(getValue(name).length() > 50){
							 errMsg = errMsg + "第"+rowNum+"行资产名称长度过长!";
						 }
					 }

					 //资产类别
					 if("".equals(getValue(assetType))){
						 errMsg = errMsg + "第"+rowNum+"行资产类别不能为空!";
					 }else {
					 	if(getValue(assetType).length() > 50){
							errMsg = errMsg + "第"+rowNum+"行资产类别长度过长!";
						}else {

							//校验资产类别是否存在
							AssetTypeQueryOneParam param = new AssetTypeQueryOneParam();
							param.setName(getValue(assetType));
							AssetTypeQueryDetailResult queryResult = assetBasicConfigService.findAssetTypeByParams(param);
							if (null == queryResult) {
								errMsg = errMsg + "第" + rowNum + "行资产类别" + getValue(assetType) + "不存在! ";
							} else {
								asset.setAssetTypeId(queryResult.getId());
							}
						}
					 }

					 //序列号
					 if("".equals(getValue(serialNo))){
						 errMsg = errMsg + "第"+rowNum+"行序列号不能为空!";
					 }else{
						 if(getValue(serialNo).length() > 50){
							 errMsg = errMsg + "第"+rowNum+"行序列号长度过长!";
						 }else{
							 String serialNoStr ="";
							 if(serialNo.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
								 DecimalFormat df = new DecimalFormat("0");
								 serialNoStr = String.valueOf(df.format(serialNo.getNumericCellValue()));
							 }else{
								 serialNo.setCellType(serialNo.CELL_TYPE_STRING);
								 serialNoStr = getValue(serialNo);
							 }
							 asset.setSerialNo(serialNoStr);
						 }
					 }

					 //来源
					 if("".equals(getValue(source))){
						 errMsg = errMsg + "第"+rowNum+"行来源不能为空!";
					 }else{
						 if("租赁".equals(getValue(source)) || "自购".equals(getValue(source)) || "租赁转自购".equals(getValue(source))){
							 asset.setSource(DictUtils.getDictValue(getValue(source),"asset_source",""));
						 }else{
							 errMsg = errMsg + "第"+rowNum+"行来源填写有误!";
						 }
					 }

					 //金额
					 if("".equals(getValue(money))){
						 errMsg = errMsg + "第"+rowNum+"行金额不能为空!";
					 }else{
						 Pattern pattern=Pattern.compile("^\\d*\\.?\\d{0,2}$"); // 判断小数点后2位的数字的正则表达式
						 Matcher match=pattern.matcher(getValue(money));
						 if(match.matches()==false){
							 errMsg = errMsg + "第"+rowNum+"行金额格式不正确只能输入整数或2位小数!";
						 }else{
							 asset.setMoney(BigDecimal.valueOf(Double.parseDouble(getValue(money))));
						 }
					 }



					 //入库时间
					 if("".equals(getValue(entryTime))){
						 errMsg = errMsg + "第"+rowNum+"行入库时间不能为空!";
					 }else{
					 	try {
							asset.setEntryTime(sdf.parse(getValue(entryTime)));
						}catch (Exception e){
//					 		e.printStackTrace();
							errMsg = errMsg + "第"+rowNum+"行入库时间格式有误!";
						}
					 }

					 //租用/购入时间
					 if("".equals(getValue(buyTime))){
						 errMsg = errMsg + "第"+rowNum+"行租用/购入时间不能为空!";
					 }else{
						 try {
							 asset.setBuyTime(sdf.parse(getValue(buyTime)));
						 }catch (Exception e){
//					 		e.printStackTrace();
							 errMsg = errMsg + "第"+rowNum+"行租用/购入时间格式有误!";
						 }
					 }

					 //放置地
					 if("".equals(getValue(assetPlaceName))){
						 errMsg = errMsg + "第"+rowNum+"行放置地不能为空!";
					 }else {
						 if(getValue(assetPlaceName).length() > 50){
							 errMsg = errMsg + "第"+rowNum+"行放置地长度过长!";
						 }else{
							 AssetPlaceQueryOneParam assetPlaceParam = new AssetPlaceQueryOneParam();
							 assetPlaceParam.setName(getValue(assetPlaceName));
							 AssetPlaceQueryPageResult placeResult = assetBasicConfigService.findAssetPlaceByParams(assetPlaceParam);
							 if (null == placeResult) {
								 errMsg = errMsg + "第" + rowNum + "行放置地" + getValue(assetPlaceName) + "不存在! ";
							 } else {
								 asset.setAssetPlaceId(placeResult.getId());
							 }
						 }
					 }

					 //供应商非必填
					 if(null == getValue(assetSupplierName) || "".equals(getValue(assetSupplierName))){
						 asset.setAssetSupplierId("");
					 }else {
						 //校验供应商是否存在
						 AssetSupplierQueryOneParam assetSupplierParam = new AssetSupplierQueryOneParam();
						 assetSupplierParam.setName(getValue(assetSupplierName));
						 AssetSupplierQueryPageResult supplierResult = assetBasicConfigService.findAssetSupplierByParams(assetSupplierParam);
						 if (null == supplierResult) {
							 errMsg = errMsg + "第" + rowNum + "行供应商" + getValue(assetPlaceName) + "不存在! ";
						 } else {
							 asset.setAssetSupplierId(supplierResult.getId());
						 }
					 }

					 //来源为租赁转自购，则付费截止时间则必填
					 if(null != getValue(source) && "租赁转自购".equals(getValue(source))){
					 	if(null == getValue(dueTime) || "".equals(getValue(dueTime))){
							errMsg = errMsg + "第"+rowNum+"行来源为租赁转自购，则付费截止时间则必填! ";
						}
					 }

					 //使用部门，使用人，工位，领用日期如果填了1个，则4个都为必填
					 if(!"".equals(getValue(usingOffice)) || !"".equals(getValue(usingPerson)) ||
							 !"".equals(getValue(usingWorkplace)) || !"".equals(getValue(doneDate))){
					 	if(!"".equals(getValue(usingOffice)) && !"".equals(getValue(usingPerson)) &&
								!"".equals(getValue(usingWorkplace)) && !"".equals(getValue(doneDate))){


							//校验使用部门是否存在
							Office result= officeService.getByName(getValue(usingOffice));
							if(null == result){
								errMsg = errMsg + "第"+rowNum+"行使用部门"+getValue(usingOffice)+"不存在! ";
							}else{
								if("0".equals(result.getUseable()) || "1".equals(result.getDelFlag())){
									errMsg = errMsg + "第"+rowNum+"行使用部门"+getValue(usingOffice)+"被停用或删除! ";
								}else{
									asset.setUsingOffice(result.getId());
								}
							}

					 		//校验使用人是否存在
							User usr = systemService.getUserByName(getValue(usingPerson));
							if(null == usr){
								errMsg = errMsg + "第"+rowNum+"行使用人"+getValue(usingPerson)+"不存在! ";
							}else{
								if("3".equals(usr.getUserStatus())){
									errMsg = errMsg + "第"+rowNum+"行使用人"+getValue(usingPerson)+"为离职人员! ";
								}else{
									asset.setUsingPerson(usr.getId());
								}
							}

							usingWorkplace.setCellType(usingWorkplace.CELL_TYPE_STRING);
							if(getValue(usingWorkplace).length() > 100){
								errMsg = errMsg + "第"+rowNum+"行使用部门长度过长! ";
							}
							asset.setUsingWorkplace(getValue(usingWorkplace));
						}else{
							errMsg = errMsg + "第"+rowNum+"行使用部门，使用人，工位，领用日期必填! ";
						}
					 }

//					 asset.setCode(getValue(code));
					 asset.setName(getValue(name));
//					 asset.setEntryTime(sdf.parse(getValue(entryTime)));
					 if(null != specificationType) {
						 specificationType.setCellType(specificationType.CELL_TYPE_STRING);
						 if(getValue(specificationType).length() > 100){
							 errMsg = errMsg + "第"+rowNum+"行规格型号长度过长! ";
						 }
					 }
					 asset.setSpecificationType(getValue(specificationType));
//					 asset.setSource(DictUtils.getDictValue(getValue(source),"asset_source",""));


					 //付费截止时间
					 if(null != getValue(dueTime) && !"".equals(getValue(dueTime))){
					 	try {
							asset.setDueTime(sdf.parse(getValue(dueTime)));
						}catch (Exception e){
							errMsg = errMsg + "第"+rowNum+"行付费截止时间格式不正确! ";
						}
					 }
					 //保修起始时间
					 if(null != getValue(guaranteeBeginTime) && !"".equals(getValue(guaranteeBeginTime))){
					 	try{
						 	asset.setGuaranteeBeginTime(sdf.parse(getValue(guaranteeBeginTime)));
						}catch (Exception e){
							errMsg = errMsg + "第"+rowNum+"行保修起始时间格式不正确! ";
						}
					 }
					 //过保时间
					 if(null != getValue(guaranteeEndTime) && !"".equals(getValue(guaranteeEndTime))){
					 	try {
							asset.setGuaranteeEndTime(sdf.parse(getValue(guaranteeEndTime)));
						}catch (Exception e){
							errMsg = errMsg + "第"+rowNum+"行过保时间格式不正确! ";
						}
					 }

					 if(!"".equals(getValue(remarks))){
						 if(getValue(remarks).length() > 200){
							 errMsg = errMsg + "第"+rowNum+"行备注过长!";
						 }
					 }
					 asset.setRemarks(getValue(remarks));
//					 asset.setUsingOffice(getValue(usingOffice));
//					 asset.setUsingPerson(getValue(usingPerson));


					 if(null != getValue(doneDate) && !"".equals(getValue(doneDate))) {
					 	try {
							asset.setDoneDate(sdf.parse(getValue(doneDate)));
						}catch (Exception e){
							errMsg = errMsg + "第"+rowNum+"行领用时间格式不正确! ";
						}
					 }

					 if(!"".equals(getValue(company))){
						 if(getValue(company).length() > 100){
							 errMsg = errMsg + "第"+rowNum+"行公司过长!";
						 }
					 }
					 asset.setCompany(getValue(company));

					 if(!"".equals(getValue(brand))){
						 if(getValue(brand).length() > 100){
							 errMsg = errMsg + "第"+rowNum+"行名牌过长!";
						 }
					 }
					 asset.setBrand(getValue(brand));
//					 asset.setSerialNo(getValue(serialNo));
					 if(null == asset.getUsingOffice() || "".equals(asset.getUsingOffice())){
						 asset.setStatus("2");
					 }else{
						 asset.setStatus("1");
					 }

					 //如果有校验失败的，跳到下一个
					 if(!"".equals(errMsg)){
						 continue;
					 }


					 list.add(asset);
				 }
			 }
		 }
		 Map resultMap = new HashMap();
		 resultMap.put("list",list);
		 resultMap.put("errMsg",errMsg);
		 return resultMap;
	 }

	/**
	 * 获取单元格的值
	 *
	 * @param hcell
	 * @return
	 */
	private String getValue(HSSFCell hcell) {

		String value = null;
		if (null != hcell) {
			switch (hcell.getCellType()) {
				// 单元格是函数计算出来的数据
				case HSSFCell.CELL_TYPE_FORMULA:
					try {
						value = String.valueOf(hcell.getNumericCellValue());
					} catch (Exception e) {
						value = String.valueOf(hcell.getRichStringCellValue());
					}
					break;
				// 单元格是数字类型的
				case HSSFCell.CELL_TYPE_NUMERIC:
					// 获取单元格的样式值，即获取单元格格式对应的数值
					int style = hcell.getCellStyle().getDataFormat();
					// 判断是否是日期格式
					if (HSSFDateUtil.isCellDateFormatted(hcell)) {
						Date date = hcell.getDateCellValue();
						// 对不同格式的日期类型做不同的输出，与单元格格式保持一致
						switch (style) {
							case 178:
								value = new SimpleDateFormat("yyyy'年'M'月'd'日'").format(date);
								break;
							case 14:
								value = new SimpleDateFormat("yyyy/MM/dd").format(date);
								break;
							case 179:
								value = new SimpleDateFormat("yyyy/MM/dd HH:mm").format(date);
								break;
							case 181:
								value = new SimpleDateFormat("yyyy/MM/dd HH:mm a ").format(date);
								break;
							case 22:
								value = new SimpleDateFormat(" yyyy/MM/dd HH:mm:ss ").format(date);
								break;
							default:
								break;
						}
					} else {
						switch (style) {
							// 单元格格式为百分比，不格式化会直接以小数输出
							case 9:
								value = new DecimalFormat("0.00%").format(hcell
										.getNumericCellValue());
								break;
							// DateUtil判断其不是日期格式，在这里也可以设置其输出类型
							case 57:
								value = new SimpleDateFormat(" yyyy'年'MM'月' ").format(hcell.getDateCellValue());
								break;
							default:
								value = String.valueOf(hcell.getNumericCellValue());
								break;
						}
					}

					break;
				// 单元格是字符串类型的
				case HSSFCell.CELL_TYPE_STRING:
					value = String.valueOf(hcell.getRichStringCellValue());
					break;
				case HSSFCell.CELL_TYPE_BLANK:
					value = "";
					break;
			}
		}else{
			value="";
		}
		return value;
	}


	public void importAsset(List<Asset> list,User user){
		for(Asset asset:list){
			AssetSaveOrUpdateParam param = change(asset,AssetSaveOrUpdateParam.class);
			param.setEntryTime(asset.getEntryTime().getTime());
			param.setBuyTime(asset.getBuyTime().getTime());
			if(null != asset.getDueTime()) {
				param.setDueTime(asset.getDueTime().getTime());
			}
			if(null != asset.getGuaranteeBeginTime()){
				param.setGuaranteeBeginTime(asset.getGuaranteeBeginTime().getTime());
			}
			if(null != asset.getGuaranteeEndTime()){
				param.setGuaranteeEndTime(asset.getGuaranteeEndTime().getTime());
			}

			param.setOperateUserId(user.getId());

			AssetPickOrTransferParam pick = new AssetPickOrTransferParam();

			if(null != asset.getUsingPerson() && !"".equals(asset.getUsingPerson())) {
				pick.setOperateUserId(user.getId());
				pick.setUsingOffice(asset.getUsingOffice());
				pick.setUsingPerson(asset.getUsingPerson());
				pick.setUsingWorkplace(asset.getUsingWorkplace());
				pick.setAssetPlaceId(asset.getAssetPlaceId());
				pick.setPickOrTransferDate(asset.getDoneDate().getTime());
				pick.setRemarks("");
			}
			assetService.saveAndPickAsset(param,pick);


		}
	}



	/**
	 *  固定资产导入模板下载
	 * @param fileName
	 * @return
	 */
	public String assetExportModel(String fileName){

//		AssetQueryPageReq req2 = new AssetQueryPageReq();
//		AssetQueryPageParam params = change(req2, AssetQueryPageParam.class);

//		List<AssetQueryListResult> resultList = assetService.queryAssetList(params);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
//		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		String now = sdf.format(new Date());
//		DecimalFormat df = new DecimalFormat("0.00");
		String url = "";
		// 创建一个新的Excel
		HSSFWorkbook  workBook = new HSSFWorkbook();
		// 创建sheet页
		HSSFSheet sheet = workBook.createSheet();
		// 设置表名
		workBook.setSheetName(0, "固定资产导出"+now);
		// 第0行 列头
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell = null;
		HSSFCellStyle style = ExportExcel.getStyle(workBook);
		ExportExcel.myCreateCell(0, "资产编号*", row, cell, style);
		ExportExcel.myCreateCell(1, "资产名称*", row, cell, style);
		ExportExcel.myCreateCell(2, "资产类别*", row, cell, style);
		ExportExcel.myCreateCell(3, "品牌", row, cell, style);
		ExportExcel.myCreateCell(4, "序列号*", row, cell, style);
		ExportExcel.myCreateCell(5, "规格型号", row, cell, style);
		ExportExcel.myCreateCell(6, "来源*", row, cell, style);
		ExportExcel.myCreateCell(7, "金额*", row, cell, style);
		ExportExcel.myCreateCell(8, "入库时间*", row, cell, style);
		ExportExcel.myCreateCell(9, "付费截止时间", row, cell, style);
		ExportExcel.myCreateCell(10, "供应商", row, cell, style);
		ExportExcel.myCreateCell(11, "租用/购入时间*", row, cell, style);
		ExportExcel.myCreateCell(12, "保修起始时间", row, cell, style);
		ExportExcel.myCreateCell(13, "过保时间", row, cell, style);
		ExportExcel.myCreateCell(14, "放置地*", row, cell, style);
		ExportExcel.myCreateCell(15, "使用部门", row, cell, style);
		ExportExcel.myCreateCell(16, "使用人", row, cell, style);
		ExportExcel.myCreateCell(17, "工位", row, cell, style);
		ExportExcel.myCreateCell(18, "领用日期", row, cell, style);
		ExportExcel.myCreateCell(19, "所属公司", row, cell, style);
		ExportExcel.myCreateCell(20, "备注", row, cell, style);

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
