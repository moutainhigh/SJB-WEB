package sijibao.oa.jeesite.modules.intfz.web.intfzweb;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.curator.shaded.com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.sijibao.base.common.api.response.PagerResponse;
import com.sijibao.oa.asset.api.IntfzWebConsumablesService;
import com.sijibao.oa.asset.api.exception.ServiceException;
import com.sijibao.oa.asset.api.request.consumables.*;
import com.sijibao.oa.asset.api.response.assettype.consumables.ConsumablesDetailResp;
import com.sijibao.oa.asset.api.response.assettype.consumables.ConsumablesPlaceResp;
import com.sijibao.oa.asset.api.response.assettype.consumables.ConsumablesResp;
import com.sijibao.oa.asset.api.response.assettype.consumables.ConsumablesTypeResp;
import com.sijibao.oa.common.utils.DateUtils;
import com.sijibao.oa.common.utils.WebUtils;
import com.sijibao.oa.common.web.BaseController;
import com.sijibao.oa.modules.intfz.bean.BaseResp;
import com.sijibao.oa.modules.intfz.enums.IntfzRs;
import com.sijibao.oa.modules.intfz.request.common.HandleReq;
import com.sijibao.oa.modules.intfz.request.consumables.*;
import com.sijibao.oa.modules.intfz.response.common.PageResponse;
import com.sijibao.oa.modules.intfz.response.consumables.MainConsumablesDetailResp;
import com.sijibao.oa.modules.intfz.response.consumables.MainConsumablesPlaceResp;
import com.sijibao.oa.modules.intfz.response.consumables.MainConsumablesResp;
import com.sijibao.oa.modules.intfz.response.consumables.MainConsumablesTypeResp;
import com.sijibao.oa.modules.intfz.service.common.IntfzCommonService;
import com.sijibao.oa.modules.intfz.service.consumables.IntfzConsumablesService;
import com.sijibao.oa.modules.intfz.utils.CharChangeUtils;
import com.sijibao.oa.modules.sys.entity.User;
import com.sijibao.oa.modules.sys.utils.UserUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 消耗品管理服务controller
 * @author wanxb
 *c
 */
@Controller
@RequestMapping(value = "${frontPath}/consumables")
@Api(value="WEB消耗品管理服务",tags="WEB消耗品管理服务")
public class IntfzWebConsumablesController extends BaseController {
	
	@Autowired
	private IntfzWebConsumablesService intfzWebConsumablesService;
    @Autowired
    private IntfzCommonService intfzCommonService;
    @Autowired
    private IntfzConsumablesService intfzConsumablesService;

