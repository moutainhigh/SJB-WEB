/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.sys.service;

import java.util.*;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import sijibao.oa.jeesite.modules.sys.dao.*;
import sijibao.oa.jeesite.modules.sys.entity.Area;
import sijibao.oa.jeesite.modules.sys.entity.PostInfo;
import sijibao.oa.jeesite.modules.sys.entity.User;
import sijibao.oa.jeesite.modules.sys.utils.UserUtils;
import sijibao.oa.jeesite.persistence.Page;

/**
 * 系统管理，安全相关实体的管理类,包括用户、角色、菜单.
 * 
 * @author ThinkGem
 * @version 2013-12-05
 */
@Service
@Transactional(readOnly = true)
public class SystemService extends BaseService implements InitializingBean {

	public static final String HASH_ALGORITHM = "SHA-1";
	public static final int HASH_INTERATIONS = 1024;
	public static final int SALT_SIZE = 8;

	@Autowired
	private UserDao userDao;
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private MenuDao menuDao;
	@Autowired
	private SessionDAO sessionDao;
	@Autowired
	private PostInfoDao postInfoDao;
	@Autowired
	private UserQuitDao userQuitDao;
	@Autowired
	private IntfzEmployeeStatusrecordService intfzEmployeeStatusrecordService;
	@Autowired
	private NeedFlowService needFlowService;

	public SessionDAO getSessionDao() {
		return sessionDao;
	}

	@Autowired
	private IdentityService identityService;

	public List<PostInfo> findAllPostName() {
		return postInfoDao.findList(new PostInfo());
	}

	@Autowired
	private AreaDao areaDao;

	public List<Area> findAllArea() {
		return areaDao.findList(new Area());
	}

	// 保存岗位列表
	@Transactional(readOnly = false)
	public void savePost(PostInfo postInfo) {
		if (StringUtils.isBlank(postInfo.getId())) {
			postInfo.preInsert();
			postInfoDao.insert(postInfo);
		} else {
			postInfo.preUpdate();
			postInfoDao.update(postInfo);
		}
	}

	public PostInfo getPostInfo(String postId) {
		return postInfoDao.get(postId);
	}

	// -- User Service --//

	/**
	 * 获取用户
	 * 
	 * @param id
	 * @return
	 */
	public User getUser(String id) {
		return UserUtils.get(id);
	}

	/**
	 * 根据登录名获取用户
	 * 
	 * @param loginName
	 *            用户账户
	 * @return
	 */
	public User getUserByLoginName(String loginName) {
		return UserUtils.getByLoginName(loginName);
	}

	/**
	 * 根据工号获取用户
	 * 
	 * @param no
	 *            用户账户
	 * @return
	 */
	public List<User> getUserByNo(String no) {
		return userDao.getUserByNo(no);
	}

	public User getUserByMobile(String mobile) {
		return UserUtils.getByPhone(mobile);
	}


	public User getUserByName(String name) {
		return userDao.getByName(name);
	}

	/**
	 * 根据微信OPENID获取用户
	 */
	public User getUserByOpenId(String openId) {
		return UserUtils.getByWechatId(openId);
	}

    /**
     * 分页查询人员列表
     */
	public Page<User> findUser(Page<User> page, User user) {
		// 生成数据权限过滤条件（dsf为dataScopeFilter的简写，在xml中使用 ${sqlMap.dsf}调用权限SQL）
		user.getSqlMap().put("dsf", dataScopeFilter(user.getCurrentUser(), "o", "a"));
		// 设置分页参数
		user.setPage(page);
		// 设置条件：当前登录用户的id，用于判断是否是管理员，非管理员用户不能查询出管理员信息
		User currentUser = new User();
		currentUser.setId(UserUtils.getUser().getId());
		user.setCurrentUser(currentUser);
		// 执行分页查询
		List<User> list = userDao.findList(user);
		ArrayList<User> li = Lists.newArrayList();
		for (User u : list) {
			if (StringUtils.isNotBlank(u.getPostIds()) && getPostInfo(u.getPostIds()) != null) {
				u.setPostName(getPostInfo(u.getPostIds()).getPostName());
			}
			u.setRoleList(roleDao.findList(new Role(user)));
			if (u.getRoleList() != null && u.getRoleIdList().size() > 0) {
				StringBuilder b = new StringBuilder();
				for (Role ro : u.getRoleList()) {
					b.append(ro.getName() + ",");
				}
				u.setRoleName(b.toString().substring(0, b.toString().length() - 1));
			}
			u.setUserStatusName(DictUtils.getDictLabels(u.getUserStatus(), "user_status", ""));
			li.add(u);
		}
		page.setList(li);
		return page;
	}

