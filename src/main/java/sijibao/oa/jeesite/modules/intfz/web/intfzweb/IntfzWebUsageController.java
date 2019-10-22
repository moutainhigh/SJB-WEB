package sijibao.oa.jeesite.modules.intfz.web.intfzweb;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sijibao.base.common.api.response.PagerResponse;
import com.sijibao.oa.common.utils.DateUtils;
import com.sijibao.oa.common.utils.WebUtils;
import com.sijibao.oa.common.web.BaseController;
import com.sijibao.oa.modules.intfz.bean.BaseResp;
import com.sijibao.oa.modules.intfz.enums.IntfzRs;
import com.sijibao.oa.modules.intfz.request.usage.ExportUsageWeekOrDayReq;
import com.sijibao.oa.modules.intfz.request.usage.PagedQueryUsageDetailWebReq;
import com.sijibao.oa.modules.intfz.request.usage.PagedQueryUsageWebReq;
import com.sijibao.oa.modules.intfz.response.common.PageResponse;
import com.sijibao.oa.modules.intfz.response.usage.DateEntity;
import com.sijibao.oa.modules.intfz.response.usage.PagedQueryUsageDetailWebResp;
import com.sijibao.oa.modules.intfz.response.usage.PagedQueryUsageWebResp;
import com.sijibao.oa.modules.intfz.service.common.IntfzCommonService;
import com.sijibao.oa.modules.intfz.service.report.IntfzUsageExportService;
import com.sijibao.oa.sys.api.IntfzWebUsageService;
import com.sijibao.oa.sys.api.request.usage.ExportUsageWeekOrDayParam;
import com.sijibao.oa.sys.api.request.usage.PagedQueryUsageDetailWebParam;
import com.sijibao.oa.sys.api.request.usage.PagedQueryUsageWebParam;
import com.sijibao.oa.sys.api.response.usage.ExportUsageDetailResult;
import com.sijibao.oa.sys.api.response.usage.ExportUsageWeekResult;
import com.sijibao.oa.sys.api.response.usage.PagedQueryUsageDetailWebResult;
import com.sijibao.oa.sys.api.response.usage.PagedQueryUsageWebResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author Jianghao Zhang
 */
@Controller
@RequestMapping(value = "${frontPath}/usage")
@Api(value = "WEB功能使用情况服务", tags = "WEB功能使用情况服务")
public class IntfzWebUsageController extends BaseController {

    @Autowired
    private IntfzWebUsageService intfzWebUsageService;
    @Autowired
    private IntfzCommonService intfzCommonService;

