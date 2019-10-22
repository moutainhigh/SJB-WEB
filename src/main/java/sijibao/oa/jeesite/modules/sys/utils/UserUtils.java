package sijibao.oa.jeesite.modules.sys.utils;

import java.security.Principal;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sijibao.oa.common.service.BaseService;
import com.sijibao.oa.common.utils.CacheUtils;
import com.sijibao.oa.common.utils.JedisUtils;
import com.sijibao.oa.common.utils.SpringContextHolder;
import com.sijibao.oa.modules.oa.dao.CustInfoDao;
import com.sijibao.oa.modules.oa.entity.CustInfo;
import com.sijibao.oa.modules.sys.dao.*;
import com.sijibao.oa.modules.sys.entity.*;
import com.sijibao.oa.modules.sys.security.SystemAuthorizingRealm.Principal;
import sijibao.oa.jeesite.modules.sys.dao.*;
import sijibao.oa.jeesite.modules.sys.entity.User;
import sijibao.oa.jeesite.utils.SpringContextHolder;

/**
 * 用户工具类
 * @author ThinkGem
 * @version 2013-12-05
 */
public class UserUtils {
	
    protected static Logger logger = LoggerFactory.getLogger(UserUtils.class.getName());

	private static UserDao userDao = SpringContextHolder.getBean(UserDao.class);
	private static RoleDao roleDao = SpringContextHolder.getBean(RoleDao.class);
	private static MenuDao menuDao = SpringContextHolder.getBean(MenuDao.class);
	private static AreaDao areaDao = SpringContextHolder.getBean(AreaDao.class);
	private static OfficeDao officeDao = SpringContextHolder.getBean(OfficeDao.class);
	private static PostInfoDao postInfoDao = SpringContextHolder.getBean(PostInfoDao.class);
	private static CustInfoDao custInfoDao = SpringContextHolder.getBean(CustInfoDao.class);

	//session中缓存的用户相关信息
    public static final String AUTH_INFO = "authInfo";//当前用户登录认证信息
    public static final String ROLE_LIST = "roleList";//当前用户的角色
    public static final String MENU_LIST = "menuList";//当前用户有权限访问的菜单
    private static final String AREA_LIST = "areaList";//当前用户有权限访问的区域
    public static final String OFFICE_LIST = "officeList";//当前用户有权限访问的部门
    public static final String OFFICE_LIST_MARKET = "officeMarketList";//当前用户有权限访问的市场部门及其后代部门

	/**
	 * 根据ID获取用户
	 * @param id 用户id
	 * @return 取不到返回null
	 */
	public static User get(String id){
		User user = (User)CacheUtils.get(CacheUtils.USER_CACHE, CacheUtils.USER_CACHE_ID_ + id);
		if (user ==  null){
			user = userDao.get(id);
			if (user == null){
				return null;
			}
			user.setRoleList(roleDao.findList(new Role(user)));
            putIntoCache(user);
		}
		return user;
	}
	
	/**
	 * 根据登录名获取用户
	 * @param loginName 用户登录名
	 * @return User
	 */
	public static User getByLoginName(String loginName){
		User user = (User)CacheUtils.get(CacheUtils.USER_CACHE, CacheUtils.USER_CACHE_LOGIN_NAME_ + loginName);
		if (user == null){
			user = userDao.getByLoginName(new User(null, loginName));
			if (user == null){
				return null;
			}
			user.setRoleList(roleDao.findList(new Role(user)));
            putIntoCache(user);
		}
		return user;
	}

	/**
	 * 根据钉钉ID获取用户
	 * @param wechatId 用户钉钉id
	 * @return User
	 */
	public static User getByWechatId(String wechatId){
		if(StringUtils.isBlank(wechatId)){
			return null;
		}
		User user = (User)CacheUtils.get(CacheUtils.USER_CACHE, CacheUtils.USER_CACHE_WECHAT_ID_ + wechatId);
		if (user == null){
			user = userDao.getByWechatId(new User(null, null, null, wechatId,null));
			if (user == null) {
				return null;
			}
			user.setRoleList(roleDao.findList(new Role(user)));
            putIntoCache(user);
		}
		return user;
	}
	
	/**
	 * 根据手机号获取用户
	 * @param phone 用户手机号
	 * @return User
	 */
	public static User getByPhone(String phone){
		if(StringUtils.isBlank(phone)){
			return null;
		}
        User user = (User)CacheUtils.get(CacheUtils.USER_CACHE, CacheUtils.USER_CACHE_MOBILE_ + phone);
        if (user == null){
            user = userDao.getByPhone(new User(null, null, null,null, phone));
            if (user == null) {
                return null;
            }
            user.setRoleList(roleDao.findList(new Role(user)));
            putIntoCache(user);
        }
        return user;
	}

