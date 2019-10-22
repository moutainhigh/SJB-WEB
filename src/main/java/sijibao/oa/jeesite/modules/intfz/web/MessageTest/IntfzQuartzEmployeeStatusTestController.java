package sijibao.oa.jeesite.modules.intfz.web.MessageTest;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sijibao.oa.common.utils.DateUtils;
import com.sijibao.oa.common.web.BaseController;
import com.sijibao.oa.modules.intfz.bean.BaseResp;
import com.sijibao.oa.modules.intfz.enums.IntfzRs;
import com.sijibao.oa.modules.intfz.request.JobTimeReq;
import com.sijibao.oa.modules.intfz.service.employeestatus.IntfzEmployeeStatusrecordService;
import com.sijibao.oa.modules.intfz.utils.Constant;
import com.sijibao.oa.modules.oa.entity.EmployeeStatusMonth;
import com.sijibao.oa.modules.oa.entity.EmployeeStatusrecord;
import com.sijibao.oa.modules.oa.service.EmployeeStatusMonthService;
import com.sijibao.oa.modules.oa.service.EmployeeStatusrecordService;
import com.sijibao.oa.modules.sys.entity.User;
import com.sijibao.oa.modules.sys.utils.UserUtils;

/**
 * 员工状态数据抽取Controller
 * @author xuby
 * @date 2018-09-18
 */
@Controller
@RequestMapping(value = "quartz/empStatusTest")
public class IntfzQuartzEmployeeStatusTestController extends BaseController{
	
	@SuppressWarnings("unused")
	@Autowired
	private IntfzEmployeeStatusrecordService intfzEmployeeStatusrecordService;
	@Autowired
	private EmployeeStatusrecordService employeeStatusrecordService;
	@Autowired
	private EmployeeStatusMonthService employeeStatusMonthService;
	/**
	 * 每天定时抽取前一天的员工状态数据进行汇总
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "synEmployeeStatusJobTest")
	@ResponseBody
	public BaseResp<String> synEmployeeStatusJob(JobTimeReq req, HttpServletRequest request, HttpServletResponse response){
		logger.info("==============IntfzQuartzEmployeeStatusController synEmployeeStatusJobTest start==============");
		
		List<EmployeeStatusrecord> newEmployeeStatusrecord = new ArrayList<EmployeeStatusrecord>();
		User user = UserUtils.getByLoginName("admin");
		
		/*
		 * 查询前一天所有状态记录数据
		 */
		//查询前一天包含项目的人员最新的项目中数据
		EmployeeStatusrecord employeeStatusrecord = new EmployeeStatusrecord();
		employeeStatusrecord.setQueryDate(req.getYesterday());
		List<EmployeeStatusrecord> empStatusProjectList = employeeStatusrecordService.queryEmpStatusProjectLastInfo(employeeStatusrecord); //项目中
		
		//查询前一天不包含项目的人员最新的状态数据
		List<EmployeeStatusrecord> empStatusLastList = employeeStatusrecordService.queryEmpStatusLastInfo(employeeStatusrecord);
		if(empStatusProjectList != null && empStatusProjectList.size() > 0){
			newEmployeeStatusrecord.addAll(empStatusProjectList);
		}
		if(empStatusLastList != null && empStatusLastList.size() > 0){
			newEmployeeStatusrecord.addAll(empStatusLastList);
		}
		
