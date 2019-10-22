package sijibao.oa.jeesite.modules.intfz.validator;

import java.lang.reflect.Field;
import java.rmi.ServerException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 验证传输字符串长度
 * 
 * @author xuhang
 * @time 2016年4月19日 下午4:55:20
 */
public class IntfzValidateUtils {

	/**
	 * 日志对象
	 */
	protected static Logger logger = LoggerFactory.getLogger(IntfzValidateUtils.class);
	
	public IntfzValidateUtils() {
		super();
	}
	
	// 解析的入口
	public static void valid(Object object) throws ServerException{
		
		if(object == null){
			throw new ServerException("参数不能为空");
		}
		
		// 获取object的类型
		Class<? extends Object> clazz = object.getClass();
		// 获取该类型声明的成员
		Field[] fields = clazz.getDeclaredFields();
		// 遍历属性
		for (Field field : fields) {
			// 对于private私有化的成员变量，通过setAccessible来修改器访问权限
			field.setAccessible(true);
			
			validate(field, object);
			// 重新设置会私有权限
			field.setAccessible(false);
		}
	}
	
	// 解析的入口
	public static void valid(Object object, boolean syn) throws ServerException {
		
	}

	private static void validate(Field field, Object object) throws ServerException {
		// 获取对象的成员的注解信息
		IntfzValid iv = field.getAnnotation(IntfzValid.class);
		if (iv == null) return;
		
		String value = null;
		try {
			value = field.get(object) == null ? "" : field.get(object).toString();
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
			throw new ServerException("获取数据失败,请重试");
		}
		
		/************* 注解解析工作开始 ******************/
		//不能为空
		if (iv.nullable() == false && StringUtils.isBlank(value)) {
			//throw new Exception(value.toString() + "不能为空");
			throw new ServerException(field.getName() + "不能为空");
		}
		if (value.toString().length() > iv.max() && iv.max() != 0) {
			//throw new Exception(value.toString() + "长度不能超过" + iv.max());
			throw new ServerException(field.getName() + "[" + value + "]" + "长度不能超过" + iv.max());
		}
		if (value.toString().length() < iv.min() && iv.min() != 0) {
			//throw new Exception(value.toString() + "长度不能小于" + iv.min());
			throw new ServerException(field.getName() + "[" + value + "]" + "长度不能小于" + iv.min());
		}
		/************* 注解解析工作结束 ******************/
	}
}