    /**
     * 根据邮箱获取用户
     * @param mail 用户邮箱
     * @return User
     */
    public static User getUserByMail(String mail) {
        return userDao.getUserByMail(mail);
    }

    /**
     * 把用户对象放入redis缓存
     * @param user 用户对象
     */
    private static void putIntoCache(User user){
        CacheUtils.put(CacheUtils.USER_CACHE, CacheUtils.USER_CACHE_ID_ + user.getId(), user);
        CacheUtils.put(CacheUtils.USER_CACHE, CacheUtils.USER_CACHE_LOGIN_NAME_ + user.getLoginName(), user);
        CacheUtils.put(CacheUtils.USER_CACHE, CacheUtils.USER_CACHE_MOBILE_ + user.getMobile(), user);
        CacheUtils.put(CacheUtils.USER_CACHE, CacheUtils.USER_CACHE_WECHAT_ID_ + user.getWechatId(), user);
    }

	/**
	 * 清除当前用户session中保存的数据，以及redis中缓存的数据
	 */
	public static void clearCache(User user){
	    if(user == null || StringUtils.isBlank(user.getId()))
	        return;

        removeFromSession(UserUtils.AUTH_INFO);
        removeFromSession(UserUtils.ROLE_LIST);
        removeFromSession(UserUtils.MENU_LIST);
        removeFromSession(UserUtils.AREA_LIST);
        removeFromSession(UserUtils.OFFICE_LIST);
        removeFromSession(UserUtils.OFFICE_LIST_MARKET);

		CacheUtils.remove(CacheUtils.USER_CACHE, CacheUtils.USER_CACHE_ID_ + user.getId());
		CacheUtils.remove(CacheUtils.USER_CACHE, CacheUtils.USER_CACHE_LOGIN_NAME_ + user.getLoginName());
		CacheUtils.remove(CacheUtils.USER_CACHE, CacheUtils.USER_CACHE_LOGIN_NAME_ + user.getOldLoginName());
		CacheUtils.remove(CacheUtils.USER_CACHE, CacheUtils.USER_CACHE_MOBILE_ + user.getMobile());
		CacheUtils.remove(CacheUtils.USER_CACHE, CacheUtils.USER_CACHE_WECHAT_ID_ + user.getWechatId());
	}

    /**
     * 清除指定部门下的人员缓存
     * @param officeId 部门ID
     */
	public static void clearUserCacheListByOfficeId(String officeId){
	    CacheUtils.remove(CacheUtils.USER_CACHE, CacheUtils.USER_CACHE_LIST_BY_OFFICE_ID_ + officeId);
    }

	/**
	 * 根据sessionId清除指定用户会话缓存
	 * @param sessionId 会话ID
	 */
	public static void clearSession(String sessionId){
		JedisUtils.delObject(JedisUtils.KEY_PREFIX+"_"+sessionId);
	}
	
	/**
	 * 获取当前用户
	 * @return 取不到返回 new User()
	 */
	public static User getUser(){
		Principal principal = getPrincipal();
		if (principal!=null){
			User user = get(principal.getId());
			if (user != null){
				return user;
			}
			return new User();
		}
		// 如果没有登录，则返回实例化空的User对象。
		return new User();
	}
	
	/**
	 * 根据sessionId获取当前用户
	 * @return User
	 */
	public static User getUser(String sessionid){
		Principal principal = getPrincipal(sessionid);
		if (principal!=null){
			User user = get(principal.getId());
			if (user != null){
				return user;
			}
			return new User();
		}
		// 如果没有登录，则返回实例化空的User对象。
		return new User();
	}
	
	/**
	 * 获取当前用户角色列表
	 * @return List<Role>
	 */
	public static List<Role> getRoleList(){
		@SuppressWarnings("unchecked")
		List<Role> roleList = (List<Role>) getFromSession(UserUtils.ROLE_LIST);
		if (roleList == null){
			User user = getUser();
			if (user.isAdmin()){
				roleList = roleDao.findAllList(new Role());
			}else{
				Role role = new Role();
				role.getSqlMap().put("dsf", BaseService.dataScopeFilter(user.getCurrentUser(), "o", "u"));
				roleList = roleDao.findList(role);
			}
			putIntoSession(UserUtils.ROLE_LIST, roleList);
		}
		return roleList;
	}
	
