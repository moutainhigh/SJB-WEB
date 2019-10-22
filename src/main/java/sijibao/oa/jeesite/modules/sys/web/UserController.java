/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.sys.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sijibao.oa.common.beanvalidator.BeanValidators;
import com.sijibao.oa.common.config.Global;
import com.sijibao.oa.common.persistence.Page;
import com.sijibao.oa.common.utils.DateUtils;
import com.sijibao.oa.common.utils.StringUtils;
import com.sijibao.oa.common.utils.excel.ExportExcel;
import com.sijibao.oa.common.utils.excel.ImportExcel;
import com.sijibao.oa.common.web.BaseController;
import com.sijibao.oa.modules.intfz.utils.Constant;
import com.sijibao.oa.modules.sys.dao.PostInfoDao;
import com.sijibao.oa.modules.sys.dao.RoleDao;
import com.sijibao.oa.modules.sys.dao.UserQuitDao;
import com.sijibao.oa.modules.sys.entity.*;
import com.sijibao.oa.modules.sys.service.SystemService;
import com.sijibao.oa.modules.sys.utils.UserUtils;
import com.sijibao.oa.sys.api.JobLevelService;
import com.sijibao.oa.sys.api.request.JobLevelReq;
import com.sijibao.oa.sys.api.response.JobLevelResponse;

