package sijibao.oa.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Author Tao.yu
 * @Date 2019/10/15 17:01
 * @Version v3.0
 **/
@Controller
public class LoginController {

	@RequestMapping(value="/home")
	public String home(){
		System.out.println("11111");
		return "login";
	}

	@RequestMapping(value = "/ceshi")
	public ModelAndView index() {
		ModelAndView view = new ModelAndView("modules/sys/sysLogin");
		view.addObject("userName", "sfadsadsfddsfds");
		return view;
	}

}
