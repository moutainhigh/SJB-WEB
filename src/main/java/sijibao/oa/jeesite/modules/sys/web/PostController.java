package sijibao.oa.jeesite.modules.sys.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sijibao.oa.common.config.Global;
import com.sijibao.oa.common.persistence.Page;
import com.sijibao.oa.common.utils.DateUtils;
import com.sijibao.oa.common.utils.StringUtils;
import com.sijibao.oa.common.web.BaseController;
import com.sijibao.oa.modules.sys.entity.PostInfo;
import com.sijibao.oa.modules.sys.service.PostService;
import com.sijibao.oa.modules.sys.utils.UserUtils;

/**
 * 岗位信息管理
 * @author wuys
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/post")
public class PostController extends BaseController  {

	
	
	@Autowired
	private PostService postService;
	
	@ModelAttribute
	public PostInfo get(@RequestParam(required=false) String id) {
		PostInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = postService.get(id);
		}
		
		if (entity == null){
			entity = new PostInfo();
		}
		return entity;
	}

	/**
	 * 岗位信息列表
	 * @param PostInfo
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"list", ""})
	public String list(PostInfo postInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<PostInfo> page = postService.findPage(new Page<PostInfo>(request, response), postInfo); 
		model.addAttribute("page", page);
		return "modules/sys/postInfoList";
	}
	/**
	 * 新增岗位
	 * @param postInfo
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "form")
	public String form(@RequestParam(required=false) String id, PostInfo postInfo, Model model) {
		String newTime = DateUtils.getDate("yyyy-MM-dd HH:mm:ss");
		PostInfo post = new PostInfo();
		if(StringUtils.isNotBlank(id)){
			post = postService.get(id);
			
		}else{
			post.setCreateByName(UserUtils.getUser().getName());
			post.setCreateDateTime(newTime);
		}
		List<PostInfo> items = postService.findList(null);
		model.addAttribute("post", post);
		model.addAttribute("newTime", newTime);
		model.addAttribute("postInfo", postInfo);
		model.addAttribute("items", items);
		return "modules/sys/postInfoForm";
	}
	/**
	 * 岗位保存
	 * @param postInfo 岗位信息对象
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "save")
	public String save(PostInfo postInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, postInfo)){
			addMessage(model, "保存用户失败，岗位信息不能为空！");
			return form(adminPath, postInfo, model);
		}
		if(StringUtils.isNotBlank(postInfo.getPostCode())){//校验岗位编号不能重复
			int count = postService.queryPostInfoCodeById(postInfo);
			if(count > 0){
				model.addAttribute("message", "岗位编号重复！");
				model.addAttribute("postInfo", postInfo);
				return "modules/sys/postInfoForm";
			}
		}
		logger.info("=======开始校验岗位名称======"+postInfo.getPostName());
		if(StringUtils.isNotBlank(postInfo.getPostName())){//校验岗位名称不能重复
			int count = postService.queryPostInfoNameById(postInfo);
			if(count > 0){
				model.addAttribute("message", "岗位名称重复！");
				model.addAttribute("postInfo", postInfo);
				return "modules/sys/postInfoForm";
			}
		}
		logger.info("=======结束校验岗位名称======");
		postInfo.setDelFlag("0");
        if(StringUtils.isNotBlank(postInfo.getId()) && postInfo.getPostCode().equals(postInfo.getOldPostCode())){

        }else if(StringUtils.isBlank(postInfo.getId()) && postInfo.getPostCode().indexOf("GW") < 0){
            postInfo.setPostCode("GW" + postInfo.getPostCode());
        }else if(StringUtils.isNotBlank(postInfo.getId()) && !postInfo.getPostCode().equals(postInfo.getOldPostCode()) && postInfo.getPostCode().indexOf("GW") < 0){

		}
		try{
			postService.save(postInfo);
		}catch(Exception e){
			if(e instanceof DuplicateKeyException){
				model.addAttribute("message", "岗位编号重复！");
				model.addAttribute("postInfo", postInfo);
				return "modules/sys/postInfoForm";
			}
		}

		addMessage(redirectAttributes, "保存岗位信息成功");
		return "redirect:"+Global.getAdminPath()+"/sys/post?repage";
	}
	/**
	 * 删除岗位
	 * @param postInfo
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "delete")
	public String delete(PostInfo postInfo, RedirectAttributes redirectAttributes) {
		postService.delete(postInfo);
		addMessage(redirectAttributes, "删除岗位信息成功");
		return "redirect:"+Global.getAdminPath()+"/sys/post?repage";
	}
}
