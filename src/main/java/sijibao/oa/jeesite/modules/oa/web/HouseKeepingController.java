/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.oa.web;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.sijibao.oa.common.persistence.Page;
import com.sijibao.oa.common.utils.DateUtils;
import com.sijibao.oa.common.utils.StringUtils;
import com.sijibao.oa.common.web.BaseController;
import com.sijibao.oa.modules.oa.entity.HouseKeeping;
import com.sijibao.oa.modules.oa.service.HouseKeepingService;
import com.sijibao.oa.modules.sys.entity.User;
import com.sijibao.oa.modules.sys.utils.UserUtils;
//import com.sijibao.tcp.service.client.file.FileUploadRes;
//import com.sijibao.tcp.service.client.file.impl.FastDFSFileUploadImpl;

/**
 * 内务工作管理Controller
 * @author Petter
 * @version 2018-04-16
 */
@Controller
@RequestMapping(value = "${adminPath}/oa/houseKeeping")
public class HouseKeepingController extends BaseController {

	@Autowired
	private HouseKeepingService houseKeepingService;
	
	@ModelAttribute
	public HouseKeeping get(@RequestParam(required=false) String id) {
		HouseKeeping entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = houseKeepingService.get(id);
		}
		if (entity == null){
			entity = new HouseKeeping();
		}
		return entity;
	}
	
	@RequiresPermissions("oa:houseKeeping:view")
	@RequestMapping(value = {"list", ""})
	public String list(HouseKeeping houseKeeping, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		//初始化当前用户当月待签到数据
		houseKeepingService.initDate(user);
		//控制只能上传本人的工作及内务管理照片
		houseKeeping.setUserName(UserUtils.getUser().getName());
		Page<HouseKeeping> page = houseKeepingService.findMonthPage(new Page<HouseKeeping>(request, response), houseKeeping, user); 
		model.addAttribute("page", page);
		return "modules/oa/houseKeepingList";
	}

	@RequiresPermissions("oa:houseKeeping:view")
	@RequestMapping(value = "form")
	public String form(HouseKeeping houseKeeping, Model model) {
		User user = UserUtils.getUser();
		Page<HouseKeeping> page = houseKeepingService.findDayPage(new Page<HouseKeeping>(1, 35), houseKeeping, user);
		List<HouseKeeping> list1 = Lists.newArrayList();
		List<HouseKeeping> list2 = Lists.newArrayList();
		Date compareDate = DateUtils.parse(DateUtils.getDate(), "yyyy-MM-dd");
		for(HouseKeeping hk : page.getList()){
			if(DateUtils.compareDate(DateUtils.parse(hk.getWorkDate(), "yyyy-MM-dd"),
					compareDate) > 0){
				list2.add(hk);
			}else{
				list1.add(hk);
			}
		}
		model.addAttribute("houseKeeping", houseKeeping);
		model.addAttribute("list1", list1);
		model.addAttribute("list2", list2);
		return "modules/oa/houseKeepingForm";
	}

	@RequiresPermissions("oa:houseKeeping:view")
	@RequestMapping(value = "save")
	public String save(HouseKeeping houseKeeping, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, houseKeeping)){
			return form(houseKeeping, model);
		}
		String msg = houseKeepingService.save(houseKeeping, UserUtils.getUser(), "1");
		houseKeeping.setId(null);
		houseKeeping.setIsSign(null);
		houseKeeping.setWorkDate(houseKeeping.getWorkDate().substring(0, 7));
		if(StringUtils.isNotBlank(msg)){
			model.addAttribute("message", "签到失败！"+msg);
		}else{
			model.addAttribute("message", "签到成功！");
		}
		return form(houseKeeping, model);
	}
	
	
	/**
	 *上传文件
	 * @param files  文件
	 * @param request	请求
	 * @param response	响应
	 * @param redirectAttributes	参数
	 * @param model	
	 * @return
	 */
//	@RequiresPermissions("oa:houseKeeping:view")
//	@ResponseBody
//	@RequestMapping(value = "upload", method=RequestMethod.POST)
//    public String upload(@RequestParam MultipartFile files, HttpServletRequest request, 
//    		HttpServletResponse response, RedirectAttributes redirectAttributes, Model model) {
//		JSONObject resJson = new JSONObject();
//		try {
//			String workId = request.getParameter("src").split("_")[1];
//			User user =  UserUtils.getUser();
//			String photoType = request.getParameter("src").split("_")[0];
//			//服务器参数
//			/*String ip = SysParamsUtils.getParamValue(Global.FILE_SERVER_IP, Global.SYS_PARAMS, "192.168.0.211"); //图片服务器IP
//			String port = SysParamsUtils.getParamValue(Global.FILE_SERVER_PORT, Global.SYS_PARAMS, "8108"); //图片服务器端口
//			String outtime = SysParamsUtils.getParamValue(Global.FILE_SERVER_OUTTIME, Global.SYS_PARAMS, "10000"); //延时时间*/
//			String serverUrl = UploadUtils.getServerUrl(); //附件服务器地址
//			
//			if(files == null){
//				resJson.put("resCode", 0);
//				resJson.put("resDesc", "请重新选择文件");
//				return resJson.toString();
//			}
//			
//			FastDFSFileUploadImpl fileUpload = new FastDFSFileUploadImpl();
//			ByteArrayOutputStream out=new ByteArrayOutputStream();
//			InputStream ins = files.getInputStream();
//			byte[] buffer=new byte[1024*4];
//			int n=0;
//			while ( (n=ins.read(buffer)) !=-1) {
//				out.write(buffer,0,n);
//			}
//			ins.close();
//			byte[] in2b = out.toByteArray();
//			out.close();
//			FileUploadRes res = fileUpload.uploadImage(files.getOriginalFilename(), in2b);
//			
//			//将文件信息保存到对象
//			HouseKeeping hk = houseKeepingService.get(workId);
//			switch(photoType){
//				case "sign":
//					hk.setSignCode(photoType);
//					break;
//				case "work":
//					hk.setWorkCode(photoType);
//					break;
//				case "inner":
//					hk.setInnerCode(photoType);
//					break;
//				default:
//					break;
//			}
//			hk.preUpdate();
//			hk.setUpdateBy(user);
//			//保存图片明细信息到数据库
//			houseKeepingService.save(hk);
//			HouseKeepingPhoto photo = new HouseKeepingPhoto();
//			photo.setWorkDate(hk.getWorkDate());
//			photo.setUserName(hk.getUserName());
//			photo.setPhotoType(photoType);
//			String storfile = res.getStorageFile();
//			photo.setUrl(storfile);
//			houseKeepingService.savePhoto(photo);
//			JSONObject storfiles = new JSONObject();
//			storfiles.put("url",storfile);
//			storfiles.put("fileName",files.getOriginalFilename());
//			storfiles.put("serverUrl",serverUrl);
//
//			resJson.put("resCode", 1);
//			resJson.put("resDesc", "上传成功.");
//			resJson.put("storfiles", storfiles);
//		} catch (Exception e) {
//			logger.error(e.getMessage(), e);
//			resJson.put("resCode", 0);
//			resJson.put("resDesc", e.getMessage());
//		}
//		return resJson.toString();
//    }
	
}