    /**
     * 功能使用情况列表（分页查询）
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "queryUsageList")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "功能使用情况列表（分页查询）")
    public BaseResp<PageResponse> queryUsageList(
			@RequestBody PagedQueryUsageWebReq req,
			@ApiParam(value = "登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) {
        WebUtils.initPre(request, response);

        PagedQueryUsageWebParam params = change(req, PagedQueryUsageWebParam.class);

        Date date;
        //如果未传时间参数，默认查询当天所在周数据
        if (req.getDate() == 0) {
            date = new Date();
        } else {
            date = new Date(req.getDate());
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int num;
        if (cal.get(Calendar.DAY_OF_WEEK) == 1) {
            num = -6;
        } else {
            num = 2 - cal.get(Calendar.DAY_OF_WEEK);
        }
        cal.add(Calendar.DAY_OF_WEEK, num);
        String mondayStr = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
        cal.add(Calendar.DAY_OF_WEEK, 1);
        String tuesdayStr = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
        cal.add(Calendar.DAY_OF_WEEK, 1);
        String wednesdayStr = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
        cal.add(Calendar.DAY_OF_WEEK, 1);
        String thursdayStr = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
        cal.add(Calendar.DAY_OF_WEEK, 1);
        String fridayStr = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
        cal.add(Calendar.DAY_OF_WEEK, 1);
        String saturdayStr = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
        cal.add(Calendar.DAY_OF_WEEK, 1);
        String sundayStr = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());

        params.setMondayStr(mondayStr);
        params.setTuesdayStr(tuesdayStr);
        params.setWednesdayStr(wednesdayStr);
        params.setThursdayStr(thursdayStr);
        params.setFridayStr(fridayStr);
        params.setSaturdayStr(saturdayStr);
        params.setSundayStr(sundayStr);

        PagerResponse<PagedQueryUsageWebResult> page = intfzWebUsageService.queryUsageList(params);

        DateEntity de = new DateEntity();
        de.setMondayStr(mondayStr);
        de.setTuesdayStr(tuesdayStr);
        de.setWednesdayStr(wednesdayStr);
        de.setThursdayStr(thursdayStr);
        de.setFridayStr(fridayStr);
        de.setSaturdayStr(saturdayStr);
        de.setSundayStr(sundayStr);

        List<PagedQueryUsageWebResp> list = new LinkedList<>();
        List<PagedQueryUsageWebResult> resultList = page.getList();
        for (PagedQueryUsageWebResult result : resultList) {
            list.add(change(result, PagedQueryUsageWebResp.class));
        }

        @SuppressWarnings("unchecked")
        PageResponse p = new PageResponse(list, req.getPageNo(), page.getCount(), de);
        return new BaseResp<>(IntfzRs.SUCCESS, "ok", p);
    }

    /**
     * 功能使用情况明细列表（分页查询）
     */
    @RequestMapping(value = "queryUsageDetailList")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "功能使用情况明细列表（分页查询）")
    public BaseResp<PageResponse<List<PagedQueryUsageDetailWebResp>>> queryUsageDetailList(
			@RequestBody PagedQueryUsageDetailWebReq req,
			@ApiParam(value = "登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) {
        WebUtils.initPre(request, response);

        PagedQueryUsageDetailWebParam params = change(req, PagedQueryUsageDetailWebParam.class);
        PagerResponse<PagedQueryUsageDetailWebResult> page = intfzWebUsageService.queryUsageDetailList(params);

        List<PagedQueryUsageDetailWebResp> list = new LinkedList<>();
        for (PagedQueryUsageDetailWebResult result : page.getList()) {
            list.add(change(result, PagedQueryUsageDetailWebResp.class));
        }

        return new BaseResp<>(IntfzRs.SUCCESS, "ok", new PageResponse<>(list, req.getPageNo(), page.getCount()));
    }

    /**
     * 导出功能使用情况周报表或具体报表
     */
    @RequestMapping(value = "exportUsageWeekOrDetail")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "导出功能使用情况周报表或具体报表")
    public BaseResp<String> exportUsageWeekOrDetail(@RequestBody ExportUsageWeekOrDayReq req,
													@ApiParam(value = "登录成功时返回的sessionid") @RequestHeader String sessionid,
													HttpServletRequest request, HttpServletResponse response) {
        WebUtils.initPre(request, response);


        Date date;
        //如果未传时间参数，默认查询当天所在周数据
        if (req.getDate() == 0) {
            date = new Date();
        } else {
            date = new Date(req.getDate());
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int num;
        if (cal.get(Calendar.DAY_OF_WEEK) == 1) {
            num = -6;
        } else {
            num = 2 - cal.get(Calendar.DAY_OF_WEEK);
        }
        cal.add(Calendar.DAY_OF_WEEK, num);
        String mondayStr = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
        cal.add(Calendar.DAY_OF_WEEK, 1);
        String tuesdayStr = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
        cal.add(Calendar.DAY_OF_WEEK, 1);
        String wednesdayStr = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
        cal.add(Calendar.DAY_OF_WEEK, 1);
        String thursdayStr = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
        cal.add(Calendar.DAY_OF_WEEK, 1);
        String fridayStr = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
        cal.add(Calendar.DAY_OF_WEEK, 1);
        String saturdayStr = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
        cal.add(Calendar.DAY_OF_WEEK, 1);
        String sundayStr = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());


        String fileName = "";
        String url = "";
//        DateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        if ("1".equals(req.getExportContent())) {
            fileName = "功能使用情况周报表" + DateUtils.formatDateFromUnix(new Date().getTime(), DateUtils.YYYYMMDD) + ".xls";

            ExportUsageWeekOrDayParam params = change(req, ExportUsageWeekOrDayParam.class);

            params.setMondayStr(mondayStr);
            params.setTuesdayStr(tuesdayStr);
            params.setWednesdayStr(wednesdayStr);
            params.setThursdayStr(thursdayStr);
            params.setFridayStr(fridayStr);
            params.setSaturdayStr(saturdayStr);
            params.setSundayStr(sundayStr);

            List<ExportUsageWeekResult> list = intfzWebUsageService.exportUsageWeek(params);

            url = IntfzUsageExportService.exportUsageWeek(params, list, fileName); //获取附件url
        } else if ("2".equals(req.getExportContent())) {
            fileName = "功能使用情况具体报表" + DateUtils.formatDateFromUnix(new Date().getTime(), DateUtils.YYYYMMDD) + ".xls";

            ExportUsageWeekOrDayParam params = change(req, ExportUsageWeekOrDayParam.class);
            params.setMondayStr(mondayStr);
            params.setSundayStr(sundayStr);
            List<ExportUsageDetailResult> list = intfzWebUsageService.exportUsageDetail(params);

            url = IntfzUsageExportService.exportUsageDetail(list, fileName); //获取附件url
        }

        String downLoadUrl = intfzCommonService.setFileDownLoadUrl(url, fileName);

        return new BaseResp<>(IntfzRs.SUCCESS, "", downLoadUrl);
    }
}