	/**
	 * 无分页查询人员列表
	 */
	public List<User> findUser(User user) {
		// 生成数据权限过滤条件（dsf为dataScopeFilter的简写，在xml中使用 ${sqlMap.dsf}调用权限SQL）
		user.getSqlMap().put("dsf", dataScopeFilter(user.getCurrentUser(), "o", "a"));
		List<User> lis = userDao.findList(user);
		ArrayList<User> list = Lists.newArrayList();
		for (User u : lis) {
			if (postInfoDao.get(u.getPostIds()) != null) {
				u.setPostName(postInfoDao.get(u.getPostIds()).getPostName());
			}
			List<Role> roleList = UserUtils.get(u.getId()).getRoleList();
			if (roleList != null && roleList.size() > 0) {
				StringBuilder s = new StringBuilder();
				for (Role r : roleList) {
					s.append(r.getName() + ",");
				}
				u.setRoleName(s.toString().substring(0, s.toString().length() - 1));
			}
			u.setUserStatusName(DictUtils.getDictLabel(u.getUserStatus(), "user_status", ""));
			list.add(u);
		}
		return list;
	}

	/**
	 * 无分页查询人员列表(无权限过滤)
	 */
	public List<User> findUserNotAccess(User user) {
		List<User> list = userDao.findList(user);
		return list;
	}

	/**
	 * 通过部门ID获取用户列表，仅返回用户id和name（树查询用户时用）
	 * 
	 * @param officeId 部门ID
	 * @return List<User> 用户列表
	 */
	@SuppressWarnings("unchecked")
	public List<User> findUserByOfficeId(String officeId) {
	    //从redis缓存中获取<部门下的全部用户>
		List<User> list = (List<User>) CacheUtils.get(CacheUtils.USER_CACHE,
				CacheUtils.USER_CACHE_LIST_BY_OFFICE_ID_ + officeId);
		if (list == null) {
			User user = new User();
			user.setOffice(new Office(officeId));
			list = userDao.findUserByOfficeId(user);
			//把从数据库中查到的<部门下的全部用户>放入redis缓存
			CacheUtils.put(CacheUtils.USER_CACHE, CacheUtils.USER_CACHE_LIST_BY_OFFICE_ID_ + officeId, list);
		}
		return list;
	}

	/**
	 * 删除机构时，根据office id判断待删除机构或其子机构下是否有员工，如果有员工则不允许删除 注意：仅仅是为了校验，跟缓存无关
	 */
	public int findUserForValidate(String officeId) {
		return userDao.findUserForValidate(officeId);
	}