		//根据人员ID，项目ID、项目节点/基地，年月份来更新天数
		if(newEmployeeStatusrecord != null && newEmployeeStatusrecord.size() > 0){
			for(EmployeeStatusrecord ne:newEmployeeStatusrecord){
				// 获取当前用户人员报表信息
				if(ne.getUser() != null){
					EmployeeStatusMonth employeeStatusMonth = new EmployeeStatusMonth();
					employeeStatusMonth.setUser(ne.getUser());
					employeeStatusMonth.setDatetime(DateUtils.format(ne.getCreateTime(), DateUtils.YYYY_MM));
					List<EmployeeStatusMonth> monthList = employeeStatusMonthService.findList(employeeStatusMonth);
					if(monthList == null || monthList.size() == 0){
						logger.info("========员工月报表{}该用户没有数据，开始新增=======",ne.getUser().getId());
						//如果没有则直接新增
						EmployeeStatusMonth em = new EmployeeStatusMonth();
						em.setBaseId(ne.getBaseId()); //基地
						em.setUser(ne.getUser()); //用户
						em.setNodeId(ne.getProjectNodeId()); //项目节点ID
						em.setDays("1"); //保持天数
						em.setProjectId(ne.getProjectId()); //项目ID
						em.setStatus(ne.getStatus()); //用户状态
						em.setDatetime(DateUtils.format(ne.getCreateTime(), DateUtils.YYYY_MM)); //年月
						em.setUpdateTime(req.getToday());
						employeeStatusMonthService.saveForWeb(em, user); //保存
						
					}else{
						logger.info("========员工月报表{}该用户有数据，开始判断数据状态=======",ne.getUser().getId());
						
						//月报表中有数据则判断，项目、地点节点/基地是否相等，都不相同则进行插入，相同则进行天数更新
						switch (ne.getStatus()) {
						case Constant.EMPLOYEE_STATUS_IN: //项目中
							logger.info("========{}数据状态为项目中===========",ne.toString());
							int count1 = 0;
							for(EmployeeStatusMonth m:monthList){
								if (m.getProjectId().equals(ne.getProjectId())
										&& m.getNodeId().equals(ne.getProjectNodeId())
										&& DateUtils.format(ne.getCreateTime(), DateUtils.YYYY_MM)
												.equals(m.getDatetime())) { // 项目、地点节点、年月都相同，则天数加一
									logger.info("========{}数据状态为项目中，匹配到了对应项目数据，开始更新===========",ne.toString());
									logger.info("=====更新数据为{}=====",m.toString());
									int day = Integer.parseInt(m.getDays())+1;
									m.setDays(String.valueOf(day));
									m.setUpdateTime(req.getToday());
									employeeStatusMonthService.saveForWeb(m, user); //保存
									count1++;
									break;
								}
							}
							
							if(count1 == 0){ //没有匹配到则进行新增
								logger.info("========{}数据状态为项目中，没有匹配到对应项目数据，开始新增===========",ne.toString());
								EmployeeStatusMonth em = new EmployeeStatusMonth();
								em.setBaseId(ne.getBaseId()); //基地
								em.setUser(ne.getUser()); //用户
								em.setNodeId(ne.getProjectNodeId()); //项目节点ID
								em.setDays("1"); //保持天数
								em.setProjectId(ne.getProjectId()); //项目ID
								em.setStatus(ne.getStatus()); //用户状态
								em.setDatetime(DateUtils.format(ne.getCreateTime(), DateUtils.YYYY_MM)); //年月
								em.setUpdateTime(req.getToday());
								employeeStatusMonthService.saveForWeb(em, user); //保存
							}
							
							break;
						case Constant.EMPLOYEE_STATUS_OUT: //待命中
							logger.info("========{}数据状态为待命中===========",ne.toString());
							//查询T-2的最终状态是否为项目中，如果是则当天也算是项目中，否则算是待命中
							// TODO
							EmployeeStatusrecord s = new EmployeeStatusrecord();
							s.setCreateTime(DateUtils.getBeforeDay(2));
							s.setUser(ne.getUser());
							s = employeeStatusrecordService.getNewTimeForUserId(s);
							  
							if(s != null){
								switch (s.getStatus()) {
								case Constant.EMPLOYEE_STATUS_IN: //T-2最终状态为项目中
									
									int count3 = 0;
									for(EmployeeStatusMonth m:monthList){
										if (m.getProjectId().equals(s.getProjectId())
												&& m.getNodeId().equals(s.getProjectNodeId())) { // 项目、地点节点、年月都相同，则天数加一
											logger.info("========{}T-2数据状态为项目中，匹配到了对应项目数据，开始更新===========",ne.toString());
											logger.info("=====更新数据为{}=====",m.toString());
											int day = Integer.parseInt(m.getDays())+1;
											m.setDays(String.valueOf(day));
											m.setUpdateTime(req.getToday());
											employeeStatusMonthService.saveForWeb(m, user); //保存
											count3++;
											break;
										}
									}
									
									if(count3 == 0){ //没有匹配到则进行新增
										logger.info("========{}T-2数据状态为项目中，没有匹配到对应项目数据，开始新增===========",ne.toString());
										EmployeeStatusMonth em = new EmployeeStatusMonth();
										em.setBaseId(s.getBaseId()); //基地
										em.setUser(s.getUser()); //用户
										em.setNodeId(s.getProjectNodeId()); //项目节点ID
										em.setDays("1"); //保持天数
										em.setProjectId(s.getProjectId()); //项目ID
										em.setStatus(s.getStatus()); //用户状态
										em.setDatetime(DateUtils.format(s.getCreateTime(), DateUtils.YYYY_MM)); //年月
										em.setUpdateTime(req.getToday());
										employeeStatusMonthService.saveForWeb(em, user); //保存
									}
									
									//并且新增一条今天的待命中数据
									EmployeeStatusrecord es = new EmployeeStatusrecord();
									es.setUser(ne.getUser());
									es.setStatus(ne.getStatus());
									es.setUserAction(ne.getUserAction());
									es.setProjectId(ne.getProjectId());
									es.setProjectName(ne.getProjectName());
									es.setProjectNodeId(ne.getProjectNodeId());
									es.setProjectNodeName(ne.getProjectNodeName());
									es.setBaseId(ne.getBaseId());
									es.setRemarks(ne.getRemarks());
									es.setUpdateTime(req.getToday());
									employeeStatusrecordService.saveForWeb(es, ne.getUser());
									break;
								case Constant.EMPLOYEE_STATUS_OUT: //T-2最终状态为待命中
									
									int count2 = 0;
									for(EmployeeStatusMonth m:monthList){
										if (Constant.EMPLOYEE_STATUS_OUT.equals(m.getStatus()) && DateUtils
												.format(ne.getCreateTime(), DateUtils.YYYY_MM).equals(m.getDatetime())) { //状态、年月相同，则天数加一
											int day = Integer.parseInt(m.getDays())+1;
											m.setDays(String.valueOf(day));
											m.setUpdateTime(req.getToday());
											employeeStatusMonthService.saveForWeb(m, user); //保存
											count2++;
										}
									}
									
									if(count2 == 0){ //没有匹配到则进行新增
										EmployeeStatusMonth em = new EmployeeStatusMonth();
										em.setBaseId(ne.getBaseId()); //基地
										em.setUser(ne.getUser()); //用户
										em.setNodeId(ne.getProjectNodeId()); //项目节点ID
										em.setDays("1"); //保持天数
										em.setProjectId(ne.getProjectId()); //项目ID
										em.setStatus(ne.getStatus()); //用户状态
										em.setDatetime(DateUtils.format(ne.getCreateTime(), DateUtils.YYYY_MM)); //年月
										em.setUpdateTime(req.getToday());
										employeeStatusMonthService.saveForWeb(em, user); //保存
									}
									
									break;
								default:
									break;
								}
							}else{ //T-2没有数据则直接按照待命中进行匹配
								int count2 = 0;
								for(EmployeeStatusMonth m:monthList){
									if (Constant.EMPLOYEE_STATUS_OUT.equals(m.getStatus()) && DateUtils
											.format(ne.getCreateTime(), DateUtils.YYYY_MM).equals(m.getDatetime())) { //状态、年月相同，则天数加一
										int day = Integer.parseInt(m.getDays())+1;
										m.setDays(String.valueOf(day));
										m.setUpdateTime(req.getToday());
										employeeStatusMonthService.saveForWeb(m, user); //保存
										count2++;
									}
								}
								
								if(count2 == 0){ //没有匹配到则进行新增
									EmployeeStatusMonth em = new EmployeeStatusMonth();
									em.setBaseId(ne.getBaseId()); //基地
									em.setUser(ne.getUser()); //用户
									em.setNodeId(ne.getProjectNodeId()); //项目节点ID
									em.setDays("1"); //保持天数
									em.setProjectId(ne.getProjectId()); //项目ID
									em.setStatus(ne.getStatus()); //用户状态
									em.setDatetime(DateUtils.format(ne.getCreateTime(), DateUtils.YYYY_MM)); //年月
									em.setUpdateTime(req.getToday());
									employeeStatusMonthService.saveForWeb(em, user); //保存
								}
							}
							
							break;
						default:
							break;
						}
					}
				}
				//更新当前用户的所有状态变更记录报表状态为1，已处理
				ne.setQueryDate(req.getYesterday());
				ne.setReportStatus("1"); //已处理
				employeeStatusrecordService.updateReportStatusForUser(ne);
			}
		}
		
