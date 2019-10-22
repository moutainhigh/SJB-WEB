/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.intfz.utils;

import java.lang.reflect.Field;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 转换特殊字符
 */
public class CharChangeUtils {

	private static Logger log = LoggerFactory.getLogger(CharChangeUtils.class);


	public static void CharChange (Object object) {
		Class clazz = object.getClass();
		Field[] fields = clazz.getDeclaredFields();
        String va = null;
        for (Field f : fields){
			try{
				f.setAccessible( true ); // 设置些属性是可以访问的
//				String type = f.getType().toString();//属性的类型
//				if(f.getType() == String.class){//如果属性是String类型
//					 va = (String) f.get(object);//获取属性的值
					//替换特殊字符
//					if(va.contains("(")){
//						va = va.replace("(","（");
//					}
//					if(va.contains(")")){
//						va = va.replace(")","）");
//					}
//					if(va.contains("'")){
//						va = va.replace("'","’");
//					}
//					if(va.contains("\"")){
//						va = va.replace("\"","”");
//					}
//					if(va.contains("\\")){
//						va = va.replace("\\","、");
//					}
//					if(va.contains("{")){
//						va = va.replace("{","｛");
//					}
//					if(va.contains("}")){
//						va = va.replace("}","｝");
//					}
//					if(va.contains("[")){
//						va = va.replace("[","【");
//					}
//					if(va.contains("]")){
//						va = va.replace("]","】");
//					}
//                    f.set(object,va);
//				}
			}catch(Exception e){

			}

		}
	}


}