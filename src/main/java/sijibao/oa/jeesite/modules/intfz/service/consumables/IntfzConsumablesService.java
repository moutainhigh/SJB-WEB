package sijibao.oa.jeesite.modules.intfz.service.consumables;

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
import com.sijibao.oa.asset.api.ConsumablesTypeService;
import com.sijibao.oa.asset.api.IntfzWebConsumablesService;
import com.sijibao.oa.asset.api.request.assetplace.AssetPlaceQueryOneParam;
import com.sijibao.oa.asset.api.request.consumables.ConsumablesReq;
import com.sijibao.oa.asset.api.request.consumables.ConsumablesSaveReq;
import com.sijibao.oa.asset.api.request.consumables.ConsumablesTypeReq;
import com.sijibao.oa.asset.api.response.assetplace.AssetPlaceQueryPageResult;
import com.sijibao.oa.asset.api.response.assettype.consumables.ConsumablesPlaceResp;
import com.sijibao.oa.asset.api.response.assettype.consumables.ConsumablesResp;
import com.sijibao.oa.asset.api.response.assettype.consumables.ConsumablesTypeResp;
import com.sijibao.oa.common.service.BaseService;
import com.sijibao.oa.common.utils.UploadUtils;
import com.sijibao.oa.common.utils.excel.ExportExcel;
import com.sijibao.oa.modules.intfz.request.consumables.MainConsumablesReq;
import com.sijibao.oa.modules.intfz.utils.CharChangeUtils;
import com.sijibao.oa.modules.sys.entity.User;

/**
 * 消耗品
 *
 * @author huangkai
 */
@Service
@Transactional(readOnly = true)
public class IntfzConsumablesService extends BaseService {

    @Autowired
    private IntfzWebConsumablesService intfzWebConsumablesService;
    @Autowired
    private AssetBasicConfigService assetBasicConfigService;
    @Autowired
    private ConsumablesTypeService consumablesTypeService;

