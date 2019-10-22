package sijibao.oa.jeesite.modules.intfz.service.intfzweb;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.sijibao.oa.common.persistence.Page;
import com.sijibao.oa.common.service.BaseService;
import com.sijibao.oa.common.service.ServiceException;
import com.sijibao.oa.common.utils.DateUtils;
import com.sijibao.oa.common.utils.StringUtils;
import com.sijibao.oa.modules.intfz.request.intfzwebreq.projectinfo.*;
import com.sijibao.oa.modules.intfz.response.intfzwebresp.projectinfo.MainProjectInfoDetailResponse;
import com.sijibao.oa.modules.intfz.response.intfzwebresp.projectinfo.MainProjectInfoResponse;
import com.sijibao.oa.modules.intfz.response.intfzwebresp.projectinfo.MainProjectMaintenanceResponse;
import com.sijibao.oa.modules.intfz.response.intfzwebresp.projectinfo.MainProjectNodeDetailResponse;
import com.sijibao.oa.modules.oa.dao.ProjectInfoDao;
import com.sijibao.oa.modules.oa.dao.ProjectMaintenanceDao;
import com.sijibao.oa.modules.oa.dao.ProjectNodeDao;
import com.sijibao.oa.modules.oa.entity.ProjectInfo;
import com.sijibao.oa.modules.oa.entity.ProjectMaintenance;
import com.sijibao.oa.modules.oa.entity.ProjectNode;
import com.sijibao.oa.modules.oa.service.CustInfoService;
import com.sijibao.oa.modules.oa.service.ProjectInfoService;
import com.sijibao.oa.modules.oa.service.ProjectMaintenanceService;
import com.sijibao.oa.modules.oa.utils.CodeUtils;
import com.sijibao.oa.modules.sys.entity.User;
import com.sijibao.oa.modules.sys.service.OfficeService;
import com.sijibao.oa.modules.sys.utils.DictUtils;
import com.sijibao.oa.modules.sys.utils.UserUtils;

/**
 * 项目管理servers(web端) 
 * @author xuby
 */
@Service
@Transactional(readOnly = true)
public class IntfzWebProjectInfoService1 extends BaseService{
	@Autowired
	private ProjectInfoService projectInfoService;
	@Autowired
	private OfficeService officeService;
	@Autowired
	private CustInfoService custInfoService;
	@Autowired
	private ProjectInfoDao projectInfoDao;
	@Autowired
	private ProjectMaintenanceDao projectMaintenanceDao;
	@Autowired
	private ProjectMaintenanceService projectMaintenanceService;
//	@Autowired
//	private ProjectNodeService projectNodeService ;
	@Autowired
	private ProjectNodeDao projectNodeDao;
	