		//今天没有更新过月报表数据，则将月报表数据的最新一条天数+1
		EmployeeStatusMonth queryMonth = new EmployeeStatusMonth();
		queryMonth.setUpdateTime(req.getToday());
		queryMonth.setDatetime(DateUtils.format(req.getYesterday(), DateUtils.YYYY_MM));
		List<EmployeeStatusMonth> empMonthLastList = employeeStatusMonthService.queryMonthInfoNoUpdateAndLastInfo(queryMonth);
		
		if(empMonthLastList != null && empMonthLastList.size() > 0){
			for(EmployeeStatusMonth e:empMonthLastList){
				int day = Integer.parseInt(e.getDays())+1;
				e.setDays(String.valueOf(day));
				e.setUpdateTime(req.getToday());
				employeeStatusMonthService.saveForWeb(e, user); //月报表数据+1
			}
		}
			
		//如果T-1是上个月最后一天，则查询月报表所有今天更新的数据
		if(!DateUtils.format(req.getYesterday(), DateUtils.YYYY_MM).equals(DateUtils.format(req.getToday(), DateUtils.YYYY_MM))){
			EmployeeStatusMonth esm = new EmployeeStatusMonth();
			esm.setUpdateTime(req.getToday());
			esm.setDatetime(DateUtils.format(req.getYesterday(), DateUtils.YYYY_MM));
			List<EmployeeStatusMonth> updateMonthList = employeeStatusMonthService.findListForUpdateTimeAndDateTime(esm);
			for(EmployeeStatusMonth e:updateMonthList){
				e.setMonthStatus("1"); //上个月数据已封存
				e.setUpdateTime(req.getToday());
				employeeStatusMonthService.updateMonthStatus(e); //月报表数据更新时间
				
				EmployeeStatusMonth m = new EmployeeStatusMonth();
				m.setUser(e.getUser());
				m.setStatus(e.getStatus());
				m.setProjectId(e.getProjectId());
				m.setNodeId(e.getNodeId());
				m.setBaseId(e.getBaseId());
				m.setRemarks(e.getRemarks());
				int day = Integer.valueOf(DateUtils.formatDate(req.getToday(), "dd"))-1;
				m.setDays(String.valueOf(day));
				m.setDatetime(DateUtils.format(req.getToday(), DateUtils.YYYY_MM)); //年月
				m.setUpdateTime(req.getToday());
				employeeStatusMonthService.saveForWeb(m, user); //新增数据
			}
		}else{
			//如果其中有天数为0的数据，则进行删除
			employeeStatusMonthService.deleteMonthZero(null);
		}	
		
		logger.info("==============IntfzQuartzEmployeeStatusTestController synEmployeeStatusJob end==============");
		return new BaseResp<String>(IntfzRs.SUCCESS,"执行成功!","");
	} 
	
}
