package sijibao.oa.jeesite.modules.intfz.service.common;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.sijibao.oa.common.config.Global;
import com.sijibao.oa.common.service.BaseService;
import com.sijibao.oa.common.utils.Encodes;
import com.sijibao.oa.modules.intfz.bean.MenuInfo;
import com.sijibao.oa.modules.sys.dao.MenuDao;
import com.sijibao.oa.modules.sys.entity.Menu;
import com.sijibao.oa.modules.sys.entity.User;
import com.sijibao.oa.modules.sys.utils.SysParamsUtils;

/**
 * 登录服务类
 * @author Petter
 */
@Service
@Transactional(readOnly = true)
public class IntfzWebLoginService extends BaseService {

	@Autowired
	private MenuDao menuDao;
	
	public List<MenuInfo> getMenuList(User user){
		
		String rootId = SysParamsUtils.getParamValue(Global.WEB_MENU_ROOT, Global.SYS_PARAMS, "");
		Menu query = new Menu(); 
		query.setUserId(user.getId());
		query.setParentIds("%,"+rootId+",%");
		List<Menu> menuList = menuDao.findByUserIdAndParentIds(query);
		if(menuList != null && menuList.size() > 0){
			List<MenuInfo> list = Lists.newArrayList();
			for(Menu menu : menuList){
				MenuInfo menuInfo = JSONObject.parseObject(Encodes.unescapeHtml(menu.getRemarks()), MenuInfo.class);
				if(rootId.equals(menu.getParentId())){
					menuInfo.setParentId("0");
				}else{
					menuInfo.setParentId(menu.getParentId());
				}
				menuInfo.setId(menu.getId());
				list.add(menuInfo);
			}
			return list;
		}
		return null;
	}
	
	public List<String> getPermissionList(User user){
		
		String rootId = SysParamsUtils.getParamValue(Global.WEB_MENU_ROOT, Global.SYS_PARAMS, "");
		Menu query = new Menu(); 
		query.setUserId(user.getId());
		query.setParentIds("%,"+rootId+",%");
		List<Menu> menuList = menuDao.findByUserIdAndParentIds(query);
		if(menuList != null && menuList.size() > 0){
			List<String> list = Lists.newArrayList();
			for(Menu menu : menuList){
				list.add(menu.getRemarks());
			}
			return list;
		}
		return null;
	}
	
}
