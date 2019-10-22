package sijibao.oa.jeesite.modules.intfz.validator;

import java.lang.annotation.*;

/**
 * 验证字段是否必填
 * @author xuby
 * @time 2018年6月1日 下午5:39:29
 */
@Documented
@Target(ElementType.FIELD)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface IntfzValid {

    int min() default 0;
    int max();
    String length_msg() default "";
    //String format();
    //String foramt_msg();
    boolean nullable() default true; //可空
}
