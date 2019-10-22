package sijibao.oa.jeesite.modules.intfz.web.intfzweb;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.sijibao.base.common.api.response.PagerResponse;
import com.sijibao.oa.asset.api.AssetBasicConfigService;
import com.sijibao.oa.asset.api.AssetService;
import com.sijibao.oa.asset.api.request.asset.*;
import com.sijibao.oa.asset.api.response.asset.AssetQueryDetailResult;
import com.sijibao.oa.asset.api.response.asset.AssetQueryPageResult;
import com.sijibao.oa.asset.api.response.assetplace.AssetPlaceQueryPageResult;
import com.sijibao.oa.asset.api.response.assetsupplier.AssetSupplierQueryPageResult;
import com.sijibao.oa.asset.api.response.assettype.AssetTypeQueryPageResult;
import com.sijibao.oa.common.service.ServiceException;
import com.sijibao.oa.common.utils.DateUtils;
import com.sijibao.oa.common.utils.StringUtils;
import com.sijibao.oa.common.utils.WebUtils;
import com.sijibao.oa.common.web.BaseController;
import com.sijibao.oa.modules.asset.entity.Asset;
import com.sijibao.oa.modules.intfz.bean.BaseResp;
import com.sijibao.oa.modules.intfz.enums.IntfzRs;
import com.sijibao.oa.modules.intfz.request.asset.*;
import com.sijibao.oa.modules.intfz.response.asset.*;
import com.sijibao.oa.modules.intfz.response.common.PageResponse;
import com.sijibao.oa.modules.intfz.service.asset.IntfzAssetService;
import com.sijibao.oa.modules.intfz.service.common.IntfzCommonService;
import com.sijibao.oa.modules.intfz.utils.CharChangeUtils;
import com.sijibao.oa.modules.sys.entity.User;
import com.sijibao.oa.modules.sys.utils.UserUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;


/**
 * 固定资产管理controller
 *
 * @author Jianghao Zhang
 */
@Controller
@RequestMapping(value = "${frontPath}/asset")
@Api(value = "WEB固定资产管理服务", tags = "WEB固定资产管理服务")
public class IntfzWebAssetController extends BaseController {

    @Autowired
    private AssetBasicConfigService assetBasicConfigService;
    @Autowired
    private AssetService assetService;
    @Autowired
    private IntfzAssetService intfzAssetService;
    @Autowired
    private IntfzCommonService intfzCommonService;