    /**
     * 保存或编辑用户
     */
	@Transactional
	public void saveUser(User user) {
        if(StringUtils.isBlank(user.getPostIds())){
            throw new ServiceException(user.getLoginName() + "岗位有误！");
        }
		// 判断离职状态
		if(StringUtils.isNotBlank(user.getId())){
			User u = UserUtils.get(user.getId());
			if ("3".equals(u.getUserStatus()) && "1".equals(user.getLoginFlag())) {
				user.setUserStatus("2");
			}
			if ( !"1".equals(u.getUserStatus()) && "0".equals(user.getLoginFlag())) {
				user.setUserStatus("3");
			}
			if (StringUtils.isBlank(user.getRank())) {
				user.setRank("");
			}
		}
		// 设置用户首字母
		if (StringUtils.isNotBlank(user.getName())) {
			user.setUnameInitials(Cn2SpellUtils.converterToFirstSpell(user.getName().subSequence(0, 1).toString()));
		}
		if (StringUtils.isBlank(user.getId())) {
			user.preInsert();
			userDao.insert(user);
            // 清除用户现在归属部门下的人员缓存
            UserUtils.clearUserCacheListByOfficeId(user.getOffice().getId());
		} else {
            User originUser = UserUtils.get(user.getId());
			// 更新用户数据
			user.preUpdate();
			userDao.update(user);
			// 清除用户缓存
            UserUtils.clearCache(originUser);
            // 清除用户原来归属部门下的人员缓存
            UserUtils.clearUserCacheListByOfficeId(originUser.getOffice().getId());
            // 清除用户现在归属部门下的人员缓存
            UserUtils.clearUserCacheListByOfficeId(user.getOffice().getId());
		}
        // 更新用户与角色关联
        userDao.deleteUserRole(user);
        if (user.getRoleList() != null && user.getRoleList().size() > 0) {
            userDao.insertUserRole(user);
        } else {
            throw new ServiceException(user.getLoginName() + "没有设置角色！");
        }
        // 将当前用户同步到Activiti
        saveActivitiUser(user);
        // // 清除权限缓存
        // systemRealm.clearAllCachedAuthorizationInfo();
	}

    /**
     * 保存或编辑用户（给APP接口专用）
     */
	@Transactional
	public void saveUserForIntfz(User user) {
		// 设置用户首字母
		if (StringUtils.isNotBlank(user.getName())) {
			user.setUnameInitials(Cn2SpellUtils.converterToFirstSpell(user.getName().subSequence(0, 1).toString()));
		}
		if (StringUtils.isBlank(user.getId())) {
			user.preInsert();
			userDao.insert(user);
            // 清除用户现在归属部门下的人员缓存
            UserUtils.clearUserCacheListByOfficeId(user.getOffice().getId());
		} else {
            User originUser = UserUtils.get(user.getId());
			// 更新用户数据
			user.preUpdate();
			userDao.update(user);
            // 清除用户缓存
            UserUtils.clearCache(originUser);
            // 清除用户原来归属部门下的人员缓存
            UserUtils.clearUserCacheListByOfficeId(originUser.getOffice().getId());
            // 清除用户现在归属部门下的人员缓存
            UserUtils.clearUserCacheListByOfficeId(user.getOffice().getId());
		}
        // 将当前用户同步到Activiti
        saveActivitiUser(user);
        // // 清除权限缓存
        // systemRealm.clearAllCachedAuthorizationInfo();
	}

    /**
     * 删除用户
     */
    @Transactional
    public void deleteUser(User user) {
        userDao.delete(user);
        // 清除用户缓存
        UserUtils.clearCache(UserUtils.get(user.getId()));
        // 清除用户现在归属部门下的人员缓存
        UserUtils.clearUserCacheListByOfficeId(user.getOffice().getId());
        // 同步到Activiti
        deleteActivitiUser(user);
        // // 清除权限缓存
        // systemRealm.clearAllCachedAuthorizationInfo();
    }

    /**
     * 用户自己操作编辑个人信息
     */
	@Transactional
	public void updateUserInfo(User user) {
        user.preUpdate();
		userDao.updateUserInfo(user);
        // 清除用户缓存
        UserUtils.clearCache(UserUtils.get(user.getId()));
        // 将当前用户同步到Activiti
        saveActivitiUser(user);
		// // 清除权限缓存
		// systemRealm.clearAllCachedAuthorizationInfo();
	}

    /**
     * 用户自己操作修改密码
     */
	@Transactional
	public void updatePasswordById(String id, String loginName, String newPassword) {
        User user = new User(id);
		user.setPassword(entryptPassword(newPassword));
		userDao.updatePasswordById(user);
		// 清除用户缓存
		UserUtils.clearCache(UserUtils.get(user.getId()));
    }

