package sijibao.oa.jeesite.modules.intfz.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * 接口处理工具类
 * 
 * @author xuhang
 *
 */
public class IntfzUtils {

	/**
	 * 日志对象
	 */
	//private static Logger logger = LoggerFactory.getLogger(IntfzUtils.class);
	
	/**
	 * 获取请求过来的http二进制流参数
	 * 
	 * @param request
	 * @return
	 */
	public static String getStreamParam(HttpServletRequest request) {
		StringBuffer resBuffer = new StringBuffer();
		/*
		try {
			InputStream is = request.getInputStream();
			byte[] b = new byte[1024];
			int len = 0;
			while ((len = is.read(b)) != -1) {
				resBuffer.append(new String(b, 0, len, "UTF-8"));
			}
			is.close();
			return resBuffer.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		*/
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
			String line = null;
			while ((line = reader.readLine()) != null) {
				resBuffer.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
//			if (null != reader) {
//				try {
//					reader.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
		}
		return resBuffer.toString();
	}

	/**
	 * 填充 键值对返回 参数集合
	 * 
	 * @param params
	 * @return
	 */
	public static Map<String, Object> fillMap(Object[]... params) {
		if (params == null) {
			return null;
		}

		Map<String, Object> params_ = new HashMap<String, Object>();

		for (int i = 0; i < params.length; i++) {
			if (params[i][0] != null && !params[i][0].toString().trim().equals("")) {
				params_.put(params[i][0].toString(), params[i][1] == null ? "" : params[i][1]);
			}
		}

		return params_;
	}

}
