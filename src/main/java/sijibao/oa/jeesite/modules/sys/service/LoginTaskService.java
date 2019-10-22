/**
 * Copyright &copy; 2012-2013 <a href="httparamMap://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.sys.service;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.sijibao.oa.modules.intfz.service.common.IntfzCommonService;
import com.sijibao.oa.modules.sys.entity.Log;

/**
 * 定时任务 把sys_log表中登录日志信息写入到txt文件中
 * @author huangkai
 * @version 2019-03-21
 */
@Service
@Lazy(false)
public class LoginTaskService{
	@Autowired
	private LogService logService;
	@Autowired
	private IntfzCommonService intfzCommonService;

//	@Scheduled(cron = "0 0 1 * * ?") //凌晨一点执行
	@Scheduled(cron = "0 0/30 * * * ?") //每半小时 用于测试
	public void saveLoginInfo() {
		Log log = new Log();
		Calendar c = Calendar.getInstance();
		Date date = new Date();
		c.setTime(date);
		c.add(Calendar.DATE, -1);
		Date beginDate = c.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdfs = new SimpleDateFormat("yyyyMMdd");
		String bDate = sdf.format(beginDate);
		String eDate = sdf.format(date);
		String logDate = sdfs.format(date);
		String resultStr = "";
		try {
			log.setBeginDate(sdf.parse(bDate));
			log.setEndDate(sdf.parse(eDate));
			log.setRequestUri("getLoginInfo");
			List<Log> logList = logService.findList(log);
			if(null != logList && logList.size() > 0) {
				StringBuffer sb = new StringBuffer("");
				int temp = 1;
				for (Log result : logList) {
					String params = result.getParams();
					//String xx = "loginName=17607109890&userName=佘浩源&loginDate=20190321094356&appVersion=1.6&phoneType=iPhone&deviceCode=72F2837B-DC76-4F30-B170-23AE89A400E3"
					String[] pars = params.split("&");
					for (String s : pars) {

						String[] parss = s.split("=");
						sb.append(parss[1]);
						if (temp < pars.length) {
							sb.append("$$");
						}
						temp++;
					}
					sb.append("\n");
					temp = 1;
				}
				resultStr = sb.toString();
			}
				String fileName = "user_oalogin.txt";
				String path = "/data/logs/LOG/"+ logDate +"/";
				File dir = new File(path);
				//检查放置文件的文件夹路径是否存在，不存在则创建
				if (!dir.exists()) {
					dir.mkdirs();// mkdirs创建多级目录
				}


				File file = new File(path+fileName);
				//检查目标文件是否存在，不存在则创建
				if (!file.exists()) {
					file.createNewFile();// 创建目标文件
				}
				if(!"".equals(resultStr)) {
					FileOutputStream outStream = new FileOutputStream(file);    //文件输出流用于将数据写入文件
					outStream.write(resultStr.getBytes("UTF-8"));
					outStream.close();    //关闭文件输出流
				}

			String path2 = "/data/applog/tomcat_oa/login/"+ logDate +"/";
			File dir2 = new File(path2);
			//检查放置文件的文件夹路径是否存在，不存在则创建
			if (!dir2.exists()) {
				dir2.mkdirs();// mkdirs创建多级目录
			}


			File file2 = new File(path2+fileName);
			//检查目标文件是否存在，不存在则创建
			if (!file2.exists()) {
				file2.createNewFile();// 创建目标文件
			}
			if(!"".equals(resultStr)) {
				FileOutputStream outStream2 = new FileOutputStream(file);    //文件输出流用于将数据写入文件
				outStream2.write(resultStr.getBytes("UTF-8"));
				outStream2.close();    //关闭文件输出流
			}



			}catch(Exception e){
				e.printStackTrace();
			}
		}
}

		