    /**
     * 更新用户登录信息
     */
	@Transactional
	public void updateUserLoginInfo(User user) {
		// 保存上次登录信息
		user.setOldLoginIp(user.getLoginIp());
		user.setOldLoginDate(user.getLoginDate());
		// 更新本次登录信息
		user.setLoginIp(StringUtils.getRemoteAddr(Servlets.getRequest()));
		user.setLoginDate(new Date());
		userDao.updateLoginInfo(user);
        // 清除用户缓存
        UserUtils.clearCache(UserUtils.get(user.getId()));
    }

	/**
	 * 生成安全的密码，生成随机的16位salt并经过1024次 sha-1 hash
	 */
	public static String entryptPassword(String plainPassword) {
		String plain = Encodes.unescapeHtml(plainPassword);
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		byte[] hashPassword = Digests.sha1(plain.getBytes(), salt, HASH_INTERATIONS);
		return Encodes.encodeHex(salt) + Encodes.encodeHex(hashPassword);
	}

	/**
	 * 验证密码
	 * 
	 * @param plainPassword
	 *            明文密码
	 * @param password
	 *            密文密码
	 * @return 验证成功返回true
	 */
	public static boolean validatePassword(String plainPassword, String password) {
		String plain = Encodes.unescapeHtml(plainPassword);
		byte[] salt = Encodes.decodeHex(password.substring(0, 16));
		byte[] hashPassword = Digests.sha1(plain.getBytes(), salt, HASH_INTERATIONS);
		return password.equals(Encodes.encodeHex(salt) + Encodes.encodeHex(hashPassword));
	}

	/**
	 * 获得活动会话
	 * 
	 * @return
	 */
	public Collection<Session> getActiveSessions() {
		return sessionDao.getActiveSessions(false);
	}

	// -- Role Service --//

	public Role getRole(String id) {
		return roleDao.get(id);
	}

	public Role getRoleByName(String name) {
		Role r = new Role();
		r.setName(name);
		return roleDao.getByName(r);
	}

	public Role getRoleByEnname(String enname) {
		Role r = new Role();
		r.setEnname(enname);
		return roleDao.getByEnname(r);
	}

	public List<Role> findRole(Role role) {
		return roleDao.findList(role);
	}

	public List<Role> findAllRole() {
		return UserUtils.getRoleList();
	}

	@Transactional(readOnly = false)
	public void saveRole(Role role) {
		if (StringUtils.isBlank(role.getId())) {
			role.preInsert();
			roleDao.insert(role);
			// 同步到Activiti
			saveActivitiGroup(role);
		} else {
			role.preUpdate();
			roleDao.update(role);
		}
		// 更新角色与菜单关联
		roleDao.deleteRoleMenu(role);
		if (role.getMenuList().size() > 0) {
			roleDao.insertRoleMenu(role);
		}
		// 更新角色与部门关联
		roleDao.deleteRoleOffice(role);
		if (role.getOfficeList().size() > 0) {
			roleDao.insertRoleOffice(role);
		}
		// 同步到Activiti
		saveActivitiGroup(role);
		// 清除用户角色缓存
		UserUtils.removeFromSession(UserUtils.ROLE_LIST,role);
		// // 清除权限缓存
		// systemRealm.clearAllCachedAuthorizationInfo();
	}

	@Transactional(readOnly = false)
	public void deleteRole(Role role) {
		roleDao.delete(role);
		// 同步到Activiti
		deleteActivitiGroup(role);
		// 清除用户角色缓存
		UserUtils.removeFromSession(UserUtils.ROLE_LIST,role);
		// // 清除权限缓存
		// systemRealm.clearAllCachedAuthorizationInfo();
	}

	@Transactional(readOnly = false)
	public Boolean outUserInRole(Role role, User user) {
		List<Role> roles = user.getRoleList();
		for (Role e : roles) {
			if (e.getId().equals(role.getId())) {
				roles.remove(e);
				saveUser(user);
				return true;
			}
		}
		return false;
	}

	@Transactional(readOnly = false)
	public User assignUserToRole(Role role, User user) {
		if (user == null) {
			return null;
		}
		List<String> roleIds = user.getRoleIdList();
		if (roleIds.contains(role.getId())) {
			return null;
		}
		user.getRoleList().add(role);
		saveUser(user);
		return user;
	}

