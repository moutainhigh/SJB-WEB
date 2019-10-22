package sijibao.oa.jeesite.modules.intfz.enums;

import com.alibaba.fastjson.JSONObject;

/**
 * 接口层返回结果枚举
 * @author Petter
 */
public enum IntfzRs {

	/**
	 * code:0, message:请求服务器成功
	 */
	SUCCESS(0, "成功"),
	ERROR(1, "失败"),
	HALF(2,"半成功"),
	/**
	 * code:-1, message:服务器未知异常
	 */
	UNKNOWN(-1, "服务器未知异常"),
	/**
	 * code:-1, message:参数错误
	 */
	PARAM(400, "参数错误"),
	AUTH_FAILED(401, "token认证失败"),
	NOTFOUND(404, "访问页面不存在"),
	LOGIN_FORBID(20,"您已被禁止登录，请联系人事开通登录权限");
	
	// 成员变量
	private Integer code;
	private String msg;
	
	// 构造方法
    private IntfzRs(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    
    private IntfzRs(String msg) {
    	this.msg = msg;
    }

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}
	
	/**
	 * 添加内容，替换当前内容
	 * @param msg
	 * @return
	 */
	public String message(Object msg){
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("status", this.code);
		jsonObject.put("message", msg);
		return jsonObject.toJSONString();
	}
	
	public String message() {
		return message(this.msg);
	}
	
	/**
	 * 添加描述内容，替换当前内容
	 * @param msg
	 * @return 返回当前对象
	 */
	public IntfzRs msg(String msg){
		this.setMsg(msg);
		return this;
	}
	
	public static Integer getCode(String msg) {
        for (IntfzRs intfzRs : IntfzRs.values()){
            if (intfzRs.getMsg().equals(msg))
                return intfzRs.code;
        }
        return 0;
    }
	
	public static IntfzRs getPayRsByMsg(String msg) {
        for (IntfzRs intfzRs : IntfzRs.values()){
            if (intfzRs.getMsg().equals(msg))
                return intfzRs;
        }
        return null;
    }
	
}
