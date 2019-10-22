/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.oa.dao;

import java.util.List;

import com.sijibao.oa.common.persistence.CrudDao;
import com.sijibao.oa.common.persistence.annotation.MyBatisDao;
import com.sijibao.oa.modules.oa.entity.MessageInfo;

/**
 * 消息DAO接口
 * @author wanxb
 * @version 2019-04-25
 */
@MyBatisDao
public interface MessageInfoDao extends CrudDao<MessageInfo> {

    int findCount(MessageInfo message);

    int findRedCount(MessageInfo message);

    void beachInsert(List<MessageInfo> list);

    int updateReadStatus(MessageInfo message);
}