	// -- Menu Service --//

	public Menu getMenu(String id) {
		return menuDao.get(id);
	}

	public List<Menu> findAllMenu() {
		return UserUtils.getMenuList();
	}

	@Transactional(readOnly = false)
	public void saveMenu(Menu menu) {

		// 获取父节点实体
		menu.setParent(this.getMenu(menu.getParent().getId()));

		// 获取修改前的parentIds，用于更新子节点的parentIds
		String oldParentIds = menu.getParentIds();

		// 设置新的父节点串
		menu.setParentIds(menu.getParent().getParentIds() + menu.getParent().getId() + ",");

		// 保存或更新实体
		if (StringUtils.isBlank(menu.getId())) {
			menu.preInsert();
			menuDao.insert(menu);
		} else {
			menu.preUpdate();
			menuDao.update(menu);
		}

		// 更新子节点 parentIds
		Menu m = new Menu();
		m.setParentIds("%," + menu.getId() + ",%");
		List<Menu> list = menuDao.findByParentIdsLike(m);
		for (Menu e : list) {
			e.setParentIds(e.getParentIds().replace(oldParentIds, menu.getParentIds()));
			menuDao.updateParentIds(e);
		}
		// 清除用户菜单缓存
		UserUtils.removeFromSession(UserUtils.MENU_LIST);
		// // 清除权限缓存
		// systemRealm.clearAllCachedAuthorizationInfo();
		// 清除系统菜单名称-路径映射缓存
		CacheUtils.remove(CacheUtils.SYS_CACHE_MENU_NAME_PATH_MAP);
	}

	@Transactional(readOnly = false)
	public void updateMenuSort(Menu menu) {
		menuDao.updateSort(menu);
		// 清除用户菜单缓存
		UserUtils.removeFromSession(UserUtils.MENU_LIST);
		// // 清除权限缓存
		// systemRealm.clearAllCachedAuthorizationInfo();
        // 清除系统菜单名称-路径映射缓存
		CacheUtils.remove(CacheUtils.SYS_CACHE_MENU_NAME_PATH_MAP);
	}

	@Transactional(readOnly = false)
	public void deleteMenu(Menu menu) {
		menuDao.delete(menu);
		// 清除用户菜单缓存
		UserUtils.removeFromSession(UserUtils.MENU_LIST);
		// // 清除权限缓存
		// systemRealm.clearAllCachedAuthorizationInfo();
        // 清除系统菜单名称-路径映射缓存
		CacheUtils.remove(CacheUtils.SYS_CACHE_MENU_NAME_PATH_MAP);
	}

	/**
	 * 获取Key加载信息
	 */
	public static boolean printKeyLoadMessage() {
		StringBuilder sb = new StringBuilder();
		sb.append("\r\n======================================================================\r\n");
		sb.append("\r\n                     sijibao-oa-web开始启动\r\n");
		sb.append("\r\n======================================================================\r\n");
		System.out.println(sb.toString());
		return true;
	}

	///////////////// Synchronized to the Activiti //////////////////

	// 已废弃，同步见：ActGroupEntityServiceFactory.java、ActUserEntityServiceFactory.java

	/**
	 * 是需要同步Activiti数据，如果从未同步过，则同步数据。
	 */
	private static boolean isSynActivitiIndetity = true;

	public void afterPropertiesSet() throws Exception {
		if (!Global.isSynActivitiIndetity()) {
			return;
		}
		if (isSynActivitiIndetity) {
			isSynActivitiIndetity = false;
			// 同步角色数据
			List<Group> groupList = identityService.createGroupQuery().list();
			if (groupList.size() == 0) {
				Iterator<Role> roles = roleDao.findAllList(new Role()).iterator();
				while (roles.hasNext()) {
					Role role = roles.next();
					saveActivitiGroup(role);
				}
			}
			// 同步用户数据
			List<org.activiti.engine.identity.User> userList = identityService.createUserQuery().list();
			if (userList.size() == 0) {
				Iterator<User> users = userDao.findAllList(new User()).iterator();
				while (users.hasNext()) {
					saveActivitiUser(users.next());
				}
			}
		}
	}