    /**
     * 消耗品导出
     *
     * @param fileName
     * @return
     */
    public String consumablesExport(MainConsumablesReq req, String fileName) {

        ConsumablesReq change = change(req, ConsumablesReq.class);
        String[] type = req.getGoodType();
        if (type != null && type.length > 0) {
            change.setGoodType(type[type.length - 1]);
        }
        List<ConsumablesResp> list = intfzWebConsumablesService.findConsumablesList(change);


        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        String now = sdf.format(new Date());
        DecimalFormat df = new DecimalFormat("0.00");
        String url = "";
        // 创建一个新的Excel
        HSSFWorkbook workBook = new HSSFWorkbook();
        // 创建sheet页
        HSSFSheet sheet = workBook.createSheet();
        // 设置表名
        workBook.setSheetName(0, "消耗品导出" + now);
        // 第0行 列头
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell = null;
        HSSFCellStyle style = ExportExcel.getStyle(workBook);
        ExportExcel.myCreateCell(0, "物品编号", row, cell, style);
        ExportExcel.myCreateCell(1, "物品名称", row, cell, style);
        ExportExcel.myCreateCell(2, "物品类别", row, cell, style);
        ExportExcel.myCreateCell(3, "单位", row, cell, style);
        ExportExcel.myCreateCell(4, "规格型号", row, cell, style);
        ExportExcel.myCreateCell(5, "放置地", row, cell, style);
        ExportExcel.myCreateCell(6, "库存数量", row, cell, style);
        ExportExcel.myCreateCell(7, "单价", row, cell, style);
        ExportExcel.myCreateCell(8, "金额", row, cell, style);
        ExportExcel.myCreateCell(9, "物品备注", row, cell, style);


        //设置数据
        int rowNum = 1;
        for (ConsumablesResp q : list) {
            row = sheet.createRow(rowNum);
//			DictUtils.getDictLabel(q.getContractFlowStatus(),"expense_status","")
            ExportExcel.myCreateCell(0, q.getGoodCode(), row, cell, style);
            ExportExcel.myCreateCell(1, q.getGoodName(), row, cell, style);
            ExportExcel.myCreateCell(2, q.getGoodTypeName(), row, cell, style);
            ExportExcel.myCreateCell(3, q.getGoodUnit(), row, cell, style);
            ExportExcel.myCreateCell(4, q.getGoodSpec(), row, cell, style);

            List<ConsumablesPlaceResp> placeList = q.getPlaces();
            StringBuffer sb = new StringBuffer("");
            int temp = 1;
            for (ConsumablesPlaceResp re : placeList) {
                sb.append(re.getPlaceName());
                if (temp < placeList.size()) {
                    sb.append(",");
                }
                temp++;
            }
            String places = sb.toString();

            ExportExcel.myCreateCell(5, places, row, cell, style);
            ExportExcel.myCreateCell(6, String.valueOf(q.getGoodCount()), row, cell, style);
            ExportExcel.myCreateCell(7, String.valueOf(q.getGoodPrice()), row, cell, style);
            ExportExcel.myCreateCell(8, String.valueOf(q.getGoodTotal()), row, cell, style);
            ExportExcel.myCreateCell(9, q.getRemarks(), row, cell, style);

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
     * 消耗品导入
     *
     * @return
     */
    public Map readXls(MultipartFile file) throws Exception {
        InputStream is = file.getInputStream();
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
        ConsumablesSaveReq consumablesSaveReq = null;
        List<ConsumablesSaveReq> list = new ArrayList<ConsumablesSaveReq>();
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
                    consumablesSaveReq = new ConsumablesSaveReq();
                    HSSFCell code = hssfRow.getCell(0);
                    HSSFCell name = hssfRow.getCell(1);
                    HSSFCell type = hssfRow.getCell(2);
                    HSSFCell unit = hssfRow.getCell(3);
                    HSSFCell spec = hssfRow.getCell(4);
                    HSSFCell remarks = hssfRow.getCell(5);
                    HSSFCell inTime = hssfRow.getCell(6);
                    HSSFCell place = hssfRow.getCell(7);
                    HSSFCell buyTime = hssfRow.getCell(8);
                    HSSFCell inCount = hssfRow.getCell(9);
                    HSSFCell inPrice = hssfRow.getCell(10);
                    HSSFCell inRemarks = hssfRow.getCell(11);


                    //消耗品编号
//                    if("".equals(getValue(code))){
//                        errMsg = errMsg + "第"+rowNum+"行消耗品编号不能为空!";
//                    }else{
                    if(!"".equals(getValue(code))){
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
                            errMsg = errMsg + "第"+rowNum+"行消耗品编号格式不正确!";
                        }else{
                            consumablesSaveReq.setGoodCode(codeStr);
                        }
                    }

                    //校验消耗品名称是否重复，如果已经存在则不能导入
                    if("".equals(getValue(name))){
                        errMsg = errMsg + "第"+rowNum+"行消耗品名称不能为空!";
                    }else {
                        if(getValue(name).length() > 50){
                            errMsg = errMsg + "第"+rowNum+"行消耗品名称长度过长!";
                        }else {

                            ConsumablesReq reqq = new ConsumablesReq();
                            reqq.setGoodName(getValue(name));
                            ConsumablesResp result = intfzWebConsumablesService.findConsumables(reqq);
                            if (null != result) {
                                errMsg = errMsg + "第" + rowNum + "行消耗品名称" + getValue(name) + "重复! ";
                            }else{
                                consumablesSaveReq.setGoodName(getValue(name));
                            }
                        }
                    }

                    //校验消耗品类别是否存在
                    if("".equals(getValue(type))){
                        errMsg = errMsg + "第"+rowNum+"行消耗品类别不能为空!";
                    }else {
                        if(getValue(type).length() > 50){
                            errMsg = errMsg + "第"+rowNum+"行消耗品类别长度过长!";
                        }else {

                            ConsumablesTypeReq reqqq = new ConsumablesTypeReq();
                            reqqq.setCtName(getValue(type));
                            ConsumablesTypeResp typeResult = consumablesTypeService.queryGoodType(reqqq);
                            if (null != typeResult) {
                                consumablesSaveReq.setGoodType(typeResult.getId());
                            } else {
                                errMsg = errMsg + "第" + rowNum + "行消耗品类别" + getValue(type) + "不存在! ";
                            }
                        }
                    }


                    //单位
                    if("".equals(getValue(unit))){
                        errMsg = errMsg + "第"+rowNum+"行单位不能为空!";
                    }else {
                        if(getValue(unit).length() > 30){
                            errMsg = errMsg + "第"+rowNum+"行单位长度过长!";
                        }else {
                            consumablesSaveReq.setGoodUnit(getValue(unit));
                        }
                    }


                    //放置地
                    if("".equals(getValue(place))){
                        errMsg = errMsg + "第"+rowNum+"行放置地不能为空!";
                    }else {
                        if(getValue(place).length() > 50){
                            errMsg = errMsg + "第"+rowNum+"行放置地长度过长!";
                        }else{
                            //放置地
                            AssetPlaceQueryOneParam queryParam = new AssetPlaceQueryOneParam();
                            queryParam.setName(getValue(place));
                            AssetPlaceQueryPageResult placeResult = assetBasicConfigService.findAssetPlaceByParams(queryParam);
                            if (null == placeResult) {
                                errMsg = errMsg + "第" + rowNum + "行放置地" + getValue(place) + "不存在! ";
                            } else {
                                consumablesSaveReq.setPutinPlace(placeResult.getId());
                            }
                        }
                    }

                    //入库数量
                    if("".equals(getValue(inCount))){
                        errMsg = errMsg + "第"+rowNum+"行入库数量不能为空!";
                    }else{
                        if(getValue(inCount).length() > 5){
                            errMsg = errMsg + "第"+rowNum+"行入库数量不能超过5位数字!";
                        }
                        //入库数量
                        Pattern pattern=Pattern.compile("^\\d*\\.?\\d{0,1}$"); // 判断小数点后1位的数字的正则表达式
                        Matcher match=pattern.matcher(getValue(inCount));
                        if(match.matches()==false){
                            errMsg = errMsg + "第"+rowNum+"行入库数量格式不正确只能输入整数或1位小数!";
                        }else{
                            consumablesSaveReq.setInCount(Double.parseDouble(getValue(inCount)));
                        }
                    }

                    //单价
                    if("".equals(getValue(inPrice))){
                        errMsg = errMsg + "第"+rowNum+"行单价不能为空!";
                    }else{
                        Pattern pattern2=Pattern.compile("^\\d*\\.?\\d{0,2}$"); // 判断小数点后2位的数字的正则表达式
                        Matcher match2=pattern2.matcher(getValue(inPrice));
                        if(match2.matches()==false){
                            errMsg = errMsg + "第"+rowNum+"行单价格式不正确只能输入整数或2位小数!";
                        }else{
                            consumablesSaveReq.setInPrice(BigDecimal.valueOf(Double.parseDouble(getValue(inPrice))));
                        }
                    }

                    if(null != spec) {
                        spec.setCellType(spec.CELL_TYPE_STRING);
                        if(!"".equals(getValue(spec))){
                            if(getValue(spec).length() > 60){
                                errMsg = errMsg + "第"+rowNum+"行规格型号过长!";
                            }
                        }
                    }

                    consumablesSaveReq.setGoodSpec(getValue(spec));


                    if(!"".equals(getValue(remarks))){
                        if(getValue(remarks).length() > 200){
                            errMsg = errMsg + "第"+rowNum+"行物品备注过长!";
                        }
                    }
                    consumablesSaveReq.setRemarks(getValue(remarks));

                    //入库日期
                    if("".equals(getValue(inTime))){
                        errMsg = errMsg + "第"+rowNum+"行入库日期不能为空!";
                    }else{
                        try {
                            consumablesSaveReq.setInTime(sdf.parse(getValue(inTime)).getTime());
                        }catch (Exception e){
                            errMsg = errMsg + "第"+rowNum+"行入库日期格式有误!";
                        }
                    }

                    //购入日期
                    if("".equals(getValue(buyTime))){
                        errMsg = errMsg + "第"+rowNum+"行购入日期不能为空!";
                    }else{
                        try {
                            consumablesSaveReq.setBuyTime(sdf.parse(getValue(buyTime)).getTime());
                        }catch (Exception e){
                            errMsg = errMsg + "第"+rowNum+"行购入日期格式有误!";
                        }
                    }

                    if(!"".equals(getValue(inRemarks))){
                        if(getValue(inRemarks).length() > 200){
                            errMsg = errMsg + "第"+rowNum+"行入库备注过长!";
                        }
                    }
                    consumablesSaveReq.setDRemark(getValue(inRemarks));

                    if (!"".equals(errMsg)) {
                        continue;
                    }
					 list.add(consumablesSaveReq);
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



	public void importConsumables(List<ConsumablesSaveReq> list, User user){
		for(ConsumablesSaveReq req:list){
			req.setUserId(user.getId());
            CharChangeUtils.CharChange(req);//替换英文字符
			intfzWebConsumablesService.saveConsumables(req);
		}
	}


	/**
	 *  消耗品导出
	 * @param fileName
	 * @return
	 */
	public String consumablesExportModel(String fileName){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        String now = sdf.format(new Date());
        DecimalFormat df = new DecimalFormat("0.00");
        String url = "";
        // 创建一个新的Excel
        HSSFWorkbook workBook = new HSSFWorkbook();
        // 创建sheet页
        HSSFSheet sheet = workBook.createSheet();
        // 设置表名
        workBook.setSheetName(0, "消耗品导出" + now);
        // 第0行 列头
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell = null;
        HSSFCellStyle style = ExportExcel.getStyle(workBook);
        ExportExcel.myCreateCell(0, "*物品编号", row, cell, style);
        ExportExcel.myCreateCell(1, "*物品名称", row, cell, style);
        ExportExcel.myCreateCell(2, "*物品类别", row, cell, style);
        ExportExcel.myCreateCell(3, "*单位", row, cell, style);
        ExportExcel.myCreateCell(4, "规格型号", row, cell, style);
        ExportExcel.myCreateCell(5, "物品备注", row, cell, style);
        ExportExcel.myCreateCell(6, "*入库日期", row, cell, style);
        ExportExcel.myCreateCell(7, "*放置地", row, cell, style);
        ExportExcel.myCreateCell(8, "*购入日期", row, cell, style);
        ExportExcel.myCreateCell(9, "*入库数量", row, cell, style);
        ExportExcel.myCreateCell(10, "*单价", row, cell, style);
        ExportExcel.myCreateCell(11, "入库备注", row, cell, style);

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