	/**
	 * 1项目管理-查询列表
	 * @param page
	 * @param req
	 * @return
	 * @throws ParseException 
	 */
	public Page<MainProjectInfoResponse> findPage(Page<ProjectInfo> page, MainProjectInfoHandleReq req){
		ProjectInfo projectInfo = new ProjectInfo();
		projectInfo.setProjectName(req.getProjectName());
		projectInfo.setProjectType(req.getProjectType());
		projectInfo.setOffice(officeService.get(req.getOfficeId()));
		projectInfo.setProjectState(req.getProjectState());
		projectInfo.setTimeType(req.getTimeType());
		projectInfo.setStart(DateUtils.parseDate(req.getApplyTimeStart()));
		projectInfo.setEnd(DateUtils.parseDate(req.getApplyTimeEnd()));
		Page<ProjectInfo> findPage = projectInfoService.findPage(page, projectInfo);
		Page<MainProjectInfoResponse> newPage =new Page<MainProjectInfoResponse>();
		newPage.setPageNo(findPage.getPageNo());
		newPage.setPageSize(findPage.getPageSize());
		newPage.setCount(findPage.getCount());
		ArrayList<MainProjectInfoResponse> list = Lists.newArrayList();
		for (ProjectInfo project : findPage.getList()) {
			MainProjectInfoResponse pro = new MainProjectInfoResponse();
			pro.setId(project.getId());
			pro.setProjectName(project.getProjectName());
			pro.setProjectCode(project.getProjectCode());
			pro.setProjectTypeName(DictUtils.getDictLabel(project.getProjectType(), "project_type", ""));
			if(project.getOffice() != null){
				pro.setOfficeName(project.getOffice().getName());
			}
			if(project.getCustInfo() != null){
				pro.setCustInfoId(project.getCustInfo().getId());
				pro.setCustInfoName(project.getCustInfo().getCustName());
			}
			pro.setProjectStateName(DictUtils.getDictLabel(project.getProjectState(), "project_state", ""));
			if(project.getOnLineDate() !=null){
				pro.setOnLineDate(project.getOnLineDate().getTime());
			}
			if(project.getImpleLeader() != null){
				pro.setImpleLeaderName(project.getImpleLeader().getName());
			}
			if(project.getMarketLeader() != null){
				pro.setMarketLeaderName(project.getMarketLeader().getName());
			}
			if(project.getProjectLeader() != null){
				pro.setProjectLeaderName(project.getProjectLeader().getName());
			}
			if(project.getUpdateTime() != null){
				pro.setUpdateTime(project.getUpdateTime().getTime());
			}
			pro.setRemarks(project.getRemarks());
			list.add(pro);
		}
		newPage.setList(list);
		return newPage;
	}
	/**
	 * 2项目管理-详细信息
	 * @param id
	 * @return
	 */
	public MainProjectInfoDetailResponse getProjectInfoDetailById(String id) {
		ProjectInfo projectInfo = projectInfoService.get(id);
		MainProjectInfoDetailResponse pro = new MainProjectInfoDetailResponse();
		pro.setId(id);
		pro.setProjectName(projectInfo.getProjectName());
		pro.setProjectCode(projectInfo.getProjectCode());
		pro.setProjectTypeName(projectInfo.getProjectType());
		pro.setProjectState(projectInfo.getProjectState());
		pro.setOfficeId(projectInfo.getOffice().getId());
		pro.setOfficeName(projectInfo.getOffice().getName());
		pro.setCustInfoId(projectInfo.getCustInfo().getId());
		pro.setCustInfoName(projectInfo.getCustInfo().getCustName());
		if(projectInfo.getOnLineDate() != null){
			pro.setOnLineDate(projectInfo.getOnLineDate().getTime());
		}
		if(projectInfo.getProjectLeader() != null){
			pro.setProjectLeaderId(projectInfo.getProjectLeader().getId());
			pro.setProjectLeaderName(projectInfo.getProjectLeader().getName());
		}
		if(projectInfo.getMarketLeader() != null){
			pro.setMarketLeaderId(projectInfo.getMarketLeader().getId());
			pro.setMarketLeaderName(projectInfo.getMarketLeader().getName());
		}
		if(projectInfo.getImpleLeader() != null){
			pro.setImpleLeaderId(projectInfo.getImpleLeader().getId());
			pro.setImpleLeaderName(projectInfo.getImpleLeader().getName());
		}
		pro.setRemarks(projectInfo.getRemarks());
//		pro.setProjectNodeDetailResponse(this.findProjectNode(id));
		
//		ProjectCompany projectCompany = projectCompanyDao.queryCompanyByProjectId(id);
//		pro.setCompanyCode(projectCompany.getCompanyCode());
//		pro.setCompanyName(projectCompany.getCompanyName());
		
		return pro;
	}
	/**
	 * 3项目管理-保存/修改
	 * @param projectInfoSaveReq
	 * @param user
	 */
	@Transactional(readOnly = false)
	public void saveProjectInfo(MainProjectInfoSaveReq projectInfoSaveReq, User user) throws ServiceException {
		ProjectInfo pro = new ProjectInfo();
		if(StringUtils.isNotBlank(projectInfoSaveReq.getId())){
			pro = projectInfoService.get(projectInfoSaveReq.getId());
		}
		pro.setProjectName(projectInfoSaveReq.getProjectName());
		pro.setProjectType(projectInfoSaveReq.getProjectType());
		pro.setOffice(officeService.get(projectInfoSaveReq.getOfficeId()));
		pro.setCustInfo(custInfoService.get(projectInfoSaveReq.getCustInfoId()));
		if(StringUtils.isNotBlank(projectInfoSaveReq.getProjectLeaderId())){
			pro.setProjectLeader(UserUtils.get(projectInfoSaveReq.getProjectLeaderId()));
		}
		if(StringUtils.isNotBlank(projectInfoSaveReq.getImpleLeaderId())){
			pro.setImpleLeader(UserUtils.get(projectInfoSaveReq.getImpleLeaderId()));
		}
		pro.setMarketLeader(UserUtils.get(projectInfoSaveReq.getMarketLeaderId()));
		pro.setRemarks(projectInfoSaveReq.getRemarks());
		if(StringUtils.isNotBlank(projectInfoSaveReq.getId())){
			pro.preUpdateForWechart(user);
			projectInfoDao.update(pro);
		}else{
			pro.setProjectCode(CodeUtils.genCode("P", DateUtils.getDate("yyyyMMdd"), 3));
			pro.preInsertForWeChart(user);
			projectInfoDao.insert(pro);
			MainProjectMaintenanceSaveReq req = new MainProjectMaintenanceSaveReq();
			req.setProjectId(pro.getId());
			req.setProjectName(pro.getProjectName());
			req.setProjectState("3");
			req.setRemarks("新建项目");
			req.setChangeTime(new Date().getTime());
			this.saveProjectMaintenance(req, user);
		}
		
		//保存节点
//		projectNodeService.deleteProjectNodeByProjectId(pro.getId());
		if(projectInfoSaveReq.getProjectNodeReqs() != null && projectInfoSaveReq.getProjectNodeReqs().size() != 0){
			int oderNum = 1;
			for (MainProjectNodeReq ss : projectInfoSaveReq.getProjectNodeReqs()) {
				if(StringUtils.isBlank(ss.getNodeId())){
					ProjectNode cc = new ProjectNode();
					cc.preInsertForWeChart(user);
					cc.setProjectId(pro.getId());
					cc.setNodeName(ss.getNodeName());
					cc.setNodeAddress(ss.getNodeAddress());
					cc.setOrderNum(oderNum);
					projectNodeDao.insert(cc);
					oderNum++ ;
				}else{
					ProjectNode p = projectNodeDao.get(ss.getNodeId());
					p.setNodeName(ss.getNodeName());
					p.setNodeAddress(ss.getNodeAddress());
					p.preUpdateForWechart(user);
					projectNodeDao.update(p);
				}

			}
		}
		//保存项目与企业关联关系
//		if(StringUtils.isNotBlank(projectInfoSaveReq.getCompanyCode())){
//			//企业编号不为空，判断企业是否绑定
//			ProjectCompany p = projectCompanyDao.queryBanding(projectInfoSaveReq.getCompanyCode());
//			if(p == null ){
//				ProjectCompany projectCompany = new ProjectCompany();
//				projectCompany.setCompanyCode(projectInfoSaveReq.getCompanyCode());
//				projectCompany.setProjectId(pro.getId());
//				projectCompanyService.saveForWeb(projectCompany, user);
//			}else{
//				throw new ServiceException("该企业已绑定！");
//			}
//		}else{
//			ProjectCompany projectCompany = new ProjectCompany();
//			projectCompany.setCompanyCode(projectInfoSaveReq.getCompanyCode());
//			projectCompany.setProjectId(pro.getId());
//			projectCompanyService.saveForWeb(projectCompany, user);
//		}
		
		
	}
	/**
	 * 4项目维护管理
	 * @param req
	 * @param user
	 */
	@Transactional(readOnly = false)
	public void saveProjectMaintenance(MainProjectMaintenanceSaveReq req, User user) {
		ProjectMaintenance p = new ProjectMaintenance();
		if(StringUtils.isNotBlank(req.getId()) && !"0".equals(req.getId())){
			p = projectMaintenanceDao.get(req.getId());
			p.preUpdateForWechart(user);
		}else{
			p.preInsertForWeChart(user);
		}
		p.setProjectId(req.getProjectId());
		p.setProjectName(req.getProjectName());
		p.setProjectState(req.getProjectState());
		p.setProjectMaintenanceState(req.getProjectState());
		p.setRemarks(req.getRemarks());
		if(req.getChangeTime() != 0l){
			p.setChangeTime(DateUtils.parseDateFormUnix(req.getChangeTime(), DateUtils.YYYY_MM_DD_HH_MM_SS));
		}
		projectMaintenanceDao.insert(p);
		if("1".equals(req.getProjectState())){
			p.setOnLineDate(DateUtils.parseDateFormUnix(req.getChangeTime(), DateUtils.YYYY_MM_DD));
		}
		p.preUpdateForWechart(user);
		projectInfoDao.updateProjectState(p);
		
	}
	/**
	 * 5项目维护管理-列表查询
	 * @param page
	 * @param req
	 * @param user
	 * @return
	 */
	public Page<MainProjectMaintenanceResponse> findProjectMaintenancePage(Page<ProjectMaintenance> page,
			MainProjectMaintenanceHandleReq req, User user) {
		ProjectMaintenance p = new ProjectMaintenance();
		p.setProjectId(req.getProjectId());
		Page<ProjectMaintenance> pa = projectMaintenanceService.findPage(page, p);
		List<MainProjectMaintenanceResponse> newList = Lists.newArrayList();
		for (ProjectMaintenance m : pa.getList()) {
			MainProjectMaintenanceResponse project = new MainProjectMaintenanceResponse();
			project.setProjectName(m.getProjectName());
			project.setProjectMaintenanceStateName(DictUtils.getDictLabel(m.getProjectMaintenanceState(), "project_state", ""));
			project.setChangeTime(m.getChangeTime().getTime());
			project.setCreateTime(m.getCreateTime().getTime());
			project.setRemarks(m.getRemarks());
			project.setCreateBy(UserUtils.get(m.getCreateBy().getId()).getName());
			newList.add(project);
		}
		Page<MainProjectMaintenanceResponse> newPage = new Page<MainProjectMaintenanceResponse>();
		newPage.setCount(page.getCount());
		newPage.setPageNo(page.getPageNo());
		newPage.setPageSize(page.getPageSize());
		newPage.setList(newList);
		return newPage;
	}
	/**
	 * 6查询节点信息
	 * @param projectId
	 * @return
	 */
	public List<MainProjectNodeDetailResponse> findProjectNode(String projectId) {
		List<ProjectNode> p = projectNodeDao.findProjectNode(projectId);
		ArrayList<MainProjectNodeDetailResponse> list = Lists.newArrayList();
		for (ProjectNode s: p) {
			MainProjectNodeDetailResponse resp = change(s,MainProjectNodeDetailResponse.class);
			resp.setNodeId(s.getId());
			list.add(resp);
		}
		return list;
	}
}