    /**
     * 查看固定资产列表（分页查询）
     */
    @RequestMapping(value = "queryAssetList")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "查看固定资产列表（分页查询）")
    public BaseResp<PageResponse<List<AssetQueryPageResp>>> queryAssetList(
			@RequestBody AssetQueryPageReq req,
			@ApiParam(value = "登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) {
        WebUtils.initPre(request, response);

        List<AssetQueryPageResp> list = new LinkedList<>();
        AssetQueryPageParam params = change(req, AssetQueryPageParam.class);
        //前端返回的assetTypeId字段比较特殊，是一个ID list，从顶级到末级依次排列，我们取末级的就行
        List<String> assetTypeIdList = req.getAssetTypeIdList();
        if (assetTypeIdList != null && assetTypeIdList.size() > 0) {
            params.setAssetTypeId(assetTypeIdList.get(assetTypeIdList.size() - 1));
        }
        if (req.getEntryTimeBegin() != 0) {
            params.setEntryTimeBegin(new Date(req.getEntryTimeBegin()));
        }
        if (req.getEntryTimeEnd() != 0) {
            params.setEntryTimeEnd(new Date(req.getEntryTimeEnd()));
        }
        PagerResponse<AssetQueryPageResult> page = assetService.queryAssetPage(params);
        List<AssetQueryPageResult> resultList = page.getList();
        for (AssetQueryPageResult result : resultList) {
            AssetQueryPageResp assetQueryPageResp = change(result, AssetQueryPageResp.class);
            assetQueryPageResp.setEntryTime(result.getEntryTime().getTime());
            assetQueryPageResp.setBuyTime(result.getBuyTime().getTime());

            assetQueryPageResp.setDueTime(result.getDueTime() == null ? 0 : result.getDueTime().getTime());
            assetQueryPageResp.setGuaranteeBeginTime(result.getGuaranteeBeginTime() == null ? 0 : result.getGuaranteeBeginTime().getTime());
            assetQueryPageResp.setGuaranteeEndTime(result.getGuaranteeEndTime() == null ? 0 : result.getGuaranteeEndTime().getTime());
            assetQueryPageResp.setPickDate(result.getPickDate() == null ? 0 : result.getPickDate().getTime());

            list.add(assetQueryPageResp);
        }

        return new BaseResp<>(IntfzRs.SUCCESS, "ok", new PageResponse<>(list, req.getPageNo(), page.getCount()));
    }

    /**
     * 查看固定资产详情
     */
    @RequestMapping(value = "queryAssetDetail")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "查看固定资产详情")
    public BaseResp<AssetQueryDetailResp> queryAssetDetail(
			@RequestBody AssetQueryDetailReq req,
			@ApiParam(value = "登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) {
        WebUtils.initPre(request, response);
//        CharChange(req);//转换字符
        AssetQueryDetailResult detail = assetService.queryAssetDetail(req.getId());
        AssetQueryDetailResp resp = change(detail, AssetQueryDetailResp.class);
        resp.setEntryTime(detail.getEntryTime().getTime());
        resp.setBuyTime(detail.getBuyTime().getTime());

        resp.setDueTime(detail.getDueTime() == null ? 0 : detail.getDueTime().getTime());
        resp.setGuaranteeBeginTime(detail.getGuaranteeBeginTime() == null ? 0 : detail.getGuaranteeBeginTime().getTime());
        resp.setGuaranteeEndTime(detail.getGuaranteeEndTime() == null ? 0 : detail.getGuaranteeEndTime().getTime());

        return new BaseResp<>(IntfzRs.SUCCESS, "ok", resp);
    }


    /**
     * 删除固定资产
     */
    @RequestMapping(value = "deleteAssetById")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "删除固定资产")
    public BaseResp<AssetQueryDetailResp> deleteAssetById(
			@RequestBody AssetDeleteByIdReq req,
			@ApiParam(value = "登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) {
        WebUtils.initPre(request, response);
//        CharChange(req);//转换字符
        logger.info("===IntfzWebAssetController deleteAssetById[id]====== " + req.getId());
        User user = UserUtils.getUser(sessionid);

        Map<String, String> resultMap;
        try {
            AssetDeleteParam param = new AssetDeleteParam();
            param.setAssetId(req.getId());
            param.setOperateUserId(user.getId());

            resultMap = assetService.deleteAssetById(param);
        } catch (ServiceException e) {
            logger.info("===IntfzWebAssetController deleteAssetById[id]====== " + req.getId());
            return new BaseResp<>(IntfzRs.ERROR, "删除固定资产出现异常", null);
        }
        if ("fail".equals(resultMap.get("flag"))) {
            return new BaseResp<>(IntfzRs.ERROR, resultMap.get("msg"), null);
        }

        return new BaseResp<>(IntfzRs.SUCCESS, resultMap.get("msg"), null);
    }

    /**
     * 新增/编辑固定资产
     */
    @RequestMapping(value = "saveOrUpdateAsset")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "新增/编辑固定资产")
    public BaseResp<String> addAsset(
			@RequestBody AssetAddOrUpdateReq req,
			@ApiParam(value = "登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) {
        WebUtils.initPre(request, response);
        logger.info("===IntfzWebAssetController saveOrUpdateAsset[req]====== " + req.toString());
        CharChangeUtils.CharChange(req);//替换英文字符
        String msg = "新增";
        if (StringUtils.isNotBlank(req.getId())) {
            msg = "编辑";
        }
        User user = UserUtils.getUser(sessionid);

        Map<String, String> resultMap;
        req.setProducSide("web");
        try {
            AssetSaveOrUpdateParam param = change(req, AssetSaveOrUpdateParam.class);
            //前端返回的assetTypeId字段比较特殊，是一个ID list，从顶级到末级依次排列，我们取末级的就行
            List<String> assetTypeIdList = req.getAssetTypeIdList();
            if (assetTypeIdList != null && assetTypeIdList.size() > 0) {
                param.setAssetTypeId(assetTypeIdList.get(assetTypeIdList.size() - 1));
            }
            //时间戳转换Date
            if (req.getGuaranteeBeginTime() != 0) {
                param.setGuaranteeBeginTime(req.getGuaranteeBeginTime());
            }
            if (req.getGuaranteeEndTime() != 0) {
                param.setGuaranteeEndTime(req.getGuaranteeEndTime());
            }
            if (req.getDueTime() != 0) {
                param.setDueTime(req.getDueTime());
            }
            param.setOperateUserId(user.getId());
            resultMap = assetService.saveOrUpdateAsset(param);
        } catch (ServiceException e) {
            return new BaseResp<>(IntfzRs.ERROR, msg + "固定资产失败", "");
        }

        if ("fail".equals(resultMap.get("flag"))) {
            return new BaseResp<>(IntfzRs.ERROR, resultMap.get("msg"), "");
        }

        return new BaseResp<>(IntfzRs.SUCCESS, resultMap.get("msg"), "");
    }

    /**
     * 资产领用/转移
     */
    @RequestMapping(value = "pickOrTransferAsset")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "领用/转移固定资产")
    public BaseResp<String> pickOrTransferAsset(
			@RequestBody AssetPickOrTransferReq req,
			@ApiParam(value = "登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) {
        WebUtils.initPre(request, response);

        User user = UserUtils.getUser(sessionid);
        Map<String, String> resultMap;
        try {
            AssetPickOrTransferParam param = change(req, AssetPickOrTransferParam.class);
            param.setOperateUserId(user.getId());
            resultMap = assetService.pickOrTransferAsset(param);
        } catch (ServiceException e) {
            return new BaseResp<>(IntfzRs.ERROR, "领用/转移固定资产出现异常", "");
        }
        if ("fail".equals(resultMap.get("flag"))) {
            return new BaseResp<>(IntfzRs.ERROR, resultMap.get("msg"), "");
        }

        return new BaseResp<>(IntfzRs.SUCCESS, resultMap.get("msg"), "");
    }

    //--------------- 辅助查询接口 开始 ---------------

    /**
     * 查询资产类别(树)数据
     */
    @RequestMapping(value = "queryAssetTypeTree")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "查询资产类别(树)数据")
    public BaseResp<List<QueryAssetTypeTreeResp>> queryAssetTypeTree(
			@ApiParam(value = "登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) {
        WebUtils.initPre(request, response);

        List<QueryAssetTypeTreeResp> list = new LinkedList<>();
        List<AssetTypeQueryPageResult> assetTypeList = assetBasicConfigService.findAllAssetTypeList();
        for (AssetTypeQueryPageResult assetType : assetTypeList) {
            QueryAssetTypeTreeResp assetTypeTreeResp = change(assetType, QueryAssetTypeTreeResp.class);
            assetTypeTreeResp.setpId(assetType.getParentId());//前端要求返回字段名pId而不是parentId，免得他们重写树组件
            list.add(assetTypeTreeResp);
        }

        return new BaseResp<>(IntfzRs.SUCCESS, "ok", list);
    }

    /**
     * 查询资产供应商列表（不分页）
     */
    @RequestMapping(value = "queryAssetSupplierList")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "查询资产供应商列表（不分页）")
    public BaseResp<List<QueryAssetSupplierListResp>> queryAssetSupplierList(
			@ApiParam(value = "登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) {
        WebUtils.initPre(request, response);

        List<QueryAssetSupplierListResp> list = new LinkedList<>();
        List<AssetSupplierQueryPageResult> assetSupplierList = assetBasicConfigService.findAllAssetSupplierList();
        for (AssetSupplierQueryPageResult result : assetSupplierList) {
            list.add(change(result, QueryAssetSupplierListResp.class));
        }
        return new BaseResp<>(IntfzRs.SUCCESS, "ok", list);
    }

    /**
     * 查询放置地列表（不分页）
     */
    @RequestMapping(value = "queryAssetPlaceList")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "查询放置地列表（不分页）")
    public BaseResp<List<QueryAssetPlaceResp>> queryAssetPlaceList(
			@ApiParam(value = "登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) {
        WebUtils.initPre(request, response);

        List<QueryAssetPlaceResp> list = new LinkedList<>();
        List<AssetPlaceQueryPageResult> assetPlaceList = assetBasicConfigService.findAllAssetPlaceList();
        for (AssetPlaceQueryPageResult result : assetPlaceList) {
            list.add(change(result, QueryAssetPlaceResp.class));
        }

        return new BaseResp<>(IntfzRs.SUCCESS, "ok", list);
    }
    //--------------- 辅助查询接口 结束 ---------------

    /**
     * 固定资产退库
     *
     * @param req
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "saveAssetOut")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "固定资产退库")
    public BaseResp<String> saveAssetOut(@RequestBody AssetOutReq req,
										 @ApiParam(value = "登录成功时返回的sessionid") @RequestHeader String sessionid,
										 HttpServletRequest request, HttpServletResponse response) throws Exception {
        WebUtils.initPre(request, response);
        User user = UserUtils.getUser(sessionid); //获取当前申请人信息
        AssetOutSaveOrUpdateReq aReq = change(req, AssetOutSaveOrUpdateReq.class);
        aReq.setOutTime(new Date(req.getOutTime()));
        aReq.setCreateBy(user.getId());
        assetBasicConfigService.saveAssetOut(aReq);
        return new BaseResp<String>(IntfzRs.SUCCESS, "资产退库成功", "");
    }

    /**
     * 固定资产核销
     *
     * @param req
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "saveAssetWriteOff")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "固定资产核销")
    public BaseResp<String> saveAssetWriteOff(@RequestBody AssetWriteOffReq req,
											  @ApiParam(value = "登录成功时返回的sessionid") @RequestHeader String sessionid,
											  HttpServletRequest request, HttpServletResponse response) throws Exception {
        WebUtils.initPre(request, response);
        User user = UserUtils.getUser(sessionid); //获取当前申请人信息
        AssetWriteOffSaveOrUpdateReq aReq = change(req, AssetWriteOffSaveOrUpdateReq.class);
        aReq.setCleanTime(new Date(req.getCleanTime()));
        aReq.setCreateBy(user.getId());
        assetBasicConfigService.saveAssetWriteOff(aReq);
        return new BaseResp<String>(IntfzRs.SUCCESS, "资产核销成功", "");
    }

    /**
     * 批量入库
     */

    /**
     * 固定资产导出
     *
     * @param req
     * @param sessionid
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "assetExport")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "固定资产导出")
    public BaseResp<String> assetExport(@RequestBody AssetQueryPageReq req,
										@ApiParam(value = "登录成功时返回的sessionid") @RequestHeader String sessionid,
										HttpServletRequest request, HttpServletResponse response) throws Exception {

        String fileName = "固定资产导出" + DateUtils.formatDateFromUnix(new Date().getTime(), DateUtils.YYYY_MM_DD) + ".xls";
        String url = intfzAssetService.assetExport(req, fileName); //获取附件url

        String downLoadUrl = intfzCommonService.setFileDownLoadUrl(url, fileName);

        return new BaseResp<String>(IntfzRs.SUCCESS, "", downLoadUrl);
    }

    /**
     * 固定资产导入
     *
     * @param file
     * @param sessionid
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "assetImport")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "固定资产导入")
    public BaseResp<String> assetImport(@RequestBody MultipartFile file,
										@ApiParam(value = "登录成功时返回的sessionid") @RequestHeader String sessionid,
										HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (file == null) {
            return new BaseResp<String>(IntfzRs.ERROR, "请重新选择文件", null);
        }
        Map result = intfzAssetService.readXls(file); //获取附件url
        String errMsg = result.get("errMsg").toString();
        if (!"".equals(errMsg)) {
            return new BaseResp<String>(IntfzRs.ERROR, errMsg, "");
        } else {
            User user = UserUtils.getUser(sessionid); //获取当前发起人信息

            List<Asset> list = (List<Asset>) result.get("list");
            intfzAssetService.importAsset(list, user);
            return new BaseResp<String>(IntfzRs.SUCCESS, "固定资产导入成功", "");
        }
    }


    /**
     * 固定资产导入模板下载
     *
     * @param sessionid
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "assetImportModel")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "固定资产导入模板下载")
    public BaseResp<String> assetImportModel(
			@ApiParam(value = "登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

        String fileName = "固定资产导入" + DateUtils.formatDateFromUnix(new Date().getTime(), DateUtils.YYYY_MM_DD) + ".xls";
        String url = intfzAssetService.assetExportModel(fileName); //获取附件url

        String downLoadUrl = intfzCommonService.setFileDownLoadUrl(url, fileName);

        return new BaseResp<String>(IntfzRs.SUCCESS, "", downLoadUrl);
    }
}