	private void saveActivitiGroup(Role role) {
		if (!Global.isSynActivitiIndetity()) {
			return;
		}
		String groupId = role.getEnname();

		// 如果修改了英文名，则删除原Activiti角色
		if (StringUtils.isNotBlank(role.getOldEnname()) && !role.getOldEnname().equals(role.getEnname())) {
			identityService.deleteGroup(role.getOldEnname());
		}

		Group group = identityService.createGroupQuery().groupId(groupId).singleResult();
		if (group == null) {
			group = identityService.newGroup(groupId);
		}
		group.setName(role.getName());
		group.setType(role.getRoleType());
		identityService.saveGroup(group);

		// 删除用户与用户组关系
		List<org.activiti.engine.identity.User> activitiUserList = identityService.createUserQuery()
				.memberOfGroup(groupId).list();
		for (org.activiti.engine.identity.User activitiUser : activitiUserList) {
			identityService.deleteMembership(activitiUser.getId(), groupId);
		}

		// 创建用户与用户组关系
		List<User> userList = findUser(new User(new Role(role.getId())));
		for (User e : userList) {
			String userId = e.getLoginName();// ObjectUtils.toString(user.getId());
			// 如果该用户不存在，则创建一个
			org.activiti.engine.identity.User activitiUser = identityService.createUserQuery().userId(userId)
					.singleResult();
			if (activitiUser == null) {
				activitiUser = identityService.newUser(userId);
				activitiUser.setFirstName(e.getName());
				activitiUser.setLastName(StringUtils.EMPTY);
				activitiUser.setEmail(e.getEmail());
				activitiUser.setPassword(StringUtils.EMPTY);
				identityService.saveUser(activitiUser);
			}
			identityService.createMembership(userId, groupId);
		}
	}

	public void deleteActivitiGroup(Role role) {
		if (!Global.isSynActivitiIndetity()) {
			return;
		}
		if (role != null) {
			String groupId = role.getEnname();
			identityService.deleteGroup(groupId);
		}
	}

	private void saveActivitiUser(User user) {
		if (!Global.isSynActivitiIndetity()) {
			return;
		}
		String userId = user.getLoginName();// ObjectUtils.toString(user.getId());
		org.activiti.engine.identity.User activitiUser = identityService.createUserQuery().userId(userId)
				.singleResult();
		if (activitiUser == null) {
			activitiUser = identityService.newUser(userId);
		}
		activitiUser.setFirstName(user.getName());
		activitiUser.setLastName(StringUtils.EMPTY);
		activitiUser.setEmail(user.getEmail());
		activitiUser.setPassword(StringUtils.EMPTY);
		identityService.saveUser(activitiUser);

		// 删除用户与用户组关系
		List<Group> activitiGroups = identityService.createGroupQuery().groupMember(userId).list();
		for (Group group : activitiGroups) {
			identityService.deleteMembership(userId, group.getId());
		}
		// 创建用户与用户组关系
		for (Role role : user.getRoleList()) {
			String groupId = role.getEnname();
			// 如果该用户组不存在，则创建一个
			Group group = identityService.createGroupQuery().groupId(groupId).singleResult();
			if (group == null) {
				group = identityService.newGroup(groupId);
				group.setName(role.getName());
				group.setType(role.getRoleType());
				identityService.saveGroup(group);
			}
			identityService.createMembership(userId, role.getEnname());
		}
	}

	private void deleteActivitiUser(User user) {
		if (!Global.isSynActivitiIndetity()) {
			return;
		}
		if (user != null) {
			String userId = user.getLoginName();// ObjectUtils.toString(user.getId());
			identityService.deleteUser(userId);
		}
	}

	public List<User> queryMemberInfo(User user) {
		// 生成数据权限过滤条件（dsf为dataScopeFilter的简写，在xml中使用 ${sqlMap.dsf}调用权限SQL）
		user.getSqlMap().put("dsf", dataScopeFilter(user, "o", "a"));
		// 执行分页查询
		return userDao.queryMemberInfo(user);
	}

