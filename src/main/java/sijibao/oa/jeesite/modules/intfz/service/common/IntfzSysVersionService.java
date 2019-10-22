package sijibao.oa.jeesite.modules.intfz.service.common;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.sijibao.oa.common.persistence.Page;
import com.sijibao.oa.common.service.BaseService;
import com.sijibao.oa.common.utils.DateUtils;
import com.sijibao.oa.modules.intfz.request.common.SysVersionHandleReq;
import com.sijibao.oa.modules.intfz.response.common.SysVersionResponse;
import com.sijibao.oa.modules.sys.entity.SysVersion;
import com.sijibao.oa.modules.sys.entity.User;
import com.sijibao.oa.modules.sys.service.SysVersionService;

/**
 * 系统版本维护服务层
 * @author xuby
 */
@Service
@Transactional(readOnly = true)
public class IntfzSysVersionService extends BaseService{
	@Autowired
	private SysVersionService sysVersionService;
	public Page<SysVersionResponse> findPage(Page<SysVersion> page, SysVersionHandleReq req, User user) {
		SysVersion sys = new SysVersion();
		sys.setVersionNo(req.getVersionNo());
		if(StringUtils.isNotBlank(req.getStart())){
			sys.setStart(DateUtils.parse(req.getStart(), DateUtils.YYYY_MM_DD_HH_MM_SS));
		}
		if(StringUtils.isNotBlank(req.getEnd())){
			sys.setEnd(DateUtils.parse(req.getEnd(), DateUtils.YYYY_MM_DD_HH_MM_SS));
		}
		Page<SysVersion> find = sysVersionService.findPage(page, sys);
		Page<SysVersionResponse> p = new Page<SysVersionResponse>();
		p.setCount(find.getCount());
		p.setPageNo(find.getPageNo());
		p.setPageSize(find.getPageSize());
		ArrayList<SysVersionResponse> list = Lists.newArrayList();
		for (SysVersion s : find.getList()) {
			SysVersionResponse sv = new SysVersionResponse();
			sv.setVersionNo(s.getVersionNo());
			sv.setContext(s.getContext());
			if(s.getCreateTime() != null){
				sv.setCreateTime(DateUtils.format(s.getCreateTime(), DateUtils.YYYY_MM_DD_HH_MM_SS));
			}
			list.add(sv);
		}
		p.setList(list);
		return p;
	}
	/**
	 * 不带分页查询列表
	 * @param req
	 * @return
	 */
	public List<SysVersionResponse> findList(SysVersionHandleReq req) {
		SysVersion sys = new SysVersion();
		sys.setVersionNo(req.getVersionNo());
		if(StringUtils.isNotBlank(req.getStart())){
			sys.setStart(DateUtils.parse(req.getStart(), DateUtils.YYYY_MM_DD_HH_MM_SS));
		}
		if(StringUtils.isNotBlank(req.getEnd())){
			sys.setEnd(DateUtils.parse(req.getEnd(), DateUtils.YYYY_MM_DD_HH_MM_SS));
		}
		List<SysVersion> find = sysVersionService.findList(sys);
		ArrayList<SysVersionResponse> list = Lists.newArrayList();
		for (SysVersion s : find) {
			SysVersionResponse sv = new SysVersionResponse();
			sv.setVersionNo(s.getVersionNo());
			sv.setContext(s.getContext());
			if(s.getCreateTime() != null){
				sv.setCreateTime(DateUtils.format(s.getCreateTime(), DateUtils.YYYY_MM_DD_HH_MM_SS));
			}
			list.add(sv);
		}
		return list;
	}

    public String queryNewVersion() {
		return sysVersionService.queryNewVersion();
    }
}