/**
 * 用户Controller
 * @author ThinkGem
 * @version 2013-8-29
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/user")
public class UserController extends BaseController {

	@Autowired
	private SystemService systemService;
	@Autowired
	private PostInfoDao postInfoDao;
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private UserQuitDao userQuitDao ;
	@Autowired
	private JobLevelService jobLevelService;
	
	@ModelAttribute
	public User get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return systemService.getUser(id);
		}else{
			return new User();
		}
	}

	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = {"index"})
	public String index(User user, Model model) {
		return "modules/sys/userIndex";
	}
	/**
	 * 列表页面
	 * @param user
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * 
	 */
	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = {"list", ""})
	public String list(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<User> page = systemService.findUser(new Page<User>(request, response), user);
        model.addAttribute("page", page);
		return "modules/sys/userList";
	}
	
	@ResponseBody
	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = {"listData"})
	public Page<User> listData(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<User> page = systemService.findUser(new Page<User>(request, response), user);
		return page;
	}
	/**
	 * 新增修改页面
	 * @param user
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = "form")
	public String form(User user, Model model) {
		if (user.getCompany()==null || user.getCompany().getId()==null){
			user.setCompany(UserUtils.getUser().getCompany());
		}
		if (user.getOffice()==null || user.getOffice().getId()==null){
			user.setOffice(UserUtils.getUser().getOffice());
		}
		if (user.getPostIds()==null){
			user.setPostIds(UserUtils.getUser().getPostIds());
		}
		if(user.getPostIds()!=null){
			List<String> postInfoList = Lists.newArrayList();
			for(String postId:user.getPostIds().split(",")){
				postInfoList.add(postId);
			}
			user.setPostNameList(postInfoList);
		}
		List<JobLevelResponse> list = jobLevelService.findAllList(new JobLevelReq());
		ArrayList<JobLevel> jobLevel = Lists.newArrayList();
		for (JobLevelResponse s : list) {
			JobLevel ss = change(s,JobLevel.class);
			jobLevel.add(ss);
		}
		model.addAttribute("user", user);
		model.addAttribute("jobLevel", jobLevel);
		model.addAttribute("allRoles", systemService.findAllRole());
		model.addAttribute("allPostName", systemService.findAllPostName());
		return "modules/sys/userForm";
	}
	/**
	 * 详细页面
	 * @param user
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = "detail")
	public String detail(User user, Model model) {
		if (user.getCompany()==null || user.getCompany().getId()==null){
			user.setCompany(UserUtils.getUser().getCompany());
		}
		if (user.getOffice()==null || user.getOffice().getId()==null){
			user.setOffice(UserUtils.getUser().getOffice());
		}
		if (user.getPostIds()==null){
			user.setPostIds(UserUtils.getUser().getPostIds());
		}
		if(user.getPostIds()!=null){
			List<String> postInfoList = Lists.newArrayList();
			for(String postId:user.getPostIds().split(",")){
				postInfoList.add(postId);
			}
			user.setPostNameList(postInfoList);
		}
		if(systemService.getPostInfo(user.getPostIds()) != null){
			user.setPostName(systemService.getPostInfo(user.getPostIds()).getPostName());
		}
		model.addAttribute("user", user);
		List<Role> role = user.getRoleList();
		StringBuilder s = new StringBuilder();
		for (Role r : role) {
			if(r != null && StringUtils.isNotBlank(s)){
				s.append(",");
			}
			s.append(r.getName());
		}
		if(s != null){
			user.setRoleName(s.toString());
		}
		List<UserQuit> list = userQuitDao.queryQuit(user.getId());
		UserQuit quit = new UserQuit();
		String handover = "";
		for (UserQuit a : list) {
			if("0".equals(a.getUserType())){
				quit = change(a,UserQuit.class);
			}else{
				handover = handover + UserUtils.get(a.getHandover()).getName() + ",";
			}
		}
		if(handover != ""){
			quit.setHandover(handover.substring(0, handover.length()-1));
		}
		List<String> handovers = userQuitDao.queryHandovers(user.getId());
		StringBuffer bu = new StringBuffer();
		for (String ss : handovers) {
			bu.append(UserUtils.get(ss).getName() + ",");
		}
		if(StringUtils.isNotBlank(bu.toString())){
			model.addAttribute("handovers",bu.toString().subSequence(0, bu.toString().length()-1));
		}
		model.addAttribute("userQuit",quit);
		model.addAttribute("allRoles", role);
		model.addAttribute("allPostName", systemService.findAllPostName());
		return "modules/sys/userDetail";
	}
	/**
	 * 保存
	 * @param user
	 * @param request
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:user:edit")
	@RequestMapping(value = "save")
	public String save(User user, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/user/list?repage";
		}
		if("false".equals(checkNo(user.getOldNo(),user.getNo()))){
			addMessage(model, "保存用户'" + user.getName() + "'失败，工号已存在！");
			user.setNo("");
			user.setMobile("");
			return form(user, model);
		}
		if(StringUtils.isBlank(user.getOffice().getName())){
			addMessage(model, "保存用户'" + user.getName() + "'部门信息不能为空！");
			user.setNo("");
			user.setMobile("");
			return form(user, model);
		}
		if("false".equals(checkMobile(user))){
			addMessage(model, "保存用户'" + user.getName() + "'失败，手机号已存在！");
			user.setNo("");
			user.setMobile("");
			return form(user, model);
		}
		// 修正引用赋值问题，不知道为何，Company和Office引用的一个实例地址，修改了一个，另外一个跟着修改。
		user.setCompany(new Office(request.getParameter("company.id")));
		user.setOffice(new Office(request.getParameter("office.id")));
		List<PostInfo> postInfoList = user.getPostInfoList();
		StringBuilder s = new StringBuilder();
		if(StringUtils.isBlank(user.getId())){
			user.setLoginName(user.getMobile());
		}else{
			user.setLoginName(user.getOldLoginName());
		}
		for (PostInfo postInfo : postInfoList) {
			if(StringUtils.isNotBlank(s.toString())){
				s = s.append(",");
			}
			s = s.append( postInfo.getId() );
		}
		user.setPostIds(s.toString());
		// 如果新密码为空，则不更换密码
		if (StringUtils.isNotBlank(user.getNewPassword())) {
			user.setPassword(SystemService.entryptPassword(user.getNewPassword()));
		}
		if (!beanValidator(model, user)){
			return form(user, model);
		}
		if (!"true".equals(checkLoginName(user))){
			addMessage(model, "保存用户'" + user.getName() + "'失败，登录名已存在");
			return form(user, model);
		}
		if (StringUtils.isNotBlank(user.getOldLoginName()) && StringUtils.isNotBlank(user.getLoginName())
				&& !user.getOldLoginName().equals(user.getLoginName())){
			addMessage(model, "保存用户'" + user.getName() + "'失败，登录名不允许变更！");
			user.setLoginName(user.getOldLoginName());
			return form(user, model);
		}
		Pattern pattern1 = Pattern.compile("\\d{4,10}");
		Matcher isNum = pattern1.matcher(user.getNo());
		if( !isNum.matches() ){
			addMessage(model, "保存用户'" + user.getName() + "'失败，工号必须为4~10位数字;");
			return form(user, model);
		}
		Pattern pattern2 = Pattern.compile("\\d{11}");
		Matcher isNum2 = pattern2.matcher(user.getMobile());
		if( !isNum2.matches() ){
			addMessage(model, "保存用户'" + user.getName() + "'失败，手机号必须为11位数字;;");
			return form(user, model);
		}
		// 角色数据有效性验证，过滤不在授权内的角色
		List<Role> roleList = Lists.newArrayList();
		List<String> roleIdList = user.getRoleIdList();
		for (Role r : systemService.findAllRole()){
			if (roleIdList.contains(r.getId())){
				roleList.add(r);
			}
		}
		user.setRoleList(roleList);
        //保存用户信息
		systemService.saveUser(user);
		addMessage(redirectAttributes, "保存用户'" + user.getLoginName() + "'成功");
		return "redirect:" + adminPath + "/sys/user/list?repage";
	}
	/**
	 * 删除
	 * @param user
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:user:edit")
	@RequestMapping(value = "delete")
	public String delete(User user, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/user/list?repage";
		}
		if (UserUtils.getUser().getId().equals(user.getId())){
			addMessage(redirectAttributes, "删除用户失败, 不允许删除当前用户");
		}else if (User.isAdmin(user.getId())){
			addMessage(redirectAttributes, "删除用户失败, 不允许删除超级管理员用户");
		}else{
            //删除用户
            systemService.deleteUser(user);
            addMessage(redirectAttributes, "删除用户成功");
		}
		return "redirect:" + adminPath + "/sys/user/list?repage";
	}
	
	/**
	 * 导出用户数据
	 * @param user
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:user:view")
    @RequestMapping(value = "export", method= RequestMethod.POST)
    public String exportFile(User user, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "用户数据"+ DateUtils.getDate("yyyyMMdd")+".xlsx";
//            Page<User> page = systemService.findUser(new Page<User>(request, response, -1), user);
            List<User> findUser = systemService.findUser(user);
    		new ExportExcel("用户数据", UserExport.class).setDataList(findUser).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出用户失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/sys/user/list?repage";
    }

	/**
	 * 导入用户数据
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:user:edit")
    @RequestMapping(value = "import", method= RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/user/list?repage";
		}
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<User> list = ei.getDataList(User.class);
			for (User user : list){
				try{
					user.setLoginName(user.getMobile());
					boolean flag = true;
					if(StringUtils.isNotBlank(user.getRoleName())){
						String[] roles = user.getRoleName().split(",");
                        List<Role> st = roleDao.getRoleByNames(roles);
						user.setRoleList(st);
						if(st ==null || st.size() < 1){
                            failureMsg.append("<br/>用户 "+user.getMobile()+" 用户角色有误; ");
                            flag = false ;
                        }
					}else{
						failureMsg.append("<br/>用户 "+user.getMobile()+" 用户角色有误; ");
						flag = false ;
					}
					if(StringUtils.isNotBlank(user.getPostName())){
                        String p = postInfoDao.getPostIdByName(user.getPostName());
						user.setPostIds(p);
						if(StringUtils.isBlank(p)){
                            failureMsg.append("<br/>用户 "+user.getMobile()+" 用户岗位有误; ");
                            flag = false ;
                        }
					}else{
						failureMsg.append("<br/>用户 "+user.getMobile()+" 用户岗位不能为空; ");
						flag = false ;
					}
					if("false".equals(checkNo("",user.getNo()))){
						failureMsg.append("<br/>工号 "+user.getNo()+" 已存在; ");
						flag = false ;
					}

					Pattern pattern1 = Pattern.compile("\\d{4,10}");
					Matcher isNum = pattern1.matcher(user.getNo());
					if( !isNum.matches() ){
						failureMsg.append("<br/>工号 "+user.getNo()+" 必须为4~10位数字; ");
						flag = false ;
					}
					Pattern pattern2 = Pattern.compile("\\d{11}");
					Matcher isNum2 = pattern2.matcher(user.getMobile());
					if( !isNum2.matches() ){
						failureMsg.append("<br/>手机号  "+user.getMobile()+" 必须为11位数字; ");
						flag = false ;
					}


					if("false".equals(checkMobile(user))){
						failureMsg.append("<br/>手机号 "+user.getMobile()+" 已存在; ");
						flag = false ;
					}
					if(StringUtils.isBlank(user.getRankName())){
						user.setRank("");
					}else{
						user.setRank(jobLevelService.queryJobLevelIdByName(user.getRankName()));
					}
					if ("true".equals(checkLoginName(user))){
						user.setPassword(SystemService.entryptPassword("123456"));
						BeanValidators.validateWithException(validator, user);
						//设置登录名
						user.setLoginName(user.getMobile());
						//设置岗位
//						PostInfo post = postInfoDao.getByPostName(user.getPostName());
//						if(post != null){
//							user.setPostIds(post.getId());
//						}
						//设置角色
						String roleName = user.getRoleName();
						if(roleName!=null && roleName != "" ){
							String[] roleNa = user.getRoleName().split(",");
							ArrayList<Role> array = Lists.newArrayList();
//							if(roleNa ==null || roleNa.length < 1){
//                                failureMsg.append("<br/>登录名 "+user.getMobile()+" 角色有误; ");
//                                flag = false ;
//                            }
							for (String s : roleNa) {
								Role ro = new Role();
								ro.setName(s);
								Role role = roleDao.getByName(ro);
								array.add(role);
							}
							user.setRoleList(array);
						}else{
                            failureMsg.append("<br/>登录名 "+user.getMobile()+" 角色有误; ");
                            flag = false ;
                        }
						if(flag){
							systemService.saveUser(user);
							successNum++;
						}

					}else{
						failureMsg.append("<br/>登录名 "+user.getMobile()+" 已存在; ");
						flag = false ;
					}
					if(!flag){
						failureNum ++ ;
					}
					
				}catch(ConstraintViolationException ex){
					failureMsg.append("<br/>登录名 "+user.getLoginName()+" 导入失败：");
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					failureNum++;
					for (String message : messageList){
						failureMsg.append(message+"; ");
//						failureNum++;
					}
				}catch (Exception ex) {
					failureNum++;
					failureMsg.append("<br/>登录名 "+user.getLoginName()+" 导入失败："+ex.getMessage());
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条用户，导入信息如下：");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条用户"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入用户失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/sys/user/list?repage";
    }
	
	/**
	 * 下载导入用户数据模板
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:user:view")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "用户数据导入模板"+DateUtils.getDate("yyyyMMdd")+".xlsx";
    		List<User> list = Lists.newArrayList();
    		list.add(UserUtils.getUser());
    		new ExportExcel("用户数据", User.class, 2).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/sys/user/list?repage";
    }

	/**
	 * 验证登录名是否有效
	 * @param oldLoginName
	 * @param loginName
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("sys:user:edit")
	@RequestMapping(value = "checkLoginName")
	public String checkLoginName(String oldLoginName, String loginName) {
		if (loginName !=null && loginName.equals(oldLoginName)) {
			return "true";
		} else if(loginName !=null && systemService.getUserByLoginName(loginName) == null){
			return "true";
		}
		return "false";
	}
	/**
	 * 不通过缓存验证登录名是否有效
	 * @return
	 */
	public String checkLoginName(User user) {
		int count = systemService.checkLoginName(user);
		if (count > 0) {
			return "false";
		}
		return "true";
	}

	/**
	 * 验证工号是否有效
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("sys:user:edit")
	@RequestMapping(value = "checkMobile")
	public String checkMobile(User user) {
		int count = systemService.checkMobile(user);
		if (count > 0) {
			return "false";
		}
		return "true";

	}
	/**
	 * 验证工号
	 * @param oldNo
	 * @param no
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("sys:user:edit")
	@RequestMapping(value = "checkNo")
	public String checkNo(String oldNo, String no) {
		if (no !=null && no.equals(oldNo)) {
			return "true";
		} else if(no !=null && (systemService.getUserByNo(no) == null || systemService.getUserByNo(no).size() == 0)){
			return "true";
		}
		return "false";
	}
	/**
	 * 用户信息显示及保存
	 * @param user
	 * @param model
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "info")
	public String info(User user, HttpServletResponse response, Model model) {
		User currentUser = UserUtils.getUser();
		if (StringUtils.isNotBlank(user.getName())){
			if(Global.isDemoMode()){
				model.addAttribute("message", "演示模式，不允许操作！");
				return "modules/sys/userInfo";
			}
			currentUser.setEmail(user.getEmail());
			currentUser.setPhone(user.getPhone());
			currentUser.setMobile(user.getMobile());
			currentUser.setRemarks(user.getRemarks());
			currentUser.setPhoto(user.getPhoto());
			systemService.updateUserInfo(currentUser);
			model.addAttribute("message", "保存用户信息成功");
		}
		model.addAttribute("user", currentUser);
		model.addAttribute("Global", new Global());
		return "modules/sys/userInfo";
	}

	/**
	 * 返回用户信息
	 * @return
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "infoData")
	public User infoData() {
		return UserUtils.getUser();
	}
	
	/**
	 * 修改个人用户密码
	 * @param oldPassword
	 * @param newPassword
	 * @param model
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "modifyPwd")
	public String modifyPwd(String oldPassword, String newPassword, Model model) {
		User user = UserUtils.getUser();
		if (StringUtils.isNotBlank(oldPassword) && StringUtils.isNotBlank(newPassword)){
			if(Global.isDemoMode()){
				model.addAttribute("message", "演示模式，不允许操作！");
				return "modules/sys/userModifyPwd";
			}
			if (SystemService.validatePassword(oldPassword, user.getPassword())){
				systemService.updatePasswordById(user.getId(), user.getLoginName(), newPassword);
				UserUtils.clearCache(user);
				model.addAttribute("message", "修改密码成功");
			}else{
				model.addAttribute("message", "修改密码失败，旧密码错误");
			}
		}
		model.addAttribute("user", user);
		return "modules/sys/userModifyPwd";
	}

	/**
	 * extId被忽略的id
	 * @param officeId 父级机构id
	 * @param extId 排除的机构id
     * @param includeExEmployee 返回数据是否包含非在职人员（ex-employee），true是，false否
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String officeId,
											  @RequestParam(required=false) String extId,
											  @RequestParam(required = false) boolean includeExEmployee,
											  HttpServletRequest request, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<User> list = systemService.findUserByOfficeId(officeId);
		for (int i=0; i<list.size(); i++){
			User u = list.get(i);
			if(StringUtils.isNotBlank(extId) && extId.equals(u.getId())){
				
			}else{
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", "u_"+u.getId());
				map.put("pId", officeId);
				map.put("name", StringUtils.replace(u.getName(), " ", ""));
				map.put("mobile", u.getMobile());
				map.put("userId", u.getId());
				if(!includeExEmployee && !Constant.USER_WORKING.equals(u.getUserStatus())){//要求返回数据不包含非在职人员时，剔除之
				    continue;
                }
                mapList.add(map);
            }
		}
		return mapList;
	}
    
	/**
	 * 离职页面
	 * 
	 * @param user
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:user:edit")
	@RequestMapping(value = "quit")
	public String quit(User user, HttpServletResponse response, Model model) {
		User u = new User();
		u.setId(user.getId());
		model.addAttribute("user", u);
		return "modules/sys/userQuit";
	}
	
	/**
	 * 保存离职信息
	 * @param userQuit
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:user:edit")
	@RequestMapping(value = "saveQuit")
	public String saveQuit(UserQuit userQuit, HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			model.addAttribute("message", "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/user/list?repage";
		}
		if(userQuit.getHandovers() == null || userQuit.getHandovers().size() == 0){
//			addMessage(redirectAttributes, "删除用户成功");
//			addMessage(redirectAttributes, "交接人不能为空！");
			User u = new User();
			u.setId(userQuit.getUserId());
			model.addAttribute("user", u);
			model.addAttribute("message", "交接人不能为空！");
			return "modules/sys/userQuit";
		}
		systemService.saveQuit(userQuit);//保存方法
		Page<User> page = systemService.findUser(new Page<User>(request, response), new User());
        model.addAttribute("page", page);
        model.addAttribute("user", new User());
//		model.addAttribute("message", "保存离职信息成功！");
        addMessage(redirectAttributes, "保存离职信息成功！");
		return "redirect:" + adminPath + "/sys/user/list?repage";
	}
	
	/**
	 * 查询单据
	 * @param user
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequiresPermissions("sys:user:edit")
	@RequestMapping(value = "queryBills")
	public void queryBills(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
		
		String massage = systemService.queryBills(user);
//		String massage = null;
		try {
			response.getWriter().write(JSON.toJSONString(massage));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 再次入职
	 * @param user
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequiresPermissions("sys:user:edit")
	@RequestMapping(value = "entry")
	public String entry(User user, HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
		
		systemService.entry(user);
		addMessage(redirectAttributes, "保存入职信息成功！");
//		model.addAttribute("message", "保存入职信息成功！");
		return "redirect:" + adminPath + "/sys/user/list?repage";
	}
	
	
//	@InitBinder
//	public void initBinder(WebDataBinder b) {
//		b.registerCustomEditor(List.class, "roleList", new PropertyEditorSupport(){
//			@Autowired
//			private SystemService systemService;
//			@Override
//			public void setAsText(String text) throws IllegalArgumentException {
//				String[] ids = StringUtils.split(text, ",");
//				List<Role> roles = new ArrayList<Role>();
//				for (String id : ids) {
//					Role role = systemService.getRole(Long.valueOf(id));
//					roles.add(role);
//				}
//				setValue(roles);
//			}
//			@Override
//			public String getAsText() {
//				return Collections3.extractToString((List) getValue(), "id", ",");
//			}
//		});
//	}
}