	/**
	 * 保存离职信息
	 * 
	 * @param userQuit
	 */
	@Transactional(readOnly = false)
	public void saveQuit(UserQuit userQuit) {
		ArrayList<UserQuit> list = Lists.newArrayList();
		// 添加交接人信息
		for (String s : userQuit.getHandovers()) {
			UserQuit ss = new UserQuit();
			ss.setId(IdGen.uuid());
			ss.setCreateBy(UserUtils.getUser());
			ss.setCreateTime(new Date());
			ss.setUserId(userQuit.getUserId());
			ss.setHandover(s);
			ss.setUserType("1");
			ss.setIsFinish("");
			ss.setUnfinishRemarks("");
			ss.setRemarks("");
			list.add(ss);
		}
		// 添加离职人本人
		UserQuit ss = new UserQuit();
		ss.setId(IdGen.uuid());
		ss.setCreateBy(UserUtils.getUser());
		ss.setCreateTime(new Date());
		ss.setUserId(userQuit.getUserId());
		ss.setUserType("0");
		ss.setIsFinish(userQuit.getIsFinish());
		ss.setHandover("");
		String frmk = userQuit.getUnfinishRemarks();
		if (StringUtils.isNotBlank(frmk)) {
			ss.setUnfinishRemarks(frmk);
		} else {
			ss.setUnfinishRemarks("");
		}
		String remarks = userQuit.getRemarks();
		if (StringUtils.isNotBlank(remarks)) {
			ss.setRemarks(remarks);
		} else {
			ss.setRemarks("");
		}
		list.add(ss);
		userQuitDao.batchInsert(list);
		// 改变用户状态，若有报销单据
		int bi = userQuitDao.queryExpenseFlow(userQuit.getUserId());
		User user = new User();
		user.setId(userQuit.getUserId());
		ArrayList<Role> lists = Lists.newArrayList();
		Role role = new Role();
		role.setName("离职");
		lists.add(roleDao.getByName(role));
		user.setRoleList(lists);
		user.preUpdate();
//		user.setPostIds(postInfoDao.getByPostName("离职").getId());
		if (bi > 0) {
			user.setUserStatus("2");
			user.setLoginFlag("");
			userDao.updateUserStatus(user);
			// // 更新用户与角色关联
			// userDao.deleteUserRole(user);
			// if (user.getRoleList() != null && user.getRoleList().size() > 0){
			// userDao.insertUserRole(user);
			// }else{
			// throw new ServiceException(user.getLoginName() + "没有设置角色！");
			// }
			// // 将当前用户同步到Activiti
			// saveActivitiUser(user);
			// // 清除用户缓存
			// UserUtils.clearCache(user);
			// // 清除权限缓存
			// systemRealm.clearAllCachedAuthorizationInfo();
		} else {// 无报销单据
			user.setUserStatus("3");
			user.setLoginFlag("0");
			userDao.updateUserStatus(user);
		}
		// 更新用户与角色关联
		userDao.deleteUserRole(user);
		userDao.insertUserRole(user);
		// 离职员工，不管前一条状态是啥，离职流程时强行插入一条待命，并第二天不显示在该报表中
		List<EmployeeStatusrecord> lis = intfzEmployeeStatusrecordService.queryEmployee(user);
		if (lis != null && lis.size() > 0) {
			EmployeeStatusSaveReq employeeStatusSaveReq = new EmployeeStatusSaveReq();
			employeeStatusSaveReq.setUserAction(Constant.EMPLOYEE_ACTION_EVINSITU);
			employeeStatusSaveReq.setRemarks("离职");
			employeeStatusSaveReq.setProjectId("");
			employeeStatusSaveReq.setProjectNodeId("");
			employeeStatusSaveReq.setBaseId("");
			intfzEmployeeStatusrecordService.saveEmployeeStatusrecord(user, employeeStatusSaveReq);
		}

		//离职人员所在的协作流程中移除
		needFlowService.removeNeedFlowByUserId(userQuit.getUserId());
		User u = UserUtils.get(userQuit.getUserId());
        UserUtils.clearCache(u);//清除用户缓存
		if (u.getOffice() != null && u.getOffice().getId() != null) {
			CacheUtils.remove(CacheUtils.USER_CACHE, CacheUtils.USER_CACHE_LIST_BY_OFFICE_ID_ + u.getOffice().getId());//清除在其归属部门中的缓存
		}


	}

