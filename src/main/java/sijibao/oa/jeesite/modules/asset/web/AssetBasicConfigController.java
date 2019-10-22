package sijibao.oa.jeesite.modules.asset.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sijibao.base.common.api.response.PagerResponse;
import com.sijibao.oa.asset.api.AssetBasicConfigService;
import com.sijibao.oa.asset.api.request.assetplace.AssetPlaceQueryOneParam;
import com.sijibao.oa.asset.api.request.assetplace.AssetPlaceQueryPageParam;
import com.sijibao.oa.asset.api.request.assetplace.AssetPlaceSaveOrUpdateParam;
import com.sijibao.oa.asset.api.request.assetsupplier.AssetSupplierQueryOneParam;
import com.sijibao.oa.asset.api.request.assetsupplier.AssetSupplierQueryPageParam;
import com.sijibao.oa.asset.api.request.assetsupplier.AssetSupplierSaveOrUpdateParam;
import com.sijibao.oa.asset.api.request.assettype.AssetTypeQueryOneParam;
import com.sijibao.oa.asset.api.request.assettype.AssetTypeQueryPageParam;
import com.sijibao.oa.asset.api.request.assettype.AssetTypeSaveOrUpdateParam;
import com.sijibao.oa.asset.api.response.assetplace.AssetPlaceQueryPageResult;
import com.sijibao.oa.asset.api.response.assetsupplier.AssetSupplierQueryPageResult;
import com.sijibao.oa.asset.api.response.assettype.AssetTypeQueryDetailResult;
import com.sijibao.oa.asset.api.response.assettype.AssetTypeQueryPageResult;
import com.sijibao.oa.common.config.Global;
import com.sijibao.oa.common.persistence.Page;
import com.sijibao.oa.common.utils.StringUtils;
import com.sijibao.oa.common.web.BaseController;
import com.sijibao.oa.modules.asset.entity.AssetPlace;
import com.sijibao.oa.modules.asset.entity.AssetSupplier;
import com.sijibao.oa.modules.asset.entity.AssetType;
import com.sijibao.oa.modules.oa.entity.NeedType;
import com.sijibao.oa.modules.sys.entity.User;
import com.sijibao.oa.modules.sys.utils.UserUtils;
import com.sijibao.oa.office.api.exception.ServiceException;

/**
 * 资产基础配置
 *
 * @author Jianghao Zhang
 */
@Controller
@RequestMapping(value = "${adminPath}/oa")
public class AssetBasicConfigController extends BaseController {

    @Autowired
    AssetBasicConfigService assetBasicConfigService;

    /**
     * 资产类别--列表
     */
    @RequestMapping(value = {"/assetType/list", ""})
    public String list(AssetType assetType, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<NeedType> p = new Page<>(request, response);
        AssetTypeQueryPageParam req = change(assetType, AssetTypeQueryPageParam.class);
        req.setPageNo(p.getPageNo());
        req.setPageSize(p.getPageSize());
        PagerResponse<AssetTypeQueryPageResult> resp = assetBasicConfigService.findAssetTypePage(req);
        Page<AssetType> page = new Page<>(request, response);
        ArrayList<AssetType> list = Lists.newArrayList();
        for (AssetTypeQueryPageResult s : resp.getList()) {
            AssetType ss = change(s, AssetType.class);
            list.add(ss);
        }
        page.setCount(resp.getCount());
        page.setList(list);
        model.addAttribute("page", page);
        return "modules/asset/assetTypeList";
    }

    /**
     * 资产类别--跳转表单页
     */
    @RequestMapping(value = "/assetType/form")
    public String form(AssetType assetType, Model model) {
        if (StringUtils.isNotBlank(assetType.getId())) {
            AssetTypeQueryOneParam param = new AssetTypeQueryOneParam();
            param.setId(assetType.getId());
            AssetTypeQueryDetailResult assetTypeDetail = assetBasicConfigService.findAssetTypeByParams(param);
            model.addAttribute("assetType", assetTypeDetail);
        } else {
            model.addAttribute("assetType", assetType);
        }
        return "modules/asset/assetTypeForm";
    }