	/**
	 * WEB消耗品管理服务-列表查询(带分页)
	 * @param req
	 * @param sessionid
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "findPageConsumables")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "WEB消耗品管理服务-列表查询(带分页)")
	public BaseResp<PageResponse<List<MainConsumablesResp>>> findPageConsumables(
			@RequestBody MainConsumablesReq req,
			@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		ConsumablesReq change = change(req, ConsumablesReq.class);
		change.setPageNo(req.getPageNo());
		change.setPageSize(req.getPageSize());
        String[] type = req.getGoodType();
		if(type != null && type.length >0){
		    change.setGoodType(type[type.length-1]);
        }
		PagerResponse<ConsumablesResp> page = intfzWebConsumablesService.findPageConsumables(change);
		ArrayList<MainConsumablesResp> list = Lists.newArrayList();
		for (ConsumablesResp ss : page.getList()) {
			MainConsumablesResp s = change(ss, MainConsumablesResp.class);
			List<MainConsumablesPlaceResp> pList = Lists.newArrayList();
			for(ConsumablesPlaceResp p:ss.getPlaces()){
				MainConsumablesPlaceResp pp = change(p,MainConsumablesPlaceResp.class);
				pList.add(pp);
			}
			s.setPlaces(pList);
			list.add(s);
		}


		return new BaseResp<PageResponse<List<MainConsumablesResp>>>(IntfzRs.SUCCESS, "ok",
				new PageResponse<List<MainConsumablesResp>>(list, req.getPageNo(), page.getCount()));
	}

    /**
     * WEB消耗品管理服务-提交(保存)
     * @param req
     * @param sessionid
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
	@RequestMapping(value = "saveConsumables")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "WEB消耗品管理服务-提交(保存)")
	public BaseResp<String> saveConsumables(
			@RequestBody MainConsumablesSaveReq req,
			@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		User user = UserUtils.getUser(sessionid);
        CharChangeUtils.CharChange(req);//替换英文字符
        req.setProducSide("web");
		ConsumablesSaveReq change = change(req, ConsumablesSaveReq.class);
		change.setUserId(user.getId());
        String[] type = req.getGoodType();
        if(type != null && type.length >0){
            change.setGoodType(type[type.length-1]);
        }
        try {
            intfzWebConsumablesService.saveConsumables(change);
        } catch (ServiceException e) {
            return new BaseResp<String>(IntfzRs.ERROR, e.getMessage(), "");
        }
		return new BaseResp<String>(IntfzRs.SUCCESS,"保存成功!",null);
	}


    /**
     * WEB消耗品管理服务-详情
     * @param req
     * @param sessionid
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
	@RequestMapping(value = "detailConsumables")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "WEB消耗品管理服务-详情")
	public BaseResp<MainConsumablesDetailResp> detailConsumables(
			@RequestBody HandleReq req,
			@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
        ConsumablesDetailResp resp = intfzWebConsumablesService.detailConsumables(req.getId());
        MainConsumablesDetailResp change = change(resp, MainConsumablesDetailResp.class);
		return new BaseResp<MainConsumablesDetailResp>(IntfzRs.SUCCESS,"",change);
	}

    @RequestMapping(value = "deleteConsumables")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "WEB消耗品管理服务-删除")
    public BaseResp<String> deleteConsumables(
			@RequestBody HandleReq req,
			@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
        WebUtils.initPre(request, response);

        try {
            intfzWebConsumablesService.deleteConsumables(req.getId());
        } catch (ServiceException e) {
            return new BaseResp<String>(IntfzRs.ERROR, e.getMessage(), "");
        }

        return new BaseResp<String>(IntfzRs.SUCCESS,"删除成功！","");
    }



    @RequestMapping(value = "putinConsumables")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "WEB消耗品管理服务-入库信息(保存)")
    public BaseResp<String> putinConsumables(
			@RequestBody MainConsumablesPutinSaveReq req,
			@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
        WebUtils.initPre(request, response);
        User user = UserUtils.getUser(sessionid);

        ConsumablesPutinSaveReq change = change(req, ConsumablesPutinSaveReq.class);
        change.setUserId(user.getId());
        List<ConsumablesPutinDetailSaveReq> list = Lists.newArrayList();
        for(MainConsumablesPutinDetailSaveReq de:req.getDetail()){
            ConsumablesPutinDetailSaveReq li = change(de,ConsumablesPutinDetailSaveReq.class);
            list.add(li);
        }
        change.setDetail(list);
        try {
            intfzWebConsumablesService.putinConsumables(change);
        } catch (ServiceException e) {
            return new BaseResp<String>(IntfzRs.ERROR, e.getMessage(), "");
        }
        return new BaseResp<String>(IntfzRs.SUCCESS,"保存成功!",null);
    }

    @RequestMapping(value = "putoutConsumables")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "WEB消耗品管理服务-出库信息(保存)")
    public BaseResp<String> putoutConsumables(
			@RequestBody MainConsumablesPutoutSaveReq req,
			@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
        WebUtils.initPre(request, response);
        User user = UserUtils.getUser(sessionid);
        CharChangeUtils.CharChange(req);//替换英文字符
        ConsumablesPutoutSaveReq change = change(req, ConsumablesPutoutSaveReq.class);
        change.setUserId(user.getId());
        List<ConsumablesPutoutDetailSaveReq> list = Lists.newArrayList();
        for(MainConsumablesPutoutDetailSaveReq de:req.getDetail()){
            ConsumablesPutoutDetailSaveReq li = change(de,ConsumablesPutoutDetailSaveReq.class);
            list.add(li);
        }
        change.setDetail(list);
        try {
            intfzWebConsumablesService.putoutConsumables(change);
        } catch (ServiceException e) {
            return new BaseResp<String>(IntfzRs.ERROR, e.getMessage(), "");
        }
        return new BaseResp<String>(IntfzRs.SUCCESS,"保存成功!",null);
    }


    @RequestMapping(value = "offConsumables")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "WEB消耗品管理服务-核销信息(保存)")
    public BaseResp<String> offConsumables(
			@RequestBody MainConsumablesOffSaveReq req,
			@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
        WebUtils.initPre(request, response);
        User user = UserUtils.getUser(sessionid);

        ConsumablesOffSaveReq change = change(req, ConsumablesOffSaveReq.class);
        change.setUserId(user.getId());
        List<ConsumablesOffDetailSaveReq> list = Lists.newArrayList();
        for(MainConsumablesOffDetailSaveReq de:req.getDetail()){
            ConsumablesOffDetailSaveReq li = change(de,ConsumablesOffDetailSaveReq.class);
            list.add(li);
        }
        change.setDetail(list);
        try {
            intfzWebConsumablesService.offConsumables(change);
        } catch (ServiceException e) {
            return new BaseResp<String>(IntfzRs.ERROR, e.getMessage(), "");
        }
        return new BaseResp<String>(IntfzRs.SUCCESS,"保存成功!",null);
    }


    /**
     * 不带分页的查询
     * @param sessionid
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "queryGoodType")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "WEB消耗品管理服务-查询物品类型")
    BaseResp<List<MainConsumablesTypeResp>> queryGoodType(
			@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
        WebUtils.initPre(request, response);
        List<ConsumablesTypeResp> list = intfzWebConsumablesService.queryGoodType();
        List<MainConsumablesTypeResp> nlist = Lists.newArrayList();
        for (ConsumablesTypeResp cc : list) {
            nlist.add(change(cc, MainConsumablesTypeResp.class));
        }
        return new BaseResp<List<MainConsumablesTypeResp>>(IntfzRs.SUCCESS, "ok", nlist);
    }


    /**
     * 消耗品导出
     * @param req
     * @param sessionid
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "consumablesExport")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "消耗品导出")
    public BaseResp<String> consumablesExport(@RequestBody MainConsumablesReq req,
											  @ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
											  HttpServletRequest request, HttpServletResponse response) throws Exception{


        String fileName = "消耗品导出" + DateUtils.formatDateFromUnix(new Date().getTime(), DateUtils.YYYY_MM_DD) +".xls";
        String url = intfzConsumablesService.consumablesExport(req,fileName); //获取附件url
        String downLoadUrl = intfzCommonService.setFileDownLoadUrl(url, fileName);

        return new BaseResp<String>(IntfzRs.SUCCESS,"",downLoadUrl);
    }


    /**
     * 消耗品导入
     *
     * @param file
     * @param sessionid
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "consumablesImport")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "消耗品导入")
    public BaseResp<String> consumablesImport(@RequestBody MultipartFile file,
											  @ApiParam(value = "登录成功时返回的sessionid") @RequestHeader String sessionid,
											  HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (file == null) {
            return new BaseResp<String>(IntfzRs.ERROR, "请重新选择文件", null);
        }
        Map result = intfzConsumablesService.readXls(file); //获取附件url
        String errMsg = result.get("errMsg").toString();
        if(!"".equals(errMsg)){
            return new BaseResp<String>(IntfzRs.ERROR, errMsg, "");
        }else {
            User user = UserUtils.getUser(sessionid); //获取当前发起人信息
            List<ConsumablesSaveReq> list = (List<ConsumablesSaveReq>)result.get("list");
            intfzConsumablesService.importConsumables(list, user);
            return new BaseResp<String>(IntfzRs.SUCCESS, "消耗品导入成功", "");
        }

    }


    /**
     * 消耗品导入模板下载
     *
     * @param sessionid
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "assetImportModel")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "消耗品导入模板下载")
    public BaseResp<String> consumablesImportModel(
			@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) throws Exception{



        String fileName = "消耗品导入" + DateUtils.formatDateFromUnix(new Date().getTime(), DateUtils.YYYY_MM_DD) +".xls";
        String url = intfzConsumablesService.consumablesExportModel(fileName); //获取附件url

        String downLoadUrl = intfzCommonService.setFileDownLoadUrl(url, fileName);

        return new BaseResp<String>(IntfzRs.SUCCESS, "", downLoadUrl);
    }
}