	/**
	 * 获取当前用户有权限访问的菜单
	 * @return List<Menu>
	 */
	public static List<Menu> getMenuList(){
		@SuppressWarnings("unchecked")
		List<Menu> menuList = (List<Menu>) getFromSession(UserUtils.MENU_LIST);
		if (menuList == null){
			User user = getUser();
			if (user.isAdmin()){
				menuList = menuDao.findAllList(new Menu());
			}else{
				Menu m = new Menu();
				m.setUserId(user.getId());
				menuList = menuDao.findByUserId(m);
			}
			putIntoSession(UserUtils.MENU_LIST, menuList);
		}
		return menuList;
	}
	
	/**
	 * 获取当前用户有权限访问的区域
	 * @return List<Area>
	 */
	public static List<Area> getAreaList(){
		@SuppressWarnings("unchecked")
		List<Area> areaList = (List<Area>) getFromSession(UserUtils.AREA_LIST);
		if (areaList == null){
			areaList = areaDao.findAllList(new Area());
			putIntoSession(UserUtils.AREA_LIST, areaList);
		}
		return areaList;
	}
	
	/**
	 * 获取当前用户有权限访问的部门
	 * @return List<Office>
	 */
	public static List<Office> getOfficeList(){
		@SuppressWarnings("unchecked")
		List<Office> officeList = (List<Office>) getFromSession(UserUtils.OFFICE_LIST);
		if (officeList == null){
			User user = getUser();
			if (user.isAdmin()){
				officeList = officeDao.findAllList(new Office());
			}else{
				Office office = new Office();
				office.getSqlMap().put("dsf", BaseService.dataScopeFilter(user, "a", ""));
				officeList = officeDao.findList(office);
			}
			putIntoSession(UserUtils.OFFICE_LIST, officeList);
		}
		return officeList;
	}

    /**
     * 获取当前用户有权限访问的营销中心或其后代部门
     * @param user 当前用户
     * @return List<Office>
     */
    public static List<Office> getMarketOfficeList(User user) {
        @SuppressWarnings("unchecked")
//        List<Office> officeList = (List<Office>) getFromSession(UserUtils.OFFICE_LIST_MARKET);
		List<Office> officeList = null;
        if (officeList == null){
            if (user.isAdmin()){
                officeList = officeDao.queryMarketOffice(new Office());
            }else{
                Office office = new Office();
                office.setUser(user);
                office.getSqlMap().put("dsf", BaseService.dataScopeFilter(user, "a", ""));
                officeList = officeDao.queryMarketOffice(office);
            }
            putIntoSession(UserUtils.OFFICE_LIST_MARKET, officeList);
        }
        return officeList;
    }
	
	/**
	 * 获取系统所有岗位
	 * @return List<PostInfo>
	 */
	public static List<PostInfo> getPostInfoAllList(){
		@SuppressWarnings("unchecked")
		List<PostInfo> postList = (List<PostInfo>) getFromSession(CacheUtils.SYS_CACHE_POST_ALL_LIST);
		if (postList == null){
			postList = postInfoDao.findAllList(new PostInfo());
		}
		return postList;
	}
	
	/**
	 * 获取系统所有客户信息
	 * @return List<CustInfo>
	 */
	public static List<CustInfo> getCustInfoAllList(){
		return custInfoDao.findList(new CustInfo());
	}
	
	/**
	 * 获取授权主要对象
	 */
	public static Subject getSubject(){
		return SecurityUtils.getSubject();
	}
	
	/**
	 * 获取当前登录者对象
	 */
	public static Principal getPrincipal(){
		try{
			Subject subject = SecurityUtils.getSubject();
			Principal principal = (Principal)subject.getPrincipal();
			if (principal != null){
				return principal;
			}
//			subject.logout();
		}catch (UnavailableSecurityManagerException | InvalidSessionException e) {
			logger.error(e.getMessage());
            e.printStackTrace();
        }
        return null;
	}
	
	/**
	 * 根据会话id获取当前登录者对象
	 * @return 取不到返回null
	 */
	public static Principal getPrincipal(String sessionid){
		Object obj= JedisUtils.getObject(JedisUtils.KEY_PREFIX+"_"+sessionid);
		if (obj!=null){
			if(obj instanceof Principal){
				return (Principal) obj;
			}
		}
		return null;
	}
	