	/**
	 * 查询离职人员未完结的单据
	 * 
	 * @param user
	 * @return
	 */
	public String queryBills(User user) {
        List<Map<String,String>> maps = userQuitDao.queryMap(user.getId());//查询所有的单据
        int cust ;// 客户
        int project ;// 项目
        int contract ;// 合同
        int need ;// 协作
        int bb = 0;// 待办
        StringBuilder s = new StringBuilder();
        int bi;
        int leader;//负责人
		JSONObject json = new JSONObject();
		if(maps != null && maps.size() > 0) {
			for (int i = 0; i < maps.size(); i++) {
				Map<String, String> each =  maps.get(i);
				json.put(each.get("name"), each.get("value"));
			}
		}
		cust = json.optInt("cust", 0);//客户管理
		project = json.optInt("project", 0);// 项目
		contract = json.optInt("contract", 0);// 合同
		need = json.optInt("need", 0);// 协作
		bb += json.optInt("needDone", 0);// 协作待办
		bb += json.optInt("needDonePrincipal", 0);//协作负责人
		bi = json.optInt("expenseFlowDone", 0);//报销待办
		leader = json.optInt("leader");//负责人
		List<String> roleLeaderName = userQuitDao.queryRoleLeader(user.getId());//流程角色
		if (cust == 0 && project == 0 && contract == 0 && need == 0 && bb == 0 && leader == 0 && roleLeaderName.size() == 0) {
			if (bi > 0) {
				return "1";
			}
			return null;
		}

		if (bb > 0) {
			s.append("该离职人还有剩余的待办任务未完成，不可提交该离职申请；");
		}

		if (cust > 0) {
			s.append("客户，");
		}
		if (project > 0) {
			s.append("项目，");
		}
		if (contract > 0) {
			s.append("合同，");
		}
		if (need > 0) {
			s.append("协作，");
		}
		if(leader > 0){
			s.append("部门负责人，");
		}
		if(roleLeaderName.size() > 0){
			String na = "";
			for(String name :roleLeaderName){
				na = na + name + "," ;
			}
			s.append(na);
		}
		String mes = s.toString().substring(0, s.toString().length() - 1);
		if (cust != 0 || project != 0 || contract != 0 || need != 0 || leader != 0 || roleLeaderName.size() > 0) {
			mes = "该离职人员还有:" + mes + "这些功能的负责人未转出，不可提交该离职申请；";
		}
		return mes;
	}

	/**
	 * 再次入职
	 * @param user
	 */
	@Transactional(readOnly = false)
	public void entry(User user) {
		user.preUpdate();
		userDao.entry(user);
		userQuitDao.deleteQuit(user.getId());
		user = UserUtils.get(user.getId());
		UserUtils.clearCache(user);
		if (user.getOffice() != null && user.getOffice().getId() != null) {
			CacheUtils.remove(CacheUtils.USER_CACHE, CacheUtils.USER_CACHE_LIST_BY_OFFICE_ID_ + user.getOffice().getId());//清除在其归属部门中的缓存
		}
	}

	public List<User> queryUserInfo() {
		return userDao.findList(new User());
	}

    public User getUserByMail(String mail) {
		return UserUtils.getUserByMail(mail);
    }

    ///////////////// Synchronized to the Activiti end //////////////////

    public List<UserRespForBI> findAllColumnsForBI(){
	    return userDao.findAllColumnsForBI();
    }

	public int checkMobile(User user) {
		return userDao.checkMobile(user);
	}

	public int checkLoginName(User user) {
		return userDao.checkLoginName(user);
	}
}
