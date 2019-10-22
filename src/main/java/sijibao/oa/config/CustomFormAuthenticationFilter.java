package sijibao.oa.config;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

/**
 * 登录拦截器
 * @Author Tao.yu
 * @Date 2019/10/18 19:37
 * @Version v3.0
 **/
public class CustomFormAuthenticationFilter extends FormAuthenticationFilter {
	public static final String DEFAULT_CAPTCHA_PARAM = "validateCode";
	public static final String DEFAULT_MOBILE_PARAM = "mobileLogin";
	public static final String DEFAULT_MESSAGE_PARAM = "message";

	private String captchaParam = DEFAULT_CAPTCHA_PARAM;
	private String mobileLoginParam = DEFAULT_MOBILE_PARAM;
	private String messageParam = DEFAULT_MESSAGE_PARAM;
}