	public static Session getSession(){
		try{
			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession(false);
			if (session == null){
				session = subject.getSession();
			}
			if (session != null){
				return session;
			}
//			subject.logout();
		}catch (InvalidSessionException e){
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	// ============== user session cache（通过attribute缓存用户相关数据） ==============
	
	public static Object getFromSession(String key) {
		return getFromSession(key, null);
	}
	
	public static Object getFromSession(String key, Object defaultValue) {
//		Object obj = getCacheMap().get(key);
		Object obj = getSession().getAttribute(key);
		return obj==null?defaultValue:obj;
	}

	public static void putIntoSession(String key, Object value) {
//		getCacheMap().put(key, value);
		getSession().setAttribute(key, value);
	}

	public static void removeFromSession(String key) {
//		getCacheMap().remove(key);
		getSession().removeAttribute(key);
	}

	public static void removeFromSession(String key,Role role) {
//		getCacheMap().remove(key);
		getSession().removeAttribute(key);
		//清除用户缓存
		List<String> ids = UserUtils.queryUserIdsByRoleId(role.getId());
		if(ids != null && ids.size() > 0){//遍历清缓存
			for (String s : ids) {
				UserUtils.clearCache(UserUtils.get(s));
			}
		}
	}

	public static List<User> getUserByIds(List<String> deputyPersonIdList) {
		if(deputyPersonIdList != null && deputyPersonIdList.size() > 0){
			return userDao.getUserByIds(deputyPersonIdList);
		}else{
			return null;
		}
	}

    public static List<String> queryUserIdsByOfficeId(String officeId) {
		return userDao.queryUserIdsByOfficeId(officeId);
    }

	public static List<String> queryUserIdsByRoleId(String roleId) {
		return userDao.queryUserIdsByRoleId(roleId);
	}

	public static List<String> queryUserIdsByPostId(String postId) {
		return userDao.queryUserIdsByPostId(postId);
	}

//	public static Map<String, Object> getCacheMap(){
//		Principal principal = getPrincipal();
//		if(principal!=null){
//			return principal.getCacheMap();
//		}
//		return new HashMap<String, Object>();
//	}

	/**
	 * 获取微服务需要的user对象
	 * @param user
	 * @return
	 * Role,postInfoList
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws BeansException 
	 */
//	public static com.sijibao.oa.sys.provider.entity.User getUserByUser(User user) throws BeansException, InstantiationException, IllegalAccessException{
//		com.sijibao.oa.sys.provider.entity.User u = new com.sijibao.oa.sys.provider.entity.User();
//		u.setId(user.getId());
//		u.setName(user.getName());
//		u.setPhone(user.getPhone());
//		u.setMobile(user.getMobile());
//		com.sijibao.oa.sys.provider.entity.Office office = new com.sijibao.oa.sys.provider.entity.Office();
//		if(user.getOffice() != null){
//			office.setId(user.getOffice().getId());
//			office.setCode(user.getOffice().getCode());
//			office.setName(user.getOffice().getName());
//		}
//		com.sijibao.oa.sys.provider.entity.Role role = new com.sijibao.oa.sys.provider.entity.Role();
//		if(user.getRole() != null){
//			role.setId(user.getRole().getId());
//			role.setName(user.getRole().getName());
//			
//		}
//		ArrayList<com.sijibao.oa.sys.provider.entity.PostInfo> list = Lists.newArrayList();
//		for (PostInfo postInfo : user.getPostInfoList()) {
//			com.sijibao.oa.sys.provider.entity.PostInfo ss = new com.sijibao.oa.sys.provider.entity.PostInfo();
//			ss.setId(postInfo.getId());
//			ss.setPostName(postInfo.getPostName());
//			list.add(ss);
//		}
////		ArrayList<com.sijibao.oa.sys.provider.entity.Role> list1 = Lists.newArrayList();
////		for (com.sijibao.oa.modules.sys.entity.Role s : user.getRoleList()) {
////			com.sijibao.oa.sys.provider.entity.Role ss = new com.sijibao.oa.sys.provider.entity.Role();
////			ss.setId(user.getRole().getId());
////			ss.setName(user.getRole().getName());
////			list1.add(ss);
////		}
//		u.setOffice(office);
//		u.setRole(role);
////		u.setRoleList(list1);
//		u.setPostInfoList(list);
//		return u;
//		
//	}
	
//	public static <T> T sssss(Object source, Class<T> targetClass) {
//        if (source == null)
//        {
//            return null;
//        }
//        T target = null;
//        try {
//            target = targetClass.newInstance();
//        } catch (InstantiationException e) {
//            logger.error(e.getMessage(), e);
//        } catch (IllegalAccessException e) {
//            logger.error(e.getMessage(), e);
//        }
//        BeanUtils.copyProperties(source, target);
//        return target;
//    }
}