    /**
     * 资产类别--树结构选择弹窗
     */
    @ResponseBody
    @RequestMapping(value = "/assetType/treeData")
    public List<Map<String, Object>> treeData(@RequestParam(required = false) String extId, @RequestParam(required = false) Model model,
											  @RequestParam(required = false) Boolean isAll) {
        List<Map<String, Object>> mapList = Lists.newArrayList();
        List<AssetTypeQueryPageResult> list = assetBasicConfigService.findAllAssetTypeList();
        for (AssetTypeQueryPageResult e : list) {
            Map<String, Object> map = Maps.newHashMap();
            map.put("id", e.getId());
            map.put("pId", e.getParentId());
            map.put("pIds", e.getParentIds());
            map.put("name", e.getName());
            mapList.add(map);
        }
        return mapList;
    }

    /**
     * 资产类别--新增/编辑
     */
    @RequestMapping(value = "/assetType/save")
    public String save(AssetType assetType, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, assetType)) {
            return form(assetType, model);
        }
        User user = UserUtils.getUser();

        AssetTypeSaveOrUpdateParam param = change(assetType, AssetTypeSaveOrUpdateParam.class);
        param.setOperateUserId(user.getId());
        Map<String, String> map;
        try {
            map = assetBasicConfigService.saveOrUpdateAssetType(param);
        } catch (ServiceException e) {
            addMessage(redirectAttributes, e.getMessage());
            return "redirect:" + Global.getAdminPath() + "/oa/assetType/form";
        }

        if ("success".equals(map.get("flag"))) {
            addMessage(redirectAttributes, map.get("msg"));
        } else if ("fail".equals(map.get("flag"))) {
            model.addAttribute("message", map.get("msg"));
            return form(assetType, model);
        }
        return "redirect:" + Global.getAdminPath() + "/oa/assetType/list";
    }

    /**
     * 资产类别--删除
     */
    @RequestMapping(value = "/assetType/delete")
    public String delete(AssetType assetType, RedirectAttributes redirectAttributes) {
        User user = UserUtils.getUser();

        Map<String, String> map;
        try {
            map = assetBasicConfigService.deleteAssetType(assetType.getId(), user.getId());
        } catch (ServiceException e) {
            addMessage(redirectAttributes, e.getMessage());
            return "redirect:" + Global.getAdminPath() + "/oa/assetType/list";
        }
        addMessage(redirectAttributes, map.get("msg"));
        return "redirect:" + Global.getAdminPath() + "/oa/assetType/list";
    }

    /**
     * 资产放置地--列表
     */
    @RequestMapping(value = {"/assetPlace/list", ""})
    public String list(AssetPlace assetPlace, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<AssetPlace> p = new Page<>(request, response);
        AssetPlaceQueryPageParam req = change(assetPlace, AssetPlaceQueryPageParam.class);
        req.setPageNo(p.getPageNo());
        req.setPageSize(p.getPageSize());
        PagerResponse<AssetPlaceQueryPageResult> resp = assetBasicConfigService.findAssetPlacePage(req);
        Page<AssetPlace> page = new Page<>(request, response);
        ArrayList<AssetPlace> list = Lists.newArrayList();
        for (AssetPlaceQueryPageResult s : resp.getList()) {
            AssetPlace ss = change(s, AssetPlace.class);
            list.add(ss);
        }
        page.setCount(resp.getCount());
        page.setList(list);
        model.addAttribute("page", page);
        return "modules/asset/assetPlaceList";
    }

    /**
     * 资产放置地--跳转表单页
     */
    @RequestMapping(value = "/assetPlace/form")
    public String form(AssetPlace assetPlace, Model model) {
        if (StringUtils.isNotBlank(assetPlace.getId())) {
            AssetPlaceQueryOneParam param = new AssetPlaceQueryOneParam();
            param.setId(assetPlace.getId());
            AssetPlaceQueryPageResult assetPlaceOne = assetBasicConfigService.findAssetPlaceByParams(param);
            model.addAttribute("assetPlace", assetPlaceOne);
        } else {
            model.addAttribute("assetPlace", assetPlace);
        }
        return "modules/asset/assetPlaceForm";
    }

    /**
     * 资产放置地--新增/编辑
     */
    @RequestMapping(value = "/assetPlace/save")
    public String save(AssetPlace assetPlace, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, assetPlace)) {
            return form(assetPlace, model);
        }
        User user = UserUtils.getUser();

        Map<String, String> resultMap;
        AssetPlaceSaveOrUpdateParam param = change(assetPlace, AssetPlaceSaveOrUpdateParam.class);
        param.setOperateUserId(user.getId());
        try {
            resultMap = assetBasicConfigService.saveOrUpdateAssetPlace(param);
        } catch (ServiceException e) {
            addMessage(redirectAttributes, e.getMessage());
            return form(assetPlace, model);
        }

        if ("success".equals(resultMap.get("flag"))) {
            addMessage(redirectAttributes, resultMap.get("msg"));
            return "redirect:" + Global.getAdminPath() + "/oa/assetPlace/list";
        } else {
            model.addAttribute("message", resultMap.get("msg"));
            return form(assetPlace, model);
        }
    }

    /**
     * 资产放置地--删除
     */
    @RequestMapping(value = "/assetPlace/delete")
    public String delete(AssetPlace assetPlace, RedirectAttributes redirectAttributes) {
        User user = UserUtils.getUser();

        Map<String, String> resultMap;
        try {
            resultMap = assetBasicConfigService.deleteAssetPlace(assetPlace.getId(), user.getId());
        } catch (ServiceException e) {
            addMessage(redirectAttributes, e.getMessage());
            return "redirect:" + Global.getAdminPath() + "/oa/assetPlace/list";
        }

        addMessage(redirectAttributes, resultMap.get("msg"));
        return "redirect:" + Global.getAdminPath() + "/oa/assetPlace/list";
    }

    /**
     * 资产供应商--列表
     */
    @RequestMapping(value = {"/assetSupplier/list", ""})
    public String list(AssetSupplier assetSupplier, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<AssetSupplier> p = new Page<>(request, response);
        AssetSupplierQueryPageParam req = change(assetSupplier, AssetSupplierQueryPageParam.class);
        req.setPageNo(p.getPageNo());
        req.setPageSize(p.getPageSize());
        PagerResponse<AssetSupplierQueryPageResult> resp = assetBasicConfigService.findAssetSupplierPage(req);
        Page<AssetSupplier> page = new Page<>(request, response);
        ArrayList<AssetSupplier> list = Lists.newArrayList();
        for (AssetSupplierQueryPageResult s : resp.getList()) {
            AssetSupplier ss = change(s, AssetSupplier.class);
            list.add(ss);
        }
        page.setCount(resp.getCount());
        page.setList(list);
        model.addAttribute("page", page);
        return "modules/asset/assetSupplierList";
    }

    /**
     * 资产供应商--跳转表单页
     */
    @RequestMapping(value = "/assetSupplier/form")
    public String form(AssetSupplier assetSupplier, Model model) {
        if (StringUtils.isNotBlank(assetSupplier.getId())) {
            AssetSupplierQueryOneParam param = new AssetSupplierQueryOneParam();
            param.setId(assetSupplier.getId());
            AssetSupplierQueryPageResult assetSupplierOne = assetBasicConfigService.findAssetSupplierByParams(param);
            model.addAttribute("assetSupplier", assetSupplierOne);
        } else {
            model.addAttribute("assetSupplier", assetSupplier);
        }
        return "modules/asset/assetSupplierForm";
    }

    /**
     * 资产供应商--新增/编辑
     */
    @RequestMapping(value = "/assetSupplier/save")
    public String save(AssetSupplier assetSupplier, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, assetSupplier)) {
            return form(assetSupplier, model);
        }
        User user = UserUtils.getUser();

        Map<String, String> resultMap = new HashMap<>();
        AssetSupplierSaveOrUpdateParam param = change(assetSupplier, AssetSupplierSaveOrUpdateParam.class);
        param.setOperateUserId(user.getId());
        try {
            resultMap = assetBasicConfigService.saveOrUpdateAssetSupplier(param);
        } catch (ServiceException e) {
            addMessage(redirectAttributes, e.getMessage());
        }

        if ("success".equals(resultMap.get("flag"))) {
            addMessage(redirectAttributes, resultMap.get("msg"));
            return "redirect:" + Global.getAdminPath() + "/oa/assetSupplier/list";
        } else {
            model.addAttribute("message", resultMap.get("msg"));
            return form(assetSupplier, model);
        }
    }

    /**
     * 资产供应商--删除
     */
    @RequestMapping(value = "/assetSupplier/delete")
    public String delete(AssetSupplier assetSupplier, RedirectAttributes redirectAttributes) {
        User user = UserUtils.getUser();

        Map<String, String> resultMap;
        try {
            resultMap = assetBasicConfigService.deleteAssetSupplier(assetSupplier.getId(), user.getId());
        } catch (ServiceException e) {
            addMessage(redirectAttributes, e.getMessage());
            return "redirect:" + Global.getAdminPath() + "/oa/assetSupplier/list";
        }
        addMessage(redirectAttributes, resultMap.get("msg"));
        return "redirect:" + Global.getAdminPath() + "/oa/assetSupplier/list";
    }
}
