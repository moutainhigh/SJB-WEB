package sijibao.oa.jeesite.modules.sys.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sijibao.oa.common.utils.IdGen;
import com.sijibao.oa.common.utils.StringUtils;
import com.sijibao.oa.common.web.BaseController;
import com.sijibao.oa.message.api.IntfzMessageService;
import com.sijibao.oa.message.api.request.DingTalkMessageRequest;
import com.sijibao.oa.modules.intfz.utils.Constant;
import com.sijibao.oa.modules.sys.entity.Office;
import com.sijibao.oa.modules.sys.entity.User;
import com.sijibao.oa.modules.sys.service.OfficeService;
import com.sijibao.oa.modules.sys.service.SystemService;
import com.sijibao.oa.modules.sys.utils.DictUtils;
import com.sijibao.oa.modules.sys.utils.UserUtils;

/**
 * 机构Controller
 * @author ThinkGem
 * @version 2013-5-15
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/office")
public class OfficeController extends BaseController {

	@Autowired
	private OfficeService officeService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private IntfzMessageService intfzMessageService;

	@ModelAttribute("office")
	public Office get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return officeService.get(new Office(id));
		}else{
			return new Office();
		}
	}

	@RequiresPermissions("sys:office:view")
	@RequestMapping(value = {""})
	public String index(Office office, Model model) {
//        model.addAttribute("list", officeService.findAll());
		return "modules/sys/officeIndex";
	}

	@RequiresPermissions("sys:office:view")
	@RequestMapping(value = {"list"})
	public String list(Office office, Model model) {
	    List<Office> list = officeService.findChildOfficeList(office);//查询下一级机构列表
	    list.add(officeService.get(office.getId()));
        model.addAttribute("list", list);
		return "modules/sys/officeList";
	}

	@RequiresPermissions("sys:office:view")
	@RequestMapping(value = "form")
	public String form(Office office, Model model) {
		User user = UserUtils.getUser();
		if (office.getParent()==null || office.getParent().getId()==null){
			office.setParent(user.getOffice());
		}
		office.setParent(officeService.get(office.getParent().getId()));
		// 自动获取排序号
//		if (StringUtils.isBlank(office.getId())){
//			int size = 0;
//			List<Office> list = officeService.findAll();
//			for (int i=0; i<list.size(); i++){
//				Office e = list.get(i);
//				if (e.getParent()!=null && e.getParent().getId()!=null
//						&& e.getParent().getId().equals(office.getParent().getId())){
//					size++;
//				}
//			}
//			office.setCode(office.getParent().getCode() + StringUtils.leftPad(String.valueOf(size > 0 ? size+1 : 1), 3, "0"));
//		}

		String deputyPersonStr = office.getDeputyPerson();
		if(StringUtils.isNotBlank(deputyPersonStr)){
			String[] deputyPersonIdList = deputyPersonStr.split(",");

			StringBuilder deputyPersonNames = new StringBuilder();
			for(String id:deputyPersonIdList){
				deputyPersonNames.append(UserUtils.get(id).getName()).append(",");
			}

			office.setDeputyPersonNameStr(deputyPersonNames.toString().substring(0,deputyPersonNames.length()-1));
		}
		model.addAttribute("office", office);
		return "modules/sys/officeForm";
	}

	/**
	 * 添加/编辑机构
	 * @param office 封装表单数据的实体对象
	 * @param model mvc模型
	 * @param redirectAttributes 重定向参数
	 * @return String 重定向信息
	 */
	@RequiresPermissions("sys:office:edit")
	@RequestMapping(value = "save")
	public String save(Office office, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        User user = UserUtils.getUser();
		if (!beanValidator(model, office)){
			return form(office, model);
		}
		//判断主负责人离职
		User primary = null;
		if(office.getPrimaryPerson() != null){
			primary = UserUtils.get(office.getPrimaryPerson().getId());
		}
		if( primary!= null &&
				!"1".equals(primary.getUserStatus())){
			addMessage(model,"主负责人(" + office.getPrimaryPerson().getName() + ")已离职，请重新选择后再提交。");
			return form(office, model);
		}
		//判断副负责人离职
		if(office.getDeputyPersonIdList() != null && office.getDeputyPersonIdList().size() > 0){
			List<User> users = UserUtils.getUserByIds(office.getDeputyPersonIdList());
			StringBuilder str = new StringBuilder();
			for(User u : users){
				if(!"1".equals(u.getUserStatus())){
					str.append(u.getName()).append(",");
				}
			}
			String msg = str.toString();
			if(StringUtils.isNotBlank(msg)){
				addMessage(model,"副负责人(" + msg.substring(0,msg.length()-1) + ")已离职，请重新选择后再提交。");
				return form(office, model);
			}
		}
		List<Office> existingOffice = officeService.findList(true);//从缓存中获取所有机构的信息
		Office parentOffice = new Office();
		int sameCodeOfficeNum = 0;
		for(Office o:existingOffice){
			//校验重复机构编码，注意，当操作为编辑机构时，校验时要排除自身
			if(!o.getId().equals(office.getId()) && o.getCode().equals(office.getCode())){
				sameCodeOfficeNum++;
			}

			if(o.getId().equals(office.getParent().getId())){
				parentOffice = o;//获得父级机构对象（全部信息）
			}
		}

		//上级机构不能选择已经停用的机构
		if(StringUtils.isNotBlank(parentOffice.getId())
			&& Constant.OFFICE_DISABLE.equals(parentOffice.getUseable())){
			addMessage(model,"上级机构已停用，不可选择");
			return form(office, model);
		}

		if(parentOffice.getParentIds().split(",").length > 8){
            addMessage(model,"机构层级最多支持8级");
            return form(office, model);
        }

		if(StringUtils.isBlank(office.getCode())){
			addMessage(model,"未填写机构编码");
			return form(office, model);
		}

		if(sameCodeOfficeNum > 0){
			addMessage(model,"机构编码重复");
			return form(office, model);
		}

		if(office.getCode().length() > 15){
			addMessage(model,"机构编码长度需在1到15之间");
			return form(office, model);
		}

		/* 自动生成机构编码，暂时注释掉，千万别删除*/
		/*
		String parentCode = officeService.get(office.getParentId()).getCode();
		//查询同级机构（父级机构的子级机构），注意，被删除的机构也算
        List<Office> sameLevelOffice = officeService.findChildList(office.getParentId());
        String numStr = "";
        int size = sameLevelOffice.size();//同级机构的数量
        if(size == 0){
            numStr = "01";
        }else {
            int num = size +1;
            if(num < 10){
                numStr += ("0"+num);//小于10则左边填充0
            }else {
                numStr += (""+num);
            }
        }
        String code = parentCode + numStr;
        office.setCode(code);
        **/

		//副负责人id拼接字符串
		StringBuilder deputyPersonIds = new StringBuilder();
		for(String id:office.getDeputyPersonIdList()){
			deputyPersonIds.append(id).append(",");
		}
		String deputyPersonIdsStr = deputyPersonIds.toString();
		//如果副负责人只有一个，那么去掉拼接成的id字符串末尾的逗号分隔符（因为目前流程服务在查询副负责人时假定只有一个，todo：后期，流程服务也应考虑机构副负责人有多个的情况）
		if(office.getDeputyPersonIdList().size() == 1){
			deputyPersonIdsStr = deputyPersonIds.substring(0, deputyPersonIdsStr.length() -1);
		}
		office.setDeputyPerson(deputyPersonIdsStr);

        if(StringUtils.isBlank(office.getId())){
            if(!"1".equals(user.getId())){
				//除admin外，只有配置了shiro权限的账号，才能添加或编辑一级机构
				if("1".equals(office.getParentId())){
					if(!SecurityUtils.getSubject().isPermitted("sys:office:maintainFirstLevelOffice")){
						addMessage(model,"您没有权限添加或编辑一级机构");
						return form(office, model);
					}
				}
				//除admin外，其他所有账号都不能增加最末级机构的下级机构
				List<Office> childList = officeService.findChildOfficeList(parentOffice);
                if (childList.size() == 0){
                    addMessage(model,"您没有权限添加最末级机构的下级机构");
                    return form(office, model);
                }
                //非admin账号最大能够添加的机构层级数受字典配置控制
				String maxLevel = DictUtils.getDictValue("非admin账号最大能够添加的机构层级数","max_add_office_level","");//4
                int num = parentOffice.getParentIds().split(",").length -1;
                if(num > Integer.parseInt(maxLevel)){
					addMessage(model,"您最大能够添加的机构层级数为"+maxLevel);
					return form(office, model);
				}
            }

            /* 注意：因为代码封装的原因，可能会因为office对象中的id为空字符串，导致office对象的parentIds属性不能被正确设置值，
             * 所以添加机构时，必须设置isNewRecord为true，并提前设置id值。下面的两行代码是有用的，别删！
             */
			office.setIsNewRecord(true);
			office.setId(IdGen.uuid());
		}

		//停用自身的同时，也停用所有子级机构
		if(Constant.OFFICE_DISABLE.equals(office.getUseable())){
			officeService.disableChildOffice(office);
		}

		officeService.save(office);
		addMessage(redirectAttributes, "保存机构'" + office.getName() + "'成功");

		//钉钉群消息提醒
		DingTalkMessageRequest dingMessage=new DingTalkMessageRequest();
		dingMessage.setProjectName("【管理助手添加/编辑机构】");
		dingMessage.setClassName("OfficeController");
		dingMessage.setExceptionNo(request.getRequestURL().toString());
		dingMessage.setErrorMsg("用户<"+user.getName()+">维护了机构<"+office.getName()+">，机构ID<"+office.getId()+">");
		intfzMessageService.sendDingTalk(dingMessage);

		String id = "0".equals(office.getParentId()) ? "" : office.getParentId();
		return "redirect:" + adminPath + "/sys/office/list?id="+id+"&isNewRecord=true";//isNewRecord的作用仅仅是判断是否需要刷新左侧树
	}

    /**
     * 删除机构
     * @param office 指定机构
     * @param redirectAttributes 失败后重定向
     * @return message
     */
	@RequiresPermissions("sys:office:edit")
	@RequestMapping(value = "delete")
	public String delete(Office office, RedirectAttributes redirectAttributes) {
		//查询此机构或其子机构下是否有人员，如果有，则不允许删除
		int count = systemService.findUserForValidate(office.getId());

//		if (Office.isRoot(id)){
//			addMessage(redirectAttributes, "删除机构失败, 不允许删除顶级机构或编号空");
//		}else{
		if(count > 0){
			addMessage(redirectAttributes, "机构下有人员, 不允许删除");
		}else {
			officeService.delete(office);
			addMessage(redirectAttributes, "删除机构成功");
		}
		return "redirect:" + adminPath + "/sys/office/list?id="+office.getParentId()+"&isNewRecord=true";//isNewRecord的作用仅仅是判断是否需要刷新左侧树
	}

	/**
	 * 获取机构JSON数据。
	 * @param extId 排除的ID
	 * @param type	类型（1：公司；2：部门/小组/其它：3：用户）
	 * @param grade 显示级别
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId,
											  @RequestParam(required=false) String type,
											  @RequestParam(required = false) boolean exclusionExOffice,
											  @RequestParam(required=false) Long grade,
											  @RequestParam(required=false) Boolean isAll, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		if(StringUtils.isNotBlank(extId)){
			extId = extId.substring(0, extId.length()-1);
		}
		List<User> userList = Lists.newArrayList();
		if(exclusionExOffice){
			userList = officeService.getUserExclusionExEmployee();
		}

		List<Office> list = officeService.findList(isAll==null?true:isAll);
		for (int i=0; i<list.size(); i++){
			Office e = list.get(i);
			if ((StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1))
					&& (type == null || (type != null && (type.equals("1") ? type.equals(e.getType()) : true)))
					&& (grade == null || (grade != null && Integer.parseInt(e.getGrade()) <= grade.intValue()))
					&& !"4".equals(type)
			){

				if(Constant.OFFICE_USABLE.equals(e.getUseable()) && !exclusionExOffice){
					Map<String, Object> map = Maps.newHashMap();
					map.put("id", e.getId());
					map.put("pId", e.getParentId());
					map.put("pIds", e.getParentIds());
					map.put("extId", extId);
					map.put("name", e.getName());
					if (type != null && "3".equals(type)){
						map.put("isParent", true);
					}
					mapList.add(map);
				}else if(Constant.OFFICE_DISABLE.equals(e.getUseable()) && !exclusionExOffice){
					Map<String, Object> map = Maps.newHashMap();
					map.put("id", e.getId());
					map.put("pId", e.getParentId());
					map.put("pIds", e.getParentIds());
					map.put("extId", extId);
					map.put("name", e.getName()+"（停用）");
					if (type != null && "3".equals(type)){
						map.put("isParent", true);
					}
					mapList.add(map);
				}else if(Constant.OFFICE_USABLE.equals(e.getUseable()) &&
						exclusionExOffice && "true".equals(this.includEmplee(userList,e.getId()))){
					Map<String, Object> map = Maps.newHashMap();
					map.put("id", e.getId());
					map.put("pId", e.getParentId());
					map.put("pIds", e.getParentIds());
					map.put("extId", extId);
					map.put("name", e.getName());
					if (type != null && "3".equals(type)){
						map.put("isParent", true);
					}
					mapList.add(map);
				}else if(Constant.OFFICE_DISABLE.equals(e.getUseable()) &&
						exclusionExOffice && "true".equals(this.includEmplee(userList,e.getId()))){
					Map<String, Object> map = Maps.newHashMap();
					map.put("id", e.getId());
					map.put("pId", e.getParentId());
					map.put("pIds", e.getParentIds());
					map.put("extId", extId);
					map.put("name", e.getName()+"（停用）");
					if (type != null && "3".equals(type)){
						map.put("isParent", true);
					}
					mapList.add(map);
				}


			}
			//修改成本中心职能选到一级部门的树形菜单
			if ((StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1))
					&& (type == null || (type != null
					&& (grade == null || (grade != null && Integer.parseInt(e.getGrade()) <= grade.intValue()))
			))&& "4".equals(type) && e.getParentIds().split(",").length < 4
			){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParentId());
				map.put("pIds", e.getParentIds());
				map.put("extId", extId);
				if(Constant.OFFICE_USABLE.equals(e.getUseable())){
					map.put("name", e.getName());
				}else if(Constant.OFFICE_DISABLE.equals(e.getUseable())){
					map.put("name", e.getName()+"（停用）");
				}
//				if (type != null && "4".equals(type)){
//					map.put("isParent", true);
//				}
				mapList.add(map);
			}
		}
		return mapList;
	}

	public String includEmplee(List<User> userList,String officeId){
		String flag = "false";
		if(userList == null || userList.size() < 1){
			return flag;
		}
		for(User u : userList){
			if(u!= null && u.getOffice() != null && StringUtils.isNotBlank(u.getOffice().getParentIds()) &&
					u.getOffice().getParentIds().contains(officeId)){
				flag = "true";
				break;
			}
		}
		return flag;
	}

	@ResponseBody
	@RequestMapping(value = "userTree")
	public List<Map<String, Object>> treeData(HttpServletRequest request, HttpServletResponse response) {
		List<Map<String, Object>> maps = officeService.queryDeptUserTree();
		return maps;
	}
}
