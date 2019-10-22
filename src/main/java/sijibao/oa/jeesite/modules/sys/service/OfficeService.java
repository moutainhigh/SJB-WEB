/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.sys.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sijibao.oa.common.service.BaseService;
import com.sijibao.oa.common.service.TreeService;
import com.sijibao.oa.common.utils.CacheUtils;
import com.sijibao.oa.modules.intfz.response.bi.OfficeRespForBI;
import com.sijibao.oa.modules.sys.dao.OfficeDao;
import com.sijibao.oa.modules.sys.entity.Office;
import com.sijibao.oa.modules.sys.entity.User;
import com.sijibao.oa.modules.sys.utils.UserUtils;

/**
 * 机构Service
 *
 * @author 张江浩
 * @version 2019年7月7日
 */
@Service
@Transactional(readOnly = true)
public class OfficeService extends TreeService<OfficeDao, Office> {

    @Autowired
    private OfficeDao officeDao;

    /**
     * 根据id精确搜索
     *
     * @param id 部门名称
     * @return Office
     */
    public Office get(String id) {
        return dao.get(new Office(id));
    }

    /**
     * 根据名称精确搜索
     *
     * @param name 部门名称
     * @return Office
     */
    public Office getByName(String name) {
        return officeDao.getByName(name);
    }

    /**
     * 查询当前用户有权访问的部门
     *
     * @return List<Office>
     */
    public List<Office> findAll() {
        return UserUtils.getOfficeList();
    }

    /**
     * 获取系统中所有部门，或者，获取当前用户有权访问的部门
     *
     * @param isAll true表示获取所有部门，false表示获取当前用户有权访问的部门
     * @return List<Office>
     */
    public List<Office> findList(Boolean isAll) {
        if (isAll != null && isAll) {
            return getOfficeAllList();
        } else {
            return UserUtils.getOfficeList();
        }
    }

    /**
     * 获取系统中所有部门
     *
     * @return List<Office>
     */
    private List<Office> getOfficeAllList() {
        @SuppressWarnings("unchecked")
        List<Office> officeList = (List<Office>) CacheUtils.get(CacheUtils.SYS_CACHE_OFFICE_ALL_LIST);
        if (officeList == null) {
            officeList = officeDao.findAllList(new Office());
            CacheUtils.put(CacheUtils.SYS_CACHE, CacheUtils.SYS_CACHE_OFFICE_ALL_LIST, officeList);
        }
        return officeList;
    }

    /**
     * 获取当前用户有权限访问的营销中心或其后代部门
     *
     * @param user 当前用户
     * @return List<Office>
     */
    public List<Office> queryMarketOffice(User user) {
        return UserUtils.getMarketOfficeList(user);
    }

    /**
     * 查找指定部门的下一级部门
     *
     * @param office 指定部门
     * @return List<Office>
     */
    public List<Office> findChildOfficeList(Office office) {
        return dao.findByParentId(office);
    }

    /**
     * 查找指定部门的所有后代部门
     *
     * @param office 指定部门
     * @return List<Office>
     */
    public List<Office> findListLike(Office office) {
        List<Office> ol = new ArrayList<>();
        if (office != null) {
            return dao.findDescendants(office);
        }
        return ol;
    }

    /**
     * 给BI系统提供全字段数据作同步
     *
     * @return List<OfficeRespForBI>
     */
    public List<OfficeRespForBI> findAllColumnsForBI() {
        return officeDao.findAllColumnsForBI();
    }

    /**
     * 新增/编辑部门
     *
     * @param office 待新增/编辑部门
     */
    @Transactional
    public void save(Office office) {
        super.save(office);
        clearOfficeCache(office);
    }

    /**
     * 删除部门（逻辑删除）
     *
     * @param office 待删除部门
     */
    @Transactional
    public void delete(Office office) {
        super.delete(office);
        clearOfficeCache(office);
    }

    /**
     * 停用指定部门的后代部门
     *
     * @param office 指定部门
     */
    @Transactional
    public void disableChildOffice(Office office) {
        List<Office> childOfficeList = dao.findDescendants(office);
        dao.disableChildOffice(office);

        //清除后代部门的缓存
        for (Office o: childOfficeList) {
            CacheUtils.remove(CacheUtils.USER_CACHE, CacheUtils.USER_CACHE_LIST_BY_OFFICE_ID_ + o.getId());
        }
    }

    /**
     * 清除redis中缓存的所有office数据，以及归属于指定office的人员数据
     *
     * @param office 指定部门
     */
    private void clearOfficeCache(Office office) {
        CacheUtils.remove(CacheUtils.SYS_CACHE, CacheUtils.SYS_CACHE_OFFICE_ALL_LIST);
        List<String> ids = UserUtils.queryUserIdsByOfficeId(office.getId());//清除部门下的用户缓存
        if(ids != null && ids.size() > 0){//遍历清缓存
            for (String s : ids) {
                UserUtils.clearCache(UserUtils.get(s));
            }
        }
        CacheUtils.remove(CacheUtils.USER_CACHE, CacheUtils.USER_CACHE_LIST_BY_OFFICE_ID_ + office.getId());
    }

    public List<User> getUserExclusionExEmployee() {
        return officeDao.getUserExclusionExEmployee();
    }


    public List<Map<String, Object>> queryDeptUserTree() {
        User user = UserUtils.getUser();
        if (user.isAdmin()){
            return officeDao.queryDeptUserTree(new Office());
        }else{
            Office office = new Office();
            office.getSqlMap().put("dsf", BaseService.dataScopeFilter(user, "a", ""));
            return officeDao.queryDeptUserTree(office);
        }
    }
}
